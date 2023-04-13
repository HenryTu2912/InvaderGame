package assign3Cst000474231Cst000473656;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
//Class for the missile
public class Shell {
	public ImageView obView; //Create an image view for the missile
	private Timeline obAni; // Create the time line for the missile
	private Pane obPane; // Create a pane for the missile
	private SpaceShip spaceship; // Create the spaceship object
	public boolean isDefeat; // Boolean to check if the missile defeat the ship or not
	private InvadersGame app; // Create the object for invaders game class
	//Constructor for the shell class
	public Shell(double x, double y, Pane obPane, SpaceShip spaceship, InvadersGame app)
	{
		this.obPane = obPane;
		this.spaceship = spaceship;
		this.app = app;
		//Create the image view for the missile
		obView = new ImageView("file:images/shell.png");
		//Set the position of the missile on screen
		obView.setX(x-obView.getImage().getWidth()/2);
		obView.setY(y-obView.getFitHeight());
		//Add the image view of missile to the pane
		obPane.getChildren().add(obView);
		//Set the time line for the missile to fly up
		obAni = new Timeline(new KeyFrame(Duration.millis(25),e->up()));
		obAni.setCycleCount(Timeline.INDEFINITE); //Set the cycle count for the animation
		obAni.play();// Play the animation of the missile		
	}
	//Method for the missile to go up by 10 px
	//Then call defeat method
	private void up()
	{
		obView.setY(obView.getY()-10);
		isDefeat();
	}
	//Method to check if the missile can defeat the spaceship or not
	public boolean isDefeat()
	{
		//if spaceship is hit by the missile
		//Stop the animation of missile
		//Stop animation of spaceship
		//Remove the image view of missile and spaceship
		//Remove the spaceship from the list
		if(spaceship.isHit(obView))
		{			
			obAni.stop();
			spaceship.obAni.stop();
			obPane.getChildren().remove(obView);
			obPane.getChildren().remove(spaceship.obView);
			app.listShip.remove(spaceship);
			return true;
		}
		else
		{
			return false;
		}
	}
	
}
