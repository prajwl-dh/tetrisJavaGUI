/*
 * This is the class to create SquareBrick
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

public class SquareBrick extends TetrisBrick
{
    public SquareBrick(int orien, int numSegmnt, int colNum, Color colrr)
    {
        super(orien,numSegmnt,colNum,colrr);
    }

    public int[][] initPosition(int cols)
    {
        int[][] finalPosition = new int[4][2];
        finalPosition[3][1] = -1;
        finalPosition[3][0] = 1;
        finalPosition[2][1] = -1;
        finalPosition[2][0] = 0;
        finalPosition[1][0] = 1;
        return finalPosition;
    }

    public void rotate()
    {
        //no need rotation
    }

    public void unrotate()
    {
        //no need rotation
    }
}
