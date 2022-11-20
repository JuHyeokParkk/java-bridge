package bridge;

public class BridgeLengthValidator {

    private static final String NUMBER_REGEX = "^[0-9]+$";
    private static final int MIN_BRIDGE_LENGTH = 3;
    private static final int MAX_BRIDGE_LENGTH = 20;

    public static void validate(String input) {
        checkNumberFormat(input);
        checkNumberRange(input);
    }

    private static void checkNumberFormat(String input) {
        if(!input.matches(NUMBER_REGEX))
            throw new IllegalArgumentException(ErrorMessage.BRIDGE_LENGTH_INPUT_ERROR_MESSAGE.toString());
    }

    private static void checkNumberRange(String input) {
        try {
            int length = Integer.parseInt(input);
            if(length < MIN_BRIDGE_LENGTH || length > MAX_BRIDGE_LENGTH) {
                throw new IllegalArgumentException(ErrorMessage.BRIDGE_LENGTH_INPUT_ERROR_MESSAGE.toString());
            }
        } catch (NumberFormatException numberFormatException) {
            throw new IllegalArgumentException(ErrorMessage.BRIDGE_LENGTH_INPUT_ERROR_MESSAGE.toString());
        }
    }

}
