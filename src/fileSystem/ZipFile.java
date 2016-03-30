package fileSystem;


import java.util.LinkedHashSet;
import java.util.Set;

public class ZipFile extends Entity implements Comparable<ZipFile>{
	private Set<Folder> folders;
	private Set<ZipFile> zipFiles;
	private Set<TextFile> textFiles;
	private Set<String> contentsInThisEntity;
	
	//constructors
	public ZipFile(){
		this.folders = new LinkedHashSet<Folder>();
		this.zipFiles = new LinkedHashSet<ZipFile>();
		this.textFiles = new LinkedHashSet<TextFile>();
		this.contentsInThisEntity = new LinkedHashSet<String>();
	}
	
	public ZipFile(String name,String pathOfParent){
		this.setName(name);
		this.setPathofParent(pathOfParent);
		this.folders = new LinkedHashSet<Folder>();
		this.zipFiles = new LinkedHashSet<ZipFile>();
		this.textFiles = new LinkedHashSet<TextFile>();
		this.contentsInThisEntity = new LinkedHashSet<String>();
	}
	
	
	//getters
	public int getSize(){
		return this.size;
	}
	
	public Set<Folder> getFolders(){
		return folders;
	}

	public Set<ZipFile> getZipFiles(){
		return zipFiles;
	}
	
	public Set<TextFile> getTextFiles() {
		return textFiles;
	}
	public Set<String> getcontentsInThisEntity() {
		return this.contentsInThisEntity;
	}
	
	
	//setters
	public void setFolders(Set<Folder> folders){
		this.folders = folders;
	}

	public void setZipFiles(Set<ZipFile> zipFiles){
		this.zipFiles = zipFiles;
	}
	public void setSize(){
		int size = 0;
		for(Folder folder:this.getFolders()){
			this.size += folder.getSize();
		}
		for(ZipFile zipFile:this.getZipFiles()){
			this.size += zipFile.getSize();
		}
		for(TextFile textFile:this.getTextFiles()){
			this.size += textFile.getSize();
		}		
		this.size = size / 2;
	}
	
	public void setTextFiles(Set<TextFile> textFiles) {
		this.textFiles = textFiles;
	}
	
	public void setcontentsInThisEntity(Set<String> contentsInThisEntity) {
		this.contentsInThisEntity = contentsInThisEntity;
	}
	
	
	//add an Entity to this Entity
		public void addToFolders(Folder folder){
			this.folders.add(folder);
			this.contentsInThisEntity.add(folder.getName());
		}
		public void addToZipFiles(ZipFile zipFile){
			this.zipFiles.add(zipFile);
			this.contentsInThisEntity.add(zipFile.getName());
		}
		public void addToTextFiles(TextFile textFile){
			this.textFiles.add(textFile);
			this.contentsInThisEntity.add(textFile.getName());
		}
		public boolean addEntity(String type, String name, String pathofParent) {
			if(this.getcontentsInThisEntity().contains(name))
				return false;
			if(type.equalsIgnoreCase("Folder"))
				this.addToFolders(new Folder(name, pathofParent));
			if(type.equalsIgnoreCase("ZipFile"))
				this.addToZipFiles(new ZipFile(name, pathofParent));
			if(type.equalsIgnoreCase("TextFile"))
				this.addToTextFiles(new TextFile(name, pathofParent));
			return true;
		}

	
	// checks if an entity is present 
	public Entity contains(Entity entity){
		if(entity.getType().equalsIgnoreCase("Folder"))
			if(this.getFolders().contains(entity.getName()))
				for(Folder folder:this.getFolders())
					if(folder.getName().equalsIgnoreCase(entity.getName()))
						return folder;
		if(entity.getType().equalsIgnoreCase("ZipFile"))
			if(this.getZipFiles().contains(entity.getName()))
				for(ZipFile zipFile:this.getZipFiles())
					if(zipFile.getName().equalsIgnoreCase(entity.getName()))
						return zipFile;
		if(entity.getType().equalsIgnoreCase("TextFile"))
			if(this.getTextFiles().contains(entity.getName()))
				for(TextFile textFile:this.getTextFiles())
					if(textFile.getName().equalsIgnoreCase(entity.getName()))
						return textFile;
		return null;
	}
	
	
	//removes an entity 
	public void removeEntity(Entity entity){
		if(entity.getType().equalsIgnoreCase("Folder"))
			if(this.getFolders().contains(entity.getName()))
				this.getFolders().remove(entity);
		
		if(entity.getType().equalsIgnoreCase("ZipFile"))
			if(this.getZipFiles().contains(entity.getName()))
				this.getZipFiles().remove(entity);
		
		if(entity.getType().equalsIgnoreCase("TextFile"))
			if(this.getTextFiles().contains(entity.getName()))
				this.getTextFiles().remove(entity);	
		
		this.getcontentsInThisEntity().remove(entity.getName());
	}
	
	//over ridden functions to use custom made objects with LinkedHashSet
	@Override
	public int compareTo(ZipFile zipFileObject){
		return this.getName().compareTo(zipFileObject.getName());
	}

	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + ((folders == null) ? 0 : folders.hashCode());
		result = prime * result + ((textFiles == null) ? 0 : textFiles.hashCode());
		result = prime * result + ((zipFiles == null) ? 0 : zipFiles.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj){
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ZipFile other = (ZipFile) obj;
		if (folders == null) {
			if (other.folders != null)
				return false;
		} else if (!folders.equals(other.folders))
			return false;
		if (textFiles == null) {
			if (other.textFiles != null)
				return false;
		} else if (!textFiles.equals(other.textFiles))
			return false;
		if (zipFiles == null) {
			if (other.zipFiles != null)
				return false;
		} else if (!zipFiles.equals(other.zipFiles))
			return false;
		return true;
	}
	

}
