import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ToDoList implements ToDoListINTERFACE {

    private Connection connection;
    private List<ToDoItem> items;

    public ToDoList(Connection connection) {
        this.connection = connection;
        items = new ArrayList<>();
        createTable();
        loadFromDatabase();
    }

    private void createTable() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS tasks (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY," +
                        "description VARCHAR(255) NOT NULL," +
                        "completed BOOLEAN NOT NULL)")) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error creating 'tasks' table: " + e.getMessage());
        }
    }

    private void loadFromDatabase() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM tasks");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String description = resultSet.getString("description");
                boolean completionStatus = resultSet.getBoolean("completed");
                ToDoItem item = new ToDoItem(id, description);
                item.markAsCompleted(completionStatus); // Use the updated method
                items.add(item);
            }
        } catch (SQLException e) {
            System.err.println("Error loading tasks from the database: " + e.getMessage());
        }
    }
    public void addTask(String description) {
        if (!isValidDescription(description)) {
            System.out.println("Invalid task description: " + description);
            return;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO tasks (description, completed) VALUES (?, ?)")) {

            preparedStatement.setString(1, description);
            preparedStatement.setBoolean(2, false); // Assuming a new task is not completed

            preparedStatement.executeUpdate();

            // Refresh the items list from the database
            items.clear();
            loadFromDatabase();

            System.out.println("Task added successfully!");

        } catch (SQLException e) {
            System.err.println("Error adding task to the database: " + e.getMessage());
        }
    }
    public void removeTask(int index) {
        if (!isValidIndex(index)) {
            try {
                throw new TaskNotFoundException("Task not found at index: " + index);
            } catch (TaskNotFoundException e) {
                System.out.println("Caught a TaskNotFoundException: " + e.getMessage());
            }
        }

        if (index >= 0 && index < items.size()) {
            int taskId = items.get(index).getId();  // Retrieve the task ID
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM tasks WHERE id = ?")) {
                preparedStatement.setInt(1, taskId);
                preparedStatement.executeUpdate();
                System.out.println("Task removed successfully from the database.");
            } catch (SQLException e) {
                System.err.println("Error removing task from the database: " + e.getMessage());
            }

            // Refresh the items list from the database
            items.clear();
            loadFromDatabase();
        } else {
            System.out.println("Invalid index. Task not found.");
        }
    }
    public boolean isValidDescription(String description) {
        return description != null && !description.trim().isEmpty();
    }
    public boolean isEmpty() {
        return items.isEmpty();
    }
    public ToDoItem getTask(int index) {
        if (index >= 0 && index < items.size()) {
            return items.get(index);
        }
        return null;
    }
    private boolean isValidIndex(int index) {
        return index >= 0 && index < items.size();
    }
    @Override
    public void displayTasks() {
        if (items.isEmpty()) {
            System.out.println("No tasks in the list.");
        } else {
            System.out.println("Tasks:");
            for (int i = 0; i < items.size(); i++) {
                ToDoItem item = items.get(i);
                System.out.println((i + 1) + ". " + item.getDescription() + " - Completed: " + item.isCompleted());
            }
        }
    }

    public void markTaskAsCompletedInDatabase(int taskId, boolean completionStatus) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE tasks SET completed = ? WHERE id = ?")) {

            preparedStatement.setInt(1, completionStatus ? 1 : 0);
            preparedStatement.setInt(2, taskId);

            preparedStatement.executeUpdate();

            // Refresh the items list from the database
            items.clear();
            loadFromDatabase();



            System.out.println("Task marked as completed in the database.");

        } catch (SQLException e) {
            System.err.println("Error updating task completion status in the database: " + e.getMessage());
        }
    }



}