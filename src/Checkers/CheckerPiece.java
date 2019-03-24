package Checkers;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
//Basic CheckerPiece Class with all necessary variables
public class CheckerPiece {
    public boolean kinged = false;
    public int column;
    public int row;
    public Circle piece;

    public CheckerPiece(Paint colour, int column, int row, double rad)
    {
        this.column = column;
        this.row = row;
        this.piece = new Circle(rad);
        piece.setFill(colour);
        piece.setStroke(Color.WHITE);
    }


}
