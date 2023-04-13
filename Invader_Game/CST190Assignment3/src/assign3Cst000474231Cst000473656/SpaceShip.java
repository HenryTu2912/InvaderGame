package assign3Cst000474231Cst000473656;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
//Class for each space ship
public class SpaceShip {
	//Variable for number of image
	public final int NUM_IMAGES = 6;
	//Variable for the fit size
	public static final int FIT_SIZE = 30;
	//Image array for the space ship
	Image[] images = new Image[NUM_IMAGES];	
	ImageView obView;//Create an image view for spaceship
	Timeline obAni;//Create a time line for space ship
	int nCurrentImage = 0; // Variable for the current image
	double increment; // Variable for the increment
	private InvadersGame app; // Variable for the main game class
	public boolean isDead; // Boolen to check if the ship is dead or not
	int random = 0; // Create a variable
	private long reloadTime = 2000000000; // Create the load time 2 seconds
	private long lastMove; ////Create the variable for the last move time
	//Constructor for the spaceship class
	public SpaceShip(int x, int y, Pane obPane, double increment, InvadersGame app)
	{
		this.app = app;
		//Loops to add image file to the array
		for(int i = 0; i<NUM_IMAGES; i++)
		{
			this.images[i] = new Image("file:images/ufo_"+i+".png");
		}
		this.increment = increment;
		//Set the obview for the spaceship
		obView = new ImageView(images[0]);
		obView.setFitWidth(FIT_SIZE); // Set the fit width for the spaceship
		obView.setFitHeight(FIT_SIZE); // Set the fit height for the spaceship
		obView.setX(x); // Set the x dimension of space ship on screen
		obView.setY(y); // Set the y dimension of space ship on screen
		obPane.getChildren().add(obView); //Add the image view of spaceship to the pane
		//Time line for dropping animation of the spaceship
		obAni = new Timeline(new KeyFrame(Duration.millis(50), e->Drop()));
		obAni.setCycleCount(Timeline.INDEFINITE); //Set the cycle count for the time line
		obAni.play(); // Play the animation	
	}
	//Method to control the dropping animation
	public void Drop()
	{
		//Set the dropping speed for the spaceship
		obView.setY(obView.getY() + this.increment);
		long now = System.nanoTime(); //Set variable for the time moment
		//Each 2 seconds, a random number will be generate from -30 to 30
		//Then add the random number to the x position of the spaceship to move it randomly 
		//to the left or the right
		//If it reach the left edge or right edge, then space ship will bounce back
		if(lastMove <= 0 || now - lastMove >= reloadTime)
		{
			int random = (int) Math.round(Math.random()*30) - (int) Math.round(Math.random()*30);
			obView.setX(obView.getX() + random);
			if(obView.getX() <= 5)
			{
				obView.setX(obView.getX() + 30);
			}
			if(obView.getX() >= 740)
			{
				obView.setX(obView.getX() - 30);
			}
			lastMove = now;
		}	
		//Set the image for the image view of space ship
		obView.setImage(images[++nCurrentImage%this.NUM_IMAGES]);		
		//if the ship reach the bottom of the screen then stop animation
		//Set the ship is dead to false
		//and display the closing screen
		//Clear all the list of image view of ship
		//Clear all the list of ship
		if(obView.getY() >= 800)
		{
			obAni.stop();
			isDead = false;	
			app.closingScreen();
			app.listImgView.clear();
			app.listShip.clear();
		}
	}	
	//Method to determine the ship is hit or not
	public boolean isHit(ImageView shell)
	{
		//if the missile hit the ship, then the ship is dead and remove the ship from screen
		if(this.obView.getX() < shell.getX() + shell.getFitWidth()
		&& obView.getX() + obView.getFitWidth() > shell.getX()
		&& obView.getY() < shell.getY() + shell.getFitHeight()
		&& obView.getY() + obView.getFitHeight() > shell.getY())
		{			
			isDead = true;
			app.removeDeadShip();
			return true;
		}
		else
		{
			return false;
		}		 
	}
	
	
}
