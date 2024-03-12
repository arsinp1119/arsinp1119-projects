package com.ars.obcs.client;

import com.ars.obcs.messages.OBCSMessage;

public abstract class CSMessage extends OBCSMessage{

	public long creationDateTime;
	
	public CSMessage(int ID, String NAME) {
		super(ID, NAME); // initializing base class obcs message
		this.creationDateTime = System.currentTimeMillis(); // setting the current UTC as the creation time
	}
	
	

}
