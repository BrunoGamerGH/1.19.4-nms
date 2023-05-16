package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL.TypeReference;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.Const.PrimitiveType;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.PrimitiveCodec;
import net.minecraft.resources.MinecraftKey;

public class DataConverterSchemaNamed extends Schema {
   public static final PrimitiveCodec<String> a = new PrimitiveCodec<String>() {
      public <T> DataResult<String> read(DynamicOps<T> var0, T var1) {
         return var0.getStringValue(var1).map(DataConverterSchemaNamed::a);
      }

      public <T> T a(DynamicOps<T> var0, String var1) {
         return (T)var0.createString(var1);
      }

      @Override
      public String toString() {
         return "NamespacedString";
      }
   };
   private static final Type<String> b = new PrimitiveType(a);

   public DataConverterSchemaNamed(int var0, Schema var1) {
      super(var0, var1);
   }

   public static String a(String var0) {
      MinecraftKey var1 = MinecraftKey.a(var0);
      return var1 != null ? var1.toString() : var0;
   }

   public static Type<String> a() {
      return b;
   }

   public Type<?> getChoiceType(TypeReference var0, String var1) {
      return super.getChoiceType(var0, a(var1));
   }
}
