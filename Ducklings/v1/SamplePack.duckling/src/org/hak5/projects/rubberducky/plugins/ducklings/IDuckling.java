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

public interface IDuckling {
	String getNamespace();
	String[] RunMacro(String macro, String args);
	Collection<Macro> Macros();
}
