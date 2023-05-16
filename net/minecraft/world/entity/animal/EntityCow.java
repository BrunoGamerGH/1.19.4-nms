package net.minecraft.world.entity.animal;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityAgeable;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntitySize;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.goal.PathfinderGoalBreed;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFloat;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFollowParent;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLookAtPlayer;
import net.minecraft.world.entity.ai.goal.PathfinderGoalPanic;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomLookaround;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomStrollLand;
import net.minecraft.world.entity.ai.goal.PathfinderGoalTempt;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemLiquidUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeItemStack;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.IBlockData;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.event.player.PlayerBucketFillEvent;

public class EntityCow extends EntityAnimal {
   public EntityCow(EntityTypes<? extends EntityCow> entitytypes, World world) {
      super(entitytypes, world);
   }

   @Override
   protected void x() {
      this.bN.a(0, new PathfinderGoalFloat(this));
      this.bN.a(1, new PathfinderGoalPanic(this, 2.0));
      this.bN.a(2, new PathfinderGoalBreed(this, 1.0));
      this.bN.a(3, new PathfinderGoalTempt(this, 1.25, RecipeItemStack.a(Items.oE), false));
      this.bN.a(4, new PathfinderGoalFollowParent(this, 1.25));
      this.bN.a(5, new PathfinderGoalRandomStrollLand(this, 1.0));
      this.bN.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
      this.bN.a(7, new PathfinderGoalRandomLookaround(this));
   }

   public static AttributeProvider.Builder q() {
      return EntityInsentient.y().a(GenericAttributes.a, 10.0).a(GenericAttributes.d, 0.2F);
   }

   @Override
   protected SoundEffect s() {
      return SoundEffects.eV;
   }

   @Override
   protected SoundEffect d(DamageSource damagesource) {
      return SoundEffects.eX;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.eW;
   }

   @Override
   protected void b(BlockPosition blockposition, IBlockData iblockdata) {
      this.a(SoundEffects.eZ, 0.15F, 1.0F);
   }

   @Override
   protected float eN() {
      return 0.4F;
   }

   @Override
   public EnumInteractionResult b(EntityHuman entityhuman, EnumHand enumhand) {
      ItemStack itemstack = entityhuman.b(enumhand);
      if (itemstack.a(Items.pG) && !this.y_()) {
         PlayerBucketFillEvent event = CraftEventFactory.callPlayerBucketFillEvent(
            (WorldServer)entityhuman.H, entityhuman, this.dg(), this.dg(), null, itemstack, Items.pM, enumhand
         );
         if (event.isCancelled()) {
            return EnumInteractionResult.d;
         } else {
            entityhuman.a(SoundEffects.eY, 1.0F, 1.0F);
            ItemStack itemstack1 = ItemLiquidUtil.a(itemstack, entityhuman, CraftItemStack.asNMSCopy(event.getItemStack()));
            entityhuman.a(enumhand, itemstack1);
            return EnumInteractionResult.a(this.H.B);
         }
      } else {
         return super.b(entityhuman, enumhand);
      }
   }

   @Nullable
   public EntityCow b(WorldServer worldserver, EntityAgeable entityageable) {
      return EntityTypes.t.a((World)worldserver);
   }

   @Override
   protected float b(EntityPose entitypose, EntitySize entitysize) {
      return this.y_() ? entitysize.b * 0.95F : 1.3F;
   }
}
