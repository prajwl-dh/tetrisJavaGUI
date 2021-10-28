/*
 * This is the class to create JayBrick
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

public class JayBrick extends TetrisBrick
{
    int orientation = 1;

    public JayBrick(int orien, int numSegmnt, int colNum, Color colrr)
    {
        super(orien,numSegmnt,colNum,colrr);
    }

    public int[][] initPosition(int cols)
    {
        int[][] finalPosition = new int[4][2];
        finalPosition[3][1] = 1;
        finalPosition[3][0] = 2;
        finalPosition[1][0] = 1;
        finalPosition[2][0] = 2;
        return finalPosition;
    }

    public void rotate()
    {
        if (orientation == 1)
        {
            position[1][1] += 1;
            position[0][0] += 2;
            position[0][1] -= 1;
            orientation = 2;
        }

        else if (orientation == 2)
        {
            unrotate();
            position[1][1] += 1;
            position[0][1] += 1;
            position[2][0] -= 2;
            orientation = 3;
        }

        else if (orientation == 3)
        {
            unrotate();
            position[1][1] -= 1;
            position[1][0] += 1;
            position[0][1] -= 1;
            position[0][0] += 3;
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
           position[1][1] -= 1;
           position[0][0] -= 2;
           position[0][1] += 1;
        }

        else if (orientation == 3)
        {
            position[1][1] -= 1;
            position[0][1] -= 1;
            position[2][0] += 2;
        }

        else if (orientation == 4)
        {
            position[1][1] += 1;
            position[1][0] -= 1;
            position[0][1] += 1;
            position[0][0] -= 3;
            orientation = 1;
        }
    }
}
