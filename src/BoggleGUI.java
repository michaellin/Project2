package src;
/**
 * Copyright (C) 2002 Michael Green <mtgreen@cs.ucsd.edu>
 * 
 * Copyright (C) 2002 Paul Kube <kube@cs.ucsd.edu>
 * 
 * Copyright (C) 2005 Owen Astrachan <ola@cs.duke.edu>
 * 
 * Copyright (C) 2011 Hoa Long Tam <hoalong.tam@berkeley.edu> and Armin Samii
 * <samii@berkeley.edu>
 * 
 * This file is part of CS Boggle.
 * 
 * CS Boggle is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * CS Boggle is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * CS boggle. If not, see <http://www.gnu.org/licenses/>.
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ProgressMonitor;
import javax.swing.ProgressMonitorInputStream;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

/**
 * A GUI for the game of Boggle.
 * 
 * BoggleGUI defines and implements methods to do these things:
 * 
 * 1. Draw the boggle board. 2. Draw and update player word lists. 3. Update the
 * scoreboard.
 * 
 * These depend only on BoggleModel and BogglePlayer, see those classes for
 * methods this GUI/View rely on.
 * 
 * @author Michael Green
 * @author Paul Kube
 * @author Owen Astrachan
 * 
 */
public class BoggleGUI extends JFrame {

  private final LexiconInterface myLexicon;
  private AutoPlayerBoardFirst computerPlayer;
  private Player humanPlayer;
  private final WordOnBoardFinder myFinder;

  private BoggleBoard myBoard;
  private BoggleBoardPanel myBoardPanel4;
  private BoggleBoardPanel myBoardPanel5;
  private BoggleBoardPanel myBoardPanel;
  private PlayerView humanArea, computerArea;
  private WordEntryField wordEntryField;
  private int myBoardSize;

  public BoggleGUI(LexiconInterface lex, WordOnBoardFinder finder,
                   InputStream stream, AutoPlayerBoardFirst compPlayer) {
    super("Welcome to Boggle!");

    myLexicon = lex;
    myBoardSize = 5;
    myFinder = finder;

    initLexicon(stream);
    initPanels();
    initPlayers(compPlayer);

    setUpMenuBar();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    pack();
    setVisible(true);
    newGame();
  }

  /**
   * Get ready for a new game, given a letter list specification of the Boggle
   * board.
   */
  public void newGame() {
    myBoard = BoggleBoardFactory.getBoard(myBoardSize);

    // set up views for new game
    if (myBoardSize == 4) {
      if (myBoardPanel != myBoardPanel4) {
        myBoardPanel = myBoardPanel4;
        getContentPane().remove(myBoardPanel5);
        getContentPane().add(myBoardPanel, BorderLayout.CENTER);
      }
    } else {
      if (myBoardPanel != myBoardPanel5) {
        myBoardPanel = myBoardPanel5;
        getContentPane().remove(myBoardPanel4);
        getContentPane().add(myBoardPanel, BorderLayout.CENTER);
      }
    }

    myBoardPanel.newGame();
    humanArea.setReady();
    computerArea.setReady();
    myBoardPanel.unHighlightAllDice();
    wordEntryField.setReady();
    ((JPanel)getContentPane()).revalidate();
    repaint();
  }

  private void initPlayers(AutoPlayerBoardFirst compPlayer) {
    computerPlayer = compPlayer;
    computerPlayer.setView(computerArea);
    humanPlayer = new HumanPlayer(myLexicon, "Human");
    humanPlayer.setView(humanArea);
  }

  /**
   * Let the computer player take its turn.
   */
  public void computerPlay() {
    computerArea.setName("Thinking!");
    computerArea.paintImmediately(computerArea.getVisibleRect());
    int minLength = myBoardSize == 4 ? 3 : 4;
    computerPlayer.findAllValidWords(myBoard, myLexicon, minLength);

    computerArea.setName("Computer");
    for (String s : computerPlayer) {
      computerArea.showWord(s, myFinder.cellsForWord(myBoard, s),
                            computerPlayer);
    }
    myBoardPanel.unHighlightAllDice(); // leave board unhighlighted when
                                       // done
  }

