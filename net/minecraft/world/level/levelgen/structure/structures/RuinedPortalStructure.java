package net.minecraft.world.level.levelgen.structure.structures;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockColumn;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.block.EnumBlockMirror;
import net.minecraft.world.level.block.EnumBlockRotation;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.SeededRandom;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureBoundingBox;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructure;

public class RuinedPortalStructure extends Structure {
   private static final String[] e = new String[]{
      "ruined_portal/portal_1",
      "ruined_portal/portal_2",
      "ruined_portal/portal_3",
      "ruined_portal/portal_4",
      "ruined_portal/portal_5",
      "ruined_portal/portal_6",
      "ruined_portal/portal_7",
      "ruined_portal/portal_8",
      "ruined_portal/portal_9",
      "ruined_portal/portal_10"
   };
   private static final String[] f = new String[]{"ruined_portal/giant_portal_1", "ruined_portal/giant_portal_2", "ruined_portal/giant_portal_3"};
   private static final float g = 0.05F;
   private static final int h = 15;
   private final List<RuinedPortalStructure.a> i;
   public static final Codec<RuinedPortalStructure> d = RecordCodecBuilder.create(
      var0 -> var0.group(a(var0), ExtraCodecs.a(RuinedPortalStructure.a.a.listOf()).fieldOf("setups").forGetter(var0x -> var0x.i))
            .apply(var0, RuinedPortalStructure::new)
   );

   public RuinedPortalStructure(Structure.c var0, List<RuinedPortalStructure.a> var1) {
      super(var0);
      this.i = var1;
   }

   public RuinedPortalStructure(Structure.c var0, RuinedPortalStructure.a var1) {
      this(var0, List.of(var1));
   }

   @Override
   public Optional<Structure.b> a(Structure.a var0) {
      RuinedPortalPiece.a var1 = new RuinedPortalPiece.a();
      SeededRandom var2 = var0.f();
      RuinedPortalStructure.a var3 = null;
      if (this.i.size() > 1) {
         float var4 = 0.0F;

         for(RuinedPortalStructure.a var6 : this.i) {
            var4 += var6.h();
         }

         float var5 = var2.i();

         for(RuinedPortalStructure.a var7 : this.i) {
            var5 -= var7.h() / var4;
            if (var5 < 0.0F) {
               var3 = var7;
               break;
            }
         }
      } else {
         var3 = this.i.get(0);
      }

      if (var3 == null) {
         throw new IllegalStateException();
      } else {
         RuinedPortalStructure.a var4 = var3;
         var1.d = a(var2, var4.b());
         var1.c = var4.c();
         var1.e = var4.d();
         var1.f = var4.e();
         var1.g = var4.g();
         MinecraftKey var5;
         if (var2.i() < 0.05F) {
            var5 = new MinecraftKey(f[var2.a(f.length)]);
         } else {
            var5 = new MinecraftKey(e[var2.a(e.length)]);
         }

         DefinedStructure var6 = var0.e().a(var5);
         EnumBlockRotation var7 = SystemUtils.a(EnumBlockRotation.values(), var2);
         EnumBlockMirror var8 = var2.i() < 0.5F ? EnumBlockMirror.a : EnumBlockMirror.c;
         BlockPosition var9 = new BlockPosition(var6.a().u() / 2, 0, var6.a().w() / 2);
         ChunkGenerator var10 = var0.b();
         LevelHeightAccessor var11 = var0.i();
         RandomState var12 = var0.d();
         BlockPosition var13 = var0.h().l();
         StructureBoundingBox var14 = var6.a(var13, var7, var9, var8);
         BlockPosition var15 = var14.f();
         int var16 = var10.a(var15.u(), var15.w(), RuinedPortalPiece.a(var4.a()), var11, var12) - 1;
         int var17 = a(var2, var10, var4.a(), var1.d, var16, var14.d(), var14, var11, var12);
         BlockPosition var18 = new BlockPosition(var13.u(), var17, var13.w());
         return Optional.of(new Structure.b(var18, (Consumer<StructurePiecesBuilder>)(var10x -> {
            if (var4.f()) {
               var1.b = a(var18, var0.b().c().getNoiseBiome(QuartPos.a(var18.u()), QuartPos.a(var18.v()), QuartPos.a(var18.w()), var12.b()));
            }

            var10x.a(new RuinedPortalPiece(var0.e(), var18, var4.a(), var1, var5, var6, var7, var8, var9));
         })));
      }
   }

   private static boolean a(SeededRandom var0, float var1) {
      if (var1 == 0.0F) {
         return false;
      } else if (var1 == 1.0F) {
         return true;
      } else {
         return var0.i() < var1;
      }
   }

