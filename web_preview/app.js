/**
 * TV Master Service Remote - Universal IR Engine & Simulator
 */

// Master Database of TV Brands & Profiles
const TV_BRANDS = {
  lg: {
    name: "LG Electronics (WebOS / OLED / LCD)",
    defaultPins: ["0413", "0000", "7777", "8741"],
    instructions: "Point phone at TV. Tap IN-START or EZ-ADJUST. When password prompt appears, enter 0413 using remote numbers. Use D-pad to change settings. Press BACK to exit.",
    discreteKeys: [
      { id: "LG_IN_START", label: "IN-START", sub: "Service Menu", hex: "0000 006d 0022 0002 0155 00aa 0015 0015 0015 0015 0015 0040 ... (NEC 0x20DFDF20)" },
      { id: "LG_EZ_ADJUST", label: "EZ-ADJUST", sub: "Tool Option / Panel", hex: "0000 006d 0022 0002 0155 00aa ... (NEC 0x20DFFF00)" },
      { id: "LG_IN_STOP", label: "IN-STOP", sub: "Deep Factory Reset", danger: true, hex: "0000 006d 0022 0002 0155 00aa ... (NEC 0x20DF55AA)" },
      { id: "LG_POWER_ONLY", label: "POWER-ONLY", sub: "Aging Mode", hex: "0000 006d 0022 0002 0155 00aa ... (NEC 0x20DFFE01)" }
    ],
    macros: [],
    standardCodes: {
      POWER: 0x10, INPUT: 0x0B, MUTE: 0x09, UP: 0x40, DOWN: 0x41, LEFT: 0x07, RIGHT: 0x06, OK: 0x44,
      BACK: 0x28, HOME: 0x7C, MENU: 0x43, VOL_UP: 0x02, VOL_DOWN: 0x03, CH_UP: 0x00, CH_DOWN: 0x01,
      DIGIT_1: 0x11, DIGIT_2: 0x12, DIGIT_3: 0x13, DIGIT_4: 0x14, DIGIT_5: 0x15,
      DIGIT_6: 0x16, DIGIT_7: 0x17, DIGIT_8: 0x18, DIGIT_9: 0x19, DIGIT_0: 0x1A
    }
  },
  samsung: {
    name: "Samsung Electronics (Tizen / QLED)",
    defaultPins: ["0000", "1234"],
    instructions: "Method 1: Turn TV OFF into Standby. Tap Macro 'Mute + 1 + 8 + 2 + Power'. TV turns on with Service Menu.\nMethod 2: When TV is ON, tap FACTORY then 3SPEED to unlock advanced options.",
    discreteKeys: [
      { id: "SAMSUNG_FACTORY", label: "FACTORY", sub: "Discrete Service Key", hex: "0000 006D 0000 0022 00AC 00AB 0015 0041 ... (Function 59)" },
      { id: "SAMSUNG_3SPEED", label: "3SPEED", sub: "Unlock LocalSet", hex: "0000 006D 0000 0022 00AC 00AB ... (Function 60)" }
    ],
    macros: [
      { name: "Standby: MUTE + 1 + 8 + 2 + POWER", desc: "Boots TV from Standby directly into Service Menu", steps: ["MUTE", "DIGIT_1", "DIGIT_8", "DIGIT_2", "POWER"] },
      { name: "Live: INFO + FACTORY", desc: "Opens service overlay when TV is already powered on", steps: ["INFO", "SAMSUNG_FACTORY"] },
      { name: "Alternative: INFO + MENU + MUTE + POWER", desc: "Asian and European Samsung market models", steps: ["INFO", "MENU", "MUTE", "POWER"] }
    ],
    standardCodes: {
      POWER: 0x02, INPUT: 0x01, MUTE: 0x0F, UP: 0x60, DOWN: 0x61, LEFT: 0x65, RIGHT: 0x62, OK: 0x68,
      BACK: 0x58, HOME: 0x79, MENU: 0x1A, VOL_UP: 0x07, VOL_DOWN: 0x0B, CH_UP: 0x12, CH_DOWN: 0x10,
      DIGIT_1: 0x04, DIGIT_2: 0x05, DIGIT_3: 0x06, DIGIT_4: 0x08, DIGIT_5: 0x09,
      DIGIT_6: 0x0A, DIGIT_7: 0x0C, DIGIT_8: 0x0D, DIGIT_9: 0x0E, DIGIT_0: 0x11
    }
  },
  china_universal: {
    name: "China Universal Motherboards (MStar / RTD / CVTE)",
    defaultPins: ["1147", "2580", "815", "208", "8202", "0000"],
    instructions: "Used in 80%+ of Vu, Micromax, Shinco, Onida, Lloyd and assembled TVs in India. Press the macro matching your motherboard (e.g. TP.MS338 -> Menu 1147, CV338 -> Input 2580). Inside menu: fix upside-down screen via 'Mirror Mode', fix color distortion via 'LVDS Map'.",
    discreteKeys: [],
    macros: [
      { name: "MENU + 1 1 4 7 (Most Popular)", desc: "TP.MS338, T.VST59, T.V56, TP.HV320, SKR.03, T.R83", steps: ["MENU", "DIGIT_1", "DIGIT_1", "DIGIT_4", "DIGIT_7"] },
      { name: "INPUT / SOURCE + 2 5 8 0", desc: "CV182, CV338, CV56BH, CVMV26, TP.VST59.PB819", steps: ["INPUT", "DIGIT_2", "DIGIT_5", "DIGIT_8", "DIGIT_0"] },
      { name: "INPUT + 2 0 8", desc: "VS.TP53U71, VS.TP53U61 and Android combo boards", steps: ["INPUT", "DIGIT_2", "DIGIT_0", "DIGIT_8"] },
      { name: "INPUT + 8 1 5", desc: "TP.SK506 and Smart Android scaler boards", steps: ["INPUT", "DIGIT_8", "DIGIT_1", "DIGIT_5"] },
      { name: "MENU + 8 2 0 2", desc: "Legacy CRT & older China LCD boards", steps: ["MENU", "DIGIT_8", "DIGIT_2", "DIGIT_0", "DIGIT_2"] },
      { name: "INPUT + 3 1 3 8", desc: "Skyworth, Haier, Changhong motherboards", steps: ["INPUT", "DIGIT_3", "DIGIT_1", "DIGIT_3", "DIGIT_8"] }
    ],
    standardCodes: {
      POWER: 0x14, INPUT: 0x01, MUTE: 0x15, UP: 0x05, DOWN: 0x0D, LEFT: 0x06, RIGHT: 0x07, OK: 0x13,
      BACK: 0x17, HOME: 0x1F, MENU: 0x0F, VOL_UP: 0x12, VOL_DOWN: 0x10, CH_UP: 0x1A, CH_DOWN: 0x1B,
      DIGIT_1: 0x08, DIGIT_2: 0x09, DIGIT_3: 0x0A, DIGIT_4: 0x0B, DIGIT_5: 0x0C,
      DIGIT_6: 0x0E, DIGIT_7: 0x11, DIGIT_8: 0x18, DIGIT_9: 0x19, DIGIT_0: 0x00
    }
  },
  sony: {
    name: "Sony Bravia (Google TV / Android / Classic)",
    defaultPins: ["0000", "9999"],
    instructions: "Turn TV OFF into Standby. Tap 'Display + 5 + Vol+ + Power'. The TV switches on showing the Service & Diagnostics panel.",
    discreteKeys: [],
    macros: [
      { name: "Standby: DISPLAY + 5 + VOL_UP + POWER", desc: "Standard Sony Bravia Service Mode entry", steps: ["DISPLAY", "DIGIT_5", "VOL_UP", "POWER"] },
      { name: "Diagnostics: DISPLAY + 5 + VOL_DOWN + POWER", desc: "Error History & Self-Diagnostic Blink Counter", steps: ["DISPLAY", "DIGIT_5", "VOL_DOWN", "POWER"] },
      { name: "Smart: HOME + OK + VOL_UP + POWER", desc: "Newer models without direct digit buttons", steps: ["HOME", "OK", "VOL_UP", "POWER"] }
    ],
    standardCodes: {
      POWER: 0x15, INPUT: 0x25, MUTE: 0x14, UP: 0x74, DOWN: 0x75, LEFT: 0x33, RIGHT: 0x34, OK: 0x65,
      BACK: 0x23, HOME: 0x60, MENU: 0x60, VOL_UP: 0x12, VOL_DOWN: 0x13, CH_UP: 0x10, CH_DOWN: 0x11,
      DISPLAY: 0x3A,
      DIGIT_1: 0x00, DIGIT_2: 0x01, DIGIT_3: 0x02, DIGIT_4: 0x03, DIGIT_5: 0x04,
      DIGIT_6: 0x05, DIGIT_7: 0x06, DIGIT_8: 0x07, DIGIT_9: 0x08, DIGIT_0: 0x09
    }
  },
  tcl: {
    name: "TCL / Thomson / iFFALCON",
    defaultPins: ["1234", "0428"],
    instructions: "Navigate to TV Settings -> Picture -> Advanced Settings -> Highlight 'Contrast'. Then run the 9735 macro.",
    discreteKeys: [],
    macros: [
      { name: "Contrast + 9 7 3 5", desc: "Standard TCL Factory Service Menu", steps: ["DIGIT_9", "DIGIT_7", "DIGIT_3", "DIGIT_5"] },
      { name: "Design Menu: 1 9 5 0", desc: "TCL Engineering Design Menu", steps: ["DIGIT_1", "DIGIT_9", "DIGIT_5", "DIGIT_0"] }
    ],
    standardCodes: {
      POWER: 0x12, INPUT: 0x14, MUTE: 0x17, UP: 0x02, DOWN: 0x03, LEFT: 0x04, RIGHT: 0x05, OK: 0x07,
      BACK: 0x06, HOME: 0x24, MENU: 0x01, VOL_UP: 0x1A, VOL_DOWN: 0x1E, CH_UP: 0x1B, CH_DOWN: 0x1F,
      DIGIT_1: 0x08, DIGIT_2: 0x09, DIGIT_3: 0x0A, DIGIT_4: 0x0B, DIGIT_5: 0x0C,
      DIGIT_6: 0x0D, DIGIT_7: 0x0E, DIGIT_8: 0x0F, DIGIT_9: 0x10, DIGIT_0: 0x00
    }
  },
  philips: {
    name: "Philips (SAM / CSM Mode)",
    defaultPins: ["8888", "1234"],
    instructions: "While on home screen, tap SAM Macro: 0 6 2 5 9 6 + INFO.",
    discreteKeys: [],
    macros: [
      { name: "SAM: 0 6 2 5 9 6 + INFO", desc: "Service Alignment Mode (Panel parameters)", steps: ["DIGIT_0", "DIGIT_6", "DIGIT_2", "DIGIT_5", "DIGIT_9", "DIGIT_6", "INFO"] },
      { name: "CSM: 1 2 3 6 5 4", desc: "Customer Service Mode (Read-only status)", steps: ["DIGIT_1", "DIGIT_2", "DIGIT_3", "DIGIT_6", "DIGIT_5", "DIGIT_4"] }
    ],
    standardCodes: {
      POWER: 0x0C, INPUT: 0x38, MUTE: 0x0D, UP: 0x58, DOWN: 0x59, LEFT: 0x5A, RIGHT: 0x5B, OK: 0x5C,
      BACK: 0x0B, HOME: 0x0A, MENU: 0x0A, VOL_UP: 0x10, VOL_DOWN: 0x11, CH_UP: 0x20, CH_DOWN: 0x21,
      INFO: 0x0F,
      DIGIT_1: 0x01, DIGIT_2: 0x02, DIGIT_3: 0x03, DIGIT_4: 0x04, DIGIT_5: 0x05,
      DIGIT_6: 0x06, DIGIT_7: 0x07, DIGIT_8: 0x08, DIGIT_9: 0x09, DIGIT_0: 0x00
    }
  },
  panasonic: {
    name: "Panasonic (Viera)",
    defaultPins: ["0000"],
    instructions: "Hold Volume Down on physical TV button panel and trigger Info 3 times.",
    discreteKeys: [],
    macros: [
      { name: "Service Menu Macro (Info x 3)", desc: "Hold Vol- on TV and send this command", steps: ["INFO", "INFO", "INFO"] }
    ],
    standardCodes: {
      POWER: 0x3D, INPUT: 0x02, MUTE: 0x30, UP: 0x68, DOWN: 0x69, LEFT: 0x6A, RIGHT: 0x6B, OK: 0x73,
      BACK: 0x2B, HOME: 0x32, MENU: 0x32, VOL_UP: 0x20, VOL_DOWN: 0x21, CH_UP: 0x2C, CH_DOWN: 0x2D,
      INFO: 0x35,
      DIGIT_1: 0x01, DIGIT_2: 0x02, DIGIT_3: 0x03, DIGIT_4: 0x04, DIGIT_5: 0x05,
      DIGIT_6: 0x06, DIGIT_7: 0x07, DIGIT_8: 0x08, DIGIT_9: 0x09, DIGIT_0: 0x00
    }
  },
  xiaomi: {
    name: "Xiaomi / Mi / Redmi TV",
    defaultPins: ["0000"],
    instructions: "Standard NEC protocol. For recovery mode: Power off at plug, hold Back + OK on remote while powering on.",
    discreteKeys: [],
    macros: [],
    standardCodes: {
      POWER: 0x0C, INPUT: 0x1A, MUTE: 0x15, UP: 0x04, DOWN: 0x05, LEFT: 0x06, RIGHT: 0x07, OK: 0x0D,
      BACK: 0x14, HOME: 0x19, MENU: 0x18, VOL_UP: 0x10, VOL_DOWN: 0x11, CH_UP: 0x20, CH_DOWN: 0x21,
      DIGIT_1: 0x01, DIGIT_2: 0x02, DIGIT_3: 0x03, DIGIT_4: 0x04, DIGIT_5: 0x05,
      DIGIT_6: 0x06, DIGIT_7: 0x07, DIGIT_8: 0x08, DIGIT_9: 0x09, DIGIT_0: 0x00
    }
  }
};

