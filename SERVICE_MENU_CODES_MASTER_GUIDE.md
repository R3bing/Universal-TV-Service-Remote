# ⚡ Universal TV Master Service Remote - Technician Handbook
*Specialized for Indian Electronics Repair Technicians, Lamington Road & Universal Motherboards*

---

## 1. How the Android IR Remote Works
Modern Android smartphones equipped with a built-in Infrared Emitter (found on **Xiaomi, Redmi, Poco, and select Huawei/Samsung devices**) use the Android `ConsumerIrManager` system API to modulate physical infrared light at specific carrier frequencies (typically 38 kHz).

### Key Technical Parameters:
- **Permission**: `android.permission.TRANSMIT_IR`
- **Carrier Frequency**: 38,000 Hz (Sony uses 40,000 Hz, Philips RC5 uses 36,000 Hz).
- **Signal Format**: Alternating Mark (ON) and Space (OFF) durations in microseconds ($\mu s$).

---

## 2. Discrete Service Keys vs. Ordinary Remote Keys
Ordinary consumer remotes only transmit basic buttons: Power, Vol+/Vol-, Ch+/Ch-, Menu, Source, Digits.
**Manufacturers intentionally hide Discrete Factory Keys from retail remotes**:

| TV Brand | Secret Discrete Button | Hex / Protocol | Purpose / Function |
| :--- | :--- | :--- | :--- |
| **LG Electronics** | **IN-START** | NEC `0x20DFDF20` | Opens main hardware diagnostics & service menu |
| **LG Electronics** | **EZ-ADJUST** | NEC `0x20DFFF00` | Opens panel tool option, screen type, & mirror settings |
| **LG Electronics** | **IN-STOP** | NEC `0x20DF55AA` | ⚠️ **Deep Factory Reset** (Clears NVRAM) |
| **LG Electronics** | **POWER-ONLY** | NEC `0x20DFFE01` | Factory aging & white screen diagnostic test |
| **Samsung** | **FACTORY** | Pronto (Device 7.7, Func 59) | Discrete Service Mode key (opens factory overlay) |
| **Samsung** | **3SPEED** | Pronto (Device 7.7, Func 60) | Unlocks grayed-out **LocalSet** / Region options |

---

## 3. Automated Service Menu Sequences (Macros)

### 🇨🇳 Chinese Universal Motherboards (MStar, Realtek, CVTE, Amlogic)
*(Used in 80%+ of Vu, Micromax, Shinco, Onida, Lloyd, Sansui, Daiwa, CloudWalker, and assembled TVs)*

1. **`MENU + 1 1 4 7` (Most Popular / Standard)**:
   - **Boards**: `TP.MS338.PC821`, `T.VST59.031`, `T.V56.03`, `TP.HV320.PB801`, `SKR.03`, `T.R83.03`
   - **Action**: Enters Factory Setting to fix Panel, Mirror Image, and LVDS map.

2. **`INPUT / SOURCE + 2 5 8 0`**:
   - **Boards**: `CV338H-A42`, `CV182L`, `CV56BH`, `CVMV26L`, `TP.VST59.PB819`
   - **Action**: Opens Design Menu -> Panel Setting -> Mirror Enable / LVDS Bit Format.

3. **`INPUT + 2 0 8` / `2 0 0 8`**:
   - **Boards**: `VS.TP53U61.2`, `VS.TP53U71.2`, and low-cost Smart Android boards.

4. **`INPUT + 8 1 5` / `MENU + 8 1 5`**:
   - **Boards**: `TP.SK506.PB801`, `TP.SK508`, Smart Frameless LED TVs.

5. **`MENU + 8 2 0 2` / `MENU + 0 0 0 0`**:
   - **Boards**: Older CRT & Legacy LCD universal replacement cards.

6. **`INPUT + 3 1 3 8` / `6 4 8 3`**:
   - **Boards**: Skyworth, Haier, Changhong smart chassis.

