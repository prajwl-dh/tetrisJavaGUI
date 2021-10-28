/*
 * This is the class that implements bricks in the game
 * It is also a super class to other brick classes
 */

/*
 * Game logic with data structure and simple methods.
 * @author prajwaldhungana
 * @version 3.000
 * May 5, 2021
 */

import java.awt.*;
import java.awt.event.*;

public abstract class TetrisBrick
{
    int[][] position = new int[4][2];
    private int orientation;
    private int numSegments = 4;
    private int colorNum;
    private Color color;

    public TetrisBrick(int orien, int numSegmnt, int colNum, Color colrr)
    {
        position = initPosition(colNum);
        color = colrr;
        orientation = orien;
        numSegments = numSegmnt;
    }

    public int getOrientation()
    {
        return orientation;
    }

    public Color getColor()
    {
        return color;
    }

    public abstract int[][] initPosition(int cols);

    public int[][] getPosition()
    {
        return position;
    }

    public abstract void rotate();

    public abstract void unrotate();

    public void moveLeft()
    {
        for (int tempRow = 0; tempRow < numSegments; tempRow++)
        {
            if (position[tempRow][0] < 1)
            {
                break;
            }
            else
            {
                position[tempRow][0] -= 1;
            }
        }
    }

    public void moveRight()
    {
        int counter = 0;
        int numberOfColumn = 12;
        int validityChecker = numberOfColumn-3;

        if (position[counter][0] < validityChecker && counter < numSegments)
        {
            for (int tempRow = 0; tempRow < numSegments; tempRow++)
            {
                position[tempRow][0] += 1;
                counter++;
            }
        }
    }

    public boolean moveUP()
    {
        for (int tempRow = 0; tempRow < numSegments; tempRow++)
        {
            position[tempRow][1] -= 1;
        }
        return true;
    }

    public void moveDown()
    {
        for (int tempRow = 0; tempRow < numSegments; tempRow++)
        {
            {
                position[tempRow][1] += 1;
            }
        }
    }

    //this method will check if the brick has reached bottom
    public int getBottom()
    {
        int bottomRow = 0;

        for (int row = 0; row < numSegments; row++)
        {
            if (position[row][1] > bottomRow)
            {
                bottomRow = position[row][1];
            }
        }
        return bottomRow;
    }

    //method to properly move long brick to right without exception
    public void moveLongBrick(int randomBrick)
    {
        if (randomBrick == 5)
        {
            int counter = 0;
            int numberOfColumn = 12;
            int validityChecker = numberOfColumn-4;

            if (position[counter][0] < validityChecker && counter < numSegments)
            {
                for (int tempRow = 0; tempRow < numSegments; tempRow++)
                {
                    position[tempRow][0] += 1;
                    counter++;
                }

            }
        }
    }

    //method to properly move square brick to right without exception
    public void moveSquareBrick(int randomBrick)
    {
        if (randomBrick == 3)
        {
            int counter = 0;
            int numberOfColumn = 12;
            int validityChecker = numberOfColumn-2;

            if (position[counter][0] < validityChecker && counter < numSegments)
            {
                for (int tempRow = 0; tempRow < numSegments; tempRow++)
                {
                    position[tempRow][0] += 1;
                    counter++;
                }
            }
        }
    }
}
