package com.zerokstudios.socratesman.gameobject;

/**
 * Created by Kevin on 5/12/2015.
 * <p/>
 * exception thrown if error with map and the existence of socrates
 */
public class SocratesNotFoundException extends Exception {
    public SocratesNotFoundException() {
    }

    public SocratesNotFoundException(String detailMessage) {
        super(detailMessage);
    }

    public SocratesNotFoundException(Throwable throwable) {
        super(throwable);
    }

    public SocratesNotFoundException(String detailMessage, Throwable throwable) {

        super(detailMessage, throwable);
    }
}
