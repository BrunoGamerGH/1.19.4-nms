package net.minecraft.world.entity.projectile;

import com.mojang.logging.LogUtils;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.particles.Particles;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketListenerPlayOut;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntity;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.stats.StatisticList;
import net.minecraft.tags.TagsFluid;
import net.minecraft.tags.TagsItem;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityExperienceOrb;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMoveType;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameterSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameters;
import net.minecraft.world.phys.MovingObjectPosition;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.MovingObjectPositionEntity;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.slf4j.Logger;

public class EntityFishingHook extends IProjectile {
   private static final Logger b = LogUtils.getLogger();
   private final RandomSource c;
   private boolean d;
   private int e;
   private static final int f = 10;
   public static final DataWatcherObject<Integer> g = DataWatcher.a(EntityFishingHook.class, DataWatcherRegistry.b);
   private static final DataWatcherObject<Boolean> h = DataWatcher.a(EntityFishingHook.class, DataWatcherRegistry.k);
   private int i;
   private int j;
   private int k;
   private int l;
   private float m;
   private boolean n;
   @Nullable
   public Entity o;
   public EntityFishingHook.HookState p;
   private final int q;
   private final int r;
   public int minWaitTime = 100;
   public int maxWaitTime = 600;
   public boolean applyLure = true;

   private EntityFishingHook(EntityTypes<? extends EntityFishingHook> entitytypes, World world, int i, int j) {
      super(entitytypes, world);
      this.c = RandomSource.a();
      this.n = true;
      this.p = EntityFishingHook.HookState.a;
      this.as = true;
      this.q = Math.max(0, i);
      this.r = Math.max(0, j);
   }

   public EntityFishingHook(EntityTypes<? extends EntityFishingHook> entitytypes, World world) {
      this(entitytypes, world, 0, 0);
   }

   public EntityFishingHook(EntityHuman entityhuman, World world, int i, int j) {
      this(EntityTypes.bu, world, i, j);
      this.b(entityhuman);
      float f = entityhuman.dy();
      float f1 = entityhuman.dw();
      float f2 = MathHelper.b(-f1 * (float) (Math.PI / 180.0) - (float) Math.PI);
      float f3 = MathHelper.a(-f1 * (float) (Math.PI / 180.0) - (float) Math.PI);
      float f4 = -MathHelper.b(-f * (float) (Math.PI / 180.0));
      float f5 = MathHelper.a(-f * (float) (Math.PI / 180.0));
      double d0 = entityhuman.dl() - (double)f3 * 0.3;
      double d1 = entityhuman.dp();
      double d2 = entityhuman.dr() - (double)f2 * 0.3;
      this.b(d0, d1, d2, f1, f);
      Vec3D vec3d = new Vec3D((double)(-f3), (double)MathHelper.a(-(f5 / f4), -5.0F, 5.0F), (double)(-f2));
      double d3 = vec3d.f();
      vec3d = vec3d.d(0.6 / d3 + this.af.a(0.5, 0.0103365), 0.6 / d3 + this.af.a(0.5, 0.0103365), 0.6 / d3 + this.af.a(0.5, 0.0103365));
      this.f(vec3d);
      this.f((float)(MathHelper.d(vec3d.c, vec3d.e) * 180.0F / (float)Math.PI));
      this.e((float)(MathHelper.d(vec3d.d, vec3d.h()) * 180.0F / (float)Math.PI));
      this.L = this.dw();
      this.M = this.dy();
   }

   @Override
   protected void a_() {
      this.aj().a(g, 0);
      this.aj().a(h, false);
   }

   @Override
   public void a(DataWatcherObject<?> datawatcherobject) {
      if (g.equals(datawatcherobject)) {
         int i = this.aj().a(g);
         this.o = i > 0 ? this.H.a(i - 1) : null;
      }

      if (h.equals(datawatcherobject)) {
         this.d = this.aj().a(h);
         if (this.d) {
            this.o(this.dj().c, (double)(-0.4F * MathHelper.a(this.c, 0.6F, 1.0F)), this.dj().e);
         }
      }

      super.a(datawatcherobject);
   }

   @Override
   public boolean a(double d0) {
      double d1 = 64.0;
      return d0 < 4096.0;
   }

   @Override
   public void a(double d0, double d1, double d2, float f, float f1, int i, boolean flag) {
   }

