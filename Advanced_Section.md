# Introduction #

Due to demand for complicated instructions and key-presses; that are not currently supported by duckyscript. I thought a quick introduction into coding a hard coded C version of instructions is beneficial for public consumption.  **The disadvantage**: This effectively hard codes the instructions into the firmware, and removes the flexibility of duckyscript, and ease of quickly producing new payloads.  **The Advantage**: It opens up the possibility for more complicated key-stroke combinations.  Like the Deep Freeze shortcut _eg. Ctrl+Alt+Shift+F6_

The main source for this branch can be found **[here](http://code.google.com/p/ducky-decode/source/browse/trunk/Source/Ducky_HID_HardCode.zip)**

Below, is a brief outline of the major code changes within main.c, that makes the advantages possible:

# main.c #

## process\_frame() ##
This is the main method that used to house the state machine.  Its the method that takes the list of predefined instructions (HID Codes), and spits them out onto the USB Bus

```
void process_frame(uint16_t framenumber)
{
	bool b_btn_state, sucess;
	static bool btn_last_state = false;
	static bool sequence_running = false;
	static uint8_t u8_sequence_pos = 0;
	uint8_t u8_value;
	static uint16_t cpt_sof = 0;

	if ((framenumber % 1000) == 0) {
		LED_On(LED1);
	}
	if ((framenumber % 1000) == 500) {
		LED_Off(LED1);
	}
	// Scan process running each 2ms
	cpt_sof++;
	if ((cpt_sof % 2) == 0) {
		return;
	}

	// Scan buttons on switch 0 to send keys sequence
	b_btn_state = (!gpio_get_pin_value(GPIO_PUSH_BUTTON_0)) ? true : false;
	if (b_btn_state != btn_last_state) {
		btn_last_state = b_btn_state;
		sequence_running = true;
	}

	// Sequence process running each period
	if (SEQUENCE_PERIOD > cpt_sof) {
		return;
	}
	cpt_sof = 0;

	if (sequence_running) {
		// Send next key
		u8_value = ui_sequence[u8_sequence_pos].u8_value;
		if (u8_value!=0) {
			if (ui_sequence[u8_sequence_pos].b_modifier) {
				if (ui_sequence[u8_sequence_pos].b_down) {
					sucess = udi_hid_kbd_modifier_down(u8_value);
				} else {
					sucess = udi_hid_kbd_modifier_up(u8_value);
				}
			} else {
				if (ui_sequence[u8_sequence_pos].b_down) {
					sucess = udi_hid_kbd_down(u8_value);
				} else {
					sucess = udi_hid_kbd_up(u8_value);
				}
			}
			if (!sucess) {
				return; // Retry it on next schedule
			}
		}
		// Valid sequence position
		u8_sequence_pos++;
		if (u8_sequence_pos >=
		sizeof(ui_sequence) / sizeof(ui_sequence[0])) {
			u8_sequence_pos = 0;
			sequence_running = false;
		}
	}
}

```
## ui\_sequence[.md](.md) ##
The second difference is the removal of the inject.bin file, and the additional of the ui\_sequence array.

This was taken straight from Atmel's HID Keyboard example; this is your list of predefined key actions.

**Most importantly it is NOT limited by the duckyscript constraints**

Here you can:
  * hold keys down indefinitely
  * build complicated key combos _eg. Ctrl+Alt+Shift+F6_

If you do not understand strut's, basically each array element has 3x fields:
  * boolean b\_modifier = is this key a modifier and currently down/pressed true/false?
  * boolean b\_down = is this button down/pressed true/false?
  * HID\_X = name of the key (US Language)

**Note:** For a valid key press you have to remember to double up on entities. For example:
  * keydown:{false,true,HID\_R},
  * keyup:{false,false,HID\_R},
Otherwise, the micro-controller will think the key is continuously pressed!

### Example ###
```
static struct {
	bool b_modifier;
	bool b_down;
	uint8_t u8_value;
}ui_sequence[] = {
	// Display windows menu
{true,true,HID_MODIFIER_LEFT_UI},
	// Launch Windows Command line
{false,true,HID_R},
{false,false,HID_R},
	// Clear modifier
{true,false,HID_MODIFIER_LEFT_UI},
	// Tape sequence "notepad" + return
{false,true,HID_N},
{false,false,HID_N},
{false,true,HID_O},
{false,false,HID_O},
{false,true,HID_T},
{false,false,HID_T},
{false,true,HID_E},
{false,false,HID_E},
{false,true,HID_P},
{false,false,HID_P},
{false,true,HID_A},
{false,false,HID_A},
{false,true,HID_D},
{false,false,HID_D},
{false,true,HID_ENTER},
{false,false,HID_ENTER},
	// Delay to wait "notepad" focus
{false,false,0}, // No key (= SEQUENCE_PERIOD delay)
{false,false,0}, // No key (= SEQUENCE_PERIOD delay)
{false,false,0}, // No key (= SEQUENCE_PERIOD delay)
{false,false,0}, // No key (= SEQUENCE_PERIOD delay)
{false,false,0}, // No key (= SEQUENCE_PERIOD delay)
{false,false,0}, // No key (= SEQUENCE_PERIOD delay)
{false,false,0}, // No key (= SEQUENCE_PERIOD delay)
	// Display "Atmel "
{true,true,HID_MODIFIER_RIGHT_SHIFT}, // Enable Maj
{false,true,HID_A},
{false,false,HID_A},
{true,false,HID_MODIFIER_RIGHT_SHIFT}, // Disable Maj
{false,true,HID_T},
{false,false,HID_T},
{false,true,HID_M},
{false,false,HID_M},
{false,true,HID_E},
{false,false,HID_E},
{false,true,HID_L},
{false,false,HID_L},
{false,true,HID_SPACEBAR},
{false,false,HID_SPACEBAR},
	// Display "AVR "
{false,true,HID_CAPS_LOCK}, // Enable caps lock
{false,false,HID_CAPS_LOCK},
{false,true,HID_A},
{false,false,HID_A},
{false,true,HID_V},
{false,false,HID_V},
{false,true,HID_R},
{false,false,HID_R},
{false,true,HID_CAPS_LOCK}, // Disable caps lock
{false,false,HID_CAPS_LOCK},
};
```