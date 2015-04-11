# Introduction #
A popular set of questions on the forum are:

  * Is the Ducky ready-to-go when delivered?
  * What languages are supported?
  * Do I really need to re-flash the Ducky?
  * Which firmware works best?
  * What firmware should I use?

These will be answered in turn below:

# Is the Ducky ready-to-go? #
**Yes** - The Ducky is pre-built with the _duck.hex_ firmware.

# What languages are supported #
## Firmware ##
The Firmware is designed to be language independent.

## Encoder ##
This is where different languages/keyboard mappings come into effect.  Currently supported keyboards are:
  * US (United States)
  * UK (United Kingdom)
  * DE (German)
  * DK (Danish)
  * FR (French)
  * CA (Canadian)
  * CH (Swiss)
  * BE (Belgian)
  * NO (Norwegian)
  * PT (Portuguese)
  * SV (Swedish)
  * IT (Italian)
  * ES (in development)
  * RU (in development)

If your language/keyboard is not here get on the forums for support and guidance on creating new keyboard mappings.

# Do I really need to re-flash the Ducky #
**No, not really.**

It depends on your purpose for investing in the Ducky.  See below for a description on each firmware image.
# Which Firmware works best/Which should I use? #
This is not an easy question to answer.

See below for descriptions, reasons, and hints:
## duck.hex (Duck(Original)) ##
Original duck firmware, enhanced to work on all Operating System's (Win,Unix,OSX,Android,+).

## usb.hex (FAT Duck) ##
With support appearing to originally dwindle, and a private side project.  USB.hex turns the Ducky into a USB Mass Storage Device.

Originally mocked, as useless; Some people missed the potential/purpose of this project.  Originally used to bypass device-control software that would black list/whitelist USB devices based off VID and PID codes.  As the Ducky is programmable, so-long-as a valid VID/PID device class was used, the Ducky could bypass popular device-control software.

Once I had my fun (few months to be honest), I decided to release this publicly, as I thought people were getting upset with the progress on the Ducky.  At least people could convert their Duck into a useful USB drive, rather than have a failed project stuck in a drawer (Folks had forked out originally $80(USD)).

## m\_duck.hex (Detour Duck(formerly Naked Duck)) ##
Based off a request in the forum (Sorry cant remember who asked first?)

I spent many a month trying to work out if payloads could be delivered based on keyboard LEDs, or on push of a Keyboard button that triggers an LED (eg. CAPS\_Lock).

This firmware supports multiple-payloads:
  * inject.bin - default payload (will always run first)
  * inject2.bin - NUM\_LOCK
  * inject3.bin - CAPS\_LOCK
  * inject4.bin - SCROLL\_LOCK

Basically, _inject.bin_ will always be triggered on Ducky insertion.

_inject2/3/4.bin_ is triggered by ensuring only Num\_Lock/Caps\_Lock/Scroll\_Lock Keyboard LED is lit, followed by pushing the button on the Ducky.

This projects Firmware was originally nicknamed **The Naked Duck / Naked Ducky Edition** as the Ducky has to be naked for you to push the button and trigger the 2nd/3rd payload.  Version 2 developments, mean the Ducky can now trigger on solely lit LEDs (if multiple LEDs are lit the last LED will take priority)

### Intended Purpose ###
One Ducky; Supporting 2x Operating Systems, Or staged Payloads:
  * _inject.bin_ - default file (simple 1-liner "DELAY 5000")
  * _inject2.bin_ - Windows XP Script/ Payload 2
  * _inject3.bin_ - OSX Script / Payload 3
  * _inject4.bin_ - Windows 7 Script / Payload 4

#### Multi OS Support ####
So on Windows Host, ensure Num\_Lock is Lit, push the Ducky's button to deliver a Windows-based Payload.

On OSX, ensure Caps\_Lock is Lit, push the Ducky's button to deliver an OSX-based Payload.

#### Multi Payload Support ####
By default _inject.bin_ triggers on insertion of the Ducky.

You may depending on installed software (e.g. powershell) want to trigger one of two different payloads.
  * Windows 7+ - Use Num\_Lock for inject2.bin to utilise powershell
  * Windows XP - Use Caps\_Lock for inject3.bin to utilise other windows binaries (eg tftp to download payloads)

## c\_duck.hex (Twin Duck) ##
This was another one of my goals (in addition to several requests).  Got a working PoC in time for the 1-year anniversary! (Originally, I was holding this back as a X-mas gift.  But someone quick spotted it, as I was cleaning up the SVN).  So I decided to publish a little earlier.

Version 2 has added improvements - multi OS support, downside memory is limited to 4KB (2048 key presses).

Nicknamed **The Twin Duck** as it functions as 2x separate Duckies; its primary function is mass-storage, and the HID attack is auto-triggered.

## cm\_duck.hex alpha(unamed) ##
Requested after the success of Twin Duck (c\_duck.hex).  This firmware has a HID keyboard with two keyboard triggers and mass storage.

This firmware attempts to merge Twin and Detour Ducks into one firmware.
  * NUM\_Lock - Triggers inject.bin (limited to 2KB of instructions)
  * CAPS\_Lock - Triggers inject2.bin (limited to 2KB instructions)

### Special Requests ###
#### S001 ####
People were worried about driver load times affecting their payloads.  So S001 uses keyboard LEDs as the HID trigger; NUM/CAPS/SCROLL LOCK all trigger 1x payload _inject.bin_.

#### S002 ####
People were worried about driver load times affecting their payloads.  So S002 uses the Ducky's GPIO button as the HID trigger; for payload _inject.bin_.

## Why Composite Firmware? ##
Brings back the old Autorun/ USB-Switchblade attacks.

### Function ###
To access Programs on the SDcard you are advised to label the SDcard eg. _DUCKY_

#### Ubuntu ####
The USB partition is normally mounted at _/media/LABEL_ e.g. /media/Ducky

#### OSX ####
OSX will auto mount at _/Volumes/LABEL_ e.g. /Volumes/Ducky

#### Windows ####
Depending on the number of additional Drives/Partitions the Ducky could appear on E:\ or F:\

Use **wmic** to dynamically locate the right drive letter
```
for /f %d in ('wmic volume get driveletter^, label ^| findstr "DUCKY"') do set myd=%d
```
Then execute your payload (this example duck.bat)
```
%myd%/duck
```