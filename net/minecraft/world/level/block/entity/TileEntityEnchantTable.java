package net.minecraft.world.level.block.entity;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.INamableTileEntity;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.IBlockData;

public class TileEntityEnchantTable extends TileEntity implements INamableTileEntity {
   public int a;
   public float b;
   public float c;
   public float d;
   public float e;
   public float f;
   public float g;
   public float h;
   public float i;
   public float j;
   private static final RandomSource k = RandomSource.a();
   private IChatBaseComponent l;

   public TileEntityEnchantTable(BlockPosition var0, IBlockData var1) {
      super(TileEntityTypes.m, var0, var1);
   }

   @Override
   protected void b(NBTTagCompound var0) {
      super.b(var0);
      if (this.aa()) {
         var0.a("CustomName", IChatBaseComponent.ChatSerializer.a(this.l));
      }
   }

   @Override
   public void a(NBTTagCompound var0) {
      super.a(var0);
      if (var0.b("CustomName", 8)) {
         this.l = IChatBaseComponent.ChatSerializer.a(var0.l("CustomName"));
      }
   }

   public static void a(World var0, BlockPosition var1, IBlockData var2, TileEntityEnchantTable var3) {
      var3.g = var3.f;
      var3.i = var3.h;
      EntityHuman var4 = var0.a((double)var1.u() + 0.5, (double)var1.v() + 0.5, (double)var1.w() + 0.5, 3.0, false);
      if (var4 != null) {
         double var5 = var4.dl() - ((double)var1.u() + 0.5);
         double var7 = var4.dr() - ((double)var1.w() + 0.5);
         var3.j = (float)MathHelper.d(var7, var5);
         var3.f += 0.1F;
         if (var3.f < 0.5F || k.a(40) == 0) {
            float var9 = var3.d;

            do {
               var3.d += (float)(k.a(4) - k.a(4));
            } while(var9 == var3.d);
         }
      } else {
         var3.j += 0.02F;
         var3.f -= 0.1F;
      }

      while(var3.h >= (float) Math.PI) {
         var3.h -= (float) (Math.PI * 2);
      }

      while(var3.h < (float) -Math.PI) {
         var3.h += (float) (Math.PI * 2);
      }

      while(var3.j >= (float) Math.PI) {
         var3.j -= (float) (Math.PI * 2);
      }

      while(var3.j < (float) -Math.PI) {
         var3.j += (float) (Math.PI * 2);
      }

      float var5 = var3.j - var3.h;

      while(var5 >= (float) Math.PI) {
         var5 -= (float) (Math.PI * 2);
      }

      while(var5 < (float) -Math.PI) {
         var5 += (float) (Math.PI * 2);
      }

      var3.h += var5 * 0.4F;
      var3.f = MathHelper.a(var3.f, 0.0F, 1.0F);
      ++var3.a;
      var3.c = var3.b;
      float var6 = (var3.d - var3.b) * 0.4F;
      float var7 = 0.2F;
      var6 = MathHelper.a(var6, -0.2F, 0.2F);
      var3.e += (var6 - var3.e) * 0.9F;
      var3.b += var3.e;
   }

   @Override
   public IChatBaseComponent Z() {
      return (IChatBaseComponent)(this.l != null ? this.l : IChatBaseComponent.c("container.enchant"));
   }

   public void a(@Nullable IChatBaseComponent var0) {
      this.l = var0;
   }

   @Nullable
   @Override
   public IChatBaseComponent ab() {
      return this.l;
   }
}
