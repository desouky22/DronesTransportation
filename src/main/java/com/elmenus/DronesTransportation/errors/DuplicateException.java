package com.elmenus.DronesTransportation.errors;

public class DuplicateException  extends RuntimeException{
    public DuplicateException() {
        super();
    }

    public DuplicateException(String message) {
        super(message);
    }
}

