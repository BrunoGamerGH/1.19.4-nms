package net.minecraft.commands.arguments;

import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.datafixers.util.Either;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.ICompletionProvider;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.core.Holder;
import net.minecraft.core.IRegistry;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;

public class ResourceOrTagKeyArgument<T> implements ArgumentType<ResourceOrTagKeyArgument.c<T>> {
   private static final Collection<String> a = Arrays.asList("foo", "foo:bar", "012", "#skeletons", "#minecraft:skeletons");
   final ResourceKey<? extends IRegistry<T>> b;

   public ResourceOrTagKeyArgument(ResourceKey<? extends IRegistry<T>> var0) {
      this.b = var0;
   }

   public static <T> ResourceOrTagKeyArgument<T> a(ResourceKey<? extends IRegistry<T>> var0) {
      return new ResourceOrTagKeyArgument<>(var0);
   }

   public static <T> ResourceOrTagKeyArgument.c<T> a(
      CommandContext<CommandListenerWrapper> var0, String var1, ResourceKey<IRegistry<T>> var2, DynamicCommandExceptionType var3
   ) throws CommandSyntaxException {
      ResourceOrTagKeyArgument.c<?> var4 = (ResourceOrTagKeyArgument.c)var0.getArgument(var1, ResourceOrTagKeyArgument.c.class);
      Optional<ResourceOrTagKeyArgument.c<T>> var5 = var4.a(var2);
      return var5.orElseThrow(() -> var3.create(var4));
   }

   public ResourceOrTagKeyArgument.c<T> a(StringReader var0) throws CommandSyntaxException {
      if (var0.canRead() && var0.peek() == '#') {
         int var1 = var0.getCursor();

         try {
            var0.skip();
            MinecraftKey var2 = MinecraftKey.a(var0);
            return new ResourceOrTagKeyArgument.d<>(TagKey.a(this.b, var2));
         } catch (CommandSyntaxException var4) {
            var0.setCursor(var1);
            throw var4;
         }
      } else {
         MinecraftKey var1 = MinecraftKey.a(var0);
         return new ResourceOrTagKeyArgument.b<>(ResourceKey.a(this.b, var1));
      }
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var0, SuggestionsBuilder var1) {
      Object var4 = var0.getSource();
      return var4 instanceof ICompletionProvider var2 ? var2.a(this.b, ICompletionProvider.a.c, var1, var0) : var1.buildFuture();
   }

   public Collection<String> getExamples() {
      return a;
   }

   public static class a<T> implements ArgumentTypeInfo<ResourceOrTagKeyArgument<T>, ResourceOrTagKeyArgument.a<T>.a> {
      public void a(ResourceOrTagKeyArgument.a<T>.a var0, PacketDataSerializer var1) {
         var1.a(var0.b.a());
      }

      public ResourceOrTagKeyArgument.a<T>.a a(PacketDataSerializer var0) {
         MinecraftKey var1 = var0.t();
         return new ResourceOrTagKeyArgument.a.a(ResourceKey.a(var1));
      }

      public void a(ResourceOrTagKeyArgument.a<T>.a var0, JsonObject var1) {
         var1.addProperty("registry", var0.b.a().toString());
      }

      public ResourceOrTagKeyArgument.a<T>.a a(ResourceOrTagKeyArgument<T> var0) {
         return new ResourceOrTagKeyArgument.a.a(var0.b);
      }

      public final class a implements ArgumentTypeInfo.a<ResourceOrTagKeyArgument<T>> {
         final ResourceKey<? extends IRegistry<T>> b;

         a(ResourceKey var1) {
            this.b = var1;
         }

         public ResourceOrTagKeyArgument<T> a(CommandBuildContext var0) {
            return new ResourceOrTagKeyArgument<>(this.b);
         }

         @Override
         public ArgumentTypeInfo<ResourceOrTagKeyArgument<T>, ?> a() {
            return a.this;
         }
      }
   }

   static record b<T>(ResourceKey<T> key) implements ResourceOrTagKeyArgument.c<T> {
      private final ResourceKey<T> a;

      b(ResourceKey<T> var0) {
         this.a = var0;
      }

      @Override
      public Either<ResourceKey<T>, TagKey<T>> a() {
         return Either.left(this.a);
      }

      @Override
      public <E> Optional<ResourceOrTagKeyArgument.c<E>> a(ResourceKey<? extends IRegistry<E>> var0) {
         return this.a.c(var0).map(ResourceOrTagKeyArgument.b::new);
      }

      public boolean a(Holder<T> var0) {
         return var0.a(this.a);
      }

      @Override
      public String b() {
         return this.a.a().toString();
      }

      public ResourceKey<T> c() {
         return this.a;
      }
   }

   public interface c<T> extends Predicate<Holder<T>> {
      Either<ResourceKey<T>, TagKey<T>> a();

      <E> Optional<ResourceOrTagKeyArgument.c<E>> a(ResourceKey<? extends IRegistry<E>> var1);

      String b();
   }

   static record d<T>(TagKey<T> key) implements ResourceOrTagKeyArgument.c<T> {
      private final TagKey<T> a;

      d(TagKey<T> var0) {
         this.a = var0;
      }

      @Override
      public Either<ResourceKey<T>, TagKey<T>> a() {
         return Either.right(this.a);
      }

      @Override
      public <E> Optional<ResourceOrTagKeyArgument.c<E>> a(ResourceKey<? extends IRegistry<E>> var0) {
         return this.a.d(var0).map(ResourceOrTagKeyArgument.d::new);
      }

      public boolean a(Holder<T> var0) {
         return var0.a(this.a);
      }

      @Override
      public String b() {
         return "#" + this.a.b();
      }

      public TagKey<T> c() {
         return this.a;
      }
   }
}
