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
public class Main {

	private static String Title = "Sample Duckling";
	private static String Version = "1.0.0.c";
	private static String Author = "Pete Matthews - ApacheTech Consultancy";

	public static void main(String[] args) {
		Duckling myDuckling = new Duckling();

		System.out.println("\n" + String.format("Duckling: \t%s\n", Title)
				+ String.format("Version: \t%s\n", Version)
				+ String.format("Author: \t%s\n", Author)
				+ String.format("Namespace: \t%s\n", myDuckling.getNamespace()));

		System.out.println("Syntax:");
		for (org.hak5.projects.rubberducky.plugins.ducklings.Macro m : myDuckling.Macros()) {
			System.out.println(String.format("  %s::%s %s", myDuckling.getNamespace(),
					m.Name(), m.Arguments()));
			System.out.println("    Description:");
			for (String line : m.Description()) {
				System.out.println("     * " + line);
			}
			System.out.println("    Script:");
			for (String line : m.Run(m.Arguments())) {
				System.out.println("     - " + line);
			}
		}
	}
}