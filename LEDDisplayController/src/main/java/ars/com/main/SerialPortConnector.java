package ars.com.main;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;
import java.util.Iterator;

public class SerialPortConnector {
	boolean portExists = false;
	public static String comPortName = "COM9";
	SerialPort serialPort;
	protected final Object syncArrayListObject = new Object();
	static int arrayLength = 0;
	static ArrayList<byte[]> aResponseByteList = new ArrayList<>();
	public static List<Integer> finalList = new ArrayList<Integer>();

	public boolean connectSerialPort() {

		String[] portName = SerialPortList.getPortNames();
		System.out.println("Available ports:");
		for (String port : portName) {
			System.out.println(port);
			if (port.equals(comPortName)) {
				System.out.println("Required port available");
				portExists = true;
				break;
			}
		}

		if (!portExists) {
			System.out.println("Error in opening the COM Port. Here the port " + comPortName + " does not exist.");
			return false;
		} else {
			// Open the serial port connection
			serialPort = new SerialPort(comPortName);
			try {
				serialPort.openPort();
				System.out.println("Serial port connection established successfully.");
				// Setting Parameters for the port.
				serialPort.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
						SerialPort.PARITY_NONE);

				serialPort.addEventListener(new SerialPortReader());

			} catch (SerialPortException e) {

				e.printStackTrace();
			}
		}
		return true;
	}

	public boolean sendMessage(byte[] message) {
		boolean messageStatus = false;
		try {
			if (!serialPort.isOpened()) {
				System.out.println("Serial Port is Closed.... Re-Opened");
				serialPort.openPort();
			}
			System.out.println("Serial Port is open...");
			serialPort.writeBytes(message);// Write data to port
			messageStatus = true;
		} catch (SerialPortException e) {
			e.printStackTrace();
			System.out.println("IOException in Closing COM Port:" + e.getMessage());

		}
		return messageStatus;
	}

	protected class SerialPortReader implements SerialPortEventListener {
		public SerialPortReader() {
			System.out.println(" Device SerialPortEventListener Initialized***********");
		}

		public void serialEvent(SerialPortEvent event) {
			// Object type SerialPortEvent carries information about which event occurred
			// and a value.
			// For example, if the data came a method event.getEventValue() returns us the
			// number of bytes in the input buffer.

			System.out.println(" *********** " + comPortName + " Device SerialPortEventListener Calling***********");
			if (event.isRXCHAR()) {
				byte buffer[] = null;
				try {
					// logger.log(Level.FINE, " ******event.getEventValue()***** " +
					// event.getEventValue());
					buffer = serialPort.readBytes(event.getEventValue());
					System.out.println(Arrays.toString(buffer));
					if (buffer != null) {
						addResponseToList(buffer);
					}
				} catch (SerialPortException ex) {
					System.out.println("Exception in serialEvent");
				}
			}
			// If the CTS line status has changed, then the method event.getEventValue()
			// returns 1 if the line is ON and 0 if it is OFF.
			else if (event.isCTS()) {
				System.out.println("*****Event is: CTS()*****");
				if (event.getEventValue() == 1) {
					System.out.println("CTS - ON");
				} else {
					System.out.println("CTS - OFF");
				}
			} else if (event.isDSR()) {
				System.out.println("*****Event is: DSR()*****");
				if (event.getEventValue() == 1) {
					System.out.println("DSR - ON");
				} else {
					System.out.println("DSR - OFF");
				}
			} else if (event.isRING()) {
				System.out.println("*****Eveint is: RING()*****Value is" + event.getEventValue());
				if (event.getEventValue() == 1) {
					System.out.println("RING - OFF");
				} else {
					System.out.println("RING - ON");
				}
			}
		}
	}

	protected synchronized void addResponseToList(byte[] buffer) {
		synchronized (syncArrayListObject) {
			arrayLength = arrayLength + buffer.length;
			aResponseByteList.add(buffer);
		}
	}

	public List<Integer> readResponse() throws IOException, InterruptedException {
		finalList.clear();
		try (ByteArrayOutputStream resultByteStream = new ByteArrayOutputStream()) {
			// synchronized (syncArrayListObject) {
			if (!aResponseByteList.isEmpty()) {
				Iterator<byte[]> itr = aResponseByteList.iterator();
				while (itr.hasNext()) {
					byte currArray[] = itr.next();
					if (currArray != null) {
						resultByteStream.write(currArray, 0, currArray.length);// Append to resultByteStream
					}
				}
				aResponseByteList.clear();
				arrayLength = 0;
				byte[] result = resultByteStream.toByteArray();
				if (result != null) {
					for (int i = 0; i < result.length; i++) {
						finalList.add((int) result[i]);
					}
					resultByteStream.close();
				}
				System.out.println("Device Response is: " + finalList);
				return finalList;
			} else {
				return finalList;
			}
			// }
		} catch (Exception e) {
			System.out.println("Exception in readResponse" + e.getMessage());
			return finalList;
		}
	}

}
