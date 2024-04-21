package dev.honkanen.compression;

/**
 * A compression strategy that does nothing.
 */
public class NoopCompressor implements CompressionStrategy {

    @Override
    public byte[] compress(byte[] src) {
        return src;
    }

    @Override
    public byte[] decompress(byte[] src) {
        return src;
    }
}
