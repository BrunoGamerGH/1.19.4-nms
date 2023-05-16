package net.minecraft.gametest.framework;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Streams;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPosition;
import net.minecraft.network.protocol.game.PacketDebug;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EnumBlockRotation;
import net.minecraft.world.level.block.entity.TileEntityStructure;
import net.minecraft.world.level.levelgen.structure.StructureBoundingBox;
import org.apache.commons.lang3.mutable.MutableInt;

public class GameTestHarnessRunner {
   private static final int e = 100;
   public static final int a = 2;
   public static final int b = 5;
   public static final int c = 6;
   public static final int d = 8;

   public static void a(GameTestHarnessInfo var0, BlockPosition var1, GameTestHarnessTicker var2) {
      var0.a();
      var2.a(var0);
      var0.a(new ReportGameListener(var0, var2, var1));
      var0.a(var1, 2);
   }

   public static Collection<GameTestHarnessInfo> a(
      Collection<GameTestHarnessBatch> var0, BlockPosition var1, EnumBlockRotation var2, WorldServer var3, GameTestHarnessTicker var4, int var5
   ) {
      GameTestHarnessBatchRunner var6 = new GameTestHarnessBatchRunner(var0, var1, var2, var3, var4, var5);
      var6.b();
      return var6.a();
   }

   public static Collection<GameTestHarnessInfo> b(
      Collection<GameTestHarnessTestFunction> var0, BlockPosition var1, EnumBlockRotation var2, WorldServer var3, GameTestHarnessTicker var4, int var5
   ) {
      return a(a(var0), var1, var2, var3, var4, var5);
   }

   public static Collection<GameTestHarnessBatch> a(Collection<GameTestHarnessTestFunction> var0) {
      Map<String, List<GameTestHarnessTestFunction>> var1 = var0.stream().collect(Collectors.groupingBy(GameTestHarnessTestFunction::e));
      return var1.entrySet()
         .stream()
         .flatMap(
            var0x -> {
               String var1x = var0x.getKey();
               Consumer<WorldServer> var2 = GameTestHarnessRegistry.c(var1x);
               Consumer<WorldServer> var3 = GameTestHarnessRegistry.d(var1x);
               MutableInt var4 = new MutableInt();
               Collection<GameTestHarnessTestFunction> var5 = var0x.getValue();
               return Streams.stream(Iterables.partition(var5, 100))
                  .map(var4x -> new GameTestHarnessBatch(var1x + ":" + var4.incrementAndGet(), ImmutableList.copyOf(var4x), var2, var3));
            }
         )
         .collect(ImmutableList.toImmutableList());
   }

   public static void a(WorldServer var0, BlockPosition var1, GameTestHarnessTicker var2, int var3) {
      var2.a();
      BlockPosition var4 = var1.b(-var3, 0, -var3);
      BlockPosition var5 = var1.b(var3, 0, var3);
      BlockPosition.b(var4, var5).filter(var1x -> var0.a_(var1x).a(Blocks.oW)).forEach(var1x -> {
         TileEntityStructure var2x = (TileEntityStructure)var0.c_(var1x);
         BlockPosition var3x = var2x.p();
         StructureBoundingBox var4x = GameTestHarnessStructures.b(var2x);
         GameTestHarnessStructures.a(var4x, var3x.v(), var0);
      });
   }

   public static void a(WorldServer var0) {
      PacketDebug.a(var0);
   }
}
