package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.Collection;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.CustomFunction;
import net.minecraft.commands.ICompletionProvider;
import net.minecraft.commands.arguments.item.ArgumentTag;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.CustomFunctionData;

public class CommandFunction {
   public static final SuggestionProvider<CommandListenerWrapper> a = (var0, var1) -> {
      CustomFunctionData var2 = ((CommandListenerWrapper)var0.getSource()).l().aA();
      ICompletionProvider.a(var2.f(), var1, "#");
      return ICompletionProvider.a(var2.e(), var1);
   };

   public static void a(CommandDispatcher<CommandListenerWrapper> var0) {
      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("function")
               .requires(var0x -> var0x.c(2)))
            .then(
               net.minecraft.commands.CommandDispatcher.a("name", ArgumentTag.a())
                  .suggests(a)
                  .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), ArgumentTag.a(var0x, "name")))
            )
      );
   }

   private static int a(CommandListenerWrapper var0, Collection<CustomFunction> var1) {
      int var2 = 0;

      for(CustomFunction var4 : var1) {
         var2 += var0.l().aA().a(var4, var0.a().b(2));
      }

      if (var1.size() == 1) {
         var0.a(IChatBaseComponent.a("commands.function.success.single", var2, var1.iterator().next().a()), true);
      } else {
         var0.a(IChatBaseComponent.a("commands.function.success.multiple", var2, var1.size()), true);
      }

      return var2;
   }
}
