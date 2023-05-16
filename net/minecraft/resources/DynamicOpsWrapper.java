package net.minecraft.resources;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.ListBuilder;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import com.mojang.serialization.ListBuilder.Builder;
import com.mojang.serialization.RecordBuilder.MapBuilder;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public abstract class DynamicOpsWrapper<T> implements DynamicOps<T> {
   protected final DynamicOps<T> a;

   protected DynamicOpsWrapper(DynamicOps<T> var0) {
      this.a = var0;
   }

   public T empty() {
      return (T)this.a.empty();
   }

   public <U> U convertTo(DynamicOps<U> var0, T var1) {
      return (U)this.a.convertTo(var0, var1);
   }

   public DataResult<Number> getNumberValue(T var0) {
      return this.a.getNumberValue(var0);
   }

   public T createNumeric(Number var0) {
      return (T)this.a.createNumeric(var0);
   }

   public T createByte(byte var0) {
      return (T)this.a.createByte(var0);
   }

   public T createShort(short var0) {
      return (T)this.a.createShort(var0);
   }

   public T createInt(int var0) {
      return (T)this.a.createInt(var0);
   }

   public T createLong(long var0) {
      return (T)this.a.createLong(var0);
   }

   public T createFloat(float var0) {
      return (T)this.a.createFloat(var0);
   }

   public T createDouble(double var0) {
      return (T)this.a.createDouble(var0);
   }

   public DataResult<Boolean> getBooleanValue(T var0) {
      return this.a.getBooleanValue(var0);
   }

   public T createBoolean(boolean var0) {
      return (T)this.a.createBoolean(var0);
   }

   public DataResult<String> getStringValue(T var0) {
      return this.a.getStringValue(var0);
   }

   public T createString(String var0) {
      return (T)this.a.createString(var0);
   }

   public DataResult<T> mergeToList(T var0, T var1) {
      return this.a.mergeToList(var0, var1);
   }

   public DataResult<T> mergeToList(T var0, List<T> var1) {
      return this.a.mergeToList(var0, var1);
   }

   public DataResult<T> mergeToMap(T var0, T var1, T var2) {
      return this.a.mergeToMap(var0, var1, var2);
   }

   public DataResult<T> mergeToMap(T var0, MapLike<T> var1) {
      return this.a.mergeToMap(var0, var1);
   }

   public DataResult<Stream<Pair<T, T>>> getMapValues(T var0) {
      return this.a.getMapValues(var0);
   }

   public DataResult<Consumer<BiConsumer<T, T>>> getMapEntries(T var0) {
      return this.a.getMapEntries(var0);
   }

   public T createMap(Stream<Pair<T, T>> var0) {
      return (T)this.a.createMap(var0);
   }

   public DataResult<MapLike<T>> getMap(T var0) {
      return this.a.getMap(var0);
   }

   public DataResult<Stream<T>> getStream(T var0) {
      return this.a.getStream(var0);
   }

   public DataResult<Consumer<Consumer<T>>> getList(T var0) {
      return this.a.getList(var0);
   }

   public T createList(Stream<T> var0) {
      return (T)this.a.createList(var0);
   }

   public DataResult<ByteBuffer> getByteBuffer(T var0) {
      return this.a.getByteBuffer(var0);
   }

   public T createByteList(ByteBuffer var0) {
      return (T)this.a.createByteList(var0);
   }

   public DataResult<IntStream> getIntStream(T var0) {
      return this.a.getIntStream(var0);
   }

   public T createIntList(IntStream var0) {
      return (T)this.a.createIntList(var0);
   }

   public DataResult<LongStream> getLongStream(T var0) {
      return this.a.getLongStream(var0);
   }

   public T createLongList(LongStream var0) {
      return (T)this.a.createLongList(var0);
   }

   public T remove(T var0, String var1) {
      return (T)this.a.remove(var0, var1);
   }

   public boolean compressMaps() {
      return this.a.compressMaps();
   }

   public ListBuilder<T> listBuilder() {
      return new Builder(this);
   }

   public RecordBuilder<T> mapBuilder() {
      return new MapBuilder(this);
   }
}
