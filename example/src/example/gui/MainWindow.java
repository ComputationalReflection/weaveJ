package example.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;


import java.awt.Dimension;

import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;

public class MainWindow extends JFrame {

	private JPanel contentPane;
	private JLabel lblPackage;
	private JTextField txtExamplecomponent;
	private JLabel lblClass;
	private JComboBox comboBoxClass;
	private JLabel lblMethod;
	private JComboBox comboBoxMethod;
	private JPanel panelTrace;
	private JButton btnTrace;
	private JComboBox comboTrace;
	private JScrollPane scrollTrace;
	private JList listTrace;
	private JButton btnRemoveTrace;
	private JPanel panelProfile;
	private JButton btnProfile;
	private JScrollPane scrollProfile;
	private JButton btnRemoveProfile;
	private JList listProfile;
	private JPanel panel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setTitle("Runtime Logging and Profiling Aspectization");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 855, 280);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getPanelTrace());
		contentPane.add(getPanelProfile());
		contentPane.add(getPanel());
		setResizable(false);

		for(String line:GUItoFileController.getFileLines())
			if(line.contains("["))
				((DefaultListModel<String>) listTrace.getModel()).addElement(line);
			else
				((DefaultListModel<String>) listProfile.getModel()).addElement(line);
	}

	private JLabel getLblPackage() {
		if (lblPackage == null) {
			lblPackage = new JLabel("Package:");
			lblPackage.setLabelFor(getTxtExamplecomponent());
			lblPackage.setDisplayedMnemonic('k');
			lblPackage.setBounds(X_1_SETTINGS, 42, 72, 29);
			//lblPackage.setFont(new Font("Tahoma", Font.PLAIN, 16));
		}
		return lblPackage;
	}

	private static int X_1_SETTINGS = 20;
	private static int X_2_SETTINGS = 90;
	private static int X_3_SETTINGS = 195;
	private static final int Y_3_SETTINGS = 180;
	
	private JTextField getTxtExamplecomponent() {
		if (txtExamplecomponent == null) {
			txtExamplecomponent = new JTextField();
			txtExamplecomponent.setBounds(X_2_SETTINGS, 42, X_3_SETTINGS, 26);
			txtExamplecomponent.setText("example/component");
			txtExamplecomponent.setColumns(10);
		}
		return txtExamplecomponent;
	}

	private JLabel getLblClass() {
		if (lblClass == null) {
			lblClass = new JLabel("Class:");
			lblClass.setLabelFor(getComboBoxClass());
			lblClass.setDisplayedMnemonic('c');
			lblClass.setBounds(X_1_SETTINGS, 110 /*124*/, 72, 29);
			//lblClass.setFont(new Font("Tahoma", Font.PLAIN, 16));
		}
		return lblClass;
	}

	private JComboBox getComboBoxClass() {
		if (comboBoxClass == null) {
			comboBoxClass = new JComboBox();
			comboBoxClass.setBounds(X_2_SETTINGS, 110 /*124*/,  X_3_SETTINGS, 26);
			comboBoxClass.setModel(new DefaultComboBoxModel(new String[] { "CreditCard" }));
		}
		return comboBoxClass;
	}
	
	private JLabel getLblMethod() {
		if (lblMethod == null) {
			lblMethod = new JLabel("Member:");
			lblMethod.setLabelFor(getComboBoxMethod());
			lblMethod.setDisplayedMnemonic('m');
			lblMethod.setBounds(X_1_SETTINGS, Y_3_SETTINGS, 80, 29);
			//lblMethod.setFont(new Font("Tahoma", Font.PLAIN, 16));
		}
		return lblMethod;
	}

	private JComboBox getComboBoxMethod() {
		if (comboBoxMethod == null) {
			comboBoxMethod = new JComboBox();
			comboBoxMethod.setBounds(X_2_SETTINGS, Y_3_SETTINGS,  X_3_SETTINGS, 26);
			comboBoxMethod.setModel(new DefaultComboBoxModel(new String[] { "deposit(double):double", "withdraw(double):double" }));
		}
		return comboBoxMethod;
	}

	private JPanel getPanelTrace() {
		if (panelTrace == null) {
			panelTrace = new JPanel();
			panelTrace.setBorder(new TitledBorder(null, "Logging", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panelTrace.setBounds(329, 11, 517, 115);
			panelTrace.setLayout(null);
			panelTrace.add(getBtnTrace());
			panelTrace.add(getComboTrace());
			panelTrace.add(getScrollTrace());
			panelTrace.add(getBtnRemoveTrace());
		}
		return panelTrace;
	}

	private JButton getBtnTrace() {
		if (btnTrace == null) {
			btnTrace = new JButton("Log   ");
			btnTrace.setIcon(new ImageIcon(MainWindow.class.getResource(ARROW_BUTTON)));
			btnTrace.setMnemonic('l');
			btnTrace.setBounds(10, 22, 136, 35);
			btnTrace.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					String summary = GUItoFileController.addTraceAspect(comboBoxMethod.getSelectedItem().toString(),
							comboTrace.getSelectedIndex() == 0);
					((DefaultListModel<String>) listTrace.getModel()).addElement(summary);
				}
			});
			btnTrace.setFont(new Font("Tahoma", Font.PLAIN, 14));
			btnTrace.setHorizontalTextPosition(SwingConstants.LEFT);
		}
		return btnTrace;
	}

	private JComboBox getComboTrace() {
		if (comboTrace == null) {
			comboTrace = new JComboBox();
			comboTrace.setFont(new Font("Tahoma", Font.PLAIN, 13));
			comboTrace.setBounds(10, 68, 136, 26);
			comboTrace.setModel(new DefaultComboBoxModel(new String[] { "Before", "After" }));
		}
		return comboTrace;
	}

	private JScrollPane getScrollTrace() {
		if (scrollTrace == null) {
			scrollTrace = new JScrollPane();
			scrollTrace.setBounds(180, 15, 283, 93);
			scrollTrace.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scrollTrace.setViewportView(getListTrace());
			scrollTrace.setMaximumSize(new Dimension(100, 50));
		}
		return scrollTrace;
	}

	private JList getListTrace() {
		if (listTrace == null) {
			listTrace = new JList();
			DefaultListModel<String> model = new DefaultListModel<String>();
			listTrace.setModel(model);
		}
		return listTrace;
	}

	private JButton getBtnRemoveTrace() {
		if (btnRemoveTrace == null) {
			btnRemoveTrace = new JButton("");
			btnRemoveTrace.setIcon(new ImageIcon(MainWindow.class.getResource(TRASH_BUTTON)));
			btnRemoveTrace.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					GUItoFileController.removeTraceIndexes(listTrace.getSelectedIndices());

					int counter = 0;
					for (int index : listTrace.getSelectedIndices())
						((DefaultListModel<String>) listTrace.getModel()).remove(index-counter++);

				}
			});
			btnRemoveTrace.setFocusPainted(false);
			btnRemoveTrace.setBounds(473, 40, 33, 33);
		}
		return btnRemoveTrace;
	}

	
	private JPanel getPanelProfile() {
		if (panelProfile == null) {
			panelProfile = new JPanel();
			panelProfile.setBorder(new TitledBorder(null, "Profiling", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panelProfile.setLayout(null);
			panelProfile.setBounds(329, /*158*/ 135, 517, 115);
			panelProfile.add(getBtnProfile());
			panelProfile.add(getScrollPane_1_1());
			panelProfile.add(getBtnRemoveProfile());
		}
		return panelProfile;
	}

	private JButton getBtnProfile() {
		if (btnProfile == null) {
			btnProfile = new JButton("Profile  ");
			btnProfile.setIcon(new ImageIcon(MainWindow.class.getResource(ARROW_BUTTON)));
			btnProfile.setMnemonic('p');
			btnProfile.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					String summary = GUItoFileController.addProfileAspect(comboBoxMethod.getSelectedItem().toString());
					((DefaultListModel<String>) listProfile.getModel()).addElement(summary);
				}
			});
			btnProfile.setHorizontalTextPosition(SwingConstants.LEFT);
			btnProfile.setFont(new Font("Tahoma", Font.PLAIN, 14));
			btnProfile.setBounds(10, 35, 136, 35);
		}
		return btnProfile;
	}

	private JScrollPane getScrollPane_1_1() {
		if (scrollProfile == null) {
			scrollProfile = new JScrollPane();
			scrollProfile.setMaximumSize(new Dimension(100, 50));
			scrollProfile.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scrollProfile.setBounds(182, 15, 283, 93);
			scrollProfile.setViewportView(getListProfile());
		}
		return scrollProfile;
	}

	private final static String TRASH_BUTTON = "/icons/trash.png";
	private final static String ARROW_BUTTON = "/icons/arrow.png";
	
	private JButton getBtnRemoveProfile() {
		if (btnRemoveProfile == null) {
			btnRemoveProfile = new JButton("");
			btnRemoveProfile.setIcon(new ImageIcon(MainWindow.class.getResource(TRASH_BUTTON)));
			btnRemoveProfile.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					GUItoFileController.removeProfileIndexes(listProfile.getSelectedIndices());

					int counter = 0;
					for (int index : listProfile.getSelectedIndices())
						((DefaultListModel<String>) listProfile.getModel()).remove(index-counter++);
					
				}
			});
			btnRemoveProfile.setFocusPainted(false);
			btnRemoveProfile.setBounds(473, 40, 33, 33);
		}
		return btnRemoveProfile;
	}

	private JList getListProfile() {
		if (listProfile == null) {
			listProfile = new JList();
			DefaultListModel<String> model = new DefaultListModel<String>();
			listProfile.setModel(model);
		}
		return listProfile;
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "Settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel.setBounds(10, 11, 306, 239 /*262*/);
			panel.setLayout(null);
			panel.add(getLblPackage());
			panel.add(getTxtExamplecomponent());
			panel.add(getLblClass());
			panel.add(getComboBoxClass());
			panel.add(getLblMethod());
			panel.add(getComboBoxMethod());
		}
		return panel;
	}
}
