package net.minecraft.world.level.gameevent;

import com.mojang.serialization.Codec;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.resources.MinecraftKey;

public interface PositionSourceType<T extends PositionSource> {
   PositionSourceType<BlockPositionSource> a = a("block", new BlockPositionSource.a());
   PositionSourceType<EntityPositionSource> b = a("entity", new EntityPositionSource.a());

   T b(PacketDataSerializer var1);

   void a(PacketDataSerializer var1, T var2);

   Codec<T> a();

   static <S extends PositionSourceType<T>, T extends PositionSource> S a(String var0, S var1) {
      return IRegistry.a(BuiltInRegistries.v, var0, var1);
   }

   static PositionSource c(PacketDataSerializer var0) {
      MinecraftKey var1 = var0.t();
      return BuiltInRegistries.v.b(var1).orElseThrow(() -> new IllegalArgumentException("Unknown position source type " + var1)).b(var0);
   }

   static <T extends PositionSource> void a(T var0, PacketDataSerializer var1) {
      var1.a(BuiltInRegistries.v.b(var0.a()));
      var0.a().a(var1, var0);
   }
}
