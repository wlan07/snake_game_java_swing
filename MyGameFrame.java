import javax.swing.JFrame;

public class MyGameFrame extends JFrame {

    MyGameFrame() {

        this.add(new MyGamePanel());

        this.setAlwaysOnTop(true);
        this.setTitle("Snake Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setResizable(false);

        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

    }

}
