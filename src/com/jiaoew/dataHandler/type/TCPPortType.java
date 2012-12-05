package com.jiaoew.dataHandler.type;

public enum TCPPortType {

	FTP(21),
	SSH(22),
	TELNET(23),
	SMTP(25),
	HTTP(80),
	;
	private int value;
	TCPPortType(int port) {
		value = port;
	}
	public int getValue() {
		return value;
	}
}
