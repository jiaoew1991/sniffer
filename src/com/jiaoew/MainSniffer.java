package com.jiaoew;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

import sun.util.resources.CalendarData;

import com.jiaoew.dataHandler.PacketHandler;
import com.jiaoew.dataHandler.PacketHandlerFactory;
import com.jiaoew.exception.NetworkInterfaceUnableException;
import com.jiaoew.filter.ContentFilter;
import com.jiaoew.filter.DstPortFilter;
import com.jiaoew.filter.PackageFilter;
import com.jiaoew.filter.ProtocalFilter;
import com.jiaoew.filter.SrcIPFilter;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.NetworkInterfaceAddress;
import jpcap.PacketReceiver;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;

public class MainSniffer {

	public static final int SNAP_LENGTH = 65535;
	public static final int TIMEOUT_SIZE = 2000;
	
	private JpcapCaptor mCaptor = null;
	private List<JpcapCaptor> allCaptor = new ArrayList<JpcapCaptor>();
	private List<NetworkInterface> mNetworkList = null;
	private NetworkInterface mCurNetwork = null;
	private PackageFilter mFilter = null;

	public MainSniffer() {
		super();
		NetworkInterface[] nilist = JpcapCaptor.getDeviceList();
		mNetworkList = new ArrayList<NetworkInterface>(Arrays.asList(nilist));
	}
	public List<NetworkInterface> getNetworkList() throws NetworkInterfaceUnableException {
		if (mNetworkList.size() < 1) {
			throw new NetworkInterfaceUnableException();
		}
		return mNetworkList;
	}

	public void setNetworkList(NetworkInterface nInterface) {
		mCurNetwork = nInterface;
	}
	public void startSnipper() {
		new Thread() {
			@Override
			public void run() {
				super.run();
				try {
					if (mCurNetwork != null) {
						mCaptor = JpcapCaptor.openDevice(mCurNetwork, SNAP_LENGTH, true, TIMEOUT_SIZE);
						System.out.println("captor start");
						mCaptor.loopPacket(-1, new BasePacketReceiver());
					} else {
						for (NetworkInterface ni : mNetworkList) {
							JpcapCaptor tCaptor = JpcapCaptor.openDevice(ni, SNAP_LENGTH, true, TIMEOUT_SIZE);
							allCaptor.add(tCaptor);
							tCaptor.loopPacket(-1, new BasePacketReceiver());
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}.start();
	}
	public void stopSnipper(){
		if (mCaptor != null) {
			mCaptor.breakLoop();
			System.out.println("captor close");
		} else {
			for (JpcapCaptor jc : allCaptor) {
				jc.breakLoop();
			}
			System.out.println("captor close");
		}
	}
	public void setPackageFilter(PackageFilter filter) {
		mFilter = filter;
	}
	private List<PacketHandler> targetPacket = new ArrayList<PacketHandler>();
	public List<PacketHandler> getTargetPacket() {
		return targetPacket;
	}
	class BasePacketReceiver implements PacketReceiver {
		@Override
		public void receivePacket(Packet packet) {
//			System.out.println("caplen: " + packet.caplen + "\n data: " + new String(packet.data) + "\n len: " + packet.len + "\n usec: " + packet.usec + "\n sec: " + packet.sec 
//					+ "\n header: " + new String(packet.header) + "\n dlink: " + packet.datalink);
			PacketHandler pHandler = PacketHandlerFactory.createPacketHandler(packet);
			if (pHandler.isPacketMatchFilter(mFilter)) {
				System.out.println(new Timestamp(packet.sec * 1000 + packet.usec / 1000));
				System.out.println(packet);
				targetPacket.add(pHandler);
			}
		}
		
	}
	public static void main(String[] args) {
		MainSniffer test = new MainSniffer();
		List<NetworkInterface> list = null;
		try {
			list = test.getNetworkList();
			System.out.println(list.size());
//			test.setNetworkList(list.get(0));
			test.startSnipper();
			Thread.sleep(1000);
			PackageFilter filter = new PackageFilter();
			filter.addFilter(new ProtocalFilter(ProtocalFilter.TCP_PROTOL));
			System.out.println("filter tcp");
			test.setPackageFilter(filter);
			Thread.sleep(1000);
			PackageFilter filter2 = new PackageFilter();
			filter2.addFilter(new ProtocalFilter(ProtocalFilter.UDP_PROTOL));
			filter2.addFilter(new DstPortFilter(11111));
			System.out.println("filter udp");
			test.setPackageFilter(filter2);
			Thread.sleep(1000);
			PackageFilter filter3 = new PackageFilter();
			filter3.addFilter(new SrcIPFilter("192.168.1.78"));
			System.out.println("filter 192.168.1.78");
			test.setPackageFilter(filter3);
			Thread.sleep(1000);
			PackageFilter filter4 = new PackageFilter();
			filter4.addFilter(new ContentFilter("a"));
			System.out.println("filter content");
			test.setPackageFilter(filter4);
			Thread.sleep(20000);
			test.stopSnipper();
			PacketHandler.SaveSelectedPacket(test.getTargetPacket(), "abc.txt");
			System.out.println("write success");
		} catch (NetworkInterfaceUnableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