  /**
   * Read word list from file with name WORDLISTFILENAME, and pass a Set
   * containing those words to the computer player to intialize its lexicon.
   */
  private void initLexicon(InputStream stream) {
    ProgressMonitorInputStream pmis;
    ProgressMonitor progress = null;
    pmis = new ProgressMonitorInputStream(this, "reading words", stream);
    progress = pmis.getProgressMonitor();
    progress.setMillisToDecideToPopup(10);
    Scanner s = new Scanner(pmis);
    myLexicon.load(s);
    try {
      pmis.close();
    } catch (IOException e) {
      JOptionPane.showMessageDialog(null, "Error Closing Stream", "Error",
                                    JOptionPane.ERROR_MESSAGE);
    }
  }

  private void initPanels() {
    Container contentPane = getContentPane();
    humanArea = new PlayerView("Me");
    computerArea = new PlayerView("Computer");
    myBoardPanel4 = new BoggleBoardPanel(4, 4);
    myBoardPanel5 = new BoggleBoardPanel(5, 5);
    myBoardPanel = myBoardPanel5;
    wordEntryField = new WordEntryField();
    contentPane.add(wordEntryField, BorderLayout.SOUTH);
    contentPane.add(humanArea, BorderLayout.WEST);
    contentPane.add(myBoardPanel, BorderLayout.CENTER);
    contentPane.add(computerArea, BorderLayout.EAST);
    contentPane.add(makeProgressBar(), BorderLayout.NORTH);
  }

  private JPanel makeProgressBar() {
    JPanel panel = new JPanel();

    return panel;
  }

  public void gameOver() {
    repaint();
    wordEntryField.setUnready();
    computerPlay();
  }

  public void showError(String message) {
    JOptionPane.showMessageDialog(this, message, "Boggle Error",
                                  JOptionPane.ERROR_MESSAGE);
  }

