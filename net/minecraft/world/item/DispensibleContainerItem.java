package net.minecraft.world.item;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.World;
import net.minecraft.world.phys.MovingObjectPositionBlock;

public interface DispensibleContainerItem {
   default void a(@Nullable EntityHuman var0, World var1, ItemStack var2, BlockPosition var3) {
   }

   boolean a(@Nullable EntityHuman var1, World var2, BlockPosition var3, @Nullable MovingObjectPositionBlock var4);
}
