import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AutoPlay implements ActionListener {
    //This script automates right side plate
    int expectedPosX, expectedPosY;
    Timer timer;
    JLabel label;
    int player;
    AutoPlay(JLabel label, int player){
        //setting up timer
        timer = new Timer(30,this);
        timer.start();
        this.label=label;
        this.player=player;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //calculating
        int curPosX= Game.ballPosX;
        int curPosY= Game.ballPosY;
        int curSpeedX = Game.ballSpeedX;
        int curSpeedY = Game.ballSpeedY;

        int steps = (label.getX() - curPosX)/30;

        if(player==2&&curPosX>400&&curSpeedX<0)
        label.setBounds(label.getX(),curPosY+curSpeedY*steps,label.getWidth(),label.getHeight());

        if(player==1&&curPosX<400&&curSpeedX>0)
            label.setBounds(label.getX(),curPosY+curSpeedY*steps,label.getWidth(),label.getHeight());

    }
}
