package dev.honkanen.compression;

import org.tukaani.xz.LZMA2Options;
import org.tukaani.xz.LZMAInputStream;
import org.tukaani.xz.LZMAOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class LzmaCompressor implements CompressionStrategy {

    private final LZMA2Options options = new LZMA2Options();

    @Override
    public byte[] compress(byte[] src) {
        try {
            var dest = new ByteArrayOutputStream();
            var out = new LZMAOutputStream(dest, options, -1);
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
            var dest = new ByteArrayOutputStream();
            var in = new LZMAInputStream(new ByteArrayInputStream(src));
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
