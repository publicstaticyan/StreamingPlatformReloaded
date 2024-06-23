package streamingplatform.utils;

public class BooleanParser {
    public static boolean parseBooleanValue(String input) {
        return input.equals("1");
    }
    
    public static String stringify(boolean input) {
    	return input ? "1" : "0";
    }
}
