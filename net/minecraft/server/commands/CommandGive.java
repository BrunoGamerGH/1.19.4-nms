package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Collection;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.arguments.ArgumentEntity;
import net.minecraft.commands.arguments.item.ArgumentItemStack;
import net.minecraft.commands.arguments.item.ArgumentPredicateItemStack;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.item.ItemStack;

public class CommandGive {
   public static final int a = 100;

   public static void a(CommandDispatcher<CommandListenerWrapper> commanddispatcher, CommandBuildContext commandbuildcontext) {
      commanddispatcher.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("give")
               .requires(commandlistenerwrapper -> commandlistenerwrapper.c(2)))
            .then(
               net.minecraft.commands.CommandDispatcher.a("targets", ArgumentEntity.d())
                  .then(
                     ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("item", ArgumentItemStack.a(commandbuildcontext))
                           .executes(
                              commandcontext -> a(
                                    (CommandListenerWrapper)commandcontext.getSource(),
                                    ArgumentItemStack.a(commandcontext, "item"),
                                    ArgumentEntity.f(commandcontext, "targets"),
                                    1
                                 )
                           ))
                        .then(
                           net.minecraft.commands.CommandDispatcher.a("count", IntegerArgumentType.integer(1))
                              .executes(
                                 commandcontext -> a(
                                       (CommandListenerWrapper)commandcontext.getSource(),
                                       ArgumentItemStack.a(commandcontext, "item"),
                                       ArgumentEntity.f(commandcontext, "targets"),
                                       IntegerArgumentType.getInteger(commandcontext, "count")
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int a(
      CommandListenerWrapper commandlistenerwrapper, ArgumentPredicateItemStack argumentpredicateitemstack, Collection<EntityPlayer> collection, int i
   ) throws CommandSyntaxException {
      int j = argumentpredicateitemstack.a().l();
      int k = j * 100;
      if (i > k) {
         commandlistenerwrapper.b(IChatBaseComponent.a("commands.give.failed.toomanyitems", k, argumentpredicateitemstack.a(i, false).I()));
         return 0;
      } else {
         for(EntityPlayer entityplayer : collection) {
            int l = i;

            while(l > 0) {
               int i1 = Math.min(j, l);
               l -= i1;
               ItemStack itemstack = argumentpredicateitemstack.a(i1, false);
               boolean flag = entityplayer.fJ().e(itemstack);
               if (flag && itemstack.b()) {
                  itemstack.f(1);
                  EntityItem entityitem = entityplayer.drop(itemstack, false, false, false);
                  if (entityitem != null) {
                     entityitem.t();
                  }

                  entityplayer.H
                     .a(
                        null,
                        entityplayer.dl(),
                        entityplayer.dn(),
                        entityplayer.dr(),
                        SoundEffects.lR,
                        SoundCategory.h,
                        0.2F,
                        ((entityplayer.dZ().i() - entityplayer.dZ().i()) * 0.7F + 1.0F) * 2.0F
                     );
                  entityplayer.bP.d();
               } else {
                  EntityItem entityitem = entityplayer.a(itemstack, false);
                  if (entityitem != null) {
                     entityitem.o();
                     entityitem.b(entityplayer.cs());
                  }
               }
            }
         }

         if (collection.size() == 1) {
            commandlistenerwrapper.a(
               IChatBaseComponent.a("commands.give.success.single", i, argumentpredicateitemstack.a(i, false).I(), collection.iterator().next().G_()), true
            );
         } else {
            commandlistenerwrapper.a(
               IChatBaseComponent.a("commands.give.success.single", i, argumentpredicateitemstack.a(i, false).I(), collection.size()), true
            );
         }

         return collection.size();
      }
   }
}
