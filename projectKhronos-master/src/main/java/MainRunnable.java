import Engine.MainEngine;

public class MainRunnable {

	public static void main(String[] args) {
		MainEngine engine = new MainEngine(1280,720);
		engine.startRendering("Level-1");
	}

}
