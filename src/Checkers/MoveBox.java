package Checkers;

import javafx.scene.paint.Color;

public class MoveBox {
    public int row;
    public boolean jump = false;
    public int column;

    //Basic constructor
    public MoveBox(int row, int column)
    {
        this.row = row;
        this.column = column;
    }
    //Generating all possible moves and storing them in an array
    public static MoveBox[] generate(CheckerPiece piece, CheckerPiece[][] board, boolean jumped)
    {
        System.out.println(piece.column);
        System.out.println(piece.row);
        int index = 0;
        MoveBox[] boxes = new MoveBox[4];
        //Piece moving Up the board
        if(piece.kinged || piece.piece.getFill() == Color.RED)
        {
            //Right Side of board
            if (piece.column != 7 && piece.row != 0)
            {
                if (board[piece.row - 1][piece.column + 1] == null)
                {
                    if(!jumped)
                    {
                        boxes[index] = new MoveBox(piece.row - 1, piece.column + 1);
                        index += 1;
                    }
                }
                else if (piece.column != 6 && piece.row != 1)
                {
                    if (board[piece.row - 1][piece.column + 1].piece.getFill() != piece.piece.getFill() &&
                            board[piece.row - 2][piece.column + 2] == null)
                    {
                        boxes[index] = new MoveBox(piece.row - 2, piece.column + 2);
                        boxes[index].jump = true;
                        index += 1;
                    }
                }
            }
            //Left side of board
            if (piece.column != 0 && piece.row != 0)
            {
                if (board[piece.row - 1][piece.column - 1] == null)
                {
                    if(!jumped)
                    {
                        boxes[index] = new MoveBox(piece.row - 1, piece.column - 1);
                        index += 1;
                    }
                }
                else if (piece.column != 1 && piece.row != 1)
                {
                    if (board[piece.row - 1][piece.column - 1].piece.getFill() != piece.piece.getFill() &&
                            board[piece.row - 2][piece.column - 2] == null)
                    {
                        boxes[index] = new MoveBox(piece.row - 2, piece.column - 2);
                        boxes[index].jump = true;
                        index += 1;
                    }
                }
            }
        }
        //Piece moving down board
        if(piece.kinged || piece.piece.getFill() == Color.BLACK)
        {
            if (piece.column != 7 && piece.row != 7)
            {
                if (board[piece.row + 1][piece.column + 1] == null)
                {
                    if(!jumped)
                    {
                        boxes[index] = new MoveBox(piece.row + 1, piece.column + 1);
                        index += 1;
                    }
                }
                else if (piece.column != 6 && piece.row != 6)
                {
                    if (board[piece.row + 1][piece.column + 1].piece.getFill() != piece.piece.getFill() &&
                            board[piece.row + 2][piece.column + 2] == null)
                    {
                        boxes[index] = new MoveBox(piece.row + 2, piece.column + 2);
                        boxes[index].jump = true;
                        index += 1;
                    }
                }
            }
            if (piece.column != 0 && piece.row != 7)
            {
                if (board[piece.row + 1][piece.column - 1] == null)
                {
                    if(!jumped)
                    {
                        boxes[index] = new MoveBox(piece.row + 1, piece.column - 1);
                        index += 1;
                    }
                }
                else if (piece.column != 1 && piece.row != 6)
                {
                    if (board[piece.row + 1][piece.column - 1].piece.getFill() != piece.piece.getFill() &&
                            board[piece.row + 2][piece.column - 2] == null)
                    {
                        boxes[index] = new MoveBox(piece.row + 2, piece.column - 2);
                        boxes[index].jump = true;
                        index += 1;
                    }
                }
            }
        }
        if(jumped && index !=0)
        {
            boxes[index] = new MoveBox(piece.row, piece.column);
        }
        return boxes;
    }
}
