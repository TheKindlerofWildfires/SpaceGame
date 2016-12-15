package gameEngine;

import combat.BuffHandler;

public class TickManager {
	public BuffHandler buffHandler;
	public TickManager() {
		buffHandler = new BuffHandler();
	}

	public void update() {
		buffHandler.update();
	}

	public void render() {
	}
}
