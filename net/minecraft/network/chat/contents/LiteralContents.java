package net.minecraft.network.chat.contents;

import java.util.Optional;
import net.minecraft.network.chat.ChatModifier;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.IChatFormatted;

public record LiteralContents(String text) implements ComponentContents {
   private final String b;

   public LiteralContents(String var0) {
      this.b = var0;
   }

   @Override
   public <T> Optional<T> a(IChatFormatted.a<T> var0) {
      return var0.accept(this.b);
   }

   @Override
   public <T> Optional<T> a(IChatFormatted.b<T> var0, ChatModifier var1) {
      return var0.accept(var1, this.b);
   }

   @Override
   public String toString() {
      return "literal{" + this.b + "}";
   }

   public String a() {
      return this.b;
   }
}
