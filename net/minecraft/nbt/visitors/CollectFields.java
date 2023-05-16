package net.minecraft.nbt.visitors;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Set;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagType;
import net.minecraft.nbt.StreamTagVisitor;

public class CollectFields extends CollectToTag {
   private int a;
   private final Set<NBTTagType<?>> b;
   private final Deque<FieldTree> c = new ArrayDeque<>();

   public CollectFields(FieldSelector... var0) {
      this.a = var0.length;
      Builder<NBTTagType<?>> var1 = ImmutableSet.builder();
      FieldTree var2 = FieldTree.a();

      for(FieldSelector var6 : var0) {
         var2.a(var6);
         var1.add(var6.b());
      }

      this.c.push(var2);
      var1.add(NBTTagCompound.b);
      this.b = var1.build();
   }

   @Override
   public StreamTagVisitor.b b(NBTTagType<?> var0) {
      return var0 != NBTTagCompound.b ? StreamTagVisitor.b.c : super.b(var0);
   }

   @Override
   public StreamTagVisitor.a a(NBTTagType<?> var0) {
      FieldTree var1 = this.c.element();
      if (this.e() > var1.b()) {
         return super.a(var0);
      } else if (this.a <= 0) {
         return StreamTagVisitor.a.d;
      } else {
         return !this.b.contains(var0) ? StreamTagVisitor.a.b : super.a(var0);
      }
   }

   @Override
   public StreamTagVisitor.a a(NBTTagType<?> var0, String var1) {
      FieldTree var2 = this.c.element();
      if (this.e() > var2.b()) {
         return super.a(var0, var1);
      } else if (var2.c().remove(var1, var0)) {
         --this.a;
         return super.a(var0, var1);
      } else {
         if (var0 == NBTTagCompound.b) {
            FieldTree var3 = var2.d().get(var1);
            if (var3 != null) {
               this.c.push(var3);
               return super.a(var0, var1);
            }
         }

         return StreamTagVisitor.a.b;
      }
   }

   @Override
   public StreamTagVisitor.b b() {
      if (this.e() == this.c.element().b()) {
         this.c.pop();
      }

      return super.b();
   }

   public int c() {
      return this.a;
   }
}
