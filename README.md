# Zelda Pet - Link Walker

A fun IntelliJ IDEA plugin that displays an animated Link character walking at the bottom of the left panel.

## Features

- 🎮 Animated Link sprite walking animation
- 📍 Positioned at the bottom of the left panel
- 🧙 Brings a touch of Hyrule to your IDE
- 🎨 Classic green tunic Link design
- 🔄 Smooth walking animation with bouncing

## Installation

### Building from Source

1. Clone this repository
2. Open the project in IntelliJ IDEA
3. Run `./gradlew buildPlugin`
4. The plugin will be built in `build/distributions/zelda-pet-1.0.0.zip`

### Installing the Plugin

1. Go to `File` → `Settings` → `Plugins`
2. Click the gear icon → `Install Plugin from Disk...`
3. Select the built `.zip` file
4. Restart IntelliJ IDEA

## Usage

After installation, the Zelda Pet tool window will appear in the left panel. You'll see Link walking back and forth at the bottom of the panel!

## Development

### Requirements

- IntelliJ IDEA 2023.3 or later
- JDK 17
- Gradle 8.0+

### Running in Development

1. Open the project in IntelliJ IDEA
2. Run the `runIde` Gradle task
3. A new IntelliJ IDEA instance will launch with the plugin installed

### Building

```bash
./gradlew buildPlugin
```

### Running Tests

```bash
./gradlew test
```

## License

This is a fan-made plugin for educational purposes. The Legend of Zelda is a trademark of Nintendo.

## Credits

- Link character design inspired by The Legend of Zelda series
- Built with IntelliJ Platform SDK
