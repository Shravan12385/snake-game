import javax.swing.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class SnakeGame extends JFrame {

    Board board;
    SnakeGame(){
        board = new Board();

        add(board);
        pack();
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        SnakeGame snakegame = new SnakeGame();
    }
}