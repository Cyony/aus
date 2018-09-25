package com.ailk.aus.demo;

public class Singleton {

	private static class SingletonHander {
		private static final Singleton instance = new Singleton();
	}

	private Singleton() {
	}

	public static final Singleton getInstance() {
		return SingletonHander.instance;
	}

}
