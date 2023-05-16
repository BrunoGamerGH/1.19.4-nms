package net.minecraft.nbt;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.List;
import java.util.regex.Pattern;
import net.minecraft.network.chat.IChatBaseComponent;

public class MojangsonParser {
   public static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(IChatBaseComponent.c("argument.nbt.trailing"));
   public static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(IChatBaseComponent.c("argument.nbt.expected.key"));
   public static final SimpleCommandExceptionType c = new SimpleCommandExceptionType(IChatBaseComponent.c("argument.nbt.expected.value"));
   public static final Dynamic2CommandExceptionType d = new Dynamic2CommandExceptionType(
      (var0, var1) -> IChatBaseComponent.a("argument.nbt.list.mixed", var0, var1)
   );
   public static final Dynamic2CommandExceptionType e = new Dynamic2CommandExceptionType(
      (var0, var1) -> IChatBaseComponent.a("argument.nbt.array.mixed", var0, var1)
   );
   public static final DynamicCommandExceptionType f = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("argument.nbt.array.invalid", var0));
   public static final char g = ',';
   public static final char h = ':';
   private static final char i = '[';
   private static final char j = ']';
   private static final char k = '}';
   private static final char l = '{';
   private static final Pattern m = Pattern.compile("[-+]?(?:[0-9]+[.]|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?", 2);
   private static final Pattern n = Pattern.compile("[-+]?(?:[0-9]+[.]?|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?d", 2);
   private static final Pattern o = Pattern.compile("[-+]?(?:[0-9]+[.]?|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?f", 2);
   private static final Pattern p = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)b", 2);
   private static final Pattern q = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)l", 2);
   private static final Pattern r = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)s", 2);
   private static final Pattern s = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)");
   private final StringReader t;

   public static NBTTagCompound a(String var0) throws CommandSyntaxException {
      return new MojangsonParser(new StringReader(var0)).a();
   }

   @VisibleForTesting
   NBTTagCompound a() throws CommandSyntaxException {
      NBTTagCompound var0 = this.f();
      this.t.skipWhitespace();
      if (this.t.canRead()) {
         throw a.createWithContext(this.t);
      } else {
         return var0;
      }
   }

   public MojangsonParser(StringReader var0) {
      this.t = var0;
   }

   protected String b() throws CommandSyntaxException {
      this.t.skipWhitespace();
      if (!this.t.canRead()) {
         throw b.createWithContext(this.t);
      } else {
         return this.t.readString();
      }
   }

   protected NBTBase c() throws CommandSyntaxException {
      this.t.skipWhitespace();
      int var0 = this.t.getCursor();
      if (StringReader.isQuotedStringStart(this.t.peek())) {
         return NBTTagString.a(this.t.readQuotedString());
      } else {
         String var1 = this.t.readUnquotedString();
         if (var1.isEmpty()) {
            this.t.setCursor(var0);
            throw c.createWithContext(this.t);
         } else {
            return this.b(var1);
         }
      }
   }

   public NBTBase b(String var0) {
      try {
         if (o.matcher(var0).matches()) {
            return NBTTagFloat.a(Float.parseFloat(var0.substring(0, var0.length() - 1)));
         }

         if (p.matcher(var0).matches()) {
            return NBTTagByte.a(Byte.parseByte(var0.substring(0, var0.length() - 1)));
         }

         if (q.matcher(var0).matches()) {
            return NBTTagLong.a(Long.parseLong(var0.substring(0, var0.length() - 1)));
         }

         if (r.matcher(var0).matches()) {
            return NBTTagShort.a(Short.parseShort(var0.substring(0, var0.length() - 1)));
         }

         if (s.matcher(var0).matches()) {
            return NBTTagInt.a(Integer.parseInt(var0));
         }

         if (n.matcher(var0).matches()) {
            return NBTTagDouble.a(Double.parseDouble(var0.substring(0, var0.length() - 1)));
         }

         if (m.matcher(var0).matches()) {
            return NBTTagDouble.a(Double.parseDouble(var0));
         }

         if ("true".equalsIgnoreCase(var0)) {
            return NBTTagByte.c;
         }

         if ("false".equalsIgnoreCase(var0)) {
            return NBTTagByte.b;
         }
      } catch (NumberFormatException var3) {
      }

      return NBTTagString.a(var0);
   }

   public NBTBase d() throws CommandSyntaxException {
      this.t.skipWhitespace();
      if (!this.t.canRead()) {
         throw c.createWithContext(this.t);
      } else {
         char var0 = this.t.peek();
         if (var0 == '{') {
            return this.f();
         } else {
            return var0 == '[' ? this.e() : this.c();
         }
      }
   }

   protected NBTBase e() throws CommandSyntaxException {
      return this.t.canRead(3) && !StringReader.isQuotedStringStart(this.t.peek(1)) && this.t.peek(2) == ';' ? this.h() : this.g();
   }

   public NBTTagCompound f() throws CommandSyntaxException {
      this.a('{');
      NBTTagCompound var0 = new NBTTagCompound();
      this.t.skipWhitespace();

      while(this.t.canRead() && this.t.peek() != '}') {
         int var1 = this.t.getCursor();
         String var2 = this.b();
         if (var2.isEmpty()) {
            this.t.setCursor(var1);
            throw b.createWithContext(this.t);
         }

         this.a(':');
         var0.a(var2, this.d());
         if (!this.i()) {
            break;
         }

         if (!this.t.canRead()) {
            throw b.createWithContext(this.t);
         }
      }

      this.a('}');
      return var0;
   }

   private NBTBase g() throws CommandSyntaxException {
      this.a('[');
      this.t.skipWhitespace();
      if (!this.t.canRead()) {
         throw c.createWithContext(this.t);
      } else {
         NBTTagList var0 = new NBTTagList();
         NBTTagType<?> var1 = null;

         while(this.t.peek() != ']') {
            int var2 = this.t.getCursor();
            NBTBase var3 = this.d();
            NBTTagType<?> var4 = var3.c();
            if (var1 == null) {
               var1 = var4;
            } else if (var4 != var1) {
               this.t.setCursor(var2);
               throw d.createWithContext(this.t, var4.b(), var1.b());
            }

            var0.add(var3);
            if (!this.i()) {
               break;
            }

            if (!this.t.canRead()) {
               throw c.createWithContext(this.t);
            }
         }

         this.a(']');
         return var0;
      }
   }

   public NBTBase h() throws CommandSyntaxException {
      this.a('[');
      int var0 = this.t.getCursor();
      char var1 = this.t.read();
      this.t.read();
      this.t.skipWhitespace();
      if (!this.t.canRead()) {
         throw c.createWithContext(this.t);
      } else if (var1 == 'B') {
         return new NBTTagByteArray(this.a(NBTTagByteArray.a, NBTTagByte.a));
      } else if (var1 == 'L') {
         return new NBTTagLongArray(this.a(NBTTagLongArray.a, NBTTagLong.a));
      } else if (var1 == 'I') {
         return new NBTTagIntArray(this.a(NBTTagIntArray.a, NBTTagInt.a));
      } else {
         this.t.setCursor(var0);
         throw f.createWithContext(this.t, String.valueOf(var1));
      }
   }

   private <T extends Number> List<T> a(NBTTagType<?> var0, NBTTagType<?> var1) throws CommandSyntaxException {
      List<T> var2 = Lists.newArrayList();

      while(this.t.peek() != ']') {
         int var3 = this.t.getCursor();
         NBTBase var4 = this.d();
         NBTTagType<?> var5 = var4.c();
         if (var5 != var1) {
            this.t.setCursor(var3);
            throw e.createWithContext(this.t, var5.b(), var0.b());
         }

         if (var1 == NBTTagByte.a) {
            var2.add((T)((NBTNumber)var4).i());
         } else if (var1 == NBTTagLong.a) {
            var2.add((T)((NBTNumber)var4).f());
         } else {
            var2.add((T)((NBTNumber)var4).g());
         }

         if (!this.i()) {
            break;
         }

         if (!this.t.canRead()) {
            throw c.createWithContext(this.t);
         }
      }

      this.a(']');
      return var2;
   }

   private boolean i() {
      this.t.skipWhitespace();
      if (this.t.canRead() && this.t.peek() == ',') {
         this.t.skip();
         this.t.skipWhitespace();
         return true;
      } else {
         return false;
      }
   }

   private void a(char var0) throws CommandSyntaxException {
      this.t.skipWhitespace();
      this.t.expect(var0);
   }
}
