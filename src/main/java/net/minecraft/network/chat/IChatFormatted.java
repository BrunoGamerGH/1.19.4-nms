package net.minecraft.network.chat;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Optional;
import net.minecraft.util.Unit;

public interface IChatFormatted {
   Optional<Unit> a = Optional.of(Unit.a);
   IChatFormatted b = new IChatFormatted() {
      @Override
      public <T> Optional<T> a(IChatFormatted.a<T> var0) {
         return Optional.empty();
      }

      @Override
      public <T> Optional<T> a(IChatFormatted.b<T> var0, ChatModifier var1) {
         return Optional.empty();
      }
   };

   <T> Optional<T> a(IChatFormatted.a<T> var1);

   <T> Optional<T> a(IChatFormatted.b<T> var1, ChatModifier var2);

   static IChatFormatted e(final String var0) {
      return new IChatFormatted() {
         @Override
         public <T> Optional<T> a(IChatFormatted.a<T> var0x) {
            return var0.accept(var0);
         }

         @Override
         public <T> Optional<T> a(IChatFormatted.b<T> var0x, ChatModifier var1) {
            return var0.accept(var1, var0);
         }
      };
   }

   static IChatFormatted a(final String var0, final ChatModifier var1) {
      return new IChatFormatted() {
         @Override
         public <T> Optional<T> a(IChatFormatted.a<T> var0x) {
            return var0.accept(var0);
         }

         @Override
         public <T> Optional<T> a(IChatFormatted.b<T> var0x, ChatModifier var1x) {
            return var0.accept(var1.a(var1), var0);
         }
      };
   }

   static IChatFormatted a(IChatFormatted... var0) {
      return a(ImmutableList.copyOf(var0));
   }

   static IChatFormatted a(final List<? extends IChatFormatted> var0) {
      return new IChatFormatted() {
         @Override
         public <T> Optional<T> a(IChatFormatted.a<T> var0x) {
            for(IChatFormatted var2 : var0) {
               Optional<T> var3 = var2.a(var0);
               if (var3.isPresent()) {
                  return var3;
               }
            }

            return Optional.empty();
         }

         @Override
         public <T> Optional<T> a(IChatFormatted.b<T> var0x, ChatModifier var1) {
            for(IChatFormatted var3 : var0) {
               Optional<T> var4 = var3.a(var0, var1);
               if (var4.isPresent()) {
                  return var4;
               }
            }

            return Optional.empty();
         }
      };
   }

   default String getString() {
      StringBuilder var0 = new StringBuilder();
      this.a(var1x -> {
         var0.append(var1x);
         return Optional.empty();
      });
      return var0.toString();
   }

   public interface a<T> {
      Optional<T> accept(String var1);
   }

   public interface b<T> {
      Optional<T> accept(ChatModifier var1, String var2);
   }
}
