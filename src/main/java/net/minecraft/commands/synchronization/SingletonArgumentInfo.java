package net.minecraft.commands.synchronization;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.ArgumentType;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.network.PacketDataSerializer;

public class SingletonArgumentInfo<A extends ArgumentType<?>> implements ArgumentTypeInfo<A, SingletonArgumentInfo<A>.a> {
   private final SingletonArgumentInfo<A>.a a;

   private SingletonArgumentInfo(Function<CommandBuildContext, A> var0) {
      this.a = new SingletonArgumentInfo.a(var0);
   }

   public static <T extends ArgumentType<?>> SingletonArgumentInfo<T> a(Supplier<T> var0) {
      return new SingletonArgumentInfo<>(var1 -> (T)var0.get());
   }

   public static <T extends ArgumentType<?>> SingletonArgumentInfo<T> a(Function<CommandBuildContext, T> var0) {
      return new SingletonArgumentInfo<>(var0);
   }

   public void a(SingletonArgumentInfo<A>.a var0, PacketDataSerializer var1) {
   }

   public void a(SingletonArgumentInfo<A>.a var0, JsonObject var1) {
   }

   public SingletonArgumentInfo<A>.a a(PacketDataSerializer var0) {
      return this.a;
   }

   public SingletonArgumentInfo<A>.a b(A var0) {
      return this.a;
   }

   public final class a implements ArgumentTypeInfo.a<A> {
      private final Function<CommandBuildContext, A> b;

      public a(Function var1) {
         this.b = var1;
      }

      @Override
      public A b(CommandBuildContext var0) {
         return (A)this.b.apply(var0);
      }

      @Override
      public ArgumentTypeInfo<A, ?> a() {
         return SingletonArgumentInfo.this;
      }
   }
}
