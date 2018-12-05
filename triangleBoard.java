import java.util.ArrayList;

public class triangleBoard {
    int remainingPegs, size;
    boolean[][] game;


    public static class Space {
        int row, column;

        @Override
        public String toString() {
            return "(" + row + "," + column + ")";
        }
        public Space(int row, int column) {
            this.row = row;
            this.column = column;
        }

        public boolean isValid(int board_size) {
            return (row >= 0) && (row < board_size) && (column >= 0) && (column <= row);
        }
        public void set(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    public class Move {
        triangleBoard.Space from;
        triangleBoard.Space to;


        public Move(triangleBoard.Space from, triangleBoard.Space to) {
            this.from = from;
            this.to = to;
        }

        public boolean isValid(triangleBoard board) {

            // make sure this spot even exists
            if (!from.isValid(board.size) || !to.isValid(board.size)) {
                return false;
            }

            // make sure there is a peg in the from spot
            if (!board.game[from.row][from.column]) {
                return false;
            }

            // check the to space doesnt have a peg
            if (board.game[to.row][to.column]) {
                return false;
            }

            int rowJump = Math.abs(from.row - to.row);
            int colJump = Math.abs(from.column - to.column);

            if (rowJump == 0) {
                if (colJump != 2) {
                    return false;
                }
            }
            else if (rowJump == 2) {
                if (colJump != 0 && colJump != 2) {
                    return false;
                }
            }
            else {
                return false;
            }

            // make sure the jump spot has a peg
            return board.game[(from.row + to.row) / 2][(from.column + to.column) / 2];
        }
    }

    public void setup(int num, triangleBoard.Space spot) {
        size = num;
        game = new boolean[num][num];
        remainingPegs = -1;

        // init the board with pegs 
        for (int i = 0; i < num; i++) {
            for (int j = 0; j <= i; j++) {
                game[i][j] = true;
                remainingPegs++;
            }
        }

        game[spot.row][spot.column] = false;
    }

    //jump a peg
    public boolean move(Move move) {
        if (!move.isValid(this)) {
            System.out.println("Invalid move.");
            return false;
        }

        // change from to empty
        game[move.from.row][move.from.column] = false;

        // change to to occupied
        game[move.to.row][move.to.column] = true;

        // change step to empty
        game[(move.from.row + move.to.row) / 2][(move.from.column + move.to.column) / 2] = false;

        //remove the jumped peg
        remainingPegs--;

        return true;
    }

    public void undoMove(Move move) {

        // change the from to filled
        game[move.from.row][move.from.column] = true;

        // change to to empty
        game[move.to.row][move.to.column] = false;

        // change the step to filled
        game[(move.from.row + move.to.row)/2][(move.from.column + move.to.column)/2] = true;

        // Increment number of pegs left on the board
        remainingPegs++;
    }

    public ArrayList<Move> validMovesFromSpace(Space from) {
        ArrayList<Move> moves = new ArrayList<Move>();

        // see if the move is valid on the game board
        if (!from.isValid(size)) {
            return moves;
        }

        // make sure the space is not empty
        if (!game[from.row][from.column]) {
            return moves;
        }

        // see if you can move to adjacent spots
        Move move = new Move(from, new Space(from.row - 2, from.column));

        if (move.isValid(this)) {
            moves.add(move);
        }

        move = new Move(from, new Space(from.row - 2, from.column - 2));

        if (move.isValid(this)) {
            moves.add(move);
        }

        move = new Move(from, new Space(from.row, from.column - 2));

        if (move.isValid(this)) {
            moves.add(move);
        }

        move = new Move(from, new Space(from.row, from.column + 2));

        if (move.isValid(this)) {
            moves.add(move);
        }

        move = new Move(from, new Space(from.row + 2, from.column));

        if (move.isValid(this)) {
            moves.add(move);
        }

        move = new Move(from, new Space(from.row + 2, from.column + 2));

        if (move.isValid(this)) {
            moves.add(move);
        }

        return moves;
    }

    public ArrayList<Move> validMoves() {
        Space space;
        ArrayList<Move> spaceMoves;
        ArrayList<Move> moves = new ArrayList<Move>();

        for(int i = 0; i < size; i++) {
            for (int j = 0; j <= i; j++) {
                space = new Space(i,j);
                spaceMoves = this.validMovesFromSpace(space);

                moves.addAll(spaceMoves);
            }
        }

        return moves;
    }

    public ArrayList<Move> bestSequence() {
        ArrayList<Move> sequence = new ArrayList<Move>();
        ArrayList<Move> moves = this.validMoves();

        if (moves.isEmpty()) {
            return sequence;
        }

        for (int i = 0; i < moves.size(); i++) {
            this.move(moves.get(i));

            if (remainingPegs == 1) {
                sequence.add(moves.get(i));
                this.undoMove(moves.get(i));

                return sequence;
            }

            ArrayList<Move> moveSequence = this.bestSequence();

            if (moveSequence.size() + 1 > sequence.size()) {
                sequence.clear();
                sequence.add(moves.get(i));
                sequence.addAll(moveSequence);
            }

            this.undoMove(moves.get(i));
        }

        return sequence;
    }


    public void printState(boolean[][] board) {
        for(int i = 0; i < size; i++) {
            System.out.print("  ");
            for(int k = 0; k < (size - i - 1); k++) {
                System.out.print(" ");
            }
            for(int j = 0; j <= i; j++) {
                System.out.print(board[i][j] ? 1 : 0);  
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}