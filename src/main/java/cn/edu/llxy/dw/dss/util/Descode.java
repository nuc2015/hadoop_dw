package cn.edu.llxy.dw.dss.util;

public class Descode{

	private static final String KEY = "si-tec";

	public Descode()
	{
	}

	public String encode(String str)
	{
		return code(str, "-e");
	}

	public String descode(String str)
	{
		return code(str, "-d");
	}

	private static String code(String args, String flag)
	{
		char key[] = new char[100];
		char instr[] = new char[100];
		char tmpstr[] = new char[100];
		String ascl = null;
		String ascr = null;
		String desstr = "";
		instr = args.toCharArray();
		key = "si-tec".toCharArray();
		if (flag.equals("-e"))
		{
			key = "si-tec".concat(args).toCharArray();
			instr = key;
			int num = instr.length;
			for (int i = 0; i < num; i++)
			{
				tmpstr[i * 2] = MockCF.byteHEX(String.valueOf(instr[i]).getBytes()[0]).toCharArray()[0];
				tmpstr[i * 2 + 1] = MockCF.byteHEX(String.valueOf(instr[i]).getBytes()[0]).toCharArray()[1];
			}

			instr = new char[100];
			for (int i = 0; i < num; i++)
				instr[i] = tmpstr[i * 2];

			for (int i = 0; i < num; i++)
				instr[num + i] = tmpstr[i * 2 + 1];

			return String.valueOf(instr).trim();
		}
		if (flag.equals("-d"))
		{
			int num = instr.length / 2;
			for (int j = 0; j < num; j++)
			{
				tmpstr[j * 2] = instr[j];
				tmpstr[j * 2 + 1] = instr[num + j];
			}

			instr = new char[100];
			for (int j = 0; j < num; j++)
			{
				ascl = String.valueOf(Integer.parseInt(MockCF.byteHEX(String.valueOf(tmpstr[j * 2]).getBytes()[0]), 16));
				ascr = String.valueOf(Integer.parseInt(MockCF.byteHEX(String.valueOf(tmpstr[j * 2 + 1]).getBytes()[0]), 16));
				int ascnuml = MockCF.atoi(ascl);
				int ascnumr = MockCF.atoi(ascr);
				if (ascnuml < 58)
					ascnuml -= 48;
				else
					ascnuml -= 87;
				if (ascnumr < 58)
					ascnumr -= 48;
				else
					ascnumr -= 87;
				int ascnum = ascnuml * 16 + ascnumr;
				instr[j] = (char)ascnum;
			}

			num = key.length;
			if (MockCF.strncmp(instr, key, num) == 0)
			{
				if (instr.length > num)
				{
					for (int k = 0; k < instr.length - num; k++)
						desstr = (new StringBuilder(String.valueOf(desstr))).append(String.valueOf(instr[num + k])).toString();

				}
				return desstr.trim();
			}
			System.out.println("-1");
		}
		return "-1";
	}
	
	public static void main(String [] a){
		System.out.println(Base64.encode("123"));
		System.out.println(Base64.decode("MTIz"));
		System.out.println(new Descode().encode("123"));
		System.out.println(new Descode().descode("76276633339d453123"));
		
	}
}