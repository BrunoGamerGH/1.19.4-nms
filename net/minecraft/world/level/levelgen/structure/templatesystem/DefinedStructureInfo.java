package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.EnumBlockMirror;
import net.minecraft.world.level.block.EnumBlockRotation;
import net.minecraft.world.level.levelgen.structure.StructureBoundingBox;

public class DefinedStructureInfo {
   private EnumBlockMirror a;
   private EnumBlockRotation b;
   private BlockPosition c;
   private boolean d;
   @Nullable
   private StructureBoundingBox e;
   private boolean f;
   @Nullable
   private RandomSource g;
   public int h = -1;
   private final List<DefinedStructureProcessor> i;
   private boolean j;
   private boolean k;

   public DefinedStructureInfo() {
      this.a = EnumBlockMirror.a;
      this.b = EnumBlockRotation.a;
      this.c = BlockPosition.b;
      this.f = true;
      this.i = Lists.newArrayList();
   }

   public DefinedStructureInfo a() {
      DefinedStructureInfo definedstructureinfo = new DefinedStructureInfo();
      definedstructureinfo.a = this.a;
      definedstructureinfo.b = this.b;
      definedstructureinfo.c = this.c;
      definedstructureinfo.d = this.d;
      definedstructureinfo.e = this.e;
      definedstructureinfo.f = this.f;
      definedstructureinfo.g = this.g;
      definedstructureinfo.h = this.h;
      definedstructureinfo.i.addAll(this.i);
      definedstructureinfo.j = this.j;
      definedstructureinfo.k = this.k;
      return definedstructureinfo;
   }

   public DefinedStructureInfo a(EnumBlockMirror enumblockmirror) {
      this.a = enumblockmirror;
      return this;
   }

   public DefinedStructureInfo a(EnumBlockRotation enumblockrotation) {
      this.b = enumblockrotation;
      return this;
   }

   public DefinedStructureInfo a(BlockPosition blockposition) {
      this.c = blockposition;
      return this;
   }

   public DefinedStructureInfo a(boolean flag) {
      this.d = flag;
      return this;
   }

   public DefinedStructureInfo a(StructureBoundingBox structureboundingbox) {
      this.e = structureboundingbox;
      return this;
   }

   public DefinedStructureInfo a(@Nullable RandomSource randomsource) {
      this.g = randomsource;
      return this;
   }

   public DefinedStructureInfo b(boolean flag) {
      this.f = flag;
      return this;
   }

   public DefinedStructureInfo c(boolean flag) {
      this.j = flag;
      return this;
   }

   public DefinedStructureInfo b() {
      this.i.clear();
      return this;
   }

   public DefinedStructureInfo a(DefinedStructureProcessor definedstructureprocessor) {
      this.i.add(definedstructureprocessor);
      return this;
   }

   public DefinedStructureInfo b(DefinedStructureProcessor definedstructureprocessor) {
      this.i.remove(definedstructureprocessor);
      return this;
   }

   public EnumBlockMirror c() {
      return this.a;
   }

   public EnumBlockRotation d() {
      return this.b;
   }

   public BlockPosition e() {
      return this.c;
   }

   public RandomSource b(@Nullable BlockPosition blockposition) {
      return this.g != null ? this.g : (blockposition == null ? RandomSource.a(SystemUtils.b()) : RandomSource.a(MathHelper.a(blockposition)));
   }

   public boolean f() {
      return this.d;
   }

   @Nullable
   public StructureBoundingBox g() {
      return this.e;
   }

   public boolean h() {
      return this.j;
   }

   public List<DefinedStructureProcessor> i() {
      return this.i;
   }

   public boolean j() {
      return this.f;
   }

   public DefinedStructure.a a(List<DefinedStructure.a> list, @Nullable BlockPosition blockposition) {
      int i = list.size();
      if (i == 0) {
         throw new IllegalStateException("No palettes");
      } else if (this.h > 0) {
         if (this.h >= i) {
            throw new IllegalArgumentException("Palette index out of bounds. Got " + this.h + " where there are only " + i + " palettes available.");
         } else {
            return list.get(this.h);
         }
      } else {
         return list.get(this.b(blockposition).a(i));
      }
   }

   public DefinedStructureInfo d(boolean flag) {
      this.k = flag;
      return this;
   }

   public boolean k() {
      return this.k;
   }
}
