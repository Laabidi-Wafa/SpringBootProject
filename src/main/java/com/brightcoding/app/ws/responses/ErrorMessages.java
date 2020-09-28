package com.brightcoding.app.ws.responses;
//class dédié pour les exceptions
public enum ErrorMessages {
	MISSING_REQUIRED_FIELD("Missing required field."), //le champ n'existe pas
	RECORD_ALREADY_EXISTS("record already exists."), //le champ deja existe
	INTERNAL_SERVER_ERROR("internal server error."),  
	NO_RECORD_FOUND("no record found."); //objet n'existe pas

	private ErrorMessages(String errorMessage) {
		this.setErrorMessage(errorMessage);
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	private String errorMessage;

}
