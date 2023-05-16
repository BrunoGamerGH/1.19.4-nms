package net.minecraft.world.entity.animal;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.particles.Particles;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.stats.StatisticList;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.RandomSource;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.EntityAgeable;
import net.minecraft.world.entity.EntityExperienceOrb;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockLightAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.PathType;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntityEnterLoveModeEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public abstract class EntityAnimal extends EntityAgeable {
   protected static final int bU = 6000;
   public int bS;
   @Nullable
   public UUID bT;
   public ItemStack breedItem;

   protected EntityAnimal(EntityTypes<? extends EntityAnimal> entitytypes, World world) {
      super(entitytypes, world);
      this.a(PathType.n, 16.0F);
      this.a(PathType.o, -1.0F);
   }

   @Override
   protected void U() {
      if (this.h() != 0) {
         this.bS = 0;
      }

      super.U();
   }

   @Override
   public void b_() {
      super.b_();
      if (this.h() != 0) {
         this.bS = 0;
      }

      if (this.bS > 0) {
         --this.bS;
         if (this.bS % 10 == 0) {
            double d0 = this.af.k() * 0.02;
            double d1 = this.af.k() * 0.02;
            double d2 = this.af.k() * 0.02;
            this.H.a(Particles.O, this.d(1.0), this.do() + 0.5, this.g(1.0), d0, d1, d2);
         }
      }
   }

   @Override
   public float a(BlockPosition blockposition, IWorldReader iworldreader) {
      return iworldreader.a_(blockposition.d()).a(Blocks.i) ? 10.0F : iworldreader.y(blockposition);
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      nbttagcompound.a("InLove", this.bS);
      if (this.bT != null) {
         nbttagcompound.a("LoveCause", this.bT);
      }
   }

   @Override
   public double bu() {
      return 0.14;
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      this.bS = nbttagcompound.h("InLove");
      this.bT = nbttagcompound.b("LoveCause") ? nbttagcompound.a("LoveCause") : null;
   }

   public static boolean b(
      EntityTypes<? extends EntityAnimal> entitytypes,
      GeneratorAccess generatoraccess,
      EnumMobSpawn enummobspawn,
      BlockPosition blockposition,
      RandomSource randomsource
   ) {
      return generatoraccess.a_(blockposition.d()).a(TagsBlock.bJ) && a(generatoraccess, blockposition);
   }

   protected static boolean a(IBlockLightAccess iblocklightaccess, BlockPosition blockposition) {
      return iblocklightaccess.b(blockposition, 0) > 8;
   }

   @Override
   public int K() {
      return 120;
   }

   @Override
   public boolean h(double d0) {
      return false;
   }

   @Override
   public int dX() {
      return 1 + this.H.z.a(3);
   }

   public boolean m(ItemStack itemstack) {
      return itemstack.a(Items.oE);
   }

   @Override
   public EnumInteractionResult b(EntityHuman entityhuman, EnumHand enumhand) {
      ItemStack itemstack = entityhuman.b(enumhand);
      if (this.m(itemstack)) {
         int i = this.h();
         if (!this.H.B && i == 0 && this.fT()) {
            this.a(entityhuman, enumhand, itemstack);
            this.f(entityhuman);
            return EnumInteractionResult.a;
         }

         if (this.y_()) {
            this.a(entityhuman, enumhand, itemstack);
            this.a(d_(-i), true);
            return EnumInteractionResult.a(this.H.B);
         }

         if (this.H.B) {
            return EnumInteractionResult.b;
         }
      }

      return super.b(entityhuman, enumhand);
   }

   protected void a(EntityHuman entityhuman, EnumHand enumhand, ItemStack itemstack) {
      if (!entityhuman.fK().d) {
         itemstack.h(1);
      }
   }

   public boolean fT() {
      return this.bS <= 0;
   }

   public void f(@Nullable EntityHuman entityhuman) {
      EntityEnterLoveModeEvent entityEnterLoveModeEvent = CraftEventFactory.callEntityEnterLoveModeEvent(entityhuman, this, 600);
      if (!entityEnterLoveModeEvent.isCancelled()) {
         this.bS = entityEnterLoveModeEvent.getTicksInLove();
         if (entityhuman != null) {
            this.bT = entityhuman.cs();
         }

         this.breedItem = entityhuman.fJ().f();
         this.H.a(this, (byte)18);
      }
   }

   public void r(int i) {
      this.bS = i;
   }

   public int fU() {
      return this.bS;
   }

   @Nullable
   public EntityPlayer fV() {
      if (this.bT == null) {
         return null;
      } else {
         EntityHuman entityhuman = this.H.b(this.bT);
         return entityhuman instanceof EntityPlayer ? (EntityPlayer)entityhuman : null;
      }
   }

   public boolean fW() {
      return this.bS > 0;
   }

   public void fX() {
      this.bS = 0;
   }

   public boolean a(EntityAnimal entityanimal) {
      return entityanimal == this ? false : (entityanimal.getClass() != this.getClass() ? false : this.fW() && entityanimal.fW());
   }

   public void a(WorldServer worldserver, EntityAnimal entityanimal) {
      EntityAgeable entityageable = this.a(worldserver, (EntityAgeable)entityanimal);
      if (entityageable != null) {
         EntityPlayer entityplayer = this.fV();
         if (entityplayer == null && entityanimal.fV() != null) {
            entityplayer = entityanimal.fV();
         }

         entityageable.a(true);
         entityageable.b(this.dl(), this.dn(), this.dr(), 0.0F, 0.0F);
         int experience = this.dZ().a(7) + 1;
         EntityBreedEvent entityBreedEvent = CraftEventFactory.callEntityBreedEvent(
            entityageable, this, entityanimal, entityplayer, this.breedItem, experience
         );
         if (entityBreedEvent.isCancelled()) {
            return;
         }

         experience = entityBreedEvent.getExperience();
         if (entityplayer != null) {
            entityplayer.a(StatisticList.P);
            CriterionTriggers.o.a(entityplayer, this, entityanimal, entityageable);
         }

         this.c_(6000);
         entityanimal.c_(6000);
         this.fX();
         entityanimal.fX();
         worldserver.addFreshEntityWithPassengers(entityageable, SpawnReason.BREEDING);
         worldserver.a(this, (byte)18);
         if (worldserver.W().b(GameRules.f) && experience > 0) {
            worldserver.b(new EntityExperienceOrb(worldserver, this.dl(), this.dn(), this.dr(), experience));
         }
      }
   }

   @Override
   public void b(byte b0) {
      if (b0 == 18) {
         for(int i = 0; i < 7; ++i) {
            double d0 = this.af.k() * 0.02;
            double d1 = this.af.k() * 0.02;
            double d2 = this.af.k() * 0.02;
            this.H.a(Particles.O, this.d(1.0), this.do() + 0.5, this.g(1.0), d0, d1, d2);
         }
      } else {
         super.b(b0);
      }
   }
}
