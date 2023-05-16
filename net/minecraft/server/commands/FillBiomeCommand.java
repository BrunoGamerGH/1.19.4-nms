package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.arguments.ResourceArgument;
import net.minecraft.commands.arguments.ResourceOrTagArgument;
import net.minecraft.commands.arguments.coordinates.ArgumentPosition;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.core.SectionPosition;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.biome.BiomeResolver;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.IChunkAccess;
import net.minecraft.world.level.levelgen.structure.StructureBoundingBox;
import org.apache.commons.lang3.mutable.MutableInt;

public class FillBiomeCommand {
   public static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(IChatBaseComponent.c("argument.pos.unloaded"));
   private static final Dynamic2CommandExceptionType b = new Dynamic2CommandExceptionType(
      (var0, var1) -> IChatBaseComponent.a("commands.fillbiome.toobig", var0, var1)
   );

   public static void a(CommandDispatcher<CommandListenerWrapper> var0, CommandBuildContext var1) {
      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("fillbiome")
               .requires(var0x -> var0x.c(2)))
            .then(
               net.minecraft.commands.CommandDispatcher.a("from", ArgumentPosition.a())
                  .then(
                     net.minecraft.commands.CommandDispatcher.a("to", ArgumentPosition.a())
                        .then(
                           ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("biome", ResourceArgument.a(var1, Registries.an))
                                 .executes(
                                    var0x -> a(
                                          (CommandListenerWrapper)var0x.getSource(),
                                          ArgumentPosition.a(var0x, "from"),
                                          ArgumentPosition.a(var0x, "to"),
                                          ResourceArgument.a(var0x, "biome", Registries.an),
                                          var0xx -> true
                                       )
                                 ))
                              .then(
                                 net.minecraft.commands.CommandDispatcher.a("replace")
                                    .then(
                                       net.minecraft.commands.CommandDispatcher.a("filter", ResourceOrTagArgument.a(var1, Registries.an))
                                          .executes(
                                             var0x -> a(
                                                   (CommandListenerWrapper)var0x.getSource(),
                                                   ArgumentPosition.a(var0x, "from"),
                                                   ArgumentPosition.a(var0x, "to"),
                                                   ResourceArgument.a(var0x, "biome", Registries.an),
                                                   ResourceOrTagArgument.a(var0x, "filter", Registries.an)::test
                                                )
                                          )
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int a(int var0) {
      return QuartPos.c(QuartPos.a(var0));
   }

   private static BlockPosition a(BlockPosition var0) {
      return new BlockPosition(a(var0.u()), a(var0.v()), a(var0.w()));
   }

   private static BiomeResolver a(MutableInt var0, IChunkAccess var1, StructureBoundingBox var2, Holder<BiomeBase> var3, Predicate<Holder<BiomeBase>> var4) {
      return (var5, var6, var7, var8) -> {
         int var9 = QuartPos.c(var5);
         int var10 = QuartPos.c(var6);
         int var11 = QuartPos.c(var7);
         Holder<BiomeBase> var12 = var1.getNoiseBiome(var5, var6, var7);
         if (var2.c(var9, var10, var11) && var4.test(var12)) {
            var0.increment();
            return var3;
         } else {
            return var12;
         }
      };
   }

   private static int a(CommandListenerWrapper var0, BlockPosition var1, BlockPosition var2, Holder.c<BiomeBase> var3, Predicate<Holder<BiomeBase>> var4) throws CommandSyntaxException {
      BlockPosition var5 = a(var1);
      BlockPosition var6 = a(var2);
      StructureBoundingBox var7 = StructureBoundingBox.a(var5, var6);
      int var8 = var7.c() * var7.d() * var7.e();
      int var9 = var0.e().W().c(GameRules.x);
      if (var8 > var9) {
         throw b.create(var9, var8);
      } else {
         WorldServer var10 = var0.e();
         List<IChunkAccess> var11 = new ArrayList<>();

         for(int var12 = SectionPosition.a(var7.i()); var12 <= SectionPosition.a(var7.l()); ++var12) {
            for(int var13 = SectionPosition.a(var7.g()); var13 <= SectionPosition.a(var7.j()); ++var13) {
               IChunkAccess var14 = var10.a(var13, var12, ChunkStatus.o, false);
               if (var14 == null) {
                  throw a.create();
               }

               var11.add(var14);
            }
         }

         MutableInt var12 = new MutableInt(0);

         for(IChunkAccess var14 : var11) {
            var14.a(a(var12, var14, var7, var3, var4), var10.k().i().b());
            var14.a(true);
         }

         var10.k().a.a(var11);
         var0.a(IChatBaseComponent.a("commands.fillbiome.success.count", var12.getValue(), var7.g(), var7.h(), var7.i(), var7.j(), var7.k(), var7.l()), true);
         return var12.getValue();
      }
   }
}
