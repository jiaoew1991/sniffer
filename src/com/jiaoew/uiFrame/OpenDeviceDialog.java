package com.jiaoew.uiFrame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSeparator;

public class OpenDeviceDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1533689843684102868L;
	private final JPanel contentPanel = new JPanel();
	JComboBox comboBox;
	JButton okButton;
	JButton cancelButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
////			OpenDeviceDialog dialog = new OpenDeviceDialog();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public OpenDeviceDialog(JFrame parentFrame, boolean modal) {
		super(parentFrame, modal);
		setBounds(100, 100, 320, 240);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[grow]", "[][][][][]"));
		{
			JLabel lblNewLabel = new JLabel(Messages.getString("openDeviceDialog.label.choose_device"));
			contentPanel.add(lblNewLabel, "cell 0 0");
		}
		{
			comboBox = new JComboBox();
			contentPanel.add(comboBox, "cell 0 1,growx");
		}
		{
			JSeparator separator = new JSeparator();
			contentPanel.add(separator, "cell 0 2");
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
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}


}
