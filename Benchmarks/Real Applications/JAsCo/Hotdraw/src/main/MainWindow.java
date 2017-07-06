package main;


import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Rectangle;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Point;
import javax.swing.JToolBar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.CardLayout;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;


import java.awt.Font;

import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.border.BevelBorder;


import controller.Controller;
import controller.Logical;

import javax.swing.JTextArea;

import paintCanvas.PanelPrincipal;
import shapesPackage.Shape;
import shapesPackage.Shapes;

import java.awt.ComponentOrientation;
import java.awt.Toolkit;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null; // @jve:decl-index=0:visual-constraint="10,10"
	private JToolBar jToolBar_Options = null;
	private JButton jButton_Insert = null;
	private JButton jButton_Delete = null;
	private JButton jButton_Move = null;
	private JButton jButton_Scale = null;
	private JButton jButton_Close = null;
	private JPanel jPanel_Presentacion = null;
	private CardLayout myCardLayout = null;
	private CardLayout myCardLayoutScale = null;
	private JPanel jPanel_Insert = null;
	private JPanel jPanel_Principal = null;
	private JRadioButton jRadioButton_Rectangle = null;
	private JRadioButton jRadioButton_Circle = null;
	private JPanel jPanel_Rectangle = null;
	private JPanel jPanel_Triangle = null;
	private JPanel jPanel_Circle = null;
	private JLabel jLabel = null;
	private JPanel jPanel_comun_datas = null;
	private JLabel jLabel1_x = null;
	private JTextField jTextField_x = null;
	private JLabel jLabel1_y = null;
	private JTextField jTextField_y = null;
	private JLabel jLabel1_width = null;
	private JTextField jTextField_width = null;
	private JLabel jLabel1_height = null;
	private JTextField jTextField_height = null;
	private JLabel jLabel1_radius = null;
	private JTextField jTextField_radius = null;
	private JLabel jLabel1_side = null;
	private JTextField jTextField_side = null;
	private JPanel jPanel_Welcome = null;
	private JLabel jLabel1_Welcome = null;
	private JRadioButton jRadioButton_Triangle = null;
	private JPanel jPanel_Delete = null;
	private JPanel jPanel_Move = null;
	private JPanel jPanel_Selection_Move = null;
	private JLabel jLabel1_select_Shape_Move = null;
	private JButton jButton_Select_Move = null;
	private JPanel jPanel_Move_Shape = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JTextField jTextField_new_x = null;
	private JLabel jLabel3 = null;
	private JTextField jTextField_new_y = null;
	private JButton jButton_Move_Shape = null;
	private JButton jButton_Insert_Shape = null;
	private ButtonGroup buttons = null; // @jve:decl-index=0:
	private ButtonGroup buttonGroupDrawIn = null; // @jve:decl-index=0:
	private JPanel jPanel_Radios_Draw = null;
	private JRadioButton jRadioButton_Console = null;
	private JRadioButton jRadioButton_Canvas = null;
	private JRadioButton jRadioButton_Both = null;
	private JTextField jTextField_color2 = null;
	private JButton jButton_Color = null;
	private Color color = Color.red;
	// private int selection = -1;
	private JPanel jPanel_Select_Shape_Delete = null;
	private JLabel jLabel4 = null;
	private JButton jButton_select_Shape_Delete = null;
	private JPanel jPanel_Delete_Shape = null;
	private JTextArea jTextArea_Shape_Delete = null;
	private JButton jButton_Delete_Shape = null;
	private JTextArea jTextArea_Shape_Move = null;
	private String action = null; // @jve:decl-index=0:
	private JPanel jPanel_Scale = null;
	private JPanel jPanel_Selection_Scale = null;
	private JLabel jLabel5 = null;
	private JButton jButton_Select_Scale = null;
	private JPanel jPanel_Scale_Shape = null;
	private JTextArea jTextArea_Shape_Scale = null;
	private JPanel jPanel_What_Shape_Scale = null;
	private JButton jButton_Scale_Circle = null;
	private JPanel jPanel_Scale_Rectangle = null;
	private JLabel jLabel6 = null;
	private JLabel jLabel7_new_width = null;
	private JTextField jTextField_new_width = null;
	private JLabel jLabel7_new_height = null;
	private JTextField jTextField_new_height = null;
	private JPanel jPanel_Scale_Circle = null;
	private JLabel jLabel7 = null;
	private JLabel jLabel8 = null;
	private JTextField jTextField_new_radius = null;
	private JPanel jPanel_Scale_Triangle = null;
	private JLabel jLabel9 = null;
	private JLabel jLabel10 = null;
	private JTextField jTextField_new_side = null;
	private JButton jButton_Scale_Triangle = null;
	private JButton jButton_Scale_Rectangle = null;
	private JPanel jPanel_Scale_RCT = null;
	private JButton jButton_Demo = null;
	private JPanel jPanel_Checks = null;
	private JCheckBox jCheckBox_Logger = null;
	private JCheckBox jCheckBox_Profiler = null;

	private Logical logical = new Logical(); // @jve:decl-index=0:

	public void close() {
		System.exit(0);
	}

	public Logical getLogical() {
		return logical;
	}

	/**
	 * This method initializes jToolBar_Options
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar_Options() {
		if (jToolBar_Options == null) {
			jToolBar_Options = new JToolBar();
			jToolBar_Options.setName("jToolBar_Options");
			jToolBar_Options.setPreferredSize(new Dimension(537, 50));
			jToolBar_Options.add(getJPanel_Radios_Draw());
			jToolBar_Options.add(getJPanel_Checks());
			jToolBar_Options.add(getJButton_Insert());
			jToolBar_Options.add(getJButton_Delete());
			jToolBar_Options.add(getJButton_Move());
			jToolBar_Options.add(getJButton_Scale());
			jToolBar_Options.add(getJButton_Demo());
			jToolBar_Options.add(getJButton_Close());
			buttonGroupDrawIn = new ButtonGroup();
			buttonGroupDrawIn.add(jRadioButton_Console);
			buttonGroupDrawIn.add(jRadioButton_Canvas);
			buttonGroupDrawIn.add(jRadioButton_Both);

		}
		return jToolBar_Options;
	}

	class ActionListenerInsert implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			clearFormInsert();
			myCardLayout.show(jPanel_Presentacion, getJPanel_Insert().getName());
		}
	}

	/**
	 * This method initializes jButton_Insert
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton_Insert() {
		if (jButton_Insert == null) {
			jButton_Insert = new JButton();
			jButton_Insert.addActionListener(new ActionListenerInsert());
			jButton_Insert.setName("Button_Insert");
			jButton_Insert.setToolTipText("Insert a new shape");
			jButton_Insert.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 12));
			jButton_Insert.setForeground(new Color(102, 102, 102));
			jButton_Insert.setEnabled(false);
			jButton_Insert.setText("Insert");
		}
		return jButton_Insert;
	}

	public void disabledControlsDelete() {
		jButton_Delete_Shape.setEnabled(false);
		jTextArea_Shape_Delete.setEnabled(false);
		jTextArea_Shape_Delete.setText("");
	}

	class ActionListenerDelete implements ActionListener {
		MainWindow p = null;

		public ActionListenerDelete(MainWindow p) {
			this.p = p;
		}

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			p.disabledControlsDelete();
			myCardLayout.show(jPanel_Presentacion, getJPanel_Delete().getName());
		}
	}

	/**
	 * This method initializes jButton_Delete
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton_Delete() {
		if (jButton_Delete == null) {
			jButton_Delete = new JButton();
			jButton_Delete.setName("Button_Delete");
			jButton_Delete.setToolTipText("Delete a shape");
			jButton_Delete.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 12));
			jButton_Delete.setForeground(new Color(102, 102, 102));
			jButton_Delete.setEnabled(false);
			jButton_Delete.setText("Delete");
			jButton_Delete.addActionListener(new ActionListenerDelete(this));
		}
		return jButton_Delete;
	}

	public void disabledControlsMove() {
		jTextArea_Shape_Move.setEnabled(false);
		jButton_Move_Shape.setEnabled(false);
		jTextField_new_x.setEnabled(false);
		jTextField_new_y.setEnabled(false);
		jTextArea_Shape_Move.setText("");
		jTextField_new_x.setText("");
		jTextField_new_y.setText("");
	}

	public void disabledControlsScale() {
		jTextArea_Shape_Scale.setEnabled(false);
		jTextArea_Shape_Scale.setText("");
		// deshabilitamos el escalado del rectángulo
		jTextField_new_width.setEnabled(false);
		jTextField_new_height.setEnabled(false);
		jTextField_new_width.setText("");
		jTextField_new_height.setText("");
		jButton_Scale_Rectangle.setEnabled(false);
		// deshabilitamos el escalado del círculo
		jTextField_new_radius.setEnabled(false);
		jTextField_new_radius.setText("");
		jButton_Scale_Circle.setEnabled(false);
		// deshabilitamos el escalado del triángulo
		jTextField_new_side.setEnabled(false);
		jTextField_new_side.setText("");
		jButton_Scale_Triangle.setEnabled(false);

		// mostramos el panel vacio
		myCardLayoutScale.show(jPanel_What_Shape_Scale, getJPanel_Scale_RCT().getName());
	}

	class ActionListenerMove implements ActionListener {
		MainWindow p = null;

		public ActionListenerMove(MainWindow p) {
			this.p = p;
		}

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			p.disabledControlsMove();
			myCardLayout.show(jPanel_Presentacion, getJPanel_Move().getName());
		}
	}

	/**
	 * This method initializes jButton_Move
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton_Move() {
		if (jButton_Move == null) {
			jButton_Move = new JButton();
			jButton_Move.setName("Button_Move");
			jButton_Move.setToolTipText("Move a shape");
			jButton_Move.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 12));
			jButton_Move.setForeground(new Color(102, 102, 102));
			jButton_Move.setEnabled(false);
			jButton_Move.setText("Move");
			jButton_Move.addActionListener(new ActionListenerMove(this));
		}
		return jButton_Move;
	}

	class ActionListenerScale implements ActionListener {
		MainWindow p = null;

		public ActionListenerScale(MainWindow p) {
			this.p = p;
		}

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			p.disabledControlsScale();
			myCardLayout.show(jPanel_Presentacion, getJPanel_Scale().getName());
		}
	}

	/**
	 * This method initializes jButton_Scale
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton_Scale() {
		if (jButton_Scale == null) {
			jButton_Scale = new JButton();
			jButton_Scale.setName("Button_Scale");
			jButton_Scale.setToolTipText("Scale a shape");
			jButton_Scale.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 12));
			jButton_Scale.setForeground(new Color(102, 102, 102));
			jButton_Scale.setEnabled(false);
			jButton_Scale.setText("Scale");
			jButton_Scale.addActionListener(new ActionListenerScale(this));
		}
		return jButton_Scale;
	}

	public void demo() {
		myCardLayout.show(jPanel_Presentacion, getJPanel_Welcome().getName());
		logical.clearCanvas();

		disabledControlsDelete();
		disabledControlsMove();
		disabledControlsScale();

		DialogSelectDemo dsd = new DialogSelectDemo(this);
		dsd.setVisible(true);

		jButton_Insert.setEnabled(true);
		jButton_Delete.setEnabled(true);
		jButton_Move.setEnabled(true);
		jButton_Scale.setEnabled(true);
	}

	class ActionListenerDemo implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			Runnable miRunnable = new Runnable() {
				public void run() {
					try {
						demo();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			Thread hilo = new Thread(miRunnable);
			hilo.start();
		}
	}

	/**
	 * This method initializes jButton_Demo
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton_Demo() {
		if (jButton_Demo == null) {
			jButton_Demo = new JButton();
			jButton_Demo.setText("Demo");
			jButton_Demo.setToolTipText("Demo");
			jButton_Demo.setEnabled(false);
			jButton_Demo.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 12));
			jButton_Demo.setForeground(new Color(102, 102, 102));
			jButton_Demo.addActionListener(new ActionListenerDemo());
		}
		return jButton_Demo;
	}

	class ActionListenerClose implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			close();
		}
	}

	/**
	 * This method initializes jButton_Close
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton_Close() {
		if (jButton_Close == null) {
			jButton_Close = new JButton();
			jButton_Close.setName("Button_Close");
			jButton_Close.setToolTipText("Finish application");
			jButton_Close.setHorizontalAlignment(SwingConstants.RIGHT);
			jButton_Close.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 12));
			jButton_Close.setForeground(new Color(102, 102, 102));
			jButton_Close.setText("Close");
			jButton_Close.addActionListener(new ActionListenerClose());
		}
		return jButton_Close;
	}

	/**
	 * This method initializes jPanel_Presentacion
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_Presentacion() {
		if (jPanel_Presentacion == null) {
			jPanel_Presentacion = new JPanel();
			jPanel_Presentacion.setPreferredSize(new Dimension(500, 400));
			myCardLayout = new CardLayout();
			myCardLayout.setHgap(0);
			myCardLayout.setVgap(0);
			jPanel_Presentacion.setLayout(myCardLayout);
			jPanel_Presentacion.add(getJPanel_Welcome(), getJPanel_Welcome().getName());
			jPanel_Presentacion.add(getJPanel_Insert(), getJPanel_Insert().getName());
			jPanel_Presentacion.add(getJPanel_Delete(), getJPanel_Delete().getName());
			jPanel_Presentacion.add(getJPanel_Move(), getJPanel_Move().getName());
			jPanel_Presentacion.add(getJPanel_Scale(), getJPanel_Scale().getName());
		}
		return jPanel_Presentacion;
	}

	/**
	 * This method initializes jPanel_Insert
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_Insert() {
		if (jPanel_Insert == null) {
			jLabel = new JLabel();
			jLabel.setText("Introduce shape`s datas that you want insert");
			jLabel.setLocation(new Point(38, 13));
			jLabel.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 12));
			jLabel.setForeground(new Color(102, 102, 102));
			jLabel.setSize(new Dimension(310, 16));
			jPanel_Insert = new JPanel();
			jPanel_Insert.setLayout(null);
			jPanel_Insert.setPreferredSize(new Dimension(230, 60));
			jPanel_Insert.setName("panel Insert");
			jPanel_Insert.setVisible(true);
			jPanel_Insert.setSize(416, 287);
			jPanel_Insert.add(getJRadioButton_Rectangle(), null);
			jPanel_Insert.add(getJRadioButton_Circle(), null);
			jPanel_Insert.add(getJRadioButton_Triangle(), null);
			buttons = new ButtonGroup();
			buttons.add(jRadioButton_Triangle);
			buttons.add(jRadioButton_Circle);
			buttons.add(jRadioButton_Rectangle);
			jPanel_Insert.add(getJPanel_Rectangle(), null);
			jPanel_Insert.add(getJPanel_Triangle(), null);
			jPanel_Insert.add(getJPanel_Circle(), null);
			jPanel_Insert.add(jLabel, null);
			jPanel_Insert.add(getJPanel_comun_datas(), null);
			jPanel_Insert.add(getJButton_Insert_Shape(), null);
		}
		return jPanel_Insert;
	}

	/**
	 * This method initializes jPanel_Principal
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_Principal() {
		if (jPanel_Principal == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(FlowLayout.CENTER);
			jPanel_Principal = new JPanel();
			jPanel_Principal.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			jPanel_Principal.setLayout(flowLayout);
			jPanel_Principal.setBackground(new Color(204, 204, 255));
			jPanel_Principal.add(getJPanel_Presentacion(), null);
		}
		return jPanel_Principal;
	}

	class ActionListenerTriangle implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			jLabel1_width.setEnabled(false);
			jTextField_width.setEnabled(false);
			jTextField_width.setText("");
			jLabel1_height.setEnabled(false);
			jTextField_height.setEnabled(false);
			jTextField_height.setText("");
			jLabel1_radius.setEnabled(false);
			jTextField_radius.setEnabled(false);
			jTextField_radius.setText("");
			jLabel1_side.setEnabled(true);
			jTextField_side.setEnabled(true);
			jTextField_side.setText("");
		}
	}

	/**
	 * This method initializes jRadioButton_Triangle
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton_Triangle() {
		if (jRadioButton_Triangle == null) {
			jRadioButton_Triangle = new JRadioButton();
			jRadioButton_Triangle.setText("Triangle");
			jRadioButton_Triangle.setLocation(new Point(39, 276));
			jRadioButton_Triangle.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 12));
			jRadioButton_Triangle.setForeground(new Color(102, 102, 102));
			jRadioButton_Triangle.setSize(new Dimension(99, 21));
			jRadioButton_Triangle.addActionListener(new ActionListenerTriangle());
		}
		return jRadioButton_Triangle;
	}

	class ActionListenerRectangle implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			jLabel1_width.setEnabled(true);
			jTextField_width.setEnabled(true);
			jTextField_width.setText("");
			jLabel1_height.setEnabled(true);
			jTextField_height.setEnabled(true);
			jTextField_height.setText("");
			jLabel1_radius.setEnabled(false);
			jTextField_radius.setEnabled(false);
			jTextField_radius.setText("");
			jLabel1_side.setEnabled(false);
			jTextField_side.setEnabled(false);
			jTextField_side.setText("");
		}
	}

	/**
	 * This method initializes jRadioButton_Rectangle
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton_Rectangle() {
		if (jRadioButton_Rectangle == null) {
			jRadioButton_Rectangle = new JRadioButton();
			jRadioButton_Rectangle.setBounds(new Rectangle(39, 141, 96, 21));
			jRadioButton_Rectangle.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 12));
			jRadioButton_Rectangle.setForeground(new Color(102, 102, 102));
			jRadioButton_Rectangle.setText("Rectangle");
			jRadioButton_Rectangle.addActionListener(new ActionListenerRectangle());
		}
		return jRadioButton_Rectangle;
	}

	class ActionListenerCircle implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			jLabel1_width.setEnabled(false);
			jTextField_width.setEnabled(false);
			jTextField_width.setText("");
			jLabel1_height.setEnabled(false);
			jTextField_height.setEnabled(false);
			jTextField_height.setText("");
			jLabel1_radius.setEnabled(true);
			jTextField_radius.setEnabled(true);
			jTextField_radius.setText("");
			jLabel1_side.setEnabled(false);
			jTextField_side.setEnabled(false);
			jTextField_side.setText("");
		}
	}

	/**
	 * This method initializes jRadioButton_Circle
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton_Circle() {
		if (jRadioButton_Circle == null) {
			jRadioButton_Circle = new JRadioButton();
			jRadioButton_Circle.setText("Circle");
			jRadioButton_Circle.setLocation(new Point(39, 213));
			jRadioButton_Circle.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 12));
			jRadioButton_Circle.setForeground(new Color(102, 102, 102));
			jRadioButton_Circle.setSize(new Dimension(91, 21));
			jRadioButton_Circle.addActionListener(new ActionListenerCircle());
		}
		return jRadioButton_Circle;
	}

	/**
	 * This method initializes jPanel_Rectangle
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_Rectangle() {
		if (jPanel_Rectangle == null) {
			jLabel1_height = new JLabel();
			jLabel1_height.setBounds(new Rectangle(121, 23, 50, 16));
			jLabel1_height.setEnabled(false);
			jLabel1_height.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 12));
			jLabel1_height.setForeground(new Color(102, 102, 102));
			jLabel1_height.setText("height");
			jLabel1_width = new JLabel();
			jLabel1_width.setBounds(new Rectangle(12, 22, 38, 16));
			jLabel1_width.setEnabled(false);
			jLabel1_width.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 12));
			jLabel1_width.setForeground(new Color(102, 102, 102));
			jLabel1_width.setText("width");
			jPanel_Rectangle = new JPanel();
			jPanel_Rectangle.setLayout(null);
			jPanel_Rectangle.setBounds(new Rectangle(174, 124, 239, 57));
			jPanel_Rectangle.setBorder(BorderFactory.createTitledBorder(null, "Rectangle",
					TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
					new Font("Franklin Gothic Heavy", Font.BOLD, 12), new Color(102, 102, 102)));
			jPanel_Rectangle.setFont(new Font("Franklin Gothic Heavy", Font.PLAIN, 12));
			jPanel_Rectangle.setForeground(new Color(102, 102, 102));
			jPanel_Rectangle.add(jLabel1_width, null);
			jPanel_Rectangle.add(getJTextField_width(), null);
			jPanel_Rectangle.add(jLabel1_height, null);
			jPanel_Rectangle.add(getJTextField_height(), null);
		}
		return jPanel_Rectangle;
	}

	/**
	 * This method initializes jPanel_Triangle
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_Triangle() {
		if (jPanel_Triangle == null) {
			jLabel1_side = new JLabel();
			jLabel1_side.setText("side");
			jLabel1_side.setLocation(new Point(12, 22));
			jLabel1_side.setEnabled(false);
			jLabel1_side.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 12));
			jLabel1_side.setForeground(new Color(102, 102, 102));
			jLabel1_side.setSize(new Dimension(38, 16));
			jPanel_Triangle = new JPanel();
			jPanel_Triangle.setLayout(null);
			jPanel_Triangle.setBorder(BorderFactory.createTitledBorder(null, "Triangle",
					TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
					new Font("Franklin Gothic Heavy", Font.BOLD, 12), new Color(102, 102, 102)));
			jPanel_Triangle.setLocation(new Point(172, 263));
			jPanel_Triangle.setSize(new Dimension(239, 57));
			jPanel_Triangle.setFont(new Font("Franklin Gothic Heavy", Font.PLAIN, 12));
			jPanel_Triangle.setForeground(new Color(102, 102, 102));
			jPanel_Triangle.add(jLabel1_side, null);
			jPanel_Triangle.add(getJTextField_side(), null);
		}
		return jPanel_Triangle;
	}

	/**
	 * This method initializes jPanel_Circle
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_Circle() {
		if (jPanel_Circle == null) {
			jLabel1_radius = new JLabel();
			jLabel1_radius.setText("radius");
			jLabel1_radius.setLocation(new Point(12, 22));
			jLabel1_radius.setEnabled(false);
			jLabel1_radius.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 12));
			jLabel1_radius.setForeground(new Color(102, 102, 102));
			jLabel1_radius.setSize(new Dimension(44, 16));
			jPanel_Circle = new JPanel();
			jPanel_Circle.setLayout(null);
			jPanel_Circle.setBorder(BorderFactory.createTitledBorder(null, "Circle", TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Franklin Gothic Heavy", Font.BOLD, 12),
					new Color(102, 102, 102)));
			jPanel_Circle.setLocation(new Point(173, 195));
			jPanel_Circle.setSize(new Dimension(239, 57));
			jPanel_Circle.setFont(new Font("Franklin Gothic Heavy", Font.PLAIN, 12));
			jPanel_Circle.setForeground(new Color(102, 102, 102));
			jPanel_Circle.add(jLabel1_radius, null);
			jPanel_Circle.add(getJTextField_radius(), null);
		}
		return jPanel_Circle;
	}

	/**
	 * This method initializes jPanel_comun_datas
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_comun_datas() {
		if (jPanel_comun_datas == null) {
			jLabel1_y = new JLabel();
			jLabel1_y.setText("y");
			jLabel1_y.setLocation(new Point(92, 22));
			jLabel1_y.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 12));
			jLabel1_y.setForeground(new Color(102, 102, 102));
			jLabel1_y.setSize(new Dimension(14, 16));
			jLabel1_x = new JLabel();
			jLabel1_x.setText("x");
			jLabel1_x.setLocation(new Point(15, 22));
			jLabel1_x.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 12));
			jLabel1_x.setForeground(new Color(102, 102, 102));
			jLabel1_x.setSize(new Dimension(15, 16));
			jPanel_comun_datas = new JPanel();
			jPanel_comun_datas.setLayout(null);
			jPanel_comun_datas.setBorder(BorderFactory.createTitledBorder(null, "comun datas to all the shapes",
					TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
					new Font("Franklin Gothic Heavy", Font.BOLD, 12), new Color(102, 102, 102)));
			jPanel_comun_datas.setLocation(new Point(38, 53));
			jPanel_comun_datas.setSize(new Dimension(376, 59));
			jPanel_comun_datas.add(jLabel1_x, null);
			jPanel_comun_datas.add(getJTextField_x(), null);
			jPanel_comun_datas.add(jLabel1_y, null);
			jPanel_comun_datas.add(getJTextField_y(), null);
			jPanel_comun_datas.add(getJTextField_color2(), null);
			jPanel_comun_datas.add(getJButton_Color(), null);
		}
		return jPanel_comun_datas;
	}

	/**
	 * This method initializes jTextField_x
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField_x() {
		if (jTextField_x == null) {
			jTextField_x = new JTextField();
			jTextField_x.setSize(new Dimension(52, 19));
			jTextField_x.setLocation(new Point(32, 22));
		}
		return jTextField_x;
	}

	/**
	 * This method initializes jTextField_y
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField_y() {
		if (jTextField_y == null) {
			jTextField_y = new JTextField();
			jTextField_y.setLocation(new Point(105, 22));
			jTextField_y.setSize(new Dimension(52, 19));
		}
		return jTextField_y;
	}

	/**
	 * This method initializes jTextField_width
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField_width() {
		if (jTextField_width == null) {
			jTextField_width = new JTextField();
			jTextField_width.setBounds(new Rectangle(54, 22, 59, 20));
			jTextField_width.setEnabled(false);
		}
		return jTextField_width;
	}

	/**
	 * This method initializes jTextField_height
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField_height() {
		if (jTextField_height == null) {
			jTextField_height = new JTextField();
			jTextField_height.setLocation(new Point(171, 22));
			jTextField_height.setEnabled(false);
			jTextField_height.setSize(new Dimension(59, 20));
		}
		return jTextField_height;
	}

	/**
	 * This method initializes jTextField_radius
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField_radius() {
		if (jTextField_radius == null) {
			jTextField_radius = new JTextField();
			jTextField_radius.setLocation(new Point(54, 22));
			jTextField_radius.setEnabled(false);
			jTextField_radius.setSize(new Dimension(59, 20));
		}
		return jTextField_radius;
	}

	/**
	 * This method initializes jTextField_side
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField_side() {
		if (jTextField_side == null) {
			jTextField_side = new JTextField();
			jTextField_side.setSize(new Dimension(59, 20));
			jTextField_side.setEnabled(false);
			jTextField_side.setLocation(new Point(54, 22));
		}
		return jTextField_side;
	}

	/**
	 * This method initializes jPanel_Welcome
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_Welcome() {
		if (jPanel_Welcome == null) {
			jLabel1_Welcome = new JLabel();
			jLabel1_Welcome.setBounds(new Rectangle(70, 159, 365, 28));
			jLabel1_Welcome.setFont(new Font("Felix Titling", Font.BOLD, 24));
			jLabel1_Welcome.setForeground(new Color(0, 0, 153));
			jLabel1_Welcome.setText("Working with aspects...");
			jPanel_Welcome = new JPanel();
			jPanel_Welcome.setLayout(null);
			jPanel_Welcome.setName("jPanel_Welcome");
			jPanel_Welcome.setPreferredSize(new Dimension(500, 400));
			jPanel_Welcome.add(jLabel1_Welcome, null);
		}
		return jPanel_Welcome;
	}

	/**
	 * This method initializes jPanel_Delete
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_Delete() {
		if (jPanel_Delete == null) {
			jPanel_Delete = new JPanel();
			jPanel_Delete.setLayout(null);
			jPanel_Delete.setName("jPanel");
			jPanel_Delete.add(getJPanel_Select_Shape_Delete(), null);
			jPanel_Delete.add(getJPanel_Delete_Shape(), null);
		}
		return jPanel_Delete;
	}

	public void setMessageDelete(String message) {
		jTextArea_Shape_Delete.setText("");
		jTextArea_Shape_Delete.setText(message);
	}

	public void setMessageMove(String message) {
		jTextArea_Shape_Move.setText("");
		jTextArea_Shape_Move.setText(message);

	}

	public void setMessageScale(String message) {
		jTextArea_Shape_Scale.setText("");
		jTextArea_Shape_Scale.setText(message);

		Shape s = logical.getShapeSelecction();
		if (s != null) {
			if (s.getClass().getName().equals("ShapesPackage.RectangleA")) {
				myCardLayoutScale.show(jPanel_What_Shape_Scale, jPanel_Scale_Rectangle.getName());
			} else if (s.getClass().getName().equals("ShapesPackage.CircleA")) {
				myCardLayoutScale.show(jPanel_What_Shape_Scale, jPanel_Scale_Circle.getName());
			} else if (s.getClass().getName().equals("ShapesPackage.TriangleA")) {
				myCardLayoutScale.show(jPanel_What_Shape_Scale, jPanel_Scale_Triangle.getName());
			}
		}

	}

	/**
	 * This method initializes jPanel_Move
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_Move() {
		if (jPanel_Move == null) {
			jPanel_Move = new JPanel();
			jPanel_Move.setLayout(null);
			jPanel_Move.setName("jPanel_Move");
			jPanel_Move.add(getJPanel_Selection_Move(), null);
			jPanel_Move.add(getJPanel_Move_Shape(), null);
		}
		return jPanel_Move;
	}

	/**
	 * This method initializes jPanel_Selection_Move
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_Selection_Move() {
		if (jPanel_Selection_Move == null) {
			jLabel1_select_Shape_Move = new JLabel();
			jLabel1_select_Shape_Move.setText("Select the shape that you want move");
			jLabel1_select_Shape_Move.setLocation(new Point(13, 21));
			jLabel1_select_Shape_Move.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 12));
			jLabel1_select_Shape_Move.setForeground(new Color(102, 102, 102));
			jLabel1_select_Shape_Move.setSize(new Dimension(239, 16));
			jPanel_Selection_Move = new JPanel();
			jPanel_Selection_Move.setLayout(null);
			jPanel_Selection_Move.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			jPanel_Selection_Move.setSize(new Dimension(437, 69));
			jPanel_Selection_Move.setLocation(new Point(37, 19));
			jPanel_Selection_Move.add(jLabel1_select_Shape_Move, null);
			jPanel_Selection_Move.add(getJButton_Select_Move(), null);
		}
		return jPanel_Selection_Move;
	}

	/**
	 * This method initializes jButton_Select_Move
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton_Select_Move() {
		if (jButton_Select_Move == null) {
			jButton_Select_Move = new JButton();
			jButton_Select_Move.setText("Select...");
			jButton_Select_Move.setSize(new Dimension(92, 26));
			jButton_Select_Move.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 12));
			jButton_Select_Move.setForeground(new Color(102, 102, 102));
			jButton_Select_Move.setLocation(new Point(320, 21));
			action = "move";
			jButton_Select_Move.addActionListener(new ActionListenerSelectShape(this, action));
		}
		return jButton_Select_Move;
	}

	/**
	 * This method initializes jPanel_Move_Shape
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_Move_Shape() {
		if (jPanel_Move_Shape == null) {
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(90, 119, 12, 16));
			jLabel3.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 12));
			jLabel3.setForeground(new Color(102, 102, 102));
			jLabel3.setText("y");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(11, 87, 277, 16));
			jLabel2.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 12));
			jLabel2.setForeground(new Color(102, 102, 102));
			jLabel2.setText("Introduce the new point for the shape");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(12, 118, 15, 16));
			jLabel1.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 12));
			jLabel1.setForeground(new Color(102, 102, 102));
			jLabel1.setText("x");
			jPanel_Move_Shape = new JPanel();
			jPanel_Move_Shape.setLayout(null);
			jPanel_Move_Shape.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			jPanel_Move_Shape.setSize(new Dimension(433, 207));
			jPanel_Move_Shape.setLocation(new Point(38, 91));
			jPanel_Move_Shape.add(jLabel1, null);
			jPanel_Move_Shape.add(jLabel2, null);
			jPanel_Move_Shape.add(jLabel3, null);
			jPanel_Move_Shape.add(getJTextField_new_y(), null);
			jPanel_Move_Shape.add(getJTextArea_Shape_Move(), null);
			jPanel_Move_Shape.add(getJButton_Move_Shape(), null);
			jPanel_Move_Shape.add(getJTextField_new_x(), null);
			jPanel_Move_Shape.setEnabled(false);
		}
		return jPanel_Move_Shape;
	}

	/**
	 * This method initializes jTextField_new_x
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField_new_x() {
		if (jTextField_new_x == null) {
			jTextField_new_x = new JTextField();
			jTextField_new_x.setLocation(new Point(29, 116));
			jTextField_new_x.setSize(new Dimension(52, 19));
			jTextField_new_x.setEnabled(false);
		}
		return jTextField_new_x;
	}

	/**
	 * This method initializes jTextField_new_y
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField_new_y() {
		if (jTextField_new_y == null) {
			jTextField_new_y = new JTextField();
			jTextField_new_y.setLocation(new Point(100, 117));
			jTextField_new_y.setSize(new Dimension(52, 19));
			jTextField_new_y.setEnabled(false);
		}
		return jTextField_new_y;
	}

	public String get_new_y() {
		return jTextField_new_y.getText();
	}

	public String get_new_x() {
		return jTextField_new_x.getText();
	}

	public String get_field_x() {
		return jTextField_x.getText();
	}

	public String get_field_y() {
		return jTextField_y.getText();
	}

	public String get_field_width() {
		return jTextField_width.getText();
	}

	public String get_field_height() {
		return jTextField_height.getText();
	}

	public String get_field_radius() {
		return jTextField_radius.getText();
	}

	public String get_field_side() {
		return jTextField_side.getText();
	}

	class ActionListenerMoveShape implements ActionListener {
		MainWindow p = null;

		public ActionListenerMoveShape(MainWindow p) {
			this.p = p;
		}

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			logical.listenerMoveShape(p);
			p.disabledControlsMove();
		}
	}

	/**
	 * This method initializes jButton_Move_Shape
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton_Move_Shape() {
		if (jButton_Move_Shape == null) {
			jButton_Move_Shape = new JButton();
			jButton_Move_Shape.setText("Move");
			jButton_Move_Shape.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 12));
			jButton_Move_Shape.setSize(new Dimension(92, 26));
			jButton_Move_Shape.setLocation(new Point(320, 172));
			jButton_Move_Shape.setForeground(new Color(102, 102, 102));
			jButton_Move_Shape.setEnabled(false);
			jButton_Move_Shape.addActionListener(new ActionListenerMoveShape(this));
		}
		return jButton_Move_Shape;
	}

	public void clearFormInsert() {
		jTextField_x.setText("");
		jTextField_y.setText("");
		jTextField_width.setText("");
		jTextField_height.setText("");
		jTextField_radius.setText("");
		jTextField_side.setText("");
	}

	/*
	 * Acción que se ejecuta cuando se clika sobre el botón Isertar del
	 * panel_Insert. Recoge todos los datos del formulario, y desencadena todo
	 * el proceso que añade la shape a la lista de shapes y la dibuja en el
	 * lienzo.
	 */
	class ActionListenerInsertShape implements ActionListener {
		MainWindow p = null;

		public ActionListenerInsertShape(MainWindow p) {
			this.p = p;
		}

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

			Map hash = null;

			if (buttons.isSelected(jRadioButton_Rectangle.getModel())) {
				hash = logical.introducDatasRec(0, p);
			} else if (buttons.isSelected(jRadioButton_Circle.getModel())) {
				hash = logical.introducDatasCir(1, p);
			} else if (buttons.isSelected(jRadioButton_Triangle.getModel())) {
				hash = logical.introducDatasTri(2, p);
			}

			if (hash != null) {
				Controller.createShape(hash);
				jButton_Delete.setEnabled(true);
				jButton_Move.setEnabled(true);
				jButton_Scale.setEnabled(true);
			} else {
				JOptionPane.showMessageDialog(p, "All fields should be full ", "", JOptionPane.INFORMATION_MESSAGE);
			}

			// limpiamos el formulario
			clearFormInsert();
		}
	}

	/**
	 * This method initializes jButton_Insert_Shape
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton_Insert_Shape() {
		if (jButton_Insert_Shape == null) {
			jButton_Insert_Shape = new JButton();
			jButton_Insert_Shape.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 12));
			jButton_Insert_Shape.setForeground(new Color(102, 102, 102));
			jButton_Insert_Shape.setLocation(new Point(329, 340));
			jButton_Insert_Shape.setSize(new Dimension(80, 34));
			jButton_Insert_Shape.setText("Insert");
			jButton_Insert_Shape.addActionListener(new ActionListenerInsertShape(this));
		}
		return jButton_Insert_Shape;
	}

	public static void mainCanvas() {
		// System.out.println("mainCanvas");
		PanelPrincipal panel = PanelPrincipal.getInstance();
		panel.setVisible(true);
		// System.out.println("ya visible");
	}

	/**
	 * This method initializes jPanel_Radios_Draw
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_Radios_Draw() {
		if (jPanel_Radios_Draw == null) {
			jPanel_Radios_Draw = new JPanel();
			jPanel_Radios_Draw.setLayout(null);
			jPanel_Radios_Draw.setSize(new Dimension(115, 46));
			jPanel_Radios_Draw.setPreferredSize(new Dimension(115, 46));
			jPanel_Radios_Draw.setBorder(BorderFactory.createTitledBorder(null, "Draw in",
					TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
					new Font("Franklin Gothic Heavy", Font.BOLD, 12), new Color(102, 102, 102)));
			jPanel_Radios_Draw.add(getJRadioButton_Console(), null);
			jPanel_Radios_Draw.add(getJRadioButton_Canvas(), null);
			jPanel_Radios_Draw.add(getJRadioButton_Both(), null);
		}
		return jPanel_Radios_Draw;
	}

	class ActionListenerRadios implements ActionListener {

		public void comprobationButtons() {
			// System.out.println("comprobationButtons");
			jButton_Insert.setEnabled(true);
			jButton_Demo.setEnabled(true);
			Controller controller = new Controller();
			List list = logical.shapesDrawIn(controller.getListShapes());
			// System.out.println("list.size: " + list.size());

			if (list.size() > 0) {
				jButton_Delete.setEnabled(true);
				jButton_Move.setEnabled(true);
				jButton_Scale.setEnabled(true);
			} else {
				jButton_Delete.setEnabled(false);
				jButton_Move.setEnabled(false);
				jButton_Scale.setEnabled(false);
			}

		}

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			// clearCanvas();
			int op = -1;
			String loadConnector = null;
			String removeConnector = null;

			if (buttonGroupDrawIn.isSelected(jRadioButton_Canvas.getModel())) {
				op = 1;
				// System.out.println("activando canvas");
				loadConnector = "src/aspect/ConnectorDrawCanvas.con";
				removeConnector = "ConnectorDrawConsole";
				logical.loadConnector(loadConnector);
				logical.removeConnector(removeConnector);
			} else if (buttonGroupDrawIn.isSelected(jRadioButton_Console.getModel())) {
				op = 2;
				loadConnector = "src/aspect/ConnectorDrawConsole.con";
				removeConnector = "ConnectorDrawCanvas";
				logical.loadConnector(loadConnector);
				logical.removeConnector(removeConnector);
			} else if (buttonGroupDrawIn.isSelected(jRadioButton_Both.getModel())) {
				op = 3;
				loadConnector = "src/aspect/ConnectorDrawCanvas.con";
				logical.loadConnector(loadConnector);
				loadConnector = "src/aspect/ConnectorDrawConsole.con";
				logical.loadConnector(loadConnector);
			}

			Shapes.getInstance().setPaintConsoleOrCanvas(op);

			if ((op == 1) || (op == 3)) { // canvas, both
				// System.out.println("abriendo canvas");
				mainCanvas();
			}

			comprobationButtons();
			// System.out.println("fin");
		}
	}

	/**
	 * This method initializes jRadioButton_Console
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton_Console() {
		if (jRadioButton_Console == null) {
			jRadioButton_Console = new JRadioButton();
			jRadioButton_Console.setText("Console");
			jRadioButton_Console.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 10));
			jRadioButton_Console.setForeground(new Color(102, 102, 102));
			jRadioButton_Console.setVerticalAlignment(SwingConstants.CENTER);
			jRadioButton_Console.setBounds(new Rectangle(11, 13, 71, 24));
			jRadioButton_Console.setHorizontalAlignment(SwingConstants.LEADING);
			jRadioButton_Console.addActionListener(new ActionListenerRadios());
		}
		return jRadioButton_Console;
	}

	/**
	 * This method initializes jRadioButton_Canvas
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton_Canvas() {
		if (jRadioButton_Canvas == null) {
			jRadioButton_Canvas = new JRadioButton();
			jRadioButton_Canvas.setText("Canvas");
			jRadioButton_Canvas.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 10));
			jRadioButton_Canvas.setForeground(new Color(102, 102, 102));
			jRadioButton_Canvas.setLocation(new Point(92, 13));
			jRadioButton_Canvas.setSize(new Dimension(67, 24));
			jRadioButton_Canvas.addActionListener(new ActionListenerRadios());
		}
		return jRadioButton_Canvas;
	}

	/**
	 * This method initializes jRadioButton_Both
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getJRadioButton_Both() {
		if (jRadioButton_Both == null) {
			jRadioButton_Both = new JRadioButton();
			jRadioButton_Both.setText("Both");
			jRadioButton_Both.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 10));
			jRadioButton_Both.setForeground(new Color(102, 102, 102));
			jRadioButton_Both.setLocation(new Point(181, 13));
			jRadioButton_Both.setSize(new Dimension(51, 24));
			jRadioButton_Both.addActionListener(new ActionListenerRadios());
		}
		return jRadioButton_Both;
	}

	/**
	 * This method initializes jTextField_color2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField_color2() {
		if (jTextField_color2 == null) {
			jTextField_color2 = new JTextField();
			jTextField_color2.setLocation(new Point(251, 22));
			jTextField_color2.setSize(new Dimension(30, 20));
			jTextField_color2.setBackground(Color.red);
			jTextField_color2.setEditable(false);
			jTextField_color2.setFocusable(false);
		}
		return jTextField_color2;
	}

	class ActionListenerColor implements ActionListener {
		JFrame frame = null;

		public ActionListenerColor(JFrame frame) {
			this.frame = frame;
		}

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			color = JColorChooser.showDialog(frame, "Select a color", Color.red);
			jTextField_color2.setBackground(color);
		}
	}

	public Color getColor() {
		return this.color;
	}

	/**
	 * This method initializes jButton_Color
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton_Color() {
		if (jButton_Color == null) {
			jButton_Color = new JButton();
			jButton_Color.setText("color...");
			jButton_Color.setSize(new Dimension(80, 16));
			jButton_Color.setLocation(new Point(285, 24));
			jButton_Color.addActionListener(new ActionListenerColor(this));
		}
		return jButton_Color;
	}

	/**
	 * This method initializes jPanel_Select_Shape_Delete
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_Select_Shape_Delete() {
		if (jPanel_Select_Shape_Delete == null) {
			jLabel4 = new JLabel();
			jLabel4.setText("Select the shape that you want delete");
			jLabel4.setLocation(new Point(13, 21));
			jLabel4.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 12));
			jLabel4.setForeground(new Color(102, 102, 102));
			jLabel4.setSize(new Dimension(270, 16));
			jPanel_Select_Shape_Delete = new JPanel();
			jPanel_Select_Shape_Delete.setLayout(null);
			jPanel_Select_Shape_Delete.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			jPanel_Select_Shape_Delete.setLocation(new Point(39, 93));
			jPanel_Select_Shape_Delete.setSize(new Dimension(437, 69));
			jPanel_Select_Shape_Delete.add(jLabel4, null);
			jPanel_Select_Shape_Delete.add(getJButton_select_Shape_Delete(), null);
		}
		return jPanel_Select_Shape_Delete;
	}

	class ActionListenerSelectShape implements ActionListener {
		private MainWindow p = null;
		private String[] sps = null;
		private String action = null;

		public ActionListenerSelectShape(MainWindow p, String action) {
			this.p = p;
			this.action = action;
		}

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			List shapes = Shapes.getInstance().getListShapes();
			if (shapes.size() > 0) {
				this.sps = logical.loadShapes();
				if (this.sps != null) {
					p.disabledControlsDelete();
					p.disabledControlsMove();
					p.disabledControlsScale();
					DialogSelectShape dss = new DialogSelectShape(this.p, this.sps, this.action);
					dss.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(p, "there isn´t any shapes", "", JOptionPane.INFORMATION_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(p, "there isn´t any shapes", "", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	/**
	 * This method initializes jButton_select_Shape_Delete
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton_select_Shape_Delete() {
		if (jButton_select_Shape_Delete == null) {
			jButton_select_Shape_Delete = new JButton();
			jButton_select_Shape_Delete.setText("Select...");
			jButton_select_Shape_Delete.setLocation(new Point(320, 21));
			jButton_select_Shape_Delete.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 12));
			jButton_select_Shape_Delete.setForeground(new Color(102, 102, 102));
			jButton_select_Shape_Delete.setSize(new Dimension(92, 26));
			action = "delete";
			jButton_select_Shape_Delete.addActionListener(new ActionListenerSelectShape(this, action));
		}
		return jButton_select_Shape_Delete;
	}

	/**
	 * This method initializes jPanel_Delete_Shape
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_Delete_Shape() {
		if (jPanel_Delete_Shape == null) {
			jPanel_Delete_Shape = new JPanel();
			jPanel_Delete_Shape.setLayout(null);
			jPanel_Delete_Shape.setSize(new Dimension(427, 110));
			jPanel_Delete_Shape.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			jPanel_Delete_Shape.setLocation(new Point(43, 174));
			jPanel_Delete_Shape.add(getJTextArea_Shape_Delete(), null);
			jPanel_Delete_Shape.add(getJButton_Delete_Shape(), null);
		}
		return jPanel_Delete_Shape;
	}

	/**
	 * This method initializes jTextArea_Shape_Delete
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getJTextArea_Shape_Delete() {
		if (jTextArea_Shape_Delete == null) {
			jTextArea_Shape_Delete = new JTextArea();
			jTextArea_Shape_Delete.setBounds(new Rectangle(5, 5, 418, 59));
			jTextArea_Shape_Delete.setForeground(new Color(0, 0, 153));
			jTextArea_Shape_Delete.setLineWrap(true);
			jTextArea_Shape_Delete.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jTextArea_Shape_Delete.setFont(new Font("Franklin Gothic Heavy", Font.PLAIN, 10));
			jTextArea_Shape_Delete.setEnabled(false);
			jTextArea_Shape_Delete.setEditable(false);
		}
		return jTextArea_Shape_Delete;
	}

	class ActionListenerDeleteShape implements ActionListener {
		MainWindow p = null;

		public ActionListenerDeleteShape(MainWindow p) {
			this.p = p;
		}

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			Controller controller = new Controller();
			List list = controller.getListShapes();
			// Shape s = (Shape)list.get(selection);
			Shape s = Shapes.getInstance().find(logical.getSelection());
			if (s != null) {
				String message = "Are you sure that you want delete the shape " + s.getClass().getName();
				int res = JOptionPane.showConfirmDialog(p, message, "", JOptionPane.YES_NO_OPTION);
				if (res == JOptionPane.YES_OPTION) {
					controller.delete(s);
				}
			}

			p.disabledControlsDelete();
		}
	}

	public void setEnabledControlsDelete() {
		jButton_Delete_Shape.setEnabled(true);
		jTextArea_Shape_Delete.setEnabled(true);
	}

	public void setEnabledControlsMove() {
		jButton_Move_Shape.setEnabled(true);
		jTextArea_Shape_Move.setEnabled(true);
		jTextField_new_x.setEnabled(true);
		jTextField_new_y.setEnabled(true);
	}

	public void setEnabledControlsScale() {
		jTextArea_Shape_Scale.setEnabled(true);
		Controller controller = new Controller();
		List list = controller.getListShapes();
		// Shape s = (Shape)list.get(selection);
		Shape s = Shapes.getInstance().find(logical.getSelection());
		if (s != null) {
			if (s.getClass().getName().equals("ShapesPackage.RectangleA")) {
				jTextField_new_width.setEnabled(true);
				jTextField_new_height.setEnabled(true);
				jButton_Scale_Rectangle.setEnabled(true);
			} else if (s.getClass().getName().equals("ShapesPackage.CircleA")) {
				jTextField_new_radius.setEnabled(true);
				jButton_Scale_Circle.setEnabled(true);
			} else if (s.getClass().getName().equals("ShapesPackage.TriangleA")) {
				jTextField_new_side.setEnabled(true);
				jButton_Scale_Triangle.setEnabled(true);
			}
		}
	}

	/**
	 * This method initializes jButton_Delete_Shape
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton_Delete_Shape() {
		if (jButton_Delete_Shape == null) {
			jButton_Delete_Shape = new JButton();
			jButton_Delete_Shape.setText("Delete");
			jButton_Delete_Shape.setLocation(new Point(320, 77));
			jButton_Delete_Shape.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 12));
			jButton_Delete_Shape.setForeground(new Color(102, 102, 102));
			jButton_Delete_Shape.setEnabled(false);
			jButton_Delete_Shape.setSize(new Dimension(93, 26));
			jButton_Delete_Shape.addActionListener(new ActionListenerDeleteShape(this));
		}
		return jButton_Delete_Shape;
	}

	/**
	 * This method initializes jTextArea_Shape_Move
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getJTextArea_Shape_Move() {
		if (jTextArea_Shape_Move == null) {
			jTextArea_Shape_Move = new JTextArea();
			jTextArea_Shape_Move.setLocation(new Point(8, 13));
			jTextArea_Shape_Move.setFont(new Font("Franklin Gothic Heavy", Font.PLAIN, 10));
			jTextArea_Shape_Move.setForeground(new Color(0, 0, 153));
			jTextArea_Shape_Move.setLineWrap(true);
			jTextArea_Shape_Move.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jTextArea_Shape_Move.setSize(new Dimension(418, 59));
			jTextArea_Shape_Move.setEnabled(false);
			jTextArea_Shape_Move.setEditable(false);
		}
		return jTextArea_Shape_Move;
	}

	/**
	 * This method initializes jPanel_Scale
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_Scale() {
		if (jPanel_Scale == null) {
			jPanel_Scale = new JPanel();
			jPanel_Scale.setLayout(null);
			jPanel_Scale.setName("jPanel_Scale");
			jPanel_Scale.add(getJPanel_Selection_Scale(), null);
			jPanel_Scale.add(getJPanel_Scale_Shape(), null);
		}
		return jPanel_Scale;
	}

	/**
	 * This method initializes jPanel_Selection_Scale
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_Selection_Scale() {
		if (jPanel_Selection_Scale == null) {
			jLabel5 = new JLabel();
			jLabel5.setText("Select the shape that you want scale");
			jLabel5.setLocation(new Point(13, 21));
			jLabel5.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 12));
			jLabel5.setForeground(new Color(102, 102, 102));
			jLabel5.setSize(new Dimension(261, 16));
			jPanel_Selection_Scale = new JPanel();
			jPanel_Selection_Scale.setLayout(null);
			jPanel_Selection_Scale.setSize(new Dimension(437, 69));
			jPanel_Selection_Scale.setLocation(new Point(37, 19));
			jPanel_Selection_Scale.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			jPanel_Selection_Scale.add(jLabel5, null);
			jPanel_Selection_Scale.add(getJButton_Select_Scale(), null);
		}
		return jPanel_Selection_Scale;
	}

	/**
	 * This method initializes jButton_Select_Scale
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton_Select_Scale() {
		if (jButton_Select_Scale == null) {
			jButton_Select_Scale = new JButton();
			jButton_Select_Scale.setText("Select...");
			jButton_Select_Scale.setLocation(new Point(320, 21));
			jButton_Select_Scale.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 12));
			jButton_Select_Scale.setForeground(new Color(102, 102, 102));
			jButton_Select_Scale.setSize(new Dimension(92, 26));
			action = "scale";
			jButton_Select_Scale.addActionListener(new ActionListenerSelectShape(this, action));
		}
		return jButton_Select_Scale;
	}

	/**
	 * This method initializes jPanel_Scale_Shape
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_Scale_Shape() {
		if (jPanel_Scale_Shape == null) {
			jPanel_Scale_Shape = new JPanel();
			jPanel_Scale_Shape.setLayout(null);
			jPanel_Scale_Shape.setBounds(new Rectangle(37, 97, 436, 280));
			jPanel_Scale_Shape.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
			jPanel_Scale_Shape.add(getJTextArea_Shape_Scale(), null);
			jPanel_Scale_Shape.add(getJPanel_What_Shape_Scale(), null);
		}
		return jPanel_Scale_Shape;
	}

	/**
	 * This method initializes jTextArea_Shape_Scale
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getJTextArea_Shape_Scale() {
		if (jTextArea_Shape_Scale == null) {
			jTextArea_Shape_Scale = new JTextArea();
			jTextArea_Shape_Scale.setSize(new Dimension(418, 59));
			jTextArea_Shape_Scale.setLineWrap(true);
			jTextArea_Shape_Scale.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jTextArea_Shape_Scale.setFont(new Font("Franklin Gothic Heavy", Font.PLAIN, 10));
			jTextArea_Shape_Scale.setForeground(new Color(0, 0, 153));
			jTextArea_Shape_Scale.setLocation(new Point(8, 13));
			jTextArea_Shape_Scale.setEnabled(false);
			jTextArea_Shape_Scale.setEditable(false);
		}
		return jTextArea_Shape_Scale;
	}

	/**
	 * This method initializes jPanel_What_Shape_Scale
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_What_Shape_Scale() {
		if (jPanel_What_Shape_Scale == null) {
			jPanel_What_Shape_Scale = new JPanel();
			myCardLayoutScale = new CardLayout();
			myCardLayout.setHgap(0);
			myCardLayout.setVgap(0);
			jPanel_What_Shape_Scale.setLayout(myCardLayoutScale);
			jPanel_What_Shape_Scale.setBounds(new Rectangle(10, 81, 416, 161));
			jPanel_What_Shape_Scale.add(getJPanel_Scale_RCT(), getJPanel_Scale_RCT().getName());
			jPanel_What_Shape_Scale.add(getJPanel_Scale_Rectangle(), getJPanel_Scale_Rectangle().getName());
			jPanel_What_Shape_Scale.add(getJPanel_Scale_Triangle(), getJPanel_Scale_Triangle().getName());
			jPanel_What_Shape_Scale.add(getJPanel_Scale_Circle(), getJPanel_Scale_Circle().getName());
		}
		return jPanel_What_Shape_Scale;
	}

	public String getField_NewWidth() {
		return jTextField_new_width.getText();
	}

	public String getField_NewHeight() {
		return jTextField_new_height.getText();
	}

	public String getField_NewRadius() {
		return jTextField_new_radius.getText();
	}

	public String getField_NewSide() {
		return jTextField_new_side.getText();
	}

	class ActionListenerScaleShape implements ActionListener {
		MainWindow p = null;

		public ActionListenerScaleShape(MainWindow p) {
			this.p = p;
		}

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			logical.ListenerScaleShape(p);
			p.disabledControlsScale();
		}
	}

	/**
	 * This method initializes jButton_Scale_Circle
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton_Scale_Circle() {
		if (jButton_Scale_Circle == null) {
			jButton_Scale_Circle = new JButton();
			jButton_Scale_Circle.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 12));
			jButton_Scale_Circle.setForeground(new Color(102, 102, 102));
			jButton_Scale_Circle.setSize(new Dimension(92, 26));
			jButton_Scale_Circle.setLocation(new Point(312, 124));
			jButton_Scale_Circle.setText("Scale");
			jButton_Scale_Circle.setEnabled(false);
			jButton_Scale_Circle.addActionListener(new ActionListenerScaleShape(this));
		}
		return jButton_Scale_Circle;
	}

	/**
	 * This method initializes jPanel_Scale_Rectangle
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_Scale_Rectangle() {
		if (jPanel_Scale_Rectangle == null) {
			jLabel7_new_height = new JLabel();
			jLabel7_new_height.setBounds(new Rectangle(164, 51, 79, 16));
			jLabel7_new_height.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 12));
			jLabel7_new_height.setForeground(new Color(102, 102, 102));
			jLabel7_new_height.setText("new height");
			jLabel7_new_width = new JLabel();
			jLabel7_new_width.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 12));
			jLabel7_new_width.setForeground(new Color(102, 102, 102));
			jLabel7_new_width.setSize(new Dimension(74, 16));
			jLabel7_new_width.setLocation(new Point(13, 51));
			jLabel7_new_width.setText("new width");
			jLabel6 = new JLabel();
			jLabel6.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 12));
			jLabel6.setForeground(new Color(102, 102, 102));
			jLabel6.setSize(new Dimension(227, 16));
			jLabel6.setLocation(new Point(13, 13));
			jLabel6.setText("Scaling a rectangle");
			jPanel_Scale_Rectangle = new JPanel();
			jPanel_Scale_Rectangle.setLayout(null);
			jPanel_Scale_Rectangle.setName("jPanel_Scale_Rectangle");
			jPanel_Scale_Rectangle.add(jLabel6, null);
			jPanel_Scale_Rectangle.add(jLabel7_new_width, null);
			jPanel_Scale_Rectangle.add(getJTextField_new_width(), null);
			jPanel_Scale_Rectangle.add(jLabel7_new_height, null);
			jPanel_Scale_Rectangle.add(getJTextField_new_height(), null);
			jPanel_Scale_Rectangle.add(getJButton_Scale_Rectangle(), null);
		}
		return jPanel_Scale_Rectangle;
	}

	/**
	 * This method initializes jTextField_new_width
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField_new_width() {
		if (jTextField_new_width == null) {
			jTextField_new_width = new JTextField();
			jTextField_new_width.setLocation(new Point(89, 49));
			jTextField_new_width.setSize(new Dimension(52, 19));
			jTextField_new_width.setEnabled(false);
		}
		return jTextField_new_width;
	}

	/**
	 * This method initializes jTextField_new_height
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField_new_height() {
		if (jTextField_new_height == null) {
			jTextField_new_height = new JTextField();
			jTextField_new_height.setSize(new Dimension(52, 19));
			jTextField_new_height.setLocation(new Point(244, 49));
			jTextField_new_height.setEnabled(false);
		}
		return jTextField_new_height;
	}

	/**
	 * This method initializes jPanel_Scale_Circle
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_Scale_Circle() {
		if (jPanel_Scale_Circle == null) {
			jLabel8 = new JLabel();
			jLabel8.setText("new radius");
			jLabel8.setSize(new Dimension(81, 16));
			jLabel8.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 12));
			jLabel8.setForeground(new Color(102, 102, 102));
			jLabel8.setLocation(new Point(16, 54));
			jLabel7 = new JLabel();
			jLabel7.setBounds(new Rectangle(16, 14, 227, 19));
			jLabel7.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 12));
			jLabel7.setForeground(new Color(102, 102, 102));
			jLabel7.setText("Scaling a circle");
			jPanel_Scale_Circle = new JPanel();
			jPanel_Scale_Circle.setLayout(null);
			jPanel_Scale_Circle.setName("jPanel_Scale_Circle");
			jPanel_Scale_Circle.add(jLabel7, null);
			jPanel_Scale_Circle.add(jLabel8, null);
			jPanel_Scale_Circle.add(getJTextField_new_radius(), null);
			jPanel_Scale_Circle.add(getJButton_Scale_Circle(), null);
		}
		return jPanel_Scale_Circle;
	}

	/**
	 * This method initializes jTextField_new_radius
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField_new_radius() {
		if (jTextField_new_radius == null) {
			jTextField_new_radius = new JTextField();
			jTextField_new_radius.setLocation(new Point(98, 52));
			jTextField_new_radius.setSize(new Dimension(52, 19));
			jTextField_new_radius.setEnabled(false);
		}
		return jTextField_new_radius;
	}

	/**
	 * This method initializes jPanel_Scale_Triangle
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_Scale_Triangle() {
		if (jPanel_Scale_Triangle == null) {
			jLabel10 = new JLabel();
			jLabel10.setText("new side");
			jLabel10.setSize(new Dimension(60, 21));
			jLabel10.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 12));
			jLabel10.setForeground(new Color(102, 102, 102));
			jLabel10.setLocation(new Point(13, 51));
			jLabel9 = new JLabel();
			jLabel9.setText("Scaling a triangle");
			jLabel9.setSize(new Dimension(120, 16));
			jLabel9.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 12));
			jLabel9.setForeground(new Color(102, 102, 102));
			jLabel9.setLocation(new Point(13, 15));
			jPanel_Scale_Triangle = new JPanel();
			jPanel_Scale_Triangle.setLayout(null);
			jPanel_Scale_Triangle.setName("jPanel_Scale_Triangle");
			jPanel_Scale_Triangle.add(jLabel9, null);
			jPanel_Scale_Triangle.add(jLabel10, null);
			jPanel_Scale_Triangle.add(getJTextField_new_side(), null);
			jPanel_Scale_Triangle.add(getJButton_Scale_Triangle(), null);
		}
		return jPanel_Scale_Triangle;
	}

	/**
	 * This method initializes jTextField_new_side
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField_new_side() {
		if (jTextField_new_side == null) {
			jTextField_new_side = new JTextField();
			jTextField_new_side.setLocation(new Point(78, 51));
			jTextField_new_side.setSize(new Dimension(52, 19));
			jTextField_new_side.setEnabled(false);
		}
		return jTextField_new_side;
	}

	/**
	 * This method initializes jButton_Scale_Triangle
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton_Scale_Triangle() {
		if (jButton_Scale_Triangle == null) {
			jButton_Scale_Triangle = new JButton();
			jButton_Scale_Triangle.setText("Scale");
			jButton_Scale_Triangle.setLocation(new Point(312, 124));
			jButton_Scale_Triangle.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 12));
			jButton_Scale_Triangle.setForeground(new Color(102, 102, 102));
			jButton_Scale_Triangle.setSize(new Dimension(92, 26));
			jButton_Scale_Triangle.setEnabled(false);
			jButton_Scale_Triangle.addActionListener(new ActionListenerScaleShape(this));
		}
		return jButton_Scale_Triangle;
	}

	/**
	 * This method initializes jButton_Scale_Rectangle
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton_Scale_Rectangle() {
		if (jButton_Scale_Rectangle == null) {
			jButton_Scale_Rectangle = new JButton();
			jButton_Scale_Rectangle.setText("Scale");
			jButton_Scale_Rectangle.setLocation(new Point(312, 124));
			jButton_Scale_Rectangle.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 12));
			jButton_Scale_Rectangle.setForeground(new Color(102, 102, 102));
			jButton_Scale_Rectangle.setSize(new Dimension(92, 26));
			jButton_Scale_Rectangle.setEnabled(false);
			jButton_Scale_Rectangle.addActionListener(new ActionListenerScaleShape(this));
		}
		return jButton_Scale_Rectangle;
	}

	/**
	 * This method initializes jPanel_Scale_RCT
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_Scale_RCT() {
		if (jPanel_Scale_RCT == null) {
			jPanel_Scale_RCT = new JPanel();
			jPanel_Scale_RCT.setLayout(null);
			jPanel_Scale_RCT.setName("jPanel_Scale_RCT");
		}
		return jPanel_Scale_RCT;
	} 

	public void initParamJasco() {
		jasco.options.Options.setOutputDir("Generated");

		String con = null;
		con = "ConnectorLogger";
		logical.removeConnector(con);
		con = "ConnectorProfiler";
		logical.removeConnector(con);
	}

	/**
	 * This method initializes jPanel_Checks
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_Checks() {
		if (jPanel_Checks == null) {
			jPanel_Checks = new JPanel();
			jPanel_Checks.setLayout(null);
			jPanel_Checks.setBorder(BorderFactory.createTitledBorder(null, "Activate",
					TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
					new Font("Franklin Gothic Heavy", Font.BOLD, 12), new Color(102, 102, 102)));
			jPanel_Checks.add(getJCheckBox_Logger(), null);
			jPanel_Checks.add(getJCheckBox_Profiler(), null);
		}
		return jPanel_Checks;
	}

	public void activateLog() {
		// System.out.println("activateLog");
		String loadConnector = null;
		loadConnector = "src/aspect/ConnectorLogger.con";
		logical.loadConnector(loadConnector);
	}

	public void disactivateLog() {
		// System.out.println("disactivateLog");
		String con = null;
		con = "ConnectorLogger";
		logical.removeConnector(con);
	}

	public void actLog() {
	}

	class ActionListenerCheckLog implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (jCheckBox_Logger.isSelected()) {
				activateLog();
			} else {
				disactivateLog();
			}
			if (jCheckBox_Logger.isSelected()) {
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				actLog();
			}
		}
	}

	/**
	 * This method initializes jCheckBox_Logger
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox_Logger() {
		if (jCheckBox_Logger == null) {
			jCheckBox_Logger = new JCheckBox();
			jCheckBox_Logger.setBounds(new Rectangle(5, 16, 49, 21));
			jCheckBox_Logger.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 10));
			jCheckBox_Logger.setForeground(new Color(102, 102, 102));
			jCheckBox_Logger.setText("Log");
			jCheckBox_Logger.addActionListener(new ActionListenerCheckLog());
		}
		return jCheckBox_Logger;
	}

	public void activateProfiler() {
		String loadConnector = null;
		loadConnector = "src/aspect/ConnectorProfiler.con";
		logical.loadConnector(loadConnector);
	}

	public void disactivateProfiler() {
		String con = null;
		con = "ConnectorProfiler";
		logical.removeConnector(con);
	}

	public void actProfiler() {
	}

	class ActionListenerProfiler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

			if (jCheckBox_Profiler.isSelected()) {
				activateProfiler();
			} else {
				disactivateProfiler();
			}
			if (jCheckBox_Profiler.isSelected()) {
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				actProfiler();
			}
		}
	}

	/**
	 * This method initializes jCheckBox_Profiler
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCheckBox_Profiler() {
		if (jCheckBox_Profiler == null) {
			jCheckBox_Profiler = new JCheckBox();
			jCheckBox_Profiler.setBounds(new Rectangle(61, 17, 58, 21));
			jCheckBox_Profiler.setFont(new Font("Franklin Gothic Heavy", Font.BOLD, 10));
			jCheckBox_Profiler.setForeground(new Color(102, 102, 102));
			jCheckBox_Profiler.setText("Prof");
			jCheckBox_Profiler.addActionListener(new ActionListenerProfiler());
		}
		return jCheckBox_Profiler;
	}

	private static int maxIterations, k;

	private static boolean memory;

	public static void main(String[] args) {
		boolean steady = args.length == 2 || (args.length == 1 && args[0].contains("STEAD"));
		memory = args.length == 2 || (args.length == 1 && args[0].contains("MEM"));
		maxIterations = steady ? 30 : 1;
		k = steady ? 10 : 1;
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {

				// int maxIterations = 1, k = 1;
				double CoV = 0.02;
				Logical logical = new Logical();
				List<Double> executionTimes = new ArrayList<Double>();
				for (int i = 1; i <= maxIterations && !areWeDone(executionTimes, k, CoV); i++) {

					long ini = System.currentTimeMillis();
					MainWindow mainWindow = new MainWindow();

					mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					mainWindow.setVisible(true);

					mainWindow.jCheckBox_Logger.doClick();
					mainWindow.jCheckBox_Profiler.doClick();
					mainWindow.jRadioButton_Both.doClick();
					// thisClass.jButton_Demo.doClick();
					logical.loadDemo("DemoTimer");

					long fin = System.currentTimeMillis();
					System.out.println("Iter " + i + " terminada. ");
					executionTimes.add((double) fin - ini);
				}
				BufferedWriter bw;
				if (memory)

					System.out.println("memory test");
				else
					try {
						bw = new BufferedWriter(new FileWriter("temp/output.txt"));
						bw.write("" + ((int) getMean(executionTimes, k)));
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				System.exit(0);
			}
		});
	}

	private static boolean areWeDone(List<Double> executionTimes, int k, double CoV) {
		if (executionTimes.size() < k)
			return false;
		double sum = 0;
		double mean = getMean(executionTimes, k);
		for (int i = executionTimes.size() - k; i < executionTimes.size(); i++)
			sum += Math.pow(executionTimes.get(i) - mean, 2);
		double stdDeviation = Math.sqrt(sum / k);
		return stdDeviation / mean < CoV;
	}

	private static double getMean(List<Double> executionTimes, int k) {

		double sum = 0;
		for (int i = executionTimes.size() - k; i < executionTimes.size(); i++)
			sum += executionTimes.get(i);
		return sum / k;
	}

	/**
	 * @param args
	 */
	/*
	 * public static void main(String[] args) { SwingUtilities.invokeLater(new
	 * Runnable() { public void run() {
	 * 
	 * //System.out.println("NUEVO TEST:"); prueba thisClass = new prueba();
	 * long ini = System.currentTimeMillis();
	 * 
	 * thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 * thisClass.setVisible(true);
	 * 
	 * thisClass.jCheckBox_Logger.doClick();
	 * thisClass.jCheckBox_Profiler.doClick();
	 * thisClass.jRadioButton_Both.doClick(); //
	 * thisClass.jButton_Demo.doClick(); Logical logical = new Logical();
	 * logical.loadDemo("DemoTimer"); long fin = System.currentTimeMillis();
	 * 
	 * BufferedWriter bw; try { bw = new BufferedWriter(new
	 * FileWriter("output.txt")); long res = fin - ini; //
	 * System.out.println(res); // System.out.println((int) res);
	 * 
	 * bw.write("" + (int) res); bw.close();
	 * //System.out.println("FICHERO CERRADO."); } catch (IOException e) { //
	 * TODO Auto-generated catch block e.printStackTrace(); } //
	 * System.out.println(Memory.getPeakMemoryUsage("java.exe"));
	 * System.exit(0);
	 * 
	 * } });
	 * 
	 * }
	 */
	public static void pruebaPrincipal() {

	}

	/**
	 * This is the default constructor
	 */
	public MainWindow() {
		super();
		initialize();
		initParamJasco();
	}

	class WindowListenerClose extends WindowAdapter {
		public void windowClosing(java.awt.event.WindowEvent e) {
			// TODO Auto-generated Event stub windowClosing()
			close();
		}
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(680, 500);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("icons/bunny2b.gif"));
		this.setLocation(new Point(350, 200));
		this.setResizable(false);
		this.setContentPane(getJContentPane());
		this.setTitle("Working with aspects");
		// mainCanvas();
		this.addWindowListener(new WindowListenerClose());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.setBackground(new Color(204, 204, 255));
			jContentPane.setEnabled(true);
			jContentPane.add(getJToolBar_Options(), BorderLayout.NORTH);
			jContentPane.add(getJPanel_Principal(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
