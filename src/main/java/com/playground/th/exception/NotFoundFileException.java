package com.playground.th.exception;

import java.io.IOException;

public class NotFoundFileException extends IOException {
    public NotFoundFileException(String fileName){
        super(fileName+"is not existed");
    }

}
