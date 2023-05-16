package net.minecraft.commands.arguments;

import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.Dynamic3CommandExceptionType;
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
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.IRegistry;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;

public class ResourceOrTagArgument<T> implements ArgumentType<ResourceOrTagArgument.c<T>> {
   private static final Collection<String> a = Arrays.asList("foo", "foo:bar", "012", "#skeletons", "#minecraft:skeletons");
   private static final Dynamic2CommandExceptionType b = new Dynamic2CommandExceptionType(
      (var0, var1) -> IChatBaseComponent.a("argument.resource_tag.not_found", var0, var1)
   );
   private static final Dynamic3CommandExceptionType c = new Dynamic3CommandExceptionType(
      (var0, var1, var2) -> IChatBaseComponent.a("argument.resource_tag.invalid_type", var0, var1, var2)
   );
   private final HolderLookup<T> d;
   final ResourceKey<? extends IRegistry<T>> e;

   public ResourceOrTagArgument(CommandBuildContext var0, ResourceKey<? extends IRegistry<T>> var1) {
      this.e = var1;
      this.d = var0.a(var1);
   }

   public static <T> ResourceOrTagArgument<T> a(CommandBuildContext var0, ResourceKey<? extends IRegistry<T>> var1) {
      return new ResourceOrTagArgument<>(var0, var1);
   }

   public static <T> ResourceOrTagArgument.c<T> a(CommandContext<CommandListenerWrapper> var0, String var1, ResourceKey<IRegistry<T>> var2) throws CommandSyntaxException {
      ResourceOrTagArgument.c<?> var3 = (ResourceOrTagArgument.c)var0.getArgument(var1, ResourceOrTagArgument.c.class);
      Optional<ResourceOrTagArgument.c<T>> var4 = var3.a(var2);
      return var4.orElseThrow(() -> (CommandSyntaxException)var3.a().map(var1xx -> {
            ResourceKey<?> var2x = var1xx.g();
            return ResourceArgument.b.create(var2x.a(), var2x.b(), var2.a());
         }, var1xx -> {
            TagKey<?> var2x = var1xx.f();
            return c.create(var2x.b(), var2x.a(), var2.a());
         }));
   }

   public ResourceOrTagArgument.c<T> a(StringReader var0) throws CommandSyntaxException {
      if (var0.canRead() && var0.peek() == '#') {
         int var1 = var0.getCursor();

         try {
            var0.skip();
            MinecraftKey var2 = MinecraftKey.a(var0);
            TagKey<T> var3 = TagKey.a(this.e, var2);
            HolderSet.Named<T> var4 = this.d.a(var3).orElseThrow(() -> b.create(var2, this.e.a()));
            return new ResourceOrTagArgument.d<>(var4);
         } catch (CommandSyntaxException var6) {
            var0.setCursor(var1);
            throw var6;
         }
      } else {
         MinecraftKey var1 = MinecraftKey.a(var0);
         ResourceKey<T> var2 = ResourceKey.a(this.e, var1);
         Holder.c<T> var3 = this.d.a(var2).orElseThrow(() -> ResourceArgument.a.create(var1, this.e.a()));
         return new ResourceOrTagArgument.b<>(var3);
      }
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var0, SuggestionsBuilder var1) {
      ICompletionProvider.a(this.d.e().map(TagKey::b), var1, "#");
      return ICompletionProvider.a(this.d.c().map(ResourceKey::a), var1);
   }

   public Collection<String> getExamples() {
      return a;
   }

   public static class a<T> implements ArgumentTypeInfo<ResourceOrTagArgument<T>, ResourceOrTagArgument.a<T>.a> {
      public void a(ResourceOrTagArgument.a<T>.a var0, PacketDataSerializer var1) {
         var1.a(var0.b.a());
      }

      public ResourceOrTagArgument.a<T>.a a(PacketDataSerializer var0) {
         MinecraftKey var1 = var0.t();
         return new ResourceOrTagArgument.a.a(ResourceKey.a(var1));
      }

      public void a(ResourceOrTagArgument.a<T>.a var0, JsonObject var1) {
         var1.addProperty("registry", var0.b.a().toString());
      }

      public ResourceOrTagArgument.a<T>.a a(ResourceOrTagArgument<T> var0) {
         return new ResourceOrTagArgument.a.a(var0.e);
      }

      public final class a implements ArgumentTypeInfo.a<ResourceOrTagArgument<T>> {
         final ResourceKey<? extends IRegistry<T>> b;

         a(ResourceKey var1) {
            this.b = var1;
         }

         public ResourceOrTagArgument<T> a(CommandBuildContext var0) {
            return new ResourceOrTagArgument<>(var0, this.b);
         }

         @Override
         public ArgumentTypeInfo<ResourceOrTagArgument<T>, ?> a() {
            return a.this;
         }
      }
   }

   static record b<T>(Holder.c<T> value) implements ResourceOrTagArgument.c<T> {
      private final Holder.c<T> a;

      b(Holder.c<T> var0) {
         this.a = var0;
      }

      @Override
      public Either<Holder.c<T>, HolderSet.Named<T>> a() {
         return Either.left(this.a);
      }

      @Override
      public <E> Optional<ResourceOrTagArgument.c<E>> a(ResourceKey<? extends IRegistry<E>> var0) {
         return this.a.g().b(var0) ? Optional.of(this) : Optional.empty();
      }

      public boolean a(Holder<T> var0) {
         return var0.equals(this.a);
      }

      @Override
      public String b() {
         return this.a.g().a().toString();
      }

      public Holder.c<T> c() {
         return this.a;
      }
   }

   public interface c<T> extends Predicate<Holder<T>> {
      Either<Holder.c<T>, HolderSet.Named<T>> a();

      <E> Optional<ResourceOrTagArgument.c<E>> a(ResourceKey<? extends IRegistry<E>> var1);

      String b();
   }

   static record d<T>(HolderSet.Named<T> tag) implements ResourceOrTagArgument.c<T> {
      private final HolderSet.Named<T> a;

      d(HolderSet.Named<T> var0) {
         this.a = var0;
      }

      @Override
      public Either<Holder.c<T>, HolderSet.Named<T>> a() {
         return Either.right(this.a);
      }

      @Override
      public <E> Optional<ResourceOrTagArgument.c<E>> a(ResourceKey<? extends IRegistry<E>> var0) {
         return this.a.f().c(var0) ? Optional.of(this) : Optional.empty();
      }

      public boolean a(Holder<T> var0) {
         return this.a.a(var0);
      }

      @Override
      public String b() {
         return "#" + this.a.f().b();
      }

      public HolderSet.Named<T> c() {
         return this.a;
      }
   }
}
