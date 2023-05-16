package net.minecraft.network.chat;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.logging.LogUtils;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.MojangsonParser;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;

public class ChatHoverable {
   static final Logger a = LogUtils.getLogger();
   private final ChatHoverable.EnumHoverAction<?> b;
   private final Object c;

   public <T> ChatHoverable(ChatHoverable.EnumHoverAction<T> var0, T var1) {
      this.b = var0;
      this.c = var1;
   }

   public ChatHoverable.EnumHoverAction<?> a() {
      return this.b;
   }

   @Nullable
   public <T> T a(ChatHoverable.EnumHoverAction<T> var0) {
      return this.b == var0 ? var0.b(this.c) : null;
   }

   @Override
   public boolean equals(Object var0) {
      if (this == var0) {
         return true;
      } else if (var0 != null && this.getClass() == var0.getClass()) {
         ChatHoverable var1 = (ChatHoverable)var0;
         return this.b == var1.b && Objects.equals(this.c, var1.c);
      } else {
         return false;
      }
   }

   @Override
   public String toString() {
      return "HoverEvent{action=" + this.b + ", value='" + this.c + "'}";
   }

   @Override
   public int hashCode() {
      int var0 = this.b.hashCode();
      return 31 * var0 + (this.c != null ? this.c.hashCode() : 0);
   }

   @Nullable
   public static ChatHoverable a(JsonObject var0) {
      String var1 = ChatDeserializer.a(var0, "action", null);
      if (var1 == null) {
         return null;
      } else {
         ChatHoverable.EnumHoverAction<?> var2 = ChatHoverable.EnumHoverAction.a(var1);
         if (var2 == null) {
            return null;
         } else {
            JsonElement var3 = var0.get("contents");
            if (var3 != null) {
               return var2.a(var3);
            } else {
               IChatBaseComponent var4 = IChatBaseComponent.ChatSerializer.a(var0.get("value"));
               return var4 != null ? var2.a(var4) : null;
            }
         }
      }
   }

   public JsonObject b() {
      JsonObject var0 = new JsonObject();
      var0.addProperty("action", this.b.b());
      var0.add("contents", this.b.a(this.c));
      return var0;
   }

   public static class EnumHoverAction<T> {
      public static final ChatHoverable.EnumHoverAction<IChatBaseComponent> a = new ChatHoverable.EnumHoverAction<>(
         "show_text", true, IChatBaseComponent.ChatSerializer::a, IChatBaseComponent.ChatSerializer::c, Function.identity()
      );
      public static final ChatHoverable.EnumHoverAction<ChatHoverable.c> b = new ChatHoverable.EnumHoverAction<>(
         "show_item", true, ChatHoverable.c::a, ChatHoverable.c::b, ChatHoverable.c::a
      );
      public static final ChatHoverable.EnumHoverAction<ChatHoverable.b> c = new ChatHoverable.EnumHoverAction<>(
         "show_entity", true, ChatHoverable.b::a, ChatHoverable.b::a, ChatHoverable.b::a
      );
      private static final Map<String, ChatHoverable.EnumHoverAction<?>> d = Stream.of(a, b, c)
         .collect(ImmutableMap.toImmutableMap(ChatHoverable.EnumHoverAction::b, var0 -> var0));
      private final String e;
      private final boolean f;
      private final Function<JsonElement, T> g;
      private final Function<T, JsonElement> h;
      private final Function<IChatBaseComponent, T> i;

      public EnumHoverAction(String var0, boolean var1, Function<JsonElement, T> var2, Function<T, JsonElement> var3, Function<IChatBaseComponent, T> var4) {
         this.e = var0;
         this.f = var1;
         this.g = var2;
         this.h = var3;
         this.i = var4;
      }

      public boolean a() {
         return this.f;
      }

      public String b() {
         return this.e;
      }

      @Nullable
      public static ChatHoverable.EnumHoverAction<?> a(String var0) {
         return d.get(var0);
      }

      T b(Object var0) {
         return (T)var0;
      }

      @Nullable
      public ChatHoverable a(JsonElement var0) {
         T var1 = this.g.apply((T)var0);
         return var1 == null ? null : new ChatHoverable(this, var1);
      }

      @Nullable
      public ChatHoverable a(IChatBaseComponent var0) {
         T var1 = this.i.apply(var0);
         return var1 == null ? null : new ChatHoverable(this, var1);
      }

      public JsonElement a(Object var0) {
         return (JsonElement)this.h.apply(this.b(var0));
      }

      @Override
      public String toString() {
         return "<action " + this.e + ">";
      }
   }

   public static class b {
      public final EntityTypes<?> a;
      public final UUID b;
      @Nullable
      public final IChatBaseComponent c;
      @Nullable
      private List<IChatBaseComponent> d;

      public b(EntityTypes<?> var0, UUID var1, @Nullable IChatBaseComponent var2) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
      }

