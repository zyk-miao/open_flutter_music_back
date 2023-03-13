package com.zyk.music.exception;

/**
 * @author 13540
 */
public class TokenUnavailableException extends RuntimeException {
    private static final String MSG = "无效令牌";

    public TokenUnavailableException() {
        super(MSG);
    }
}
