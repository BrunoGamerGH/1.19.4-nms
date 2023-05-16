package net.minecraft.world.level.levelgen.structure.structures;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.decoration.EntityItemFrame;
import net.minecraft.world.entity.monster.EntityShulker;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.level.block.EnumBlockRotation;
import net.minecraft.world.level.block.entity.TileEntityLootable;
import net.minecraft.world.level.levelgen.structure.DefinedStructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureBoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.WorldGenFeatureStructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureInfo;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureProcessorBlockIgnore;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.storage.loot.LootTables;

public class EndCityPieces {
   private static final int a = 8;
   static final EndCityPieces.b b = new EndCityPieces.b() {
      @Override
      public void a() {
      }

      @Override
      public boolean a(StructureTemplateManager var0, int var1, EndCityPieces.a var2, BlockPosition var3, List<StructurePiece> var4, RandomSource var5) {
         if (var1 > 8) {
            return false;
         } else {
            EnumBlockRotation var6 = var2.e().d();
            EndCityPieces.a var7 = EndCityPieces.a(var4, EndCityPieces.a(var0, var2, var3, "base_floor", var6, true));
            int var8 = var5.a(3);
            if (var8 == 0) {
               var7 = EndCityPieces.a(var4, EndCityPieces.a(var0, var7, new BlockPosition(-1, 4, -1), "base_roof", var6, true));
            } else if (var8 == 1) {
               var7 = EndCityPieces.a(var4, EndCityPieces.a(var0, var7, new BlockPosition(-1, 0, -1), "second_floor_2", var6, false));
               var7 = EndCityPieces.a(var4, EndCityPieces.a(var0, var7, new BlockPosition(-1, 8, -1), "second_roof", var6, false));
               EndCityPieces.a(var0, EndCityPieces.d, var1 + 1, var7, null, var4, var5);
            } else if (var8 == 2) {
               var7 = EndCityPieces.a(var4, EndCityPieces.a(var0, var7, new BlockPosition(-1, 0, -1), "second_floor_2", var6, false));
               var7 = EndCityPieces.a(var4, EndCityPieces.a(var0, var7, new BlockPosition(-1, 4, -1), "third_floor_2", var6, false));
               var7 = EndCityPieces.a(var4, EndCityPieces.a(var0, var7, new BlockPosition(-1, 8, -1), "third_roof", var6, true));
               EndCityPieces.a(var0, EndCityPieces.d, var1 + 1, var7, null, var4, var5);
            }

            return true;
         }
      }
   };
   static final List<Tuple<EnumBlockRotation, BlockPosition>> c = Lists.newArrayList(
      new Tuple[]{
         new Tuple<>(EnumBlockRotation.a, new BlockPosition(1, -1, 0)),
         new Tuple<>(EnumBlockRotation.b, new BlockPosition(6, -1, 1)),
         new Tuple<>(EnumBlockRotation.d, new BlockPosition(0, -1, 5)),
         new Tuple<>(EnumBlockRotation.c, new BlockPosition(5, -1, 6))
      }
   );
   static final EndCityPieces.b d = new EndCityPieces.b() {
      @Override
      public void a() {
      }

      @Override
      public boolean a(StructureTemplateManager var0, int var1, EndCityPieces.a var2, BlockPosition var3, List<StructurePiece> var4, RandomSource var5) {
         EnumBlockRotation var6 = var2.e().d();
         EndCityPieces.a var7 = EndCityPieces.a(
            var4, EndCityPieces.a(var0, var2, new BlockPosition(3 + var5.a(2), -3, 3 + var5.a(2)), "tower_base", var6, true)
         );
         var7 = EndCityPieces.a(var4, EndCityPieces.a(var0, var7, new BlockPosition(0, 7, 0), "tower_piece", var6, true));
         EndCityPieces.a var8x = var5.a(3) == 0 ? var7 : null;
         int var9 = 1 + var5.a(3);

         for(int var10 = 0; var10 < var9; ++var10) {
            var7 = EndCityPieces.a(var4, EndCityPieces.a(var0, var7, new BlockPosition(0, 4, 0), "tower_piece", var6, true));
            if (var10 < var9 - 1 && var5.h()) {
               var8x = var7;
            }
         }

         if (var8x != null) {
            for(Tuple<EnumBlockRotation, BlockPosition> var11 : EndCityPieces.c) {
               if (var5.h()) {
                  EndCityPieces.a var12 = EndCityPieces.a(var4, EndCityPieces.a(var0, var8x, var11.b(), "bridge_end", var6.a(var11.a()), true));
                  EndCityPieces.a(var0, EndCityPieces.e, var1 + 1, var12, null, var4, var5);
               }
            }

            var7 = EndCityPieces.a(var4, EndCityPieces.a(var0, var7, new BlockPosition(-1, 4, -1), "tower_top", var6, true));
         } else {
            if (var1 != 7) {
               return EndCityPieces.a(var0, EndCityPieces.g, var1 + 1, var7, null, var4, var5);
            }

            var7 = EndCityPieces.a(var4, EndCityPieces.a(var0, var7, new BlockPosition(-1, 4, -1), "tower_top", var6, true));
         }

         return true;
      }
   };
   static final EndCityPieces.b e = new EndCityPieces.b() {
      public boolean a;

      @Override
      public void a() {
         this.a = false;
      }

      @Override
      public boolean a(StructureTemplateManager var0, int var1, EndCityPieces.a var2, BlockPosition var3, List<StructurePiece> var4, RandomSource var5) {
         EnumBlockRotation var6 = var2.e().d();
         int var7 = var5.a(4) + 1;
         EndCityPieces.a var8 = EndCityPieces.a(var4, EndCityPieces.a(var0, var2, new BlockPosition(0, 0, -4), "bridge_piece", var6, true));
         var8.a(-1);
         int var9 = 0;

         for(int var10 = 0; var10 < var7; ++var10) {
            if (var5.h()) {
               var8 = EndCityPieces.a(var4, EndCityPieces.a(var0, var8, new BlockPosition(0, var9, -4), "bridge_piece", var6, true));
               var9 = 0;
            } else {
               if (var5.h()) {
                  var8 = EndCityPieces.a(var4, EndCityPieces.a(var0, var8, new BlockPosition(0, var9, -4), "bridge_steep_stairs", var6, true));
               } else {
                  var8 = EndCityPieces.a(var4, EndCityPieces.a(var0, var8, new BlockPosition(0, var9, -8), "bridge_gentle_stairs", var6, true));
               }

               var9 = 4;
            }
         }

         if (!this.a && var5.a(10 - var1) == 0) {
            EndCityPieces.a(var4, EndCityPieces.a(var0, var8, new BlockPosition(-8 + var5.a(8), var9, -70 + var5.a(10)), "ship", var6, true));
            this.a = true;
         } else if (!EndCityPieces.a(var0, EndCityPieces.b, var1 + 1, var8, new BlockPosition(-3, var9 + 1, -11), var4, var5)) {
            return false;
         }

         var8 = EndCityPieces.a(var4, EndCityPieces.a(var0, var8, new BlockPosition(4, var9, 0), "bridge_end", var6.a(EnumBlockRotation.c), true));
         var8.a(-1);
         return true;
      }
   };
   static final List<Tuple<EnumBlockRotation, BlockPosition>> f = Lists.newArrayList(
      new Tuple[]{
         new Tuple<>(EnumBlockRotation.a, new BlockPosition(4, -1, 0)),
         new Tuple<>(EnumBlockRotation.b, new BlockPosition(12, -1, 4)),
         new Tuple<>(EnumBlockRotation.d, new BlockPosition(0, -1, 8)),
         new Tuple<>(EnumBlockRotation.c, new BlockPosition(8, -1, 12))
      }
   );
   static final EndCityPieces.b g = new EndCityPieces.b() {
      @Override
      public void a() {
      }

      @Override
      public boolean a(StructureTemplateManager var0, int var1, EndCityPieces.a var2, BlockPosition var3, List<StructurePiece> var4, RandomSource var5) {
         EnumBlockRotation var7 = var2.e().d();
         EndCityPieces.a var6 = EndCityPieces.a(var4, EndCityPieces.a(var0, var2, new BlockPosition(-3, 4, -3), "fat_tower_base", var7, true));
         var6 = EndCityPieces.a(var4, EndCityPieces.a(var0, var6, new BlockPosition(0, 4, 0), "fat_tower_middle", var7, true));

         for(int var8 = 0; var8 < 2 && var5.a(3) != 0; ++var8) {
            var6 = EndCityPieces.a(var4, EndCityPieces.a(var0, var6, new BlockPosition(0, 8, 0), "fat_tower_middle", var7, true));

            for(Tuple<EnumBlockRotation, BlockPosition> var10 : EndCityPieces.f) {
               if (var5.h()) {
                  EndCityPieces.a var11 = EndCityPieces.a(var4, EndCityPieces.a(var0, var6, var10.b(), "bridge_end", var7.a(var10.a()), true));
                  EndCityPieces.a(var0, EndCityPieces.e, var1 + 1, var11, null, var4, var5);
               }
            }
         }

         var6 = EndCityPieces.a(var4, EndCityPieces.a(var0, var6, new BlockPosition(-2, 8, -2), "fat_tower_top", var7, true));
         return true;
      }
   };

