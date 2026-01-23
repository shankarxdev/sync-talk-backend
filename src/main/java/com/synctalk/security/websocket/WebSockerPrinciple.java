package com.synctalk.security.websocket;

import lombok.RequiredArgsConstructor;

import java.security.Principal;

/**
 * Author: Shankar Chakraborty
 * Date: 2026-01-21
 * Time: 9:52 a.m.
 */

@RequiredArgsConstructor
public class WebSockerPrinciple implements Principal {

    private final String userName;

    @Override
    public String getName() {
        return this.userName;
    }
}
