package net.minecraft.world.level.levelgen.structure.structures;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.level.block.BlockChest;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EnumBlockMirror;
import net.minecraft.world.level.block.EnumBlockRotation;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.structure.DefinedStructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureBoundingBox;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.WorldGenFeatureStructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureInfo;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureProcessorBlockIgnore;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.storage.loot.LootTables;

public class WoodlandMansionPieces {
   public static void a(StructureTemplateManager var0, BlockPosition var1, EnumBlockRotation var2, List<WoodlandMansionPieces.i> var3, RandomSource var4) {
      WoodlandMansionPieces.c var5 = new WoodlandMansionPieces.c(var4);
      WoodlandMansionPieces.d var6 = new WoodlandMansionPieces.d(var0, var4);
      var6.a(var1, var2, var3, var5);
   }

   public static void a(String[] var0) {
      RandomSource var1 = RandomSource.a();
      long var2 = var1.g();
      System.out.println("Seed: " + var2);
      var1.b(var2);
      WoodlandMansionPieces.c var4 = new WoodlandMansionPieces.c(var1);
      var4.a();
   }

   static class a extends WoodlandMansionPieces.b {
      @Override
      public String a(RandomSource var0) {
         return "1x1_a" + (var0.a(5) + 1);
      }

      @Override
      public String b(RandomSource var0) {
         return "1x1_as" + (var0.a(4) + 1);
      }

      @Override
      public String a(RandomSource var0, boolean var1) {
         return "1x2_a" + (var0.a(9) + 1);
      }

      @Override
      public String b(RandomSource var0, boolean var1) {
         return "1x2_b" + (var0.a(5) + 1);
      }

      @Override
      public String c(RandomSource var0) {
         return "1x2_s" + (var0.a(2) + 1);
      }

      @Override
      public String d(RandomSource var0) {
         return "2x2_a" + (var0.a(4) + 1);
      }

      @Override
      public String e(RandomSource var0) {
         return "2x2_s1";
      }
   }

   abstract static class b {
      public abstract String a(RandomSource var1);

      public abstract String b(RandomSource var1);

      public abstract String a(RandomSource var1, boolean var2);

      public abstract String b(RandomSource var1, boolean var2);

      public abstract String c(RandomSource var1);

      public abstract String d(RandomSource var1);

      public abstract String e(RandomSource var1);
   }

   static class c {
      private static final int a = 11;
      private static final int b = 0;
      private static final int c = 1;
      private static final int d = 2;
      private static final int e = 3;
      private static final int f = 4;
      private static final int g = 5;
      private static final int h = 65536;
      private static final int i = 131072;
      private static final int j = 262144;
      private static final int k = 1048576;
      private static final int l = 2097152;
      private static final int m = 4194304;
      private static final int n = 8388608;
      private static final int o = 983040;
      private static final int p = 65535;
      private final RandomSource q;
      final WoodlandMansionPieces.g r;
      final WoodlandMansionPieces.g s;
      final WoodlandMansionPieces.g[] t;
      final int u;
      final int v;

      public c(RandomSource var0) {
         this.q = var0;
         int var1 = 11;
         this.u = 7;
         this.v = 4;
         this.r = new WoodlandMansionPieces.g(11, 11, 5);
         this.r.a(this.u, this.v, this.u + 1, this.v + 1, 3);
         this.r.a(this.u - 1, this.v, this.u - 1, this.v + 1, 2);
         this.r.a(this.u + 2, this.v - 2, this.u + 3, this.v + 3, 5);
         this.r.a(this.u + 1, this.v - 2, this.u + 1, this.v - 1, 1);
         this.r.a(this.u + 1, this.v + 2, this.u + 1, this.v + 3, 1);
         this.r.a(this.u - 1, this.v - 1, 1);
         this.r.a(this.u - 1, this.v + 2, 1);
         this.r.a(0, 0, 11, 1, 5);
         this.r.a(0, 9, 11, 11, 5);
         this.a(this.r, this.u, this.v - 2, EnumDirection.e, 6);
         this.a(this.r, this.u, this.v + 3, EnumDirection.e, 6);
         this.a(this.r, this.u - 2, this.v - 1, EnumDirection.e, 3);
         this.a(this.r, this.u - 2, this.v + 2, EnumDirection.e, 3);

         while(this.a(this.r)) {
         }

         this.t = new WoodlandMansionPieces.g[3];
         this.t[0] = new WoodlandMansionPieces.g(11, 11, 5);
         this.t[1] = new WoodlandMansionPieces.g(11, 11, 5);
         this.t[2] = new WoodlandMansionPieces.g(11, 11, 5);
         this.a(this.r, this.t[0]);
         this.a(this.r, this.t[1]);
         this.t[0].a(this.u + 1, this.v, this.u + 1, this.v + 1, 8388608);
         this.t[1].a(this.u + 1, this.v, this.u + 1, this.v + 1, 8388608);
         this.s = new WoodlandMansionPieces.g(this.r.b, this.r.c, 5);
         this.b();
         this.a(this.s, this.t[2]);
      }

      public static boolean a(WoodlandMansionPieces.g var0, int var1, int var2) {
         int var3 = var0.a(var1, var2);
         return var3 == 1 || var3 == 2 || var3 == 3 || var3 == 4;
      }

      public boolean a(WoodlandMansionPieces.g var0, int var1, int var2, int var3, int var4) {
         return (this.t[var3].a(var1, var2) & 65535) == var4;
      }

      @Nullable
      public EnumDirection b(WoodlandMansionPieces.g var0, int var1, int var2, int var3, int var4) {
         for(EnumDirection var6 : EnumDirection.EnumDirectionLimit.a) {
            if (this.a(var0, var1 + var6.j(), var2 + var6.l(), var3, var4)) {
               return var6;
            }
         }

         return null;
      }

