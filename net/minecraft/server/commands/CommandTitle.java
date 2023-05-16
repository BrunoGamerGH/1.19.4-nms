package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Collection;
import java.util.function.Function;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.arguments.ArgumentChatComponent;
import net.minecraft.commands.arguments.ArgumentEntity;
import net.minecraft.commands.arguments.ArgumentTime;
import net.minecraft.network.chat.ChatComponentUtils;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundClearTitlesPacket;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
import net.minecraft.server.level.EntityPlayer;

public class CommandTitle {
   public static void a(CommandDispatcher<CommandListenerWrapper> var0) {
      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("title")
               .requires(var0x -> var0x.c(2)))
            .then(
               ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                                    "targets", ArgumentEntity.d()
                                 )
                                 .then(
                                    net.minecraft.commands.CommandDispatcher.a("clear")
                                       .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), ArgumentEntity.f(var0x, "targets")))
                                 ))
                              .then(
                                 net.minecraft.commands.CommandDispatcher.a("reset")
                                    .executes(var0x -> b((CommandListenerWrapper)var0x.getSource(), ArgumentEntity.f(var0x, "targets")))
                              ))
                           .then(
                              net.minecraft.commands.CommandDispatcher.a("title")
                                 .then(
                                    net.minecraft.commands.CommandDispatcher.a("title", ArgumentChatComponent.a())
                                       .executes(
                                          var0x -> a(
                                                (CommandListenerWrapper)var0x.getSource(),
                                                ArgumentEntity.f(var0x, "targets"),
                                                ArgumentChatComponent.a(var0x, "title"),
                                                "title",
                                                ClientboundSetTitleTextPacket::new
                                             )
                                       )
                                 )
                           ))
                        .then(
                           net.minecraft.commands.CommandDispatcher.a("subtitle")
                              .then(
                                 net.minecraft.commands.CommandDispatcher.a("title", ArgumentChatComponent.a())
                                    .executes(
                                       var0x -> a(
                                             (CommandListenerWrapper)var0x.getSource(),
                                             ArgumentEntity.f(var0x, "targets"),
                                             ArgumentChatComponent.a(var0x, "title"),
                                             "subtitle",
                                             ClientboundSetSubtitleTextPacket::new
                                          )
                                    )
                              )
                        ))
                     .then(
                        net.minecraft.commands.CommandDispatcher.a("actionbar")
                           .then(
                              net.minecraft.commands.CommandDispatcher.a("title", ArgumentChatComponent.a())
                                 .executes(
                                    var0x -> a(
                                          (CommandListenerWrapper)var0x.getSource(),
                                          ArgumentEntity.f(var0x, "targets"),
                                          ArgumentChatComponent.a(var0x, "title"),
                                          "actionbar",
                                          ClientboundSetActionBarTextPacket::new
                                       )
                                 )
                           )
                     ))
                  .then(
                     net.minecraft.commands.CommandDispatcher.a("times")
                        .then(
                           net.minecraft.commands.CommandDispatcher.a("fadeIn", ArgumentTime.a())
                              .then(
                                 net.minecraft.commands.CommandDispatcher.a("stay", ArgumentTime.a())
                                    .then(
                                       net.minecraft.commands.CommandDispatcher.a("fadeOut", ArgumentTime.a())
                                          .executes(
                                             var0x -> a(
                                                   (CommandListenerWrapper)var0x.getSource(),
                                                   ArgumentEntity.f(var0x, "targets"),
                                                   IntegerArgumentType.getInteger(var0x, "fadeIn"),
                                                   IntegerArgumentType.getInteger(var0x, "stay"),
                                                   IntegerArgumentType.getInteger(var0x, "fadeOut")
                                                )
                                          )
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int a(CommandListenerWrapper var0, Collection<EntityPlayer> var1) {
      ClientboundClearTitlesPacket var2 = new ClientboundClearTitlesPacket(false);

      for(EntityPlayer var4 : var1) {
         var4.b.a(var2);
      }

      if (var1.size() == 1) {
         var0.a(IChatBaseComponent.a("commands.title.cleared.single", var1.iterator().next().G_()), true);
      } else {
         var0.a(IChatBaseComponent.a("commands.title.cleared.multiple", var1.size()), true);
      }

      return var1.size();
   }

   private static int b(CommandListenerWrapper var0, Collection<EntityPlayer> var1) {
      ClientboundClearTitlesPacket var2 = new ClientboundClearTitlesPacket(true);

      for(EntityPlayer var4 : var1) {
         var4.b.a(var2);
      }

      if (var1.size() == 1) {
         var0.a(IChatBaseComponent.a("commands.title.reset.single", var1.iterator().next().G_()), true);
      } else {
         var0.a(IChatBaseComponent.a("commands.title.reset.multiple", var1.size()), true);
      }

      return var1.size();
   }

   private static int a(
      CommandListenerWrapper var0, Collection<EntityPlayer> var1, IChatBaseComponent var2, String var3, Function<IChatBaseComponent, Packet<?>> var4
   ) throws CommandSyntaxException {
      for(EntityPlayer var6 : var1) {
         var6.b.a(var4.apply(ChatComponentUtils.a(var0, var2, var6, 0)));
      }

      if (var1.size() == 1) {
         var0.a(IChatBaseComponent.a("commands.title.show." + var3 + ".single", var1.iterator().next().G_()), true);
      } else {
         var0.a(IChatBaseComponent.a("commands.title.show." + var3 + ".multiple", var1.size()), true);
      }

      return var1.size();
   }

   private static int a(CommandListenerWrapper var0, Collection<EntityPlayer> var1, int var2, int var3, int var4) {
      ClientboundSetTitlesAnimationPacket var5 = new ClientboundSetTitlesAnimationPacket(var2, var3, var4);

      for(EntityPlayer var7 : var1) {
         var7.b.a(var5);
      }

      if (var1.size() == 1) {
         var0.a(IChatBaseComponent.a("commands.title.times.single", var1.iterator().next().G_()), true);
      } else {
         var0.a(IChatBaseComponent.a("commands.title.times.multiple", var1.size()), true);
      }

      return var1.size();
   }
}
