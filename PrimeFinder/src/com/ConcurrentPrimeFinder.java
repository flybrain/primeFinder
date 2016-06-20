package com;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
public class ConcurrentPrimeFinder extends AbstractPrimeFinder {
	//����ÿ���̼߳��㷶Χ�ڵ����ֵ�����
	private final int poolSize;
	//�����߳�����
	private final int numberOfParts;
	//�̹߳��췽����������ʼ��ͼ�������
	public ConcurrentPrimeFinder(final int thePoolSize,final int theNumberOfParts) {
		poolSize = thePoolSize;
		numberOfParts = theNumberOfParts;
	}
	//��дcountPrimes����
	public int countPrimes(final int number) {
		int count = 0;
		try {
			final List<Callable<Integer>> partitions = new ArrayList<Callable<Integer>>();
			final int chunksPerPartition = number / numberOfParts;
			for (int i = 0; i < numberOfParts; i++) {
				int lower = (i * chunksPerPartition) + 1;
				int upper;
				if(i == numberOfParts - 1){
					upper=number;
				}
				else{
					upper=lower+ chunksPerPartition - 1;
				}
				partitions.add(new Callable<Integer>() {
					public Integer call() {
						return countPrimesInRange(lower, upper);
					}
				});
			}
			final ExecutorService executorPool = Executors.newFixedThreadPool(poolSize);
			final List<Future<Integer>> resultFromParts = executorPool.invokeAll(partitions, 10000, TimeUnit.SECONDS);
			executorPool.shutdown();
			for (final Future<Integer> result : resultFromParts)
				count += result.get();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return count;
	}
}
