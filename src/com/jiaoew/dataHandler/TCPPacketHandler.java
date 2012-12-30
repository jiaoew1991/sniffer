package com.jiaoew.dataHandler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.jiaoew.dataHandler.type.ProtocalType;
import com.jiaoew.dataHandler.type.TCPPortType;
import com.jiaoew.filter.DstIPFilter;
import com.jiaoew.filter.DstPortFilter;
import com.jiaoew.filter.PackageFilter;
import com.jiaoew.filter.ProtocalFilter;
import com.jiaoew.filter.SrcIPFilter;
import com.jiaoew.filter.SrcPortFilter;

import jpcap.packet.TCPPacket;

public class TCPPacketHandler extends IPPacketHandler {
	
	public TCPPacketHandler(TCPPacket packet) {
		super(packet);
	}
	public static void reductionFile(List<PacketHandler> dataList, File file) throws IOException {
		PackageFilter filter = new PackageFilter();
		filter.addFilter(new ProtocalFilter(ProtocalType.TCP));
		List<TCPPacketHandler> tcpDataList = new ArrayList<TCPPacketHandler>();
		for (PacketHandler ph : dataList) {
			if (ph.isPacketMatchFilter(filter)) {
				tcpDataList.add((TCPPacketHandler) ph);
			}
		}
		int index = 0;
		List<String> seqList = new ArrayList<String>();
		while (index != -1) {
			index = wirteSingleFile(file, tcpDataList, seqList);
		}
	}
	private static int wirteSingleFile(File file, List<TCPPacketHandler> tcpDataList, List<String> seqList) throws IOException {
		Iterator<TCPPacketHandler> it = tcpDataList.listIterator();
		long nextSeq = -1;
		int aport = -1;
		int bport = -1;
		int ret = -1;
		String dstIp = null;
		String srcIp = null;
		while(it.hasNext()) {
			TCPPacketHandler tcp = it.next();
			String str = tcp.getDestPort() + tcp.getSourcePort() + tcp.getDestIp() + tcp.getSrcIp();
			if (!seqList.contains(str)) {
				nextSeq = tcp.getSequence() + 1;
				aport = tcp.getSourcePort();
				bport = tcp.getDestPort();
				ret = tcpDataList.indexOf(tcp);
				dstIp = tcp.getDestIp();
				srcIp = tcp.getSrcIp();
				seqList.add(str);
				break;
			}
		}
		if (nextSeq != -1) {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
			PackageFilter aFilter = new PackageFilter();
			aFilter.addFilter(new ProtocalFilter(ProtocalType.TCP));
			aFilter.addFilter(new SrcPortFilter(aport));
			aFilter.addFilter(new DstPortFilter(bport));
			aFilter.addFilter(new DstIPFilter(dstIp));
			aFilter.addFilter(new SrcIPFilter(srcIp));
			while (it.hasNext()) {
				TCPPacketHandler tcp = it.next();
				if (tcp.isPacketMatchFilter(aFilter)) {
					bw.append(tcp.getPacketData());
				}
//				} else if(tcp.isPacketMatchFilter(bFilter) && tcp.getSequence() == nextSeq) {
//					nextSeq = tcp.getAskSequence();
//					bw.append(tcp.getPacketData());
//					isSource = true;
//				} 
			}
			bw.append("\r\n\r\n");
			bw.close();
		}
		return ret;
	}

	private String getSrcIp() {
		return ((TCPPacket)mPacket).src_ip.toString();
	}
	private String getDestIp() {
		return ((TCPPacket)mPacket).dst_ip.toString();
	}
	@SuppressWarnings("unused")
	private boolean isFinish() {
		return ((TCPPacket)mPacket).fin;
	}
	private int getDestPort() {
		return ((TCPPacket)mPacket).dst_port;
	}
	private int getSourcePort() {
		return ((TCPPacket)mPacket).src_port;
	}
	@SuppressWarnings("unused")
	private long getAskSequence() {
		if (((TCPPacket)mPacket).ack) {
			return ((TCPPacket)mPacket).ack_num;
		}
		return -1;
	}
	@SuppressWarnings("unused")
	private boolean isSyn() {
		return ((TCPPacket)mPacket).syn;
	}
	
	@Override
	public String getPacketData() {
		TCPPacket tcp = (TCPPacket) mPacket;
		int len = Math.min(tcp.data.length, tcp.len - tcp.header.length);
		return new String(tcp.data, 0, len);
	}
	private long getSequence() {
		return ((TCPPacket)mPacket).sequence;
	}
	@Override
	protected void write2File(BufferedWriter bfWriter) throws IOException {
		super.write2File(bfWriter);
		bfWriter.append(this.details()); 
	}
	
	public static String getProtocalNameByPort(int port) {
		for (TCPPortType tType : TCPPortType.values()) {
			if (port == tType.getValue()) {
				return tType.name();
			}
		}
		return String.valueOf(port);
	}
	@Override
	public List<String> getPacketInfo() {
		TCPPacket tcpPacket = (TCPPacket)mPacket;
		List<String> list = super.getPacketInfo();
		String name = TCPPacketHandler.getProtocalNameByPort(tcpPacket.src_port);
		if (name.equals(String.valueOf(tcpPacket.src_port)))
			name = ProtocalType.TCP.name();
		list.set(3, name);
		return list;
	}
	@Override
	public String getPacketDetailInfo() {
		return super.getPacketDetailInfo() + details();
	}
	private String details() {
		TCPPacket tcpPacket = (TCPPacket)mPacket;
		return ", Source port: " + getProtocalNameByPort(tcpPacket.src_port) + ", Destination port: " + tcpPacket.dst_port + ", Sequcence Number: "+ tcpPacket.sequence+
				", Winodws Size: "+ tcpPacket.window + ", ack: "  + (tcpPacket.ack? tcpPacket.ack_num:"false") + ", SYN: " + tcpPacket.syn + ", FIN: " + tcpPacket.fin + ", PSH: " +
				tcpPacket.psh + ", RST: " + tcpPacket.rst + ", URG: " + tcpPacket.urg;
	}
}
