package com.venus.support;

public class Result
{
	private boolean success;

	private String message;

	private Object object;

	public boolean isSuccess()
	{

		return success;
	}

	public void setSuccess(boolean success)
	{

		this.success = success;
	}

	public String getMessage()
	{

		return message;
	}

	public void setMessage(String message)
	{

		this.message = message;
	}

	public Object getObject()
	{

		return object;
	}

	public void setObject(Object object)
	{

		this.object = object;
	}

	public static Result getSuccess(String message, Object object)
	{

		Result result = new Result();
		result.setMessage(message);
		result.setObject(object);
		result.setSuccess(true);
		return result;

	}

	public static Result getFailure(String message, Object object)
	{

		Result result = new Result();
		result.setMessage(message);
		result.setObject(object);
		result.setSuccess(false);
		return result;
	}

}
