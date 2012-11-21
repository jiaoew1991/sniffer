package com.jiaoew.dataHandler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jiaoew.MainSniffer;
import com.jiaoew.filter.PackageFilter;

import jpcap.packet.ARPPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;
import jpcap.packet.UDPPacket;

public class PacketHandler {

	protected Packet mPacket;
	public PacketHandler(Packet packet) {
		super();
		mPacket = packet;
	}
	public boolean isPacketMatchFilter(PackageFilter filter) {

		if (mPacket == null) {
			return false;
		} else if (filter == null) {
			return true;
		}
		return filter.filterPacket(mPacket);
	}
	protected void write2File(BufferedWriter bfWriter) throws IOException {
//		String p = mPacket.toString();
//		int white = p.indexOf(' ');
		String timeStamp = new Timestamp(mPacket.sec * 1000 + mPacket.usec / 1000).toString(); 
//		String target = timeStamp + " " + p.substring(white + 1);
		bfWriter.append(timeStamp);
	}
	public static void SaveSelectedPacket(List<PacketHandler> packet, String fileName) throws IOException {
		File file = new File(fileName);
		if (file.exists()) {
			file.delete();
		}
		file.createNewFile();
		BufferedWriter bfWriter = new BufferedWriter(new FileWriter(file));
		for (PacketHandler packetHandler : packet) {
			packetHandler.write2File(bfWriter);
			bfWriter.append("\r\n");
		}
		bfWriter.close();
	}
}
