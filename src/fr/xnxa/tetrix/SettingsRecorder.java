package fr.xnxa.tetrix;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javafx.scene.paint.Color;

/**
 * this class is able to store and retrieve all settings for Tetrix app. These
 * settings can be edited by the user in the settings view.
 *
 * Currently, the parameters are as follows:
 *
 * - sound during play : boolean value (default to true)
 * - dropping speed increases when level increases : true/false (default to false)
 * - custom colors for tetriminos
 *
 */
final class SettingsRecorder {

	private String path_to_settings_file = "fr.xnxa.tetrix.";
	private String fileName = "settings";

	static public final String SOUND_SETTING = "music";
	static public final String INCREASE_SPEED_SETTING = "increase_speed";
	static public final String I_COLOR_SETTING = "i_color";
	static public final String J_COLOR_SETTING = "j_color";
	static public final String L_COLOR_SETTING = "l_color";
	static public final String T_COLOR_SETTING = "t_color";
	static public final String S_COLOR_SETTING = "s_color";
	static public final String Z_COLOR_SETTING = "z_color";
	static public final String O_COLOR_SETTING = "o_color";

	static public final String I_DEFAULT_COLOR_SETTING = "#00FFFF"; // CYAN
	static public final String J_DEFAULT_COLOR_SETTING = "#FFA500"; // ORANGE
	static public final String L_DEFAULT_COLOR_SETTING = "#0000FF"; // BLUE
	static public final String T_DEFAULT_COLOR_SETTING = "#008000"; // GREEN
	static public final String S_DEFAULT_COLOR_SETTING = "#FFFF00"; // YELLOW
	static public final String Z_DEFAULT_COLOR_SETTING = "#FF0000"; // RED
	static public final String O_DEFAULT_COLOR_SETTING = "#800080"; // PURPLE

	/**
	 * mapping color picker names with the corresponding property name.
	 */
	static private final Map<String, String> correspondance = new HashMap<>();
	static {
		correspondance.put("cp_I", I_COLOR_SETTING);
		correspondance.put("cp_J", J_COLOR_SETTING);
		correspondance.put("cp_L", L_COLOR_SETTING);
		correspondance.put("cp_T", T_COLOR_SETTING);
		correspondance.put("cp_S", S_COLOR_SETTING);
		correspondance.put("cp_Z", Z_COLOR_SETTING);
		correspondance.put("cp_O", O_COLOR_SETTING);
	}

	private Properties appSettings = null;

	/**
	 * Checks if a setting name exists in this store
	 *
	 * @param setting_name the name of a setting
	 * @return true / false (this name is unknown)
	 */
	public boolean exists_setting(String setting_name) {
		if (appSettings == null) {
			return false;
		} else {
			Set<Object> elts = appSettings.keySet();
			return elts.contains(setting_name);
		}
	}

	/**
	 * Creates a new settings recorder.
	 *
	 * @throws IOException if the properties file cannot be created.
	 *
	 */
	public SettingsRecorder() throws IOException, FileNotFoundException {
		appSettings = new Properties();
		try {
			// try to open our properties file and read the properties into appSettings
			openReader();
		} catch (FileNotFoundException e1) {
			System.out.println("Properties file not found : creating a new one");
			// file doesnt exist : need to create a new file
			try {
				// create new file
				CreateNewFile();

			} catch (IOException e3) {
				/// writing to a new FileOutputStream doesn't work !
				//System.out.println("Writing to new properties file doesn't work");
				throw e3;
			}

		} catch (IOException e2) {
			System.out.println("IO Exception during read properties file access");
			initDefaultProperties();
		}

	}

	private void openReader() throws FileNotFoundException, IOException {
		// String rootpath = getClass().getResource("").getPath();
		// String prop_file = rootpath + fileName + ".properties";
		String prop_file = path_to_settings_file + fileName + ".properties";
		FileInputStream fi = new FileInputStream(prop_file);
		appSettings.load(fi);
		//System.out.println("OK : Properties read from " + prop_file);
		fi.close();
	}

	/**
	 * Change "sound during play" setting
	 *
	 * @param newValue new boolean value
	 * @throws Exception happens if this SettingsRecorder wasn't correctly
	 *                   initialised
	 */
	public void changeSoundSetting(boolean newValue) throws Exception {
		if (appSettings == null) {
			throw new Exception("appSettings == null : should not happen !");
		}

		String val = appSettings.getProperty(SOUND_SETTING);
		boolean val_boolean = Boolean.valueOf(val);

		if (val_boolean != newValue) {
			// change the propertie only if the new value differs from the stored property
			appSettings.setProperty(SOUND_SETTING, String.valueOf(newValue));
			//System.out.println("Sound property set to " + newValue);
		}
	}

