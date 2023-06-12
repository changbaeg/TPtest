import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class ClickExp2 extends JFrame implements ActionListener {
	private JLabel label, label1;
	private JButton button;
	static int count = 1;
	private int exp = 0;
//	static int level = 1;

	public ClickExp2() {
		JPanel panel = new JPanel();
		label = new JLabel("LEVEL");
		panel.add(label);

		label1 = new JLabel("" + count);
		label1.setFont(new Font("exp", Font.BOLD | Font.ITALIC, 50));
		panel.add(label1);

		JPanel panel_b = new JPanel();
		button = new JButton("Done");
		panel_b.add(button);
		panel.add(panel_b);
		button.addActionListener(this);
		add(panel);
		
		setSize(600, 600);
		setTitle("My Counter");
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == button) {
			exp++;
			if (exp > 5) {
				count++;
				label1.setText(count + "");
				exp = 0;
//				level = count;
			}
		}
	}

	public static void main(String args[]) {
		new ClickExp2();
	}
}
