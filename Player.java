package application;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Player {

    // Declaring double to store X and Y of player
    public double xLocation;
    public double yLocation;

    // Declaring int to store location of player
    public int squareLocation;

    // Declaring String to store name of player
    public String playerName;

    // Declaring ImageView to store node of player
    public ImageView playerNode;

    // Declaring int to store number that the player rolled
    public int rolledNum;

    // Declaring int to store the current location of player
    public int currentSquare;

    // Declaring Color to store the color of the node of the player
    public Color playerColor;

    // Declaring int to store the total number of rolls the player has done
    public int playerRolls;

    // Constructor to assign value to Player object
    Player (double x, double y, int square, String name, ImageView playerImageView, int diceNum, Color color, int roll) {

        xLocation = x;
        yLocation = y;
        squareLocation = square;
        playerName = name;
        playerNode = playerImageView;
        rolledNum = diceNum;
        currentSquare = 1;
        playerColor = color;
        playerRolls = roll;

    }

    // Method to get X location of player
    public double getX() {

        return xLocation;

    }

    // Method to get Y location of player
    public double getY() {

        return yLocation;

    }

    // Method to get location of player
    public int getSquareLocation() {

        return squareLocation;

    }

    // Method to get node of player
    public ImageView getNode() {

        return playerNode;

    }

    // Method to get name of player
    public String getName() {

        return playerName;

    }

    // Method to set X location of player
    public void setX(double x) {

        xLocation = x;

    }

    // Method to set Y location of player
    public void setY(double y) {

        yLocation = y;

    }

    // Method to set number of rolls for player
    public void setRolledNum(int diceNum) {

        rolledNum = diceNum;

    }

    // Method to set name of player
    public void setName(String name) {

        playerName = name;

    }

    // Method to set square location of player
    public void setSquareLocation(int square) {

        squareLocation = square;

    }

    // Method to get current location of player
    public int getCurrentSquare() {

        return currentSquare;

    }

    // Method to set current location of player
    public void setCurrentSquare(int current) {

        currentSquare = current;

    }

    // Method to get color of the node of the player
    public Color getPlayerColor() {

        return playerColor;

    }

    // Method to get total number of rolls of the player
    public int getRolls() {

        return playerRolls;

    }
    
    // Method to set total number of rolls of the player
    public void setRolls(int roll) {

        playerRolls = roll;

    }

}
