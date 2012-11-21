package com.jiaoew.filter;

import java.net.InetAddress;
import java.net.UnknownHostException;

import jpcap.packet.ARPPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;

public class SrcIPFilter implements Filter {

	private InetAddress mIp = null;
	public SrcIPFilter(String ip) {
		super();
		try {
			mIp = InetAddress.getByName(ip);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	@Override
	public boolean filterPacket(Packet packet) {
		if (mIp != null) {
			if (packet instanceof IPPacket && ((IPPacket) packet).src_ip.equals(mIp)) return true;
			else if (packet instanceof ARPPacket && ((ARPPacket) packet).getSenderProtocolAddress().equals(mIp)) return true;
		}
		return false;
	}

}
