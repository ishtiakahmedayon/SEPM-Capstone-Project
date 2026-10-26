import java.io.Serializable;

public class Habit implements Serializable {
    private static final long serialVersionUID = 1L;
    private final int id;
    private String name;
    private String description;
    private String frequency;
    private boolean active;

    public Habit(int id, String name, String description, String frequency) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.frequency = frequency;
        this.active = true;
    }
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getFrequency() { return frequency; }
    public boolean isActive() { return active; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setFrequency(String frequency) { this.frequency = frequency; }
    public void markActive(boolean active) { this.active = active; }
    @Override public String toString() { return name + " (" + frequency + ")"; }
}
