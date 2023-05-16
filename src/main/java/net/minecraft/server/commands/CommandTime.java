package net.minecraft.server.commands;

import com.google.common.collect.Iterators;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import java.util.Iterator;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.arguments.ArgumentTime;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.level.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.event.world.TimeSkipEvent;
import org.bukkit.event.world.TimeSkipEvent.SkipReason;

public class CommandTime {
   public static void a(CommandDispatcher<CommandListenerWrapper> commanddispatcher) {
      commanddispatcher.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                        "time"
                     )
                     .requires(commandlistenerwrapper -> commandlistenerwrapper.c(2)))
                  .then(
                     ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                                       "set"
                                    )
                                    .then(
                                       net.minecraft.commands.CommandDispatcher.a("day")
                                          .executes(commandcontext -> a((CommandListenerWrapper)commandcontext.getSource(), 1000))
                                    ))
                                 .then(
                                    net.minecraft.commands.CommandDispatcher.a("noon")
                                       .executes(commandcontext -> a((CommandListenerWrapper)commandcontext.getSource(), 6000))
                                 ))
                              .then(
                                 net.minecraft.commands.CommandDispatcher.a("night")
                                    .executes(commandcontext -> a((CommandListenerWrapper)commandcontext.getSource(), 13000))
                              ))
                           .then(
                              net.minecraft.commands.CommandDispatcher.a("midnight")
                                 .executes(commandcontext -> a((CommandListenerWrapper)commandcontext.getSource(), 18000))
                           ))
                        .then(
                           net.minecraft.commands.CommandDispatcher.a("time", ArgumentTime.a())
                              .executes(
                                 commandcontext -> a(
                                       (CommandListenerWrapper)commandcontext.getSource(), IntegerArgumentType.getInteger(commandcontext, "time")
                                    )
                              )
                        )
                  ))
               .then(
                  net.minecraft.commands.CommandDispatcher.a("add")
                     .then(
                        net.minecraft.commands.CommandDispatcher.a("time", ArgumentTime.a())
                           .executes(
                              commandcontext -> b((CommandListenerWrapper)commandcontext.getSource(), IntegerArgumentType.getInteger(commandcontext, "time"))
                           )
                     )
               ))
            .then(
               ((LiteralArgumentBuilder)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("query")
                        .then(
                           net.minecraft.commands.CommandDispatcher.a("daytime")
                              .executes(
                                 commandcontext -> c(
                                       (CommandListenerWrapper)commandcontext.getSource(), a(((CommandListenerWrapper)commandcontext.getSource()).e())
                                    )
                              )
                        ))
                     .then(
                        net.minecraft.commands.CommandDispatcher.a("gametime")
                           .executes(
                              commandcontext -> c(
                                    (CommandListenerWrapper)commandcontext.getSource(),
                                    (int)(((CommandListenerWrapper)commandcontext.getSource()).e().U() % 2147483647L)
                                 )
                           )
                     ))
                  .then(
                     net.minecraft.commands.CommandDispatcher.a("day")
                        .executes(
                           commandcontext -> c(
                                 (CommandListenerWrapper)commandcontext.getSource(),
                                 (int)(((CommandListenerWrapper)commandcontext.getSource()).e().V() / 24000L % 2147483647L)
                              )
                        )
                  )
            )
      );
   }

   private static int a(WorldServer worldserver) {
      return (int)(worldserver.V() % 24000L);
   }

   private static int c(CommandListenerWrapper commandlistenerwrapper, int i) {
      commandlistenerwrapper.a(IChatBaseComponent.a("commands.time.query", i), false);
      return i;
   }

   public static int a(CommandListenerWrapper commandlistenerwrapper, int i) {
      Iterator iterator = Iterators.singletonIterator(commandlistenerwrapper.e());

      while(iterator.hasNext()) {
         WorldServer worldserver = (WorldServer)iterator.next();
         TimeSkipEvent event = new TimeSkipEvent(worldserver.getWorld(), SkipReason.COMMAND, (long)i - worldserver.V());
         Bukkit.getPluginManager().callEvent(event);
         if (!event.isCancelled()) {
            worldserver.b(worldserver.V() + event.getSkipAmount());
         }
      }

      commandlistenerwrapper.a(IChatBaseComponent.a("commands.time.set", i), true);
      return a(commandlistenerwrapper.e());
   }

   public static int b(CommandListenerWrapper commandlistenerwrapper, int i) {
      Iterator iterator = Iterators.singletonIterator(commandlistenerwrapper.e());

      while(iterator.hasNext()) {
         WorldServer worldserver = (WorldServer)iterator.next();
         TimeSkipEvent event = new TimeSkipEvent(worldserver.getWorld(), SkipReason.COMMAND, (long)i);
         Bukkit.getPluginManager().callEvent(event);
         if (!event.isCancelled()) {
            worldserver.b(worldserver.V() + event.getSkipAmount());
         }
      }

      int j = a(commandlistenerwrapper.e());
      commandlistenerwrapper.a(IChatBaseComponent.a("commands.time.set", j), true);
      return j;
   }
}
