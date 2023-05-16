package net.minecraft.world.entity.npc;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.GameProfileSerializer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.stats.StatisticList;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityAgeable;
import net.minecraft.world.entity.EntityExperienceOrb;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.ai.goal.PathfinderGoalAvoidTarget;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFloat;
import net.minecraft.world.entity.ai.goal.PathfinderGoalInteract;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLookAtPlayer;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLookAtTradingPlayer;
import net.minecraft.world.entity.ai.goal.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.world.entity.ai.goal.PathfinderGoalPanic;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomStrollLand;
import net.minecraft.world.entity.ai.goal.PathfinderGoalTradeWithPlayer;
import net.minecraft.world.entity.ai.goal.PathfinderGoalUseItem;
import net.minecraft.world.entity.monster.EntityEvoker;
import net.minecraft.world.entity.monster.EntityIllagerIllusioner;
import net.minecraft.world.entity.monster.EntityPillager;
import net.minecraft.world.entity.monster.EntityVex;
import net.minecraft.world.entity.monster.EntityVindicator;
import net.minecraft.world.entity.monster.EntityZoglin;
import net.minecraft.world.entity.monster.EntityZombie;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtil;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.trading.MerchantRecipe;
import net.minecraft.world.item.trading.MerchantRecipeList;
import net.minecraft.world.level.World;
import net.minecraft.world.phys.Vec3D;
import org.apache.commons.lang3.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftMerchantRecipe;
import org.bukkit.entity.AbstractVillager;
import org.bukkit.event.entity.VillagerAcquireTradeEvent;

public class EntityVillagerTrader extends EntityVillagerAbstract {
   private static final int bU = 5;
   @Nullable
   private BlockPosition bV;
   private int bW;

   public EntityVillagerTrader(EntityTypes<? extends EntityVillagerTrader> entitytypes, World world) {
      super(entitytypes, world);
      this.t(48000);
   }

   @Override
   protected void x() {
      this.bN.a(0, new PathfinderGoalFloat(this));
      this.bN
         .a(
            0,
            new PathfinderGoalUseItem<>(
               this, PotionUtil.a(new ItemStack(Items.rr), Potions.i), SoundEffects.yO, entityvillagertrader -> this.H.N() && !entityvillagertrader.ca()
            )
         );
      this.bN
         .a(0, new PathfinderGoalUseItem<>(this, new ItemStack(Items.pM), SoundEffects.yT, entityvillagertrader -> this.H.M() && entityvillagertrader.ca()));
      this.bN.a(1, new PathfinderGoalTradeWithPlayer(this));
      this.bN.a(1, new PathfinderGoalAvoidTarget<>(this, EntityZombie.class, 8.0F, 0.5, 0.5));
      this.bN.a(1, new PathfinderGoalAvoidTarget<>(this, EntityEvoker.class, 12.0F, 0.5, 0.5));
      this.bN.a(1, new PathfinderGoalAvoidTarget<>(this, EntityVindicator.class, 8.0F, 0.5, 0.5));
      this.bN.a(1, new PathfinderGoalAvoidTarget<>(this, EntityVex.class, 8.0F, 0.5, 0.5));
      this.bN.a(1, new PathfinderGoalAvoidTarget<>(this, EntityPillager.class, 15.0F, 0.5, 0.5));
      this.bN.a(1, new PathfinderGoalAvoidTarget<>(this, EntityIllagerIllusioner.class, 12.0F, 0.5, 0.5));
      this.bN.a(1, new PathfinderGoalAvoidTarget<>(this, EntityZoglin.class, 10.0F, 0.5, 0.5));
      this.bN.a(1, new PathfinderGoalPanic(this, 0.5));
      this.bN.a(1, new PathfinderGoalLookAtTradingPlayer(this));
      this.bN.a(2, new EntityVillagerTrader.a(this, 2.0, 0.35));
      this.bN.a(4, new PathfinderGoalMoveTowardsRestriction(this, 0.35));
      this.bN.a(8, new PathfinderGoalRandomStrollLand(this, 0.35));
      this.bN.a(9, new PathfinderGoalInteract(this, EntityHuman.class, 3.0F, 1.0F));
      this.bN.a(10, new PathfinderGoalLookAtPlayer(this, EntityInsentient.class, 8.0F));
   }

   @Nullable
   @Override
   public EntityAgeable a(WorldServer worldserver, EntityAgeable entityageable) {
      return null;
   }

   @Override
   public boolean fV() {
      return false;
   }

   @Override
   public EnumInteractionResult b(EntityHuman entityhuman, EnumHand enumhand) {
      ItemStack itemstack = entityhuman.b(enumhand);
      if (!itemstack.a(Items.sN) && this.bq() && !this.fT() && !this.y_()) {
         if (enumhand == EnumHand.a) {
            entityhuman.a(StatisticList.S);
         }

         if (this.fU().isEmpty()) {
            return EnumInteractionResult.a(this.H.B);
         } else {
            if (!this.H.B) {
               this.e(entityhuman);
               this.a(entityhuman, this.G_(), 1);
            }

            return EnumInteractionResult.a(this.H.B);
         }
      } else {
         return super.b(entityhuman, enumhand);
      }
   }

