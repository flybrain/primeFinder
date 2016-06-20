package com;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
public class ConcurrentPrimeFinder extends AbstractPrimeFinder {
	//定义每个线程计算范围内的数字的数量
	private final int poolSize;
	//定义线程数量
	private final int numberOfParts;
	//线程构造方法，根据起始点和计算数量
	public ConcurrentPrimeFinder(final int thePoolSize,final int theNumberOfParts) {
		poolSize = thePoolSize;
		numberOfParts = theNumberOfParts;
	}
	//重写countPrimes方法
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
