package net.minecraft.network.chat;

import com.google.common.collect.Lists;
import com.google.common.collect.Streams;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.stream.JsonReader;
import com.mojang.brigadier.Message;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.network.chat.contents.BlockDataSource;
import net.minecraft.network.chat.contents.DataSource;
import net.minecraft.network.chat.contents.EntityDataSource;
import net.minecraft.network.chat.contents.KeybindContents;
import net.minecraft.network.chat.contents.LiteralContents;
import net.minecraft.network.chat.contents.NbtContents;
import net.minecraft.network.chat.contents.ScoreContents;
import net.minecraft.network.chat.contents.SelectorContents;
import net.minecraft.network.chat.contents.StorageDataSource;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.util.ChatTypeAdapterFactory;
import net.minecraft.util.FormattedString;

public interface IChatBaseComponent extends Message, IChatFormatted, Iterable<IChatBaseComponent> {
   default Stream<IChatBaseComponent> stream() {
      return Streams.concat(new Stream[]{Stream.of(this), this.c().stream().flatMap(IChatBaseComponent::stream)});
   }

   @Override
   default Iterator<IChatBaseComponent> iterator() {
      return this.stream().iterator();
   }

   ChatModifier a();

   ComponentContents b();

   @Override
   default String getString() {
      return IChatFormatted.super.getString();
   }

   default String a(int i) {
      StringBuilder stringbuilder = new StringBuilder();
      this.a(s -> {
         int j = i - stringbuilder.length();
         if (j <= 0) {
            return a;
         } else {
            stringbuilder.append(s.length() <= j ? s : s.substring(0, j));
            return Optional.empty();
         }
      });
      return stringbuilder.toString();
   }

   List<IChatBaseComponent> c();

   default IChatMutableComponent d() {
      return IChatMutableComponent.a(this.b());
   }

   default IChatMutableComponent e() {
      return new IChatMutableComponent(this.b(), new ArrayList<>(this.c()), this.a());
   }

   FormattedString f();

   @Override
   default <T> Optional<T> a(IChatFormatted.b<T> ichatformatted_b, ChatModifier chatmodifier) {
      ChatModifier chatmodifier1 = this.a().a(chatmodifier);
      Optional<T> optional = this.b().a(ichatformatted_b, chatmodifier1);
      if (optional.isPresent()) {
         return optional;
      } else {
         for(IChatBaseComponent ichatbasecomponent : this.c()) {
            Optional optional1 = ichatbasecomponent.a(ichatformatted_b, chatmodifier1);
            if (optional1.isPresent()) {
               return optional1;
            }
         }

         return Optional.empty();
      }
   }

   @Override
   default <T> Optional<T> a(IChatFormatted.a<T> ichatformatted_a) {
      Optional<T> optional = this.b().a(ichatformatted_a);
      if (optional.isPresent()) {
         return optional;
      } else {
         for(IChatBaseComponent ichatbasecomponent : this.c()) {
            Optional optional1 = ichatbasecomponent.a(ichatformatted_a);
            if (optional1.isPresent()) {
               return optional1;
            }
         }

         return Optional.empty();
      }
   }

   default List<IChatBaseComponent> g() {
      return this.a(ChatModifier.a);
   }

   default List<IChatBaseComponent> a(ChatModifier chatmodifier) {
      List<IChatBaseComponent> list = Lists.newArrayList();
      this.a((chatmodifier1, s) -> {
         if (!s.isEmpty()) {
            list.add(b(s).c(chatmodifier1));
         }

         return Optional.empty();
      }, chatmodifier);
      return list;
   }

   default boolean a(IChatBaseComponent ichatbasecomponent) {
      if (this.equals(ichatbasecomponent)) {
         return true;
      } else {
         List<IChatBaseComponent> list = this.g();
         List<IChatBaseComponent> list1 = ichatbasecomponent.a(this.a());
         return Collections.indexOfSubList(list, list1) != -1;
      }
   }

   static IChatBaseComponent a(@Nullable String s) {
      return (IChatBaseComponent)(s != null ? b(s) : CommonComponents.a);
   }

   static IChatMutableComponent b(String s) {
      return IChatMutableComponent.a(new LiteralContents(s));
   }

   static IChatMutableComponent c(String s) {
      return IChatMutableComponent.a(new TranslatableContents(s, null, TranslatableContents.b));
   }

   static IChatMutableComponent a(String s, Object... aobject) {
      return IChatMutableComponent.a(new TranslatableContents(s, null, aobject));
   }

   static IChatMutableComponent a(String s, @Nullable String s1) {
      return IChatMutableComponent.a(new TranslatableContents(s, s1, TranslatableContents.b));
   }

