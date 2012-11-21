package com.jiaoew.dataHandler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;

import com.jiaoew.MainSniffer;
import com.jiaoew.PackageFilter;

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
		if (filter == null) {
			return true;
		} else {
			if (mPacket instanceof IPPacket) {
				try {
					if (filter.getDst_ip() != null && !InetAddress.getByName(filter.getDst_ip()).equals(((IPPacket) mPacket).dst_ip)) return false;
					if (filter.getSrc_ip() != null && !InetAddress.getByName(filter.getSrc_ip()).equals(((IPPacket) mPacket).src_ip)) return false;
					if (filter.getProtocal() != -1 && filter.getProtocal() != ((IPPacket)mPacket).protocol) return false;
					if (filter.getSrc_port() != -1 && mPacket instanceof TCPPacket && filter.getSrc_port() != ((TCPPacket)mPacket).src_port) return false;
					if (filter.getDst_port() != -1 && mPacket instanceof TCPPacket && filter.getDst_port() != ((TCPPacket)mPacket).dst_port) return false;
					if (filter.getSrc_port() != -1 && mPacket instanceof UDPPacket && filter.getSrc_port() != ((UDPPacket)mPacket).src_port) return false;
					if (filter.getDst_port() != -1 && mPacket instanceof UDPPacket && filter.getDst_port() != ((UDPPacket)mPacket).dst_port) return false;
					return true;
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (mPacket instanceof ARPPacket){
				try {
					if (filter.getProtocal() != -1 && filter.getProtocal() != MainSniffer.ARP_PROTOL) return false;
					if (filter.getDst_ip() != null && !InetAddress.getByName(filter.getDst_ip()).equals(((ARPPacket) mPacket).getTargetProtocolAddress())) return false;
					if (filter.getSrc_ip() != null && !InetAddress.getByName(filter.getSrc_ip()).equals(((ARPPacket) mPacket).getSenderProtocolAddress())) return false;
					return true;
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	private void write2File(BufferedWriter bfWriter) throws IOException {
		bfWriter.append(mPacket.toString());
	}
	public static void SaveSelectedPacket(List<PacketHandler> packet, String fileName) throws IOException {
		File file = new File(fileName);
		if (!file.exists()) {
			file.createNewFile();
		}
		BufferedWriter bfWriter = new BufferedWriter(new FileWriter(file));
		for (PacketHandler packetHandler : packet) {
			packetHandler.write2File(bfWriter);
			bfWriter.append("\n");
		}
	}
}
