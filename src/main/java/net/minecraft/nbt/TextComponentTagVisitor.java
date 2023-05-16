package net.minecraft.nbt;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteOpenHashSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import net.minecraft.EnumChatFormat;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.IChatMutableComponent;
import org.slf4j.Logger;

public class TextComponentTagVisitor implements TagVisitor {
   private static final Logger a = LogUtils.getLogger();
   private static final int b = 8;
   private static final ByteCollection c = new ByteOpenHashSet(Arrays.asList((byte)1, (byte)2, (byte)3, (byte)4, (byte)5, (byte)6));
   private static final EnumChatFormat d = EnumChatFormat.l;
   private static final EnumChatFormat e = EnumChatFormat.k;
   private static final EnumChatFormat f = EnumChatFormat.g;
   private static final EnumChatFormat g = EnumChatFormat.m;
   private static final Pattern h = Pattern.compile("[A-Za-z0-9._+-]+");
   private static final String i = String.valueOf(':');
   private static final String j = String.valueOf(',');
   private static final String k = "[";
   private static final String l = "]";
   private static final String m = ";";
   private static final String n = " ";
   private static final String o = "{";
   private static final String p = "}";
   private static final String q = "\n";
   private final String r;
   private final int s;
   private IChatBaseComponent t = CommonComponents.a;

   public TextComponentTagVisitor(String var0, int var1) {
      this.r = var0;
      this.s = var1;
   }

   public IChatBaseComponent a(NBTBase var0) {
      var0.a(this);
      return this.t;
   }

   @Override
   public void a(NBTTagString var0) {
      String var1 = NBTTagString.b(var0.f_());
      String var2 = var1.substring(0, 1);
      IChatBaseComponent var3 = IChatBaseComponent.b(var1.substring(1, var1.length() - 1)).a(e);
      this.t = IChatBaseComponent.b(var2).b(var3).f(var2);
   }

   @Override
   public void a(NBTTagByte var0) {
      IChatBaseComponent var1 = IChatBaseComponent.b("b").a(g);
      this.t = IChatBaseComponent.b(String.valueOf(var0.l())).b(var1).a(f);
   }

   @Override
   public void a(NBTTagShort var0) {
      IChatBaseComponent var1 = IChatBaseComponent.b("s").a(g);
      this.t = IChatBaseComponent.b(String.valueOf(var0.l())).b(var1).a(f);
   }

   @Override
   public void a(NBTTagInt var0) {
      this.t = IChatBaseComponent.b(String.valueOf(var0.l())).a(f);
   }

   @Override
   public void a(NBTTagLong var0) {
      IChatBaseComponent var1 = IChatBaseComponent.b("L").a(g);
      this.t = IChatBaseComponent.b(String.valueOf(var0.l())).b(var1).a(f);
   }

   @Override
   public void a(NBTTagFloat var0) {
      IChatBaseComponent var1 = IChatBaseComponent.b("f").a(g);
      this.t = IChatBaseComponent.b(String.valueOf(var0.k())).b(var1).a(f);
   }

   @Override
   public void a(NBTTagDouble var0) {
      IChatBaseComponent var1 = IChatBaseComponent.b("d").a(g);
      this.t = IChatBaseComponent.b(String.valueOf(var0.j())).b(var1).a(f);
   }

   @Override
   public void a(NBTTagByteArray var0) {
      IChatBaseComponent var1 = IChatBaseComponent.b("B").a(g);
      IChatMutableComponent var2 = IChatBaseComponent.b("[").b(var1).f(";");
      byte[] var3 = var0.e();

      for(int var4 = 0; var4 < var3.length; ++var4) {
         IChatMutableComponent var5 = IChatBaseComponent.b(String.valueOf(var3[var4])).a(f);
         var2.f(" ").b(var5).b(var1);
         if (var4 != var3.length - 1) {
            var2.f(j);
         }
      }

      var2.f("]");
      this.t = var2;
   }

