package net.minecraft.world.entity.decoration;

import com.mojang.logging.LogUtils;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.MathHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLightning;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMoveType;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.BlockDiodeAbstract;
import net.minecraft.world.level.block.EnumBlockMirror;
import net.minecraft.world.level.block.EnumBlockRotation;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.Vec3D;
import org.apache.commons.lang3.Validate;
import org.bukkit.entity.Hanging;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingBreakEvent.RemoveCause;
import org.slf4j.Logger;

public abstract class EntityHanging extends Entity {
   private static final Logger e = LogUtils.getLogger();
   protected static final Predicate<Entity> b = entity -> entity instanceof EntityHanging;
   private int f;
   public BlockPosition c;
   protected EnumDirection d;

   protected EntityHanging(EntityTypes<? extends EntityHanging> entitytypes, World world) {
      super(entitytypes, world);
      this.d = EnumDirection.d;
   }

   protected EntityHanging(EntityTypes<? extends EntityHanging> entitytypes, World world, BlockPosition blockposition) {
      this(entitytypes, world);
      this.c = blockposition;
   }

   @Override
   protected void a_() {
   }

   public void a(EnumDirection enumdirection) {
      Validate.notNull(enumdirection);
      Validate.isTrue(enumdirection.o().d());
      this.d = enumdirection;
      this.f((float)(this.d.e() * 90));
      this.L = this.dw();
      this.r();
   }

   protected void r() {
      if (this.d != null) {
         this.a(calculateBoundingBox(this, this.c, this.d, this.t(), this.v()));
      }
   }

   public static AxisAlignedBB calculateBoundingBox(@Nullable Entity entity, BlockPosition blockPosition, EnumDirection direction, int width, int height) {
      double d0 = (double)blockPosition.u() + 0.5;
      double d1 = (double)blockPosition.v() + 0.5;
      double d2 = (double)blockPosition.w() + 0.5;
      double d3 = 0.46875;
      double d4 = b(width);
      double d5 = b(height);
      d0 -= (double)direction.j() * 0.46875;
      d2 -= (double)direction.l() * 0.46875;
      d1 += d5;
      EnumDirection enumdirection = direction.i();
      d0 += d4 * (double)enumdirection.j();
      d2 += d4 * (double)enumdirection.l();
      if (entity != null) {
         entity.p(d0, d1, d2);
      }

      double d6 = (double)width;
      double d7 = (double)height;
      double d8 = (double)width;
      if (direction.o() == EnumDirection.EnumAxis.c) {
         d8 = 1.0;
      } else {
         d6 = 1.0;
      }

      d6 /= 32.0;
      d7 /= 32.0;
      d8 /= 32.0;
      return new AxisAlignedBB(d0 - d6, d1 - d7, d2 - d8, d0 + d6, d1 + d7, d2 + d8);
   }

   private static double b(int i) {
      return i % 32 == 0 ? 0.5 : 0.0;
   }

   @Override
   public void l() {
      if (!this.H.B) {
         this.ap();
         if (this.f++ == this.H.spigotConfig.hangingTickFrequency) {
            this.f = 0;
            if (!this.dB() && !this.s()) {
               Material material = this.H.a_(this.dg()).d();
               RemoveCause cause;
               if (!material.equals(Material.a)) {
                  cause = RemoveCause.OBSTRUCTION;
               } else {
                  cause = RemoveCause.PHYSICS;
               }

               HangingBreakEvent event = new HangingBreakEvent((Hanging)this.getBukkitEntity(), cause);
               this.H.getCraftServer().getPluginManager().callEvent(event);
               if (this.dB() || event.isCancelled()) {
                  return;
               }

               this.ai();
               this.a(null);
            }
         }
      }
   }

   public boolean s() {
      if (!this.H.g(this)) {
         return false;
      } else {
         int i = Math.max(1, this.t() / 16);
         int j = Math.max(1, this.v() / 16);
         BlockPosition blockposition = this.c.a(this.d.g());
         EnumDirection enumdirection = this.d.i();
         BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();

         for(int k = 0; k < i; ++k) {
            for(int l = 0; l < j; ++l) {
               int i1 = (i - 1) / -2;
               int j1 = (j - 1) / -2;
               blockposition_mutableblockposition.g(blockposition).c(enumdirection, k + i1).c(EnumDirection.b, l + j1);
               IBlockData iblockdata = this.H.a_(blockposition_mutableblockposition);
               if (!iblockdata.d().b() && !BlockDiodeAbstract.n(iblockdata)) {
                  return false;
               }
            }
         }

         return this.H.a(this, this.cD(), b).isEmpty();
      }
   }

