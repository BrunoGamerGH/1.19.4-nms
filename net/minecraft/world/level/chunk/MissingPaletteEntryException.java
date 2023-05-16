package net.minecraft.world.level.chunk;

public class MissingPaletteEntryException extends RuntimeException {
   public MissingPaletteEntryException(int var0) {
      super("Missing Palette entry for index " + var0 + ".");
   }
}