   @Override
   public void l() {
      this.c.b(this.cs().getLeastSignificantBits() ^ this.H.U());
      super.l();
      EntityHuman entityhuman = this.j();
      if (entityhuman == null) {
         this.ai();
      } else if (this.H.B || !this.a(entityhuman)) {
         if (this.N) {
            ++this.i;
            if (this.i >= 1200) {
               this.ai();
               return;
            }
         } else {
            this.i = 0;
         }

         float f = 0.0F;
         BlockPosition blockposition = this.dg();
         Fluid fluid = this.H.b_(blockposition);
         if (fluid.a(TagsFluid.a)) {
            f = fluid.a((IBlockAccess)this.H, blockposition);
         }

         boolean flag = f > 0.0F;
         if (this.p == EntityFishingHook.HookState.a) {
            if (this.o != null) {
               this.f(Vec3D.b);
               this.p = EntityFishingHook.HookState.b;
               return;
            }

            if (flag) {
               this.f(this.dj().d(0.3, 0.2, 0.3));
               this.p = EntityFishingHook.HookState.c;
               return;
            }

            this.o();
         } else {
            if (this.p == EntityFishingHook.HookState.b) {
               if (this.o != null) {
                  if (!this.o.dB() && this.o.H.ab() == this.H.ab()) {
                     this.e(this.o.dl(), this.o.e(0.8), this.o.dr());
                  } else {
                     this.x(null);
                     this.p = EntityFishingHook.HookState.a;
                  }
               }

               return;
            }

            if (this.p == EntityFishingHook.HookState.c) {
               Vec3D vec3d = this.dj();
               double d0 = this.dn() + vec3d.d - (double)blockposition.v() - (double)f;
               if (Math.abs(d0) < 0.01) {
                  d0 += Math.signum(d0) * 0.1;
               }

               this.o(vec3d.c * 0.9, vec3d.d - d0 * (double)this.af.i() * 0.2, vec3d.e * 0.9);
               if (this.j <= 0 && this.l <= 0) {
                  this.n = true;
               } else {
                  this.n = this.n && this.e < 10 && this.b(blockposition);
               }

               if (flag) {
                  this.e = Math.max(0, this.e - 1);
                  if (this.d) {
                     this.f(this.dj().b(0.0, -0.1 * (double)this.c.i() * (double)this.c.i(), 0.0));
                  }

                  if (!this.H.B) {
                     this.a(blockposition);
                  }
               } else {
                  this.e = Math.min(10, this.e + 1);
               }
            }
         }

         if (!fluid.a(TagsFluid.a)) {
            this.f(this.dj().b(0.0, -0.03, 0.0));
         }

         this.a(EnumMoveType.a, this.dj());
         this.A();
         if (this.p == EntityFishingHook.HookState.a && (this.N || this.O)) {
            this.f(Vec3D.b);
         }

         double d1 = 0.92;
         this.f(this.dj().a(0.92));
         this.an();
      }
   }

   private boolean a(EntityHuman entityhuman) {
      ItemStack itemstack = entityhuman.eK();
      ItemStack itemstack1 = entityhuman.eL();
      boolean flag = itemstack.a(Items.qd);
      boolean flag1 = itemstack1.a(Items.qd);
      if (!entityhuman.dB() && entityhuman.bq() && (flag || flag1) && this.f(entityhuman) <= 1024.0) {
         return false;
      } else {
         this.ai();
         return true;
      }
   }

   private void o() {
      MovingObjectPosition movingobjectposition = ProjectileHelper.a(this, this::a);
      this.preOnHit(movingobjectposition);
   }

   @Override
   protected boolean a(Entity entity) {
      return super.a(entity) || entity.bq() && entity instanceof EntityItem;
   }

   @Override
   protected void a(MovingObjectPositionEntity movingobjectpositionentity) {
      super.a(movingobjectpositionentity);
      if (!this.H.B) {
         this.x(movingobjectpositionentity.a());
      }
   }

   @Override
   protected void a(MovingObjectPositionBlock movingobjectpositionblock) {
      super.a(movingobjectpositionblock);
      this.f(this.dj().d().a(movingobjectpositionblock.a(this)));
   }

