package fileSystem;


import java.util.LinkedHashSet;
import java.util.Set;

public class Drive extends Entity implements Comparable<Drive>{
	
	private Set<Folder> folders;
	private Set<ZipFile> zipFiles;
	private Set<TextFile> textFiles;
	private Set<String> contentsInThisEntity;
	
	
	//constructors
	public Drive(){
		this.folders = new LinkedHashSet<Folder>();
		this.zipFiles = new LinkedHashSet<ZipFile>();
		this.textFiles = new LinkedHashSet<TextFile>();
		this.contentsInThisEntity = new LinkedHashSet<String>();
	}
	public Drive(String name){
		this.setName(name);
		this.setType(this.getClass().getName());
		this.folders = new LinkedHashSet<Folder>();
		this.zipFiles = new LinkedHashSet<ZipFile>();
		this.textFiles = new LinkedHashSet<TextFile>();
		this.contentsInThisEntity = new LinkedHashSet<String>();
	}
	
	
	
	// getters and setters
	
	public Set<String> getcontentsInThisEntity() {
		return this.contentsInThisEntity;
	}
	public void setcontentsInThisEntity(Set<String> contentsInThisEntity) {
		this.contentsInThisEntity = contentsInThisEntity;
	}
	public Set<Folder> getFolders() {
		return folders;
	}
	public void setFolders(Set<Folder> folders) {
		this.folders = folders;
	}
	public Set<ZipFile> getZipFiles() {
		return zipFiles;
	}
	public void setZipFiles(Set<ZipFile> zipFiles) {
		this.zipFiles = zipFiles;
	}
	public Set<TextFile> getTextFiles() {
		return textFiles;
	}
	public void setTextFiles(Set<TextFile> textFiles) {
		this.textFiles = textFiles;
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
		this.size = size;
	}
	public int getSize(){
		return this.size;
	}
	
	
	// add to entities stubs
	
	public void addToFolders(Folder folder){
		this.folders.add(folder);
		this.addToContentsInThisEntity(folder.getName());
	}
	public void addToZipFiles(ZipFile zipFile){
		this.zipFiles.add(zipFile);
		this.addToContentsInThisEntity(zipFile.getName());
	}
	public void addToTextFiles(TextFile textFile){
		this.textFiles.add(textFile);
		this.addToContentsInThisEntity(textFile.getName());
	}
	public void addToContentsInThisEntity(String entityName){
		this.contentsInThisEntity.add(entityName);
	}
	//adds an entity
	public void addEntity(Entity entity){
			if(entity.getType().equalsIgnoreCase("Folder"))
				if(!this.getFolders().contains(entity.getName()))
					this.getFolders().remove(entity);
			
			if(entity.getType().equalsIgnoreCase("ZipFile"))
				if(this.getZipFiles().contains(entity.getName()))
					this.getZipFiles().remove(entity);
			
			if(entity.getType().equalsIgnoreCase("TextFile"))
				if(this.getTextFiles().contains(entity.getName()))
					this.getTextFiles().remove(entity);	
			
			this.getcontentsInThisEntity().remove(entity.getName());
		}
		
	/*adds an entity when passed type, name and pathOfParent
		acts like a helper function to the constructor*/
		
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
	
	//removes an Entity 
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
		
		// checks if an entity is present in this entity
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
	
	
	//over ridden functions to use custom made objects with LinkedHashSet
	@Override
	public int compareTo(Drive drive){
		return this.getName().compareTo(drive.getName());
	}
	public int compareTo(String driveInString) {
		return this.getName().compareTo(driveInString);
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
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Drive other = (Drive) obj;
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
