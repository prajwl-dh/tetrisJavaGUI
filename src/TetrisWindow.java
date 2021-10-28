/*
 * This is the class that houses the Game display and instantiates
 * the actual game
 * It provides admin capability such as game set up
 * The critical methods are the constructor.
 */

/*
 * Game logic with data structure and simple methods
 * @author prajwaldhungana
 * @version 3.000
 * May 5, 2021
 */

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.Scanner;

public class TetrisWindow extends JFrame
{
    private int win_width = 450;
    private int win_height = 500;
    private int soundState = 1;

    private TetrisDisplay display;
    private TetrisGame game;

    public TetrisWindow()
    {
        this.setTitle("Tetris Game \t\t@PrajwalDhungana");
        this.setSize(win_width,win_height);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                game.highScoreToFile(game.gameScore);
                e.getWindow().dispose();
            }
        });
        this.setLocationRelativeTo(null);

        game = new TetrisGame();
        display = new TetrisDisplay(game);
        this.add(display);

        gameMenuBar();

        this.setVisible(true);
    }

    public void gameMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        JMenu Game = new JMenu("Game");
        menuBar.add(Game);

        JMenuItem newGame = new JMenuItem("New Game");
        Game.add(newGame);
        Game.addSeparator();

        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                game.newGame();
            }
        });

        JMenuItem highScore = new JMenuItem("High Score");
        Game.add(highScore);

        //to read and sort high scores from file
        highScore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                game.highScoreFromFile();
            }
        });

        JMenuItem clearHighScore = new JMenuItem("Clear High Score");
        Game.add(clearHighScore);
        Game.addSeparator();
        //clears all high score from file
        clearHighScore.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                game.clearHighScore();
            }
        });

        JMenuItem saveGame = new JMenuItem("Save Game");
        Game.add(saveGame);
        saveGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                game.saveGameToFile();
                JOptionPane.showMessageDialog(null,"Game saved to saveGame.csv file", "Game Saved", 1);
            }
        });

        JMenuItem loadGame = new JMenuItem("Load Game");
        Game.add(loadGame);
        loadGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                game.loadGameFromFile();
                JOptionPane.showMessageDialog(null,"Game loaded from saveGame.csv file", "Game Loaded", 1);
            }
        });

        JMenu music = new JMenu("Music");
        menuBar.add(music);

        JMenuItem musicOnOffToggle = new JMenuItem("On/Off");
        music.add(musicOnOffToggle);
        music.addSeparator();
        backGroundMusic(musicOnOffToggle);

        JMenu colorChange = new JMenu("Color");
        menuBar.add(colorChange);

        JMenuItem defaultColor = new JMenuItem("Default Color");
        colorChange.add(defaultColor);
        colorChange.addSeparator();
        defaultColor.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                game.elBrickColor = Color.decode("#56E39F");
                game.stackBrickColor = Color.decode("#FADF63");
                game.jayBrickColor = Color.decode("#53B3CB");
                game.squareBrickColor = Color.decode("#CE5374");
                game.essBrickColor = Color.decode("#BC96E6");
                game.longBrickColor = Color.decode("#E9724C");
                game.zeeBrickColor = Color.decode("#EA3788");

                display.boarderColor = Color.decode("#00AF54");

                display.colors = new Color[]{Color.BLACK, Color.decode("#CE5374"), Color.decode("#53B3CB"), Color.decode("#BC96E6"),
                        Color.decode("#FADF63"), Color.decode("#EA3788"), Color.decode("#56E39F"),
                        Color.decode("#E9724C")};
            }
        });

        JMenuItem colorOne = new JMenuItem("Color Combo 1");
        colorChange.add(colorOne);
        colorOne.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                game.elBrickColor = Color.decode("#EB5E55");
                game.stackBrickColor = Color.decode("#07BEB8");
                game.jayBrickColor = Color.decode("#45462A");
                game.squareBrickColor = Color.decode("#386C0B");
                game.essBrickColor = Color.decode("#446DF6");
                game.longBrickColor = Color.decode("#D81E5B");
                game.zeeBrickColor = Color.decode("#EFD9CE");

                display.boarderColor = Color.decode("#FC2F00");

                display.colors = new Color[]{Color.BLACK, Color.decode("#386C0B"), Color.decode("#45462A"), Color.decode("#446DF6"),
                        Color.decode("#07BEB8"), Color.decode("#EFD9CE"), Color.decode("#EB5E55"),
                        Color.decode("#D81E5B")};
            }
        });

        JMenuItem colorTwo = new JMenuItem("Color Combo 2");
        colorChange.add(colorTwo);
        colorTwo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                game.elBrickColor = Color.decode("#2F2963");
                game.stackBrickColor = Color.decode("#FF9F1C");
                game.jayBrickColor = Color.decode("#A22C29");
                game.squareBrickColor = Color.decode("553555");
                game.essBrickColor = Color.decode("#CCFF66");
                game.longBrickColor = Color.decode("#CC4BC2");
                game.zeeBrickColor = Color.decode("#333745");

                display.boarderColor = Color.decode("#FF70A6");

                display.colors = new Color[]{Color.BLACK, Color.decode("553555"), Color.decode("#A22C29"), Color.decode("#CCFF66"),
                        Color.decode("#FF9F1C"), Color.decode("#333745"), Color.decode("#2F2963"),
                        Color.decode("#CC4BC2")};
            }
        });

    }

    public void backGroundMusic(JMenuItem musicOnOffToggle)
    {
        File musicTetris = new File("tetrisSound.wav");
        try
        {
            AudioInputStream musicInput = AudioSystem.getAudioInputStream(musicTetris);
            Clip audioClip = AudioSystem.getClip();
            audioClip.open(musicInput);
            audioClip.start();
            audioClip.loop(Clip.LOOP_CONTINUOUSLY);

            musicOnOffToggle.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    if (soundState == 1)
                    {
                        audioClip.stop();
                        soundState += 1;
                    }
                    else if (soundState == 2)
                    {
                        audioClip.start();
                        audioClip.loop(Clip.LOOP_CONTINUOUSLY);
                        soundState -= 1;
                    }
                }
            });
        }
        catch (IOException | UnsupportedAudioFileException | LineUnavailableException ioe)
        {
            JOptionPane.showMessageDialog(null,"Failed to open the music file!", "IO Error", 2);
        }
    }

    public static void main(String[] args)
    {
        new TetrisWindow();
    }
}
