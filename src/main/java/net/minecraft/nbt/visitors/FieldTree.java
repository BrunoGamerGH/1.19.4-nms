package net.minecraft.nbt.visitors;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.nbt.NBTTagType;

public record FieldTree(int depth, Map<String, NBTTagType<?>> selectedFields, Map<String, FieldTree> fieldsToRecurse) {
   private final int a;
   private final Map<String, NBTTagType<?>> b;
   private final Map<String, FieldTree> c;

   private FieldTree(int var0) {
      this(var0, new HashMap<>(), new HashMap<>());
   }

   public FieldTree(int var0, Map<String, NBTTagType<?>> var1, Map<String, FieldTree> var2) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
   }

   public static FieldTree a() {
      return new FieldTree(1);
   }

   public void a(FieldSelector var0) {
      if (this.a <= var0.a().size()) {
         this.c.computeIfAbsent(var0.a().get(this.a - 1), var0x -> new FieldTree(this.a + 1)).a(var0);
      } else {
         this.b.put(var0.c(), var0.b());
      }
   }

   public boolean a(NBTTagType<?> var0, String var1) {
      return var0.equals(this.c().get(var1));
   }

   public int b() {
      return this.a;
   }

   public Map<String, NBTTagType<?>> c() {
      return this.b;
   }

   public Map<String, FieldTree> d() {
      return this.c;
   }
}
