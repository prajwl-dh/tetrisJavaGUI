/*
 * This is the class to create StackBrick
 *  and is a sub class to TetrisBrick superclass
 */

/*
 * Tetris bricks
 * @author prajwaldhungana
 * @version 3.000
 * May 5, 2021
 */

import javax.swing.*;
import java.awt.*;

public class StackBrick extends TetrisBrick
{
    int orientation = 1;

    public StackBrick(int orien, int numSegmnt, int colNum, Color colrr)
    {
        super(orien,numSegmnt,colNum,colrr);
    }

    public int[][] initPosition(int cols)
    {
        int[][] finalPosition = new int[4][2];
        finalPosition[0][0] = 0;
        finalPosition[1][0] = 1;
        finalPosition[2][0] = 2;
        finalPosition[3][1] = -1;
        finalPosition[3][0] = 1;
        return finalPosition;
    }

    public void rotate()
    {
        if (orientation == 1)
        {
            position[0][0] = position[1][0];
            position[0][1] += 1;
            orientation = 2;
        }

        else if (orientation == 2)
        {
            unrotate();
            position[3][1] += 2;
            orientation = 3;
        }

        else if (orientation == 3)
        {
            unrotate();
            position[2][1] += 1;
            position[2][0] -= 1;
            orientation = 4;
        }

        else if (orientation == 4)
        {
            unrotate();
        }
    }

    public void unrotate()
    {
        if (orientation == 2)
        {
            position[0][0] = position[1][0];
            position[0][0] -= 1;
            position[0][1] -= 1;
        }

        else if (orientation == 3)
        {
            position[3][1] -= 2;
        }

        else if (orientation == 4)
        {
            position[2][1] -= 1;
            position[2][0] += 1;
            orientation = 1;
        }
    }
}