      private void a(WoodlandMansionPieces.g var0, int var1, int var2, EnumDirection var3, int var4) {
         if (var4 > 0) {
            var0.a(var1, var2, 1);
            var0.a(var1 + var3.j(), var2 + var3.l(), 0, 1);

            for(int var5 = 0; var5 < 8; ++var5) {
               EnumDirection var6 = EnumDirection.b(this.q.a(4));
               if (var6 != var3.g() && (var6 != EnumDirection.f || !this.q.h())) {
                  int var7 = var1 + var3.j();
                  int var8 = var2 + var3.l();
                  if (var0.a(var7 + var6.j(), var8 + var6.l()) == 0 && var0.a(var7 + var6.j() * 2, var8 + var6.l() * 2) == 0) {
                     this.a(var0, var1 + var3.j() + var6.j(), var2 + var3.l() + var6.l(), var6, var4 - 1);
                     break;
                  }
               }
            }

            EnumDirection var5 = var3.h();
            EnumDirection var6 = var3.i();
            var0.a(var1 + var5.j(), var2 + var5.l(), 0, 2);
            var0.a(var1 + var6.j(), var2 + var6.l(), 0, 2);
            var0.a(var1 + var3.j() + var5.j(), var2 + var3.l() + var5.l(), 0, 2);
            var0.a(var1 + var3.j() + var6.j(), var2 + var3.l() + var6.l(), 0, 2);
            var0.a(var1 + var3.j() * 2, var2 + var3.l() * 2, 0, 2);
            var0.a(var1 + var5.j() * 2, var2 + var5.l() * 2, 0, 2);
            var0.a(var1 + var6.j() * 2, var2 + var6.l() * 2, 0, 2);
         }
      }

      private boolean a(WoodlandMansionPieces.g var0) {
         boolean var1 = false;

         for(int var2 = 0; var2 < var0.c; ++var2) {
            for(int var3 = 0; var3 < var0.b; ++var3) {
               if (var0.a(var3, var2) == 0) {
                  int var4 = 0;
                  var4 += a(var0, var3 + 1, var2) ? 1 : 0;
                  var4 += a(var0, var3 - 1, var2) ? 1 : 0;
                  var4 += a(var0, var3, var2 + 1) ? 1 : 0;
                  var4 += a(var0, var3, var2 - 1) ? 1 : 0;
                  if (var4 >= 3) {
                     var0.a(var3, var2, 2);
                     var1 = true;
                  } else if (var4 == 2) {
                     int var5 = 0;
                     var5 += a(var0, var3 + 1, var2 + 1) ? 1 : 0;
                     var5 += a(var0, var3 - 1, var2 + 1) ? 1 : 0;
                     var5 += a(var0, var3 + 1, var2 - 1) ? 1 : 0;
                     var5 += a(var0, var3 - 1, var2 - 1) ? 1 : 0;
                     if (var5 <= 1) {
                        var0.a(var3, var2, 2);
                        var1 = true;
                     }
                  }
               }
            }
         }

         return var1;
      }

      private void b() {
         List<Tuple<Integer, Integer>> var0 = Lists.newArrayList();
         WoodlandMansionPieces.g var1 = this.t[1];

         for(int var2 = 0; var2 < this.s.c; ++var2) {
            for(int var3 = 0; var3 < this.s.b; ++var3) {
               int var4 = var1.a(var3, var2);
               int var5 = var4 & 983040;
               if (var5 == 131072 && (var4 & 2097152) == 2097152) {
                  var0.add(new Tuple<>(var3, var2));
               }
            }
         }

         if (var0.isEmpty()) {
            this.s.a(0, 0, this.s.b, this.s.c, 5);
         } else {
            Tuple<Integer, Integer> var2 = var0.get(this.q.a(var0.size()));
            int var3 = var1.a(var2.a(), var2.b());
            var1.a(var2.a(), var2.b(), var3 | 4194304);
            EnumDirection var4 = this.b(this.r, var2.a(), var2.b(), 1, var3 & 65535);
            int var5 = var2.a() + var4.j();
            int var6 = var2.b() + var4.l();

            for(int var7 = 0; var7 < this.s.c; ++var7) {
               for(int var8 = 0; var8 < this.s.b; ++var8) {
                  if (!a(this.r, var8, var7)) {
                     this.s.a(var8, var7, 5);
                  } else if (var8 == var2.a() && var7 == var2.b()) {
                     this.s.a(var8, var7, 3);
                  } else if (var8 == var5 && var7 == var6) {
                     this.s.a(var8, var7, 3);
                     this.t[2].a(var8, var7, 8388608);
                  }
               }
            }

            List<EnumDirection> var7 = Lists.newArrayList();

            for(EnumDirection var9 : EnumDirection.EnumDirectionLimit.a) {
               if (this.s.a(var5 + var9.j(), var6 + var9.l()) == 0) {
                  var7.add(var9);
               }
            }

            if (var7.isEmpty()) {
               this.s.a(0, 0, this.s.b, this.s.c, 5);
               var1.a(var2.a(), var2.b(), var3);
            } else {
               EnumDirection var8 = var7.get(this.q.a(var7.size()));
               this.a(this.s, var5 + var8.j(), var6 + var8.l(), var8, 4);

               while(this.a(this.s)) {
               }
            }
         }
      }

