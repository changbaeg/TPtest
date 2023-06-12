import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.io.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TodoFrame {
	private JFrame frame;
	private JPanel mainPanel;
	private JTextField newItemTextField;
	private JButton addButton;
	private JList<String> toDoList;
	private DefaultListModel<String> listModel;
	private JButton completeButton;
	private JLabel levelLabel, countLabel;
	public int level;
	private int completionCount;
	public static int count = 0;
	private final String dataFolder = "data";
	private final String dataFile = "data\\todolist.txt";

	public TodoFrame() {
		frame = new JFrame("MISSION");
//      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 600);
		frame.setLocationRelativeTo(null);

		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		newItemTextField = new JTextField();
		newItemTextField.setPreferredSize(new Dimension(200, 30));

		addButton = new JButton("Add");
		addButton.addActionListener(new AddButtonListener());

		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new FlowLayout());
		inputPanel.add(newItemTextField);
		inputPanel.add(addButton);

		listModel = new DefaultListModel<>();
		toDoList = new JList<>(listModel);
		JScrollPane listScrollPane = new JScrollPane(toDoList);
		listScrollPane.setBorder(BorderFactory.createTitledBorder("To Do List"));
		listScrollPane.setPreferredSize(new Dimension(360, 300));

		completeButton = new JButton("Complete");
		completeButton.addActionListener(new CompleteButtonListener());
		completeButton.setEnabled(false);

		countLabel = new JLabel("" + count);
		levelLabel = new JLabel("Level: " + level);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.add(completeButton);
		buttonPanel.add(levelLabel);

		mainPanel.add(inputPanel, BorderLayout.NORTH);
		mainPanel.add(listScrollPane, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);

		frame.getContentPane().add(mainPanel);
		frame.setVisible(true);

		// createDataFolder(); DB 연결 되면 삭제
	}

	private class AddButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String newItem = newItemTextField.getText();
			if (!newItem.isEmpty()) {
				listModel.addElement(newItem);
				newItemTextField.setText("");
				completeButton.setEnabled(true);
			}
		}
	}

	public class CompleteButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int selectedIndex = toDoList.getSelectedIndex();
			if (selectedIndex != -1) {
				listModel.remove(selectedIndex);
				completionCount++;
				if (completionCount % 5 == 0) {
					level++;
					count++;
					levelLabel.setText("Level: " + level);
				}
				if (listModel.isEmpty()) {
					completeButton.setEnabled(false);
				}
			}
		}
	}

	/*
	 * // 파일에 저장 (DB 연동 되면 삭제) private void saveData() { try (PrintWriter writer =
	 * new PrintWriter(new FileWriter(dataFile))) { for (int i = 0; i <
	 * listModel.getSize(); i++) { writer.println(listModel.getElementAt(i)); }
	 * writer.println("Level=" + level); writer.println("CompletionCount=" +
	 * completionCount); System.out.println("데이터가 파일에 저장되었습니다."); } catch
	 * (IOException e) { System.out.println("데이터를 저장하는 동안 오류가 발생했습니다.");
	 * e.printStackTrace(); } }
	 * 
	 * private void loadData() { try (BufferedReader reader = new BufferedReader(new
	 * FileReader(dataFile))) { String line; while ((line = reader.readLine()) !=
	 * null) { if (line.startsWith("Level=")) { level =
	 * Integer.parseInt(line.substring(line.indexOf("=") + 1));
	 * levelLabel.setText("Level: " + level); } else if
	 * (line.startsWith("CompletionCount=")) { completionCount =
	 * Integer.parseInt(line.substring(line.indexOf("=") + 1)); } else {
	 * listModel.addElement(line); } } if (!listModel.isEmpty()) {
	 * completeButton.setEnabled(true); } } catch (FileNotFoundException e) { // 파일이
	 * 존재하지 않을 경우 무시 } catch (IOException e) { e.printStackTrace(); } }
	 * 
	 * // 파일 생성 함수 private void createDataFolder() { File folder = new
	 * File(dataFolder); if (!folder.exists()) { try { if (folder.mkdir()) {
	 * System.out.println("데이터 폴더가 생성되었습니다."); } else {
	 * System.out.println("데이터 폴더 생성에 실패했습니다."); } } catch (SecurityException e) {
	 * System.out.println("데이터 폴더를 생성할 권한이 없습니다."); e.printStackTrace(); } } }
	 */

	
	  public static void main(String[] args) { SwingUtilities.invokeLater(new
	  Runnable() { public void run() { new TodoFrame(); } }); }
	 

}