package com.jiaoew.dataHandler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

import jpcap.packet.UDPPacket;

public class UDPPacketHandler extends IPPacketHandler {

	public UDPPacketHandler(UDPPacket packet) {
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
		UDPPacket udpPacket = (UDPPacket) mPacket;
		return ", Source Port: " + udpPacket.src_port + ", Destination Port: " + udpPacket.dst_port ;
	}
}
