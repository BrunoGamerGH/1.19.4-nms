package net.minecraft.world.entity.projectile;

import com.google.common.base.MoreObjects;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketListenerPlayOut;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntity;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.MovingObjectPosition;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.MovingObjectPositionEntity;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.projectiles.ProjectileSource;

public abstract class IProjectile extends Entity implements TraceableEntity {
   @Nullable
   private UUID b;
   @Nullable
   private Entity c;
   private boolean d;
   private boolean e;
   private boolean hitCancelled = false;

   IProjectile(EntityTypes<? extends IProjectile> entitytypes, World world) {
      super(entitytypes, world);
   }

   public void b(@Nullable Entity entity) {
      if (entity != null) {
         this.b = entity.cs();
         this.c = entity;
      }

      this.projectileSource = entity != null && entity.getBukkitEntity() instanceof ProjectileSource ? (ProjectileSource)entity.getBukkitEntity() : null;
   }

   @Nullable
   @Override
   public Entity v() {
      if (this.c != null && !this.c.dB()) {
         return this.c;
      } else if (this.b != null && this.H instanceof WorldServer) {
         this.c = ((WorldServer)this.H).a(this.b);
         return this.c;
      } else {
         return null;
      }
   }

   public Entity z() {
      return (Entity)MoreObjects.firstNonNull(this.v(), this);
   }

   @Override
   protected void b(NBTTagCompound nbttagcompound) {
      if (this.b != null) {
         nbttagcompound.a("Owner", this.b);
      }

      if (this.d) {
         nbttagcompound.a("LeftOwner", true);
      }

      nbttagcompound.a("HasBeenShot", this.e);
   }

   protected boolean d(Entity entity) {
      return entity.cs().equals(this.b);
   }

   @Override
   protected void a(NBTTagCompound nbttagcompound) {
      if (nbttagcompound.b("Owner")) {
         this.b = nbttagcompound.a("Owner");
      }

      this.d = nbttagcompound.q("LeftOwner");
      this.e = nbttagcompound.q("HasBeenShot");
   }

   @Override
   public void l() {
      if (!this.e) {
         this.a(GameEvent.O, this.v());
         this.e = true;
      }

      if (!this.d) {
         this.d = this.j();
      }

      super.l();
   }

   private boolean j() {
      Entity entity = this.v();
      if (entity != null) {
         for(Entity entity1 : this.H.a(this, this.cD().b(this.dj()).g(1.0), entity1x -> !entity1x.F_() && entity1x.bm())) {
            if (entity1.cS() == entity.cS()) {
               return false;
            }
         }
      }

      return true;
   }

   public void c(double d0, double d1, double d2, float f, float f1) {
      Vec3D vec3d = new Vec3D(d0, d1, d2)
         .d()
         .b(this.af.a(0.0, 0.0172275 * (double)f1), this.af.a(0.0, 0.0172275 * (double)f1), this.af.a(0.0, 0.0172275 * (double)f1))
         .a((double)f);
      this.f(vec3d);
      double d3 = vec3d.h();
      this.f((float)(MathHelper.d(vec3d.c, vec3d.e) * 180.0F / (float)Math.PI));
      this.e((float)(MathHelper.d(vec3d.d, d3) * 180.0F / (float)Math.PI));
      this.L = this.dw();
      this.M = this.dy();
   }

   public void a(Entity entity, float f, float f1, float f2, float f3, float f4) {
      float f5 = -MathHelper.a(f1 * (float) (Math.PI / 180.0)) * MathHelper.b(f * (float) (Math.PI / 180.0));
      float f6 = -MathHelper.a((f + f2) * (float) (Math.PI / 180.0));
      float f7 = MathHelper.b(f1 * (float) (Math.PI / 180.0)) * MathHelper.b(f * (float) (Math.PI / 180.0));
      this.c((double)f5, (double)f6, (double)f7, f3, f4);
      Vec3D vec3d = entity.dj();
      this.f(this.dj().b(vec3d.c, entity.ax() ? 0.0 : vec3d.d, vec3d.e));
   }

