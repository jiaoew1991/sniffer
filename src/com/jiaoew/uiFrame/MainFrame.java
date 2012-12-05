package com.jiaoew.uiFrame;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.SystemColor;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JMenu;
import javax.swing.UIManager;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;
import javax.swing.JTextArea;

import com.jiaoew.MainSniffer;
import com.jiaoew.MainSniffer.OnSnifferListener;
import com.jiaoew.dataHandler.PacketHandler;
import com.jiaoew.dataHandler.TCPPacketHandler;
import com.jiaoew.exception.NetworkInterfaceUnableException;
import com.jiaoew.filter.ContentFilter;
import com.jiaoew.filter.DstIPFilter;
import com.jiaoew.filter.DstPortFilter;
import com.jiaoew.filter.PackageFilter;
import com.jiaoew.filter.ProtocalFilter;
import com.jiaoew.filter.SrcIPFilter;
import com.jiaoew.filter.SrcPortFilter;

import net.miginfocom.swing.MigLayout;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import jpcap.NetworkInterface;

public class MainFrame {

	private JFrame frame;
	private JTable table;
	private MainSniffer mSniffer;

	private List<PacketHandler> pHandlerList = new ArrayList<PacketHandler>();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
//				loadLibrary();
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					MainFrame window = new MainFrame();
					window.frame.setVisible(true);
//					window.frame.setFont(new Font())
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		mSniffer = new MainSniffer();
		frame.setForeground(SystemColor.text);
		frame.setTitle(Messages.getString("app.title"));
		frame.setBounds(100, 100, 563, 752);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		initMenuBar();
		initContentPane();
	}

	private void initMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu(Messages.getString("menu.file")); 
		menuBar.add(mnNewMenu);
		
		JMenuItem startMenuItem = new JMenuItem(Messages.getString("menu.file.startSniffer"));
		startMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (showOpenDeviceDialog()) {
					mnNewMenu_1.setEnabled(true);
				}
			}
		});
		mnNewMenu.add(startMenuItem);
		
		JMenuItem endMenuItem = new JMenuItem(Messages.getString("menu.file.endSniffer"));
		endMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopSniffer();
			}
		});
		mnNewMenu.add(endMenuItem);
		
		JMenuItem exitMenuItem = new JMenuItem();
		exitMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		exitMenuItem.setText(Messages.getString("menu.file.exit")); 
		mnNewMenu.add(exitMenuItem);
		
		mnNewMenu_1 = new JMenu(Messages.getString("menu.edit"));
		mnNewMenu_1.setEnabled(false);
		menuBar.add(mnNewMenu_1);
		
		JMenuItem optionMenuItem = new JMenuItem(Messages.getString("menu.edit.option")); 
		optionMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				savePacket2File();
			}
		});
		mnNewMenu_1.add(optionMenuItem);
		
		JMenuItem mntmNewMenuItem = new JMenuItem(Messages.getString("menu.edit.filter"));
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showFilterDialog();
			}
		});
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem(Messages.getString("menu.edit.clear"));
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearTable();
			}

		});
		mnNewMenu_1.add(mntmNewMenuItem_1);
		mnNewMenu_1.add(mntmNewMenuItem);
		
		JMenuItem menuItem = new JMenuItem(Messages.getString("menu.edit.search"));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showSearchKeyDialog();
			}
		});
		mnNewMenu_1.add(menuItem);
		
		JMenuItem reductionMenuItem = new JMenuItem(Messages.getString("menu.edit.reductionFile")); 
		reductionMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (showRedeuctionDialog()) {
					stopSniffer();
				}
			}
		});
		mnNewMenu_1.add(reductionMenuItem);
		
		JMenu mnNewMenu_2 = new JMenu(Messages.getString("menu.help"));
		menuBar.add(mnNewMenu_2);
		
		JMenuItem aboutMenuItem = new JMenuItem(Messages.getString("menu.help.about")); 
		aboutMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showAboutDialog();
			}
		});
		mnNewMenu_2.add(aboutMenuItem);
	}
	private JTextArea packetInfoText = null;
	private JTextArea packetDataText = null;
	private JScrollPane scrollPane;
	private JMenu mnNewMenu_1;
	private void initContentPane() {
		frame.getContentPane().setLayout(new MigLayout("", "[grow]", "[grow][grow][grow]"));
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				Messages.getString("mainFrame.table.header.time"), 
				Messages.getString("mainFrame.table.header.src_ip"),
				Messages.getString("mainFrame.table.header.dst_ip"),
				Messages.getString("mainFrame.table.header.protocal"),
				Messages.getString("mainFrame.table.header.other_info")
			}
		));
		scrollPane = new JScrollPane(table);
		scrollPane.setViewportView(table);
		frame.getContentPane().add(scrollPane, "cell 0 0,grow");
