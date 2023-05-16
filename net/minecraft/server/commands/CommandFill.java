package net.minecraft.server.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.arguments.blocks.ArgumentBlockPredicate;
import net.minecraft.commands.arguments.blocks.ArgumentTile;
import net.minecraft.commands.arguments.blocks.ArgumentTileLocation;
import net.minecraft.commands.arguments.coordinates.ArgumentPosition;
import net.minecraft.core.BlockPosition;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.Clearable;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.state.pattern.ShapeDetectorBlock;
import net.minecraft.world.level.levelgen.structure.StructureBoundingBox;

public class CommandFill {
   private static final Dynamic2CommandExceptionType a = new Dynamic2CommandExceptionType(
      (var0, var1) -> IChatBaseComponent.a("commands.fill.toobig", var0, var1)
   );
   static final ArgumentTileLocation b = new ArgumentTileLocation(Blocks.a.o(), Collections.emptySet(), null);
   private static final SimpleCommandExceptionType c = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.fill.failed"));

   public static void a(CommandDispatcher<CommandListenerWrapper> var0, CommandBuildContext var1) {
      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("fill")
               .requires(var0x -> var0x.c(2)))
            .then(
               net.minecraft.commands.CommandDispatcher.a("from", ArgumentPosition.a())
                  .then(
                     net.minecraft.commands.CommandDispatcher.a("to", ArgumentPosition.a())
                        .then(
                           ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                                                "block", ArgumentTile.a(var1)
                                             )
                                             .executes(
                                                var0x -> a(
                                                      (CommandListenerWrapper)var0x.getSource(),
                                                      StructureBoundingBox.a(ArgumentPosition.a(var0x, "from"), ArgumentPosition.a(var0x, "to")),
                                                      ArgumentTile.a(var0x, "block"),
                                                      CommandFill.Mode.a,
                                                      null
                                                   )
                                             ))
                                          .then(
                                             ((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("replace")
                                                   .executes(
                                                      var0x -> a(
                                                            (CommandListenerWrapper)var0x.getSource(),
                                                            StructureBoundingBox.a(ArgumentPosition.a(var0x, "from"), ArgumentPosition.a(var0x, "to")),
                                                            ArgumentTile.a(var0x, "block"),
                                                            CommandFill.Mode.a,
                                                            null
                                                         )
                                                   ))
                                                .then(
                                                   net.minecraft.commands.CommandDispatcher.a("filter", ArgumentBlockPredicate.a(var1))
                                                      .executes(
                                                         var0x -> a(
                                                               (CommandListenerWrapper)var0x.getSource(),
                                                               StructureBoundingBox.a(ArgumentPosition.a(var0x, "from"), ArgumentPosition.a(var0x, "to")),
                                                               ArgumentTile.a(var0x, "block"),
                                                               CommandFill.Mode.a,
                                                               ArgumentBlockPredicate.a(var0x, "filter")
                                                            )
                                                      )
                                                )
                                          ))
                                       .then(
                                          net.minecraft.commands.CommandDispatcher.a("keep")
                                             .executes(
                                                var0x -> a(
                                                      (CommandListenerWrapper)var0x.getSource(),
                                                      StructureBoundingBox.a(ArgumentPosition.a(var0x, "from"), ArgumentPosition.a(var0x, "to")),
                                                      ArgumentTile.a(var0x, "block"),
                                                      CommandFill.Mode.a,
                                                      var0xx -> var0xx.c().w(var0xx.d())
                                                   )
                                             )
                                       ))
                                    .then(
                                       net.minecraft.commands.CommandDispatcher.a("outline")
                                          .executes(
                                             var0x -> a(
                                                   (CommandListenerWrapper)var0x.getSource(),
                                                   StructureBoundingBox.a(ArgumentPosition.a(var0x, "from"), ArgumentPosition.a(var0x, "to")),
                                                   ArgumentTile.a(var0x, "block"),
                                                   CommandFill.Mode.b,
                                                   null
                                                )
                                          )
                                    ))
                                 .then(
                                    net.minecraft.commands.CommandDispatcher.a("hollow")
                                       .executes(
                                          var0x -> a(
                                                (CommandListenerWrapper)var0x.getSource(),
                                                StructureBoundingBox.a(ArgumentPosition.a(var0x, "from"), ArgumentPosition.a(var0x, "to")),
                                                ArgumentTile.a(var0x, "block"),
                                                CommandFill.Mode.c,
                                                null
                                             )
                                       )
                                 ))
                              .then(
                                 net.minecraft.commands.CommandDispatcher.a("destroy")
                                    .executes(
                                       var0x -> a(
                                             (CommandListenerWrapper)var0x.getSource(),
                                             StructureBoundingBox.a(ArgumentPosition.a(var0x, "from"), ArgumentPosition.a(var0x, "to")),
                                             ArgumentTile.a(var0x, "block"),
                                             CommandFill.Mode.d,
                                             null
                                          )
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int a(
      CommandListenerWrapper var0, StructureBoundingBox var1, ArgumentTileLocation var2, CommandFill.Mode var3, @Nullable Predicate<ShapeDetectorBlock> var4
   ) throws CommandSyntaxException {
      int var5 = var1.c() * var1.d() * var1.e();
      int var6 = var0.e().W().c(GameRules.x);
      if (var5 > var6) {
         throw a.create(var6, var5);
      } else {
         List<BlockPosition> var7 = Lists.newArrayList();
         WorldServer var8 = var0.e();
         int var9 = 0;

         for(BlockPosition var11 : BlockPosition.b(var1.g(), var1.h(), var1.i(), var1.j(), var1.k(), var1.l())) {
            if (var4 == null || var4.test(new ShapeDetectorBlock(var8, var11, true))) {
               ArgumentTileLocation var12 = var3.e.filter(var1, var11, var2, var8);
               if (var12 != null) {
                  TileEntity var13 = var8.c_(var11);
                  Clearable.a_(var13);
                  if (var12.a(var8, var11, 2)) {
                     var7.add(var11.i());
                     ++var9;
                  }
               }
            }
         }

         for(BlockPosition var11 : var7) {
            Block var12 = var8.a_(var11).b();
            var8.b(var11, var12);
         }

         if (var9 == 0) {
            throw c.create();
         } else {
            var0.a(IChatBaseComponent.a("commands.fill.success", var9), true);
            return var9;
         }
      }
   }

   static enum Mode {
      a((var0, var1, var2, var3) -> var2),
      b(
         (var0, var1, var2, var3) -> var1.u() != var0.g()
                  && var1.u() != var0.j()
                  && var1.v() != var0.h()
                  && var1.v() != var0.k()
                  && var1.w() != var0.i()
                  && var1.w() != var0.l()
               ? null
               : var2
      ),
      c(
         (var0, var1, var2, var3) -> var1.u() != var0.g()
                  && var1.u() != var0.j()
                  && var1.v() != var0.h()
                  && var1.v() != var0.k()
                  && var1.w() != var0.i()
                  && var1.w() != var0.l()
               ? CommandFill.b
               : var2
      ),
      d((var0, var1, var2, var3) -> {
         var3.b(var1, true);
         return var2;
      });

      public final CommandSetBlock.Filter e;

      private Mode(CommandSetBlock.Filter var2) {
         this.e = var2;
      }
   }
}
