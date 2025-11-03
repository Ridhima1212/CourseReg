package university;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class CSV {
    public static List<String[]> read(Path p) {
        List<String[]> rows = new ArrayList<>();
        if (!Files.exists(p)) return rows;
        try (BufferedReader br = Files.newBufferedReader(p)) {
            String line;
            while ((line = br.readLine()) != null) rows.add(line.split(",", -1));
        } catch (IOException e) { System.out.println("CSV read error: " + e.getMessage()); }
        return rows;
    }
    public static void writeAll(Path p, List<String[]> rows) {
        try (BufferedWriter bw = Files.newBufferedWriter(p, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            for (String[] r : rows) { bw.write(String.join(",", r)); bw.newLine(); }
        } catch (IOException e) { System.out.println("CSV write error: " + e.getMessage()); }
    }
    public static void append(Path p, String[] row) {
        try (BufferedWriter bw = Files.newBufferedWriter(p, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            bw.write(String.join(",", row)); bw.newLine();
        } catch (IOException e) { System.out.println("CSV append error: " + e.getMessage()); }
    }
}