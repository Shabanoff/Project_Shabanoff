package controller.util.validator;


public class AmountValidator extends RegexValidator {
    private final static int MAX_LENGTH = 14;
    private final static String AMOUNT_REGEX = "^\\d{1,3}(?:[.,]\\d{3})*(?:[.,]\\d{2})$";
    private final static String INVALID_AMOUNT = "invalid.amount.format";

    public AmountValidator() {
        super(AMOUNT_REGEX, MAX_LENGTH, INVALID_AMOUNT);
    }
    public AmountValidator(String message) {
        super(AMOUNT_REGEX, MAX_LENGTH, message);
    }
}
