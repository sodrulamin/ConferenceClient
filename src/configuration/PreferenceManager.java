package configuration;

import sample.Main;

import java.util.prefs.Preferences;

public class PreferenceManager {
    private static Preferences preferences = null;
    private PreferenceManager() {
    }
    public static Preferences getInstance(){
        if(preferences == null)
            preferences = Preferences.userRoot().node(Main.class.getName());
        return preferences;
    }

}
