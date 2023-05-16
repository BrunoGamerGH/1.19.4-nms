package net.minecraft.world.entity.monster;

import java.util.Collection;
import javax.annotation.Nullable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.TagsItem;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityAreaEffectCloud;
import net.minecraft.world.entity.EntityLightning;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.PowerableMob;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.goal.PathfinderGoalAvoidTarget;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFloat;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLookAtPlayer;
import net.minecraft.world.entity.ai.goal.PathfinderGoalMeleeAttack;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomLookaround;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomStrollLand;
import net.minecraft.world.entity.ai.goal.PathfinderGoalSwell;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalHurtByTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.animal.EntityCat;
import net.minecraft.world.entity.animal.EntityOcelot;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.World;
import net.minecraft.world.level.gameevent.GameEvent;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.CreeperPowerEvent.PowerCause;

public class EntityCreeper extends EntityMonster implements PowerableMob {
   private static final DataWatcherObject<Integer> b = DataWatcher.a(EntityCreeper.class, DataWatcherRegistry.b);
   private static final DataWatcherObject<Boolean> c = DataWatcher.a(EntityCreeper.class, DataWatcherRegistry.k);
   private static final DataWatcherObject<Boolean> d = DataWatcher.a(EntityCreeper.class, DataWatcherRegistry.k);
   private int e;
   public int bS;
   public int bT = 30;
   public int bU = 3;
   private int bV;

   public EntityCreeper(EntityTypes<? extends EntityCreeper> entitytypes, World world) {
      super(entitytypes, world);
   }

   @Override
   protected void x() {
      this.bN.a(1, new PathfinderGoalFloat(this));
      this.bN.a(2, new PathfinderGoalSwell(this));
      this.bN.a(3, new PathfinderGoalAvoidTarget<>(this, EntityOcelot.class, 6.0F, 1.0, 1.2));
      this.bN.a(3, new PathfinderGoalAvoidTarget<>(this, EntityCat.class, 6.0F, 1.0, 1.2));
      this.bN.a(4, new PathfinderGoalMeleeAttack(this, 1.0, false));
      this.bN.a(5, new PathfinderGoalRandomStrollLand(this, 0.8));
      this.bN.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
      this.bN.a(6, new PathfinderGoalRandomLookaround(this));
      this.bO.a(1, new PathfinderGoalNearestAttackableTarget<>(this, EntityHuman.class, true));
      this.bO.a(2, new PathfinderGoalHurtByTarget(this));
   }

   public static AttributeProvider.Builder q() {
      return EntityMonster.fY().a(GenericAttributes.d, 0.25);
   }

   @Override
   public int cp() {
      return this.P_() == null ? 3 : 3 + (int)(this.eo() - 1.0F);
   }

   @Override
   public boolean a(float f, float f1, DamageSource damagesource) {
      boolean flag = super.a(f, f1, damagesource);
      this.bS += (int)(f * 1.5F);
      if (this.bS > this.bT - 5) {
         this.bS = this.bT - 5;
      }

      return flag;
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(b, -1);
      this.am.a(c, false);
      this.am.a(d, false);
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      if (this.am.a(c)) {
         nbttagcompound.a("powered", true);
      }

      nbttagcompound.a("Fuse", (short)this.bT);
      nbttagcompound.a("ExplosionRadius", (byte)this.bU);
      nbttagcompound.a("ignited", this.w());
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      this.am.b(c, nbttagcompound.q("powered"));
      if (nbttagcompound.b("Fuse", 99)) {
         this.bT = nbttagcompound.g("Fuse");
      }

      if (nbttagcompound.b("ExplosionRadius", 99)) {
         this.bU = nbttagcompound.f("ExplosionRadius");
      }

      if (nbttagcompound.q("ignited")) {
         this.fS();
      }
   }

   @Override
   public void l() {
      if (this.bq()) {
         this.e = this.bS;
         if (this.w()) {
            this.b(1);
         }

         int i = this.r();
         if (i > 0 && this.bS == 0) {
            this.a(SoundEffects.fc, 1.0F, 0.5F);
            this.a(GameEvent.M);
         }

         this.bS += i;
         if (this.bS < 0) {
            this.bS = 0;
         }

         if (this.bS >= this.bT) {
            this.bS = this.bT;
            this.fV();
         }
      }

      super.l();
   }

