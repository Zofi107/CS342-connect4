import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class JavaFXTemplate extends Application {
	
	public static final int COLUMNS = 7;
	public static final int ROWS = 6;
	public static final int[][] BOARD = new int[ROWS][COLUMNS];  // 2d array that keeps track of who made what moves
	public static int turn = 0;  // used to decide who's move it is
	public static int[] PrevMoveR = new int[43];  // keeps track of the row index when moves are being made
	public static int[] PrevMoveC = new int[43];  // keeps track of the column index when moves are being made
	public static int RMoveCount = 1;  // used to remove a move
	public static TextArea t1;  // displays who's turn it is
	public static TextArea t2;  // displays the moves made
	public static int[] WinR = new int[7];  // used to highlight a winner if there is a winner
	public static int[] WinC = new int[7];  // used to highlight a winner if there is a winner
	public static int WinCount = 0; // used when looking for a winner
	public static GridPane Grid = new GridPane();  // the board grid
	public static MenuItem ReverseMove = new MenuItem("Reverse Move");  // menu item
	public static Stage stage;
	public static Scene scene;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
	
	// Makes the Board
	public static void makeboard(GridPane Grid, int r, int c) {
		Grid.setHgap(5);
		Grid.setVgap(5);
		for(int i = 0; i < COLUMNS; i++) {
			for(int j = 0; j < ROWS; j++) {
				GameButton button = new GameButton(j, i, stage);
				Grid.add(button, i, j);
			}
		}
	}

	// Resets the button to remove back to white
	private void resetGameButton(GridPane gridPane, int col, int row) {
	    for (Node node : gridPane.getChildren()) {
	        if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
	            node.setStyle("-fx-background-color: white");
	        }
	    }
	}
	
	// Highlights the Winner
	public static void setWinner(GridPane gridPane, int col, int row) {
	    for (Node node : gridPane.getChildren()) {
	        if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
	            node.setStyle("-fx-background-color: #39FF14");
	        }
	    }
	}
	
    //changes the most recent button move back to empty
	public void rmove() {
        if(turn > 0)
        {
            BOARD[PrevMoveR[RMoveCount-1]][PrevMoveC[RMoveCount-1]] = 0;
            resetGameButton(Grid, PrevMoveC[RMoveCount-1], PrevMoveR[RMoveCount-1]);
            RMoveCount--;
            WinCount = 0;
            turn--;
            if (turn%2 == 0) {
				JavaFXTemplate.t1.setText("TURN:\nPlayer 1's Turn");
            } else {
				JavaFXTemplate.t1.setText("TURN:\nPlayer 2's Turn"); 
            }
        }
    }
	
	

	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Welcome to Connect 4");
		stage = primaryStage;
		
		//Start Page buttons
		Button playgame = new Button();
		Image pgb = new Image("Playgamebutton.jpg", 236, 62, false, false);
		ImageView view = new ImageView(pgb);
		playgame.setPrefHeight(62);
		playgame.setPrefWidth(236);
		playgame.setGraphic(view);
		HBox Pg = new HBox(playgame);
		BorderPane Wborder = new BorderPane();
		Pg.setAlignment(Pos.CENTER_RIGHT);
		Themes welcometheme = new Themes();
		Wborder.setBackground(welcometheme.setbg("welcomescreen.jpg"));
		Wborder.setPadding(new Insets(50));
		Wborder.setBottom(Pg);
		Scene startpage = new Scene(Wborder, 1920, 1017);
		primaryStage.setScene(startpage);
		primaryStage.show();
		
		// Making the board
		BorderPane border = new BorderPane();
		makeboard(Grid, ROWS, COLUMNS);
		
		// The text fields
		t1 = new TextArea("TURN:\nPlayer 1's Turn.");
		t2 = new TextArea("Moves made:");
		t1.setPrefHeight(40);
		t2.setPrefHeight(535);
		t1.setEditable(false);
		t2.setEditable(false);
		VBox text = new VBox(10,t1, t2);
		
		// The Menu Options
		MenuBar bar = new MenuBar();
		Menu theme = new Menu("Themes");
		Menu gameplay = new Menu("GamePlay");
		Menu options = new Menu("Options");
		
		// The Gameplay menu item
		bar.getMenus().add(gameplay);
		
		gameplay.getItems().add(ReverseMove);
		
		// Reverse Move Option
		ReverseMove.setOnAction(e-> {
			rmove();
		});
		
		// The Theme menu item
		bar.getMenus().add(theme);
		MenuItem Default = new MenuItem("Original theme");
		MenuItem Anime = new MenuItem("Theme 1");
		MenuItem Meme = new MenuItem("Theme 2");
		
		theme.getItems().add(Default);
		theme.getItems().add(Anime);
		theme.getItems().add(Meme);
		
		// Default theme option
		Default.setOnAction(e->{
			Themes t = new Themes();
			border.setBackground(t.setbg("defaulttheme.jpg"));
		});
		
		// Anime theme option
		Anime.setOnAction(e-> {
			Themes t = new Themes();
			border.setBackground(t.setbg("animetheme.jpg"));
		});
		
		// Meme theme option
		Meme.setOnAction(e-> {
			Themes t = new Themes();
			border.setBackground(t.setbg("memetheme.jpg"));
		});
		
		// The Option menu item
		bar.getMenus().add(options);
		MenuItem HtP = new MenuItem("How to play");
		MenuItem Ng = new MenuItem("New game");
		MenuItem Exit = new MenuItem("Exit");
		options.getItems().add(HtP);
		options.getItems().add(Ng);
		options.getItems().add(Exit);
		
		// Exit Option
		Exit.setOnAction(e-> Platform.exit());

		
		// New Game Option
		Ng.setOnAction(e-> {
			Grid.getChildren().clear();
			makeboard(Grid, ROWS, COLUMNS);
			turn = 0;
			WinCount = 0;
			RMoveCount = 1;
			if(ReverseMove.isDisable())
				ReverseMove.setDisable(false);
			if(Grid.isMouseTransparent())
				Grid.setMouseTransparent(false);
			t1.setText("Player 1's Turn.");
			t2.setText("Moves made:");
			
		});
		
		Themes t = new Themes();
		border.setBackground(t.setbg("defaulttheme.jpg"));
		Grid.setAlignment(Pos.CENTER);
		border.setCenter(Grid);
		border.setRight(text);
		border.setTop(bar);

		scene = new Scene(border, 1920, 1017);
		
		//Play game button on welcome screen
		playgame.setOnAction(e->{
			primaryStage.setScene(scene);
			primaryStage.show();
			
		});
		
		// How To Play Option
		HtP.setOnAction(e-> {
			BorderPane bx = new BorderPane();
			TextArea instruct = new TextArea();
			instruct.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
			instruct.setText("RULES:"
					+ "\nTo win Connect Four, all you have to do is connect four of your colored checker pieces"
					+ "\nin a row, much the same as tic tac toe. This can be done horizontally, vertically, or diagonally."
					+ "\nEach player will select a button on the board at a time. This will give you a chance to either"
					+ "\nbuild your row or stop your opponent from getting four in a row.The game is over either when"
					+ "\nyou or your friend reaches four in a row, or when all 42 slots are filled, ending in a"
					+ "\nstalemate."
					+ "\nUse the reverse move button located in the GamePlay menu option to undo a move made.");
			Button ex =new Button("Back");
			ex.setPrefHeight(25);
			ex.setPrefWidth(50);
			ex.setOnAction(b-> {
				primaryStage.setScene(scene);
				primaryStage.show();
			});
			bx.setAlignment(ex, Pos.CENTER);
			bx.setCenter(instruct);
			bx.setBottom(ex);
			
			Scene HowtoPlay = new Scene(bx, 1000, 250);
			primaryStage.setScene(HowtoPlay);
			primaryStage.show();
		});
		
	}
}