//		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		final DefaultTableModel model = (DefaultTableModel) table.getModel();
		mSniffer.setOnSnifferListener(new OnSnifferListener() {
			
			@Override
			public void onSniffer(PacketHandler ph) {
				try {
					model.addRow(ph.getPacketInfo().toArray());				
				} catch (Exception e) {
					e.printStackTrace();
				}
				pHandlerList.add(ph);
				JScrollBar vBar = scrollPane.getVerticalScrollBar();
				vBar.setValue(vBar.getMaximum());
			}
		});
		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				changeInfoText();
			}
			
		});

		JScrollPane scrollPane_1 = new JScrollPane();
		frame.getContentPane().add(scrollPane_1, "cell 0 1,grow");
		
		packetInfoText = new JTextArea();
		packetInfoText.setEditable(false);
		scrollPane_1.setViewportView(packetInfoText);
		packetInfoText.setText("");
		packetInfoText.setLineWrap(true);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		frame.getContentPane().add(scrollPane_2, "cell 0 2,grow");
		
		packetDataText = new JTextArea();
		scrollPane_2.setViewportView(packetDataText);
		packetDataText.setEditable(false);
		packetDataText.setLineWrap(true);
	}

	private void clearTable() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.getDataVector().removeAllElements();
		pHandlerList = new ArrayList<PacketHandler>();
		packetInfoText.setText("");
		packetDataText.setText("");
	}
	private void changeInfoText() {
		int row = table.getSelectedRow();
		PacketHandler ph = pHandlerList.get(row);
		StringBuffer sBuffer = new StringBuffer();
		for (String str : ph.getPacketInfo()) {
			sBuffer.append(str);
			sBuffer.append('\n');
		}
		packetInfoText.setText(sBuffer.toString());
		packetDataText.setText(ph.getPacketData());
	}
	public void setStartSniffer(int index) {
		try {
			mSniffer.setNetworkList(mSniffer.getNetworkList().get(index));
			mSniffer.startSnipper();
		} catch (NetworkInterfaceUnableException e) {
			e.printStackTrace();
		}
	}
	private boolean showOpenDeviceDialog() {
		final OpenDeviceDialog dialog = new OpenDeviceDialog(frame, true);
		dialog.setSize(360, 240);
		try {
			List<NetworkInterface> niList = mSniffer.getNetworkList();
			for (NetworkInterface ni: niList) {
				dialog.comboBox.addItem(ni.description);
			}
			dialog.cancelButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					dialog.dispose();				
				}
			});
			dialog.okButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					int index = dialog.comboBox.getSelectedIndex();
					setStartSniffer(index);
					clearTable();
					dialog.dispose();
				}
			});
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			return true;
		} catch (NetworkInterfaceUnableException e1) {
			e1.printStackTrace();
			return false;
		}
	}

	protected void showAboutDialog() {
		
	}

	protected boolean showRedeuctionDialog() {
		JFileChooser chooser = new JFileChooser();
		int ret = chooser.showDialog(frame, Messages.getString("optionDialog.label.save"));
		if (ret == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			try {
				TCPPacketHandler.reductionFile(pHandlerList, file);
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	protected void showFilterDialog() {
		final FilterDialog dialog = new FilterDialog(frame, true);
		dialog.cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		final PackageFilter filter = new PackageFilter();
		dialog.okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String a = dialog.srcIpTextField.getText().trim();
				if (!a.equals("")) {
					filter.addFilter(new SrcIPFilter(a));
				}
				a = dialog.dstIpTextField.getText().trim();
				if (!a.equals("")) {
					filter.addFilter(new DstIPFilter(a));
				}
				a = dialog.dstPortTextField.getText().trim();
				if (!a.equals("")) {
					filter.addFilter(new DstPortFilter(Short.valueOf(a)));
				}
				a = dialog.srcPortTextField.getText().trim();
				if (!a.equals("")) {
					filter.addFilter(new SrcPortFilter(Short.valueOf(a)));
				}
				a = (String) dialog.comboBox.getSelectedItem();
				if (!a.equals("")) {
					filter.addFilter(new ProtocalFilter(a));
				}
				mSniffer.setPackageFilter(filter);
				dialog.dispose();
			}
		});
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	}

	protected void savePacket2File() {
		JFileChooser chooser = new JFileChooser();
		int ret = chooser.showDialog(frame, Messages.getString("optionDialog.label.save"));
		if (ret == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			try {
				PacketHandler.SaveSelectedPacket(pHandlerList, file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	protected void showSearchKeyDialog() {
		final SearchKeyDialog	 dialog = new SearchKeyDialog(frame, true);
		dialog.okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String text = dialog.textField.getText();
				PackageFilter filter = (PackageFilter) mSniffer.getPackageFilter();
				filter.addFilter(new ContentFilter(text));
				mSniffer.setPackageFilter(filter);
				dialog.dispose();
			}
		});
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	}

	private void stopSniffer() {
		mSniffer.stopSniffer();
		mSniffer.setPackageFilter(null);
		mnNewMenu_1.setEnabled(false);
	}
}
