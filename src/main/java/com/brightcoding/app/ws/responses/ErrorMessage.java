package com.brightcoding.app.ws.responses;

import java.util.Date;

public class ErrorMessage {
	private Date timestamp;
	private String message;
	public ErrorMessage(Date timestamp, String message) {
		super();
		this.setTimestamp(timestamp);
		this.setMessage(message);
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
