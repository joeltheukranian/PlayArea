package demo;

public class ThreadHelper {

	public static ThreadHelper start(Thread thread) {
		thread.start();
		return new ThreadHelper();
	}

}
