package net.minecraft.util.datafix.fixes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.lang.reflect.Type;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.IChatMutableComponent;
import net.minecraft.util.ChatDeserializer;
import org.apache.commons.lang3.StringUtils;

public class DataConverterSignText extends DataConverterNamedEntity {
   public static final Gson a = new GsonBuilder().registerTypeAdapter(IChatBaseComponent.class, new JsonDeserializer<IChatBaseComponent>() {
      public IChatMutableComponent a(JsonElement var0, Type var1, JsonDeserializationContext var2) throws JsonParseException {
         if (var0.isJsonPrimitive()) {
            return IChatBaseComponent.b(var0.getAsString());
         } else if (var0.isJsonArray()) {
            JsonArray var3 = var0.getAsJsonArray();
            IChatMutableComponent var4 = null;

            for(JsonElement var6 : var3) {
               IChatMutableComponent var7 = this.a(var6, var6.getClass(), var2);
               if (var4 == null) {
                  var4 = var7;
               } else {
                  var4.b(var7);
               }
            }

            return var4;
         } else {
            throw new JsonParseException("Don't know how to turn " + var0 + " into a Component");
         }
      }
   }).create();

   public DataConverterSignText(Schema var0, boolean var1) {
      super(var0, var1, "BlockEntitySignTextStrictJsonFix", DataConverterTypes.l, "Sign");
   }

   private Dynamic<?> a(Dynamic<?> var0, String var1) {
      String var2 = var0.get(var1).asString("");
      IChatBaseComponent var3 = null;
      if (!"null".equals(var2) && !StringUtils.isEmpty(var2)) {
         if (var2.charAt(0) == '"' && var2.charAt(var2.length() - 1) == '"' || var2.charAt(0) == '{' && var2.charAt(var2.length() - 1) == '}') {
            try {
               var3 = ChatDeserializer.b(a, var2, IChatBaseComponent.class, true);
               if (var3 == null) {
                  var3 = CommonComponents.a;
               }
            } catch (Exception var8) {
            }

            if (var3 == null) {
               try {
                  var3 = IChatBaseComponent.ChatSerializer.a(var2);
               } catch (Exception var7) {
               }
            }

            if (var3 == null) {
               try {
                  var3 = IChatBaseComponent.ChatSerializer.b(var2);
               } catch (Exception var6) {
               }
            }

            if (var3 == null) {
               var3 = IChatBaseComponent.b(var2);
            }
         } else {
            var3 = IChatBaseComponent.b(var2);
         }
      } else {
         var3 = CommonComponents.a;
      }

      return var0.set(var1, var0.createString(IChatBaseComponent.ChatSerializer.a(var3)));
   }

   @Override
   protected Typed<?> a(Typed<?> var0) {
      return var0.update(DSL.remainderFinder(), var0x -> {
         var0x = this.a(var0x, "Text1");
         var0x = this.a(var0x, "Text2");
         var0x = this.a(var0x, "Text3");
         return this.a(var0x, "Text4");
      });
   }
}
