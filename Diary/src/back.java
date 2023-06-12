import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class back extends JFrame implements ActionListener/* , KeyListener */ {
	JPanel panel/* , topPanel */;
	Border border;
	JLabel label;
	Image icon;
	Image no1;
	int x = 250, y = 250;
//	int b = 1;
	

	public back() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 800);
		setVisible(true);
		setLocationRelativeTo(null);
//		addKeyListener(this);
		icon = new ImageIcon("img\\background.png").getImage();
		label = new JLabel();
		panel = new JPanel(new GridLayout(0, 3));

//버튼생성 후 패널에 추가
//		addpanel("LEVEL", panel);
		addButton("mission", panel);
		addButton("closet", panel);
		addButton("calender", panel);
//패널 추가
		add(panel, "North");
		border = new Border();
		border.add(label);
		add(border, "Center");
		border.setLayout(null);

	}

//버튼생성, 이벤트실행
	void addButton(String str, Container target) {
		JButton button = new JButton(str);
		button.addActionListener(this);
		target.add(button);
	}

//이벤트 메소드
	@SuppressWarnings("unused")
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("mission")) {
			new TodoFrame();
		} else if (e.getActionCommand().equals("closet")) {
			new Closet();
		} else if (e.getActionCommand().equals("calender")) {
			MemoCalendar a = new MemoCalendar();
		}
	}

	class Border extends JPanel {
		BorderLayout layout;

		public Border() {
			layout = new BorderLayout();
			setLayout(layout);
	}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			no1 = new ImageIcon(Closet.a).getImage();
			Dimension d = getSize();
			g.drawImage(icon, 0, 0, d.width, d.height, null);
			g.drawImage(no1, 325, 300, 130, 180, null);
			repaint();
			// 여기에 캐릭터 움직이는 코드를 넣어야하는가
		}
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		back b = new back();
	}
}