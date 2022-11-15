import javax.swing.*;

class GameCode extends JFrame{
    GamePanel game = new GamePanel();

    public GameCode() {
        super("Meteors");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(game);
		pack();  // set the size of my Frame exactly big enough to hold the contents
		setVisible(true);
        setResizable(false);
    }

    public static void main(String[] args) {
        GameCode frame = new GameCode();
    }

}
