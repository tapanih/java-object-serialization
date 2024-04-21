package dev.honkanen.benchmark;

public record BenchmarkInfo(String serializerName, String compressorName, int serializedSize, int compressedSize) {
}
