package com.jiaoew.filter;

import java.util.ArrayList;
import java.util.List;

import jpcap.packet.Packet;

public class PackageFilter implements Filter{

	private List<Filter> mFilterList = new ArrayList<Filter>();

	public PackageFilter addFilter(Filter filter) {
		mFilterList.add(filter);
		return this;
	}
	@Override
	public boolean filterPacket(Packet packet) {
		for (Filter fl : mFilterList) {
			if (!fl.filterPacket(packet)) {
				return false;
			}
		}
		return true;
	}
	
}
