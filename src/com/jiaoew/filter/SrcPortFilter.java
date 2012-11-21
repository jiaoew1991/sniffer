package com.jiaoew.filter;

import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;
import jpcap.packet.UDPPacket;

public class SrcPortFilter implements Filter {

	private int mPort = -1;
	public SrcPortFilter(int port) {
		super();
		mPort = port;
	}

	@Override
	public boolean filterPacket(Packet packet) {
		if (packet instanceof TCPPacket && ((TCPPacket) packet).src_port == mPort) return true;
		else if (packet instanceof UDPPacket && ((UDPPacket) packet).src_port == mPort) return true;
		return false;
	}

}
