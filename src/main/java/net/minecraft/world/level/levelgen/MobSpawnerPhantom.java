package net.minecraft.world.level.levelgen;

import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.stats.ServerStatisticManager;
import net.minecraft.stats.StatisticList;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyDamageScaler;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.GroupDataEntity;
import net.minecraft.world.entity.monster.EntityPhantom;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.MobSpawner;
import net.minecraft.world.level.SpawnerCreature;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.material.Fluid;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class MobSpawnerPhantom implements MobSpawner {
   private int a;

   @Override
   public int a(WorldServer worldserver, boolean flag, boolean flag1) {
      if (!flag) {
         return 0;
      } else if (!worldserver.W().b(GameRules.A)) {
         return 0;
      } else {
         RandomSource randomsource = worldserver.z;
         --this.a;
         if (this.a > 0) {
            return 0;
         } else {
            this.a += (60 + randomsource.a(60)) * 20;
            if (worldserver.o_() < 5 && worldserver.q_().g()) {
               return 0;
            } else {
               int i = 0;

               for(EntityHuman entityhuman : worldserver.v()) {
                  if (!entityhuman.F_()) {
                     BlockPosition blockposition = entityhuman.dg();
                     if (!worldserver.q_().g() || blockposition.v() >= worldserver.m_() && worldserver.g(blockposition)) {
                        DifficultyDamageScaler difficultydamagescaler = worldserver.d_(blockposition);
                        if (difficultydamagescaler.a(randomsource.i() * 3.0F)) {
                           ServerStatisticManager serverstatisticmanager = ((EntityPlayer)entityhuman).D();
                           int j = MathHelper.a(serverstatisticmanager.a(StatisticList.i.b(StatisticList.n)), 1, Integer.MAX_VALUE);
                           boolean flag2 = true;
                           if (randomsource.a(j) >= 72000) {
                              BlockPosition blockposition1 = blockposition.b(20 + randomsource.a(15)).g(-10 + randomsource.a(21)).e(-10 + randomsource.a(21));
                              IBlockData iblockdata = worldserver.a_(blockposition1);
                              Fluid fluid = worldserver.b_(blockposition1);
                              if (SpawnerCreature.a(worldserver, blockposition1, iblockdata, fluid, EntityTypes.au)) {
                                 GroupDataEntity groupdataentity = null;
                                 int k = 1 + randomsource.a(difficultydamagescaler.a().a() + 1);

                                 for(int l = 0; l < k; ++l) {
                                    EntityPhantom entityphantom = EntityTypes.au.a((World)worldserver);
                                    if (entityphantom != null) {
                                       entityphantom.a(blockposition1, 0.0F, 0.0F);
                                       groupdataentity = entityphantom.a(worldserver, difficultydamagescaler, EnumMobSpawn.a, groupdataentity, null);
                                       worldserver.addFreshEntityWithPassengers(entityphantom, SpawnReason.NATURAL);
                                       ++i;
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }

               return i;
            }
         }
      }
   }
}
