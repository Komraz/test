package ClassAnalizier;

import java.io.File;

public interface AnalizierBuilder {
	public AnalizierBuilder createDefault();
	public AnalizierBuilder initializePos();
	public AnalizierBuilder setFile(File file);
	public AnalizierBuilder setBeginMethods(String methods);
	public FileAnalizier getResult();
}
