package program.thread;

public class Mythread extends Thread {
	
	@Override
	public void run() {
		for (int i = 0; i < 3; i++) {
			System.out.print(". ");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println(e);
			}
		}
	}
	
	private Mythread() {
		run();
	}
	
	public static void call() {
		new Mythread();
	}

}
