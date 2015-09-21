import java.util.ArrayList;

/**Represents an abstract ChickenAbstrct that has velocity, x and y coordinates, and an array of eggs.  
 * @author endriaskinfe
 *
 */
public class ChickenAbstract {

	//x coordinate of Chicken
	private int x;
	//y coordinate of Chicken
	private int y;
	//velocity in x direction
	private int vx;
	//velocity in y direction
	private int vy;
	private ArrayList <EggAbstract> eggs;

	/**
	 * Creates a chicken with an x and y coordinate 
	 * @param x - x coordinate for where the chicken is
	 * @param y - y coordiante for where the chicken is  
	 */
	public ChickenAbstract(int x, int y)
	{	eggs= new ArrayList <EggAbstract> ();

	setX(x);
	setY(y);

	}

	/**
	 * Sets the vertical speed of the Chicken
	 * @param x units to move vertically
	 */
	public void setX( int x ) {
		//not checking if x is valid because that depends on the coordinate system
		this.x = x;
	}

	/**
	 *Adds an egg to the ArrayList this chicken has.
	 * @param egg to add
	 */
	public void addEgg( EggAbstract x ) {
		//not checking if x is valid because that depends on the coordinate system
		if (x!=null){
			eggs.add(x);
		}
	}

	/**
	 * Sets the vertical speed of the Chicken
	 * @param x units to move vertically
	 */
	public ArrayList <EggAbstract> getEggs() {
		//not checking if x is valid because that depends on the coordinate system
		return eggs;
	}
	/**
	 * Sets the horizontal 'speed' of the Chicken
	 * @param y units to move horizontally
	 */
	public void setY( int y ) {
		//not checking if y is valid because that depends on the coordinate system
		this.y = y;
	}

	/**
	 * Set the vertical 'speed' of the Chicken
	 * @param vx the vertical speed to set a Chicken to
	 */
	public boolean setVx( int vx ) {
		//every velocity is valid depending on the coordinate system
		this.vx = vx;
		return true;
	}

	/**
	 * Set the horizontal 'speed' of the Chicken
	 * @param vy the horizontal speed to set the Chicken to
	 */

	public void setVy( int vy ) {
		//every velocity is valid depending on the coordinate system
		this.vy = vy;
	}


	/**Finds X coordinate of Chicken.
	 * @return X coordinate.
	 */
	public int getX() { return x; }

	/**Finds Y coordinate  of Chicken.
	 * @return Y coordinate.
	 */
	public int getY() { return y; }

	/**Finds velocity in the x coordinate  of Chicken.
	 * @return velocity in x direction 
	 */
	public int getVx() { return vx; }
	/**Finds velocity in the y coordinate  of Chicken.
	 * @return velocity in y direction 
	 */
	public int getVy() { return vy; }
	/**Clones the chickens passed in as parameter ( so that only this class can directly communicate with the model)
	 * The chickens returned are clones, and changing it doesn't affect
	 * the chickens this class has (deep copy).
	 * 
	 * @return an arrayList of chickens that are clones of the parameter 
	 */
	public ArrayList <ChickenAbstract> getCloneChickens(ArrayList <ChickenAbstract> chickens){

		ArrayList <ChickenAbstract> cloneChickens= new ArrayList <ChickenAbstract> ();
		//clones all the chickens including their eggs
		for (int i=0; i <chickens.size(); i++){
			ChickenAbstract chicken= chickens.get(i);

			cloneChickens.add(new ChickenAbstract (chicken.getX(),chicken.getY()));
			ArrayList <EggAbstract> eggs = chickens.get(i).getEggs();
			//clones each chickens eggs 
			for(EggAbstract egg: eggs)
			{
				cloneChickens.get(i).addEgg(new EggAbstract (egg.getX(),egg.getY(),egg.getRadius(),egg.getVx(),egg.getVy()));
			}


		}

		return cloneChickens;
	}
	/**
	 * Moves the Chicken in the X and Y direction
	 */
	public void move() {
		moveX();
		moveY();
	}

	/**
	 * Moves the Chicken in the X direction by its X velocity
	 */
	public void moveX() {
		setX( getX() + getVx() );
	}
	/**
	 * Moves the Chicken in the Y direction by its Y velocity 
	 */
	public void moveY() {
		setY( getY() + getVy() );
	}

}