import java.awt.Color;
import java.awt.event.*;
import java.util.*;

//import javax.swing.Timer;

/**Serves as the controller for the SpaceShip game. Can directly communicate with the models, ShipAbstract, and ChickenAbstract.
 * Has a method that moves all the bullets and eggs, and checks for intersection when requested by the view. Also responds accordingly 
 * when keys are pressed on the keyboard. Space to make the ship shoot, left arrow to move it to the left, right arrow to move it the right. 
 * Also fires eggs in random intervals, and game gets harder as time goes on.. 
 * 
 * @author endriaskinfe
 *
 */
public class Controller extends KeyAdapter {
	private SpaceShipAbstract ship;
	//multiple chickens
	private ArrayList <ChickenAbstract> chickens;
	//to have the chickens randomly fire eggs 
	private Timer eggTimer;
	public int timePerEgg;

	//Randomly fires an egg when called by eggTimer
	private TimerTask eggTask;

	// the chance of laying an egg is 1/10
	private static final int EGG_CHANCE = 1;
	private static final int EGG_OUT_OF = 5;
	private static final int MIN_TIME=10;
	//velocity of the bullets in the y direction
	private int vyBullet;
	//velocity of the bullets in x direction
	private int vxBullet;
	//velocity of the eggs in x direction
	private int vxEgg;
	
	//velocity of the eggs in the y direction
	private int vyEgg;
	private int sizeOfShip;
	private static final int FIRE_RATE=400; //in miliseconds
	private boolean fired;
	//turns fired to false after FIRE_RATE has elapsed
	private Timer updateFired;
	//whether the game is over or not
	public static final int LIVES=5;
	private boolean gameOver;
	private int bulletRadius;

	/**Creates a new ship with the specified x, and y coordinate, and store the Vertical and horizontal
	 * velocity of the bullet and egg to use it when a bullet is fired / or an egg is fred.
	 * @param x- x coordinate of the spaceShip (starting)
	 * @param y - y coordinate of the SpaceShip (starting)
	 * @param radius - radius of bullet
	 * @param vxBullet - vertical velocity of bullet
	 * @param vyBullet - horizontal velocity of bullet
	 * @param vxEgg - vertical velocity of Egg
	 * @param vyEgg -horizontal velocity of Egg
	 */
	public Controller(int x,int y ,int vxBullet, int vyBullet, int vxEgg, int vyEgg, int sizeOfShip, int bulletRadius){
		ship = new SpaceShipAbstract(x,y,LIVES);
		this.vxBullet=vxBullet;
		this.bulletRadius=bulletRadius;
		this.vyBullet=vyBullet;
		this.vxEgg=vxEgg;
		this.vyEgg=vyEgg;
		this.sizeOfShip=sizeOfShip;
		chickens = new ArrayList <ChickenAbstract> ();
		this.timePerEgg=500;
		updateFired= new Timer();
		eggTimer= new Timer();
		eggTask= new EggTask();
		//schedules for eggs to be randomly fired from the chickens
		eggTimer.schedule(eggTask, timePerEgg);
		//ship has not fired yet
		fired=false;
	}
	//Ship can fire again after 40 mili seconds
	private class RemindTask extends TimerTask {
		public void run() {
			fired=false;

		}
	}
		//Fires eggs randomly from each chicken, calls it self faster with each call so that the game gets harder.  
	private class EggTask extends TimerTask {
		public void run() {
			Random x= new Random();
			//each chicken has 1/10 chance of laying an egg  
			for(ChickenAbstract chicken:chickens){
				int rand= x.nextInt(EGG_OUT_OF);

				if(rand==EGG_CHANCE){
					chicken.addEgg(new EggAbstract(chicken.getX()+40,chicken.getY()+50,5, vxEgg, vyEgg));

				}

			}

			// breathing room for player 
			if(timePerEgg<MIN_TIME){
				timePerEgg=MIN_TIME*10;
			}
			//Schedules new eggs to be laid in less time in the previous (Makes the game harder with each call) 
			eggTimer.schedule(new EggTask(), timePerEgg--);

		}
	}

