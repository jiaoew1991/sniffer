package com.jiaoew.dataHandler;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import jpcap.packet.ARPPacket;
import jpcap.packet.ICMPPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;
import jpcap.packet.UDPPacket;

public class PacketHandlerFactory {

	@SuppressWarnings("rawtypes")
	public static PacketHandler createPacketHandler(Packet packet) {
		
//		String name = packet.getClass().getName().substring(13);
//		PacketHandler handler = null;
//		try {
//			Class<?> c = Class.forName("com.jiaoew.dataHandler." + name + "Handler");
//			Class[] params = {packet.getClass()};
//			Constructor con = c.getConstructor(params);
//			Object[] initargs = {packet};
//			handler = (PacketHandler) con.newInstance(initargs);
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} catch (SecurityException e) {
//			e.printStackTrace();
//		} catch (NoSuchMethodException e) {
//			e.printStackTrace();
//		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
//		} catch (InstantiationException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		} catch (InvocationTargetException e) {
//			e.printStackTrace();
//		}
//		return handler		
		PacketHandler handler = null;
		if (packet instanceof TCPPacket) {
			handler = new TCPPacketHandler((TCPPacket) packet);
		} else if (packet instanceof UDPPacket) {
			handler = new UDPPacketHandler((UDPPacket) packet);
		} else if (packet instanceof ICMPPacket) {
			handler = new ICMPPacketHandler((ICMPPacket) packet);
		} else if (packet instanceof IPPacket) {
			handler = new IPPacketHandler((IPPacket) packet);
		} else if (packet instanceof ARPPacket) {
			handler = new ARPPacketHandler((ARPPacket) packet);
		} else{
			handler = new PacketHandler(packet);
		}
		return handler;
	}
}
