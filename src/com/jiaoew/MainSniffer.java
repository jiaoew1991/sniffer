package com.jiaoew;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jiaoew.dataHandler.PacketHandler;
import com.jiaoew.dataHandler.PacketHandlerFactory;
import com.jiaoew.dataHandler.TCPPacketHandler;
import com.jiaoew.dataHandler.type.ProtocalType;
import com.jiaoew.exception.NetworkInterfaceUnableException;
import com.jiaoew.filter.ContentFilter;
import com.jiaoew.filter.DstPortFilter;
import com.jiaoew.filter.Filter;
import com.jiaoew.filter.PackageFilter;
import com.jiaoew.filter.ProtocalFilter;
import com.jiaoew.filter.SrcIPFilter;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.PacketReceiver;
import jpcap.packet.Packet;

public class MainSniffer {

	public static final int SNAP_LENGTH = 65535;
	public static final int TIMEOUT_SIZE = 2000;
	
	private JpcapCaptor mCaptor = null;
	private List<NetworkInterface> mNetworkList = null;
	private NetworkInterface mCurNetwork = null;
	private Filter mFilter = null;
	private OnSnifferListener mOnSnifferListener = null;

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
						} 
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}.start();
	}
	public void stopSniffer(){
		if (mCaptor != null) {
			mCaptor.breakLoop();
			System.out.println("captor close");
		} 
	}
	public void setPackageFilter(Filter filter) {
		mFilter = filter;
	}
	public void setOnSnifferListener(OnSnifferListener mOnSnifferListener) {
		this.mOnSnifferListener = mOnSnifferListener;
	}

	public Filter getPackageFilter() {
		return mFilter;
	}
	class BasePacketReceiver implements PacketReceiver {
		@Override
		public void receivePacket(Packet packet) {
			PacketHandler pHandler = PacketHandlerFactory.createPacketHandler(packet);
			if (pHandler.isPacketMatchFilter(mFilter)) {
//				System.out.println(new Timestamp(packet.sec * 1000 + packet.usec / 1000));
//				System.out.println(packet);
//				System.out.println("data: " + new String(packet.data));
//				try {
//					BufferedWriter bw = new BufferedWriter(new FileWriter("abcd", true));
//					bw.append(packet.toString() + ",,,,,,,,len: " + packet.len + ",,,,,,,, header_len: " + packet.header.length + ",,,,,,,,,,data_len: " + packet.data.length + "\r\n");
//					bw.append(pHandler.getPacketData());
//					bw.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//				
//				targetPacket.add(pHandler);
				if (mOnSnifferListener != null) {
					mOnSnifferListener.onSniffer(pHandler);
				}
			}
		}
		
	}
	public interface OnSnifferListener {
		public void onSniffer(PacketHandler ph);
	}
	public static void main(String[] args) {
		MainSniffer test = new MainSniffer();
		List<NetworkInterface> list = null;
		try {
			list = test.getNetworkList();
			System.out.println(list.size());
			test.setNetworkList(list.get(0));
			final List<PacketHandler> packetList = new ArrayList<PacketHandler>();
			test.setOnSnifferListener(new OnSnifferListener() {
				
				@Override
				public void onSniffer(PacketHandler ph) {
					packetList.add(ph);
				}
			});
			test.startSnipper();
			
//			test1(test);
			
			PackageFilter filter = new PackageFilter();
			filter.addFilter(new ProtocalFilter(ProtocalType.ARP));
//			filter.addFilter(new SrcPortFilter(80));
//			filter.addFilter(new SrcIPFilter("58.205.210.237"));
			test.setPackageFilter(filter);
			Thread.sleep(30000);
			test.stopSniffer();
			try {
				TCPPacketHandler.reductionFile(packetList, new File("lllll"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (NetworkInterfaceUnableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
	}
	@SuppressWarnings("unused")
	private static void test1(MainSniffer test) throws InterruptedException, IOException {
		Thread.sleep(1000);
		PackageFilter filter = new PackageFilter();
		filter.addFilter(new ProtocalFilter(ProtocalType.TCP));
		System.out.println("filter tcp");
		test.setPackageFilter(filter);
		Thread.sleep(1000);
		PackageFilter filter2 = new PackageFilter();
		filter2.addFilter(new ProtocalFilter(ProtocalType.UDP));
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
		Thread.sleep(2000);
		test.stopSniffer();
	}
}
