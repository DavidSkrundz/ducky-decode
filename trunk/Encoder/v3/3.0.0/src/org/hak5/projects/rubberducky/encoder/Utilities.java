package org.hak5.projects.rubberducky.encoder;

import java.util.List;
import java.util.Properties;

public class Utilities {

	public static Properties layoutProps = new Properties();
	public static Properties keyboardProps = new Properties();

	public static byte[] charToBytes(char c) {
		return codeToBytes(charToCode(c));
	}

	public static String charToCode(char c) {
		String code;
		if (c < 128) {
			code = "ASCII_" + Integer.toHexString(c).toUpperCase();
		} else if (c < 256) {
			code = "ISO_8859_1_" + Integer.toHexString(c).toUpperCase();
		} else {
			code = "UNICODE_" + Integer.toHexString(c).toUpperCase();
		}
		return code;
	}

	public static byte[] codeToBytes(String str) {
		if (layoutProps.getProperty(str) != null) {
			String keys[] = layoutProps.getProperty(str).split(",");
			byte[] byteTab = new byte[keys.length];
			for (int j = 0; j < keys.length; j++) {
				String key = keys[j].trim();
				if (keyboardProps.getProperty(key) != null) {
					byteTab[j] = strToByte(keyboardProps.getProperty(key)
							.trim());
				} else if (layoutProps.getProperty(key) != null) {
					byteTab[j] = strToByte(layoutProps.getProperty(key).trim());
				} else {
					System.out.println("Key not found:" + key);
					byteTab[j] = (byte) 0x00;
				}
			}
			return byteTab;
		} else {
			System.out.println("Char not found:" + str);
			byte[] byteTab = new byte[1];
			byteTab[0] = (byte) 0x00;
			return byteTab;
		}
	}

	public static byte strToByte(String str) {
		if (str.startsWith("0x")) {
			return (byte) Integer.parseInt(str.substring(2), 16);
		} else {
			return (byte) Integer.parseInt(str);
		}
	}

	public static byte strInstrToByte(String instruction) {
		instruction = instruction.trim();
		if (keyboardProps.getProperty("KEY_" + instruction) != null)
			return strToByte(keyboardProps.getProperty("KEY_" + instruction));
		/* instruction different from the key name */
		if (instruction.equals("ESCAPE"))
			return strInstrToByte("ESC");
		if (instruction.equals("DEL"))
			return strInstrToByte("DELETE");
		if (instruction.equals("BREAK"))
			return strInstrToByte("PAUSE");
		if (instruction.equals("CONTROL"))
			return strInstrToByte("CTRL");
		if (instruction.equals("DOWNARROW"))
			return strInstrToByte("DOWN");
		if (instruction.equals("UPARROW"))
			return strInstrToByte("UP");
		if (instruction.equals("LEFTARROW"))
			return strInstrToByte("LEFT");
		if (instruction.equals("RIGHTARROW"))
			return strInstrToByte("RIGHT");
		if (instruction.equals("MENU"))
			return strInstrToByte("APP");
		if (instruction.equals("WINDOWS"))
			return strInstrToByte("GUI");
		if (instruction.equals("PLAY") || instruction.equals("PAUSE"))
			return strInstrToByte("MEDIA_PLAY_PAUSE");
		if (instruction.equals("STOP"))
			return strInstrToByte("MEDIA_STOP");
		if (instruction.equals("MUTE"))
			return strInstrToByte("MEDIA_MUTE");
		if (instruction.equals("VOLUMEUP"))
			return strInstrToByte("MEDIA_VOLUME_INC");
		if (instruction.equals("VOLUMEDOWN"))
			return strInstrToByte("MEDIA_VOLUME_DEC");
		if (instruction.equals("SCROLLLOCK"))
			return strInstrToByte("SCROLL_LOCK");
		if (instruction.equals("NUMLOCK"))
			return strInstrToByte("NUM_LOCK");
		if (instruction.equals("CAPSLOCK"))
			return strInstrToByte("CAPS_LOCK");
		/* else take first letter */
		return charToBytes(instruction.charAt(0))[0];
	}
	
	/**
	 * Add a number of bytes to the output file.
	 * 
	 * @param file
	 * @param byteTab
	 */
	public static void addBytes(List<Byte> file, byte[] byteTab) {
		for (int i = 0; i < byteTab.length; i++)
			file.add(byteTab[i]);
		if (byteTab.length % 2 != 0) {
			file.add((byte) 0x00);
		}
	}
}
