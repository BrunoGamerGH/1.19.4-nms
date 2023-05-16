package net.minecraft.network.protocol.game;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public class PacketPlayInBEdit implements Packet<PacketListenerPlayIn> {
   public static final int a = 4;
   private static final int b = 128;
   private static final int c = 8192;
   private static final int d = 200;
   private final int e;
   private final List<String> f;
   private final Optional<String> g;

   public PacketPlayInBEdit(int var0, List<String> var1, Optional<String> var2) {
      this.e = var0;
      this.f = ImmutableList.copyOf(var1);
      this.g = var2;
   }

   public PacketPlayInBEdit(PacketDataSerializer var0) {
      this.e = var0.m();
      this.f = var0.a(PacketDataSerializer.a(Lists::newArrayListWithCapacity, 200), var0x -> var0x.e(8192));
      this.g = var0.b(var0x -> var0x.e(128));
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.d(this.e);
      var0.a(this.f, (var0x, var1x) -> var0x.a(var1x, 8192));
      var0.a(this.g, (var0x, var1x) -> var0x.a(var1x, 128));
   }

   public void a(PacketListenerPlayIn var0) {
      var0.a(this);
   }

   public List<String> a() {
      return this.f;
   }

   public Optional<String> c() {
      return this.g;
   }

   public int d() {
      return this.e;
   }
}
