package com.ars.obcs.client;

import com.ars.obcs.OBCSConnectMessageBuffer;

/**
 * Class with common methods for reading different data types from the obcsClient buffer
 * @author Anaz
 *
 */
public class CSBufferReader {
	/**
	 * Reads the number of bytes into byte array 
	 * @param buffer
	 * @param length
	 * @return byte[] of data stream with the passed length
	 */
	public static final byte[] readStream(OBCSConnectMessageBuffer receiveBuffer, int length){
		byte[] buffer = null;
		buffer = new byte[length];
		if(receiveBuffer.available() < length || length == 0) {
			return null;
		}
		for(int i = 0; i < length; i++) {
			buffer[i] = (byte) receiveBuffer.read();
		}
		return buffer;
	}

}
