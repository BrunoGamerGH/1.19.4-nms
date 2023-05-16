package net.minecraft.network.protocol.game;

import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.List;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.stats.RecipeBookSettings;

public class PacketPlayOutRecipes implements Packet<PacketListenerPlayOut> {
   private final PacketPlayOutRecipes.Action a;
   private final List<MinecraftKey> b;
   private final List<MinecraftKey> c;
   private final RecipeBookSettings d;

   public PacketPlayOutRecipes(PacketPlayOutRecipes.Action var0, Collection<MinecraftKey> var1, Collection<MinecraftKey> var2, RecipeBookSettings var3) {
      this.a = var0;
      this.b = ImmutableList.copyOf(var1);
      this.c = ImmutableList.copyOf(var2);
      this.d = var3;
   }

   public PacketPlayOutRecipes(PacketDataSerializer var0) {
      this.a = var0.b(PacketPlayOutRecipes.Action.class);
      this.d = RecipeBookSettings.a(var0);
      this.b = var0.a(PacketDataSerializer::t);
      if (this.a == PacketPlayOutRecipes.Action.a) {
         this.c = var0.a(PacketDataSerializer::t);
      } else {
         this.c = ImmutableList.of();
      }
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(this.a);
      this.d.b(var0);
      var0.a(this.b, PacketDataSerializer::a);
      if (this.a == PacketPlayOutRecipes.Action.a) {
         var0.a(this.c, PacketDataSerializer::a);
      }
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public List<MinecraftKey> a() {
      return this.b;
   }

   public List<MinecraftKey> c() {
      return this.c;
   }

   public RecipeBookSettings d() {
      return this.d;
   }

   public PacketPlayOutRecipes.Action e() {
      return this.a;
   }

   public static enum Action {
      a,
      b,
      c;
   }
}
