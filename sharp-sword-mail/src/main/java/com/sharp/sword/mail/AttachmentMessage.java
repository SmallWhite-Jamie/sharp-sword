package com.sharp.sword.mail;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.util.List;

/**
 * @author lizheng
 * @date: 13:02 2020/02/27
 * @Description: AttachmentMessage
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentMessage {
    private String from;
    private String to;
    private String subject;
    private String text;
    private List<Attachment> attachments;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Attachment {
        private String name;
        private File file;
    }
}
