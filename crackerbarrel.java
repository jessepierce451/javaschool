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
        beginPos.add(new triangleBoard.Space(2,2));
        beginPos.add(new triangleBoard.Space(3,0));
        beginPos.add(new triangleBoard.Space(3,1));
        beginPos.add(new triangleBoard.Space(3,2));
        beginPos.add(new triangleBoard.Space(3,3));
        beginPos.add(new triangleBoard.Space(4,0));
        beginPos.add(new triangleBoard.Space(4,1));
        beginPos.add(new triangleBoard.Space(4,2));
        beginPos.add(new triangleBoard.Space(4,3));
        beginPos.add(new triangleBoard.Space(4,4));
        

        for(int i = 0; i < beginPos.size(); i++) {

            //iterates through the first spot without a peg for each new game
            triangleBoard.Space begin = beginPos.get(i);

            System.out.println("\n=== " + begin + " ===");

            //init the gamebaord for play
            game.setup(5, begin);

            //returns the best play out of possible plays
            ArrayList<triangleBoard.Move> gameSeq = game.bestSequence();

            //Print the state of the gameboard
            System.out.println();
            game.printBoard(game.game);

            for(int j = 0; j < gameSeq.size(); j++){
                game.move(gameSeq.get(j));
                game.printBoard(game.game);
            }
        }
    }
}