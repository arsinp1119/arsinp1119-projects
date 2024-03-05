package ars.com.main;

import java.util.LinkedList;
import java.util.List;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

import jssc.SerialPortList;

public class VPDisplayCommunicator {
	protected static final String START_OF_TRANSMISSION = "01";
	protected static final String END_OF_TRANSIMISSION = "04";
	private static final String ADDRESS = "1";
	private static final String PACKET_ID = "1";
	private static final String STX = "02";
	private static final String ETX = "03";
	protected static final int SOT = 1;
	protected static final int EOT = 4;
	String messageFormat = null;
	SerialPortConnector serialport = new SerialPortConnector();
	private boolean isStartDetected = false;
	private static List<Integer> processList = new LinkedList<>();
	protected static final int MAX_RESPONSE_TIME = 60000;
	private static byte[] ackData = null;

	public void processCommunicationToPort() throws Exception {

		byte[] commandSc = hexStringToByteArray(createPacketMsg());
		System.out.print("Byte array to the serial port: ");
		for (byte b : commandSc) {
			System.out.printf("%02X ", b);
		}

		if (serialport.connectSerialPort()) {
			if (serialport.sendMessage(commandSc)) {
				System.out.println("Data has been sent to serial port");
				String ack;
				int responseTime = MAX_RESPONSE_TIME;
				ack = getResponse(responseTime);
				System.out.println("ack****" + ack);
				ackData = ack.substring(4, ack.length() - 4).getBytes(StandardCharsets.ISO_8859_1);

				System.out.println("Response data" + ackData.toString());
				updateGeneralAckInfo(ackData);
			}
			;
		}
	}

	private String getResponse(int maxResTime) throws Exception {
		long start = System.currentTimeMillis();
		StringBuilder response = new StringBuilder();
		isStartDetected = false;
		long timeout = System.currentTimeMillis() + maxResTime;
		while (System.currentTimeMillis() < timeout) {
			processList.clear();
			List<Integer> responseList = serialport.readResponse();

			if (!responseList.isEmpty()) {
				for (int i = 0; i < responseList.size(); i++) {
					processList.add(responseList.get(i));
				}
				if (processList.contains(SOT) && processList.contains(SOT)) {
					Iterator<Integer> itr = processList.iterator();
					while (itr.hasNext()) {
						int val = itr.next();
						if (val == SOT)
							isStartDetected = true;
						if (isStartDetected) {
							response.append((char) (int) val);
							if (val == EOT) {
								timeout = 0; // to break the while loop
								itr.remove();
								if (processList.contains(SOT) && processList.contains(EOT)) {
									isStartDetected = false;
									continue;
								} else
									break;
							}
						}
						itr.remove();
					}
				} else {
					System.out.println("Response Not Contains Full Message...");
				}
			}
		}

		return response.toString();
	}

