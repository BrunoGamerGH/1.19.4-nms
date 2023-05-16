package net.minecraft.world.entity.animal;

import com.google.common.collect.Maps;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyDamageScaler;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityAgeable;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntitySize;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.GroupDataEntity;
import net.minecraft.world.entity.IShearable;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.goal.PathfinderGoalBreed;
import net.minecraft.world.entity.ai.goal.PathfinderGoalEatTile;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFloat;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFollowParent;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLookAtPlayer;
import net.minecraft.world.entity.ai.goal.PathfinderGoalPanic;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomLookaround;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomStrollLand;
import net.minecraft.world.entity.ai.goal.PathfinderGoalTempt;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.inventory.Container;
import net.minecraft.world.inventory.Containers;
import net.minecraft.world.inventory.InventoryCraftResult;
import net.minecraft.world.inventory.InventoryCrafting;
import net.minecraft.world.item.EnumColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDye;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeItemStack;
import net.minecraft.world.item.crafting.Recipes;
import net.minecraft.world.level.IMaterial;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootTables;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.entity.Sheep;
import org.bukkit.event.entity.SheepRegrowWoolEvent;
import org.bukkit.inventory.InventoryView;

public class EntitySheep extends EntityAnimal implements IShearable {
   private static final int bS = 40;
   private static final DataWatcherObject<Byte> bT = DataWatcher.a(EntitySheep.class, DataWatcherRegistry.a);
   private static final Map<EnumColor, IMaterial> bV = SystemUtils.a(Maps.newEnumMap(EnumColor.class), enummap -> {
      enummap.put(EnumColor.a, Blocks.bz);
      enummap.put(EnumColor.b, Blocks.bA);
      enummap.put(EnumColor.c, Blocks.bB);
      enummap.put(EnumColor.d, Blocks.bC);
      enummap.put(EnumColor.e, Blocks.bD);
      enummap.put(EnumColor.f, Blocks.bE);
      enummap.put(EnumColor.g, Blocks.bF);
      enummap.put(EnumColor.h, Blocks.bG);
      enummap.put(EnumColor.i, Blocks.bH);
      enummap.put(EnumColor.j, Blocks.bI);
      enummap.put(EnumColor.k, Blocks.bJ);
      enummap.put(EnumColor.l, Blocks.bK);
      enummap.put(EnumColor.m, Blocks.bL);
      enummap.put(EnumColor.n, Blocks.bM);
      enummap.put(EnumColor.o, Blocks.bN);
      enummap.put(EnumColor.p, Blocks.bO);
   });
   private static final Map<EnumColor, float[]> bW = Maps.newEnumMap(
      Arrays.stream(EnumColor.values()).collect(Collectors.toMap(enumcolor -> enumcolor, EntitySheep::c))
   );
   private int bX;
   private PathfinderGoalEatTile bY;

   private static float[] c(EnumColor enumcolor) {
      if (enumcolor == EnumColor.a) {
         return new float[]{0.9019608F, 0.9019608F, 0.9019608F};
      } else {
         float[] afloat = enumcolor.d();
         float f = 0.75F;
         return new float[]{afloat[0] * 0.75F, afloat[1] * 0.75F, afloat[2] * 0.75F};
      }
   }

   public static float[] a(EnumColor enumcolor) {
      return bW.get(enumcolor);
   }

   public EntitySheep(EntityTypes<? extends EntitySheep> entitytypes, World world) {
      super(entitytypes, world);
   }

   @Override
   protected void x() {
      this.bY = new PathfinderGoalEatTile(this);
      this.bN.a(0, new PathfinderGoalFloat(this));
      this.bN.a(1, new PathfinderGoalPanic(this, 1.25));
      this.bN.a(2, new PathfinderGoalBreed(this, 1.0));
      this.bN.a(3, new PathfinderGoalTempt(this, 1.1, RecipeItemStack.a(Items.oE), false));
      this.bN.a(4, new PathfinderGoalFollowParent(this, 1.1));
      this.bN.a(5, this.bY);
      this.bN.a(6, new PathfinderGoalRandomStrollLand(this, 1.0));
      this.bN.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
      this.bN.a(8, new PathfinderGoalRandomLookaround(this));
   }

