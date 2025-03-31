package riwi.lastfilter.spring.utils.exceptions;

public class CouponUsedException extends RuntimeException {
    
    private static final String ERROR_MESSAGE = "The coupon has been already used";
    // use the RuntimeException constructor to handle exceptions and insert the message
    public CouponUsedException() {
        super(String.format(ERROR_MESSAGE));
    }
}
