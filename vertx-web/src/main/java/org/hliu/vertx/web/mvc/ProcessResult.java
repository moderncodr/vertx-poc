package org.hliu.vertx.web.mvc;

public class ProcessResult {

	public final static int OK_STATUS = 0;
	
	public final static int REDIRECT_STATUS = 1;

	public final static int NOT_FOUND_STATUS = 2;
	
	public final static int NOT_ALLOWED_STATUS = 3;

	public static final ProcessResult NOT_FOUND = new ProcessResult(NOT_FOUND_STATUS);

	public static final ProcessResult NOT_ALLOWED = new ProcessResult(NOT_ALLOWED_STATUS);
	
	public static ProcessResult OK_RESULT = new ProcessResult(OK_STATUS);
	
	public static ProcessResult redirect(String target) {
		return new ProcessResult(REDIRECT_STATUS, target);
	}
	
	public ProcessResult(int status) {
		this.status = status;
	}

	public ProcessResult(int status, String target) {
		this.status = status;
		this.target = target;
	}
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public static int getOkStatus() {
		return OK_STATUS;
	}

	public static int getRedirectStatus() {
		return REDIRECT_STATUS;
	}

	
	private int status;
	
	private String target;
}
