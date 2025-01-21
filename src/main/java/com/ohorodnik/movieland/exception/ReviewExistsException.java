package com.ohorodnik.movieland.exception;

public class ReviewExistsException extends RuntimeException {

    private static final String MESSAGE = "User %s added review for movie %d already";

    public ReviewExistsException(String nickName, Integer movieId) {
        super(String.format(MESSAGE, nickName, movieId));
    }
}
