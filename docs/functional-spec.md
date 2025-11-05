# Neural Fighter Functional Specification

## Vision
- **Mission**: Make AI literacy irresistible for kids by embedding modern machine-learning concepts inside the games they already love—starting with Minecraft.
- **Belief**: AI literacy is the most important skill of this generation. Traditional teaching tools fail because they are neither fun nor culturally relevant.
- **North Star**: Deliver a Minecraft-native learning experience that is simultaneously high on *Fun* and *Relevance*, keeping kids engaged while teaching actionable AI/ML skills.

## Audience
- **Primary Learners**: Middle- and high-school kids addicted to sandbox games, especially in affluent West Coast households (Caucasian / Asian) whose parents prioritize STEM outcomes.
- **Buyer Persona**: Tech-savvy parents willing to pay for subscriptions if the product is educational, fun, and produces sharable progress artifacts.
- **Advocates/Influencers**: Minecraft community leaders, STEM-focused educators, and influencers (e.g., Ssundee, Sky Factory speedrunners) who can validate the experience.

## Product Pillars
1. **Playable AI Curriculum**
   - Deep Learning 101 storyline in Minecraft.
   - Quests that mirror ML workflows: data collection, model training, evaluation, deployment.
   - Progression that unlocks more complex neural network mechanics as players advance.
2. **Neural Network Visualization**
   - In-world blocks/entities that show activations, weights, and decisions in real time.
   - Ability to export trained “models” as blueprints/data packs for use outside the game.
3. **AI Companion Fighter**
   - Custom entity controlled by evolving AI policies.
   - Starts rule-based, transitions to imitation learning, then offers online fine-tuning via API.
   - Kids observe cause/effect as their fighter improves against mobs and bosses.
4. **Progress Bridge**
   - Export pathways to connect in-game achievements to the real world (e.g., differently exporting neural network built in the game out in real life for the child to see the python/java code).
   - Encourage parents with dashboards, weekly summaries, and competencies unlocked.   
5. **Monetization & Platform**
   - Subscription gated behind API calls (model training, analytics, premium quests).
   - Dedicated managed servers with “always-on” AI experiences and community features.
   - Supports an optional single-player mod for discovery; full program requires subscription.

## Experience Flow
1. **Onboarding**
   - Parents subscribe (web portal) and create learner profiles.
   - Kids download the launcher/mod pack or join the managed server.
   - Tutorial quest introduces AI concepts using the neural visualization lab.
2. **Core Loop**
   - Complete quests (e.g., collect training data, tune a neural node) to upgrade the AI fighter. These quests should be directly related to ML concepts.
   - Deploy the fighter in combat challenges; observe performance via visualizers.
   - Receive feedback, adjust parameters, unlock new content.

## Technical Components
- **Minecraft Fabric Mod (Client/Server)**
  - Entity/AI goal system for the fighter.
  - Block entities + renderers for neural visualization.
  - Data capture pipeline (JSONL logs, analytics hooks).
- **Managed Server Infrastructure**
  - Monetized servers similar to Wynncraft/Hypixel model.
  - Supports scale, mod distribution, and social features.
- **Subscription Backend**
  - Authentication, billing, API rate enforcement.
  - Progress dashboards and export tools.
- **Integrations**
  - Optional connectors to school LMS or curriculum materials.
  - External AI model training services/APIs (priced usage).