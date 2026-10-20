# 📺 TV Master Service Remote
### Industrial-Grade Universal TV Service Remote & Factory Menu Controller for Android

<p align="center">
  <img src="https://img.shields.io/badge/Platform-Android%205.0%2B%20(API%2021%2B)-brightgreen?style=for-the-badge&logo=android" alt="Android">
  <img src="https://img.shields.io/badge/Kotlin-2.0.21-purple?style=for-the-badge&logo=kotlin" alt="Kotlin">
  <img src="https://img.shields.io/badge/UI-Jetpack%20Compose%20%2B%20Material%203-blue?style=for-the-badge&logo=jetpackcompose" alt="Jetpack Compose">
  <img src="https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge" alt="License MIT">
  <img src="https://img.shields.io/badge/Hardware-Consumer%20IR%20Blaster-red?style=for-the-badge" alt="IR Blaster">
  <img src="https://img.shields.io/badge/PRs-Welcome-orange?style=for-the-badge" alt="PRs Welcome">
</p>

---

## 🌟 Overview

**TV Master Service Remote** is a powerful, open-source Android tool tailored for **electronics repair technicians, TV engineers, and hobbyists**. 

Retail television remotes intentionally omit **discrete factory and service keys**. When a technician needs to configure panel settings, fix flipped/mirror displays, adjust LVDS mapping, or troubleshoot firmware on consumer or universal boards, dedicated physical service remotes are often expensive or unavailable.

This application transforms any Android smartphone equipped with an **Infrared (IR) Blaster** into an all-in-one universal service tool capable of transmitting discrete service codes, firing automated macro sequences, looking up universal Chinese chassis codes, and transmitting raw Pronto Hex strings.

---

## ⚡ Key Features

- 🔑 **Discrete Service Mode Keys**: Direct transmission of factory-level keys unavailable on consumer remotes:
  - **LG**: `IN-START` (`0x20DFDF20`), `EZ-ADJUST` (`0x20DFFF00`), `IN-STOP` (`0x20DF55AA`), `POWER-ONLY` (`0x20DFFE01`).
  - **Samsung**: `FACTORY` (Device 7.7, Func 59), `3SPEED` (Device 7.7, Func 60) for unlocking region/LocalSet options.
  - **Sony**: `SERVICE` discrete sequence & test mode toggles.
- 🤖 **Automated Factory Menu Sequences (Macros)**: One-tap automated execution with microsecond-precise timings:
  - `Menu + 1 1 4 7` (Most common for Chinese boards)
  - `Input / Source + 2 5 8 0` (CVTE / Realtek / MStar)
  - `Input + 2 0 8` / `2 0 0 8`
  - `Input + 8 1 5` / `Menu + 8 1 5`
  - `Menu + 8 2 0 2` / `Menu + 0 0 0 0`
  - Samsung Standby Sequence: `[MUTE] → [1] → [8] → [2] → [POWER]`
  - Sony Standby Sequence: `[DISPLAY] → [5] → [VOL+] → [POWER]`
- 🎛️ **Universal Chassis & Motherboard Database**: Built-in lookup library for universal boards:
  - `TP.MS338.PC821`, `T.VST59.031`, `T.V56.03`, `TP.HV320.PB801`, `SKR.03`, `T.R83.03`
  - `CV338H-A42`, `CV182L`, `CV56BH`, `CVMV26L`, `TP.VST59.PB819`, `VS.TP53U61.2`, and more.
- 📡 **Bit-Accurate IR Modulation Engine**:
  - **NEC Protocol**: 32-bit standard & extended with carrier modulation at 38 kHz.
  - **Sony SIRC Protocol**: 12-bit, 15-bit, and 20-bit with carrier modulation at 40 kHz.
  - **Pronto Hex Converter**: Instant parsing and translation of raw 4-digit Pronto Hex arrays into alternating Mark/Space timings.
- 🎮 **Tactile D-Pad & Service Controls**: Modern dark-themed UI built with **Jetpack Compose** and **Material 3**, featuring haptic feedback for precise navigation inside factory menus.
- 🛠️ **Custom Hex / Discrete Code Sender**: Test and send custom IR codes on the fly.
- 🌐 **Interactive Web Preview**: Includes a standalone, offline-ready web version under `/web_preview` for rapid testing and demonstrations in any modern web browser.

---

## 📱 Hardware Requirements

To transmit physical IR signals, you will need:
1. **An Android smartphone with a built-in Infrared Emitter (IR Blaster)**:
   - Supported brands: **Xiaomi, Redmi, Poco** (e.g., Redmi Note series, Poco F/X series), select **Huawei/Honor**, and select older **Samsung Galaxy / HTC** devices.
