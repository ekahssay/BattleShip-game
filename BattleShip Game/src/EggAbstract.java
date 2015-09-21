/**Represents an abstract Egg that has velocity, x and y coordinates.
 * 
 * @author endriaskinfe
 *
 */
public class EggAbstract {
	//x coordinate of Egg
	private int x;
	//y coordinate of Egg
	private int y;
	//velocity in x direction
	private int vx;
	//velocity in y direction
	private int vy;
	//radius of ball
	private int radius;
	private static final int DEFAULT_X_VELOCITY=0;
	
	private static final int DEFAULT_Y_VELOCITY=0;
	
	/**
	 * Constructs a new egg with a specific x and y coordinates, and velocity.
	 * 
	 * @param radius- radius of Egg
	 * @param y- center y coordinate of egg
	 * @param vx- vertical velocity of the egg 
	 * @param vy- vertical velocity of the egg
	 */
	public EggAbstract(int x, int y, int radius, int vX, int vY)
	{
		setVx(vX);
		setVy(vY);
		setX(x);
		setY(y);
		setRadius(radius);
	}
	
	/**
	 * Sets the vertical speed of the Egg
	 * @param x units to move vertically
	 */
	public void setX( int x ) {
		//not checking if x is valid because that depends on the coordinate system
		this.x = x;
	}
	
	/**
	 * Sets the horizontal 'speed' of the Egg
	 * @param y units to move horizontally
	 */
	public void setY( int y ) {
		//not checking if y is valid because that depends on the coordinate system
		this.y = y;
	}
	
	/**
	 * Set the vertical 'speed' of the Egg
	 * @param vx the vertical speed to set a Egg to
	 */
	public boolean setVx( int vx ) {
		//every velocity is valid depending on the coordinate system
		this.vx = vx;
		return true;
	}
	
	/**
	 * Set the horizontal 'speed' of the Egg
	 * @param vy the horizontal speed to set the Egg to
	 */
	
	public void setVy( int vy ) {
		//every velocity is valid depending on the coordinate system
		this.vy = vy;
	}
	
	/**
	 * Sets the unit radius of the egg
	 * @param r the magnitude of the egg's radius
	 	@throws illegalArgumentException is radius is not postive 
	 */
	public void setRadius( int r ) {
		
		if( r < 0 ) {
			throw new IllegalArgumentException("Radius has to be postive");
		}
		radius = r;
	}
	
	/**Finds X coordinate of Egg.
	 * @return X coordinate.
	 */
	public int getX() { return x; }
	
	/**Finds Y coordinate  of Egg.
	 * @return Y coordinate.
	 */
	public int getY() { return y; }
	
	/**Finds velocity in the x coordinate  of Egg.
	 * @return velocity in x direction 
	 */
	public int getVx() { return vx; }
	/**Finds velocity in the y coordinate  of Egg.
	 * @return velocity in y direction 
	 */
	public int getVy() { return vy; }
	
	/**Finds Radius of Egg.
	 * @return radius.
	 */
	public int getRadius() { return radius; }
	
	
	
	/**
	 * Moves the Egg in the X and Y direction
	 */
	public void move() {
		moveX();
		moveY();
	}
	
	/**
	 * Moves the Egg in the X direction by its X velocity
	 */
	public void moveX() {
		setX( getX() + getVx() );
	}
	/**
	 * Moves the Egg in the Y direction by its Y velocity 
	 */
	public void moveY() {
		setY( getY() + getVy() );
	}
}