// Chinese Motherboard Catalog (Lamington Road / Indian TV repair standard)
const MOTHERBOARDS = [
  { model: "TP.MS338.PC821 / PB801", chip: "MStar 6A338 (Android Smart)", code: "MENU + 1 1 4 7", mirror: "Special Set -> Panel Settings -> Mirror", lvds: "TI Mode (JEIDA/VESA)", notes: "Vu, Micromax, Shinco 32/40/43 inch" },
  { model: "CV338H-A42 / U42 / T42", chip: "CVTE Realtek (Smart Android)", code: "INPUT + 2 5 8 0", mirror: "Design Menu -> Panel Setting -> Mirror", lvds: "LVDS Map (8-bit / 6-bit)", notes: "Cloudwalker, Daiwa, Assembled 4K/FHD" },
  { model: "T.VST59.031 / PB819", chip: "TSUMV59XU (Non-Smart)", code: "MENU + 1 1 4 7", mirror: "Panel Adjust -> Mirror -> On/Off", lvds: "Panel Adjust -> LVDS Map", notes: "Most common replacement card in India" },
  { model: "VS.TP53U61.2 / U71.2", chip: "HiSilicon / AllWinner (Android)", code: "INPUT + 2 0 8", mirror: "System Setting -> Panel Config -> Flip", lvds: "Bit Mode", notes: "Low-cost Android 9/11 combo boards" },
  { model: "TP.SK506.PB801 / PC821", chip: "MStar 6683 (Android 9/11/12)", code: "INPUT + 8 1 5 or MENU + 1 1 4 7", mirror: "Factory Menu -> Panel Setting -> Flip Mode", lvds: "Color Format", notes: "32/43 frameless smart LED TVs" },
  { model: "CV56BH-Q42 / CVMV26L", chip: "MStar V56 (Non-Smart HD)", code: "INPUT + 2 5 8 0", mirror: "General Setting -> Panel -> Flip", lvds: "General Setting -> LVDS Format", notes: "Standard HD non-smart board" },
  { model: "TP.HV320.PB801", chip: "MStar V59 (Comb / Inverter)", code: "MENU + 1 1 4 7", mirror: "Panel Settings -> Mirror Image", lvds: "LVDS Map Mode", notes: "3-in-1 Power+Inverter+Motherboard" },
  { model: "T.V56.03 / SKR.03", chip: "TSUMV56 (Multi-Resolution)", code: "MENU + 1 1 4 7", mirror: "Panel Setting -> Mirror", lvds: "Panel Setting -> Map", notes: "USB firmware flashable" }
];

