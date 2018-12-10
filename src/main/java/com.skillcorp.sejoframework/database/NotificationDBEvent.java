package com.skillcorp.sejoframework.database;

import java.util.EventObject;

public class NotificationDBEvent extends EventObject {

	NotificationDB notification;
	public NotificationDBEvent(Object source,NotificationDB notify) {
		super(source);
		notification = notify;
	}

}
