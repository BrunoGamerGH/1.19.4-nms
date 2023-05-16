package net.minecraft.network.protocol.game;

import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.List;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.item.crafting.IRecipe;

public class PacketPlayOutRecipeUpdate implements Packet<PacketListenerPlayOut> {
   private final List<IRecipe<?>> a;

   public PacketPlayOutRecipeUpdate(Collection<IRecipe<?>> var0) {
      this.a = Lists.newArrayList(var0);
   }

   public PacketPlayOutRecipeUpdate(PacketDataSerializer var0) {
      this.a = var0.a(PacketPlayOutRecipeUpdate::b);
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(this.a, PacketPlayOutRecipeUpdate::a);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public List<IRecipe<?>> a() {
      return this.a;
   }

   public static IRecipe<?> b(PacketDataSerializer var0) {
      MinecraftKey var1 = var0.t();
      MinecraftKey var2 = var0.t();
      return BuiltInRegistries.t.b(var1).orElseThrow(() -> new IllegalArgumentException("Unknown recipe serializer " + var1)).b(var2, var0);
   }

   public static <T extends IRecipe<?>> void a(PacketDataSerializer var0, T var1) {
      var0.a(BuiltInRegistries.t.b(var1.ai_()));
      var0.a(var1.e());
      var1.ai_().a(var0, var1);
   }
}
