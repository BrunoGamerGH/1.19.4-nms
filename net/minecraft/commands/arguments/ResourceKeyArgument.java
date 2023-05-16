package net.minecraft.commands.arguments;

import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.ICompletionProvider;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.core.Holder;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.pools.WorldGenFeatureDefinedStructurePoolTemplate;

public class ResourceKeyArgument<T> implements ArgumentType<ResourceKey<T>> {
   private static final Collection<String> a = Arrays.asList("foo", "foo:bar", "012");
   private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("commands.place.feature.invalid", var0));
   private static final DynamicCommandExceptionType c = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("commands.place.structure.invalid", var0));
   private static final DynamicCommandExceptionType d = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("commands.place.jigsaw.invalid", var0));
   final ResourceKey<? extends IRegistry<T>> e;

   public ResourceKeyArgument(ResourceKey<? extends IRegistry<T>> var0) {
      this.e = var0;
   }

   public static <T> ResourceKeyArgument<T> a(ResourceKey<? extends IRegistry<T>> var0) {
      return new ResourceKeyArgument<>(var0);
   }

   private static <T> ResourceKey<T> a(
      CommandContext<CommandListenerWrapper> var0, String var1, ResourceKey<IRegistry<T>> var2, DynamicCommandExceptionType var3
   ) throws CommandSyntaxException {
      ResourceKey<?> var4 = (ResourceKey)var0.getArgument(var1, ResourceKey.class);
      Optional<ResourceKey<T>> var5 = var4.c(var2);
      return var5.orElseThrow(() -> var3.create(var4));
   }

   private static <T> IRegistry<T> a(CommandContext<CommandListenerWrapper> var0, ResourceKey<? extends IRegistry<T>> var1) {
      return ((CommandListenerWrapper)var0.getSource()).l().aX().d(var1);
   }

   private static <T> Holder.c<T> b(CommandContext<CommandListenerWrapper> var0, String var1, ResourceKey<IRegistry<T>> var2, DynamicCommandExceptionType var3) throws CommandSyntaxException {
      ResourceKey<T> var4 = a(var0, var1, var2, var3);
      return a(var0, var2).b(var4).orElseThrow(() -> var3.create(var4.a()));
   }

   public static Holder.c<WorldGenFeatureConfigured<?, ?>> a(CommandContext<CommandListenerWrapper> var0, String var1) throws CommandSyntaxException {
      return b(var0, var1, Registries.aq, b);
   }

   public static Holder.c<Structure> b(CommandContext<CommandListenerWrapper> var0, String var1) throws CommandSyntaxException {
      return b(var0, var1, Registries.ax, c);
   }

   public static Holder.c<WorldGenFeatureDefinedStructurePoolTemplate> c(CommandContext<CommandListenerWrapper> var0, String var1) throws CommandSyntaxException {
      return b(var0, var1, Registries.aA, d);
   }

   public ResourceKey<T> a(StringReader var0) throws CommandSyntaxException {
      MinecraftKey var1 = MinecraftKey.a(var0);
      return ResourceKey.a(this.e, var1);
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var0, SuggestionsBuilder var1) {
      Object var4 = var0.getSource();
      return var4 instanceof ICompletionProvider var2 ? var2.a(this.e, ICompletionProvider.a.b, var1, var0) : var1.buildFuture();
   }

   public Collection<String> getExamples() {
      return a;
   }

   public static class a<T> implements ArgumentTypeInfo<ResourceKeyArgument<T>, ResourceKeyArgument.a<T>.a> {
      public void a(ResourceKeyArgument.a<T>.a var0, PacketDataSerializer var1) {
         var1.a(var0.b.a());
      }

      public ResourceKeyArgument.a<T>.a a(PacketDataSerializer var0) {
         MinecraftKey var1 = var0.t();
         return new ResourceKeyArgument.a.a(ResourceKey.a(var1));
      }

      public void a(ResourceKeyArgument.a<T>.a var0, JsonObject var1) {
         var1.addProperty("registry", var0.b.a().toString());
      }

      public ResourceKeyArgument.a<T>.a a(ResourceKeyArgument<T> var0) {
         return new ResourceKeyArgument.a.a(var0.e);
      }

      public final class a implements ArgumentTypeInfo.a<ResourceKeyArgument<T>> {
         final ResourceKey<? extends IRegistry<T>> b;

         a(ResourceKey var1) {
            this.b = var1;
         }

         public ResourceKeyArgument<T> a(CommandBuildContext var0) {
            return new ResourceKeyArgument<>(this.b);
         }

         @Override
         public ArgumentTypeInfo<ResourceKeyArgument<T>, ?> a() {
            return a.this;
         }
      }
   }
}
