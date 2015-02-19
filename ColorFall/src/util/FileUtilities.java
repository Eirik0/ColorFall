package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
}