      private void a(WoodlandMansionPieces.g var0, WoodlandMansionPieces.g var1) {
         ObjectArrayList<Tuple<Integer, Integer>> var2 = new ObjectArrayList();

         for(int var3 = 0; var3 < var0.c; ++var3) {
            for(int var4 = 0; var4 < var0.b; ++var4) {
               if (var0.a(var4, var3) == 2) {
                  var2.add(new Tuple<>(var4, var3));
               }
            }
         }

         SystemUtils.b(var2, this.q);
         int var3 = 10;
         ObjectListIterator var20 = var2.iterator();

         while(var20.hasNext()) {
            Tuple<Integer, Integer> var5 = (Tuple)var20.next();
            int var6 = var5.a();
            int var7 = var5.b();
            if (var1.a(var6, var7) == 0) {
               int var8 = var6;
               int var9 = var6;
               int var10 = var7;
               int var11 = var7;
               int var12 = 65536;
               if (var1.a(var6 + 1, var7) == 0
                  && var1.a(var6, var7 + 1) == 0
                  && var1.a(var6 + 1, var7 + 1) == 0
                  && var0.a(var6 + 1, var7) == 2
                  && var0.a(var6, var7 + 1) == 2
                  && var0.a(var6 + 1, var7 + 1) == 2) {
                  var9 = var6 + 1;
                  var11 = var7 + 1;
                  var12 = 262144;
               } else if (var1.a(var6 - 1, var7) == 0
                  && var1.a(var6, var7 + 1) == 0
                  && var1.a(var6 - 1, var7 + 1) == 0
                  && var0.a(var6 - 1, var7) == 2
                  && var0.a(var6, var7 + 1) == 2
                  && var0.a(var6 - 1, var7 + 1) == 2) {
                  var8 = var6 - 1;
                  var11 = var7 + 1;
                  var12 = 262144;
               } else if (var1.a(var6 - 1, var7) == 0
                  && var1.a(var6, var7 - 1) == 0
                  && var1.a(var6 - 1, var7 - 1) == 0
                  && var0.a(var6 - 1, var7) == 2
                  && var0.a(var6, var7 - 1) == 2
                  && var0.a(var6 - 1, var7 - 1) == 2) {
                  var8 = var6 - 1;
                  var10 = var7 - 1;
                  var12 = 262144;
               } else if (var1.a(var6 + 1, var7) == 0 && var0.a(var6 + 1, var7) == 2) {
                  var9 = var6 + 1;
                  var12 = 131072;
               } else if (var1.a(var6, var7 + 1) == 0 && var0.a(var6, var7 + 1) == 2) {
                  var11 = var7 + 1;
                  var12 = 131072;
               } else if (var1.a(var6 - 1, var7) == 0 && var0.a(var6 - 1, var7) == 2) {
                  var8 = var6 - 1;
                  var12 = 131072;
               } else if (var1.a(var6, var7 - 1) == 0 && var0.a(var6, var7 - 1) == 2) {
                  var10 = var7 - 1;
                  var12 = 131072;
               }

               int var13 = this.q.h() ? var8 : var9;
               int var14 = this.q.h() ? var10 : var11;
               int var15 = 2097152;
               if (!var0.b(var13, var14, 1)) {
                  var13 = var13 == var8 ? var9 : var8;
                  var14 = var14 == var10 ? var11 : var10;
                  if (!var0.b(var13, var14, 1)) {
                     var14 = var14 == var10 ? var11 : var10;
                     if (!var0.b(var13, var14, 1)) {
                        var13 = var13 == var8 ? var9 : var8;
                        var14 = var14 == var10 ? var11 : var10;
                        if (!var0.b(var13, var14, 1)) {
                           var15 = 0;
                           var13 = var8;
                           var14 = var10;
                        }
                     }
                  }
               }

               for(int var16 = var10; var16 <= var11; ++var16) {
                  for(int var17 = var8; var17 <= var9; ++var17) {
                     if (var17 == var13 && var16 == var14) {
                        var1.a(var17, var16, 1048576 | var15 | var12 | var3);
                     } else {
                        var1.a(var17, var16, var12 | var3);
                     }
                  }
               }

               ++var3;
            }
         }
      }

      public void a() {
         for(int var0 = 0; var0 < 2; ++var0) {
            WoodlandMansionPieces.g var1 = var0 == 0 ? this.r : this.s;

            for(int var2 = 0; var2 < var1.c; ++var2) {
               for(int var3 = 0; var3 < var1.b; ++var3) {
                  int var4 = var1.a(var3, var2);
                  if (var4 == 1) {
                     System.out.print("+");
                  } else if (var4 == 4) {
                     System.out.print("x");
                  } else if (var4 == 2) {
                     System.out.print("X");
                  } else if (var4 == 3) {
                     System.out.print("O");
                  } else if (var4 == 5) {
                     System.out.print("#");
                  } else {
                     System.out.print(" ");
                  }
               }

               System.out.println("");
            }

            System.out.println("");
         }
      }
   }

   static class d {
      private final StructureTemplateManager a;
      private final RandomSource b;
      private int c;
      private int d;

      public d(StructureTemplateManager var0, RandomSource var1) {
         this.a = var0;
         this.b = var1;
      }