  private void setUpMenuBar() {
    // Set Up Menu Bar
    JMenuBar menu = new JMenuBar();

    // Game Menu
    JMenu gameMenu = new JMenu("Game");
    menu.add(gameMenu);

    JMenuItem newRandom = new JMenuItem("New Game");
    gameMenu.add(newRandom);
    newRandom.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
                                                    Toolkit.getDefaultToolkit()
                                                           .getMenuShortcutKeyMask()));
    newRandom.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        newGame();
      }
    });

    gameMenu.addSeparator();

    ButtonGroup bg = new ButtonGroup();
    JRadioButtonMenuItem size4 = new JRadioButtonMenuItem("4x4 board");
    size4.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        myBoardSize = 4;
      }
    });
    size4.setSelected(false);
    bg.add(size4);
    gameMenu.add(size4);
    JRadioButtonMenuItem size5 = new JRadioButtonMenuItem("5x5 board");
    size5.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        myBoardSize = 5;
      }
    });
    size4.setSelected(true);
    bg.add(size5);
    gameMenu.add(size5);

    gameMenu.addSeparator();
    JMenuItem quitGame = new JMenuItem("Quit");
    gameMenu.add(quitGame);
    quitGame.setMnemonic('Q');
    quitGame.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        System.exit(0);
      }
    });

    // Help menu
    JMenu helpMenu = new JMenu("Help");
    menu.add(helpMenu);
    helpMenu.setMnemonic(KeyEvent.VK_H);

    JMenuItem aboutGame = new JMenuItem("About...");
    helpMenu.add(aboutGame);
    aboutGame.setMnemonic(KeyEvent.VK_A);
    aboutGame.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(BoggleGUI.this,
                                      "Compsci Boggle, brought to you\n"
                                      + "by educators, students,\n"
                                      + "the Great Courtney Wang,\n"
                                      + "and, of course, you.",
                                      "About Game", JOptionPane.PLAIN_MESSAGE);
      }
    });
    setJMenuBar(menu);
  }

  /**
   * A class defining the visual appearance of a Boggle board, and defining some
   * related methods.
   */
  private class BoggleBoardPanel extends JPanel {
    public final Color BACKGROUNDCOLOR = new Color(255, 219, 13);

    private final DiePanel theDice[][]; // draw the Dice here
    private final int rows, cols;

    BoggleBoardPanel(int rows, int cols) {
      this.rows = rows;
      this.cols = cols;

      // create a JPanel with rowsXcols GridLayout to hold the DiePanels
      JPanel innerPanel = new JPanel();
      innerPanel.setLayout(new GridLayout(rows, cols, 1, 1));
      innerPanel.setBackground(BACKGROUNDCOLOR);

      // Create Individual DiePanels, and add them
      theDice = new DiePanel[rows][cols];
      for (int row = 0; row < rows; row++) {
        for (int col = 0; col < cols; col++) {
          theDice[row][col] = new DiePanel();

          innerPanel.add(theDice[row][col]);
        }
      }
      innerPanel.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10,
                                                           BACKGROUNDCOLOR));
      this.add(innerPanel);
    }

    /**
     * There's a new game, update display to reflect this new game.
     */
    public void newGame() {

      // Set the DiePanels with given letters
      for (int row = 0; row < rows; row++) {
        for (int col = 0; col < cols; col++) {
          String s = myBoard.getFace(row, col).toUpperCase();
          if (s.length() > 1) {
            s = "Qu";
          }
          theDice[row][col].setFace(s);
        }
      }
    }

    public void highlightDice(java.util.List<BoardCell> locations) {
      if (locations == null) {
        return;
      }

      unHighlightAllDice();
      for (BoardCell cell : locations) {
        highlightDie(cell.row, cell.col);
      }
      this.paintImmediately(getVisibleRect());
    }

    /**
     * Highlight the specified die, given row and column.
     */
    public void highlightDie(int row, int column) {
      theDice[row][column].highlight();
    }

    /**
     * Unhighlight all dice.
     */
    public void unHighlightAllDice() {
      for (int row = 0; row < theDice.length; row++) {
        for (int col = 0; col < theDice[row].length; col++) {
          theDice[row][col].unHighlight();
        }
      }
      this.paintImmediately(getVisibleRect());
    }

    // For displaying one Die on the board
    private class DiePanel extends JPanel {
      private String face;
      private boolean isHighlighted;
      private final JLabel faceLabel;

      private final Color DIECOLOR = new Color(230, 230, 230);
      private final Color FACECOLOR = new Color(3, 51, 217);

      private final Font FACEFONT = new Font("SansSerif", Font.PLAIN, 24);
      private final int DIESIZE = 50;

      public DiePanel() {
        face = new String("");
        faceLabel = new JLabel("", SwingConstants.CENTER);
        setLayout(new BorderLayout());
        add(faceLabel, BorderLayout.CENTER);
        setBackground(BACKGROUNDCOLOR);
        setSize(DIESIZE, DIESIZE);
      }

      @Override
      public Dimension getPreferredSize() {
        return (new Dimension(DIESIZE + 1, DIESIZE + 1));
      }

      @Override
      public Dimension getMinimumSize() {
        return (getPreferredSize());
      }

      public void setFace(String chars) {
        if (chars.length() > 1) {
          face = chars.substring(0, 1) + chars.substring(1).toLowerCase();
        } else {
          face = chars;
        }
      }

      public String getFace() {
        return face;
      }

      /**
       * Draw one die including the letter centered in the middle of the die. If
       * highlight is true, we reverse the background and letter colors to
       * highlight the die.
       */
      @Override
      public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int centeredXOffset, centeredYOffset;
        // Draw the blank die
        g.setColor((isHighlighted) ? FACECOLOR : DIECOLOR);
        g.fillRoundRect(0, 0, DIESIZE, DIESIZE, DIESIZE / 2, DIESIZE / 2);

        // Outline the die with black
        g.setColor(Color.black);
        g.drawRoundRect(0, 0, DIESIZE, DIESIZE, DIESIZE / 2, DIESIZE / 2);
        Graphics faceGraphics = faceLabel.getGraphics();
        faceGraphics.setColor(isHighlighted ? DIECOLOR : FACECOLOR);
        Color myColor = isHighlighted ? DIECOLOR : FACECOLOR;
        faceLabel.setForeground(myColor);
        faceLabel.setFont(FACEFONT);
        faceLabel.setText(face);
      }

      public void unHighlight() {
        isHighlighted = false;
      }

      public void highlight() {
        isHighlighted = true;
      }
    }

  } // class BoggeBoard

  // Maintains name, score, and word list information for one player
  class PlayerView extends JPanel implements PlayerViewInterface {

    private String playerName;

    private final Font ScoreFont = new Font("SansSerif", Font.PLAIN, 18);
    private final Font WordFont = new Font("Geneva", Font.PLAIN, 9);
    private final Font LabelFont = new Font("Helvitica", Font.PLAIN, 9);

    private final JPanel topPanel, wordPanel, namePanel, scorePanel;
    private final ExpandableList myWordList;
    private final JLabel nameText, scoreText;

    public PlayerView(String player) {
      playerName = new String(player);

      // Set-Up Top of Score Area
      namePanel = new JPanel();
      nameText = new JLabel(player);
      nameText.setFont(ScoreFont);
      namePanel.setLayout(new BorderLayout());
      namePanel.add(nameText, BorderLayout.CENTER);

      scorePanel = new JPanel();
      scoreText = new JLabel("  0");
      scoreText.setFont(ScoreFont);
      scorePanel.setLayout(new BorderLayout());
      scorePanel.add(scoreText, BorderLayout.CENTER);

      topPanel = new JPanel();
      BoxLayout layout = new BoxLayout(topPanel, BoxLayout.LINE_AXIS);

      topPanel.setLayout(layout);
      topPanel.add(namePanel);
      topPanel.add(Box.createRigidArea(new Dimension(10, 0)));
      topPanel.add(scorePanel);
      topPanel.add(Box.createRigidArea(new Dimension(10, 0)));

      // Create bordering for top panel
      Border raisedBevel, loweredBevel, compound;

      raisedBevel = BorderFactory.createRaisedBevelBorder();
      loweredBevel = BorderFactory.createLoweredBevelBorder();
      compound = BorderFactory.createCompoundBorder(raisedBevel, loweredBevel);
      topPanel.setBorder(compound);

      // Set-Up area to display word list
      wordPanel = new JPanel();
      Border etched = BorderFactory.createEtchedBorder();
      TitledBorder etchedTitle = BorderFactory.createTitledBorder(etched,
                                                                  "Word List");
      etchedTitle.setTitleJustification(TitledBorder.RIGHT);
      wordPanel.setBorder(etchedTitle);
      myWordList = new ExpandableList();
      myWordList.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
          String word = e.getActionCommand();
          java.util.List<BoardCell> list = myFinder.cellsForWord(myBoard, word);
          myBoardPanel.highlightDice(list);
        }

      });
      wordPanel.add(new JScrollPane(myWordList,
                                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));

      setLayout(new BorderLayout(30, 30));
      add(topPanel, BorderLayout.NORTH);
      add(wordPanel, BorderLayout.CENTER);
    }

    @Override
    public void showError(String word, String error) {
      JOptionPane.showMessageDialog(this, word + ": " + error + "!!!", error,
                                    JOptionPane.ERROR_MESSAGE);
      wordEntryField.clear();
    }

    public void setReady() {
      resetScore(); // zero out score
      myWordList.clear(); // remove words from Panel/list
      paintImmediately(getVisibleRect());
    }

    @Override
    public void setName(String newName) {
      playerName = newName;
      nameText.setText(playerName);
      repaint();
    }

    @Override
    public void showWord(String word,
      java.util.List<BoardCell> letterLocations, Player player) {
      myWordList.add(word);
      scoreText.setText(player.getScore() + "");
      scoreText.paintImmediately(scoreText.getVisibleRect());
      myWordList.paintImmediately(myWordList.getVisibleRect());
      wordEntryField.clear(); // clear the wordEntryField text
    }

    public void resetScore() {
      scoreText.setText(0 + "");
      scoreText.paintImmediately(scoreText.getVisibleRect());
    }

  } // class ScoreArea

  class WordEntryField extends JPanel {
    private final JTextField textField;
    private final JButton myDoneButton;
    private final StringBuilder myString;

    public WordEntryField() {
      // Set up for human player's Text Entry Field
      textField = new JTextField(30);
      myString = new StringBuilder("");
      // Add listener to text entry field
      textField.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          String s = textField.getText().toLowerCase();
          java.util.List<BoardCell> list = myFinder.cellsForWord(myBoard, s);
          myString.delete(0, myString.length());
          for (BoardCell cell : list) {
            myString.append(myBoard.getFace(cell.row, cell.col));
          }

          if (!myString.toString().equals(s)) {
            showError(s + " not on board");
          } else if (humanPlayer.add(textField.getText().toLowerCase())) {
            humanArea.showWord(s, list, humanPlayer);
            myBoardPanel.highlightDice(list);
          }
        }
      });
      myDoneButton = new JButton("DONE");
      myDoneButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          gameOver();
        }
      });
      this.add(new JLabel("Enter word: "));
      this.add(textField);
      this.add(myDoneButton);
      setUnready();
    }

    public void clear() {
      textField.setText("");
    }

    public void setUnready() {
      clear();
      textField.setEditable(false);
      paintImmediately(getVisibleRect());
      // attempt to give up focus to top-level frame
      BoggleGUI.this.requestFocus();
    }

    public void setReady() {
      textField.setEditable(true);
      textField.requestFocus();
    }
  } // class WordEntryField
}
