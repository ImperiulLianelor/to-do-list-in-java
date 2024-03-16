public interface ToDoItemINTERFACE {
    String getDescription();

    void setDescription(String description);

    boolean isCompleted(); // Change the return type to boolean

    void markAsCompleted(boolean completionStatus); // Change the parameter type to boolean

    void setCompleted(boolean completed);
}
