package net.stepinto.engine.event;

import java.util.ArrayList;
import java.util.HashMap;

public class EventBus {

	private final HashMap<Class, ArrayList> map = new HashMap<Class, ArrayList>(10);
	
	public <L> void listen(Class<? extends IGameEvent<L>> evtClass, L listener){
		final ArrayList<L> listeners = listenersOf(evtClass);
		synchronized(listeners){
			if (! listeners.contains(listener)){
				listeners.add(listener);
			}
		}
	}
	
	
	private <L> ArrayList<L> listenersOf(Class<? extends IGameEvent<L>> evtClass) {
		synchronized(map){
			@SuppressWarnings("unchecked")
			final ArrayList<L> existing = map.get(evtClass);
			if(existing != null){
				return existing;
			}
			
			final ArrayList<L> emptyList = new ArrayList<L>(5);
			map.put(evtClass, emptyList);
			return emptyList;
		}
	}
	
	public <L> void notify (final IGameEvent<L> evt) {
		@SuppressWarnings("unchecked")
		Class<IGameEvent<L>> evtClass = (Class<IGameEvent<L>>) evt.getClass();
		
		for(L listener : listenersOf(evtClass)){
			evt.notify(listener);
		}
	}
	
}
