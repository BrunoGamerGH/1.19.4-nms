package net.minecraft.world.entity.projectile;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.particles.Particles;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.level.World;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class EntityEvokerFangs extends Entity implements TraceableEntity {
   public static final int b = 20;
   public static final int c = 2;
   public static final int d = 14;
   private int e;
   private boolean f;
   private int g = 22;
   private boolean h;
   @Nullable
   private EntityLiving i;
   @Nullable
   private UUID j;

   public EntityEvokerFangs(EntityTypes<? extends EntityEvokerFangs> entitytypes, World world) {
      super(entitytypes, world);
   }

   public EntityEvokerFangs(World world, double d0, double d1, double d2, float f, int i, EntityLiving entityliving) {
      this(EntityTypes.H, world);
      this.e = i;
      this.a(entityliving);
      this.f(f * (180.0F / (float)Math.PI));
      this.e(d0, d1, d2);
   }

   @Override
   protected void a_() {
   }

   public void a(@Nullable EntityLiving entityliving) {
      this.i = entityliving;
      this.j = entityliving == null ? null : entityliving.cs();
   }

   @Nullable
   public EntityLiving i() {
      if (this.i == null && this.j != null && this.H instanceof WorldServer) {
         Entity entity = ((WorldServer)this.H).a(this.j);
         if (entity instanceof EntityLiving) {
            this.i = (EntityLiving)entity;
         }
      }

      return this.i;
   }

   @Override
   protected void a(NBTTagCompound nbttagcompound) {
      this.e = nbttagcompound.h("Warmup");
      if (nbttagcompound.b("Owner")) {
         this.j = nbttagcompound.a("Owner");
      }
   }

   @Override
   protected void b(NBTTagCompound nbttagcompound) {
      nbttagcompound.a("Warmup", this.e);
      if (this.j != null) {
         nbttagcompound.a("Owner", this.j);
      }
   }

   @Override
   public void l() {
      super.l();
      if (this.H.B) {
         if (this.h) {
            --this.g;
            if (this.g == 14) {
               for(int i = 0; i < 12; ++i) {
                  double d0 = this.dl() + (this.af.j() * 2.0 - 1.0) * (double)this.dc() * 0.5;
                  double d1 = this.dn() + 0.05 + this.af.j();
                  double d2 = this.dr() + (this.af.j() * 2.0 - 1.0) * (double)this.dc() * 0.5;
                  double d3 = (this.af.j() * 2.0 - 1.0) * 0.3;
                  double d4 = 0.3 + this.af.j() * 0.3;
                  double d5 = (this.af.j() * 2.0 - 1.0) * 0.3;
                  this.H.a(Particles.g, d0, d1 + 1.0, d2, d3, d4, d5);
               }
            }
         }
      } else if (--this.e < 0) {
         if (this.e == -8) {
            for(EntityLiving entityliving : this.H.a(EntityLiving.class, this.cD().c(0.2, 0.0, 0.2))) {
               this.c(entityliving);
            }
         }

         if (!this.f) {
            this.H.a(this, (byte)4);
            this.f = true;
         }

         if (--this.g < 0) {
            this.ai();
         }
      }
   }

   private void c(EntityLiving entityliving) {
      EntityLiving entityliving1 = this.i();
      if (entityliving.bq() && !entityliving.cm() && entityliving != entityliving1) {
         if (entityliving1 == null) {
            CraftEventFactory.entityDamage = this;
            entityliving.a(this.dG().o(), 6.0F);
            CraftEventFactory.entityDamage = null;
         } else {
            if (entityliving1.p(entityliving)) {
               return;
            }

            entityliving.a(this.dG().c(this, entityliving1), 6.0F);
         }
      }
   }

   @Override
   public void b(byte b0) {
      super.b(b0);
      if (b0 == 4) {
         this.h = true;
         if (!this.aO()) {
            this.H.a(this.dl(), this.dn(), this.dr(), SoundEffects.hq, this.cX(), 1.0F, this.af.i() * 0.2F + 0.85F, false);
         }
      }
   }

   public float a(float f) {
      if (!this.h) {
         return 0.0F;
      } else {
         int i = this.g - 2;
         return i <= 0 ? 1.0F : 1.0F - ((float)i - f) / 20.0F;
      }
   }
}
