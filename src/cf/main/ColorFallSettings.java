package cf.main;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gt.io.FileUtilities;

public class ColorFallSettings {
    public final static String COLUMN_TYE_SETTING = "Column Type";
    public final static String COLUMN_TYE_CIRCLES = "Circles";
    public final static String COLUMN_TYE_SQUARES = "Squares";

    private static ColorFallSettings instance;

    private final Map<String, String> settingsMap = new HashMap<>();

    private ColorFallSettings() {
        settingsMap.put(COLUMN_TYE_SETTING, COLUMN_TYE_SQUARES);
    }

    private ColorFallSettings(List<String> settings) {
        for (String string : settings) {
            String[] keyValue = string.split(",");
            if (keyValue.length == 2) {
                settingsMap.put(keyValue[0], keyValue[1]);
            }
        }
    }

    public static void loadSettings() {
        instance = FileUtilities.loadFromFile(ColorFall.SETTINGS_FILE_PATH, list -> new ColorFallSettings(list), () -> new ColorFallSettings());
        ColorFall.initializeSettings(instance.settingsMap);
    }

    private static void saveSettings() {
        ColorFall.ensureColorFallDirectoryExists();
        try {
            FileUtilities.collectionToFile(new File(ColorFall.SETTINGS_FILE_PATH), instance.settingsMap.entrySet(),
                    entry -> entry.getKey() + "," + entry.getValue() + System.lineSeparator());
        } catch (IOException e) {
        }
    }

    public static String getSetting(String settingName, String defaultValue) {
        return instance.settingsMap.getOrDefault(settingName, defaultValue);
    }

    public static void setSetting(String settingName, String settingValue) {
        instance.settingsMap.put(settingName, settingValue);
        saveSettings();
        ColorFall.initializeSettings(instance.settingsMap);
    }
}
