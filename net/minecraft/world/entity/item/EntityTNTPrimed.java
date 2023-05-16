package net.minecraft.world.entity.item;

import javax.annotation.Nullable;
import net.minecraft.core.particles.Particles;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntitySize;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMoveType;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.level.World;
import org.bukkit.entity.Explosive;
import org.bukkit.event.entity.ExplosionPrimeEvent;

public class EntityTNTPrimed extends Entity implements TraceableEntity {
   private static final DataWatcherObject<Integer> b = DataWatcher.a(EntityTNTPrimed.class, DataWatcherRegistry.b);
   private static final int c = 80;
   @Nullable
   public EntityLiving d;
   public float yield = 4.0F;
   public boolean isIncendiary = false;

   public EntityTNTPrimed(EntityTypes<? extends EntityTNTPrimed> entitytypes, World world) {
      super(entitytypes, world);
      this.F = true;
   }

   public EntityTNTPrimed(World world, double d0, double d1, double d2, @Nullable EntityLiving entityliving) {
      this(EntityTypes.aY, world);
      this.e(d0, d1, d2);
      double d3 = world.z.j() * (float) (Math.PI * 2);
      this.o(-Math.sin(d3) * 0.02, 0.2F, -Math.cos(d3) * 0.02);
      this.b(80);
      this.I = d0;
      this.J = d1;
      this.K = d2;
      this.d = entityliving;
   }

   @Override
   protected void a_() {
      this.am.a(b, 80);
   }

   @Override
   protected Entity.MovementEmission aQ() {
      return Entity.MovementEmission.a;
   }

   @Override
   public boolean bm() {
      return !this.dB();
   }

   @Override
   public void l() {
      if (this.H.spigotConfig.maxTntTicksPerTick <= 0 || ++this.H.spigotConfig.currentPrimedTnt <= this.H.spigotConfig.maxTntTicksPerTick) {
         if (!this.aP()) {
            this.f(this.dj().b(0.0, -0.04, 0.0));
         }

         this.a(EnumMoveType.a, this.dj());
         this.f(this.dj().a(0.98));
         if (this.N) {
            this.f(this.dj().d(0.7, -0.5, 0.7));
         }

         int i = this.j() - 1;
         this.b(i);
         if (i <= 0) {
            if (!this.H.B) {
               this.k();
            }

            this.ai();
         } else {
            this.aZ();
            if (this.H.B) {
               this.H.a(Particles.ab, this.dl(), this.dn() + 0.5, this.dr(), 0.0, 0.0, 0.0);
            }
         }
      }
   }

   private void k() {
      ExplosionPrimeEvent event = new ExplosionPrimeEvent((Explosive)this.getBukkitEntity());
      this.H.getCraftServer().getPluginManager().callEvent(event);
      if (!event.isCancelled()) {
         this.H.a(this, this.dl(), this.e(0.0625), this.dr(), event.getRadius(), event.getFire(), World.a.d);
      }
   }

   @Override
   protected void b(NBTTagCompound nbttagcompound) {
      nbttagcompound.a("Fuse", (short)this.j());
   }

   @Override
   protected void a(NBTTagCompound nbttagcompound) {
      this.b(nbttagcompound.g("Fuse"));
   }

   @Nullable
   public EntityLiving i() {
      return this.d;
   }

   @Override
   protected float a(EntityPose entitypose, EntitySize entitysize) {
      return 0.15F;
   }

   public void b(int i) {
      this.am.b(b, i);
   }

   public int j() {
      return this.am.a(b);
   }
}
