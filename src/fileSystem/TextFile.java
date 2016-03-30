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
		return content;
	}
	public int getSize(){
		return this.size;
	}
	
	
	//setters
	public void setSize(int size) {
		this.size = size;
	}

	public void setContent(String content) {
		this.content = content;
		this.updateSize(content);
	}
	

	//updates this size once content has been changed
	private void updateSize(String content) {
		this.setSize(this.getSize()+content.length());
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
