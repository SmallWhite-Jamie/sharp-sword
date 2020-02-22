package com.jamie.framework.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private String id;
    private String name;
    private Integer age;
    private String addr;
}