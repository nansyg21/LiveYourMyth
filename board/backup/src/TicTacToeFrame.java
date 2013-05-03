/*
	Title: Tic-Tac-Toe Game
	Created: October 5, 2008
	Last Edited: October 13, 2008
	Author: Blmaster
	Changes:
		See Below...
 */
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

public class TicTacToeFrame implements ActionListener	{
	final String VERSION = "3.0";
	//Setting up ALL the variables
	JFrame window = new JFrame("Tic-Tac-Toe " + VERSION);

	JMenuBar mnuMain = new JMenuBar();
			
	

	JButton btnEmpty[] = new JButton[10];

	JPanel 	pnlNewGame = new JPanel(),
			pnlMenu = new JPanel(),
			pnlMain = new JPanel(),
			pnlTop = new JPanel(),
			pnlBottom = new JPanel(),
			pnlQuitNTryAgain = new JPanel(),
			pnlPlayingField = new JPanel();

	JLabel 	lblTitle = new JLabel("Tic-Tac-Toe"),
			lblTurn = new JLabel(),
			lblStatus = new JLabel("", JLabel.CENTER),
			lblMode = new JLabel("", JLabel.LEFT);
	
	JLabel back_lbl = new JLabel();
	
	JTextArea txtMessage = new JTextArea();

	final int winCombo[][] = new int[][]	{
			{1, 2, 3}, 			{1, 4, 7}, 		{1, 5, 9},
			{4, 5, 6}, 			{2, 5, 8}, 		{3, 5, 7},
			{7, 8, 9}, 			{3, 6, 9}
			/*Horizontal Wins*/	/*Vertical Wins*/ /*Diagonal Wins*/
	};
	final int X = 535, Y = 342,
			mainColorR = 190, mainColorG = 50, mainColorB = 50,
			btnColorR = 70, btnColorG = 70, btnColorB = 70;
	Color clrBtnWonColor = new Color(190, 190, 190);
	int 	turn = 1,
			player1Won = 0, player2Won = 0,
			wonNumber1 = 1, wonNumber2 = 1, wonNumber3 = 1,
			option;
	boolean 	inGame = false,
			CPUGame = false,
			win = false;
	String 	message,
	Player1 = "Player 1", Player2 = "Player 2",
	tempPlayer2 = "Player 2";
	private final JPanel GeneralPnl = new JPanel();


	public TicTacToeFrame()	{	//Setting game properties and layout and sytle...
		//*** MenuBar ***//
				window.setJMenuBar(new JMenuFrame().getMenu()); // Getting the Menu from the JMenuFrame
	
		//Getting Dimensions
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		System.out.println("Width is: "+width +" and height is: "+height);
		
		//Setting window properties:
		window.setSize((int) width,(int) height);
		window.setLocation(0, 0);
		window.setUndecorated(true);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		pnlNewGame.setBackground(new Color(mainColorR - 50, mainColorG - 50, mainColorB- 50));
		
		

		//Adding buttons to NewGame panel
		pnlNewGame.setLayout(new GridLayout(4, 1, 2, 10));
	
	

		//Setting txtMessage Properties
		txtMessage.setBackground(new Color(mainColorR-30, mainColorG-30, mainColorB-30));
		txtMessage.setForeground(Color.white);
		txtMessage.setEditable(false);
		for(int i=1; i<=9; i++)	{
			btnEmpty[i] = new JButton();
			btnEmpty[i].setBackground(new Color(btnColorR, btnColorG, btnColorB));
			btnEmpty[i].addActionListener(this);
			pnlPlayingField.add(btnEmpty[i]);//	Playing Field is Compelte
		}

		GeneralPnl.setBounds(0, 0,(int) width,(int) height); //General Panel is in fullscreen
		window.getContentPane().add(GeneralPnl);
		GeneralPnl.setLayout(null);


		//Setting Menu, Main, Top, Bottom Panel Layout/Backgrounds
		pnlMenu.setLayout(new FlowLayout(FlowLayout.CENTER));
		pnlMenu.setBackground(new Color((mainColorR - 50), (mainColorG - 50), (mainColorB- 50)));

		//Adding everything needed to pnlMenu and pnlMain
		lblMode.setForeground(Color.white);
		pnlMenu.add(lblMode);
		pnlMenu.add(mnuMain);
		pnlTop.setLayout(new FlowLayout(FlowLayout.CENTER));
		pnlBottom.setLayout(new FlowLayout(FlowLayout.CENTER));

		pnlMain.setBounds((int) width/3,(int) (height/4) ,(int) width/3, (int) (2*(height/4)) ); // 1/3 ths othonhs
		GeneralPnl.add(pnlMain);

		pnlMenu.setBounds(pnlMain.getX(), pnlMain.getY()-50, pnlMain.getWidth(), 40 );
		GeneralPnl.add(pnlMenu);
		pnlMain.setBackground(new Color(mainColorR, mainColorG, mainColorB));
		pnlTop.setBackground(new Color(mainColorR, mainColorG, mainColorB));
		pnlBottom.setBackground(new Color(mainColorR, mainColorG, mainColorB));

		//Setting up Panel QuitNTryAgain
		pnlQuitNTryAgain.setLayout(new GridLayout(1, 2, 2, 2));

		//Adding Action Listener to all the Buttons and Menu Items


		//Setting up the playing field
		pnlPlayingField.setLayout(new GridLayout(3, 3, 2, 2));
		pnlPlayingField.setBackground(Color.black);
		pnlMain.add(lblTitle);
		pnlMain.setLayout(new BorderLayout());
		pnlTop.setLayout(new BorderLayout());
		pnlBottom.setLayout(new BorderLayout());
		 
		// IMAGE BACKGROUND
		back_lbl.setBounds(0, 0, (int) width, (int) height);
		ImageIcon img_icn = new ImageIcon("UIcons\\tic_tac_toe_background.jpg");
		Image img = img_icn.getImage().getScaledInstance(back_lbl.getWidth(), back_lbl.getHeight(), 0);
				
		back_lbl.setIcon(new ImageIcon(img) );
		GeneralPnl.add(back_lbl);
	
		window.repaint();//Refresh the image
		beginToPlay();
		/**
		//player vs computer epiloges ktl
		clearPanelSouth();
		setDefaultLayout();
		pnlTop.add(pnlNewGame);
		pnlMain.add(pnlTop);
		 **/
	}
	public static void main(String[] args) {
		new TicTacToeFrame();
		
	}

