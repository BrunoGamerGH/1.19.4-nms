package net.minecraft.world.level;

import com.mojang.logging.LogUtils;
import java.util.Optional;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.particles.Particles;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.DynamicOpsNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityPositionTypes;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AxisAlignedBB;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.slf4j.Logger;

public abstract class MobSpawnerAbstract {
   public static final String b = "SpawnData";
   private static final Logger a = LogUtils.getLogger();
   private static final int c = 1;
   public int d = 20;
   public SimpleWeightedRandomList<MobSpawnerData> e = SimpleWeightedRandomList.b();
   @Nullable
   public MobSpawnerData f;
   private double g;
   private double h;
   public int i = 200;
   public int j = 800;
   public int k = 4;
   @Nullable
   private Entity l;
   public int m = 6;
   public int n = 16;
   public int o = 4;

   public void a(EntityTypes<?> entitytypes, @Nullable World world, RandomSource randomsource, BlockPosition blockposition) {
      this.b(world, randomsource, blockposition).a().a("id", BuiltInRegistries.h.b(entitytypes).toString());
      this.e = SimpleWeightedRandomList.b();
   }

   private boolean b(World world, BlockPosition blockposition) {
      return world.a((double)blockposition.u() + 0.5, (double)blockposition.v() + 0.5, (double)blockposition.w() + 0.5, (double)this.n);
   }

   public void a(World world, BlockPosition blockposition) {
      if (!this.b(world, blockposition)) {
         this.h = this.g;
      } else if (this.l != null) {
         RandomSource randomsource = world.r_();
         double d0 = (double)blockposition.u() + randomsource.j();
         double d1 = (double)blockposition.v() + randomsource.j();
         double d2 = (double)blockposition.w() + randomsource.j();
         world.a(Particles.ab, d0, d1, d2, 0.0, 0.0, 0.0);
         world.a(Particles.C, d0, d1, d2, 0.0, 0.0, 0.0);
         if (this.d > 0) {
            --this.d;
         }

         this.h = this.g;
         this.g = (this.g + (double)(1000.0F / ((float)this.d + 200.0F))) % 360.0;
      }
   }

   public void a(WorldServer worldserver, BlockPosition blockposition) {
      if (this.b(worldserver, blockposition)) {
         if (this.d == -1) {
            this.c(worldserver, blockposition);
         }

         if (this.d > 0) {
            --this.d;
         } else {
            boolean flag = false;
            RandomSource randomsource = worldserver.r_();
            MobSpawnerData mobspawnerdata = this.b(worldserver, randomsource, blockposition);

            for(int i = 0; i < this.k; ++i) {
               NBTTagCompound nbttagcompound = mobspawnerdata.a();
               Optional<EntityTypes<?>> optional = EntityTypes.a(nbttagcompound);
               if (optional.isEmpty()) {
                  this.c(worldserver, blockposition);
                  return;
               }

               NBTTagList nbttaglist = nbttagcompound.c("Pos", 6);
               int j = nbttaglist.size();
               double d0 = j >= 1 ? nbttaglist.h(0) : (double)blockposition.u() + (randomsource.j() - randomsource.j()) * (double)this.o + 0.5;
               double d1 = j >= 2 ? nbttaglist.h(1) : (double)(blockposition.v() + randomsource.a(3) - 1);
               double d2 = j >= 3 ? nbttaglist.h(2) : (double)blockposition.w() + (randomsource.j() - randomsource.j()) * (double)this.o + 0.5;
               if (worldserver.b(optional.get().a(d0, d1, d2))) {
                  BlockPosition blockposition1 = BlockPosition.a(d0, d1, d2);
                  if (mobspawnerdata.b().isPresent()) {
                     if (!optional.get().f().d() && worldserver.ah() == EnumDifficulty.a) {
                        continue;
                     }

                     MobSpawnerData.a mobspawnerdata_a = mobspawnerdata.b().get();
                     if (!mobspawnerdata_a.a().a(worldserver.a(EnumSkyBlock.b, blockposition1))
                        || !mobspawnerdata_a.b().a(worldserver.a(EnumSkyBlock.a, blockposition1))) {
                        continue;
                     }
                  } else if (!EntityPositionTypes.a(optional.get(), worldserver, EnumMobSpawn.c, blockposition1, worldserver.r_())) {
                     continue;
                  }

                  Entity entity = EntityTypes.a(nbttagcompound, worldserver, entity1 -> {
                     entity1.b(d0, d1, d2, entity1.dw(), entity1.dy());
                     return entity1;
                  });
                  if (entity == null) {
                     this.c(worldserver, blockposition);
                     return;
                  }

                  int k = worldserver.a(
                        entity.getClass(),
                        new AxisAlignedBB(
                              (double)blockposition.u(),
                              (double)blockposition.v(),
                              (double)blockposition.w(),
                              (double)(blockposition.u() + 1),
                              (double)(blockposition.v() + 1),
                              (double)(blockposition.w() + 1)
                           )
                           .g((double)this.o)
                     )
                     .size();
                  if (k >= this.m) {
                     this.c(worldserver, blockposition);
                     return;
                  }

                  entity.b(entity.dl(), entity.dn(), entity.dr(), randomsource.i() * 360.0F, 0.0F);
                  if (entity instanceof EntityInsentient entityinsentient) {
                     if (mobspawnerdata.b().isEmpty() && !entityinsentient.a(worldserver, EnumMobSpawn.c) || !entityinsentient.a(worldserver)) {
                        continue;
                     }

                     if (mobspawnerdata.a().f() == 1 && mobspawnerdata.a().b("id", 8)) {
                        ((EntityInsentient)entity).a(worldserver, worldserver.d_(entity.dg()), EnumMobSpawn.c, null, null);
                     }

                     if (entityinsentient.H.spigotConfig.nerfSpawnerMobs) {
                        entityinsentient.aware = false;
                     }
                  }

                  if (CraftEventFactory.callSpawnerSpawnEvent(entity, blockposition).isCancelled()) {
                     Entity vehicle = entity.cV();
                     if (vehicle != null) {
                        vehicle.ai();
                     }

                     for(Entity passenger : entity.cQ()) {
                        passenger.ai();
                     }
                  } else {
                     if (!worldserver.tryAddFreshEntityWithPassengers(entity, SpawnReason.SPAWNER)) {
                        this.c(worldserver, blockposition);
                        return;
                     }

                     worldserver.c(2004, blockposition, 0);
                     worldserver.a(entity, GameEvent.u, blockposition1);
                     if (entity instanceof EntityInsentient) {
                        ((EntityInsentient)entity).M();
                     }

                     flag = true;
                  }
               }
            }

            if (flag) {
               this.c(worldserver, blockposition);
            }
         }
      }
   }

