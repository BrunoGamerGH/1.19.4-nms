package net.minecraft.world.entity.projectile;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLightning;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentManager;
import net.minecraft.world.level.World;
import net.minecraft.world.phys.MovingObjectPositionEntity;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.event.weather.LightningStrikeEvent.Cause;

public class EntityThrownTrident extends EntityArrow {
   private static final DataWatcherObject<Byte> g = DataWatcher.a(EntityThrownTrident.class, DataWatcherRegistry.a);
   private static final DataWatcherObject<Boolean> h = DataWatcher.a(EntityThrownTrident.class, DataWatcherRegistry.k);
   public ItemStack i = new ItemStack(Items.uP);
   private boolean j;
   public int f;

   public EntityThrownTrident(EntityTypes<? extends EntityThrownTrident> entitytypes, World world) {
      super(entitytypes, world);
   }

   public EntityThrownTrident(World world, EntityLiving entityliving, ItemStack itemstack) {
      super(EntityTypes.bb, entityliving, world);
      this.i = itemstack.o();
      this.am.b(g, (byte)EnchantmentManager.g(itemstack));
      this.am.b(h, itemstack.A());
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(g, (byte)0);
      this.am.a(h, false);
   }

   @Override
   public void l() {
      if (this.c > 4) {
         this.j = true;
      }

      Entity entity = this.v();
      byte b0 = this.am.a(g);
      if (b0 > 0 && (this.j || this.x()) && entity != null) {
         if (!this.D()) {
            if (!this.H.B && this.d == EntityArrow.PickupStatus.b) {
               this.a(this.o(), 0.1F);
            }

            this.ai();
         } else {
            this.p(true);
            Vec3D vec3d = entity.bk().d(this.de());
            this.p(this.dl(), this.dn() + vec3d.d * 0.015 * (double)b0, this.dr());
            if (this.H.B) {
               this.ac = this.dn();
            }

            double d0 = 0.05 * (double)b0;
            this.f(this.dj().a(0.95).e(vec3d.d().a(d0)));
            if (this.f == 0) {
               this.a(SoundEffects.xq, 10.0F, 1.0F);
            }

            ++this.f;
         }
      }

      super.l();
   }

   private boolean D() {
      Entity entity = this.v();
      return entity != null && entity.bq() ? !(entity instanceof EntityPlayer) || !entity.F_() : false;
   }

   @Override
   protected ItemStack o() {
      return this.i.o();
   }

   @Override
   public boolean y() {
      return this.am.a(h);
   }

   @Nullable
   @Override
   protected MovingObjectPositionEntity a(Vec3D vec3d, Vec3D vec3d1) {
      return this.j ? null : super.a(vec3d, vec3d1);
   }

   @Override
   protected void a(MovingObjectPositionEntity movingobjectpositionentity) {
      Entity entity = movingobjectpositionentity.a();
      float f = 8.0F;
      if (entity instanceof EntityLiving entityliving) {
         f += EnchantmentManager.a(this.i, entityliving.eJ());
      }

      Entity entity1 = this.v();
      DamageSource damagesource = this.dG().a(this, (Entity)(entity1 == null ? this : entity1));
      this.j = true;
      SoundEffect soundeffect = SoundEffects.xo;
      if (entity.a(damagesource, f)) {
         if (entity.ae() == EntityTypes.E) {
            return;
         }

         if (entity instanceof EntityLiving entityliving1) {
            if (entity1 instanceof EntityLiving) {
               EnchantmentManager.a(entityliving1, entity1);
               EnchantmentManager.b((EntityLiving)entity1, entityliving1);
            }

            this.a(entityliving1);
         }
      }

      this.f(this.dj().d(-0.01, -0.1, -0.01));
      float f1 = 1.0F;
      if (this.H instanceof WorldServer && this.H.X() && this.C()) {
         BlockPosition blockposition = entity.dg();
         if (this.H.g(blockposition)) {
            EntityLightning entitylightning = EntityTypes.ai.a(this.H);
            if (entitylightning != null) {
               entitylightning.d(Vec3D.c(blockposition));
               entitylightning.b(entity1 instanceof EntityPlayer ? (EntityPlayer)entity1 : null);
               ((WorldServer)this.H).strikeLightning(entitylightning, Cause.TRIDENT);
               soundeffect = SoundEffects.xv;
               f1 = 5.0F;
            }
         }
      }

      this.a(soundeffect, f1, 1.0F);
   }

   public boolean C() {
      return EnchantmentManager.i(this.i);
   }

   @Override
   protected boolean a(EntityHuman entityhuman) {
      return super.a(entityhuman) || this.x() && this.d((Entity)entityhuman) && entityhuman.fJ().e(this.o());
   }

   @Override
   protected SoundEffect j() {
      return SoundEffects.xp;
   }

   @Override
   public void b_(EntityHuman entityhuman) {
      if (this.d((Entity)entityhuman) || this.v() == null) {
         super.b_(entityhuman);
      }
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      if (nbttagcompound.b("Trident", 10)) {
         this.i = ItemStack.a(nbttagcompound.p("Trident"));
      }

      this.j = nbttagcompound.q("DealtDamage");
      this.am.b(g, (byte)EnchantmentManager.g(this.i));
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      nbttagcompound.a("Trident", this.i.b(new NBTTagCompound()));
      nbttagcompound.a("DealtDamage", this.j);
   }

   @Override
   public void i() {
      byte b0 = this.am.a(g);
      if (this.d != EntityArrow.PickupStatus.b || b0 <= 0) {
         super.i();
      }
   }

   @Override
   protected float w() {
      return 0.99F;
   }

   @Override
   public boolean k(double d0, double d1, double d2) {
      return true;
   }
}
