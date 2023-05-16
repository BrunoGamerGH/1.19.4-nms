package net.minecraft.commands.arguments.coordinates;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Objects;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.util.MathHelper;
import net.minecraft.world.phys.Vec2F;
import net.minecraft.world.phys.Vec3D;

public class ArgumentVectorPosition implements IVectorPosition {
   public static final char a = '^';
   private final double b;
   private final double c;
   private final double d;

   public ArgumentVectorPosition(double var0, double var2, double var4) {
      this.b = var0;
      this.c = var2;
      this.d = var4;
   }

   @Override
   public Vec3D a(CommandListenerWrapper var0) {
      Vec2F var1 = var0.k();
      Vec3D var2 = var0.m().a(var0);
      float var3 = MathHelper.b((var1.j + 90.0F) * (float) (Math.PI / 180.0));
      float var4 = MathHelper.a((var1.j + 90.0F) * (float) (Math.PI / 180.0));
      float var5 = MathHelper.b(-var1.i * (float) (Math.PI / 180.0));
      float var6 = MathHelper.a(-var1.i * (float) (Math.PI / 180.0));
      float var7 = MathHelper.b((-var1.i + 90.0F) * (float) (Math.PI / 180.0));
      float var8 = MathHelper.a((-var1.i + 90.0F) * (float) (Math.PI / 180.0));
      Vec3D var9 = new Vec3D((double)(var3 * var5), (double)var6, (double)(var4 * var5));
      Vec3D var10 = new Vec3D((double)(var3 * var7), (double)var8, (double)(var4 * var7));
      Vec3D var11 = var9.c(var10).a(-1.0);
      double var12 = var9.c * this.d + var10.c * this.c + var11.c * this.b;
      double var14 = var9.d * this.d + var10.d * this.c + var11.d * this.b;
      double var16 = var9.e * this.d + var10.e * this.c + var11.e * this.b;
      return new Vec3D(var2.c + var12, var2.d + var14, var2.e + var16);
   }

   @Override
   public Vec2F b(CommandListenerWrapper var0) {
      return Vec2F.a;
   }

   @Override
   public boolean a() {
      return true;
   }

   @Override
   public boolean b() {
      return true;
   }

   @Override
   public boolean c() {
      return true;
   }

   public static ArgumentVectorPosition a(StringReader var0) throws CommandSyntaxException {
      int var1 = var0.getCursor();
      double var2 = a(var0, var1);
      if (var0.canRead() && var0.peek() == ' ') {
         var0.skip();
         double var4 = a(var0, var1);
         if (var0.canRead() && var0.peek() == ' ') {
            var0.skip();
            double var6 = a(var0, var1);
            return new ArgumentVectorPosition(var2, var4, var6);
         } else {
            var0.setCursor(var1);
            throw ArgumentVec3.a.createWithContext(var0);
         }
      } else {
         var0.setCursor(var1);
         throw ArgumentVec3.a.createWithContext(var0);
      }
   }

   private static double a(StringReader var0, int var1) throws CommandSyntaxException {
      if (!var0.canRead()) {
         throw ArgumentParserPosition.a.createWithContext(var0);
      } else if (var0.peek() != '^') {
         var0.setCursor(var1);
         throw ArgumentVec3.b.createWithContext(var0);
      } else {
         var0.skip();
         return var0.canRead() && var0.peek() != ' ' ? var0.readDouble() : 0.0;
      }
   }

   @Override
   public boolean equals(Object var0) {
      if (this == var0) {
         return true;
      } else if (!(var0 instanceof ArgumentVectorPosition)) {
         return false;
      } else {
         ArgumentVectorPosition var1 = (ArgumentVectorPosition)var0;
         return this.b == var1.b && this.c == var1.c && this.d == var1.d;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.b, this.c, this.d);
   }
}