   static IChatMutableComponent a(String s, @Nullable String s1, Object... aobject) {
      return IChatMutableComponent.a(new TranslatableContents(s, s1, aobject));
   }

   static IChatMutableComponent h() {
      return IChatMutableComponent.a(ComponentContents.a);
   }

   static IChatMutableComponent d(String s) {
      return IChatMutableComponent.a(new KeybindContents(s));
   }

   static IChatMutableComponent a(String s, boolean flag, Optional<IChatBaseComponent> optional, DataSource datasource) {
      return IChatMutableComponent.a(new NbtContents(s, flag, optional, datasource));
   }

   static IChatMutableComponent b(String s, String s1) {
      return IChatMutableComponent.a(new ScoreContents(s, s1));
   }

   static IChatMutableComponent a(String s, Optional<IChatBaseComponent> optional) {
      return IChatMutableComponent.a(new SelectorContents(s, optional));
   }

   public static class ChatSerializer implements JsonDeserializer<IChatMutableComponent>, JsonSerializer<IChatBaseComponent> {
      private static final Gson a = SystemUtils.a(() -> {
         GsonBuilder gsonbuilder = new GsonBuilder();
         gsonbuilder.disableHtmlEscaping();
         gsonbuilder.registerTypeHierarchyAdapter(IChatBaseComponent.class, new IChatBaseComponent.ChatSerializer());
         gsonbuilder.registerTypeHierarchyAdapter(ChatModifier.class, new ChatModifier.ChatModifierSerializer());
         gsonbuilder.registerTypeAdapterFactory(new ChatTypeAdapterFactory());
         return gsonbuilder.create();
      });
      private static final Field b = SystemUtils.a(() -> {
         try {
            new JsonReader(new StringReader(""));
            Field field = JsonReader.class.getDeclaredField("pos");
            field.setAccessible(true);
            return field;
         } catch (NoSuchFieldException var1) {
            throw new IllegalStateException("Couldn't get field 'pos' for JsonReader", var1);
         }
      });
      private static final Field c = SystemUtils.a(() -> {
         try {
            new JsonReader(new StringReader(""));
            Field field = JsonReader.class.getDeclaredField("lineStart");
            field.setAccessible(true);
            return field;
         } catch (NoSuchFieldException var1) {
            throw new IllegalStateException("Couldn't get field 'lineStart' for JsonReader", var1);
         }
      });

