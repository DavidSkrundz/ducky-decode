package org.hak5.projects.rubberducky.encoder;

/**
 * @author Apache
 */
public class Instruction {

	// 
	private String[] tempList = { null, null, null };
	private final int _NAMESPACE_	= 0;
	private final int _COMMAND_		= 1;
	private final int _ARGUMENTS_	= 2;

	public Instruction(String instruction) {
		
		String line[] = instruction.split(" ", 2);
		String command[] = line[0].split("::", 2);
		
		// Namespace and Command
		if (command.length == 1) {
			this.tempList[_NAMESPACE_] = "CORE";
			this.tempList[_COMMAND_] = command[0];
		} else {
			this.tempList[_NAMESPACE_] = command[0];
			this.tempList[_COMMAND_] = command[1];
		}
			
		// Arguments
		if (line.length == 2) {
			this.tempList[_ARGUMENTS_] = line[1];
		} else {
			this.tempList[_ARGUMENTS_] = null;
		}
	}

	/**
	 * @return The namespace of the instruction.
	 */
	public String Namespace() {
		return tempList[_NAMESPACE_].trim().toUpperCase();
	}

	/**
	 * @return The syntactic command of the instruction.
	 */
	public String Command() {
		return tempList[_COMMAND_].trim().toUpperCase();
	}

	/**
	 * @return The arguments for the instruction.
	 */
	public String Arguments() {
		return tempList[_ARGUMENTS_].trim();
	}
}
