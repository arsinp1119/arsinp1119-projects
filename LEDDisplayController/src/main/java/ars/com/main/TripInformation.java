package ars.com.main;

public class TripInformation {

	private static String LineNumber;
	private static String destinationName;
	private static String departureTime;
	
	
	
	public static String getLineNumber() {
		return LineNumber;
	}
	public static void setLineNumber(String lineNumber) {
		LineNumber = lineNumber;
	}
	public static String getDestinationName() {
		return destinationName;
	}
	public static void setDestinationName(String destinationName) {
		TripInformation.destinationName = destinationName;
	}
	public static String getDepartureTime() {
		return departureTime;
	}
	public static void setDepartureTime(String departureTime) {
		TripInformation.departureTime = departureTime;
	}
	
	
}
