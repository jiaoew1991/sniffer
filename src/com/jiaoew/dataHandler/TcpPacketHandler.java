package com.jiaoew.dataHandler;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.jiaoew.filter.PackageFilter;

import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;

public class TcpPacketHandler extends PacketHandler {

	public TcpPacketHandler(TCPPacket packet) {
		super(packet);
		// TODO Auto-generated constructor stub
	}
	public static void reductionFile(List<TcpPacketHandler> dataList, String fileName) throws IOException {
		File file = new File(fileName);
		if (!file.exists()) {
			file.createNewFile();
		}
		for (TcpPacketHandler tcpPacketHandler : dataList) {
			if (tcpPacketHandler.getTargetFileName().equals(fileName)) {
				
			}
		}
	}

	
	private String getTargetFileName() {
		TCPPacket tPacket = (TCPPacket) mPacket;
		return null;
	}

}
