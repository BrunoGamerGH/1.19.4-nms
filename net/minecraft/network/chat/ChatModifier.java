package net.minecraft.network.chat;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.EnumChatFormat;
import net.minecraft.ResourceKeyInvalidException;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.ChatDeserializer;

public class ChatModifier {
   public static final ChatModifier a = new ChatModifier(null, null, null, null, null, null, null, null, null, null);
   public static final Codec<ChatModifier> b = RecordCodecBuilder.create(
      var0 -> var0.group(
               ChatHexColor.a.optionalFieldOf("color").forGetter(var0x -> Optional.ofNullable(var0x.d)),
               Codec.BOOL.optionalFieldOf("bold").forGetter(var0x -> Optional.ofNullable(var0x.e)),
               Codec.BOOL.optionalFieldOf("italic").forGetter(var0x -> Optional.ofNullable(var0x.f)),
               Codec.BOOL.optionalFieldOf("underlined").forGetter(var0x -> Optional.ofNullable(var0x.g)),
               Codec.BOOL.optionalFieldOf("strikethrough").forGetter(var0x -> Optional.ofNullable(var0x.h)),
               Codec.BOOL.optionalFieldOf("obfuscated").forGetter(var0x -> Optional.ofNullable(var0x.i)),
               Codec.STRING.optionalFieldOf("insertion").forGetter(var0x -> Optional.ofNullable(var0x.l)),
               MinecraftKey.a.optionalFieldOf("font").forGetter(var0x -> Optional.ofNullable(var0x.m))
            )
            .apply(var0, ChatModifier::a)
   );
   public static final MinecraftKey c = new MinecraftKey("minecraft", "default");
   @Nullable
   final ChatHexColor d;
   @Nullable
   final Boolean e;
   @Nullable
   final Boolean f;
   @Nullable
   final Boolean g;
   @Nullable
   final Boolean h;
   @Nullable
   final Boolean i;
   @Nullable
   final ChatClickable j;
   @Nullable
   final ChatHoverable k;
   @Nullable
   final String l;
   @Nullable
   final MinecraftKey m;

   private static ChatModifier a(
      Optional<ChatHexColor> var0,
      Optional<Boolean> var1,
      Optional<Boolean> var2,
      Optional<Boolean> var3,
      Optional<Boolean> var4,
      Optional<Boolean> var5,
      Optional<String> var6,
      Optional<MinecraftKey> var7
   ) {
      return new ChatModifier(
         var0.orElse(null),
         var1.orElse(null),
         var2.orElse(null),
         var3.orElse(null),
         var4.orElse(null),
         var5.orElse(null),
         null,
         null,
         var6.orElse(null),
         var7.orElse(null)
      );
   }

   ChatModifier(
      @Nullable ChatHexColor var0,
      @Nullable Boolean var1,
      @Nullable Boolean var2,
      @Nullable Boolean var3,
      @Nullable Boolean var4,
      @Nullable Boolean var5,
      @Nullable ChatClickable var6,
      @Nullable ChatHoverable var7,
      @Nullable String var8,
      @Nullable MinecraftKey var9
   ) {
      this.d = var0;
      this.e = var1;
      this.f = var2;
      this.g = var3;
      this.h = var4;
      this.i = var5;
      this.j = var6;
      this.k = var7;
      this.l = var8;
      this.m = var9;
   }

   @Nullable
   public ChatHexColor a() {
      return this.d;
   }

   public boolean b() {
      return this.e == Boolean.TRUE;
   }

   public boolean c() {
      return this.f == Boolean.TRUE;
   }

   public boolean d() {
      return this.h == Boolean.TRUE;
   }

   public boolean e() {
      return this.g == Boolean.TRUE;
   }

   public boolean f() {
      return this.i == Boolean.TRUE;
   }

   public boolean g() {
      return this == a;
   }

   @Nullable
   public ChatClickable h() {
      return this.j;
   }

   @Nullable
   public ChatHoverable i() {
      return this.k;
   }

   @Nullable
   public String j() {
      return this.l;
   }

   public MinecraftKey k() {
      return this.m != null ? this.m : c;
   }

   public ChatModifier a(@Nullable ChatHexColor var0) {
      return new ChatModifier(var0, this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.m);
   }

   public ChatModifier a(@Nullable EnumChatFormat var0) {
      return this.a(var0 != null ? ChatHexColor.a(var0) : null);
   }

   public ChatModifier a(int var0) {
      return this.a(ChatHexColor.a(var0));
   }

   public ChatModifier a(@Nullable Boolean var0) {
      return new ChatModifier(this.d, var0, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.m);
   }

   public ChatModifier b(@Nullable Boolean var0) {
      return new ChatModifier(this.d, this.e, var0, this.g, this.h, this.i, this.j, this.k, this.l, this.m);
   }

   public ChatModifier c(@Nullable Boolean var0) {
      return new ChatModifier(this.d, this.e, this.f, var0, this.h, this.i, this.j, this.k, this.l, this.m);
   }

