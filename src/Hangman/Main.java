package Hangman;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Load answer from file
        Pane pane = new Pane();
        String answer = "Hello";
        HBox displayAnswer = new HBox();
        displayAnswer.setLayoutY(5);
        displayAnswer.setLayoutX(5);
        HBox guessBox = new HBox();
        guessBox.setLayoutX(5);
        guessBox.setLayoutY(40);
        guessBox.setSpacing(15);
        displayAnswer.setSpacing(15);
        TextField guess = new TextField();
        Button submit = new Button("Submit");
        guessBox.getChildren().addAll(guess,submit);
        char[] guesses =  new char[26];
        //This is kinda hacky
        int[] lettersFound =  new int[1];
        int[] lastIndex = new int[1];
        //Boxes for letter answers
        for(int i=0; i<answer.length(); i+=1)
        {
            Rectangle letterBox = new Rectangle(20,20);
            letterBox.setFill(Color.TRANSPARENT);
            letterBox.setStroke(Color.BLACK);
            displayAnswer.getChildren().add(letterBox);
        }
        submit.setOnAction(e ->
        {
            char input = guess.getText().toCharArray()[0];
            int correct = 0;
            int guessed = 0;
            //Check to see if character has already been guessed
            for(char character : guesses)
            {
                if(input == character)
                {
                    //Display error
                    guessed = 1;
                }
            }
            //Otherwise
            if(guessed == 0)
            {
                //Check to see if character is in answer
                int index = 0;
                guesses[lastIndex[0]] = input;
                lastIndex[0] += 1;
                for(char character : answer.toCharArray())
                {
                    if(input == character)
                    {
                        //Place letter in its box
                        System.out.println(index);
                        Text displayChar = new Text(Character.toString(character));
                        pane.getChildren().add(displayChar);
                        System.out.println(displayAnswer.getLayoutX()+6+36.25*index);
                        displayChar.setLayoutX(displayAnswer.getLayoutX()+6+36.25*index);
                        displayChar.setLayoutY(displayAnswer.getLayoutY()+14);
                        correct = 1;
                        lettersFound[0] += 1;
                    }
                    index += 1;
                }
                if(lettersFound[0] == answer.length())
                {
                    //Winning Conditions
                }

                if(correct == 0)
                {
                    //Add part to hangman and display incorrect letters in side box

                }
            }
            //Clear TextField for next guess
            guess.setText("");
            //Autosave with server
        });
        pane.setPadding(new Insets(15,15,15,15));
        pane.getChildren().addAll(displayAnswer,guessBox);
        primaryStage.setTitle("Hangman Game");
        primaryStage.setScene(new Scene(pane, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
