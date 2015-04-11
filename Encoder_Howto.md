# Introduction #
The Duck Encoder Version 2.1/2.2 supports multiple languages through a properties file.

The properties file, simply maps a character from a charset( ASCII, ISO, UTF) to given key press or combination of key presses.

Download the svn:

```
svn checkout https://ducky-decode.googlecode.com/svn/ ducky-decode
```

The Encoder lives at:
```
./trunk/Encoder/v2/
```
# Details #

## Normal Operation ##
The normal operation defaults to the US keyboard mappings:
```
java -jar duckencoder.java -i input_file.txt -o inject.bin
```

## Language Support ##
This is provided with the _"-l"_ flag:

Linux:
```
-l <[2 letter country code]|[path to resources/country.properties file]>
```

Windows:
```
-l <[2 letter country code]|[path to resources\country.properties file]>
```
### Encoder - UK Example ###
The normal operation default to the US keyboard mappings:
```
java -jar duckencoder.java -l uk -i input_file.txt -o inject.bin
```

### Encoder - FR Example ###
The normal operation default to the US keyboard mappings:

Linux:
```
java -jar duckencoder.java -l resources/fr.properties -i input_file.txt -o inject.bin
```

Windows:
```
java -jar duckencoder.java -l resources\fr.properties -i input_file.txt -o inject.bin
```
**Note** : Backslash instead of forward slash

# Problems #
## Trying to run encoder.jar under OS X 10.5 (Leopard)-10.8 (Mountain Lion) ##
Error:
```
$ java -jar encoder.jar
Exception in thread "main" java.lang.UnsupportedClassVersionError: Encoder : Unsupported major.minor version 51.0
	at java.lang.ClassLoader.defineClass1(Native Method)
	at java.lang.ClassLoader.defineClassCond(ClassLoader.java:631)
	at java.lang.ClassLoader.defineClass(ClassLoader.java:615)
	at java.security.SecureClassLoader.defineClass(SecureClassLoader.java:141)
	at java.net.URLClassLoader.defineClass(URLClassLoader.java:283)
	at java.net.URLClassLoader.access$000(URLClassLoader.java:58)
	at java.net.URLClassLoader$1.run(URLClassLoader.java:197)
	at java.security.AccessController.doPrivileged(Native Method)
	at java.net.URLClassLoader.findClass(URLClassLoader.java:190)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:306)
	at sun.misc.Launcher$AppClassLoader.loadClass(Launcher.java:301)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:247)
```
Version of Java:
```
$ java -version
java version "1.6.0_37"
```
### Solution 1 ###
Update java to version 1.7.0
  * http://docs.oracle.com/javase/7/docs/webnotes/install/mac/mac-jdk.html
  * http://reviews.cnet.com/8301-13727_7-57423014-263/how-to-install-and-uninstall-java-7-for-os-x/

### Solution 2 ###
compile the program on Java 1.6.X

Compile the src:
```
$ cd ducky-decode/trunk/Encoder/v2/src
$ javavc Encoder.java
```
This should generate a class file, to run the code
```
$ java Encoder
```
if it runs, the jar file in ducky-decode/trunk/Encoder/v2/ is essentially a zip, mv encoder.class to this dir
```
$ cd ducky-decode/trunk/Encoder/v2/
$ mv src/Encoder.class ./
$ zip encoder.jar Encoder.class
```
then run it as normal
```
$ java -jar encoder.jar
```

## Null-Pointer Exception ##
Example:
```
java -jar encoder.jar -i script.txt -o inject.bin -l ru
Error with input file!
Exception in thread "main" java.lang.NullPointerException
        at Encoder.main(Encoder.java:107)
```
### Cause ###
The Encoder is detecting a SPACE/ non-printable character at the end of a line.
### Solution ###
  1. Ensure there are no spaces at the end of a line.
  1. Ensure your file is either plain-text or RTF