	/*
		-------------------------
		Start of all METHODS.	|
		-------------------------
	 */
	public void beginToPlay(){  //starts a new game from the beginning
		//-------------------//
		//*****NEW GAME*****//
		//--------------------//
		// 1 v CPU Game by default epilogh
		Player2 = "Computer";
		player1Won = 0;
		player2Won = 0;
		lblMode.setText("1 v CPU");
		CPUGame = true;
		newGame();
		
		pnlMain.setVisible(false);
		pnlMain.setVisible(true);
	}
	
	
	public void showGame()	{	//	Shows the Playing Field
		//	*IMPORTANT*- Does not start out brand new (meaning just shows what it had before)
		clearPanelSouth();
		pnlTop.add(pnlPlayingField);
		pnlBottom.add(lblTurn, BorderLayout.WEST);
		pnlBottom.add(lblStatus, BorderLayout.CENTER);
		pnlBottom.add(pnlQuitNTryAgain, BorderLayout.EAST);
		pnlMain.add(pnlTop, BorderLayout.CENTER);
		pnlMain.add(pnlBottom, BorderLayout.SOUTH);
		pnlPlayingField.requestFocus();
		inGame = true;
		checkTurn();
		checkWinStatus();
	}
	//-----------------------------------------------------------------------------------------------------------------------------------	
	public void newGame()	{	//	Sets all the game required variables to default
		//	and then shows the playing field.
		//	(Basically: Starts a new 1v1 Game)
		btnEmpty[wonNumber1].setBackground(new Color(btnColorR, btnColorG, btnColorB));
		btnEmpty[wonNumber2].setBackground(new Color(btnColorR, btnColorG, btnColorB));
		btnEmpty[wonNumber3].setBackground(new Color(btnColorR, btnColorG, btnColorB));
		for(int i=1; i<10; i++)	{
			btnEmpty[i].setText("");
			btnEmpty[i].setEnabled(true);
		}
		turn = 1;
		win = false;
		showGame();
	}
	//-----------------------------------------------------------------------------------------------------------------------------------	
	public void quit()	{
		inGame = false;
		lblMode.setText("");
		clearPanelSouth();
		setDefaultLayout();
		pnlTop.add(pnlNewGame);
		pnlMain.add(pnlTop);
	}
	//-----------------------------------------------------------------------------------------------------------------------------------	
	public void checkWin()	{	//	checks if there are 3 symbols in a row vertically, diagonally, or horizontally.
		//	then shows a message and disables buttons. If the game is over then it asks
		//	if you want to play again.
		for(int i=0; i<8; i++)	{
			if(
					!btnEmpty[winCombo[i][0]].getText().equals("") &&
					btnEmpty[winCombo[i][0]].getText().equals(btnEmpty[winCombo[i][1]].getText()) &&
					//								if {1 == 2 && 2 == 3}
					btnEmpty[winCombo[i][1]].getText().equals(btnEmpty[winCombo[i][2]].getText()))	{
				/*
					The way this checks the if someone won is:
					First: it checks if the btnEmpty[x] is not equal to an empty string-	x being the array number 
						inside the multi-dementional array winCombo[checks inside each of the 7 sets][the first number]
					Secong: it checks if btnEmpty[x] is equal to btnEmpty[y]- x being winCombo[each set][the first number]
						y being winCombo[each set the same as x][the second number] (So basically checks if the first and
						second number in each set is equal to each other)
					Third: it checks if btnEmtpy[y] is eual to btnEmpty[z]- y being the same y as last time and z being
						winCombo[each set as y][the third number]
					Conclusion:	So basically it checks if it is equal to the btnEmpty is equal to each set of numbers
				 */
				win = true;
				wonNumber1 = winCombo[i][0];
				wonNumber2 = winCombo[i][1];
				wonNumber3 = winCombo[i][2];
				btnEmpty[wonNumber1].setBackground(clrBtnWonColor);
				btnEmpty[wonNumber2].setBackground(clrBtnWonColor);
				btnEmpty[wonNumber3].setBackground(clrBtnWonColor);
				break;
			}
		}
		if(win || (!win && turn>9))	{
			if(win)	{
				if(btnEmpty[wonNumber1].getText().equals("X"))	{
					message = "������������ �������!";           //***********PLAYER WINS  (X)**********//
					player1Won++;
				}
				else	{
					message ="������...!";                       //***********PLAYER LOSES (O)**********//
					player2Won++;
				}
			}	else if(!win && turn>9)
				message = "��������!";                            //***********  DRAW   *********//
			showMessage(message);
			for(int i=1; i<=9; i++)	{
				btnEmpty[i].setEnabled(false);
			}
			checkWinStatus();
		} else
			checkTurn();
	}
	//-----------------------------------------------------------------------------------------------------------------------------------	
	public void AI()	{
		int computerButton;
		if(turn <= 9)	{
			turn++;
			computerButton = TicTacToeCPU.doMove(
					btnEmpty[1], btnEmpty[2], btnEmpty[3],
					btnEmpty[4], btnEmpty[5], btnEmpty[6],
					btnEmpty[7], btnEmpty[8], btnEmpty[9]);
			if(computerButton == 0)
				Random();
			else {
				btnEmpty[computerButton].setText("O");
				btnEmpty[computerButton].setEnabled(false);
			}
			checkWin();
		}
	}
	//-----------------------------------------------------------------------------------------------------------------------------------	
	public void Random()	{
		int random;
		if(turn <= 9)	{
			random = 0;
			while(random == 0)	{
				random = (int)(Math.random() * 10);
			}
			if(TicTacToeCPU.doRandomMove(btnEmpty[random]))	{
				btnEmpty[random].setText("O");
				btnEmpty[random].setEnabled(false);
			} else {
				Random();
			}
		}
	}
	//-----------------------------------------------------------------------------------------------------------------------------------	
	public void checkTurn()	{
		String whoTurn;
		if(!(turn % 2 == 0))	{
			whoTurn = Player1 + " [X]";
		}	else	{
			whoTurn = Player2 + " [O]";
		}
		lblTurn.setText("Turn: " + whoTurn);
	}
	
