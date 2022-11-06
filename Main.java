package application;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

// ICS4U1 CPT: Snakes and Ladders
// TEACHER: MR. CONWAY
// NAME: JUGRAJ SINGH
// DESCRIPTION: 
// This Snakes and Ladders game is available in 2,3 and 4 player mode. The player names are taken from a .txt file located in the game folder and every name should be on it’s own line. 
// If a player does not provide a name then they will be given a default name. 
// The rules are simple, the first player will roll by clicking the roll button and the number rolled will be shown on an image of a dice and displayed via a text field. 
// The number they roll is the number of squares they will move forward via an AnimationTimer forward. 
// If the square that the player lands on is a ladder beginning or a snake head, then they will be moved to the end of the ladder top or snake tail. 
// At this point the next player will go and this cycle continues until a player lands on square 100, at which point the game ends.
// To win, a player must land on square 100 and if the player’s dice roll plus their current location is greater than 100, then they will not be moved.
// One can exit the program by clicking the exit button or close the window at any time.
// Upon winning, program will rank winner’s high score amongst previous high scores and displays an alert thanking users for playing. 

// DETAILS:
// Upon running the program, the user will be shown the opening menu, which contains 3 buttons (1 player, 2 player, 3 player) inside a VBox. 
// When the button is clicked, suppose the user clicks the 3 player button, then the program will invoke the corresponding method which will create 3 player objects in the Main class and add them to an ArrayList. 
// The method setGame will then be called, which changes the scene, reads the player names from a .txt file, initializes an array of 100 square objects and displays the game interface. 
// After the game has been set, the first player will be prompted to click the roll button via a label, which calls the roll method.
// In the role method, a random number from 1 - 6 will be generated from the Dice class and displayed in the form of an image and outputted via a text field. 
// The play method will then be called and the player node will be animated via an AnimationTimer from its current square till it reaches the destination square, which is the player's current location plus the number they rolled.
// After reaching the destination square, checkLadder and checkSnake methods (which in-turn invoke methods methods from the Square class) will be called to check if the destination square is a snake or ladder.
// The player will be moved accordingly and it will be displayed in the TextField. 
// After the first player’s turn is complete, next player is selected from the ArrayList. Once the last player has gone his turn, the cycle will reset back to the beginning of the ArrayList.
// This cycle will continue until one player has landed on square 100 at which point the checkWinner method will run and the highScorces method will be called.
// The highScores method will read a .txt file and use insertion sort, to sort the highScores in ascending order and write them back to the same file. 
// After winning, the user will be congratulated via an alert and the program will close. The user can exit the program any time by pressing the exit button or closing the window.



public class Main extends Application {

    // Declaring and initializing Panes for the opening menu and game interface
    public static Pane openingMenu = new Pane();
    public static Pane gameCenter = new Pane();

    // Declaring and initializing Scenes to display the opening menu and game interface
    public static Scene scene = new Scene(openingMenu);
    
    public static Scene gameDisplay = new Scene(gameCenter);

    // Declaring and initializing Imageview for the gameboard
    public static ImageView board = new ImageView("File:GameBoard.jpg");

    // Declaring and initializing buttons for user to roll and exit program
    public static Button rollBtn = new Button();
    public static Button exitBtn = new Button();

    // Declaring and initializing ImageView to display the number that the user rolled
    public static ImageView diceDisplay = new ImageView();

    // Declaring array of squares 
    public static Square[] squares = new Square[101];

    // Declaring player objects
    public static Player player1;
    public static Player player2;
    public static Player player3;
    public static Player player4;

    // Declaring ArrayLists to store the player objects and to store the player names
    public static ArrayList<Player> players = new ArrayList<Player>();
    public static ArrayList<String> playerNames = new ArrayList<String>();

    // Declaring dice object and int to store the number rolled
    public static Dice dice = new Dice();
    public static int diceNum;

    // Declaring and initializing int index
    public static int index = -1;

    // Declaring AnimationTimer to allow player nodes to move
    private static AnimationTimer timer;

    // Declaring and initializing text field to display game information
    public static TextField gameConsole = new TextField();

    // Declaring and initializing label to display which user is playing
    public static Label playerTurnLabel = new Label();

    // Declaring and initializing int playerNum to store the index of the player playing
    public static int playerNum = 0;

    // Declaring and initializing boolean to see if user has won the game and declaring int to store index of winning player
    public static boolean winner = false;
    public static int winnerPlayer;


