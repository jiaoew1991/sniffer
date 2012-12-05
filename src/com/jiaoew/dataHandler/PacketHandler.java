package com.jiaoew.dataHandler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import jpcap.packet.Packet;

import com.jiaoew.filter.Filter;
public class PacketHandler {

	protected Packet mPacket;
	protected Timestamp mTime;
	public PacketHandler(Packet packet) {
		super();
		mPacket = packet;
		mTime = new Timestamp(mPacket.sec * 1000 + mPacket.usec / 1000);
	}
	public boolean isPacketMatchFilter(Filter filter) {
		if (mPacket == null) {
			return false;
		} else if (filter == null) {
			return true;
		}
		return filter.filterPacket(mPacket);
	}
	protected void write2File(BufferedWriter bfWriter) throws IOException {
		String timeStamp = mTime.toString(); 
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
	public static void SaveSelectedPacket(List<PacketHandler> packet, File file) throws IOException {
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
	public String getPacketData() {
		return new String(mPacket.data);
	}
	public List<String> getPacketInfo() {
		List<String> info = new ArrayList<String>();
		info.add(mTime.toString());
		info.add("");
		info.add("");
		info.add("");
		info.add("");
		return info;
	}
}
