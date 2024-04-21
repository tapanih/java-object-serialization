package dev.honkanen.compression;

public interface CompressionStrategy {

    byte[] compress(byte[] src);

    byte[] decompress(byte[] src);
}