package net.minecraft.world.level.levelgen.structure.pools;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.mojang.logging.LogUtils;
import java.util.Deque;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.core.BaseBlockPosition;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.Holder;
import net.minecraft.core.IRegistry;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.WorldGenFeaturePieces;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.block.BlockJigsaw;
import net.minecraft.world.level.block.EnumBlockRotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.SeededRandom;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureBoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.WorldGenFeaturePillagerOutpostPoolPiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.shapes.OperatorBoolean;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapes;
import org.apache.commons.lang3.mutable.MutableObject;
import org.slf4j.Logger;

public class WorldGenFeatureDefinedStructureJigsawPlacement {
   static final Logger a = LogUtils.getLogger();

   public static Optional<Structure.b> a(
      Structure.a var0,
      Holder<WorldGenFeatureDefinedStructurePoolTemplate> var1,
      Optional<MinecraftKey> var2,
      int var3,
      BlockPosition var4,
      boolean var5,
      Optional<HeightMap.Type> var6,
      int var7
   ) {
      IRegistryCustom var8 = var0.a();
      ChunkGenerator var9 = var0.b();
      StructureTemplateManager var10 = var0.e();
      LevelHeightAccessor var11 = var0.i();
      SeededRandom var12 = var0.f();
      IRegistry<WorldGenFeatureDefinedStructurePoolTemplate> var13 = var8.d(Registries.aA);
      EnumBlockRotation var14 = EnumBlockRotation.a(var12);
      WorldGenFeatureDefinedStructurePoolTemplate var15 = var1.a();
      WorldGenFeatureDefinedStructurePoolStructure var16 = var15.a(var12);
      if (var16 == WorldGenFeatureDefinedStructurePoolEmpty.b) {
         return Optional.empty();
      } else {
         BlockPosition var17;
         if (var2.isPresent()) {
            MinecraftKey var18 = var2.get();
            Optional<BlockPosition> var19 = a(var16, var18, var4, var14, var10, var12);
            if (var19.isEmpty()) {
               a.error("No starting jigsaw {} found in start pool {}", var18, var1.e().map(var0x -> var0x.a().toString()).orElse("<unregistered>"));
               return Optional.empty();
            }

            var17 = var19.get();
         } else {
            var17 = var4;
         }

         BaseBlockPosition var18 = var17.b(var4);
         BlockPosition var19 = var4.b(var18);
         WorldGenFeaturePillagerOutpostPoolPiece var20 = new WorldGenFeaturePillagerOutpostPoolPiece(
            var10, var16, var19, var16.f(), var14, var16.a(var10, var19, var14)
         );
         StructureBoundingBox var21 = var20.f();
         int var22 = (var21.j() + var21.g()) / 2;
         int var23 = (var21.l() + var21.i()) / 2;
         int var24;
         if (var6.isPresent()) {
            var24 = var4.v() + var9.b(var22, var23, var6.get(), var11, var0.d());
         } else {
            var24 = var19.v();
         }

         int var25 = var21.h() + var20.d();
         var20.a(0, var24 - var25, 0);
         int var26 = var24 + var18.v();
         return Optional.of(
            new Structure.b(
               new BlockPosition(var22, var26, var23),
               (Consumer<StructurePiecesBuilder>)(var14x -> {
                  List<WorldGenFeaturePillagerOutpostPoolPiece> var15x = Lists.newArrayList();
                  var15x.add(var20);
                  if (var3 > 0) {
                     AxisAlignedBB var16x = new AxisAlignedBB(
                        (double)(var22 - var7),
                        (double)(var26 - var7),
                        (double)(var23 - var7),
                        (double)(var22 + var7 + 1),
                        (double)(var26 + var7 + 1),
                        (double)(var23 + var7 + 1)
                     );
                     VoxelShape var17x = VoxelShapes.a(VoxelShapes.a(var16x), VoxelShapes.a(AxisAlignedBB.a(var21)), OperatorBoolean.e);
                     a(var0.d(), var3, var5, var9, var10, var11, var12, var13, var20, var15x, var17x);
                     var15x.forEach(var14x::a);
                  }
               })
            )
         );
      }
   }

