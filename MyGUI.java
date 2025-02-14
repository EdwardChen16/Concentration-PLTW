import javax.swing.JFrame;
    
public class MyGUI extends JFrame {
    public MyGUI() {
        setTitle("My First GUI");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MyGUI();
    }
}