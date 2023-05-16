package net.minecraft.world;

import javax.annotation.Nullable;
import net.minecraft.network.chat.IChatBaseComponent;

public interface INamableTileEntity {
   IChatBaseComponent Z();

   default boolean aa() {
      return this.ab() != null;
   }

   default IChatBaseComponent G_() {
      return this.Z();
   }

   @Nullable
   default IChatBaseComponent ab() {
      return null;
   }
}
