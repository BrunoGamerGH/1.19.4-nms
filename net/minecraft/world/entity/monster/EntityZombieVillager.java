package net.minecraft.world.entity.monster;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.DynamicOpsNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.DifficultyDamageScaler;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.GroupDataEntity;
import net.minecraft.world.entity.ai.village.ReputationEvent;
import net.minecraft.world.entity.npc.EntityVillager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerDataHolder;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentManager;
import net.minecraft.world.item.trading.MerchantRecipeList;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.level.block.BlockBed;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityPotionEffectEvent.Cause;
import org.bukkit.event.entity.EntityTransformEvent.TransformReason;
import org.slf4j.Logger;

public class EntityZombieVillager extends EntityZombie implements VillagerDataHolder {
   private static final Logger b = LogUtils.getLogger();
   public static final DataWatcherObject<Boolean> c = DataWatcher.a(EntityZombieVillager.class, DataWatcherRegistry.k);
   private static final DataWatcherObject<VillagerData> d = DataWatcher.a(EntityZombieVillager.class, DataWatcherRegistry.t);
   private static final int bW = 3600;
   private static final int bX = 6000;
   private static final int bY = 14;
   private static final int bZ = 4;
   public int ca;
   @Nullable
   public UUID cb;
   @Nullable
   private NBTBase cc;
   @Nullable
   private NBTTagCompound cd;
   private int ce;
   private int lastTick = MinecraftServer.currentTick;

   public EntityZombieVillager(EntityTypes<? extends EntityZombieVillager> entitytypes, World world) {
      super(entitytypes, world);
      BuiltInRegistries.z.a(this.af).ifPresent(holder_c -> this.a(this.gd().a(holder_c.a())));
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(c, false);
      this.am.a(d, new VillagerData(VillagerType.c, VillagerProfession.b, 1));
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      DataResult<NBTBase> dataresult = VillagerData.c.encodeStart(DynamicOpsNBT.a, this.gd());
      Logger logger = b;
      dataresult.resultOrPartial(logger::error).ifPresent(nbtbase -> nbttagcompound.a("VillagerData", nbtbase));
      if (this.cd != null) {
         nbttagcompound.a("Offers", this.cd);
      }

      if (this.cc != null) {
         nbttagcompound.a("Gossips", this.cc);
      }

      nbttagcompound.a("ConversionTime", this.gc() ? this.ca : -1);
      if (this.cb != null) {
         nbttagcompound.a("ConversionPlayer", this.cb);
      }

      nbttagcompound.a("Xp", this.ce);
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      if (nbttagcompound.b("VillagerData", 10)) {
         DataResult<VillagerData> dataresult = VillagerData.c.parse(new Dynamic(DynamicOpsNBT.a, nbttagcompound.c("VillagerData")));
         Logger logger = b;
         dataresult.resultOrPartial(logger::error).ifPresent(this::a);
      }

      if (nbttagcompound.b("Offers", 10)) {
         this.cd = nbttagcompound.p("Offers");
      }

      if (nbttagcompound.b("Gossips", 9)) {
         this.cc = nbttagcompound.c("Gossips", 10);
      }

      if (nbttagcompound.b("ConversionTime", 99) && nbttagcompound.h("ConversionTime") > -1) {
         this.a(nbttagcompound.b("ConversionPlayer") ? nbttagcompound.a("ConversionPlayer") : null, nbttagcompound.h("ConversionTime"));
      }

      if (nbttagcompound.b("Xp", 3)) {
         this.ce = nbttagcompound.h("Xp");
      }
   }

   @Override
   public void l() {
      if (!this.H.B && this.bq() && this.gc()) {
         int i = this.gf();
         int elapsedTicks = MinecraftServer.currentTick - this.lastTick;
         i *= elapsedTicks;
         this.ca -= i;
         if (this.ca <= 0) {
            this.c((WorldServer)this.H);
         }
      }

      super.l();
      this.lastTick = MinecraftServer.currentTick;
   }

   @Override
   public EnumInteractionResult b(EntityHuman entityhuman, EnumHand enumhand) {
      ItemStack itemstack = entityhuman.b(enumhand);
      if (itemstack.a(Items.pi)) {
         if (this.a(MobEffects.r)) {
            if (!entityhuman.fK().d) {
               itemstack.h(1);
            }

            if (!this.H.B) {
               this.a(entityhuman.cs(), this.af.a(2401) + 3600);
            }

            return EnumInteractionResult.a;
         } else {
            return EnumInteractionResult.b;
         }
      } else {
         return super.b(entityhuman, enumhand);
      }
   }

   @Override
   protected boolean fT() {
      return false;
   }

   @Override
   public boolean h(double d0) {
      return !this.gc() && this.ce == 0;
   }

   public boolean gc() {
      return this.aj().a(c);
   }

   public void a(@Nullable UUID uuid, int i) {
      this.cb = uuid;
      this.ca = i;
      this.aj().b(c, true);
      this.removeEffect(MobEffects.r, Cause.CONVERSION);
      this.addEffect(new MobEffect(MobEffects.e, i, Math.min(this.H.ah().a() - 1, 0)), Cause.CONVERSION);
      this.H.a(this, (byte)16);
   }

