import javafx.scene.input.KeyCode;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.io.*;

public class TodoPanel extends JPanel implements ActionListener{

    JPanel inputPanel;
    JTextArea todoListArea, finishListArea;
    JTextField todoInputField;
    JButton addTodo, finishTodo;
    //saveTodo;
    JLabel label, label1; 

    ArrayList<String> todoList;
	public static int count = 1; 
	public int exp = 0;


    public TodoPanel() {
        initComponent();
        setLayout(new BorderLayout());
        setComponentTitleAndBorder();
        locatedComponent();
    }

    private void initComponent() {

        inputPanel = new JPanel(new FlowLayout());
        inputPanel.setBorder(new TitledBorder("Input Area"));

        todoListArea = new JTextArea();
        finishListArea = new JTextArea();
        todoInputField = new JTextField();

        addTodo = new JButton("add");
        finishTodo = new JButton("finish");
       // saveTodo = new JButton("save");
        
 
		label = new JLabel("LEVEL");
		inputPanel.add(label);

		label1 = new JLabel("" + count);
		label1.setFont(new Font("exp", Font.BOLD | Font.ITALIC, 50));
		inputPanel.add(label1);
 
        todoInputField.setPreferredSize(new Dimension(400, 50));

        addTodo.addActionListener(this);
        finishTodo.addActionListener(this);
     //   saveTodo.addActionListener(this);

        todoList = new ArrayList<String>();
       

    }

    

    // TextArea 
    private void setComponentTitleAndBorder() {
        todoListArea.setText("====== Today's To-Do List ======\n");
        todoListArea.setBorder(new TitledBorder("todoList"));

        finishListArea.setText("====== Today's Finish List ======\n");
        finishListArea.setBorder(new TitledBorder("finishList"));
    }


    private void locatedComponent() {
        add(todoListArea, BorderLayout.WEST);
        add(finishListArea, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        inputPanel.add(todoInputField); 
        inputPanel.add(addTodo);
        inputPanel.add(finishTodo);
       // inputPanel.add(saveTodo);
    }
 
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(addTodo)) {
            try {
				addTask(todoInputField.getText());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            
        } else if (e.getSource().equals(finishTodo)){
            try {
				finishTask(todoInputField.getText());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        } else { 
        	//FileSave1(todoInputField.getText());
        }
    }

    private void addTask(String string) throws IOException {

        if (!isEmpty(string)) {
            appendAndSettingText(string, todoListArea);
            JTextComponent bw;
            //bw.writer(string);
            //((PrintStream) bw).newLine();
            //Object clone = bw.clone();

            BufferedReader br = null;
            try { 
              br=new BufferedReader(new FileReader("C:\\dairy_save\\addTask.txt"));
              String line=null;

              while((line = br.readLine()) != null) {
               System.out.println(line); }
             }catch (FileNotFoundException e) {
              System.out.println("파일을 찾지 못했습니다.");
             }catch (Exception e) {
              e.printStackTrace();
             }finally {
              if (br != null) {
               br.close();
              }
             }
        }
       // String task;
        todoList.add(string);
    }

    private void finishTask(String task) throws IOException {

        if (isExist(task)) {
            appendAndSettingText(task, finishListArea);
            BufferedWriter bw = new BufferedWriter(new FileWriter("test.txt",true));
            bw.write(task);
            bw.newLine();
            bw.close();

            BufferedReader br = null;
            try { 
              br=new BufferedReader(new FileReader("C:\\dairy_save\\finishTask.txt"));
              String line=null;

              while((line = br.readLine()) != null) {
               System.out.println(line); }
             }catch (FileNotFoundException e) {
              System.out.println("파일을 찾지 못했습니다.");
             }catch (Exception e) {
              e.printStackTrace();
             }finally {
              if (br != null) {
               br.close();
              }
             }

        }

			exp++;
			if (exp > 5) {
				count++;
				label1.setText(count + "");
				exp = 1;
			}
        deleteTaskFromTodoList(task);
        todoList.add(task);
        todoList.remove(task);
        

    }


	//@Override


    private boolean isExist(String string) {
        return todoList.contains(string);
    }

    private boolean isEmpty(String string) {
        return string.equals("");
    }

    private void appendAndSettingText(String task, JTextArea ListArea) {
        ListArea.append(task + "\n");
        todoInputField.setText("");
    }

    private void deleteTaskFromTodoList(String text) {

        String todoListAreaText = todoListArea.getText();

        if (todoListAreaText.contains(text)) {
            System.out.println("LEVEL UP!"+"level:"+exp);
            todoListAreaText = todoListAreaText.replace(text, "");
            todoListArea.setText(todoListAreaText);
        }
    }

}