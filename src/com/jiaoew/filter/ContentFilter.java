package com.jiaoew.filter;

import jpcap.packet.Packet;

public class ContentFilter implements Filter {

	private String mContent = null;
	public ContentFilter(String content) {
		super();
		mContent = content;
	}

	@Override
	public boolean filterPacket(Packet packet) {
		String packetData = new String(packet.data);
		if (packetData.contains(mContent)) {
			System.out.println("lllll");
		}
		return packetData.contains(mContent);
	}

}
