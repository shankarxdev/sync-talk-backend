package com.synctalk.common.exception;

/**
 * Author: Shankar Chakraborty
 * Date: 2026-01-20
 * Time: 4:29 p.m.
 */


public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
