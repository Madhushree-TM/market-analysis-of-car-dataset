

---

# DV Project

## Overview
The **DV Project** is a Java-based application that provides data visualization and predictive analysis features. It includes various utilities for charting, file selection, and data input forms.

## Features
- **Bar Chart Example**: Visualize data using bar charts.
- **Car Sales Prediction**: Predict car sales using statistical or machine learning models.
- **File Chooser**: Open and manage files via a graphical file chooser interface.
- **AWT-based Data Forms**: Collect and display data using AWT forms.

## Project Structure
```plaintext
dv_project/
├── .gitignore                  # Git ignored files
├── dv project.iml              # IntelliJ IDEA project file
├── .idea/                      # IDE configuration files
├── src/                        # Source code directory
│   ├── BarChartExample.java    # Bar chart visualization example
│   ├── CarSalesPredictionApp.java # Main application for car sales prediction
│   ├── DataFormAWT.java        # AWT data form example
│   ├── DataFormAWT2.java       # Extended AWT data form
│   ├── FileChooserExample.java # File chooser utility
│   └── Main.java               # Entry point of the application
```

## Prerequisites
- **Java Development Kit (JDK)**: Version 8 or higher.
- **IntelliJ IDEA** (or any Java IDE) for development and testing.

## How to Run
### Using IntelliJ IDEA
1. **Clone the Repository**:
   ```bash
   git clone <repository-url>
   cd dv_project
   ```
2. **Open in IntelliJ IDEA**:
   - Open IntelliJ IDEA.
   - Select `File > Open...` and navigate to the cloned `dv_project` directory.
3. **Build the Project**:
   - IntelliJ will detect the project files.
   - Click on `Build > Build Project` to compile the source code.
4. **Run the Main Class**:
   - Locate `Main.java` in the `src/` directory.
   - Right-click on `Main.java` and select `Run 'Main'`.

### Using Command Line
1. **Navigate to the `src` Directory**:
   ```bash
   cd dv_project/src
   ```
2. **Compile the Code**:
   Compile all `.java` files:
   ```bash
   javac *.java
   ```
3. **Run the Application**:
   Run the main application:
   ```bash
   java Main
   ```

## Contribution
Contributions are welcome! Please fork the repository and submit a pull request with detailed changes.



---
