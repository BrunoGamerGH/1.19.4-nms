package net.minecraft.world.level.levelgen.structure;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.WorldGenFeatureStructurePieceType;

public abstract class WorldGenScatteredPiece extends StructurePiece {
   protected final int a;
   protected final int b;
   protected final int c;
   protected int d = -1;

   protected WorldGenScatteredPiece(WorldGenFeatureStructurePieceType var0, int var1, int var2, int var3, int var4, int var5, int var6, EnumDirection var7) {
      super(var0, 0, StructurePiece.a(var1, var2, var3, var7, var4, var5, var6));
      this.a = var4;
      this.b = var5;
      this.c = var6;
      this.a(var7);
   }

   protected WorldGenScatteredPiece(WorldGenFeatureStructurePieceType var0, NBTTagCompound var1) {
      super(var0, var1);
      this.a = var1.h("Width");
      this.b = var1.h("Height");
      this.c = var1.h("Depth");
      this.d = var1.h("HPos");
   }

   @Override
   protected void a(StructurePieceSerializationContext var0, NBTTagCompound var1) {
      var1.a("Width", this.a);
      var1.a("Height", this.b);
      var1.a("Depth", this.c);
      var1.a("HPos", this.d);
   }

   protected boolean a(GeneratorAccess var0, StructureBoundingBox var1, int var2) {
      if (this.d >= 0) {
         return true;
      } else {
         int var3 = 0;
         int var4 = 0;
         BlockPosition.MutableBlockPosition var5 = new BlockPosition.MutableBlockPosition();

         for(int var6 = this.f.i(); var6 <= this.f.l(); ++var6) {
            for(int var7 = this.f.g(); var7 <= this.f.j(); ++var7) {
               var5.d(var7, 64, var6);
               if (var1.b(var5)) {
                  var3 += var0.a(HeightMap.Type.f, var5).v();
                  ++var4;
               }
            }
         }

         if (var4 == 0) {
            return false;
         } else {
            this.d = var3 / var4;
            this.f.a(0, this.d - this.f.h() + var2, 0);
            return true;
         }
      }
   }

   protected boolean a(GeneratorAccess var0, int var1) {
      if (this.d >= 0) {
         return true;
      } else {
         int var2 = var0.ai();
         boolean var3 = false;
         BlockPosition.MutableBlockPosition var4 = new BlockPosition.MutableBlockPosition();

         for(int var5 = this.f.i(); var5 <= this.f.l(); ++var5) {
            for(int var6 = this.f.g(); var6 <= this.f.j(); ++var6) {
               var4.d(var6, 0, var5);
               var2 = Math.min(var2, var0.a(HeightMap.Type.f, var4).v());
               var3 = true;
            }
         }

         if (!var3) {
            return false;
         } else {
            this.d = var2;
            this.f.a(0, this.d - this.f.h() + var1, 0);
            return true;
         }
      }
   }
}
