package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import java.util.Collection;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.arguments.ArgumentEntity;
import net.minecraft.commands.arguments.ArgumentMinecraftKeyRegistered;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.commands.synchronization.CompletionProviders;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.game.PacketPlayOutStopSound;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.sounds.SoundCategory;

public class CommandStopSound {
   public static void a(CommandDispatcher<CommandListenerWrapper> var0) {
      RequiredArgumentBuilder<CommandListenerWrapper, EntitySelector> var1 = (RequiredArgumentBuilder)((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
               "targets", ArgumentEntity.d()
            )
            .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), ArgumentEntity.f(var0x, "targets"), null, null)))
         .then(
            net.minecraft.commands.CommandDispatcher.a("*")
               .then(
                  net.minecraft.commands.CommandDispatcher.a("sound", ArgumentMinecraftKeyRegistered.a())
                     .suggests(CompletionProviders.c)
                     .executes(
                        var0x -> a(
                              (CommandListenerWrapper)var0x.getSource(),
                              ArgumentEntity.f(var0x, "targets"),
                              null,
                              ArgumentMinecraftKeyRegistered.e(var0x, "sound")
                           )
                     )
               )
         );

      for(SoundCategory var5 : SoundCategory.values()) {
         var1.then(
            ((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(var5.a())
                  .executes(var1x -> a((CommandListenerWrapper)var1x.getSource(), ArgumentEntity.f(var1x, "targets"), var5, null)))
               .then(
                  net.minecraft.commands.CommandDispatcher.a("sound", ArgumentMinecraftKeyRegistered.a())
                     .suggests(CompletionProviders.c)
                     .executes(
                        var1x -> a(
                              (CommandListenerWrapper)var1x.getSource(),
                              ArgumentEntity.f(var1x, "targets"),
                              var5,
                              ArgumentMinecraftKeyRegistered.e(var1x, "sound")
                           )
                     )
               )
         );
      }

      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("stopsound")
               .requires(var0x -> var0x.c(2)))
            .then(var1)
      );
   }

   private static int a(CommandListenerWrapper var0, Collection<EntityPlayer> var1, @Nullable SoundCategory var2, @Nullable MinecraftKey var3) {
      PacketPlayOutStopSound var4 = new PacketPlayOutStopSound(var3, var2);

      for(EntityPlayer var6 : var1) {
         var6.b.a(var4);
      }

      if (var2 != null) {
         if (var3 != null) {
            var0.a(IChatBaseComponent.a("commands.stopsound.success.source.sound", var3, var2.a()), true);
         } else {
            var0.a(IChatBaseComponent.a("commands.stopsound.success.source.any", var2.a()), true);
         }
      } else if (var3 != null) {
         var0.a(IChatBaseComponent.a("commands.stopsound.success.sourceless.sound", var3), true);
      } else {
         var0.a(IChatBaseComponent.c("commands.stopsound.success.sourceless.any"), true);
      }

      return var1.size();
   }
}
