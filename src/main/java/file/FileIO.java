package main.java.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map.Entry;

public class FileIO {
	private String source = new String();
	
	public FileIO(String source){
		this.source = source;
	}
	
	public void createFolder(String folderPath) {
		File dir = new File(source + folderPath);
		dir.mkdir();
	}
	
	public void clearFolder(String folderPath){
		Path dir = Paths.get(source + folderPath);
		
		if(!Files.exists(dir)) {
			createFolder(folderPath);
		}
		
		try {
			Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
				 @Override
                 public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                     Files.delete(file);
                     return FileVisitResult.CONTINUE;
                 }
				 // TOFIX: This should not delete the root...
				 /*@Override
                 public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                     System.out.println("Deleting dir: " + dir);
                     if (exc == null) {
                         Files.delete(dir);
                         return FileVisitResult.CONTINUE;
                     } else {
                         throw exc;
                     }
                 }*/
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void createFile(String filePath){
		File file =new File(filePath);
		try {
			file.getParentFile().mkdirs();
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void appendFile(String fullFilePath, String content) throws IOException{
		File file =new File(fullFilePath);
		if(!file.exists()){
			createFile(fullFilePath);
	    }
		FileWriter fw = new FileWriter(file,true);
		BufferedWriter bw = new BufferedWriter(fw);
    	bw.write(content);
    	bw.newLine();
    	bw.close();
	}
	
	
	public void writeString(String filePath, String content) {
		try {
			appendFile(source + filePath + ".txt", content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