   private void c(World world, BlockPosition blockposition) {
      RandomSource randomsource = world.z;
      if (this.j <= this.i) {
         this.d = this.i;
      } else {
         this.d = this.i + randomsource.a(this.j - this.i);
      }

      this.e.b(randomsource).ifPresent(weightedentry_b -> this.a(world, blockposition, weightedentry_b.b()));
      this.a(world, blockposition, 1);
   }

   public void a(@Nullable World world, BlockPosition blockposition, NBTTagCompound nbttagcompound) {
      this.d = nbttagcompound.g("Delay");
      boolean flag = nbttagcompound.b("SpawnData", 10);
      if (flag) {
         MobSpawnerData mobspawnerdata = MobSpawnerData.b
            .parse(DynamicOpsNBT.a, nbttagcompound.p("SpawnData"))
            .resultOrPartial(s -> a.warn("Invalid SpawnData: {}", s))
            .orElseGet(MobSpawnerData::new);
         this.a(world, blockposition, mobspawnerdata);
      }

      boolean flag1 = nbttagcompound.b("SpawnPotentials", 9);
      if (flag1) {
         NBTTagList nbttaglist = nbttagcompound.c("SpawnPotentials", 10);
         this.e = (SimpleWeightedRandomList)MobSpawnerData.c
            .parse(DynamicOpsNBT.a, nbttaglist)
            .resultOrPartial(s -> a.warn("Invalid SpawnPotentials list: {}", s))
            .orElseGet(SimpleWeightedRandomList::b);
      } else {
         this.e = SimpleWeightedRandomList.a(this.f != null ? this.f : new MobSpawnerData());
      }

      if (nbttagcompound.b("MinSpawnDelay", 99)) {
         this.i = nbttagcompound.g("MinSpawnDelay");
         this.j = nbttagcompound.g("MaxSpawnDelay");
         this.k = nbttagcompound.g("SpawnCount");
      }

      if (nbttagcompound.b("MaxNearbyEntities", 99)) {
         this.m = nbttagcompound.g("MaxNearbyEntities");
         this.n = nbttagcompound.g("RequiredPlayerRange");
      }

      if (nbttagcompound.b("SpawnRange", 99)) {
         this.o = nbttagcompound.g("SpawnRange");
      }

      this.l = null;
   }

   public NBTTagCompound a(NBTTagCompound nbttagcompound) {
      nbttagcompound.a("Delay", (short)this.d);
      nbttagcompound.a("MinSpawnDelay", (short)this.i);
      nbttagcompound.a("MaxSpawnDelay", (short)this.j);
      nbttagcompound.a("SpawnCount", (short)this.k);
      nbttagcompound.a("MaxNearbyEntities", (short)this.m);
      nbttagcompound.a("RequiredPlayerRange", (short)this.n);
      nbttagcompound.a("SpawnRange", (short)this.o);
      if (this.f != null) {
         nbttagcompound.a(
            "SpawnData",
            (NBTBase)MobSpawnerData.b.encodeStart(DynamicOpsNBT.a, this.f).result().orElseThrow(() -> new IllegalStateException("Invalid SpawnData"))
         );
      }

      nbttagcompound.a("SpawnPotentials", (NBTBase)MobSpawnerData.c.encodeStart(DynamicOpsNBT.a, this.e).result().orElseThrow());
      return nbttagcompound;
   }

   @Nullable
   public Entity a(World world, RandomSource randomsource, BlockPosition blockposition) {
      if (this.l == null) {
         NBTTagCompound nbttagcompound = this.b(world, randomsource, blockposition).a();
         if (!nbttagcompound.b("id", 8)) {
            return null;
         }

         this.l = EntityTypes.a(nbttagcompound, world, Function.identity());
         if (nbttagcompound.f() == 1 && this.l instanceof EntityInsentient) {
         }
      }

      return this.l;
   }

   public boolean a(World world, int i) {
      if (i == 1) {
         if (world.B) {
            this.d = this.i;
         }

         return true;
      } else {
         return false;
      }
   }

   protected void a(@Nullable World world, BlockPosition blockposition, MobSpawnerData mobspawnerdata) {
      this.f = mobspawnerdata;
   }

   private MobSpawnerData b(@Nullable World world, RandomSource randomsource, BlockPosition blockposition) {
      if (this.f != null) {
         return this.f;
      } else {
         this.a(world, blockposition, this.e.b(randomsource).map(WeightedEntry.b::b).orElseGet(MobSpawnerData::new));
         return this.f;
      }
   }

   public abstract void a(World var1, BlockPosition var2, int var3);

   public double a() {
      return this.g;
   }

   public double b() {
      return this.h;
   }
}
