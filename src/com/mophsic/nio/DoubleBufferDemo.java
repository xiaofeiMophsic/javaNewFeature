package com.mophsic.nio;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;

/**
 * @author xiaofei
 * @date 2017/2/14
 */
public class DoubleBufferDemo {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        DoubleBuffer db = byteBuffer.asDoubleBuffer();

        db.put(new double[]{1.0, 1.1, 1.2, 1.3});
        System.out.println(db.get(2));

        db.put(3, 1.4);

        db.flip();

        while (db.hasRemaining()){
            System.out.println(db.get());
        }
    }
}
