
package Checkers;

import java.util.Iterator;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

//TODO ADD IN SECOND PLAYER TURN WITH BLACK PIECES
public class Main extends Application {
	CheckerPiece jumpingPiece = null;
	int playerTurn = 2;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		BorderPane pane = new BorderPane();
		Pane paneG = new Pane();
		pane.setCenter(paneG);

		// This is the width and height for the screen displayed
		double width = 800.0, height = 600.0;
		mainMenu(pane, width, height);

		Scene scene = new Scene(pane, width, height);
		scene.setFill(Color.BLACK);
		primaryStage.setTitle("Menu"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage

	}

	private void mainMenu(BorderPane pane, double width, double height) {

		Pane paneG = (Pane) (pane.getCenter());

		double wi = width / 4.0, hi = height / 3.0;

		// double size = height/8.0;

		int num = 0;
		for (int x = 0; x < width; x += wi / 2.0) {
			for (int y = 0; y < height; y += hi) {
				Rectangle back = new Rectangle(x, y + (hi / 2.0) * num, wi / 2.0, hi / 2.0);
				back.setFill(Color.RED);
				paneG.getChildren().add(back);
			}
			num = (num + 1) % 2;
		}

		Text text = new Text(175, 150, "Checkers!");
		System.out.println(text.getLayoutX());
		text.setFill(Color.RED);
		text.setStrokeWidth(2.0);
		text.setStroke(Color.BLACK);
		text.setFont(new Font("Impact", 100));
		paneG.getChildren().add(text);

		Circle[] menuBoxes = new Circle[2];
		menuBoxes[0] = new Circle(225, 400, 75);
		menuBoxes[0].setFill(Color.RED);
		menuBoxes[0].setStroke(Color.BLACK);
		menuBoxes[1] = new Circle(550, 400, 75);
		menuBoxes[1].setFill(Color.RED);
		menuBoxes[1].setStroke(Color.BLACK);

		paneG.getChildren().addAll(menuBoxes);

		// This sets the actions for all of the menu "panels" for the checkers game
		for (Circle panel : menuBoxes) {
			panel.setOnMouseEntered(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					panel.setFill(new Color(1.0, 0.2, 0.2, 1));
				}
			});

