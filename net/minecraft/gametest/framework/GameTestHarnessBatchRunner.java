package net.minecraft.gametest.framework;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.level.block.EnumBlockRotation;
import net.minecraft.world.level.block.entity.TileEntityStructure;
import net.minecraft.world.phys.AxisAlignedBB;
import org.slf4j.Logger;

public class GameTestHarnessBatchRunner {
   private static final Logger a = LogUtils.getLogger();
   private final BlockPosition b;
   final WorldServer c;
   private final GameTestHarnessTicker d;
   private final int e;
   private final List<GameTestHarnessInfo> f;
   private final List<Pair<GameTestHarnessBatch, Collection<GameTestHarnessInfo>>> g;
   private final BlockPosition.MutableBlockPosition h;

   public GameTestHarnessBatchRunner(
      Collection<GameTestHarnessBatch> var0, BlockPosition var1, EnumBlockRotation var2, WorldServer var3, GameTestHarnessTicker var4, int var5
   ) {
      this.h = var1.j();
      this.b = var1;
      this.c = var3;
      this.d = var4;
      this.e = var5;
      this.g = var0.stream()
         .map(
            var2x -> {
               Collection<GameTestHarnessInfo> var3x = var2x.b()
                  .stream()
                  .map(var2xx -> new GameTestHarnessInfo(var2xx, var2, var3))
                  .collect(ImmutableList.toImmutableList());
               return Pair.of(var2x, var3x);
            }
         )
         .collect(ImmutableList.toImmutableList());
      this.f = this.g.stream().flatMap(var0x -> ((Collection)var0x.getSecond()).stream()).collect(ImmutableList.toImmutableList());
   }

   public List<GameTestHarnessInfo> a() {
      return this.f;
   }

   public void b() {
      this.a(0);
   }

   void a(final int var0) {
      if (var0 < this.g.size()) {
         Pair<GameTestHarnessBatch, Collection<GameTestHarnessInfo>> var1 = (Pair)this.g.get(var0);
         final GameTestHarnessBatch var2 = (GameTestHarnessBatch)var1.getFirst();
         Collection<GameTestHarnessInfo> var3 = (Collection)var1.getSecond();
         Map<GameTestHarnessInfo, BlockPosition> var4 = this.a(var3);
         String var5 = var2.a();
         a.info("Running test batch '{}' ({} tests)...", var5, var3.size());
         var2.a(this.c);
         final GameTestHarnessCollector var6 = new GameTestHarnessCollector();
         var3.forEach(var6::a);
         var6.a(new GameTestHarnessListener() {
            private void a() {
               if (var6.i()) {
                  var2.b(GameTestHarnessBatchRunner.this.c);
                  GameTestHarnessBatchRunner.this.a(var0 + 1);
               }
            }

            @Override
            public void a(GameTestHarnessInfo var0x) {
            }

            @Override
            public void b(GameTestHarnessInfo var0x) {
               this.a();
            }

            @Override
            public void c(GameTestHarnessInfo var0x) {
               this.a();
            }
         });
         var3.forEach(var1x -> {
            BlockPosition var2x = var4.get(var1x);
            GameTestHarnessRunner.a(var1x, var2x, this.d);
         });
      }
   }

   private Map<GameTestHarnessInfo, BlockPosition> a(Collection<GameTestHarnessInfo> var0) {
      Map<GameTestHarnessInfo, BlockPosition> var1 = Maps.newHashMap();
      int var2 = 0;
      AxisAlignedBB var3 = new AxisAlignedBB(this.h);

      for(GameTestHarnessInfo var5 : var0) {
         BlockPosition var6 = new BlockPosition(this.h);
         TileEntityStructure var7 = GameTestHarnessStructures.a(var5.t(), var6, var5.u(), 2, this.c, true);
         AxisAlignedBB var8 = GameTestHarnessStructures.a(var7);
         var5.a(var7.p());
         var1.put(var5, new BlockPosition(this.h));
         var3 = var3.b(var8);
         this.h.e((int)var8.b() + 5, 0, 0);
         if (var2++ % this.e == this.e - 1) {
            this.h.e(0, 0, (int)var3.d() + 6);
            this.h.p(this.b.u());
            var3 = new AxisAlignedBB(this.h);
         }
      }

      return var1;
   }
}
