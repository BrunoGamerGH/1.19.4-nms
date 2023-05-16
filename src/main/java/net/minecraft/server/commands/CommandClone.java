package net.minecraft.server.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Deque;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.arguments.ArgumentDimension;
import net.minecraft.commands.arguments.blocks.ArgumentBlockPredicate;
import net.minecraft.commands.arguments.coordinates.ArgumentPosition;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.Clearable;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.pattern.ShapeDetectorBlock;
import net.minecraft.world.level.levelgen.structure.StructureBoundingBox;

public class CommandClone {
   private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.clone.overlap"));
   private static final Dynamic2CommandExceptionType c = new Dynamic2CommandExceptionType(
      (var0, var1) -> IChatBaseComponent.a("commands.clone.toobig", var0, var1)
   );
   private static final SimpleCommandExceptionType d = new SimpleCommandExceptionType(IChatBaseComponent.c("commands.clone.failed"));
   public static final Predicate<ShapeDetectorBlock> a = var0 -> !var0.a().h();

   public static void a(CommandDispatcher<CommandListenerWrapper> var0, CommandBuildContext var1) {
      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("clone")
                  .requires(var0x -> var0x.c(2)))
               .then(a(var1, var0x -> ((CommandListenerWrapper)var0x.getSource()).e())))
            .then(
               net.minecraft.commands.CommandDispatcher.a("from")
                  .then(
                     net.minecraft.commands.CommandDispatcher.a("sourceDimension", ArgumentDimension.a())
                        .then(a(var1, var0x -> ArgumentDimension.a(var0x, "sourceDimension")))
                  )
            )
      );
   }

   private static ArgumentBuilder<CommandListenerWrapper, ?> a(
      CommandBuildContext var0, CommandClone.b<CommandContext<CommandListenerWrapper>, WorldServer> var1
   ) {
      return net.minecraft.commands.CommandDispatcher.a("begin", ArgumentPosition.a())
         .then(
            ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("end", ArgumentPosition.a())
                  .then(a(var0, var1, var0x -> ((CommandListenerWrapper)var0x.getSource()).e())))
               .then(
                  net.minecraft.commands.CommandDispatcher.a("to")
                     .then(
                        net.minecraft.commands.CommandDispatcher.a("targetDimension", ArgumentDimension.a())
                           .then(a(var0, var1, var0x -> ArgumentDimension.a(var0x, "targetDimension")))
                     )
               )
         );
   }

   private static CommandClone.c a(CommandContext<CommandListenerWrapper> var0, WorldServer var1, String var2) throws CommandSyntaxException {
      BlockPosition var3 = ArgumentPosition.a(var0, var1, var2);
      return new CommandClone.c(var1, var3);
   }

   private static ArgumentBuilder<CommandListenerWrapper, ?> a(
      CommandBuildContext var0,
      CommandClone.b<CommandContext<CommandListenerWrapper>, WorldServer> var1,
      CommandClone.b<CommandContext<CommandListenerWrapper>, WorldServer> var2
   ) {
      CommandClone.b<CommandContext<CommandListenerWrapper>, CommandClone.c> var3 = var1x -> a(var1x, var1.apply(var1x), "begin");
      CommandClone.b<CommandContext<CommandListenerWrapper>, CommandClone.c> var4 = var1x -> a(var1x, var1.apply(var1x), "end");
      CommandClone.b<CommandContext<CommandListenerWrapper>, CommandClone.c> var5 = var1x -> a(var1x, var2.apply(var1x), "destination");
      return ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                     "destination", ArgumentPosition.a()
                  )
                  .executes(
                     var3x -> a(
                           (CommandListenerWrapper)var3x.getSource(),
                           var3.apply(var3x),
                           var4.apply(var3x),
                           var5.apply(var3x),
                           var0xx -> true,
                           CommandClone.Mode.c
                        )
                  ))
               .then(
                  a(
                     var3,
                     var4,
                     var5,
                     var0x -> var0xx -> true,
                     net.minecraft.commands.CommandDispatcher.a("replace")
                        .executes(
                           var3x -> a(
                                 (CommandListenerWrapper)var3x.getSource(),
                                 var3.apply(var3x),
                                 var4.apply(var3x),
                                 var5.apply(var3x),
                                 var0xx -> true,
                                 CommandClone.Mode.c
                              )
                        )
                  )
               ))
            .then(
               a(
                  var3,
                  var4,
                  var5,
                  var0x -> a,
                  net.minecraft.commands.CommandDispatcher.a("masked")
                     .executes(
                        var3x -> a((CommandListenerWrapper)var3x.getSource(), var3.apply(var3x), var4.apply(var3x), var5.apply(var3x), a, CommandClone.Mode.c)
                     )
               )
            ))
         .then(
            net.minecraft.commands.CommandDispatcher.a("filtered")
               .then(
                  a(
                     var3,
                     var4,
                     var5,
                     var0x -> ArgumentBlockPredicate.a(var0x, "filter"),
                     net.minecraft.commands.CommandDispatcher.a("filter", ArgumentBlockPredicate.a(var0))
                        .executes(
                           var3x -> a(
                                 (CommandListenerWrapper)var3x.getSource(),
                                 var3.apply(var3x),
                                 var4.apply(var3x),
                                 var5.apply(var3x),
                                 ArgumentBlockPredicate.a(var3x, "filter"),
                                 CommandClone.Mode.c
                              )
                        )
                  )
               )
         );
   }

   private static ArgumentBuilder<CommandListenerWrapper, ?> a(
      CommandClone.b<CommandContext<CommandListenerWrapper>, CommandClone.c> var0,
      CommandClone.b<CommandContext<CommandListenerWrapper>, CommandClone.c> var1,
      CommandClone.b<CommandContext<CommandListenerWrapper>, CommandClone.c> var2,
      CommandClone.b<CommandContext<CommandListenerWrapper>, Predicate<ShapeDetectorBlock>> var3,
      ArgumentBuilder<CommandListenerWrapper, ?> var4
   ) {
      return var4.then(
            net.minecraft.commands.CommandDispatcher.a("force")
               .executes(
                  var4x -> a(
                        (CommandListenerWrapper)var4x.getSource(),
                        var0.apply(var4x),
                        var1.apply(var4x),
                        var2.apply(var4x),
                        var3.apply(var4x),
                        CommandClone.Mode.a
                     )
               )
         )
         .then(
            net.minecraft.commands.CommandDispatcher.a("move")
               .executes(
                  var4x -> a(
                        (CommandListenerWrapper)var4x.getSource(),
                        var0.apply(var4x),
                        var1.apply(var4x),
                        var2.apply(var4x),
                        var3.apply(var4x),
                        CommandClone.Mode.b
                     )
               )
         )
         .then(
            net.minecraft.commands.CommandDispatcher.a("normal")
               .executes(
                  var4x -> a(
                        (CommandListenerWrapper)var4x.getSource(),
                        var0.apply(var4x),
                        var1.apply(var4x),
                        var2.apply(var4x),
                        var3.apply(var4x),
                        CommandClone.Mode.c
                     )
               )
         );
   }

   private static int a(
      CommandListenerWrapper var0, CommandClone.c var1, CommandClone.c var2, CommandClone.c var3, Predicate<ShapeDetectorBlock> var4, CommandClone.Mode var5
   ) throws CommandSyntaxException {
      BlockPosition var6 = var1.b();
      BlockPosition var7 = var2.b();
      StructureBoundingBox var8 = StructureBoundingBox.a(var6, var7);
      BlockPosition var9 = var3.b();
      BlockPosition var10 = var9.a(var8.b());
      StructureBoundingBox var11 = StructureBoundingBox.a(var9, var10);
      WorldServer var12 = var1.a();
      WorldServer var13 = var3.a();
      if (!var5.a() && var12 == var13 && var11.a(var8)) {
         throw b.create();
      } else {
         int var14 = var8.c() * var8.d() * var8.e();
         int var15 = var0.e().W().c(GameRules.x);
         if (var14 > var15) {
            throw c.create(var15, var14);
         } else if (var12.a(var6, var7) && var13.a(var9, var10)) {
            List<CommandClone.CommandCloneStoredTileEntity> var16 = Lists.newArrayList();
            List<CommandClone.CommandCloneStoredTileEntity> var17 = Lists.newArrayList();
            List<CommandClone.CommandCloneStoredTileEntity> var18 = Lists.newArrayList();
            Deque<BlockPosition> var19 = Lists.newLinkedList();
            BlockPosition var20 = new BlockPosition(var11.g() - var8.g(), var11.h() - var8.h(), var11.i() - var8.i());

            for(int var21 = var8.i(); var21 <= var8.l(); ++var21) {
               for(int var22 = var8.h(); var22 <= var8.k(); ++var22) {
                  for(int var23 = var8.g(); var23 <= var8.j(); ++var23) {
                     BlockPosition var24 = new BlockPosition(var23, var22, var21);
                     BlockPosition var25 = var24.a(var20);
                     ShapeDetectorBlock var26 = new ShapeDetectorBlock(var12, var24, false);
                     IBlockData var27 = var26.a();
                     if (var4.test(var26)) {
                        TileEntity var28 = var12.c_(var24);
                        if (var28 != null) {
                           NBTTagCompound var29 = var28.o();
                           var17.add(new CommandClone.CommandCloneStoredTileEntity(var25, var27, var29));
                           var19.addLast(var24);
                        } else if (!var27.i(var12, var24) && !var27.r(var12, var24)) {
                           var18.add(new CommandClone.CommandCloneStoredTileEntity(var25, var27, null));
                           var19.addFirst(var24);
                        } else {
                           var16.add(new CommandClone.CommandCloneStoredTileEntity(var25, var27, null));
                           var19.addLast(var24);
                        }
                     }
                  }
               }
            }

            if (var5 == CommandClone.Mode.b) {
               for(BlockPosition var22 : var19) {
                  TileEntity var23 = var12.c_(var22);
                  Clearable.a_(var23);
                  var12.a(var22, Blocks.hV.o(), 2);
               }

               for(BlockPosition var22 : var19) {
                  var12.a(var22, Blocks.a.o(), 3);
               }
            }

            List<CommandClone.CommandCloneStoredTileEntity> var21 = Lists.newArrayList();
            var21.addAll(var16);
            var21.addAll(var17);
            var21.addAll(var18);
            List<CommandClone.CommandCloneStoredTileEntity> var22 = Lists.reverse(var21);

            for(CommandClone.CommandCloneStoredTileEntity var24 : var22) {
               TileEntity var25 = var13.c_(var24.a);
               Clearable.a_(var25);
               var13.a(var24.a, Blocks.hV.o(), 2);
            }

            int var23 = 0;

            for(CommandClone.CommandCloneStoredTileEntity var25 : var21) {
               if (var13.a(var25.a, var25.b, 2)) {
                  ++var23;
               }
            }

            for(CommandClone.CommandCloneStoredTileEntity var25 : var17) {
               TileEntity var26 = var13.c_(var25.a);
               if (var25.c != null && var26 != null) {
                  var26.a(var25.c);
                  var26.e();
               }

               var13.a(var25.a, var25.b, 2);
            }

            for(CommandClone.CommandCloneStoredTileEntity var25 : var22) {
               var13.b(var25.a, var25.b.b());
            }

            var13.l().a(var12.l(), var8, var20);
            if (var23 == 0) {
               throw d.create();
            } else {
               var0.a(IChatBaseComponent.a("commands.clone.success", var23), true);
               return var23;
            }
         } else {
            throw ArgumentPosition.a.create();
         }
      }
   }

   static class CommandCloneStoredTileEntity {
      public final BlockPosition a;
      public final IBlockData b;
      @Nullable
      public final NBTTagCompound c;

      public CommandCloneStoredTileEntity(BlockPosition var0, IBlockData var1, @Nullable NBTTagCompound var2) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
      }
   }

   static enum Mode {
      a(true),
      b(true),
      c(false);

      private final boolean d;

      private Mode(boolean var2) {
         this.d = var2;
      }

      public boolean a() {
         return this.d;
      }
   }

   @FunctionalInterface
   interface b<T, R> {
      R apply(T var1) throws CommandSyntaxException;
   }

   static record c(WorldServer dimension, BlockPosition position) {
      private final WorldServer a;
      private final BlockPosition b;

      c(WorldServer var0, BlockPosition var1) {
         this.a = var0;
         this.b = var1;
      }
   }
}
