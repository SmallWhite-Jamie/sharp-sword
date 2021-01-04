package com.sharp.sword.mail;

import lombok.Data;

/**
 * @author lizheng
 * @date: 12:38 2020/02/27
 * @Description: SimpleMessage
 */
@Data
public class SimpleMessage {
    private String from;
    private String to;
    private String subject;
    private String text;
}
