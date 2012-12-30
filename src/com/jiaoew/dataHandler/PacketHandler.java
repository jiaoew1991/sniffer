package com.jiaoew.dataHandler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public static Map<Integer, PacketHandler>getTargetPacket(List<PacketHandler> list, Filter filter) {
		Map<Integer, PacketHandler> map = new HashMap<Integer, PacketHandler>();
		int count = 0;
		for (PacketHandler ph : list) {
			if (ph.isPacketMatchFilter(filter)) {
				map.put(new Integer(count), ph);
			}
			count ++;
		}
		return map;
	}
	public static List<Integer> getTargetPacketIndex(List<PacketHandler> list, Filter filter) {
		List<Integer> mList = new ArrayList<Integer>();
		int count = 0;
		for (PacketHandler ph : list) {
			if (ph.isPacketMatchFilter(filter)) {
				mList.add(count);
			}
			count ++;
		}
		return mList;
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
	public String getPacketDetailInfo() {
		return mPacket.datalink.toString();
	}
}