	//-----------------------------------------------------------------------------------------------------------------------------------	
	public void setDefaultLayout()	{
		pnlMain.setLayout(new GridLayout(2, 1, 2, 5));
		pnlTop.setLayout(new FlowLayout(FlowLayout.CENTER));
		pnlBottom.setLayout(new FlowLayout(FlowLayout.CENTER));
	}
	//-----------------------------------------------------------------------------------------------------------------------------------	
	public void checkWinStatus()	{
		lblStatus.setText(Player1 + ": " + player1Won + " | " + Player2 + ": " + player2Won);	
	}
	//-----------------------------------------------------------------------------------------------------------------------------------	
	public int askMessage(String msg, String tle, int op)	{
		return JOptionPane.showConfirmDialog(null, msg, tle, op);
	}
	//-----------------------------------------------------------------------------------------------------------------------------------
	public String getInput(String msg, String setText)	{
		return JOptionPane.showInputDialog(null, msg, setText);
	}
	//-----------------------------------------------------------------------------------------------------------------------------------
	public void showMessage(String msg)	{
		JOptionPane.showMessageDialog(null, msg);
	}
	//-----------------------------------------------------------------------------------------------------------------------------------	
	public void clearPanelSouth()	{	//Removes all the possible panels 
		//that pnlMain, pnlTop, pnlBottom
		//could have.
		pnlMain.remove(lblTitle);
		pnlMain.remove(pnlTop);
		pnlMain.remove(pnlBottom);
		pnlTop.remove(pnlNewGame);
		pnlTop.remove(txtMessage);
		pnlTop.remove(pnlPlayingField);
		pnlBottom.remove(lblTurn);
		pnlBottom.remove(pnlQuitNTryAgain);
	}
	/*
		-------------------------------------
		End of all non-Abstract METHODS.		|
		-------------------------------------
	 */

