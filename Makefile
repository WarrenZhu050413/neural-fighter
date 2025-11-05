JAVA_HOME ?= $(HOME)/.jdks/jdk-21.0.9+10/Contents/Home
GRADLE    = JAVA_HOME="$(JAVA_HOME)" PATH="$(JAVA_HOME)/bin:$$PATH" ./gradlew
MODS_DIR ?= $(HOME)/Library/Application\ Support/minecraft/mods

.PHONY: build install run-client run-server clean

build:
	$(GRADLE) build

install: build
	@mkdir -p "$(MODS_DIR)"
	@LATEST_JAR=$$(ls -t build/libs/neuralfighter-*.jar | head -n1); \
	if [ -z "$$LATEST_JAR" ]; then \
		echo "No mod jar found in build/libs. Run 'make build' first."; \
		exit 1; \
	fi; \
	cp "$$LATEST_JAR" "$(MODS_DIR)"; \
	echo "Installed $$LATEST_JAR -> $(MODS_DIR)"

run-client:
	$(GRADLE) runClient

run-server:
	$(GRADLE) runServer

clean:
	$(GRADLE) clean
