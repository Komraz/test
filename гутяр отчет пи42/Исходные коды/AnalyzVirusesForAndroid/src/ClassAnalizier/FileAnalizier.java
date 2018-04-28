package ClassAnalizier;

import java.io.File;

public interface FileAnalizier {
	public String analizeFile();
	public void setPos(int p);
	public void setFile(File file);
	public void setBeginMethods(String data);
	public boolean isSingle();
}
