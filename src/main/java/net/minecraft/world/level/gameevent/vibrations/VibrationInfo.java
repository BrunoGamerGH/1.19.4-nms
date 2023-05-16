package net.minecraft.world.level.gameevent.vibrations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.IProjectile;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3D;

public record VibrationInfo(GameEvent gameEvent, float distance, Vec3D pos, @Nullable UUID uuid, @Nullable UUID projectileOwnerUuid, @Nullable Entity entity) {
   private final GameEvent b;
   private final float c;
   private final Vec3D d;
   @Nullable
   private final UUID e;
   @Nullable
   private final UUID f;
   @Nullable
   private final Entity g;
   public static final Codec<VibrationInfo> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               BuiltInRegistries.b.q().fieldOf("game_event").forGetter(VibrationInfo::a),
               Codec.floatRange(0.0F, Float.MAX_VALUE).fieldOf("distance").forGetter(VibrationInfo::b),
               Vec3D.a.fieldOf("pos").forGetter(VibrationInfo::c),
               UUIDUtil.a.optionalFieldOf("source").forGetter(var0x -> Optional.ofNullable(var0x.d())),
               UUIDUtil.a.optionalFieldOf("projectile_owner").forGetter(var0x -> Optional.ofNullable(var0x.e()))
            )
            .apply(var0, (var0x, var1, var2, var3, var4) -> new VibrationInfo(var0x, var1, var2, (UUID)var3.orElse(null), (UUID)var4.orElse(null)))
   );

   public VibrationInfo(GameEvent var0, float var1, Vec3D var2, @Nullable UUID var3, @Nullable UUID var4) {
      this(var0, var1, var2, var3, var4, null);
   }

   public VibrationInfo(GameEvent var0, float var1, Vec3D var2, @Nullable Entity var3) {
      this(var0, var1, var2, var3 == null ? null : var3.cs(), a(var3), var3);
   }

   public VibrationInfo(GameEvent var0, float var1, Vec3D var2, @Nullable UUID var3, @Nullable UUID var4, @Nullable Entity var5) {
      this.b = var0;
      this.c = var1;
      this.d = var2;
      this.e = var3;
      this.f = var4;
      this.g = var5;
   }

   @Nullable
   private static UUID a(@Nullable Entity var0) {
      if (var0 instanceof IProjectile var1 && var1.v() != null) {
         return var1.v().cs();
      }

      return null;
   }

   public Optional<Entity> a(WorldServer var0) {
      return Optional.ofNullable(this.g).or(() -> Optional.ofNullable(this.e).map(var0::a));
   }

   public Optional<Entity> b(WorldServer var0) {
      return this.a(var0)
         .filter(var0x -> var0x instanceof IProjectile)
         .map(var0x -> (IProjectile)var0x)
         .map(IProjectile::v)
         .or(() -> Optional.ofNullable(this.f).map(var0::a));
   }

   public GameEvent a() {
      return this.b;
   }

   public float b() {
      return this.c;
   }

   public Vec3D c() {
      return this.d;
   }

   @Nullable
   public UUID d() {
      return this.e;
   }

   @Nullable
   public UUID e() {
      return this.f;
   }

   @Nullable
   public Entity f() {
      return this.g;
   }
}