   static EndCityPieces.a a(StructureTemplateManager var0, EndCityPieces.a var1, BlockPosition var2, String var3, EnumBlockRotation var4, boolean var5) {
      EndCityPieces.a var6 = new EndCityPieces.a(var0, var3, var1.d(), var4, var5);
      BlockPosition var7 = var1.c().a(var1.e(), var2, var6.e(), BlockPosition.b);
      var6.a(var7.u(), var7.v(), var7.w());
      return var6;
   }

   public static void a(StructureTemplateManager var0, BlockPosition var1, EnumBlockRotation var2, List<StructurePiece> var3, RandomSource var4) {
      g.a();
      b.a();
      e.a();
      d.a();
      EndCityPieces.a var5 = a(var3, new EndCityPieces.a(var0, "base_floor", var1, var2, true));
      var5 = a(var3, a(var0, var5, new BlockPosition(-1, 0, -1), "second_floor_1", var2, false));
      var5 = a(var3, a(var0, var5, new BlockPosition(-1, 4, -1), "third_floor_1", var2, false));
      var5 = a(var3, a(var0, var5, new BlockPosition(-1, 8, -1), "third_roof", var2, true));
      a(var0, d, 1, var5, null, var3, var4);
   }

   static EndCityPieces.a a(List<StructurePiece> var0, EndCityPieces.a var1) {
      var0.add(var1);
      return var1;
   }

