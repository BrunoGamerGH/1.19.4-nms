package net.minecraft.world.level.block;

import java.util.function.Predicate;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.IEntitySelector;
import net.minecraft.world.entity.item.EntityFallingBlock;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.IBlockData;

public interface Fallable {
   default void a(World var0, BlockPosition var1, IBlockData var2, IBlockData var3, EntityFallingBlock var4) {
   }

   default void a(World var0, BlockPosition var1, EntityFallingBlock var2) {
   }

   default DamageSource a(Entity var0) {
      return var0.dG().a(var0);
   }

   default Predicate<Entity> al_() {
      return IEntitySelector.f;
   }
}
