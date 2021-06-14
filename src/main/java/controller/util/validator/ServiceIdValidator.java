package controller.util.validator;


public class ServiceIdValidator extends RegexValidator {
    private final static int MAX_LENGTH = 14;
    private final static String AMOUNT_REGEX = "^[1-9]\\d*(\\.\\d+)?$";
    private final static String INVALID_AMOUNT = "invalid.service.format";

    public ServiceIdValidator() {
        super(AMOUNT_REGEX, MAX_LENGTH, INVALID_AMOUNT);
    }
}
