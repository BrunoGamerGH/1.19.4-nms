package net.minecraft.server.commands;

import com.google.common.base.Stopwatch;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import java.time.Duration;
import java.util.Optional;
import net.minecraft.EnumChatFormat;
import net.minecraft.SystemUtils;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.arguments.ResourceOrTagArgument;
import net.minecraft.commands.arguments.ResourceOrTagKeyArgument;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.ChatClickable;
import net.minecraft.network.chat.ChatComponentUtils;
import net.minecraft.network.chat.ChatHoverable;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.ai.village.poi.VillagePlace;
import net.minecraft.world.entity.ai.village.poi.VillagePlaceType;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.levelgen.structure.Structure;
import org.slf4j.Logger;

public class CommandLocate {
   private static final Logger a = LogUtils.getLogger();
   private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(
      var0 -> IChatBaseComponent.a("commands.locate.structure.not_found", var0)
   );
   private static final DynamicCommandExceptionType c = new DynamicCommandExceptionType(
      var0 -> IChatBaseComponent.a("commands.locate.structure.invalid", var0)
   );
   private static final DynamicCommandExceptionType d = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("commands.locate.biome.not_found", var0));
   private static final DynamicCommandExceptionType e = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("commands.locate.poi.not_found", var0));
   private static final int f = 100;
   private static final int g = 6400;
   private static final int h = 32;
   private static final int i = 64;
   private static final int j = 256;

   public static void a(CommandDispatcher<CommandListenerWrapper> var0, CommandBuildContext var1) {
      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                        "locate"
                     )
                     .requires(var0x -> var0x.c(2)))
                  .then(
                     net.minecraft.commands.CommandDispatcher.a("structure")
                        .then(
                           net.minecraft.commands.CommandDispatcher.a("structure", ResourceOrTagKeyArgument.a(Registries.ax))
                              .executes(
                                 var0x -> a((CommandListenerWrapper)var0x.getSource(), ResourceOrTagKeyArgument.a(var0x, "structure", Registries.ax, c))
                              )
                        )
                  ))
               .then(
                  net.minecraft.commands.CommandDispatcher.a("biome")
                     .then(
                        net.minecraft.commands.CommandDispatcher.a("biome", ResourceOrTagArgument.a(var1, Registries.an))
                           .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), ResourceOrTagArgument.a(var0x, "biome", Registries.an)))
                     )
               ))
            .then(
               net.minecraft.commands.CommandDispatcher.a("poi")
                  .then(
                     net.minecraft.commands.CommandDispatcher.a("poi", ResourceOrTagArgument.a(var1, Registries.R))
                        .executes(var0x -> b((CommandListenerWrapper)var0x.getSource(), ResourceOrTagArgument.a(var0x, "poi", Registries.R)))
                  )
            )
      );
   }

   private static Optional<? extends HolderSet.b<Structure>> a(ResourceOrTagKeyArgument.c<Structure> var0, IRegistry<Structure> var1) {
      return (Optional<? extends HolderSet.b<Structure>>)var0.a().map(var1x -> var1.b(var1x).map(var0xx -> HolderSet.a(var0xx)), var1::b);
   }

   private static int a(CommandListenerWrapper var0, ResourceOrTagKeyArgument.c<Structure> var1) throws CommandSyntaxException {
      IRegistry<Structure> var2 = var0.e().u_().d(Registries.ax);
      HolderSet<Structure> var3 = a(var1, var2).orElseThrow(() -> c.create(var1.b()));
      BlockPosition var4 = BlockPosition.a(var0.d());
      WorldServer var5 = var0.e();
      Stopwatch var6 = Stopwatch.createStarted(SystemUtils.b);
      Pair<BlockPosition, Holder<Structure>> var7 = var5.k().g().a(var5, var3, var4, 100, false);
      var6.stop();
      if (var7 == null) {
         throw b.create(var1.b());
      } else {
         return a(var0, var1, var4, var7, "commands.locate.structure.success", false, var6.elapsed());
      }
   }

   private static int a(CommandListenerWrapper var0, ResourceOrTagArgument.c<BiomeBase> var1) throws CommandSyntaxException {
      BlockPosition var2 = BlockPosition.a(var0.d());
      Stopwatch var3 = Stopwatch.createStarted(SystemUtils.b);
      Pair<BlockPosition, Holder<BiomeBase>> var4 = var0.e().a(var1, var2, 6400, 32, 64);
      var3.stop();
      if (var4 == null) {
         throw d.create(var1.b());
      } else {
         return a(var0, var1, var2, var4, "commands.locate.biome.success", true, var3.elapsed());
      }
   }

   private static int b(CommandListenerWrapper var0, ResourceOrTagArgument.c<VillagePlaceType> var1) throws CommandSyntaxException {
      BlockPosition var2 = BlockPosition.a(var0.d());
      WorldServer var3 = var0.e();
      Stopwatch var4 = Stopwatch.createStarted(SystemUtils.b);
      Optional<Pair<Holder<VillagePlaceType>, BlockPosition>> var5 = var3.w().e(var1, var2, 256, VillagePlace.Occupancy.c);
      var4.stop();
      if (var5.isEmpty()) {
         throw e.create(var1.b());
      } else {
         return a(var0, var1, var2, ((Pair)var5.get()).swap(), "commands.locate.poi.success", false, var4.elapsed());
      }
   }

   private static String a(Pair<BlockPosition, ? extends Holder<?>> var0) {
      return ((Holder)var0.getSecond()).e().map(var0x -> var0x.a().toString()).orElse("[unregistered]");
   }

   public static int a(
      CommandListenerWrapper var0,
      ResourceOrTagArgument.c<?> var1,
      BlockPosition var2,
      Pair<BlockPosition, ? extends Holder<?>> var3,
      String var4,
      boolean var5,
      Duration var6
   ) {
      String var7 = (String)var1.a().map(var1x -> var1.b(), var2x -> var1.b() + " (" + a(var3) + ")");
      return a(var0, var2, var3, var4, var5, var7, var6);
   }

   public static int a(
      CommandListenerWrapper var0,
      ResourceOrTagKeyArgument.c<?> var1,
      BlockPosition var2,
      Pair<BlockPosition, ? extends Holder<?>> var3,
      String var4,
      boolean var5,
      Duration var6
   ) {
      String var7 = (String)var1.a().map(var0x -> var0x.a().toString(), var1x -> "#" + var1x.b() + " (" + a(var3) + ")");
      return a(var0, var2, var3, var4, var5, var7, var6);
   }

   private static int a(
      CommandListenerWrapper var0, BlockPosition var1, Pair<BlockPosition, ? extends Holder<?>> var2, String var3, boolean var4, String var5, Duration var6
   ) {
      BlockPosition var7 = (BlockPosition)var2.getFirst();
      int var8 = var4 ? MathHelper.d(MathHelper.c((float)var1.j(var7))) : MathHelper.d(a(var1.u(), var1.w(), var7.u(), var7.w()));
      String var9 = var4 ? String.valueOf(var7.v()) : "~";
      IChatBaseComponent var10 = ChatComponentUtils.a((IChatBaseComponent)IChatBaseComponent.a("chat.coordinates", var7.u(), var9, var7.w()))
         .a(
            var2x -> var2x.a(EnumChatFormat.k)
                  .a(new ChatClickable(ChatClickable.EnumClickAction.d, "/tp @s " + var7.u() + " " + var9 + " " + var7.w()))
                  .a(new ChatHoverable(ChatHoverable.EnumHoverAction.a, IChatBaseComponent.c("chat.coordinates.tooltip")))
         );
      var0.a(IChatBaseComponent.a(var3, var5, var10, var8), false);
      a.info("Locating element " + var5 + " took " + var6.toMillis() + " ms");
      return var8;
   }

   private static float a(int var0, int var1, int var2, int var3) {
      int var4 = var2 - var0;
      int var5 = var3 - var1;
      return MathHelper.c((float)(var4 * var4 + var5 * var5));
   }
}
