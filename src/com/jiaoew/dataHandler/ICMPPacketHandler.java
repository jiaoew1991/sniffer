package com.jiaoew.dataHandler;

import java.io.BufferedWriter;
import java.io.IOException;

import jpcap.packet.ICMPPacket;

public class ICMPPacketHandler extends IPPacketHandler {

	public ICMPPacketHandler(ICMPPacket packet) {
		super(packet);
	}

	@Override
	protected void write2File(BufferedWriter bfWriter) throws IOException {
		super.write2File(bfWriter);
		bfWriter.append(details());
	}

	@Override
	public String getPacketDetailInfo() {
		return super.getPacketDetailInfo() + details();
	}

	private String details() {
		ICMPPacket icmpPacket = (ICMPPacket) mPacket;
		return ", Type:" + icmpPacket.type + ", Code:" + icmpPacket.code;
	}
}