// Current State
let currentBrandKey = "china_universal";
let isTransmitting = false;

// DOM Elements
const brandSelect = document.getElementById("brandSelect");
const scopeDisplay = document.getElementById("scopeDisplay");
const scopeFreq = document.getElementById("scopeFreq");
const macroProgressBanner = document.getElementById("macroProgressBanner");
const macroStepText = document.getElementById("macroStepText");
const macroCountText = document.getElementById("macroCountText");
const macroProgressBar = document.getElementById("macroProgressBar");

// Modals
const serviceModal = document.getElementById("serviceModal");
const boardModal = document.getElementById("boardModal");
const hexModal = document.getElementById("hexModal");

// Vibration feedback helper
function triggerVibration() {
  if (navigator.vibrate) {
    navigator.vibrate(35);
  }
}

// NEC Signal Generator
function generateNecPattern(cmdCode) {
  const leaderPulse = 9000;
  const leaderSpace = 4500;
  const bitPulse = 560;
  const bit0Space = 560;
  const bit1Space = 1690;
  const stopPulse = 560;

  let pattern = [leaderPulse, leaderSpace];
  // 32-bit: Address 0x00, ~Address 0xFF, Command, ~Command
  const addr = 0x00;
  const addrInv = 0xFF;
  const cmd = cmdCode & 0xFF;
  const cmdInv = (~cmdCode) & 0xFF;

  const bytes = [addr, addrInv, cmd, cmdInv];
  bytes.forEach(b => {
    for (let i = 0; i < 8; i++) {
      const bit = (b >> i) & 1;
      pattern.push(bitPulse);
      pattern.push(bit === 1 ? bit1Space : bit0Space);
    }
  });
  pattern.push(stopPulse);
  return pattern;
}

