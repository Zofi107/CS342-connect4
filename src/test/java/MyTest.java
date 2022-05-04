import static org.junit.jupiter.api.Assertions.*;
import java.lang.Math; 
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
class MyTest {
	private static int TRACKER = 4;
	private static int[][] grid = new int[6][7];
	private static int P1 = 1;
	private static int P2 = 2;
	public static int counter;
	
	@BeforeAll
	static void setUp()
	{
		for(int a = 0; a < 6; a++)
			for(int b = 0; b < 7; b++)
				//Creates a connect4 grid that's empty
				grid[a][b] =0;
	}
	
	@BeforeEach
	void fillUp()
	{
		for(int a = 0; a < 6; a++)
			for(int b = 0; b < 7; b++)
				//Random fills the grid with 1 or 2
				grid[a][b] = (int) (Math.random()*(P2-P1+1) + P1);
	}
	
	// tests for a vertical win
	@Test
	void getWinVTest() {
		counter = 0;
		for(int x=0; x <6; x++) {
			for(int y = 0; y< 4; y++) {
				for(int i = 0; i < 4; i++) {
					if((grid[x][y+i]==P1) || (grid[x][y+i]==P2))
						{counter++;}
					else
						{counter = 0;}
					
					if(counter >= 4)
						assertTrue(true);
				}
			}
		}
	}
	
	// tests for a horizontal win
	@Test
	void getWinHTest() {
		counter = 0;
		for(int y=0; y <7; y++) {
			for(int x = 0; x< 3; x++) {
				for(int i = 0; i < 4; i++) {
					if((grid[x+i][y]==P1) || (grid[x+i][y]==P2))
						{counter++;}
					else
						{counter = 0;}
					
					if(counter >= 4)
						assertTrue(true);
				}
			}
		}
	}
	
	// tests for a vertical win for player 2
	@Test
	void getWinVTestp2() {
		counter = 0;
		for(int x=0; x <6; x++) {
			for(int y = 0; y< 4; y++) {
				for(int i = 0; i < 4; i++) {
					if((grid[x][y+i]==P2))
						{counter++;}
					else
						{counter = 0;}
					
					if(counter >= 4)
						assertTrue(true);
				}
			}
		}
	}
	
	// tests for a horizontal win for player 1
	@Test
	void getWinHTestp2() {
		counter = 0;
		for(int y=0; y <7; y++) {
			for(int x = 0; x< 3; x++) {
				for(int i = 0; i < 4; i++) {
					if((grid[x+i][y]==P2))
						{counter++;}
					else
						{counter = 0;}
					
					if(counter >= 4)
						assertTrue(true);						
				}
			}
		}
	}
	
	// tests for a left diagonal win
	@Test
	void getWinLDTest() {
		for(int x = 3; x < 6; x++) {
			for(int y = 0; y< 4; y++) {
				counter = 0;
				for(int i = 0; i < 4; i++) {
					if((grid[x-i][y+i]==P1) || (grid[x-i][y+i]==P2))
						{counter++;}
					else
						{counter = 0;}
				}
			}
		}
		assertEquals(TRACKER, counter, "Something went wrong.");
	}

	// tests for a right diagonal win
	@Test
	void getWinRDTest() {
		for(int x = 0; x < 3; x++) {
			for(int y = 0; y< 4; y++) {
				counter = 0;
				for(int i = 0; i < 4; i++) {
					if((grid[x+i][y+i]==P1) || (grid[x+i][y+i]==P2))
						{counter++;}
					else
						{counter = 0;}
				}
			}
		}
		assertEquals(TRACKER, counter, "Something went wrong.");
	}
	
	// tests for a Tie
	@Test
	void getTieTest() {
		for(int x = 0; x < 6; x++)
			for(int y = 0; y < 7; y++)
				if((grid[x][y]== P1) || (grid[x][y] == P2))
					assertTrue(true);
				else
					assertTrue(false, "A player did not choose a space.");
					
	}

}