	/**
	 * Change "speed increase when level increases" setting
	 *
	 * @param newValue new boolean value
	 * @throws Exception happens if this SettingsRecorder wasn't correctly
	 *                   initialised
	 */
	public void changeSpeedIncreaseSetting(boolean newValue) throws Exception {
		if (appSettings == null) {
			throw new Exception("appSettings == null : should not happen !");
		}

		String val = appSettings.getProperty(INCREASE_SPEED_SETTING);
		boolean val_boolean = Boolean.valueOf(val);

		if (val_boolean != newValue) {
			// change the propertie only if the new value differs from the stored property
			appSettings.setProperty(INCREASE_SPEED_SETTING, String.valueOf(newValue));
			//System.out.println("Speed increase property set to " + newValue);
		}
	}

	public void SaveSettings() throws IOException {
		// String rootpath = getClass().getResource("").getPath();
		// String prop_file = rootpath + fileName + ".properties";
		String prop_file = path_to_settings_file + fileName + ".properties";

		FileOutputStream fo;
		try {
			fo = new FileOutputStream(prop_file);
			appSettings.store(fo, "Tetrix app by XNXA");
			//System.out.println("OK : all properties stored to file " + prop_file);
			fo.close();
		} catch (FileNotFoundException e1) {
			System.out.println("EXCEPTION saving all props - file not found : " + prop_file);
			e1.printStackTrace();
		} catch (SecurityException e2) {
			System.out.println("SECURITY EXCEPTION during access to file when saving all props : " + prop_file);
			e2.printStackTrace();
		}
	}

	private void CreateNewFile() throws IOException {
		// String rootpath =
		// Thread.currentThread().getContextClassLoader().getResource("").getPath();
		// String rootpath = getClass().getResource("").getPath();
		// String prop_file = rootpath + fileName + ".properties";
		String prop_file = path_to_settings_file + fileName + ".properties";
		FileOutputStream fo;

		try {
			fo = new FileOutputStream(prop_file);
			//System.out.println("created new prop file : " + prop_file);
		} catch (FileNotFoundException e) {
			System.out.println("File is already in usage and cannot be created !");
			e.printStackTrace();
			return;
		}
		initDefaultProperties();
		appSettings.store(fo, "Tetrix App settings");
		fo.close();
		//System.out.println("new file creation : initial properties stored OK");
	}

	/**
	 * when props file doesn't exist, this function sets and stores all default
	 * values for all the settings.
	 */
	private void initDefaultProperties() {
		if (appSettings == null) {
			appSettings = new Properties();
		}
		appSettings.clear();
		boolean valeur = false;
		appSettings.setProperty(INCREASE_SPEED_SETTING, String.valueOf(valeur));
		valeur = true;
		appSettings.setProperty(SOUND_SETTING, String.valueOf(valeur));
		// colors settings
		appSettings.setProperty(I_COLOR_SETTING, I_DEFAULT_COLOR_SETTING);
		appSettings.setProperty(J_COLOR_SETTING, J_DEFAULT_COLOR_SETTING);
		appSettings.setProperty(L_COLOR_SETTING, L_DEFAULT_COLOR_SETTING);
		appSettings.setProperty(T_COLOR_SETTING, T_DEFAULT_COLOR_SETTING);
		appSettings.setProperty(S_COLOR_SETTING, S_DEFAULT_COLOR_SETTING);
		appSettings.setProperty(Z_COLOR_SETTING, Z_DEFAULT_COLOR_SETTING);
		appSettings.setProperty(O_COLOR_SETTING, O_DEFAULT_COLOR_SETTING);
		// store default color values
		appSettings.setProperty(I_COLOR_SETTING + "_DEF", I_DEFAULT_COLOR_SETTING);
		appSettings.setProperty(J_COLOR_SETTING + "_DEF", J_DEFAULT_COLOR_SETTING);
		appSettings.setProperty(L_COLOR_SETTING + "_DEF", L_DEFAULT_COLOR_SETTING);
		appSettings.setProperty(T_COLOR_SETTING + "_DEF", T_DEFAULT_COLOR_SETTING);
		appSettings.setProperty(S_COLOR_SETTING + "_DEF", S_DEFAULT_COLOR_SETTING);
		appSettings.setProperty(Z_COLOR_SETTING + "_DEF", Z_DEFAULT_COLOR_SETTING);
		appSettings.setProperty(O_COLOR_SETTING + "_DEF", O_DEFAULT_COLOR_SETTING);

	}

