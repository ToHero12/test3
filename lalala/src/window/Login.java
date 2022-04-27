package window;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login extends JFrame {
	private JButton loginButton;
	private JButton exitButton;
	private JTextField userNameTextBox;
	private JPasswordField passwordTextBox;

	/**
	 * Create the frame.
	 */
	// 1
	public Login() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setTitle("Login Form");
		setSize(300, 200);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout());
		JPanel headerPanel = new JPanel();
		headerPanel.setLayout(new FlowLayout());

		JLabel nameLabel = new JLabel("Welcome to Q Payroll System");
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(2, 2));
		JLabel userNameLabel = new JLabel("Username :");
		userNameTextBox = new JTextField(20);
		userNameTextBox.setText("adm");

		JLabel passwordLabel = new JLabel("Password :");

		passwordTextBox = new JPasswordField(20);

		JPanel actionPanel = new JPanel();
		actionPanel.setLayout(new FlowLayout());

		loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkLogin();
			}
		});

		exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(NORMAL);
			}
		});

		headerPanel.add(nameLabel);
		headerPanel.setOpaque(true);
		inputPanel.add(userNameLabel);
		inputPanel.add(userNameTextBox);
		inputPanel.add(passwordLabel);
		inputPanel.add(passwordTextBox);
		inputPanel.setOpaque(true);
		actionPanel.add(loginButton);
		actionPanel.add(exitButton);
		actionPanel.setOpaque(true);

		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		contentPane.add(headerPanel);
		contentPane.add(inputPanel);
		contentPane.add(actionPanel);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screen.width - 500) / 2, ((screen.height - 350) / 2));
		setVisible(true);
	}
	
	
	
	
	//2
	public void checkLogin() {
		String user = userNameTextBox.getText().trim();
		String pass = new String(passwordTextBox.getPassword());
		
		boolean result = false;
		try {
			String line;
			BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir")+"/bin/user.csv"));
			while ((line = br.readLine()) != null) {
				String[] d = line.split(";");
				if (user.equals(d[0])) {
					if (pass.equals(d[1]))
						result = true;
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (result) {
			setVisible(false);
			MainWindow m = new MainWindow(user,new Date());
			m.setVisible(true);
			dispose();
		} else {
			JOptionPane.showMessageDialog(null, "User name or password is invalid", "WARNING!!",
					JOptionPane.WARNING_MESSAGE);

			passwordTextBox.setText("");

		}
	}

}
