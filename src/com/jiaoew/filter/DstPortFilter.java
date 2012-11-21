package com.jiaoew.filter;

import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;
import jpcap.packet.UDPPacket;

public class DstPortFilter implements Filter {

	private int mPort = -1;
	public DstPortFilter(int port) {
		super();
		mPort = port;
	}

	@Override
	public boolean filterPacket(Packet packet) {
		if (packet instanceof TCPPacket && ((TCPPacket) packet).dst_port == mPort) return true;
		else if (packet instanceof UDPPacket && ((UDPPacket) packet).dst_port == mPort) return true;
		return false;
	}

}
