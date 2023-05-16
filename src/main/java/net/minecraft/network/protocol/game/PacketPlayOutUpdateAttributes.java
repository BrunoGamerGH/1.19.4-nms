package net.minecraft.network.protocol.game;

import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.List;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.entity.ai.attributes.AttributeBase;
import net.minecraft.world.entity.ai.attributes.AttributeModifiable;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class PacketPlayOutUpdateAttributes implements Packet<PacketListenerPlayOut> {
   private final int a;
   private final List<PacketPlayOutUpdateAttributes.AttributeSnapshot> b;

   public PacketPlayOutUpdateAttributes(int var0, Collection<AttributeModifiable> var1) {
      this.a = var0;
      this.b = Lists.newArrayList();

      for(AttributeModifiable var3 : var1) {
         this.b.add(new PacketPlayOutUpdateAttributes.AttributeSnapshot(var3.a(), var3.b(), var3.c()));
      }
   }

   public PacketPlayOutUpdateAttributes(PacketDataSerializer var0) {
      this.a = var0.m();
      this.b = var0.a(
         (PacketDataSerializer.a<PacketPlayOutUpdateAttributes.AttributeSnapshot>)(var0x -> {
            MinecraftKey var1x = var0x.t();
            AttributeBase var2 = BuiltInRegistries.u.a(var1x);
            double var3 = var0x.readDouble();
            List<AttributeModifier> var5 = var0x.a(
               (PacketDataSerializer.a<AttributeModifier>)(var0xx -> new AttributeModifier(
                     var0xx.o(), "Unknown synced attribute modifier", var0xx.readDouble(), AttributeModifier.Operation.a(var0xx.readByte())
                  ))
            );
            return new PacketPlayOutUpdateAttributes.AttributeSnapshot(var2, var3, var5);
         })
      );
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.d(this.a);
      var0.a(this.b, (var0x, var1x) -> {
         var0x.a(BuiltInRegistries.u.b(var1x.a()));
         var0x.writeDouble(var1x.b());
         var0x.a(var1x.c(), (var0xx, var1xx) -> {
            var0xx.a(var1xx.a());
            var0xx.writeDouble(var1xx.d());
            var0xx.writeByte(var1xx.c().a());
         });
      });
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public int a() {
      return this.a;
   }

   public List<PacketPlayOutUpdateAttributes.AttributeSnapshot> c() {
      return this.b;
   }

   public static class AttributeSnapshot {
      private final AttributeBase a;
      private final double b;
      private final Collection<AttributeModifier> c;

      public AttributeSnapshot(AttributeBase var0, double var1, Collection<AttributeModifier> var3) {
         this.a = var0;
         this.b = var1;
         this.c = var3;
      }

      public AttributeBase a() {
         return this.a;
      }

      public double b() {
         return this.b;
      }

      public Collection<AttributeModifier> c() {
         return this.c;
      }
   }
}
