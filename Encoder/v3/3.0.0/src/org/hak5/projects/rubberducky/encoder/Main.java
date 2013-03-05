package org.hak5.projects.rubberducky.encoder;

/**
 * Title:			HAK5 - USB Rubber Ducky Script Encoder.
 * Version:			3.0.0
 *  
 * Original Author: Jason Appelbaum
 * Contributors:	Midnitesnake, Dnucna, Apache
 * 
 * Changelog:		vX.x.x (DD/MM/YYYY)  - (Author) * Details.
 *                  ------------------------------------------
 * 					v1.0.0 (08/10/2011)	 - (Jason Appelbaum)
 * 										 -  * Initial Release.
 * 
 * 					v2.0.0 (18/10/2012)	 - (Dnucna)
 * 										 -  * New Major Release.
 * 
 * 					v2.1.0 (TODO) 		 - TODO
 * 
 * 					v2.2.0 (01/03/2013)	 - (Midnitesnake)
 * 										 -  * Added COMMAND syntax.
 * 
 * 					v2.3.0 (05/02/2013)	 - (Midnitesnake)
 * 										 -  * Added ALT-SHIFT syntax.
 * 
 *					v2.4.0 (01/03/2013)	 - (Midnitesnake)
 *										 -  * Added REPEAT syntax.
 *
 *					v3.0.0 (05/03/2013)	 - (Apache)
 *										 -  * New major release.
 *										 -  * Restructured code layout.
 *										 -  * Added basic OOP functionality.
 *										 -  * Added Duckling support (dynamically loaded plugins).
 *										 -  * Added basic defensive capabilities to code throughout.
 *										 -  * Split code into more easily manageable files.
 *										 -  * Added code annotation and documentation.
 *										 -  * Added SamplePack.duckling.jar file.
 *										 -  * Added __DEBUG_MODE__ switch to encoder.
 *										 -  * Added output text to show progress to user.
 */

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.rtf.RTFEditorKit;

import org.hak5.projects.rubberducky.plugins.ducklings.IDuckling;

/**
 * TODO: Overall Defensive Strategies (Ongoing):
 * 
 * There seems to be a lot of unhandled or poorly handled exceptions within the
 * code. It does still allow malformed scripts to pass through, ending up with 0
 * byte inject.bin files, whilst saying the scripts were handled ok at all
 * stages.
 * 
 * This will become more of an issue as the ducklings are rolled out, due to
 * user-code being imported into the program. Defensive strategies will more
 * than likely form the next few minor and revision updates.
 */

/**
 * @author Jason Appelbaum, Midnitesnake, Dnucna, Apache
 */
public class Main {

	// Version number.
	private static String version = "v3.0.0";

	// Debug mode switch.
	private static boolean __DEBUG_MODE__ = false;

	// Byte array for the output file.
	private static List<Byte> file = new ArrayList<Byte>();

	// Application Strings (allows for localisation at a later date).
	private static AppStrings strings = new AppStrings();

	// Dictionary of "duckling" plugins.
	// <NAMESPACE, INSTANCE_OF_PLUGIN_CLASS>
	private static Map<String, IDuckling> Ducklings = new TreeMap<String, IDuckling>();

	/**
	 * Main function.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		// Declare files paths.
		String inputFile = null;
		String outputFile = "inject.bin";
		String layoutFile = null;

		// Handle command line arguments.
		if (args.length == 0) {
			System.out.println(strings.helpStr);
			System.exit(0);
		}
		for (int i = 0; i < args.length; i++) {
			try {
				switch (args[i]) {

				/**
				 * GUI
				 */

				case "--gui":
					System.out
							.println("Are you a Java progammer?\n\n"
									+ "Help us build a GUI for the Duck Encoder. Contact us on the forum at:\n"
									+ "http://forums.hak5.org/index.php?/forum/56-usb-rubber-ducky/");
					System.exit(0);
					break;

				/**
				 * Debug Mode.
				 */

				case "--debug":
					__DEBUG_MODE__ = true;
					break;

				/**
				 * Decode Mode.
				 */

				case "--d":
					System.out
							.println("Decoding to be implemented at a later date.");
					System.exit(0);
					break;

				/**
				 * Input File.
				 */

				case "-i":
					if (args[i + 1].startsWith("-"))
						throw new ArrayIndexOutOfBoundsException();
					inputFile = args[++i];
					break;

				/**
				 * Keyboard mapping.
				 */

