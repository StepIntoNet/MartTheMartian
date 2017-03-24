package net.stepinto.engine.event;

public interface IGameEvent<L> {

	public void notify (final L listener);
	
}
