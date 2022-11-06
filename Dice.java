package application;

import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Dice {

    // Declaring int DiceNum, Image diceImage and Random rnd
    public int diceNum;
    public Image diceImage;
    public Random rnd;

    // Creating constructor
    Dice() {
        
        // Initializing diceNum, diceImage and rnd
        diceNum = 0;
        diceImage = null;
        rnd = new Random();

    }

    // Method to generate random number from range 1 - 6 and return it 
    public int roll() {

        diceNum = rnd.nextInt(6) + 1;
        
        return diceNum;

    }

    // Method to update the image of the dice to the number that the user rolled and return the updated image
    public Image getDiceImage() {

        diceImage = new Image("dice" + diceNum + ".png");

        return diceImage;

    }
}
