package net.minecraft.network.protocol.game;

import com.google.common.collect.Sets;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.core.RegistrySynchronization;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.DynamicOpsNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.EnumGamemode;
import net.minecraft.world.level.World;
import net.minecraft.world.level.dimension.DimensionManager;

public record PacketPlayOutLogin(
   int playerId,
   boolean hardcore,
   EnumGamemode gameType,
   @Nullable EnumGamemode previousGameType,
   Set<ResourceKey<World>> levels,
   IRegistryCustom.Dimension registryHolder,
   ResourceKey<DimensionManager> dimensionType,
   ResourceKey<World> dimension,
   long seed,
   int maxPlayers,
   int chunkRadius,
   int simulationDistance,
   boolean reducedDebugInfo,
   boolean showDeathScreen,
   boolean isDebug,
   boolean isFlat,
   Optional<GlobalPos> lastDeathLocation
) implements Packet<PacketListenerPlayOut> {
   private final int a;
   private final boolean b;
   private final EnumGamemode c;
   @Nullable
   private final EnumGamemode d;
   private final Set<ResourceKey<World>> e;
   private final IRegistryCustom.Dimension f;
   private final ResourceKey<DimensionManager> g;
   private final ResourceKey<World> h;
   private final long i;
   private final int j;
   private final int k;
   private final int l;
   private final boolean m;
   private final boolean n;
   private final boolean o;
   private final boolean p;
   private final Optional<GlobalPos> q;
   private static final RegistryOps<NBTBase> r = RegistryOps.a(DynamicOpsNBT.a, IRegistryCustom.a(BuiltInRegistries.an));

   public PacketPlayOutLogin(PacketDataSerializer var0) {
      this(
         var0.readInt(),
         var0.readBoolean(),
         EnumGamemode.a(var0.readByte()),
         EnumGamemode.b(var0.readByte()),
         var0.a(Sets::newHashSetWithExpectedSize, var0x -> var0x.a(Registries.aF)),
         var0.<IRegistryCustom>a(r, RegistrySynchronization.a).c(),
         var0.a(Registries.as),
         var0.a(Registries.aF),
         var0.readLong(),
         var0.m(),
         var0.m(),
         var0.m(),
         var0.readBoolean(),
         var0.readBoolean(),
         var0.readBoolean(),
         var0.readBoolean(),
         var0.b(PacketDataSerializer::i)
      );
   }

   public PacketPlayOutLogin(
      int var0,
      boolean var1,
      EnumGamemode var2,
      @Nullable EnumGamemode var3,
      Set<ResourceKey<World>> var4,
      IRegistryCustom.Dimension var5,
      ResourceKey<DimensionManager> var6,
      ResourceKey<World> var7,
      long var8,
      int var10,
      int var11,
      int var12,
      boolean var13,
      boolean var14,
      boolean var15,
      boolean var16,
      Optional<GlobalPos> var17
   ) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
      this.d = var3;
      this.e = var4;
      this.f = var5;
      this.g = var6;
      this.h = var7;
      this.i = var8;
      this.j = var10;
      this.k = var11;
      this.l = var12;
      this.m = var13;
      this.n = var14;
      this.o = var15;
      this.p = var16;
      this.q = var17;
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.writeInt(this.a);
      var0.writeBoolean(this.b);
      var0.writeByte(this.c.a());
      var0.writeByte(EnumGamemode.a(this.d));
      var0.a(this.e, PacketDataSerializer::b);
      var0.a(r, RegistrySynchronization.a, this.f);
      var0.b(this.g);
      var0.b(this.h);
      var0.writeLong(this.i);
      var0.d(this.j);
      var0.d(this.k);
      var0.d(this.l);
      var0.writeBoolean(this.m);
      var0.writeBoolean(this.n);
      var0.writeBoolean(this.o);
      var0.writeBoolean(this.p);
      var0.a(this.q, PacketDataSerializer::a);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public boolean c() {
      return this.b;
   }

   public EnumGamemode d() {
      return this.c;
   }

   @Nullable
   public EnumGamemode e() {
      return this.d;
   }

   public Set<ResourceKey<World>> f() {
      return this.e;
   }

   public IRegistryCustom.Dimension g() {
      return this.f;
   }

   public ResourceKey<DimensionManager> h() {
      return this.g;
   }

   public ResourceKey<World> i() {
      return this.h;
   }

   public long j() {
      return this.i;
   }

   public int k() {
      return this.j;
   }

   public int l() {
      return this.k;
   }

   public int m() {
      return this.l;
   }

   public boolean n() {
      return this.m;
   }

   public boolean o() {
      return this.n;
   }

   public boolean p() {
      return this.o;
   }

   public boolean q() {
      return this.p;
   }

   public Optional<GlobalPos> r() {
      return this.q;
   }
}
