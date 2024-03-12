
package com.ars.obcs.messages;

import java.util.Hashtable;

import com.ars.obcs.OBCSConnectMessageBuffer;
import com.ars.obcs.client.CSMessage; 


/**
 * The loop controller sends this message to the OBCS when it got any vehicle
 * detection message in its queue
 */
public class DBusUpdateTrafficSignalReq extends CSMessage {

	public static final int ID =9053; 
	public static final String NAME = "DBusUpdateTrafficSignalreq";
	protected static short msgSequenceNumber=1;
	protected byte signalId; 
	protected byte RequestedLightState;
	protected int timeStamp;

	private Hashtable<String, String> abbreviatedDestNames;
	public Object logger;


	public DBusUpdateTrafficSignalReq() { 
		super(ID, NAME); abbreviatedDestNames = new Hashtable<String, String>(); 
	}
	public DBusUpdateTrafficSignalReq(int
			ID,String NAME) {
		super(ID, NAME);
	}



	public int getId() {
		return ID;
	}



	public synchronized static short getNextSeqNo() {
		if (msgSequenceNumber  == Short.MAX_VALUE) {
			msgSequenceNumber = 0;
		}
		return msgSequenceNumber = (short) (msgSequenceNumber + 1);
	}

	 public short getMsgSequenceNumber() {
		 return msgSequenceNumber; 
		 } 

	public void setMsgSequenceNumber(short msgSequenceNumber) {
		DBusUpdateTrafficSignalReq.msgSequenceNumber = msgSequenceNumber; }


	public byte getsignalID() {
		return signalId;
	}

	public void setsignalID(byte signalId) {
		this.signalId = signalId;
	}

	public byte getrequestedLightState() {
		return RequestedLightState;
	}

	public void setrequestedLightState(byte RequestedLightState) {
		this.RequestedLightState = RequestedLightState;
	}
	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(int timeStamp) {
		this.timeStamp = timeStamp;
	}


	public void processNewMessage(OBCSConnectMessageBuffer buffer) throws
	Exception { 
		//ID = buffer.readShort(); //TODO read line planning number
		msgSequenceNumber = (short) buffer.readShort();
		signalId = (byte)buffer.read(); 
		RequestedLightState = (byte) buffer.readShort();
		timeStamp	    = buffer.readInt();
	}

	@Override
	public short loadStreamData(OBCSConnectMessageBuffer buffer)
			throws Exception {
		buffer.writeShort(msgSequenceNumber);
		buffer.write(signalId);
		buffer.writeShort(RequestedLightState);
		buffer.writeShort((int) timeStamp);
		return ID;
	}





}


