package net.minecraft.commands.arguments.coordinates;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.world.phys.Vec2F;
import net.minecraft.world.phys.Vec3D;

public class VectorPosition implements IVectorPosition {
   private final ArgumentParserPosition a;
   private final ArgumentParserPosition b;
   private final ArgumentParserPosition c;

   public VectorPosition(ArgumentParserPosition var0, ArgumentParserPosition var1, ArgumentParserPosition var2) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
   }

   @Override
   public Vec3D a(CommandListenerWrapper var0) {
      Vec3D var1 = var0.d();
      return new Vec3D(this.a.a(var1.c), this.b.a(var1.d), this.c.a(var1.e));
   }

   @Override
   public Vec2F b(CommandListenerWrapper var0) {
      Vec2F var1 = var0.k();
      return new Vec2F((float)this.a.a((double)var1.i), (float)this.b.a((double)var1.j));
   }

   @Override
   public boolean a() {
      return this.a.a();
   }

   @Override
   public boolean b() {
      return this.b.a();
   }

   @Override
   public boolean c() {
      return this.c.a();
   }

   @Override
   public boolean equals(Object var0) {
      if (this == var0) {
         return true;
      } else if (!(var0 instanceof VectorPosition)) {
         return false;
      } else {
         VectorPosition var1 = (VectorPosition)var0;
         if (!this.a.equals(var1.a)) {
            return false;
         } else {
            return !this.b.equals(var1.b) ? false : this.c.equals(var1.c);
         }
      }
   }

   public static VectorPosition a(StringReader var0) throws CommandSyntaxException {
      int var1 = var0.getCursor();
      ArgumentParserPosition var2 = ArgumentParserPosition.a(var0);
      if (var0.canRead() && var0.peek() == ' ') {
         var0.skip();
         ArgumentParserPosition var3 = ArgumentParserPosition.a(var0);
         if (var0.canRead() && var0.peek() == ' ') {
            var0.skip();
            ArgumentParserPosition var4 = ArgumentParserPosition.a(var0);
            return new VectorPosition(var2, var3, var4);
         } else {
            var0.setCursor(var1);
            throw ArgumentVec3.a.createWithContext(var0);
         }
      } else {
         var0.setCursor(var1);
         throw ArgumentVec3.a.createWithContext(var0);
      }
   }

   public static VectorPosition a(StringReader var0, boolean var1) throws CommandSyntaxException {
      int var2 = var0.getCursor();
      ArgumentParserPosition var3 = ArgumentParserPosition.a(var0, var1);
      if (var0.canRead() && var0.peek() == ' ') {
         var0.skip();
         ArgumentParserPosition var4 = ArgumentParserPosition.a(var0, false);
         if (var0.canRead() && var0.peek() == ' ') {
            var0.skip();
            ArgumentParserPosition var5 = ArgumentParserPosition.a(var0, var1);
            return new VectorPosition(var3, var4, var5);
         } else {
            var0.setCursor(var2);
            throw ArgumentVec3.a.createWithContext(var0);
         }
      } else {
         var0.setCursor(var2);
         throw ArgumentVec3.a.createWithContext(var0);
      }
   }

   public static VectorPosition a(double var0, double var2, double var4) {
      return new VectorPosition(new ArgumentParserPosition(false, var0), new ArgumentParserPosition(false, var2), new ArgumentParserPosition(false, var4));
   }

   public static VectorPosition a(Vec2F var0) {
      return new VectorPosition(
         new ArgumentParserPosition(false, (double)var0.i), new ArgumentParserPosition(false, (double)var0.j), new ArgumentParserPosition(true, 0.0)
      );
   }

   public static VectorPosition d() {
      return new VectorPosition(new ArgumentParserPosition(true, 0.0), new ArgumentParserPosition(true, 0.0), new ArgumentParserPosition(true, 0.0));
   }

   @Override
   public int hashCode() {
      int var0 = this.a.hashCode();
      var0 = 31 * var0 + this.b.hashCode();
      return 31 * var0 + this.c.hashCode();
   }
}
