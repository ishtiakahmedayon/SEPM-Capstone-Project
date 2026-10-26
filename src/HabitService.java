import java.time.LocalDate;
import java.util.*;

public class HabitService {
    private final HabitRepository repository;
    private final List<Habit> habits = new ArrayList<>();
    private final List<CompletionRecord> records = new ArrayList<>();
    private int nextId = 1;

    public HabitService(HabitRepository repository) {
        this.repository = repository;
        List<Object> data = repository.loadData();
        for (Object item : data) {
            if (item instanceof Habit) {
                Habit h = (Habit) item; habits.add(h); nextId = Math.max(nextId, h.getId() + 1);
            } else if (item instanceof CompletionRecord) records.add((CompletionRecord) item);
        }
    }
    public List<Habit> getActiveHabits() {
        List<Habit> result = new ArrayList<>();
        for (Habit h : habits) if (h.isActive()) result.add(h);
        return result;
    }
    public void addHabit(String name, String frequency) {
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Please enter a habit name.");
        habits.add(new Habit(nextId++, name.trim(), "", frequency));
        save();
    }
    public void toggleCompletion(int habitId, LocalDate date) {
        for (CompletionRecord r : records) {
            if (r.getHabitId() == habitId && r.getDate().equals(date)) {
                r.toggle(); save(); return;
            }
        }
        records.add(new CompletionRecord(habitId, date, true));
        save();
    }
    public boolean isComplete(int habitId, LocalDate date) {
        for (CompletionRecord r : records)
            if (r.getHabitId() == habitId && r.getDate().equals(date)) return r.isComplete();
        return false;
    }
    public String getSummary(LocalDate date) {
        int total = getActiveHabits().size(), done = 0;
        for (Habit h : getActiveHabits()) if (isComplete(h.getId(), date)) done++;
        int percent = total == 0 ? 0 : (int)Math.round(done * 100.0 / total);
        return String.format("Today: %d/%d completed (%d%%)", done, total, percent);
    }
    private void save() {
        List<Object> data = new ArrayList<>();
        data.addAll(habits); data.addAll(records);
        repository.saveData(data);
    }
}
