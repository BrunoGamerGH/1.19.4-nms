package net.minecraft.world.entity;

import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.core.particles.ParticleParam;
import net.minecraft.core.particles.Particles;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.players.NameReferencingFileConverter;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.animal.EntityAnimal;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.World;
import net.minecraft.world.scores.ScoreboardTeamBase;

public abstract class EntityTameableAnimal extends EntityAnimal implements OwnableEntity {
   protected static final DataWatcherObject<Byte> bS = DataWatcher.a(EntityTameableAnimal.class, DataWatcherRegistry.a);
   protected static final DataWatcherObject<Optional<UUID>> bT = DataWatcher.a(EntityTameableAnimal.class, DataWatcherRegistry.q);
   private boolean bV;

   protected EntityTameableAnimal(EntityTypes<? extends EntityTameableAnimal> var0, World var1) {
      super(var0, var1);
      this.r();
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(bS, (byte)0);
      this.am.a(bT, Optional.empty());
   }

   @Override
   public void b(NBTTagCompound var0) {
      super.b(var0);
      if (this.T_() != null) {
         var0.a("Owner", this.T_());
      }

      var0.a("Sitting", this.bV);
   }

   @Override
   public void a(NBTTagCompound var0) {
      super.a(var0);
      UUID var1;
      if (var0.b("Owner")) {
         var1 = var0.a("Owner");
      } else {
         String var2 = var0.l("Owner");
         var1 = NameReferencingFileConverter.a(this.cH(), var2);
      }

      if (var1 != null) {
         try {
            this.b(var1);
            this.x(true);
         } catch (Throwable var4) {
            this.x(false);
         }
      }

      this.bV = var0.q("Sitting");
      this.y(this.bV);
   }

   @Override
   public boolean a(EntityHuman var0) {
      return !this.fI();
   }

   protected void w(boolean var0) {
      ParticleParam var1 = Particles.O;
      if (!var0) {
         var1 = Particles.ab;
      }

      for(int var2 = 0; var2 < 7; ++var2) {
         double var3 = this.af.k() * 0.02;
         double var5 = this.af.k() * 0.02;
         double var7 = this.af.k() * 0.02;
         this.H.a(var1, this.d(1.0), this.do() + 0.5, this.g(1.0), var3, var5, var7);
      }
   }

   @Override
   public void b(byte var0) {
      if (var0 == 7) {
         this.w(true);
      } else if (var0 == 6) {
         this.w(false);
      } else {
         super.b(var0);
      }
   }

   public boolean q() {
      return (this.am.a(bS) & 4) != 0;
   }

   public void x(boolean var0) {
      byte var1 = this.am.a(bS);
      if (var0) {
         this.am.b(bS, (byte)(var1 | 4));
      } else {
         this.am.b(bS, (byte)(var1 & -5));
      }

      this.r();
   }

   protected void r() {
   }

   public boolean w() {
      return (this.am.a(bS) & 1) != 0;
   }

   public void y(boolean var0) {
      byte var1 = this.am.a(bS);
      if (var0) {
         this.am.b(bS, (byte)(var1 | 1));
      } else {
         this.am.b(bS, (byte)(var1 & -2));
      }
   }

   @Nullable
   @Override
   public UUID T_() {
      return this.am.a(bT).orElse(null);
   }

   public void b(@Nullable UUID var0) {
      this.am.b(bT, Optional.ofNullable(var0));
   }

   public void e(EntityHuman var0) {
      this.x(true);
      this.b(var0.cs());
      if (var0 instanceof EntityPlayer) {
         CriterionTriggers.x.a((EntityPlayer)var0, this);
      }
   }

   @Override
   public boolean c(EntityLiving var0) {
      return this.m(var0) ? false : super.c(var0);
   }

   public boolean m(EntityLiving var0) {
      return var0 == this.H_();
   }

   public boolean a(EntityLiving var0, EntityLiving var1) {
      return true;
   }

   @Override
   public ScoreboardTeamBase cb() {
      if (this.q()) {
         EntityLiving var0 = this.H_();
         if (var0 != null) {
            return var0.cb();
         }
      }

      return super.cb();
   }

   @Override
   public boolean p(Entity var0) {
      if (this.q()) {
         EntityLiving var1 = this.H_();
         if (var0 == var1) {
            return true;
         }

         if (var1 != null) {
            return var1.p(var0);
         }
      }

      return super.p(var0);
   }

   @Override
   public void a(DamageSource var0) {
      if (!this.H.B && this.H.W().b(GameRules.m) && this.H_() instanceof EntityPlayer) {
         this.H_().a(this.eC().b());
      }

      super.a(var0);
   }

   public boolean fS() {
      return this.bV;
   }

   public void z(boolean var0) {
      this.bV = var0;
   }
}
