import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Ping Pong");
        frame.setDefaultCloseOperation(3);
        frame.setSize(800,500);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        Game game = new Game();

        frame.add(game);
        frame.setVisible(true);
    }
}