   protected void preOnHit(MovingObjectPosition movingobjectposition) {
      ProjectileHitEvent event = CraftEventFactory.callProjectileHitEvent(this, movingobjectposition);
      this.hitCancelled = event != null && event.isCancelled();
      if (movingobjectposition.c() == MovingObjectPosition.EnumMovingObjectType.b || !this.hitCancelled) {
         this.a(movingobjectposition);
      }
   }

   protected void a(MovingObjectPosition movingobjectposition) {
      MovingObjectPosition.EnumMovingObjectType movingobjectposition_enummovingobjecttype = movingobjectposition.c();
      if (movingobjectposition_enummovingobjecttype == MovingObjectPosition.EnumMovingObjectType.c) {
         this.a((MovingObjectPositionEntity)movingobjectposition);
         this.H.a(GameEvent.N, movingobjectposition.e(), GameEvent.a.a(this, null));
      } else if (movingobjectposition_enummovingobjecttype == MovingObjectPosition.EnumMovingObjectType.b) {
         MovingObjectPositionBlock movingobjectpositionblock = (MovingObjectPositionBlock)movingobjectposition;
         this.a(movingobjectpositionblock);
         BlockPosition blockposition = movingobjectpositionblock.a();
         this.H.a(GameEvent.N, blockposition, GameEvent.a.a(this, this.H.a_(blockposition)));
      }
   }

   protected void a(MovingObjectPositionEntity movingobjectpositionentity) {
   }

   protected void a(MovingObjectPositionBlock movingobjectpositionblock) {
      if (!this.hitCancelled) {
         IBlockData iblockdata = this.H.a_(movingobjectpositionblock.a());
         iblockdata.a(this.H, iblockdata, movingobjectpositionblock, this);
      }
   }

   @Override
   public void l(double d0, double d1, double d2) {
      this.o(d0, d1, d2);
      if (this.M == 0.0F && this.L == 0.0F) {
         double d3 = Math.sqrt(d0 * d0 + d2 * d2);
         this.e((float)(MathHelper.d(d1, d3) * 180.0F / (float)Math.PI));
         this.f((float)(MathHelper.d(d0, d2) * 180.0F / (float)Math.PI));
         this.M = this.dy();
         this.L = this.dw();
         this.b(this.dl(), this.dn(), this.dr(), this.dw(), this.dy());
      }
   }

   protected boolean a(Entity entity) {
      if (!entity.bl()) {
         return false;
      } else {
         Entity entity1 = this.v();
         return entity1 == null || this.d || !entity1.v(entity);
      }
   }

   protected void A() {
      Vec3D vec3d = this.dj();
      double d0 = vec3d.h();
      this.e(d(this.M, (float)(MathHelper.d(vec3d.d, d0) * 180.0F / (float)Math.PI)));
      this.f(d(this.L, (float)(MathHelper.d(vec3d.c, vec3d.e) * 180.0F / (float)Math.PI)));
   }

   protected static float d(float f, float f1) {
      while(f1 - f < -180.0F) {
         f -= 360.0F;
      }

      while(f1 - f >= 180.0F) {
         f += 360.0F;
      }

      return MathHelper.i(0.2F, f, f1);
   }

   @Override
   public Packet<PacketListenerPlayOut> S() {
      Entity entity = this.v();
      return new PacketPlayOutSpawnEntity(this, entity == null ? 0 : entity.af());
   }

   @Override
   public void a(PacketPlayOutSpawnEntity packetplayoutspawnentity) {
      super.a(packetplayoutspawnentity);
      Entity entity = this.H.a(packetplayoutspawnentity.n());
      if (entity != null) {
         this.b(entity);
      }
   }

   @Override
   public boolean a(World world, BlockPosition blockposition) {
      Entity entity = this.v();
      return entity instanceof EntityHuman ? entity.a(world, blockposition) : entity == null || world.W().b(GameRules.c);
   }
}
