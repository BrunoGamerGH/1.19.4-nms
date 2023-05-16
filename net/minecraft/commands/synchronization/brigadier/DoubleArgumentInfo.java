package net.minecraft.commands.synchronization.brigadier;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentUtils;
import net.minecraft.network.PacketDataSerializer;

public class DoubleArgumentInfo implements ArgumentTypeInfo<DoubleArgumentType, DoubleArgumentInfo.a> {
   public void a(DoubleArgumentInfo.a var0, PacketDataSerializer var1) {
      boolean var2 = var0.b != -Double.MAX_VALUE;
      boolean var3 = var0.c != Double.MAX_VALUE;
      var1.writeByte(ArgumentUtils.a(var2, var3));
      if (var2) {
         var1.writeDouble(var0.b);
      }

      if (var3) {
         var1.writeDouble(var0.c);
      }
   }

   public DoubleArgumentInfo.a a(PacketDataSerializer var0) {
      byte var1 = var0.readByte();
      double var2 = ArgumentUtils.a(var1) ? var0.readDouble() : -Double.MAX_VALUE;
      double var4 = ArgumentUtils.b(var1) ? var0.readDouble() : Double.MAX_VALUE;
      return new DoubleArgumentInfo.a(var2, var4);
   }

   public void a(DoubleArgumentInfo.a var0, JsonObject var1) {
      if (var0.b != -Double.MAX_VALUE) {
         var1.addProperty("min", var0.b);
      }

      if (var0.c != Double.MAX_VALUE) {
         var1.addProperty("max", var0.c);
      }
   }

   public DoubleArgumentInfo.a a(DoubleArgumentType var0) {
      return new DoubleArgumentInfo.a(var0.getMinimum(), var0.getMaximum());
   }

   public final class a implements ArgumentTypeInfo.a<DoubleArgumentType> {
      final double b;
      final double c;

      a(double var1, double var3) {
         this.b = var1;
         this.c = var3;
      }

      public DoubleArgumentType a(CommandBuildContext var0) {
         return DoubleArgumentType.doubleArg(this.b, this.c);
      }

      @Override
      public ArgumentTypeInfo<DoubleArgumentType, ?> a() {
         return DoubleArgumentInfo.this;
      }
   }
}
