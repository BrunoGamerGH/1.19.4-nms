package net.minecraft.util.datafix;

import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.DSL.TypeReference;
import com.mojang.serialization.Dynamic;
import java.util.Set;
import net.minecraft.SharedConstants;
import net.minecraft.nbt.DynamicOpsNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.fixes.DataConverterTypes;

public enum DataFixTypes {
   a(DataConverterTypes.a),
   b(DataConverterTypes.b),
   c(DataConverterTypes.c),
   d(DataConverterTypes.d),
   e(DataConverterTypes.e),
   f(DataConverterTypes.f),
   g(DataConverterTypes.g),
   h(DataConverterTypes.h),
   i(DataConverterTypes.i),
   j(DataConverterTypes.j),
   k(DataConverterTypes.A),
   l(DataConverterTypes.k);

   public static final Set<TypeReference> m;
   private final TypeReference n;

   private DataFixTypes(TypeReference var2) {
      this.n = var2;
   }

   private static int a() {
      return SharedConstants.b().d().c();
   }

   public <T> Dynamic<T> a(DataFixer var0, Dynamic<T> var1, int var2, int var3) {
      return var0.update(this.n, var1, var2, var3);
   }

   public <T> Dynamic<T> a(DataFixer var0, Dynamic<T> var1, int var2) {
      return this.a(var0, var1, var2, a());
   }

   public NBTTagCompound a(DataFixer var0, NBTTagCompound var1, int var2, int var3) {
      return (NBTTagCompound)this.a(var0, new Dynamic(DynamicOpsNBT.a, var1), var2, var3).getValue();
   }

   public NBTTagCompound a(DataFixer var0, NBTTagCompound var1, int var2) {
      return this.a(var0, var1, var2, a());
   }

   static {
      m = Set.of(a.n);
   }
}
