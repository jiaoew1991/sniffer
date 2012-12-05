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
		UDPPacket udpPacket = (UDPPacket) mPacket;
		bfWriter.append(", Source Port: " + udpPacket.src_port + ", Destination Port: " + udpPacket.dst_port );
	}

	public static void reductionFile(List<UDPPacketHandler> dataList, String fileName) {
		
	}
}
