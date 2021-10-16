import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MyGamePanel extends JPanel {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final String GAME_OVER_TEXT = "Game Over";
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static final int DELAY = 250;
    final int[] x = new int[GAME_UNITS];
    final int[] y = new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;
    ActionListener taskPerformer;

    MyGamePanel() {

        random = new Random();

        taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (running) {

                    move();
                    checkApple();
                    checkCollisions();

                }

                repaint();
            }

        };

        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());

        // START THE GAME
        startGame();

    }

    public void startGame() {

        newApple();
        running = true;
        timer = new Timer(DELAY, taskPerformer);

        timer.start();

    }

    public void newApple() {
        appleX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void move() {

        for (int i = bodyParts; i > 0; i--) {

            x[i] = x[i - 1];
            y[i] = y[i - 1];

        }

        switch (direction) {
        case 'U':
            y[0] = y[0] - UNIT_SIZE;
            break;
        case 'D':
            y[0] = y[0] + UNIT_SIZE;
            break;
        case 'L':
            x[0] = x[0] - UNIT_SIZE;
            break;
        case 'R':
            x[0] = x[0] + UNIT_SIZE;
            break;
        }

    }

    public void draw(Graphics g) {

        if (running) {

            // * DRAW THE GRID
            /*
             * for (int i = 0; i < (SCREEN_HEIGHT / UNIT_SIZE); i++) {
             * 
             * g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
             * 
             * g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
             * 
             * }
             */
            
            // * DRAW THE APPLE
            g.setColor(Color.red);
            g.drawOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            // * DRAW THE SNAKE PARTS
            for (int i = 0; i < bodyParts; i++) {

                if (i == 0) {

                    g.setColor(Color.green);

                    g.fillRect(x[0], y[0], UNIT_SIZE, UNIT_SIZE);
                } else {

                    g.setColor(Color.red);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);

                }

            }

            // * DRAW THE NB OF EATEN APPLES

            g.setColor(Color.red);

            g.setFont(new Font("Ink Free", Font.BOLD, 40));

            FontMetrics metrics = getFontMetrics(g.getFont());

            final String score = "Score : " + applesEaten;

            g.drawString(score, (SCREEN_WIDTH - metrics.stringWidth(score)) / 2, g.getFont().getSize());

        } else {

            gameover(g);

        }

    }

    public void gameover(Graphics g) {

        g.setColor(Color.red);

        g.setFont(new Font("Ink Free", Font.BOLD, 75));

        FontMetrics metrics = getFontMetrics(g.getFont());

        // * DRAW SCORE IN THE TOP CENTER OF THE PANEL

        final String score = "Score : " + applesEaten;

        g.drawString(score, (SCREEN_WIDTH - metrics.stringWidth(score)) / 2, g.getFont().getSize());

        // * DRAW GAME OVER TEXT IN THE CENTER OF THE PANEL

        g.drawString(GAME_OVER_TEXT, (SCREEN_WIDTH - metrics.stringWidth(GAME_OVER_TEXT)) / 2, SCREEN_HEIGHT / 2);

    }

    public void checkApple() {

        if (x[0] == appleX && y[0] == appleY) {
            bodyParts++;
            applesEaten++;
            newApple();
        }

    }

    public void checkCollisions() {

        // * HEAD WITH A BODY PART COLLISION CHECKING
        for (int i = bodyParts; i > 0; i--) {

            if (x[0] == x[i] && y[0] == y[i]) {

                running = false;

            }

        }
        // * HEAD TOUCH LEFT BORDER CHECKING
        if (x[0] < 0) {
            running = false;
        }
        // * HEAD TOUCH RIGHT BORDER CHECKING
        if (x[0] > SCREEN_WIDTH) {
            running = false;
        }
        // * HEAD TOUCH TOP BORDER CHECKING
        if (y[0] < 0) {
            running = false;
        }
        // * HEAD TOUCH BOTTOM BORDER CHECKING
        if (y[0] > SCREEN_HEIGHT) {
            running = false;
        }

        if (!running) {

            timer.stop();

        }

    }

    public class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if (direction != 'R') {
                    direction = 'L';
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (direction != 'L') {
                    direction = 'R';
                }
                break;
            case KeyEvent.VK_UP:
                if (direction != 'D') {
                    direction = 'U';
                }
                break;
            case KeyEvent.VK_DOWN:
                if (direction != 'U') {
                    direction = 'D';
                }
                break;
            }
        }

    }

}
