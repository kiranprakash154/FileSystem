package fileSystem;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws Exception_PathAlreadyExists, Exception_IllegalFileSystemOperation, Exception_PathNotFound {

		Scanner sc = new Scanner(System.in);
		System.out.println("1 -> Create");
		System.out.println("2 -> Delete");
		System.out.println("3 -> Move");
		System.out.println("4 -> WriteToFile ");
		System.out.println("5 -> Display File System ");
		int choice = sc.nextInt();
		while(true){
			switch(choice){
			case 1: System.out.println("What do you want to create ?");
			System.out.println("1 -> Drive");
			System.out.println("2 -> Folder");
			System.out.println("3 -> ZipFile");
			System.out.println("4 -> TextFile");
			int ch = sc.nextInt();
			switch(ch)
			{
		 	 case 1: System.out.println("Name: ");
			 String name = sc.nextLine();
			 System.out.println("Path: ");
			 String path = sc.nextLine();
			 EntityCaller caller = new EntityCaller();
			 caller.create("Drive", name, path);
			   break;
			 case 2: System.out.println("Name: ");
		     name = sc.nextLine();
			 System.out.println("Path: ");
			 path = sc.nextLine();
			 caller = new EntityCaller();
			 caller.create("Folder", name, path);
			   break;
			 case 3: System.out.println("Name: ");
			 name = sc.nextLine();
		  	 System.out.println("Path: ");
			 path = sc.nextLine();
			 caller = new EntityCaller();
			 caller.create("ZipFile", name, path);
			   break;
			 case 4: System.out.println("Name: ");
			 name = sc.nextLine();
			 System.out.println("Path: ");
			 path = sc.nextLine();
			 caller = new EntityCaller();
			 caller.create("TextFile", name, path);
			}
				break;
				
				
				
			default:
				break;
			}
		}

		


	}

}
