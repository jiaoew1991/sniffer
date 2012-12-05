package com.jiaoew.uiFrame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class SearchKeyDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5176628831221955173L;
	private final JPanel contentPanel = new JPanel();
	JTextField textField;
	JButton okButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
//			SearchKeyDialog dialog = new SearchKeyDialog();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public SearchKeyDialog(JFrame owner, boolean modal) {
		super(owner, modal);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[][grow]", "[]"));
		{
			JLabel lblNewLabel = new JLabel(Messages.getString("searchkeyDialog.label.search")); //$NON-NLS-1$
			contentPanel.add(lblNewLabel, "cell 0 0,alignx trailing");
		}
		{
			textField = new JTextField();
			contentPanel.add(textField, "cell 1 0,growx");
			textField.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						SearchKeyDialog.this.dispose();
					}
				});
				buttonPane.add(cancelButton);
			}
		}
	}

}
