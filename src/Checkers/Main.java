

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
    boolean jumped1 = false;
    int playerTurn = 2;
    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        //Starting player turn
        boolean moved = false;
        //Setup a board for checks
        CheckerPiece[][] board = new CheckerPiece[8][8];
        //GridPane is easiest
        GridPane pane = new GridPane();
        Scene scene = new Scene(pane, 300,300);
        //Testing pieces


        //testing setup
        for(int i=0; i<8; i+=1)
        {
            //Set height and widths for columns
            pane.getColumnConstraints().add(new ColumnConstraints(30));
            pane.getRowConstraints().add(new RowConstraints(30));
            //Generate 8 red pieces for testing
            CheckerPiece blackPiece = new CheckerPiece(Color.BLACK,i,0);
            CheckerPiece redPiece = new CheckerPiece(Color.RED,i,7);
            //Place them in the board
            board[7][i] = redPiece;
            board[0][i] = blackPiece;
            //For each piece set up a click function
            redPiece.piece.setOnMouseClicked(e ->
            {
                if(playerTurn == 1)
                {
                    //Generate all possible moves for that piece
                    MoveBox[] boxes = MoveBox.generate(redPiece, board, jumped1);
                    if(jumped1 && boxes[0]== null)
                    {
                        //If we ran out of jumping moves
                        jumped(false);
                    }
                    for(int j=0; j<4; j+=1)
                    {
                        //For all the possible moves add a small square to show where on the board the are
                        if(boxes[j] != null)
                        {
                            Rectangle box = new Rectangle(15, 15);
                            box.setFill(Color.TRANSPARENT);
                            int boxRow = boxes[j].row;
                            boolean jump = boxes[j].jump;
                            int boxColumn = boxes[j].column;
                            box.setStroke(Color.TURQUOISE);
                            //For each of these create a click event
                            box.setOnMouseClicked(m ->
                            {
                                boolean jumped = false;
                                //If we jumped have to keep repeating
                                if(jump) {
                                    jumped = true;
                                    //Remove the piece we jumped over
                                    board[redPiece.row + (boxRow - redPiece.row)]
                                            [redPiece.column + (boxColumn - redPiece.column)] = null;
                                    //Move our piece(Might pull this out to method later)
                                    board[redPiece.row][redPiece.column] = null;
                                    board[boxRow][boxColumn] = redPiece;
                                    if (boxRow == 7) {
                                        redPiece.kinged = true;
                                    }
                                    redPiece.row = boxRow;
                                    redPiece.column = boxColumn;
                                    //We jumped so jumped should be true
                                    jumped(jumped);
                                    pane.getChildren().removeIf(Rectangle.class::isInstance);

                                }
                                //When clicked we move the piece to that point on the board updating all variables of its move
                                //We didn't jump so we don't need to repeat
                                else
                                {
                                    //Thread to update GUI
                                    //We didn't jump so set it to false
                                    jumped(jumped);
                                    //Update board and piece
                                    board[redPiece.row][redPiece.column] = null;
                                    board[boxRow][boxColumn] = redPiece;
                                    if (boxRow == 7)
                                    {
                                        redPiece.kinged = true;
                                    }
                                    redPiece.row = boxRow;
                                    redPiece.column = boxColumn;
                                    //Change the player's turn
                                    changePlayer(playerTurn);
                                    //Remove our move boxes (Might be cleaner way to do this
                                    pane.getChildren().removeIf(Rectangle.class::isInstance);
                                }

                            });
                            pane.add(box,boxColumn,boxRow);
                        }
                    }
                }

            });
            //Same as above but for black pieces
            blackPiece.piece.setOnMouseClicked(e ->
            {
                if(playerTurn == 2)
                {
                    //Generate all possible moves for that piece
                    MoveBox[] boxes = MoveBox.generate(blackPiece, board, jumped1);
                    if(jumped1 && boxes[0]== null)
                    {
                        jumped(false);
                    }
                    for(int j=0; j<4; j+=1)
                    {
                        //For all the possible moves add a small square to show where on the board the are
                        if(boxes[j] != null)
                        {
                            Rectangle box = new Rectangle(15, 15);
                            box.setFill(Color.TRANSPARENT);
                            int boxRow = boxes[j].row;
                            boolean jump = boxes[j].jump;
                            int boxColumn = boxes[j].column;
                            box.setStroke(Color.TURQUOISE);
                            //For each of these create a click event
                            box.setOnMouseClicked(m ->
                            {
                                boolean jumped = false;
                                //If we jumped have to keep repeating
                                if(jump) {
                                    jumped = true;
                                    //Remove the piece we jumped over
                                    board[blackPiece.row + (boxRow - blackPiece.row)]
                                            [blackPiece.column + (boxColumn - blackPiece.column)] = null;
                                    //Move our piece(Might pull this out to method later)
                                    board[blackPiece.row][blackPiece.column] = null;
                                    board[boxRow][boxColumn] = blackPiece;
                                    if (boxRow == 0) {
                                        blackPiece.kinged = true;
                                    }
                                    blackPiece.row = boxRow;
                                    blackPiece.column = boxColumn;
                                    jumped(jumped);
                                    pane.getChildren().removeIf(Rectangle.class::isInstance);

                                }
                                //When clicked we move the piece to that point on the board updating all variables of its move
                                //We didn't jump so we don't need to repeat
                                else
                                {
                                    //Thread to update GUI
                                    jumped(jumped);
                                    board[blackPiece.row][blackPiece.column] = null;
                                    board[boxRow][boxColumn] = blackPiece;
                                    if (boxRow == 0)
                                    {
                                        blackPiece.kinged = true;
                                    }
                                    blackPiece.row = boxRow;
                                    blackPiece.column = boxColumn;
                                    //Change the player's turn
                                    changePlayer(playerTurn);
                                    pane.getChildren().removeIf(Rectangle.class::isInstance);
                                }

                            });
                            pane.add(box,boxColumn,boxRow);
                        }
                    }
                }

            });
            pane.add(blackPiece.piece,i,0);
            pane.add(redPiece.piece,i,7);
        }
        primaryStage.setTitle("Checkers"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }


    public void changePlayer(int playerTurn2)
    {
        if(playerTurn2 == 1)
        {
            playerTurn = 2;
        }
        else
        {
            playerTurn = 1;
        }
    }

    public void jumped(boolean jumped)
    {
        jumped1 = jumped;
    }
    public static void main(String[] args) {
        launch(args);
    }
}


