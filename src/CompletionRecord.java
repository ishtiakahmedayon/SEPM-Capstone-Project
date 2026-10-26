import java.io.Serializable;
import java.time.LocalDate;

public class CompletionRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    private final int habitId;
    private final LocalDate date;
    private boolean complete;

    public CompletionRecord(int habitId, LocalDate date, boolean complete) {
        this.habitId = habitId;
        this.date = date;
        this.complete = complete;
    }
    public int getHabitId() { return habitId; }
    public LocalDate getDate() { return date; }
    public boolean isComplete() { return complete; }
    public void toggle() { complete = !complete; }
    public void setComplete(boolean complete) { this.complete = complete; }
}
