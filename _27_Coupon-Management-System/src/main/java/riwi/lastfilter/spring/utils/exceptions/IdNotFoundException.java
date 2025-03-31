package riwi.lastfilter.spring.utils.exceptions;

public class IdNotFoundException extends RuntimeException {
    
    private static final String ERROR_MESSAGE = "There are no records in the entity %s with the supplied id";
    // use the RuntimeException constructor to handle exceptions and insert the message
    public IdNotFoundException(String nameEntity) {
        super(String.format(ERROR_MESSAGE, nameEntity));
    }
}
