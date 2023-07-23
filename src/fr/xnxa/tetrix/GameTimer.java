package fr.xnxa.tetrix;

import javafx.animation.AnimationTimer;

public class GameTimer extends AnimationTimer {
	
	private Game game;
	
	private long previousTime = 0;
	private long interval; 
	
	public GameTimer(Game game) {
		interval = 1_000_000_000;
		this.game = game;
	}
	
	@Override
	public void handle(long currentTime) {
		if (previousTime == 0) {
			previousTime = currentTime;
			return;
		}

		if (currentTime - previousTime >= interval) {
			game.tick();
			previousTime = currentTime;
		}
	}
	
	public void setTickSpeed(int level) {
		double result = 0.8 - ((level - 1) * 0.007);
		result = Math.pow(result, level - 1);
		result *= 1_000_000_000;
		interval = (long) result;
	}

}
