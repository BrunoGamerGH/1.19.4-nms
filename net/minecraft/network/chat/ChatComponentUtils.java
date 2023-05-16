package net.minecraft.network.chat;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.DataFixUtils;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.EnumChatFormat;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.locale.LocaleLanguage;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.entity.Entity;

public class ChatComponentUtils {
   public static final String a = ", ";
   public static final IChatBaseComponent b = IChatBaseComponent.b(", ").a(EnumChatFormat.h);
   public static final IChatBaseComponent c = IChatBaseComponent.b(", ");

   public static IChatMutableComponent a(IChatMutableComponent var0, ChatModifier var1) {
      if (var1.g()) {
         return var0;
      } else {
         ChatModifier var2 = var0.a();
         if (var2.g()) {
            return var0.b(var1);
         } else {
            return var2.equals(var1) ? var0 : var0.b(var2.a(var1));
         }
      }
   }

   public static Optional<IChatMutableComponent> a(@Nullable CommandListenerWrapper var0, Optional<IChatBaseComponent> var1, @Nullable Entity var2, int var3) throws CommandSyntaxException {
      return var1.isPresent() ? Optional.of(a(var0, var1.get(), var2, var3)) : Optional.empty();
   }

   public static IChatMutableComponent a(@Nullable CommandListenerWrapper var0, IChatBaseComponent var1, @Nullable Entity var2, int var3) throws CommandSyntaxException {
      if (var3 > 100) {
         return var1.e();
      } else {
         IChatMutableComponent var4 = var1.b().a(var0, var2, var3 + 1);

         for(IChatBaseComponent var6 : var1.c()) {
            var4.b(a(var0, var6, var2, var3 + 1));
         }

         return var4.c(a(var0, var1.a(), var2, var3));
      }
   }

   private static ChatModifier a(@Nullable CommandListenerWrapper var0, ChatModifier var1, @Nullable Entity var2, int var3) throws CommandSyntaxException {
      ChatHoverable var4 = var1.i();
      if (var4 != null) {
         IChatBaseComponent var5 = var4.a(ChatHoverable.EnumHoverAction.a);
         if (var5 != null) {
            ChatHoverable var6 = new ChatHoverable(ChatHoverable.EnumHoverAction.a, a(var0, var5, var2, var3 + 1));
            return var1.a(var6);
         }
      }

      return var1;
   }

   public static IChatBaseComponent a(GameProfile var0) {
      if (var0.getName() != null) {
         return IChatBaseComponent.b(var0.getName());
      } else {
         return var0.getId() != null ? IChatBaseComponent.b(var0.getId().toString()) : IChatBaseComponent.b("(unknown)");
      }
   }

   public static IChatBaseComponent a(Collection<String> var0) {
      return a(var0, var0x -> IChatBaseComponent.b(var0x).a(EnumChatFormat.k));
   }

   public static <T extends Comparable<T>> IChatBaseComponent a(Collection<T> var0, Function<T, IChatBaseComponent> var1) {
      if (var0.isEmpty()) {
         return CommonComponents.a;
      } else if (var0.size() == 1) {
         return var1.apply(var0.iterator().next());
      } else {
         List<T> var2 = Lists.newArrayList(var0);
         var2.sort(Comparable::compareTo);
         return b(var2, var1);
      }
   }

   public static <T> IChatBaseComponent b(Collection<? extends T> var0, Function<T, IChatBaseComponent> var1) {
      return a(var0, b, var1);
   }

   public static <T> IChatMutableComponent a(Collection<? extends T> var0, Optional<? extends IChatBaseComponent> var1, Function<T, IChatBaseComponent> var2) {
      return a(var0, (IChatBaseComponent)DataFixUtils.orElse(var1, b), var2);
   }

   public static IChatBaseComponent a(Collection<? extends IChatBaseComponent> var0, IChatBaseComponent var1) {
      return a(var0, var1, Function.identity());
   }

   public static <T> IChatMutableComponent a(Collection<? extends T> var0, IChatBaseComponent var1, Function<T, IChatBaseComponent> var2) {
      if (var0.isEmpty()) {
         return IChatBaseComponent.h();
      } else if (var0.size() == 1) {
         return var2.apply(var0.iterator().next()).e();
      } else {
         IChatMutableComponent var3 = IChatBaseComponent.h();
         boolean var4 = true;

         for(T var6 : var0) {
            if (!var4) {
               var3.b(var1);
            }

            var3.b(var2.apply(var6));
            var4 = false;
         }

         return var3;
      }
   }

   public static IChatMutableComponent a(IChatBaseComponent var0) {
      return IChatBaseComponent.a("chat.square_brackets", var0);
   }

   public static IChatBaseComponent a(Message var0) {
      return (IChatBaseComponent)(var0 instanceof IChatBaseComponent ? (IChatBaseComponent)var0 : IChatBaseComponent.b(var0.getString()));
   }

   public static boolean b(@Nullable IChatBaseComponent var0) {
      if (var0 != null) {
         ComponentContents var2 = var0.b();
         if (var2 instanceof TranslatableContents var1) {
            String var2x = var1.a();
            String var3 = var1.b();
            return var3 != null || LocaleLanguage.a().b(var2x);
         }
      }

      return true;
   }

   public static IChatMutableComponent a(String var0) {
      return a(
         (IChatBaseComponent)IChatBaseComponent.b(var0)
            .a(
               var1 -> var1.a(EnumChatFormat.k)
                     .a(new ChatClickable(ChatClickable.EnumClickAction.f, var0))
                     .a(new ChatHoverable(ChatHoverable.EnumHoverAction.a, IChatBaseComponent.c("chat.copy.click")))
                     .a(var0)
            )
      );
   }
}
