package Checkers;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
//Basic CheckerPiece Class with all necessary variables
public class CheckerPiece {
    public boolean kinged = false;
    public int column;
    public int row;
    public Circle piece = new Circle(10);

    public CheckerPiece(Paint colour, int column, int row)
    {
        this.column = column;
        this.row = row;
        piece.setFill(colour);
    }


}
