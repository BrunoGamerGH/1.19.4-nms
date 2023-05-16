package net.minecraft.network.protocol.game;

import com.google.common.collect.Lists;
import java.util.BitSet;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.SectionPosition;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.EnumSkyBlock;
import net.minecraft.world.level.chunk.NibbleArray;
import net.minecraft.world.level.lighting.LightEngine;

public class ClientboundLightUpdatePacketData {
   private final BitSet a;
   private final BitSet b;
   private final BitSet c;
   private final BitSet d;
   private final List<byte[]> e;
   private final List<byte[]> f;
   private final boolean g;

   public ClientboundLightUpdatePacketData(ChunkCoordIntPair var0, LightEngine var1, @Nullable BitSet var2, @Nullable BitSet var3, boolean var4) {
      this.g = var4;
      this.a = new BitSet();
      this.b = new BitSet();
      this.c = new BitSet();
      this.d = new BitSet();
      this.e = Lists.newArrayList();
      this.f = Lists.newArrayList();

      for(int var5 = 0; var5 < var1.b(); ++var5) {
         if (var2 == null || var2.get(var5)) {
            this.a(var0, var1, EnumSkyBlock.a, var5, this.a, this.c, this.e);
         }

         if (var3 == null || var3.get(var5)) {
            this.a(var0, var1, EnumSkyBlock.b, var5, this.b, this.d, this.f);
         }
      }
   }

   public ClientboundLightUpdatePacketData(PacketDataSerializer var0, int var1, int var2) {
      this.g = var0.readBoolean();
      this.a = var0.y();
      this.b = var0.y();
      this.c = var0.y();
      this.d = var0.y();
      this.e = var0.a((PacketDataSerializer.a<byte[]>)(var0x -> var0x.b(2048)));
      this.f = var0.a((PacketDataSerializer.a<byte[]>)(var0x -> var0x.b(2048)));
   }

   public void a(PacketDataSerializer var0) {
      var0.writeBoolean(this.g);
      var0.a(this.a);
      var0.a(this.b);
      var0.a(this.c);
      var0.a(this.d);
      var0.a(this.e, PacketDataSerializer::a);
      var0.a(this.f, PacketDataSerializer::a);
   }

   private void a(ChunkCoordIntPair var0, LightEngine var1, EnumSkyBlock var2, int var3, BitSet var4, BitSet var5, List<byte[]> var6) {
      NibbleArray var7 = var1.a(var2).a(SectionPosition.a(var0, var1.c() + var3));
      if (var7 != null) {
         if (var7.c()) {
            var5.set(var3);
         } else {
            var4.set(var3);
            var6.add((byte[])var7.a().clone());
         }
      }
   }

   public BitSet a() {
      return this.a;
   }

   public BitSet b() {
      return this.c;
   }

   public List<byte[]> c() {
      return this.e;
   }

   public BitSet d() {
      return this.b;
   }

   public BitSet e() {
      return this.d;
   }

   public List<byte[]> f() {
      return this.f;
   }

   public boolean g() {
      return this.g;
   }
}
