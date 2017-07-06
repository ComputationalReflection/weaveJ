package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import utilsClass.Utils;

public class DialogSelectShape extends JDialog {

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
	class ActionListenerOK implements ActionListener{
		
		JDialog dialog = null;
		
		public ActionListenerOK(JDialog dialog){
			this.dialog = dialog;
		}
	
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			int selection = jList_Shapes.getSelectedIndex(); 
			if(selection != -1){
				actions(selection);
				dialog.setVisible(false);
			}
			else{
				JOptionPane.showMessageDialog(dialog, "select a shape...","", JOptionPane.INFORMATION_MESSAGE);
			}
		}

		public void actions(int selection){
			//int s = getIdSelection(selection);
			int s = Utils.getIdSelection(jList_Shapes,selection);

			((MainWindow) dialog.getParent()).getLogical().setSelection(s);
			if(action.equals("delete")){
				((MainWindow) dialog.getParent()).setMessageDelete(sps[selection]);
				((MainWindow) dialog.getParent()).setEnabledControlsDelete();
			}
			else if(action.equals("move")){
				((MainWindow) dialog.getParent()).setMessageMove(sps[selection]);
				((MainWindow) dialog.getParent()).setEnabledControlsMove();
			}
			else if(action.equals("scale")){
				((MainWindow) dialog.getParent()).setMessageScale(sps[selection]);
				((MainWindow) dialog.getParent()).setEnabledControlsScale();
			}
		}
		
	}
	private static final long serialVersionUID = 1L;
	private String action = null;
	private JButton jButton_Cancel = null;
	private JButton jButton_OK = null;
	private JPanel jContentPane = null;
	private JList jList_Shapes = null;
	private JPanel jPanel_OK = null;
	private JPanel jPanel_Select = null;
	
	
	private int n = 0;
	
		
	
	
	private String sps[] = null;

	/**
	 * @param owner
	 */
	public DialogSelectShape(MainWindow owner,String[] sps,String action) {
		super(owner);
		this.sps = sps;
		this.action = action;
		//owner.setSelection(-1);
		owner.getLogical().setSelection(-1);
		initialize();
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
			//jContentPane.add(new JScrollPane(jList_Shapes));
		}
		return jContentPane;
	}

	
	
	
	/**
	 * This method initializes jList_Shapes	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getJList_Shapes() {
		if (jList_Shapes == null) {
			if(sps.length==0){
				jList_Shapes = new JList();
			}
			else jList_Shapes = new JList(sps);
			jList_Shapes.setBounds(new Rectangle(9, 11, 528, 196));
			jList_Shapes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		return jList_Shapes;
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
			jPanel_OK.setBounds(new Rectangle(-2, 211, 556, 60));
			jPanel_OK.add(getJButton_OK(), null);
			jPanel_OK.add(getJButton_Cancel(), null);
		}
		return jPanel_OK;
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
		jPanel_Select.setBounds(new Rectangle(1, -2, 547, 212));
		jPanel_Select.add(getJList_Shapes(), null);
	}
	return jPanel_Select;
}
	
	
	

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(559, 304);
		this.setTitle("Select a shape...");
		this.setContentPane(getJContentPane());
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
