package net.minecraft.world.entity.animal.horse;

import javax.annotation.Nullable;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityAgeable;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.level.World;

public class EntityHorseMule extends EntityHorseChestedAbstract {
   public EntityHorseMule(EntityTypes<? extends EntityHorseMule> var0, World var1) {
      super(var0, var1);
   }

   @Override
   protected SoundEffect s() {
      return SoundEffects.nJ;
   }

   @Override
   protected SoundEffect gr() {
      return SoundEffects.nK;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.nM;
   }

   @Nullable
   @Override
   protected SoundEffect fZ() {
      return SoundEffects.nN;
   }

   @Override
   protected SoundEffect d(DamageSource var0) {
      return SoundEffects.nO;
   }

   @Override
   protected void fS() {
      this.a(SoundEffects.nL, 1.0F, (this.af.i() - this.af.i()) * 0.2F + 1.0F);
   }

   @Nullable
   @Override
   public EntityAgeable a(WorldServer var0, EntityAgeable var1) {
      return EntityTypes.ap.a((World)var0);
   }
}
