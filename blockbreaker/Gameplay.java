package blockbreaker;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener{
    private boolean play = false;
    private int score = 0;

    private int level = 1;

    private int rows = 3;
    private int cols = 7;

    private boolean isDead = false;

    private int totalBricks = 21;

    private Timer timer;
    private int delay = 8;

    private int playerXpos = 300;

    private int ballXpos = 300;
    private int ballYpos = 350;
    private int ballXdir = 1;
    private int ballYdir = 2;

    private MapGenerator map;

    public Learning learn;

    public Gameplay(){
        map = new MapGenerator(3, 7);
//        learn = new Learning();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g){
        //background
        g.setColor(Color.BLACK);
        g.fillRect(1, 1, 692, 592);

        //borders
        g.setColor(Color.WHITE);
        g.fillRect( 0, 0, 3, 592);
        g.fillRect( 0, 0, 692, 3);
        g.fillRect( 691, 0, 3, 592);

        //paddle
        g.setColor(Color.GREEN);
        g.fillRect(playerXpos, 550, 100, 8);

        //ball
        g.setColor(Color.WHITE);
        g.fillOval(ballXpos, ballYpos, 15, 15);

        //score
        g.setColor(Color.WHITE);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("" + score, 30, 30);

        //bricks
        map.draw(g);

        //levels title
        g.setColor(Color.WHITE);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("Level " + level, 300, 30);

        if(play == false &&  isDead == false){
            g.setColor(Color.WHITE);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Press arrow keys to play", 200, 350);
        }

        if(ballYpos > 570){
            ballXdir = 0;
            ballYdir = 0;
            play = false;
            isDead = true;
            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("You Died", 300, 350);
            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to Restart", 275, 375);
        }


    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        timer.start();
        if (new Rectangle(ballXpos, ballYpos, 15, 15).intersects(new Rectangle(playerXpos, 550, 100, 8))){
            ballYdir = -ballYdir;
            if(ballXpos - playerXpos >= 75){
                ballXdir = 1;
            }else if(ballXpos - playerXpos <= 25){
                ballXdir = -1;
            }else{
                ballXdir = ballXdir;
            }
        }

        repaint();

        if(play){

            A: for(int i = 0; i < map.map.length; i++){
                for(int j = 0; j < map.map[0].length; j++){
                    if (map.map[i][j] > 0){
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ball = new Rectangle(ballXpos, ballYpos, 15, 15);
                        Rectangle brickRect = rect;

                        if(ball.intersects(brickRect)){
                            map.setBrickValue(0, i, j);
                            totalBricks -= 1;
                            score += 5;

                            if(ballXpos + 19 <= brickRect.x || ballXpos + 1 >= brickRect.x + brickWidth){
                                ballXdir = -ballXdir;
                            }else {
                                ballYdir = -ballYdir;
                            }

                            if(totalBricks <= 0 && play){
                                rows += 1;
                                level += 1;
                                totalBricks = rows * cols;
                                map = new MapGenerator(cols, rows);
                            }

                            break A;
                        }
                    }
                }
            }
            ballXpos += ballXdir;
            ballYpos += ballYdir;
            if (ballXpos < 0){
                ballXdir = -ballXdir;
            }
            if (ballXpos > 670){
                ballXdir = -ballXdir;
            }
            if (ballYpos < 0){
                ballYdir = -ballYdir;
            }
            if (ballYpos > 670){
                ballYdir = -ballYdir;
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            if(playerXpos >= 600){
                playerXpos = 600;
            }else{
                moveRight();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            if(playerXpos <= 10){
                playerXpos = 10;
            }else{
                moveLeft();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            if(!play && isDead){
                ballXpos = 120;
                ballYpos = 350;
                ballXdir = 1;
                ballYdir = 2;
                score = 0;
                playerXpos = 310;
                totalBricks = 21;
                map = new MapGenerator(3,7);

                repaint();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }
    public void moveRight(){
        play = true;
        playerXpos += 20;
    }

    public void moveLeft(){
        play = true;
        playerXpos -= 20;
    }

}
