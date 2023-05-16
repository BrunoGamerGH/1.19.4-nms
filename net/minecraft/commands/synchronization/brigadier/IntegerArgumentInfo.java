package net.minecraft.commands.synchronization.brigadier;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentUtils;
import net.minecraft.network.PacketDataSerializer;

public class IntegerArgumentInfo implements ArgumentTypeInfo<IntegerArgumentType, IntegerArgumentInfo.a> {
   public void a(IntegerArgumentInfo.a var0, PacketDataSerializer var1) {
      boolean var2 = var0.b != Integer.MIN_VALUE;
      boolean var3 = var0.c != Integer.MAX_VALUE;
      var1.writeByte(ArgumentUtils.a(var2, var3));
      if (var2) {
         var1.writeInt(var0.b);
      }

      if (var3) {
         var1.writeInt(var0.c);
      }
   }

   public IntegerArgumentInfo.a a(PacketDataSerializer var0) {
      byte var1 = var0.readByte();
      int var2 = ArgumentUtils.a(var1) ? var0.readInt() : Integer.MIN_VALUE;
      int var3 = ArgumentUtils.b(var1) ? var0.readInt() : Integer.MAX_VALUE;
      return new IntegerArgumentInfo.a(var2, var3);
   }

   public void a(IntegerArgumentInfo.a var0, JsonObject var1) {
      if (var0.b != Integer.MIN_VALUE) {
         var1.addProperty("min", var0.b);
      }

      if (var0.c != Integer.MAX_VALUE) {
         var1.addProperty("max", var0.c);
      }
   }

   public IntegerArgumentInfo.a a(IntegerArgumentType var0) {
      return new IntegerArgumentInfo.a(var0.getMinimum(), var0.getMaximum());
   }

   public final class a implements ArgumentTypeInfo.a<IntegerArgumentType> {
      final int b;
      final int c;

      a(int var1, int var2) {
         this.b = var1;
         this.c = var2;
      }

      public IntegerArgumentType a(CommandBuildContext var0) {
         return IntegerArgumentType.integer(this.b, this.c);
      }

      @Override
      public ArgumentTypeInfo<IntegerArgumentType, ?> a() {
         return IntegerArgumentInfo.this;
      }
   }
}
