package net.minecraft.nbt;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class StringTagVisitor implements TagVisitor {
   private static final Pattern a = Pattern.compile("[A-Za-z0-9._+-]+");
   private final StringBuilder b = new StringBuilder();

   public String a(NBTBase var0) {
      var0.a(this);
      return this.b.toString();
   }

   @Override
   public void a(NBTTagString var0) {
      this.b.append(NBTTagString.b(var0.f_()));
   }

   @Override
   public void a(NBTTagByte var0) {
      this.b.append(var0.l()).append('b');
   }

   @Override
   public void a(NBTTagShort var0) {
      this.b.append(var0.l()).append('s');
   }

   @Override
   public void a(NBTTagInt var0) {
      this.b.append(var0.l());
   }

   @Override
   public void a(NBTTagLong var0) {
      this.b.append(var0.l()).append('L');
   }

   @Override
   public void a(NBTTagFloat var0) {
      this.b.append(var0.k()).append('f');
   }

   @Override
   public void a(NBTTagDouble var0) {
      this.b.append(var0.j()).append('d');
   }

   @Override
   public void a(NBTTagByteArray var0) {
      this.b.append("[B;");
      byte[] var1 = var0.e();

      for(int var2 = 0; var2 < var1.length; ++var2) {
         if (var2 != 0) {
            this.b.append(',');
         }

         this.b.append(var1[var2]).append('B');
      }

      this.b.append(']');
   }

   @Override
   public void a(NBTTagIntArray var0) {
      this.b.append("[I;");
      int[] var1 = var0.g();

      for(int var2 = 0; var2 < var1.length; ++var2) {
         if (var2 != 0) {
            this.b.append(',');
         }

         this.b.append(var1[var2]);
      }

      this.b.append(']');
   }

   @Override
   public void a(NBTTagLongArray var0) {
      this.b.append("[L;");
      long[] var1 = var0.g();

      for(int var2 = 0; var2 < var1.length; ++var2) {
         if (var2 != 0) {
            this.b.append(',');
         }

         this.b.append(var1[var2]).append('L');
      }

      this.b.append(']');
   }

   @Override
   public void a(NBTTagList var0) {
      this.b.append('[');

      for(int var1 = 0; var1 < var0.size(); ++var1) {
         if (var1 != 0) {
            this.b.append(',');
         }

         this.b.append(new StringTagVisitor().a(var0.k(var1)));
      }

      this.b.append(']');
   }

   @Override
   public void a(NBTTagCompound var0) {
      this.b.append('{');
      List<String> var1 = Lists.newArrayList(var0.e());
      Collections.sort(var1);

      for(String var3 : var1) {
         if (this.b.length() != 1) {
            this.b.append(',');
         }

         this.b.append(a(var3)).append(':').append(new StringTagVisitor().a(var0.c(var3)));
      }

      this.b.append('}');
   }

   protected static String a(String var0) {
      return a.matcher(var0).matches() ? var0 : NBTTagString.b(var0);
   }

   @Override
   public void a(NBTTagEnd var0) {
      this.b.append("END");
   }
}