   @Override
   protected void U() {
      this.bX = this.bY.h();
      super.U();
   }

   @Override
   public void b_() {
      if (this.H.B) {
         this.bX = Math.max(0, this.bX - 1);
      }

      super.b_();
   }

   public static AttributeProvider.Builder q() {
      return EntityInsentient.y().a(GenericAttributes.a, 8.0).a(GenericAttributes.d, 0.23F);
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(bT, (byte)0);
   }

   @Override
   public MinecraftKey O() {
      if (this.w()) {
         return this.ae().j();
      } else {
         return switch(this.r()) {
            case a -> LootTables.S;
            case b -> LootTables.T;
            case c -> LootTables.U;
            case d -> LootTables.V;
            case e -> LootTables.W;
            case f -> LootTables.X;
            case g -> LootTables.Y;
            case h -> LootTables.Z;
            case i -> LootTables.aa;
            case j -> LootTables.ab;
            case k -> LootTables.ac;
            case l -> LootTables.ad;
            case m -> LootTables.ae;
            case n -> LootTables.af;
            case o -> LootTables.ag;
            case p -> LootTables.ah;
         };
      }
   }

   @Override
   public void b(byte b0) {
      if (b0 == 10) {
         this.bX = 40;
      } else {
         super.b(b0);
      }
   }

   public float C(float f) {
      return this.bX <= 0 ? 0.0F : (this.bX >= 4 && this.bX <= 36 ? 1.0F : (this.bX < 4 ? ((float)this.bX - f) / 4.0F : -((float)(this.bX - 40) - f) / 4.0F));
   }

   public float D(float f) {
      if (this.bX > 4 && this.bX <= 36) {
         float f1 = ((float)(this.bX - 4) - f) / 32.0F;
         return ((float) (Math.PI / 5)) + 0.21991149F * MathHelper.a(f1 * 28.7F);
      } else {
         return this.bX > 0 ? (float) (Math.PI / 5) : this.dy() * (float) (Math.PI / 180.0);
      }
   }

   @Override
   public EnumInteractionResult b(EntityHuman entityhuman, EnumHand enumhand) {
      ItemStack itemstack = entityhuman.b(enumhand);
      if (itemstack.a(Items.rc)) {
         if (this.H.B || !this.a()) {
            return EnumInteractionResult.b;
         } else if (!CraftEventFactory.handlePlayerShearEntityEvent(entityhuman, this, itemstack, enumhand)) {
            return EnumInteractionResult.d;
         } else {
            this.a(SoundCategory.h);
            this.a(GameEvent.Q, entityhuman);
            itemstack.a(1, entityhuman, entityhuman1 -> entityhuman1.d(enumhand));
            return EnumInteractionResult.a;
         }
      } else {
         return super.b(entityhuman, enumhand);
      }
   }

   @Override
   public void a(SoundCategory soundcategory) {
      this.H.a(null, this, SoundEffects.uy, soundcategory, 1.0F, 1.0F);
      this.w(true);
      int i = 1 + this.af.a(3);

      for(int j = 0; j < i; ++j) {
         this.forceDrops = true;
         EntityItem entityitem = this.a(bV.get(this.r()), 1);
         this.forceDrops = false;
         if (entityitem != null) {
            entityitem.f(
               entityitem.dj().b((double)((this.af.i() - this.af.i()) * 0.1F), (double)(this.af.i() * 0.05F), (double)((this.af.i() - this.af.i()) * 0.1F))
            );
         }
      }
   }

   @Override
   public boolean a() {
      return this.bq() && !this.w() && !this.y_();
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      nbttagcompound.a("Sheared", this.w());
      nbttagcompound.a("Color", (byte)this.r().a());
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      this.w(nbttagcompound.q("Sheared"));
      this.b(EnumColor.a(nbttagcompound.f("Color")));
   }

