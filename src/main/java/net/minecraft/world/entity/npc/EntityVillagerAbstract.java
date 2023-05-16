package net.minecraft.world.entity.npc;

import com.google.common.collect.Sets;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.core.particles.ParticleParam;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.MathHelper;
import net.minecraft.world.DifficultyDamageScaler;
import net.minecraft.world.InventorySubcontainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityAgeable;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntitySize;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.GroupDataEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.IMerchant;
import net.minecraft.world.item.trading.MerchantRecipe;
import net.minecraft.world.item.trading.MerchantRecipeList;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftAbstractVillager;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftMerchant;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftMerchantRecipe;
import org.bukkit.entity.AbstractVillager;
import org.bukkit.event.entity.VillagerAcquireTradeEvent;

public abstract class EntityVillagerAbstract extends EntityAgeable implements InventoryCarrier, NPC, IMerchant {
   private CraftMerchant craftMerchant;
   private static final DataWatcherObject<Integer> bU = DataWatcher.a(EntityVillagerAbstract.class, DataWatcherRegistry.b);
   public static final int bS = 300;
   private static final int bV = 8;
   @Nullable
   private EntityHuman bW;
   @Nullable
   protected MerchantRecipeList bT;
   private final InventorySubcontainer bX = new InventorySubcontainer(8, (CraftAbstractVillager)this.getBukkitEntity());

   @Override
   public CraftMerchant getCraftMerchant() {
      return this.craftMerchant == null ? (this.craftMerchant = new CraftMerchant(this)) : this.craftMerchant;
   }

   public EntityVillagerAbstract(EntityTypes<? extends EntityVillagerAbstract> entitytypes, World world) {
      super(entitytypes, world);
      this.a(PathType.n, 16.0F);
      this.a(PathType.o, -1.0F);
   }

   @Override
   public GroupDataEntity a(
      WorldAccess worldaccess,
      DifficultyDamageScaler difficultydamagescaler,
      EnumMobSpawn enummobspawn,
      @Nullable GroupDataEntity groupdataentity,
      @Nullable NBTTagCompound nbttagcompound
   ) {
      if (groupdataentity == null) {
         groupdataentity = new EntityAgeable.a(false);
      }

      return super.a(worldaccess, difficultydamagescaler, enummobspawn, groupdataentity, nbttagcompound);
   }

   public int q() {
      return this.am.a(bU);
   }

   public void r(int i) {
      this.am.b(bU, i);
   }

   @Override
   public int r() {
      return 0;
   }

   @Override
   protected float b(EntityPose entitypose, EntitySize entitysize) {
      return this.y_() ? 0.81F : 1.62F;
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(bU, 0);
   }

   @Override
   public void e(@Nullable EntityHuman entityhuman) {
      this.bW = entityhuman;
   }

   @Nullable
   @Override
   public EntityHuman fS() {
      return this.bW;
   }

   public boolean fT() {
      return this.bW != null;
   }

   @Override
   public MerchantRecipeList fU() {
      if (this.bT == null) {
         this.bT = new MerchantRecipeList();
         this.fZ();
      }

      return this.bT;
   }

   @Override
   public void a(@Nullable MerchantRecipeList merchantrecipelist) {
   }

   @Override
   public void s(int i) {
   }

   @Override
   public void a(MerchantRecipe merchantrecipe) {
      merchantrecipe.j();
      this.bH = -this.K();
      this.b(merchantrecipe);
      if (this.bW instanceof EntityPlayer) {
         CriterionTriggers.s.a((EntityPlayer)this.bW, this, merchantrecipe.d());
      }
   }

   protected abstract void b(MerchantRecipe var1);

   @Override
   public boolean fV() {
      return true;
   }

   @Override
   public void l(ItemStack itemstack) {
      if (!this.H.B && this.bH > -this.K() + 20) {
         this.bH = -this.K();
         this.a(this.w(!itemstack.b()), this.eN(), this.eO());
      }
   }

   @Override
   public SoundEffect fW() {
      return SoundEffects.yo;
   }

   protected SoundEffect w(boolean flag) {
      return flag ? SoundEffects.yo : SoundEffects.ym;
   }

   public void fX() {
      this.a(SoundEffects.yj, this.eN(), this.eO());
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      MerchantRecipeList merchantrecipelist = this.fU();
      if (!merchantrecipelist.isEmpty()) {
         nbttagcompound.a("Offers", merchantrecipelist.a());
      }

      this.a_(nbttagcompound);
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      if (nbttagcompound.b("Offers", 10)) {
         this.bT = new MerchantRecipeList(nbttagcompound.p("Offers"));
      }

      this.c(nbttagcompound);
   }

   @Nullable
   @Override
   public Entity b(WorldServer worldserver) {
      this.fY();
      return super.b(worldserver);
   }

   protected void fY() {
      this.e(null);
   }

   @Override
   public void a(DamageSource damagesource) {
      super.a(damagesource);
      this.fY();
   }

   protected void a(ParticleParam particleparam) {
      for(int i = 0; i < 5; ++i) {
         double d0 = this.af.k() * 0.02;
         double d1 = this.af.k() * 0.02;
         double d2 = this.af.k() * 0.02;
         this.H.a(particleparam, this.d(1.0), this.do() + 1.0, this.g(1.0), d0, d1, d2);
      }
   }

   @Override
   public boolean a(EntityHuman entityhuman) {
      return false;
   }

   @Override
   public InventorySubcontainer w() {
      return this.bX;
   }

   @Override
   public SlotAccess a_(int i) {
      int j = i - 300;
      return j >= 0 && j < this.bX.b() ? SlotAccess.a(this.bX, j) : super.a_(i);
   }

   protected abstract void fZ();

   protected void a(MerchantRecipeList merchantrecipelist, VillagerTrades.IMerchantRecipeOption[] avillagertrades_imerchantrecipeoption, int i) {
      Set<Integer> set = Sets.newHashSet();
      if (avillagertrades_imerchantrecipeoption.length > i) {
         while(set.size() < i) {
            set.add(this.af.a(avillagertrades_imerchantrecipeoption.length));
         }
      } else {
         for(int j = 0; j < avillagertrades_imerchantrecipeoption.length; ++j) {
            set.add(j);
         }
      }

      for(Integer integer : set) {
         VillagerTrades.IMerchantRecipeOption villagertrades_imerchantrecipeoption = avillagertrades_imerchantrecipeoption[integer];
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
   public Vec3D u(float f) {
      float f1 = MathHelper.i(f, this.aU, this.aT) * (float) (Math.PI / 180.0);
      Vec3D vec3d = new Vec3D(0.0, this.cD().c() - 1.0, 0.2);
      return this.p(f).e(vec3d.b(-f1));
   }

   @Override
   public boolean ga() {
      return this.H.B;
   }
}
