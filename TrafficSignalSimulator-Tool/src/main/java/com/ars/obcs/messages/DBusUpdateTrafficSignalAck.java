
package com.ars.obcs.messages;

import java.util.Hashtable;

import com.ars.obcs.OBCSConnectMessageBuffer;
import com.ars.obcs.client.CSMessage; 


/**
 * The loop controller sends this message to the OBCS when it got any vehicle
 * detection message in its queue
 */
public class DBusUpdateTrafficSignalAck extends CSMessage {

	public static final int ID =9054; 
	public static final String NAME = "DBusUpdateTrafficSignalAck";
	protected static short msgSequenceNumber;
	protected byte signalId; 
	protected byte currentLightState;
	protected byte statusCode;

	private Hashtable<String, String> abbreviatedDestNames;
	public Object logger;


	public DBusUpdateTrafficSignalAck() { 
		super(ID, NAME); abbreviatedDestNames = new Hashtable<String, String>(); 
	}
	public DBusUpdateTrafficSignalAck(int
			ID,String NAME) {
		super(ID, NAME);
	}



	public int getId() {
		return ID;
	}


	/*
	 * public synchronized static short getNextSeqNo() { if (msgSequenceNumber ==
	 * Short.MAX_VALUE) { msgSequenceNumber = 0; } return msgSequenceNumber =
	 * (short) (msgSequenceNumber + 1); }
	 */
	/* public short getMsgSequenceNumber() { return msgSequenceNumber; } */

	public void setMsgSequenceNumber(short msgSequenceNumber) {
		this.msgSequenceNumber = msgSequenceNumber; }


	public byte getsignalID() {
		return signalId;
	}

	public void setsignalID(byte signalId) {
		this.signalId = signalId;
	}

	public byte getCurrentLightState() {
		return currentLightState;
	}

	public void setCurrentLightState(byte currentLightState) {
		this.currentLightState = currentLightState;
	}
	public byte getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(byte statusCode) {
		this.statusCode = statusCode;
	}


	public void processNewMessage(OBCSConnectMessageBuffer buffer) throws
	Exception { 
		//ID = buffer.readShort(); //TODO read line planning number
		msgSequenceNumber = (short) buffer.readShort();
		signalId = (byte)buffer.read(); 
		currentLightState = (byte) buffer.readShort();
		statusCode	    = (byte) buffer.readShort();
	}

	@Override
	public short loadStreamData(OBCSConnectMessageBuffer buffer)
			throws Exception {
		buffer.writeShort(msgSequenceNumber);
		buffer.write(signalId);
		buffer.writeShort(currentLightState);
		buffer.writeShort(statusCode);
		return ID;
	}





}


