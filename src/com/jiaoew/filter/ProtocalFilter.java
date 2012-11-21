package com.jiaoew.filter;

import jpcap.packet.ARPPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;

public class ProtocalFilter implements Filter {
	
	public static final short ICMP_PROTOL = 1;
	public static final short IGMP_PROTOL = 2;
	public static final short IP_PROTOL = 4;
	public static final short TCP_PROTOL = 6;
	public static final short ARP_PROTOL = 10;
	public static final short UDP_PROTOL = 17;
	private short mProtocal = -1;
	public ProtocalFilter(short protocal) {
		super();
		mProtocal = protocal;
	}
	@Override
	public boolean filterPacket(Packet packet) {
		if (packet instanceof IPPacket && ((IPPacket) packet).protocol == mProtocal) return true;
		else if (packet instanceof ARPPacket && mProtocal == ARP_PROTOL) return true;
		return false;
	}

}
