package net.minecraft.server.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.logging.LogUtils;
import java.util.Collection;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.repository.ResourcePackRepository;
import net.minecraft.world.level.storage.SaveData;
import org.slf4j.Logger;

public class CommandReload {
   private static final Logger a = LogUtils.getLogger();

   public static void a(Collection<String> collection, CommandListenerWrapper commandlistenerwrapper) {
      commandlistenerwrapper.l().a(collection).exceptionally(throwable -> {
         a.warn("Failed to execute reload", throwable);
         commandlistenerwrapper.b(IChatBaseComponent.c("commands.reload.failure"));
         return null;
      });
   }

   private static Collection<String> a(ResourcePackRepository resourcepackrepository, SaveData savedata, Collection<String> collection) {
      resourcepackrepository.a();
      Collection<String> collection1 = Lists.newArrayList(collection);
      Collection<String> collection2 = savedata.F().a().b();

      for(String s : resourcepackrepository.b()) {
         if (!collection2.contains(s) && !collection1.contains(s)) {
            collection1.add(s);
         }
      }

      return collection1;
   }

   public static void reload(MinecraftServer minecraftserver) {
      ResourcePackRepository resourcepackrepository = minecraftserver.aB();
      SaveData savedata = minecraftserver.aW();
      Collection<String> collection = resourcepackrepository.d();
      Collection<String> collection1 = a(resourcepackrepository, savedata, collection);
      minecraftserver.a(collection1);
   }

   public static void a(CommandDispatcher<CommandListenerWrapper> commanddispatcher) {
      commanddispatcher.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("reload")
               .requires(commandlistenerwrapper -> commandlistenerwrapper.c(2)))
            .executes(commandcontext -> {
               CommandListenerWrapper commandlistenerwrapper = (CommandListenerWrapper)commandcontext.getSource();
               MinecraftServer minecraftserver = commandlistenerwrapper.l();
               ResourcePackRepository resourcepackrepository = minecraftserver.aB();
               SaveData savedata = minecraftserver.aW();
               Collection<String> collection = resourcepackrepository.d();
               Collection<String> collection1 = a(resourcepackrepository, savedata, collection);
               commandlistenerwrapper.a(IChatBaseComponent.c("commands.reload.success"), true);
               a(collection1, commandlistenerwrapper);
               return 0;
            })
      );
   }
}
