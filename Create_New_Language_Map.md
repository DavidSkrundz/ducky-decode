## Language Pack Location ##
If you downloaded the SVN, it can be found at:

_trunk/Encoder/v2/resources_

## How Language Packs Work? ##
The main file is “keyboard.properties”, this file matches QWERTY ASCII characters to HID codes.
### Example: HID Mappings ###
```
KEY_A = 4
KEY_B = 5
KEY_C = 6
KEY_D = 7
…
```
Please read the file for a definitive list.

Then a country file (_xx.properties_), specifies your languages characters to key mappings from keyboard.properties
```
ASCII_41 = KEY_A, MODIFIERKEY_SHIFT
```

When your Ducky Script is read, the Encoder simply replaces the Ducky Script with the appropriate binary code.

This is then saved as a binary file (default _inject.bin_).

The Ducky reads this binary file, and sends the data as raw HID codes – thus emulating a USB Keyboard.

## Creating New Language Support(1) ##
Easy method.

Create a DuckyScript with your native chars.

Run the encoder, and you should have a list of missing char\_codes:

### Example ###
```
Char not found:ASCII_72 
Char not found:ASCII_70 
Char not found:ASCII_6F 
```

Simply create a properties file for your language e.g. uk.properites and fill in the missing codes

```
ASCII_72 = KEY_R
```

## Creating New Language Support (2) ##
**Optional Method**

You can either match up your characters, to those that appear on a QWERTY keyboard.

### Example 2 (Taken From de.properties) ###

```
ISO_8859_1_A7 = KEY3, MODIFIER_SHIFT
//167 § SECTION SIGN
```

So how do you know  § = ISO\_8859\_1\_A7?

Easy use an online charset map:
http://www.charset.org/charactersets.php

## Creating New Language Support (3) ##
**Optional Method**

### Example(Taken from uk.properties) ###
```
KEY_BACKSLASH = 100
```
## How do you discover HID codes? ##
The easiest method is to use a USB sniffer.
### Windows Software ###
  * Busdog (Open Source) http://code.google.com/p/busdog/
  * USBlyzer (Commercial, Trial) http://www.usblyzer.com/download.htm
### Linux Software ###
  * Wireshark (Open Source) http://www.wireshark.org/

Once you have installed an appropriate USB sniffer and your computer is ready.
  1. Start your USB Sniffer
  1. Put the sniffer into capture mode.
  1. Plug in a USB Keyboard
  1. Type a predefined sequence of keys.  BUT ensure you press the first and last key 5x.

**IMPORTANT**: Record you key strokes, this way its easy to work out the HID codes.  You should be able to easily identify the start and end because the same character/code should be repeated 5x in a row.
