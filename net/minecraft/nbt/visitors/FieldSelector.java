package net.minecraft.nbt.visitors;

import java.util.List;
import net.minecraft.nbt.NBTTagType;

public record FieldSelector(List<String> path, NBTTagType<?> type, String name) {
   private final List<String> a;
   private final NBTTagType<?> b;
   private final String c;

   public FieldSelector(NBTTagType<?> var0, String var1) {
      this(List.of(), var0, var1);
   }

   public FieldSelector(String var0, NBTTagType<?> var1, String var2) {
      this(List.of(var0), var1, var2);
   }

   public FieldSelector(String var0, String var1, NBTTagType<?> var2, String var3) {
      this(List.of(var0, var1), var2, var3);
   }

   public FieldSelector(List<String> var0, NBTTagType<?> var1, String var2) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
   }
}