   private static Optional<BlockPosition> a(
      WorldGenFeatureDefinedStructurePoolStructure var0,
      MinecraftKey var1,
      BlockPosition var2,
      EnumBlockRotation var3,
      StructureTemplateManager var4,
      SeededRandom var5
   ) {
      List<DefinedStructure.BlockInfo> var6 = var0.a(var4, var2, var3, var5);
      Optional<BlockPosition> var7 = Optional.empty();

      for(DefinedStructure.BlockInfo var9 : var6) {
         MinecraftKey var10 = MinecraftKey.a(var9.c.l("name"));
         if (var1.equals(var10)) {
            var7 = Optional.of(var9.a);
            break;
         }
      }

      return var7;
   }

   private static void a(
      RandomState var0,
      int var1,
      boolean var2,
      ChunkGenerator var3,
      StructureTemplateManager var4,
      LevelHeightAccessor var5,
      RandomSource var6,
      IRegistry<WorldGenFeatureDefinedStructurePoolTemplate> var7,
      WorldGenFeaturePillagerOutpostPoolPiece var8,
      List<WorldGenFeaturePillagerOutpostPoolPiece> var9,
      VoxelShape var10
   ) {
      WorldGenFeatureDefinedStructureJigsawPlacement.b var11 = new WorldGenFeatureDefinedStructureJigsawPlacement.b(var7, var1, var3, var4, var9, var6);
      var11.g.addLast(new WorldGenFeatureDefinedStructureJigsawPlacement.a(var8, new MutableObject(var10), 0));

      while(!var11.g.isEmpty()) {
         WorldGenFeatureDefinedStructureJigsawPlacement.a var12 = var11.g.removeFirst();
         var11.a(var12.a, var12.b, var12.c, var2, var5, var0);
      }
   }

   public static boolean a(
      WorldServer var0, Holder<WorldGenFeatureDefinedStructurePoolTemplate> var1, MinecraftKey var2, int var3, BlockPosition var4, boolean var5
   ) {
      ChunkGenerator var6 = var0.k().g();
      StructureTemplateManager var7 = var0.p();
      StructureManager var8 = var0.a();
      RandomSource var9 = var0.r_();
      Structure.a var10 = new Structure.a(var0.u_(), var6, var6.c(), var0.k().i(), var7, var0.A(), new ChunkCoordIntPair(var4), var0, var0x -> true);
      Optional<Structure.b> var11 = a(var10, var1, Optional.of(var2), var3, var4, false, Optional.empty(), 128);
      if (var11.isPresent()) {
         StructurePiecesBuilder var12 = var11.get().a();

         for(StructurePiece var14 : var12.a().c()) {
            if (var14 instanceof WorldGenFeaturePillagerOutpostPoolPiece var15) {
               var15.a(var0, var8, var6, var9, StructureBoundingBox.a(), var4, var5);
            }
         }

         return true;
      } else {
         return false;
      }
   }

   static final class a {
      final WorldGenFeaturePillagerOutpostPoolPiece a;
      final MutableObject<VoxelShape> b;
      final int c;

