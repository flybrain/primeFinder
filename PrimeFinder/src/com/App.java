package com;

public class App {

	public static void main(String[] args) {
		int count=0;
		String type=args[0];
		int num=Integer.parseInt(args[1]);
		if(num>0){
			if(type.equalsIgnoreCase("S")){//单线程计算
				System.out.println("SequentialPrimeFinder...");
				count=new SequentialPrimeFinder().timeAndCompute(num);
			}
			else{//多线程计算
				//线程池大小
				System.out.println("ConcurrentPrimeFinder...");
				int poolSize=Integer.parseInt(args[2]);
				//线程数量
				int numberOfParts=Integer.parseInt(args[3]);
				if(poolSize>0 && numberOfParts>0){
					count=new ConcurrentPrimeFinder(poolSize,numberOfParts).timeAndCompute(num);
				}
			}
		}
		System.out.printf("Number of primes under %d is %d\n", num, count);

	}

}
