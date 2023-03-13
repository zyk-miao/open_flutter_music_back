package com.zyk.music.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zyk
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReturnRes<T> implements Serializable {
    String code;
    String msg;
    T data;

    public ReturnRes(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ReturnRes<Object> success(String msg) {
        return new ReturnRes<Object>("200", msg);
    }

    public static ReturnRes<Object> error(String msg) {
        return new ReturnRes<>("500", msg);
    }

    public static<T> ReturnRes<T> success(String msg, T data) {
        return new ReturnRes<>("200", msg, data);
    }
    public static<T> ReturnRes<T> refuse() {
        return new ReturnRes<>("403", "server refuse");
    }
}
