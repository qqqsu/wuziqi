package com.pojo;

import lombok.Data;

@Data
public class InviteMessage {
    private String messageType;
    private String fromName;
    private String toName;
}