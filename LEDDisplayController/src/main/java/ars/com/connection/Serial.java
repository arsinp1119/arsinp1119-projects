package ars.com.connection;


import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class Serial {

    public static void main(String[] args) {
        // Define port name
        String portName = "COM1"; // Change this to your port name

        // Create serial port object
        SerialPort serialPort = new SerialPort(portName);

        try {
            // Open the serial port
            Boolean opened=serialPort.openPort();
            
            // Set port parameters
            serialPort.setParams(
                SerialPort.BAUDRATE_9600,
                SerialPort.DATABITS_8,
                SerialPort.STOPBITS_1,
                SerialPort.PARITY_NONE
            );
if(opened) {
	System.out.println("Port Opened");
	 String [] machinePortName=SerialPortList.getPortNames();
	 for(String port: machinePortName) {
		 System.out.println("Available ports:"+port);
	 }
	 
}
            
            // Now you can use serialPort.writeBytes() to send data and serialPort.readBytes() to read data
            
        } catch (SerialPortException ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            // Don't forget to close the port when done
            try {
                if (serialPort.isOpened()) {
                    serialPort.closePort();
                }
            } catch (SerialPortException ex) {
                System.out.println("Error closing port: " + ex.getMessage());
            }
        }
    }
}
