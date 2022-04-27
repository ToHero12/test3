package window;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import javax.swing.*;
import java.util.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class AddEmployee extends JInternalFrame implements ActionListener {

	JFrame JFParentFrame;
	JDesktopPane desktop;
	private JPanel panel1;
	private JPanel panel2;
	private JButton AddBtn;
	private JButton ResetBtn;
	private JButton ExitBtn;
	private JLabel LblEmp_Code, LblEmp_Name1, LblEmp_Name2, LblEmp_Desi, LblEmp_Add, LblEmp_No;
	private JTextField TxtEmp_Code, TxtEmp_Name1, TxtEmp_Name2, TxtEmp_Add, TxtEmp_No;
	private JComboBox Emp_Type;
	String dialogmessage;
	String dialogs;
	int dialogtype = JOptionPane.PLAIN_MESSAGE;

	public static int record;
	String Emp_Code = "";
	String Emp_Name1 = "";
	String Emp_Name2 = "";
	String Emp_Desi = "";
	String Emp_Add = "";
	String Emp_No = "";

	public AddEmployee(JFrame getParentFrame) {

		super("Add - Employee ", true, true, true, true);
		setSize(400, 800);
		JFParentFrame = getParentFrame;
		panel1 = new JPanel();
		panel1.setLayout(new GridLayout(7, 7));

		LblEmp_Code = new JLabel(" Employee Code :");
		LblEmp_Name1 = new JLabel(" First Name    :");
		LblEmp_Name2 = new JLabel(" Last Name     :");
		LblEmp_Desi = new JLabel(" Designation   :");
		LblEmp_Add = new JLabel(" Address       :");
		LblEmp_No = new JLabel(" Contact No    :");

		TxtEmp_Code = new JTextField(20);
		Emp_Type = new JComboBox();
		Emp_Type.addActionListener(this);
		Emp_Type.setEditable(false);
		add_Cat_combo(Emp_Type);
		TxtEmp_Name1 = new JTextField(20);
		TxtEmp_Name2 = new JTextField(20);

		TxtEmp_Add = new JTextField(20);
		TxtEmp_No = new JTextField(20);

		panel1.add(LblEmp_Code);
		panel1.add(TxtEmp_Code);

		panel1.add(LblEmp_Desi);
		panel1.add(Emp_Type);

		panel1.add(LblEmp_Name1);
		panel1.add(TxtEmp_Name1);

		panel1.add(LblEmp_Name2);
		panel1.add(TxtEmp_Name2);

		panel1.add(LblEmp_Add);
		panel1.add(TxtEmp_Add);

		panel1.add(LblEmp_No);
		panel1.add(TxtEmp_No);
		panel1.setOpaque(true);

		panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		AddBtn = new JButton("Add");
		ResetBtn = new JButton("Reset");
		ExitBtn = new JButton("Exit");

		panel2.add(AddBtn);
		AddBtn.addActionListener(this);
		panel2.add(ResetBtn);
		ResetBtn.addActionListener(this);
		panel2.add(ExitBtn);
		ExitBtn.addActionListener(this);
		panel2.setOpaque(true);

		getContentPane().setLayout(new GridLayout(2, 1));
		getContentPane().add(panel1, "CENTER");
		getContentPane().add(panel2, "CENTER");
		setFrameIcon(new ImageIcon("images/backup.gif"));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();

		checkNumber(TxtEmp_No);

	}

	public void checkNumber(JTextField txtField) {
		txtField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
					e.consume();
				}
			}
		});
	}

	// Emp_Code;Emp_Name1;Emp_name2;Emp_Desi;Emp_Add;Emp_No
	public void actionPerformed(ActionEvent event) {

		Object source = event.getSource();

		if (source.equals(Emp_Type)) {

			Emp_Desi = (String) Emp_Type.getSelectedItem();

		}

		if (source.equals(AddBtn)) {

			Emp_Code = "";
			Emp_Name1 = "";
			Emp_Name2 = "";
			Emp_Desi = "";
			Emp_Add = "";
			Emp_No = "";

			Emp_Code = TxtEmp_Code.getText().trim();
			Emp_Name1 = TxtEmp_Name1.getText().trim();
			Emp_Name2 = TxtEmp_Name2.getText().trim();
			Emp_Desi = (String) Emp_Type.getSelectedItem();
			Emp_Add = TxtEmp_Add.getText().trim();
			Emp_No = TxtEmp_No.getText().trim();

			try {

				if (!Emp_Code.equals("") && !Emp_Name1.equals("") && !Emp_Name2.equals("") && !Emp_Desi.equals("")
						&& !Emp_Add.equals("") && !Emp_No.equals(""))
				{

					try {
						String line;
						BufferedReader br = new BufferedReader(
								new FileReader(System.getProperty("user.dir") + "/bin/employee.csv"));
						while ((line = br.readLine()) != null) {
							if (line.length() == 0)
								break;
							String[] d = line.split(";");
							if (d[0].equals(Emp_Code)) {
								dialogmessage = "Record Already Exists in DataBase!!!";
								dialogtype = JOptionPane.WARNING_MESSAGE;
								JOptionPane.showMessageDialog((Component) null, dialogmessage, dialogs, dialogtype);
								br.close();
								return;
							}
						}
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					String temp = Emp_Code + ";" + Emp_Name1 + ";" + Emp_Name2 + ";" + Emp_Desi + ";" + Emp_Add + ";"
							+ Emp_No + "\n";
					Files.write(Paths.get(System.getProperty("user.dir") + "/bin/employee.csv"), temp.getBytes(),
							StandardOpenOption.APPEND);

					System.out.println("Recorded Added");
					ResetRecord();
				} else {
					dialogmessage = "Empty Record !!!";
					dialogtype = JOptionPane.WARNING_MESSAGE;
					JOptionPane.showMessageDialog((Component) null, dialogmessage, dialogs, dialogtype);
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "GENERAL EXCEPTION", "WARNING!!!", JOptionPane.INFORMATION_MESSAGE);
			}

		}

		else if (source == ResetBtn) {
			ResetRecord();
		} else if (source == ExitBtn) {
			setVisible(false);
			dispose();
		}

	}

	private void ResetRecord() {
		TxtEmp_Code.setText("");
		TxtEmp_Name1.setText("");
		TxtEmp_Name2.setText("");
		TxtEmp_Add.setText("");
		TxtEmp_No.setText("");
	}

	public void add_Cat_combo(JComboBox cmb) {

		try {
			String line;
			BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/bin/setting.csv"));
			while ((line = br.readLine()) != null) {
				String[] d = line.split(";");
				cmb.addItem(d[1]);
				record = Integer.parseInt(d[0]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
