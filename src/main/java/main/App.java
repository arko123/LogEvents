package main;

import java.io.IOException;

public class App {

	public static void main(String[] args) {
		if(args.length == 2 ){
			try {
			LogEvents logEvents = new LogEvents(args[0], args[1],args[2]);
				logEvents.run();
			} catch (InterruptedException | IOException e) {
				e.printStackTrace();
				System.err.println("Please enter:");
				System.err.println("1) Download Configuration File Path");
				System.err.println("2) Mail Property File Path");
				System.err.println("3) Analize Property File Path");
			}
		} else {
			System.err.println("Wrong nuber of arguments");
			System.err.println("Please enter:");
			System.err.println("1) Download Configuration File Path");
			System.err.println("2) Mail Property File Path");
			System.err.println("3) Analize Property File Path");
		}
	}

}
