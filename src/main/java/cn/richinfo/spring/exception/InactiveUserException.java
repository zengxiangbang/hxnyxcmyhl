package cn.richinfo.spring.exception;


/**
 * @author Grant
 */
public class InactiveUserException extends RuntimeException {


    private static final long serialVersionUID = 2766393813206375012L;

    public InactiveUserException() {
    }

    public InactiveUserException(String message) {
        super(message);
    }
}
