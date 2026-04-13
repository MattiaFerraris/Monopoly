# JavaFX Monopoly Project

A custom digital version of the Monopoly board game, built with Java and JavaFX for the "Fondamenti di Informatica" course at SUPSI.

## Features

- **Graphical User Interface**: Full GUI built with JavaFX and FXML, featuring custom images for dice, buttons, and the game board.
- **Dynamic Gameplay**: Supports up to 4 players with an automated turn-order system determined by initial dice rolls.
- **Game Logic**:
  - **Property Management**: Players can buy properties, pay rent, and trade with others.
  - **Buildings**: Ability to build houses and hotels on properties once a full color set is owned.
  - **Events**: Functional "Chance" and "Probability" card decks that trigger payments, rewards, or movement.
  - **Banking**: Automated bank system to handle transactions and taxes.
- **Save & Load**: Save your current progress to a file and resume later using Java Object Serialization.

## Project Structure

- `src/game`: Contains the main application entry point and UI controllers (`StartController`, `NameController`, `TableController`).
- `src/gameLogic`: Core mechanics like the Bank and Dice rolling.
- `src/player`: Player entity and turn management.
- `src/table`: Defines board elements including different types of boxes (Properties, Taxes, Prison, Parking).
- `src/Resources`: FXML layouts and graphical assets (images).

## Getting Started

### Prerequisites
- Java JDK 17 or higher.
- JavaFX SDK.

### Installation & Execution
1. Clone the repository.
2. Ensure `PropertyData.csv`, `Imprevisti.txt`, and `Probabilita.txt` are in the root directory for the board to initialize correctly.
3. Run the `Game.java` class to launch the application.

## Usage
1. **Start Screen**: Choose "Nuova Partita" (New Game) or "Carica Partita" (Load Game).
2. **Setup**: Enter names and single-character symbols for 4 players.
3. **Gameplay**: Click "Tira i dadi" (Roll Dice) to move. Interaction prompts (buying/building) will appear in alerts.

## Authors
- Developed by **Gruppo_05**.
