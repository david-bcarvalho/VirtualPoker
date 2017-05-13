package doubleBonusTenSeven;

import java.awt.EventQueue;

import javax.swing.*;

import javax.imageio.ImageIO;
import java.awt.event.WindowAdapter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;
import player.Player;
import java.awt.Image;

import com.sun.glass.events.WindowEvent;

import doubleBonus.DoubleBonus;

/**
 * The Class GUI.
 */
public class GUI extends DoubleBonusTenSeven {
	
	/** The print. */
	String print = "";
	
	/**cardsclicked. array that saves if a card button was pressed or not
	 * saves 1 if it was and 0 if it wasn't
	 * (if it was at 1 and is pressed again it is set to 0)  */
	int[] cardsclicked = new int[5];
	
	/**credits. Saves the credits that the player inserted as an integer */
	static int credits = 0;
	
	/** input. variable that will save the string written on the beginning prompt */
	String input;
	
	/**game. creates a new DoubleBonus10_7 game*/
	DoubleBonusTenSeven game = new DoubleBonusTenSeven();
	
	/**player. new player*/
	Player player;
	
	/** Value bet. value bet in each  */
	int ValueBet = 5;
	
	int c;
	
	/** card back. icon variable that will place images in the cards*/
	ImageIcon cardBack;
	
	/**frame. main game frame*/
	private JFrame frame;
	
	/**text field. text field that will contain game messages*/
	private JTextField textField;
	
	
	/**statvisibility. variable that defines the statistics frame visibility 
	 * 1 if visible and 0 if not*/
	int statVisibility;
	
	/**stats frame. Frame that will show the player statistics */
	JFrame statsFrame = new JFrame();
	
	
	/**
	 * Launch the application. main method that runs the GUI
	 *
	 * @param args, arguments of the main method
	 * (the GUI has no use for arguments)
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}//End of main

	/**
	 * Create the application.
	 */
	public GUI() {
		while(credits<=0){	
			try{
				input = JOptionPane.showInputDialog(null, "Input amount of credits to be used:");
				if(input==null){//In case the user closes the window, the program exits
					System.exit(0);
				}
				credits = Integer.parseInt(input);
				if(credits <= 0){//Minimum amount of credits
					JOptionPane.showMessageDialog(null, "You must insert credits!");
				}
				if(credits >= 1000000){//Maximum amount of credits
					JOptionPane.showMessageDialog(null, "Please insert an amount inferior to 99999999.");
				}
				player = new Player(credits, DoubleBonus.nWinningHands);
			}catch(NumberFormatException e){
				JOptionPane.showMessageDialog(null, "Credits must be a numeric value!");
				
			}
		}
		initialize();
	}//End of GUI method