      public void a(BlockPosition var0, EnumBlockRotation var1, List<WoodlandMansionPieces.i> var2, WoodlandMansionPieces.c var3) {
         WoodlandMansionPieces.e var4 = new WoodlandMansionPieces.e();
         var4.b = var0;
         var4.a = var1;
         var4.c = "wall_flat";
         WoodlandMansionPieces.e var5 = new WoodlandMansionPieces.e();
         this.a(var2, var4);
         var5.b = var4.b.b(8);
         var5.a = var4.a;
         var5.c = "wall_window";
         if (!var2.isEmpty()) {
         }

         WoodlandMansionPieces.g var6 = var3.r;
         WoodlandMansionPieces.g var7 = var3.s;
         this.c = var3.u + 1;
         this.d = var3.v + 1;
         int var8 = var3.u + 1;
         int var9 = var3.v;
         this.a(var2, var4, var6, EnumDirection.d, this.c, this.d, var8, var9);
         this.a(var2, var5, var6, EnumDirection.d, this.c, this.d, var8, var9);
         WoodlandMansionPieces.e var10 = new WoodlandMansionPieces.e();
         var10.b = var4.b.b(19);
         var10.a = var4.a;
         var10.c = "wall_window";
         boolean var11 = false;

         for(int var12 = 0; var12 < var7.c && !var11; ++var12) {
            for(int var13 = var7.b - 1; var13 >= 0 && !var11; --var13) {
               if (WoodlandMansionPieces.c.a(var7, var13, var12)) {
                  var10.b = var10.b.a(var1.a(EnumDirection.d), 8 + (var12 - this.d) * 8);
                  var10.b = var10.b.a(var1.a(EnumDirection.f), (var13 - this.c) * 8);
                  this.b(var2, var10);
                  this.a(var2, var10, var7, EnumDirection.d, var13, var12, var13, var12);
                  var11 = true;
               }
            }
         }

         this.a(var2, var0.b(16), var1, var6, var7);
         this.a(var2, var0.b(27), var1, var7, null);
         if (!var2.isEmpty()) {
         }

         WoodlandMansionPieces.b[] var12 = new WoodlandMansionPieces.b[]{
            new WoodlandMansionPieces.a(), new WoodlandMansionPieces.f(), new WoodlandMansionPieces.h()
         };

         for(int var13 = 0; var13 < 3; ++var13) {
            BlockPosition var14 = var0.b(8 * var13 + (var13 == 2 ? 3 : 0));
            WoodlandMansionPieces.g var15 = var3.t[var13];
            WoodlandMansionPieces.g var16 = var13 == 2 ? var7 : var6;
            String var17 = var13 == 0 ? "carpet_south_1" : "carpet_south_2";
            String var18 = var13 == 0 ? "carpet_west_1" : "carpet_west_2";

            for(int var19 = 0; var19 < var16.c; ++var19) {
               for(int var20 = 0; var20 < var16.b; ++var20) {
                  if (var16.a(var20, var19) == 1) {
                     BlockPosition var21 = var14.a(var1.a(EnumDirection.d), 8 + (var19 - this.d) * 8);
                     var21 = var21.a(var1.a(EnumDirection.f), (var20 - this.c) * 8);
                     var2.add(new WoodlandMansionPieces.i(this.a, "corridor_floor", var21, var1));
                     if (var16.a(var20, var19 - 1) == 1 || (var15.a(var20, var19 - 1) & 8388608) == 8388608) {
                        var2.add(new WoodlandMansionPieces.i(this.a, "carpet_north", var21.a(var1.a(EnumDirection.f), 1).c(), var1));
                     }

                     if (var16.a(var20 + 1, var19) == 1 || (var15.a(var20 + 1, var19) & 8388608) == 8388608) {
                        var2.add(
                           new WoodlandMansionPieces.i(this.a, "carpet_east", var21.a(var1.a(EnumDirection.d), 1).a(var1.a(EnumDirection.f), 5).c(), var1)
                        );
                     }

                     if (var16.a(var20, var19 + 1) == 1 || (var15.a(var20, var19 + 1) & 8388608) == 8388608) {
                        var2.add(new WoodlandMansionPieces.i(this.a, var17, var21.a(var1.a(EnumDirection.d), 5).a(var1.a(EnumDirection.e), 1), var1));
                     }

                     if (var16.a(var20 - 1, var19) == 1 || (var15.a(var20 - 1, var19) & 8388608) == 8388608) {
                        var2.add(new WoodlandMansionPieces.i(this.a, var18, var21.a(var1.a(EnumDirection.e), 1).a(var1.a(EnumDirection.c), 1), var1));
                     }
                  }
               }
            }

            String var19 = var13 == 0 ? "indoors_wall_1" : "indoors_wall_2";
            String var20 = var13 == 0 ? "indoors_door_1" : "indoors_door_2";
            List<EnumDirection> var21 = Lists.newArrayList();

            for(int var22 = 0; var22 < var16.c; ++var22) {
               for(int var23 = 0; var23 < var16.b; ++var23) {
                  boolean var24 = var13 == 2 && var16.a(var23, var22) == 3;
                  if (var16.a(var23, var22) == 2 || var24) {
                     int var25 = var15.a(var23, var22);
                     int var26 = var25 & 983040;
                     int var27 = var25 & 65535;
                     var24 = var24 && (var25 & 8388608) == 8388608;
                     var21.clear();
                     if ((var25 & 2097152) == 2097152) {
                        for(EnumDirection var29 : EnumDirection.EnumDirectionLimit.a) {
                           if (var16.a(var23 + var29.j(), var22 + var29.l()) == 1) {
                              var21.add(var29);
                           }
                        }
                     }

                     EnumDirection var28 = null;
                     if (!var21.isEmpty()) {
                        var28 = var21.get(this.b.a(var21.size()));
                     } else if ((var25 & 1048576) == 1048576) {
                        var28 = EnumDirection.b;
                     }

                     BlockPosition var29 = var14.a(var1.a(EnumDirection.d), 8 + (var22 - this.d) * 8);
                     var29 = var29.a(var1.a(EnumDirection.f), -1 + (var23 - this.c) * 8);
                     if (WoodlandMansionPieces.c.a(var16, var23 - 1, var22) && !var3.a(var16, var23 - 1, var22, var13, var27)) {
                        var2.add(new WoodlandMansionPieces.i(this.a, var28 == EnumDirection.e ? var20 : var19, var29, var1));
                     }

                     if (var16.a(var23 + 1, var22) == 1 && !var24) {
                        BlockPosition var30 = var29.a(var1.a(EnumDirection.f), 8);
                        var2.add(new WoodlandMansionPieces.i(this.a, var28 == EnumDirection.f ? var20 : var19, var30, var1));
                     }

                     if (WoodlandMansionPieces.c.a(var16, var23, var22 + 1) && !var3.a(var16, var23, var22 + 1, var13, var27)) {
                        BlockPosition var30 = var29.a(var1.a(EnumDirection.d), 7);
                        var30 = var30.a(var1.a(EnumDirection.f), 7);
                        var2.add(new WoodlandMansionPieces.i(this.a, var28 == EnumDirection.d ? var20 : var19, var30, var1.a(EnumBlockRotation.b)));
                     }

                     if (var16.a(var23, var22 - 1) == 1 && !var24) {
                        BlockPosition var30 = var29.a(var1.a(EnumDirection.c), 1);
                        var30 = var30.a(var1.a(EnumDirection.f), 7);
                        var2.add(new WoodlandMansionPieces.i(this.a, var28 == EnumDirection.c ? var20 : var19, var30, var1.a(EnumBlockRotation.b)));
                     }

                     if (var26 == 65536) {
                        this.a(var2, var29, var1, var28, var12[var13]);
                     } else if (var26 == 131072 && var28 != null) {
                        EnumDirection var30 = var3.b(var16, var23, var22, var13, var27);
                        boolean var31 = (var25 & 4194304) == 4194304;
                        this.a(var2, var29, var1, var30, var28, var12[var13], var31);
                     } else if (var26 == 262144 && var28 != null && var28 != EnumDirection.b) {
                        EnumDirection var30 = var28.h();
                        if (!var3.a(var16, var23 + var30.j(), var22 + var30.l(), var13, var27)) {
                           var30 = var30.g();
                        }

                        this.a(var2, var29, var1, var30, var28, var12[var13]);
                     } else if (var26 == 262144 && var28 == EnumDirection.b) {
                        this.a(var2, var29, var1, var12[var13]);
                     }
                  }
               }
            }
         }
      }

      private void a(
         List<WoodlandMansionPieces.i> var0,
         WoodlandMansionPieces.e var1,
         WoodlandMansionPieces.g var2,
         EnumDirection var3,
         int var4,
         int var5,
         int var6,
         int var7
      ) {
         int var8 = var4;
         int var9 = var5;
         EnumDirection var10 = var3;

         do {
            if (!WoodlandMansionPieces.c.a(var2, var8 + var3.j(), var9 + var3.l())) {
               this.c(var0, var1);
               var3 = var3.h();
               if (var8 != var6 || var9 != var7 || var10 != var3) {
                  this.b(var0, var1);
               }
            } else if (WoodlandMansionPieces.c.a(var2, var8 + var3.j(), var9 + var3.l())
               && WoodlandMansionPieces.c.a(var2, var8 + var3.j() + var3.i().j(), var9 + var3.l() + var3.i().l())) {
               this.d(var0, var1);
               var8 += var3.j();
               var9 += var3.l();
               var3 = var3.i();
            } else {
               var8 += var3.j();
               var9 += var3.l();
               if (var8 != var6 || var9 != var7 || var10 != var3) {
                  this.b(var0, var1);
               }
            }
         } while(var8 != var6 || var9 != var7 || var10 != var3);
      }

