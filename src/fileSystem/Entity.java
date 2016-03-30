package fileSystem;

abstract class Entity extends FileSystem {

	public String type;
	public String name;
	public String pathofParent;
	public int size;
	
	//Function 1
   public void create(String type, String Name, String PathofParent) throws Exception_PathAlreadyExists,Exception_IllegalFileSystemOperation,Exception_PathNotFound
   {
	   boolean added = true;
	   
	   if(!(isAlphaNumeric(Name)))
    	   throw new Exception_IllegalFileSystemOperation("Invalid File Name, Only Alphanumeric is Accepted");
	   
	   if(type.equalsIgnoreCase("drive")){
		   if(PathofParent != null) //Drive cannot have any parent path, so it has to be null
   			throw new Exception_IllegalFileSystemOperation("Cannot create Drive in another Entity");
		   else
   			added = FileSystem.addToDrivesList(new Drive(Name));
	   }
	   if(!(type.equalsIgnoreCase("folder") || type.equalsIgnoreCase("zipfile") || type.equalsIgnoreCase("textfile")))
       throw new Exception_IllegalFileSystemOperation("Unrecognised Entity Type !");
       
       
    		 Entity parentEntity = getEntity(PathofParent);
    		 if(parentEntity == null) throw new Exception_PathNotFound("Path Not Found");
    		     		 
    		 if(parentEntity.getType().equalsIgnoreCase("Drive")){
    			 Drive driveRef = (Drive) parentEntity;
    			 added = driveRef.addEntity(type,Name,PathofParent);
    		 }
    		 else if(parentEntity.getType().equalsIgnoreCase("Folder")){
    			 Folder folderRef = (Folder) parentEntity;
    			 added = folderRef.addEntity(type,Name,PathofParent);
    		 }
    		 else if(parentEntity.getType().equalsIgnoreCase("ZipFile")){
    			 ZipFile zipFileRef = (ZipFile) parentEntity;
    			 added = zipFileRef.addEntity(type,Name,PathofParent);
    		 }
    		 if(!added) throw new Exception_PathAlreadyExists("Path Already Exists");
    			 
    			     			 
    			 
    }
       
   
   
 //Function 2
   public void delete(String completePath)throws Exception_PathNotFound {
	   
	   Entity parentEntity = getEntity(getParentPath(completePath));
	   Entity toBeDeleted = getEntity(completePath);
	   if(parentEntity == null || toBeDeleted== null) throw new Exception_PathNotFound("Path Not Found");
	   
	   if(parentEntity.getType().equalsIgnoreCase("Folder")){
		   Folder folderRef = (Folder) parentEntity;
		   folderRef.removeEntity(toBeDeleted);
	   }
	   else if(parentEntity.getType().equalsIgnoreCase("Drive")){
		   Drive driveRef = (Drive) parentEntity;
		   driveRef.removeEntity(toBeDeleted);
	   }
	   else if(parentEntity.getType().equalsIgnoreCase("ZipFile")){
		   ZipFile zipFileRef = (ZipFile) parentEntity;
		   zipFileRef.removeEntity(toBeDeleted);
	   }
   }
	   
	   
	   

//Function 3
   public void move(String sourcePath,String destinationPath)throws Exception_PathAlreadyExists,Exception_IllegalFileSystemOperation,Exception_PathNotFound {
   
//	   String sourceParentPath = getParentPath(sourcePath);
//	   String destinationParentPath = getParentPath(destinationPath);
	   
	   String[] tempSourcePath = sourcePath.split("/");
	   String sourceEntityName = tempSourcePath[tempSourcePath.length-1];
	   
 
	   Entity sourceParentEntity = getEntity(getParentPath(sourcePath));
	   if(sourceParentEntity == null)
		   throw new Exception_PathNotFound("Source Path Error. Please check.");
	   
	   Entity destinationParentEntity = getEntity(getParentPath(destinationPath));
	   if(destinationParentEntity == null)
		   throw new Exception_PathNotFound("Destination Path Error. Please check.");
	   
	   
	   if(sourceParentEntity.getType().equalsIgnoreCase("ZipFile"))
		   throw new Exception_IllegalFileSystemOperation("Cannot Move from a ZipFile. Sorry :(");
	   if(sourceParentEntity.getType().equalsIgnoreCase("TextFile"))
           throw new Exception_IllegalFileSystemOperation("TextFile Does not contain any entity.");
	   if(destinationParentEntity.getType().equalsIgnoreCase("ZipFile"))		   
		   throw new Exception_IllegalFileSystemOperation("Destination Cannot be a ZipFile.");
	   if(destinationParentEntity.getType().equalsIgnoreCase("TextFile"))		   
		   throw new Exception_IllegalFileSystemOperation("Destination Cannot be a TextFile.");
	   
	   // returns the entity to be moved
	   Entity entity = return_From_This_Entity(sourceParentEntity, sourceEntityName);
	   boolean added = addToEntity(entity,destinationParentEntity); // adds entity to destination parent entity
	   if(!added) throw new Exception_PathAlreadyExists("Path Already Exists");
	   
	   // we can move contents from a drive and a folder, not a zipfile.
	   // so we make sure that after copying the entity to its destination, we delete it in the source
	   if(sourceParentEntity.getType().equalsIgnoreCase("Drive")){
		   Drive driveRef = (Drive) sourceParentEntity;
		   driveRef.removeEntity(entity);
	   }
	   else if(sourceParentEntity.getType().equalsIgnoreCase("Folder")){
		   Folder folderRef = (Folder) sourceParentEntity;
		   folderRef.removeEntity(entity);
	   }
   }
   
