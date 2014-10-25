package it.univpm.deit.semedia.gameclasses.rooms;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import it.itido.quinta.informatici.HolyBorio.gameclasses.endgame.Credit;
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

	private Method endMethod;
	private Object[] args;

	private boolean fatal;

	public Trigger(Room room) {
		this.initialized = false;
		room.setTrigger(this);
		this.triggerRoom = room;
		setTriggerCount(0);
		setStartTrigger(null);
		setEvent(null);
		this.eventResult = false;
		setEndTrigger(null);
		setFatal(false);
	}

	public void init(int triggerCount, String startText, Gioco event, ScrollableText endTrigger) {
		setTriggerCount(triggerCount);
		setStartTrigger(startText);
		setEvent(event);
		setEndTrigger(endTrigger);
		this.initialized = true;
	}

	public void setTriggerCount(int triggerCount) {
		this.triggerCount = triggerCount;
	}

	public void setStartTrigger(String startText) {
		this.startTrigger = new ScrollableText(startText);
	}

	public void setEvent(Gioco event) {
		this.event = event;
		if(event == null) {
			setFatal(false);
		}
	}

	public void setEndTrigger(ScrollableText endTrigger) {
		this.endTrigger = endTrigger;
	}

	public void setFatal(boolean fatal) {
		this.fatal = fatal;
	}

	public void setMethod(Class obj, String methodName, Object... args) {
		this.args = args;


		Class[] params = new Class[args.length];
		for (int i = 0; i < args.length; i++) {
			params[i] = args[i].getClass();
		}

		try {
			this.endMethod = obj.getMethod(methodName, params);
		} catch (NoSuchMethodException e) {
		} catch (SecurityException e) {
		}		
	}

	public void execute() {
		if(initialized) {
			if(triggerCount == triggerRoom.getCount()) {
				if(startTrigger != null) {
					startTrigger.showDinamicWait();
				}
				if(event != null) {
					eventResult = event.getResult();
					if(endTrigger != null) {
						if (endTrigger instanceof Credit) {
							Credit credit = (Credit) endTrigger;
							if(eventResult) {
								credit.setText(credit.getText().split("\\|")[1]);
								credit.show();
							}
						}
						endTrigger.show(eventResult);
					}
					if(eventResult) {
						if(endMethod != null) {
							try {
								endMethod.invoke(null, this.args);
							} catch (IllegalAccessException e) {
							} catch (IllegalArgumentException e) {
							} catch (InvocationTargetException e) {
							}
						}
					}
				}
			}
			if(fatal) {
				if(!eventResult) {
					System.exit(0);
				}
			}
		}
	}


}