   private static boolean a(BlockPosition var0, Holder<BiomeBase> var1) {
      return var1.a().b(var0);
   }

   private static int a(
      RandomSource var0,
      ChunkGenerator var1,
      RuinedPortalPiece.b var2,
      boolean var3,
      int var4,
      int var5,
      StructureBoundingBox var6,
      LevelHeightAccessor var7,
      RandomState var8
   ) {
      int var10 = var7.v_() + 15;
      int var9;
      if (var2 == RuinedPortalPiece.b.f) {
         if (var3) {
            var9 = MathHelper.b(var0, 32, 100);
         } else if (var0.i() < 0.5F) {
            var9 = MathHelper.b(var0, 27, 29);
         } else {
            var9 = MathHelper.b(var0, 29, 100);
         }
      } else if (var2 == RuinedPortalPiece.b.d) {
         int var11 = var4 - var5;
         var9 = a(var0, 70, var11);
      } else if (var2 == RuinedPortalPiece.b.e) {
         int var11 = var4 - var5;
         var9 = a(var0, var10, var11);
      } else if (var2 == RuinedPortalPiece.b.b) {
         var9 = var4 - var5 + MathHelper.b(var0, 2, 8);
      } else {
         var9 = var4;
      }

      List<BlockPosition> var11 = ImmutableList.of(
         new BlockPosition(var6.g(), 0, var6.i()),
         new BlockPosition(var6.j(), 0, var6.i()),
         new BlockPosition(var6.g(), 0, var6.l()),
         new BlockPosition(var6.j(), 0, var6.l())
      );
      List<BlockColumn> var12 = var11.stream().map(var3x -> var1.a(var3x.u(), var3x.w(), var7, var8)).collect(Collectors.toList());
      HeightMap.Type var13 = var2 == RuinedPortalPiece.b.c ? HeightMap.Type.c : HeightMap.Type.a;

      int var14;
      for(var14 = var9; var14 > var10; --var14) {
         int var15 = 0;

         for(BlockColumn var17 : var12) {
            IBlockData var18 = var17.a(var14);
            if (var13.e().test(var18)) {
               if (++var15 == 3) {
                  return var14;
               }
            }
         }
      }

      return var14;
   }

   private static int a(RandomSource var0, int var1, int var2) {
      return var1 < var2 ? MathHelper.b(var0, var1, var2) : var2;
   }

   @Override
   public StructureType<?> e() {
      return StructureType.l;
   }

   public static record a(
      RuinedPortalPiece.b placement,
      float airPocketProbability,
      float mossiness,
      boolean overgrown,
      boolean vines,
      boolean canBeCold,
      boolean replaceWithBlackstone,
      float weight
   ) {
      private final RuinedPortalPiece.b b;
      private final float c;
      private final float d;
      private final boolean e;
      private final boolean f;
      private final boolean g;
      private final boolean h;
      private final float i;
      public static final Codec<RuinedPortalStructure.a> a = RecordCodecBuilder.create(
         var0 -> var0.group(
                  RuinedPortalPiece.b.g.fieldOf("placement").forGetter(RuinedPortalStructure.a::a),
                  Codec.floatRange(0.0F, 1.0F).fieldOf("air_pocket_probability").forGetter(RuinedPortalStructure.a::b),
                  Codec.floatRange(0.0F, 1.0F).fieldOf("mossiness").forGetter(RuinedPortalStructure.a::c),
                  Codec.BOOL.fieldOf("overgrown").forGetter(RuinedPortalStructure.a::d),
                  Codec.BOOL.fieldOf("vines").forGetter(RuinedPortalStructure.a::e),
                  Codec.BOOL.fieldOf("can_be_cold").forGetter(RuinedPortalStructure.a::f),
                  Codec.BOOL.fieldOf("replace_with_blackstone").forGetter(RuinedPortalStructure.a::g),
                  ExtraCodecs.j.fieldOf("weight").forGetter(RuinedPortalStructure.a::h)
               )
               .apply(var0, RuinedPortalStructure.a::new)
      );

      public a(RuinedPortalPiece.b var0, float var1, float var2, boolean var3, boolean var4, boolean var5, boolean var6, float var7) {
         this.b = var0;
         this.c = var1;
         this.d = var2;
         this.e = var3;
         this.f = var4;
         this.g = var5;
         this.h = var6;
         this.i = var7;
      }

      public RuinedPortalPiece.b a() {
         return this.b;
      }

      public float b() {
         return this.c;
      }

      public float c() {
         return this.d;
      }

      public boolean d() {
         return this.e;
      }

      public boolean e() {
         return this.f;
      }

      public boolean f() {
         return this.g;
      }

      public boolean g() {
         return this.h;
      }

      public float h() {
         return this.i;
      }
   }
}
