/**
 * @author Moataz Soliman
 * 
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SuokuSolver {
	public static int SolutionsCounter =0;
    public static final int PuzzleSize=9;
    private  int[][] Puzzle;
    
    public SuokuSolver()
    {
    	Puzzle = new int[PuzzleSize][PuzzleSize];					//Create a grid with specific size
    }
    
    public static void main(String[] args) {
    	
    	
    	
    	SuokuSolver s = new SuokuSolver();
    	
    	System.out.print("Please enter the path for the input file:");
    	//creating a reader to read the path from the user
    	BufferedReader keyboardReader = new BufferedReader(
				new InputStreamReader(System.in));
    	
    	String file;
		try {
			file = keyboardReader.readLine();	// read the path of the input puzzle
			s.readfile(file);	
	        System.out.println("The Puzzle in file"+ file);
	        s.printPuzzle();		// printing the grid
	        s.FindSolution();		// calling a function to solve the puzzle grid
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
    
    /**
     * calls a recursive function that uses backtracking recursion to solve the puzzle
     * 
     */
    public void FindSolution(){
    	System.out.println("The puzzle Solutions for this text file:");
       	SolvePuzzle(0,0);		// Calling the recursive function with the starting cell 
       						// cell (0,0) the first cell in the puzzle
        System.out.println("The number of solutions is: " + SolutionsCounter);
        
    }
    
    /**
     * a recursive function that checks the cells and adds valid numbers using resursion and 
     * backtraching
     * @param i
     * @param j
     * @return
     */
    public boolean SolvePuzzle( int i, int j )
    {
       if( i >= PuzzleSize ){		// checking if we reached the last row which means we find a solution
    	 printPuzzle();			// print the grid with the solution
          SolutionsCounter++;	// increment the solution counter
          return true;
       }

       	// If the cell is not empty, continue with the next cell
       if( Puzzle[i][j] != 0 ){					// If the cell is not empty
    	   if( j < PuzzleSize-1 )							// checking all columns
    	          SolvePuzzle( i, j + 1 ) ;	 	// call solving function with the next cell on the next
    	   										//column
    	       else
    	          SolvePuzzle( i + 1, 0 ) ;		// We move to the next row if we checked all columns
       }else{
          // Find a valid number for the empty cell
          for( int number = 1; number <= PuzzleSize; number++ )
          {
             if( isValidPosition(i, j, number) )		// check if we can put " number " without crossing the rules
             {
                Puzzle[i][j] = number ;			// filling a cell with a valid number
                 if( j < PuzzleSize-1 )					// check if we cheched the last column
                    SolvePuzzle( i, j + 1 ) ;	// call solving function with the next cell on the next
												//column	
                 else
                    SolvePuzzle( i + 1, 0 ) ;	// We move to the next row if we checked all columns
             }
          }
          
          // Reset the value of the cell to zero for backtracking
          Puzzle[i][j] = 0 ;
       }
       return false;
    }

   
    /**
     * Reads the input for the file whose name is passed in as a parameter
     * @param myfilename
     */
    public void readfile(String myfilename) {
    	
        
        try {
        												
          BufferedReader in = new BufferedReader(new FileReader(myfilename));	//construct the buffer reader that reads the values from the file
          String line = in.readLine();						// Read the next line 
          StringTokenizer Tokenizer = null;
          int i,j;
          int NumEntries = Integer.parseInt(line);			//get the number of entries which is in the first line

          int loop = 0;
          while(loop < NumEntries) {						// check if we reach the end of the file
        	line = in.readLine();
        	Tokenizer = new StringTokenizer(line.toString()," \t"); // specifies that there's a "tab" between numbers
          
            //get the first three number( row , column , value)
    		  i= (new Integer(Tokenizer.nextToken())).intValue();
    		  j= (new Integer(Tokenizer.nextToken())).intValue();
    		  Puzzle[i][j] = (new Integer(Tokenizer.nextToken())).intValue();
            loop++;		// move to the next entry
    	  }
          in.close();
        }
        catch(IOException e) {
    	  e.printStackTrace();
    	}
       
      }
    
    /**
     * Printing the whole grid with values in it
     */
    public void  printPuzzle() {
    	
        for (int i = 0; i < PuzzleSize; i++) {  // going through the rows
            if (i % 3 == 0)	// To put lines after 3 rows
            	System.out.println(" ------- ------- ------- ");
            for (int j = 0; j < PuzzleSize; j++) {
                if (j % 3 == 0) {
                	System.out.print("| ");
                }
                if(Puzzle[i][j] == 0){
                	//if it is zero print a space 
                	System.out.print(" ");
                }
                else{
                	//print the number if the cell
                	System.out.print("" + Puzzle[i][j]);
                }
                System.out.print(' ');
            }
            System.out.println("|");
        }
        //put line at the end
        System.out.println(" ------- ------- ------- ");
        System.out.println("");
    }
    /**
     * Check if putting the number in the cell [i][j] is valid according to the rules
     * @param i
     * @param j
     * @param number
     * @return
     */
     public boolean isValidPosition(int i, int j, int number) {
     	// To check if there's any similar number in the same row
         for (int k = 0; k < PuzzleSize; k++) 
             if (number == Puzzle[i][k])
                 return false;
      	// To check if there's any similar number in the same column
         for (int k = 0; k < PuzzleSize; k++) 
             if (number == Puzzle[k][j])
                 return false;
      	// To check if there's any similar number in the 3x3 box that has the number
         int row = (i / 3)*3;
         int col = (j / 3)*3;
         for (int q = 0; q < 3; q++) 
             for (int p = 0; p < 3; p++)
                 if (number == Puzzle[row+q][col+p])
                     return false;
         return true; 
     }

}