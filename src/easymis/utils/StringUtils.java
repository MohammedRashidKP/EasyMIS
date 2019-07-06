package easymis.utils;

/**
 *
 * @author RashidKP
 */
public class StringUtils {

    public static boolean isNotNullCheckSpace(
            final String string) {
        return string != null && !string.isEmpty() && !string.trim().isEmpty();
    }
}
