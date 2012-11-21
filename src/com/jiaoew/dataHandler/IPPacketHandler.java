package com.jiaoew.dataHandler;

import java.io.BufferedWriter;
import java.io.IOException;

import jpcap.packet.IPPacket;
import jpcap.packet.Packet;

public class IPPacketHandler extends PacketHandler {

	public IPPacketHandler(IPPacket packet) {
		super(packet);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void write2File(BufferedWriter bfWriter) throws IOException {
		super.write2File(bfWriter);
		bfWriter.append(' ');
		IPPacket ipPacket = (IPPacket) mPacket;
		bfWriter.append("from: " + ipPacket.src_ip + ", to: " + ipPacket.dst_ip);
		
	}

}
