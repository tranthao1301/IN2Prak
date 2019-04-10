package informatik.haw_hamburg.de.IN2Prak;

import java.sql.*;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

public class Connect {
	static String connectionURL = "jdbc:oracle:thin:@ora14.informatik.haw-hamburg.de:1521:inf14";
	static String userName;
	static String password;
	Connection con = null;

	public Connection connect() {

		String[] data = getUserData();
		userName = data[0];
		password = data[1];
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			con = DriverManager.getConnection(connectionURL, userName, password);
			if (con != null) {
				System.out.println("!! Connected With Oracle Database !!\n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return con;

	}

	private static String[] getUserData() {
		String[] userData = new String[2];
		JPasswordField pswField = new JPasswordField();
		pswField.setEchoChar('*');
		userData[0] = JOptionPane.showInputDialog("Please enter userid!");
		Object[] message = { "Please enter password!\n", pswField };
		int resp = JOptionPane.showConfirmDialog(null, message, "Retrieve Password", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		if (resp == JOptionPane.OK_OPTION) {
			userData[1] = new String(pswField.getPassword());
		} else {
			System.exit(-1);
		}
		return userData;
	}

}
