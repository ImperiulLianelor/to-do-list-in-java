public class ToDoItem {
    private String description;
    private boolean completed;

    public ToDoItem(String description) {
        this.description = description;
        this.completed = false;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public boolean isCompleted() {
        return completed;
    }

    public void markasCompleted() {
        completed=true;
    }
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}