	protected static void updateGeneralAckInfo(byte[] ackData) {
		if (ackData == null) {
			System.out.println("Acknowledgement data is null");
		}
		StringBuilder genAck = byteArrToString(ackData); // General Acknowledgement

		// <ACK/NAK>
		try {
			String ack = genAck.substring(0, 2);
			if (ack.equals("00")) { // 00 -- Acknowledged
				System.out.println("Transmission OK");
			} else {
				ackErrorLog(ack);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public static StringBuilder byteArrToString(byte[] bytes) {
		StringBuilder stringResponse = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			stringResponse.append((char) byte2int(bytes[i]));
		}
		return stringResponse;
	}

	public static int byte2int(byte byteValue) {
		int intValue = byteValue;
		if (intValue < 0) {
			intValue += 256;
		}
		return intValue;
	}

	private static void ackErrorLog(String nack) {

		if (nack.equals("01")) {
			System.out.println("Timeout");
		}
		if (nack.equals("02")) {
			System.out.println("CRC error");
		}
		if (nack.equals("03")) {
			System.out.println("Packet length error ( >1024)");
		}
		if (nack.equals("04")) {
			System.out.println("Packet number error");
		}
		if (nack.equals("05")) {
			System.out.println("Invalid String format");
		}
		if (nack.equals("06")) {
			System.out.println("Invalid Page format");
		}
		if (nack.equals("07")) {
			System.out.println("Invalid Message format");
		}
		if (nack.equals("08")) {
			System.out.println("Invalid command");
		}
		if (nack.equals("09")) {
			System.out.println("Invalid message number");
		}
		if (nack.equals("0A")) {
			System.out.println("Invalid Page number");
		}
		if (nack.equals("0B")) {
			System.out.println("Invalid Line number");
		}
		if (nack.equals("0C")) {
			System.out.println("Unsupported command");
		}
		if (nack.equals("0D")) {
			System.out.println("Out of Flash memory");
		}
		if (nack.equals("0E")) {
			System.out.println("Out of memory");
		}
		if (nack.equals("FF")) {
			System.out.println("Rx Buffer overflow");
		}
		if (nack.equals("20")) {
			System.out.println("Previous packet incomplete");
		}
		if (nack.equals("21")) {
			System.out.println("Illegal frame format");
		}
		if (nack.equals("22")) {
			System.out.println("Illegal parameter");
		}
		if (nack.equals("23")) {
			System.out.println("Structure too large");
		}
		if (nack.equals("24")) {
			System.out.println("Unable to set parameter");
		}

	}

	public String createPacketMsg() {// String command, int address

		StringBuilder packetMsg = new StringBuilder();
		// packetMsg.append(START_OF_TRANSMISSION); // Add Start Byte (Start of
		// transmission)
		// packetMsg.append(convertToHex(ADDRESS));
		// packetMsg.append(convertToHex(PACKET_ID)); // Type of packet
		// packetMsg.append(STX);
		// packetMsg.append(packetData());
		// packetMsg.append(ETX);
		// packetMsg.append(calculateCheckSum());
		// packetMsg.append(END_OF_TRANSIMISSION);
		//
		// System.out.println("MessageFormat:" + messageFormat);
		// System.out.println("Data to be added in the packet(In Hex):" + packetData());

		String command = "" + "013031383002440d4d30315850303141"// .0180.D.M01XP01A\r\n"
				+ "30303130324c3031413230543130314c"// 00102L01A20T101L\r\n"
				+ "3031413232543130324c303241323054"// 01A22T102L02A20T\r\n"
				+ "3230314c3032413232543230324c3033"// 201L02A22T202L03\r\n"
				+ "413230543330314c3033413232543330"// A20T301L03A22T30\r\n"
				+ "324c3034413230543430314c30344132"// 2L04A20T401L04A2\r\n"
				+ "32543430324c3035413030543838380d"// 2T402L05A00T888.\r\n"
				+ "4d3032455030314130303130324c3031"// M02EP01A00102L01\r\n"
				+ "413130543130314c3031413032543130"// A10T101L01A02T10\r\n"
				+ "324c3032413230543530314c30324132"// 2L02A20T501L02A2\r\n"
				+ "32543530324c3033413230543630314c"// 2T502L03A20T601L\r\n"
				+ "3033413232543630324c303441323054"// 03A22T602L04A20T\r\n"
				+ "3730314c3034413232543730324c3035"// 701L04A22T702L05\r\n"
				+ "413030543838380d4d30324550303241"// A00T888.M02EP02A\r\n"
				+ "30303035324c3031413130543130314c"// 00052L01A10T101L\r\n"
				+ "3031413032543130324c303241323054"// 01A02T102L02A20T\r\n"
				+ "3830314c3032413232543830324c3033"// 801L02A22T802L03\r\n"
				+ "413230543930314c3033413232543930"// A20T901L03A22T90\r\n"
				+ "324c3034413230543131314c30344132"// 2L04A20T111L04A2\r\n"
				+ "32543131324c3035413030543838380d"// 2T112L05A00T888.\r\n"
				+ "4d3032455030334130303035324c3031"// M02EP03A00052L01\r\n"
				+ "413230543130314c3031413232543130"// A20T101L01A22T10\r\n"
				+ "334c3032413030543830314c30324130"// 3L02A00T801L02A0\r\n"
				+ "32543830324c3033413030543930314c"// 2T802L03A00T901L\r\n"
				+ "3033413032543930324c303441303054"// 03A02T902L04A00T\r\n"
				+ "3131314c3034413032543131324c3035"// 111L04A02T112L05\r\n"
				+ "413030543838380d4d30324550303441"// A00T888.M02EP04A\r\n"
				+ "30303130324c3031413130543130314c"// 00102L01A10T101L
				+ "3031413132543130334c303241323054"// 000001c0: 01A12T103L02A20T\r\n"
				+ "3230314c3032413232543230324c3033"// 201L02A22T202L03\r\n"
				+ "413230543330314c3033413232543330"/// A20T301L03A22T30\r\n"
				+ "324c3034413230543430314c30344132"// 2L04A20T401L04A2\r\n"
				+ "32543430324c3035413030543838380d"// 2T402L05A00T888.\r\n"
				+ "4d3032455030354130303035324c3031"// M02EP05A00052L01\r\n"
				+ "413130543130314c3031413032543130"// A10T101L01A02T10\r\n"
				+ "334c3032413230543530314c30324132"// 3L02A20T501L02A2\r\n"
				+ "32543530324c3033413230543630314c"// 2T502L03A20T601L\r\n"
				+ "3033413232543630324c303441323054"// 03A22T602L04A20T\r\n"
				+ "3730314c3034413232543730324c3035"// 701L04A22T702L05\r\n"
				+ "413030543838380d4d30324550303641"// A00T888.M02EP06A\r\n"
				+ "30303035324c3031413130543130314c"// 00052L01A10T101L\r\n"
				+ "3031413032543130334c303241303054"// 01A02T103L02A00T\r\n"
				+ "3530314c3032413032543530324c3033"// 501L02A02T502L03\r\n"
				+ "413030543630314c3033413032543630"// A00T601L03A02T60\r\n"
				+ "324c3034413230543730314c30344132"// 2L04A20T701L04A2\r\n"
				+ "32543730334c3035413030543838380d"// 2T703L05A00T888.\r\n"
				+ "4d3032455030374130303035324c3031"// M02EP07A00052L01\r\n"
				+ "413130543130314c3031413032543130"// A10T101L01A02T10\r\n"
				+ "334c3032413230543830314c30324132"// 3L02A20T801L02A2\r\n"
				+ "32543830324c3033413230543930314c"// 2T802L03A20T901L\r\n"
				+ "3033413232543930324c303441303054"// 03A22T902L04A00T\r\n"
				+ "3131314c3034413232543131324c3035"// 111L04A22T112L05"
				+ "413030543838380d5431303112322020"// A00T888.T101.2\r\n"
				+ "3120202020202030312020486f6e6f72"// 101Honor\r\n"
				+ "204f616b0d54313032123232206d696e"// Oak.T102.22min\r\n"
				+ "202020200d5431303312324475652020"// .T103.2Due\r\n"
				+ "20200d54323031123220203220202020"// .T201.22\r\n"
				+ "20203032202045617374626f756e640d"// 02Eastbound.\r\n"
				+ "5432303212322034206d696e20202020"// T202.24min\r\n"
				+ "0d543330311232202033202020202020"// .T301.23\r\n"
				+ "3033202046617272696e67646f6e2053"// 03FarringdonS\r\n"
				+ "74726565740d5433303212322035206d"// treet.T302.25m\r\n"
				+ "696e202020200d543430311232202034"// in.T401.24\r\n"
				+ "202020202020303420204b0337350401"// 04K.75..\r\n"
				+ "3031303102696e6727732043726f7373"// 0101.ing'sCross\r\n"
				+ "0d5434303212322037206d696e202020"// .T402.27min\r\n"
				+ "200d5435303112322020352020202020"// .T501.25\r\n"
				+ "203035202049736c65206f6620446f67"// 05IsleofDog\r\n"
				+ "730d5435303212323138206d696e2020"// s.T502.218min\r\n"
				+ "20200d54363031123220203620202020"// .T601.26\r\n"
				+ "20203036202043686172696e67204372"// 06CharingCr\r\n"
				+ "6f73730d5436303212323230206d696e"// oss.T602.220min\r\n"
				+ "202020200d5437303112322020372020"// .T701.27\r\n"
				+ "2020202030372020506963636164696c"// 07Piccadil\r\n"
				+ "6c790d5437303212323235206d696e20"// ly.T702.225min\r\n"
				+ "2020200d5437303312323234206d696e"// .T703.224min\r\n"
				+ "202020200d5438303112322020382020"// .T801.28\r\n"
				+ "20202020303820204f78666f72642053"// 08OxfordS\r\n"
				+ "74726565740d5438303212323237206d"// treet.T802.227m\r\n"
				+ "696e202020200d543930311232202039"// in.T901.29\r\n"
				+ "202020202020303920204d696c652045"// 09MileE\r\n"
				+ "6e6420526f61640d5439303212323239"// ndRoad.T902.229\r\n"
				+ "206d696e202020200d54313131123231"// min.T111.21\r\n"
				+ "30202020202020313020204b696e6773"// 010Kings\r\n"
				+ "2043726f73732053746174696f6e0d54"// CrossStation.T\r\n"
				+ "31313212323430206d696e202020200d"// 112.240min.\r\n"
				+ "543838381231818103304304";// T888.1Â?Â?.0C."

		return command;
	}

	public String calculateCheckSum() {
		// 2 byte XORing from Address to ETX.

		StringBuilder xor = new StringBuilder();
		xor.append(convertToHex(ADDRESS));
		xor.append(convertToHex(PACKET_ID)); // Type of packet
		xor.append(STX);
		xor.append(packetData());
		xor.append(ETX);

		System.out.println("XORinput=" + xor);
		int result = 0;

		// Iterate through the StringBuilder and XOR consecutive pairs of bytes
		for (int i = 0; i < xor.length() - 1; i += 2) {
			char c1 = xor.charAt(i);
			char c2 = xor.charAt(i + 1);
			int value1 = Character.digit(c1, 16);
			int value2 = Character.digit(c2, 16);
			result ^= ((value1 << 4) | value2);
		}

		// Convert result to hexadecimal representation
		String resultHex = String.format("%02X", result);
		System.out.println("XORed Value=" + resultHex);

		return resultHex;
	}

	public String packetData() {
		// StringBuilder dataStore=new StringBuilder();
		String T001 = ("T001" + TripInformation.getLineNumber());
		String T002 = ("T002" + TripInformation.getDestinationName());
		String T003 = ("T003" + TripInformation.getDepartureTime());

		String messageFormat = "M01EP01L01T001L01C06T002L01C22A02T003";
		StringBuilder dataStore = new StringBuilder();
		dataStore.append(convertToHex(T001));
		dataStore.append("0D"); // First sub Packet
		dataStore.append(convertToHex(T002));
		dataStore.append("0D"); // Second sub Packet
		dataStore.append(convertToHex(T003));
		dataStore.append("0D"); // Third sub packet
		dataStore.append(convertToHex(messageFormat));
		return dataStore.toString();

	}

	public String convertToHex(String value) {
		byte[] bytes = value.getBytes();

		// Convert each byte to hexadecimal representation
		StringBuilder hexBuilder = new StringBuilder();
		for (byte b : bytes) {
			// Convert each byte to hexadecimal and append to the string builder
			hexBuilder.append(String.format("%02X", b));
		}

		// Print the hexadecimal representation
		return hexBuilder.toString();
	}

	/**
	 * Converts a HexString to corresponding byte array
	 * 
	 * @param hexString
	 * @return
	 */
	public static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}
		return data;
	}
}
