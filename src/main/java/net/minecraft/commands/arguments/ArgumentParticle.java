package net.minecraft.commands.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.ICompletionProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.Particle;
import net.minecraft.core.particles.ParticleParam;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;

public class ArgumentParticle implements ArgumentType<ParticleParam> {
   private static final Collection<String> b = Arrays.asList("foo", "foo:bar", "particle with options");
   public static final DynamicCommandExceptionType a = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("particle.notFound", var0));
   private final HolderLookup<Particle<?>> c;

   public ArgumentParticle(CommandBuildContext var0) {
      this.c = var0.a(Registries.P);
   }

   public static ArgumentParticle a(CommandBuildContext var0) {
      return new ArgumentParticle(var0);
   }

   public static ParticleParam a(CommandContext<CommandListenerWrapper> var0, String var1) {
      return (ParticleParam)var0.getArgument(var1, ParticleParam.class);
   }

   public ParticleParam a(StringReader var0) throws CommandSyntaxException {
      return a(var0, this.c);
   }

   public Collection<String> getExamples() {
      return b;
   }

   public static ParticleParam a(StringReader var0, HolderLookup<Particle<?>> var1) throws CommandSyntaxException {
      Particle<?> var2 = b(var0, var1);
      return a(var0, var2);
   }

   private static Particle<?> b(StringReader var0, HolderLookup<Particle<?>> var1) throws CommandSyntaxException {
      MinecraftKey var2 = MinecraftKey.a(var0);
      ResourceKey<Particle<?>> var3 = ResourceKey.a(Registries.P, var2);
      return var1.a(var3).orElseThrow(() -> a.create(var2)).a();
   }

   private static <T extends ParticleParam> T a(StringReader var0, Particle<T> var1) throws CommandSyntaxException {
      return var1.d().b(var1, var0);
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var0, SuggestionsBuilder var1) {
      return ICompletionProvider.a(this.c.c().map(ResourceKey::a), var1);
   }
}
