import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.*;
/**SpaceGameView is the view of SpaceShip game. It refreshes itself every 60 milliseconds and draws the current coordinates 
 * of the chicken, ship, bullets, and eggs. If the game is over, displays a table with the high score. This game is Called SpaceShip vs Chicken. To control the ship, 
 *use left and right arrows. To shoot, press space. The chickens will randomly lay eggs and your job is to make sure to shoot all of the eggs. 
 *You start with 5 lives, and your live is displayed on the top right corner. Your score is how many eggs you have shot down. If the eggs
 * reach the ground (bottom) without being shot, then you lose one life. If your life is 0, then the game is over. Game gets harder as it progresses. 
 * 
 * @author endriaskinfe
 *
 */

public class SpaceGameView extends JPanel implements ActionListener{
	//width of board
	public static final int WIDTH= 700;
	//length of board
	public static final int LENGTH= 700;
	//speed of bullet 
	private static final int BULLET_SPEED = -10;
	//speed of egg
	private static final int EGG_SPEED =  2;
	private static final int FRAME_RATE = 60;
	private static final int SIZE_OF_SHIP=60;
	private static final int BULLET_SIZE=10;
	private static final int CHICKEN_LENGTH= 100;
	private static final int BROWN_RED_VALUE=153;
	private static final int BROWN_GREEN_VALUE=51;
	private static final int BROWN_BLUE_VALUE=0;
	private static final int GREEN_VARIATION_RED = 155;
	private static final int HIGH_SCORE_BOARD_SIZE = 10;
	
	private Controller control;
	private int score;
	private Timer timer;
	private JFrame frame;
	private JFrame highScore;
	
	
	
	/**Creates the window where the game will be displayed and starts the timer that 
	 * refreshes the board every 60 miliseconds. Also sets up level one for Space Ship
	 * game which has 7 chickens that lay eggs at different timees. 
	 * 
	 */

	public SpaceGameView(){
		frame= new JFrame();
		frame.setSize(WIDTH, LENGTH);
		setSize(WIDTH,LENGTH);
		

		frame.setTitle("Space Ship vs Chicken");

		//places it in the middle
		frame.setLocationRelativeTo(null);
		//can't resize 
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

		instructions();
		
		control = new Controller(WIDTH/2, LENGTH, 0,BULLET_SPEED, 0, EGG_SPEED, SIZE_OF_SHIP,BULLET_SIZE);
		control.level1(0, CHICKEN_LENGTH, LENGTH,CHICKEN_LENGTH);
		
		//runs at 60 framerates

		timer= new Timer(FRAME_RATE,this);
		timer.start();
		frame.setVisible(true);

		//sets the background to the JPanel that has the game drawn in it. 
		frame.setContentPane(this);
		frame.addKeyListener(control);
		//game is not over yet, to be used when the game ends 



	}

