package com.mophsic.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author xiaofei
 * @date 2017/2/9
 */
public class GetChannel {
    private static final int BSIZE = 1024;
    public static void main(String[] args) throws IOException {

        FileChannel channel = new FileOutputStream("data.txt").getChannel();
        channel.write(ByteBuffer.wrap("some text".getBytes()));
        channel.close();

        channel = new RandomAccessFile("data.txt", "rw").getChannel();
        channel.position(channel.size());
        channel.write(ByteBuffer.wrap("more text".getBytes()));
        channel.close();

        channel = new FileInputStream("data.txt").getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(BSIZE);
        channel.read(buffer);
        buffer.flip();

        while (buffer.hasRemaining()) {
            System.out.print((char) buffer.get());
        }
    }
}
