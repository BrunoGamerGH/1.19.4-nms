package net.minecraft.commands.synchronization;

import com.google.common.collect.Maps;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import net.minecraft.SystemUtils;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.ICompletionProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.entity.EntityTypes;

public class CompletionProviders {
   private static final Map<MinecraftKey, SuggestionProvider<ICompletionProvider>> e = Maps.newHashMap();
   private static final MinecraftKey f = new MinecraftKey("ask_server");
   public static final SuggestionProvider<ICompletionProvider> a = a(f, (var0, var1) -> ((ICompletionProvider)var0.getSource()).a(var0));
   public static final SuggestionProvider<CommandListenerWrapper> b = a(
      new MinecraftKey("all_recipes"), (var0, var1) -> ICompletionProvider.a(((ICompletionProvider)var0.getSource()).s(), var1)
   );
   public static final SuggestionProvider<CommandListenerWrapper> c = a(
      new MinecraftKey("available_sounds"), (var0, var1) -> ICompletionProvider.a(((ICompletionProvider)var0.getSource()).r(), var1)
   );
   public static final SuggestionProvider<CommandListenerWrapper> d = a(
      new MinecraftKey("summonable_entities"),
      (var0, var1) -> ICompletionProvider.a(
            BuiltInRegistries.h.s().filter(var1x -> var1x.a(((ICompletionProvider)var0.getSource()).v()) && var1x.c()),
            var1,
            EntityTypes::a,
            var0x -> IChatBaseComponent.c(SystemUtils.a("entity", EntityTypes.a(var0x)))
         )
   );

   public static <S extends ICompletionProvider> SuggestionProvider<S> a(MinecraftKey var0, SuggestionProvider<ICompletionProvider> var1) {
      if (e.containsKey(var0)) {
         throw new IllegalArgumentException("A command suggestion provider is already registered with the name " + var0);
      } else {
         e.put(var0, var1);
         return new CompletionProviders.a(var0, var1);
      }
   }

   public static SuggestionProvider<ICompletionProvider> a(MinecraftKey var0) {
      return (SuggestionProvider<ICompletionProvider>)e.getOrDefault(var0, a);
   }

   public static MinecraftKey a(SuggestionProvider<ICompletionProvider> var0) {
      return var0 instanceof CompletionProviders.a ? ((CompletionProviders.a)var0).b : f;
   }

   public static SuggestionProvider<ICompletionProvider> b(SuggestionProvider<ICompletionProvider> var0) {
      return var0 instanceof CompletionProviders.a ? var0 : a;
   }

   protected static class a implements SuggestionProvider<ICompletionProvider> {
      private final SuggestionProvider<ICompletionProvider> a;
      final MinecraftKey b;

      public a(MinecraftKey var0, SuggestionProvider<ICompletionProvider> var1) {
         this.a = var1;
         this.b = var0;
      }

      public CompletableFuture<Suggestions> getSuggestions(CommandContext<ICompletionProvider> var0, SuggestionsBuilder var1) throws CommandSyntaxException {
         return this.a.getSuggestions(var0, var1);
      }
   }
}
