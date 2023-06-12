import javax.swing.*; //윈도우창 띄울 때 사용되는 모듈

import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class GameMain extends JFrame implements KeyListener {
	Image back, rocket;
	int x = 250, y = 250;
	JScrollPane scrollPane;

	@SuppressWarnings("unused")
	public GameMain() { // 생성자
//		add("Center", gv);
		GameView();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 600);
		setVisible(true);
		addKeyListener(this);
		
		JPanel panel = new JPanel((LayoutManager) back);
		
		JLabel panel2 = new JLabel((Icon) rocket);
		panel.add(panel2);
		
		JButton button1 = new JButton("mission");
		panel.add(button1);
		scrollPane = new JScrollPane();
		setContentPane(scrollPane);
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ClickExp2(); //미션 클래스 호출
			}
		});
	}

	public static void main(String[] args) {
		GameMain gm = new GameMain();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == this) {
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				y -= 5;
				if (y < 0)
					y = 600;
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				y += 5;
				if (y > 600)
					y = 0;

			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				x -= 5;
				if (x < 0)
					x = 600;
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				x += 5;
				if (x > 600)
					x = 0;
			}
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				System.exit(0);
			}
			repaint();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	public void GameView() {
		back = Toolkit.getDefaultToolkit().getImage("C:.\\background.jpg");
		rocket = Toolkit.getDefaultToolkit().getImage("C:.\\rpg.png");
	}

	// 이미지 출력
	public void paint(Graphics g) {
		g.drawImage(back, 0, 0, getWidth(), getHeight(), this);
		g.drawImage(rocket, x, y, 100, 150, this);
	}
}