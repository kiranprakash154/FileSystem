package fileSystem;

import java.util.HashSet;
import java.util.Set;

abstract class FileSystem
{
	private static Set<Drive> drives = new HashSet<Drive>();
	
	// by default a C drive is created in the file system
	static
	{
		Drive defaultDrive = new Drive("C");
		addToDrivesList(defaultDrive);
		displayDrives();
	}
	
	public static Set<Drive> getDrivesList(){
		return drives;
	}
	

	//adds a drive to the set of drives
	public static boolean addToDrivesList(Drive drive){
		if(drives.contains(drive.getName()))
			return false;
		drives.add(drive);
		return true;
	}
	
	//displays the drives 
	public static void displayDrives() {
		for(Drive drive:getDrivesList())
			System.out.println("Drive: "+drive.getName());
	}
	

	
}
