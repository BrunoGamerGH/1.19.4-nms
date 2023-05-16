package net.minecraft.util;

import java.util.Optional;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.IBlockData;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class SpawnUtil {
   public static <T extends EntityInsentient> Optional<T> a(
      EntityTypes<T> entitytypes,
      EnumMobSpawn enummobspawn,
      WorldServer worldserver,
      BlockPosition blockposition,
      int i,
      int j,
      int k,
      SpawnUtil.a spawnutil_a
   ) {
      return trySpawnMob(entitytypes, enummobspawn, worldserver, blockposition, i, j, k, spawnutil_a, SpawnReason.DEFAULT);
   }

   public static <T extends EntityInsentient> Optional<T> trySpawnMob(
      EntityTypes<T> entitytypes,
      EnumMobSpawn enummobspawn,
      WorldServer worldserver,
      BlockPosition blockposition,
      int i,
      int j,
      int k,
      SpawnUtil.a spawnutil_a,
      SpawnReason reason
   ) {
      BlockPosition.MutableBlockPosition blockposition_mutableblockposition = blockposition.j();

      for(int l = 0; l < i; ++l) {
         int i1 = MathHelper.b(worldserver.z, -j, j);
         int j1 = MathHelper.b(worldserver.z, -j, j);
         blockposition_mutableblockposition.a(blockposition, i1, k, j1);
         if (worldserver.p_().a(blockposition_mutableblockposition) && a(worldserver, k, blockposition_mutableblockposition, spawnutil_a)) {
            T t0 = entitytypes.b(worldserver, null, null, blockposition_mutableblockposition, enummobspawn, false, false);
            if (t0 != null) {
               if (t0.a(worldserver, enummobspawn) && t0.a(worldserver)) {
                  worldserver.addFreshEntityWithPassengers(t0, reason);
                  return Optional.of(t0);
               }

               t0.ai();
            }
         }
      }

      return Optional.empty();
   }

   private static boolean a(WorldServer worldserver, int i, BlockPosition.MutableBlockPosition blockposition_mutableblockposition, SpawnUtil.a spawnutil_a) {
      BlockPosition.MutableBlockPosition blockposition_mutableblockposition1 = new BlockPosition.MutableBlockPosition().g(blockposition_mutableblockposition);
      IBlockData iblockdata = worldserver.a_(blockposition_mutableblockposition1);

      for(int j = i; j >= -i; --j) {
         blockposition_mutableblockposition.c(EnumDirection.a);
         blockposition_mutableblockposition1.a(blockposition_mutableblockposition, EnumDirection.b);
         IBlockData iblockdata1 = worldserver.a_(blockposition_mutableblockposition);
         if (spawnutil_a.canSpawnOn(worldserver, blockposition_mutableblockposition, iblockdata1, blockposition_mutableblockposition1, iblockdata)) {
            blockposition_mutableblockposition.c(EnumDirection.b);
            return true;
         }

         iblockdata = iblockdata1;
      }

      return false;
   }

   public interface a {
      SpawnUtil.a a = (worldserver, blockposition, iblockdata, blockposition1, iblockdata1) -> (iblockdata1.h() || iblockdata1.d().a()) && iblockdata.d().f();
      SpawnUtil.a b = (worldserver, blockposition, iblockdata, blockposition1, iblockdata1) -> iblockdata1.k(worldserver, blockposition1).b()
            && Block.a(iblockdata.k(worldserver, blockposition), EnumDirection.b);

      boolean canSpawnOn(WorldServer var1, BlockPosition var2, IBlockData var3, BlockPosition var4, IBlockData var5);
   }
}
