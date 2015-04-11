# Introduction #

Rather than recompile the firmware to change the VID and PID of the Ducky.

The development Team have made it even easier to alter the VID (Vendor ID) & PID (Product ID) of the Ducky.

Upgrade to Version 2 Firmware for the enhancements!

## Warning ##

You need to keep the VID & PID within the same device class.  Eg keyboard for HID mode, USB Drive, for storage mode.

If you give the Ducky a completely different (or random) VID & PID such as a digital camera / webcam - the OS will load the wrong driver and the Ducky **will not work!**

## Version 2 Firmware ##
Version 2 Supports the emulation of a specific VID & PID read from a binary file: `vidpid.bin`.

`vidpid.bin` needs to be within the root `/` of the sdcard.

Simply use a hex editor to specify the 2byte VID followed by the 2byte PID

### Linux ###
Example: Setting VID & PID for composite device:
```
hexedit /media/Ducky/vidpid.bin 
00000000  03 eb 24 03                                       |..$.|
```

### WIndows ###
TODO

## Version 1 Firmware ##
### Locate the VID & PID ###
The default VID & PID is 03EB (VID) 2403 (PID)

Due to the Endianess of the hex file we need to search for **EB030324** - thats  reverse(03EB) + reverse (2403) = (EB03)(0324) = **EB030324**

```
hexdump -C usb.hex |grep "EB030324"
00010700  34 30 45 42 30 33 30 33  32 34 36 38 0d 0a 3a 31  |40EB03032468..:1|
```

### Hex Table ###
To understand the relationship between hex and decimal, please refer to the table in the link below:
  * http://www.asciitable.com

### Change the VID & PID ###
Now on Linux, we can easily change the PID to 2503. (or 0325 after being converted to hex = **\x30\x33\x32\x35** via _sed_

The following command is used to change the VID & PID, usb.hex is left in its default state (backup) usb1.hex will contain our new firmware with the VID /PID changed:
```
sed 's/\x45\x42\x30\x33\x30\x33\x32\x34/\x45\x42\x30\x33\x30\x33\x32\x35/g' < usb.hex >usb1.hex 
```

Now to check usb1.hex, for the VID/PID (03EB 2503):
```
hexdump -C usb1.hex |grep "EB030325"
00010700  34 30 45 42 30 33 30 33  32 35 36 38 0d 0a 3a 31  |40EB03032568..:1|
```