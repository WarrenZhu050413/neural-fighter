# Getting Started with Neural Fighter Mod

Hey! This is a simple guide to help you understand the codebase and start contributing.

## What is This?

This is a **Minecraft mod** built with **Fabric** (a lightweight modding framework). The entire project structure was generated from Fabric's official generator, so it follows standard conventions.

---

## Quick Start (Just Run It!)

```bash
make build          # Build the mod
make run-client     # Start Minecraft with the mod loaded
```

When Minecraft opens, you'll need to **enable Fabric mods** in the main menu. Then create a new world, and you'll automatically get some neural blocks in your inventory to test with!

---

## Code Structure (The Simple Version)

### 1. **Creating New Blocks/Items** → `src/main/java/.../content/`

This is where you **register new game objects**. Currently we have:
- `NeuralNodeBlock.java` - A neural network node block
- `NeuralConnectionBlock.java` - A block representing connections between nodes
- `RegistryObjects.java` - This is where ALL blocks/items get registered with Minecraft

**To add a new block:** Create a new class in `content/`, then register it in `RegistryObjects.java`.

---

### 2. **Event Handlers** → `src/main/java/.../event/`

This handles **"when X happens, do Y"** logic. Currently:
- `PlayerJoinEventHandler.java` - When a player joins, give them neural blocks for testing

**To add a new event:** Create a handler class here and register it in `NeuralFighterMod.java`.

---

### 3. **Mixins** → `src/main/java/.../mixin/`

Advanced stuff. Mixins let you **modify Minecraft's base code** without touching it directly. You probably won't need to touch this unless you need to change core Minecraft behavior.

---

### 4. **Visualization** → `src/main/java/.../visualization/`

**⚠️ Warning:** This code was AI-generated and I haven't fully understood it yet!

- `NeuralNetworkVisualizationManager.java` - Manages rendering of neural network visualizations
- Nodes and edges get registered here, then rendered in 3D

---

## Resources Directory (Client vs Server Data)

The `src/main/resources/` folder contains **non-code files** that Minecraft needs:

```
resources/
├── assets/          ← CLIENT-SIDE (what players SEE)
│   └── neuralfighter/
│       ├── blockstates/    # How blocks look in different states
│       ├── models/         # 3D block/item models
│       └── icon.png        # Mod icon
│
├── data/            ← SERVER-SIDE (game LOGIC)
│   └── neuralfighter/
│       └── loot_tables/    # What drops when you break blocks
│
├── fabric.mod.json          # Mod metadata (name, version, etc.)
└── neuralfighter.mixins.json  # Mixin configuration
```

### **What's the Difference?**

| Folder | Purpose | Examples |
|--------|---------|----------|
| **`assets/`** | **Visual stuff** (textures, models, sounds) | Block textures, 3D models, how blocks look |
| **`data/`** | **Game logic** (recipes, loot, tags) | What drops when broken, crafting recipes |

**Easy way to remember:**
- `assets` = What the player **sees**
- `data` = How the game **behaves**

**Example:**
- `assets/blockstates/neural_node.json` → Tells Minecraft which 3D model to show for different "layer" states (layer 0-7)
- `data/loot_tables/blocks/neural_node.json` → When you break a neural node block, you get 1 neural node item back

---

## Project Layout Overview

```
src/main/java/com/fuchengwarren/neuralfighter/
├── NeuralFighterMod.java       # Main entry point (starts everything)
├── content/                    # NEW BLOCKS/ITEMS GO HERE
│   ├── NeuralNodeBlock.java
│   ├── NeuralConnectionBlock.java
│   └── RegistryObjects.java    # Register all your stuff here
├── event/                      # EVENT HANDLERS GO HERE
│   └── PlayerJoinEventHandler.java
├── mixin/                      # Advanced Minecraft code modifications
└── visualization/              # Neural network rendering (AI-generated)
    └── NeuralNetworkVisualizationManager.java
```

---

## Common Tasks

### **I want to add a new block**

1. Create `MyNewBlock.java` in `content/`
2. Register it in `RegistryObjects.java`
3. Add blockstate JSON in `resources/assets/neuralfighter/blockstates/`
4. Add loot table JSON in `resources/data/neuralfighter/loot_tables/blocks/`

### **I want to handle an event**

1. Create `MyEventHandler.java` in `event/`
2. Register it in `NeuralFighterMod.onInitialize()`

### **I want to test my changes**

```bash
make build
make run-client
```

---

## Why Fabric?

Fabric is a **lightweight, fast modding framework** that's great for:
- Quick prototyping
- Modern Minecraft versions
- Clean, organized code structure

It's easier than Forge for small/medium mods like this one.

---

## Need Help?

- Check out `docs/` for more detailed documentation
- Read the [Fabric Wiki](https://fabricmc.net/wiki/)
- Ask me! (Warren)

Happy modding! 🎮
