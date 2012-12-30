package com.jiaoew.dataHandler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

import com.jiaoew.dataHandler.type.ProtocalType;
import com.jiaoew.uiFrame.Messages;

import jpcap.packet.ICMPPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.TCPPacket;
import jpcap.packet.UDPPacket;

public class IPPacketHandler extends PacketHandler {

	public IPPacketHandler(IPPacket packet) {
		super(packet);
	}

	@Override
	protected void write2File(BufferedWriter bfWriter) throws IOException {
		super.write2File(bfWriter);
		bfWriter.append(' ');
		bfWriter.append(this.details());
//		bfWriter.append("Source IP: " + ipPacket.src_ip + ", Destination IP: " + ipPacket.dst_ip + ", Protocal: " + ProtocalType.getProtocalByValue(ipPacket.protocol).name() + ", priority" + ipPacket.priority);
//		if (ipPacket.version == 4) {
//			bfWriter.append(", D: " + ipPacket.d_flag + ", T: " + ipPacket.t_flag + ", R: " + ipPacket.r_flag + ", hop limit: " + ipPacket.hop_limit
//					+ ", RF: " + ipPacket.rsv_frag + ", DF: " + ipPacket.dont_frag + ", MF: " + ipPacket.more_frag + ", Offset: " + ipPacket.offset + ", Ident: " + ipPacket.ident);
//		} else {
//			bfWriter.append(", Flowlabel:" + ipPacket.flow_label + ", hop:" + ipPacket.hop_limit);
// 
//		}
	}
	@Override
	public List<String> getPacketInfo() {
		IPPacket ipPacket = (IPPacket) mPacket;
		List<String> list = super.getPacketInfo();
		list.set(1, ipPacket.src_ip.toString());
		list.set(2, ipPacket.dst_ip.toString());
		ProtocalType type = ProtocalType.getProtocalByValue(ipPacket.protocol);
		list.set(3, type.name());
		switch (type) {
		case TCP:
			list.set(4, Messages.getString("util.info.tcp.request", new String[] {((TCPPacket)ipPacket).src_port + "", ((TCPPacket)ipPacket).dst_port + ""}));
			break;
		case UDP:
			list.set(4, Messages.getString("util.info.tcp.request", new String[] {((UDPPacket)ipPacket).src_port + "", ((UDPPacket)ipPacket).dst_port + ""}));
			break;
		case ICMP:
			list.set(4, Messages.getString("util.info.icmp.content", new String[] {String.valueOf(((ICMPPacket)ipPacket).type), String.valueOf(((ICMPPacket)ipPacket).code)}));
			break;
		case IGMP:
			list.set(4, Messages.getString("util.info.igmp.broadcast", new String[] {ipPacket.src_ip.toString()}));
			break;
		}
		return list;
	}

	@Override
	public String getPacketDetailInfo() {
		StringBuffer sb = new StringBuffer(super.getPacketDetailInfo());
		sb.append(this.details());
		return sb.toString();
	}
	
	private String details() {
		StringBuffer sb = new StringBuffer();
		IPPacket ipPacket = (IPPacket) mPacket;
		sb.append("Source IP: " + ipPacket.src_ip + ", Destination IP: " + ipPacket.dst_ip + ", Protocal: " + ProtocalType.getProtocalByValue(ipPacket.protocol).name() + ", priority" + ipPacket.priority);
		if (ipPacket.version == 4) {
			sb.append(", version: IPv4" + ", D: " + ipPacket.d_flag + ", T: " + ipPacket.t_flag + ", R: " + ipPacket.r_flag + ", hop limit: " + ipPacket.hop_limit
					+ ", RF: " + ipPacket.rsv_frag + ", DF: " + ipPacket.dont_frag + ", MF: " + ipPacket.more_frag + ", Offset: " + ipPacket.offset + ", Ident: " + ipPacket.ident);
		} else {
			sb.append(", version: IPv6" + ", Flowlabel:" + ipPacket.flow_label + ", hop:" + ipPacket.hop_limit);
		}
		return sb.toString();
	}

}
