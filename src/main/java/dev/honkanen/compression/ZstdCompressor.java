package dev.honkanen.compression;


import com.github.luben.zstd.Zstd;

public class ZstdCompressor implements CompressionStrategy {

    private final int compressionLevel = 19;

    public ZstdCompressor() {
    }

    @Override
    public byte[] compress(byte[] src) {
        return Zstd.compress(src, compressionLevel);
    }

    @Override
    public byte[] decompress(byte[] src) {
        byte[] dst = new byte[src.length * 100]; // TODO: Use stream decompression
        Zstd.decompress(dst, src);
        return dst;
    }
}