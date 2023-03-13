package com.zyk.music.exception;

/**
 * @author 13540
 */
public class NoLoginException extends RuntimeException {
    private static final String MSG = "未登录";

    public NoLoginException() {
        super(MSG);
    }

}
