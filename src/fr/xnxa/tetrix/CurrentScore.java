package fr.xnxa.tetrix;
import javafx.scene.control.Label;

public class CurrentScore {

	private static long points = 0;
	private static int totalCompletedLines = 0;

	private static Label s;

	public static void setScoreLabel(Label score) {
		s = score;
	}

	public static void raz() {
		points = 0;
		updateLabel();
	}

	public static void add(int amountOfLines) {
		switch (amountOfLines) {
			case 0:
				break;
			case 1:
				points+=100*getLevel();
				break;
			case 2:
				points+=300*getLevel();
				break;
			case 3:
				points+=500*getLevel();
				break;
			case 4:
				points+=800*getLevel();
				break;
			default:
				throw new IllegalStateException("The number of lines deleted can't be : " + amountOfLines);
		}
		
		totalCompletedLines += amountOfLines;
		updateLabel();
	}

	public static long getScore() {
		return points;
	}

	public static int getLevel() {
		
		int level = 1;
		
		while (totalCompletedLines >= level * 5) {
			level++;
		}
		
		return level;
	}
	
	private static void updateLabel() {
		s.setText("Score : " + points + " (Level : " + getLevel() + ")");
	}
}