	//-------------------ACTION PERFORMED METHOD (Button Click --> Action?)-------------------------//	
	public void actionPerformed(ActionEvent click)	{
		Object source = click.getSource();
		for(int i=1; i<=9; i++)	{
			if(source == btnEmpty[i] && turn <	10)	{
				if(!(turn % 2 == 0))
					btnEmpty[i].setText("X");
				else
					btnEmpty[i].setText("O");
				btnEmpty[i].setEnabled(false);
				pnlPlayingField.requestFocus();
				turn++;
				checkWin();
				if(CPUGame && win == false)
					AI();
			}
		}

		
	}
}
/* Future Plans:
 */
/*	Changes:
	3.0- changes below: Added AI [Stable]
		2.9-	Added Label which shows waht game mode user is in.
		2.8-	Quit goes back to Gmae Options rather than Main Screen.
		2.79-	fixed bug: win count will not show 0 when New Game is started.
		2.75-	fixed bug: Players win count didnt change to 0 when New Game is started.
		2.7-	Player 2 name will change back from Computer in single player.
		2.6-	AI name is constant to Computer.
		2.55- fixed bug: Ad. AI clicks middle spot if available.
		2.5-	improved basic AI to Advanced AI.
		2.4-	fixed bugs below...
			fixed bug: AI crashes sometime.
			fixed bug: AI does checkWin twice.
			fixed bug: AI does not count as turn.
			fixed bug: AI does not check if won.
		2.3-	added basic AI (Artificial Intelligence).
		2.2-	Player vs CPU does random move.
		2.1-	removed unnessary code. more effienct.
	2.0- changes below: Changed Layout [Stable]
		1.95- fixed bug: TryAgain Button takes over Status Label
		1.9-	added Label that shows Player 1 and Player 2 wins
		1.8-	removed Try Again pop-up. Added Try Again Button
		1.7-	removed Back Button. Added Continue Button
		1.6-	fixed bugs below...
			fixed bug: Same name for both players.
			fixed bug: Names updated in game if user changes.
			fixed bug: Names are null if user presses cancel.
		1.5-	added function to set Player Names.
		1.4-	program more efficient/faster.
		1.35- fixed bugs, added turn status to status bar.
		1.3-	added Status bar with quit button during gameplay.
		1.2-	changed theme.
		1.15- fixed bug: one win combo not working.
		1.1-	added play again function.
	1.0- 	changes below: (problems gone!) [Stable]
		0.9-	added back button, added comments.
		0.8-	added dynamic win message.
		0.7-	added game function- game playable.
		0.6-	changed menu layout.
		0.5-	basic functions with menu and nice GUI.
 */


/*	LAYOUT OF THE GAME:

	THE WINDOW: (pnlMenu/pnlMain)
	pnlMenu: (THE MENU)
	pnlMain:	(pnlTop/pnlBottom)
	pnlTop:	(pnlPlayingField/INSTRUCTIONS/ABOUT/NEW GAME)
	pnlBottom:(STATUS BAR/BACK BUTTON)

	/////////////////////////////////////////////////--------------|
	/						pnlMenu								/					|
	/																/					|
	/////////////////////////////////////////////////-----|			|
	/																/		|			|
	/																/		|			|
	/																/		|			|
	/						pnlTop								/		|			|
	/																/		|			|- MAIN WINDOW
	/																/		|-			|
	/																/		|pnlMain	|
	/																/		|			|
	/																/		|			|
	/																/		|			|
	/																/		|			|
	/																/		|			|		
	/////////////////////////////////////////////////		|			|
	/						pnlBottom							/		|			|
	/////////////////////////////////////////////////-----|--------|

 */