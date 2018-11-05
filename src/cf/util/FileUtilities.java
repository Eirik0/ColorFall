package cf.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class FileUtilities {
    public static List<String> fileToList(File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            List<String> lines = new ArrayList<>();

            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

            return lines;
        }
    }

    public static <T> void toFile(File file, List<T> ts, Function<T, String> toString) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (T t : ts) {
                writer.write(toString.apply(t));
            }
        }
    }

    public static <T> T loadFromFile(String filePath, Function<List<String>, T> onSuccess, Supplier<T> onFailure) {
        File settingFile = new File(filePath);
        if (settingFile.exists()) {
            try {
                return onSuccess.apply(FileUtilities.fileToList(settingFile));
            } catch (IOException e) {
            }
        }
        return onFailure.get();
    }

    public static void initFromFile(String filePath, Consumer<List<String>> onSuccess, Runnable onFailure) {
        File settingFile = new File(filePath);
        if (settingFile.exists()) {
            try {
                onSuccess.accept(FileUtilities.fileToList(settingFile));
                return;
            } catch (IOException e) {
            }
        }
        onFailure.run();
    }
}
