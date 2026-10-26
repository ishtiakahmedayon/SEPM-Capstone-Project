import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HabitRepository repository = new HabitRepository();
            HabitService service = new HabitService(repository);
            new HabitTrackerFrame(service).setVisible(true);
        });
    }
}
