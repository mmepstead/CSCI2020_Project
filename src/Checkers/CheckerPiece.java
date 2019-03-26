package Checkers;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
//Basic CheckerPiece Class with all necessary variables
public class CheckerPiece {
    public boolean kinged = false;
    public int column;
    public int row;
    public Circle piece;
    public ImageView image;

    public CheckerPiece(Paint colour, int column, int row, double rad, Image image)
    {
        this.column = column;
        this.row = row;
        this.piece = new Circle(rad);
        piece.setFill(colour);
        piece.setStroke(Color.WHITE);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(((rad*8)/3) * 0.75);
        imageView.setFitWidth(((rad*8))/3 * 0.75);
        this.image = imageView;
    }


}
