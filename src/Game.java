import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Game extends JPanel implements KeyListener, ActionListener {

    ImageIcon plate = new ImageIcon("plate.png");
    JLabel plateP1;                     //for player 1
    JLabel plateP2;                     //for player 2
    Timer timer;                        //game Timer
    Random random = new Random();
    boolean play = true;

    static int ballPosX, ballPosY, ballSpeedX, ballSpeedY, ballSpeed;

    Game(){
        this.setBackground(Color.BLACK);
        this.setLayout(null);
        this.setFocusable(true);
        this.addKeyListener(this);

        plateP1 = new JLabel();
        plateP1.setIcon(plate);

        plateP2 = new JLabel();
        plateP2.setIcon(plate);

        defaultValues();

        //setting timer
        timer = new Timer(30,this);
        timer.start();

        //new AutoPlay(plateP2,2);
        //new AutoPlay(plateP1,1);

        this.add(plateP1);
        this.add(plateP2);
    }

    public void paint(Graphics g){

        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(Color.white);
        g2d.fillOval(ballPosX,ballPosY,30,30);

        if(!play){
            g2d.setPaint(Color.red);
            g2d.setFont(new Font(null,Font.BOLD,50));
            g2d.drawString("GAME OVER",250,250);
            g2d.setPaint(Color.white);
            g2d.drawString("PRESS SPACE TO RESTART",60,380);
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case 37-> {
               //left
            }
            case 39-> {
                //right
            }
            case 40-> {
                //down
                if(plateP1.getY()<405)
                plateP1.setBounds(plateP1.getX(),plateP1.getY()+18,10,60);
            }
            case 38-> {
                //up
                if(plateP1.getY()>5)
                plateP1.setBounds(plateP1.getX(),plateP1.getY()-18,10,60);
            }

            case 87-> {
                //W
                if(plateP2.getY()>5)
                    plateP2.setBounds(plateP2.getX(),plateP2.getY()-18,10,60);
            }

            case 83-> {
                //S
                if(plateP2.getY()<405)
                    plateP2.setBounds(plateP2.getX(),plateP2.getY()+18,10,60);
            }
            case 32-> {
                //space
                if(!play){
                    play = true;
                    defaultValues();
                    timer.start();
                }
            }

            case 88-> {
                //X
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    // All game actions
    @Override
    public void actionPerformed(ActionEvent e) {

        if(new Rectangle(plateP1.getX(),plateP1.getY(),plateP1.getWidth(),plateP1.getHeight()).intersects(new Rectangle(ballPosX,ballPosY,30,30))){
            ballSpeedX*=-1;
        }

        if(new Rectangle(plateP2.getX(),plateP2.getY(),plateP2.getWidth(),plateP2.getHeight()).intersects(new Rectangle(ballPosX,ballPosY,30,30))){
            ballSpeedX*=-1;
        }

        ballPosX -= ballSpeedX;

        //lose case
        if(ballPosX<10) {
            play = false;
            timer.stop();
        }

        //win case
        if(ballPosX>770){
            play = false;
            timer.stop();
        }

        //for Y-Direction
        if(ballPosY<2) {
            ballPosY+=ballSpeedY;
            ballSpeedY*=-1;
        }
        else if(ballPosY<440) ballPosY-=ballSpeedY;
        else {
            ballPosY+=ballSpeedY;
            ballSpeedY*=-1;
        }

        repaint();
    }

    private void defaultValues(){
        ballSpeed=6;
        plateP1.setBounds(20,170,10,60);
        plateP2.setBounds(770,250,10,60);
        ballPosX=random.nextInt(200,400);
        ballPosY=200;
        ballSpeedX=-ballSpeed;
        ballSpeedY=ballSpeed;
    }
}
