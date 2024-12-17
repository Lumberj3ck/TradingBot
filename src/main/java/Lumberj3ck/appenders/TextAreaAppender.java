package Lumberj3ck.appenders;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.io.Serializable;

public class TextAreaAppender extends AbstractAppender {
    private static TextArea textArea;  // Reference to the JavaFX TextArea

    public static void setTextArea(TextArea textArea) {
        TextAreaAppender.textArea = textArea;
    }

    protected TextAreaAppender(String name, Filter filter, Layout<? extends Serializable> layout) {
        super(name, filter, layout, true);
    }

    @Override
    public void append(LogEvent event) {
        final String message = new String(getLayout().toByteArray(event));
        if (textArea != null) {
            Platform.runLater(() -> textArea.appendText(message)); // Append log message to TextArea
        }
    }

    public static Appender createAppender() {
        Layout<? extends Serializable> layout = PatternLayout.newBuilder()
            .withPattern("%d{HH:mm:ss.SSS} [%t] %-5level %msg%n")
            .build();
        return new TextAreaAppender("TextAreaAppender", null, layout);
    }
}
