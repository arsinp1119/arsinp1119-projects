package com.ars.drismcm.rabbit;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import com.ars.obcs.OBCSConnectMessageBuffer;

public class RabitOBCSConnectMessageBuffer extends OBCSConnectMessageBuffer{

	
	public RabitOBCSConnectMessageBuffer(DataInputStream sockInput,ByteArrayInputStream receiveBuffer) {
		super(sockInput);
		super.receiveBuffer = receiveBuffer;
	}

	
}
