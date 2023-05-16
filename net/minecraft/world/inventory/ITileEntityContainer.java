package net.minecraft.world.inventory;

import javax.annotation.Nullable;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.player.PlayerInventory;

@FunctionalInterface
public interface ITileEntityContainer {
   @Nullable
   Container createMenu(int var1, PlayerInventory var2, EntityHuman var3);
}
