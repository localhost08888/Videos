import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

class Ball extends Thread {
    private int x, y, diameter;
    private int speed;
    private Color color;
    private JPanel panel;
    private boolean movingDown = true;

    public Ball(JPanel panel) {
        this.panel = panel;
        Random random = new Random();

        // Randomize initial position, speed, and color
        this.x = random.nextInt(panel.getWidth() - 30); // Ensure the ball stays within the panel
        this.y = random.nextInt(panel.getHeight() - 30);
        this.diameter = 30;
        this.speed = random.nextInt(5) + 2; // Speed between 2 and 6
        this.color = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    @Override
    public void run() {
        while (true) {
            synchronized (panel) {
                // Update position
                if (movingDown) {
                    y += speed;
                    if (y + diameter >= panel.getHeight()) {
                        movingDown = false; // Reverse direction
                    }
                } else {
                    y -= speed;
                    if (y <= 0) {
                        movingDown = true; // Reverse direction
                    }
                }

                // Repaint the panel to show updated position
                panel.repaint();
            }

            // Delay for smoother animation
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, diameter, diameter);
    }
}

public class BallAnimation extends JFrame {
    private JPanel panel;
    private JButton startButton;
    private ArrayList<Ball> balls;

    public BallAnimation() {
        setTitle("Ball Animation");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel to draw balls
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw all balls
                for (Ball ball : balls) {
                    ball.draw(g);
                }
            }
        };
        panel.setBackground(Color.WHITE);
        add(panel, BorderLayout.CENTER);

        // Start Button
        startButton = new JButton("Start");
        startButton.addActionListener(e -> {
            Ball ball = new Ball(panel);
            balls.add(ball);
            ball.start(); // Start ball thread
        });
        add(startButton, BorderLayout.SOUTH);

        balls = new ArrayList<>();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BallAnimation frame = new BallAnimation();
            frame.setVisible(true);
        });
    }
}
