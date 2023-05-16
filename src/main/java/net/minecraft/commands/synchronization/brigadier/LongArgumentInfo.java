package net.minecraft.commands.synchronization.brigadier;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.LongArgumentType;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentUtils;
import net.minecraft.network.PacketDataSerializer;

public class LongArgumentInfo implements ArgumentTypeInfo<LongArgumentType, LongArgumentInfo.a> {
   public void a(LongArgumentInfo.a var0, PacketDataSerializer var1) {
      boolean var2 = var0.b != Long.MIN_VALUE;
      boolean var3 = var0.c != Long.MAX_VALUE;
      var1.writeByte(ArgumentUtils.a(var2, var3));
      if (var2) {
         var1.writeLong(var0.b);
      }

      if (var3) {
         var1.writeLong(var0.c);
      }
   }

   public LongArgumentInfo.a a(PacketDataSerializer var0) {
      byte var1 = var0.readByte();
      long var2 = ArgumentUtils.a(var1) ? var0.readLong() : Long.MIN_VALUE;
      long var4 = ArgumentUtils.b(var1) ? var0.readLong() : Long.MAX_VALUE;
      return new LongArgumentInfo.a(var2, var4);
   }

   public void a(LongArgumentInfo.a var0, JsonObject var1) {
      if (var0.b != Long.MIN_VALUE) {
         var1.addProperty("min", var0.b);
      }

      if (var0.c != Long.MAX_VALUE) {
         var1.addProperty("max", var0.c);
      }
   }

   public LongArgumentInfo.a a(LongArgumentType var0) {
      return new LongArgumentInfo.a(var0.getMinimum(), var0.getMaximum());
   }

   public final class a implements ArgumentTypeInfo.a<LongArgumentType> {
      final long b;
      final long c;

      a(long var1, long var3) {
         this.b = var1;
         this.c = var3;
      }

      public LongArgumentType a(CommandBuildContext var0) {
         return LongArgumentType.longArg(this.b, this.c);
      }

      @Override
      public ArgumentTypeInfo<LongArgumentType, ?> a() {
         return LongArgumentInfo.this;
      }
   }
}
