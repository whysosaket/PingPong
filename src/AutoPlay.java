import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AutoPlay implements ActionListener {
    //This script automates right side plate
    int expectedPosX, expectedPosY;
    private Timer timer;
    JLabel label;
    int player,curPosX,curPosY,curSpeedX,curSpeedY,steps,difficulty=2;
    boolean move=false;
    AutoPlay(JLabel label, int player){
        //setting up timer
        timer = new Timer(30,this);
        this.label=label;
        this.player=player;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        curPosX= Game.ballPosX;
        curSpeedX = Game.ballSpeedX;
        if(player==2&&curPosX>400&&curSpeedX<0||player==1&&curPosX<400&&curSpeedX>0) {
            move=true;
            calculate();
        }else move=false;

        if(move) {
                if(label.getY()<curPosY+curSpeedY*steps) {
                    label.setBounds(label.getX(),label.getY()+getDifficulty(),label.getWidth(),label.getHeight());
                } else if (label.getY()>curPosY+curSpeedY*steps) {
                    label.setBounds(label.getX(),label.getY()-getDifficulty(),label.getWidth(),label.getHeight());
                }
        }
    }

    void calculate(){
        //calculating
        curPosX= Game.ballPosX;
        curPosY= Game.ballPosY;
        curSpeedX = Game.ballSpeedX;
        curSpeedY = Game.ballSpeedY;
        steps = (label.getX() - curPosX)/30;
    }

    void start(){
        timer.start();
    }

    void stop(){
        timer.stop();
    }

    void setDifficulty(){
        difficulty = (difficulty+1)%3;
    }
    int getDifficulty(){
        int speed=0;
        switch (difficulty){
            case 0-> speed=6;
            case 1-> speed=8;
            case 2-> speed=9;
            case 10-> speed=16;
        }
        return speed;
    }
}
