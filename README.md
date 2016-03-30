# FileSystem
In Java<br /><br />

######FILESYSTEM STRUCTURE: <br />
1. File System has set of Drives with the default C drive.<br />
2. Each Drive, Folder and ZipFiles can contain Folders, ZipFiles and TextFiles.<br />
3. ZipFiles are write protected.<br /><br />


######CLASSES:<br />
1. Abstract Class FileSystem.<br />
2. Abstract Class Entity that extends to FileSystem.<br />
3. Classes Drive, Folder, ZipFile and TextFile extends Entity.<br />
4. Classes for Custom Exceptions.<br /><br />

######THINGS TO NOTE:<br />
1. FileSystem creates a default C drive to serve the user well.<br />
2. To avoid duplicates, every Entity maintains a Set of Entity Names it contains.<br />
3. Size gets updated automatically when you write or change the contents of a text file.<br />
4. Size of an entity is calculated recursively with the entities they hold.<br />
5. Size of a textFile is equal to the size of its contents.<br />
6. Size of a Folder, Drive and zipFile is measured with the sum of sizes of its contents<br />
7. Size of a zipFile is always half its actual size.<br /><br />


######User can,<br />
1. create, move, delete any entity.<br />
2. write string to a Text File.<br /><br />

######User cannot,<br />
1. create, move, or delete any entities in/from a zipFile.<br />
2. set/update size of an entity.<br /><br />

