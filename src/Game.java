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
    Timer timer;                        //game Timerz
    Random random = new Random();
    AutoPlay autoPlay;
    AutoPlay sim;
    boolean play = true, doublePlayer=false, simulate=false;

    static int ballPosX, ballPosY, ballSpeedX, ballSpeedY, ballSpeed=6;

    // for animation
    boolean moveDown = false; int downTarget=0;
    boolean moveUp = false; int upTarget=0;

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

        autoPlay = new AutoPlay(plateP2,2);
        sim = new AutoPlay(plateP1,1);


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
        }else if(!simulate) {
            g2d.setFont(new Font(null,Font.BOLD,10));
            switch (ballSpeed){
                case 2-> g2d.drawString("Speed: "+1,720,450);
                case 4-> g2d.drawString("Speed: "+2,720,450);
                case 6-> g2d.drawString("Speed: "+3,720,450);
                case 8-> g2d.drawString("Speed: "+4,720,450);
                case 10-> g2d.drawString("Speed: "+5,720,450);
            }

        }

        if(doublePlayer){
            g2d.setPaint(Color.green);
            g2d.setFont(new Font(null,Font.BOLD,10));
            g2d.drawString("Computer",720,20);

            switch (autoPlay.difficulty){
                case 0->{
                    g2d.drawString("Easy",720,30);
                }
                case 1->{
                    g2d.setPaint(Color.yellow);
                    g2d.drawString("Medium",720,30);
                }
                case 2->{
                    g2d.setPaint(Color.red);
                    g2d.drawString("Hard",720,30);
                }
                case 10-> {
                    g2d.setPaint(Color.WHITE);
                    g2d.drawString("Simulate",720,30);
                }
            }
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
                if(plateP1.getY()<405){
                    moveDown=true;
                    downTarget=plateP1.getY()+18;
                }
                //plateP1.setBounds(plateP1.getX(),plateP1.getY()+18,10,60);
            }
            case 38-> {
                //up
                if(plateP1.getY()>5){
                    moveUp=true;
                    upTarget=plateP1.getY()-18;
                }
                //plateP1.setBounds(plateP1.getX(),plateP1.getY()-18,10,60);
            }

            case 87-> {
                //W
                if(plateP2.getY()>5&&!doublePlayer)
                    plateP2.setBounds(plateP2.getX(),plateP2.getY()-18,10,60);
            }

            case 83-> {
                //S
                if(plateP2.getY()<405&&doublePlayer)
                    plateP2.setBounds(plateP2.getX(),plateP2.getY()+18,10,60);
            }
            case 32-> {
                //space
                if(!play){
                    play = true;
                    defaultValues();
                    timer.start();
                }
                else{
                    simulateGame();
                }
            }

            case 88-> {
                //X
                doublePlayer=!doublePlayer;
                autoPlay.difficulty=1;
                if(doublePlayer) autoPlay.start();
                else autoPlay.stop();
            }

            case 68-> {
                //D
                autoPlay.setDifficulty();
            }

            case 66-> {
                ballSpeed=(ballSpeed+1)%10+1;
                ballSpeedX=(ballSpeedX/Math.abs(ballSpeedX))*ballSpeed;
                ballSpeedY=(ballSpeedY/Math.abs(ballSpeedY))*ballSpeed;
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
            if(simulate) simulateGame();
        }

        //win case
        if(ballPosX>770){
            play = false;
            timer.stop();
            if(simulate) simulateGame();
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

        //moving plates down
        if(moveDown){
            moveUp=false;
            if(plateP1.getY()<downTarget)
            plateP1.setBounds(plateP1.getX(),plateP1.getY()+7,10,60);
            else moveDown=false;
        }

        if(moveUp){
            moveDown=false;
            if(plateP1.getY()>upTarget)
                plateP1.setBounds(plateP1.getX(),plateP1.getY()-7,10,60);
            else moveUp=false;
        }

        repaint();
    }

    private void defaultValues(){
        plateP1.setBounds(20,170,10,60);
        plateP2.setBounds(770,250,10,60);
        ballPosX=random.nextInt(200,400);
        ballPosY=200;
        ballSpeedX=-ballSpeed;
        ballSpeedY=ballSpeed;
    }

    void simulateGame(){
        simulate=!simulate;
        if(simulate){
            doublePlayer=true;
            autoPlay.start();
            sim.start();
            sim.difficulty=10;
            autoPlay.difficulty=10;
            ballSpeedX*=2;
            ballSpeedY*=2;
        }else {
            doublePlayer=false;
            autoPlay.difficulty=8;
            autoPlay.stop();
            sim.stop();
            defaultValues();
        }
    }
}