	/**Responds to keys that are pressed. When space is pressed, shoots a new bullet, 
	 * Space to make the ship shoot, left arrow to move it to the left by 10 pixels, right arrow to move it the right by 10 pixels. 
	 * Can only fire every 0.4 seconds so that the user can't spam. 
	 * KeyEvent e - Information about which key was pressed 
	 */
	public void keyPressed(KeyEvent e) {
		//current x coordinate of ship
		int x= ship.getX();
		int length=SpaceGameView.LENGTH;
		int width=SpaceGameView.WIDTH;
		int move=this.sizeOfShip/5;
		switch( e.getKeyCode() ) {

			case KeyEvent.VK_RIGHT:
	
	
				//checks that the space ship doesn't go beyond the right side of the panel
				if(x+move+this.sizeOfShip<=length){
	
					ship.setX(x+move);
				}
				else{
	
					ship.setX(width-this.sizeOfShip);
				}
				break;
			case KeyEvent.VK_LEFT:
	
				//checks that the ship doesn't go beyond the left side of the panel 
	
				if(x-move>=0){
					ship.setX(x-move);
				}
				else{
					ship.setX(0);
				}
				break;
	
			case KeyEvent.VK_SPACE:
			{	//if a shot has not been fired in the last 40 milliseconds, shoots 	
				if(!fired){
					//fires a bullet from the center x coordinate of the ship and the smallest y point on the ship
					BulletAbstract bullet= new BulletAbstract (x+sizeOfShip-30,ship.getY()-sizeOfShip,this.bulletRadius, this.vxBullet, this.vyBullet);
	
	
					ship.addBullet(bullet);
	
					fired=true;
	
					updateFired.schedule(new RemindTask(), FIRE_RATE);
				}
			}
	
	
			}
		



	}
	
	
	/**Gets a clone of the ship ( so that only this class can directly communicate with the model)
	 * The ship returned is a new object, and changing it doesn't affect
	 * the ship this class has (deep copy).
	 * 
	 * @return clone of ship 
	 */
	public SpaceShipAbstract getCloneShip(){
		return ship.getCloneShip();
	}
	/**Gets a clone of the chickens ( so that only this class can directly communicate with the model)
	 * The chickens returned are clones, and changing it doesn't affect
	 * the chickens this class has (deep copy).
	 * 
	 * @return clone of chicken ArrayList   
	 */
	public ArrayList <ChickenAbstract> getCloneChicken(){
		if(chickens.size()>0){
		return this.chickens.get(0).getCloneChickens(chickens);
		}
		//chicken is empty so return new ArrayList of chicens
		return new ArrayList <ChickenAbstract> ();
	}


	/**Initializes x amount of chickens onto the board (x is dependent on the separation between the chickens and endingY. )
	 * @param startingX -X coordinate of where to put in the first chicken
	 * @param startingY - Y coordinate of where to put all the chicken
	 * @param endingX- No chicken will be put after the endingX
	 * @param apart- width of each chicken + separation 
	 */
	public void level1(int startingX, int startingY, int endingX,int apart){
		for(int x=startingX; x<endingX; x+=apart){
			chickens.add(new ChickenAbstract (x,startingY));
		}
	}

	/** Gets whether the game is over or not.
	 * @return gameOver - whether the game is over or not 
	 */
	public boolean gameOver(){
		return this.gameOver;
	}
	
	/** Moves all the bullets and eggs by their velocity, and checks for intersection between bullets and eggs. Also checks if an egg 
	 * touches the bottom of the board without being hit and subtracts 1 from space ship's life .
	 * 
	 * @param length - length of board 
	 * 
	 */
	public  void update(int length) {
		ArrayList <BulletAbstract> bullets =ship.getBullets();
		//moves all the bullets
		for(BulletAbstract bullet: bullets){
			bullet.move();
		}
		//moves all the eggs
		for (ChickenAbstract chicken:chickens){
			EggAbstract egg;
			ArrayList <EggAbstract> eggs= chicken.getEggs();
			for(int i=0; i<eggs.size(); i++){
				egg= eggs.get(i);
				egg.move();

				int radius= egg.getRadius();
				boolean alreadyRemoved=false;
				//checks for intersection between bullets
				for(int z=0; z<bullets.size(); z++){
					
					BulletAbstract bullet=bullets.get(z);
					//removes the egg if it intersects it, the bullet is not removed and can
					//destroy other eggs in its way 
					if(bullet.intersects(egg)){
						eggs.remove(i);
						ship.addOneScore();
						alreadyRemoved=true;
						//System.out.println(ship.getScore());
						i--;
					}
					if(bullet.getY()<bullet.getRadius()){
						bullets.remove(z);
						z--;
						
					}
					
				}
				
					//an egg is out of the board, so subtract one from life 
					
					if (egg.getY()+radius>length && !alreadyRemoved){
						eggs.remove(i);
						i--;
						if(ship.getLife()==0){
							this.gameOver=true;
						}
						else{
							ship.lostOneLife();
						} 
						

					}
					//bullet is out of the board, so is removed
				
					
				}
			}

		}

	}







