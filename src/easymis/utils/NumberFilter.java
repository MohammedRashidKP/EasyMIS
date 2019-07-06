package easymis.utils;

import java.util.function.UnaryOperator;
import javafx.scene.control.TextFormatter;

/**
 *
 * @author RashidKP
 */
public class NumberFilter {
    
    public static TextFormatter<String> pinFilter (){
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getText();
            if (text.matches("[0-9]*")) {
                return change;
            }
            return null;
        };
        return new TextFormatter<>(filter);
    }
}
