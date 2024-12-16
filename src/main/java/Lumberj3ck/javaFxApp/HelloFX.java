package Lumberj3ck.javaFxApp;

import java.util.HashMap;
import java.util.Map;

import Lumberj3ck.AlpacaPaperExecutor;
import Lumberj3ck.RSIStrategy;
import Lumberj3ck.Runner;
import Lumberj3ck.SmaStrategy;
import Lumberj3ck.Strategy;
import Lumberj3ck.TestExecutor;
import Lumberj3ck.TestStrategy;
import Lumberj3ck.TradeExecutor;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HelloFX extends Application {
    private ComboBox<String> strategySelector;
    private ComboBox<String> executorSelector;

    private Button executeButton;
    private TextArea outputArea;

    private Transition trayIcon;

    // Maps to hold Strategy and Executor class instances
    private final Map<String, Strategy> strategies = new HashMap<>();
    private final Map<String, TradeExecutor> executors = new HashMap<>();

    @Override
    public void start(Stage primaryStage) {
        // setupTrayIcon();
        loadStrategiesAndExecutors();

        // UI Components
        Label strategyLabel = new Label("Select Strategy:");
        strategySelector = new ComboBox<>();
        strategySelector.getItems().addAll(strategies.keySet());

        Label executorLabel = new Label("Select Executor:");
        executorSelector = new ComboBox<>();
        executorSelector.getItems().addAll(executors.keySet());

        executeButton = new Button("Run");
        outputArea = new TextArea();
        outputArea.setEditable(false);

        VBox layout = new VBox(10, strategyLabel, strategySelector, executorLabel, executorSelector, executeButton, outputArea);
        layout.setPadding(new javafx.geometry.Insets(10));

        executeButton.setOnAction(e -> runStrategy());

        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setTitle("Strategy Executor App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void runStrategy() {
        String strategyName = strategySelector.getValue();
        String executorName = executorSelector.getValue();

        if (strategyName == null || executorName == null) {
            outputArea.appendText("Please select both a strategy and an executor.\n");
            return;
        }

        Strategy strategy = strategies.get(strategyName);
        TradeExecutor executor = executors.get(executorName);

        Runner runner = new Runner();
        runner.run(strategy, executor);

        // String message = String.format("Strategy %s executed in %d ms using %s.\n", strategyName, executionTime, executorName);

        // outputArea.appendText(message);
        // showTrayMessage("Execution Complete", message);
    }

    private void loadStrategiesAndExecutors() {
        strategies.put("SmaStrategy", new SmaStrategy());
        strategies.put("RsiStrategy", new RSIStrategy());
        strategies.put("TestStrategy", new TestStrategy());

        executors.put("AlpacaPaperExecutor", new AlpacaPaperExecutor());
        executors.put("TestExecutor", new TestExecutor());
    }

    // private void setupTrayIcon() {
    //     if (!SystemTray.isSupported()) {
    //         System.out.println("System tray not supported!");
    //         return;
    //     }

    //     SystemTray tray = SystemTray.getSystemTray();
    //     Image image = Toolkit.getDefaultToolkit().getImage("icon.png"); // Provide path to icon

    //     PopupMenu popup = new PopupMenu();
    //     MenuItem exitItem = new MenuItem("Exit");
    //     exitItem.addActionListener(e -> System.exit(0));
    //     popup.add(exitItem);

    //     trayIcon = new TrayIcon(image, "Strategy Executor", popup);
    //     trayIcon.setImageAutoSize(true);

    //     try {
    //         tray.add(trayIcon);
    //     } catch (AWTException e) {
    //         System.out.println("TrayIcon could not be added.");
    //     }
    // }

    // private void showTrayMessage(String title, String message) {
    //     if (trayIcon != null) {
    //         trayIcon.displayMessage(title, message, MessageType.INFO);
    //     }
    // }

    public static void main(String[] args) {
        launch(args);
    }

}