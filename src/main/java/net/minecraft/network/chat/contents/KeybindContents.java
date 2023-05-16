package net.minecraft.network.chat.contents;

import java.util.Optional;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.network.chat.ChatModifier;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.IChatFormatted;

public class KeybindContents implements ComponentContents {
   private final String b;
   @Nullable
   private Supplier<IChatBaseComponent> c;

   public KeybindContents(String var0) {
      this.b = var0;
   }

   private IChatBaseComponent b() {
      if (this.c == null) {
         this.c = KeybindResolver.a.apply(this.b);
      }

      return this.c.get();
   }

   @Override
   public <T> Optional<T> a(IChatFormatted.a<T> var0) {
      return this.b().a(var0);
   }

   @Override
   public <T> Optional<T> a(IChatFormatted.b<T> var0, ChatModifier var1) {
      return this.b().a(var0, var1);
   }

   @Override
   public boolean equals(Object var0) {
      if (this == var0) {
         return true;
      } else {
         if (var0 instanceof KeybindContents var1 && this.b.equals(var1.b)) {
            return true;
         }

         return false;
      }
   }

   @Override
   public int hashCode() {
      return this.b.hashCode();
   }

   @Override
   public String toString() {
      return "keybind{" + this.b + "}";
   }

   public String a() {
      return this.b;
   }
}
