//Written by Dylan Lesesne

import java.io.*;
import java.util.*;

// Main class to run the Robot Command Simulator
public class RobotSimulator {
    public static void main(String[] args) {
        // Initialize robot and board
        Robot robot = new Robot();
        Board board = new Board();
        
        // Get filenames from user
        Scanner input = new Scanner(System.in);
        System.out.print("Enter board file name: ");
        String boardFile = input.nextLine();
        System.out.print("Enter command file name: ");
        String commandFile = input.nextLine();
        
        // Load board and commands
        if (board.loadBoardFromFile(boardFile) && robot.loadCommandsFromFile(commandFile)) {
            robot.runSimulation(board);
        } else {
            System.out.println("Error loading files. Please check filenames and try again.");
        }
        
        input.close();
    }
}

// Class to represent the board with obstacles
class Board {
    private char[][] grid;
    private final int SIZE = 10;  // Assuming 10x10 board

    public Board() {
        grid = new char[SIZE][SIZE];
    }

    // Method to load board from file
    public boolean loadBoardFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int row = 0;
            while ((line = reader.readLine()) != null && row < SIZE) {
                for (int col = 0; col < SIZE; col++) {
                    grid[row][col] = line.charAt(col);
                }
                row++;
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error reading board file: " + e.getMessage());
            return false;
        }
    }

    // Method to display the board with the robot's position
    public void displayBoard(int robotX, int robotY) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (row == robotY && col == robotX) {
                    System.out.print("R "); // Representing the robot as 'R'
                } else {
                    System.out.print(grid[row][col] + " ");
                }
            }
            System.out.println();
        }
    }

    // Check if a position has an obstacle
    public boolean isObstacle(int x, int y) {
        return grid[y][x] == 'X';
    }

    // Check if a position is within bounds
    public boolean inBounds(int x, int y) {
        return x >= 0 && x < SIZE && y >= 0 && y < SIZE;
    }
}

// Class to represent the Robot
class Robot {
    private int x, y;  // Robot's position on the board
    private GenericQueue<String> commands;

    public Robot() {
        x = 0;  // Starting at top-left corner
        y = 0;
        commands = new GenericQueue<>();
    }

    // Load commands from a file into the queue
    public boolean loadCommandsFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (isValidCommand(line)) {
                    commands.enqueue(line);
                }
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error reading command file: " + e.getMessage());
            return false;
        }
    }

    // Check if command is valid
    private boolean isValidCommand(String command) {
        return command.equals("Move Up") || command.equals("Move Down") || command.equals("Move Left") || command.equals("Move Right");
    }

    // Execute each command and display the board
    public void runSimulation(Board board) {
        while (!commands.isEmpty()) {
            String command = commands.dequeue();
            System.out.println("Executing command: " + command);

            // Update position based on command
            int newX = x, newY = y;
            switch (command) {
                case "Move Up":
                    newY--;
                    break;
                case "Move Down":
                    newY++;
                    break;
                case "Move Left":
                    newX--;
                    break;
                case "Move Right":
                    newX++;
                    break;
            }

            // Check for bounds and obstacles
            if (!board.inBounds(newX, newY)) {
                System.out.println("CRASH: Out of bounds!");
                break;
            } else if (board.isObstacle(newX, newY)) {
                System.out.println("CRASH: Hit an obstacle!");
                break;
            } else {
                x = newX;
                y = newY;
                board.displayBoard(x, y);
            }
        }

        // Prompt for restart or quit
        System.out.print("Simulation complete. Enter 'R' to restart or any other key to quit: ");
        Scanner scanner = new Scanner(System.in);
        if (scanner.nextLine().equalsIgnoreCase("R")) {
            x = 0; // Reset position
            y = 0;
            runSimulation(board);
        }
    }
}

// Generic Queue class implementation
class GenericQueue<T> {
    private LinkedList<T> queue;

    public GenericQueue() {
        queue = new LinkedList<>();
    }

    public void enqueue(T item) {
        queue.addLast(item);
    }

    public T dequeue() {
        return queue.isEmpty() ? null : queue.removeFirst();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
