package net.minecraft.commands.arguments;

import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.Dynamic3CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.ICompletionProvider;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffectList;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.attributes.AttributeBase;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;
import net.minecraft.world.level.levelgen.structure.Structure;

public class ResourceArgument<T> implements ArgumentType<Holder.c<T>> {
   private static final Collection<String> c = Arrays.asList("foo", "foo:bar", "012");
   private static final DynamicCommandExceptionType d = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("entity.not_summonable", var0));
   public static final Dynamic2CommandExceptionType a = new Dynamic2CommandExceptionType(
      (var0, var1) -> IChatBaseComponent.a("argument.resource.not_found", var0, var1)
   );
   public static final Dynamic3CommandExceptionType b = new Dynamic3CommandExceptionType(
      (var0, var1, var2) -> IChatBaseComponent.a("argument.resource.invalid_type", var0, var1, var2)
   );
   final ResourceKey<? extends IRegistry<T>> e;
   private final HolderLookup<T> f;

   public ResourceArgument(CommandBuildContext var0, ResourceKey<? extends IRegistry<T>> var1) {
      this.e = var1;
      this.f = var0.a(var1);
   }

   public static <T> ResourceArgument<T> a(CommandBuildContext var0, ResourceKey<? extends IRegistry<T>> var1) {
      return new ResourceArgument<>(var0, var1);
   }

   public static <T> Holder.c<T> a(CommandContext<CommandListenerWrapper> var0, String var1, ResourceKey<IRegistry<T>> var2) throws CommandSyntaxException {
      Holder.c<T> var3 = (Holder.c)var0.getArgument(var1, Holder.c.class);
      ResourceKey<?> var4 = var3.g();
      if (var4.b(var2)) {
         return var3;
      } else {
         throw b.create(var4.a(), var4.b(), var2.a());
      }
   }

   public static Holder.c<AttributeBase> a(CommandContext<CommandListenerWrapper> var0, String var1) throws CommandSyntaxException {
      return a(var0, var1, Registries.b);
   }

   public static Holder.c<WorldGenFeatureConfigured<?, ?>> b(CommandContext<CommandListenerWrapper> var0, String var1) throws CommandSyntaxException {
      return a(var0, var1, Registries.aq);
   }

   public static Holder.c<Structure> c(CommandContext<CommandListenerWrapper> var0, String var1) throws CommandSyntaxException {
      return a(var0, var1, Registries.ax);
   }

   public static Holder.c<EntityTypes<?>> d(CommandContext<CommandListenerWrapper> var0, String var1) throws CommandSyntaxException {
      return a(var0, var1, Registries.r);
   }

   public static Holder.c<EntityTypes<?>> e(CommandContext<CommandListenerWrapper> var0, String var1) throws CommandSyntaxException {
      Holder.c<EntityTypes<?>> var2 = a(var0, var1, Registries.r);
      if (!var2.a().c()) {
         throw d.create(var2.g().a().toString());
      } else {
         return var2;
      }
   }

   public static Holder.c<MobEffectList> f(CommandContext<CommandListenerWrapper> var0, String var1) throws CommandSyntaxException {
      return a(var0, var1, Registries.N);
   }

   public static Holder.c<Enchantment> g(CommandContext<CommandListenerWrapper> var0, String var1) throws CommandSyntaxException {
      return a(var0, var1, Registries.q);
   }

   public Holder.c<T> a(StringReader var0) throws CommandSyntaxException {
      MinecraftKey var1 = MinecraftKey.a(var0);
      ResourceKey<T> var2 = ResourceKey.a(this.e, var1);
      return this.f.a(var2).orElseThrow(() -> a.create(var1, this.e.a()));
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var0, SuggestionsBuilder var1) {
      return ICompletionProvider.a(this.f.c().map(ResourceKey::a), var1);
   }

   public Collection<String> getExamples() {
      return c;
   }

   public static class a<T> implements ArgumentTypeInfo<ResourceArgument<T>, ResourceArgument.a<T>.a> {
      public void a(ResourceArgument.a<T>.a var0, PacketDataSerializer var1) {
         var1.a(var0.b.a());
      }

      public ResourceArgument.a<T>.a a(PacketDataSerializer var0) {
         MinecraftKey var1 = var0.t();
         return new ResourceArgument.a.a(ResourceKey.a(var1));
      }

      public void a(ResourceArgument.a<T>.a var0, JsonObject var1) {
         var1.addProperty("registry", var0.b.a().toString());
      }

      public ResourceArgument.a<T>.a a(ResourceArgument<T> var0) {
         return new ResourceArgument.a.a(var0.e);
      }

      public final class a implements ArgumentTypeInfo.a<ResourceArgument<T>> {
         final ResourceKey<? extends IRegistry<T>> b;

         a(ResourceKey var1) {
            this.b = var1;
         }

         public ResourceArgument<T> a(CommandBuildContext var0) {
            return new ResourceArgument<>(var0, this.b);
         }

         @Override
         public ArgumentTypeInfo<ResourceArgument<T>, ?> a() {
            return a.this;
         }
      }
   }
}
