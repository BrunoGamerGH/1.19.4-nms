package net.minecraft.network.protocol.game;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.MinecraftKey;

public class PacketPlayOutAdvancements implements Packet<PacketListenerPlayOut> {
   private final boolean a;
   private final Map<MinecraftKey, Advancement.SerializedAdvancement> b;
   private final Set<MinecraftKey> c;
   private final Map<MinecraftKey, AdvancementProgress> d;

   public PacketPlayOutAdvancements(boolean var0, Collection<Advancement> var1, Set<MinecraftKey> var2, Map<MinecraftKey, AdvancementProgress> var3) {
      this.a = var0;
      Builder<MinecraftKey, Advancement.SerializedAdvancement> var4 = ImmutableMap.builder();

      for(Advancement var6 : var1) {
         var4.put(var6.i(), var6.a());
      }

      this.b = var4.build();
      this.c = ImmutableSet.copyOf(var2);
      this.d = ImmutableMap.copyOf(var3);
   }

   public PacketPlayOutAdvancements(PacketDataSerializer var0) {
      this.a = var0.readBoolean();
      this.b = var0.a(PacketDataSerializer::t, Advancement.SerializedAdvancement::b);
      this.c = var0.a(Sets::newLinkedHashSetWithExpectedSize, PacketDataSerializer::t);
      this.d = var0.a(PacketDataSerializer::t, AdvancementProgress::b);
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.writeBoolean(this.a);
      var0.a(this.b, PacketDataSerializer::a, (var0x, var1x) -> var1x.a(var0x));
      var0.a(this.c, PacketDataSerializer::a);
      var0.a(this.d, PacketDataSerializer::a, (var0x, var1x) -> var1x.a(var0x));
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public Map<MinecraftKey, Advancement.SerializedAdvancement> a() {
      return this.b;
   }

   public Set<MinecraftKey> c() {
      return this.c;
   }

   public Map<MinecraftKey, AdvancementProgress> d() {
      return this.d;
   }

   public boolean e() {
      return this.a;
   }
}
