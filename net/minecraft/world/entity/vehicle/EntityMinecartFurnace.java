package net.minecraft.world.entity.vehicle;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.particles.Particles;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeItemStack;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.BlockFurnaceFurace;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.Vec3D;

public class EntityMinecartFurnace extends EntityMinecartAbstract {
   private static final DataWatcherObject<Boolean> e = DataWatcher.a(EntityMinecartFurnace.class, DataWatcherRegistry.k);
   public int f;
   public double c;
   public double d;
   private static final RecipeItemStack g = RecipeItemStack.a(Items.nE, Items.nF);

   public EntityMinecartFurnace(EntityTypes<? extends EntityMinecartFurnace> var0, World var1) {
      super(var0, var1);
   }

   public EntityMinecartFurnace(World var0, double var1, double var3, double var5) {
      super(EntityTypes.P, var0, var1, var3, var5);
   }

   @Override
   public EntityMinecartAbstract.EnumMinecartType s() {
      return EntityMinecartAbstract.EnumMinecartType.c;
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(e, false);
   }

   @Override
   public void l() {
      super.l();
      if (!this.H.k_()) {
         if (this.f > 0) {
            --this.f;
         }

         if (this.f <= 0) {
            this.c = 0.0;
            this.d = 0.0;
         }

         this.p(this.f > 0);
      }

      if (this.z() && this.af.a(4) == 0) {
         this.H.a(Particles.U, this.dl(), this.dn() + 0.8, this.dr(), 0.0, 0.0, 0.0);
      }
   }

   @Override
   protected double j() {
      return (this.aT() ? 3.0 : 4.0) / 20.0;
   }

   @Override
   protected Item i() {
      return Items.mY;
   }

   @Override
   protected void c(BlockPosition var0, IBlockData var1) {
      double var2 = 1.0E-4;
      double var4 = 0.001;
      super.c(var0, var1);
      Vec3D var6 = this.dj();
      double var7 = var6.i();
      double var9 = this.c * this.c + this.d * this.d;
      if (var9 > 1.0E-4 && var7 > 0.001) {
         double var11 = Math.sqrt(var7);
         double var13 = Math.sqrt(var9);
         this.c = var6.c / var11 * var13;
         this.d = var6.e / var11 * var13;
      }
   }

   @Override
   protected void o() {
      double var0 = this.c * this.c + this.d * this.d;
      if (var0 > 1.0E-7) {
         var0 = Math.sqrt(var0);
         this.c /= var0;
         this.d /= var0;
         Vec3D var2 = this.dj().d(0.8, 0.0, 0.8).b(this.c, 0.0, this.d);
         if (this.aT()) {
            var2 = var2.a(0.1);
         }

         this.f(var2);
      } else {
         this.f(this.dj().d(0.98, 0.0, 0.98));
      }

      super.o();
   }

   @Override
   public EnumInteractionResult a(EntityHuman var0, EnumHand var1) {
      ItemStack var2 = var0.b(var1);
      if (g.a(var2) && this.f + 3600 <= 32000) {
         if (!var0.fK().d) {
            var2.h(1);
         }

         this.f += 3600;
      }

      if (this.f > 0) {
         this.c = this.dl() - var0.dl();
         this.d = this.dr() - var0.dr();
      }

      return EnumInteractionResult.a(this.H.B);
   }

   @Override
   protected void b(NBTTagCompound var0) {
      super.b(var0);
      var0.a("PushX", this.c);
      var0.a("PushZ", this.d);
      var0.a("Fuel", (short)this.f);
   }

   @Override
   protected void a(NBTTagCompound var0) {
      super.a(var0);
      this.c = var0.k("PushX");
      this.d = var0.k("PushZ");
      this.f = var0.g("Fuel");
   }

   protected boolean z() {
      return this.am.a(e);
   }

   protected void p(boolean var0) {
      this.am.b(e, var0);
   }

   @Override
   public IBlockData v() {
      return Blocks.cC.o().a(BlockFurnaceFurace.a, EnumDirection.c).a(BlockFurnaceFurace.b, Boolean.valueOf(this.z()));
   }
}
