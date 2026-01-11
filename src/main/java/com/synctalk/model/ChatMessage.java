package com.synctalk.model;

import lombok.*;

/**
 * Author: Shankar Chakraborty
 * Date: 2026-01-10
 * Time: 11:41 p.m.
 */


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ChatMessage {
    private String sender;
    private String content;
    private String type;
}
