import java.util.Scanner;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ToDoManager {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ToDoList toDoList = new ToDoList();
        Properties config = new Properties();
        try {
            config.load(new FileInputStream("config.properties"));
        }catch (IOException e){
            System.err.println("Error loading config: "+ e.getMessage());
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
                    if(toDoList.isValidDescription(description))
                    {
                    toDoList.addTask(description);
                    System.out.println("Task added successfully.");
                    break;
                    }
                    else{
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
                    if(toDoList.isEmpty()){
                        System.out.println("No tasks in the list.");
                    }else
                    {
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
                        if (answer.equals("y")) {
                            selectedTask.markasCompleted();
                            System.out.println("Task marked as completed.");
                        }

                     else {
                        System.out.println("Invalid index. Task not found.");
                    }}}
                    break;

                    case 4:
                    exit = true;
                    break;


                default:
                    System.out.println("Invalid choice. Please try again.");


            }
        }
        scanner.close();

    }

}