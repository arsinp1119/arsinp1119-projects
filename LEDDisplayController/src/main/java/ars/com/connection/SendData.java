package ars.com.connection;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.File;
import jssc.SerialPort;
import jssc.SerialPortException;
public class SendData {

	
	public void sendingData() {



        // Check if filename is provided as argument
//        if (args.length != 2) {
//            System.out.println("Usage: java VIS2PNGConverter <vis_file> <serial_port>");
//            return;
//        }

        // Get the filename of the .vis file
        String visFileName = "file.vis";
        
        // Read the contents of the .vis file
        try (BufferedReader reader = new BufferedReader(new FileReader(visFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Process each line of the .vis file
                // For simplicity, let's just print each line here
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading .vis file: " + e.getMessage());
            return;
        }

        // Convert .vis script to PNG image
        // Here you would implement the conversion logic
        // For now, let's assume the conversion process is complete and we have a PNG file

        // Output PNG filename
        String pngFileName = visFileName.replace(".vis", ".png");

        // Dummy code to create a PNG file (replace with actual conversion logic)
        createDummyPNG(pngFileName);

        // Output the name of the PNG file
        System.out.println("PNG file created: " + pngFileName);

        // Send PNG data over serial port
        String serialPortName = "COM1";
        sendPNGDataOverSerialPort(pngFileName, serialPortName);
    }

    // Dummy method to create a PNG file
    private static void createDummyPNG(String fileName) {
        try (FileWriter writer = new FileWriter(new File(fileName))) {
            writer.write("This is a dummy PNG file.");
        } catch (IOException e) {
            System.err.println("Error creating dummy PNG file: " + e.getMessage());
        }
    }

    // Method to send PNG data over serial port
    private static void sendPNGDataOverSerialPort(String pngFileName, String portName) {
        try {
            // Open the serial port
            SerialPort serialPort = new SerialPort(portName);
            serialPort.openPort();
            serialPort.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

            // Read PNG data from file and send over serial port
            try (BufferedReader reader = new BufferedReader(new FileReader(pngFileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    serialPort.writeBytes(line.getBytes());
                }
            } catch (IOException e) {
                System.err.println("Error reading PNG file: " + e.getMessage());
            }

            // Close the serial port
            serialPort.closePort();
        } catch (SerialPortException e) {
            System.err.println("Error opening serial port: " + e.getMessage());
        }

	}
	
	
}
