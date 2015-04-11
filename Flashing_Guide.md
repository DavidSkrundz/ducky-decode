# Programming/Flashing the Ducky #

## Windows ##
When it comes to programming the Duck you'll need these resources for Windows: http://code.google.com/p/ducky-decode/source/browse/trunk/Flash/Duck%20Programming.zip .

Additionally you may need JRE FLIP from http://www.atmel.com/tools/FLIP.aspx and be sure to use the drivers in the Programming.zip

**All relevant Flash files can be found in the SVN repo**

Microsoft Visual C++ Redistributable:
  * x86 - http://www.microsoft.com/en-gb/download/deails.aspx?id=5555
  * x64 - http://www.microsoft.com/en-gb/download/details.aspx?id=14632

### Installation ###
From a clean install on my system, I made the following steps:
  * Install Microsoft Visual C++ 2010 Redistributable
  * Install Flip
  * Install Atmel Driver

### Atmel Driver ###
I inserted the ducky in dfu-mode (holding the Ducky's button down, while inserting the Ducky at the same time)

When Windows couldnt find the driver, I did a manual install (sometimes a wizard will pop up, sometimes it wont)
  * Load Device Manager
  * Find Atmel DFU Device
  * Update Driver
  * Manual Install
  * Point Windows to the Atmel drivers from duck\_programming.zip

Or alternatively, you could try:

Control-Panel ->Hardware & Sound -> Add a device -> select atmel-dfu -> manually search for driver -> point to unzipped atmel-driver folder -> ok ->done

### Problems ###
**Signed Driver Warning**:

On Win7 I had a signed driver warning, but chose "install anyway". To successfully install the driver.

**Win7 wont allow me to install an unsigned driver**:

The Atmel driver is signed! You should not see this error!

But for reference:

Take a look at http://www.techspot.com/community/topics/how-to-install-use-unsigned-drivers-in-windows-vista-7-x64.127187/

**Windows says the driver is already installed!**

This message should be familiar

```
Device selection....................... PASS
Hardware selection..................... PASS
Opening port........................... AtLibUsbDfu: 3EB 2FF6 no device present.
```

Windows installs the wrong driver....

Perform these steps:
  * Load Device Manager
  * Find Atmel DFU Device
  * Update Driver
  * Manual Install
  * Point Windows to the Atmel drivers from duck\_programming.zip

Now you should get no errors:

```
Device selection....................... PASS
Hardware selection..................... PASS
Opening port........................... PASS
Reading Bootloader version............. PASS    1.0.2
Erasing................................ PASS
Selecting FLASH........................ PASS
Blank checking......................... PASS    0x00000 0x3ffff
Parsing HEX file....................... PASS    USB_v2.hex
WARNING: The user program and the bootloader overlap!
Programming memory..................... PASS    0x00000 0x07caf
Verifying memory....................... PASS    0x00000 0x07caf
Starting Application................... PASS    RESET   0
```

### Flashing ###
First insert the ducky while continuously keeping the little black button pressed.

This puts the ducky into _dfu-mode_; we need to be in this mode to update the firmware.

It's pretty simple, just execute:

```
program.bat newfirmware.hex 
```

## Unix/OSX ##
On the Unix/OSX side grab these nice shell scripts to dump existing and program new firmware.
Available here:
  * [dfu-programmer-0.5.4](http://downloads.sourceforge.net/project/dfu-programmer/dfu-programmer/0.5.4/dfu-programmer-0.5.4.tar.gz?r=http%3A%2F%2Fsourceforge.net%2Fprojects%2Fdfu-programmer%2F%3Fsource%3Ddlp&ts=1354995624&use_mirror=netcologne)

**Note**: There are reported problems with dfu programmer version 0.5.2, please try the latest version in the link provided above.
### Errors ###
When running make:
```
"Fatal Error: usb.h not found".
```
You have not installed the package libusb-dev (libusb-1.0-0-dev ubuntu) (libusb-devel OSX), either compile from source, or recommended solution: **use your package manager!**

### Flashing the Firmware ###

#### Dump(backup) current firmware ####

```
sudo ./dfu-programmer at32uc3b1256 dump >dump.bin
```

Don't forget to get the ducky working again:

```
sudo ./dfu-programmer at32uc3b1256 reset
```

#### Update ####

1st Erase ducky:

```
sudo ./dfu-programmer at32uc3b1256 erase
```

Update firmware:

```
sudo ./dfu-programmer at32uc3b1256 flash --suppress-bootloader-mem ducky-update.hex
```

Don't forget to get the ducky working again:

```
sudo ./dfu-programmer at32uc3b1256 reset
```