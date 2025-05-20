# Welcome to **Typing Game**  
*Test your speed and accuracy, one sentence at a time!*

---

### Objective

Choose your preferred **difficulty level** and start typing!  
Your goal is to complete the given sentence **as quickly and accurately as possible**.  

The game tracks your **Words Per Minute (WPM)** and **typing accuracy**, offering a fun and competitive way to improve your skills.  
See your performance stats and try to **beat your personal best**!

---

### Developed By

- Kong Pheng Yang  
- Bibesh Pyakural  
- Samuel Lillge  
- Ryan Wichman

---

### Setup Instructions

#### Step 1: Download the Game Files  
Clone or download the project repository from the provided link.

#### Step 2: Set Up JavaFX in Eclipse  

1. **Install JavaFX SDK version 8.**  
2. In Eclipse:
   - Right-click your project → **Properties** → **Java Build Path** → **Libraries**
   - Click **Module Path** → **Add External JARs** → Select the **JavaFX SDK JARs**
3. Go to **Run Configuration** → **Arguments**  
   Add the following to **VM arguments**:
   ```
   --module-path /path/to/javafx-sdk-<version>/lib --add-modules javafx.controls,javafx.fxml
   ```
   *(Replace `/path/to/javafx-sdk-<version>/lib` with the actual path to your SDK folder.)*

#### Step 3: Launch the Game  

1. Open the project in Eclipse.  
2. Run `TypingGame.java`.  
3. From the title screen, click **"Start Game"**.  
4. Choose your difficulty: **Easy** or **Hard**.  
5. Start typing the displayed sentence.  
   - Press **Enter** to submit.  
   - View your **WPM** and **accuracy** once the round is over.  
6. Use **"Try Again"** to play another round or **"Main Menu"** to return.

---

### Enjoy sharpening your typing skills!
