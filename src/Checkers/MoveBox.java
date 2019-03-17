package Checkers;

public class MoveBox {
    public int row;
    public int column;

    //Basic constructor
    public MoveBox(int row, int column)
    {
        this.row = row;
        this.column = column;
    }
    //Generating all possible moves and storing them in an array
    //TODO CAPTURING PIECES
    public static MoveBox[] generate(CheckerPiece piece, CheckerPiece[][] board)
    {
        System.out.println(piece.column);
        System.out.println(piece.row);
        System.out.println(board[piece.row][piece.column]);
        int index = 0;
        MoveBox[] boxes = new MoveBox[4];
        if(piece.column != 7)
        {
            if (board[piece.column + 1][piece.row - 1] == null)
            {
                boxes[index] = new MoveBox(piece.row - 1, piece.column + 1);
                index += 1;
            }
        }
        if(piece.column != 0)
        {
            if (board[piece.column - 1][piece.row - 1] == null)
            {
                boxes[index] = new MoveBox(piece.row - 1, piece.column - 1);
                index += 1;
            }
        }
        if(piece.kinged)
        {

            if(board[piece.column+1][piece.row+1] == null)
            {
                boxes[index] = new MoveBox(piece.row-1,piece.column+1);
                index += 1;
            }
            if(board[piece.column-1][piece.row+1] == null)
            {
                boxes[index] = new MoveBox(piece.row-1,piece.column-1);
                index += 1;
            }
        }
        return boxes;
    }
}
