package net.minecraft.commands.synchronization;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.ArgumentType;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.network.PacketDataSerializer;

public interface ArgumentTypeInfo<A extends ArgumentType<?>, T extends ArgumentTypeInfo.a<A>> {
   void a(T var1, PacketDataSerializer var2);

   T b(PacketDataSerializer var1);

   void a(T var1, JsonObject var2);

   T a(A var1);

   public interface a<A extends ArgumentType<?>> {
      A b(CommandBuildContext var1);

      ArgumentTypeInfo<A, ?> a();
   }
}
