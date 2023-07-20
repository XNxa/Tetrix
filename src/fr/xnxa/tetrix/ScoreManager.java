package fr.xnxa.tetrix;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ScoreManager {
	
	private static final String SAVE_FILE = "scores.txt";
	
	private static final String HOME_FOLDER = System.getProperty("user.home");
	
	private static final String TETRIX_FOLDER = "Tetrix";
	
	private File save;
	
	/**
	 * The constructor checks if the save file already exist or not
	 * If it doesn't => it creates it
	 */
	public ScoreManager() {
		
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
	
	public List<String> getScores() {
		
		List<String> scores = new LinkedList<>();
		
		try (FileReader file = new FileReader(save)) {
			
			BufferedReader buffer = new BufferedReader(file);
			
			String line;
			while ((line = buffer.readLine()) != null) {
				scores.add(line);
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return scores;
	}
	
	public void appendScore(String playername, long points) {
		
		try (FileWriter file = new FileWriter(save, true)) {
			
			playername.trim();
			file.write(playername + " - " + points + "\n");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
