package net.minecraft.world.level.levelgen.structure;

import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.List;
import java.util.Locale;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.DynamicOpsNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.resources.RegistryOps;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.block.EnumBlockRotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.WorldGenFeatureStructurePieceType;
import net.minecraft.world.level.levelgen.structure.pools.WorldGenFeatureDefinedStructureJigsawJunction;
import net.minecraft.world.level.levelgen.structure.pools.WorldGenFeatureDefinedStructurePoolStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.slf4j.Logger;

public class WorldGenFeaturePillagerOutpostPoolPiece extends StructurePiece {
   private static final Logger d = LogUtils.getLogger();
   protected final WorldGenFeatureDefinedStructurePoolStructure a;
   protected BlockPosition b;
   private final int h;
   protected final EnumBlockRotation c;
   private final List<WorldGenFeatureDefinedStructureJigsawJunction> i = Lists.newArrayList();
   private final StructureTemplateManager j;

   public WorldGenFeaturePillagerOutpostPoolPiece(
      StructureTemplateManager var0,
      WorldGenFeatureDefinedStructurePoolStructure var1,
      BlockPosition var2,
      int var3,
      EnumBlockRotation var4,
      StructureBoundingBox var5
   ) {
      super(WorldGenFeatureStructurePieceType.ad, 0, var5);
      this.j = var0;
      this.a = var1;
      this.b = var2;
      this.h = var3;
      this.c = var4;
   }

   public WorldGenFeaturePillagerOutpostPoolPiece(StructurePieceSerializationContext var0, NBTTagCompound var1) {
      super(WorldGenFeatureStructurePieceType.ad, var1);
      this.j = var0.c();
      this.b = new BlockPosition(var1.h("PosX"), var1.h("PosY"), var1.h("PosZ"));
      this.h = var1.h("ground_level_delta");
      DynamicOps<NBTBase> var2 = RegistryOps.a(DynamicOpsNBT.a, var0.b());
      this.a = (WorldGenFeatureDefinedStructurePoolStructure)WorldGenFeatureDefinedStructurePoolStructure.e
         .parse(var2, var1.p("pool_element"))
         .resultOrPartial(d::error)
         .orElseThrow(() -> new IllegalStateException("Invalid pool element found"));
      this.c = EnumBlockRotation.valueOf(var1.l("rotation"));
      this.f = this.a.a(this.j, this.b, this.c);
      NBTTagList var3 = var1.c("junctions", 10);
      this.i.clear();
      var3.forEach(var1x -> this.i.add(WorldGenFeatureDefinedStructureJigsawJunction.a(new Dynamic(var2, var1x))));
   }

   @Override
   protected void a(StructurePieceSerializationContext var0, NBTTagCompound var1) {
      var1.a("PosX", this.b.u());
      var1.a("PosY", this.b.v());
      var1.a("PosZ", this.b.w());
      var1.a("ground_level_delta", this.h);
      DynamicOps<NBTBase> var2 = RegistryOps.a(DynamicOpsNBT.a, var0.b());
      WorldGenFeatureDefinedStructurePoolStructure.e.encodeStart(var2, this.a).resultOrPartial(d::error).ifPresent(var1x -> var1.a("pool_element", var1x));
      var1.a("rotation", this.c.name());
      NBTTagList var3 = new NBTTagList();

      for(WorldGenFeatureDefinedStructureJigsawJunction var5 : this.i) {
         var3.add((NBTBase)var5.a(var2).getValue());
      }

      var1.a("junctions", var3);
   }

   @Override
   public void a(
      GeneratorAccessSeed var0,
      StructureManager var1,
      ChunkGenerator var2,
      RandomSource var3,
      StructureBoundingBox var4,
      ChunkCoordIntPair var5,
      BlockPosition var6
   ) {
      this.a(var0, var1, var2, var3, var4, var6, false);
   }

   public void a(
      GeneratorAccessSeed var0, StructureManager var1, ChunkGenerator var2, RandomSource var3, StructureBoundingBox var4, BlockPosition var5, boolean var6
   ) {
      this.a.a(this.j, var0, var1, var2, this.b, var5, this.c, var4, var3, var6);
   }

   @Override
   public void a(int var0, int var1, int var2) {
      super.a(var0, var1, var2);
      this.b = this.b.b(var0, var1, var2);
   }

   @Override
   public EnumBlockRotation a() {
      return this.c;
   }

   @Override
   public String toString() {
      return String.format(Locale.ROOT, "<%s | %s | %s | %s>", this.getClass().getSimpleName(), this.b, this.c, this.a);
   }

   public WorldGenFeatureDefinedStructurePoolStructure b() {
      return this.a;
   }

   public BlockPosition c() {
      return this.b;
   }

   public int d() {
      return this.h;
   }

   public void a(WorldGenFeatureDefinedStructureJigsawJunction var0) {
      this.i.add(var0);
   }

   public List<WorldGenFeatureDefinedStructureJigsawJunction> e() {
      return this.i;
   }
}
