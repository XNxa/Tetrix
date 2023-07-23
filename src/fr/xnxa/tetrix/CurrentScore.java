package fr.xnxa.tetrix;
import javafx.scene.control.Label;

public class CurrentScore {

	private static long points = 0;
	private static int level = 1;

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
				points+=100*level;
				break;
			case 2:
				points+=300*level;
				break;
			case 3:
				points+=500*level;
				break;
			case 4:
				points+=800*level;
				break;
			default:
				throw new IllegalStateException("The number of lines deleted can't be : " + amountOfLines);
		}
		updateLabel();
	}

	public static long getScore() {
		return points;
	}

	public static int getLevel() {
		return level;
	}
	
	private static void updateLabel() {
		s.setText("Score : " + points);
	}
}
