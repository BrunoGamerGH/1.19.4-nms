package net.minecraft.world.entity.monster;

import java.util.List;
import net.minecraft.network.protocol.game.PacketPlayOutGameStateChange;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.level.World;
import org.bukkit.event.entity.EntityPotionEffectEvent.Cause;

public class EntityGuardianElder extends EntityGuardian {
   public static final float b = EntityTypes.A.k() / EntityTypes.V.k();
   private static final int e = 1200;
   private static final int bS = 50;
   private static final int bT = 6000;
   private static final int bU = 2;
   private static final int bV = 1200;

   public EntityGuardianElder(EntityTypes<? extends EntityGuardianElder> entitytypes, World world) {
      super(entitytypes, world);
      this.fz();
      if (this.d != null) {
         this.d.c(400);
      }
   }

   public static AttributeProvider.Builder q() {
      return EntityGuardian.fS().a(GenericAttributes.d, 0.3F).a(GenericAttributes.f, 8.0).a(GenericAttributes.a, 80.0);
   }

   @Override
   public int r() {
      return 60;
   }

   @Override
   protected SoundEffect s() {
      return this.aW() ? SoundEffects.gD : SoundEffects.gE;
   }

   @Override
   protected SoundEffect d(DamageSource damagesource) {
      return this.aW() ? SoundEffects.gJ : SoundEffects.gK;
   }

   @Override
   protected SoundEffect x_() {
      return this.aW() ? SoundEffects.gG : SoundEffects.gH;
   }

   @Override
   protected SoundEffect w() {
      return SoundEffects.gI;
   }

   @Override
   protected void U() {
      super.U();
      if ((this.ag + this.af()) % 1200 == 0) {
         MobEffect mobeffect = new MobEffect(MobEffects.d, 6000, 2);
         List<EntityPlayer> list = MobEffectUtil.addEffectToPlayersAround((WorldServer)this.H, this, this.de(), 50.0, mobeffect, 1200, Cause.ATTACK);
         list.forEach(entityplayer -> entityplayer.b.a(new PacketPlayOutGameStateChange(PacketPlayOutGameStateChange.k, this.aO() ? 0.0F : 1.0F)));
      }

      if (!this.fG()) {
         this.a(this.dg(), 16);
      }
   }
}