 //Function 4
   /*writes to a given file 
   I have considered write as OverWrite. Hence, the contents will be overwritten*/
  public void writeToFile(String path,String content) throws Exception_PathNotFound,Exception_NotATextFile
  {	
  	Entity entity = getEntity(path);
  	if(entity == null) throw new Exception_PathNotFound("Path Not Found.");
  	
  	if(!(entity.getType().equalsIgnoreCase("TextFile"))) throw new Exception_NotATextFile("Not a TextFile");
	
	TextFile textFileRef = (TextFile) entity;
	textFileRef.setContent(content);
	
  }
  
  
  /*Start of HELPER functions*/
  
 // this function adds an entity into another entity
 private boolean addToEntity(Entity source, Entity destination){
	        if(source != null && destination != null){
	     	  if(destination.getType().equalsIgnoreCase("Folder"))
	     	  {
	     		 Folder destinationRef = (Folder) destination;
	     		 if(destinationRef.getcontentsInThisEntity().contains(source.getName()))
		     		  return false;		     		  
		         if(source.getType().equalsIgnoreCase("Folder"))
		     		  destinationRef.addToFolders((Folder)source);
		     	 if(source.getType().equalsIgnoreCase("ZipFile"))
		     		  destinationRef.addToZipFiles((ZipFile)source);
		     	 if(source.getType().equalsIgnoreCase("TextFile"))
		     		  destinationRef.addToTextFiles((TextFile)source);	
	     	  }
	     	 else if(destination.getType().equalsIgnoreCase("Drive"))
	     	  {
	     		  Drive destinationRef = (Drive) destination;
	     		 if(destinationRef.getcontentsInThisEntity().contains(source.getName()))
		     		  return false;
	     		 
	     		 if(source.getType().equalsIgnoreCase("Folder"))
	     			destinationRef.addToFolders((Folder)source);
	     		 if(source.getType().equalsIgnoreCase("ZipFile"))
	     			destinationRef.addToZipFiles((ZipFile)source);
	     		 if(source.getType().equalsIgnoreCase("TextFile"))
	     			destinationRef.addToTextFiles((TextFile)source);	
	     	  }
	     		  
	     	  }
	        return true;
 }
	     	 
	
 // returns the entity when given with a path
   private Entity getEntity(String path){
	   String[] pathArray = path.split("/");
	   return getEntity(pathArray,0,null);
   }
   
// returns the entity when given with a path array
   @SuppressWarnings("unused")
   private Entity getEntity(String[] path){
	   return getEntity(path,0,null);
   }
   
   /*recursively traverses through entities for the entity to be searched
   the above two functions redirects uses this function to get the entity*/
   private Entity getEntity(String[] path, int index, Entity entity)
   {
	   if(index>path.length)
		   return entity;
	   
	   if(index == 0){
			if(FileSystem.getDrivesList().contains(path[index])){
				for(Drive driveIter:FileSystem.getDrivesList())
					if(driveIter.getName().equals(path[0])){
						entity = driveIter;
						return getEntity(path, index+1,entity);
					}	
			}
	   }
	   else{
	       if(entity.getType().equalsIgnoreCase("Drive")){
	 	       Drive driveRef = (Drive) entity;
		       if(driveRef.getcontentsInThisEntity().contains(path[index])){
			      Entity tempEntity = return_From_This_Drive(driveRef, path[index]);
			      if(tempEntity != null)
				  return getEntity(path, index+1,tempEntity);
		       }
	        }
	       if(entity.getType().equalsIgnoreCase("Folder")){
	 	       Folder folderRef = (Folder) entity;
		       if(folderRef.getcontentsInThisEntity().contains(path[index])){
			      Entity tempEntity = return_From_This_Folder(folderRef, path[index]);
			      if(tempEntity != null)
				  return getEntity(path, index+1,tempEntity);
		       }
	        }
	       if(entity.getType().equalsIgnoreCase("ZipFile")){
	 	       ZipFile zipFileRef = (ZipFile) entity;
		       if(zipFileRef.getcontentsInThisEntity().contains(path[index])){
			      Entity tempEntity = return_From_This_ZipFile(zipFileRef, path[index]);
			      if(tempEntity != null)
				  return getEntity(path, index+1,tempEntity);
		       }
	        }
	   }
	   
	   return null ;
   }
   

   
   /*this function will return the path of the parent when given the complete path
    * Example: if completePath = C/folder1/zipFile1/textFile1
    * then, parentPath = C/folder1/zipFile1*/
   
