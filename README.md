# InterBunny
##### Intranet through Internet

It's a simple HTTP tunneler, allowing you to access simple HTTP site of intranet from outside the range i.e. through the public Internet.

##### Configuration
1. Host _index.php_ to a server.
2. Update _server.java_
  * Change `static String WEB_URL = "http://localhost/InterBunny/"` value to your hosted server address.
  * Compile it. `javac server.java`
3. Forward all compiled `*.class` or server.java to someone who has access to intranet.
4. __Hosting Intranet__
  * Ask the person to run `java InterBunny` after compilation.
  * Pick 'Y' when ask for hosting a network.
  * Done!
  * `Ctrl + C` whenever want to stop sharing.
5. __Accessing Intranet__
  * Open you web browser, Change proxy setting of HTTP to '127.0.0.1' with port '7786'.
  * Run `java InterBunny` after compilation.
  * Pick 'N' when ask for hosting a network.
  * Done!
  * `Ctrl + C` whenever want to stop sharing and put back proxy setting to normal.
  * Note : You can also remove 'localhost, 127.0.0.1' from ignore proxy setting if you want to access locally hosted site on your friends network.

##### Note
These files are in beta state and not a very efficient way for the work. Use these at your own willingness. 
  