      private void a(
         List<WoodlandMansionPieces.i> var0, BlockPosition var1, EnumBlockRotation var2, WoodlandMansionPieces.g var3, @Nullable WoodlandMansionPieces.g var4
      ) {
         for(int var5 = 0; var5 < var3.c; ++var5) {
            for(int var6 = 0; var6 < var3.b; ++var6) {
               BlockPosition var7 = var1.a(var2.a(EnumDirection.d), 8 + (var5 - this.d) * 8);
               var7 = var7.a(var2.a(EnumDirection.f), (var6 - this.c) * 8);
               boolean var8x = var4 != null && WoodlandMansionPieces.c.a(var4, var6, var5);
               if (WoodlandMansionPieces.c.a(var3, var6, var5) && !var8x) {
                  var0.add(new WoodlandMansionPieces.i(this.a, "roof", var7.b(3), var2));
                  if (!WoodlandMansionPieces.c.a(var3, var6 + 1, var5)) {
                     BlockPosition var9 = var7.a(var2.a(EnumDirection.f), 6);
                     var0.add(new WoodlandMansionPieces.i(this.a, "roof_front", var9, var2));
                  }

                  if (!WoodlandMansionPieces.c.a(var3, var6 - 1, var5)) {
                     BlockPosition var9 = var7.a(var2.a(EnumDirection.f), 0);
                     var9 = var9.a(var2.a(EnumDirection.d), 7);
                     var0.add(new WoodlandMansionPieces.i(this.a, "roof_front", var9, var2.a(EnumBlockRotation.c)));
                  }

                  if (!WoodlandMansionPieces.c.a(var3, var6, var5 - 1)) {
                     BlockPosition var9 = var7.a(var2.a(EnumDirection.e), 1);
                     var0.add(new WoodlandMansionPieces.i(this.a, "roof_front", var9, var2.a(EnumBlockRotation.d)));
                  }

                  if (!WoodlandMansionPieces.c.a(var3, var6, var5 + 1)) {
                     BlockPosition var9 = var7.a(var2.a(EnumDirection.f), 6);
                     var9 = var9.a(var2.a(EnumDirection.d), 6);
                     var0.add(new WoodlandMansionPieces.i(this.a, "roof_front", var9, var2.a(EnumBlockRotation.b)));
                  }
               }
            }
         }

         if (var4 != null) {
            for(int var5 = 0; var5 < var3.c; ++var5) {
               for(int var6 = 0; var6 < var3.b; ++var6) {
                  BlockPosition var17 = var1.a(var2.a(EnumDirection.d), 8 + (var5 - this.d) * 8);
                  var17 = var17.a(var2.a(EnumDirection.f), (var6 - this.c) * 8);
                  boolean var8 = WoodlandMansionPieces.c.a(var4, var6, var5);
                  if (WoodlandMansionPieces.c.a(var3, var6, var5) && var8) {
                     if (!WoodlandMansionPieces.c.a(var3, var6 + 1, var5)) {
                        BlockPosition var9 = var17.a(var2.a(EnumDirection.f), 7);
                        var0.add(new WoodlandMansionPieces.i(this.a, "small_wall", var9, var2));
                     }

                     if (!WoodlandMansionPieces.c.a(var3, var6 - 1, var5)) {
                        BlockPosition var9 = var17.a(var2.a(EnumDirection.e), 1);
                        var9 = var9.a(var2.a(EnumDirection.d), 6);
                        var0.add(new WoodlandMansionPieces.i(this.a, "small_wall", var9, var2.a(EnumBlockRotation.c)));
                     }

                     if (!WoodlandMansionPieces.c.a(var3, var6, var5 - 1)) {
                        BlockPosition var9 = var17.a(var2.a(EnumDirection.e), 0);
                        var9 = var9.a(var2.a(EnumDirection.c), 1);
                        var0.add(new WoodlandMansionPieces.i(this.a, "small_wall", var9, var2.a(EnumBlockRotation.d)));
                     }

                     if (!WoodlandMansionPieces.c.a(var3, var6, var5 + 1)) {
                        BlockPosition var9 = var17.a(var2.a(EnumDirection.f), 6);
                        var9 = var9.a(var2.a(EnumDirection.d), 7);
                        var0.add(new WoodlandMansionPieces.i(this.a, "small_wall", var9, var2.a(EnumBlockRotation.b)));
                     }

                     if (!WoodlandMansionPieces.c.a(var3, var6 + 1, var5)) {
                        if (!WoodlandMansionPieces.c.a(var3, var6, var5 - 1)) {
                           BlockPosition var9 = var17.a(var2.a(EnumDirection.f), 7);
                           var9 = var9.a(var2.a(EnumDirection.c), 2);
                           var0.add(new WoodlandMansionPieces.i(this.a, "small_wall_corner", var9, var2));
                        }

                        if (!WoodlandMansionPieces.c.a(var3, var6, var5 + 1)) {
                           BlockPosition var9 = var17.a(var2.a(EnumDirection.f), 8);
                           var9 = var9.a(var2.a(EnumDirection.d), 7);
                           var0.add(new WoodlandMansionPieces.i(this.a, "small_wall_corner", var9, var2.a(EnumBlockRotation.b)));
                        }
                     }

                     if (!WoodlandMansionPieces.c.a(var3, var6 - 1, var5)) {
                        if (!WoodlandMansionPieces.c.a(var3, var6, var5 - 1)) {
                           BlockPosition var9 = var17.a(var2.a(EnumDirection.e), 2);
                           var9 = var9.a(var2.a(EnumDirection.c), 1);
                           var0.add(new WoodlandMansionPieces.i(this.a, "small_wall_corner", var9, var2.a(EnumBlockRotation.d)));
                        }

                        if (!WoodlandMansionPieces.c.a(var3, var6, var5 + 1)) {
                           BlockPosition var9 = var17.a(var2.a(EnumDirection.e), 1);
                           var9 = var9.a(var2.a(EnumDirection.d), 8);
                           var0.add(new WoodlandMansionPieces.i(this.a, "small_wall_corner", var9, var2.a(EnumBlockRotation.c)));
                        }
                     }
                  }
               }
            }
         }

         for(int var5 = 0; var5 < var3.c; ++var5) {
            for(int var6 = 0; var6 < var3.b; ++var6) {
               BlockPosition var19 = var1.a(var2.a(EnumDirection.d), 8 + (var5 - this.d) * 8);
               var19 = var19.a(var2.a(EnumDirection.f), (var6 - this.c) * 8);
               boolean var8 = var4 != null && WoodlandMansionPieces.c.a(var4, var6, var5);
               if (WoodlandMansionPieces.c.a(var3, var6, var5) && !var8) {
                  if (!WoodlandMansionPieces.c.a(var3, var6 + 1, var5)) {
                     BlockPosition var9 = var19.a(var2.a(EnumDirection.f), 6);
                     if (!WoodlandMansionPieces.c.a(var3, var6, var5 + 1)) {
                        BlockPosition var10 = var9.a(var2.a(EnumDirection.d), 6);
                        var0.add(new WoodlandMansionPieces.i(this.a, "roof_corner", var10, var2));
                     } else if (WoodlandMansionPieces.c.a(var3, var6 + 1, var5 + 1)) {
                        BlockPosition var10 = var9.a(var2.a(EnumDirection.d), 5);
                        var0.add(new WoodlandMansionPieces.i(this.a, "roof_inner_corner", var10, var2));
                     }

                     if (!WoodlandMansionPieces.c.a(var3, var6, var5 - 1)) {
                        var0.add(new WoodlandMansionPieces.i(this.a, "roof_corner", var9, var2.a(EnumBlockRotation.d)));
                     } else if (WoodlandMansionPieces.c.a(var3, var6 + 1, var5 - 1)) {
                        BlockPosition var10 = var19.a(var2.a(EnumDirection.f), 9);
                        var10 = var10.a(var2.a(EnumDirection.c), 2);
                        var0.add(new WoodlandMansionPieces.i(this.a, "roof_inner_corner", var10, var2.a(EnumBlockRotation.b)));
                     }
                  }

                  if (!WoodlandMansionPieces.c.a(var3, var6 - 1, var5)) {
                     BlockPosition var9 = var19.a(var2.a(EnumDirection.f), 0);
                     var9 = var9.a(var2.a(EnumDirection.d), 0);
                     if (!WoodlandMansionPieces.c.a(var3, var6, var5 + 1)) {
                        BlockPosition var10 = var9.a(var2.a(EnumDirection.d), 6);
                        var0.add(new WoodlandMansionPieces.i(this.a, "roof_corner", var10, var2.a(EnumBlockRotation.b)));
                     } else if (WoodlandMansionPieces.c.a(var3, var6 - 1, var5 + 1)) {
                        BlockPosition var10 = var9.a(var2.a(EnumDirection.d), 8);
                        var10 = var10.a(var2.a(EnumDirection.e), 3);
                        var0.add(new WoodlandMansionPieces.i(this.a, "roof_inner_corner", var10, var2.a(EnumBlockRotation.d)));
                     }

                     if (!WoodlandMansionPieces.c.a(var3, var6, var5 - 1)) {
                        var0.add(new WoodlandMansionPieces.i(this.a, "roof_corner", var9, var2.a(EnumBlockRotation.c)));
                     } else if (WoodlandMansionPieces.c.a(var3, var6 - 1, var5 - 1)) {
                        BlockPosition var10 = var9.a(var2.a(EnumDirection.d), 1);
                        var0.add(new WoodlandMansionPieces.i(this.a, "roof_inner_corner", var10, var2.a(EnumBlockRotation.c)));
                     }
                  }
               }
            }
         }
      }

