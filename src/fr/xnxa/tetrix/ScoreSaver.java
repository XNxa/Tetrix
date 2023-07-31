package fr.xnxa.tetrix;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ScoreSaver {

	private static final String SAVE_FILE = "scores.txt";

	private static final String HOME_FOLDER = System.getProperty("user.home");

	private static final String TETRIX_FOLDER = "Tetrix";

	private File save;

	/**
	 * The constructor checks if the save file already exist or not
	 * If it doesn't => it creates it
	 */
	public ScoreSaver() {

		String path = HOME_FOLDER;

		File home = new File(path);

		if (!home.exists()) {
			System.err.println("Impossible de trouver le dossier racine");
		}

		path += File.separator + TETRIX_FOLDER;
		File tetrix = new File(path);

		if (!tetrix.exists()) {
			tetrix.mkdir();
		}

		path += File.separator + SAVE_FILE;
		save = new File(path);

		if (!save.exists()) {
			try {
				save.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	/**
	 * Get the sorted scores in form the save file.
	 * @return List of string following the pattern "playername - points"
	 */
	public List<String> getScores() {

		List<Score> scores = new LinkedList<>();

		try (FileReader file = new FileReader(save)) {

			BufferedReader buffer = new BufferedReader(file);

			String line;
			while ((line = buffer.readLine()) != null) {
				for (String string : line.split(",")) {

					String name = string.split(":")[0];
					long points = Long.parseLong(string.split(":")[1]);

					scores.add(new Score(name, points));
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Collections.sort(scores, Collections.reverseOrder());

		LinkedList<String> result = new LinkedList<>();

		for (Score s : scores) {
			result.add(s.getPlayername() + " - " + ((Long) s.getPoints()).toString());
		}

		return result;
	}
	
	/**
	 * Add a new entry to the save file.
	 * @param Score
	 */
	public void appendScore(Score s) {

		try (FileWriter file = new FileWriter(save, true)) {

			file.write(s.toString() + ",");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
