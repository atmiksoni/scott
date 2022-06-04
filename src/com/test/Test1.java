package com.test;

import java.math.BigDecimal;

import org.junit.Test;

public class Test1 {
	@Test
	public void test() {
		SourceA s = new SourceA();
		TestThread tt = new TestThread(s);
		TestRunnable tr = new TestRunnable(s);
		Thread t = new Thread(tr);
		System.out.println("调用线程1");
		tt.start();
		System.out.println("调用线程2");
		t.start();
	}
	
	
	public static void main(String[] args) {
		double a=2.55;
		BigDecimal ab = new BigDecimal(a).setScale(2,BigDecimal.ROUND_HALF_UP);
		System.out.println(a*100.32);
		System.out.println(ab.multiply(new BigDecimal(100)));
	}
}
