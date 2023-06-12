import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TodoFrame extends JFrame {

    public TodoFrame() {
        setSize(800, 400);
        setTitle("MISSION");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setContentPane(new TodoPanel());
        setVisible(true);

    }

}