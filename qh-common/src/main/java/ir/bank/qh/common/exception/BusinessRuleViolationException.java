package ir.bank.qh.common.exception;

/**
 * Raised when a service-layer check equivalent to one of the model's CHECK
 * constraints (e.g. CK_PRODUCT_VERSION_DATES, CK_PER_AGE_RANGE) fails.
 */
public class BusinessRuleViolationException extends RuntimeException {
    public BusinessRuleViolationException(String message) {
        super(message);
    }
}
