# Neural Fighter Mod

I built this with Fabric because it is a lightweight modding framework that makes it easy to prototype. The entire project structure was generated from Fabric's official generator, so it follows standard conventions.

To run, do this:

```bash
make build          # Build the mod
make run-client     # Start Minecraft with the mod loaded
```

When Minecraft opens, you'll need to enable Fabric mods in the main menu. Do this by clicking "Single Player", then "Create New World", then "More", then "Data Packs", and then select "Fabric Mods" on the right column. At lastcreate the new world. Neural blocks and a "fighter egg" spawner item will be automatically in one's inventory.

Now some things I learned about the code structure:

## Code Structure

### 1. **Creating New Blocks/Items** → `src/main/java/.../content/`

You register new game objects in content. Currently we have:
- `NeuralNodeBlock.java` - A neural network node block
- `NeuralConnectionBlock.java` - A block representing connections between nodes
- `RegistryObjects.java` - This is where ALL blocks/items get registered with Minecraft

**To add a new block:** Create a new class in `content/`, then register it in `RegistryObjects.java`.

---

### 2. **Event Handlers** → `src/main/java/.../event/`

Currently:
- `PlayerJoinEventHandler.java` - When a player joins, give them neural blocks for testing

**To add a new event:** Create a handler class here and register it in `NeuralFighterMod.java`.

---

### 3. **Mixins** → `src/main/java/.../mixin/`

Not too important. It's there to modify the Minecraft base code without touching it. Event handlers should mostly be enough. (Event Handlers are Fabric's nicely packaged API, mixins are lower level.)

---

### 4. **Visualization** → `src/main/java/.../visualization/`

Currently there are weird flames coming out of the nodes. This is an AI placeholder. Not sure how to visualize this yet.

The logic is in:

- `NeuralNetworkVisualizationManager.java` - Manages rendering of neural network visualizations
- Nodes and edges get registered to the manager in other parts of the code, then rendered by the manager.

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
