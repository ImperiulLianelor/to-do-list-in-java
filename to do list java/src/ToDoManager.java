import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class ToDoManager {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Establish a database connection
        Connection connection = connectToDatabase();

        // Create ToDoList object with the database connection
        ToDoList toDoList = new ToDoList(connection);

        Properties config = new Properties();
        try {
            config.load(new FileInputStream("config.properties"));
        } catch (IOException e) {
            System.err.println("Error loading config: " + e.getMessage());
        }

        boolean exit = false;
        while (!exit) {
            System.out.println("\n1. Add Task\n2. Remove Task\n3. Display Tasks\n4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter task description: ");
                    String description = scanner.nextLine();
                    if (toDoList.isValidDescription(description)) {
                        toDoList.addTask(description);
                        System.out.println("Task added successfully.");
                        break;
                    } else {
                        System.out.println("Invalid description. Please provide a valid description.");
                        break;
                    }
                case 2:
                    System.out.print("Enter task index to remove: ");
                    int index = scanner.nextInt();
                    scanner.nextLine();
                    toDoList.removeTask(index - 1);
                    break;
                case 3:
                    if (toDoList.isEmpty()) {
                        System.out.println("No tasks in the list.");
                    } else {
                        toDoList.displayTasks();

                        System.out.print("Choose a task index to update (0 to go back): ");
                        int taskIndex = scanner.nextInt();
                        scanner.nextLine();

                        if (taskIndex == 0) {
                            break;
                        }

                        ToDoItem selectedTask = toDoList.getTask(taskIndex - 1);
                        if (selectedTask != null) {
                            System.out.println("Task selected: " + selectedTask.getDescription());
                            System.out.print("Did you complete this task? (Y/N): ");
                            String answer = scanner.nextLine().trim().toLowerCase();

                            int completionStatus = 0; // Default to false (not completed)
                            if (answer.equals("y")) {
                                completionStatus = 1; // Set to true if 'Y' (completed)
                            }

                            // Update the task completion status in the database
                            toDoList.markTaskAsCompletedInDatabase(selectedTask.getId(), completionStatus == 1);
                            System.out.println("Task marked as completed in the database.");
                        } else {
                            System.out.println("Invalid index. Task not found.");
                        }
                    }
                    break;

                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        // Close the database connection
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing the database connection: " + e.getMessage());
        }

        scanner.close();
    }

    public static Connection connectToDatabase() {
        try {
            String jdbcUrl = "jdbc:mysql://localhost:3306/test";
            String user = "boss";
            String password = "123";
            return DriverManager.getConnection(jdbcUrl, user, password);
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
            throw new RuntimeException("Failed to connect to the database.");
        }
    }
}
