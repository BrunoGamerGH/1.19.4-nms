package net.minecraft.world.level.dimension.end;

import com.google.common.collect.ImmutableList;
import java.util.List;
import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.boss.enderdragon.EntityEnderCrystal;
import net.minecraft.world.level.World;
import net.minecraft.world.level.levelgen.feature.WorldGenEnder;
import net.minecraft.world.level.levelgen.feature.WorldGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureEndSpikeConfiguration;

public enum EnumDragonRespawn {
   a {
      @Override
      public void a(WorldServer var0, EnderDragonBattle var1, List<EntityEnderCrystal> var2, int var3, BlockPosition var4) {
         BlockPosition var5 = new BlockPosition(0, 128, 0);

         for(EntityEnderCrystal var7 : var2) {
            var7.a(var5);
         }

         var1.a(b);
      }
   },
   b {
      @Override
      public void a(WorldServer var0, EnderDragonBattle var1, List<EntityEnderCrystal> var2, int var3, BlockPosition var4) {
         if (var3 < 100) {
            if (var3 == 0 || var3 == 50 || var3 == 51 || var3 == 52 || var3 >= 95) {
               var0.c(3001, new BlockPosition(0, 128, 0), 0);
            }
         } else {
            var1.a(c);
         }
      }
   },
   c {
      @Override
      public void a(WorldServer var0, EnderDragonBattle var1, List<EntityEnderCrystal> var2, int var3, BlockPosition var4) {
         int var5 = 40;
         boolean var6 = var3 % 40 == 0;
         boolean var7 = var3 % 40 == 39;
         if (var6 || var7) {
            List<WorldGenEnder.Spike> var8 = WorldGenEnder.a(var0);
            int var9 = var3 / 40;
            if (var9 < var8.size()) {
               WorldGenEnder.Spike var10 = var8.get(var9);
               if (var6) {
                  for(EntityEnderCrystal var12 : var2) {
                     var12.a(new BlockPosition(var10.a(), var10.d() + 1, var10.b()));
                  }
               } else {
                  int var11 = 10;

                  for(BlockPosition var13 : BlockPosition.a(
                     new BlockPosition(var10.a() - 10, var10.d() - 10, var10.b() - 10), new BlockPosition(var10.a() + 10, var10.d() + 10, var10.b() + 10)
                  )) {
                     var0.a(var13, false);
                  }

                  var0.a(null, (double)((float)var10.a() + 0.5F), (double)var10.d(), (double)((float)var10.b() + 0.5F), 5.0F, World.a.b);
                  WorldGenFeatureEndSpikeConfiguration var12 = new WorldGenFeatureEndSpikeConfiguration(
                     true, ImmutableList.of(var10), new BlockPosition(0, 128, 0)
                  );
                  WorldGenerator.K.a(var12, var0, var0.k().g(), RandomSource.a(), new BlockPosition(var10.a(), 45, var10.b()));
               }
            } else if (var6) {
               var1.a(d);
            }
         }
      }
   },
   d {
      @Override
      public void a(WorldServer var0, EnderDragonBattle var1, List<EntityEnderCrystal> var2, int var3, BlockPosition var4) {
         if (var3 >= 100) {
            var1.a(e);
            var1.f();

            for(EntityEnderCrystal var6 : var2) {
               var6.a(null);
               var0.a(var6, var6.dl(), var6.dn(), var6.dr(), 6.0F, World.a.a);
               var6.ai();
            }
         } else if (var3 >= 80) {
            var0.c(3001, new BlockPosition(0, 128, 0), 0);
         } else if (var3 == 0) {
            for(EntityEnderCrystal var6 : var2) {
               var6.a(new BlockPosition(0, 128, 0));
            }
         } else if (var3 < 5) {
            var0.c(3001, new BlockPosition(0, 128, 0), 0);
         }
      }
   },
   e {
      @Override
      public void a(WorldServer var0, EnderDragonBattle var1, List<EntityEnderCrystal> var2, int var3, BlockPosition var4) {
      }
   };

   public abstract void a(WorldServer var1, EnderDragonBattle var2, List<EntityEnderCrystal> var3, int var4, BlockPosition var5);
}
