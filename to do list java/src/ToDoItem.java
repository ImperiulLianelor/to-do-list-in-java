public class ToDoItem implements ToDoItemINTERFACE {
    private int id; // Unique identifier
    private String description;
    private int completed;

    public ToDoItem(int id, String description) {
        this.id = id;
        this.description = description;
        this.completed = 0;
    }

    public int getId() {
        return id;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean isCompleted() {
        return completed != 0; // Return true if completed is not equal to 0
    }

    public void markAsCompleted(boolean completionStatus) {
        completed = completionStatus ? 1 : 0;
    }

    @Override
    public void setCompleted(boolean completed) {
        this.completed = completed ? 1 : 0;
    }
}
