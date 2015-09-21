/**Represents an abstract Bullet that has  velocity, x and y coordinates. Also has a method to 
 * check for intersection between an egg and a bullet. 
 * 
 * @author endriaskinfe
 *
 */
public class BulletAbstract {

	//x coordinate of Bullet
	private int x;
	//y coordinate of Bullet
	private int y;
	//velocity in x direction
	private int vx;
	//velocity in y direction
	private int vy;
	//radius of ball
	private int radius;
	private static final int DEFAULT_X_VELOCITY=0;
	
	private static final int DEFAULT_Y_VELOCITY=0;
	
	/**Constructs a new Bullet with vertical  and horizontal velocity and current x and y coordinate.
	 * 
	 * @param radius- radius of bullet
	 * @param x- center x coordinate of bullet
	 * @param y- center y coordinate of bullet
	 * @param vx- vertical velocity of the bullet 
	 * @param vy- vertical velocity of the bullet 
	 */
	public BulletAbstract(int x, int y, int radius, int vx, int vy)
	{
		setVx(vx);
		setVy(vy);
		setX(x);
		setY(y);
		setRadius(radius);
	}
	
	/**
	 * Sets the vertical speed of the Bullet
	 * @param x units to move vertically
	 */
	public void setX( int x ) {
		//not checking if x is valid because that depends on the coordinate system
		this.x = x;
	}
	
	/**
	 * Sets the horizontal 'speed' of the Bullet
	 * @param y units to move horizontally
	 */
	public void setY( int y ) {
		//not checking if y is valid because that depends on the coordinate system
		this.y = y;
	}
	
	/**
	 * Set the vertical 'speed' of the Bullet
	 * @param vx the vertical speed to set a Bullet to
	 */
	public void setVx( int vx ) {
		//every velocity is valid depending on the coordinate system
		this.vx = vx;
	}
	
	/**
	 * Set the horizontal 'speed' of the Bullet
	 * @param vy the horizontal speed to set the Bullet to
	 */
	
	public void setVy( int vy ) {
		//every velocity is valid depending on the coordinate system
		this.vy = vy;
	}
	
	/**
	 * Sets the unit radius of the ball
	 * @param r the magnitude of the ball's radius
	 	@throw illegalArgumentException if radius is negative 
	 */
	public void setRadius( int r ) {
		
		if( r < 0 ) {
			throw new IllegalArgumentException("Radius has to be postive");
		}
		radius = r;
	}
	
	/**Finds X coordinate of Bullet.
	 * @return X coordinate.
	 */
	public int getX() { return x; }
	
	/**Finds Y coordinate  of Bullet.
	 * @return Y coordinate.
	 */
	public int getY() { return y; }
	
	/**Finds velocity in the x coordinate  of Bullet.
	 * @return velocity in x direction 
	 */
	public int getVx() { return vx; }
	/**Finds velocity in the y coordinate  of Bullet.
	 * @return velocity in y direction 
	 */
	public int getVy() { return vy; }
	
	/**Finds Radius of Bullet.
	 * @return radius.
	 */
	public int getRadius() { return radius; }
	
	
	
	/**
	 * Moves the Bullet in the X and Y direction
	 */
	public void move() {
		moveX();
		moveY();
	}
	
	/**
	 * Moves the Bullet in the X direction by its X velocity
	 */
	public void moveX() {
		setX( getX() + getVx() );
	}
	/**
	 * Moves the Bullet in the Y direction by its Y velocity 
	 */
	public void moveY() {
		setY( getY() + getVy() );
	}

	/**
	 * Checks for an intersection between an egg and a bullet 
	 * @param other the other egg to check for intersection
	 * @return true if they intersect, false otherwise
	 */
	public boolean intersects(EggAbstract other ) {
		// Distance formula
		int otherRadius = other.getRadius();
		int x2 = this.getX()-other.getX();
		int y2 = this.getY()-other.getY();
		x2 *= x2;
		y2 *= y2;
		int d = x2 + y2;
		
		// sum of radius squared
		int r2 = otherRadius + radius;
		r2 *= r2;
		
		return (d <= r2) ? true : false;
	}
	
	
}


