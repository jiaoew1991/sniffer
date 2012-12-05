package com.jiaoew.dataHandler.type;

public enum ProtocalType {
	ICMP(1), 
	IGMP(2), 
	IP(4),
	TCP(6),
	ARP(10), 
	UDP(17);
	int value;
	ProtocalType(int num) {
		this.value = num;
	}
	public int getValue() {
		return value;
	}
	public static ProtocalType getProtocalByValue(int value) {
		for (ProtocalType pro : ProtocalType.values()) {
			if (value == pro.value) {
				return pro;
			}
		}
		return null;
	}

}