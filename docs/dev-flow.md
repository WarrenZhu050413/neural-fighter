# Neural Fighter Development Flow

## Prerequisites
- Install a Java 21+ JDK (Temurin 21 LTS works well). If you extracted the bundled archive, set `export JAVA_HOME="$HOME/.jdks/jdk-21.0.9+10/Contents/Home"` and prepend `$JAVA_HOME/bin` to `PATH`.
- Install VS Code with the Java Extension Pack, Fabric Language Kotlin (optional), and the Gradle extension.
- Verify Minecraft Java Edition is installed and that you can log in with the Microsoft launcher.

## Workspace Bootstrap
1. Clone or open this repository.
2. Run `./gradlew genSources` to download mappings and decompile the game sources.
3. Run `./gradlew vscode` to generate `.vscode/launch.json` and Gradle task bindings.
4. In VS Code, trust the workspace when prompted so Fabric Loom can configure the Gradle daemons.
5. Use the Gradle sidebar to execute `runClient`; the task launches a modded Minecraft instance for live testing.

## Iteration Loop
- Edit Java/Kotlin sources under `src/main/java` and assets under `src/main/resources`.
- Keep gameplay logic server-side (`main` source set) and visual-only code in the `client` source set.
- Use `./gradlew runClient` for rapid iteration; `./gradlew build` before committing or sharing artifacts.
- If mappings seem stale, rerun `./gradlew genSources` and reload VS Code.

### Visualization Blocks
- `neural_node` blocks animate activation by layer index (0-7). The block entity recomputes a sine wave so you can layer them in columns.
- `neural_connection` blocks look for the two closest nodes within eight blocks and stream particles between them with sign-coded weights.
- `/neuralfighter demo_graph` refreshes registration after you move or clone blocks.

## Roadmap toward the AI Companion
1. **Rule-Based Fighter**
   - Register a custom `PathAwareEntity` with Fabric’s entity builder.
   - Attach basic `Goal`s: follow owner, attack hostile mobs, retreat on low health.
   - Expose attributes via `FabricDefaultAttributeRegistry` and add a `/neuralfighter debug goals` command to visualize priorities.
2. **Visualization Block**
   - Create a block + block entity that renders a small neural graph (nodes and weighted edges).
   - Drive the renderer from deterministic logic first; log activations using `ServerTickEvents.END_SERVER_TICK`.
   - Serialize visualization state in the block entity’s NBT so worlds persist changes.
3. **Data Capture**
   - Log fighter observations (vectorized state, chosen action, reward) to JSONL under `run/logs/`.
   - Add toggles through gamerules or config files to control logging volume.
4. **Offline Imitation Learning**
   - Train policies with PyTorch outside Minecraft; export frozen weights as JSON.
   - Load weights during `NeuralFighterMod#onInitialize` and keep a rule-based fallback if loading fails.
5. **Online Adaptation (Later)**
   - Stream activations to the visualization via custom packets.
   - Gate any live learning behind config flags to avoid destabilizing servers.

## Troubleshooting
- “Unable to locate a Java Runtime” ⇒ Install a Java 21 JDK and ensure `JAVA_HOME` points to it.
- Gradle cache errors ⇒ Delete `.gradle/` and rerun `./gradlew genSources`.
- Client fails to launch ⇒ Confirm loader & Fabric API versions match the target Minecraft version in `gradle.properties`.
