package net.minecraft.world.level;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.IBlockData;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public interface IWorldWriter {
   boolean a(BlockPosition var1, IBlockData var2, int var3, int var4);

   default boolean a(BlockPosition blockposition, IBlockData iblockdata, int i) {
      return this.a(blockposition, iblockdata, i, 512);
   }

   boolean a(BlockPosition var1, boolean var2);

   default boolean b(BlockPosition blockposition, boolean flag) {
      return this.a(blockposition, flag, null);
   }

   default boolean a(BlockPosition blockposition, boolean flag, @Nullable Entity entity) {
      return this.a(blockposition, flag, entity, 512);
   }

   boolean a(BlockPosition var1, boolean var2, @Nullable Entity var3, int var4);

   default boolean b(Entity entity) {
      return false;
   }

   default boolean addFreshEntity(Entity entity, SpawnReason reason) {
      return false;
   }
}
