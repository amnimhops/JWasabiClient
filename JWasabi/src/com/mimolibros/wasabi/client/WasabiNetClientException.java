package com.mimolibros.wasabi.client;

public class WasabiNetClientException extends Exception {
	String xmlRequest;
	String xmlResponse;
	
	public WasabiNetClientException(String msg){
		super(msg);
		this.xmlRequest=null;
		this.xmlResponse=null;
	}
	
	public WasabiNetClientException(String msg,String request, String response){
		super(msg);
		this.xmlRequest=request;
		this.xmlResponse=response;
	}

	public String getXmlRequest() {
		return xmlRequest;
	}

	public void setXmlRequest(String xmlRequest) {
		this.xmlRequest = xmlRequest;
	}

	public String getXmlResponse() {
		return xmlResponse;
	}

	public void setXmlResponse(String xmlResponse) {
		this.xmlResponse = xmlResponse;
	}

	
}
