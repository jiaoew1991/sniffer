package com.jiaoew.dataHandler;

import java.io.BufferedWriter;
import java.io.IOException;

import jpcap.packet.ICMPPacket;

public class ICMPPacketHandler extends IPPacketHandler {

	public ICMPPacketHandler(ICMPPacket packet) {
		super(packet);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void write2File(BufferedWriter bfWriter) throws IOException {
		super.write2File(bfWriter);
		ICMPPacket icmpPacket = (ICMPPacket) mPacket;
		bfWriter.append(", Type:" + icmpPacket.type + ", Code:" + icmpPacket.code);
	}

}
