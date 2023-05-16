package net.minecraft.world.entity.vehicle;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.particles.Particles;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagsBlock;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.projectile.EntityArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.material.Fluid;
import org.bukkit.event.entity.ExplosionPrimeEvent;

public class EntityMinecartTNT extends EntityMinecartAbstract {
   private static final byte c = 10;
   private int d = -1;

   public EntityMinecartTNT(EntityTypes<? extends EntityMinecartTNT> entitytypes, World world) {
      super(entitytypes, world);
   }

   public EntityMinecartTNT(World world, double d0, double d1, double d2) {
      super(EntityTypes.aZ, world, d0, d1, d2);
   }

   @Override
   public EntityMinecartAbstract.EnumMinecartType s() {
      return EntityMinecartAbstract.EnumMinecartType.d;
   }

   @Override
   public IBlockData v() {
      return Blocks.cj.o();
   }

   @Override
   public void l() {
      super.l();
      if (this.d > 0) {
         --this.d;
         this.H.a(Particles.ab, this.dl(), this.dn() + 0.5, this.dr(), 0.0, 0.0, 0.0);
      } else if (this.d == 0) {
         this.h(this.dj().i());
      }

      if (this.O) {
         double d0 = this.dj().i();
         if (d0 >= 0.01F) {
            this.h(d0);
         }
      }
   }

   @Override
   public boolean a(DamageSource damagesource, float f) {
      Entity entity = damagesource.c();
      if (entity instanceof EntityArrow entityarrow && entityarrow.bK()) {
         DamageSource damagesource1 = this.dG().d(this, damagesource.d());
         this.a(damagesource1, entityarrow.dj().g());
      }

      return super.a(damagesource, f);
   }

   @Override
   public void a(DamageSource damagesource) {
      double d0 = this.dj().i();
      if (!damagesource.a(DamageTypeTags.i) && !damagesource.a(DamageTypeTags.l) && d0 < 0.01F) {
         super.a(damagesource);
      } else if (this.d < 0) {
         this.z();
         this.d = this.af.a(20) + this.af.a(20);
      }
   }

   @Override
   protected Item i() {
      return Items.mZ;
   }

   protected void h(double d0) {
      this.a(null, d0);
   }

   protected void a(@Nullable DamageSource damagesource, double d0) {
      if (!this.H.B) {
         double d1 = Math.sqrt(d0);
         if (d1 > 5.0) {
            d1 = 5.0;
         }

         ExplosionPrimeEvent event = new ExplosionPrimeEvent(this.getBukkitEntity(), (float)(4.0 + this.af.j() * 1.5 * d1), false);
         this.H.getCraftServer().getPluginManager().callEvent(event);
         if (event.isCancelled()) {
            this.d = -1;
            return;
         }

         this.H.a(this, damagesource, null, this.dl(), this.dn(), this.dr(), event.getRadius(), event.getFire(), World.a.d);
         this.ai();
      }
   }

   @Override
   public boolean a(float f, float f1, DamageSource damagesource) {
      if (f >= 3.0F) {
         float f2 = f / 10.0F;
         this.h((double)(f2 * f2));
      }

      return super.a(f, f1, damagesource);
   }

   @Override
   public void a(int i, int j, int k, boolean flag) {
      if (flag && this.d < 0) {
         this.z();
      }
   }

   @Override
   public void b(byte b0) {
      if (b0 == 10) {
         this.z();
      } else {
         super.b(b0);
      }
   }

   public void z() {
      this.d = 80;
      if (!this.H.B) {
         this.H.a(this, (byte)10);
         if (!this.aO()) {
            this.H.a(null, this.dl(), this.dn(), this.dr(), SoundEffects.xm, SoundCategory.e, 1.0F, 1.0F);
         }
      }
   }

   public int A() {
      return this.d;
   }

   public boolean C() {
      return this.d > -1;
   }

   @Override
   public float a(Explosion explosion, IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata, Fluid fluid, float f) {
      return !this.C() || !iblockdata.a(TagsBlock.M) && !iblockaccess.a_(blockposition.c()).a(TagsBlock.M)
         ? super.a(explosion, iblockaccess, blockposition, iblockdata, fluid, f)
         : 0.0F;
   }

   @Override
   public boolean a(Explosion explosion, IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata, float f) {
      return !this.C() || !iblockdata.a(TagsBlock.M) && !iblockaccess.a_(blockposition.c()).a(TagsBlock.M)
         ? super.a(explosion, iblockaccess, blockposition, iblockdata, f)
         : false;
   }

   @Override
   protected void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      if (nbttagcompound.b("TNTFuse", 99)) {
         this.d = nbttagcompound.h("TNTFuse");
      }
   }

   @Override
   protected void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      nbttagcompound.a("TNTFuse", this.d);
   }
}