   public void x(@Nullable Entity entity) {
      this.o = entity;
      this.aj().b(g, entity == null ? 0 : entity.af() + 1);
   }

   private void a(BlockPosition blockposition) {
      WorldServer worldserver = (WorldServer)this.H;
      int i = 1;
      BlockPosition blockposition1 = blockposition.c();
      if (this.af.i() < 0.25F && this.H.t(blockposition1)) {
         ++i;
      }

      if (this.af.i() < 0.5F && !this.H.g(blockposition1)) {
         --i;
      }

      if (this.j > 0) {
         --this.j;
         if (this.j <= 0) {
            this.k = 0;
            this.l = 0;
            this.aj().b(h, false);
            PlayerFishEvent playerFishEvent = new PlayerFishEvent(
               (Player)this.j().getBukkitEntity(), null, (FishHook)this.getBukkitEntity(), State.FAILED_ATTEMPT
            );
            this.H.getCraftServer().getPluginManager().callEvent(playerFishEvent);
         }
      } else if (this.l > 0) {
         this.l -= i;
         if (this.l > 0) {
            this.m += (float)this.af.a(0.0, 9.188);
            float f = this.m * (float) (Math.PI / 180.0);
            float f1 = MathHelper.a(f);
            float f2 = MathHelper.b(f);
            double d0 = this.dl() + (double)(f1 * (float)this.l * 0.1F);
            double d1 = (double)((float)MathHelper.a(this.dn()) + 1.0F);
            double d2 = this.dr() + (double)(f2 * (float)this.l * 0.1F);
            IBlockData iblockdata = worldserver.a_(BlockPosition.a(d0, d1 - 1.0, d2));
            if (iblockdata.a(Blocks.G)) {
               if (this.af.i() < 0.15F) {
                  worldserver.a(Particles.e, d0, d1 - 0.1F, d2, 1, (double)f1, 0.1, (double)f2, 0.0);
               }

               float f3 = f1 * 0.04F;
               float f4 = f2 * 0.04F;
               worldserver.a(Particles.B, d0, d1, d2, 0, (double)f4, 0.01, (double)(-f3), 1.0);
               worldserver.a(Particles.B, d0, d1, d2, 0, (double)(-f4), 0.01, (double)f3, 1.0);
            }
         } else {
            PlayerFishEvent playerFishEvent = new PlayerFishEvent((Player)this.j().getBukkitEntity(), null, (FishHook)this.getBukkitEntity(), State.BITE);
            this.H.getCraftServer().getPluginManager().callEvent(playerFishEvent);
            if (playerFishEvent.isCancelled()) {
               return;
            }

            this.a(SoundEffects.hM, 0.25F, 1.0F + (this.af.i() - this.af.i()) * 0.4F);
            double d3 = this.dn() + 0.5;
            worldserver.a(Particles.e, this.dl(), d3, this.dr(), (int)(1.0F + this.dc() * 20.0F), (double)this.dc(), 0.0, (double)this.dc(), 0.2F);
            worldserver.a(Particles.B, this.dl(), d3, this.dr(), (int)(1.0F + this.dc() * 20.0F), (double)this.dc(), 0.0, (double)this.dc(), 0.2F);
            this.j = MathHelper.a(this.af, 20, 40);
            this.aj().b(h, true);
         }
      } else if (this.k > 0) {
         this.k -= i;
         float f = 0.15F;
         if (this.k < 20) {
            f += (float)(20 - this.k) * 0.05F;
         } else if (this.k < 40) {
            f += (float)(40 - this.k) * 0.02F;
         } else if (this.k < 60) {
            f += (float)(60 - this.k) * 0.01F;
         }

         if (this.af.i() < f) {
            float f1 = MathHelper.a(this.af, 0.0F, 360.0F) * (float) (Math.PI / 180.0);
            float f2 = MathHelper.a(this.af, 25.0F, 60.0F);
            double d0 = this.dl() + (double)(MathHelper.a(f1) * f2) * 0.1;
            double d1 = (double)((float)MathHelper.a(this.dn()) + 1.0F);
            double d2 = this.dr() + (double)(MathHelper.b(f1) * f2) * 0.1;
            IBlockData iblockdata = worldserver.a_(BlockPosition.a(d0, d1 - 1.0, d2));
            if (iblockdata.a(Blocks.G)) {
               worldserver.a(Particles.ai, d0, d1, d2, 2 + this.af.a(2), 0.1F, 0.0, 0.1F, 0.0);
            }
         }

         if (this.k <= 0) {
            this.m = MathHelper.a(this.af, 0.0F, 360.0F);
            this.l = MathHelper.a(this.af, 20, 80);
         }
      } else {
         this.k = MathHelper.a(this.af, this.minWaitTime, this.maxWaitTime);
         this.k -= this.applyLure ? this.r * 20 * 5 : 0;
      }
   }

