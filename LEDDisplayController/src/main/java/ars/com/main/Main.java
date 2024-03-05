package ars.com.main;

public class Main {

	static VPDisplayCommunicator displayCommunicator = new VPDisplayCommunicator();

	public static void main(String[] args) {

		try {

			displayCommunicator.processCommunicationToPort();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public StringBuilder dataTobeSend() {
		return null;

	}

}