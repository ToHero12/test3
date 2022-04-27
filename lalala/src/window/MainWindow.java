package window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class MainWindow extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9021746385813691334L;

	JDesktopPane desktop = new JDesktopPane();

// Menu Bar Variables

	JMenuBar menubar = new JMenuBar();

	JMenu menuFile = new JMenu("File");
	JMenu menuEmployee = new JMenu("Employee");
	JMenu menuTools = new JMenu("Tools");
	JMenu menuReports = new JMenu("Reports");
	JMenu menuHelp = new JMenu("Help");

// Menu Item

	JMenuItem itemExit = new JMenuItem();

	JMenuItem itemAdd = new JMenuItem();
	JMenuItem itemEdit = new JMenuItem();
	JMenuItem itemDelete = new JMenuItem();

	JMenuItem itemSettings = new JMenuItem();
	JMenuItem itemCalculator = new JMenuItem();
	JMenuItem itemNotePad = new JMenuItem();

	JMenuItem itemEmprpt = new JMenuItem();

	JMenuItem itemAuthor = new JMenuItem();
	JMenuItem itemHelp = new JMenuItem();

// JPanel 

	JPanel panel_Bottom = new JPanel();
	JPanel panel_Top = new JPanel();

// Label 

	JLabel lblUsername = new JLabel("User Name:");
	JLabel lblLogDetails = new JLabel("Time Login :");
	JLabel lblTimeNow = new JLabel();

