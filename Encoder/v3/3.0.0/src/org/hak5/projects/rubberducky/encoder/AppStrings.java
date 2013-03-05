package org.hak5.projects.rubberducky.encoder;

public class AppStrings {

	public String helpStr = "Hak5  - Rubber Ducky Script Encoder v3.0.0\n\n"
			+ "The USB Rubber Ducky is an educational tool only. Hak5, the creator and\n" 
			+ "contributors of this encoder, nor anyone else associated with the development\n" 
			+ "of this device are in any was responsible or liable for misuse of this hardware.\n\n"
			+ "Usage: encoder -i [file ..]\t\t\tencode specified file\n"
			+ "   or: encoder -i [file ..] -o [file ..]\tencode to specified file\n"
			+ "\nArguments:\n"
			+ "   -i [file ..] \t\tInput File\n"
			+ "   -o [file ..] \t\tOutput File\n"
			+ "   -l [file ..] \t\tKeyboard Layout (uk/us/fr/pt or a path to a\n"
			+ "   \t\t\t\tproperties file)\n"
			+ "\nScript Commands:\n"
			+ "   ALT [key name] (ex: ALT F4, ALT SPACE)\n"
			+ "   CTRL | CONTROL [key name] (ex: CTRL ESC)\n"
			+ "   CTRL-ALT [key name] (ex: CTRL-ALT DEL)\n"
			+ "   CTRL-SHIFT [key name] (ex: CTRL-SHIFT ESC)\n"
			+ "   DEFAULT_DELAY [Time in ms * 10] (change the static delay between each command)\n"
			+ "   DELAY [Time in ms * 10] (delay the next command by <X>)\n"
			+ "   GUI | WINDOWS [key name] (ex: GUI r, GUI l)\n"
			+ "   REM [anything] (used to comment your code, no obligation :) )\n"
			+ "   ALT-SHIFT (swap language)\n"
			+ "   SHIFT [key name] (ex: SHIFT DEL)\n"
			+ "   STRING [any character of your layout]\n"
			+ "   REPEAT [Number] (Repeat last instruction N times)\n"
			+ "   [key name] (anything in the keyboard.properties)\n"
			+ "\nDucklings:\n"
			+ "   Ducklings are stored within the \"ducklings\" subdirectory. The encoder will\n"
			+ "   automatically load any duckling classes available and enable the stored\n"
			+ "   procedures and macros within them to be used.\n\n"
			+ "   Please be careful when using third party plugins. The creators of this\n"
			+ "   encoder cannot ensure the quality or integrity of any third party plugins.\n\n"
			+ "   Please read the documentation for each duckling to view the specific\n"
			+ "   syntax required. The basic syntax for any duckling is as folllows:\n\n" 
			+ "   NAMESPACE::COMMAND arguments\n\n"
			+ "   Any command without a namepace (<X>::) indicator will be read as a core\n"
			+ "   DuckScript instruction.\n\n"
			+ "   For more information on creating your own ducklings, please visit\n"
			+ "   the forums at: http://forums.hak5.org/index.php?/forum/56-usb-rubber-ducky/\n\n"
			+ "   -={ HAK5 :: TRUST YOUR MOTHERDUCKING TECHNOLUST }=-\n";
}
