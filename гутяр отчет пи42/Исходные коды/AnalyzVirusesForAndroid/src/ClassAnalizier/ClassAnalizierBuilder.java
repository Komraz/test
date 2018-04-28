package ClassAnalizier;

import java.io.File;

public class ClassAnalizierBuilder implements AnalizierBuilder{
	private ClassAnalizier obj;
	@Override
	public ClassAnalizierBuilder createDefault()
	{
		obj=ClassAnalizier.getInstance();
		return this;
	}
	@Override
	public AnalizierBuilder initializePos()
	{
		obj.setPos(0);
		return this;
	}
	@Override
	public AnalizierBuilder setBeginMethods(String methods)
	{
		obj.setBeginMethods(methods);
		return this;
	}
	@Override
	public AnalizierBuilder setFile(File file)
	{
		obj.setFile(file);
		return this;
	}
	@Override
	public FileAnalizier getResult()
	{
		return obj;
	}
}
