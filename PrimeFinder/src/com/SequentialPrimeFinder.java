package com;

public class SequentialPrimeFinder extends AbstractPrimeFinder {
	//��д�����countPrimes����
	public int countPrimes(final int number) {
		return countPrimesInRange(1, number);
	}
}
