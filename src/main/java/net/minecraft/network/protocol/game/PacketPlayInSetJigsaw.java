package net.minecraft.network.protocol.game;

import net.minecraft.core.BlockPosition;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.level.block.entity.TileEntityJigsaw;

public class PacketPlayInSetJigsaw implements Packet<PacketListenerPlayIn> {
   private final BlockPosition a;
   private final MinecraftKey b;
   private final MinecraftKey c;
   private final MinecraftKey d;
   private final String e;
   private final TileEntityJigsaw.JointType f;

   public PacketPlayInSetJigsaw(BlockPosition var0, MinecraftKey var1, MinecraftKey var2, MinecraftKey var3, String var4, TileEntityJigsaw.JointType var5) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
      this.d = var3;
      this.e = var4;
      this.f = var5;
   }

   public PacketPlayInSetJigsaw(PacketDataSerializer var0) {
      this.a = var0.f();
      this.b = var0.t();
      this.c = var0.t();
      this.d = var0.t();
      this.e = var0.s();
      this.f = TileEntityJigsaw.JointType.a(var0.s()).orElse(TileEntityJigsaw.JointType.b);
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(this.a);
      var0.a(this.b);
      var0.a(this.c);
      var0.a(this.d);
      var0.a(this.e);
      var0.a(this.f.c());
   }

   public void a(PacketListenerPlayIn var0) {
      var0.a(this);
   }

   public BlockPosition a() {
      return this.a;
   }

   public MinecraftKey c() {
      return this.b;
   }

   public MinecraftKey d() {
      return this.c;
   }

   public MinecraftKey e() {
      return this.d;
   }

   public String f() {
      return this.e;
   }

   public TileEntityJigsaw.JointType g() {
      return this.f;
   }
}
