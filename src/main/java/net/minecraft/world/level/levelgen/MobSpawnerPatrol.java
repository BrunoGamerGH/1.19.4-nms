package net.minecraft.world.level.levelgen;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.monster.EntityMonsterPatrolling;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.MobSpawner;
import net.minecraft.world.level.SpawnerCreature;
import net.minecraft.world.level.World;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.block.state.IBlockData;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class MobSpawnerPatrol implements MobSpawner {
   private int a;

   @Override
   public int a(WorldServer worldserver, boolean flag, boolean flag1) {
      if (!flag) {
         return 0;
      } else if (!worldserver.W().b(GameRules.G)) {
         return 0;
      } else {
         RandomSource randomsource = worldserver.z;
         --this.a;
         if (this.a > 0) {
            return 0;
         } else {
            this.a += 12000 + randomsource.a(1200);
            long i = worldserver.V() / 24000L;
            if (i < 5L || !worldserver.M()) {
               return 0;
            } else if (randomsource.a(5) != 0) {
               return 0;
            } else {
               int j = worldserver.v().size();
               if (j < 1) {
                  return 0;
               } else {
                  EntityHuman entityhuman = worldserver.v().get(randomsource.a(j));
                  if (entityhuman.F_()) {
                     return 0;
                  } else if (worldserver.a(entityhuman.dg(), 2)) {
                     return 0;
                  } else {
                     int k = (24 + randomsource.a(24)) * (randomsource.h() ? -1 : 1);
                     int l = (24 + randomsource.a(24)) * (randomsource.h() ? -1 : 1);
                     BlockPosition.MutableBlockPosition blockposition_mutableblockposition = entityhuman.dg().j().e(k, 0, l);
                     boolean flag2 = true;
                     if (!worldserver.b(
                        blockposition_mutableblockposition.u() - 10,
                        blockposition_mutableblockposition.w() - 10,
                        blockposition_mutableblockposition.u() + 10,
                        blockposition_mutableblockposition.w() + 10
                     )) {
                        return 0;
                     } else {
                        Holder<BiomeBase> holder = worldserver.v(blockposition_mutableblockposition);
                        if (holder.a(BiomeTags.ae)) {
                           return 0;
                        } else {
                           int i1 = 0;
                           int j1 = (int)Math.ceil((double)worldserver.d_(blockposition_mutableblockposition).b()) + 1;

                           for(int k1 = 0; k1 < j1; ++k1) {
                              ++i1;
                              blockposition_mutableblockposition.q(worldserver.a(HeightMap.Type.f, blockposition_mutableblockposition).v());
                              if (k1 == 0) {
                                 if (!this.a(worldserver, blockposition_mutableblockposition, randomsource, true)) {
                                    break;
                                 }
                              } else {
                                 this.a(worldserver, blockposition_mutableblockposition, randomsource, false);
                              }

                              blockposition_mutableblockposition.p(blockposition_mutableblockposition.u() + randomsource.a(5) - randomsource.a(5));
                              blockposition_mutableblockposition.r(blockposition_mutableblockposition.w() + randomsource.a(5) - randomsource.a(5));
                           }

                           return i1;
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private boolean a(WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource, boolean flag) {
      IBlockData iblockdata = worldserver.a_(blockposition);
      if (!SpawnerCreature.a(worldserver, blockposition, iblockdata, iblockdata.r(), EntityTypes.ay)) {
         return false;
      } else if (!EntityMonsterPatrolling.b(EntityTypes.ay, worldserver, EnumMobSpawn.p, blockposition, randomsource)) {
         return false;
      } else {
         EntityMonsterPatrolling entitymonsterpatrolling = EntityTypes.ay.a((World)worldserver);
         if (entitymonsterpatrolling != null) {
            if (flag) {
               entitymonsterpatrolling.w(true);
               entitymonsterpatrolling.ga();
            }

            entitymonsterpatrolling.e((double)blockposition.u(), (double)blockposition.v(), (double)blockposition.w());
            entitymonsterpatrolling.a(worldserver, worldserver.d_(blockposition), EnumMobSpawn.p, null, null);
            worldserver.addFreshEntityWithPassengers(entitymonsterpatrolling, SpawnReason.PATROL);
            return true;
         } else {
            return false;
         }
      }
   }
}
