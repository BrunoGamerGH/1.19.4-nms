package net.minecraft.commands.arguments.coordinates;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.network.chat.IChatBaseComponent;

public class ArgumentParserPosition {
   private static final char c = '~';
   public static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(IChatBaseComponent.c("argument.pos.missing.double"));
   public static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(IChatBaseComponent.c("argument.pos.missing.int"));
   private final boolean d;
   private final double e;

   public ArgumentParserPosition(boolean var0, double var1) {
      this.d = var0;
      this.e = var1;
   }

   public double a(double var0) {
      return this.d ? this.e + var0 : this.e;
   }

   public static ArgumentParserPosition a(StringReader var0, boolean var1) throws CommandSyntaxException {
      if (var0.canRead() && var0.peek() == '^') {
         throw ArgumentVec3.b.createWithContext(var0);
      } else if (!var0.canRead()) {
         throw a.createWithContext(var0);
      } else {
         boolean var2 = b(var0);
         int var3 = var0.getCursor();
         double var4 = var0.canRead() && var0.peek() != ' ' ? var0.readDouble() : 0.0;
         String var6 = var0.getString().substring(var3, var0.getCursor());
         if (var2 && var6.isEmpty()) {
            return new ArgumentParserPosition(true, 0.0);
         } else {
            if (!var6.contains(".") && !var2 && var1) {
               var4 += 0.5;
            }

            return new ArgumentParserPosition(var2, var4);
         }
      }
   }

   public static ArgumentParserPosition a(StringReader var0) throws CommandSyntaxException {
      if (var0.canRead() && var0.peek() == '^') {
         throw ArgumentVec3.b.createWithContext(var0);
      } else if (!var0.canRead()) {
         throw b.createWithContext(var0);
      } else {
         boolean var1 = b(var0);
         double var2;
         if (var0.canRead() && var0.peek() != ' ') {
            var2 = var1 ? var0.readDouble() : (double)var0.readInt();
         } else {
            var2 = 0.0;
         }

         return new ArgumentParserPosition(var1, var2);
      }
   }

   public static boolean b(StringReader var0) {
      boolean var1;
      if (var0.peek() == '~') {
         var1 = true;
         var0.skip();
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public boolean equals(Object var0) {
      if (this == var0) {
         return true;
      } else if (!(var0 instanceof ArgumentParserPosition)) {
         return false;
      } else {
         ArgumentParserPosition var1 = (ArgumentParserPosition)var0;
         if (this.d != var1.d) {
            return false;
         } else {
            return Double.compare(var1.e, this.e) == 0;
         }
      }
   }

   @Override
   public int hashCode() {
      int var0 = this.d ? 1 : 0;
      long var1 = Double.doubleToLongBits(this.e);
      return 31 * var0 + (int)(var1 ^ var1 >>> 32);
   }

   public boolean a() {
      return this.d;
   }
}
