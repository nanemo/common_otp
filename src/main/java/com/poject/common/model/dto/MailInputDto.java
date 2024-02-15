package com.poject.common.model.dto;


import java.io.Serializable;
import java.util.Arrays;

public class MailInputDto implements Serializable {

    private String[] emailTo;
    private String[] cc;
    private String[] bcc;
    private String subject;
    private String content;
    private String attachmentName;
    private String attachmentBase64;

    public MailInputDto() {
    }

    public MailInputDto(String[] emailTo,
                        String[] cc,
                        String[] bcc,
                        String subject,
                        String content,
                        String attachmentName,
                        String attachmentBase64) {
        this.emailTo = emailTo;
        this.cc = cc;
        this.bcc = bcc;
        this.subject = subject;
        this.content = content;
        this.attachmentName = attachmentName;
        this.attachmentBase64 = attachmentBase64;
    }

    public String[] getEmailTo() {
        return emailTo;
    }

    public MailInputDto setEmailTo(String[] emailTo) {
        this.emailTo = emailTo;
        return this;
    }

    public String[] getCc() {
        return cc;
    }

    public MailInputDto setCc(String[] cc) {
        this.cc = cc;
        return this;
    }

    public String[] getBcc() {
        return bcc;
    }

    public MailInputDto setBcc(String[] bcc) {
        this.bcc = bcc;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public MailInputDto setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getContent() {
        return content;
    }

    public MailInputDto setContent(String content) {
        this.content = content;
        return this;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public MailInputDto setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
        return this;
    }

    public String getAttachmentBase64() {
        return attachmentBase64;
    }

    public MailInputDto setAttachmentBase64(String attachmentBase64) {
        this.attachmentBase64 = attachmentBase64;
        return this;
    }

    @Override
    public String toString() {
        return "MailInputDto{" +
                "emailTo=" + Arrays.toString(emailTo) +
                ", cc=" + Arrays.toString(cc) +
                ", bcc=" + Arrays.toString(bcc) +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                ", attachmentName='" + attachmentName + '\'' +
                ", attachmentBase64='" + attachmentBase64 + '\'' +
                '}';
    }
}
