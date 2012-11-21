package com.jiaoew.dataHandler;

import jpcap.packet.ARPPacket;
import jpcap.packet.EthernetPacket;
import jpcap.packet.ICMPPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;
import jpcap.packet.UDPPacket;

public class PacketHandlerFactory {

	public static PacketHandler createPacketHandler(Packet packet) {
		
		PacketHandler handler = null;
		if (packet instanceof TCPPacket) {
			handler = new TcpPacketHandler((TCPPacket) packet);
		} else if (packet instanceof UDPPacket) {
			handler = new UdpPacketHandler((UDPPacket) packet);
		} else if (packet instanceof ICMPPacket) {
			handler = new ICMPPacketHandler((ICMPPacket) packet);
		} else if (packet instanceof IPPacket) {
			handler = new IPPacketHandler((IPPacket) packet);
		} else if (packet instanceof ARPPacket) {
			handler = new ARPPacketHandler((ARPPacket) packet);
		} else{
			handler = new PacketHandler(packet);
		}
		return handler;
	}
}
