package net.minecraft.server.commands;

import com.google.common.base.Joiner;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.arguments.coordinates.ArgumentPosition;
import net.minecraft.commands.arguments.coordinates.ArgumentVec2I;
import net.minecraft.core.SectionPosition;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.BlockPosition2D;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.World;

public class CommandForceload {
   private static final int a = 256;
   private static final Dynamic2CommandExceptionType b = new Dynamic2CommandExceptionType(
      (var0, var1) -> IChatBaseComponent.a("commands.forceload.toobig", var0, var1)
   );
   private static final Dynamic2CommandExceptionType c = new Dynamic2CommandExceptionType(
      (var0, var1) -> IChatBaseComponent.a("commands.forceload.query.failure", var0, var1)
   );
   private static final SimpleCommandExceptionType d = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.forceload.added.failure"));
   private static final SimpleCommandExceptionType e = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.forceload.removed.failure"));

   public static void a(CommandDispatcher<CommandListenerWrapper> var0) {
      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                        "forceload"
                     )
                     .requires(var0x -> var0x.c(2)))
                  .then(
                     net.minecraft.commands.CommandDispatcher.a("add")
                        .then(
                           ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("from", ArgumentVec2I.a())
                                 .executes(
                                    var0x -> a((CommandListenerWrapper)var0x.getSource(), ArgumentVec2I.a(var0x, "from"), ArgumentVec2I.a(var0x, "from"), true)
                                 ))
                              .then(
                                 net.minecraft.commands.CommandDispatcher.a("to", ArgumentVec2I.a())
                                    .executes(
                                       var0x -> a(
                                             (CommandListenerWrapper)var0x.getSource(), ArgumentVec2I.a(var0x, "from"), ArgumentVec2I.a(var0x, "to"), true
                                          )
                                    )
                              )
                        )
                  ))
               .then(
                  ((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("remove")
                        .then(
                           ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("from", ArgumentVec2I.a())
                                 .executes(
                                    var0x -> a(
                                          (CommandListenerWrapper)var0x.getSource(), ArgumentVec2I.a(var0x, "from"), ArgumentVec2I.a(var0x, "from"), false
                                       )
                                 ))
                              .then(
                                 net.minecraft.commands.CommandDispatcher.a("to", ArgumentVec2I.a())
                                    .executes(
                                       var0x -> a(
                                             (CommandListenerWrapper)var0x.getSource(), ArgumentVec2I.a(var0x, "from"), ArgumentVec2I.a(var0x, "to"), false
                                          )
                                    )
                              )
                        ))
                     .then(net.minecraft.commands.CommandDispatcher.a("all").executes(var0x -> b((CommandListenerWrapper)var0x.getSource())))
               ))
            .then(
               ((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("query").executes(var0x -> a((CommandListenerWrapper)var0x.getSource())))
                  .then(
                     net.minecraft.commands.CommandDispatcher.a("pos", ArgumentVec2I.a())
                        .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), ArgumentVec2I.a(var0x, "pos")))
                  )
            )
      );
   }

   private static int a(CommandListenerWrapper var0, BlockPosition2D var1) throws CommandSyntaxException {
      ChunkCoordIntPair var2 = var1.a();
      WorldServer var3 = var0.e();
      ResourceKey<World> var4 = var3.ab();
      boolean var5 = var3.u().contains(var2.a());
      if (var5) {
         var0.a(IChatBaseComponent.a("commands.forceload.query.success", var2, var4.a()), false);
         return 1;
      } else {
         throw c.create(var2, var4.a());
      }
   }

   private static int a(CommandListenerWrapper var0) {
      WorldServer var1 = var0.e();
      ResourceKey<World> var2 = var1.ab();
      LongSet var3 = var1.u();
      int var4 = var3.size();
      if (var4 > 0) {
         String var5 = Joiner.on(", ").join(var3.stream().sorted().map(ChunkCoordIntPair::new).map(ChunkCoordIntPair::toString).iterator());
         if (var4 == 1) {
            var0.a(IChatBaseComponent.a("commands.forceload.list.single", var2.a(), var5), false);
         } else {
            var0.a(IChatBaseComponent.a("commands.forceload.list.multiple", var4, var2.a(), var5), false);
         }
      } else {
         var0.b(IChatBaseComponent.a("commands.forceload.added.none", var2.a()));
      }

      return var4;
   }

   private static int b(CommandListenerWrapper var0) {
      WorldServer var1 = var0.e();
      ResourceKey<World> var2 = var1.ab();
      LongSet var3 = var1.u();
      var3.forEach(var1x -> var1.a(ChunkCoordIntPair.a(var1x), ChunkCoordIntPair.b(var1x), false));
      var0.a(IChatBaseComponent.a("commands.forceload.removed.all", var2.a()), true);
      return 0;
   }

   private static int a(CommandListenerWrapper var0, BlockPosition2D var1, BlockPosition2D var2, boolean var3) throws CommandSyntaxException {
      int var4 = Math.min(var1.c(), var2.c());
      int var5 = Math.min(var1.d(), var2.d());
      int var6 = Math.max(var1.c(), var2.c());
      int var7 = Math.max(var1.d(), var2.d());
      if (var4 >= -30000000 && var5 >= -30000000 && var6 < 30000000 && var7 < 30000000) {
         int var8 = SectionPosition.a(var4);
         int var9 = SectionPosition.a(var5);
         int var10 = SectionPosition.a(var6);
         int var11 = SectionPosition.a(var7);
         long var12 = ((long)(var10 - var8) + 1L) * ((long)(var11 - var9) + 1L);
         if (var12 > 256L) {
            throw b.create(256, var12);
         } else {
            WorldServer var14 = var0.e();
            ResourceKey<World> var15 = var14.ab();
            ChunkCoordIntPair var16 = null;
            int var17 = 0;

            for(int var18 = var8; var18 <= var10; ++var18) {
               for(int var19 = var9; var19 <= var11; ++var19) {
                  boolean var20 = var14.a(var18, var19, var3);
                  if (var20) {
                     ++var17;
                     if (var16 == null) {
                        var16 = new ChunkCoordIntPair(var18, var19);
                     }
                  }
               }
            }

            if (var17 == 0) {
               throw (var3 ? d : e).create();
            } else {
               if (var17 == 1) {
                  var0.a(IChatBaseComponent.a("commands.forceload." + (var3 ? "added" : "removed") + ".single", var16, var15.a()), true);
               } else {
                  ChunkCoordIntPair var18 = new ChunkCoordIntPair(var8, var9);
                  ChunkCoordIntPair var19 = new ChunkCoordIntPair(var10, var11);
                  var0.a(IChatBaseComponent.a("commands.forceload." + (var3 ? "added" : "removed") + ".multiple", var17, var15.a(), var18, var19), true);
               }

               return var17;
            }
         }
      } else {
         throw ArgumentPosition.b.create();
      }
   }
}
