package com.jiaoew.filter;

import jpcap.packet.Packet;

public interface Filter {
	public boolean filterPacket(Packet packet);
}
