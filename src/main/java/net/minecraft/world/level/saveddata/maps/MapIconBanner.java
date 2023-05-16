package net.minecraft.world.level.saveddata.maps;

import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.GameProfileSerializer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.world.item.EnumColor;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityBanner;

public class MapIconBanner {
   private final BlockPosition a;
   private final EnumColor b;
   @Nullable
   private final IChatBaseComponent c;

   public MapIconBanner(BlockPosition var0, EnumColor var1, @Nullable IChatBaseComponent var2) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
   }

   public static MapIconBanner a(NBTTagCompound var0) {
      BlockPosition var1 = GameProfileSerializer.b(var0.p("Pos"));
      EnumColor var2 = EnumColor.a(var0.l("Color"), EnumColor.a);
      IChatBaseComponent var3 = var0.e("Name") ? IChatBaseComponent.ChatSerializer.a(var0.l("Name")) : null;
      return new MapIconBanner(var1, var2, var3);
   }

   @Nullable
   public static MapIconBanner a(IBlockAccess var0, BlockPosition var1) {
      TileEntity var2 = var0.c_(var1);
      if (var2 instanceof TileEntityBanner var3) {
         EnumColor var4 = var3.g();
         IChatBaseComponent var5 = var3.aa() ? var3.ab() : null;
         return new MapIconBanner(var1, var4, var5);
      } else {
         return null;
      }
   }

   public BlockPosition a() {
      return this.a;
   }

   public EnumColor b() {
      return this.b;
   }

   public MapIcon.Type c() {
      switch(this.b) {
         case a:
            return MapIcon.Type.k;
         case b:
            return MapIcon.Type.l;
         case c:
            return MapIcon.Type.m;
         case d:
            return MapIcon.Type.n;
         case e:
            return MapIcon.Type.o;
         case f:
            return MapIcon.Type.p;
         case g:
            return MapIcon.Type.q;
         case h:
            return MapIcon.Type.r;
         case i:
            return MapIcon.Type.s;
         case j:
            return MapIcon.Type.t;
         case k:
            return MapIcon.Type.u;
         case l:
            return MapIcon.Type.v;
         case m:
            return MapIcon.Type.w;
         case n:
            return MapIcon.Type.x;
         case o:
            return MapIcon.Type.y;
         case p:
         default:
            return MapIcon.Type.z;
      }
   }

   @Nullable
   public IChatBaseComponent d() {
      return this.c;
   }

   @Override
   public boolean equals(Object var0) {
      if (this == var0) {
         return true;
      } else if (var0 != null && this.getClass() == var0.getClass()) {
         MapIconBanner var1 = (MapIconBanner)var0;
         return Objects.equals(this.a, var1.a) && this.b == var1.b && Objects.equals(this.c, var1.c);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.a, this.b, this.c);
   }

   public NBTTagCompound e() {
      NBTTagCompound var0 = new NBTTagCompound();
      var0.a("Pos", GameProfileSerializer.a(this.a));
      var0.a("Color", this.b.b());
      if (this.c != null) {
         var0.a("Name", IChatBaseComponent.ChatSerializer.a(this.c));
      }

      return var0;
   }

   public String f() {
      return "banner-" + this.a.u() + "," + this.a.v() + "," + this.a.w();
   }
}
