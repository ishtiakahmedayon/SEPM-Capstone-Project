import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class HabitTrackerFrame extends JFrame {
    private final HabitService service;
    private final JTextField nameField = new JTextField(18);
    private final JComboBox<String> frequencyBox = new JComboBox<>(new String[]{"Daily", "Weekly"});
    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"ID", "Habit", "Frequency", "Completed today"}, 0) {
        @Override public boolean isCellEditable(int row, int col) { return false; }
    };
    private final JTable table = new JTable(model);
    private final JLabel summary = new JLabel();

    public HabitTrackerFrame(HabitService service) {
        super("Ovvasjatra — Habit Tracker");
        this.service = service;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 420);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel heading = new JLabel("Ovvasjatra | Build consistency, one day at a time", SwingConstants.CENTER);
        heading.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        add(heading, BorderLayout.NORTH);

        JPanel input = new JPanel(new FlowLayout(FlowLayout.LEFT));
        input.add(new JLabel("Habit:")); input.add(nameField);
        input.add(frequencyBox);
        JButton addButton = new JButton("Add Habit");
        input.add(addButton);
        JButton doneButton = new JButton("Toggle Completion");
        input.add(doneButton);
        add(input, BorderLayout.SOUTH);

        add(new JScrollPane(table), BorderLayout.CENTER);
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        footer.add(summary);
        add(footer, BorderLayout.WEST);

        addButton.addActionListener(e -> {
            try {
                service.addHabit(nameField.getText(), (String) frequencyBox.getSelectedItem());
                nameField.setText(""); refresh();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Input needed", JOptionPane.WARNING_MESSAGE);
            } catch (IllegalStateException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Save error", JOptionPane.ERROR_MESSAGE);
            }
        });
        doneButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Select a habit first."); return;
            }
            int id = (int) model.getValueAt(row, 0);
            try { service.toggleCompletion(id, LocalDate.now()); refresh(); }
            catch (IllegalStateException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Save error", JOptionPane.ERROR_MESSAGE);
            }
        });
        refresh();
    }

    private void refresh() {
        model.setRowCount(0);
        List<Habit> habits = service.getActiveHabits();
        LocalDate today = LocalDate.now();
        for (Habit h : habits)
            model.addRow(new Object[]{h.getId(), h.getName(), h.getFrequency(),
                    service.isComplete(h.getId(), today) ? "Yes" : "No"});
        summary.setText(service.getSummary(today));
    }
}
