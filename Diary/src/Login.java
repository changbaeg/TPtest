import java.awt.CardLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Login extends JFrame {
    public JPanel panel;
    public CardLayout card;
    private String id;

    private Connection con;

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/diary";
    private static final String USER = "root";
    private static final String PASSWORD = "4011"; // 공백인 이유?

    public static void main(String[] args) {
        Login lp = new Login();
        lp.setFrame();
    }

    public Login() {
        connect();
    }

    public void setFrame() {
        LoginPanel lp = new LoginPanel(this);
        SignUpPanel sp = new SignUpPanel(this);

        card = new CardLayout();
        panel = new JPanel(card);
        panel.add(lp.mainPanel, "Login");
        panel.add(sp.getMainPanel(), "Register");

        add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 700);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    public Connection getCon() throws SQLException {
        if (con == null || con.isClosed()) {
            connect();
        }
        return con;
    }

    private void connect() {
        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("DB 연결 성공");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC 드라이버를 찾을 수 없음");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("DB 연결 실패");
            e.printStackTrace();
        }
    }

    public void closeDB() {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void setId(String id) {
        this.id = id;
    }

    class LoginPanel extends JPanel {
        JPanel mainPanel;
        JTextField idText;
        JPasswordField passText;
        char[] password;

        String userMode = "관리자";
        Login lp;
        Font font = new Font("돋움", Font.PLAIN, 50);
        String admin = "admin";

        public LoginPanel(Login lp) {
            this.lp = lp;

            mainPanel = new JPanel();
            mainPanel.setLayout(new GridLayout(5, 1));

            JPanel centerPanel = new JPanel();
            JLabel loginLabel = new JLabel("로그인 화면");
            loginLabel.setFont(font);
            centerPanel.add(loginLabel);

            JPanel userPanel = new JPanel();

            JPanel gridBagidInfo = new JPanel(new GridBagLayout());
            gridBagidInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel idLabel = new JLabel("ID: ");
            idText = new JTextField(10);
            GridBagConstraints idConstraints = new GridBagConstraints();
            idConstraints.gridx = 0;
            idConstraints.gridy = 0;
            idConstraints.anchor = GridBagConstraints.WEST;
            idConstraints.insets = new Insets(10, 0, 10, 0);
            gridBagidInfo.add(idLabel, idConstraints);

            idConstraints.gridx = 1;
            gridBagidInfo.add(idText, idConstraints);

            JPanel gridBagPassInfo = new JPanel(new GridBagLayout());
            gridBagPassInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel passLabel = new JLabel("PW: ");
            passText = new JPasswordField(10);
            GridBagConstraints passConstraints = new GridBagConstraints();
            passConstraints.gridx = 0;
            passConstraints.gridy = 0;
            passConstraints.anchor = GridBagConstraints.WEST;
            passConstraints.insets = new Insets(10, 0, 10, 0);
            gridBagPassInfo.add(passLabel, passConstraints);

            passConstraints.gridx = 1;
            gridBagPassInfo.add(passText, passConstraints);

            userPanel.add(gridBagidInfo);
            userPanel.add(gridBagPassInfo);

            JPanel bottomPanel = new JPanel();
            JButton loginButton = new JButton("로그인");
            JButton registerButton = new JButton("회원가입");

            bottomPanel.add(loginButton);
            bottomPanel.add(registerButton);

            mainPanel.add(centerPanel);
            mainPanel.add(userPanel);
            mainPanel.add(bottomPanel);

            // 로그인 버튼에 ActionListener 추가
            loginButton.addActionListener((event) -> {
                login();
            });

            // 회원가입 버튼에 ActionListener 추가
            registerButton.addActionListener((event) -> {
                card.show(panel, "Register");
            });
        }

        private void login() {
            String id = idText.getText();
            password = passText.getPassword();
            String pass = new String(password);

            if (id.equals(admin) && pass.equals(admin)) {
                JOptionPane.showMessageDialog(this, "관리자로 로그인 되었습니다.");
                Home cm = new Home();
                cm.run();
                
                lp.card.show(lp.panel, "Home"); // Show the CharacterMovement panel
            } else {
                try {
                    String query = "SELECT * FROM user WHERE id=?";
                    PreparedStatement pstmt = lp.getCon().prepareStatement(query);
                    pstmt.setString(1, id);
                    ResultSet rs = pstmt.executeQuery();

                    if (rs.next()) {
                        String storedPass = rs.getString("password");
                        if (pass.equals(storedPass)) {
                            JOptionPane.showMessageDialog(this, "로그인에  성공하였습니다.");
                            lp.setId(id);
                            Home cm = new Home();
                            cm.run();
                            lp.card.show(lp.panel, "Home"); // Show the CharacterMovement panel
                        } else {
                            JOptionPane.showMessageDialog(this, "비밀번호가 일치하지 않습니다.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "일치하는 ID가 없습니다.");
                    }

                    rs.close();
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class SignUpPanel {
        JPanel mainPanel;
        JButton registerButton; // registerButton을 필드로 이동

        public SignUpPanel(Login lp) {
            mainPanel = new JPanel();
            mainPanel.setLayout(new GridLayout(5, 1));

            JPanel centerPanel = new JPanel();
            JLabel loginLabel = new JLabel("회원가입 화면");
            loginLabel.setFont(new Font("돋움", Font.PLAIN, 50));
            centerPanel.add(loginLabel);

            JPanel userPanel = new JPanel();

            JPanel gridBagidInfo = new JPanel(new GridBagLayout());
            gridBagidInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel idLabel = new JLabel("ID: ");
            JTextField idText = new JTextField(10);
            GridBagConstraints idConstraints = new GridBagConstraints();
            idConstraints.gridx = 0;
            idConstraints.gridy = 0;
            idConstraints.anchor = GridBagConstraints.WEST;
            idConstraints.insets = new Insets(10, 0, 10, 0);
            gridBagidInfo.add(idLabel, idConstraints);

            idConstraints.gridx = 1;
            gridBagidInfo.add(idText, idConstraints);

            JPanel gridBagPassInfo = new JPanel(new GridBagLayout());
            gridBagPassInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel passLabel = new JLabel("PW: ");
            JPasswordField passText = new JPasswordField(10);
            GridBagConstraints passConstraints = new GridBagConstraints();
            passConstraints.gridx = 0;
            passConstraints.gridy = 0;
            passConstraints.anchor = GridBagConstraints.WEST;
            passConstraints.insets = new Insets(10, 0, 10, 0);
            gridBagPassInfo.add(passLabel, passConstraints);

            passConstraints.gridx = 1;
            gridBagPassInfo.add(passText, passConstraints);

            userPanel.add(gridBagidInfo);
            userPanel.add(gridBagPassInfo);

            JPanel bottomPanel = new JPanel();
            registerButton = new JButton("회원가입");
            JButton backButton = new JButton("뒤로 가기");

            bottomPanel.add(registerButton);
            bottomPanel.add(backButton);

            mainPanel.add(centerPanel);
            mainPanel.add(userPanel);
            mainPanel.add(bottomPanel);

            // 회원가입 버튼에 ActionListener 추가
            registerButton.addActionListener((event) -> {
                String id = idText.getText();
                char[] password = passText.getPassword();
                String pass = new String(password);

                //  유효성 검사
                if (id.isEmpty() || pass.isEmpty()) {
                    JOptionPane.showMessageDialog(this.mainPanel, "ID, PW 모두 입력하세요.");
                    return;
                }

                try {
                    String query = "INSERT INTO user(id, password) VALUES(?, ?)";
                    PreparedStatement pstmt = lp.getCon().prepareStatement(query);
                    pstmt.setString(1, id);
                    pstmt.setString(2, pass);
                    int rowsAffected = pstmt.executeUpdate();
                    pstmt.close();

                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(this.mainPanel, "회원가입에 성공하였습니다.");
                        card.show(panel, "Login");
                    } else {
                        JOptionPane.showMessageDialog(this.mainPanel, "회원가입에 실패하였습니다.");
                    }
                } catch (SQLException e) {
                    if (e.getErrorCode() == 1062) {
                        JOptionPane.showMessageDialog(this.mainPanel, "이미 존재하는 ID입니다.");
                    } else {
                        JOptionPane.showMessageDialog(this.mainPanel, "회원가입 중에 오류가 발생하였습니다.");
                    }
                    e.printStackTrace();
                }
            });

            // 뒤로가기 버튼에 ActionListener 추가
            backButton.addActionListener((event) -> {
                card.show(panel, "Login");
            });
        }

        public JPanel getMainPanel() {
            return mainPanel;
        }
    }
}