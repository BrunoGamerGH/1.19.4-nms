package net.minecraft.core;

import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.Lifecycle;
import com.mojang.util.UUIDTypeAdapter;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.UUID;
import net.minecraft.SystemUtils;

public final class UUIDUtil {
   public static final Codec<UUID> a = Codec.INT_STREAM.comapFlatMap(var0 -> SystemUtils.a(var0, 4).map(UUIDUtil::a), var0 -> Arrays.stream(a(var0)));
   public static final Codec<UUID> b = Codec.STRING.comapFlatMap(var0 -> {
      try {
         return DataResult.success(UUID.fromString(var0), Lifecycle.stable());
      } catch (IllegalArgumentException var2) {
         return DataResult.error(() -> "Invalid UUID " + var0 + ": " + var2.getMessage());
      }
   }, UUID::toString);
   public static Codec<UUID> c = Codec.either(a, Codec.STRING.comapFlatMap(var0 -> {
      try {
         return DataResult.success(UUIDTypeAdapter.fromString(var0), Lifecycle.stable());
      } catch (IllegalArgumentException var2) {
         return DataResult.error(() -> "Invalid UUID " + var0 + ": " + var2.getMessage());
      }
   }, UUIDTypeAdapter::fromUUID)).xmap(var0 -> (UUID)var0.map(var0x -> var0x, var0x -> var0x), Either::right);
   public static final int d = 16;
   private static final String e = "OfflinePlayer:";

   private UUIDUtil() {
   }

   public static UUID a(int[] var0) {
      return new UUID((long)var0[0] << 32 | (long)var0[1] & 4294967295L, (long)var0[2] << 32 | (long)var0[3] & 4294967295L);
   }

   public static int[] a(UUID var0) {
      long var1 = var0.getMostSignificantBits();
      long var3 = var0.getLeastSignificantBits();
      return a(var1, var3);
   }

   private static int[] a(long var0, long var2) {
      return new int[]{(int)(var0 >> 32), (int)var0, (int)(var2 >> 32), (int)var2};
   }

   public static byte[] b(UUID var0) {
      byte[] var1 = new byte[16];
      ByteBuffer.wrap(var1).order(ByteOrder.BIG_ENDIAN).putLong(var0.getMostSignificantBits()).putLong(var0.getLeastSignificantBits());
      return var1;
   }

   public static UUID a(Dynamic<?> var0) {
      int[] var1 = var0.asIntStream().toArray();
      if (var1.length != 4) {
         throw new IllegalArgumentException("Could not read UUID. Expected int-array of length 4, got " + var1.length + ".");
      } else {
         return a(var1);
      }
   }

   public static UUID a(GameProfile var0) {
      UUID var1 = var0.getId();
      if (var1 == null) {
         var1 = a(var0.getName());
      }

      return var1;
   }

   public static UUID a(String var0) {
      return UUID.nameUUIDFromBytes(("OfflinePlayer:" + var0).getBytes(StandardCharsets.UTF_8));
   }
}