      @Nullable
      public static ChatHoverable.b a(JsonElement var0) {
         if (!var0.isJsonObject()) {
            return null;
         } else {
            JsonObject var1 = var0.getAsJsonObject();
            EntityTypes<?> var2 = BuiltInRegistries.h.a(new MinecraftKey(ChatDeserializer.h(var1, "type")));
            UUID var3 = UUID.fromString(ChatDeserializer.h(var1, "id"));
            IChatBaseComponent var4 = IChatBaseComponent.ChatSerializer.a(var1.get("name"));
            return new ChatHoverable.b(var2, var3, var4);
         }
      }

      @Nullable
      public static ChatHoverable.b a(IChatBaseComponent var0) {
         try {
            NBTTagCompound var1 = MojangsonParser.a(var0.getString());
            IChatBaseComponent var2 = IChatBaseComponent.ChatSerializer.a(var1.l("name"));
            EntityTypes<?> var3 = BuiltInRegistries.h.a(new MinecraftKey(var1.l("type")));
            UUID var4 = UUID.fromString(var1.l("id"));
            return new ChatHoverable.b(var3, var4, var2);
         } catch (Exception var5) {
            return null;
         }
      }

      public JsonElement a() {
         JsonObject var0 = new JsonObject();
         var0.addProperty("type", BuiltInRegistries.h.b(this.a).toString());
         var0.addProperty("id", this.b.toString());
         if (this.c != null) {
            var0.add("name", IChatBaseComponent.ChatSerializer.c(this.c));
         }

         return var0;
      }

      public List<IChatBaseComponent> b() {
         if (this.d == null) {
            this.d = Lists.newArrayList();
            if (this.c != null) {
               this.d.add(this.c);
            }

            this.d.add(IChatBaseComponent.a("gui.entity_tooltip.type", this.a.h()));
            this.d.add(IChatBaseComponent.b(this.b.toString()));
         }

         return this.d;
      }

      @Override
      public boolean equals(Object var0) {
         if (this == var0) {
            return true;
         } else if (var0 != null && this.getClass() == var0.getClass()) {
            ChatHoverable.b var1 = (ChatHoverable.b)var0;
            return this.a.equals(var1.a) && this.b.equals(var1.b) && Objects.equals(this.c, var1.c);
         } else {
            return false;
         }
      }

      @Override
      public int hashCode() {
         int var0 = this.a.hashCode();
         var0 = 31 * var0 + this.b.hashCode();
         return 31 * var0 + (this.c != null ? this.c.hashCode() : 0);
      }
   }

   public static class c {
      private final Item a;
      private final int b;
      @Nullable
      private final NBTTagCompound c;
      @Nullable
      private ItemStack d;

      c(Item var0, int var1, @Nullable NBTTagCompound var2) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
      }

      public c(ItemStack var0) {
         this(var0.c(), var0.K(), var0.u() != null ? var0.u().h() : null);
      }

      @Override
      public boolean equals(Object var0) {
         if (this == var0) {
            return true;
         } else if (var0 != null && this.getClass() == var0.getClass()) {
            ChatHoverable.c var1 = (ChatHoverable.c)var0;
            return this.b == var1.b && this.a.equals(var1.a) && Objects.equals(this.c, var1.c);
         } else {
            return false;
         }
      }

      @Override
      public int hashCode() {
         int var0 = this.a.hashCode();
         var0 = 31 * var0 + this.b;
         return 31 * var0 + (this.c != null ? this.c.hashCode() : 0);
      }

      public ItemStack a() {
         if (this.d == null) {
            this.d = new ItemStack(this.a, this.b);
            if (this.c != null) {
               this.d.c(this.c);
            }
         }

         return this.d;
      }

      private static ChatHoverable.c a(JsonElement var0) {
         if (var0.isJsonPrimitive()) {
            return new ChatHoverable.c(BuiltInRegistries.i.a(new MinecraftKey(var0.getAsString())), 1, null);
         } else {
            JsonObject var1 = ChatDeserializer.m(var0, "item");
            Item var2 = BuiltInRegistries.i.a(new MinecraftKey(ChatDeserializer.h(var1, "id")));
            int var3 = ChatDeserializer.a(var1, "count", 1);
            if (var1.has("tag")) {
               String var4 = ChatDeserializer.h(var1, "tag");

               try {
                  NBTTagCompound var5 = MojangsonParser.a(var4);
                  return new ChatHoverable.c(var2, var3, var5);
               } catch (CommandSyntaxException var6) {
                  ChatHoverable.a.warn("Failed to parse tag: {}", var4, var6);
               }
            }

            return new ChatHoverable.c(var2, var3, null);
         }
      }

      @Nullable
      private static ChatHoverable.c a(IChatBaseComponent var0) {
         try {
            NBTTagCompound var1 = MojangsonParser.a(var0.getString());
            return new ChatHoverable.c(ItemStack.a(var1));
         } catch (CommandSyntaxException var2) {
            ChatHoverable.a.warn("Failed to parse item tag: {}", var0, var2);
            return null;
         }
      }

      private JsonElement b() {
         JsonObject var0 = new JsonObject();
         var0.addProperty("id", BuiltInRegistries.i.b(this.a).toString());
         if (this.b != 1) {
            var0.addProperty("count", this.b);
         }

         if (this.c != null) {
            var0.addProperty("tag", this.c.toString());
         }

         return var0;
      }
   }
}