    // Creating method start
    public void start(Stage primaryStage) {

        // Try Catch to catch any exceptions
        try {
        	
        	// Setting stage resizable to false    	
            primaryStage.setResizable(false);

            // Declaring and initializing ImageView for opening screen background
            ImageView jungleBackground = new ImageView(new Image("File:JungleBackground.jpg"));
            jungleBackground.setPreserveRatio(true);
            jungleBackground.setFitHeight(550);

            // Declaring and initializing ImageView for snakes and ladders logo
            ImageView snakesLogo = new ImageView(new Image("File:SnakesLaddersLogo.png"));
            snakesLogo.setPreserveRatio(true);
            snakesLogo.setFitWidth(550);
            snakesLogo.setLayoutX(snakesLogo.getFitWidth() / 3);
            snakesLogo.setLayoutY(30);

            // Declaring and initializing button for playing the game with 2 players
            Button twoPlayer = new Button();
            twoPlayer.setText("2 PLAYERS");
            twoPlayer.setFont(Font.font("Britannic Bold", FontWeight.BOLD, 20));
            twoPlayer.setPrefSize(150, 75);
            twoPlayer.setAlignment(Pos.CENTER);

            // Call method clickTwo on click and pass in primaryStage
            twoPlayer.setOnAction(e -> clickTwo(primaryStage));

            // Declaring and initializing button for playing the game with 3 players
            Button threePlayer = new Button();
            threePlayer.setText("3 PLAYERS");
            threePlayer.setFont(Font.font("Britannic Bold", FontWeight.BOLD, 20));
            threePlayer.setPrefSize(150, 75);
            threePlayer.setAlignment(Pos.CENTER);

            // Call method clickThree on click and pass in primaryStage
            threePlayer.setOnAction(e -> clickThree(primaryStage));

            // Declaring and initializing button for playing the game with 4 players
            Button fourPlayer = new Button();
            fourPlayer.setText("4 PLAYERS");
            fourPlayer.setFont(Font.font("Britannic Bold", FontWeight.BOLD, 20));
            fourPlayer.setPrefSize(150, 75);
            fourPlayer.setAlignment(Pos.CENTER);

            // Call method clickFour on click and pass in primaryStage
            fourPlayer.setOnAction(e -> clickFour(primaryStage));

            // Declaring and initializing label to display message in the opening screen
            Label message = new Label();
            message.setText("Remember to store your names in PlayerNames.txt!");
            message.setFont(Font.font("Britannic Bold", FontWeight.BOLD, 16));
            message.setPrefWidth(450);
            message.setAlignment(Pos.CENTER);

            // Declaring and initializing VBox layout with vertical gap of twenty
            VBox buttons = new VBox(20);

            // Adding message, twoPlayer, threePlayer, fourPlayer to VBox
            buttons.getChildren().addAll(message, twoPlayer, threePlayer, fourPlayer);
            buttons.setLayoutX(245);
            buttons.setLayoutY(235);
            buttons.setAlignment(Pos.CENTER);

            // Creating Window event handler
            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

                public void handle(WindowEvent e) {

                    // Display alert to confirm if user wants to quit
                    Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Snakes and Ladders");
                    alert.setHeaderText(null);
                    alert.setContentText("Are you sure you want to exit?");
                    alert.getButtonTypes().clear();
                    alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);

                    // Get their answer
                    Optional<ButtonType> result = alert.showAndWait();

                    // If they wish to quit
                    if (result.get() == ButtonType.YES) {

                        // Display an alert thanking them for playing and close program
                        Alert thanks = new Alert(AlertType.INFORMATION);
                        thanks.setTitle("Snakes and Ladders");
                        thanks.setHeaderText(null);
                        thanks.setContentText("Thanks for playing!");
                        thanks.showAndWait();
                        System.exit(0);

                    }  

                    // If they do not wish to quit then return to program
                    else {
                        
                        e.consume();

                    }

                }

            });

            // Add all components to openingMenu pane
            openingMenu.getChildren().addAll(jungleBackground, snakesLogo, buttons);

            // Set the scene
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            System.out.println("Exception was found: " + e.getMessage());
        }
    }


    // Create method for when user wants to play 2 player mode and pass in the stage
    private void clickTwo(Stage stage) {

        // Declaring and initializing ImageViews to store nodes of the players
        ImageView player1Node = new ImageView(new Image("File:player1.png"));
        ImageView player2Node = new ImageView(new Image("File:player2.png"));

        // Try Catch to catch any exceptions
        try {

            // Call method readNames to get player names
            readNames();

        } catch (Exception e) {

            System.out.println("Exception was found: " + e.getMessage());
        }

        // Initialize Player objects
        player1 = new Player(45, 30 + (70 * 9), 1, "Player 1", player1Node, 0, Color.RED, 0);
        player2 = new Player(45, 30 + (70 * 9), 1, "Player 2", player2Node, 0, Color.BLUE, 0);

        // Add Player objects to ArrayList
        players.add(player1);
        players.add(player2);

        // Setting the player name if they have entered one
        for (int x = 0; x < playerNames.size(); x++) {

            if (!(playerNames.get(x).equals("")) && x < players.size()) {

                // Call setName method in Player class
                players.get(x).setName(playerNames.get(x));

            }

        }

        // Call method setGame and pass in the Stage
        setGame(stage);

    }


    // Create method for when user wants to play 3 player mode and pass in the stage
    private void clickThree(Stage stage) {

        // Declaring and initializing ImageViews to store nodes of the players
        ImageView player1Node = new ImageView(new Image("File:player1.png"));
        ImageView player2Node = new ImageView(new Image("File:player2.png"));
        ImageView player3Node = new ImageView(new Image("File:player3.png"));
        
        // Try Catch to catch any exceptions
        try {

            // Call method readNames to get player names
            readNames();

        } catch (Exception e) {

            System.out.println("Exception was found: " + e.getMessage());

        }

        // Initialize Player objects
        player1 = new Player(45, 30 + (70 * 9), 1, "Player 1", player1Node, 0, Color.RED, 0);
        player2 = new Player(45, 30 + (70 * 9), 1, "Player 2", player2Node, 0, Color.BLUE, 0);
        player3 = new Player(45, 30 + (70 * 9), 1, "Player 3", player3Node, 0, Color.GREEN, 0);
       
        // Add Player objects to ArrayList
        players.add(player1);
        players.add(player2);
        players.add(player3);

        // Setting the player name if they have entered one
        for (int x = 0; x < playerNames.size(); x++) {

            if (!(playerNames.get(x).equals("")) && x < players.size()) {

                // Call setName method in Player class
                players.get(x).setName(playerNames.get(x));

            }

        }

        // Call method setGame and pass in the Stage
        setGame(stage);

    }


    // Create method for when user wants to play 4 player mode and pass in the stage
    private void clickFour(Stage stage) {

        // Declaring and initializing ImageViews to store nodes of the players
        ImageView player1Node = new ImageView(new Image("File:player1.png"));
        ImageView player2Node = new ImageView(new Image("File:player2.png"));
        ImageView player3Node = new ImageView(new Image("File:player3.png"));
        ImageView player4Node = new ImageView(new Image("File:player4.png"));

        // Try Catch to catch any exceptions
        try {

            // Call method readNames to get player names
            readNames();

        } catch (Exception e) {

            System.out.println("Exception was found: " + e.getMessage());

        }

        // Initialize Player objects
        player1 = new Player(45, 30 + (70 * 9), 1, "Player 1", player1Node, 0, Color.RED, 0);
        player2 = new Player(45, 30 + (70 * 9), 1, "Player 2", player2Node, 0, Color.BLUE, 0);
        player3 = new Player(45, 30 + (70 * 9), 1, "Player 3", player3Node, 0, Color.GREEN, 0);
        player4 = new Player(45, 30 + (70 * 9), 1, "Player 4", player4Node, 0, Color.PURPLE, 0);
       
        // Add Player objects to ArrayList
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);

        // Setting the player name if they have entered one
        for (int x = 0; x < playerNames.size(); x++) {

            if (!(playerNames.get(x).equals("")) && x < players.size()) {
                
                // Call setName method in Player class
                players.get(x).setName(playerNames.get(x));

            }

        }

        // Call method setGame and pass in the Stage
        setGame(stage);

    }


    // Creating method to set up game and passing in Stage
    public static void setGame(Stage stage) {

        // Declaring and initializing int for each row
        int row0 = 0;
        int row1 = 0;
        int row2 = 0;
        int row3 = 0;
        int row4 = 0;
        int row5 = 0;
        int row6 = 0;
        int row7 = 0;
        int row8 = 0;
        int row9 = 0;

        // Initializing every Square object in array except index 0
        for (int x = 1; x <= 100; x++) {

            // Initializing first row of Squares on game board 
            if (x >= 1 && x <= 10) {

                squares[x] = new Square(45 + (70 * row0), 30 + (70 * 9), x, false, false, 0);
                row0++;

            }

            // Initializing second row of Squares on game board 
            if (x >= 11 && x <= 20) {

                squares[x] = new Square(45 + (70 * (9 - row1)), 30 + (70 * 8), x, false, false, 1);
                row1++;

            }

            // Initializing third row of Squares on game board 
            if (x >= 21 && x <= 30) {

                squares[x] = new Square(45 + (70 * row2), 30 + (70 * 7), x, false, false, 0);
                row2++;

            }

            // Initializing fourth row of Squares on game board 
            if (x >= 31 && x <= 40) {

                squares[x] = new Square(45 + (70 * (9 - row3)), 30 + (70 * 6), x, false, false, 1);
                row3++;

            }

            // Initializing fifth row of Squares on game board 
            if (x >= 41 && x <= 50) {

                squares[x] = new Square(45 + (70 * row4), 30 + (70 * 5), x, false, false, 0);
                row4++;

            }

            // Initializing sixth row of Squares on game board 
            if (x >= 51 && x <= 60) {

                squares[x] = new Square(45 + (70 * (9 - row5)), 30 + (70 * 4), x, false, false, 1);
                row5++;

            }

            // Initializing seventh row of Squares on game board 
            if (x >= 61 && x <= 70) {

                squares[x] = new Square(45 + (70 * row6), 30 + (70 * 3), x, false, false, 0);
                row6++;

            }

            // Initializing eighth row of Squares on game board 
            if (x >= 71 && x <= 80) {

                squares[x] = new Square(45 + (70 * (9 - row7)), 30 + (70 * 2), x, false, false, 1);
                row7++;

            }

            // Initializing ninth row of Squares on game board 
            if (x >= 81 && x <= 90) {

                squares[x] = new Square(45 + (70 * row8), 30 + (70 * 1), x, false, false, 0);
                row8++;

            }

            // Initializing tenth row of Squares on game board 
            if (x >= 91 && x <= 100) {

                squares[x] = new Square(45 + (70 * (9 - row9)), 30, x, false, false, 1);
                row9++;

            }

        }

        // Calling method setLatterLocation from Square class to make certain Squares in to ladders as well. Passing in value of what square the ladder should put user at.
        squares[2].setLatterLocation(true, squares[38].getX(), squares[38].getY());
        squares[7].setLatterLocation(true, squares[14].getX(), squares[14].getY());
        squares[8].setLatterLocation(true, squares[31].getX(), squares[31].getY());
        squares[15].setLatterLocation(true, squares[26].getX(), squares[26].getY());
        squares[21].setLatterLocation(true, squares[42].getX(), squares[42].getY());
        squares[28].setLatterLocation(true, squares[84].getX(), squares[84].getY());
        squares[36].setLatterLocation(true, squares[44].getX(), squares[44].getY());
        squares[51].setLatterLocation(true, squares[67].getX(), squares[67].getY());
        squares[71].setLatterLocation(true, squares[91].getX(), squares[91].getY());
        squares[78].setLatterLocation(true, squares[98].getX(), squares[98].getY());
        squares[87].setLatterLocation(true, squares[94].getX(), squares[94].getY());

        // Calling method setSnakeLocation from Square class to make certain Squares in to snakes as well. Passing in value of what square the snake should put user at.
        squares[16].setSnakeLocation(true, squares[6].getX(), squares[6].getY());
        squares[46].setSnakeLocation(true, squares[25].getX(), squares[25].getY());
        squares[49].setSnakeLocation(true, squares[11].getX(), squares[11].getY());
        squares[62].setSnakeLocation(true, squares[19].getX(), squares[19].getY());
        squares[64].setSnakeLocation(true, squares[60].getX(), squares[60].getY());
        squares[74].setSnakeLocation(true, squares[53].getX(), squares[53].getY());
        squares[89].setSnakeLocation(true, squares[68].getX(), squares[68].getY());
        squares[92].setSnakeLocation(true, squares[88].getX(), squares[88].getY());
        squares[95].setSnakeLocation(true, squares[75].getX(), squares[75].getY());
        squares[99].setSnakeLocation(true, squares[80].getX(), squares[80].getY());
        
        // Setting properties for game board
        board.setPreserveRatio(true);
        board.setFitWidth(700);
        board.setLayoutX(20);
        board.setLayoutY(20);

        // Setting properties for roll button
        rollBtn.setPrefSize(300, 75);
        rollBtn.setText("ROLL");
        rollBtn.setFont(Font.font("Britannic Bold", FontWeight.BOLD, 20));
        rollBtn.setAlignment(Pos.CENTER);

        // Upon clicking, call the roll method
        rollBtn.setOnAction(e -> roll());

        // Setting properties for exit button
        exitBtn.setPrefSize(300, 75);
        exitBtn.setText("EXIT");
        exitBtn.setFont(Font.font("Britannic Bold", FontWeight.BOLD, 20));
        exitBtn.setAlignment(Pos.CENTER);

        // Upon clicking, call the exit method
        exitBtn.setOnAction(e -> exit());

        // Setting properties for label that displays which user is currently playing
        playerTurnLabel.setPrefSize(300, 50);
        playerTurnLabel.setWrapText(true);
        playerTurnLabel.setText(players.get(playerNum).getName() + "'s turn!");
        playerTurnLabel.setFont(Font.font("Britannic Bold", FontWeight.BOLD, 20));
        playerTurnLabel.setAlignment(Pos.CENTER);
        playerTurnLabel.setPadding(new Insets(0, 0, 0, 10));
        playerTurnLabel.setTextFill(Color.RED);

        // Setting properties for Textfield which displays game information
        gameConsole.setPrefSize(300, 100);
        gameConsole.setFont(Font.font("Britannic Bold", FontWeight.BOLD, 17));
        gameConsole.setAlignment(Pos.CENTER);
        gameConsole.setEditable(false);
        

        // Setting properties for ImageView which displays what number the user rolled
        diceDisplay.setImage(new Image("File:dice1.png"));
        diceDisplay.setPreserveRatio(true);
        diceDisplay.setFitWidth(200);

        // Creating VBox layout with Vertical gap of 10
        VBox sideMenu = new VBox(10);
        sideMenu.setAlignment(Pos.CENTER);
        sideMenu.setPadding(new Insets(10, 10, 10, 10));

        // Adding diceDisplay, playerTurnLabel, gameConsole, rollBtn, exitBtn to VBox
        sideMenu.getChildren().addAll(diceDisplay, playerTurnLabel, gameConsole, rollBtn, exitBtn);
        sideMenu.setLayoutX(720);

        // Setting properties and adding components to gameCenter Pane
        gameCenter.setPadding(new Insets(0, 0, 20, 10));
        gameCenter.getChildren().add(board);
        gameCenter.getChildren().add(sideMenu);

        // Setting starting position for player nodes
        for (int x = 0; x < players.size(); x++) {

            players.get(x).getNode().setLayoutX(players.get(x).getX());
            players.get(x).getNode().setLayoutY(players.get(x).getY());
            gameCenter.getChildren().add(players.get(x).getNode());

        }  

        // Setting scene to gameDisplay
        stage.setScene(gameDisplay);

    }


    // Method to allow user to exit program
    private static void exit() {

        // Display alert to confirm if user wants to leave program
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Snakes and Ladders");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to exit?");
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);

        // Get user input
        Optional<ButtonType> result = alert.showAndWait();

        // If user wishes to leave then display alert thanking them for playing and exit program
        if (result.get() == ButtonType.YES) {

            Alert thanks = new Alert(AlertType.INFORMATION);
            thanks.setTitle("Snakes and Ladders");
            thanks.setHeaderText(null);
            thanks.setContentText("Thanks for playing!");
            thanks.showAndWait();
            System.exit(0);

        }

    }


    // Create method to roll when roll button is pressed
    private static void roll() {

        // Get the number rolled from Dice class
        diceNum = dice.roll();

        // Set dice image to what number user got
        diceDisplay.setImage(dice.getDiceImage());
        diceDisplay.setPreserveRatio(true);
        diceDisplay.setFitWidth(200);

        // Disable roll button
        rollBtn.setDisable(true);

        // Tell user what they rolled through gameConsole text field
        gameConsole.setText("You rolled a " + diceNum + "!");

        // Try Catch to catch any exceptions
        try {

            // Add delay using Thread.sleep
            Thread.sleep(1000);

        } catch (InterruptedException e) {

            System.out.println("Exception was found: " + e.getMessage());

        }

        // Call play method
        play();

        // Increase index by 1
        index++;

        // Increase playerNum by 1
        playerNum++;

        // Resetting index back to first player after last player rolls
        if (index > players.size() - 1) {

            index = 0;

        }

        // Add to the current user's roll total using Player class
        players.get(index).setRolls(players.get(index).getRolls() + 1);

    }

    // Declaring int destinationSquare
    static int destinationSquare;

    // Declaring and initializing int currentSquare and counter
    static int currentSquare = 1;
    static int counter = 0;


    // Method to play the user's turn
    public static void play() {

        // Initializing AnimationTimer
        timer = new AnimationTimer() {

            public void handle(long now) {

                // Getting the user's current square
                currentSquare = players.get(index).getCurrentSquare();

                // Calculating where user should be moved
                destinationSquare = players.get(index).getSquareLocation() + diceNum;

                // If user is to be moved to a square greater then 100 then do not move the user and tell them they must win by landing on square 100
                if (destinationSquare > 100) {

                    gameConsole.setText("You must land on 100, not "+ destinationSquare + "!");
                    destinationSquare = currentSquare;

                    // Stop AnimationTimer
                    timer.stop();

                    // Enable roll button
                    rollBtn.setDisable(false);

                    // Call method to change the turn to the next player
                    setName();

                    // Return from method
                    return;

                }

                // If user is moved to where they should land after their roll
                if (players.get(index).getX() == squares[destinationSquare].getX() && players.get(index).getY() == squares[destinationSquare].getY()) {
                    
                    // Stop AnimationTimer
                    timer.stop();

                    // Update user's current location
                    players.get(index).setSquareLocation(destinationSquare);

                    // Call method to check if square is snake or a ladder
                    checkLadder();
                    checkSnake();

                    // Call method to see if user has landed on Square 100 and won the game
                    winner = checkWinner();

                    // If user is the winner then return from method
                    if (winner) {

                        return;

                    }

                    // Call method to change the turn to the next player 
                    setName();

                    // Enable roll button
                    rollBtn.setDisable(false);

                }

                // If user needs to move right on the game board then animate the node
                if (squares[currentSquare + 1].getRowNumber() == 0 && currentSquare != destinationSquare) {
                    
                    // Locate the user's location
                    currentSquare = findCurrentSquare(currentSquare);
                    
                    // Move the user by 7 pixels to the right and update location
                    players.get(index).setX(players.get(index).getX() + 7);
                    players.get(index).getNode().setLayoutX(players.get(index).getX());

                    // Call method to see if user has landed on Square 100 and won the game
                    winner = checkWinner();
                    
                    // If user is the winner then  disable roll method and return from method
                    if (winner) {

                        rollBtn.setDisable(true);
                        return;

                    }

                }
                
                // If user's node reaches the right edge of the gameboard then move the user's node up 1 square
                if (players.get(index).getX() + 45 >= 720 && squares[currentSquare + 1].getRowNumber() == 1 && squares[currentSquare].getY() != squares[destinationSquare].getY()) {
                    
                    // Setting user and user's node location to the next square
                    players.get(index).setY(squares[currentSquare + 1].getY());
                    players.get(index).setX(squares[currentSquare + 1].getX());
                    players.get(index).getNode().setLayoutY(squares[currentSquare + 1].getY());
                    players.get(index).getNode().setLayoutX(squares[currentSquare + 1].getX());
                    
                    // Call currentSquare method to get user's current location
                    currentSquare = findCurrentSquare(currentSquare);

                }

                // If user needs to move left on the game board then animate the node
                if (squares[currentSquare + 1].getRowNumber() == 1 && currentSquare != destinationSquare) {

                    // Call currentSquare method to get user's current location
                    currentSquare = findCurrentSquare(currentSquare);

                    // Move the user by 7 pixels to the left and update location
                    players.get(index).setX(players.get(index).getX() - 7);
                    players.get(index).getNode().setLayoutX(players.get(index).getX());
                    
                    // Call method to see if user has landed on Square 100 and won the game
                    winner = checkWinner();
                    
                    // If user is the winner then  disable roll method and return from method
                    if (winner) {

                        rollBtn.setDisable(true);
                        return;

                    }
                }

                // If user's node reaches the left edge of the game board then move the user's node up 1 square
                if (players.get(index).getX() <= 45 && squares[currentSquare + 1].getRowNumber() == 0 && squares[currentSquare].getY() != squares[destinationSquare].getY()) {
                    
                    // Setting user and user's node location to the next square
                    players.get(index).setY(squares[currentSquare + 1].getY());
                    players.get(index).setX(squares[currentSquare + 1].getX());
                    players.get(index).getNode().setLayoutY(squares[currentSquare + 1].getY());
                    players.get(index).getNode().setLayoutX(squares[currentSquare + 1].getX());
                    
                    // Call currentSquare method to get user's current location
                    currentSquare = findCurrentSquare(currentSquare);

                }

            }

        };

        // Start the timer
        timer.start();

    }

    // Method to find the user's current location 
    public static int findCurrentSquare(int passedSquare) {

        int square = passedSquare;
        
        // Use a linear search to see when the user's coordinates math with one of the squares coordinates
        for (int x = 1; x <= 100; x++) {

            if (players.get(index).getNode().getLayoutX() == squares[x].getX() && players.get(index).getNode().getLayoutY() == squares[x].getY()) {
                
                // Store the index of the square that the user is on
                square = x;

            }

        }

        // Update the user's location
        players.get(index).setCurrentSquare(square);
        
        // Return the index of the square that the user is on
        return square;

    }


    // Method to check if the square that the user has landed on is a ladder
    public static void checkLadder() {

        // If the square's ladder boolean is true then the square is a ladder
        if (squares[players.get(index).getSquareLocation()].checkLadder()) {
           
            // Try Catch to catch any exceptions
            try {
                
                // Set delay using Thread.sleep
                Thread.sleep(500);

            } catch (InterruptedException e) {

                System.out.println("Exception was found: " + e.getMessage());
            }
            
            // Update TextField text to tell user that they landed on a ladder
            gameConsole.setText(gameConsole.getText() + " Up the ladder you go!");
            
            // Change the user's and user's node location to the square which the ladder ends at
            players.get(index).setX(squares[players.get(index).getSquareLocation()].getLadderX());
            players.get(index).setY(squares[players.get(index).getSquareLocation()].getLadderY());
            players.get(index).getNode().setLayoutX(players.get(index).getX());
            players.get(index).getNode().setLayoutY(players.get(index).getY());
            
            // Call method currentSquare to get user's location
            currentSquare = findCurrentSquare(currentSquare);
            
            // Update user's location
            players.get(index).setSquareLocation(currentSquare);
            destinationSquare = currentSquare;

        }

    }


    // Method to check if the square that the user has landed on is a snake
    public static void checkSnake() {
        
        // If the square's snake boolean is true then the square is a snake
        if (squares[players.get(index).getSquareLocation()].checkSnake()) {
            
            // Try Catch to catch any exceptions
            try {

                // Set delay using Thread.sleep
                Thread.sleep(500);

            } catch (InterruptedException e) {

                System.out.println("Exception was found: " + e.getMessage());
            }
            
            // Update TextField text to tell user that they landed on a snake
            gameConsole.setText(gameConsole.getText() + " Snake! Down you go!");
            
            // Change the user's and user's node location to the square which the snake ends at
            players.get(index).setX(squares[players.get(index).getSquareLocation()].getSnakeX());
            players.get(index).setY(squares[players.get(index).getSquareLocation()].getSnakeY());
            players.get(index).getNode().setLayoutX(players.get(index).getX());
            players.get(index).getNode().setLayoutY(players.get(index).getY());
            
            // Call method currentSquare to get user's location
            currentSquare = findCurrentSquare(currentSquare);
            
            // Update user's location
            players.get(index).setSquareLocation(currentSquare);
            destinationSquare = currentSquare;

        }

    }


    // Method to see if user has landed on square 100 and won the game
    public static boolean checkWinner() {

        // Declaring and initializing boolean winnerCheck
        boolean winnerCheck = false;

        // If user is on square 100 and that was their destination
        if (currentSquare == 100 && destinationSquare == 100) {
            
            // Store the index of the user that won
            winnerPlayer = index;
            
            // Stop the timer
            timer.stop();
            
            // Update TextField to display winner message
            gameConsole.setText("Congrats " + players.get(index).getName() + " you win!");
            winnerCheck = true;
            
            // Try Catch to catch any exceptions
            try {

                // Call highScores method to write user score to file
                highScores();

            } catch (Exception e) {
        
                System.out.println("Exception was found: " + e.getMessage());

            }

            // Dispay alert thanking the user for playing and then close program
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Snakes and Ladders");
            alert.setHeaderText(null);
            alert.setContentText("Congrats to " + players.get(index).getName()+ " for your awesome win!\n\nThe game will now end, check the highscores file to see where you rank!\n\nThanks for playing!");
            alert.show();
            alert.setOnHidden(evt -> System.exit(0));
            alert.show();

        }

        // Return boolean
        return winnerCheck;

    }


    // Method to update which player is playing their turn
    public static void setName() {

        // If last player plays then start back at first player
        if (playerNum > players.size() - 1) {

            playerNum = 0;

        }

        // Update label text and text color
        playerTurnLabel.setText(players.get(playerNum).getName() + "'s turn!");
        playerTurnLabel.setTextFill(players.get(playerNum).getPlayerColor());

    }


    // Method to read the names from file
    public static void readNames() throws Exception {

        // Try Catch to catch any exceptions
        try {

            // Declaring and initializing File, FileReader and BufferedReader
            File file = new File("PlayerNames.txt");
            FileReader fReader = new FileReader(file);
            BufferedReader bReader = new BufferedReader(fReader);
            
            // Declaring String line
            String line;
            
            // Read file line by line until the line is null
            while ((line = bReader.readLine()) != null) {
                
                // Add player names to ArrayList
                playerNames.add(line);

            }
            
            // Close the BufferedReader and FileReader
            bReader.close();
            fReader.close();

        }

        catch (IOException e) {

            System.out.println("IOException: " + e.getMessage());

        }

    }


    // Method to read and write high scores to file
    public static void highScores() throws Exception {
        
        // Declaring and initializing ArrayLists to store high score and name and one to store just highscore
        ArrayList<String> highscores = new ArrayList<String>();
        ArrayList<String> highscoresNumbers = new ArrayList<String>();
        
        // Try Catch to catch any exceptions
        try {

            // Declaring and initializing File, FileReader and BufferedReader
            File file = new File("HighScores.txt");
            FileReader fReader = new FileReader(file);
            BufferedReader bReader = new BufferedReader(fReader);
            
            // Declaring String line
            String line;
            
            // Read file line by line until the line is null
            while ((line = bReader.readLine()) != null) {
                
                // Add the line to ArrayList
                highscores.add(line);

            }
            
            // Add the winner of the current game to the ArrayList
            highscores.add(players.get(winnerPlayer).getRolls() + " - " + players.get(winnerPlayer).getName());
            
            // Close the BufferedReader and FileReader
            bReader.close();
            fReader.close();
            
            // Declaring and initializing int highScoreCounter and boolean gate
            int highScoreCounter = 0;
            boolean gate = false;

            // Store the highscore number only of the users in a different ArrayList
            for (int x = 0; x < highscores.size(); x++) {

                // Reset value of highScoreCounter and highScoreNumber
                String highScoreNumber = "";
                highScoreCounter = 0;

                do {

                    gate = false;

                    // If the character is a number then concatanate until the next character is not a number
                    if (Character.isDigit(highscores.get(x).charAt(highScoreCounter))) {

                        highScoreNumber = highScoreNumber.concat((String.valueOf(highscores.get(x).charAt(highScoreCounter))));
                        
                        // Change value of gate to true
                        gate = true;

                    }
                    
                    // Increase value of highScoreCounter by 1
                    highScoreCounter++;

                } while (gate == true);
                
                // Add the number to ArrayList
                highscoresNumbers.add(highScoreNumber);

            }

            // Using Insertion Sort to sort both ArrayLists in ascending order simultaneously
            for (int end = 1; end < highscores.size(); end++) {

                String item = highscoresNumbers.get(end);
                String itemString = highscores.get(end);
                int i = end;

                while (i > 0 && Integer.parseInt(item) < Integer.parseInt(highscoresNumbers.get(i - 1))) {

                    highscoresNumbers.set(i, highscoresNumbers.get(i - 1));
                    highscores.set(i, highscores.get(i - 1));
                    i--;

                }

                highscoresNumbers.set(i, item);
                highscores.set(i, itemString);

            }

            // Declaring and initializing File, FileWriter and BufferedWriter
            File highscoresFile = new File("HighScores.txt");
            FileWriter fWriter = new FileWriter(highscoresFile);
            BufferedWriter bWriter = new BufferedWriter(fWriter);
            
            // Write the sorted high scores to the file
            for (int y = 0; y < highscores.size(); y++) {

                bWriter.write(highscores.get(y));

                if (y != highscores.size() - 1) {
                    bWriter.newLine();
                }

            }
            
            // Close the BufferedWriter and FileWriter
            bWriter.close();
            fWriter.close();

        } catch (IOException e) {

            System.out.println("IOException: " + e.getMessage());

        }

    }


    // Method to run program
    public static void main(String[] args) {
        launch(args);
    }

}
