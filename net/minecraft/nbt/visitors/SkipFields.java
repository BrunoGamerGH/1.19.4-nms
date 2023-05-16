package net.minecraft.nbt.visitors;

import java.util.ArrayDeque;
import java.util.Deque;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagType;
import net.minecraft.nbt.StreamTagVisitor;

public class SkipFields extends CollectToTag {
   private final Deque<FieldTree> a = new ArrayDeque<>();

   public SkipFields(FieldSelector... var0) {
      FieldTree var1 = FieldTree.a();

      for(FieldSelector var5 : var0) {
         var1.a(var5);
      }

      this.a.push(var1);
   }

   @Override
   public StreamTagVisitor.a a(NBTTagType<?> var0, String var1) {
      FieldTree var2 = this.a.element();
      if (var2.a(var0, var1)) {
         return StreamTagVisitor.a.b;
      } else {
         if (var0 == NBTTagCompound.b) {
            FieldTree var3 = var2.d().get(var1);
            if (var3 != null) {
               this.a.push(var3);
            }
         }

         return super.a(var0, var1);
      }
   }

   @Override
   public StreamTagVisitor.b b() {
      if (this.e() == this.a.element().b()) {
         this.a.pop();
      }

      return super.b();
   }
}
