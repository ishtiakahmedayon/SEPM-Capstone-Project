import java.io.*;
import java.nio.file.*;
import java.util.*;

public class HabitRepository {
    private final Path dataFile = Paths.get(System.getProperty("user.home"), ".ovvasjatra.dat");

    @SuppressWarnings("unchecked")
    public synchronized List<Object> loadData() {
        if (!Files.exists(dataFile)) return new ArrayList<>();
        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(dataFile))) {
            return (List<Object>) in.readObject();
        } catch (IOException | ClassNotFoundException | ClassCastException ex) {
            return new ArrayList<>();
        }
    }

    public synchronized void saveData(List<Object> data) {
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(dataFile))) {
            out.writeObject(new ArrayList<>(data));
        } catch (IOException ex) {
            throw new IllegalStateException("Could not save local data: " + ex.getMessage(), ex);
        }
    }
}