	/**
	 * get the value for "sound during play" setting
	 *
	 * @return true or false
	 * @throws Exception if settings not correctly initialised.
	 */
	public String getMusicSetting() throws Exception {
		if (appSettings != null) {
			String setting = appSettings.getProperty(SOUND_SETTING);
			return setting;
		} else {
			throw new Exception("application settings not initialised");
		}
	}

	/**
	 * get the value for "increase speed when level increases" setting
	 *
	 * @return true or false
	 * @throws Exception if settings not correctly initialised.
	 */
	public String getSpeedIncreaseSetting() throws Exception {
		if (appSettings != null) {
			String setting = appSettings.getProperty(INCREASE_SPEED_SETTING);
			return setting;
		} else {
			throw new Exception("application settings not initialised");
		}
	}

	/**
	 * save a new color for a tetromino
	 *
	 * @param color_picker_id the id of the color picker
	 * @param color_value     the new color value ( ATTENTION : #RRGGBB format)
	 * @return the old color value
	 */
	public String saveNewColor(String color_picker_id, String color_value) {
		String new_value = color_value;
		String key = correspondance.get(color_picker_id);
		if(color_value.length()>7) {
			new_value = color_value.substring(0, 7);
		}
		String old_val = (String) appSettings.setProperty(key, new_value);
		return old_val;
	}

	/**
	 * get the color value from this settings recorder
	 *
	 * @param string the id (name) of the color picker
	 * @return color value stored in this properties recorder (#RRGGBB format)
	 */
	public String getColor(String color_picker_id) {
		String key = correspondance.get(color_picker_id);
		String val = appSettings.getProperty(key);

		return val;
	}

	/**
	 * get the stored property color for this shape name
	 * @param shape_name "I", "J", "L", "T", "S", "Z" or "O"
	 * @return a color value for this shape
	 */
	public Color getColorForShape(String shape_name) {
		String color = getColor("cp_" + shape_name);
		Color result = Color.valueOf(color);
		return result;
	}

	/**
	 * Checks if all saved color properties for the shapes are standard colors.
	 * @return true if all shapes are with standard color, false otherwise.
	 */
	public boolean isStandardColors() {
		String col;

		col = getColor("cp_O");
		if(!col.equals(O_DEFAULT_COLOR_SETTING)) {
			//System.out.println("O color isn't standard");
			return false;
		}
		col = getColor("cp_I");
		if(!col.equals(I_DEFAULT_COLOR_SETTING)) {
			//System.out.println("I color isn't standard");
			return false;
		}
		col = getColor("cp_T");
		if(!col.equals(T_DEFAULT_COLOR_SETTING)) {
			//System.out.println("T color isn't standard");
			return false;
		}
		col = getColor("cp_J");
		if(!col.equals(J_DEFAULT_COLOR_SETTING)) {
			//System.out.println("J color isn't standard");
			return false;
		}
		col = getColor("cp_L");
		if(!col.equals(L_DEFAULT_COLOR_SETTING)) {
			//System.out.println("L color isn't standard");
			return false;
		}
		col = getColor("cp_S");
		if(!col.equals(S_DEFAULT_COLOR_SETTING)) {
			//System.out.println("S color isn't standard");
			return false;
		}
		col = getColor("cp_Z");
		if(!col.equals(Z_DEFAULT_COLOR_SETTING)) {
			//System.out.println("Z color isn't standard");
			return false;
		}
		return true;
	}

	/**
	 * get standard colors for the given color picker name
	 * @param string color picker name (eg : cp_I)
	 * @return default color in #RRGGBB format
	 */
	public String getStdColor(String string) {
		switch (string) {
		case "cp_I":
			return I_DEFAULT_COLOR_SETTING;
		case "cp_J":
			return J_DEFAULT_COLOR_SETTING;
		case "cp_L":
			return L_DEFAULT_COLOR_SETTING;
		case "cp_O":
			return O_DEFAULT_COLOR_SETTING;
		case "cp_T":
			return T_DEFAULT_COLOR_SETTING;
		case "cp_S":
			return S_DEFAULT_COLOR_SETTING;
		case "cp_Z":
			return Z_DEFAULT_COLOR_SETTING;
		default:
			return null;
		}

	}



}
