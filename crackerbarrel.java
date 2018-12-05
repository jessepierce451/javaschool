import java.util.ArrayList;

public class crackerbarrel {
    public static void main(String[] args) {
        
        triangleBoard game = new triangleBoard();

        ArrayList<triangleBoard.Space> beginPos = new ArrayList<triangleBoard.Space>();

        beginPos.add(new triangleBoard.Space(0,0));
        beginPos.add(new triangleBoard.Space(1,0));
        beginPos.add(new triangleBoard.Space(1,1));
        beginPos.add(new triangleBoard.Space(2,0));
        beginPos.add(new triangleBoard.Space(2,1));

        for(int i = 0; i < beginPos.size(); i++) {

            // Get next starting position
            triangleBoard.Space begin = beginPos.get(i);

            System.out.println("\n=== " + begin + " ===");

            // Initialize the board
            game.setup(5, begin);

            // Get the best sequence of moves to win
            ArrayList<triangleBoard.Move> gameSeq = game.bestSequence();

            // Print the move
            System.out.println();
            game.printState(game.game);

            for(int j = 0; j < gameSeq.size(); j++){
                System.out.println("\n" + gameSeq.get(j) + "\n");

                game.move(gameSeq.get(j));

                game.printState(game.game);
            }
        }
    }
}