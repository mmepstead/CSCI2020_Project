

package Checkers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
//TODO ADD IN SECOND PLAYER TURN WITH BLACK PIECES
public class Main extends Application{
    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        //Starting player turn
        int playerTurn = 1;
        boolean moved = false;
        //Setup a board for checks
        CheckerPiece[][] board = new CheckerPiece[8][8];
        //GridPane is easiest
        GridPane pane = new GridPane();
        Scene scene = new Scene(pane, 300,300);
        for(int i=0; i<8; i+=1)
        {
            //Set height and widths for columns
            pane.getColumnConstraints().add(new ColumnConstraints(30));
            pane.getRowConstraints().add(new RowConstraints(30));
            //Generate 8 red pieces for testing
            CheckerPiece redPiece = new CheckerPiece(Color.RED,i,7);
            //Place them in the board
            board[7][i] = redPiece;
            //For each piece set up a click function
            redPiece.piece.setOnMouseClicked(e ->
            {
                //Generate all possible moves for that piece
                MoveBox[] boxes = MoveBox.generate(redPiece, board);
                for(int j=0; j<4; j+=1)
                {
                    //For all the possible moves add a small square to show where on the board the are
                    if(boxes[j] != null)
                    {
                        Rectangle box = new Rectangle(15, 15);
                        box.setFill(Color.TRANSPARENT);
                        int boxRow = boxes[j].row;
                        int boxColumn = boxes[j].column;
                        box.setStroke(Color.TURQUOISE);
                        //For each of these create a click event
                        box.setOnMouseClicked(m ->
                        {
                            //When clicked we move the piece to that point on the board updating all variables of its move
                            CheckerPiece redPiece2 = new CheckerPiece(Color.RED,boxColumn,boxRow);

                            //Thread to update GUI
                            board[boxRow][boxColumn] = redPiece2;
                            //Change the player's turn
                            changePlayer(playerTurn);
                            pane.getChildren().removeIf(Rectangle.class::isInstance);

                        });
                        pane.add(box,boxColumn,boxRow);
                    }
                }

            });

            pane.add(redPiece.piece,i,7);
        }
        primaryStage.setTitle("Checkers"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }

    public static void changePlayer(int playerTurn)
    {
        if(playerTurn == 1)
        {
            playerTurn = 2;
        }
        else
        {
            playerTurn = 1;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}