      a(WorldGenFeaturePillagerOutpostPoolPiece var0, MutableObject<VoxelShape> var1, int var2) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
      }
   }

   static final class b {
      private final IRegistry<WorldGenFeatureDefinedStructurePoolTemplate> a;
      private final int b;
      private final ChunkGenerator c;
      private final StructureTemplateManager d;
      private final List<? super WorldGenFeaturePillagerOutpostPoolPiece> e;
      private final RandomSource f;
      final Deque<WorldGenFeatureDefinedStructureJigsawPlacement.a> g = Queues.newArrayDeque();

      b(
         IRegistry<WorldGenFeatureDefinedStructurePoolTemplate> var0,
         int var1,
         ChunkGenerator var2,
         StructureTemplateManager var3,
         List<? super WorldGenFeaturePillagerOutpostPoolPiece> var4,
         RandomSource var5
      ) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
         this.d = var3;
         this.e = var4;
         this.f = var5;
      }

      void a(WorldGenFeaturePillagerOutpostPoolPiece var0, MutableObject<VoxelShape> var1, int var2, boolean var3, LevelHeightAccessor var4, RandomState var5) {
         WorldGenFeatureDefinedStructurePoolStructure var6 = var0.b();
         BlockPosition var7 = var0.c();
         EnumBlockRotation var8 = var0.a();
         WorldGenFeatureDefinedStructurePoolTemplate.Matching var9 = var6.e();
         boolean var10 = var9 == WorldGenFeatureDefinedStructurePoolTemplate.Matching.b;
         MutableObject<VoxelShape> var11 = new MutableObject();
         StructureBoundingBox var12 = var0.f();
         int var13 = var12.h();

         label129:
         for(DefinedStructure.BlockInfo var15 : var6.a(this.d, var7, var8, this.f)) {
            EnumDirection var16 = BlockJigsaw.h(var15.b);
            BlockPosition var17 = var15.a;
            BlockPosition var18 = var17.a(var16);
            int var19 = var17.v() - var13;
            int var20 = -1;
            ResourceKey<WorldGenFeatureDefinedStructurePoolTemplate> var21 = a(var15);
            Optional<? extends Holder<WorldGenFeatureDefinedStructurePoolTemplate>> var22 = this.a.b(var21);
            if (var22.isEmpty()) {
               WorldGenFeatureDefinedStructureJigsawPlacement.a.warn("Empty or non-existent pool: {}", var21.a());
            } else {
               Holder<WorldGenFeatureDefinedStructurePoolTemplate> var23 = var22.get();
               if (var23.a().b() == 0 && !var23.a(WorldGenFeaturePieces.a)) {
                  WorldGenFeatureDefinedStructureJigsawPlacement.a.warn("Empty or non-existent pool: {}", var21.a());
               } else {
                  Holder<WorldGenFeatureDefinedStructurePoolTemplate> var24 = var23.a().a();
                  if (var24.a().b() == 0 && !var24.a(WorldGenFeaturePieces.a)) {
                     WorldGenFeatureDefinedStructureJigsawPlacement.a
                        .warn("Empty or non-existent fallback pool: {}", var24.e().map(var0x -> var0x.a().toString()).orElse("<unregistered>"));
                  } else {
                     boolean var26 = var12.b(var18);
                     MutableObject<VoxelShape> var25;
                     if (var26) {
                        var25 = var11;
                        if (var11.getValue() == null) {
                           var11.setValue(VoxelShapes.a(AxisAlignedBB.a(var12)));
                        }
                     } else {
                        var25 = var1;
                     }

                     List<WorldGenFeatureDefinedStructurePoolStructure> var27 = Lists.newArrayList();
                     if (var2 != this.b) {
                        var27.addAll(var23.a().b(this.f));
                     }

                     var27.addAll(var24.a().b(this.f));

                     for(WorldGenFeatureDefinedStructurePoolStructure var29 : var27) {
                        if (var29 == WorldGenFeatureDefinedStructurePoolEmpty.b) {
                           break;
                        }

                        for(EnumBlockRotation var31 : EnumBlockRotation.b(this.f)) {
                           List<DefinedStructure.BlockInfo> var32 = var29.a(this.d, BlockPosition.b, var31, this.f);
                           StructureBoundingBox var33 = var29.a(this.d, BlockPosition.b, var31);
                           int var34;
                           if (var3 && var33.d() <= 16) {
                              var34 = var32.stream()
                                 .mapToInt(
                                    var1x -> {
                                       if (!var33.b(var1x.a.a(BlockJigsaw.h(var1x.b)))) {
                                          return 0;
                                       } else {
                                          ResourceKey<WorldGenFeatureDefinedStructurePoolTemplate> var2x = a(var1x);
                                          Optional<? extends Holder<WorldGenFeatureDefinedStructurePoolTemplate>> var3x = this.a.b(var2x);
                                          Optional<Holder<WorldGenFeatureDefinedStructurePoolTemplate>> var4x = var3x.map(
                                             var0xx -> ((WorldGenFeatureDefinedStructurePoolTemplate)var0xx.a()).a()
                                          );
                                          int var5x = var3x.<Integer>map(var0xx -> ((WorldGenFeatureDefinedStructurePoolTemplate)var0xx.a()).a(this.d))
                                             .orElse(0);
                                          int var6x = var4x.<Integer>map(var0xx -> ((WorldGenFeatureDefinedStructurePoolTemplate)var0xx.a()).a(this.d))
                                             .orElse(0);
                                          return Math.max(var5x, var6x);
                                       }
                                    }
                                 )
                                 .max()
                                 .orElse(0);
                           } else {
                              var34 = 0;
                           }

                           for(DefinedStructure.BlockInfo var36 : var32) {
                              if (BlockJigsaw.a(var15, var36)) {
                                 BlockPosition var37 = var36.a;
                                 BlockPosition var38 = var18.b(var37);
                                 StructureBoundingBox var39 = var29.a(this.d, var38, var31);
                                 int var40 = var39.h();
                                 WorldGenFeatureDefinedStructurePoolTemplate.Matching var41 = var29.e();
                                 boolean var42 = var41 == WorldGenFeatureDefinedStructurePoolTemplate.Matching.b;
                                 int var43 = var37.v();
                                 int var44 = var19 - var43 + BlockJigsaw.h(var15.b).k();
                                 int var45;
                                 if (var10 && var42) {
                                    var45 = var13 + var44;
                                 } else {
                                    if (var20 == -1) {
                                       var20 = this.c.b(var17.u(), var17.w(), HeightMap.Type.a, var4, var5);
                                    }

                                    var45 = var20 - var43;
                                 }

                                 int var46 = var45 - var40;
                                 StructureBoundingBox var47 = var39.b(0, var46, 0);
                                 BlockPosition var48 = var38.b(0, var46, 0);
                                 if (var34 > 0) {
                                    int var49 = Math.max(var34 + 1, var47.k() - var47.h());
                                    var47.a(new BlockPosition(var47.g(), var47.h() + var49, var47.i()));
                                 }

                                 if (!VoxelShapes.c((VoxelShape)var25.getValue(), VoxelShapes.a(AxisAlignedBB.a(var47).h(0.25)), OperatorBoolean.c)) {
                                    var25.setValue(VoxelShapes.b((VoxelShape)var25.getValue(), VoxelShapes.a(AxisAlignedBB.a(var47)), OperatorBoolean.e));
                                    int var49 = var0.d();
                                    int var50;
                                    if (var42) {
                                       var50 = var49 - var44;
                                    } else {
                                       var50 = var29.f();
                                    }

                                    WorldGenFeaturePillagerOutpostPoolPiece var51 = new WorldGenFeaturePillagerOutpostPoolPiece(
                                       this.d, var29, var48, var50, var31, var47
                                    );
                                    int var52;
                                    if (var10) {
                                       var52 = var13 + var19;
                                    } else if (var42) {
                                       var52 = var45 + var43;
                                    } else {
                                       if (var20 == -1) {
                                          var20 = this.c.b(var17.u(), var17.w(), HeightMap.Type.a, var4, var5);
                                       }

                                       var52 = var20 + var44 / 2;
                                    }

                                    var0.a(new WorldGenFeatureDefinedStructureJigsawJunction(var18.u(), var52 - var19 + var49, var18.w(), var44, var41));
                                    var51.a(new WorldGenFeatureDefinedStructureJigsawJunction(var17.u(), var52 - var43 + var50, var17.w(), -var44, var9));
                                    this.e.add(var51);
                                    if (var2 + 1 <= this.b) {
                                       this.g.addLast(new WorldGenFeatureDefinedStructureJigsawPlacement.a(var51, var25, var2 + 1));
                                    }
                                    continue label129;
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      private static ResourceKey<WorldGenFeatureDefinedStructurePoolTemplate> a(DefinedStructure.BlockInfo var0) {
         return ResourceKey.a(Registries.aA, new MinecraftKey(var0.c.l("pool")));
      }
   }
}
