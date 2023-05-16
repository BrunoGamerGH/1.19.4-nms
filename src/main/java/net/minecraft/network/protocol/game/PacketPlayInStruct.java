package net.minecraft.network.protocol.game;

import net.minecraft.core.BaseBlockPosition;
import net.minecraft.core.BlockPosition;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.util.MathHelper;
import net.minecraft.world.level.block.EnumBlockMirror;
import net.minecraft.world.level.block.EnumBlockRotation;
import net.minecraft.world.level.block.entity.TileEntityStructure;
import net.minecraft.world.level.block.state.properties.BlockPropertyStructureMode;

public class PacketPlayInStruct implements Packet<PacketListenerPlayIn> {
   private static final int a = 1;
   private static final int b = 2;
   private static final int c = 4;
   private final BlockPosition d;
   private final TileEntityStructure.UpdateType e;
   private final BlockPropertyStructureMode f;
   private final String g;
   private final BlockPosition h;
   private final BaseBlockPosition i;
   private final EnumBlockMirror j;
   private final EnumBlockRotation k;
   private final String l;
   private final boolean m;
   private final boolean n;
   private final boolean o;
   private final float p;
   private final long q;

   public PacketPlayInStruct(
      BlockPosition var0,
      TileEntityStructure.UpdateType var1,
      BlockPropertyStructureMode var2,
      String var3,
      BlockPosition var4,
      BaseBlockPosition var5,
      EnumBlockMirror var6,
      EnumBlockRotation var7,
      String var8,
      boolean var9,
      boolean var10,
      boolean var11,
      float var12,
      long var13
   ) {
      this.d = var0;
      this.e = var1;
      this.f = var2;
      this.g = var3;
      this.h = var4;
      this.i = var5;
      this.j = var6;
      this.k = var7;
      this.l = var8;
      this.m = var9;
      this.n = var10;
      this.o = var11;
      this.p = var12;
      this.q = var13;
   }

   public PacketPlayInStruct(PacketDataSerializer var0) {
      this.d = var0.f();
      this.e = var0.b(TileEntityStructure.UpdateType.class);
      this.f = var0.b(BlockPropertyStructureMode.class);
      this.g = var0.s();
      int var1 = 48;
      this.h = new BlockPosition(MathHelper.a(var0.readByte(), -48, 48), MathHelper.a(var0.readByte(), -48, 48), MathHelper.a(var0.readByte(), -48, 48));
      int var2 = 48;
      this.i = new BaseBlockPosition(MathHelper.a(var0.readByte(), 0, 48), MathHelper.a(var0.readByte(), 0, 48), MathHelper.a(var0.readByte(), 0, 48));
      this.j = var0.b(EnumBlockMirror.class);
      this.k = var0.b(EnumBlockRotation.class);
      this.l = var0.e(128);
      this.p = MathHelper.a(var0.readFloat(), 0.0F, 1.0F);
      this.q = var0.n();
      int var3 = var0.readByte();
      this.m = (var3 & 1) != 0;
      this.n = (var3 & 2) != 0;
      this.o = (var3 & 4) != 0;
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(this.d);
      var0.a(this.e);
      var0.a(this.f);
      var0.a(this.g);
      var0.writeByte(this.h.u());
      var0.writeByte(this.h.v());
      var0.writeByte(this.h.w());
      var0.writeByte(this.i.u());
      var0.writeByte(this.i.v());
      var0.writeByte(this.i.w());
      var0.a(this.j);
      var0.a(this.k);
      var0.a(this.l);
      var0.writeFloat(this.p);
      var0.b(this.q);
      int var1 = 0;
      if (this.m) {
         var1 |= 1;
      }

      if (this.n) {
         var1 |= 2;
      }

      if (this.o) {
         var1 |= 4;
      }

      var0.writeByte(var1);
   }

   public void a(PacketListenerPlayIn var0) {
      var0.a(this);
   }

   public BlockPosition a() {
      return this.d;
   }

   public TileEntityStructure.UpdateType c() {
      return this.e;
   }

   public BlockPropertyStructureMode d() {
      return this.f;
   }

   public String e() {
      return this.g;
   }

   public BlockPosition f() {
      return this.h;
   }

   public BaseBlockPosition g() {
      return this.i;
   }

   public EnumBlockMirror h() {
      return this.j;
   }

   public EnumBlockRotation i() {
      return this.k;
   }

   public String j() {
      return this.l;
   }

   public boolean k() {
      return this.m;
   }

   public boolean l() {
      return this.n;
   }

   public boolean m() {
      return this.o;
   }

   public float n() {
      return this.p;
   }

   public long o() {
      return this.q;
   }
}