      public IChatMutableComponent a(JsonElement jsonelement, Type type, JsonDeserializationContext jsondeserializationcontext) throws JsonParseException {
         if (jsonelement.isJsonPrimitive()) {
            return IChatBaseComponent.b(jsonelement.getAsString());
         } else if (!jsonelement.isJsonObject()) {
            if (jsonelement.isJsonArray()) {
               JsonArray jsonarray = jsonelement.getAsJsonArray();
               IChatMutableComponent ichatmutablecomponent = null;

               for(JsonElement jsonelement1 : jsonarray) {
                  IChatMutableComponent ichatmutablecomponent1 = this.a(jsonelement1, jsonelement1.getClass(), jsondeserializationcontext);
                  if (ichatmutablecomponent == null) {
                     ichatmutablecomponent = ichatmutablecomponent1;
                  } else {
                     ichatmutablecomponent.b(ichatmutablecomponent1);
                  }
               }

               return ichatmutablecomponent;
            } else {
               throw new JsonParseException("Don't know how to turn " + jsonelement + " into a Component");
            }
         } else {
            JsonObject jsonobject = jsonelement.getAsJsonObject();
            IChatMutableComponent ichatmutablecomponent;
            if (jsonobject.has("text")) {
               String s = ChatDeserializer.h(jsonobject, "text");
               ichatmutablecomponent = s.isEmpty() ? IChatBaseComponent.h() : IChatBaseComponent.b(s);
            } else if (jsonobject.has("translate")) {
               String s = ChatDeserializer.h(jsonobject, "translate");
               String s1 = ChatDeserializer.a(jsonobject, "fallback", null);
               if (jsonobject.has("with")) {
                  JsonArray jsonarray1 = ChatDeserializer.u(jsonobject, "with");
                  Object[] aobject = new Object[jsonarray1.size()];

                  for(int i = 0; i < aobject.length; ++i) {
                     aobject[i] = a(this.a(jsonarray1.get(i), type, jsondeserializationcontext));
                  }

                  ichatmutablecomponent = IChatBaseComponent.a(s, s1, aobject);
               } else {
                  ichatmutablecomponent = IChatBaseComponent.a(s, s1);
               }
            } else if (jsonobject.has("score")) {
               JsonObject jsonobject1 = ChatDeserializer.t(jsonobject, "score");
               if (!jsonobject1.has("name") || !jsonobject1.has("objective")) {
                  throw new JsonParseException("A score component needs a least a name and an objective");
               }

               ichatmutablecomponent = IChatBaseComponent.b(ChatDeserializer.h(jsonobject1, "name"), ChatDeserializer.h(jsonobject1, "objective"));
            } else if (jsonobject.has("selector")) {
               Optional<IChatBaseComponent> optional = this.a(type, jsondeserializationcontext, jsonobject);
               ichatmutablecomponent = IChatBaseComponent.a(ChatDeserializer.h(jsonobject, "selector"), optional);
            } else if (jsonobject.has("keybind")) {
               ichatmutablecomponent = IChatBaseComponent.d(ChatDeserializer.h(jsonobject, "keybind"));
            } else {
               if (!jsonobject.has("nbt")) {
                  throw new JsonParseException("Don't know how to turn " + jsonelement + " into a Component");
               }

               String s = ChatDeserializer.h(jsonobject, "nbt");
               Optional<IChatBaseComponent> optional1 = this.a(type, jsondeserializationcontext, jsonobject);
               boolean flag = ChatDeserializer.a(jsonobject, "interpret", false);
               Object object;
               if (jsonobject.has("block")) {
                  object = new BlockDataSource(ChatDeserializer.h(jsonobject, "block"));
               } else if (jsonobject.has("entity")) {
                  object = new EntityDataSource(ChatDeserializer.h(jsonobject, "entity"));
               } else {
                  if (!jsonobject.has("storage")) {
                     throw new JsonParseException("Don't know how to turn " + jsonelement + " into a Component");
                  }

                  object = new StorageDataSource(new MinecraftKey(ChatDeserializer.h(jsonobject, "storage")));
               }

               ichatmutablecomponent = IChatBaseComponent.a(s, flag, optional1, (DataSource)object);
            }

            if (jsonobject.has("extra")) {
               JsonArray jsonarray2 = ChatDeserializer.u(jsonobject, "extra");
               if (jsonarray2.size() <= 0) {
                  throw new JsonParseException("Unexpected empty array of components");
               }

               for(int j = 0; j < jsonarray2.size(); ++j) {
                  ichatmutablecomponent.b(this.a(jsonarray2.get(j), type, jsondeserializationcontext));
               }
            }

            ichatmutablecomponent.b((ChatModifier)jsondeserializationcontext.deserialize(jsonelement, ChatModifier.class));
            return ichatmutablecomponent;
         }
      }

      private static Object a(Object object) {
         if (object instanceof IChatBaseComponent ichatbasecomponent && ichatbasecomponent.a().g() && ichatbasecomponent.c().isEmpty()) {
            ComponentContents componentcontents = ichatbasecomponent.b();
            if (componentcontents instanceof LiteralContents literalcontents) {
               return literalcontents.a();
            }
         }

         return object;
      }

      private Optional<IChatBaseComponent> a(Type type, JsonDeserializationContext jsondeserializationcontext, JsonObject jsonobject) {
         return jsonobject.has("separator") ? Optional.of(this.a(jsonobject.get("separator"), type, jsondeserializationcontext)) : Optional.empty();
      }

      private void a(ChatModifier chatmodifier, JsonObject jsonobject, JsonSerializationContext jsonserializationcontext) {
         JsonElement jsonelement = jsonserializationcontext.serialize(chatmodifier);
         if (jsonelement.isJsonObject()) {
            JsonObject jsonobject1 = (JsonObject)jsonelement;

            for(Entry<String, JsonElement> entry : jsonobject1.entrySet()) {
               jsonobject.add(entry.getKey(), (JsonElement)entry.getValue());
            }
         }
      }

