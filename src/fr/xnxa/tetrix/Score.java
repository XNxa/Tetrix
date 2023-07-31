package fr.xnxa.tetrix;

/**
 * Class used to represent a Score
 */
public class Score implements Comparable<Score> {

	private String playername;
	private long points;

	public Score(String playername, long points) {
		this.playername = playername.trim();
		this.points = points;
	}

	public String getPlayername() {
		return playername;
	}

	public long getPoints() {
		return points;
	}
	
	/**
	 * A score is bigger if the amount of points is.
	 */
	@Override
	public int compareTo(Score other) {
		return ((Long) this.points).compareTo(other.points);
	}

	@Override
	public String toString() {
		return playername + ":" + points ;
	}

}
