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

public class DeleteEmployee extends JInternalFrame implements ActionListener {

	JFrame JFParentFrame;
	JDesktopPane desktop;
	private JPanel panel1;
	private JPanel panel2;
	private JButton FindBtn;
	private JButton DeleteBtn;
	private JButton ExitBtn;
	private JLabel LblEmp_Code, LblEmp_Name1, LblEmp_Name2, LblEmp_Desi, LblEmp_Add, LblEmp_No;
	private JTextField TxtEmp_Code, TxtEmp_Name1, TxtEmp_Name2, TxtEmp_Desi, TxtEmp_Add, TxtEmp_No;
	String dialogmessage;
	String dialogs;
	int dialogtype = JOptionPane.PLAIN_MESSAGE;

	public DeleteEmployee(JFrame getParentFrame) {

		super("Delete - Employee ", true, true, true, true);
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
		TxtEmp_Name1 = new JTextField(20);
		TxtEmp_Name2 = new JTextField(20);
		TxtEmp_Desi = new JTextField(20);
		TxtEmp_Add = new JTextField(20);
		TxtEmp_No = new JTextField(20);

		TxtEmp_Name1.setEditable(false);
		TxtEmp_Name2.setEditable(false);
		TxtEmp_Desi.setEditable(false);
		TxtEmp_Add.setEditable(false);
		TxtEmp_No.setEditable(false);

		panel1.add(LblEmp_Code);
		panel1.add(TxtEmp_Code);

		panel1.add(LblEmp_Name1);
		panel1.add(TxtEmp_Name1);

		panel1.add(LblEmp_Name2);
		panel1.add(TxtEmp_Name2);

		panel1.add(LblEmp_Desi);
		panel1.add(TxtEmp_Desi);

		panel1.add(LblEmp_Add);
		panel1.add(TxtEmp_Add);

		panel1.add(LblEmp_No);
		panel1.add(TxtEmp_No);

		panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		FindBtn = new JButton("Find");
		DeleteBtn = new JButton("Delete");
		ExitBtn = new JButton("Exit");

		panel2.add(FindBtn);
		FindBtn.addActionListener(this);
		panel2.add(DeleteBtn);
		DeleteBtn.addActionListener(this);
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

	public void actionPerformed(ActionEvent event) {

		Object source = event.getSource();

		String sEmp_Code = "";
		String sEmp_Name1 = "";
		String sEmp_Name2 = "";
		String sEmp_Desi = "";
		String sEmp_Add = "";
		String sEmp_No = "";
		if (source.equals(FindBtn)) {

			sEmp_Code = TxtEmp_Code.getText().trim();
			try {
				boolean found = false;
				try {
					String line;
					BufferedReader br = new BufferedReader(
							new FileReader(System.getProperty("user.dir") + "/bin/employee.csv"));
					while ((line = br.readLine()) != null) {
						if (line.length() == 0)
							break;
						String[] d = line.split(";");
						if (d[0].equals(sEmp_Code)) {
							sEmp_Name1 = d[1].trim();
							sEmp_Name2 = d[2].trim();
							sEmp_Desi = d[3].trim();
							sEmp_Add = d[4].trim();
							sEmp_No = d[5].trim();

							TxtEmp_Name1.setText(sEmp_Name1);
							TxtEmp_Name2.setText(sEmp_Name2);
							TxtEmp_Desi.setText(sEmp_Desi);
							TxtEmp_Add.setText(sEmp_Add);
							TxtEmp_No.setText(sEmp_No);
							found = true;
							break;
						}
					}
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (!found) {
					dialogmessage = "No Such Employuee";

					dialogtype = JOptionPane.WARNING_MESSAGE;
					JOptionPane.showMessageDialog((Component) null, dialogmessage, dialogs, dialogtype);
					ResetRecord();

				}

			} catch (Exception e) {
				System.out.println("\nUnknown Error");
			}

		}

		else if (source.equals(DeleteBtn)) {

			int DResult = JOptionPane.showConfirmDialog((Component) null, "Are you sure you want to delete Record?");

			if (DResult == JOptionPane.NO_OPTION) {
				ResetRecord();

			}

			if (DResult == JOptionPane.YES_OPTION) {

				try {
					sEmp_Code = TxtEmp_Code.getText().trim();

					if (!sEmp_Code.equals("")) {
						String db = "";
						boolean found = false;
						try {
							String line;
							BufferedReader br = new BufferedReader(
									new FileReader(System.getProperty("user.dir") + "/bin/employee.csv"));
							while ((line = br.readLine()) != null) {
								if (line.length() == 0)
									break;
								String[] d = line.split(";");
								if (d[0].equals(sEmp_Code)) {
									found = true;
								} else
									db += line + "\n";
							}
							br.close();
						} catch (IOException e) {
							e.printStackTrace();
						}

						if (found) {
							Files.write(Paths.get(System.getProperty("user.dir") + "/bin/employee.csv"), db.getBytes(),
									StandardOpenOption.TRUNCATE_EXISTING);
							dialogmessage = "Employee Record Deleted!!!";
							dialogtype = JOptionPane.WARNING_MESSAGE;
							JOptionPane.showMessageDialog((Component) null, dialogmessage, dialogs, dialogtype);
							ResetRecord();
						}

						else

						{
							dialogmessage = "No Such Employuee";

							dialogtype = JOptionPane.WARNING_MESSAGE;
							JOptionPane.showMessageDialog((Component) null, dialogmessage, dialogs, dialogtype);
							ResetRecord();

						}

					}

				}

				catch (Exception e) {
					System.out.println("\nUnknown Error" + e);
				}
			}

		} else if (source == ExitBtn) {
			setVisible(false);
			dispose();
		}

	}

	private void ResetRecord() {
		TxtEmp_Code.setText("");
		TxtEmp_Name1.setText("");
		TxtEmp_Name2.setText("");
		TxtEmp_Desi.setText("");
		TxtEmp_Add.setText("");
		TxtEmp_No.setText("");
	}

}
