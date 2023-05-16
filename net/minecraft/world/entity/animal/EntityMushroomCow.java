package net.minecraft.world.entity.animal;

import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.particles.Particles;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.TagsBlock;
import net.minecraft.tags.TagsItem;
import net.minecraft.util.INamable;
import net.minecraft.util.RandomSource;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.effect.MobEffectList;
import net.minecraft.world.entity.EntityAgeable;
import net.minecraft.world.entity.EntityLightning;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.IShearable;
import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemLiquidUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemSuspiciousStew;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SuspiciousEffectHolder;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.gameevent.GameEvent;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.entity.Item;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityTransformEvent.TransformReason;

public class EntityMushroomCow extends EntityCow implements IShearable, VariantHolder<EntityMushroomCow.Type> {
   private static final DataWatcherObject<String> bS = DataWatcher.a(EntityMushroomCow.class, DataWatcherRegistry.e);
   private static final int bT = 1024;
   @Nullable
   private MobEffectList bV;
   private int bW;
   @Nullable
   private UUID bX;

   public EntityMushroomCow(EntityTypes<? extends EntityMushroomCow> entitytypes, World world) {
      super(entitytypes, world);
   }

   @Override
   public float a(BlockPosition blockposition, IWorldReader iworldreader) {
      return iworldreader.a_(blockposition.d()).a(Blocks.fk) ? 10.0F : iworldreader.y(blockposition);
   }

   public static boolean c(
      EntityTypes<EntityMushroomCow> entitytypes,
      GeneratorAccess generatoraccess,
      EnumMobSpawn enummobspawn,
      BlockPosition blockposition,
      RandomSource randomsource
   ) {
      return generatoraccess.a_(blockposition.d()).a(TagsBlock.bM) && a(generatoraccess, blockposition);
   }

   @Override
   public void a(WorldServer worldserver, EntityLightning entitylightning) {
      UUID uuid = entitylightning.cs();
      if (!uuid.equals(this.bX)) {
         this.a(this.r() == EntityMushroomCow.Type.a ? EntityMushroomCow.Type.b : EntityMushroomCow.Type.a);
         this.bX = uuid;
         this.a(SoundEffects.na, 2.0F, 1.0F);
      }
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(bS, EntityMushroomCow.Type.a.d);
   }

   @Override
   public EnumInteractionResult b(EntityHuman entityhuman, EnumHand enumhand) {
      ItemStack itemstack = entityhuman.b(enumhand);
      if (itemstack.a(Items.oy) && !this.y_()) {
         boolean flag = false;
         ItemStack itemstack1;
         if (this.bV != null) {
            flag = true;
            itemstack1 = new ItemStack(Items.uU);
            ItemSuspiciousStew.a(itemstack1, this.bV, this.bW);
            this.bV = null;
            this.bW = 0;
         } else {
            itemstack1 = new ItemStack(Items.oz);
         }

         ItemStack itemstack2 = ItemLiquidUtil.a(itemstack, entityhuman, itemstack1, false);
         entityhuman.a(enumhand, itemstack2);
         SoundEffect soundeffect;
         if (flag) {
            soundeffect = SoundEffects.nd;
         } else {
            soundeffect = SoundEffects.nc;
         }

         this.a(soundeffect, 1.0F, 1.0F);
         return EnumInteractionResult.a(this.H.B);
      } else if (!itemstack.a(Items.rc) || !this.a()) {
         if (this.r() == EntityMushroomCow.Type.b && itemstack.a(TagsItem.N)) {
            if (this.bV != null) {
               for(int i = 0; i < 2; ++i) {
                  this.H.a(Particles.ab, this.dl() + this.af.j() / 2.0, this.e(0.5), this.dr() + this.af.j() / 2.0, 0.0, this.af.j() / 5.0, 0.0);
               }
            } else {
               Optional<Pair<MobEffectList, Integer>> optional = this.l(itemstack);
               if (!optional.isPresent()) {
                  return EnumInteractionResult.d;
               }

               Pair<MobEffectList, Integer> pair = (Pair)optional.get();
               if (!entityhuman.fK().d) {
                  itemstack.h(1);
               }

               for(int j = 0; j < 4; ++j) {
                  this.H.a(Particles.q, this.dl() + this.af.j() / 2.0, this.e(0.5), this.dr() + this.af.j() / 2.0, 0.0, this.af.j() / 5.0, 0.0);
               }

               this.bV = (MobEffectList)pair.getLeft();
               this.bW = pair.getRight();
               this.a(SoundEffects.nb, 2.0F, 1.0F);
            }

            return EnumInteractionResult.a(this.H.B);
         } else {
            return super.b(entityhuman, enumhand);
         }
      } else if (!CraftEventFactory.handlePlayerShearEntityEvent(entityhuman, this, itemstack, enumhand)) {
         return EnumInteractionResult.d;
      } else {
         this.a(SoundCategory.h);
         this.a(GameEvent.Q, entityhuman);
         if (!this.H.B) {
            itemstack.a(1, entityhuman, entityhuman1 -> entityhuman1.d(enumhand));
         }

         return EnumInteractionResult.a(this.H.B);
      }
   }

