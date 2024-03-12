package com.ars.obcs.messages;

import com.ars.obcs.OBCSConnectMessageBuffer;
import com.ars.obcs.client.CSMessage;

public class ANPRBusDetectionReq extends CSMessage{

	/** The id of the message */
	public static final int ID = 9050;
	
	/** The name of the message */
	protected static final String NAME = "ANPR Bus Detection Message";
	
	protected short msgSequenceNumber;
	
	protected byte cameraID;
	
	protected byte locationID;
	
	protected int timeStamp;
	
	protected byte licensePlateLength;
	
	protected byte[] licensePlateCharacters;
	
	protected byte direction;

	protected byte licencePlateConfidence;
	
	protected int detectionID;
	
	/** ANPR bus detection message detected time*/
	private String msgDetectionTime;
	
	protected String location;
	
	/** The id of the entry section if detection was at any entry loop.*/
	protected int entryGateID;
	
	/** The id of the exit section if detection was at any exit loop.*/
	protected int exitGateID;


	public ANPRBusDetectionReq() {
		super(ID, NAME);
	}
	
	public ANPRBusDetectionReq(int ID, String NAME) {
		super(ID, NAME);
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return ID;
	}

	public short getMsgSequenceNumber() {
		return msgSequenceNumber;
	}

	public void setMsgSequenceNumber(short msgSequenceNumber) {
		this.msgSequenceNumber = msgSequenceNumber;
	}

	public byte getCameraID() {
		return cameraID;
	}

	public void setCameraID(byte cameraID) {
		this.cameraID = cameraID;
	}

	public byte getLocationID() {
		return locationID;
	}

	public void setLocationID(byte locationID) {
		this.locationID = locationID;
	}

	public int getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(int timeStamp) {
		this.timeStamp = timeStamp;
	}

	public byte getLicensePlateLength() {
		return licensePlateLength;
	}

	public void setLicensePlateLength(byte licensePlateLength) {
		this.licensePlateLength = licensePlateLength;
	}

	public byte[] getLicensePlateCharacters() {
		return licensePlateCharacters;
	}

	public void setLicensePlateCharacters(byte[] licensePlateCharacters) {
		this.licensePlateCharacters = licensePlateCharacters;
	}

	public byte getDirection() {
		return direction;
	}

	public void setDirection(byte direction) {
		this.direction = direction;
	}
	
	public byte getLicencePlateConfidence() {
		return licencePlateConfidence;
	}

	public void setLicencePlateConfidence(byte licencePlateConfidence) {
		this.licencePlateConfidence = licencePlateConfidence;
	}

	public int getDetectionID() {
		return detectionID;
	}

	public void setDetectionID(int detectionID) {
		this.detectionID = detectionID;
	}
	
	public String getMsgDetectionTime() {
		return msgDetectionTime;
	}

	public void setMsgDetectionTime(String msgDetectionTime) {
		this.msgDetectionTime = msgDetectionTime;
	}
	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	
	public int getEntryGateID() {
		return entryGateID;
	}

	public void setEntryGateID(int entryGateID) {
		this.entryGateID = entryGateID;
	}

	public int getExitGateID() {
		return exitGateID;
	}

	public void setExitGateID(int exitGateID) {
		this.exitGateID = exitGateID;
	}

	@Override
	public short loadStreamData(OBCSConnectMessageBuffer buffer) throws Exception {
		// TODO Auto-generated method stub
		buffer.writeShort(msgSequenceNumber);
		buffer.write(cameraID);
		buffer.write(locationID);
		buffer.writeInt(timeStamp);
		buffer.write(licensePlateLength);
		buffer.writeStream(licensePlateCharacters);
		buffer.write(direction);
		buffer.write(licencePlateConfidence);
		buffer.write(detectionID);
		byte[] content = buffer.getDataBytes(); // Hypothetical method to get buffer content
		// Print the content of the buffer
		for (byte b : content) {
		    System.out.print(b + " ");
		}
		//TODO read line planning number
		return 0;
	}

	@Override
	public void processNewMessage(OBCSConnectMessageBuffer buffer) throws Exception {
		// TODO Auto-generated method stub
		msgSequenceNumber = (short) buffer.readShort();
		cameraID 		= (byte)buffer.read();
		locationID = (byte) buffer.read();
		timeStamp	    = buffer.readInt();
		licensePlateLength         = (byte)buffer.read();
		licensePlateCharacters 		= buffer.readString(licensePlateLength).getBytes();
		direction = (byte)buffer.read();
		licencePlateConfidence 		= (byte)buffer.readInt();
		detectionID 			= buffer.readInt();
	}

}
