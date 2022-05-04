import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class GameButton extends Button{
	private int sWidth = 100, sLength = 100;
	private int Row, Column;  // keeps track of which row, column the button is located in the grid
	private Stage sc;
	public GameButton(int r, int c, Stage prime) {
		super();  // calls button
		String s=String.valueOf(r)+String.valueOf(c);
		sc = prime;
		this.setText(s);
		this.setPrefSize(sWidth, sLength);  // making square		
		this.setShape(new Circle(50));
		this.setStyle("-fx-background-color: white");
		Row = r;
		Column = c;
		JavaFXTemplate.BOARD[Row][Column]=0;
		// calls the turn function
		turn();
		
	}
	
	// checks if there is a Tie
	public boolean isTie() {
		
		// for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 7; j++) {
				// if there is a single empty space on the board then there is no tie.
				if(JavaFXTemplate.BOARD[0][j] == 0) {
					return false;
				}
			}
		//}
		return true;
	}
	
	// checks if a player won
	public boolean isWin(int player) {

		//Tracks horizontal wins 
		for(int x = 0; x < 6; x++)
		{
			for(int y = 0; y < 4; y++)
			{
				JavaFXTemplate.WinCount = 0;
				for(int u = 0; u < 4; u++)
				{
					if(JavaFXTemplate.BOARD[x][y+u] == player) {
						JavaFXTemplate.WinR[JavaFXTemplate.WinCount] = x;
						JavaFXTemplate.WinC[JavaFXTemplate.WinCount] = y+u;
						JavaFXTemplate.WinCount++;
					} else
						JavaFXTemplate.WinCount = 0;
					
					if(JavaFXTemplate.WinCount >= 4) 
						return true;
				}
			}
		}
		
		//Tracks Vertical wins
			for(int y = 0; y < 7; y++)
				{
				for(int x = 0; x < 3; x++)
					{
						JavaFXTemplate.WinCount = 0;
						for(int u = 0; u < 4; u++)
						{
							if(JavaFXTemplate.BOARD[x+u][y] == player) {
								JavaFXTemplate.WinR[JavaFXTemplate.WinCount] = x+u;
								JavaFXTemplate.WinC[JavaFXTemplate.WinCount] = y;
								JavaFXTemplate.WinCount++;
							} else
								JavaFXTemplate.WinCount = 0;
							
							if(JavaFXTemplate.WinCount >= 4) 
								return true;
						}
					}
				}
		

		//Tracks Diagonal wins (right)
		for(int x = 0; x < 3; x++)
		{
			for(int y = 0; y < 4; y++)
			{
				JavaFXTemplate.WinCount = 0;
				for(int u = 0; u < 4; u++)
				{
					if(JavaFXTemplate.BOARD[x+u][y+u] == player) {
						JavaFXTemplate.WinR[JavaFXTemplate.WinCount] = x+u;
						JavaFXTemplate.WinC[JavaFXTemplate.WinCount] = y+u;
						JavaFXTemplate.WinCount++;
					}
					else
						JavaFXTemplate.WinCount = 0;
					
					if(JavaFXTemplate.WinCount >= 4) 
						return true;
				}
			}
		}
		
		//Tracks Diagonal wins (left)
		for(int x = 3; x < JavaFXTemplate.ROWS; x++)
		{
			for(int y = 0; y < 4; y++)
			{
				JavaFXTemplate.WinCount = 0;
				for(int u = 0; u < 4; u++)
				{
					if(JavaFXTemplate.BOARD[x-u][y+u] == player) {
						JavaFXTemplate.WinR[JavaFXTemplate.WinCount] = x-u;
						JavaFXTemplate.WinC[JavaFXTemplate.WinCount] = y+u;
						JavaFXTemplate.WinCount++;
					}
					else
						JavaFXTemplate.WinCount = 0;
					
					if(JavaFXTemplate.WinCount >= 4) 
						return true;
				}
				if(JavaFXTemplate.WinCount >= 4) 
					return true;
			}
		}
		return false;
	}

	// finds out who's turn it is, checks if it is a valid move or not and then checks if anyone won.
	public void turn() {
		this.setOnAction(e-> {
			
			String oldline;
			
			if(JavaFXTemplate.turn%2 == 0) {  // If it is Player 1's turn
				// Valid Move
				if((Row == 5 && JavaFXTemplate.BOARD[Row][Column] == 0) || (JavaFXTemplate.BOARD[Row][Column] == 0 && JavaFXTemplate.BOARD[Row+1][Column] != 0)) {
					
					JavaFXTemplate.BOARD[Row][Column] = 1;				
					this.setStyle("-fx-background-color: RED");			
					JavaFXTemplate.t1.setText("TURN:\nPlayer 2's Turn");  
					oldline =  JavaFXTemplate.t2.getText();
					JavaFXTemplate.t2.setText(oldline + "\nPlayer 1 moved to "+Row + ","+Column+". This is a valid move.");
					JavaFXTemplate.PrevMoveR[JavaFXTemplate.RMoveCount] = Row;
					JavaFXTemplate.PrevMoveC[JavaFXTemplate.RMoveCount] = Column;
					JavaFXTemplate.RMoveCount++;
					
				} else {  // InValid Move
					
					JavaFXTemplate.turn--;
					oldline =  JavaFXTemplate.t2.getText();
					JavaFXTemplate.t2.setText(oldline + "\nPlayer 1 moved to "+Row + ","+Column+". This is NOT a valid move. Player 1 pick again.");
					
				}
				
			} else {  // If it is Player 2's turn
				// Valid Move
				if((Row == 5 && JavaFXTemplate.BOARD[Row][Column] == 0) || (JavaFXTemplate.BOARD[Row][Column] == 0 && JavaFXTemplate.BOARD[Row+1][Column] != 0)) {
					
					JavaFXTemplate.BOARD[Row][Column] = 2;
					this.setStyle("-fx-background-color: YELLOW");
					JavaFXTemplate.t1.setText("TURN:\nPlayer 1's Turn"); 
					oldline =  JavaFXTemplate.t2.getText();
					JavaFXTemplate.t2.setText(oldline + "\nPlayer 2 moved to "+Row + ","+Column+". This is a valid move.");
					JavaFXTemplate.PrevMoveR[JavaFXTemplate.RMoveCount] = Row;
					JavaFXTemplate.PrevMoveC[JavaFXTemplate.RMoveCount] = Column;
					JavaFXTemplate.RMoveCount++;
					
				} else {  // InValid Move
					
					JavaFXTemplate.turn--;
					oldline =  JavaFXTemplate.t2.getText();
					JavaFXTemplate.t2.setText(oldline + "\nPlayer 2 moved to "+Row + ","+Column+". This is NOT a valid move. Player 2 pick again.");
					
				}
				
			}
			
			// Check if Player 1 or Player 2 Won
			if(isWin(1) || isWin(2)) {
				
				for(int a = 0; a < 4; a++) {
					JavaFXTemplate.setWinner(JavaFXTemplate.Grid, JavaFXTemplate.WinC[a], JavaFXTemplate.WinR[a]);
				}
				
				JavaFXTemplate.Grid.setMouseTransparent(true);
				JavaFXTemplate.ReverseMove.setDisable(true);
				
				
				PauseTransition pause = new PauseTransition(Duration.seconds(4));
				pause.setOnFinished(g->{
					
					BorderPane b = new BorderPane();
					b.setPadding(new Insets(50));
					Themes Winner = new Themes();
					if(isWin(1))
						b.setBackground(Winner.setbg("Player1win.png"));
					else
						b.setBackground(Winner.setbg("Player2win.png"));
					Button Playagain = new Button("Play Again");
					Playagain.setPrefSize(100, 50);
					Playagain.setOnAction(w-> {
						
						JavaFXTemplate.Grid.getChildren().clear();
						JavaFXTemplate.makeboard(JavaFXTemplate.Grid, JavaFXTemplate.ROWS, JavaFXTemplate.COLUMNS);
						JavaFXTemplate.turn = 0;
						JavaFXTemplate.WinCount = 0;
						JavaFXTemplate.RMoveCount = 1;
						if(JavaFXTemplate.ReverseMove.isDisable())
							JavaFXTemplate.ReverseMove.setDisable(false);
						if(JavaFXTemplate.Grid.isMouseTransparent())
							JavaFXTemplate.Grid.setMouseTransparent(false);
						JavaFXTemplate.t1.setText("Player 1's Turn.");
						JavaFXTemplate.t2.setText("Moves made:");
						sc.setScene(JavaFXTemplate.scene);
						
						sc.show();
					});
					
					Button exit = new Button("Exit");
					exit.setPrefSize(100, 50);
					exit.setOnAction(r->Platform.exit());
					
					HBox h = new HBox(25,Playagain,exit);
					h.setAlignment(Pos.CENTER);
					b.setAlignment(h, Pos.CENTER);
					//b.setCenter(Winner);
					b.setBottom(h);
					
					Scene winnerwindow = new Scene(b, 1920, 1017);
					sc.setScene(winnerwindow);
					sc.show();
				});
				pause.play();
				
			} else if (isTie()) {
				
				JavaFXTemplate.Grid.setMouseTransparent(true);
				JavaFXTemplate.ReverseMove.setDisable(true);
				
				PauseTransition pause = new PauseTransition(Duration.seconds(4));
				pause.setOnFinished(g->{
					BorderPane b = new BorderPane();
					b.setPadding(new Insets(50));
					Themes Winner = new Themes();					
					b.setBackground(Winner.setbg("Tiegame.png"));
					Button Playagain = new Button("Play Again");
					Playagain.setPrefSize(100, 50);
					Playagain.setOnAction(w-> {
						JavaFXTemplate.Grid.getChildren().clear();
						JavaFXTemplate.makeboard(JavaFXTemplate.Grid, JavaFXTemplate.ROWS, JavaFXTemplate.COLUMNS);
						JavaFXTemplate.turn = 0;
						JavaFXTemplate.WinCount = 0;
						JavaFXTemplate.RMoveCount = 1;
						if(JavaFXTemplate.ReverseMove.isDisable())
							JavaFXTemplate.ReverseMove.setDisable(false);
						if(JavaFXTemplate.Grid.isMouseTransparent())
							JavaFXTemplate.Grid.setMouseTransparent(false);
						JavaFXTemplate.t1.setText("Player 1's Turn.");
						JavaFXTemplate.t2.setText("Moves made:");
						sc.setScene(JavaFXTemplate.scene);
						sc.show();
						
					});
					
					Button exit = new Button("Exit");
					exit.setPrefSize(100, 50);
					exit.setOnAction(r->Platform.exit());
					
					HBox h = new HBox(25,Playagain,exit);
					h.setAlignment(Pos.CENTER);
					b.setAlignment(h, Pos.CENTER);
					b.setBottom(h);
					
					Scene winnerwindow = new Scene(b, 1920, 1017);
					sc.setScene(winnerwindow);
					sc.show();	
				});
				pause.play();
			}

			JavaFXTemplate.turn++;
		});
	}
	
	// gets the row
	public int getRow() {
		return Row;
	}
	
	// gets the column
	public int getColumn() {
		return Column;
	}
	
}