   @Override
   public void a(NBTTagIntArray var0) {
      IChatBaseComponent var1 = IChatBaseComponent.b("I").a(g);
      IChatMutableComponent var2 = IChatBaseComponent.b("[").b(var1).f(";");
      int[] var3 = var0.g();

      for(int var4 = 0; var4 < var3.length; ++var4) {
         var2.f(" ").b(IChatBaseComponent.b(String.valueOf(var3[var4])).a(f));
         if (var4 != var3.length - 1) {
            var2.f(j);
         }
      }

      var2.f("]");
      this.t = var2;
   }

   @Override
   public void a(NBTTagLongArray var0) {
      IChatBaseComponent var1 = IChatBaseComponent.b("L").a(g);
      IChatMutableComponent var2 = IChatBaseComponent.b("[").b(var1).f(";");
      long[] var3 = var0.g();

      for(int var4 = 0; var4 < var3.length; ++var4) {
         IChatBaseComponent var5 = IChatBaseComponent.b(String.valueOf(var3[var4])).a(f);
         var2.f(" ").b(var5).b(var1);
         if (var4 != var3.length - 1) {
            var2.f(j);
         }
      }

      var2.f("]");
      this.t = var2;
   }

   @Override
   public void a(NBTTagList var0) {
      if (var0.isEmpty()) {
         this.t = IChatBaseComponent.b("[]");
      } else if (c.contains(var0.f()) && var0.size() <= 8) {
         String var1 = j + " ";
         IChatMutableComponent var2 = IChatBaseComponent.b("[");

         for(int var3 = 0; var3 < var0.size(); ++var3) {
            if (var3 != 0) {
               var2.f(var1);
            }

            var2.b(new TextComponentTagVisitor(this.r, this.s).a(var0.k(var3)));
         }

         var2.f("]");
         this.t = var2;
      } else {
         IChatMutableComponent var1 = IChatBaseComponent.b("[");
         if (!this.r.isEmpty()) {
            var1.f("\n");
         }

         for(int var2 = 0; var2 < var0.size(); ++var2) {
            IChatMutableComponent var3 = IChatBaseComponent.b(Strings.repeat(this.r, this.s + 1));
            var3.b(new TextComponentTagVisitor(this.r, this.s + 1).a(var0.k(var2)));
            if (var2 != var0.size() - 1) {
               var3.f(j).f(this.r.isEmpty() ? " " : "\n");
            }

            var1.b(var3);
         }

         if (!this.r.isEmpty()) {
            var1.f("\n").f(Strings.repeat(this.r, this.s));
         }

         var1.f("]");
         this.t = var1;
      }
   }

   @Override
   public void a(NBTTagCompound var0) {
      if (var0.g()) {
         this.t = IChatBaseComponent.b("{}");
      } else {
         IChatMutableComponent var1 = IChatBaseComponent.b("{");
         Collection<String> var2 = var0.e();
         if (a.isDebugEnabled()) {
            List<String> var3 = Lists.newArrayList(var0.e());
            Collections.sort(var3);
            var2 = var3;
         }

         if (!this.r.isEmpty()) {
            var1.f("\n");
         }

         IChatMutableComponent var5;
         for(Iterator<String> var3 = var2.iterator(); var3.hasNext(); var1.b(var5)) {
            String var4 = var3.next();
            var5 = IChatBaseComponent.b(Strings.repeat(this.r, this.s + 1))
               .b(a(var4))
               .f(i)
               .f(" ")
               .b(new TextComponentTagVisitor(this.r, this.s + 1).a(var0.c(var4)));
            if (var3.hasNext()) {
               var5.f(j).f(this.r.isEmpty() ? " " : "\n");
            }
         }

         if (!this.r.isEmpty()) {
            var1.f("\n").f(Strings.repeat(this.r, this.s));
         }

         var1.f("}");
         this.t = var1;
      }
   }

   protected static IChatBaseComponent a(String var0) {
      if (h.matcher(var0).matches()) {
         return IChatBaseComponent.b(var0).a(d);
      } else {
         String var1 = NBTTagString.b(var0);
         String var2 = var1.substring(0, 1);
         IChatBaseComponent var3 = IChatBaseComponent.b(var1.substring(1, var1.length() - 1)).a(d);
         return IChatBaseComponent.b(var2).b(var3).f(var2);
      }
   }

   @Override
   public void a(NBTTagEnd var0) {
      this.t = CommonComponents.a;
   }
}
