package com.bigapple.model.response;

import lombok.Data;

@Data
public class Msg {
    private int code;
    private String msg;
    private Object data;

    @Data
    public static class ID {
        private int id;
    }
}