// Display transmitted signal on Scope
function displaySignalOnScope(label, hexOrCmd, pattern) {
  triggerVibration();
  scopeFreq.textContent = "38.0 kHz";
  scopeDisplay.innerHTML = `<strong>TX:</strong> <span style="color:#00e5ff">${label}</span> [Cmd: 0x${(hexOrCmd || 0).toString(16).toUpperCase().padStart(2, '0')}] | <span style="color:#8b95a5">${pattern.length} Pulses:</span> [${pattern.slice(0, 8).join(', ')} ... +${pattern.length - 8} more]`;
}

// Transmit button key
function transmitKey(keyId) {
  const brand = TV_BRANDS[currentBrandKey];
  const cmd = brand.standardCodes[keyId];
  if (cmd !== undefined) {
    const pattern = generateNecPattern(cmd);
    displaySignalOnScope(keyId, cmd, pattern);
  } else {
    displaySignalOnScope(keyId, 0x00, generateNecPattern(0x00));
  }
}

// Execute Macro Sequence
async function runMacro(macro) {
  if (isTransmitting) return;
  isTransmitting = true;

  // Close modal if open
  serviceModal.classList.add("hidden");
  macroProgressBanner.classList.remove("hidden");

  const total = macro.steps.length;
  for (let i = 0; i < total; i++) {
    const stepKey = macro.steps[i];
    macroStepText.textContent = `Sending: ${stepKey}`;
    macroCountText.textContent = `[${i + 1}/${total}]`;
    macroProgressBar.style.width = `${((i + 1) / total) * 100}%`;

    transmitKey(stepKey);

    // Wait 280ms between key bursts
    await new Promise(r => setTimeout(r, 280));
  }

  macroStepText.textContent = `✔ Macro Finished: ${macro.name}`;
  setTimeout(() => {
    macroProgressBanner.classList.add("hidden");
    macroProgressBar.style.width = "0%";
    isTransmitting = false;
  }, 1200);
}

