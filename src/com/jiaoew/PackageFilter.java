package com.jiaoew;

import java.net.InetAddress;

public class PackageFilter {

	private String src_ip = null;
	private String dst_ip = null;
	private int src_port = -1;
	private int dst_port = -1;
	private short protocal = -1;
	
	public PackageFilter() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PackageFilter(short protocal) {
		super();
		this.protocal = protocal;
	}
	public String getSrc_ip() {
		return src_ip;
	}
	public void setSrc_ip(String src_ip) {
		this.src_ip = src_ip;
	}
	public String getDst_ip() {
		return dst_ip;
	}
	public void setDst_ip(String dst_ip) {
		this.dst_ip = dst_ip;
	}
	public int getSrc_port() {
		return src_port;
	}
	public void setSrc_port(int src_port) {
		this.src_port = src_port;
	}
	public int getDst_port() {
		return dst_port;
	}
	public void setDst_port(int dst_port) {
		this.dst_port = dst_port;
	}
	public short getProtocal() {
		return protocal;
	}
	public void setProtocal(short protocal) {
		this.protocal = protocal;
	}
	public boolean isUnInit() {
		return src_ip == null && dst_ip == null && src_port == -1 && dst_port == -1 && protocal == -1;
	}
}
