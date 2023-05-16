package net.minecraft.world.level;

import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;

public interface GeneratorAccessSeed extends WorldAccess {
   long A();

   default boolean e_(BlockPosition var0) {
      return true;
   }

   default void a(@Nullable Supplier<String> var0) {
   }
}
