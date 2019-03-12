package cn.edu.llxy.dw.dss.util;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;


/**
 */
public class KeepAliveOutputStream extends FilterOutputStream {

    /**
     * Constructor of KeepAliveOutputStream.
     *
     * @param out an OutputStream value, it shoudl be standard output.
     */
    public KeepAliveOutputStream(OutputStream out) {
        super(out);
    }

    /**
     * This method does nothing.
     * @throws IOException as we are overridding FilterOutputStream.
     */
    public void close() throws IOException {
        // do not close the stream
    }
}