
package cn.edu.llxy.dw.dss.exception;

import cn.edu.llxy.dw.dss.message.ExceptionMessage;


public class CheckedException extends Exception
{

	private static final long serialVersionUID = -6658424340296267525L;

	private String errorCode;

	private String[] msgParams;

	private String debugMessage; // Technical error message

	/**
	 * Common infomation of the exception. Sub class can reset it.
	 */
	protected static String commonMessgage = "";

	public CheckedException() {
		this(null, null, null, null);
	}

	/**
	 * @param cause
	 *            The cause. (A null value is permitted, and indicates that the
	 *            cause is nonexistent or unknown.)
	 */
	public CheckedException(Throwable cause) {
		this(null, null, null, cause);
	}

	/**
	 * @param debugMessage
	 *            technical error message
	 */
	public CheckedException(String debugMessage) {
		this(debugMessage, null, null, null);
	}

	/**
	 * @param debugMessage
	 *            technical error message
	 * 
	 * @param cause
	 *            the cause. (A null value is permitted, and indicates that the
	 *            cause is nonexistent or unknown.)
	 */
	public CheckedException(String debugMessage, Throwable cause) {
		this(debugMessage, null, null, cause);
	}

	/**
	 * 
	 * @param errorCode
	 * @param params
	 */
//	public CheckedException(String errorCode, String[] params) {
//		this(null, errorCode, params, null);
//	}

	/**
	 * @param errorCode
	 * @param params1
	 */
	public CheckedException(String errorCode, String ...params1) {
		this(null, errorCode, params1, null);
	}
	/**
	 * 
	 * @param errorCode
	 * @param params
	 * @param cause
	 */
	public CheckedException(String errorCode, String[] params, Throwable cause) {
		this(null, errorCode, params, cause);
	}

	/**
	 * 
	 * @param debugMessage
	 *            technical error message
	 * @param errorCode
	 * @param params
	 */
	public CheckedException(String debugMessage, String errorCode,
			String[] params) {
		this(debugMessage, errorCode, params, null);
	}

	/**
	 * 
	 * @param debugMessage
	 *            error message in technical tongue
	 * @param errorCode
	 * @param params
	 * @param cause
	 */
	public CheckedException(String debugMessage, String errorCode,
			String[] params, Throwable cause) {
		super(cause);
		this.debugMessage = debugMessage;
		if (errorCode != null)
		{
			this.errorCode = errorCode;
			this.msgParams = params;
		}
	}

	/**
	 * Get user friendly error message of this exception
	 * 
	 * @return user friendly error message of this exception. (which may be
	 *         <tt>null</tt>).
	 */
	public String getDisplayMessage()
	{

		String dispMsg = ExceptionMessage.getString(errorCode, msgParams);

		Throwable cause = this.getCause();

		String causedDispMsg = null;
		if (cause != null)
		{
			if (cause instanceof UnCheckedException)
			{
				causedDispMsg = ((UnCheckedException) cause)
						.getDisplayMessage();
			} else
			{
				if (cause instanceof CheckedException)
				{
					causedDispMsg = ((CheckedException) cause)
							.getDisplayMessage();
				}
			}
		}

		if (dispMsg != null)
		{
			if (causedDispMsg != null)
			{
				dispMsg += "\n  Caused by: " + causedDispMsg;
			}
		} else
		{
			if (causedDispMsg != null)
			{
				dispMsg = causedDispMsg;
			}
		}

		return dispMsg;
	}

	/**
	 * Get debug error message of this exception. This method overrides the same
	 * method in the parent class.
	 * 
	 * @return detail error message of this exception. (which may be
	 *         <tt>null</tt>).
	 */
	public String getMessage()
	{
		String msg = commonMessgage;

		if (debugMessage != null && debugMessage.length() > 0)
		{
			msg += " " + debugMessage;
		} else
		{
			String dispMsg = getDisplayMessage();
			if (dispMsg != null && dispMsg.length() > 0)
			{
				msg += "" + dispMsg;
			}
		}
		return msg;
	}

	/**
	 * Get user friendly error message of this exception
	 * 
	 * @return user friendly error message of this exception. (which may be
	 *         <tt>null</tt>).
	 */

	public String getErrorCode()
	{
		return errorCode;
	}

}