   @Override
   public void a(SoundCategory soundcategory) {
      this.H.a(null, this, SoundEffects.ne, soundcategory, 1.0F, 1.0F);
      if (!this.H.k_()) {
         EntityCow entitycow = EntityTypes.t.a(this.H);
         if (entitycow != null) {
            ((WorldServer)this.H).a(Particles.x, this.dl(), this.e(0.5), this.dr(), 1, 0.0, 0.0, 0.0, 0.0);
            entitycow.b(this.dl(), this.dn(), this.dr(), this.dw(), this.dy());
            entitycow.c(this.eo());
            entitycow.aT = this.aT;
            if (this.aa()) {
               entitycow.b(this.ab());
               entitycow.n(this.cx());
            }

            if (this.fB()) {
               entitycow.fz();
            }

            entitycow.m(this.cm());
            if (CraftEventFactory.callEntityTransformEvent(this, entitycow, TransformReason.SHEARED).isCancelled()) {
               return;
            }

            this.H.addFreshEntity(entitycow, SpawnReason.SHEARED);
            this.ai();

            for(int i = 0; i < 5; ++i) {
               EntityItem entityitem = new EntityItem(this.H, this.dl(), this.e(1.0), this.dr(), new ItemStack(this.r().e.b()));
               EntityDropItemEvent event = new EntityDropItemEvent(this.getBukkitEntity(), (Item)entityitem.getBukkitEntity());
               Bukkit.getPluginManager().callEvent(event);
               if (!event.isCancelled()) {
                  this.H.b(entityitem);
               }
            }
         }
      }
   }

   @Override
   public boolean a() {
      return this.bq() && !this.y_();
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      nbttagcompound.a("Type", this.r().c());
      if (this.bV != null) {
         nbttagcompound.a("EffectId", MobEffectList.a(this.bV));
         nbttagcompound.a("EffectDuration", this.bW);
      }
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      this.a(EntityMushroomCow.Type.a(nbttagcompound.l("Type")));
      if (nbttagcompound.b("EffectId", 1)) {
         this.bV = MobEffectList.a(nbttagcompound.h("EffectId"));
      }

      if (nbttagcompound.b("EffectDuration", 3)) {
         this.bW = nbttagcompound.h("EffectDuration");
      }
   }

   private Optional<Pair<MobEffectList, Integer>> l(ItemStack itemstack) {
      SuspiciousEffectHolder suspiciouseffectholder = SuspiciousEffectHolder.a(itemstack.c());
      return suspiciouseffectholder != null ? Optional.of(Pair.of(suspiciouseffectholder.b(), suspiciouseffectholder.c())) : Optional.empty();
   }

   public void a(EntityMushroomCow.Type entitymushroomcow_type) {
      this.am.b(bS, entitymushroomcow_type.d);
   }

   public EntityMushroomCow.Type r() {
      return EntityMushroomCow.Type.a(this.am.a(bS));
   }

   @Nullable
   public EntityMushroomCow c(WorldServer worldserver, EntityAgeable entityageable) {
      EntityMushroomCow entitymushroomcow = EntityTypes.ao.a((World)worldserver);
      if (entitymushroomcow != null) {
         entitymushroomcow.a(this.a((EntityMushroomCow)entityageable));
      }

      return entitymushroomcow;
   }

   private EntityMushroomCow.Type a(EntityMushroomCow entitymushroomcow) {
      EntityMushroomCow.Type entitymushroomcow_type = this.r();
      EntityMushroomCow.Type entitymushroomcow_type1 = entitymushroomcow.r();
      EntityMushroomCow.Type entitymushroomcow_type2;
      if (entitymushroomcow_type == entitymushroomcow_type1 && this.af.a(1024) == 0) {
         entitymushroomcow_type2 = entitymushroomcow_type == EntityMushroomCow.Type.b ? EntityMushroomCow.Type.a : EntityMushroomCow.Type.b;
      } else {
         entitymushroomcow_type2 = this.af.h() ? entitymushroomcow_type : entitymushroomcow_type1;
      }

      return entitymushroomcow_type2;
   }

   public static enum Type implements INamable {
      a("red", Blocks.cf.o()),
      b("brown", Blocks.ce.o());

      public static final INamable.a<EntityMushroomCow.Type> c = INamable.a(EntityMushroomCow.Type::values);
      final String d;
      final IBlockData e;

      private Type(String s, IBlockData iblockdata) {
         this.d = s;
         this.e = iblockdata;
      }

      public IBlockData a() {
         return this.e;
      }

      @Override
      public String c() {
         return this.d;
      }

      static EntityMushroomCow.Type a(String s) {
         return c.a(s, a);
      }
   }
}