	/**
	 * Initialization of the contents of the main frame.
	 */
	private void initialize() {
		//Creating the frame/window of the GUI(the place where all the interface will be located)
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(0, 128, 0));
		frame.getContentPane().setForeground(new Color(255, 255, 224));
		frame.setBounds(100, 100, 644, 356);
		frame.setTitle("Video Poker");
		frame.setLocationRelativeTo ( null );
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);	
		
		
		/**
		 * Setting a statistics window frame settings
		 */
		statsFrame.setResizable(false);
		statsFrame.getContentPane().setBackground(Color.BLACK);
		statsFrame.getContentPane().setForeground(new Color(255, 255, 224));
		statsFrame.setLocationRelativeTo ( null );
		statsFrame.setResizable(false);
		statsFrame.setBounds(100, 100, 275, 425);
		statsFrame.setTitle("Statitics");
		statsFrame.addWindowListener(new WindowAdapter() {
		    @SuppressWarnings("unused")
			public void windowClosing(WindowEvent evt) {
		    	statVisibility = 0;
		    	statsFrame.setVisible(false);
		    	System.out.println(statVisibility);
		  }
		});
		
		
		JButton[] base = new JButton[5];
		
		/**
		 * Label that displays the player's credits
		 */
		JLabel lblCredits = new JLabel("");
		lblCredits.setForeground(new Color(255, 250, 250));
		lblCredits.setFont(new Font("Cambria Math", Font.PLAIN, 17));
		lblCredits.setBounds(21, 39, 89, 23);
		frame.getContentPane().add(lblCredits);
		lblCredits.setText(Integer.toString(player.getCredit()));
	
		/**
		 * Text Field to display player winnings and losses, etc
		 */
		textField = new JTextField();
		textField.setFont(new Font("Malgun Gothic", Font.ITALIC, 16));
		textField.setEnabled(false);
		textField.setEditable(false);
		textField.setForeground(Color.WHITE);
		textField.setBackground(Color.BLACK);
		textField.setBounds(152, 19, 289, 43);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		/**
		 * Layered pane to place buttons on-top of shadows
		 */
		JLayeredPane pane = new JLayeredPane();
		pane.setBounds(0, 163, 638, 164);
		frame.getContentPane().add(pane);
		
		/**
		 * Button array that represent the cards in hand 
		 */
		JButton[] card = new JButton[5];
		/**
		 * Generating hand cards
		 */
		for(c=0; c<5; c++){
			card[c] = new JButton();
			card[c].setBounds(20+(120*c),10, 89, 119);
			pane.add(card[c], 2, 0);
		}
		
		/**
		 * Defining action listeners for the cards in hand
		 */
		card[0].addActionListener(new ActionListener() {	//Card Action
			public void actionPerformed(ActionEvent e) {
				if(cardsclicked[0] == 0 && game.getState()==DEALING){
					cardsclicked[0]=1;
					highlightbtn(card[0],0);
				}else{
					cardsclicked[0]=0;
					resetbtn(card[0],0);
				}
			}
		});
		card[1].addActionListener(new ActionListener() {	//Card Action
			public void actionPerformed(ActionEvent e) {
				if(cardsclicked[1] == 0 && game.getState()==DEALING){
					cardsclicked[1]=1;
					highlightbtn(card[1],1);
				}else{
					cardsclicked[1]=0;
					resetbtn(card[1],1);
				}
			}
		});
		card[2].addActionListener(new ActionListener() {	//Card Action
			public void actionPerformed(ActionEvent e) {
				if(cardsclicked[2] == 0 && game.getState()==DEALING){
					cardsclicked[2]=1;
					highlightbtn(card[2],2);
				}else{
					cardsclicked[2]=0;
					resetbtn(card[2],2);
				}
			}
		});
		card[3].addActionListener(new ActionListener() {	//Card Action
			public void actionPerformed(ActionEvent e) {
				if(cardsclicked[3] == 0 && game.getState()==DEALING){
					cardsclicked[3]=1;
					highlightbtn(card[3],3);
				}else{
					cardsclicked[3]=0;
					resetbtn(card[3],3);
				}
			}
		});
		card[4].addActionListener(new ActionListener() {	//Card Action
			public void actionPerformed(ActionEvent e) {
				if(cardsclicked[4] == 0 && game.getState()==DEALING){
					cardsclicked[4]=1;
					highlightbtn(card[4],4);
				}else{
					cardsclicked[4]=0;
					resetbtn(card[4],4);
				}
			}
		});		
		
		
		/**
		 * Hold button for holding the selected cards and 
		 */
		JButton btnHold = new JButton("Hold");
		btnHold.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] indexes = new int[5];
				int nCardsToHold = 0;
				for(int i = 0; i < 5; i++){
					if(cardsclicked[i] == 1){
						indexes[nCardsToHold] = i+1;
						nCardsToHold++;
					}
				}
				if(game.getState() == DEALING){
					player.hand.hold(indexes, game.getDeck());
					paintHand(card, player);
					int score = game.handScore(player);
					player.setCredit(player.getCredit() + reward(score, player.getLastBet()));
					if(score == 0)
						textField.setText("Player had nothing in hand");
					else
						textField.setText("Player wins with a " + game.scoreToString(score));
					
					player.incStatistics(score);
					player.incHandsPlayed();
					game.setState(BETTING);
					/**If the statistics frame is visible the sats will be updated**/
					if(statsFrame.isVisible()){
						framestatistics(player);
					}
				}else
					JOptionPane.showMessageDialog(null,"You can't hold right now!");
				
				if(player.getCredit() == 0){
					JOptionPane.showMessageDialog(null,"GAME OVER!\nBye!\n");
					System.exit(3);
				}
				
				lblCredits.setText(Integer.toString(player.getCredit()));
				for(int i=0; i<5; i++)	//Resetting the cards held vector
					cardsclicked[i]=0;
				resetallbtn(card);
			}
		});
		btnHold.setFont(new Font("Cambria Math", Font.PLAIN, 17));
		btnHold.setBounds(21, 96, 89, 23);
		frame.getContentPane().add(btnHold);
		
		/**
		 * Bet action button with slider that represents the amount the player will bet.
		 * After the slider is set if the user presses bet he will bet that amount 
		 */
		JSlider Betslider = new JSlider(1,5,5);
		Betslider.setPaintLabels(true);
		Betslider.setMinorTickSpacing(1);
		Betslider.setMajorTickSpacing(1);
		Betslider.setSnapToTicks(true);
		Betslider.setPaintTicks(true);
		Betslider.setBackground(new Color(0, 128, 0));
		Betslider.setBounds(141, 128, 89, 36);
		frame.getContentPane().add(Betslider);
		
		JButton btnBet = new JButton("Bet");
		btnBet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ValueBet = Betslider.getValue();
				if(game.getState() == BETTING||game.getState()==INITIATING)
					if(ValueBet > player.getCredit())
						JOptionPane.showMessageDialog(null, 
								"You can't bet more that the amount of credits you have!");
					else{
						textField.setText("Player bet: "+ ValueBet);
						game.bet(player, ValueBet);
						
					}
				else
					JOptionPane.showMessageDialog(null, "You can only bet before dealing!");
			}
		});
		btnBet.setFont(new Font("Cambria Math", Font.PLAIN, 17));
		btnBet.setBounds(141, 96, 89, 23);
		frame.getContentPane().add(btnBet);
		
		
		/**
		 * Button responsible for generating the best move for the current hand
		 * based on the predetermined strategy
		 */
		JButton btnAdvise = new JButton("Advise");
		btnAdvise.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(game.getState()==DEALING){
					int[] shouldhold = new int[5];
					shouldhold = game.cardsToHold(player);
					adviseHighlight(shouldhold, card);
					cardsclicked=shouldhold;
					for(int i=0;i<5;i++){
						if(shouldhold[i]!=1)
							resetbtn(card[i],i);
					}
				}else
					JOptionPane.showMessageDialog(null, "You can only get advise after dealing!");
			}
		});
		btnAdvise.setFont(new Font("Cambria Math", Font.PLAIN, 17));
		btnAdvise.setBounds(268, 96, 89, 23);
		frame.getContentPane().add(btnAdvise);
		
		/**
		 * Simple label to set the "Credits:" title 
		 */
		JLabel lblPlayerCredits = new JLabel("Credits:");
		lblPlayerCredits.setForeground(new Color(255, 255, 224));
		lblPlayerCredits.setFont(new Font("Cambria Math", Font.BOLD, 17));
		lblPlayerCredits.setBounds(21, 17, 70, 23);
		frame.getContentPane().add(lblPlayerCredits);
		
		
		/**
		 * Button that represents the deck pressing it will deal the cards
		 * (Set tool tip in case it is unclear that the deal button is the deck)
		 */
		JButton deck = new JButton();
		deck.setToolTipText("Press this button to deal the cards");
		try{
			Image back= ImageIO.read((GUI.class.getResource("/cardsPNG/403px-Card_back-Overwatch.png")));
			back = back.getScaledInstance(89, 119, Image.SCALE_SMOOTH);
			cardBack = new ImageIcon(back);;
		}catch(Exception e){
			e.printStackTrace();
		}
		deck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(game.getState()== BETTING){
					if(ValueBet > player.getCredit())
						JOptionPane.showMessageDialog(null, 
								"You can't bet more that the amount of credits you have!");
					else{
						resetallbtn(card);
						game.shuffle();
						game.deal(player);
						lblCredits.setText(Integer.toString(player.getCredit()));
						paintHand(card, player);
						game.setState(DEALING);
						textField.setText("");
					}
					
				}else
					JOptionPane.showMessageDialog(null,
							"You can only deal after you bet or after this turned is resolved!");
			}
		});
		deck.setIcon(cardBack);
		deck.setBounds(517, 19, 89, 119);
		frame.getContentPane().add(deck);
		
		/**
		 * Button that prompts up the statistics board
		 */
		JButton statisticsBtn = new JButton("Statistics");
		statisticsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				framestatistics(player);
			}
		});
		statisticsBtn.setFont(new Font("Cambria Math", Font.PLAIN, 17));
		statisticsBtn.setBounds(383, 96, 105, 23);
		frame.getContentPane().add(statisticsBtn);
		
		
		/**
		 * Generating of buttons to act as shadow for the card ones
		 */
		for(int i=0; i<5; i++){
			base[i] = new JButton();
			base[i].setBackground(Color.DARK_GRAY);
			base[i].setEnabled(false);
			base[i].setBounds(20+(120*i),10, 89, 119);
			pane.add(base[i], 1, 0);
		}
		
		/**
		 * Initiating the cards in hand with the card back
		 */
		paintHand(card, player);
	}
	
	/**
	 * Highlightbtn. Method that elevates a single button to represent that it is being held
	 *
	 * @param card JButton to be highlighted
	 * @param index index of the JButton to be highlighted
	 */
	void highlightbtn(JButton card, int index){			
		card.setBounds(30+(120*index),0, 89, 119);
	}
	
	/**
	 * Advise highlight. Method that elevates some buttons to represent that they are currently being held
	 * 
	 * @param shouldhold array of integers of cards that should be held
	 * @param card array of the buttons that represent the cards in hand
	 */

	void adviseHighlight(int[] shouldhold, JButton[] card){
		for(int i=0; i<5; i++){
			if(shouldhold[i]==1){
				card[i].setBounds(30+(120*i), 0, 89, 119);
			}
		}
	}
	
	/**
	 * Resetbtn. Method that resets a single button to its original position
	 * @param card  JButton  to be reset
	 * @param index the index of the JButton to be reset
	 */
	//
	void resetbtn(JButton card, int index){				
		card.setBounds(20+(120*index), 10, 89, 119);	
	}
	
	/**
	 * Resetallbtn. Method that resets all buttons to they initial position	
	 *
	 * @param card array of all the JButtons that represent the cards in hand 
	 */
	//Function that resets all buttons to its original position
	void resetallbtn(JButton[] card){	
		for(int i=0; i<5; i++){
			card[i].setBounds(20+(120*i), 10, 89, 119);
		}
	}
	
	/**
	 * Paint hand.Method that places the image of the card dealt in the respective buttons
	 *	
	 * @param card  array of buttons, places the cards in the players hand in the respective buttons in the array
	 * @param player of type sent as parameter so it is possible to access the player hand
	 */
	void paintHand(JButton[] card,Player player){
		String image ;
		for(int i = 0; i<5; i++){
			if(game.getState()==INITIATING)
				image = "/cardsPNG/403px-Card_back-Overwatch.png";
			else
				image = "/cardsPNG/"+ player.hand.hand[i].toString() +".png";
				try{
					Image back= ImageIO.read((GUI.class.getResource(image)));
					back = back.getScaledInstance(89, 119, Image.SCALE_SMOOTH);
					cardBack = new ImageIcon(back);
				}catch(Exception e){
					e.printStackTrace();
				}
				card[i].setIcon(cardBack);			
		}
	}
	
	/**
	 * Framestatistics. Method that makes the statistics frame visible
	 *  refreshing it so the players statistics are the current ones
	 *
	 * @param player Player variable so the player statistics can be accessed
	 * */

	void framestatistics(Player player){
			statsFrame.setVisible(true);
			statVisibility = 1;
			/**
			 * Adding a window action so, the statistics frame close button just hides it's visibility
			 */
	        JTextArea statArea = new JTextArea(statsFrame.getHeight(),statsFrame.getWidth());
			statArea.setFont(new Font("Malgun Gothic", Font.PLAIN, 16));
			statArea.setEnabled(false);
			statArea.setEditable(false);
			statArea.setForeground(Color.WHITE);
			statArea.setBackground(Color.BLACK);
			statsFrame.getContentPane().add(statArea);
			
			//Minimizing the length of the percentage of credits that will appear in frame
			//so it does not extend outside the frame
			double percentage = ((double)player.getCredit())/player.getInitialCredit()*100;
			String percentageStr = String.valueOf(percentage);
			int maxLength = 5;
			if(percentageStr.length() < 5)
				maxLength = percentageStr.length();
			
			percentageStr = percentageStr.substring(0, maxLength);
			
			statArea.append("\n   Hand\t\tNb\n");
			statArea.append("   ---------------------------------\n");
			statArea.append("   Jacks or Better"+"\t" + 
											String.valueOf(player.statistics[JACKS_OR_BETTER])+ "\n");
			statArea.append("   Two Pair" +"\t\t" + 
											String.valueOf(player.statistics[TWO_PAIR]+ "\n"));
			statArea.append("   Three of a Kind" +"\t" + 
											String.valueOf(player.statistics[THREE_OF_A_KIND]+ "\n"));
			statArea.append("   Straight" +"\t\t"+  String.valueOf(player.statistics[STRAIGHT])+ "\n");
			statArea.append("   Flush" +"\t\t"+ String.valueOf(player.statistics[FLUSH])+ "\n");
			statArea.append("   Full house" +"\t\t"+ String.valueOf(player.statistics[FULL_HOUSE])+ "\n");
			statArea.append("   Four of a Kind" +"\t"+ String.valueOf(player.statistics[FOUR_ACES] 
						+ player.statistics[FOUR_2_4] + player.statistics[FOUR_5_K])+ "\n");
			statArea.append("   Straight Flush" +"\t"+ 
												String.valueOf(player.statistics[STRAIGHT_FLUSH])+ "\n");
			statArea.append("   Royal Flush" +"\t\t"+ String.valueOf(player.statistics[ROYAL_FLUSH])+ "\n");
			statArea.append("   Other" +"\t\t"+ String.valueOf(player.statistics[OTHER])+ "\n");
			statArea.append("   ---------------------------------\n");
			statArea.append("   Total" +"\t\t"+ String.valueOf(player.handsPlayed)+ "\n");
			statArea.append("   ---------------------------------\n");
			statArea.append("   Credit" +"                 "+ 
										String.valueOf(player.getCredit())+" (" + percentageStr +")");
	}
}