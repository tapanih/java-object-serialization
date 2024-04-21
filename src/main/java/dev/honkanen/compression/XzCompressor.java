package dev.honkanen.compression;

import org.tukaani.xz.LZMA2Options;
import org.tukaani.xz.XZInputStream;
import org.tukaani.xz.XZOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class XzCompressor implements CompressionStrategy {

    LZMA2Options options = new LZMA2Options();

    @Override
    public byte[] compress(byte[] src) {
        try {
            ByteArrayOutputStream dest = new ByteArrayOutputStream();
            XZOutputStream out = new XZOutputStream(dest, options);
            out.write(src);
            out.close();
            return dest.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public byte[] decompress(byte[] src) {
        try {
            ByteArrayOutputStream dest = new ByteArrayOutputStream();
            XZInputStream in = new XZInputStream(new ByteArrayInputStream(src));
            byte[] buffer = new byte[8192];
            int size;
            while ((size = in.read(buffer)) != -1) {
                dest.write(buffer, 0, size);
            }
            return dest.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
