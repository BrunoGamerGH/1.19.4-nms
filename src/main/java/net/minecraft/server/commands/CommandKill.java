package net.minecraft.server.commands;

import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import java.util.Collection;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.arguments.ArgumentEntity;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.world.entity.Entity;

public class CommandKill {
   public static void a(CommandDispatcher<CommandListenerWrapper> var0) {
      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("kill")
                  .requires(var0x -> var0x.c(2)))
               .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), ImmutableList.of(((CommandListenerWrapper)var0x.getSource()).g()))))
            .then(
               net.minecraft.commands.CommandDispatcher.a("targets", ArgumentEntity.b())
                  .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), ArgumentEntity.b(var0x, "targets")))
            )
      );
   }

   private static int a(CommandListenerWrapper var0, Collection<? extends Entity> var1) {
      for(Entity var3 : var1) {
         var3.ah();
      }

      if (var1.size() == 1) {
         var0.a(IChatBaseComponent.a("commands.kill.success.single", var1.iterator().next().G_()), true);
      } else {
         var0.a(IChatBaseComponent.a("commands.kill.success.multiple", var1.size()), true);
      }

      return var1.size();
   }
}