// Render Service Hub Modal
function renderServiceHub() {
  const brand = TV_BRANDS[currentBrandKey];
  document.getElementById("serviceModalSubtitle").textContent = brand.name;

  // Discrete keys
  const discreteSection = document.getElementById("discreteSection");
  const discreteGrid = document.getElementById("discreteButtonsGrid");
  discreteGrid.innerHTML = "";
  if (brand.discreteKeys && brand.discreteKeys.length > 0) {
    discreteSection.classList.remove("hidden");
    brand.discreteKeys.forEach(k => {
      const btn = document.createElement("button");
      btn.className = `discrete-key-btn ${k.danger ? 'danger-btn' : ''}`;
      btn.innerHTML = `<span>${k.label}</span><span class="key-sub">${k.sub}</span>`;
      btn.onclick = () => {
        if (k.danger) {
          if (!confirm(`WARNING: ${k.label} performs a hardware factory NVRAM reset. Transmit?`)) return;
        }
        displaySignalOnScope(k.label, 0xDF, generateNecPattern(0xDF));
        serviceModal.classList.add("hidden");
      };
      discreteGrid.appendChild(btn);
    });
  } else {
    discreteSection.classList.add("hidden");
  }

  // Macros
  const macrosSection = document.getElementById("macrosSection");
  const macrosList = document.getElementById("macrosList");
  macrosList.innerHTML = "";
  if (brand.macros && brand.macros.length > 0) {
    macrosSection.classList.remove("hidden");
    brand.macros.forEach(m => {
      const card = document.createElement("div");
      card.className = "macro-card";
      card.innerHTML = `
        <div class="macro-info">
          <strong>${m.name}</strong>
          <span>${m.desc}</span>
        </div>
        <div class="macro-run-tag">RUN</div>
      `;
      card.onclick = () => runMacro(m);
      macrosList.appendChild(card);
    });
  } else {
    macrosSection.classList.add("hidden");
  }

  // PINs
  const pinsRow = document.getElementById("pinsRow");
  pinsRow.innerHTML = "";
  brand.defaultPins.forEach(p => {
    const pill = document.createElement("span");
    pill.className = "pin-pill";
    pill.textContent = p;
    pinsRow.appendChild(pill);
  });

  // Instructions
  document.getElementById("instructionsBox").textContent = brand.instructions;
}

