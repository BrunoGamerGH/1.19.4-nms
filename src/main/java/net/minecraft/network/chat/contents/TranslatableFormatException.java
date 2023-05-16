package net.minecraft.network.chat.contents;

import java.util.Locale;

public class TranslatableFormatException extends IllegalArgumentException {
   public TranslatableFormatException(TranslatableContents var0, String var1) {
      super(String.format(Locale.ROOT, "Error parsing: %s: %s", var0, var1));
   }

   public TranslatableFormatException(TranslatableContents var0, int var1) {
      super(String.format(Locale.ROOT, "Invalid index %d requested for %s", var1, var0));
   }

   public TranslatableFormatException(TranslatableContents var0, Throwable var1) {
      super(String.format(Locale.ROOT, "Error while parsing: %s", var0), var1);
   }
}
