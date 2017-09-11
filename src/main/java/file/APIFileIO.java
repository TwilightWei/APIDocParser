package main.java.file;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class APIFileIO {
	public void createFile(String filePath){
		File file =new File(filePath);
		try {
			file.getParentFile().mkdirs();
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void appendFile(String filePath, String content) throws IOException{
		File file =new File(filePath);
		if(!file.exists()){
			createFile(filePath);
	    }
		else{
			FileWriter fw = new FileWriter(file,true);
			BufferedWriter bw = new BufferedWriter(fw);
	    	bw.write(content);
	    	bw.newLine();
	    	bw.close();
		}
	}
}