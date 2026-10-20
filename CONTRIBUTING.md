# Contributing to TV Master Service Remote

First off, thank you for considering contributing to **TV Master Service Remote**! 

Technicians and developers from across the world keep this database up-to-date with new TV chassis, motherboard models, and discrete IR codes.

---

## 🛠️ How Can You Help?

### 1. Adding New TV Service Menu Codes or Motherboards
Most contributors help by expanding our motherboard and service code database:
1. Open [`app/src/main/java/com/servicetv/remote/data/TvDatabase.kt`](app/src/main/java/com/servicetv/remote/data/TvDatabase.kt).
2. Locate the appropriate category (e.g., `Brand`, `Chinese Motherboards`, or `Discrete Keys`).
3. Add your motherboard number, access sequence, and known factory defaults.
4. If applicable, also update [`SERVICE_MENU_CODES_MASTER_GUIDE.md`](SERVICE_MENU_CODES_MASTER_GUIDE.md).

### 2. Improving IR Protocols
If you know timings or hex representations for other protocols (e.g., RC-5, RC-6, Sharp, Panasonic), check [`app/src/main/java/com/servicetv/remote/ir/`](app/src/main/java/com/servicetv/remote/ir/).

### 3. Reporting Bugs or Requesting Chassis Codes
- Check the [Issues tab](../../issues) to see if the chassis is already requested or discussed.
- Use our **Chassis / Code Request** template.

---

## 🧪 Development Workflow

1. Fork the repo and clone locally:
   ```bash
   git clone https://github.com/YOUR_USERNAME/Universal-TV-Service-Remote.git
   ```
2. Open the project in **Android Studio** (or use command line).
3. Verify your build:
   ```bash
   ./gradlew assembleDebug
   ```
4. Test the generated APK on a real device with an IR blaster.
5. Submit a Pull Request with a clear description of the TV brand or motherboard tested.

---

Thank you for helping technicians and makers worldwide! 🚀
