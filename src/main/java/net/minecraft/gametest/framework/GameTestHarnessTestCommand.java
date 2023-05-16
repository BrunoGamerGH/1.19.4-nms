package net.minecraft.gametest.framework;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.EnumChatFormat;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.arguments.blocks.ArgumentTileLocation;
import net.minecraft.core.BaseBlockPosition;
import net.minecraft.core.BlockPosition;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.structures.DebugReportNBT;
import net.minecraft.nbt.GameProfileSerializer;
import net.minecraft.nbt.NBTCompressedStreamTools;
import net.minecraft.network.chat.ChatClickable;
import net.minecraft.network.chat.ChatHoverable;
import net.minecraft.network.chat.ChatModifier;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.game.PacketDebug;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EnumBlockRotation;
import net.minecraft.world.level.block.entity.TileEntityStructure;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import org.apache.commons.io.IOUtils;

public class GameTestHarnessTestCommand {
   private static final int a = 200;
   private static final int b = 1024;
   private static final int c = 15;
   private static final int d = 200;
   private static final int e = 3;
   private static final int f = 10000;
   private static final int g = 5;
   private static final int h = 5;
   private static final int i = 5;

   public static void a(CommandDispatcher<CommandListenerWrapper> var0) {
      var0.register(
         (LiteralArgumentBuilder<CommandListenerWrapper>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                                             "test"
                                          )
                                          .then(
                                             net.minecraft.commands.CommandDispatcher.a("runthis")
                                                .executes(var0x -> a((CommandListenerWrapper)var0x.getSource()))
                                          ))
                                       .then(
                                          net.minecraft.commands.CommandDispatcher.a("runthese")
                                             .executes(var0x -> b((CommandListenerWrapper)var0x.getSource()))
                                       ))
                                    .then(
                                       ((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("runfailed")
                                             .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), false, 0, 8)))
                                          .then(
                                             ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("onlyRequiredTests", BoolArgumentType.bool())
                                                   .executes(
                                                      var0x -> a(
                                                            (CommandListenerWrapper)var0x.getSource(),
                                                            BoolArgumentType.getBool(var0x, "onlyRequiredTests"),
                                                            0,
                                                            8
                                                         )
                                                   ))
                                                .then(
                                                   ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                                                            "rotationSteps", IntegerArgumentType.integer()
                                                         )
                                                         .executes(
                                                            var0x -> a(
                                                                  (CommandListenerWrapper)var0x.getSource(),
                                                                  BoolArgumentType.getBool(var0x, "onlyRequiredTests"),
                                                                  IntegerArgumentType.getInteger(var0x, "rotationSteps"),
                                                                  8
                                                               )
                                                         ))
                                                      .then(
                                                         net.minecraft.commands.CommandDispatcher.a("testsPerRow", IntegerArgumentType.integer())
                                                            .executes(
                                                               var0x -> a(
                                                                     (CommandListenerWrapper)var0x.getSource(),
                                                                     BoolArgumentType.getBool(var0x, "onlyRequiredTests"),
                                                                     IntegerArgumentType.getInteger(var0x, "rotationSteps"),
                                                                     IntegerArgumentType.getInteger(var0x, "testsPerRow")
                                                                  )
                                                            )
                                                      )
                                                )
                                          )
                                    ))
                                 .then(
                                    net.minecraft.commands.CommandDispatcher.a("run")
                                       .then(
                                          ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                                                   "testName", GameTestHarnessTestFunctionArgument.a()
                                                )
                                                .executes(
                                                   var0x -> a(
                                                         (CommandListenerWrapper)var0x.getSource(),
                                                         GameTestHarnessTestFunctionArgument.a(var0x, "testName"),
                                                         0
                                                      )
                                                ))
                                             .then(
                                                net.minecraft.commands.CommandDispatcher.a("rotationSteps", IntegerArgumentType.integer())
                                                   .executes(
                                                      var0x -> a(
                                                            (CommandListenerWrapper)var0x.getSource(),
                                                            GameTestHarnessTestFunctionArgument.a(var0x, "testName"),
                                                            IntegerArgumentType.getInteger(var0x, "rotationSteps")
                                                         )
                                                   )
                                             )
                                       )
                                 ))
                              .then(
                                 ((LiteralArgumentBuilder)((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("runall")
                                          .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), 0, 8)))
                                       .then(
                                          ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                                                   "testClassName", GameTestHarnessTestClassArgument.a()
                                                )
                                                .executes(
                                                   var0x -> a(
                                                         (CommandListenerWrapper)var0x.getSource(),
                                                         GameTestHarnessTestClassArgument.a(var0x, "testClassName"),
                                                         0,
                                                         8
                                                      )
                                                ))
                                             .then(
                                                ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a(
                                                         "rotationSteps", IntegerArgumentType.integer()
                                                      )
                                                      .executes(
                                                         var0x -> a(
                                                               (CommandListenerWrapper)var0x.getSource(),
                                                               GameTestHarnessTestClassArgument.a(var0x, "testClassName"),
                                                               IntegerArgumentType.getInteger(var0x, "rotationSteps"),
                                                               8
                                                            )
                                                      ))
                                                   .then(
                                                      net.minecraft.commands.CommandDispatcher.a("testsPerRow", IntegerArgumentType.integer())
                                                         .executes(
                                                            var0x -> a(
                                                                  (CommandListenerWrapper)var0x.getSource(),
                                                                  GameTestHarnessTestClassArgument.a(var0x, "testClassName"),
                                                                  IntegerArgumentType.getInteger(var0x, "rotationSteps"),
                                                                  IntegerArgumentType.getInteger(var0x, "testsPerRow")
                                                               )
                                                         )
                                                   )
                                             )
                                       ))
                                    .then(
                                       ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("rotationSteps", IntegerArgumentType.integer())
                                             .executes(
                                                var0x -> a(
                                                      (CommandListenerWrapper)var0x.getSource(), IntegerArgumentType.getInteger(var0x, "rotationSteps"), 8
                                                   )
                                             ))
                                          .then(
                                             net.minecraft.commands.CommandDispatcher.a("testsPerRow", IntegerArgumentType.integer())
                                                .executes(
                                                   var0x -> a(
                                                         (CommandListenerWrapper)var0x.getSource(),
                                                         IntegerArgumentType.getInteger(var0x, "rotationSteps"),
                                                         IntegerArgumentType.getInteger(var0x, "testsPerRow")
                                                      )
                                                )
                                          )
                                    )
                              ))
                           .then(
                              net.minecraft.commands.CommandDispatcher.a("export")
                                 .then(
                                    net.minecraft.commands.CommandDispatcher.a("testName", StringArgumentType.word())
                                       .executes(var0x -> c((CommandListenerWrapper)var0x.getSource(), StringArgumentType.getString(var0x, "testName")))
                                 )
                           ))
                        .then(net.minecraft.commands.CommandDispatcher.a("exportthis").executes(var0x -> c((CommandListenerWrapper)var0x.getSource()))))
                     .then(
                        net.minecraft.commands.CommandDispatcher.a("import")
                           .then(
                              net.minecraft.commands.CommandDispatcher.a("testName", StringArgumentType.word())
                                 .executes(var0x -> d((CommandListenerWrapper)var0x.getSource(), StringArgumentType.getString(var0x, "testName")))
                           )
                     ))
                  .then(
                     ((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("pos")
                           .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), "pos")))
                        .then(
                           net.minecraft.commands.CommandDispatcher.a("var", StringArgumentType.word())
                              .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), StringArgumentType.getString(var0x, "var")))
                        )
                  ))
               .then(
                  net.minecraft.commands.CommandDispatcher.a("create")
                     .then(
                        ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("testName", StringArgumentType.word())
                              .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), StringArgumentType.getString(var0x, "testName"), 5, 5, 5)))
                           .then(
                              ((RequiredArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("width", IntegerArgumentType.integer())
                                    .executes(
                                       var0x -> a(
                                             (CommandListenerWrapper)var0x.getSource(),
                                             StringArgumentType.getString(var0x, "testName"),
                                             IntegerArgumentType.getInteger(var0x, "width"),
                                             IntegerArgumentType.getInteger(var0x, "width"),
                                             IntegerArgumentType.getInteger(var0x, "width")
                                          )
                                    ))
                                 .then(
                                    net.minecraft.commands.CommandDispatcher.a("height", IntegerArgumentType.integer())
                                       .then(
                                          net.minecraft.commands.CommandDispatcher.a("depth", IntegerArgumentType.integer())
                                             .executes(
                                                var0x -> a(
                                                      (CommandListenerWrapper)var0x.getSource(),
                                                      StringArgumentType.getString(var0x, "testName"),
                                                      IntegerArgumentType.getInteger(var0x, "width"),
                                                      IntegerArgumentType.getInteger(var0x, "height"),
                                                      IntegerArgumentType.getInteger(var0x, "depth")
                                                   )
                                             )
                                       )
                                 )
                           )
                     )
               ))
            .then(
               ((LiteralArgumentBuilder)net.minecraft.commands.CommandDispatcher.a("clearall")
                     .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), 200)))
                  .then(
                     net.minecraft.commands.CommandDispatcher.a("radius", IntegerArgumentType.integer())
                        .executes(var0x -> a((CommandListenerWrapper)var0x.getSource(), IntegerArgumentType.getInteger(var0x, "radius")))
                  )
            )
      );
   }

   private static int a(CommandListenerWrapper var0, String var1, int var2, int var3, int var4) {
      if (var2 <= 48 && var3 <= 48 && var4 <= 48) {
         WorldServer var5 = var0.e();
         BlockPosition var6 = BlockPosition.a(var0.d());
         BlockPosition var7 = new BlockPosition(var6.u(), var0.e().a(HeightMap.Type.b, var6).v(), var6.w() + 3);
         GameTestHarnessStructures.a(var1.toLowerCase(), var7, new BaseBlockPosition(var2, var3, var4), EnumBlockRotation.a, var5);

         for(int var8 = 0; var8 < var2; ++var8) {
            for(int var9 = 0; var9 < var4; ++var9) {
               BlockPosition var10 = new BlockPosition(var7.u() + var8, var7.v() + 1, var7.w() + var9);
               Block var11 = Blocks.h;
               ArgumentTileLocation var12 = new ArgumentTileLocation(var11.o(), Collections.emptySet(), null);
               var12.a(var5, var10, 2);
            }
         }

         GameTestHarnessStructures.a(var7, new BlockPosition(1, 0, -1), EnumBlockRotation.a, var5);
         return 0;
      } else {
         throw new IllegalArgumentException("The structure must be less than 48 blocks big in each axis");
      }
   }

   private static int a(CommandListenerWrapper var0, String var1) throws CommandSyntaxException {
      MovingObjectPositionBlock var2 = (MovingObjectPositionBlock)var0.h().a(10.0, 1.0F, false);
      BlockPosition var3 = var2.a();
      WorldServer var4 = var0.e();
      Optional<BlockPosition> var5 = GameTestHarnessStructures.a(var3, 15, var4);
      if (!var5.isPresent()) {
         var5 = GameTestHarnessStructures.a(var3, 200, var4);
      }

      if (!var5.isPresent()) {
         var0.b(IChatBaseComponent.b("Can't find a structure block that contains the targeted pos " + var3));
         return 0;
      } else {
         TileEntityStructure var6 = (TileEntityStructure)var4.c_(var5.get());
         BlockPosition var7 = var3.b(var5.get());
         String var8 = var7.u() + ", " + var7.v() + ", " + var7.w();
         String var9 = var6.f();
         IChatBaseComponent var10 = IChatBaseComponent.b(var8)
            .b(
               ChatModifier.a
                  .a(true)
                  .a(EnumChatFormat.k)
                  .a(new ChatHoverable(ChatHoverable.EnumHoverAction.a, IChatBaseComponent.b("Click to copy to clipboard")))
                  .a(new ChatClickable(ChatClickable.EnumClickAction.f, "final BlockPos " + var1 + " = new BlockPos(" + var8 + ");"))
            );
         var0.a(IChatBaseComponent.b("Position relative to " + var9 + ": ").b(var10), false);
         PacketDebug.a(var4, new BlockPosition(var3), var8, -2147418368, 10000);
         return 1;
      }
   }

   private static int a(CommandListenerWrapper var0) {
      BlockPosition var1 = BlockPosition.a(var0.d());
      WorldServer var2 = var0.e();
      BlockPosition var3 = GameTestHarnessStructures.b(var1, 15, var2);
      if (var3 == null) {
         a(var2, "Couldn't find any structure block within 15 radius", EnumChatFormat.m);
         return 0;
      } else {
         GameTestHarnessRunner.a(var2);
         a(var2, var3, null);
         return 1;
      }
   }

   private static int b(CommandListenerWrapper var0) {
      BlockPosition var1 = BlockPosition.a(var0.d());
      WorldServer var2 = var0.e();
      Collection<BlockPosition> var3 = GameTestHarnessStructures.c(var1, 200, var2);
      if (var3.isEmpty()) {
         a(var2, "Couldn't find any structure blocks within 200 block radius", EnumChatFormat.m);
         return 1;
      } else {
         GameTestHarnessRunner.a(var2);
         b(var0, "Running " + var3.size() + " tests...");
         GameTestHarnessCollector var4 = new GameTestHarnessCollector();
         var3.forEach(var2x -> a(var2, var2x, var4));
         return 1;
      }
   }

   private static void a(WorldServer var0, BlockPosition var1, @Nullable GameTestHarnessCollector var2) {
      TileEntityStructure var3 = (TileEntityStructure)var0.c_(var1);
      String var4 = var3.f();
      GameTestHarnessTestFunction var5 = GameTestHarnessRegistry.f(var4);
      GameTestHarnessInfo var6 = new GameTestHarnessInfo(var5, var3.w(), var0);
      if (var2 != null) {
         var2.a(var6);
         var6.a(new GameTestHarnessTestCommand.a(var0, var2));
      }

      a(var5, var0);
      AxisAlignedBB var7 = GameTestHarnessStructures.a(var3);
      BlockPosition var8 = BlockPosition.a(var7.a, var7.b, var7.c);
      GameTestHarnessRunner.a(var6, var8, GameTestHarnessTicker.a);
   }

   static void a(WorldServer var0, GameTestHarnessCollector var1) {
      if (var1.i()) {
         a(var0, "GameTest done! " + var1.h() + " tests were run", EnumChatFormat.p);
         if (var1.d()) {
            a(var0, var1.a() + " required tests failed :(", EnumChatFormat.m);
         } else {
            a(var0, "All required tests passed :)", EnumChatFormat.k);
         }

         if (var1.e()) {
            a(var0, var1.b() + " optional tests failed", EnumChatFormat.h);
         }
      }
   }

   private static int a(CommandListenerWrapper var0, int var1) {
      WorldServer var2 = var0.e();
      GameTestHarnessRunner.a(var2);
      BlockPosition var3 = BlockPosition.a(var0.d().c, (double)var0.e().a(HeightMap.Type.b, BlockPosition.a(var0.d())).v(), var0.d().e);
      GameTestHarnessRunner.a(var2, var3, GameTestHarnessTicker.a, MathHelper.a(var1, 0, 1024));
      return 1;
   }

   private static int a(CommandListenerWrapper var0, GameTestHarnessTestFunction var1, int var2) {
      WorldServer var3 = var0.e();
      BlockPosition var4 = BlockPosition.a(var0.d());
      int var5 = var0.e().a(HeightMap.Type.b, var4).v();
      BlockPosition var6 = new BlockPosition(var4.u(), var5, var4.w() + 3);
      GameTestHarnessRunner.a(var3);
      a(var1, var3);
      EnumBlockRotation var7 = GameTestHarnessStructures.a(var2);
      GameTestHarnessInfo var8 = new GameTestHarnessInfo(var1, var7, var3);
      GameTestHarnessRunner.a(var8, var6, GameTestHarnessTicker.a);
      return 1;
   }

   private static void a(GameTestHarnessTestFunction var0, WorldServer var1) {
      Consumer<WorldServer> var2 = GameTestHarnessRegistry.c(var0.e());
      if (var2 != null) {
         var2.accept(var1);
      }
   }

   private static int a(CommandListenerWrapper var0, int var1, int var2) {
      GameTestHarnessRunner.a(var0.e());
      Collection<GameTestHarnessTestFunction> var3 = GameTestHarnessRegistry.a();
      b(var0, "Running all " + var3.size() + " tests...");
      GameTestHarnessRegistry.d();
      a(var0, var3, var1, var2);
      return 1;
   }

   private static int a(CommandListenerWrapper var0, String var1, int var2, int var3) {
      Collection<GameTestHarnessTestFunction> var4 = GameTestHarnessRegistry.a(var1);
      GameTestHarnessRunner.a(var0.e());
      b(var0, "Running " + var4.size() + " tests from " + var1 + "...");
      GameTestHarnessRegistry.d();
      a(var0, var4, var2, var3);
      return 1;
   }

   private static int a(CommandListenerWrapper var0, boolean var1, int var2, int var3) {
      Collection<GameTestHarnessTestFunction> var4;
      if (var1) {
         var4 = GameTestHarnessRegistry.c().stream().filter(GameTestHarnessTestFunction::d).collect(Collectors.toList());
      } else {
         var4 = GameTestHarnessRegistry.c();
      }

      if (var4.isEmpty()) {
         b(var0, "No failed tests to rerun");
         return 0;
      } else {
         GameTestHarnessRunner.a(var0.e());
         b(var0, "Rerunning " + var4.size() + " failed tests (" + (var1 ? "only required tests" : "including optional tests") + ")");
         a(var0, var4, var2, var3);
         return 1;
      }
   }

   private static void a(CommandListenerWrapper var0, Collection<GameTestHarnessTestFunction> var1, int var2, int var3) {
      BlockPosition var4 = BlockPosition.a(var0.d());
      BlockPosition var5 = new BlockPosition(var4.u(), var0.e().a(HeightMap.Type.b, var4).v(), var4.w() + 3);
      WorldServer var6 = var0.e();
      EnumBlockRotation var7 = GameTestHarnessStructures.a(var2);
      Collection<GameTestHarnessInfo> var8 = GameTestHarnessRunner.b(var1, var5, var7, var6, GameTestHarnessTicker.a, var3);
      GameTestHarnessCollector var9 = new GameTestHarnessCollector(var8);
      var9.a(new GameTestHarnessTestCommand.a(var6, var9));
      var9.a(var0x -> GameTestHarnessRegistry.a(var0x.v()));
   }

   private static void b(CommandListenerWrapper var0, String var1) {
      var0.a(IChatBaseComponent.b(var1), false);
   }

   private static int c(CommandListenerWrapper var0) {
      BlockPosition var1 = BlockPosition.a(var0.d());
      WorldServer var2 = var0.e();
      BlockPosition var3 = GameTestHarnessStructures.b(var1, 15, var2);
      if (var3 == null) {
         a(var2, "Couldn't find any structure block within 15 radius", EnumChatFormat.m);
         return 0;
      } else {
         TileEntityStructure var4 = (TileEntityStructure)var2.c_(var3);
         String var5 = var4.f();
         return c(var0, var5);
      }
   }

   private static int c(CommandListenerWrapper var0, String var1) {
      Path var2 = Paths.get(GameTestHarnessStructures.b);
      MinecraftKey var3 = new MinecraftKey("minecraft", var1);
      Path var4 = var0.e().p().a(var3, ".nbt");
      Path var5 = DebugReportNBT.a(CachedOutput.a, var4, var1, var2);
      if (var5 == null) {
         b(var0, "Failed to export " + var4);
         return 1;
      } else {
         try {
            Files.createDirectories(var5.getParent());
         } catch (IOException var7) {
            b(var0, "Could not create folder " + var5.getParent());
            var7.printStackTrace();
            return 1;
         }

         b(var0, "Exported " + var1 + " to " + var5.toAbsolutePath());
         return 0;
      }
   }

   private static int d(CommandListenerWrapper var0, String var1) {
      Path var2 = Paths.get(GameTestHarnessStructures.b, var1 + ".snbt");
      MinecraftKey var3 = new MinecraftKey("minecraft", var1);
      Path var4 = var0.e().p().a(var3, ".nbt");

      try {
         BufferedReader var5 = Files.newBufferedReader(var2);
         String var6 = IOUtils.toString(var5);
         Files.createDirectories(var4.getParent());

         try (OutputStream var7 = Files.newOutputStream(var4)) {
            NBTCompressedStreamTools.a(GameProfileSerializer.a(var6), var7);
         }

         b(var0, "Imported to " + var4.toAbsolutePath());
         return 0;
      } catch (CommandSyntaxException | IOException var12) {
         System.err.println("Failed to load structure " + var1);
         var12.printStackTrace();
         return 1;
      }
   }

   private static void a(WorldServer var0, String var1, EnumChatFormat var2) {
      var0.a(var0x -> true).forEach(var2x -> var2x.a(IChatBaseComponent.b(var2 + var1)));
   }

   static class a implements GameTestHarnessListener {
      private final WorldServer a;
      private final GameTestHarnessCollector b;

      public a(WorldServer var0, GameTestHarnessCollector var1) {
         this.a = var0;
         this.b = var1;
      }

      @Override
      public void a(GameTestHarnessInfo var0) {
      }

      @Override
      public void b(GameTestHarnessInfo var0) {
         GameTestHarnessTestCommand.a(this.a, this.b);
      }

      @Override
      public void c(GameTestHarnessInfo var0) {
         GameTestHarnessTestCommand.a(this.a, this.b);
      }
   }
}
