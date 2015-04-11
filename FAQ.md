# I inserted my Ducky into a Windows Computer and nothing happens? #
The Ducky’s LEDs are programmed to provide feedback to the user, flashing green LED usually means the computer and Ducky are talking to each other. A flashing red LED means the Ducky can’t read the SDcard.
Sometimes, the host OS is a bit slow and misses the Ducky’s commands while it is enumerating the device.

The Ducky’s button acts as a simple reply button in its default setting.

**Check the Ducky's Button is not stuck**

Try pushing the button on the Ducky… any lights? actions?

# My Ducky is flashing Red, what now? #
Take out the SDcard (it can be stiff first of all, so don’t worry), try reinserting the SDcard, or alternatively insert the card into an SDcard adapter/reader, and see if the host OS (Windows) can natively read the card.

If the host OS can read the card, re-insert it back into the Ducky and try again.

If the host OS can’t read the card, you may try re-formatting the card, or simply try another SDcard that you may have (commonly found in mobile phones, cameras, etc).

# When I plug in the Ducky, it does something weird, and executes everything on my desktop? #
The secret behind multi-OS support, was the timings in the USB stack – The Ducky is real fast.  As such the Ducky will start quacking commands as soon as it is inserted into the computer.  Try adding a wait command “DELAY 5000” as the first line in your Ducky Script.  This gives the host OS enough time to enumerate the Ducky as a HID keyboard.

**Note**: You may need to tweak the DELAY command depending on your system(s).

# I’m from X country, my language is not supported, the Ducky is pointless. #
Please don’t think like that.

The solution is simple. Get onto the forums http://forums.hak5.org and ask for support.  We can guide you through the process of creating a new key-map which will benefit everyone.  Without the community, this project cannot succeed.  We need you! And your feedback is welcomed!

# I think my Ducky is Dead? #

Don’t Panic!

Check the FAQ's above, you may want to try flashing the Ducky, to rule out software errors.

If this all fails, its likely a hardware fault/ Ducky has become damaged in the post (it can happen).

There is a decent Hak5 Returns/Exchange Policy, just contact shop@hak5.org.