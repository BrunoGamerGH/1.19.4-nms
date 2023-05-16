package net.minecraft.advancements;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.MojangsonParser;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class AdvancementDisplay {
   private final IChatBaseComponent a;
   private final IChatBaseComponent b;
   private final ItemStack c;
   @Nullable
   private final MinecraftKey d;
   private final AdvancementFrameType e;
   private final boolean f;
   private final boolean g;
   private final boolean h;
   private float i;
   private float j;

   public AdvancementDisplay(
      ItemStack var0,
      IChatBaseComponent var1,
      IChatBaseComponent var2,
      @Nullable MinecraftKey var3,
      AdvancementFrameType var4,
      boolean var5,
      boolean var6,
      boolean var7
   ) {
      this.a = var1;
      this.b = var2;
      this.c = var0;
      this.d = var3;
      this.e = var4;
      this.f = var5;
      this.g = var6;
      this.h = var7;
   }

   public void a(float var0, float var1) {
      this.i = var0;
      this.j = var1;
   }

   public IChatBaseComponent a() {
      return this.a;
   }

   public IChatBaseComponent b() {
      return this.b;
   }

   public ItemStack c() {
      return this.c;
   }

   @Nullable
   public MinecraftKey d() {
      return this.d;
   }

   public AdvancementFrameType e() {
      return this.e;
   }

   public float f() {
      return this.i;
   }

   public float g() {
      return this.j;
   }

   public boolean h() {
      return this.f;
   }

   public boolean i() {
      return this.g;
   }

   public boolean j() {
      return this.h;
   }

   public static AdvancementDisplay a(JsonObject var0) {
      IChatBaseComponent var1 = IChatBaseComponent.ChatSerializer.a(var0.get("title"));
      IChatBaseComponent var2 = IChatBaseComponent.ChatSerializer.a(var0.get("description"));
      if (var1 != null && var2 != null) {
         ItemStack var3 = b(ChatDeserializer.t(var0, "icon"));
         MinecraftKey var4 = var0.has("background") ? new MinecraftKey(ChatDeserializer.h(var0, "background")) : null;
         AdvancementFrameType var5 = var0.has("frame") ? AdvancementFrameType.a(ChatDeserializer.h(var0, "frame")) : AdvancementFrameType.a;
         boolean var6 = ChatDeserializer.a(var0, "show_toast", true);
         boolean var7 = ChatDeserializer.a(var0, "announce_to_chat", true);
         boolean var8 = ChatDeserializer.a(var0, "hidden", false);
         return new AdvancementDisplay(var3, var1, var2, var4, var5, var6, var7, var8);
      } else {
         throw new JsonSyntaxException("Both title and description must be set");
      }
   }

   private static ItemStack b(JsonObject var0) {
      if (!var0.has("item")) {
         throw new JsonSyntaxException("Unsupported icon type, currently only items are supported (add 'item' key)");
      } else {
         Item var1 = ChatDeserializer.i(var0, "item");
         if (var0.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
         } else {
            ItemStack var2 = new ItemStack(var1);
            if (var0.has("nbt")) {
               try {
                  NBTTagCompound var3 = MojangsonParser.a(ChatDeserializer.a(var0.get("nbt"), "nbt"));
                  var2.c(var3);
               } catch (CommandSyntaxException var4) {
                  throw new JsonSyntaxException("Invalid nbt tag: " + var4.getMessage());
               }
            }

            return var2;
         }
      }
   }

   public void a(PacketDataSerializer var0) {
      var0.a(this.a);
      var0.a(this.b);
      var0.a(this.c);
      var0.a(this.e);
      int var1 = 0;
      if (this.d != null) {
         var1 |= 1;
      }

      if (this.f) {
         var1 |= 2;
      }

      if (this.h) {
         var1 |= 4;
      }

      var0.writeInt(var1);
      if (this.d != null) {
         var0.a(this.d);
      }

      var0.writeFloat(this.i);
      var0.writeFloat(this.j);
   }

   public static AdvancementDisplay b(PacketDataSerializer var0) {
      IChatBaseComponent var1 = var0.l();
      IChatBaseComponent var2 = var0.l();
      ItemStack var3 = var0.r();
      AdvancementFrameType var4 = var0.b(AdvancementFrameType.class);
      int var5 = var0.readInt();
      MinecraftKey var6 = (var5 & 1) != 0 ? var0.t() : null;
      boolean var7 = (var5 & 2) != 0;
      boolean var8 = (var5 & 4) != 0;
      AdvancementDisplay var9 = new AdvancementDisplay(var3, var1, var2, var6, var4, var7, false, var8);
      var9.a(var0.readFloat(), var0.readFloat());
      return var9;
   }

   public JsonElement k() {
      JsonObject var0 = new JsonObject();
      var0.add("icon", this.l());
      var0.add("title", IChatBaseComponent.ChatSerializer.c(this.a));
      var0.add("description", IChatBaseComponent.ChatSerializer.c(this.b));
      var0.addProperty("frame", this.e.a());
      var0.addProperty("show_toast", this.f);
      var0.addProperty("announce_to_chat", this.g);
      var0.addProperty("hidden", this.h);
      if (this.d != null) {
         var0.addProperty("background", this.d.toString());
      }

      return var0;
   }

   private JsonObject l() {
      JsonObject var0 = new JsonObject();
      var0.addProperty("item", BuiltInRegistries.i.b(this.c.c()).toString());
      if (this.c.t()) {
         var0.addProperty("nbt", this.c.u().toString());
      }

      return var0;
   }
}
