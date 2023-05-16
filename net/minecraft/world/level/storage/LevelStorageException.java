package net.minecraft.world.level.storage;

import net.minecraft.network.chat.IChatBaseComponent;

public class LevelStorageException extends RuntimeException {
   private final IChatBaseComponent a;

   public LevelStorageException(IChatBaseComponent var0) {
      super(var0.getString());
      this.a = var0;
   }

   public IChatBaseComponent a() {
      return this.a;
   }
}
