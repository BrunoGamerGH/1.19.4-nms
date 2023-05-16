package net.minecraft.world.level.saveddata.maps;

import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.GameProfileSerializer;
import net.minecraft.nbt.NBTTagCompound;

public class WorldMapFrame {
   private final BlockPosition a;
   private final int b;
   private final int c;

   public WorldMapFrame(BlockPosition var0, int var1, int var2) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
   }

   public static WorldMapFrame a(NBTTagCompound var0) {
      BlockPosition var1 = GameProfileSerializer.b(var0.p("Pos"));
      int var2 = var0.h("Rotation");
      int var3 = var0.h("EntityId");
      return new WorldMapFrame(var1, var2, var3);
   }

   public NBTTagCompound a() {
      NBTTagCompound var0 = new NBTTagCompound();
      var0.a("Pos", GameProfileSerializer.a(this.a));
      var0.a("Rotation", this.b);
      var0.a("EntityId", this.c);
      return var0;
   }

   public BlockPosition b() {
      return this.a;
   }

   public int c() {
      return this.b;
   }

   public int d() {
      return this.c;
   }

   public String e() {
      return a(this.a);
   }

   public static String a(BlockPosition var0) {
      return "frame-" + var0.u() + "," + var0.v() + "," + var0.w();
   }
}