2. **Alternative for non-IR phones**:
   - Supports external 3.5mm headphone jack or USB-C IR transmitter dongles.

> [!NOTE]
> Even on devices without an IR blaster, the app opens seamlessly, allowing technicians to look up motherboard combinations, read technician guides, and view code references.

---

## 📺 Supported TV Brands & Chassis

| Brand / Chassis | Protocols Supported | Dedicated Service Keys | Macro Sequences Supported |
| :--- | :--- | :--- | :--- |
| **LG Electronics** | NEC (38 kHz) | In-Start, Ez-Adjust, In-Stop, Power-Only | Direct Discrete Key |
| **Samsung** | NEC / Pronto Hex (38 kHz) | Factory, 3Speed, Info, Factory Reset | Mute + 1 + 8 + 2 + Power |
| **Sony** | SIRC (40 kHz, 12/15/20-bit) | Service, Test, Wide, Digital | Display + 5 + Vol+ + Power |
| **TCL / Thomson** | NEC (38 kHz) | Factory, Design Menu | Contrast 9735, Contrast 1950 |
| **Philips** | RC5 / NEC (36-38 kHz) | SAM, CSM, Factory | 062596 + Info / Menu |
| **Panasonic** | Panasonic (37 kHz) | Service Mode 1 & 2 | Vol- + Info (3 times) |
| **Universal Boards** | NEC Standard / Extended | Factory Key, Panel Set | 1147, 2580, 208, 815, 8202 |

---

## 🚀 Quick Start & Installation

### Option 1: Direct APK Download
1. Head over to the [**Releases**](../../releases) tab.
2. Download the latest `app-debug.apk` or `app-release.apk`.
3. Sideload the APK onto your Android phone and install it.

### Option 2: Build From Source
Clone the repository and build using Gradle:

```bash
# Clone the repository
git clone https://github.com/R3bing/Universal-TV-Service-Remote.git
cd Universal-TV-Service-Remote

# Build Debug APK
./gradlew assembleDebug

# Output APK path:
# app/build/outputs/apk/debug/app-debug.apk
```

---

## 🏗️ Project Architecture

```
Universal-TV-Service-Remote/
├── app/
│   ├── src/main/
│   │   ├── java/com/servicetv/remote/
│   │   │   ├── data/
│   │   │   │   └── TvDatabase.kt             # Brand codes & motherboard database
│   │   │   ├── ir/
│   │   │   │   ├── IrBlasterManager.kt       # Android ConsumerIrManager interface
│   │   │   │   ├── MacroExecutor.kt          # Timed IR sequence execution engine
│   │   │   │   ├── NecEncoder.kt             # NEC 32-bit modulation
│   │   │   │   ├── SonySircEncoder.kt        # Sony SIRC 12/15/20-bit modulation
│   │   │   │   └── ProntoHexConverter.kt     # Pronto Hex string interpreter
│   │   │   ├── model/
│   │   │   │   └── TvModels.kt               # Data structures and entities
│   │   │   ├── ui/
│   │   │   │   ├── components/               # Custom D-Pad, Hex Dialog, Service Menu
│   │   │   │   ├── screens/RemoteScreen.kt   # Main Compose Remote interface
│   │   │   │   └── theme/                    # Material 3 Color Schemes & Styling
│   │   │   └── MainActivity.kt               # Android entry point
│   │   └── res/                              # Drawables, layouts, and adaptive icons
│   └── build.gradle.kts                      # Module build configuration
├── web_preview/                              # Standalone HTML5 / PWA Web Simulator
├── SERVICE_MENU_CODES_MASTER_GUIDE.md        # Comprehensive technician manual
└── README.md
```

---

## ⚠️ Technician Disclaimer

> [!WARNING]
> Accessing television service menus enables low-level modification of EEPROM, panel timing parameters, LVDS bit depth, and backlight voltages. **Incorrect adjustments can cause panel blanking or boot loops.** Always record initial values before modifying any factory options. Use the `IN-STOP` function with extreme caution as it performs an irreversible NVRAM wipe.

---

## 🤝 Contributing

Contributions are warmly welcomed! If you have discrete IR codes for missing brands or new Chinese motherboards:
1. Fork the Project.
2. Create your Feature Branch (`git checkout -b feature/NewMotherboardSupport`).
3. Commit your changes (`git commit -m 'Add support for TP.MT5522.PC821 chassis'`).
4. Push to the Branch (`git push origin feature/NewMotherboardSupport`).
5. Open a Pull Request.

---

## 📄 License

Distributed under the **MIT License**. See [`LICENSE`](LICENSE) for more information.

---

<p align="center">
  Crafted with ❤️ for Electronics Technicians and Open Source enthusiasts worldwide.
</p>
