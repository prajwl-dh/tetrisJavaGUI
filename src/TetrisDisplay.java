/*
 * This is the class that displays the game state and allows
 * the user to enter moves to the game,
 * THe critical components are paintComponent and process move
 */

/*
 * Game logic with data structure and simple methods.
 * @author prajwaldhungana
 * @version 3.000
 * May 5, 2021
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TetrisDisplay extends JPanel
{
    private TetrisGame game;
    private int start_x = 115;
    private int start_y = 40;
    private int cell_size = 40;
    Timer timer;
    int timer_speed;

    Color elBrickColor = Color.decode("#56E39F");
    Color stackBrickColor = Color.decode("#FADF63");
    Color jayBrickColor = Color.decode("#53B3CB");
    Color squareBrickColor = Color.decode("#CE5374");
    Color essBrickColor = Color.decode("#BC96E6");
    Color longBrickColor = Color.decode("#E9724C");
    Color zeeBrickColor = Color.decode("#EA3788");

    Color boarderColor = Color.decode("#00AF54");

    Color[] colors = {Color.BLACK, squareBrickColor, jayBrickColor, essBrickColor,
            stackBrickColor, zeeBrickColor, elBrickColor,
            longBrickColor};


    public TetrisDisplay(TetrisGame gam)
    {
        game = gam;
        processMove();
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        game.initBoard(start_x,start_y,cell_size,g);

        g.setColor(Color.black);
        for(int row = 0; row < game.getFallingBrick().position.length; row++)
        {
            int brickStartX = (game.getFallingBrick().position[row][0]*cell_size)+start_x;
            int brickStartY = (game.getFallingBrick().position[row][1]*cell_size)+start_y;

            g.setColor(game.getFallingBrick().getColor());
            g.fillRect(brickStartX , brickStartY, cell_size, cell_size);
            g.setColor(Color.BLACK);
            g.drawRect(brickStartX, brickStartY, cell_size, cell_size);
        }

        g.setColor(boarderColor);
        g.drawLine(start_x, start_y, (start_x+(game.fetchCols()*cell_size)), start_y);

        g.fillRect(start_x - cell_size, start_y, cell_size, game.fetchRows()*cell_size);
        g.fillRect(start_x + game.fetchCols() * cell_size, start_y, cell_size, game.fetchRows()*cell_size);
        g.fillRect(start_x - cell_size, start_y + game.fetchRows()*cell_size, (game.fetchCols()+2)*(cell_size), cell_size);

        //painting the brick into the background
        for(int row = 0; row < game.fetchRows(); row++)
        {
            for(int col = 0; col < game.fetchCols(); col++)
            {
                int individualCellColor = game.brickColor(row, col);
                if(individualCellColor > 0)
                {
                    g.setColor(colors[individualCellColor]);
                    g.fillRect(start_x + col*cell_size, start_y + row*cell_size, cell_size, cell_size);
                    g.setColor(colors[0]);
                    g.drawRect(start_x + col*cell_size, start_y + row*cell_size, cell_size, cell_size);
                }
            }
        }

        gameScoreBoard(g);

        if (game.gameOverDetection())
        {
            gameOverDisplay(g);
        }
    }

    public void processMove()
    {
        KeyEvent ke;

        timer_speed = 250;
        timer = new Timer(timer_speed, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                game.getFallingBrick().moveDown();
                repaint();

                if (!game.validateMove())
                {
                    boolean rowTest = true;
                    game.getFallingBrick().moveUP();
                    game.transferColor();
                    repaint();
                    if (game.gameOverDetection())
                    {
                        pauseTheGame();
                    }
                    else
                    {
                        while (rowTest == true)
                        {
                            rowTest = game.fullRowDetection();
                        }
                        game.spawnBrick();
                    }
                }

                repaint();
            }

        });
        timer.start();

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke)
            {
                int getKey = translateKey(ke);
                game.makeMove(getKey);
                if (game.state == 1)
                {
                    timer.stop();
                }
                else
                {
                    timer.start();
                }
            }
        });

        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(true);
    }

    public int translateKey(KeyEvent ke)
    {
        int move = ke.getKeyCode();
        if (move == KeyEvent.VK_LEFT)
        {
            return 1;
        }
        else if (move == KeyEvent.VK_RIGHT)
        {
            return 2;
        }
        else if (move == KeyEvent.VK_SPACE)
        {
            return 3;
        }
        else if (move == KeyEvent.VK_N)
        {
           game.newGame();
        }
        else if (move == KeyEvent.VK_UP)
        {
            game.rotate();
        }
        return 0;
    }

    public void pauseTheGame()
    {
        timer.stop();
    }

    //creating the scoreboard
    public void gameScoreBoard(Graphics g)
    {
        int x_Score = 1;
        int y_Score = 1;
        int scoreWidth = 140;
        int scoreHeight = 30;
        int scoreFont_y = 22;
        Font scoreFontSize = new Font("Arial", 1, 20);

        Graphics2D g2 = (Graphics2D)g;
        int borderThickness = 2;
        g2.setColor(Color.decode("#283d3b"));
        g2.setFont(scoreFontSize);
        Stroke borderStroke = g2.getStroke();
        g2.setStroke(new BasicStroke(borderThickness));

        g2.drawRect(x_Score,y_Score,scoreWidth,scoreHeight);
        g2.fillRect(x_Score,y_Score,scoreWidth,scoreHeight);
        g2.setStroke(borderStroke);

        g2.setColor(Color.decode("#ec4e20"));
        String finalScore = " Score: " + game.gameScore;
        g2.drawString(finalScore, x_Score, scoreFont_y);

    }

    public void gameOverDisplay(Graphics g)
    {
        int gameOverX = 60;
        int gameOverY = 120;
        int gameOverWidth = 350;
        int gameOverHeight = 200;
        int gameOverFontX = 70;
        int gameOverFontY = 235;
        int newGameFontX = 120;
        int newGameFontY = 280;
        Font gameOverfont = new Font("Arial", 1, 50);

        Graphics2D g2 = (Graphics2D) g;
        int borderThickness = 2;
        g2.setColor(Color.decode("#403F4B"));
        g2.setFont(gameOverfont);
        Stroke borderStroke = g2.getStroke();
        g2.setStroke(new BasicStroke(borderThickness));

        g2.drawRect(gameOverX,gameOverY,gameOverWidth,gameOverHeight);
        g2.fillRect(gameOverX,gameOverY,gameOverWidth,gameOverHeight);
        g2.setStroke(borderStroke);

        g2.setColor(Color.decode("#009b72"));
        g2.drawString(" Game Over !!",gameOverFontX,gameOverFontY);

        Graphics2D g3 = (Graphics2D)g;
        Font newGameFont = new Font("Arial", 1, 20);
        g3.setFont(newGameFont);
        g3.setColor(Color.decode("#009b72"));
        g3.drawString("Press N to start new game",newGameFontX,newGameFontY);
    }
}
