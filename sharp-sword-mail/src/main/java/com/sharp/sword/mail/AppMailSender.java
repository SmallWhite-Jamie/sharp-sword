package com.sharp.sword.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author lizheng
 * @date: 12:34 2020/02/27
 * @Description: MailSender
 */
@Component
@Slf4j
public class AppMailSender {

    @Autowired
    private JavaMailSenderImpl mailSender;

    /**
     * 发送一个简单的邮件
     * @param message
     */
    public void sendSimpleMail(SimpleMessage message){
        try{
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage .setFrom(message.getFrom());
            simpleMailMessage .setTo(message.getTo());
            simpleMailMessage.setSubject(message.getSubject());
            simpleMailMessage.setText(message.getText());
            //发送
            mailSender.send(simpleMailMessage);
        } catch(Exception e){
          log.error("邮件发送失败，", e);
        }
    }

    /**
     * 发送一个 HTML 格式的邮件
     * @param message
     */
    public void sendHTMLMail(SimpleMessage message) {
        try {
            MimeMessage mimeMailMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
            mimeMessageHelper.setFrom(message.getFrom());
            mimeMessageHelper.setTo(message.getTo());
            mimeMessageHelper.setSubject(message.getSubject());
            mimeMessageHelper.setText(message.getText(), true);
            mailSender.send(mimeMailMessage);
        } catch (Exception e) {
            log.error("邮件发送失败，", e.getMessage());
        }
    }

    /**
     * 发送一个带附件列表
     * @param message
     */
    public void sendAttachmentMail(AttachmentMessage message) {
        Assert.notEmpty(message.getAttachments(), "附件为空");
        try {
            MimeMessage  mimeMailMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
            mimeMessageHelper.setFrom(message.getFrom());
            mimeMessageHelper.setTo(message.getTo());
            mimeMessageHelper.setSubject(message.getSubject());
            mimeMessageHelper.setText(message.getText());
            List<AttachmentMessage.Attachment> attachments = message.getAttachments();
            for (AttachmentMessage.Attachment attachment : attachments) {
                mimeMessageHelper.addAttachment(attachment.getName(), attachment.getFile());
            }
            mailSender.send(mimeMailMessage);
        } catch (Exception e) {
            log.error("邮件发送失败", e.getMessage());
        }
    }

    /**
     * 发送一个复杂的邮件
     * @param consumer
     */
    public void sendMimeMessage(Consumer<MimeMessageHelper> consumer) {
        try {
            MimeMessage mimeMailMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
            consumer.accept(mimeMessageHelper);
            mailSender.send(mimeMailMessage);
        } catch (Exception e) {
            log.error("邮件发送失败", e.getMessage());
        }
    }

}
