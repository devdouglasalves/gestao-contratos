package model.exceptions;

import java.io.Serial;

public class ContractException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ContractException(String message) {
        super(message);
    }
}
