package net.minecraft.server.packs.metadata.pack;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.packs.metadata.MetadataSectionType;
import net.minecraft.util.ChatDeserializer;

public class ResourcePackInfoDeserializer implements MetadataSectionType<ResourcePackInfo> {
   public ResourcePackInfo b(JsonObject var0) {
      IChatBaseComponent var1 = IChatBaseComponent.ChatSerializer.a(var0.get("description"));
      if (var1 == null) {
         throw new JsonParseException("Invalid/missing description!");
      } else {
         int var2 = ChatDeserializer.n(var0, "pack_format");
         return new ResourcePackInfo(var1, var2);
      }
   }

   public JsonObject a(ResourcePackInfo var0) {
      JsonObject var1 = new JsonObject();
      var1.add("description", IChatBaseComponent.ChatSerializer.c(var0.a()));
      var1.addProperty("pack_format", var0.b());
      return var1;
   }

   @Override
   public String a() {
      return "pack";
   }
}
