package net.minecraft.commands.arguments;

import com.google.common.collect.Lists;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.ICompletionProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.stats.Statistic;
import net.minecraft.stats.StatisticWrapper;
import net.minecraft.world.scores.criteria.IScoreboardCriteria;

public class ArgumentScoreboardCriteria implements ArgumentType<IScoreboardCriteria> {
   private static final Collection<String> b = Arrays.asList("foo", "foo.bar.baz", "minecraft:foo");
   public static final DynamicCommandExceptionType a = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("argument.criteria.invalid", var0));

   private ArgumentScoreboardCriteria() {
   }

   public static ArgumentScoreboardCriteria a() {
      return new ArgumentScoreboardCriteria();
   }

   public static IScoreboardCriteria a(CommandContext<CommandListenerWrapper> var0, String var1) {
      return (IScoreboardCriteria)var0.getArgument(var1, IScoreboardCriteria.class);
   }

   public IScoreboardCriteria a(StringReader var0) throws CommandSyntaxException {
      int var1 = var0.getCursor();

      while(var0.canRead() && var0.peek() != ' ') {
         var0.skip();
      }

      String var2 = var0.getString().substring(var1, var0.getCursor());
      return IScoreboardCriteria.a(var2).orElseThrow(() -> {
         var0.setCursor(var1);
         return a.create(var2);
      });
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var0, SuggestionsBuilder var1) {
      List<String> var2 = Lists.newArrayList(IScoreboardCriteria.c());

      for(StatisticWrapper<?> var4 : BuiltInRegistries.x) {
         for(Object var6 : var4.a()) {
            String var7 = this.a(var4, var6);
            var2.add(var7);
         }
      }

      return ICompletionProvider.b(var2, var1);
   }

   public <T> String a(StatisticWrapper<T> var0, Object var1) {
      return Statistic.a(var0, (T)var1);
   }

   public Collection<String> getExamples() {
      return b;
   }
}
