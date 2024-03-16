public class ToDoItemTest {
    public static void main(String[] args) {
        testConstructor();
        testGetId();
        testGetDescription();
        testSetDescription();
        testIsCompleted();
        testMarkAsCompleted();
        testSetCompleted();
    }

    private static void testConstructor() {
        ToDoItem item = new ToDoItem(1, "Test Task");
        System.out.println("Test Constructor:");
        System.out.println("Expected Id: 1, Actual Id: " + item.getId());
        System.out.println("Expected Description: Test Task, Actual Description: " + item.getDescription());
        System.out.println("Expected Completed: false, Actual Completed: " + item.isCompleted());
    }

    private static void testGetId() {
        ToDoItem item = new ToDoItem(1, "Test Task");
        System.out.println("Test GetId:");
        System.out.println("Expected Id: 1, Actual Id: " + item.getId());
    }

    private static void testGetDescription() {
        ToDoItem item = new ToDoItem(1, "Test Task");
        System.out.println("Test GetDescription:");
        System.out.println("Expected Description: Test Task, Actual Description: " + item.getDescription());
    }

    private static void testSetDescription() {
        ToDoItem item = new ToDoItem(1, "Test Task");
        item.setDescription("Updated Task");
        System.out.println("Test SetDescription:");
        System.out.println("Expected Description: Updated Task, Actual Description: " + item.getDescription());
    }

    private static void testIsCompleted() {
        ToDoItem item = new ToDoItem(1, "Test Task");
        System.out.println("Test IsCompleted:");
        System.out.println("Expected Completed: false, Actual Completed: " + item.isCompleted());
    }

    private static void testMarkAsCompleted() {
        ToDoItem item = new ToDoItem(1, "Test Task");
        item.markAsCompleted(true);
        System.out.println("Test MarkAsCompleted:");
        System.out.println("Expected Completed: true, Actual Completed: " + item.isCompleted());
    }

    private static void testSetCompleted() {
        ToDoItem item = new ToDoItem(1, "Test Task");
        item.setCompleted(true);
        System.out.println("Test SetCompleted:");
        System.out.println("Expected Completed: true, Actual Completed: " + item.isCompleted());
    }
}
