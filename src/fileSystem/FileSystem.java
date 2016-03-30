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
	
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		Scanner sc = new Scanner(System.in);
//		System.out.println("Main Started");
//		System.out.println("1 -> Create");
//		System.out.println("2 -> Delete");
//		System.out.println("3 -> Move");
//		System.out.println("4 -> WriteToFile ");
//		System.out.println("5 -> Display File System ");
//		int choice = sc.nextInt();
//		
//		
//		while(true){
//			switch(choice){
//			case 1: 
//				break;
//			default:
//				break;
//			}
//		}
////		if(choice == 1){
////			System.out.println("Okay Let us create for you");
////			System.out.println("1 -> Drive");
////			System.out.println("2 -> Folder");
////			System.out.println("3 -> ZipFile");
////			System.out.println("4 -> TextFile");
////		}
//
//	}
	
}