   @Override
   public boolean bm() {
      return true;
   }

   @Override
   public boolean r(Entity entity) {
      if (entity instanceof EntityHuman entityhuman) {
         return !this.H.a(entityhuman, this.c) ? true : this.a(this.dG().a(entityhuman), 0.0F);
      } else {
         return false;
      }
   }

   @Override
   public EnumDirection cA() {
      return this.d;
   }

   @Override
   public boolean a(DamageSource damagesource, float f) {
      if (this.b(damagesource)) {
         return false;
      } else {
         if (!this.dB() && !this.H.B) {
            Entity damager = damagesource.b() ? damagesource.d() : damagesource.c();
            HangingBreakEvent event;
            if (damager != null) {
               event = new HangingBreakByEntityEvent(
                  (Hanging)this.getBukkitEntity(), damager.getBukkitEntity(), damagesource.a(DamageTypeTags.l) ? RemoveCause.EXPLOSION : RemoveCause.ENTITY
               );
            } else {
               event = new HangingBreakEvent((Hanging)this.getBukkitEntity(), damagesource.a(DamageTypeTags.l) ? RemoveCause.EXPLOSION : RemoveCause.DEFAULT);
            }

            this.H.getCraftServer().getPluginManager().callEvent(event);
            if (this.dB() || event.isCancelled()) {
               return true;
            }

            this.ah();
            this.bj();
            this.a(damagesource.d());
         }

         return true;
      }
   }

   @Override
   public void a(EnumMoveType enummovetype, Vec3D vec3d) {
      if (!this.H.B && !this.dB() && vec3d.g() > 0.0) {
         if (this.dB()) {
            return;
         }

         HangingBreakEvent event = new HangingBreakEvent((Hanging)this.getBukkitEntity(), RemoveCause.PHYSICS);
         this.H.getCraftServer().getPluginManager().callEvent(event);
         if (this.dB() || event.isCancelled()) {
            return;
         }

         this.ah();
         this.a(null);
      }
   }

   @Override
   public void j(double d0, double d1, double d2) {
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      BlockPosition blockposition = this.x();
      nbttagcompound.a("TileX", blockposition.u());
      nbttagcompound.a("TileY", blockposition.v());
      nbttagcompound.a("TileZ", blockposition.w());
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      BlockPosition blockposition = new BlockPosition(nbttagcompound.h("TileX"), nbttagcompound.h("TileY"), nbttagcompound.h("TileZ"));
      if (!blockposition.a(this.dg(), 16.0)) {
         e.error("Hanging entity at invalid position: {}", blockposition);
      } else {
         this.c = blockposition;
      }
   }

   public abstract int t();

   public abstract int v();

   public abstract void a(@Nullable Entity var1);

   public abstract void w();

   @Override
   public EntityItem a(ItemStack itemstack, float f) {
      EntityItem entityitem = new EntityItem(
         this.H, this.dl() + (double)((float)this.d.j() * 0.15F), this.dn() + (double)f, this.dr() + (double)((float)this.d.l() * 0.15F), itemstack
      );
      entityitem.k();
      this.H.b(entityitem);
      return entityitem;
   }

   @Override
   protected boolean bo() {
      return false;
   }

   @Override
   public void e(double d0, double d1, double d2) {
      this.c = BlockPosition.a(d0, d1, d2);
      this.r();
      this.at = true;
   }

   public BlockPosition x() {
      return this.c;
   }

   @Override
   public float a(EnumBlockRotation enumblockrotation) {
      if (this.d.o() != EnumDirection.EnumAxis.b) {
         switch(enumblockrotation) {
            case b:
               this.d = this.d.h();
               break;
            case c:
               this.d = this.d.g();
               break;
            case d:
               this.d = this.d.i();
         }
      }

      float f = MathHelper.g(this.dw());
      switch(enumblockrotation) {
         case b:
            return f + 270.0F;
         case c:
            return f + 180.0F;
         case d:
            return f + 90.0F;
         default:
            return f;
      }
   }

   @Override
   public float a(EnumBlockMirror enumblockmirror) {
      return this.a(enumblockmirror.a(this.d));
   }

   @Override
   public void a(WorldServer worldserver, EntityLightning entitylightning) {
   }

   @Override
   public void c_() {
   }
}
