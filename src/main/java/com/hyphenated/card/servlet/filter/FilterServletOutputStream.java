/*
 * File take from https://github.com/bhagyas/spring-jsonp-support
 * Copyright Bhagya Nirmaan Silva
 */
package com.hyphenated.card.servlet.filter;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;

public class FilterServletOutputStream extends ServletOutputStream {

    private DataOutputStream stream;

    public FilterServletOutputStream(OutputStream output) {
        stream = new DataOutputStream(output);
    }

    @Override
    public boolean isReady() { return true; }

    @Override
    public void setWriteListener(WriteListener listener) {
        throw new UnsupportedOperationException("This buffering stream supports blocking IO only");
    }

    public void write(int b) throws IOException {
        stream.write(b);
    }

    public void write(byte[] b) throws IOException {
        stream.write(b);
    }

    public void write(byte[] b, int off, int len) throws IOException {
        stream.write(b, off, len);
    }

}