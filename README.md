# Neural Fighter

Companion combat AI for Minecraft with an in-game neural network visualizer.

## Development Setup

1. Install a Java 21 (or newer) JDK. If you used the bundled Temurin archive, export `JAVA_HOME="$HOME/.jdks/jdk-21.0.9+10/Contents/Home"` and prepend `$JAVA_HOME/bin` to `PATH` before running Gradle.
2. In this folder, run `./gradlew genSources` once to decompile the game sources.
3. Run `./gradlew vscode` so VS Code picks up Fabric’s launch configurations.
4. Use VS Code’s Java Extension Pack (see `.vscode/extensions.json`) and open the Gradle view to run:
   - `runClient` for a modded Minecraft client (or execute `scripts/run-client.sh` from the shell).
   - `runServer` for a dedicated server session (or execute `scripts/run-server.sh`).
5. Prefer `make build` (or `make run-client`) to wrap the Gradle tasks with the correct `JAVA_HOME`; `make install` builds and copies the freshest `neuralfighter-*.jar` into your Minecraft `mods/` folder.

## Neural Network Sandbox

- `neuralfighter:neural_node` – block entity that pulses activation by layer (0-7). Breaks and drops itself.
- `neuralfighter:neural_connection` – automatically links the two nearest nodes when placed and animates a weight pulse between them.
- On first login the mod auto-spawns a two-node demo network and seeds your inventory with both block types.
- Use `/neuralfighter demo_graph` to re-scan nearby node/connection blocks and re-enable the visualization after you move things around.

See `docs/dev-flow.md` for additional workflow notes and next steps.

## License

This project keeps the upstream CC0-1.0 license; reuse the template freely.