   private boolean b(BlockPosition blockposition) {
      EntityFishingHook.WaterPosition entityfishinghook_waterposition = EntityFishingHook.WaterPosition.c;

      for(int i = -1; i <= 2; ++i) {
         EntityFishingHook.WaterPosition entityfishinghook_waterposition1 = this.a(blockposition.b(-2, i, -2), blockposition.b(2, i, 2));
         switch(entityfishinghook_waterposition1) {
            case a:
               if (entityfishinghook_waterposition == EntityFishingHook.WaterPosition.c) {
                  return false;
               }
               break;
            case b:
               if (entityfishinghook_waterposition == EntityFishingHook.WaterPosition.a) {
                  return false;
               }
               break;
            case c:
               return false;
         }

         entityfishinghook_waterposition = entityfishinghook_waterposition1;
      }

      return true;
   }

   private EntityFishingHook.WaterPosition a(BlockPosition blockposition, BlockPosition blockposition1) {
      return BlockPosition.b(blockposition, blockposition1)
         .map(this::c)
         .reduce(
            (entityfishinghook_waterposition, entityfishinghook_waterposition1) -> entityfishinghook_waterposition == entityfishinghook_waterposition1
                  ? entityfishinghook_waterposition
                  : EntityFishingHook.WaterPosition.c
         )
         .orElse(EntityFishingHook.WaterPosition.c);
   }

   private EntityFishingHook.WaterPosition c(BlockPosition blockposition) {
      IBlockData iblockdata = this.H.a_(blockposition);
      if (!iblockdata.h() && !iblockdata.a(Blocks.fl)) {
         Fluid fluid = iblockdata.r();
         return fluid.a(TagsFluid.a) && fluid.b() && iblockdata.k(this.H, blockposition).b()
            ? EntityFishingHook.WaterPosition.b
            : EntityFishingHook.WaterPosition.c;
      } else {
         return EntityFishingHook.WaterPosition.a;
      }
   }

   public boolean i() {
      return this.n;
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
   }

