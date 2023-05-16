package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import java.util.Collection;
import java.util.Collections;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.arguments.ArgumentAngle;
import net.minecraft.commands.arguments.ArgumentEntity;
import net.minecraft.commands.arguments.coordinates.ArgumentPosition;
import net.minecraft.core.BlockPosition;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.level.World;

public class CommandSpawnpoint {
   public static void a(CommandDispatcher<CommandListenerWrapper> var0) {
      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                     "spawnpoint"
                  )
                  .requires(var0x -> var0x.c(2)))
               .executes(
                  var0x -> a(
                        (CommandListenerWrapper)var0x.getSource(),
                        Collections.singleton(((CommandListenerWrapper)var0x.getSource()).h()),
                        BlockPosition.a(((CommandListenerWrapper)var0x.getSource()).d()),
                        0.0F
                     )
               ))
            .then(
               ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("targets", ArgumentEntity.d())
                     .executes(
                        var0x -> a(
                              (CommandListenerWrapper)var0x.getSource(),
                              ArgumentEntity.f(var0x, "targets"),
                              BlockPosition.a(((CommandListenerWrapper)var0x.getSource()).d()),
                              0.0F
                           )
                     ))
                  .then(
                     ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("pos", ArgumentPosition.a())
                           .executes(
                              var0x -> a((CommandListenerWrapper)var0x.getSource(), ArgumentEntity.f(var0x, "targets"), ArgumentPosition.c(var0x, "pos"), 0.0F)
                           ))
                        .then(
                           net.minecraft.commands.CommandDispatcher.a("angle", ArgumentAngle.a())
                              .executes(
                                 var0x -> a(
                                       (CommandListenerWrapper)var0x.getSource(),
                                       ArgumentEntity.f(var0x, "targets"),
                                       ArgumentPosition.c(var0x, "pos"),
                                       ArgumentAngle.a(var0x, "angle")
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int a(CommandListenerWrapper var0, Collection<EntityPlayer> var1, BlockPosition var2, float var3) {
      ResourceKey<World> var4 = var0.e().ab();

      for(EntityPlayer var6 : var1) {
         var6.a(var4, var2, var3, true, false);
      }

      String var5 = var4.a().toString();
      if (var1.size() == 1) {
         var0.a(IChatBaseComponent.a("commands.spawnpoint.success.single", var2.u(), var2.v(), var2.w(), var3, var5, var1.iterator().next().G_()), true);
      } else {
         var0.a(IChatBaseComponent.a("commands.spawnpoint.success.multiple", var2.u(), var2.v(), var2.w(), var3, var5, var1.size()), true);
      }

      return var1.size();
   }
}
