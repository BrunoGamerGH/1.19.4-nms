package net.minecraft.world.effect;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.IPosition;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.UtilColor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.event.entity.EntityPotionEffectEvent.Cause;

public final class MobEffectUtil {
   public static IChatBaseComponent a(MobEffect mobeffect, float f) {
      if (mobeffect.b()) {
         return IChatBaseComponent.c("effect.duration.infinite");
      } else {
         int i = MathHelper.d((float)mobeffect.d() * f);
         return IChatBaseComponent.b(UtilColor.a(i));
      }
   }

   public static boolean a(EntityLiving entityliving) {
      return entityliving.a(MobEffects.c) || entityliving.a(MobEffects.C);
   }

   public static int b(EntityLiving entityliving) {
      int i = 0;
      int j = 0;
      if (entityliving.a(MobEffects.c)) {
         i = entityliving.b(MobEffects.c).e();
      }

      if (entityliving.a(MobEffects.C)) {
         j = entityliving.b(MobEffects.C).e();
      }

      return Math.max(i, j);
   }

   public static boolean c(EntityLiving entityliving) {
      return entityliving.a(MobEffects.m) || entityliving.a(MobEffects.C);
   }

   public static List<EntityPlayer> a(WorldServer worldserver, @Nullable Entity entity, Vec3D vec3d, double d0, MobEffect mobeffect, int i) {
      return addEffectToPlayersAround(worldserver, entity, vec3d, d0, mobeffect, i, Cause.UNKNOWN);
   }

   public static List<EntityPlayer> addEffectToPlayersAround(
      WorldServer worldserver, @Nullable Entity entity, Vec3D vec3d, double d0, MobEffect mobeffect, int i, Cause cause
   ) {
      MobEffectList mobeffectlist = mobeffect.c();
      List<EntityPlayer> list = worldserver.a(
         entityplayer -> entityplayer.d.d()
               && (entity == null || !entity.p(entityplayer))
               && vec3d.a((IPosition)entityplayer.de(), d0)
               && (!entityplayer.a(mobeffectlist) || entityplayer.b(mobeffectlist).e() < mobeffect.e() || entityplayer.b(mobeffectlist).a(i - 1))
      );
      list.forEach(entityplayer -> entityplayer.addEffect(new MobEffect(mobeffect), entity, cause));
      return list;
   }
}
