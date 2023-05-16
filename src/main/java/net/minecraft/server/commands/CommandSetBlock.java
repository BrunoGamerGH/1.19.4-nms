package net.minecraft.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.arguments.blocks.ArgumentTile;
import net.minecraft.commands.arguments.blocks.ArgumentTileLocation;
import net.minecraft.commands.arguments.coordinates.ArgumentPosition;
import net.minecraft.core.BlockPosition;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.Clearable;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.state.pattern.ShapeDetectorBlock;
import net.minecraft.world.level.levelgen.structure.StructureBoundingBox;

public class CommandSetBlock {
   private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.setblock.failed"));

   public static void a(CommandDispatcher<CommandListenerWrapper> var0, CommandBuildContext var1) {
      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("setblock")
               .requires(var0x -> var0x.c(2)))
            .then(
               net.minecraft.commands.CommandDispatcher.a("pos", ArgumentPosition.a())
                  .then(
                     ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                                    "block", ArgumentTile.a(var1)
                                 )
                                 .executes(
                                    var0x -> a(
                                          (CommandListenerWrapper)var0x.getSource(),
                                          ArgumentPosition.a(var0x, "pos"),
                                          ArgumentTile.a(var0x, "block"),
                                          CommandSetBlock.Mode.a,
                                          null
                                       )
                                 ))
                              .then(
                                 net.minecraft.commands.CommandDispatcher.a("destroy")
                                    .executes(
                                       var0x -> a(
                                             (CommandListenerWrapper)var0x.getSource(),
                                             ArgumentPosition.a(var0x, "pos"),
                                             ArgumentTile.a(var0x, "block"),
                                             CommandSetBlock.Mode.b,
                                             null
                                          )
                                    )
                              ))
                           .then(
                              net.minecraft.commands.CommandDispatcher.a("keep")
                                 .executes(
                                    var0x -> a(
                                          (CommandListenerWrapper)var0x.getSource(),
                                          ArgumentPosition.a(var0x, "pos"),
                                          ArgumentTile.a(var0x, "block"),
                                          CommandSetBlock.Mode.a,
                                          var0xx -> var0xx.c().w(var0xx.d())
                                       )
                                 )
                           ))
                        .then(
                           net.minecraft.commands.CommandDispatcher.a("replace")
                              .executes(
                                 var0x -> a(
                                       (CommandListenerWrapper)var0x.getSource(),
                                       ArgumentPosition.a(var0x, "pos"),
                                       ArgumentTile.a(var0x, "block"),
                                       CommandSetBlock.Mode.a,
                                       null
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int a(
      CommandListenerWrapper var0, BlockPosition var1, ArgumentTileLocation var2, CommandSetBlock.Mode var3, @Nullable Predicate<ShapeDetectorBlock> var4
   ) throws CommandSyntaxException {
      WorldServer var5 = var0.e();
      if (var4 != null && !var4.test(new ShapeDetectorBlock(var5, var1, true))) {
         throw a.create();
      } else {
         boolean var6;
         if (var3 == CommandSetBlock.Mode.b) {
            var5.b(var1, true);
            var6 = !var2.a().h() || !var5.a_(var1).h();
         } else {
            TileEntity var7 = var5.c_(var1);
            Clearable.a_(var7);
            var6 = true;
         }

         if (var6 && !var2.a(var5, var1, 2)) {
            throw a.create();
         } else {
            var5.b(var1, var2.a().b());
            var0.a(IChatBaseComponent.a("commands.setblock.success", var1.u(), var1.v(), var1.w()), true);
            return 1;
         }
      }
   }

   public interface Filter {
      @Nullable
      ArgumentTileLocation filter(StructureBoundingBox var1, BlockPosition var2, ArgumentTileLocation var3, WorldServer var4);
   }

   public static enum Mode {
      a,
      b;
   }
}
