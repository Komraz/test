package ClassAnalizier;

import java.io.File;

public class ProxyAnalizierBuilder implements AnalizierBuilder {
	private ProxyFileAnalizier obj;
	private AnalizierObserver parent;
	public ProxyAnalizierBuilder(AnalizierObserver parent)
	{
		this.parent=parent;
	}
	
	@Override
	public AnalizierBuilder createDefault() {
		obj=new ProxyFileAnalizier(parent);
		return this;
	}

	@Override
	public AnalizierBuilder initializePos() {
		obj.setPos(0);
		return this;
	}

	@Override
	public AnalizierBuilder setBeginMethods(String methods) {
		obj.setBeginMethods(methods);
		return this;
	}

	@Override
	public AnalizierBuilder setFile(File file) {
		obj.setFile(file);
		return this;
	}
	@Override
	public FileAnalizier getResult() {
		return obj;
	}

}
