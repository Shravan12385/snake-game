import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener {
    int b_height = 400, b_width = 400;
    int MAX_SIZE = 1600;
    int DOT_SIZE = 10;
    int DOT;

    int x[] = new int[MAX_SIZE];
    int y[] = new int[MAX_SIZE];


    //apple
    int apple_x;
    int apple_y;

    //
    Image body, head, Apple;

    //timer
    Timer timer;
    int Delay = 300;

    boolean leftDirection = true;
    boolean rightDirection = false;

    boolean upDirection = false;

    boolean downDirection = false;

    boolean inGame = true;

    Board() {
        TAdapter tAdapter = new TAdapter();
        addKeyListener(tAdapter);
        setFocusable(true);
        setPreferredSize(new Dimension(b_width, b_height));
        setBackground(Color.black);
        initgame();
        loadimage();
    }

    //intitalize the game
    public void initgame() {
        DOT = 3;
        x[0] = 250;
        y[0] = 250;

        //intitalising snake position
        for (int i = 1; i < DOT; i++) {
            x[i] = x[0] + DOT_SIZE * i;
            y[i] = y[0];
        }

        //intialising Apple

//        apple_x = 150;
//        apple_y = 150;
        locatApple();

        //timer
        timer = new Timer(Delay, this);
        timer.start();


    }

    //load images from resource file to image object
    public void loadimage() {
        ImageIcon bodyicon = new ImageIcon("src/resources/dot.png");

        body = bodyicon.getImage();

        ImageIcon headicon = new ImageIcon("src/resources/head.png");

        head = headicon.getImage();

        ImageIcon Appleicon = new ImageIcon("src/resources/apple.png");

        Apple = Appleicon.getImage();

    }

    //drag the positions
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);

    }

    //drag image
    public void doDrawing(Graphics g) {
        if(inGame)
        {
            g.drawImage(Apple, apple_x, apple_y, this);

            for (int i = 0; i < DOT; i++) {
                if (i == 0) {
                    g.drawImage(head, x[0], y[0], this);
                } else {
                    g.drawImage(body, x[i], y[i], this);
                }
            }
        }
        else {
            gameOver(g);
            timer.stop();
        }
    }

    //locate of apple in random

    public void locatApple() {
        apple_x = ((int) (Math.random() * 39)) * DOT_SIZE;

        apple_y = ((int) (Math.random() * 39)) * DOT_SIZE;

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(inGame)
        {
            eatApple();
            move();
            checkCollision();
        }

        repaint();

    }

    //snake move
    public void move() {
        for (int i = DOT - 1; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        if (leftDirection) {
            x[0] -= DOT_SIZE;
        }
        if (rightDirection) {
            x[0] += DOT_SIZE;
        }

        if (upDirection) {
            y[0] -= DOT_SIZE;
        }
        if (downDirection) {
            y[0] += DOT_SIZE;
        }


    }

    //game over
    private void gameOver(Graphics g) {
        String msg = "Game Over!";
        int score=(DOT-3)*10;
        String scoremsg="Final Score : "+Integer.toString(score);
        Font font = new Font("helvetica", Font.BOLD, 24);
        FontMetrics metrics = getFontMetrics(font);
        g.setColor(Color.white);
        g.setFont(font);
        g.drawString(msg, (b_width - metrics.stringWidth(msg)) / 2, b_height/ 4);
        g.drawString(scoremsg, (b_width- metrics.stringWidth(scoremsg)) / 2, 3*b_height / 4);
    }

    //apple
    public  void eatApple()
    {
        if(apple_x==x[0] &&apple_y==y[0])
        {
            DOT++;
            locatApple();
        }
    }

    private void checkCollision(){
        //Collision with left border
        if(x[0]<0){
            inGame = false;
        }
        //Collision with right border
        if(x[0]>=b_width){
            inGame = false;
        }
        //Collision with up border
        if(y[0]<0){
            inGame = false;
        }
        //collision with down border
        if(y[0]>=b_height){
            inGame = false;
        }

        for(int i = 4;i<DOT;i++){
            if(x[0]==x[i]&&y[0]==y[i]){
                inGame = false;
            }
        }
    }

    //implements controls
    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent keyEvent) {
            int key = keyEvent.getKeyCode();
            if (key == KeyEvent.VK_LEFT && (!rightDirection)) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if (key == KeyEvent.VK_RIGHT && (!leftDirection)) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if (key == KeyEvent.VK_UP && (!downDirection)) {
                leftDirection = false;
                upDirection = true;
                rightDirection = false;
            }
            if (key == KeyEvent.VK_DOWN && (!upDirection)) {
                leftDirection = false;
                rightDirection = false;
                downDirection = true;
            }

        }
    }
}


