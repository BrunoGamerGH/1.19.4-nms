package net.minecraft.world.entity.animal.horse;

import javax.annotation.Nullable;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.RandomSource;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityAgeable;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMonsterType;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.World;

public class EntityHorseZombie extends EntityHorseAbstract {
   public EntityHorseZombie(EntityTypes<? extends EntityHorseZombie> var0, World var1) {
      super(var0, var1);
   }

   public static AttributeProvider.Builder q() {
      return gs().a(GenericAttributes.a, 15.0).a(GenericAttributes.d, 0.2F);
   }

   @Override
   protected void a(RandomSource var0) {
      this.a(GenericAttributes.m).a(a(var0::j));
   }

   @Override
   public EnumMonsterType eJ() {
      return EnumMonsterType.b;
   }

   @Override
   protected SoundEffect s() {
      return SoundEffects.AC;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.AD;
   }

   @Override
   protected SoundEffect d(DamageSource var0) {
      return SoundEffects.AE;
   }

   @Nullable
   @Override
   public EntityAgeable a(WorldServer var0, EntityAgeable var1) {
      return EntityTypes.bq.a((World)var0);
   }

   @Override
   public EnumInteractionResult b(EntityHuman var0, EnumHand var1) {
      return !this.gh() ? EnumInteractionResult.d : super.b(var0, var1);
   }

   @Override
   protected void gi() {
   }
}
