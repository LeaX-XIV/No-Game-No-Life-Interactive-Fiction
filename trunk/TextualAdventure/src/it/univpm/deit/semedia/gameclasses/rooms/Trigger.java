package it.univpm.deit.semedia.gameclasses.rooms;

import it.itido.quinta.informatici.HolyBorio.gameclasses.endgame.ScrollableText;
import it.itido.quinta.informatici.HolyBorio.gameclasses.giochi.Gioco;
import it.univpm.deit.semedia.GenericConsole;

public class Trigger {
	
	private boolean initialized;
	
	private Room triggerRoom;
	private int triggerCount;
	
	private ScrollableText startTrigger;
	private Gioco event;
	private boolean eventResult;
	private ScrollableText endTrigger;
	
	public Trigger(Room room) {
		this.initialized = false;
		this.triggerRoom = room;
		this.startTrigger = null;
		this.event = null;
		this.eventResult = false;
		this.endTrigger = null;
	}
	
	public void init(int triggerCount, String startText, Gioco event, ScrollableText endTrigger) {
		this.triggerCount = triggerCount;
		this.startTrigger = new ScrollableText(startText);
		this.event = event;
		this.endTrigger = endTrigger;
		this.initialized = true;
	}
	
	public void execute() {
		if(initialized) {
			if(triggerCount == triggerRoom.getCount()) {
				if(startTrigger != null) {
					startTrigger.show();
				}
				if(event != null) {
					eventResult = event.getResult();
					if(endTrigger != null) {
						endTrigger.show(eventResult);
					}
				}
				
				
			}
		}
	}

}