---

### 🇰🇷 Samsung Smart TV (Tizen / QLED / Crystal UHD)
- **Standby Sequence**: Turn TV **OFF** (standby), then press:
  $$\mathbf{[MUTE]} \rightarrow \mathbf{[1]} \rightarrow \mathbf{[8]} \rightarrow \mathbf{[2]} \rightarrow \mathbf{[POWER]}$$
  The TV boots up directly into the blue factory service menu.
- **Live Sequence (While ON)**: Press **[INFO]** then tap **[FACTORY]** (discrete code). Once inside, tap **[3SPEED]** to unlock advanced grayed-out menus.

---

### 🇰🇷 LG Electronics (WebOS / OLED / NanoCell)
- Tap discrete **[IN-START]** or **[EZ-ADJUST]**.
- When the 4-digit PIN prompt appears on the TV screen, enter:
  - `0413` (Standard WebOS & OLED)
  - `0000` (Legacy / Older models)
  - `7777` or `8741` (Hotel / Commercial models)

---

### 🇯🇵 Sony Bravia (Google TV / Android / Bravia XR)
- Turn TV **OFF** into Standby. Press:
  $$\mathbf{[DISPLAY / INFO]} \rightarrow \mathbf{[5]} \rightarrow \mathbf{[VOL\text{+}UP]} \rightarrow \mathbf{[POWER]}$$
- **Self-Diagnostic Screen (Blink Code Counter)**:
  $$\mathbf{[DISPLAY / INFO]} \rightarrow \mathbf{[5]} \rightarrow \mathbf{[VOL\text{-}DOWN]} \rightarrow \mathbf{[POWER]}$$

---

### 🇨🇳 TCL / Thomson / iFFALCON
- Go to TV **Settings** $\rightarrow$ **Picture** $\rightarrow$ **Advanced Settings** $\rightarrow$ Highlight **Contrast**.
- Rapidly enter on the remote: `9 7 3 5` (or `1 9 5 0` for design menu).

---

## 4. Top 2 Common LED TV Technician Fixes

### Fix 1: Upside-Down / Inverted Picture ("Mirror Flip")
* **Cause**: New motherboard firmware default panel orientation does not match the physical LED panel mounting.
* **Fix**:
  1. Open Factory Menu using the remote code (e.g., `MENU 1147` or `INPUT 2580`).
  2. Navigate to **Panel Settings** or **Special Set**.
  3. Locate **Mirror Mode** (or **H-Mirror / V-Mirror**).
  4. Toggle from `OFF` to `ON` (or `NORMAL` to `MIRROR`).
  5. Turn the TV OFF with remote and power back on. The picture is now right-side up!

### Fix 2: Solarized / Negative / Distorted Colors ("LVDS JEIDA vs VESA")
* **Cause**: Mismatch between 6-bit/8-bit or JEIDA/VESA data mapping between mainboard and T-Con / Panel.
* **Fix**:
  1. Inside Factory Menu, open **Panel Settings**.
  2. Find **LVDS Map** (or **TI Mode** or **Bit Depth**).
  3. Toggle between `JEIDA` $\leftrightarrow$ `VESA` (or `8-bit` $\leftrightarrow$ `6-bit`).
  4. Screen instantly shifts from oil-slick/negative color into vibrant true color.

---

## 5. How to Compile into an `.apk` via Android Studio
1. Open **Android Studio**.
2. Select **Open** $\rightarrow$ Select folder `d:\remote`.
3. Allow Gradle to sync.
4. Click **Build $\rightarrow$ Build Bundle(s) / APK(s) $\rightarrow$ Build APK(s)**.
5. In 60 seconds, your installable `app-debug.apk` is created at:
   `app/build/outputs/apk/debug/app-debug.apk`.
6. Open WhatsApp Web on your PC, drag & drop `app-debug.apk` into your chat, and send it to your phone!
