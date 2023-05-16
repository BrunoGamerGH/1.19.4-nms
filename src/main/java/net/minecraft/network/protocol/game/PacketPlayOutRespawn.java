package net.minecraft.network.protocol.game;

import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.EnumGamemode;
import net.minecraft.world.level.World;
import net.minecraft.world.level.dimension.DimensionManager;

public class PacketPlayOutRespawn implements Packet<PacketListenerPlayOut> {
   public static final byte a = 1;
   public static final byte b = 2;
   public static final byte c = 3;
   private final ResourceKey<DimensionManager> d;
   private final ResourceKey<World> e;
   private final long f;
   private final EnumGamemode g;
   @Nullable
   private final EnumGamemode h;
   private final boolean i;
   private final boolean j;
   private final byte k;
   private final Optional<GlobalPos> l;

   public PacketPlayOutRespawn(
      ResourceKey<DimensionManager> var0,
      ResourceKey<World> var1,
      long var2,
      EnumGamemode var4,
      @Nullable EnumGamemode var5,
      boolean var6,
      boolean var7,
      byte var8,
      Optional<GlobalPos> var9
   ) {
      this.d = var0;
      this.e = var1;
      this.f = var2;
      this.g = var4;
      this.h = var5;
      this.i = var6;
      this.j = var7;
      this.k = var8;
      this.l = var9;
   }

   public PacketPlayOutRespawn(PacketDataSerializer var0) {
      this.d = var0.a(Registries.as);
      this.e = var0.a(Registries.aF);
      this.f = var0.readLong();
      this.g = EnumGamemode.a(var0.readUnsignedByte());
      this.h = EnumGamemode.b(var0.readByte());
      this.i = var0.readBoolean();
      this.j = var0.readBoolean();
      this.k = var0.readByte();
      this.l = var0.b(PacketDataSerializer::i);
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.b(this.d);
      var0.b(this.e);
      var0.writeLong(this.f);
      var0.writeByte(this.g.a());
      var0.writeByte(EnumGamemode.a(this.h));
      var0.writeBoolean(this.i);
      var0.writeBoolean(this.j);
      var0.writeByte(this.k);
      var0.a(this.l, PacketDataSerializer::a);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public ResourceKey<DimensionManager> a() {
      return this.d;
   }

   public ResourceKey<World> c() {
      return this.e;
   }

   public long d() {
      return this.f;
   }

   public EnumGamemode e() {
      return this.g;
   }

   @Nullable
   public EnumGamemode f() {
      return this.h;
   }

   public boolean g() {
      return this.i;
   }

   public boolean h() {
      return this.j;
   }

   public boolean a(byte var0) {
      return (this.k & var0) != 0;
   }

   public Optional<GlobalPos> i() {
      return this.l;
   }
}
