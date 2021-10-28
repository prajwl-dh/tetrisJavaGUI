/*
 * This is the class that provides the data structures and logic for our game
 * In this program, it will have a 2D array for the game board
 * and methods to validate a move and make a move.
 */

/*
 * Game logic with data structure and simple methods
 * @author prajwaldhungana
 * @version 3.000
 * May 5, 2021
 */

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class TetrisGame
{
    private TetrisBrick fallingBrick;
    private int[][] background;
    int randomBrick;
    Random randomGen;
    int row = 20;
    int col = 12;
    int numSegments = 4;
    int state;
    int gameScore = 0;

    Color elBrickColor = Color.decode("#56E39F");
    Color stackBrickColor = Color.decode("#FADF63");
    Color jayBrickColor = Color.decode("#53B3CB");
    Color squareBrickColor = Color.decode("#CE5374");
    Color essBrickColor = Color.decode("#BC96E6");
    Color longBrickColor = Color.decode("#E9724C");
    Color zeeBrickColor = Color.decode("#EA3788");

    public TetrisGame()
    {
        background = new int[row][col];

        spawnBrick();
    }

    public int fetchPosition(int row, int col)
    {
        return background[row][col];
    }

    public int fetchRows()
    {
        return background.length;
    }

    public int fetchCols()
    {
        return background[0].length;
    }


    public void initBoard(int start_x, int start_y, int cell_size, Graphics g)
    {
        int gameCol;
        int gameRow = start_y;

        for (int row = 0; row < fetchRows(); row++)
        {
            gameCol = start_x;

            for (int col = 0; col < fetchCols(); col++)
            {
                g.setColor(Color.white);
                g.fillRect(gameCol,gameRow,cell_size,cell_size);

                gameCol += cell_size;
            }
            gameRow += cell_size;
        }
    }

    public void newGame()
    {
        highScoreToFile(gameScore);
        background = new int[row][col];
        gameScore = 0;
        spawnBrick();
    }


    public void spawnBrick()
    {
        int totalNoOfBricks = 7;
        randomGen = new Random();
        randomBrick = randomGen.nextInt(totalNoOfBricks);

        int brickOrientation = 0;
        int numberOfSegment = 4;

        int numberOfRows = 20;
        int numberOfCols = 12;

        switch (randomBrick)
        {
            case 0:
                fallingBrick = new ElBrick(brickOrientation, numberOfSegment, numberOfCols,elBrickColor);
                return;
            case 1:
                fallingBrick = new StackBrick(brickOrientation, numberOfSegment, numberOfCols,stackBrickColor);
                return;
            case 2:
                fallingBrick = new JayBrick(brickOrientation, numberOfSegment, numberOfCols,jayBrickColor);
                return;
            case 3:
                fallingBrick = new SquareBrick(brickOrientation, numberOfSegment, numberOfCols,squareBrickColor);
                return;
            case 4:
                fallingBrick = new EssBrick(brickOrientation, numberOfSegment, numberOfCols,essBrickColor);
                return;
            case 5:
                fallingBrick = new LongBrick(brickOrientation, numberOfSegment, numberOfCols,longBrickColor);
                return;
            case 6:
                fallingBrick = new ZeeBrick(brickOrientation, numberOfSegment, numberOfCols,zeeBrickColor);
                return;

        }
    }

    public void makeMove(int getKey)
    {
        if (getKey == 1)
        {
            if (validateMove())
            {
                fallingBrick.moveLeft();
            }
        }

        else if (getKey == 2)
        {
            if (validateMove())
            {
                if (randomBrick == 5)
                {
                    if (validateMove())
                    {
                        fallingBrick.moveLongBrick(randomBrick);
                    }
                }
                else if (randomBrick == 3)
                {
                    if (validateMove())
                    {
                        fallingBrick.moveSquareBrick(randomBrick);
                    }
                }
                else
                {
                    fallingBrick.moveRight();
                }
            }
            else
            {
                if (validateMove())
                {
                    fallingBrick.moveRight();
                }
            }
        }

        else if (getKey == 3)
        {
            state = (state+1)%2;
        }
    }

    public boolean validateMove()
    {
        int numberOfRows = 20;
        int numberOfCols = 12;

        int[][] positionBrick = fallingBrick.getPosition();

        //check if the brick is not above top
        if (fallingBrick.getBottom() <= 0 )
        {
            return false;
        }

        //check if the brick is not below the bottom
        if (fallingBrick.getBottom() >= numberOfRows)
        {
            return false;
        }

        //check if falling brick is not overlapping the painted brick
        for (int currentSegment = 0; currentSegment < numSegments; currentSegment++)
        {
            int previousBrickCol = positionBrick[currentSegment][0];
            int previousBrickRow =positionBrick[currentSegment][1];

            if (background[previousBrickRow][previousBrickCol] > 0)
            {
                return false;
            }
        }

        return true;
    }

    public void transferColor()
    {
        int[][] fallingBrickPosition = fallingBrick.getPosition();

        int redColor = 1;
        int blueColor = 2;
        int tealColor = 3;
        int yellowColor = 4;
        int purpleColor = 5;
        int greenColor = 6;
        int orangeColor = 7;

        for(int currentSeg = 0; currentSeg < numSegments; currentSeg++)
        {
            int col = fallingBrickPosition[currentSeg][0];
            int row = fallingBrickPosition[currentSeg][1];

            switch(randomBrick)
            {
                case 0:
                    background[row][col] = greenColor;
                    break;
                case 1:
                    background[row][col] = yellowColor;
                    break;
                case 2:
                    background[row][col] = blueColor;
                    break;
                case 3:
                    background[row][col] = redColor;
                    break;
                case 4:
                    background[row][col] = tealColor;
                    break;
                case 5:
                    background[row][col] = orangeColor;
                    break;
                case 6:
                    background[row][col] = purpleColor;
                    break;
            }
        }
    }

    public void rotate()
    {
        if (randomBrick == 5)
        {
            if (validateMove())
            {
                fallingBrick.rotate();
            }
        }
        else
        {
            fallingBrick.rotate();
        }
    }

    public int brickColor(int row, int cols)
    {
        int brickColor = -1;
        brickColor = background[row][cols];
        return brickColor;
    }

    public TetrisBrick getFallingBrick()
    {
        return fallingBrick;
    }

    public boolean gameOverDetection()
    {
        boolean endDetection = false;
        int topRow = 1;

        for (int tempCol = 0; tempCol < col; tempCol++)
        {
            if (background[topRow][tempCol] > 0)
            {
                endDetection = true;
            }
        }


        return endDetection;
    }

    //method to detect full rows
    public boolean fullRowDetection()
    {
        boolean fullRow = true;
        int finalRow = row - 1;

        for (int tempRow = 0; tempRow < row; tempRow++)
        {
            for (int tempCol = 0; tempCol < col; tempCol++)
            {
                if (background[finalRow][tempCol] == 0)
                {
                    fullRow = false;
                }
            }
        }

        if (fullRow == true)
        {
            rowDeletion();
            dropBricks();
        }
        return fullRow;
    }

    public void rowDeletion()
    {
        int scoreIncrement = 100;
        int finalRow = row - 1;
        for (int tempCol = 0; tempCol < col; tempCol++)
        {
            background[finalRow][tempCol] = 0;
        }
        gameScore += scoreIncrement;
    }

    //method to drop down bricks by 1 row
    public void dropBricks()
    {
        int finalRow = row-1;
        int finalCol = col-1;

        for (int tempCol = finalCol; tempCol>=0; tempCol--)
        {
            for (int tempRow = finalRow; tempRow >=0; tempRow--)
            {
                int newRow = tempRow + 1;

                if (background[tempRow][tempCol] > 0)
                {
                    background[newRow][tempCol] = background[tempRow][tempCol];
                    background[tempRow][tempCol] = 0;
                }
            }
        }
    }

    //Writing high scores to file
    public void highScoreToFile(int highestScore)
    {
        File highScore = new File("highScore.csv");
        try
        {
            FileWriter addingHighScore = new FileWriter(highScore, true);

            if (highestScore > 0)
            {
                addingHighScore.write(highestScore + "");
                addingHighScore.write("\n");
            }
            addingHighScore.close();
        }

        catch (IOException ioe)
        {
            String userOutput = "Warning: error in data from\n" + "file: " + "highScore.csv";
            JOptionPane.showMessageDialog(null, userOutput, "File IO Error" , 2);
        }
    }

    //load high score from file
    public void highScoreFromFile()
    {
        File inputFile = new File("highScore.csv");
        int totalHighscoreTracker = 0;
        int tenHighScore = 10;

        try
        {
            //first scanner to get number of lines
            Scanner lineScanner = new Scanner(inputFile).useDelimiter("[,\n]");
            String finalOutput = "";

            while (lineScanner.hasNextLine())
            {
                lineScanner.nextLine();
                totalHighscoreTracker += 1;
            }
            lineScanner.close();

            try
            {
                if (totalHighscoreTracker > 0)
                {
                    //second scanner to read from file
                    Scanner inScanner = new Scanner(inputFile).useDelimiter("[,\n]");
                    int highScoreArray[];
                    highScoreArray = new int[totalHighscoreTracker];

                    for (int index = 0; index < totalHighscoreTracker; index++)
                    {
                        int score = inScanner.nextInt();
                        highScoreArray[index] = score;
                    }

                    if (totalHighscoreTracker > 2)
                    {
                        //bubble sorting highscore in descending order
                        for (int index = 0; index < totalHighscoreTracker - 1; index++) {
                            for (int indexTwo = 0; indexTwo < totalHighscoreTracker - index-1; indexTwo++) {
                                if (highScoreArray[indexTwo] < highScoreArray[indexTwo + 1]) {
                                    int temp = highScoreArray[indexTwo];
                                    highScoreArray[indexTwo] = highScoreArray[indexTwo + 1];
                                    highScoreArray[indexTwo + 1] = temp;
                                }
                            }
                        }
                    }

                    if (totalHighscoreTracker == 2)
                    {
                        for (int index = 0; index < totalHighscoreTracker-1; index++)
                        {
                            if (highScoreArray[index] < highScoreArray[index+1])
                            {
                                int temp = highScoreArray[index];
                                highScoreArray[index] = highScoreArray[index+1];
                                highScoreArray[index+1] = temp;
                            }
                        }
                    }


                    if (totalHighscoreTracker < 10) {
                        //printing top10 high score in Joptionpane
                        for (int index = 0; index < totalHighscoreTracker; index++) {
                            finalOutput += "<html><b>";
                            finalOutput += highScoreArray[index];
                            finalOutput += "</b></html>";
                            finalOutput += "\n";
                        }
                        JOptionPane.showMessageDialog(null, finalOutput, "Top 10 Highscore", 1);
                    }
                    else
                    {
                        for (int index = 0; index < tenHighScore; index++)
                        {
                            finalOutput += "<html><b>";
                            finalOutput += highScoreArray[index];
                            finalOutput += "</b></html>";
                            finalOutput += "\n";
                        }
                        JOptionPane.showMessageDialog(null, finalOutput, "Top 10 Highscore", 1);
                    }
                }
                else if (totalHighscoreTracker == 0)
                {
                    JOptionPane.showMessageDialog(null, "Empty HighScore", "Top 10 Highscore", 1);
                }
            }
            catch (IOException ioe)
            {
                String userOutput = "Warning: error in data from\n" + "file: " + "highScore.csv";
                JOptionPane.showMessageDialog(null, userOutput, "File IO Error" , 2);
            }
        }
        catch (IOException ioe)
        {
            String userOutput = "Warning: error in data from\n" + "file: " + "highScore.csv";
            JOptionPane.showMessageDialog(null, userOutput, "File IO Error" , 2);
        }
    }

    public void clearHighScore()
    {
        File targetFile = new File("highScore.csv");

        try
        {
            PrintWriter clearFile = new PrintWriter(targetFile);
            clearFile.print("");
            clearFile.close();
            JOptionPane.showMessageDialog(null, "All High Scores Cleared", "HighScore Cleared", 1);
        }
        catch (IOException ioe)
        {
            String userOutput = "Warning: error in data from\n" + "file: " + "highScore.csv";
            JOptionPane.showMessageDialog(null, userOutput, "File IO Error" , 2);
        }
    }

    public void saveGameToFile()
    {
        File savedGame = new File("saveGame.csv");
        try
        {
            FileWriter savingBackground = new FileWriter(savedGame,false);
            for (int tempRow = 0; tempRow < row; tempRow++)
            {
                for (int tempCol = 0; tempCol < col; tempCol++)
                {
                    savingBackground.write(background[tempRow][tempCol] + ",");
                }
                savingBackground.write("\n");
            }
            savingBackground.close();
        }
        catch (IOException ioe)
        {
            String userOutput = "Warning: error in data from\n" + "file: " + "saveGame.csv";
            JOptionPane.showMessageDialog(null, userOutput, "File IO Error" , 2);
        }
    }

    public void loadGameFromFile()
    {
        File savedGame = new File("saveGame.csv");
        int totalNoOfLines = 0;

        try
        {
            Scanner lineScanner = new Scanner(savedGame).useDelimiter("[,\n]");

            while (lineScanner.hasNextLine())
            {
                lineScanner.nextLine();
                totalNoOfLines += 1;
            }
            lineScanner.close();

            Scanner inScanner = new Scanner(savedGame).useDelimiter("[,\n]");
            for (int tempRow = 0; tempRow < row; tempRow++)
            {
                for (int tempCol = 0; tempCol < col; tempCol++)
                {
                    background[tempRow][tempCol] = inScanner.nextInt();
                }
                inScanner.nextLine();
            }
            inScanner.close();
        }
        catch (IOException ioe)
        {
            String userOutput = "Warning: error in data from\n" + "file: " + "saveGame.csv";
            JOptionPane.showMessageDialog(null, userOutput, "File IO Error" , 2);
        }
    }
}
