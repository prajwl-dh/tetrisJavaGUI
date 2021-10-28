/*
 * This is the class to create ZeeBrick
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

public class ZeeBrick extends TetrisBrick
{
    int orientation = 1;

    public ZeeBrick(int orien, int numSegmnt, int colNum, Color colrr)
    {
        super(orien,numSegmnt,colNum,colrr);
    }

    public int[][] initPosition(int cols)
    {
        int[][] finalPosition = new int[4][2];
        finalPosition[0][1] = -1;
        finalPosition[0][0] = 0;
        finalPosition[1][1] = -1;
        finalPosition[1][0] = 1;
        finalPosition[2][1] = 0;
        finalPosition[2][0] = 1;
        finalPosition[3][1] = 0;
        finalPosition[3][0] = 2;
        return finalPosition;
    }

    public void rotate()
    {
        if (orientation == 1)
        {
            position[0][0] += 2;
            position[0][1] -= 1;
            position[3][1] -= 1;
            orientation = 2;
        }
        else
        {
            unrotate();
        }
    }


    public void unrotate()
    {
        position[0][0] -= 2;
        position[0][1] += 1;
        position[3][1] += 1;
        orientation = 1;
    }
}
