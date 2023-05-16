package net.minecraft.world.level.levelgen.structure;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.logging.LogUtils;
import java.util.function.Function;
import net.minecraft.commands.arguments.blocks.ArgumentBlock;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EnumBlockRotation;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockPropertyStructureMode;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.WorldGenFeatureStructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureInfo;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.slf4j.Logger;

public abstract class DefinedStructurePiece extends StructurePiece {
   private static final Logger h = LogUtils.getLogger();
   protected final String a;
   protected DefinedStructure b;
   protected DefinedStructureInfo c;
   protected BlockPosition d;

   public DefinedStructurePiece(
      WorldGenFeatureStructurePieceType var0,
      int var1,
      StructureTemplateManager var2,
      MinecraftKey var3,
      String var4,
      DefinedStructureInfo var5,
      BlockPosition var6
   ) {
      super(var0, var1, var2.a(var3).b(var5, var6));
      this.a(EnumDirection.c);
      this.a = var4;
      this.d = var6;
      this.b = var2.a(var3);
      this.c = var5;
   }

   public DefinedStructurePiece(
      WorldGenFeatureStructurePieceType var0, NBTTagCompound var1, StructureTemplateManager var2, Function<MinecraftKey, DefinedStructureInfo> var3
   ) {
      super(var0, var1);
      this.a(EnumDirection.c);
      this.a = var1.l("Template");
      this.d = new BlockPosition(var1.h("TPX"), var1.h("TPY"), var1.h("TPZ"));
      MinecraftKey var4 = this.b();
      this.b = var2.a(var4);
      this.c = var3.apply(var4);
      this.f = this.b.b(this.c, this.d);
   }

   protected MinecraftKey b() {
      return new MinecraftKey(this.a);
   }

   @Override
   protected void a(StructurePieceSerializationContext var0, NBTTagCompound var1) {
      var1.a("TPX", this.d.u());
      var1.a("TPY", this.d.v());
      var1.a("TPZ", this.d.w());
      var1.a("Template", this.a);
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
      this.c.a(var4);
      this.f = this.b.b(this.c, this.d);
      if (this.b.a(var0, this.d, var6, this.c, var3, 2)) {
         for(DefinedStructure.BlockInfo var9 : this.b.a(this.d, this.c, Blocks.oW)) {
            if (var9.c != null) {
               BlockPropertyStructureMode var10 = BlockPropertyStructureMode.valueOf(var9.c.l("mode"));
               if (var10 == BlockPropertyStructureMode.d) {
                  this.a(var9.c.l("metadata"), var9.a, var0, var3, var4);
               }
            }
         }

         for(DefinedStructure.BlockInfo var10 : this.b.a(this.d, this.c, Blocks.oX)) {
            if (var10.c != null) {
               String var11 = var10.c.l("final_state");
               IBlockData var12 = Blocks.a.o();

               try {
                  var12 = ArgumentBlock.a(var0.a(Registries.e), var11, true).a();
               } catch (CommandSyntaxException var15) {
                  h.error("Error while parsing blockstate {} in jigsaw block @ {}", var11, var10.a);
               }

               var0.a(var10.a, var12, 3);
            }
         }
      }
   }

   protected abstract void a(String var1, BlockPosition var2, WorldAccess var3, RandomSource var4, StructureBoundingBox var5);

   @Deprecated
   @Override
   public void a(int var0, int var1, int var2) {
      super.a(var0, var1, var2);
      this.d = this.d.b(var0, var1, var2);
   }

   @Override
   public EnumBlockRotation a() {
      return this.c.d();
   }

   public DefinedStructure c() {
      return this.b;
   }

   public BlockPosition d() {
      return this.d;
   }

   public DefinedStructureInfo e() {
      return this.c;
   }
}
