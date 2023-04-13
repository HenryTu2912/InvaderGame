package assign3Cst000474231Cst000473656;

import java.util.ArrayList;
import java.util.stream.Collectors;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class InvadersGame extends Application{
	private long reloadTime = 1000000000; //Variable for loading of missile in 1 seconds
	private long lastMove; // Variable for the last time press Up key
	public ArrayList<SpaceShip> listShip = new ArrayList<>(); // Create list of ship in game
	private int x = 0; // Variable for the x position of ship
	private int y = 0; // Variable for the y position of ship
	private int maxScreen = 800; // Variable for the max width of screen
	private int levelDefault = 0; // Variable for the default level
	private int defaultDiff = 0; // Variable for the default difficult
	private int[] numUFO = {4, 5, 6, 7}; // An array for difficult level has more UFOs
	// An array for difficult level will increase the speed of missile's delay
	private double[] difficult = {1, 1.2, 1.4, 1.6, 2}; 
	private int levelIncrease = 0; // Variable to follow the increase of level in game
	private Stage gameStage = new Stage(); // Variable for main game stage
	private ArrayList<Player> player = new ArrayList<>(); //Create list of players
	private Player newP; // Variable for a new player
	private boolean winBattle = false; // Variable to determine player win the battle
	// Create a list of image view of each spaceship
	public ArrayList<ImageView> listImgView = new ArrayList<>();
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		//Set the title for primary stage
		primaryStage.setTitle("Welcome to Invaders Game");
		//Create a flow pane in vertical to contain welcome node
		FlowPane welcomePane = new FlowPane(Orientation.VERTICAL);
		//Set the gap between node in flowpane
		welcomePane.setVgap(10);
		//Create an image object for welcome stage background
		Image imgWC = new Image("file:images/welcome.jpg");
		//Create an background image for welcome screen
		BackgroundImage backgroundWC = new BackgroundImage(imgWC, 
																BackgroundRepeat.NO_REPEAT, 
																BackgroundRepeat.NO_REPEAT, 
																BackgroundPosition.DEFAULT, 
																BackgroundSize.DEFAULT);
		//Create an background for welcome screen
		Background bgroundWC = new Background(backgroundWC);
		//Set background for welcome screen
		welcomePane.setBackground(bgroundWC);		
		welcomePane.setHgap(5);//Set the horizontal gap for welcome pane
		//Align to center for welcome pane
		welcomePane.setAlignment(Pos.CENTER);
		//Set the padding for welcomePane
		welcomePane.setPadding(new Insets(0,0,0,0));
		//Create an image for the image of game's name 
		ImageView imgWord = new ImageView(new Image("file:images/welcome1.png"));
		//Create a label to indicate user entering their user name
		Label userLabel = new Label("Enter your user name:");
		//Set the color for the label
		userLabel.setTextFill(Color.GOLDENROD);
		//Set the font, font size, font weight for the label
		userLabel.setFont(Font.font("Verdana",FontWeight.BOLD, 14));
		//Create a textfield 
		TextField username = new TextField();
		//Set the max width for textfield
		username.setMaxWidth(250);
		//Set the alignment to center in the text field
		username.setAlignment(Pos.CENTER);
		//Create a button
		Button startGame = new Button("Start Game");
		VBox vBox = new VBox(10); // Create a VBox
		vBox.setAlignment(Pos.CENTER); // Set alignment for node in vbox to center
		// Add all label, text field and button as children to vbox
		vBox.getChildren().addAll(userLabel, username, startGame);
		startGame.setDisable(true); // Set status at the beginning for button to disable
		//Add all name of the game and vbox as children to welcome pane
		welcomePane.getChildren().addAll(imgWord, vBox);
		//Set the key type action for text field
		username.setOnKeyTyped(event -> {
			//Create a new player with the name from text field and score at 0
			newP = new Player(username.getText(), 0); 
			//Condition to ensure the length of user name in text field must longer than 4 character
			//Then the start button will be set to visible
			//If the length of text is shorter than 4 character again, start button will be disable again
			if(username.getText().length() >= 4)
			{
				startGame.setDisable(false);
			}
			else
			{
				startGame.setDisable(true);
			}
		});
		//Set the action for the start button
		startGame.setOnAction((ActionEvent event) ->{
			levelDefault = 0; // Set level to default at 0
			defaultDiff = 0; // Set the difficult level to 0
			winBattle = false; // Set the win battle to false
			levelIncrease = 0; // Set the level increment to 0
			//Call method start game when the button is clicked
			gameStart(gameStage, levelDefault, defaultDiff);
			x = 0; // Set the x position of ship to 0 again
			//Set the start button disable again
			startGame.setDisable(true);
		});
		//Create a scene for welcome pane having 800 x 800
		Scene scene = new Scene(welcomePane, 800,800);
		//Set the scene for primary stage
		primaryStage.setScene(scene);
		primaryStage.show(); // Show the primary stage
		
	}
	// Method to start the game at specific level and difficulty
	public void gameStart(Stage obStage, int level, int diff) {
		Pane obPane = new Pane(); // Create a new pane
		//Create an image for the background of game
		Image img = new Image("file:images/outerspacex.png");
		//Create background image for the image
		BackgroundImage background = new BackgroundImage(img, 
															BackgroundRepeat.NO_REPEAT, 
															BackgroundRepeat.NO_REPEAT, 
															BackgroundPosition.DEFAULT, 
															BackgroundSize.DEFAULT);
		//Create a background for the game
		Background bground = new Background(background);
		//Set the background for the game
		obPane.setBackground(bground);
		// Create an image for the launch
		ImageView obTank = new ImageView("file:images/Launch.png");
		obTank.setX(40); // Set the X dimension for the launch
		obTank.setY(700); //  Set the X dimension for the launch
		obPane.getChildren().add(obTank); // Add the image view as children to the pane	
		
		for(int i = 0; i < numUFO[level]; i++)
		{			
			//Create distance for each UFO based on the number of UFO
			int distance = maxScreen/numUFO[level] - 30;
			//Create a random number to generate the distance of the ship
			int random = (int) Math.round(Math.random()*distance + 30);
			x += random;
			// Create the ship and add to list of ship
			listShip.add(new SpaceShip(x, y, obPane, difficult[diff], this));
			// Add the image view of the created ship to the list of image view
			listImgView.add(listShip.get(i).obView);
		}
		//Set the key event for the gaming pane
		obPane.setOnKeyPressed(e -> HandleKeyPressed(e, obTank, obPane, listShip));
		//Create a scene for the gaming pane
		Scene newScene = new Scene(obPane, 800, 800);
		obStage.setScene(newScene); // Set new scene for the game stage
		obStage.show(); // Show the stage
		obPane.requestFocus();	
		
	}
	//Method to remove the dead ship
	public void removeDeadShip()
	{
		//Searching for list ship, if any ship is dead, remove its image view from the list
		for(SpaceShip ship:listShip)
		{
			if(ship.isDead)
			{
				listImgView.remove(ship.obView);
			}	
		}
		//If the list view is empty, then show pop up window and set win battle to true
		if(listImgView.size() == 0)
		{
			popUp(); //Call popup method
			winBattle = true;
		}
	}
	//Method to handle the key pressed in the gaming pane
	private void HandleKeyPressed(KeyEvent e, ImageView tank, Pane obPane, ArrayList<SpaceShip> spaceShip)
	{
		//if the left key is pressed and the launch not reach the left edge, then move the launch to the left
		//If the right key is pressed and the launch not reach the right edge, then move the launch to the right
		if(e.getCode() == KeyCode.LEFT)
		{
			if(tank.getX()>0)
				tank.setX(tank.getX() - 5);
		}
		else if(e.getCode() == KeyCode.RIGHT)
		{
			if(tank.getX()<800-tank.getImage().getWidth())
				tank.setX(tank.getX() + 5);
		}
		//Set variable for the time moment 
		long now = System.nanoTime();
		//if the last move variable <= 0 or the time between last move and now is equal or longer than 1 second
		//then, if the key up is pressed, a new missile will be created 
		if(lastMove <= 0 || now - lastMove >= reloadTime)
		{
			if(e.getCode() == KeyCode.UP)
			{
				for(SpaceShip spaceship:listShip)
				{
					new Shell(tank.getX()+tank.getImage().getWidth()/2,tank.getY(), obPane, spaceship, this);			
				}	
			}			
			lastMove = now;
		}
	}
	//Method to create a pop up window when winning a battle
	public void popUp()
	{
		//Create a border pane for pop up window
		BorderPane popUp = new BorderPane();
		HBox btnBox = new HBox(10); // Create a Hbox with the distance of each elements is 10
		Button yes = new Button("Yes"); // Create a button call yes
		Button no = new Button("No"); // Create a button call no
		btnBox.getChildren().addAll(yes, no); // Add all yes and no button as children of hbox
		int level = levelDefault + defaultDiff + 1; // Create variable to save the number of level
		//Create a lable 
		Label tAsk = new Label ("Congratulation! You win level " + level +".\n    Do you want to continue?",btnBox);
		tAsk.setContentDisplay(ContentDisplay.BOTTOM); // Set the position of content of label
		popUp.setCenter(tAsk); // Set the label to the center of the pane
		//Create the scene for pop up window
		Scene popUpScene = new Scene(popUp, 500, 200);
		gameStage.setTitle("You win"); //Set the title for the pop up window
		gameStage.setScene(popUpScene); // set the scene for the pop up window
		gameStage.show(); // Show the stage of pop up window
		//Set the event handler for the yes button to start new game at new level
		//Increase the level when the level number is less than 4
		//Then, increase the difficult when the level number larger than 4.
		//If player wants to continue to play, increase the level increment to update score later
		yes.setOnAction(event -> {
			if(levelDefault < 3) levelDefault++;
			if(levelDefault == 3 && defaultDiff <4) defaultDiff++;
			if(levelDefault == 3 && defaultDiff == 4)
			{
				levelIncrease ++;
			}
			gameStart(gameStage, levelDefault, defaultDiff);
			x = 0; // reset the x dimension of the space ship
		});
		//Set the event handler for the no button to switch to closing screen
		no.setOnAction(event -> {
			closingScreen();
		});
	}
	//Method to set for the closing screen
	public void closingScreen()
	{
		int score = 0; // Create variable for score
		//If win the battle, then score will increase by 1
		//else the score will remain
		if(winBattle)
		{
			score = levelDefault + defaultDiff + 1;
		}
		else
		{
			score = levelDefault + defaultDiff;
		}
		//if at the highest level, and players want to continue to play,
		//increase score by level increase
		if(levelDefault == 3 && defaultDiff == 4)
		{
			score += levelIncrease;
		}
		newP.setScore(score); // Update the score for the player
		//If the player has the same username in the list, then remove him/her
		if(player.contains(newP)) player.remove(newP);
		player.add(newP); // Add the new player
		//Create an list of top 3 player having high score
		ArrayList<Player> top = (ArrayList<Player>) player.stream().sorted((m, n) -> n.getScore() - m.getScore()).limit(3).collect(Collectors.toList());
		//Create a border pane for closing screen
		BorderPane closingScreen = new BorderPane();
		//Create an list for the text of top 3 player
		ArrayList<Text> topList = new ArrayList<>();
		//Each player will be created a text and add to the top list
		//this text will be set font and fill with color
		for(int i = 0; i < top.size(); i++)
		{					
			String s = "Top" + (i+1) + " : " + top.get(i).toString();
			Text t = new Text(s);
			t.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
			t.setFill(Color.WHITE);
			topList.add(t);			
		}
		//Create an image background for closing screen
		Image imgClose = new Image("file:images/end.jpg");
		BackgroundImage backgroundClose = new BackgroundImage(imgClose, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		Background bgroundClose = new Background(backgroundClose);
		closingScreen.setBackground(bgroundClose); // Set the background for closing screen
		//Create an image view for the image of end game word
		ImageView imgWord = new ImageView(new Image("file:images/endgame.png"));
		//Create new vbox
		VBox listWin = new VBox(10);
		//Create a text and set its font and color
		Text title = new Text("Top 3 Players");
		title.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 40));
		title.setFill(Color.GOLDENROD);
		//Add all title and image view to the vbox
		listWin.getChildren().addAll(imgWord, title);
		//Add all the text of each top 3 player to the vbox
		for(Text t: topList)
		{
			listWin.getChildren().add(t);
		}
		//Set the alignment for the vbox
		listWin.setAlignment(Pos.CENTER);
		// Add the vbox as children to closing screen
		closingScreen.setCenter(listWin);
		//Create the scene for closing screen
		Scene closing = new Scene(closingScreen, 800, 800);
		//Set title for closing screen
		gameStage.setTitle("Game result");
		//Set the scene for closing screen
		gameStage.setScene(closing);
		//Show the stage
		gameStage.show();
	}
	
	public static void main(String[] args) {
	    launch(args);
	  }
}
//Create class for each player
class Player 
{
	private String name; //Declare string object for player's name
	private int score; //Declare variable for player's score
	//Constructor for class player
	public Player(String name, int score)
	{
		this.name = name;
		this.score = score;
	}
	//Method to get name of player
	public String getName()
	{
		return this.name;
	}
	//Method to get score of player
	public int getScore()
	{
		return this.score;
	}
	//Method to update the score for player
	public void setScore(int number)
	{
		this.score = number;
	}
	//Method to print information of the player
	@Override
    public String toString() {
        return String.format("Player name: %s; Score: %d", this.name, this.score);
    }

	
	
}
