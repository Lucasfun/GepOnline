package com.gep.online.base;public class GepException extends Throwable {
    GepException(String message) {
        super(message);
        printStackTrace();
    }
}
