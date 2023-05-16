package net.minecraft.commands.arguments;

import com.google.common.collect.Maps;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.ICompletionProvider;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3D;

public class ArgumentAnchor implements ArgumentType<ArgumentAnchor.Anchor> {
   private static final Collection<String> a = Arrays.asList("eyes", "feet");
   private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(var0 -> IChatBaseComponent.a("argument.anchor.invalid", var0));

   public static ArgumentAnchor.Anchor a(CommandContext<CommandListenerWrapper> var0, String var1) {
      return (ArgumentAnchor.Anchor)var0.getArgument(var1, ArgumentAnchor.Anchor.class);
   }

   public static ArgumentAnchor a() {
      return new ArgumentAnchor();
   }

   public ArgumentAnchor.Anchor a(StringReader var0) throws CommandSyntaxException {
      int var1 = var0.getCursor();
      String var2 = var0.readUnquotedString();
      ArgumentAnchor.Anchor var3 = ArgumentAnchor.Anchor.a(var2);
      if (var3 == null) {
         var0.setCursor(var1);
         throw b.createWithContext(var0, var2);
      } else {
         return var3;
      }
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var0, SuggestionsBuilder var1) {
      return ICompletionProvider.b(ArgumentAnchor.Anchor.c.keySet(), var1);
   }

   public Collection<String> getExamples() {
      return a;
   }

   public static enum Anchor {
      a("feet", (var0, var1) -> var0),
      b("eyes", (var0, var1) -> new Vec3D(var0.c, var0.d + (double)var1.cE(), var0.e));

      static final Map<String, ArgumentAnchor.Anchor> c = SystemUtils.a(Maps.newHashMap(), var0 -> {
         for(ArgumentAnchor.Anchor var4 : values()) {
            var0.put(var4.d, var4);
         }
      });
      private final String d;
      private final BiFunction<Vec3D, Entity, Vec3D> e;

      private Anchor(String var2, BiFunction var3) {
         this.d = var2;
         this.e = var3;
      }

      @Nullable
      public static ArgumentAnchor.Anchor a(String var0) {
         return c.get(var0);
      }

      public Vec3D a(Entity var0) {
         return this.e.apply(var0.de(), var0);
      }

      public Vec3D a(CommandListenerWrapper var0) {
         Entity var1 = var0.f();
         return var1 == null ? var0.d() : this.e.apply(var0.d(), var1);
      }
   }
}