   @Override
   public void b(byte b0) {
      if (b0 == 16) {
         if (!this.aO()) {
            this.H.a(this.dl(), this.dp(), this.dr(), SoundEffects.AO, this.cX(), 1.0F + this.af.i(), this.af.i() * 0.7F + 0.3F, false);
         }
      } else {
         super.b(b0);
      }
   }

   private void c(WorldServer worldserver) {
      EntityVillager entityvillager = this.convertTo(EntityTypes.bf, false, TransformReason.CURED, SpawnReason.CURED);
      if (entityvillager == null) {
         ((ZombieVillager)this.getBukkitEntity()).setConversionTime(-1);
      } else {
         for(EnumItemSlot enumitemslot : EnumItemSlot.values()) {
            ItemStack itemstack = this.c(enumitemslot);
            if (!itemstack.b()) {
               if (EnchantmentManager.d(itemstack)) {
                  entityvillager.a_(enumitemslot.b() + 300).a(itemstack);
               } else {
                  double d0 = (double)this.f(enumitemslot);
                  if (d0 > 1.0) {
                     this.forceDrops = true;
                     this.b(itemstack);
                     this.forceDrops = false;
                  }
               }
            }
         }

         entityvillager.a(this.gd());
         if (this.cc != null) {
            entityvillager.a(this.cc);
         }

         if (this.cd != null) {
            entityvillager.b(new MerchantRecipeList(this.cd));
         }

         entityvillager.t(this.ce);
         entityvillager.a(worldserver, worldserver.d_(entityvillager.dg()), EnumMobSpawn.i, null, null);
         entityvillager.c(worldserver);
         if (this.cb != null) {
            EntityHuman entityhuman = worldserver.b(this.cb);
            if (entityhuman instanceof EntityPlayer) {
               CriterionTriggers.r.a((EntityPlayer)entityhuman, this, entityvillager);
               worldserver.a(ReputationEvent.a, entityhuman, entityvillager);
            }
         }

         entityvillager.addEffect(new MobEffect(MobEffects.i, 200, 0), Cause.CONVERSION);
         if (!this.aO()) {
            worldserver.a(null, 1027, this.dg(), 0);
         }
      }
   }

   private int gf() {
      int i = 1;
      if (this.af.i() < 0.01F) {
         int j = 0;
         BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();

         for(int k = (int)this.dl() - 4; k < (int)this.dl() + 4 && j < 14; ++k) {
            for(int l = (int)this.dn() - 4; l < (int)this.dn() + 4 && j < 14; ++l) {
               for(int i1 = (int)this.dr() - 4; i1 < (int)this.dr() + 4 && j < 14; ++i1) {
                  IBlockData iblockdata = this.H.a_(blockposition_mutableblockposition.d(k, l, i1));
                  if (iblockdata.a(Blocks.eW) || iblockdata.b() instanceof BlockBed) {
                     if (this.af.i() < 0.3F) {
                        ++i;
                     }

                     ++j;
                  }
               }
            }
         }
      }

      return i;
   }

   @Override
   public float eO() {
      return this.y_() ? (this.af.i() - this.af.i()) * 0.2F + 2.0F : (this.af.i() - this.af.i()) * 0.2F + 1.0F;
   }

   @Override
   public SoundEffect s() {
      return SoundEffects.AM;
   }

   @Override
   public SoundEffect d(DamageSource damagesource) {
      return SoundEffects.AQ;
   }

   @Override
   public SoundEffect x_() {
      return SoundEffects.AP;
   }

   @Override
   public SoundEffect w() {
      return SoundEffects.AR;
   }

   @Override
   protected ItemStack fS() {
      return ItemStack.b;
   }

   public void c(NBTTagCompound nbttagcompound) {
      this.cd = nbttagcompound;
   }

   public void a(NBTBase nbtbase) {
      this.cc = nbtbase;
   }

   @Nullable
   @Override
   public GroupDataEntity a(
      WorldAccess worldaccess,
      DifficultyDamageScaler difficultydamagescaler,
      EnumMobSpawn enummobspawn,
      @Nullable GroupDataEntity groupdataentity,
      @Nullable NBTTagCompound nbttagcompound
   ) {
      this.a(this.gd().a(VillagerType.a(worldaccess.v(this.dg()))));
      return super.a(worldaccess, difficultydamagescaler, enummobspawn, groupdataentity, nbttagcompound);
   }

   @Override
   public void a(VillagerData villagerdata) {
      VillagerData villagerdata1 = this.gd();
      if (villagerdata1.b() != villagerdata.b()) {
         this.cd = null;
      }

      this.am.b(d, villagerdata);
   }

   @Override
   public VillagerData gd() {
      return this.am.a(d);
   }

   public int ge() {
      return this.ce;
   }

   @Override
   public void b(int i) {
      this.ce = i;
   }
}
