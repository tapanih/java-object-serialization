package dev.honkanen.benchmark;

import dev.honkanen.compression.*;
import dev.honkanen.input.GeoJson;
import dev.honkanen.input.GeoJsonParser;
import dev.honkanen.serialization.*;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 1, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 1, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class JavaSerializationBenchmark {

    private static final ThreadLocal<Map<String, SerializationStrategy>> serializers = ThreadLocal.withInitial(() -> {
        Map<String, SerializationStrategy> serializers = new HashMap<>();
        serializers.put("JavaSerializer", new JavaSerializer());
        serializers.put("KryoSerializer", new KryoSerializer());
        serializers.put("CborSerializer", new CborSerializer());
        serializers.put("IonSerializer", new IonSerializer());
        return serializers;
    });

    private static final ThreadLocal<Map<String, CompressionStrategy>> compressors = ThreadLocal.withInitial(() -> {
        Map<String, CompressionStrategy> compressors = new HashMap<>();
        compressors.put("NoopCompressor", new NoopCompressor());
        compressors.put("DeflateCompressor", new DeflateCompressor());
        compressors.put("ZstdCompressor", new ZstdCompressor());
        compressors.put("XzCompressor", new XzCompressor());
        compressors.put("LzmaCompressor", new LzmaCompressor());
        return compressors;
    });

    @Param({"KryoSerializer", "JavaSerializer", "CborSerializer", "IonSerializer"})
    public String serializerName;

    @Param({"NoopCompressor", "DeflateCompressor", "ZstdCompressor", "XzCompressor", "LzmaCompressor"})
    public String compressorName;

    private static final ThreadLocal<GeoJson> geoJson = ThreadLocal.withInitial(() -> {
        try {
            var resource = JavaSerializationBenchmark.class
                    .getClassLoader()
                    .getResource("data/HSL_lines.geojson");
            if (resource == null) {
                throw new RuntimeException("Resource not found");
            }
            var reader = new BufferedReader(new FileReader(new File(resource.toURI())));
            var content = reader.lines().collect(Collectors.joining("\n"));
            reader.close();
            return new GeoJsonParser().parse(content);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    });

    private static final ConcurrentHashMap<BenchmarkParams, BenchmarkInfo> benchmarkInfos = new ConcurrentHashMap<>();

    @TearDown(Level.Trial)
    public void tearDown() {
        // TODO: Print results after benchmarking (write to file and read in Main.java)
        benchmarkInfos.forEach((params, info) -> {
            System.out.println("Serialized size: " + info.serializedSize() + " bytes" +
                    ", Compressed size: " + info.compressedSize() + " bytes");
        });
    }

    @Benchmark
    public void testJavaSerialization(Blackhole blackhole) {
        var params = new BenchmarkParams(serializerName, compressorName);
        var serializer = serializers.get().get(serializerName);
        var serialized = serializer.serialize(geoJson.get());
        var compressor = compressors.get().get(compressorName);
        var compressed = compressor.compress(serialized);
        benchmarkInfos.put(params, new BenchmarkInfo(serializerName, compressorName, serialized.length, compressed.length));
        var deserialized = serializer.deserialize(compressor.decompress(compressed), GeoJson.class);
        blackhole.consume(deserialized);
    }
}