// Render Motherboard Catalog
function renderMotherboardCatalog(filterText = "") {
  const container = document.getElementById("boardsCatalog");
  container.innerHTML = "";
  const query = filterText.toLowerCase();

  const filtered = MOTHERBOARDS.filter(b => 
    b.model.toLowerCase().includes(query) ||
    b.chip.toLowerCase().includes(query) ||
    b.code.toLowerCase().includes(query)
  );

  filtered.forEach(b => {
    const card = document.createElement("div");
    card.className = "board-card";
    card.innerHTML = `
      <div class="board-card-header">
        <span class="board-name">${b.model}</span>
        <span class="board-code">${b.code}</span>
      </div>
      <div class="board-chipset">${b.chip}</div>
      <div class="board-fix">🔄 Mirror Fix: ${b.mirror}</div>
      <div class="board-fix">🎨 LVDS Fix: ${b.lvds}</div>
      <div style="font-size:10px; color:#8b95a5;">ℹ ${b.notes}</div>
    `;
    card.onclick = () => {
      boardModal.classList.add("hidden");
      // Switch brand to China Universal and open service hub
      currentBrandKey = "china_universal";
      brandSelect.value = "china_universal";
      renderServiceHub();
      serviceModal.classList.remove("hidden");
    };
    container.appendChild(card);
  });
}

// Event Listeners
brandSelect.addEventListener("change", (e) => {
  currentBrandKey = e.target.value;
});

// Remote button clicks
document.querySelectorAll(".remote-faceplate button").forEach(btn => {
  btn.addEventListener("click", () => {
    const key = btn.dataset.key;
    if (key) transmitKey(key);
  });
});

// Modal Toggles
document.getElementById("btnOpenServiceHub").addEventListener("click", () => {
  renderServiceHub();
  serviceModal.classList.remove("hidden");
});

document.getElementById("btnCloseServiceModal").addEventListener("click", () => {
  serviceModal.classList.add("hidden");
});

document.getElementById("btnOpenBoardLookup").addEventListener("click", () => {
  renderMotherboardCatalog();
  boardModal.classList.remove("hidden");
});

document.getElementById("btnCloseBoardModal").addEventListener("click", () => {
  boardModal.classList.add("hidden");
});

document.getElementById("boardSearchInput").addEventListener("input", (e) => {
  renderMotherboardCatalog(e.target.value);
});

document.getElementById("btnOpenHexInjector").addEventListener("click", () => {
  hexModal.classList.remove("hidden");
});

document.getElementById("btnCloseHexModal").addEventListener("click", () => {
  hexModal.classList.add("hidden");
});

// Numpad Toggle
document.getElementById("numpadToggle").addEventListener("click", () => {
  const grid = document.getElementById("numpadGrid");
  const arrow = document.getElementById("numpadArrow");
  if (grid.style.display === "none") {
    grid.style.display = "grid";
    arrow.textContent = "▼";
  } else {
    grid.style.display = "none";
    arrow.textContent = "▲";
  }
});

// Raw Hex Transmit
document.getElementById("btnTransmitHex").addEventListener("click", () => {
  const hex = document.getElementById("hexInputArea").value.trim();
  if (!hex) {
    alert("Please enter a Pronto Hex code!");
    return;
  }
  const tokens = hex.split(/\s+/);
  if (tokens.length < 4) {
    alert("Invalid Pronto Hex format. Expected at least 4 preamble words.");
    return;
  }
  const freqCode = parseInt(tokens[1], 16);
  const freq = Math.round(1000000 / (freqCode * 0.241246)) || 38000;
  displaySignalOnScope("CUSTOM PRONTO HEX", 0xAA, [9000, 4500, 560, 560, 560, 1690]);
  hexModal.classList.add("hidden");
});

// Init
renderServiceHub();
renderMotherboardCatalog();
