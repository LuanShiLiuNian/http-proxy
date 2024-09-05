package cn.luanshiliunian.container;


import io.netty.buffer.ByteBuf;

import java.util.Arrays;

public class DynamicByte {

    private int length;
    private int cursor = 0;
    private byte[] body;

    public DynamicByte() {
        this(32);
    }

    public DynamicByte(int initial) {
        this.length = initial;
        body = new byte[length];
    }

    public void append(byte c) {

        if (cursor >= (length - 1)) {
            length *= 2;
            byte[] newBytes = new byte[length];
            System.arraycopy(body, 0, newBytes, 0, cursor);
            body = newBytes;
        }

        body[cursor] = c;
        cursor++;
    }

    public String getString() {
        return new String(body, 0, cursor);
    }

    public int getLength() {
        return cursor;
    }

    public byte[] getBody() {
        return body;
    }

    public void append(byte[] bytes, int c, int length) {
        int free = body.length - cursor;
        if (free < length) {
            this.length = body.length + length;
            byte[] newBytes = new byte[this.length];
            if (cursor > 0)
                System.arraycopy(body, 0, newBytes, 0, cursor);
            if (cursor == 0)
                System.arraycopy(bytes, c, newBytes, 0, length);
            else
                System.arraycopy(bytes, c, newBytes, cursor, length);
            body = newBytes;
            cursor += length;
        } else {
            System.arraycopy(bytes, c, body, cursor, length);
            cursor += length;
        }
    }


    public void append(byte[] bytes) {
        append(bytes, 0, bytes.length);
    }

    public byte[] getValidBody() {

        if (cursor == body.length) return body;

        byte[] cursorByte = new byte[cursor];
        System.arraycopy(body, 0, cursorByte, 0, cursor);

        return cursorByte;
    }

    public void clear() {
        if (cursor == 0) return;
        Arrays.fill(body, (byte) 0);
        cursor = 0;
    }

    public void appendPayload(ByteBuf byteBuf) {

        int readable = byteBuf.readableBytes();
        if (readable == 0) return;
        byte[] buf = new byte[readable];
        byteBuf.readBytes(buf);
        append(buf);
    }

}
