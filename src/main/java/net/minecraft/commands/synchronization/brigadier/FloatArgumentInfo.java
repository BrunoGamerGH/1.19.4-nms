package net.minecraft.commands.synchronization.brigadier;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.FloatArgumentType;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentUtils;
import net.minecraft.network.PacketDataSerializer;

public class FloatArgumentInfo implements ArgumentTypeInfo<FloatArgumentType, FloatArgumentInfo.a> {
   public void a(FloatArgumentInfo.a var0, PacketDataSerializer var1) {
      boolean var2 = var0.b != -Float.MAX_VALUE;
      boolean var3 = var0.c != Float.MAX_VALUE;
      var1.writeByte(ArgumentUtils.a(var2, var3));
      if (var2) {
         var1.writeFloat(var0.b);
      }

      if (var3) {
         var1.writeFloat(var0.c);
      }
   }

   public FloatArgumentInfo.a a(PacketDataSerializer var0) {
      byte var1 = var0.readByte();
      float var2 = ArgumentUtils.a(var1) ? var0.readFloat() : -Float.MAX_VALUE;
      float var3 = ArgumentUtils.b(var1) ? var0.readFloat() : Float.MAX_VALUE;
      return new FloatArgumentInfo.a(var2, var3);
   }

   public void a(FloatArgumentInfo.a var0, JsonObject var1) {
      if (var0.b != -Float.MAX_VALUE) {
         var1.addProperty("min", var0.b);
      }

      if (var0.c != Float.MAX_VALUE) {
         var1.addProperty("max", var0.c);
      }
   }

   public FloatArgumentInfo.a a(FloatArgumentType var0) {
      return new FloatArgumentInfo.a(var0.getMinimum(), var0.getMaximum());
   }

   public final class a implements ArgumentTypeInfo.a<FloatArgumentType> {
      final float b;
      final float c;

      a(float var1, float var2) {
         this.b = var1;
         this.c = var2;
      }

      public FloatArgumentType a(CommandBuildContext var0) {
         return FloatArgumentType.floatArg(this.b, this.c);
      }

      @Override
      public ArgumentTypeInfo<FloatArgumentType, ?> a() {
         return FloatArgumentInfo.this;
      }
   }
}