   public ChatModifier d(@Nullable Boolean var0) {
      return new ChatModifier(this.d, this.e, this.f, this.g, var0, this.i, this.j, this.k, this.l, this.m);
   }

   public ChatModifier e(@Nullable Boolean var0) {
      return new ChatModifier(this.d, this.e, this.f, this.g, this.h, var0, this.j, this.k, this.l, this.m);
   }

   public ChatModifier a(@Nullable ChatClickable var0) {
      return new ChatModifier(this.d, this.e, this.f, this.g, this.h, this.i, var0, this.k, this.l, this.m);
   }

   public ChatModifier a(@Nullable ChatHoverable var0) {
      return new ChatModifier(this.d, this.e, this.f, this.g, this.h, this.i, this.j, var0, this.l, this.m);
   }

   public ChatModifier a(@Nullable String var0) {
      return new ChatModifier(this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k, var0, this.m);
   }

   public ChatModifier a(@Nullable MinecraftKey var0) {
      return new ChatModifier(this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l, var0);
   }

   public ChatModifier b(EnumChatFormat var0) {
      ChatHexColor var1 = this.d;
      Boolean var2 = this.e;
      Boolean var3 = this.f;
      Boolean var4 = this.h;
      Boolean var5 = this.g;
      Boolean var6 = this.i;
      switch(var0) {
         case q:
            var6 = true;
            break;
         case r:
            var2 = true;
            break;
         case s:
            var4 = true;
            break;
         case t:
            var5 = true;
            break;
         case u:
            var3 = true;
            break;
         case v:
            return a;
         default:
            var1 = ChatHexColor.a(var0);
      }

      return new ChatModifier(var1, var2, var3, var5, var4, var6, this.j, this.k, this.l, this.m);
   }

   public ChatModifier c(EnumChatFormat var0) {
      ChatHexColor var1 = this.d;
      Boolean var2 = this.e;
      Boolean var3 = this.f;
      Boolean var4 = this.h;
      Boolean var5 = this.g;
      Boolean var6 = this.i;
      switch(var0) {
         case q:
            var6 = true;
            break;
         case r:
            var2 = true;
            break;
         case s:
            var4 = true;
            break;
         case t:
            var5 = true;
            break;
         case u:
            var3 = true;
            break;
         case v:
            return a;
         default:
            var6 = false;
            var2 = false;
            var4 = false;
            var5 = false;
            var3 = false;
            var1 = ChatHexColor.a(var0);
      }

      return new ChatModifier(var1, var2, var3, var5, var4, var6, this.j, this.k, this.l, this.m);
   }

   public ChatModifier a(EnumChatFormat... var0) {
      ChatHexColor var1 = this.d;
      Boolean var2 = this.e;
      Boolean var3 = this.f;
      Boolean var4 = this.h;
      Boolean var5 = this.g;
      Boolean var6 = this.i;

      for(EnumChatFormat var10 : var0) {
         switch(var10) {
            case q:
               var6 = true;
               break;
            case r:
               var2 = true;
               break;
            case s:
               var4 = true;
               break;
            case t:
               var5 = true;
               break;
            case u:
               var3 = true;
               break;
            case v:
               return a;
            default:
               var1 = ChatHexColor.a(var10);
         }
      }

      return new ChatModifier(var1, var2, var3, var5, var4, var6, this.j, this.k, this.l, this.m);
   }

   public ChatModifier a(ChatModifier var0) {
      if (this == a) {
         return var0;
      } else {
         return var0 == a
            ? this
            : new ChatModifier(
               this.d != null ? this.d : var0.d,
               this.e != null ? this.e : var0.e,
               this.f != null ? this.f : var0.f,
               this.g != null ? this.g : var0.g,
               this.h != null ? this.h : var0.h,
               this.i != null ? this.i : var0.i,
               this.j != null ? this.j : var0.j,
               this.k != null ? this.k : var0.k,
               this.l != null ? this.l : var0.l,
               this.m != null ? this.m : var0.m
            );
      }
   }

   @Override
   public String toString() {
      final StringBuilder var0 = new StringBuilder("{");

      class a {
         private boolean c;

         private void a() {
            if (this.c) {
               var0.append(',');
            }

            this.c = true;
         }

         void a(String var0x, @Nullable Boolean var1) {
            if (var1 != null) {
               this.a();
               if (!var1) {
                  var0.append('!');
               }

               var0.append(var0);
            }
         }

         void a(String var0x, @Nullable Object var1) {
            if (var1 != null) {
               this.a();
               var0.append(var0);
               var0.append('=');
               var0.append(var1);
            }
         }
      }

      a var1 = new a();
      var1.a("color", this.d);
      var1.a("bold", this.e);
      var1.a("italic", this.f);
      var1.a("underlined", this.g);
      var1.a("strikethrough", this.h);
      var1.a("obfuscated", this.i);
      var1.a("clickEvent", this.j);
      var1.a("hoverEvent", this.k);
      var1.a("insertion", this.l);
      var1.a("font", this.m);
      var0.append("}");
      return var0.toString();
   }

