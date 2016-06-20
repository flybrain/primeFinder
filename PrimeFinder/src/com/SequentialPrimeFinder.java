package com;

public class SequentialPrimeFinder extends AbstractPrimeFinder {
	//重写父类的countPrimes方法
	public int countPrimes(final int number) {
		return countPrimesInRange(1, number);
	}
}
