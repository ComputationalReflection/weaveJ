package main;

import javax.swing.JPanel;
import javax.swing.JDialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;

import controller.Logical;
import shapesPackage.Shapes;

import java.awt.Point;

public class DialogSelectDemo extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel jPanel_Select = null;
	private JPanel jPanel_OK = null;
	private JList jList_Demos = null;
	private JButton jButton_OK = null;
	private JButton jButton_Cancel = null;

	/**
	 * @param owner
	 */
	public DialogSelectDemo(MainWindow owner) {
		super(owner);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(559, 504);
		this.setTitle("Select a shape...");
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJPanel_Select(), null);
			jContentPane.add(getJPanel_OK(), null);
			
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel_Select	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel_Select() {
		if (jPanel_Select == null) {
			jPanel_Select = new JPanel();
			jPanel_Select.setLayout(null);
			jPanel_Select.setBounds(new Rectangle(1, -2, 547, 412));
			jPanel_Select.add(getJList_Demos(), null);
		}
		return jPanel_Select;
	}

	/**
	 * This method initializes jPanel_OK	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel_OK() {
		if (jPanel_OK == null) {
			jPanel_OK = new JPanel();
			jPanel_OK.setLayout(null);
			jPanel_OK.setBounds(new Rectangle(-2, 211, 556, 160));
			jPanel_OK.add(getJButton_OK(), null);
			jPanel_OK.add(getJButton_Cancel(), null);
		}
		return jPanel_OK;
	}

	/**
	 * This method initializes jList_Shapes	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getJList_Demos() {
		String sps[] = ((MainWindow)this.getParent()).getLogical().getDemos();
		
		if (jList_Demos == null) {
			if(sps.length==0){
				jList_Demos = new JList();
			}
			else jList_Demos = new JList(sps);
			jList_Demos.setBounds(new Rectangle(8, 11, 528, 196));
			jList_Demos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		//javax.swing.JScrollPane barra = new javax.swing.JScrollPane(jList_Demos);
		//jPanel_Select.add(barra, BorderLayout.CENTER);
		return jList_Demos;
	}

	
	
	
	class ActionListenerOK implements ActionListener{
		
		JDialog dialog = null;
		
		public ActionListenerOK(JDialog dialog){
			this.dialog = dialog;
		}

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			int selection = jList_Demos.getSelectedIndex();
			if(selection != -1){
				String cadena = (String)jList_Demos.getSelectedValue();
				dialog.setVisible(false);
				Logical logical = new Logical();
				logical.loadDemo(cadena);	
			}
			else{
				JOptionPane.showMessageDialog(dialog, "select a demo...","", JOptionPane.INFORMATION_MESSAGE);
			}
			
		}
		
	}
	
	
	
	
	/**
	 * This method initializes jButton_OK	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_OK() {
		if (jButton_OK == null) {
			jButton_OK = new JButton();
			jButton_OK.setText("OK");
			jButton_OK.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 10));
			jButton_OK.setForeground(new Color(102, 102, 102));
			jButton_OK.setSize(new Dimension(79, 29));
			jButton_OK.setLocation(new Point(380, 18));
			jButton_OK.addActionListener(new ActionListenerOK(this));
		}
		return jButton_OK;
	}



	
	
	
class ActionListenerCancel implements ActionListener{
		
		JDialog dialog = null;
		
		public ActionListenerCancel(JDialog dialog){
			this.dialog = dialog;
		}
		
		
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			dialog.setVisible(false);
		}
		
	}
	
	
	

	/**
	 * This method initializes jButton_Cancel	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_Cancel() {
		if (jButton_Cancel == null) {
			jButton_Cancel = new JButton();
			jButton_Cancel.setText("Cancel");
			jButton_Cancel.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 10));
			jButton_Cancel.setForeground(new Color(102, 102, 102));
			jButton_Cancel.setLocation(new Point(464, 18));
			jButton_Cancel.setSize(new Dimension(79, 29));
			jButton_Cancel.addActionListener(new ActionListenerCancel(this));
		}
		return jButton_Cancel;
	}

}