      private void a(List<WoodlandMansionPieces.i> var0, WoodlandMansionPieces.e var1) {
         EnumDirection var2 = var1.a.a(EnumDirection.e);
         var0.add(new WoodlandMansionPieces.i(this.a, "entrance", var1.b.a(var2, 9), var1.a));
         var1.b = var1.b.a(var1.a.a(EnumDirection.d), 16);
      }

      private void b(List<WoodlandMansionPieces.i> var0, WoodlandMansionPieces.e var1) {
         var0.add(new WoodlandMansionPieces.i(this.a, var1.c, var1.b.a(var1.a.a(EnumDirection.f), 7), var1.a));
         var1.b = var1.b.a(var1.a.a(EnumDirection.d), 8);
      }

      private void c(List<WoodlandMansionPieces.i> var0, WoodlandMansionPieces.e var1) {
         var1.b = var1.b.a(var1.a.a(EnumDirection.d), -1);
         var0.add(new WoodlandMansionPieces.i(this.a, "wall_corner", var1.b, var1.a));
         var1.b = var1.b.a(var1.a.a(EnumDirection.d), -7);
         var1.b = var1.b.a(var1.a.a(EnumDirection.e), -6);
         var1.a = var1.a.a(EnumBlockRotation.b);
      }

      private void d(List<WoodlandMansionPieces.i> var0, WoodlandMansionPieces.e var1) {
         var1.b = var1.b.a(var1.a.a(EnumDirection.d), 6);
         var1.b = var1.b.a(var1.a.a(EnumDirection.f), 8);
         var1.a = var1.a.a(EnumBlockRotation.d);
      }

      private void a(List<WoodlandMansionPieces.i> var0, BlockPosition var1, EnumBlockRotation var2, EnumDirection var3, WoodlandMansionPieces.b var4) {
         EnumBlockRotation var5 = EnumBlockRotation.a;
         String var6 = var4.a(this.b);
         if (var3 != EnumDirection.f) {
            if (var3 == EnumDirection.c) {
               var5 = var5.a(EnumBlockRotation.d);
            } else if (var3 == EnumDirection.e) {
               var5 = var5.a(EnumBlockRotation.c);
            } else if (var3 == EnumDirection.d) {
               var5 = var5.a(EnumBlockRotation.b);
            } else {
               var6 = var4.b(this.b);
            }
         }

         BlockPosition var7 = DefinedStructure.a(new BlockPosition(1, 0, 0), EnumBlockMirror.a, var5, 7, 7);
         var5 = var5.a(var2);
         var7 = var7.a(var2);
         BlockPosition var8 = var1.b(var7.u(), 0, var7.w());
         var0.add(new WoodlandMansionPieces.i(this.a, var6, var8, var5));
      }

