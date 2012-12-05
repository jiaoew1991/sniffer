package com.jiaoew.uiFrame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;

import com.jiaoew.dataHandler.type.ProtocalType;

public class FilterDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -606714332030585777L;
	private final JPanel contentPanel = new JPanel();
	JTextField srcIpTextField;
	JTextField dstIpTextField;
	JTextField srcPortTextField;
	JTextField dstPortTextField;
	JComboBox comboBox;
	JButton okButton;
	JButton cancelButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
//			FilterDialog dialog = new FilterDialog();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public FilterDialog(JFrame owner, boolean modal) {
		super(owner, modal);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.NORTH);
		contentPanel.setLayout(new MigLayout("", "[][grow]", "[][][][][]"));
		{
			JLabel lblNewLabel = new JLabel(Messages.getString("filterDialog.label.src_ip")); //$NON-NLS-1$
			contentPanel.add(lblNewLabel, "cell 0 0,alignx trailing");
		}
		{
			srcIpTextField = new JTextField();
			contentPanel.add(srcIpTextField, "cell 1 0,growx");
			srcIpTextField.setColumns(10);
		}
		{
			JLabel lblNewLabel_1 = new JLabel(Messages.getString("filterDialog.label.dst_ip")); //$NON-NLS-1$
			contentPanel.add(lblNewLabel_1, "cell 0 1,alignx trailing");
		}
		{
			dstIpTextField = new JTextField();
			contentPanel.add(dstIpTextField, "cell 1 1,growx");
			dstIpTextField.setColumns(10);
		}
		{
			JLabel lblNewLabel_2 = new JLabel(Messages.getString("filterDialog.label.src_port")); //$NON-NLS-1$
			contentPanel.add(lblNewLabel_2, "cell 0 2,alignx trailing");
		}
		{
			srcPortTextField = new JTextField();
			contentPanel.add(srcPortTextField, "cell 1 2,growx");
			srcPortTextField.setColumns(10);
		}
		{
			JLabel lblNewLabel_3 = new JLabel(Messages.getString("filterDialog.label.dst_port")); //$NON-NLS-1$
			contentPanel.add(lblNewLabel_3, "cell 0 3,alignx trailing");
		}
		{
			dstPortTextField = new JTextField();
			contentPanel.add(dstPortTextField, "cell 1 3,growx");
			dstPortTextField.setColumns(10);
		}
		{
			JLabel lblNewLabel_4 = new JLabel(Messages.getString("filterDialog.label.protocal")); //$NON-NLS-1$
			contentPanel.add(lblNewLabel_4, "cell 0 4,alignx trailing");
		}
		{
			comboBox = new JComboBox();
			contentPanel.add(comboBox, "cell 1 4,growx");
			comboBox.addItem("");
			for (ProtocalType pro : ProtocalType.values()) {
				comboBox.addItem(pro.name());
			}
//			comboBox.addItem(ProtocalFilter.getProtocalById(ProtocalFilter.IP_PROTOL));
//			comboBox.addItem(ProtocalFilter.getProtocalById(ProtocalFilter.ARP_PROTOL));
//			comboBox.addItem(ProtocalFilter.getProtocalById(ProtocalFilter.ICMP_PROTOL));
//			comboBox.addItem(ProtocalFilter.getProtocalById(ProtocalFilter.IGMP_PROTOL));
//			comboBox.addItem(ProtocalFilter.getProtocalById(ProtocalFilter.TCP_PROTOL));
//			comboBox.addItem(ProtocalFilter.getProtocalById(ProtocalFilter.UDP_PROTOL));
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton(Messages.getString("util.ui.ok"));
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton(Messages.getString("util.ui.cancel"));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
