package org.hak5.projects.rubberducky.plugins.ducklings;

/**
 * Title:			HAK5 - USB Rubber Duckling (SamplePack).
 * Version:			1.0.0.c
 *  
 * Original Author: Pete Matthews (Apache :: apachetech@live.co.uk)
 * Contributors:	Apache
 * 
 * Changelog:		vX.x.x.a (DD/MM/YYYY) - (Author) * Details.
 *                  ------------------------------------------
 * 					v1.0.0   (04/03/2013) - (Apache)
 * 										  -  * Initial Release.
 */
public class Macro {
	
	/**
	 * Private Fields
	 */

	private String mName;
	private String[] mDescription;
	private String mScript;
	private String mArguments;

	/**
	 * Constructor
	 */
	
	public Macro(String name, String args) {
		this.mName = name;
		this.mArguments = args;
	}

	/**
	 * Properties
	 */
	
	public String Name() {
		return mName;
	}
	public void setName(String name) {
		this.mName = name;
	}
	public String[] Description() {
		return mDescription;
	}
	public void setDescription(String desc) {
		char[] digit = desc.toCharArray();
		int count = 0;
		for (int i = 0; i < digit.length; i++) {
			count++;
			if (count > 70) {
				if (digit[i] == ' ') {
					digit[i] = '\n';
					count = 0;
				}
			}
		}
		this.mDescription = new String(digit).split("\n");
	}
	public String[] Run(String args) {
		return String.format(this.mScript, (Object[])(args).split(" ")).split("\n");
	}
	public void setScript(String script) {
		this.mScript = script;
	}
	public String Arguments() {
		String strArgs = "";
		for (String arg : this.mArguments.split(" ")) {
			strArgs += String.format("[%s] ", arg);
		}
			
		return strArgs;
	}
}
