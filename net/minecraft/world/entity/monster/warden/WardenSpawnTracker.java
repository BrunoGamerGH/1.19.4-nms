package net.minecraft.world.entity.monster.warden;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Predicate;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.IPosition;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.IEntitySelector;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.Vec3D;

public class WardenSpawnTracker {
   public static final Codec<WardenSpawnTracker> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               ExtraCodecs.h.fieldOf("ticks_since_last_warning").orElse(0).forGetter(var0x -> var0x.g),
               ExtraCodecs.h.fieldOf("warning_level").orElse(0).forGetter(var0x -> var0x.h),
               ExtraCodecs.h.fieldOf("cooldown_ticks").orElse(0).forGetter(var0x -> var0x.i)
            )
            .apply(var0, WardenSpawnTracker::new)
   );
   public static final int b = 4;
   private static final double c = 16.0;
   private static final int d = 48;
   private static final int e = 12000;
   private static final int f = 200;
   private int g;
   private int h;
   private int i;

   public WardenSpawnTracker(int var0, int var1, int var2) {
      this.g = var0;
      this.h = var1;
      this.i = var2;
   }

   public void a() {
      if (this.g >= 12000) {
         this.f();
         this.g = 0;
      } else {
         ++this.g;
      }

      if (this.i > 0) {
         --this.i;
      }
   }

   public void b() {
      this.g = 0;
      this.h = 0;
      this.i = 0;
   }

   public static OptionalInt a(WorldServer var0, BlockPosition var1, EntityPlayer var2) {
      if (a(var0, var1)) {
         return OptionalInt.empty();
      } else {
         List<EntityPlayer> var3 = b(var0, var1);
         if (!var3.contains(var2)) {
            var3.add(var2);
         }

         if (var3.stream().anyMatch(var0x -> var0x.W().map(WardenSpawnTracker::d).orElse(false))) {
            return OptionalInt.empty();
         } else {
            Optional<WardenSpawnTracker> var4 = var3.stream().flatMap(var0x -> var0x.W().stream()).max(Comparator.comparingInt(WardenSpawnTracker::c));
            if (var4.isPresent()) {
               WardenSpawnTracker var5 = var4.get();
               var5.e();
               var3.forEach(var1x -> var1x.W().ifPresent(var1xx -> var1xx.a(var5)));
               return OptionalInt.of(var5.h);
            } else {
               return OptionalInt.empty();
            }
         }
      }
   }

   private boolean d() {
      return this.i > 0;
   }

   private static boolean a(WorldServer var0, BlockPosition var1) {
      AxisAlignedBB var2 = AxisAlignedBB.a(Vec3D.b(var1), 48.0, 48.0, 48.0);
      return !var0.a(Warden.class, var2).isEmpty();
   }

   private static List<EntityPlayer> b(WorldServer var0, BlockPosition var1) {
      Vec3D var2 = Vec3D.b(var1);
      Predicate<EntityPlayer> var3 = var1x -> var1x.de().a((IPosition)var2, 16.0);
      return var0.a(var3.and(EntityLiving::bq).and(IEntitySelector.f));
   }

   private void e() {
      if (!this.d()) {
         this.g = 0;
         this.i = 200;
         this.a(this.c() + 1);
      }
   }

   private void f() {
      this.a(this.c() - 1);
   }

   public void a(int var0) {
      this.h = MathHelper.a(var0, 0, 4);
   }

   public int c() {
      return this.h;
   }

   private void a(WardenSpawnTracker var0) {
      this.h = var0.h;
      this.i = var0.i;
      this.g = var0.g;
   }
}
