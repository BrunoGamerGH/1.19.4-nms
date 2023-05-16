package net.minecraft.commands.arguments;

import com.google.gson.JsonPrimitive;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.commands.ICompletionProvider;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.util.INamable;

public class StringRepresentableArgument<T extends Enum<T> & INamable> implements ArgumentType<T> {
   private static final DynamicCommandExceptionType a = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("argument.enum.invalid", var0));
   private final Codec<T> b;
   private final Supplier<T[]> c;

   protected StringRepresentableArgument(Codec<T> var0, Supplier<T[]> var1) {
      this.b = var0;
      this.c = var1;
   }

   public T a(StringReader var0) throws CommandSyntaxException {
      String var1 = var0.readUnquotedString();
      return (T)this.b.parse(JsonOps.INSTANCE, new JsonPrimitive(var1)).result().orElseThrow(() -> a.create(var1));
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var0, SuggestionsBuilder var1) {
      return ICompletionProvider.b(
         Arrays.<Enum>stream((Enum[])this.c.get()).map(var0x -> ((INamable)var0x).c()).map(this::a).collect(Collectors.toList()), var1
      );
   }

   public Collection<String> getExamples() {
      return Arrays.<Enum>stream((Enum[])this.c.get()).map(var0 -> ((INamable)var0).c()).map(this::a).limit(2L).collect(Collectors.toList());
   }

   protected String a(String var0) {
      return var0;
   }
}