   static boolean a(
      StructureTemplateManager var0, EndCityPieces.b var1, int var2, EndCityPieces.a var3, BlockPosition var4, List<StructurePiece> var5, RandomSource var6
   ) {
      if (var2 > 8) {
         return false;
      } else {
         List<StructurePiece> var7 = Lists.newArrayList();
         if (var1.a(var0, var2, var3, var4, var7, var6)) {
            boolean var8 = false;
            int var9 = var6.f();

            for(StructurePiece var11 : var7) {
               var11.a(var9);
               StructurePiece var12 = StructurePiece.a(var5, var11.f());
               if (var12 != null && var12.g() != var3.g()) {
                  var8 = true;
                  break;
               }
            }

            if (!var8) {
               var5.addAll(var7);
               return true;
            }
         }

         return false;
      }
   }

   public static class a extends DefinedStructurePiece {
      public a(StructureTemplateManager var0, String var1, BlockPosition var2, EnumBlockRotation var3, boolean var4) {
         super(WorldGenFeatureStructurePieceType.Y, 0, var0, a(var1), var1, a(var4, var3), var2);
      }

      public a(StructureTemplateManager var0, NBTTagCompound var1) {
         super(WorldGenFeatureStructurePieceType.Y, var1, var0, var1x -> a(var1.q("OW"), EnumBlockRotation.valueOf(var1.l("Rot"))));
      }

      private static DefinedStructureInfo a(boolean var0, EnumBlockRotation var1) {
         DefinedStructureProcessorBlockIgnore var2 = var0 ? DefinedStructureProcessorBlockIgnore.b : DefinedStructureProcessorBlockIgnore.d;
         return new DefinedStructureInfo().a(true).a(var2).a(var1);
      }

      @Override
      protected MinecraftKey b() {
         return a(this.a);
      }

      private static MinecraftKey a(String var0) {
         return new MinecraftKey("end_city/" + var0);
      }

      @Override
      protected void a(StructurePieceSerializationContext var0, NBTTagCompound var1) {
         super.a(var0, var1);
         var1.a("Rot", this.c.d().name());
         var1.a("OW", this.c.i().get(0) == DefinedStructureProcessorBlockIgnore.b);
      }

      @Override
      protected void a(String var0, BlockPosition var1, WorldAccess var2, RandomSource var3, StructureBoundingBox var4) {
         if (var0.startsWith("Chest")) {
            BlockPosition var5 = var1.d();
            if (var4.b(var5)) {
               TileEntityLootable.a(var2, var3, var5, LootTables.c);
            }
         } else if (var4.b(var1) && World.k(var1)) {
            if (var0.startsWith("Sentry")) {
               EntityShulker var5 = EntityTypes.aG.a((World)var2.C());
               if (var5 != null) {
                  var5.e((double)var1.u() + 0.5, (double)var1.v(), (double)var1.w() + 0.5);
                  var2.b(var5);
               }
            } else if (var0.startsWith("Elytra")) {
               EntityItemFrame var5 = new EntityItemFrame(var2.C(), var1, this.c.d().a(EnumDirection.d));
               var5.a(new ItemStack(Items.nd), false);
               var2.b(var5);
            }
         }
      }
   }

   interface b {
      void a();

      boolean a(StructureTemplateManager var1, int var2, EndCityPieces.a var3, BlockPosition var4, List<StructurePiece> var5, RandomSource var6);
   }
}