				case "-l":
					if (args[i + 1].startsWith("-"))
						throw new ArrayIndexOutOfBoundsException();
					layoutFile = args[++i];
					break;

				/**
				 * Output file.
				 */

				case "-o":
					if (args[i + 1].startsWith("-"))
						throw new ArrayIndexOutOfBoundsException();
					outputFile = args[++i];
					break;

				/**
				 * Help/Default listing.
				 */
				default:
				case "--help":
				case "-h":
					System.out.println(strings.helpStr);
					System.exit(0);
				}
			} catch (ArrayIndexOutOfBoundsException ex) {
				System.out.println(ex.getMessage());
				System.out.println("\n\n" + strings.helpStr);
				System.exit(0);
			}
		}

		System.out
				.println("\n-={ HAK5 :: TRUST YOUR MOTHERDUCKING TECHNOLUST }=-");
		System.out.printf("\nUSB Rubber Ducky Encoder %s\n", version);
		System.out.printf("\nScript: %s\n", inputFile);

		// Import the contents of the input file.
		if (inputFile != null) {
			String scriptStr = null;
			System.out.print("\nLoading DuckScript ..... \t\t");
			if (inputFile.contains(".rtf")) {
				try {
					FileInputStream stream = new FileInputStream(inputFile);
					RTFEditorKit kit = new RTFEditorKit();
					Document doc = kit.createDefaultDocument();
					kit.read(stream, doc, 0);
					scriptStr = doc.getText(0, doc.getLength());
					System.out.println("[ OK ]");
				} catch (IOException e) {
					System.out.println("[ FAILED ]");
					if (__DEBUG_MODE__) {
						e.printStackTrace();
					}
					System.exit(-1);
				} catch (BadLocationException e) {
					System.out.println("[ FAILED ]");
					if (__DEBUG_MODE__) {
						e.printStackTrace();
					}
					System.exit(-1);
				}
			} else {
				DataInputStream in = null;
				try {
					File f = new File(inputFile);
					byte[] buffer = new byte[(int) f.length()];
					in = new DataInputStream(new FileInputStream(f));
					in.readFully(buffer);
					scriptStr = new String(buffer);
					System.out.println("[ OK ]");
				} catch (IOException e) {
					System.out.println("[ FAILED ]");
					if (__DEBUG_MODE__) {
						e.printStackTrace();
					}
					System.exit(-1);
				} finally {
					try {
						in.close();
					} catch (IOException e) { /* ignore it */
					}
				}
			}

			// Dynamically load plugins.
			System.out.print("Spawning Ducklings ..... \t\t");
			loadDucklings(new File("ducklings"));
			System.out.println("[ OK ]");

			// Configure keyboard settings.
			loadProperties((layoutFile == null) ? "us" : layoutFile);

			// Encode the script and compile to binary.
			encodeToFile(scriptStr, outputFile);

			System.out.println("\nEncoding Complete!\n");
		}
	}

	/**
	 * Recursively load any duckling plugins from a given base directory.
	 * 
	 * @param dir
	 */
	private static void loadDucklings(File dir) {
		/**
		 * TODO: Duckling Management (Ongoing).
		 * 
		 * - Duckling Limitations:
		 * 
		 * Ducklings work by resolving a single line entry into a multi-line
		 * script snippet, inline with where it needs to be in the code. It is
		 * possible to use multiple ducklings within the same script, it is even
		 * possible to nest duckling macros inside each other, so long as all
		 * dependency files are available when the script is encoded. The main
		 * limitation is that the REPEAT command will merely repeat the last line 
		 * of the stored procedure, not the macro itself. This will be fixed in a
		 * later revision.
		 * 
		 * - Defensive Strategies:
		 * 
		 * The ducklings all derive from the IDuckling interface, so are
		 * imported into the program strongly typed. There are minor chances of
		 * exploitation within this, however, their main role as an ancillary
		 * plugin will not affect the overall performance of the encoder through
		 * exploitation. The best defensive strategy here is an all or nothing
		 * approach. If any errors occur, dump and exit.
		 * 
		 * - Breaking Issues:
		 * 
		 * There are possible breaking issues regarding rolling out the
		 * ducklings. In future releases, namespace structure changes, changes
		 * to the package/folder structure, changes to the IDuckling interface
		 * or the Macro object and any extensive overhauling of duckling
		 * management will be classed as breaking changes, resulting in possible
		 * loss of function and/or fatal errors when importing older versions of
		 * any duckling plugins. Backwards compatibility, or some other form of
		 * version management may need to be implemented in this case to resolve
		 * any breaking issues.
		 * 
		 * - Break Testing:
		 * 
		 * This code is still experimental and alpha testing seems to show that
		 * it can be quite temperamental at times. Changes made during the alpha
		 * beta crossover have significantly increased reliability, however,
		 * malformed/poorly written ducklings and any user changes to the
		 * ducklings package structure still result in the expected total loss
		 * of function. Unit testing suggests keeping as much of the background
		 * work away from user space to allow for the least amount of user-born
		 * corruption of files.
		 */
		if (dir.exists() && dir.isDirectory()) {
			for (File f : dir.listFiles()) {
				if (f.getName().endsWith(".duckling.jar")) {
					try {
						ClassLoader cl = ClassLoader.getSystemClassLoader();
						URLClassLoader ucl = URLClassLoader.newInstance(
								new URL[] { f.toURI().toURL() }, cl);
						IDuckling duckling = (IDuckling) ucl
								.loadClass(
										"org.hak5.projects.rubberducky.plugins.ducklings.Duckling")
								.newInstance();
						Ducklings.put(duckling.getNamespace(), duckling);
					} catch (Exception e) {
						// Bad Stuff Happens: Exit the program, lose one
						// level.
						System.out.println("[ FAILED ]");
						if (__DEBUG_MODE__) {
							e.printStackTrace();
						}
						System.exit(-1);
					}
				} else if (f.isDirectory()) {
					loadDucklings(f);
				}
			}
		}
	}

	/**
	 * Load keyboard and localisation settings.
	 * 
	 * @param lang
	 */
	private static void loadProperties(String lang) {
		InputStream in;
		ClassLoader loader = ClassLoader.getSystemClassLoader();
		try {
			System.out.print("Generating Keyboard Settings ..... \t");
			in = loader.getResourceAsStream("keyboard.properties");
			if (in != null) {
				Utilities.keyboardProps.load(in);
				in.close();
			} else {
				System.out.println("[ FAILED ]");
				System.exit(-1);
			}
			System.out.println("[ OK ]");
		} catch (IOException e) {
			System.out.println("[ FAILED ]");
			if (__DEBUG_MODE__) {
				e.printStackTrace();
			}
			System.exit(-1);
		}

		try {
			System.out.print("Localising Keyboard Settings ..... \t");
			in = loader.getResourceAsStream(lang + ".properties");
			if (in != null) {
				Utilities.layoutProps.load(in);
				in.close();
			} else {
				if (new File(lang).isFile()) {
					Utilities.layoutProps.load(new FileInputStream(lang));
				} else {
					System.out.println("[ FAILED ]");
					System.exit(-1);
				}
			}
			System.out.println("[ OK ]");
		} catch (IOException e) {
			System.out.println("[ FAILED ]");
			if (__DEBUG_MODE__) {
				e.printStackTrace();
			}
			System.exit(-1);
		}

	}

	/**
	 * Convert the input script to binary and save.
	 * 
	 * @param inStr
	 * @param fileDest
	 */
	private static void encodeToFile(String inStr, String fileDest) {

		// CRLF fix.
		inStr = inStr.replaceAll("\\r", "");

		// Encode the input script.
		processInstructionList(inStr.split("\n"));

		// Write byte array to file
		byte[] data = new byte[file.size()];
		for (int i = 0; i < file.size(); i++) {
			data[i] = file.get(i);
		}
		try {
			System.out.print("Writing Binary File ..... \t\t");
			File someFile = new File(fileDest);
			FileOutputStream fos = new FileOutputStream(someFile);
			fos.write(data);
			fos.flush();
			fos.close();
			System.out.println("[ OK ]");
		} catch (Exception e) {
			System.out.println("[ FAILED ]");
			if (__DEBUG_MODE__) {
				e.printStackTrace();
			}
			System.exit(-1);
		}

	}

	/**
	 * Recursively convert macros to basic syntax.
	 * 
	 * @param tmpList
	 * @param instructions
	 */
	private static void preProcess(List<Instruction> tmpList,
			String[] instructions) {
		for (String rawInstruction : instructions) {
			if (rawInstruction.substring(0, 2).equals("//"))
				continue;
			try {
				Instruction instruction = new Instruction(rawInstruction);
				if (instruction.Namespace() == "CORE") {
					tmpList.add(instruction);
				} else { // Recursively inject macro into instruction list.
					if (Ducklings.containsKey(instruction.Namespace())) {
						preProcess(
								tmpList,
								Ducklings.get(instruction.Namespace())
										.RunMacro(instruction.Command(),
												instruction.Arguments()));
					} else {
						System.out.println("[ FAILED ]");
						System.out
								.println("Cannot find "
										+ instruction.Namespace()
										+ " namespace.\n"
										+ "Please check the required duckling is in place and try again.");
						System.exit(-1);
					}
				}
			} catch (Exception e) {
				if (__DEBUG_MODE__) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Process the input script, line by line.
	 * 
	 * @param instructions
	 */
	private static void processInstructionList(String[] instructions) {

		// Initialise and pre-process the instruction list.
		List<Instruction> instructionList = new ArrayList<Instruction>();
		System.out.print("Preprocessing Script ..... \t\t");
		preProcess(instructionList, instructions);
		System.out.println("[ OK ]");

		// Encode each instruction in turn.
		System.out.print("Encoding Script ..... \t\t\t");
		for (Instruction instruction : instructionList) {
			try {
				// Repeat <#> functionality.
				int loop = 1;
				boolean repeat = false;

				// Delay functionality.
				int defaultDelay = 0;
				boolean delayOverride = false;

				// While loop for repeat functionality.
				while (loop > 0) {

					// Repeat last action if needed.
					if (repeat && instructionList.indexOf(instruction) > 0) {
						instruction = instructionList.get(instructionList
								.indexOf(instruction) - 1);
					}

					/**
					 * The Big Ungainly Line by Line Switch for Handling Instructions Thing.
					 * 
					 * The BULLSHIT is in alphabetical order, with the command's alternative
					 * forms as nested cases underneath the main command name.
					 * 
					 *  - Development
					 *  
					 *  I would like to restructure this BULLSHIT as a separate class
					 *  which would include a public parser and keep the actual encoding
					 *  methods private. There doesn't seem to be a "delegate" option
					 *  in Java, as there is in C# without using reflection, which
					 *  would be best avoiding if possible. This would make it a lot
					 *  easier to add syntax (including basic stored procedures) later
					 *  on, instead of merely adding them to the switch statement. To
					 *  do this though, it would mean a rethink of how the encoding is
					 *  handled at each stage.
					 */
					switch (instruction.Command()) {

					/**
					 * ALT Command
					 */

					case "ALT":
						if (instruction.Arguments() != null) {
							file.add(Utilities.strInstrToByte(instruction
									.Arguments()));
							file.add(Utilities
									.strToByte(Utilities.keyboardProps
											.getProperty("MODIFIERKEY_ALT")));
						} else {
							file.add(Utilities
									.strToByte(Utilities.keyboardProps
											.getProperty("KEY_LEFT_ALT")));
							file.add((byte) 0x00);
						}
						break;

					/**
					 * ALT-SHIFT Command
					 */

					case "ALT-SHIFT":
						file.add(Utilities.strToByte(Utilities.keyboardProps
								.getProperty("KEY_LEFT_ALT")));
						file.add((byte) (Utilities
								.strToByte(Utilities.keyboardProps
										.getProperty("MODIFIERKEY_LEFT_ALT")) | Utilities
								.strToByte(Utilities.keyboardProps
										.getProperty("MODIFIERKEY_SHIFT"))));
						break;

					/**
					 * COMMAND Command
					 */

					case "COMMAND":
						if (instruction.Arguments() == null) {
							file.add(Utilities
									.strToByte(Utilities.keyboardProps
											.getProperty("KEY_COMMAND")));
							file.add((byte) 0x00);
						} else {
							file.add(Utilities.strInstrToByte(instruction
									.Arguments()));
							file.add(Utilities.strToByte(Utilities.keyboardProps
									.getProperty("MODIFIERKEY_LEFT_GUI")));
						}
						break;

					/**
					 * CTRL Command
					 */

					case "CTRL":
					case "CONTROL":
						if (instruction.Arguments() != null) {
							file.add(Utilities.strInstrToByte(instruction
									.Arguments()));
							file.add(Utilities
									.strToByte(Utilities.keyboardProps
											.getProperty("MODIFIERKEY_CTRL")));
						} else {
							file.add(Utilities
									.strToByte(Utilities.keyboardProps
											.getProperty("KEY_LEFT_CTRL")));
							file.add((byte) 0x00);
						}
						break;

					/**
					 * CTRL-ALT Command
					 */

					case "CTRL-ALT":
					case "CONTROL-ALT":
						if (instruction.Arguments() != null) {
							file.add(Utilities.strInstrToByte(instruction
									.Arguments()));
							file.add((byte) (Utilities
									.strToByte(Utilities.keyboardProps
											.getProperty("MODIFIERKEY_CTRL")) | Utilities
									.strToByte(Utilities.keyboardProps
											.getProperty("MODIFIERKEY_ALT"))));
						}
						break;

					/**
					 * CTRL-SHIFT Command
					 */

					case "CTRL-SHIFT":
					case "CONTROL-SHIFT":
						if (instruction.Arguments() != null) {
							file.add(Utilities.strInstrToByte(instruction
									.Arguments()));
							file.add((byte) (Utilities
									.strToByte(Utilities.keyboardProps
											.getProperty("MODIFIERKEY_CTRL")) | Utilities
									.strToByte(Utilities.keyboardProps
											.getProperty("MODIFIERKEY_SHIFT"))));
						}
						break;

					/**
					 * DEFAULT_DELAY Command
					 */

					case "DEFAULT_DELAY":
					case "DEFAULTDELAY":
						defaultDelay = Integer
								.parseInt(instruction.Arguments());
						delayOverride = true;
						break;

					/**
					 * DELAY Command
					 */

					case "DELAY":
						int delay = Integer.parseInt(instruction.Arguments());
						while (delay > 0) {
							file.add((byte) 0x00);
							if (delay > 255) {
								file.add((byte) 0xFF);
								delay = delay - 255;
							} else {
								file.add((byte) delay);
								delay = 0;
							}
						}
						delayOverride = true;
						break;

					/**
					 * GUI Command
					 */

					case "GUI":
					case "WINDOWS":
						if (instruction.Arguments() == null) {
							file.add(Utilities.strToByte(Utilities.keyboardProps
									.getProperty("MODIFIERKEY_LEFT_GUI")));
							file.add((byte) 0x00);
						} else {
							file.add(Utilities.strInstrToByte(instruction
									.Arguments()));
							file.add(Utilities.strToByte(Utilities.keyboardProps
									.getProperty("MODIFIERKEY_LEFT_GUI")));
						}
						break;

					/**
					 * REM Command
					 */

					case "REM":
					case "COMMENT":
						/* no default delay for the comments */
						delayOverride = true;
						continue;

						/**
						 * REPEAT Command
						 */

					case "REPEAT":
						loop = Integer.parseInt(instruction.Arguments()) + 1;
						repeat = true;
						break;

					/**
					 * SHIFT Command
					 */

					case "SHIFT":
						if (instruction.Arguments() != null) {
							file.add(Utilities.strInstrToByte(instruction
									.Arguments()));
							file.add(Utilities
									.strToByte(Utilities.keyboardProps
											.getProperty("MODIFIERKEY_SHIFT")));
						} else {
							file.add(Utilities
									.strToByte(Utilities.keyboardProps
											.getProperty("KEY_LEFT_SHIFT")));
							file.add((byte) 0x00);
						}
						break;

					/**
					 * STRING Command
					 */

					case "STRING":
						for (int j = 0; j < instruction.Arguments().length(); j++) {
							char c = instruction.Arguments().charAt(j);
							Utilities.addBytes(file, Utilities.charToBytes(c));
						}
						break;

					/**
					 * Default Generic Command
					 */

					default:
						file.add(Utilities.strInstrToByte(instruction.Command()));
						file.add((byte) 0x00);
					}
					loop--;
				} // End of command switch.

				// Default delay
				if (!delayOverride & defaultDelay > 0) {
					int delayCounter = defaultDelay;
					while (delayCounter > 0) {
						file.add((byte) 0x00);
						if (delayCounter > 255) {
							file.add((byte) 0xFF);
							delayCounter = delayCounter - 255;
						} else {
							file.add((byte) delayCounter);
							delayCounter = 0;
						}
					}
				}
			} catch (Exception e) {
				System.out.println("[ FAILED ]");
				System.out.println("Error on Line: "
						+ (instructionList.indexOf(instruction) + 1));
				if (__DEBUG_MODE__) {
					e.printStackTrace();
				}
				System.exit(-1);
			}
		}
		System.out.println("[ OK ]");
	}
}