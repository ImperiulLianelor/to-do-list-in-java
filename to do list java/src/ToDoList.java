import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.Properties;

public class ToDoList {

    private String saveFileName;
    private String loadFileName;


    public boolean isValidDescription(String description) {
    return description != null && !description.trim().isEmpty();
}
public boolean isValidIndex(int index) {
        return index >= 0 && index < items.size();
    }
    private List<ToDoItem> items;

    public ToDoList() {
        items = new ArrayList<>();
        Properties config = new Properties();
        try{
            config.load(new FileInputStream("Config.properties"));
            saveFileName = config.getProperty("saveFileName", "default_save_file.csv");
            loadFileName = config.getProperty("loadFileName", "default_load_file.csv");

            loadFromFile(saveFileName);
        }catch(IOException e){
            System.err.println("Error loading config: "+ e.getMessage());
        }


    }
    public void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(saveFileName))) {
            for (ToDoItem item : items) {
                writer.println(item.getDescription() + "," + item.isCompleted());
            }
            System.out.println("To-Do List saved successfully to " + saveFileName);
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
            // You might want to log the exception or take corrective actions
        }
    }
    public void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(saveFileName))) {
            String line;
            items.clear(); // Clear existing list before loading
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String description = parts[0];
                    boolean completed = Boolean.parseBoolean(parts[1]);
                    ToDoItem item = new ToDoItem(description);
                    item.setCompleted(completed);
                    items.add(item);
                }
            }
            System.out.println("To-Do List loaded successfully from " + saveFileName);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
            // Handle the absence of the file (create a new file, prompt the user, etc.)
        } catch (IOException e) {
            System.out.println("IOException occurred: " + e.getMessage());
            // Handle other I/O related issues
        }
    }


    public void addTask(String description) {
        ToDoItem newItem = new ToDoItem(description);
        items.add(newItem);
        saveToFile();
    }


    public void removeTask(int index) {
        if (index >= 0 && index < items.size()) {
            items.remove(index);
        } else {
            System.out.println("Invalid index. Task not found.");
        }
        saveToFile();
    }


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
        saveToFile();
    }
    public ToDoItem getTask(int index) {
        if (index >= 0 && index < items.size()) {
            return items.get(index);
        }
        return null;
    }
    public void markTaskAsCompleted(int index){
        if(items.isEmpty()){
            System.out.println("No tasks in the list.");
                    return;

        }
        if(index >= 0 && index < items.size()){
            ToDoItem item = items.get(index);
            item.markasCompleted();
            System.out.println("Task marked as completed.");
            saveToFile();
        }
        else{
            System.out.println("Invalid index. Task not found");
        }

    }

    public void saveToFile(String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (ToDoItem item : items) {
                writer.println(item.getDescription() + "," + item.isCompleted());
            }
            System.out.println("To-Do List saved successfully to " + fileName);
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }
    public void loadFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            items.clear(); // Clear existing list before loading
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String description = parts[0];
                    boolean completed = Boolean.parseBoolean(parts[1]);
                    ToDoItem item = new ToDoItem(description);
                    item.setCompleted(completed);
                    items.add(item);
                }
            }
            System.out.println("To-Do List loaded successfully from " + fileName);
        } catch (IOException e) {
            System.out.println("Error loading from file: " + e.getMessage());
        }
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}



