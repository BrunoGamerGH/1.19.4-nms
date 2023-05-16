package net.minecraft.world.level.chunk;

import javax.annotation.Nullable;
import net.minecraft.core.SectionPosition;
import net.minecraft.world.level.EnumSkyBlock;
import net.minecraft.world.level.IBlockAccess;

public interface ILightAccess {
   @Nullable
   IBlockAccess c(int var1, int var2);

   default void a(EnumSkyBlock var0, SectionPosition var1) {
   }

   IBlockAccess q();
}
