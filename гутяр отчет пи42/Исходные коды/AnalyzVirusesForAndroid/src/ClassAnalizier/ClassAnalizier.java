package ClassAnalizier;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

enum TYPELEX{TYPE_NAME,TYPE_LEFTROUND,TYPE_RIGHTROUND,TYPE_LEFTFIG,TYPE_RIGHTFIG,TYPE_COMMA,TYPE_TYPE,TYPE_NONE,TYPE_END};

public class ClassAnalizier implements FileAnalizier{
	private int pos;
	private String lastLex,data;
	private String methods;
	private static ClassAnalizier instance=null;
	public static ClassAnalizier getInstance()
	{
		if(instance==null)
		{
			instance=new ClassAnalizier();
		}
		return instance;
	}
	public void setBeginMethods(String m)
	{
		methods=m;
	}
	public void setFile(File file)
	{
		if (!file.isDirectory()) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                stringBuilder.append(scanner.nextLine()).append("\n");
            }
    		data=stringBuilder.toString();
            scanner.close();
        	} catch (FileNotFoundException e) {
        		e.printStackTrace();
        	}
		}
	}
	private ClassAnalizier()
	{
		
	}
	
	public void setPos(int p)
	{
		pos=p;
	}
	
	public boolean isSingle()
	{
		return true;
	}
	
	private boolean isSystem(String lex)
	{
		String list[]= {"if","while","for","switch"};
		for(int i=0;i<list.length;i++)
			if(lex.compareTo(list[i])==0)
				return true;
		return false;
	}
	private TYPELEX analizeNextLex(String text)
	{
		int length,locPos;
		TYPELEX result;
		length=text.length();
		char mas[]=text.toCharArray();
		if(pos<length)
			do{
				if(mas[pos]==' '||mas[pos]=='\n')
					pos++;
				else if(mas[pos]=='/'&&pos+1<length&&mas[pos+1]=='/')
					do {
						pos++;
					}while(pos<length&&mas[pos]!='\n'&&mas[pos]!='\t'&&mas[pos]!='\r');
				else if(mas[pos]=='/'&&pos+1<length&&mas[pos+1]=='*')
					do {
						pos++;
					}while(pos+1<length&&mas[pos]!='*'||mas[pos+1]!='/');
				else break;
			}while(pos<length);
		else result=TYPELEX.TYPE_END;
		locPos=pos;
		if(locPos<length)
		{
			if(mas[locPos]>='a'&&mas[locPos]<='z'||mas[locPos]>='A'&&mas[locPos]<='Z'||mas[locPos]=='_'||
					mas[locPos]>='0'&&mas[locPos]<='9')
			{
				int fSkip=0;
				do {
					locPos++;
					if(mas[locPos]=='<')
						fSkip++;
					else if(mas[locPos]=='>')
					{
						fSkip--;
						if(fSkip==0) locPos++;
					}
				}while(mas[locPos]>='a'&&mas[locPos]<='z'||mas[locPos]>='A'&&mas[locPos]<='Z'||
						mas[locPos]>='0'&&mas[locPos]<='9'||mas[locPos]=='_'||mas[locPos]=='['||mas[locPos]==']'||fSkip>0);
				if(isSystem(text.substring(pos, locPos)))
					result=TYPELEX.TYPE_NONE;
				else result=TYPELEX.TYPE_NAME;
			}
			else
			{
				if(mas[locPos]=='(')
					result=TYPELEX.TYPE_LEFTROUND;
				else if(mas[locPos]==')')
					result=TYPELEX.TYPE_RIGHTROUND;
				else if(mas[locPos]=='{')
					result=TYPELEX.TYPE_LEFTFIG;
				else if(mas[locPos]=='}')
					result=TYPELEX.TYPE_RIGHTFIG;
				else if(mas[locPos]==',')
					result=TYPELEX.TYPE_COMMA;
				else if(mas[locPos]=='@')
					{
						locPos++;
						if(mas[locPos]>='a'&&mas[locPos]<='z'||mas[locPos]>='A'&&mas[locPos]<='Z'||mas[locPos]=='_')
						{
							do{
								locPos++;
							}while(mas[locPos]>='a'&&mas[locPos]<='z'||mas[locPos]>='A'&&mas[locPos]<='Z'||
									mas[locPos]>='0'&&mas[locPos]<='9'||mas[locPos]=='_');
						}
						locPos--;
						result=TYPELEX.TYPE_NONE;
					}
				else result=TYPELEX.TYPE_NONE;
				locPos++;
			}
		}
		else result=TYPELEX.TYPE_END;
		lastLex=text.substring(pos, locPos);
		pos=locPos;
		return result;
	}
	private void analizeMethodCall(String from,String to)
	{
		TYPELEX type;
		int parCount;
		String name;
		parCount=0;
		do {
			type=analizeNextLex(data);
			while(type==TYPELEX.TYPE_NAME)
			{
				parCount++;
				name=lastLex;
				type=analizeNextLex(data);
				if(type==TYPELEX.TYPE_COMMA)
					type=analizeNextLex(data);
				else if(type==TYPELEX.TYPE_LEFTROUND)
				{
					analizeMethodCall(from,name);
				}
			}
		}while(type!=TYPELEX.TYPE_RIGHTROUND);
		String method="\""+from+"\"->\""+to+"("+parCount+")\";\n";
		if(!methods.contains(method))
			methods+=method;
	}
	private void analizeMethodRealize(String name)
	{
		TYPELEX type;
		int parCount;
		parCount=0;
		do {
			type=analizeNextLex(data);
			while(type==TYPELEX.TYPE_NAME)
			{
				parCount++;
				type=analizeNextLex(data);
				if(type==TYPELEX.TYPE_NAME)
				{
					type=analizeNextLex(data);
					if(type==TYPELEX.TYPE_COMMA)
						type=analizeNextLex(data);
				}
			}
		}while(type!=TYPELEX.TYPE_RIGHTROUND);
		analizeNextLex(data);
		analizeCode(name+"("+parCount+")");
	}
	private void analizeCode(String tempMethod)
	{
		int prevPos;
		TYPELEX type;
		int blockCount;
		blockCount=1;
		do {
			type=analizeNextLex(data);
			if(type==TYPELEX.TYPE_NAME)
			{
				boolean isNoCall;
				String name=lastLex;
				prevPos=pos;
				isNoCall=false;
				type=analizeNextLex(data);
				if(type==TYPELEX.TYPE_NAME)
				{
					isNoCall=true;
					name=lastLex;
					type=analizeNextLex(data);
				}
				if(type==TYPELEX.TYPE_LEFTROUND)
				{
					prevPos=pos;
					type=analizeNextLex(data);
					while(type!=TYPELEX.TYPE_RIGHTROUND)
					{
						type=analizeNextLex(data);
					}
					if(type==TYPELEX.TYPE_RIGHTROUND)
					{
						type=analizeNextLex(data);
						if(type==TYPELEX.TYPE_LEFTFIG)
						{
							pos=prevPos;
							analizeMethodRealize(name);
						}
						else if(!isNoCall)
						{
							pos=prevPos;
							analizeMethodCall(tempMethod,name);
						}
					}
				}
				else if(type==TYPELEX.TYPE_NAME) pos=prevPos;
				else if(type==TYPELEX.TYPE_LEFTFIG) blockCount++;
				else if(type==TYPELEX.TYPE_RIGHTFIG) blockCount--;
			}
		}while(type!=TYPELEX.TYPE_END&&blockCount>0);
	}
	
	public String analizeFile()
	{
        analizeCode("!!invalid!!");
		return methods;
	}
}
