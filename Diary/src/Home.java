import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class Home extends JFrame implements ActionListener/* , KeyListener */ {
	JPanel panel/* , topPanel */;
	Border border;
	//JLabel label;
	Image icon;
	//Image no1;
	int x = 250, y = 250;
//	int b = 1;
	
	//캐릭터 이동 코드 new 선언
    private JLabel characterLabel;
    //private int x, y;

    private static final int MOVE_AMOUNT = 10;
	//~

	public Home() { // mainFrame 호출! -> setting
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 800);
		setVisible(true);
		setLocationRelativeTo(null);
//		addKeyListener(this);
		icon = new ImageIcon("img\\background.png").getImage();
		//label = new JLabel();
		panel = new JPanel(new GridLayout(0, 3));

//버튼생성 후 패널에 추가
//		addpanel("LEVEL", panel);
		addButton("mission", panel);
		addButton("closet", panel);
		addButton("calender", panel);
//패널 추가
		add(panel, "North");
		border = new Border();
		border.add(characterLabel);
		add(border, "Center");
		border.setLayout(null);
		
        ImageIcon settingsIcon = new ImageIcon("img\\setting.png"); // 설정 이미지 파일 경로에 맞게 수정
        settingsIcon = new ImageIcon(settingsIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)); // 아이콘 크기 조정
        JLabel settingsLabel = new JLabel(settingsIcon);

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

		public void add(Image characterLabel) {
			// TODO Auto-generated method stub
			
		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			characterLabel = (JLabel)new JLabel(Closet.a).getIcon();
			// 의문점: a라는 이미지는 레벨1 이미지인데 홈화면에 a이미지만 나오게 되는거 아닌가?
			characterLabel.setSize(50, 50);
			//~
			Dimension d = getSize();
			g.drawImage(icon, 0, 0, d.width, d.height, null);//배경
			//g.drawImage(characterLabel, 325, 300, 130, 180, null); //캐릭터

			
			// 캐릭터 움직이는 코드 NEW,,
			x = 175;  // 초기 위치 설정
	        y = 175;
	        characterLabel.setLocation(x, y);
	        add(characterLabel);

	        addKeyListener(new KeyAdapter() {
	            @Override
	            public void keyPressed(KeyEvent e) {
	                int keyCode = e.getKeyCode();
	                moveCharacter(keyCode);
	            }
	        });

	        setFocusable(true);
	        requestFocus();

	        setVisible(true); } }
	        
	        private void moveCharacter(int keyCode) {
	            int dx = 0;
	            int dy = 0;

	            switch (keyCode) {
	                case KeyEvent.VK_W:
	                    dy = -MOVE_AMOUNT;
	                    break;
	                case KeyEvent.VK_S:
	                    dy = MOVE_AMOUNT;
	                    break;
	                case KeyEvent.VK_A:
	                    dx = -MOVE_AMOUNT;
	                    break;
	                case KeyEvent.VK_D:
	                    dx = MOVE_AMOUNT;
	                    break;
	            }

	            x += dx;
	            y += dy;
	            extracted();
	            //여기까지 new 이동 코드
			repaint();
			
	 }

			private void extracted() {
				characterLabel.setLocation(x, y);
			}
	

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Home b = new Home();
	}

	public void run() {
		// TODO Auto-generated method stub
		
	}
}