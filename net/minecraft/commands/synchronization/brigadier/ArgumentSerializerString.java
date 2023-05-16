package net.minecraft.commands.synchronization.brigadier;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType.StringType;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.network.PacketDataSerializer;

public class ArgumentSerializerString implements ArgumentTypeInfo<StringArgumentType, ArgumentSerializerString.a> {
   public void a(ArgumentSerializerString.a var0, PacketDataSerializer var1) {
      var1.a(var0.b);
   }

   public ArgumentSerializerString.a a(PacketDataSerializer var0) {
      StringType var1 = var0.b(StringType.class);
      return new ArgumentSerializerString.a(var1);
   }

   public void a(ArgumentSerializerString.a var0, JsonObject var1) {
      var1.addProperty("type", switch(var0.b) {
         case SINGLE_WORD -> "word";
         case QUOTABLE_PHRASE -> "phrase";
         case GREEDY_PHRASE -> "greedy";
         default -> throw new IncompatibleClassChangeError();
      });
   }

   public ArgumentSerializerString.a a(StringArgumentType var0) {
      return new ArgumentSerializerString.a(var0.getType());
   }

   public final class a implements ArgumentTypeInfo.a<StringArgumentType> {
      final StringType b;

      public a(StringType var1) {
         this.b = var1;
      }

      public StringArgumentType a(CommandBuildContext var0) {
         return switch(this.b) {
            case SINGLE_WORD -> StringArgumentType.word();
            case QUOTABLE_PHRASE -> StringArgumentType.string();
            case GREEDY_PHRASE -> StringArgumentType.greedyString();
            default -> throw new IncompatibleClassChangeError();
         };
      }

      @Override
      public ArgumentTypeInfo<StringArgumentType, ?> a() {
         return ArgumentSerializerString.this;
      }
   }
}