   private String getParentPath(String completePath){	
		StringBuilder parentPath = new StringBuilder();
		int i= completePath.length()-1;
		while(completePath.charAt(i) != '/')
		i--;	
		
		int index = i-1;
		for(int j=0;j<=index; j++)
		parentPath.append(completePath.charAt(j));
		
		return parentPath.toString();
	}




   // searches for an Entity in the zipFile 
	private Entity return_From_This_ZipFile(ZipFile zipFileRef, String entityToBeReturned){
		if(zipFileRef != null){
			if(zipFileRef.getFolders().contains(entityToBeReturned))
				for(Folder folder:zipFileRef.getFolders())
					if(folder.getName().equalsIgnoreCase(entityToBeReturned))
						return folder;
			if(zipFileRef.getZipFiles().contains(entityToBeReturned))
				for(ZipFile zipFile:zipFileRef.getZipFiles())
					if(zipFile.getName().equalsIgnoreCase(entityToBeReturned))
						return zipFile;
			if(zipFileRef.getTextFiles().contains(entityToBeReturned))
				for(TextFile textFile:zipFileRef.getTextFiles())
					if(textFile.getName().equalsIgnoreCase(entityToBeReturned))
						return textFile;
		}
		return null;
	}


	// searches for an Entity in the Folder 
    private Entity return_From_This_Folder(Folder folderRef,String entityToBeReturned){
		
		if(folderRef.getFolders().contains(entityToBeReturned))
				for(Folder folder:folderRef.getFolders())
					if(folder.getName().equalsIgnoreCase(entityToBeReturned))
						return folder;
			if(folderRef.getZipFiles().contains(entityToBeReturned))
				for(ZipFile zipFile:folderRef.getZipFiles())
					if(zipFile.getName().equalsIgnoreCase(entityToBeReturned))
						return zipFile;
			if(folderRef.getTextFiles().contains(entityToBeReturned))
				for(TextFile textFile:folderRef.getTextFiles())
					if(textFile.getName().equalsIgnoreCase(entityToBeReturned))
						return textFile;
		
		return null;
	}


	// searches for an Entity in the Drive 
	private Entity return_From_This_Drive(Drive driveRef, String entityToBeReturned){
		if(driveRef != null)
		{
			if(driveRef.getFolders().contains(entityToBeReturned))
				for(Folder folder:driveRef.getFolders())
					if(folder.getName().equalsIgnoreCase(entityToBeReturned))
						return folder;
			if(driveRef.getZipFiles().contains(entityToBeReturned))
				for(ZipFile zipFile:driveRef.getZipFiles())
					if(zipFile.getName().equalsIgnoreCase(entityToBeReturned))
						return zipFile;
			if(driveRef.getTextFiles().contains(entityToBeReturned))
				for(TextFile textFile:driveRef.getTextFiles())
					if(textFile.getName().equalsIgnoreCase(entityToBeReturned))
						return textFile;
		 }
		return null;
	}
	
	
	/*searches for an Entity in the Entity
	uses the above three functions as helper functions based on the entity*/
    private Entity return_From_This_Entity(Entity entityRef, String entityToBeReturned)
    {
    Entity entity = null;
	
    if(entityRef.getType().equalsIgnoreCase("Drive"))
    	entity = return_From_This_Drive((Drive) entityRef, entityToBeReturned);
    	
    else if(entityRef.getType().equalsIgnoreCase("Folder"))
    	entity = return_From_This_Entity((Folder) entityRef, entityToBeReturned);
    
    else if(entityRef.getType().equalsIgnoreCase("ZipFile"))
    	entity = return_From_This_ZipFile((ZipFile) entityRef, entityToBeReturned);
    
    return entity;  
    }
    
    
    // checks if the given string is Alphanumeric and returns true on success.
	public boolean isAlphaNumeric(String name) 
    {
    	if((name.matches("[A-Za-z0-9]+"))) return true;
    	return false;
	}

	
	// getters and setters
    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPathofParent() {
		return pathofParent;
	}

	public void setPathofParent(String pathofParent) {
		this.pathofParent = pathofParent;
	}

	
	//over ridden functions to use custom made objects with LinkedHashSet
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((pathofParent == null) ? 0 : pathofParent.hashCode());
		result = prime * result + size;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Entity other = (Entity) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (pathofParent == null) {
			if (other.pathofParent != null)
				return false;
		} else if (!pathofParent.equals(other.pathofParent))
			return false;
		if (size != other.size)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

}




	
	