      private void a(
         List<WoodlandMansionPieces.i> var0,
         BlockPosition var1,
         EnumBlockRotation var2,
         EnumDirection var3,
         EnumDirection var4,
         WoodlandMansionPieces.b var5,
         boolean var6
      ) {
         if (var4 == EnumDirection.f && var3 == EnumDirection.d) {
            BlockPosition var7 = var1.a(var2.a(EnumDirection.f), 1);
            var0.add(new WoodlandMansionPieces.i(this.a, var5.a(this.b, var6), var7, var2));
         } else if (var4 == EnumDirection.f && var3 == EnumDirection.c) {
            BlockPosition var7 = var1.a(var2.a(EnumDirection.f), 1);
            var7 = var7.a(var2.a(EnumDirection.d), 6);
            var0.add(new WoodlandMansionPieces.i(this.a, var5.a(this.b, var6), var7, var2, EnumBlockMirror.b));
         } else if (var4 == EnumDirection.e && var3 == EnumDirection.c) {
            BlockPosition var7 = var1.a(var2.a(EnumDirection.f), 7);
            var7 = var7.a(var2.a(EnumDirection.d), 6);
            var0.add(new WoodlandMansionPieces.i(this.a, var5.a(this.b, var6), var7, var2.a(EnumBlockRotation.c)));
         } else if (var4 == EnumDirection.e && var3 == EnumDirection.d) {
            BlockPosition var7 = var1.a(var2.a(EnumDirection.f), 7);
            var0.add(new WoodlandMansionPieces.i(this.a, var5.a(this.b, var6), var7, var2, EnumBlockMirror.c));
         } else if (var4 == EnumDirection.d && var3 == EnumDirection.f) {
            BlockPosition var7 = var1.a(var2.a(EnumDirection.f), 1);
            var0.add(new WoodlandMansionPieces.i(this.a, var5.a(this.b, var6), var7, var2.a(EnumBlockRotation.b), EnumBlockMirror.b));
         } else if (var4 == EnumDirection.d && var3 == EnumDirection.e) {
            BlockPosition var7 = var1.a(var2.a(EnumDirection.f), 7);
            var0.add(new WoodlandMansionPieces.i(this.a, var5.a(this.b, var6), var7, var2.a(EnumBlockRotation.b)));
         } else if (var4 == EnumDirection.c && var3 == EnumDirection.e) {
            BlockPosition var7 = var1.a(var2.a(EnumDirection.f), 7);
            var7 = var7.a(var2.a(EnumDirection.d), 6);
            var0.add(new WoodlandMansionPieces.i(this.a, var5.a(this.b, var6), var7, var2.a(EnumBlockRotation.b), EnumBlockMirror.c));
         } else if (var4 == EnumDirection.c && var3 == EnumDirection.f) {
            BlockPosition var7 = var1.a(var2.a(EnumDirection.f), 1);
            var7 = var7.a(var2.a(EnumDirection.d), 6);
            var0.add(new WoodlandMansionPieces.i(this.a, var5.a(this.b, var6), var7, var2.a(EnumBlockRotation.d)));
         } else if (var4 == EnumDirection.d && var3 == EnumDirection.c) {
            BlockPosition var7 = var1.a(var2.a(EnumDirection.f), 1);
            var7 = var7.a(var2.a(EnumDirection.c), 8);
            var0.add(new WoodlandMansionPieces.i(this.a, var5.b(this.b, var6), var7, var2));
         } else if (var4 == EnumDirection.c && var3 == EnumDirection.d) {
            BlockPosition var7 = var1.a(var2.a(EnumDirection.f), 7);
            var7 = var7.a(var2.a(EnumDirection.d), 14);
            var0.add(new WoodlandMansionPieces.i(this.a, var5.b(this.b, var6), var7, var2.a(EnumBlockRotation.c)));
         } else if (var4 == EnumDirection.e && var3 == EnumDirection.f) {
            BlockPosition var7 = var1.a(var2.a(EnumDirection.f), 15);
            var0.add(new WoodlandMansionPieces.i(this.a, var5.b(this.b, var6), var7, var2.a(EnumBlockRotation.b)));
         } else if (var4 == EnumDirection.f && var3 == EnumDirection.e) {
            BlockPosition var7 = var1.a(var2.a(EnumDirection.e), 7);
            var7 = var7.a(var2.a(EnumDirection.d), 6);
            var0.add(new WoodlandMansionPieces.i(this.a, var5.b(this.b, var6), var7, var2.a(EnumBlockRotation.d)));
         } else if (var4 == EnumDirection.b && var3 == EnumDirection.f) {
            BlockPosition var7 = var1.a(var2.a(EnumDirection.f), 15);
            var0.add(new WoodlandMansionPieces.i(this.a, var5.c(this.b), var7, var2.a(EnumBlockRotation.b)));
         } else if (var4 == EnumDirection.b && var3 == EnumDirection.d) {
            BlockPosition var7 = var1.a(var2.a(EnumDirection.f), 1);
            var7 = var7.a(var2.a(EnumDirection.c), 0);
            var0.add(new WoodlandMansionPieces.i(this.a, var5.c(this.b), var7, var2));
         }
      }

      private void a(
         List<WoodlandMansionPieces.i> var0, BlockPosition var1, EnumBlockRotation var2, EnumDirection var3, EnumDirection var4, WoodlandMansionPieces.b var5
      ) {
         int var6 = 0;
         int var7 = 0;
         EnumBlockRotation var8 = var2;
         EnumBlockMirror var9 = EnumBlockMirror.a;
         if (var4 == EnumDirection.f && var3 == EnumDirection.d) {
            var6 = -7;
         } else if (var4 == EnumDirection.f && var3 == EnumDirection.c) {
            var6 = -7;
            var7 = 6;
            var9 = EnumBlockMirror.b;
         } else if (var4 == EnumDirection.c && var3 == EnumDirection.f) {
            var6 = 1;
            var7 = 14;
            var8 = var2.a(EnumBlockRotation.d);
         } else if (var4 == EnumDirection.c && var3 == EnumDirection.e) {
            var6 = 7;
            var7 = 14;
            var8 = var2.a(EnumBlockRotation.d);
            var9 = EnumBlockMirror.b;
         } else if (var4 == EnumDirection.d && var3 == EnumDirection.e) {
            var6 = 7;
            var7 = -8;
            var8 = var2.a(EnumBlockRotation.b);
         } else if (var4 == EnumDirection.d && var3 == EnumDirection.f) {
            var6 = 1;
            var7 = -8;
            var8 = var2.a(EnumBlockRotation.b);
            var9 = EnumBlockMirror.b;
         } else if (var4 == EnumDirection.e && var3 == EnumDirection.c) {
            var6 = 15;
            var7 = 6;
            var8 = var2.a(EnumBlockRotation.c);
         } else if (var4 == EnumDirection.e && var3 == EnumDirection.d) {
            var6 = 15;
            var9 = EnumBlockMirror.c;
         }

         BlockPosition var10 = var1.a(var2.a(EnumDirection.f), var6);
         var10 = var10.a(var2.a(EnumDirection.d), var7);
         var0.add(new WoodlandMansionPieces.i(this.a, var5.d(this.b), var10, var8, var9));
      }

