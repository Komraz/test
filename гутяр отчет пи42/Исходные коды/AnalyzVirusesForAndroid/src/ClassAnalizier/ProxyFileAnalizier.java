package ClassAnalizier;

import java.io.File;

public class ProxyFileAnalizier extends AnalizierSubject implements FileAnalizier {
	private ClassAnalizier analizier;
	private String methods;
	private int pos;
	private File file;
	public ProxyFileAnalizier(AnalizierObserver parent)
	{
		analizier=null;
		observer=parent;
	}
	public boolean isSingle()
	{
		return false;
	}
	public String analizeFile(File f,String m)
	{
		setFile(f);
		setBeginMethods(m);
		setPos(0);
		return analizeFile();
	}
	public String analizeFile() {
		if(analizier==null)
		{
			AnalizierBuilder builder=new ClassAnalizierBuilder();
			builder.createDefault().setFile(file).initializePos().setBeginMethods(methods);
			analizier=(ClassAnalizier)builder.getResult();
		}
		if(observer!=null) sendCompleted();
		return analizier.analizeFile();
	}
	public void setPos(int p)
	{
		if(analizier!=null)
			analizier.setPos(p);
		else pos=p;
	}
	public void setBeginMethods(String data)
	{
		if(analizier!=null)
			analizier.setBeginMethods(data);
		else methods=data;
	}
	public void setFile(File data)
	{
		if(analizier!=null)
			analizier.setFile(data);
		else file=data;
	}

}
