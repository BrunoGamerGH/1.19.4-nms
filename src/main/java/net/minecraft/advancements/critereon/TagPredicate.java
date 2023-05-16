package net.minecraft.advancements.critereon;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import javax.annotation.Nullable;
import net.minecraft.core.Holder;
import net.minecraft.core.IRegistry;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ChatDeserializer;

public class TagPredicate<T> {
   private final TagKey<T> a;
   private final boolean b;

   public TagPredicate(TagKey<T> var0, boolean var1) {
      this.a = var0;
      this.b = var1;
   }

   public static <T> TagPredicate<T> a(TagKey<T> var0) {
      return new TagPredicate<>(var0, true);
   }

   public static <T> TagPredicate<T> b(TagKey<T> var0) {
      return new TagPredicate<>(var0, false);
   }

   public boolean a(Holder<T> var0) {
      return var0.a(this.a) == this.b;
   }

   public JsonElement a() {
      JsonObject var0 = new JsonObject();
      var0.addProperty("id", this.a.b().toString());
      var0.addProperty("expected", this.b);
      return var0;
   }

   public static <T> TagPredicate<T> a(@Nullable JsonElement var0, ResourceKey<? extends IRegistry<T>> var1) {
      if (var0 == null) {
         throw new JsonParseException("Expected a tag predicate");
      } else {
         JsonObject var2 = ChatDeserializer.m(var0, "Tag Predicate");
         MinecraftKey var3 = new MinecraftKey(ChatDeserializer.h(var2, "id"));
         boolean var4 = ChatDeserializer.j(var2, "expected");
         return new TagPredicate<>(TagKey.a(var1, var3), var4);
      }
   }
}