   @Override
   public boolean equals(Object var0) {
      if (this == var0) {
         return true;
      } else if (!(var0 instanceof ChatModifier)) {
         return false;
      } else {
         ChatModifier var1 = (ChatModifier)var0;
         return this.b() == var1.b()
            && Objects.equals(this.a(), var1.a())
            && this.c() == var1.c()
            && this.f() == var1.f()
            && this.d() == var1.d()
            && this.e() == var1.e()
            && Objects.equals(this.h(), var1.h())
            && Objects.equals(this.i(), var1.i())
            && Objects.equals(this.j(), var1.j())
            && Objects.equals(this.k(), var1.k());
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l);
   }

   public static class ChatModifierSerializer implements JsonDeserializer<ChatModifier>, JsonSerializer<ChatModifier> {
      @Nullable
      public ChatModifier a(JsonElement var0, Type var1, JsonDeserializationContext var2) throws JsonParseException {
         if (var0.isJsonObject()) {
            JsonObject var3 = var0.getAsJsonObject();
            if (var3 == null) {
               return null;
            } else {
               Boolean var4 = a(var3, "bold");
               Boolean var5 = a(var3, "italic");
               Boolean var6 = a(var3, "underlined");
               Boolean var7 = a(var3, "strikethrough");
               Boolean var8 = a(var3, "obfuscated");
               ChatHexColor var9 = e(var3);
               String var10 = d(var3);
               ChatClickable var11 = c(var3);
               ChatHoverable var12 = b(var3);
               MinecraftKey var13 = a(var3);
               return new ChatModifier(var9, var4, var5, var6, var7, var8, var11, var12, var10, var13);
            }
         } else {
            return null;
         }
      }

      @Nullable
      private static MinecraftKey a(JsonObject var0) {
         if (var0.has("font")) {
            String var1 = ChatDeserializer.h(var0, "font");

            try {
               return new MinecraftKey(var1);
            } catch (ResourceKeyInvalidException var3) {
               throw new JsonSyntaxException("Invalid font name: " + var1);
            }
         } else {
            return null;
         }
      }

      @Nullable
      private static ChatHoverable b(JsonObject var0) {
         if (var0.has("hoverEvent")) {
            JsonObject var1 = ChatDeserializer.t(var0, "hoverEvent");
            ChatHoverable var2 = ChatHoverable.a(var1);
            if (var2 != null && var2.a().a()) {
               return var2;
            }
         }

         return null;
      }

      @Nullable
      private static ChatClickable c(JsonObject var0) {
         if (var0.has("clickEvent")) {
            JsonObject var1 = ChatDeserializer.t(var0, "clickEvent");
            String var2 = ChatDeserializer.a(var1, "action", null);
            ChatClickable.EnumClickAction var3 = var2 == null ? null : ChatClickable.EnumClickAction.a(var2);
            String var4 = ChatDeserializer.a(var1, "value", null);
            if (var3 != null && var4 != null && var3.a()) {
               return new ChatClickable(var3, var4);
            }
         }

         return null;
      }

      @Nullable
      private static String d(JsonObject var0) {
         return ChatDeserializer.a(var0, "insertion", null);
      }

      @Nullable
      private static ChatHexColor e(JsonObject var0) {
         if (var0.has("color")) {
            String var1 = ChatDeserializer.h(var0, "color");
            return ChatHexColor.a(var1);
         } else {
            return null;
         }
      }

      @Nullable
      private static Boolean a(JsonObject var0, String var1) {
         return var0.has(var1) ? var0.get(var1).getAsBoolean() : null;
      }

      @Nullable
      public JsonElement a(ChatModifier var0, Type var1, JsonSerializationContext var2) {
         if (var0.g()) {
            return null;
         } else {
            JsonObject var3 = new JsonObject();
            if (var0.e != null) {
               var3.addProperty("bold", var0.e);
            }

            if (var0.f != null) {
               var3.addProperty("italic", var0.f);
            }

            if (var0.g != null) {
               var3.addProperty("underlined", var0.g);
            }

            if (var0.h != null) {
               var3.addProperty("strikethrough", var0.h);
            }

            if (var0.i != null) {
               var3.addProperty("obfuscated", var0.i);
            }

            if (var0.d != null) {
               var3.addProperty("color", var0.d.b());
            }

            if (var0.l != null) {
               var3.add("insertion", var2.serialize(var0.l));
            }

            if (var0.j != null) {
               JsonObject var4 = new JsonObject();
               var4.addProperty("action", var0.j.a().b());
               var4.addProperty("value", var0.j.b());
               var3.add("clickEvent", var4);
            }

            if (var0.k != null) {
               var3.add("hoverEvent", var0.k.b());
            }

            if (var0.m != null) {
               var3.addProperty("font", var0.m.toString());
            }

            return var3;
         }
      }
   }
}
