package application;

public class Square {

    // Declaring doubles to store xLocation and yLocation of square
    public double xLocation;
    public double yLocation;

    // Declaring int to store the index of the square
    public int squareNumber;

    // Declaring booleans to store whether square is ladder or snake
    public boolean isladder;
    public boolean isSnake;

    // Declaring int to store the row number of the square
    public int rowNumber;

    // Declaring double to store the end of the ladder if square is a ladder
    public double ladderX;
    public double ladderY;

    // Declaring double to store the end of the snake if square is a snake
    public double snakeX;
    public double snakeY;

    // Constructor to initialize xLocation, yLocation, squareNumber, isladder, isSnake and rowNumber
    Square(double x, double y, int square, boolean ladder, boolean snake, int row) {

        xLocation = x;
        yLocation = y;
        squareNumber = square;
        isladder = ladder;
        isSnake = snake;
        rowNumber = row;

    }

    // Method to get X location of square
    public double getX() {

        return xLocation;

    }
    
    // Method to get Y location of square
    public double getY() {

        return yLocation;

    }
    
    // Method to get index of square
    public int getSquareNum() {

        return squareNumber;

    }
   
    // Method to get ladder boolean of square
    public boolean getLadder() {

        return isladder;

    }
    
    // Method to get snake boolean of square
    public boolean getSnake() {

        return isSnake;

    }
    
    // Method to set X location of square
    public void setX(double x) {

        xLocation = x;

    }
    
    // Method to set Y location of square
    public void setY(double y) {

        yLocation = y;

    }
    
    // Method to set index of square
    public void setSquareNum(int square) {

        squareNumber = square;

    }
    
    // Method to set ladder properties of square
    public void setLatterLocation(boolean ladder, double setX, double setY) {

        isladder = ladder;
        ladderX = setX;
        ladderY = setY;

    }
    
    // Method to set snake properties of square
    public void setSnakeLocation(boolean snake, double setX, double setY) {

        isSnake = snake;
        snakeX = setX;
        snakeY = setY;

    }
    
    // Method to get X of ladder end of square
    public double getLadderX() {

        return ladderX;

    }
    
    // Method to get Y of ladder end of square
    public double getLadderY() {

        return ladderY;

    }
    
    // Method to get X of snake end of square
    public double getSnakeX() {

        return snakeX;

    }
    
    // Method to get Y of snake end of square
    public double getSnakeY() {

        return snakeY;

    }
    
    // Method to get ladder boolean of square
    public boolean checkLadder() {

        return isladder;

    }
    
    // Method to get snake boolean of square
    public boolean checkSnake() {

        return isSnake;

    }
    
    // Method to get row number of square
    public int getRowNumber() {

        return rowNumber;

    }

}
