package com.pojo;

import lombok.Data;

@Data
public class InviteretMessage {
    private String messageType ;
    private String fromName;
    private String toName;
    private boolean accept;

}
