package controller.util.validator;

public class UserNumberValidator extends RegexValidator {
    private final static int MAX_LENGTH = 30;
    private final static String ACCOUNT_REGEX = "^(\\d+)$";
    private final static String INVALID_ACCOUNT_NUMBER = "invalid.account.number";

    public UserNumberValidator() {
        super(ACCOUNT_REGEX, MAX_LENGTH, INVALID_ACCOUNT_NUMBER);
    }
}