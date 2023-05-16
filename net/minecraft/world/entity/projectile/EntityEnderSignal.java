package net.minecraft.world.entity.projectile;

import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.particles.Particles;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.World;
import net.minecraft.world.phys.Vec3D;

public class EntityEnderSignal extends Entity implements ItemSupplier {
   private static final DataWatcherObject<ItemStack> b = DataWatcher.a(EntityEnderSignal.class, DataWatcherRegistry.h);
   public double c;
   public double d;
   public double e;
   public int f;
   public boolean g;

   public EntityEnderSignal(EntityTypes<? extends EntityEnderSignal> entitytypes, World world) {
      super(entitytypes, world);
   }

   public EntityEnderSignal(World world, double d0, double d1, double d2) {
      this(EntityTypes.K, world);
      this.e(d0, d1, d2);
   }

   public void a(ItemStack itemstack) {
      this.aj().b(b, SystemUtils.a(itemstack.o(), itemstack1 -> itemstack1.f(1)));
   }

   private ItemStack j() {
      return this.aj().a(b);
   }

   @Override
   public ItemStack i() {
      ItemStack itemstack = this.j();
      return itemstack.b() ? new ItemStack(Items.rz) : itemstack;
   }

   @Override
   protected void a_() {
      this.aj().a(b, ItemStack.b);
   }

   @Override
   public boolean a(double d0) {
      double d1 = this.cD().a() * 4.0;
      if (Double.isNaN(d1)) {
         d1 = 4.0;
      }

      d1 *= 64.0;
      return d0 < d1 * d1;
   }

   public void a(BlockPosition blockposition) {
      double d0 = (double)blockposition.u();
      int i = blockposition.v();
      double d1 = (double)blockposition.w();
      double d2 = d0 - this.dl();
      double d3 = d1 - this.dr();
      double d4 = Math.sqrt(d2 * d2 + d3 * d3);
      if (d4 > 12.0) {
         this.c = this.dl() + d2 / d4 * 12.0;
         this.e = this.dr() + d3 / d4 * 12.0;
         this.d = this.dn() + 8.0;
      } else {
         this.c = d0;
         this.d = (double)i;
         this.e = d1;
      }

      this.f = 0;
      this.g = this.af.a(5) > 0;
   }

   @Override
   public void l(double d0, double d1, double d2) {
      this.o(d0, d1, d2);
      if (this.M == 0.0F && this.L == 0.0F) {
         double d3 = Math.sqrt(d0 * d0 + d2 * d2);
         this.f((float)(MathHelper.d(d0, d2) * 180.0F / (float)Math.PI));
         this.e((float)(MathHelper.d(d1, d3) * 180.0F / (float)Math.PI));
         this.L = this.dw();
         this.M = this.dy();
      }
   }

   @Override
   public void l() {
      super.l();
      Vec3D vec3d = this.dj();
      double d0 = this.dl() + vec3d.c;
      double d1 = this.dn() + vec3d.d;
      double d2 = this.dr() + vec3d.e;
      double d3 = vec3d.h();
      this.e(IProjectile.d(this.M, (float)(MathHelper.d(vec3d.d, d3) * 180.0F / (float)Math.PI)));
      this.f(IProjectile.d(this.L, (float)(MathHelper.d(vec3d.c, vec3d.e) * 180.0F / (float)Math.PI)));
      if (!this.H.B) {
         double d4 = this.c - d0;
         double d5 = this.e - d2;
         float f = (float)Math.sqrt(d4 * d4 + d5 * d5);
         float f1 = (float)MathHelper.d(d5, d4);
         double d6 = MathHelper.d(0.0025, d3, (double)f);
         double d7 = vec3d.d;
         if (f < 1.0F) {
            d6 *= 0.8;
            d7 *= 0.8;
         }

         int i = this.dn() < this.d ? 1 : -1;
         vec3d = new Vec3D(Math.cos((double)f1) * d6, d7 + ((double)i - d7) * 0.015F, Math.sin((double)f1) * d6);
         this.f(vec3d);
      }

      float f2 = 0.25F;
      if (this.aT()) {
         for(int j = 0; j < 4; ++j) {
            this.H.a(Particles.e, d0 - vec3d.c * 0.25, d1 - vec3d.d * 0.25, d2 - vec3d.e * 0.25, vec3d.c, vec3d.d, vec3d.e);
         }
      } else {
         this.H
            .a(
               Particles.Z,
               d0 - vec3d.c * 0.25 + this.af.j() * 0.6 - 0.3,
               d1 - vec3d.d * 0.25 - 0.5,
               d2 - vec3d.e * 0.25 + this.af.j() * 0.6 - 0.3,
               vec3d.c,
               vec3d.d,
               vec3d.e
            );
      }

      if (!this.H.B) {
         this.e(d0, d1, d2);
         ++this.f;
         if (this.f > 80 && !this.H.B) {
            this.a(SoundEffects.gW, 1.0F, 1.0F);
            this.ai();
            if (this.g) {
               this.H.b(new EntityItem(this.H, this.dl(), this.dn(), this.dr(), this.i()));
            } else {
               this.H.c(2003, this.dg(), 0);
            }
         }
      } else {
         this.p(d0, d1, d2);
      }
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      ItemStack itemstack = this.j();
      if (!itemstack.b()) {
         nbttagcompound.a("Item", itemstack.b(new NBTTagCompound()));
      }
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      ItemStack itemstack = ItemStack.a(nbttagcompound.p("Item"));
      if (!itemstack.b()) {
         this.a(itemstack);
      }
   }

   @Override
   public float bh() {
      return 1.0F;
   }

   @Override
   public boolean cl() {
      return false;
   }
}