	/**Draws the space ship game on the board, and calls the control.update function to 
	 * have the coordinates of egg and bullet moved by their velocity and to draws it after to 
	 * represent any change in the model in the view. 
	 * @param  Graphics g - Graphics object to draw with, should be attached to a jpanel.
	 */
	public void paintComponent(Graphics g){
		//doesn't actually get ship, gets a clone, for seperation of view and model
		//and to avoid concurrent access exception
		SpaceShipAbstract ship = control.getCloneShip();

		//game is not over, so draw board 
		
			//to avoid ghost effects
			super.paintComponent(g);
			
			//gets the bullets
			ArrayList <BulletAbstract> bullets =ship.getBullets();
			//clone of chickens, can't actually modify 
			ArrayList <ChickenAbstract> chickens =control.getCloneChicken();
			//draws the bullets in blue color 
			g.setColor(Color.BLUE);



			BufferedImage img=null;
			try {
				//draws the background
				img = ImageIO.read(new File("Space.jpg"));
				g.drawImage(img, 0, 0, null);
				img = ImageIO.read(new File("spaceShip.png"));
				//draws ship approximately in the bottom middle


				g.drawImage(img,ship.getX(),ship.getY()-100,null);


				img = ImageIO.read(new File("Chicken.png"));
				//draws the bullets 
				drawBullets(g, bullets);
				drawChickenEggs(g,chickens,img);


			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//draws box for how many lives the ship has, and puts how many lives the ship has in it 
			g.setColor(new Color(BROWN_RED_VALUE,BROWN_GREEN_VALUE,BROWN_BLUE_VALUE));
			g.fillRect(LENGTH-100, 0, 100, 50);
			g.setColor(Color.WHITE);
			g.drawString("Lives: "+ship.getLife(), LENGTH-90, 25);
			//draws box for how many points the ship has scored. 
			g.setColor(new Color(BROWN_RED_VALUE-2,200,0));
			g.fillRect(LENGTH-200, 0, 100, 50);
			g.setColor(Color.WHITE);
			g.drawString("Score: "+ship.getScore(), LENGTH-190, 25);


		
		if(this.control.gameOver()) {
			//used to stop calling repaint 
			score=ship.getScore();

		}


	}
	//draws the bullets at their x and y coordinates 
	private void drawBullets (Graphics g, ArrayList <BulletAbstract> bullets){
		for(int z=0; z<bullets.size(); z++){
			BulletAbstract bullet=bullets.get(z);
			int radius=bullet.getRadius();
			//fillOval uses rectangle coordinates 
			g.fillOval(bullet.getX()-radius, bullet.getY() - radius , 2*bullet.getRadius(), 2*bullet.getRadius());	
		}
	}
	//draws the chickens and their eggs at their coordinates
	//@param Graphics g - Graphics object to draw with
	//@param Chickens - an arrayList of chickens to draw onto using the graphics g object
	//@param Img - the image to draw for the chicken. 
	private void drawChickenEggs(Graphics g, ArrayList <ChickenAbstract> chickens, Image chickenImg){
		for (ChickenAbstract chicken:chickens){

			g.setColor(Color.RED);
			//draws the chicken
			g.drawImage(chickenImg, chicken.getX(), chicken.getY(), null);
			EggAbstract egg;
			ArrayList <EggAbstract> eggs= chicken.getEggs();
			//draws all the eggs that belong to that chicken
			for(int i=0; i<eggs.size(); i++){
				egg= eggs.get(i);


				int radius= egg.getRadius();
				int x=egg.getX()-radius;
				int y=egg.getY()-radius;
				g.fillOval(x, y, 2*radius, 2*radius);


			}
		}
		}



	//Displays top 10 high score table, and prompts the user for their name if they made it to the high score table 
	//@param score- the score of the ship for the game that just finished 
		private void table(int score) throws FileNotFoundException{
			//game is over, so no need to refresh 
			timer.stop();
			//column names for the table 
			String[] columnNames = {"Score", "Name"};

			File highScores = new File ("HighScores.txt");
			ArrayList <String> nameAndScore = new ArrayList <String> ();
			Object [][] data = new Object [HIGH_SCORE_BOARD_SIZE][2];
			boolean newScore=true;
			//file must already been created, so read it and see if the user's score made it high score table 
			if(highScores.canRead()){
				Scanner read = new Scanner(highScores);
				//to use shift names if the high score for a game is greater than a score on the previous high score board. 
				while (read.hasNextLine()){
					String x=read.nextLine();
					//name and score are seperated by spaces 
					String [] split =x.split(" ");
					int value= Integer.parseInt(split[0]);
					String theirName= split[1];
					//this means they made the high score 
					if(value<=score && newScore){
						String name=promptForName();
						while(name==null){
							JOptionPane.showMessageDialog(null,"Enter a name greater than 0 characters");
							try{
							name=promptForName();
							}
							catch(Exception e){
								//they didn't enter a name so reprompts 
							}
						}
						nameAndScore.add(score+" " +name);
						newScore=false;
					}

					nameAndScore.add(x+" " +theirName);
				}
			}
			
			if (nameAndScore.size() <HIGH_SCORE_BOARD_SIZE){
				String name=promptForName();
				nameAndScore.add(score+" " +name);
			}
			while(nameAndScore.size() < HIGH_SCORE_BOARD_SIZE){
				nameAndScore.add("0"+" " +"-");
			}

			

			PrintStream write= new PrintStream(highScores);

			for(int i=0; i<nameAndScore.size() && i<10; i++){
				String split []=nameAndScore.get(i).split(" ");
				data[i][0]=split[0];
				data[i][1]=split[1];

				write.println(nameAndScore.get(i));

			}


			
			JTable table  = new JTable(data,columnNames);
			//displays the table onto the screen 
			displayTable(table);
			
			


		}
		
		//makes sure the user can't edit the table, and also displays it on the screen.
		private void displayTable(JTable table){
			//user can't edit the table 
			table.setEnabled(false);
			table.setPreferredScrollableViewportSize(new Dimension (WIDTH/2,LENGTH-400));
			table.setFillsViewportHeight(true);
			JScrollPane scrollpane = new JScrollPane(table);
			highScore= new JFrame();
			highScore.add(scrollpane);
			highScore.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

			highScore.setSize(WIDTH/2,LENGTH-400); 
			//setLayout(new FlowLayout());
			//add(scrollpane)
			highScore.setLocationRelativeTo(null);
			highScore.pack();
			//frame without this line of code 
			highScore.setVisible(true);
		}
		//displays instructions of how to play the game 
		private void instructions(){
			JOptionPane.showMessageDialog(null, "Hello! This game is Called SpaceShip vs Chicken. To control the ship, \n "
					+ "use left and right arrows. To shoot, press space. The chickens will randomly lay eggs and your job \n"
					+ "is to make sure to shoot all of the eggs. You start with 5 lives, and your live is displayed on the top right \n"
					+ "corner. Your score is how many eggs you have shot down. If the eggs reach the ground (bottom) without being \n"
					+ "shot, then you lose one life. If your life is 0, then the game is over.");
		}
		

		@Override
		/**Repaints the game if it is not over. Also requests the controller to update the model 
		 * by one frame. If the game is over, displays the high score table. 
		 * @param ActionEvent e - Information about which action event timer called the function  
		 */
		public void actionPerformed(ActionEvent e) {
			if(!control.gameOver()){
				repaint();
				//updates model 
				control.update(LENGTH);
			}

			else {
				try {
					//to make sure score is 0
					repaint();
					table(this.score);
				} catch (FileNotFoundException ea) {
					// TODO Auto-generated catch block
					ea.printStackTrace();
				}
			}
		}
		//prompts for nanme 
		private String promptForName(){
			String name= JOptionPane.showInputDialog(null,"Congraluation! You have made it to the high score list. What is your name?");
			return name;
		}
	
		public static void main (String [] args){
			SpaceGameView frame= new SpaceGameView();


		}
	}
