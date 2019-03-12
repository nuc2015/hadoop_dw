// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MockCF.java

package cn.edu.llxy.dw.dss.util;


public class MockCF
{

	public MockCF()
	{
	}

	public static int atoi(String para)
	{
		if (para == null || para.trim().length() == 0)
			return 0;
		int ret = 0;
		String str = para.trim();
		int len = str.length();
		int pos = len;
		for (int i = 0; i < len; i++)
		{
			char ch = str.charAt(i);
			if (i == 0 && (ch == '+' || ch == '-') || ch >= '0' && ch <= '9')
				continue;
			pos = i;
			break;
		}

		try
		{
			if (str.charAt(0) == '+')
			{
				str = str.substring(1);
				pos--;
			}
			ret = Integer.parseInt(str.substring(0, pos));
		}
		catch (Exception ex) { }
		return ret;
	}

	public static String byteHEX(byte ib)
	{
		char Digit[] = {
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
			'a', 'b', 'c', 'd', 'e', 'f'
		};
		char ob[] = new char[2];
		ob[0] = Digit[ib >>> 4 & 0xf];
		ob[1] = Digit[ib & 0xf];
		String s = new String(ob);
		return s;
	}

	public static int strncmp(char frist[], char sec[], int num)
	{
		String fs = null;
		if (frist.length >= num)
			fs = String.valueOf(frist).substring(0, num);
		if (frist.length < num)
			fs = String.valueOf(frist);
		String ss = String.valueOf(sec);
		if (fs.equals(ss))
			return 0;
		if (fs.length() > ss.length())
			return 1;
		return fs.length() >= ss.length() ? -2 : -1;
	}
}