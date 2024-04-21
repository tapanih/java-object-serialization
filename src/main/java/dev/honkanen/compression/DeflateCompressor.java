package dev.honkanen.compression;

import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * Compresses and decompresses byte arrays using the DEFLATE algorithm.
 */
public class DeflateCompressor implements CompressionStrategy {
    @Override
    public byte[] compress(byte[] src) {
        Deflater deflater = new Deflater(Deflater.BEST_COMPRESSION);
        deflater.setInput(src);
        deflater.finish();
        byte[] buffer = new byte[src.length * 2];
        int size = deflater.deflate(buffer);
        deflater.end();
        byte[] compressed = new byte[size];
        System.arraycopy(buffer, 0, compressed, 0, size);
        return compressed;
    }

    @Override
    public byte[] decompress(byte[] src) {
        try {
            Inflater inflater = new Inflater();
            inflater.setInput(src);
            byte[] buffer = new byte[src.length * 100]; // TODO: Use streaming decompression?
            int size = inflater.inflate(buffer);
            inflater.end();
            byte[] decompressed = new byte[size];
            System.arraycopy(buffer, 0, decompressed, 0, size);
            return decompressed;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
