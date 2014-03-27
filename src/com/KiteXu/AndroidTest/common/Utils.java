package com.KiteXu.AndroidTest.common;

public class Utils {
	
	private static Object fragmentMessage;
	
	public static void setFragmentMessage(Object object)
	{
		fragmentMessage = object;
	}
	
	public static Object getFragmentMessage()
	{
		return fragmentMessage;
	}
}
