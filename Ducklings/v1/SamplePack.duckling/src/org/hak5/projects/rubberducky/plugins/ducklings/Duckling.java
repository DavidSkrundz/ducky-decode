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
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class Duckling implements IDuckling {

	/**
	 * TODO: Set the namespace for the duckling.
	 */
	private String mNamespace = "SAMPLE";

	/**
	 * TODO: Declare and initialise your macros.
	 */
	private void InitialiseMacros() {

		/**
		 * TODO: Declare each macro in turn and add it to the dictionary.
		 */
		Macro HelloWorld = new Macro("HELLOWORLD", "anything");
		HelloWorld
				.setDescription("Types \"Hello World!\", followed by an optional string on the next line.");
		HelloWorld.setScript("STRING Hello World!\nSTRING You said: %s");
		this.MacroList.put("HELLOWORLD", HelloWorld);
	}

	/**
	 * No need to edit below here.
	 */
	@Override
	public String[] RunMacro(String macro, String args)
			throws NullPointerException {
		if (MacroList.containsKey(macro)) {
			return MacroList.get(macro).Run(args);
		} else {
			throw new NullPointerException(String.format(
					"%s not found in the %s namespace.", macro,
					this.getNamespace()));
		}
	}

	@Override
	public Collection<Macro> Macros() {
		return this.MacroList.values();
	}

	@Override
	public String getNamespace() {
		return mNamespace;
	}

	public Duckling() {
		this.MacroList = new TreeMap<String, Macro>();
		InitialiseMacros();
	}

	private Map<String, Macro> MacroList;
}