			panel.setOnMouseExited(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					panel.setFill(Color.RED);
				}
			});
		}

		// button for offline play
		menuBoxes[0].setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				GameStart(pane, width, height);
			}
		});
		// Button for on line play
		// THIS IS THE BUTTON THAT SHOULD LEAD TO THE ONLINE FUNCTIONALITY
		menuBoxes[1].setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				GameStart(pane, width, height);
			}
		});

		// Font.getFamilies() prints out all the available fonts
		// setting the text for the buttons on the main menu
		Text offline = new Text(menuBoxes[0].getCenterX() - 30, menuBoxes[0].getCenterY() + 5, "Offline");
		offline.setFill(Color.WHITE);
		offline.setFont(new Font("Times New Roman", 18));
		paneG.getChildren().add(offline);
		Text online = new Text(menuBoxes[1].getCenterX() - 25, menuBoxes[1].getCenterY() + 5, "Online");
		online.setFill(Color.WHITE);
		online.setFont(new Font("Times New Roman", 18));
		paneG.getChildren().add(online);
	}

	private void GameStart(BorderPane pane, double width, double height) {

		pane.getChildren().clear();
		GridPane paneG = new GridPane();
		pane.setCenter(paneG);

		// size of each section of the board
		double size = height / 8.0;
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				Rectangle section = new Rectangle(size, size);
				if ((x + y) % 2 == 0)
					section.setFill(Color.RED);
				else
					section.setFill(Color.BLACK);
				paneG.add(section, y, x);
			}
		}

		// Setup a board for checks
		CheckerPiece[][] board = new CheckerPiece[8][8];
		// imports the "king" image
		FileInputStream input = null;
		try {
			input = new FileInputStream("images/WhiteKing.png");
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		}
		Image image = new Image(input);

		// testing setup
		for (int i = 0; i < 12; i += 1) {
			// Set height and widths for columns
			// paneG.getColumnConstraints().add(new ColumnConstraints(height));
			// paneG.getRowConstraints().add(new RowConstraints(height));
			// Generate 8 red pieces for testing
			CheckerPiece blackPiece = new CheckerPiece(Color.BLACK, (i * 2 + ((i / 4) % 2)) % 8, i / 4,
					(size * 3.0) / 8.0);
			CheckerPiece redPiece = new CheckerPiece(Color.RED, (i * 2 + ((i / 4 + 1) % 2)) % 8, 7 - i / 4,
					(size * 3.0) / 8.0);
			// Place them in the board
			board[7 - i / 4][(i * 2 + ((i / 4 + 1) % 2)) % 8] = redPiece;
			board[i / 4][(i * 2 + ((i / 4) % 2)) % 8] = blackPiece;
			// For each piece set up a click function

			redPiece.piece.setOnMouseClicked(e -> {
				if (playerTurn == 1) {
					// Generate all possible moves for that piece
					if(jumpingPiece != null && redPiece != jumpingPiece)
					{
						return;
					}
					MoveBox[] boxes = MoveBox.generate(redPiece, board, jumpingPiece);
					for (int j = 0; j < 4; j += 1) {
						// For all the possible moves add a small square to show where on the board the
						// are
						if (boxes[j] != null) {
							Rectangle box = new Rectangle(size * 0.75, size * 0.75);
							box.setFill(Color.TRANSPARENT);
							int boxRow = boxes[j].row;
							boolean jump = boxes[j].jump;
							int boxColumn = boxes[j].column;
							box.setStroke(Color.TURQUOISE);
							// For each of these create a click event
							box.setOnMouseClicked(m -> {
								CheckerPiece jumped = null;
								// If we jumped have to keep repeating
								if (jump) {
									jumped = redPiece;
									// Remove the piece we jumped over
									int r = (redPiece.row + boxRow) / 2;
									int c = (redPiece.column + boxColumn) / 2;
									removeGraphic(paneG, r, c);
									if (board[r][c].kinged)
										removeGraphic(paneG, r, c);
									board[r][c] = null;
									//Check after jump to see if any jumps left
									MoveBox[] boxesAfterJump = MoveBox.generate(redPiece, board, jumpingPiece);
									if (boxesAfterJump[0] == null) {
										// If we ran out of jumping moves
										jumped = null;
										changePlayer(playerTurn);
									}
								}
								// When clicked we move the piece to that point on the board updating all
								// variables of its move
								// We didn't jump so we don't need to repeat
								else {
									changePlayer(playerTurn);
								}

								// All the code that progresses a turn no matter what happens
								board[redPiece.row][redPiece.column] = null;
								board[boxRow][boxColumn] = redPiece;
								// updates graphics
								removeGraphic(paneG, redPiece.row, redPiece.column);
								paneG.add(redPiece.piece, boxColumn, boxRow);
								if (redPiece.kinged) {
									removeGraphic(paneG, redPiece.row, redPiece.column);
									ImageView imageView = new ImageView(image);
									imageView.setFitHeight(size * 0.75);
									imageView.setFitWidth(size * 0.75);
									paneG.add(imageView, boxColumn, boxRow);
									paneG.setValignment(imageView, VPos.CENTER);
									paneG.setHalignment(imageView, HPos.CENTER);
								}

								redPiece.row = boxRow;
								redPiece.column = boxColumn;

								if (boxRow == 0) {
									redPiece.kinged = true;
									ImageView imageView = new ImageView(image);
									imageView.setFitHeight(size * 0.75);
									imageView.setFitWidth(size * 0.75);
									paneG.add(imageView, redPiece.column, redPiece.row);
									paneG.setValignment(imageView, VPos.CENTER);
									paneG.setHalignment(imageView, HPos.CENTER);
								}

								jumped(jumped);

								removeBoxes(paneG);
							});
							paneG.add(box, boxColumn, boxRow);
							paneG.setValignment(box, VPos.CENTER);
							paneG.setHalignment(box, HPos.CENTER);
						}
					}
				}

			});
			// Same as above but for black pieces
			blackPiece.piece.setOnMouseClicked(e -> {
				if (playerTurn == 2) {
					// Generate all possible moves for that piece
					if(jumpingPiece != null && blackPiece != jumpingPiece)
					{
						return;
					}
					MoveBox[] boxes = MoveBox.generate(blackPiece, board, jumpingPiece);
					for (int j = 0; j < 4; j += 1) {
						// For all the possible moves add a small square to show where on the board the
						// are
						if (boxes[j] != null) {
							Rectangle box = new Rectangle(size * 0.75, size * 0.75);
							box.setFill(Color.TRANSPARENT);
							int boxRow = boxes[j].row;
							boolean jump = boxes[j].jump;
							int boxColumn = boxes[j].column;
							box.setStroke(Color.TURQUOISE);
							// For each of these create a click event
							box.setOnMouseClicked(m -> {
								CheckerPiece jumped = null;
								// If we jumped have to keep repeating
								if (jump) {
									jumped = blackPiece;
									// Remove the piece we jumped over
									int r = (blackPiece.row + boxRow) / 2;
									int c = (blackPiece.column + boxColumn) / 2;
									removeGraphic(paneG, r, c);
									if (board[r][c].kinged)
										removeGraphic(paneG, r, c);
									board[r][c] = null;
									MoveBox[] boxesAfterJump = MoveBox.generate(redPiece, board, jumpingPiece);
									if (jumpingPiece != null && boxesAfterJump[0] == null) {
										// If we ran out of jumping moves
										jumped = null;
										changePlayer(playerTurn);
									}
								}
								// When clicked we move the piece to that point on the board updating all
								// variables of its move
								// We didn't jump so we don't need to repeat
								else {
									// Thread to update GUI
									changePlayer(playerTurn);
								}

								// All the code that progresses a turn no matter what happens

								board[blackPiece.row][blackPiece.column] = null;
								board[boxRow][boxColumn] = blackPiece;
								// updates graphics
								removeGraphic(paneG, blackPiece.row, blackPiece.column);
								paneG.add(blackPiece.piece, boxColumn, boxRow);
								if (blackPiece.kinged) {
									removeGraphic(paneG, blackPiece.row, blackPiece.column);
									ImageView imageView = new ImageView(image);
									imageView.setFitHeight(size * 0.75);
									imageView.setFitWidth(size * 0.75);
									paneG.add(imageView, boxColumn, boxRow);
									paneG.setValignment(imageView, VPos.CENTER);
									paneG.setHalignment(imageView, HPos.CENTER);
								}

								blackPiece.row = boxRow;
								blackPiece.column = boxColumn;

								if (boxRow == 7) {
									blackPiece.kinged = true;
									ImageView imageView = new ImageView(image);
									imageView.setFitHeight(size * 0.75);
									imageView.setFitWidth(size * 0.75);
									paneG.add(imageView, blackPiece.column, blackPiece.row);
									paneG.setValignment(imageView, VPos.CENTER);
									paneG.setHalignment(imageView, HPos.CENTER);
								}

								jumped(jumped);
								removeBoxes(paneG);
							});
							paneG.add(box, boxColumn, boxRow);
							paneG.setValignment(box, VPos.CENTER);
							paneG.setHalignment(box, HPos.CENTER);
						}
					}
				}

			});

			paneG.add(blackPiece.piece, (i * 2 + ((i / 4) % 2)) % 8, i / 4);
			paneG.add(redPiece.piece, (i * 2 + ((i / 4 + 1) % 2)) % 8, 7 - i / 4);

			paneG.setValignment(blackPiece.piece, VPos.CENTER);
			paneG.setHalignment(blackPiece.piece, HPos.CENTER);
			paneG.setValignment(redPiece.piece, VPos.CENTER);
			paneG.setHalignment(redPiece.piece, HPos.CENTER);
		}

		// sets up the log and the chat for the game and places them onto a
		// vbox within the Borderpane
		VBox vBox = new VBox();
		TextArea Log = new TextArea();
		Log.setPrefSize(width / 4.0, height / 2.0);
		Log.setEditable(false);
		Log.setWrapText(true);
		Log.setPromptText("A log for showing events in the game");
		TextArea chat = new TextArea();
		chat.setPrefSize(width / 4.0, height / 2.0);
		chat.setEditable(false);
		chat.setWrapText(true);
		chat.setPromptText("Chat");

		TextField field = new TextField();
		field.setPromptText("enter messages here");
		Button btn = new Button("send");
		btn.setDefaultButton(true);

		vBox.getChildren().addAll(Log, chat, field, btn);

		pane.setLeft(vBox);

		// what button does is it sends whatever is in the text field to
		// the chat text area, then clears the test field
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Calendar cal = Calendar.getInstance();
				String hour = Integer.toString(cal.get(Calendar.HOUR_OF_DAY));
				if (hour.length() < 2)
					hour = "0" + hour;
				String minute = Integer.toString(cal.get(Calendar.MINUTE));
				if (minute.length() < 2)
					minute = "0" + minute;
				String second = Integer.toString(cal.get(Calendar.SECOND));
				if (second.length() < 2)
					second = "0" + second;
				String time = hour + ":" + minute + ":" + second;

				chat.appendText(time + ") " + field.getText() + "\r\n");
				field.clear();
			}
		});
	}

	public void changePlayer(int playerTurn2) {
		if (playerTurn2 == 1) {
			playerTurn = 2;
		} else {
			playerTurn = 1;
		}
	}

	public void jumped(CheckerPiece jumped) {
		jumpingPiece = jumped;
	}

	public void removeBoxes(GridPane pane) {
		Iterator<Node> itr = pane.getChildren().iterator();
		ArrayList<Node> found = new ArrayList<Node>();
		while (itr.hasNext()) {
			Node x = itr.next();
			if (x != null) {
				if (x instanceof Rectangle) {
					if (((Rectangle) x).getStroke() != null) {
						if (((Rectangle) x).getStroke().equals(Color.TURQUOISE)) {
							found.add(x);
						}
					}
				}
			}
		}
		pane.getChildren().removeAll(found);
	}

	public void removeGraphic(GridPane pane, int row, int col) {

		Iterator<Node> itr = pane.getChildren().iterator();
		while (itr.hasNext()) {
			Node node = itr.next();
			if (node instanceof Circle && GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
				pane.getChildren().remove(node);
				break;
			}
			if (node instanceof ImageView && GridPane.getRowIndex(node) == row
					&& GridPane.getColumnIndex(node) == col) {
				System.out.println(pane.getChildren().remove(((ImageView) node)));
				break;
			}
		}
	}

}
