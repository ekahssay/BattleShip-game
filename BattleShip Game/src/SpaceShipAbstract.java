import java.util.ArrayList;

import javax.management.RuntimeErrorException;

/**Represents an abstract Spaceship that has lives, velocity, x and y coordinates 
 * and an array of bullets. 
 * 
 * @author endriaskinfe
 *
 */
public class SpaceShipAbstract {
	//x coordinate of spaceship
	private int x;
	//y coordinate of spaceship
	private int y;
	//velocity in x direction
	private int vx;
	//velocity in y direction
	private int vy;
	//how many lives this ship has 

	private int life;
	//how much the ship has scored 
	private int score;
	
	private static final int DEFAULT_X_VELOCITY=0;
	private static final int DEFAULT_Y_VELOCITY=0;
	private ArrayList <BulletAbstract> bullets;
	/**
	 * Constructs a new ship with vertical velocity and current x and y coordinate.
	 * @param  x- starting x coordinate of space ship
	 * @param y- starting y coordinate of space ship
	 * @param life - how many lives this ship has 
	 */
	public SpaceShipAbstract(int x, int y, int life)
	{	this.life=life;
	setVx(DEFAULT_X_VELOCITY);
	setVy(DEFAULT_Y_VELOCITY);
	setX(x);
	setY(y);
	score=0;

	bullets= new ArrayList <BulletAbstract> ();
	}
	/**
	 * Sets the vertical speed of the SpaceSHip
	 * @param x units to move vertically
	 */
	public void addBullet( BulletAbstract x ) {
		//not checking if x is valid because that depends on the coordinate system
		if (x!=null){
			bullets.add(x);
		}
	}
	/**
	 * Sets the vertical speed of the SpaceShip
	 * @param x units to move vertically
	 */
	public ArrayList <BulletAbstract> getBullets( ) {
		//not checking if x is valid because that depends on the coordinate system
		return bullets;
	}
	/**
	 * Sets the vertical speed of the SpaceShip
	 * @param x units to move vertically
	 */
	public void setX( int x ) {
		//not checking if x is valid because that depends on the coordinate system
		this.x = x;
	}

	/**
	 * Sets the horizontal 'speed' of the Spaceship
	 * @param y units to move horizontally
	 */
	public void setY( int y ) {
		//not checking if y is valid because that depends on the coordinate system
		this.y = y;
	}

	/**
	 * Set the vertical 'speed' of the SpaceShip
	 * @param vx the vertical speed to set a spaceShip to
	 */
	public boolean setVx( int vx ) {
		//every velocity is valid depending on the coordinate system
		this.vx = vx;
		return true;
	}

	/**
	 * Set the horizontal 'speed' of the SpaceShip
	 * @param vy the horizontal speed to set the SpaceShip to
	 */

	public void setVy( int vy ) {
		//every velocity is valid depending on the coordinate system
		this.vy = vy;
	}



	/**Finds X coordinate of SpaceShip.
	 * @return X coordinate.
	 */
	public int getX() { return x; }

	/**Finds Y coordinate  of SpaceShip.
	 * @return Y coordinate.
	 */
	public int getY() { return y; }

	/**Finds velocity in the x coordinate  of SpaceShip.
	 * @return velocity in x direction 
	 */
	public int getVx() { return vx; }
	/**Finds velocity in the y coordinate  of SpaceShip.
	 * @return velocity in y direction 
	 */
	public int getVy() { return vy; }

	/**Finds Radius of SpaceShip.
	 * @return radius - radius of spaceship 
	 */


	/**Finds how many lives SpaceShip has.
	 * @return life - how many lives
	 */
	public int getLife() { return life; }
	/**Finds the score of this SpaceShip.
	 * @return score- the score 
	 */
	public int getScore() { return score; }


	/**
	 * Moves the SpaceShip in the X and Y direction
	 */
	public void move() {
		moveX();
		moveY();
	}

	/**
	 * Moves the SpaceShip in the X direction by its X velocity
	 */
	public void moveX() {
		setX( getX() + getVx() );
	}
	/**
	 * Moves the SpaceShip in the Y direction by its Y velocity 
	 */
	public void moveY() {
		setY( getY() + getVy() );
	}
	//used to throw an exception if there was an attempt to make life less than 0.
	private class IllegalLifeException extends RuntimeException {

		public IllegalLifeException(String string) {
			super(string);
		}

	}

	/**Subtracts one from life.
	 * @throws IllegalLifeException if there is an attempt to make life less than 0. 
	 */
	public void lostOneLife() {
		//subtracts one from life if current life is greater than 0. 
		if(this.life==0){
			throw new IllegalLifeException ("Ship can't have negative lives.");
		}
		else{
			this.life--;
		}
	}
	/**Adds one to score.
	 * 
	 */
	public void addOneScore() {
		//subtracts one from life if current life is greater than 0. 
		

		this.score++;
	}
	/**Gets a clone of the ship ( so that only this class can directly communicate with the model)
	 * The ship returned is a new object, and changing it doesn't affect
	 * the ship this class has (deep copy).
	 * 
	 * @return clone of ship 
	 */
	public SpaceShipAbstract getCloneShip(){
		//creates a clone ship 
		SpaceShipAbstract cloneShip = new SpaceShipAbstract (this.getX(), this.getY(),this.getLife()) ;
		cloneShip.score=this.score;
		ArrayList <BulletAbstract>  bullets=  this.getBullets();
		//clones all the bullets from orignal ship 
		for (BulletAbstract bullet: bullets){
			cloneShip.addBullet(new BulletAbstract (bullet.getX(),bullet.getY(), bullet.getRadius(), bullet.getVx(),bullet.getVy()));
		}
		return cloneShip;
	}
}