   @Override
   protected void fZ() {
      VillagerTrades.IMerchantRecipeOption[] avillagertrades_imerchantrecipeoption = (VillagerTrades.IMerchantRecipeOption[])VillagerTrades.b.get(1);
      VillagerTrades.IMerchantRecipeOption[] avillagertrades_imerchantrecipeoption1 = (VillagerTrades.IMerchantRecipeOption[])VillagerTrades.b.get(2);
      if (avillagertrades_imerchantrecipeoption != null && avillagertrades_imerchantrecipeoption1 != null) {
         if (this.H.G().b(FeatureFlags.c)) {
            VillagerTrades.IMerchantRecipeOption[] avillagertrades_imerchantrecipeoption2 = (VillagerTrades.IMerchantRecipeOption[])VillagerTrades.c.get(1);
            if (avillagertrades_imerchantrecipeoption2 != null) {
               avillagertrades_imerchantrecipeoption = (VillagerTrades.IMerchantRecipeOption[])ArrayUtils.addAll(
                  avillagertrades_imerchantrecipeoption, avillagertrades_imerchantrecipeoption2
               );
            }
         }

         MerchantRecipeList merchantrecipelist = this.fU();
         this.a(merchantrecipelist, avillagertrades_imerchantrecipeoption, 5);
         int i = this.af.a(avillagertrades_imerchantrecipeoption1.length);
         VillagerTrades.IMerchantRecipeOption villagertrades_imerchantrecipeoption = avillagertrades_imerchantrecipeoption1[i];
         MerchantRecipe merchantrecipe = villagertrades_imerchantrecipeoption.a(this, this.af);
         if (merchantrecipe != null) {
            VillagerAcquireTradeEvent event = new VillagerAcquireTradeEvent((AbstractVillager)this.getBukkitEntity(), merchantrecipe.asBukkit());
            if (this.valid) {
               Bukkit.getPluginManager().callEvent(event);
            }

            if (!event.isCancelled()) {
               merchantrecipelist.add(CraftMerchantRecipe.fromBukkit(event.getRecipe()).toMinecraft());
            }
         }
      }
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      nbttagcompound.a("DespawnDelay", this.bW);
      if (this.bV != null) {
         nbttagcompound.a("WanderTarget", GameProfileSerializer.a(this.bV));
      }
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      if (nbttagcompound.b("DespawnDelay", 99)) {
         this.bW = nbttagcompound.h("DespawnDelay");
      }

      if (nbttagcompound.e("WanderTarget")) {
         this.bV = GameProfileSerializer.b(nbttagcompound.p("WanderTarget"));
      }

      this.c_(Math.max(0, this.h()));
   }

   @Override
   public boolean h(double d0) {
      return false;
   }

   @Override
   protected void b(MerchantRecipe merchantrecipe) {
      if (merchantrecipe.s()) {
         int i = 3 + this.af.a(4);
         this.H.b(new EntityExperienceOrb(this.H, this.dl(), this.dn() + 0.5, this.dr(), i));
      }
   }

   @Override
   protected SoundEffect s() {
      return this.fT() ? SoundEffects.yU : SoundEffects.yM;
   }

   @Override
   protected SoundEffect d(DamageSource damagesource) {
      return SoundEffects.yR;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.yN;
   }

   @Override
   protected SoundEffect c(ItemStack itemstack) {
      return itemstack.a(Items.pM) ? SoundEffects.yP : SoundEffects.yQ;
   }

   @Override
   protected SoundEffect w(boolean flag) {
      return flag ? SoundEffects.yV : SoundEffects.yS;
   }

   @Override
   public SoundEffect fW() {
      return SoundEffects.yV;
   }

   public void t(int i) {
      this.bW = i;
   }

   public int gb() {
      return this.bW;
   }

   @Override
   public void b_() {
      super.b_();
      if (!this.H.B) {
         this.gc();
      }
   }

   private void gc() {
      if (this.bW > 0 && !this.fT() && --this.bW == 0) {
         this.ai();
      }
   }

   public void g(@Nullable BlockPosition blockposition) {
      this.bV = blockposition;
   }

   @Nullable
   BlockPosition gd() {
      return this.bV;
   }

   private class a extends PathfinderGoal {
      final EntityVillagerTrader a;
      final double b;
      final double c;

      a(EntityVillagerTrader entityvillagertrader, double d0, double d1) {
         this.a = entityvillagertrader;
         this.b = d0;
         this.c = d1;
         this.a(EnumSet.of(PathfinderGoal.Type.a));
      }

      @Override
      public void d() {
         this.a.g(null);
         EntityVillagerTrader.this.bM.n();
      }

      @Override
      public boolean a() {
         BlockPosition blockposition = this.a.gd();
         return blockposition != null && this.a(blockposition, this.b);
      }

      @Override
      public void e() {
         BlockPosition blockposition = this.a.gd();
         if (blockposition != null && EntityVillagerTrader.this.bM.l()) {
            if (this.a(blockposition, 10.0)) {
               Vec3D vec3d = new Vec3D(
                     (double)blockposition.u() - this.a.dl(), (double)blockposition.v() - this.a.dn(), (double)blockposition.w() - this.a.dr()
                  )
                  .d();
               Vec3D vec3d1 = vec3d.a(10.0).b(this.a.dl(), this.a.dn(), this.a.dr());
               EntityVillagerTrader.this.bM.a(vec3d1.c, vec3d1.d, vec3d1.e, this.c);
            } else {
               EntityVillagerTrader.this.bM.a((double)blockposition.u(), (double)blockposition.v(), (double)blockposition.w(), this.c);
            }
         }
      }

      private boolean a(BlockPosition blockposition, double d0) {
         return !blockposition.a(this.a.de(), d0);
      }
   }
}
