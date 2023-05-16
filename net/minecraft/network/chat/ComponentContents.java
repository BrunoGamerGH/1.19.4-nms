package net.minecraft.network.chat;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.world.entity.Entity;

public interface ComponentContents {
   ComponentContents a = new ComponentContents() {
      @Override
      public String toString() {
         return "empty";
      }
   };

   default <T> Optional<T> a(IChatFormatted.b<T> var0, ChatModifier var1) {
      return Optional.empty();
   }

   default <T> Optional<T> a(IChatFormatted.a<T> var0) {
      return Optional.empty();
   }

   default IChatMutableComponent a(@Nullable CommandListenerWrapper var0, @Nullable Entity var1, int var2) throws CommandSyntaxException {
      return IChatMutableComponent.a(this);
   }
}
