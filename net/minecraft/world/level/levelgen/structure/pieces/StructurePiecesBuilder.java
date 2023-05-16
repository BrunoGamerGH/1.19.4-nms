package net.minecraft.world.level.levelgen.structure.pieces;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.structure.StructureBoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;

public class StructurePiecesBuilder implements StructurePieceAccessor {
   private final List<StructurePiece> a = Lists.newArrayList();

   @Override
   public void a(StructurePiece var0) {
      this.a.add(var0);
   }

   @Nullable
   @Override
   public StructurePiece a(StructureBoundingBox var0) {
      return StructurePiece.a(this.a, var0);
   }

   @Deprecated
   public void a(int var0) {
      for(StructurePiece var2 : this.a) {
         var2.a(0, var0, 0);
      }
   }

   @Deprecated
   public int a(int var0, int var1, RandomSource var2, int var3) {
      int var4 = var0 - var3;
      StructureBoundingBox var5 = this.d();
      int var6 = var5.d() + var1 + 1;
      if (var6 < var4) {
         var6 += var2.a(var4 - var6);
      }

      int var7 = var6 - var5.k();
      this.a(var7);
      return var7;
   }

   /** @deprecated */
   public void a(RandomSource var0, int var1, int var2) {
      StructureBoundingBox var3 = this.d();
      int var4 = var2 - var1 + 1 - var3.d();
      int var5;
      if (var4 > 1) {
         var5 = var1 + var0.a(var4);
      } else {
         var5 = var1;
      }

      int var6 = var5 - var3.h();
      this.a(var6);
   }

   public PiecesContainer a() {
      return new PiecesContainer(this.a);
   }

   public void b() {
      this.a.clear();
   }

   public boolean c() {
      return this.a.isEmpty();
   }

   public StructureBoundingBox d() {
      return StructurePiece.a(this.a.stream());
   }
}