// TextField
	JTextField username = new JTextField();
	JTextField logtime = new JTextField();

	ActionListener JToolBarActionListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String source = e.getActionCommand();

			if (source == "File_Exit") {
				loadJInternalFrame(2);
			} else if (source == "Emp_Add") {
				loadJInternalFrame(3);
			} else if (source == "Emp_Edit") {
				loadJInternalFrame(4);
			} else if (source == "Emp_Delete") {
				loadJInternalFrame(5);
			} else if (source == "Settings") {
				loadJInternalFrame(6);
			} else if (source == "Tools_Calculator") {
				loadJInternalFrame(7);
			} else if (source == "Tools_NotePad") {
				loadJInternalFrame(8);
			} else if (source == "Reports_Employee") {
				loadJInternalFrame(9);
			}

			else if (source == "Help_Author") {
				loadJInternalFrame(11);
			} else if (source == "Help_Help") {
				loadJInternalFrame(12);
			}
		}

	};

	public void actionPerformed(ActionEvent event) {
		Object object = event.getSource();

		if (object == itemExit) {
			loadJInternalFrame(2);
		} else if (object == itemAdd) {
			loadJInternalFrame(3);
		} else if (object == itemEdit) {
			loadJInternalFrame(4);
		} else if (object == itemDelete) {
			loadJInternalFrame(5);
		} else if (object == itemSettings) {
			loadJInternalFrame(6);
		} else if (object == itemCalculator) {
			loadJInternalFrame(7);

		} else if (object == itemNotePad) {
			loadJInternalFrame(8);
		} else if (object == itemEmprpt) {
			loadJInternalFrame(9);
		}

		else if (object == itemAuthor) {
			loadJInternalFrame(12);
		} else if (object == itemHelp) {
			loadJInternalFrame(13);
		}
	}

	private void loadJInternalFrame(int intWhich) {
		switch (intWhich) {

		case 2:
			System.exit(0);
			break;

		case 3:
			try {
				AddEmployee FormAddwindow = new AddEmployee(this);
				loadForm("Add Employee", FormAddwindow);
			} catch (Exception e) {
				System.out.println("\nError");
			}
			break;

		case 4:
			try {
				EditEmployee FormEditwindow = new EditEmployee(this);
				loadForm("Edit Employee", FormEditwindow);
			} catch (Exception e) {
				System.out.println("\nError");
			}
			break;

		case 5:
			try {
				DeleteEmployee FormDeletewindow = new DeleteEmployee(this);
				loadForm("Delete Employee", FormDeletewindow);
			} catch (Exception e) {
				System.out.println("\nError");
			}
			break;

		case 6:
			try {
				Setting FormSettingswindow = new Setting(this);
				loadForm("Settings of Employee", FormSettingswindow);
			} catch (Exception e) {
				System.out.println("\nError");
			}
			break;

		case 7:
			runComponents("Calc.exe");
			break;

		case 8:
			runComponents("Notepad.exe");
			break;

		case 9:
			try {
				Payslip FormEmprptwindow = new Payslip(this);
				loadForm("Employee PaySlip", FormEmprptwindow);

			} catch (Exception e) {
				System.out.println("\nError" + e);
			}
			break;

		}

	}

	protected void runComponents(String sComponents) {
		Runtime rt = Runtime.getRuntime();
		try {
			rt.exec(sComponents);
		} catch (IOException evt) {
			JOptionPane.showMessageDialog(null, evt.getMessage(), "Error Found", JOptionPane.ERROR_MESSAGE);
		}
	}

	protected void loadForm(String Title, JInternalFrame clsForm) {

		boolean xForm = isLoaded(Title);
		if (xForm == false) {
			desktop.add(clsForm);
			clsForm.setVisible(true);
			clsForm.show();
		} else {
			try {
				clsForm.setIcon(false);
				clsForm.setSelected(true);

			} catch (PropertyVetoException e) {
			}
		}
	}

	protected boolean isLoaded(String FormTitle) {
		JInternalFrame Form[] = desktop.getAllFrames();
		for (int i = 0; i < Form.length; i++) {
			if (Form[i].getTitle().equalsIgnoreCase(FormTitle)) {
				Form[i].show();
				try {
					Form[i].setIcon(false);
					Form[i].setSelected(true);

				} catch (PropertyVetoException e) {

				}
				return true;
			}
		}
		return false;
	}

	protected void UnloadWindow() {
		try {
			int reply = JOptionPane.showConfirmDialog(this, "Are you sure to exit?", "", JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE);
			if (reply == JOptionPane.YES_OPTION) {
				setVisible(false);
				System.exit(0);
			}
		} catch (Exception e) {
		}

	}

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public MainWindow(String user, Date date) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JTextField username = new JTextField();
		username.setEditable(false);
		JTextField logtime = new JTextField();
		logtime.setEditable(false);
		username.setText(user);
		logtime.setText(DateFormat.getDateTimeInstance().format(date));

		panel_Bottom.setLayout(new FlowLayout());
		panel_Bottom.setPreferredSize(new Dimension(10, 25));
		panel_Bottom.add(lblUsername);
		panel_Bottom.add(username);
		panel_Bottom.add(lblLogDetails);
		panel_Bottom.add(logtime);

		desktop.setBackground(Color.WHITE);
		desktop.setAutoscrolls(true);
		desktop.setBorder(BorderFactory.createLoweredBevelBorder());
		desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);

		getContentPane().add(desktop, BorderLayout.CENTER);
		getContentPane().add(panel_Bottom, BorderLayout.PAGE_END);

		addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				UnloadWindow();
			}
		});
		menuFile.add(setMenuItem(itemExit, "Quit"));

		itemExit.addActionListener(this);

		menuEmployee.add(setMenuItem(itemAdd, "Add Employee"));
		menuEmployee.add(setMenuItem(itemEdit, "Edit Employee"));
		menuEmployee.addSeparator();
		menuEmployee.add(setMenuItem(itemDelete, "Delete Employee"));

		itemAdd.addActionListener(this);
		itemEdit.addActionListener(this);
		itemDelete.addActionListener(this);

		menuTools.add(setMenuItem(itemSettings, "Settings"));
		menuTools.add(setMenuItem(itemCalculator, "Calculator"));
		menuTools.addSeparator();
		menuTools.add(setMenuItem(itemNotePad, "NotePad"));

		itemSettings.addActionListener(this);
		itemCalculator.addActionListener(this);
		itemNotePad.addActionListener(this);

		menuReports.add(setMenuItem(itemEmprpt, "Employee Report"));
		menuTools.addSeparator();
		menuTools.addSeparator();
		itemEmprpt.addActionListener(this);

		menuHelp.add(setMenuItem(itemAuthor, "About Author"));
		menuHelp.add(setMenuItem(itemHelp, "Help"));

		itemAuthor.addActionListener(this);
		itemHelp.addActionListener(this);

		menubar.add(setMenu(menuFile));
		menubar.add(setMenu(menuEmployee));
		menubar.add(setMenu(menuTools));
		menubar.add(setMenu(menuReports));
		menubar.add(setMenu(menuHelp));
		setJMenuBar(menubar);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setSize(700, 700);
		setLocation(2, 2);

	}

	private Color black = new Color(0, 0, 0);

	public JMenu setMenu(JMenu menu) {
		menu.setFont(new Font("Dialog", Font.BOLD, 12));
		menu.setCursor(new Cursor(Cursor.HAND_CURSOR));
		menu.setForeground(black);
		return menu;
	}

	public JMenuItem setMenuItem(JMenuItem item, String caption) {
		item.setText(caption);
		item.setCursor(new Cursor(Cursor.HAND_CURSOR));
		item.setFont(new Font("Dialog", Font.PLAIN, 12));
		item.setForeground(black);
		return item;
	}

}
