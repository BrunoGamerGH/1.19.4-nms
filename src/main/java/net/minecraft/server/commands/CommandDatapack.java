package net.minecraft.server.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.ICompletionProvider;
import net.minecraft.network.chat.ChatComponentUtils;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.packs.repository.ResourcePackLoader;
import net.minecraft.server.packs.repository.ResourcePackRepository;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;

public class CommandDatapack {
   private static final DynamicCommandExceptionType a = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("commands.datapack.unknown", var0));
   private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("commands.datapack.enable.failed", var0));
   private static final DynamicCommandExceptionType c = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("commands.datapack.disable.failed", var0));
   private static final Dynamic2CommandExceptionType d = new Dynamic2CommandExceptionType(
      (var0, var1) -> IChatBaseComponent.a("commands.datapack.enable.failed.no_flags", var0, var1)
   );
   private static final SuggestionProvider<CommandListenerWrapper> e = (var0, var1) -> ICompletionProvider.b(
         ((CommandListenerWrapper)var0.getSource()).l().aB().d().stream().map(StringArgumentType::escapeIfRequired), var1
      );
   private static final SuggestionProvider<CommandListenerWrapper> f = (var0, var1) -> {
      ResourcePackRepository var2 = ((CommandListenerWrapper)var0.getSource()).l().aB();
      Collection<String> var3 = var2.d();
      FeatureFlagSet var4 = ((CommandListenerWrapper)var0.getSource()).v();
      return ICompletionProvider.b(
         var2.c()
            .stream()
            .filter(var1x -> var1x.d().a(var4))
            .map(ResourcePackLoader::f)
            .filter(var1x -> !var3.contains(var1x))
            .map(StringArgumentType::escapeIfRequired),
         var1
      );
   };

   public static void a(CommandDispatcher<CommandListenerWrapper> var0) {
      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                        "datapack"
                     )
                     .requires(var0x -> var0x.c(2)))
                  .then(
                     net.minecraft.commands.CommandDispatcher.a("enable")
                        .then(
                           ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                                             "name", StringArgumentType.string()
                                          )
                                          .suggests(f)
                                          .executes(
                                             var0x -> a(
                                                   (CommandListenerWrapper)var0x.getSource(),
                                                   a(var0x, "name", true),
                                                   (var0xx, var1) -> var1.i().a(var0xx, var1, var0xxx -> var0xxx, false)
                                                )
                                          ))
                                       .then(
                                          net.minecraft.commands.CommandDispatcher.a("after")
                                             .then(
                                                net.minecraft.commands.CommandDispatcher.a("existing", StringArgumentType.string())
                                                   .suggests(e)
                                                   .executes(
                                                      var0x -> a(
                                                            (CommandListenerWrapper)var0x.getSource(),
                                                            a(var0x, "name", true),
                                                            (var1, var2) -> var1.add(var1.indexOf(a(var0x, "existing", false)) + 1, var2)
                                                         )
                                                   )
                                             )
                                       ))
                                    .then(
                                       net.minecraft.commands.CommandDispatcher.a("before")
                                          .then(
                                             net.minecraft.commands.CommandDispatcher.a("existing", StringArgumentType.string())
                                                .suggests(e)
                                                .executes(
                                                   var0x -> a(
                                                         (CommandListenerWrapper)var0x.getSource(),
                                                         a(var0x, "name", true),
                                                         (var1, var2) -> var1.add(var1.indexOf(a(var0x, "existing", false)), var2)
                                                      )
                                                )
                                          )
                                    ))
                                 .then(
                                    net.minecraft.commands.CommandDispatcher.a("last")
                                       .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), a(var0x, "name", true), List::add))
                                 ))
                              .then(
                                 net.minecraft.commands.CommandDispatcher.a("first")
                                    .executes(
                                       var0x -> a((CommandListenerWrapper)var0x.getSource(), a(var0x, "name", true), (var0xx, var1) -> var0xx.add(0, var1))
                                    )
                              )
                        )
                  ))
               .then(
                  net.minecraft.commands.CommandDispatcher.a("disable")
                     .then(
                        net.minecraft.commands.CommandDispatcher.a("name", StringArgumentType.string())
                           .suggests(e)
                           .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), a(var0x, "name", false)))
                     )
               ))
            .then(
               ((LiteralArgumentBuilder)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("list")
                        .executes(var0x -> a((CommandListenerWrapper)var0x.getSource())))
                     .then(net.minecraft.commands.CommandDispatcher.a("available").executes(var0x -> b((CommandListenerWrapper)var0x.getSource()))))
                  .then(net.minecraft.commands.CommandDispatcher.a("enabled").executes(var0x -> c((CommandListenerWrapper)var0x.getSource())))
            )
      );
   }

   private static int a(CommandListenerWrapper var0, ResourcePackLoader var1, CommandDatapack.a var2) throws CommandSyntaxException {
      ResourcePackRepository var3 = var0.l().aB();
      List<ResourcePackLoader> var4 = Lists.newArrayList(var3.f());
      var2.apply(var4, var1);
      var0.a(IChatBaseComponent.a("commands.datapack.modify.enable", var1.a(true)), true);
      CommandReload.a(var4.stream().map(ResourcePackLoader::f).collect(Collectors.toList()), var0);
      return var4.size();
   }

   private static int a(CommandListenerWrapper var0, ResourcePackLoader var1) {
      ResourcePackRepository var2 = var0.l().aB();
      List<ResourcePackLoader> var3 = Lists.newArrayList(var2.f());
      var3.remove(var1);
      var0.a(IChatBaseComponent.a("commands.datapack.modify.disable", var1.a(true)), true);
      CommandReload.a(var3.stream().map(ResourcePackLoader::f).collect(Collectors.toList()), var0);
      return var3.size();
   }

   private static int a(CommandListenerWrapper var0) {
      return c(var0) + b(var0);
   }

   private static int b(CommandListenerWrapper var0) {
      ResourcePackRepository var1 = var0.l().aB();
      var1.a();
      Collection<ResourcePackLoader> var2 = var1.f();
      Collection<ResourcePackLoader> var3 = var1.c();
      FeatureFlagSet var4 = var0.v();
      List<ResourcePackLoader> var5 = var3.stream().filter(var2x -> !var2.contains(var2x) && var2x.d().a(var4)).toList();
      if (var5.isEmpty()) {
         var0.a(IChatBaseComponent.c("commands.datapack.list.available.none"), false);
      } else {
         var0.a(IChatBaseComponent.a("commands.datapack.list.available.success", var5.size(), ChatComponentUtils.b(var5, var0x -> var0x.a(false))), false);
      }

      return var5.size();
   }

   private static int c(CommandListenerWrapper var0) {
      ResourcePackRepository var1 = var0.l().aB();
      var1.a();
      Collection<? extends ResourcePackLoader> var2 = var1.f();
      if (var2.isEmpty()) {
         var0.a(IChatBaseComponent.c("commands.datapack.list.enabled.none"), false);
      } else {
         var0.a(IChatBaseComponent.a("commands.datapack.list.enabled.success", var2.size(), ChatComponentUtils.b(var2, var0x -> var0x.a(true))), false);
      }

      return var2.size();
   }

   private static ResourcePackLoader a(CommandContext<CommandListenerWrapper> var0, String var1, boolean var2) throws CommandSyntaxException {
      String var3 = StringArgumentType.getString(var0, var1);
      ResourcePackRepository var4 = ((CommandListenerWrapper)var0.getSource()).l().aB();
      ResourcePackLoader var5 = var4.c(var3);
      if (var5 == null) {
         throw a.create(var3);
      } else {
         boolean var6 = var4.f().contains(var5);
         if (var2 && var6) {
            throw b.create(var3);
         } else if (!var2 && !var6) {
            throw c.create(var3);
         } else {
            FeatureFlagSet var7 = ((CommandListenerWrapper)var0.getSource()).v();
            FeatureFlagSet var8 = var5.d();
            if (!var8.a(var7)) {
               throw d.create(var3, FeatureFlags.a(var7, var8));
            } else {
               return var5;
            }
         }
      }
   }

   interface a {
      void apply(List<ResourcePackLoader> var1, ResourcePackLoader var2) throws CommandSyntaxException;
   }
}
