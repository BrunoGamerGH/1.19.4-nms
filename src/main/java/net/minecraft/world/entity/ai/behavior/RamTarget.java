package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.MathHelper;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.MemoryTarget;
import net.minecraft.world.entity.ai.targeting.PathfinderTargetCondition;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.phys.Vec3D;

public class RamTarget extends Behavior<Goat> {
   public static final int c = 200;
   public static final float d = 1.65F;
   private final Function<Goat, UniformInt> e;
   private final PathfinderTargetCondition f;
   private final float g;
   private final ToDoubleFunction<Goat> h;
   private Vec3D i;
   private final Function<Goat, SoundEffect> j;
   private final Function<Goat, SoundEffect> k;

   public RamTarget(
      Function<Goat, UniformInt> var0,
      PathfinderTargetCondition var1,
      float var2,
      ToDoubleFunction<Goat> var3,
      Function<Goat, SoundEffect> var4,
      Function<Goat, SoundEffect> var5
   ) {
      super(ImmutableMap.of(MemoryModuleType.U, MemoryStatus.b, MemoryModuleType.V, MemoryStatus.a), 200);
      this.e = var0;
      this.f = var1;
      this.g = var2;
      this.h = var3;
      this.j = var4;
      this.k = var5;
      this.i = Vec3D.b;
   }

   protected boolean a(WorldServer var0, Goat var1) {
      return var1.dH().a(MemoryModuleType.V);
   }

   protected boolean a(WorldServer var0, Goat var1, long var2) {
      return var1.dH().a(MemoryModuleType.V);
   }

   protected void b(WorldServer var0, Goat var1, long var2) {
      BlockPosition var4 = var1.dg();
      BehaviorController<?> var5 = var1.dH();
      Vec3D var6 = var5.c(MemoryModuleType.V).get();
      this.i = new Vec3D((double)var4.u() - var6.a(), 0.0, (double)var4.w() - var6.c()).d();
      var5.a(MemoryModuleType.m, new MemoryTarget(var6, this.g, 0));
   }

   protected void c(WorldServer var0, Goat var1, long var2) {
      List<EntityLiving> var4 = var0.a(EntityLiving.class, this.f, var1, var1.cD());
      BehaviorController<?> var5 = var1.dH();
      if (!var4.isEmpty()) {
         EntityLiving var6 = var4.get(0);
         var6.a(var0.af().c((EntityLiving)var1), (float)var1.b(GenericAttributes.f));
         int var7 = var1.a(MobEffects.a) ? var1.b(MobEffects.a).e() + 1 : 0;
         int var8 = var1.a(MobEffects.b) ? var1.b(MobEffects.b).e() + 1 : 0;
         float var9 = 0.25F * (float)(var7 - var8);
         float var10 = MathHelper.a(var1.eW() * 1.65F, 0.2F, 3.0F) + var9;
         float var11 = var6.f(var0.af().b((EntityLiving)var1)) ? 0.5F : 1.0F;
         var6.q((double)(var11 * var10) * this.h.applyAsDouble(var1), this.i.a(), this.i.c());
         this.b(var0, var1);
         var0.a(null, var1, this.j.apply(var1), SoundCategory.g, 1.0F, 1.0F);
      } else if (this.c(var0, var1)) {
         var0.a(null, var1, this.j.apply(var1), SoundCategory.g, 1.0F, 1.0F);
         boolean var6 = var1.fZ();
         if (var6) {
            var0.a(null, var1, this.k.apply(var1), SoundCategory.g, 1.0F, 1.0F);
         }

         this.b(var0, var1);
      } else {
         Optional<MemoryTarget> var6 = var5.c(MemoryModuleType.m);
         Optional<Vec3D> var7 = var5.c(MemoryModuleType.V);
         boolean var8 = var6.isEmpty() || var7.isEmpty() || var6.get().a().a().a(var7.get(), 0.25);
         if (var8) {
            this.b(var0, var1);
         }
      }
   }

   private boolean c(WorldServer var0, Goat var1) {
      Vec3D var2 = var1.dj().d(1.0, 0.0, 1.0).d();
      BlockPosition var3 = BlockPosition.a(var1.de().e(var2));
      return var0.a_(var3).a(TagsBlock.bZ) || var0.a_(var3.c()).a(TagsBlock.bZ);
   }

   protected void b(WorldServer var0, Goat var1) {
      var0.a(var1, (byte)59);
      var1.dH().a(MemoryModuleType.U, this.e.apply(var1).a(var0.z));
      var1.dH().b(MemoryModuleType.V);
   }
}