   public int a(ItemStack itemstack) {
      EntityHuman entityhuman = this.j();
      if (!this.H.B && entityhuman != null && !this.a(entityhuman)) {
         int i = 0;
         if (this.o != null) {
            PlayerFishEvent playerFishEvent = new PlayerFishEvent(
               (Player)entityhuman.getBukkitEntity(), this.o.getBukkitEntity(), (FishHook)this.getBukkitEntity(), State.CAUGHT_ENTITY
            );
            this.H.getCraftServer().getPluginManager().callEvent(playerFishEvent);
            if (playerFishEvent.isCancelled()) {
               return 0;
            }

            this.c(this.o);
            CriterionTriggers.D.a((EntityPlayer)entityhuman, itemstack, this, Collections.emptyList());
            this.H.a(this, (byte)31);
            i = this.o instanceof EntityItem ? 3 : 5;
         } else if (this.j > 0) {
            LootTableInfo.Builder loottableinfo_builder = new LootTableInfo.Builder((WorldServer)this.H)
               .a(LootContextParameters.f, this.de())
               .a(LootContextParameters.i, itemstack)
               .a(LootContextParameters.a, this)
               .a(this.af)
               .a((float)this.q + entityhuman.gf());
            LootTable loottable = this.H.n().aH().a(LootTables.ai);
            List<ItemStack> list = loottable.a(loottableinfo_builder.a(LootContextParameterSets.e));
            CriterionTriggers.D.a((EntityPlayer)entityhuman, itemstack, this, list);

            for(ItemStack itemstack1 : list) {
               EntityItem entityitem = new EntityItem(this.H, this.dl(), this.dn(), this.dr(), itemstack1);
               PlayerFishEvent playerFishEvent = new PlayerFishEvent(
                  (Player)entityhuman.getBukkitEntity(), entityitem.getBukkitEntity(), (FishHook)this.getBukkitEntity(), State.CAUGHT_FISH
               );
               playerFishEvent.setExpToDrop(this.af.a(6) + 1);
               this.H.getCraftServer().getPluginManager().callEvent(playerFishEvent);
               if (playerFishEvent.isCancelled()) {
                  return 0;
               }

               double d0 = entityhuman.dl() - this.dl();
               double d1 = entityhuman.dn() - this.dn();
               double d2 = entityhuman.dr() - this.dr();
               double d3 = 0.1;
               entityitem.o(d0 * 0.1, d1 * 0.1 + Math.sqrt(Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2)) * 0.08, d2 * 0.1);
               this.H.b(entityitem);
               if (playerFishEvent.getExpToDrop() > 0) {
                  entityhuman.H
                     .b(
                        new EntityExperienceOrb(
                           entityhuman.H, entityhuman.dl(), entityhuman.dn() + 0.5, entityhuman.dr() + 0.5, playerFishEvent.getExpToDrop()
                        )
                     );
               }

               if (itemstack1.a(TagsItem.an)) {
                  entityhuman.a(StatisticList.R, 1);
               }
            }

            i = 1;
         }

         if (this.N) {
            PlayerFishEvent playerFishEvent = new PlayerFishEvent(
               (Player)entityhuman.getBukkitEntity(), null, (FishHook)this.getBukkitEntity(), State.IN_GROUND
            );
            this.H.getCraftServer().getPluginManager().callEvent(playerFishEvent);
            if (playerFishEvent.isCancelled()) {
               return 0;
            }

            i = 2;
         }

         if (i == 0) {
            PlayerFishEvent playerFishEvent = new PlayerFishEvent((Player)entityhuman.getBukkitEntity(), null, (FishHook)this.getBukkitEntity(), State.REEL_IN);
            this.H.getCraftServer().getPluginManager().callEvent(playerFishEvent);
            if (playerFishEvent.isCancelled()) {
               return 0;
            }
         }

         this.ai();
         return i;
      } else {
         return 0;
      }
   }

   @Override
   public void b(byte b0) {
      if (b0 == 31 && this.H.B && this.o instanceof EntityHuman && ((EntityHuman)this.o).g()) {
         this.c(this.o);
      }

      super.b(b0);
   }

   public void c(Entity entity) {
      Entity entity1 = this.v();
      if (entity1 != null) {
         Vec3D vec3d = new Vec3D(entity1.dl() - this.dl(), entity1.dn() - this.dn(), entity1.dr() - this.dr()).a(0.1);
         entity.f(entity.dj().e(vec3d));
      }
   }

   @Override
   protected Entity.MovementEmission aQ() {
      return Entity.MovementEmission.a;
   }

   @Override
   public void a(Entity.RemovalReason entity_removalreason) {
      this.a(null);
      super.a(entity_removalreason);
   }

   @Override
   public void ak() {
      this.a(null);
   }

   @Override
   public void b(@Nullable Entity entity) {
      super.b(entity);
      this.a(this);
   }

   private void a(@Nullable EntityFishingHook entityfishinghook) {
      EntityHuman entityhuman = this.j();
      if (entityhuman != null) {
         entityhuman.ch = entityfishinghook;
      }
   }

   @Nullable
   public EntityHuman j() {
      Entity entity = this.v();
      return entity instanceof EntityHuman ? (EntityHuman)entity : null;
   }

   @Nullable
   public Entity k() {
      return this.o;
   }

   @Override
   public boolean co() {
      return false;
   }

   @Override
   public Packet<PacketListenerPlayOut> S() {
      Entity entity = this.v();
      return new PacketPlayOutSpawnEntity(this, entity == null ? this.af() : entity.af());
   }

   @Override
   public void a(PacketPlayOutSpawnEntity packetplayoutspawnentity) {
      super.a(packetplayoutspawnentity);
      if (this.j() == null) {
         int i = packetplayoutspawnentity.n();
         b.error("Failed to recreate fishing hook on client. {} (id: {}) is not a valid owner.", this.H.a(i), i);
         this.ah();
      }
   }

   public static enum HookState {
      a,
      b,
      c;
   }

   private static enum WaterPosition {
      a,
      b,
      c;
   }
}
