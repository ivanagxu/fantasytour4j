package com.ivan.buildindexsheet;

import java.io.File;
import java.util.HashMap;

import javax.swing.Icon;
import javax.swing.filechooser.FileSystemView;

public class IndexFile extends File {
	
	private static Long FILE_ID;
	static{
		FILE_ID = 1L;
	}

	private Icon icon = null;
	private IndexFile parentFile = null;
	private IndexFile[] childFiles = null;
	private int indexLevel;
	private Long Id;
	
	public IndexFile(String pathname, int level) {
		super(pathname);
		this.indexLevel = level;
		
		synchronized (IndexFile.class) {
			Id = FILE_ID++;
        }
		
		
		icon = FileSystemView.getFileSystemView().getSystemIcon(this);
		
		if(level > 1 && this.isDirectory())
		{
			File[] files = this.listFiles();
			if(files.length > 0)
			{
				childFiles = new IndexFile[files.length];
				for(int i = 0; i < childFiles.length; i++){
					childFiles[i] = new IndexFile(files[i].getAbsolutePath(), level - 1);
					childFiles[i].setParentFile(this);
				}
			}
		}
	}
	
	public void setParentFile(IndexFile parentFile){
		this.parentFile = parentFile;
	}
	
	public IndexFile getParentFile(){
		return this.parentFile;
	}
	
	public IndexFile[] childFiles(){
		return this.childFiles;
	}
	
	public Icon getIcon(){
		return this.icon;
	}
	
	public int getIndexLevel(){
		return this.indexLevel;
	}
	
	public Long getId(){
		return this.Id;
	}
	
	private void generateIconIdMap(HashMap<Long, IndexFile> idMap, IndexFile file){
		idMap.put(file.getId(), file);
		
		if(file.childFiles != null){
			for(int i = 0; i < file.childFiles.length; i++){
				generateIconIdMap(idMap, file.childFiles[i]);
			}
		}
	}
	
	public HashMap<Long, IndexFile> getIdMap(){
		HashMap<Long, IndexFile> idMap = new HashMap<Long, IndexFile>();
		
		idMap.put(Id, this);
		
		if(childFiles != null){
			for(int i = 0; i < childFiles.length; i++){
				generateIconIdMap(idMap, childFiles[i]);
			}
		}
		return idMap;
	}
}