   @Override
   protected SoundEffect s() {
      return SoundEffects.uv;
   }

   @Override
   protected SoundEffect d(DamageSource damagesource) {
      return SoundEffects.ux;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.uw;
   }

   @Override
   protected void b(BlockPosition blockposition, IBlockData iblockdata) {
      this.a(SoundEffects.uz, 0.15F, 1.0F);
   }

   public EnumColor r() {
      return EnumColor.a(this.am.a(bT) & 15);
   }

   public void b(EnumColor enumcolor) {
      byte b0 = this.am.a(bT);
      this.am.b(bT, (byte)(b0 & 240 | enumcolor.a() & 15));
   }

   public boolean w() {
      return (this.am.a(bT) & 16) != 0;
   }

   public void w(boolean flag) {
      byte b0 = this.am.a(bT);
      if (flag) {
         this.am.b(bT, (byte)(b0 | 16));
      } else {
         this.am.b(bT, (byte)(b0 & -17));
      }
   }

   public static EnumColor a(RandomSource randomsource) {
      int i = randomsource.a(100);
      return i < 5
         ? EnumColor.p
         : (i < 10 ? EnumColor.h : (i < 15 ? EnumColor.i : (i < 18 ? EnumColor.m : (randomsource.a(500) == 0 ? EnumColor.g : EnumColor.a))));
   }

   @Nullable
   public EntitySheep b(WorldServer worldserver, EntityAgeable entityageable) {
      EntitySheep entitysheep = EntityTypes.aF.a((World)worldserver);
      if (entitysheep != null) {
         entitysheep.b(this.a(this, (EntitySheep)entityageable));
      }

      return entitysheep;
   }

   @Override
   public void J() {
      SheepRegrowWoolEvent event = new SheepRegrowWoolEvent((Sheep)this.getBukkitEntity());
      this.H.getCraftServer().getPluginManager().callEvent(event);
      if (!event.isCancelled()) {
         super.J();
         this.w(false);
         if (this.y_()) {
            this.b_(60);
         }
      }
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
      this.b(a(worldaccess.r_()));
      return super.a(worldaccess, difficultydamagescaler, enummobspawn, groupdataentity, nbttagcompound);
   }

   private EnumColor a(EntityAnimal entityanimal, EntityAnimal entityanimal1) {
      EnumColor enumcolor = ((EntitySheep)entityanimal).r();
      EnumColor enumcolor1 = ((EntitySheep)entityanimal1).r();
      InventoryCrafting inventorycrafting = a(enumcolor, enumcolor1);
      Optional<Item> optional = this.H
         .q()
         .a(Recipes.a, inventorycrafting, this.H)
         .map(recipecrafting -> recipecrafting.a(inventorycrafting, this.H.u_()))
         .map(ItemStack::c);
      optional = optional.filter(ItemDye.class::isInstance);
      return optional.map(ItemDye.class::cast).map(ItemDye::d).orElseGet(() -> this.H.z.h() ? enumcolor : enumcolor1);
   }

   private static InventoryCrafting a(EnumColor enumcolor, EnumColor enumcolor1) {
      InventoryCrafting inventorycrafting = new InventoryCrafting(new Container(null, -1) {
         @Override
         public ItemStack a(EntityHuman entityhuman, int i) {
            return ItemStack.b;
         }

         @Override
         public boolean a(EntityHuman entityhuman) {
            return false;
         }

         @Override
         public InventoryView getBukkitView() {
            return null;
         }
      }, 2, 1);
      inventorycrafting.a(0, new ItemStack(ItemDye.a(enumcolor)));
      inventorycrafting.a(1, new ItemStack(ItemDye.a(enumcolor1)));
      inventorycrafting.resultInventory = new InventoryCraftResult();
      return inventorycrafting;
   }

   @Override
   protected float b(EntityPose entitypose, EntitySize entitysize) {
      return 0.95F * entitysize.b;
   }
}