      public JsonElement a(IChatBaseComponent ichatbasecomponent, Type type, JsonSerializationContext jsonserializationcontext) {
         JsonObject jsonobject = new JsonObject();
         if (!ichatbasecomponent.a().g()) {
            this.a(ichatbasecomponent.a(), jsonobject, jsonserializationcontext);
         }

         if (!ichatbasecomponent.c().isEmpty()) {
            JsonArray jsonarray = new JsonArray();

            for(IChatBaseComponent ichatbasecomponent1 : ichatbasecomponent.c()) {
               jsonarray.add(this.a(ichatbasecomponent1, IChatBaseComponent.class, jsonserializationcontext));
            }

            jsonobject.add("extra", jsonarray);
         }

         ComponentContents componentcontents = ichatbasecomponent.b();
         if (componentcontents == ComponentContents.a) {
            jsonobject.addProperty("text", "");
         } else if (componentcontents instanceof LiteralContents literalcontents) {
            jsonobject.addProperty("text", literalcontents.a());
         } else if (componentcontents instanceof TranslatableContents translatablecontents) {
            jsonobject.addProperty("translate", translatablecontents.a());
            String s = translatablecontents.b();
            if (s != null) {
               jsonobject.addProperty("fallback", s);
            }

            if (translatablecontents.c().length > 0) {
               JsonArray jsonarray1 = new JsonArray();

               for(Object object : translatablecontents.c()) {
                  if (object instanceof IChatBaseComponent) {
                     jsonarray1.add(this.a((IChatBaseComponent)object, object.getClass(), jsonserializationcontext));
                  } else {
                     jsonarray1.add(new JsonPrimitive(String.valueOf(object)));
                  }
               }

               jsonobject.add("with", jsonarray1);
            }
         } else if (componentcontents instanceof ScoreContents scorecontents) {
            JsonObject jsonobject1 = new JsonObject();
            jsonobject1.addProperty("name", scorecontents.a());
            jsonobject1.addProperty("objective", scorecontents.c());
            jsonobject.add("score", jsonobject1);
         } else if (componentcontents instanceof SelectorContents selectorcontents) {
            jsonobject.addProperty("selector", selectorcontents.a());
            this.a(jsonserializationcontext, jsonobject, selectorcontents.c());
         } else if (componentcontents instanceof KeybindContents keybindcontents) {
            jsonobject.addProperty("keybind", keybindcontents.a());
         } else {
            if (!(componentcontents instanceof NbtContents)) {
               throw new IllegalArgumentException("Don't know how to serialize " + componentcontents + " as a Component");
            }

            NbtContents nbtcontents = (NbtContents)componentcontents;
            jsonobject.addProperty("nbt", nbtcontents.a());
            jsonobject.addProperty("interpret", nbtcontents.b());
            this.a(jsonserializationcontext, jsonobject, nbtcontents.c());
            DataSource datasource = nbtcontents.d();
            if (datasource instanceof BlockDataSource blockdatasource) {
               jsonobject.addProperty("block", blockdatasource.a());
            } else if (datasource instanceof EntityDataSource entitydatasource) {
               jsonobject.addProperty("entity", entitydatasource.a());
            } else {
               if (!(datasource instanceof StorageDataSource)) {
                  throw new IllegalArgumentException("Don't know how to serialize " + componentcontents + " as a Component");
               }

               StorageDataSource storagedatasource = (StorageDataSource)datasource;
               jsonobject.addProperty("storage", storagedatasource.a().toString());
            }
         }

         return jsonobject;
      }

      private void a(JsonSerializationContext jsonserializationcontext, JsonObject jsonobject, Optional<IChatBaseComponent> optional) {
         optional.ifPresent(
            ichatbasecomponent -> jsonobject.add("separator", this.a(ichatbasecomponent, ichatbasecomponent.getClass(), jsonserializationcontext))
         );
      }

      public static String a(IChatBaseComponent ichatbasecomponent) {
         return a.toJson(ichatbasecomponent);
      }

      public static String b(IChatBaseComponent ichatbasecomponent) {
         return ChatDeserializer.e(c(ichatbasecomponent));
      }

      public static JsonElement c(IChatBaseComponent ichatbasecomponent) {
         return a.toJsonTree(ichatbasecomponent);
      }

      @Nullable
      public static IChatMutableComponent a(String s) {
         return ChatDeserializer.b(a, s, IChatMutableComponent.class, false);
      }

      @Nullable
      public static IChatMutableComponent a(JsonElement jsonelement) {
         return (IChatMutableComponent)a.fromJson(jsonelement, IChatMutableComponent.class);
      }

      @Nullable
      public static IChatMutableComponent b(String s) {
         return ChatDeserializer.b(a, s, IChatMutableComponent.class, true);
      }

      public static IChatMutableComponent a(com.mojang.brigadier.StringReader com_mojang_brigadier_stringreader) {
         try {
            JsonReader jsonreader = new JsonReader(new StringReader(com_mojang_brigadier_stringreader.getRemaining()));
            jsonreader.setLenient(false);
            IChatMutableComponent ichatmutablecomponent = (IChatMutableComponent)a.getAdapter(IChatMutableComponent.class).read(jsonreader);
            com_mojang_brigadier_stringreader.setCursor(com_mojang_brigadier_stringreader.getCursor() + a(jsonreader));
            return ichatmutablecomponent;
         } catch (IOException | StackOverflowError var3) {
            throw new JsonParseException(var3);
         }
      }

      private static int a(JsonReader jsonreader) {
         try {
            return b.getInt(jsonreader) - c.getInt(jsonreader) + 1;
         } catch (IllegalAccessException var2) {
            throw new IllegalStateException("Couldn't read position of JsonReader", var2);
         }
      }
   }
}
