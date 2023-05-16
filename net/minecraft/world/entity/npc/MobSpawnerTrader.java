package net.minecraft.world.entity.npc;

import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityPositionTypes;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.entity.ai.village.poi.VillagePlace;
import net.minecraft.world.entity.ai.village.poi.VillagePlaceType;
import net.minecraft.world.entity.animal.horse.EntityLlamaTrader;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.MobSpawner;
import net.minecraft.world.level.SpawnerCreature;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.storage.IWorldDataServer;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class MobSpawnerTrader implements MobSpawner {
   private static final int b = 1200;
   public static final int a = 24000;
   private static final int c = 25;
   private static final int d = 75;
   private static final int e = 25;
   private static final int f = 10;
   private static final int g = 10;
   private final RandomSource h = RandomSource.a();
   private final IWorldDataServer i;
   private int j;
   private int k;
   private int l;

   public MobSpawnerTrader(IWorldDataServer iworlddataserver) {
      this.i = iworlddataserver;
      this.j = 1200;
      this.k = iworlddataserver.v();
      this.l = iworlddataserver.w();
      if (this.k == 0 && this.l == 0) {
         this.k = 24000;
         iworlddataserver.g(this.k);
         this.l = 25;
         iworlddataserver.h(this.l);
      }
   }

   @Override
   public int a(WorldServer worldserver, boolean flag, boolean flag1) {
      if (!worldserver.W().b(GameRules.H)) {
         return 0;
      } else if (--this.j > 0) {
         return 0;
      } else {
         this.j = 1200;
         this.k -= 1200;
         this.i.g(this.k);
         if (this.k > 0) {
            return 0;
         } else {
            this.k = 24000;
            if (!worldserver.W().b(GameRules.e)) {
               return 0;
            } else {
               int i = this.l;
               this.l = MathHelper.a(this.l + 25, 25, 75);
               this.i.h(this.l);
               if (this.h.a(100) > i) {
                  return 0;
               } else if (this.a(worldserver)) {
                  this.l = 25;
                  return 1;
               } else {
                  return 0;
               }
            }
         }
      }
   }

   private boolean a(WorldServer worldserver) {
      EntityPlayer entityplayer = worldserver.i();
      if (entityplayer == null) {
         return true;
      } else if (this.h.a(10) != 0) {
         return false;
      } else {
         BlockPosition blockposition = entityplayer.dg();
         boolean flag = true;
         VillagePlace villageplace = worldserver.w();
         Optional<BlockPosition> optional = villageplace.d(
            holder -> holder.a(PoiTypes.o), blockposition1x -> true, blockposition, 48, VillagePlace.Occupancy.c
         );
         BlockPosition blockposition1 = optional.orElse(blockposition);
         BlockPosition blockposition2 = this.a(worldserver, blockposition1, 48);
         if (blockposition2 != null && this.a(worldserver, blockposition2)) {
            if (worldserver.v(blockposition2).a(BiomeTags.af)) {
               return false;
            }

            EntityVillagerTrader entityvillagertrader = EntityTypes.bh.spawn(worldserver, blockposition2, EnumMobSpawn.h, SpawnReason.NATURAL);
            if (entityvillagertrader != null) {
               for(int i = 0; i < 2; ++i) {
                  this.a(worldserver, entityvillagertrader, 4);
               }

               this.i.a(entityvillagertrader.cs());
               entityvillagertrader.g(blockposition1);
               entityvillagertrader.a(blockposition1, 16);
               return true;
            }
         }

         return false;
      }
   }

   private void a(WorldServer worldserver, EntityVillagerTrader entityvillagertrader, int i) {
      BlockPosition blockposition = this.a(worldserver, entityvillagertrader.dg(), i);
      if (blockposition != null) {
         EntityLlamaTrader entityllamatrader = EntityTypes.ba.spawn(worldserver, blockposition, EnumMobSpawn.h, SpawnReason.NATURAL);
         if (entityllamatrader != null) {
            entityllamatrader.b(entityvillagertrader, true);
         }
      }
   }

   @Nullable
   private BlockPosition a(IWorldReader iworldreader, BlockPosition blockposition, int i) {
      BlockPosition blockposition1 = null;

      for(int j = 0; j < 10; ++j) {
         int k = blockposition.u() + this.h.a(i * 2) - i;
         int l = blockposition.w() + this.h.a(i * 2) - i;
         int i1 = iworldreader.a(HeightMap.Type.b, k, l);
         BlockPosition blockposition2 = new BlockPosition(k, i1, l);
         if (SpawnerCreature.a(EntityPositionTypes.Surface.a, iworldreader, blockposition2, EntityTypes.bh)) {
            blockposition1 = blockposition2;
            break;
         }
      }

      return blockposition1;
   }

   private boolean a(IBlockAccess iblockaccess, BlockPosition blockposition) {
      for(BlockPosition blockposition1 : BlockPosition.a(blockposition, blockposition.b(1, 2, 1))) {
         if (!iblockaccess.a_(blockposition1).k(iblockaccess, blockposition1).b()) {
            return false;
         }
      }

      return true;
   }
}
