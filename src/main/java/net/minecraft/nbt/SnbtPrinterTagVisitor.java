package net.minecraft.nbt;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import net.minecraft.SystemUtils;

public class SnbtPrinterTagVisitor implements TagVisitor {
   private static final Map<String, List<String>> a = SystemUtils.a(Maps.newHashMap(), var0 -> {
      var0.put("{}", Lists.newArrayList(new String[]{"DataVersion", "author", "size", "data", "entities", "palette", "palettes"}));
      var0.put("{}.data.[].{}", Lists.newArrayList(new String[]{"pos", "state", "nbt"}));
      var0.put("{}.entities.[].{}", Lists.newArrayList(new String[]{"blockPos", "pos"}));
   });
   private static final Set<String> b = Sets.newHashSet(new String[]{"{}.size.[]", "{}.data.[].{}", "{}.palette.[].{}", "{}.entities.[].{}"});
   private static final Pattern c = Pattern.compile("[A-Za-z0-9._+-]+");
   private static final String d = String.valueOf(':');
   private static final String e = String.valueOf(',');
   private static final String f = "[";
   private static final String g = "]";
   private static final String h = ";";
   private static final String i = " ";
   private static final String j = "{";
   private static final String k = "}";
   private static final String l = "\n";
   private final String m;
   private final int n;
   private final List<String> o;
   private String p = "";

   public SnbtPrinterTagVisitor() {
      this("    ", 0, Lists.newArrayList());
   }

   public SnbtPrinterTagVisitor(String var0, int var1, List<String> var2) {
      this.m = var0;
      this.n = var1;
      this.o = var2;
   }

   public String a(NBTBase var0) {
      var0.a(this);
      return this.p;
   }

   @Override
   public void a(NBTTagString var0) {
      this.p = NBTTagString.b(var0.f_());
   }

   @Override
   public void a(NBTTagByte var0) {
      this.p = var0.l() + "b";
   }

   @Override
   public void a(NBTTagShort var0) {
      this.p = var0.l() + "s";
   }

   @Override
   public void a(NBTTagInt var0) {
      this.p = String.valueOf(var0.l());
   }

   @Override
   public void a(NBTTagLong var0) {
      this.p = var0.l() + "L";
   }

   @Override
   public void a(NBTTagFloat var0) {
      this.p = var0.k() + "f";
   }

   @Override
   public void a(NBTTagDouble var0) {
      this.p = var0.j() + "d";
   }

   @Override
   public void a(NBTTagByteArray var0) {
      StringBuilder var1 = new StringBuilder("[").append("B").append(";");
      byte[] var2 = var0.e();

      for(int var3 = 0; var3 < var2.length; ++var3) {
         var1.append(" ").append(var2[var3]).append("B");
         if (var3 != var2.length - 1) {
            var1.append(e);
         }
      }

      var1.append("]");
      this.p = var1.toString();
   }

   @Override
   public void a(NBTTagIntArray var0) {
      StringBuilder var1 = new StringBuilder("[").append("I").append(";");
      int[] var2 = var0.g();

      for(int var3 = 0; var3 < var2.length; ++var3) {
         var1.append(" ").append(var2[var3]);
         if (var3 != var2.length - 1) {
            var1.append(e);
         }
      }

      var1.append("]");
      this.p = var1.toString();
   }

   @Override
   public void a(NBTTagLongArray var0) {
      String var1 = "L";
      StringBuilder var2 = new StringBuilder("[").append("L").append(";");
      long[] var3 = var0.g();

      for(int var4 = 0; var4 < var3.length; ++var4) {
         var2.append(" ").append(var3[var4]).append("L");
         if (var4 != var3.length - 1) {
            var2.append(e);
         }
      }

      var2.append("]");
      this.p = var2.toString();
   }

   @Override
   public void a(NBTTagList var0) {
      if (var0.isEmpty()) {
         this.p = "[]";
      } else {
         StringBuilder var1 = new StringBuilder("[");
         this.b("[]");
         String var2 = b.contains(this.a()) ? "" : this.m;
         if (!var2.isEmpty()) {
            var1.append("\n");
         }

         for(int var3 = 0; var3 < var0.size(); ++var3) {
            var1.append(Strings.repeat(var2, this.n + 1));
            var1.append(new SnbtPrinterTagVisitor(var2, this.n + 1, this.o).a(var0.k(var3)));
            if (var3 != var0.size() - 1) {
               var1.append(e).append(var2.isEmpty() ? " " : "\n");
            }
         }

         if (!var2.isEmpty()) {
            var1.append("\n").append(Strings.repeat(var2, this.n));
         }

         var1.append("]");
         this.p = var1.toString();
         this.b();
      }
   }

   @Override
   public void a(NBTTagCompound var0) {
      if (var0.g()) {
         this.p = "{}";
      } else {
         StringBuilder var1 = new StringBuilder("{");
         this.b("{}");
         String var2 = b.contains(this.a()) ? "" : this.m;
         if (!var2.isEmpty()) {
            var1.append("\n");
         }

         Collection<String> var3 = this.b(var0);
         Iterator<String> var4 = var3.iterator();

         while(var4.hasNext()) {
            String var5 = var4.next();
            NBTBase var6 = var0.c(var5);
            this.b(var5);
            var1.append(Strings.repeat(var2, this.n + 1))
               .append(a(var5))
               .append(d)
               .append(" ")
               .append(new SnbtPrinterTagVisitor(var2, this.n + 1, this.o).a(var6));
            this.b();
            if (var4.hasNext()) {
               var1.append(e).append(var2.isEmpty() ? " " : "\n");
            }
         }

         if (!var2.isEmpty()) {
            var1.append("\n").append(Strings.repeat(var2, this.n));
         }

         var1.append("}");
         this.p = var1.toString();
         this.b();
      }
   }

   private void b() {
      this.o.remove(this.o.size() - 1);
   }

   private void b(String var0) {
      this.o.add(var0);
   }

   protected List<String> b(NBTTagCompound var0) {
      Set<String> var1 = Sets.newHashSet(var0.e());
      List<String> var2 = Lists.newArrayList();
      List<String> var3 = a.get(this.a());
      if (var3 != null) {
         for(String var5 : var3) {
            if (var1.remove(var5)) {
               var2.add(var5);
            }
         }

         if (!var1.isEmpty()) {
            var1.stream().sorted().forEach(var2::add);
         }
      } else {
         var2.addAll(var1);
         Collections.sort(var2);
      }

      return var2;
   }

   public String a() {
      return String.join(".", this.o);
   }

   protected static String a(String var0) {
      return c.matcher(var0).matches() ? var0 : NBTTagString.b(var0);
   }

   @Override
   public void a(NBTTagEnd var0) {
   }
}
