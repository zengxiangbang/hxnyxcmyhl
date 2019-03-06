package cn.richinfo.spring.exception;

public class ActiveIllegalStateException extends RuntimeException {
    private static final long serialVersionUID = 4818049819918564885L;

    public ActiveIllegalStateException() {
    }

    public ActiveIllegalStateException(String message) {
        super(message);
    }
}