      private void a(List<WoodlandMansionPieces.i> var0, BlockPosition var1, EnumBlockRotation var2, WoodlandMansionPieces.b var3) {
         BlockPosition var4 = var1.a(var2.a(EnumDirection.f), 1);
         var0.add(new WoodlandMansionPieces.i(this.a, var3.e(this.b), var4, var2, EnumBlockMirror.a));
      }
   }

   static class e {
      public EnumBlockRotation a;
      public BlockPosition b;
      public String c;
   }

   static class f extends WoodlandMansionPieces.b {
      @Override
      public String a(RandomSource var0) {
         return "1x1_b" + (var0.a(4) + 1);
      }

      @Override
      public String b(RandomSource var0) {
         return "1x1_as" + (var0.a(4) + 1);
      }

      @Override
      public String a(RandomSource var0, boolean var1) {
         return var1 ? "1x2_c_stairs" : "1x2_c" + (var0.a(4) + 1);
      }

      @Override
      public String b(RandomSource var0, boolean var1) {
         return var1 ? "1x2_d_stairs" : "1x2_d" + (var0.a(5) + 1);
      }

      @Override
      public String c(RandomSource var0) {
         return "1x2_se" + (var0.a(1) + 1);
      }

      @Override
      public String d(RandomSource var0) {
         return "2x2_b" + (var0.a(5) + 1);
      }

      @Override
      public String e(RandomSource var0) {
         return "2x2_s1";
      }
   }

   static class g {
      private final int[][] a;
      final int b;
      final int c;
      private final int d;

      public g(int var0, int var1, int var2) {
         this.b = var0;
         this.c = var1;
         this.d = var2;
         this.a = new int[var0][var1];
      }

      public void a(int var0, int var1, int var2) {
         if (var0 >= 0 && var0 < this.b && var1 >= 0 && var1 < this.c) {
            this.a[var0][var1] = var2;
         }
      }

      public void a(int var0, int var1, int var2, int var3, int var4) {
         for(int var5 = var1; var5 <= var3; ++var5) {
            for(int var6 = var0; var6 <= var2; ++var6) {
               this.a(var6, var5, var4);
            }
         }
      }

      public int a(int var0, int var1) {
         return var0 >= 0 && var0 < this.b && var1 >= 0 && var1 < this.c ? this.a[var0][var1] : this.d;
      }

      public void a(int var0, int var1, int var2, int var3) {
         if (this.a(var0, var1) == var2) {
            this.a(var0, var1, var3);
         }
      }

      public boolean b(int var0, int var1, int var2) {
         return this.a(var0 - 1, var1) == var2 || this.a(var0 + 1, var1) == var2 || this.a(var0, var1 + 1) == var2 || this.a(var0, var1 - 1) == var2;
      }
   }

   static class h extends WoodlandMansionPieces.f {
   }

   public static class i extends DefinedStructurePiece {
      public i(StructureTemplateManager var0, String var1, BlockPosition var2, EnumBlockRotation var3) {
         this(var0, var1, var2, var3, EnumBlockMirror.a);
      }

      public i(StructureTemplateManager var0, String var1, BlockPosition var2, EnumBlockRotation var3, EnumBlockMirror var4) {
         super(WorldGenFeatureStructurePieceType.Z, 0, var0, a(var1), var1, a(var4, var3), var2);
      }

      public i(StructureTemplateManager var0, NBTTagCompound var1) {
         super(WorldGenFeatureStructurePieceType.Z, var1, var0, var1x -> a(EnumBlockMirror.valueOf(var1.l("Mi")), EnumBlockRotation.valueOf(var1.l("Rot"))));
      }

      @Override
      protected MinecraftKey b() {
         return a(this.a);
      }

      private static MinecraftKey a(String var0) {
         return new MinecraftKey("woodland_mansion/" + var0);
      }

      private static DefinedStructureInfo a(EnumBlockMirror var0, EnumBlockRotation var1) {
         return new DefinedStructureInfo().a(true).a(var1).a(var0).a(DefinedStructureProcessorBlockIgnore.b);
      }

      @Override
      protected void a(StructurePieceSerializationContext var0, NBTTagCompound var1) {
         super.a(var0, var1);
         var1.a("Rot", this.c.d().name());
         var1.a("Mi", this.c.c().name());
      }

      @Override
      protected void a(String var0, BlockPosition var1, WorldAccess var2, RandomSource var3, StructureBoundingBox var4) {
         if (var0.startsWith("Chest")) {
            EnumBlockRotation var5 = this.c.d();
            IBlockData var6 = Blocks.cu.o();
            if ("ChestWest".equals(var0)) {
               var6 = var6.a(BlockChest.b, var5.a(EnumDirection.e));
            } else if ("ChestEast".equals(var0)) {
               var6 = var6.a(BlockChest.b, var5.a(EnumDirection.f));
            } else if ("ChestSouth".equals(var0)) {
               var6 = var6.a(BlockChest.b, var5.a(EnumDirection.d));
            } else if ("ChestNorth".equals(var0)) {
               var6 = var6.a(BlockChest.b, var5.a(EnumDirection.c));
            }

            this.a(var2, var4, var3, var1, LootTables.D, var6);
         } else {
            List<EntityInsentient> var5 = new ArrayList<>();
            switch(var0) {
               case "Mage":
                  var5.add(EntityTypes.G.a((World)var2.C()));
                  break;
               case "Warrior":
                  var5.add(EntityTypes.bg.a((World)var2.C()));
                  break;
               case "Group of Allays":
                  int var8 = var2.r_().a(3) + 1;

                  for(int var9 = 0; var9 < var8; ++var9) {
                     var5.add(EntityTypes.b.a((World)var2.C()));
                  }
                  break;
               default:
                  return;
            }

            for(EntityInsentient var7 : var5) {
               if (var7 != null) {
                  var7.fz();
                  var7.a(var1, 0.0F, 0.0F);
                  var7.a(var2, var2.d_(var7.dg()), EnumMobSpawn.d, null, null);
                  var2.a_(var7);
                  var2.a(var1, Blocks.a.o(), 2);
               }
            }
         }
      }
   }
}
