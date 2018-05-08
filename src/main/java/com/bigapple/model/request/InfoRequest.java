package com.bigapple.model.request;

import lombok.Data;

@Data
public class InfoRequest {
    private int id;
    private String address;
    private String name;
    private String phone;
    private long reportDate;
}
