package com.jiaoew.filter;

import com.jiaoew.dataHandler.type.ProtocalType;

import jpcap.packet.ARPPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;

public class ProtocalFilter implements Filter {
	
//	public static final short ICMP_PROTOL = 1;
//	public static final short IGMP_PROTOL = 2;
//	public static final short IP_PROTOL = 4;
//	public static final short TCP_PROTOL = 6;
//	public static final short ARP_PROTOL = 10;
//	public static final short UDP_PROTOL = 17;
	private ProtocalType pType;
	public ProtocalFilter(short protocal) {
		super();
		for (ProtocalType pro : ProtocalType.values()) {
			if (pro.getValue() == protocal) {
				pType = pro;
			}
		}
	}
	public ProtocalFilter(String protocal) {
		super();
		for (ProtocalType pro : ProtocalType.values()) {
			if (pro.name().equals(protocal)) 
				pType = pro;
		}
	}
	public ProtocalFilter(ProtocalType type) {
		super();
		pType = type;
	}
	@Override
	public boolean filterPacket(Packet packet) {
		if (packet instanceof IPPacket && ((IPPacket) packet).protocol == pType.getValue()) return true;
		else if (packet instanceof ARPPacket && pType == ProtocalType.ARP) return true;
		return false;
	}
//	public static String getProtocalById(short id) {
//		for (ProtocalType pro : ProtocalType.values()) {
//			if (id == pro.getValue()) {
//				return pro.name();
//			}
//		}
//		return null;
//	}

}
