package com.jiaoew.filter;

import jpcap.packet.ARPPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;

public class DstIPFilter implements Filter {
	private String mIp = null;
	public DstIPFilter(String ip) {
		super();
		mIp = ip;
	}
	@Override
	public boolean filterPacket(Packet packet) {
		if (mIp != null) {
			if (packet instanceof IPPacket && ((IPPacket) packet).dst_ip.toString().equals(mIp)) return true;
			else if (packet instanceof ARPPacket && ((ARPPacket) packet).getTargetProtocolAddress().toString().equals(mIp)) return true;
		}
		return false;
	}

}
