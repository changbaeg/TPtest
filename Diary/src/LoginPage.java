import java.awt.CardLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class LoginPage extends JFrame {
	JPanel panel;
	LoginPage lp;
	CardLayout card;
	String id = null;

	private Connection con = null;

	final private String driver = "com.mysql.jdbc.Driver";
	final private String url = "jdbc:mysql://localhost:3306/diary?zeroDateTimeBehavior=convertToNull";
	final private String user = "root";
	final private String password = "1234";

	public static void main(String[] args) {

		LoginPage lp = new LoginPage();
		lp.setFrame(lp);
	}

	public void setFrame(LoginPage lpro) {

		LoginPanel lp = new LoginPanel(lpro);
		SingUpPanel sp = new SingUpPanel(lpro);

		card = new CardLayout();

		panel = new JPanel(card);
		panel.add(lp.mainPanel, "Login");
		panel.add(sp.mainPanel, "Register");

		add(panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 700);
		setVisible(true);
		setLocationRelativeTo(null);
	}

	public Connection getCon() throws SQLException {
		if (con == null) {
			connect();
			if (con == null) {// db접속불가 시 재시도
				getCon();
			}
		}
		return con;
	}

	private void connect() {
		if (con == null) {
			try {
				Class.forName(driver);
				this.con = DriverManager.getConnection(url, user, password);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			if (this.con == null) {
				System.out.println("DB 연결 실패");
			} else {
				System.out.println("DB 연결 성공");
			}
		}

	}

	void closeDB() {
		if (this.con != null) {
			try {
				this.con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void setid(String id) {
		this.id = id;
	}
}
