package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.network.chat.ChatComponentUtils;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.entity.player.EntityHuman;
import org.bukkit.entity.Player;

public class CommandList {
   public static void a(CommandDispatcher<CommandListenerWrapper> commanddispatcher) {
      commanddispatcher.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("list")
               .executes(commandcontext -> a((CommandListenerWrapper)commandcontext.getSource())))
            .then(net.minecraft.commands.CommandDispatcher.a("uuids").executes(commandcontext -> b((CommandListenerWrapper)commandcontext.getSource())))
      );
   }

   private static int a(CommandListenerWrapper commandlistenerwrapper) {
      return a(commandlistenerwrapper, EntityHuman::G_);
   }

   private static int b(CommandListenerWrapper commandlistenerwrapper) {
      return a(commandlistenerwrapper, entityplayer -> IChatBaseComponent.a("commands.list.nameAndId", entityplayer.Z(), entityplayer.fI().getId()));
   }

   private static int a(CommandListenerWrapper commandlistenerwrapper, Function<EntityPlayer, IChatBaseComponent> function) {
      PlayerList playerlist = commandlistenerwrapper.l().ac();
      List<EntityPlayer> list = playerlist.t();
      if (commandlistenerwrapper.getBukkitSender() instanceof Player sender) {
         list = list.stream().filter(ep -> sender.canSee(ep.getBukkitEntity())).collect(Collectors.toList());
      }

      IChatBaseComponent ichatbasecomponent = ChatComponentUtils.b(list, function);
      commandlistenerwrapper.a(IChatBaseComponent.a("commands.list.players", list.size(), playerlist.n(), ichatbasecomponent), false);
      return list.size();
   }
}
