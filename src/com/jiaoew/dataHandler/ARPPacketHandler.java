package com.jiaoew.dataHandler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

import com.jiaoew.uiFrame.Messages;

import jpcap.packet.ARPPacket;

public class ARPPacketHandler extends PacketHandler {

	public static String PROTOCAL_NAME = "ARP";
	
	public ARPPacketHandler(ARPPacket packet) {
		super(packet);
	}

	@Override
	protected void write2File(BufferedWriter bfWriter) throws IOException {
		super.write2File(bfWriter);
		ARPPacket arpPacket = (ARPPacket) mPacket;
		bfWriter.append(", Operation: " + this.getArpOperation() + ", Source IP: " + arpPacket.getSenderProtocolAddress() + ", Destination IP: " + arpPacket.getTargetProtocolAddress() + ", Source Hardware: " + arpPacket.getSenderHardwareAddress() +
				", Destination Hardware: " + arpPacket.getTargetHardwareAddress());
	}

	public String getArpOperation() {
		ARPPacket arpPacket = (ARPPacket) mPacket;
		switch(arpPacket.operation){
		case ARPPacket.ARP_REQUEST: return "ARP REQUEST ";
		case ARPPacket.ARP_REPLY: return "ARP REPLY ";
		case ARPPacket.RARP_REQUEST: return "RARP REQUEST ";
		case ARPPacket.RARP_REPLY: return "RARP REPLY ";
		case ARPPacket.INV_REQUEST: return "IDENTIFY REQUEST ";
		case ARPPacket.INV_REPLY: return "IDENTIFY REPLY ";
		default: return "UNKNOWN ";
		}
	}

	@Override
	public List<String> getPacketInfo() {
		List<String> list = super.getPacketInfo();
		ARPPacket arpPacket = (ARPPacket) mPacket;
		list.set(1, arpPacket.getSenderProtocolAddress().toString());
		list.set(2, arpPacket.getTargetProtocolAddress().toString());
		list.set(3, PROTOCAL_NAME);
		list.set(4, Messages.getString("util.info.arp.ask", new String[] {arpPacket.getSenderProtocolAddress().toString(), arpPacket.getTargetProtocolAddress().toString()}));
		return list;
	}
}
