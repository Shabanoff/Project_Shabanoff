package controller.util.validator;

public class TariffNameValidator extends RegexValidator {
    private final static String INVALID_LOGIN_KEY = "invalid.tariff.name";

    private final static int MAX_LENGTH = 50;

    /**
     * Regex used to perform validation of data.
     */
    private static final String TARIFF_NAME_REGEX = "^(.+)$";

    public TariffNameValidator() {
        super(TARIFF_NAME_REGEX, MAX_LENGTH, INVALID_LOGIN_KEY);
    }
}