   @Override
   public void i(@Nullable EntityLiving entityliving) {
      if (!(entityliving instanceof Goat)) {
         super.i(entityliving);
      }
   }

   @Override
   protected SoundEffect d(DamageSource damagesource) {
      return SoundEffects.fb;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.fa;
   }

   @Override
   protected void a(DamageSource damagesource, int i, boolean flag) {
      super.a(damagesource, i, flag);
      Entity entity = damagesource.d();
      if (entity != this && entity instanceof EntityCreeper entitycreeper && entitycreeper.fT()) {
         entitycreeper.fU();
         this.a(Items.tr);
      }
   }

   @Override
   public boolean z(Entity entity) {
      return true;
   }

   @Override
   public boolean a() {
      return this.am.a(c);
   }

   public float C(float f) {
      return MathHelper.i(f, (float)this.e, (float)this.bS) / (float)(this.bT - 2);
   }

   public int r() {
      return this.am.a(b);
   }

   public void b(int i) {
      this.am.b(b, i);
   }

   @Override
   public void a(WorldServer worldserver, EntityLightning entitylightning) {
      super.a(worldserver, entitylightning);
      if (!CraftEventFactory.callCreeperPowerEvent(this, entitylightning, PowerCause.LIGHTNING).isCancelled()) {
         this.setPowered(true);
      }
   }

   public void setPowered(boolean powered) {
      this.am.b(c, powered);
   }

   @Override
   protected EnumInteractionResult b(EntityHuman entityhuman, EnumHand enumhand) {
      ItemStack itemstack = entityhuman.b(enumhand);
      if (itemstack.a(TagsItem.aE)) {
         SoundEffect soundeffect = itemstack.a(Items.tb) ? SoundEffects.hz : SoundEffects.hO;
         this.H.a(entityhuman, this.dl(), this.dn(), this.dr(), soundeffect, this.cX(), 1.0F, this.af.i() * 0.4F + 0.8F);
         if (!this.H.B) {
            this.fS();
            if (!itemstack.h()) {
               itemstack.h(1);
            } else {
               itemstack.a(1, entityhuman, entityhuman1 -> entityhuman1.d(enumhand));
            }
         }

         return EnumInteractionResult.a(this.H.B);
      } else {
         return super.b(entityhuman, enumhand);
      }
   }

   public void fV() {
      if (!this.H.B) {
         float f = this.a() ? 2.0F : 1.0F;
         ExplosionPrimeEvent event = new ExplosionPrimeEvent(this.getBukkitEntity(), (float)this.bU * f, false);
         this.H.getCraftServer().getPluginManager().callEvent(event);
         if (!event.isCancelled()) {
            this.aZ = true;
            this.H.a(this, this.dl(), this.dn(), this.dr(), event.getRadius(), event.getFire(), World.a.c);
            this.ai();
            this.fW();
         } else {
            this.bS = 0;
         }
      }
   }

   private void fW() {
      Collection<MobEffect> collection = this.el();
      if (!collection.isEmpty()) {
         EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(this.H, this.dl(), this.dn(), this.dr());
         entityareaeffectcloud.a(this);
         entityareaeffectcloud.a(2.5F);
         entityareaeffectcloud.b(-0.5F);
         entityareaeffectcloud.d(10);
         entityareaeffectcloud.b(entityareaeffectcloud.m() / 2);
         entityareaeffectcloud.c(-entityareaeffectcloud.h() / (float)entityareaeffectcloud.m());

         for(MobEffect mobeffect : collection) {
            entityareaeffectcloud.a(new MobEffect(mobeffect));
         }

         this.H.addFreshEntity(entityareaeffectcloud, SpawnReason.EXPLOSION);
      }
   }

   public boolean w() {
      return this.am.a(d);
   }

   public void fS() {
      this.am.b(d, true);
   }

   public boolean fT() {
      return this.a() && this.bV < 1;
   }

   public void fU() {
      ++this.bV;
   }
}
