package fileSystem;



public class TextFile extends Entity implements Comparable<TextFile>{
	
	private String content;
	
	
	//constructors
	public TextFile(){
		this.content = null;
	}
	public TextFile(String name,String pathOfParent){
		this.setName(name);
		this.setPathofParent(pathOfParent);
	}
	
	
	//getters
	public String getContent() {
		return this.content;
	}
	public int getSize(){
		this.initSize();
		return this.size;
	}
	
	// initializes the size
	private void initSize() {
		this.size = this.content.length();
	}
	
	//setters

	public void setContent(String content) {
		this.content = content;
		this.size = this.content.length();
	}

	//over ridden functions to use custom made objects with HashSet
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		TextFile other = (TextFile) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		return true;
	}

	@Override
	public int compareTo(TextFile textFileObject) {
		return this.getName().compareTo(textFileObject.getName());
	}
	

}
