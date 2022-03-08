package controllers;

public class MainServidor {

	public static void main(String[] args) {
		MainController server = new MainController();
		Thread runnable = new Thread(server);
		runnable.run();
	}
}
