
package Checkers;

import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Calendar;

import javafx.application.Application;
import javafx.application.Platform;
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
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static java.lang.Thread.sleep;

//TODO ADD IN SECOND PLAYER TURN WITH BLACK PIECES
public class Main extends Application {
	CheckerPiece jumpingPiece = null;

	static boolean myTurn;

	int redPieces = 12;
	int blackPieces = 12;
	static boolean moveFinished = false;
	static int playerNumber;


	//Networking variables
	static ServerSocket ss;
	static Socket socket;
	static DataInputStream in;
	static DataOutputStream out;
	static String IPaddress;
	static ArrayList<Move> turn;

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


		//initialize IPaddress
		try {
			IPaddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}


	//Here are the host game and join game functions
	private static void hostGame() {
		myTurn = true; //host goes first
		playerNumber = 1;
		turn = new ArrayList<>();
		//initialize server socket
		try {
			ss = new ServerSocket(8000);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//UI: display IPaddress and screen waiting for opponent to connect

		//NETWORK: wait for player to connect and assign socket and I/O streams - DONE
		try {
			//create socket to communicate with client
			socket = ss.accept();
			//initialize IO streams
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			/*new Thread( () -> {
				while(true)
				{
					//NETWORK - read opponent's move information
					int pieceRow = in.readInt();
					int pieceCol = in.readInt();
					CheckerPiece piece = board[pieceRow][pieceCol];
					boolean jump = in.readBoolean();
					int boxRow = in.readInt();
					int boxCol = in.readInt();
					//Call move function to execute opponent's move
					move(piece, jump, boxRow, boxCol)
				}
				}).start;*/ //implementing this sequentially instead, if time should check here in a thread to ensure
							//with opponent and terminate game if connection is lost, returning player to the menu with
							//a message that the connection was lost
		} catch (IOException e) {
			System.out.println("Exception1");
			e.printStackTrace();
		}
	}

	private static void joinGame() {
		myTurn = false; //host goes first
		playerNumber = 2;
		String hostIPaddress = "localhost";

		//UI: have user enter an IP address to connect to, store IP address in the String variable "hostIPaddress"

		//NETWORK: assign socket and I/O streams - DONE
		try {
			//create socket and connect to server
			socket = new Socket(hostIPaddress, 8000);
			//initialize IO streams
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());

			/*new Thread( () -> {
				while(true)
				{
					//Either reading from server or writing to server
					//based on playerTurn variable
				}
				}).start;*/ //implementing this sequentially instead, if time should check here in a thread to ensure
			//with opponent and terminate game if connection is lost, returning player to the menu with
			//a message that the connection was lost

		} catch (IOException e) {
			System.out.println("Exception");
			e.printStackTrace();
		}
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

		// button for hosting game
		menuBoxes[0].setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				hostGame();
				System.out.println("Host");
				GameStart(pane, width, height);
			}
		});
		// Button for on line play
		// button for joining game
		menuBoxes[1].setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				joinGame();
				System.out.println("Join");
				GameStart(pane, width, height);
			}
		});

		// Font.getFamilies() prints out all the available fonts
		// setting the text for the buttons on the main menu
		Text offline = new Text(menuBoxes[0].getCenterX() - 30, menuBoxes[0].getCenterY() + 5, "Host Game");
		offline.setFill(Color.WHITE);
		offline.setFont(new Font("Times New Roman", 18));
		paneG.getChildren().add(offline);
		Text online = new Text(menuBoxes[1].getCenterX() - 25, menuBoxes[1].getCenterY() + 5, "Join Game");
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

		// Setup a board for checker pieces
		CheckerPiece[][] board = new CheckerPiece[8][8];
		// imports the "king" image
		FileInputStream input = null;
		try {
			input = new FileInputStream("images/WhiteKing.png");
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		}
		Image image = new Image(input);
		new Thread(() ->
		{
			while(true)
			{
				System.out.println("Looping");
				if(moveFinished && !myTurn)
				{
					endTurn(paneG, board,image,size);
					moveFinished = false;
					System.out.println("Finished");
				}
			}
		}).start();
		// testing setup
		for (int i = 0; i < 24; i += 1) {
			// Set height and widths for columns
			// paneG.getColumnConstraints().add(new ColumnConstraints(height));
			// paneG.getRowConstraints().add(new RowConstraints(height));

			//create new piece
			int pieceRow, pieceCol;
			Color pieceColor;
			if (i < 12) {
				pieceRow = i / 4;
				pieceCol = (i * 2 + ((i / 4) % 2)) % 8;
				pieceColor = Color.BLACK;
			} else {
				pieceRow = 7 - (i - 12) / 4;
				pieceCol = ((i - 12) * 2 + (((i - 12) / 4 + 1) % 2)) % 8;
				pieceColor = Color.RED;
			}
			CheckerPiece piece = new CheckerPiece(pieceColor, pieceCol, pieceRow, (size * 3.0) / 8.0);

			// Place them in the board
			board[pieceRow][pieceCol] = piece;

			// For each piece set up a click function
			piece.piece.setOnMouseClicked(e -> {

				//used to hold all the moves the player during this turn so they
				//ArrayList turn = new ArrayList();	//used to hold all the moves the player during this turn so they

				//can be sent over the network to the opponent
				// Generate all possible moves for that piece
				if (myTurn) {
					if (jumpingPiece != null && piece != jumpingPiece) {
						return;
					}
					MoveBox[] boxes = MoveBox.generate(piece, board, jumpingPiece);
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
								//add move to turn ArrayList
								turn.add(new Move(piece.row, piece.column, jump, boxRow, boxColumn));
								//execute move
								move(piece, jump, boxRow, boxColumn, paneG, board, image, size);
								moveFinished = true;
							});
							paneG.add(box, boxColumn, boxRow);
							paneG.setValignment(box, VPos.CENTER);
							paneG.setHalignment(box, HPos.CENTER);
						}
					}
				}
			});
			//add piece to pane
			paneG.add(piece.piece, pieceCol, pieceRow);
			paneG.setValignment(piece.piece, VPos.CENTER);
			paneG.setHalignment(piece.piece, HPos.CENTER);
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
		System.out.println("Showing Board");
		if(!myTurn){
			System.out.println("not my turn");
			executeOpponentTurn(paneG,board,image,size);
		}

	}

	public void endTurn(GridPane paneG, CheckerPiece[][] board, Image image, double size) {
		myTurn = false;
		//NETWORK - send turn data to opponent
		try {
			out.writeInt(turn.size());
			for (Move m : turn) {
				out.writeInt(m.pieceRow);
				out.writeInt(m.pieceCol);
				out.writeBoolean(m.jump);
				out.writeInt(m.boxRow);
				out.writeInt(m.boxCol);
			}
			executeOpponentTurn(paneG, board, image, size);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

		public void jumped (CheckerPiece jumped){
			jumpingPiece = jumped;
		}

		public void removeBoxes (GridPane pane){
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

		public void removeGraphic (GridPane pane,int row, int col){

			Iterator<Node> itr = pane.getChildren().iterator();
			while (itr.hasNext()) {
				Node node = itr.next();
				if (node instanceof Circle && GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
					Platform.runLater(() ->
					{
						pane.getChildren().remove(node);
					});
					break;
				}
				if (node instanceof ImageView && GridPane.getRowIndex(node) == row
						&& GridPane.getColumnIndex(node) == col) {
					Platform.runLater(() ->
					{
						pane.getChildren().remove(((ImageView) node));
					});
					break;
				}
			}
		}

		public void move (CheckerPiece piece,boolean jump, int boxRow, int boxColumn, GridPane paneG, CheckerPiece[][]
		board, Image image,double size){
			CheckerPiece jumped = null;
			// If we jumped have to keep repeating
			if (jump) {
				jumped(piece);
				// Remove the piece we jumped over
				int r = (piece.row + boxRow) / 2;
				int c = (piece.column + boxColumn) / 2;
				if (board[r][c].piece.getFill() == Color.RED) {
					redPieces -= 1;
				}
				if (board[r][c].piece.getFill() == Color.BLACK) {
					blackPieces -= 1;
				}
				removeGraphic(paneG, r, c);
				if (board[r][c].kinged) {
					removeGraphic(paneG, r, c);
				}
				board[r][c] = null;
				//Check after jump to see if any jumps left
				if (myTurn) {
					MoveBox[] boxesAfterJump = MoveBox.generate(piece, board, jumpingPiece);
					if (boxesAfterJump[0] == null) {
						// If we ran out of jumping moves
						jumped = null;
						myTurn = false;
					}
				}
			}
			// When clicked we move the piece to that point on the board updating all
			// variables of its move
			// We didn't jump so we don't need to repeat

			else if (myTurn) {
				myTurn = false;
			} else {
				if (blackPieces == 0) {
					//Player 1 wins
				}
				if (redPieces == 0) {
					//Player 2 wins
				}
			}
			// All the code that progresses a turn no matter what happens
			board[piece.row][piece.column] = null;
			board[boxRow][boxColumn] = piece;
			// updates graphics
			removeGraphic(paneG, piece.row, piece.column);
			if (boxRow == 0 && piece.piece.getFill() == Color.RED) {
				piece.kinged = true;
				ImageView imageView = new ImageView(image);
				imageView.setFitHeight(size * 0.75);
				imageView.setFitWidth(size * 0.75);
				Platform.runLater(()-> {
					paneG.add(imageView, piece.column, piece.row);
					paneG.setValignment(imageView, VPos.CENTER);
					paneG.setHalignment(imageView, HPos.CENTER);
				});
			}
			if (boxRow == 7 && piece.piece.getFill() == Color.BLACK) {
				piece.kinged = true;
				ImageView imageView = new ImageView(image);
				imageView.setFitHeight(size * 0.75);
				imageView.setFitWidth(size * 0.75);
				Platform.runLater(()-> {
					paneG.add(imageView, piece.column, piece.row);
					paneG.setValignment(imageView, VPos.CENTER);
					paneG.setHalignment(imageView, HPos.CENTER);
				});
			}
			if (piece.kinged) {
				removeGraphic(paneG, piece.row, piece.column);
				ImageView imageView = new ImageView(image);
				imageView.setFitHeight(size * 0.75);
				imageView.setFitWidth(size * 0.75);
				Platform.runLater(()-> {
					paneG.add(imageView, boxColumn, boxRow);
					paneG.setValignment(imageView, VPos.CENTER);
					paneG.setHalignment(imageView, HPos.CENTER);
				});
			}

			piece.row = boxRow;
			piece.column = boxColumn;

			Platform.runLater(()-> {
				paneG.add(piece.piece, boxColumn, boxRow);
			});

			jumped(jumped);
			Platform.runLater(()-> {
				removeBoxes(paneG);
			});
		}


	void executeOpponentTurn(GridPane paneG, CheckerPiece[][] board, Image image, double size) {
		//NETWORK - get opponent's turn

		//turn = new ArrayList<>();
		try {
			System.out.println(in);
			int numberOfMoves = in.readInt();
			System.out.println(numberOfMoves);
			for(int i = 0; i < numberOfMoves; i++){
				System.out.println("WE FUCKING LOOPED");
				int row = in.readInt();
				int col = in.readInt();
				boolean jump = in.readBoolean();
				int boxRow = in.readInt();
				int boxCol = in.readInt();
				move(board[row][col], jump, boxRow, boxCol, paneG, board, image, size);
				//turn.add(new Move(in.readInt(),in.readInt(),in.readBoolean(),in.readInt(),in.readInt()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		//execute opponent's turn
		/*
		for (Move m : turn) {
			move(board[m.pieceRow][m.pieceCol], m.jump, m.boxRow, m.boxCol, paneG, board, image, size);
			try {
				sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}*/
		myTurn = true;
		turn = new ArrayList<>();
	}

	//an ArrayList of Move objects is used to send and receive turns over network
	class Move {
		public int pieceRow, pieceCol, boxRow, boxCol;
		public boolean jump;

		Move(int pieceRow, int pieceCol, boolean jump, int boxRow, int boxCol) {
			this.pieceRow = pieceRow;
			this.pieceCol = pieceCol;
			this.jump = jump;
			this.boxRow = boxRow;
			this.boxCol = boxCol;
		}
	}
}

