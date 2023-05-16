package net.minecraft.world.entity.animal.horse;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyDamageScaler;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.IInventory;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityAgeable;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.GroupDataEntity;
import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.animal.EntityAnimal;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemHorseArmor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.level.block.SoundEffectType;

public class EntityHorse extends EntityHorseAbstract implements VariantHolder<HorseColor> {
   private static final UUID bS = UUID.fromString("556E1665-8B10-40C8-8F9D-CF9B1667F295");
   private static final DataWatcherObject<Integer> bT = DataWatcher.a(EntityHorse.class, DataWatcherRegistry.b);

   public EntityHorse(EntityTypes<? extends EntityHorse> var0, World var1) {
      super(var0, var1);
   }

   @Override
   protected void a(RandomSource var0) {
      this.a(GenericAttributes.a).a((double)a(var0::a));
      this.a(GenericAttributes.d).a(b(var0::j));
      this.a(GenericAttributes.m).a(a(var0::j));
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(bT, 0);
   }

   @Override
   public void b(NBTTagCompound var0) {
      super.b(var0);
      var0.a("Variant", this.ga());
      if (!this.cn.a(1).b()) {
         var0.a("ArmorItem", this.cn.a(1).b(new NBTTagCompound()));
      }
   }

   public ItemStack q() {
      return this.c(EnumItemSlot.e);
   }

   private void n(ItemStack var0) {
      this.a(EnumItemSlot.e, var0);
      this.a(EnumItemSlot.e, 0.0F);
   }

   @Override
   public void a(NBTTagCompound var0) {
      super.a(var0);
      this.v(var0.h("Variant"));
      if (var0.b("ArmorItem", 10)) {
         ItemStack var1 = ItemStack.a(var0.p("ArmorItem"));
         if (!var1.b() && this.l(var1)) {
            this.cn.a(1, var1);
         }
      }

      this.gp();
   }

   private void v(int var0) {
      this.am.b(bT, var0);
   }

   private int ga() {
      return this.am.a(bT);
   }

   public void a(HorseColor var0, HorseStyle var1) {
      this.v(var0.a() & 0xFF | var1.a() << 8 & 0xFF00);
   }

   public HorseColor r() {
      return HorseColor.a(this.ga() & 0xFF);
   }

   public void a(HorseColor var0) {
      this.v(var0.a() & 0xFF | this.ga() & -256);
   }

   public HorseStyle fS() {
      return HorseStyle.a((this.ga() & 0xFF00) >> 8);
   }

   @Override
   protected void gp() {
      if (!this.H.B) {
         super.gp();
         this.o(this.cn.a(1));
         this.a(EnumItemSlot.e, 0.0F);
      }
   }

   private void o(ItemStack var0) {
      this.n(var0);
      if (!this.H.B) {
         this.a(GenericAttributes.i).b(bS);
         if (this.l(var0)) {
            int var1 = ((ItemHorseArmor)var0.c()).i();
            if (var1 != 0) {
               this.a(GenericAttributes.i).b(new AttributeModifier(bS, "Horse armor bonus", (double)var1, AttributeModifier.Operation.a));
            }
         }
      }
   }

   @Override
   public void a(IInventory var0) {
      ItemStack var1 = this.q();
      super.a(var0);
      ItemStack var2 = this.q();
      if (this.ag > 20 && this.l(var2) && var1 != var2) {
         this.a(SoundEffects.kX, 0.5F, 1.0F);
      }
   }

   @Override
   protected void a(SoundEffectType var0) {
      super.a(var0);
      if (this.af.a(10) == 0) {
         this.a(SoundEffects.kY, var0.a() * 0.6F, var0.b());
      }
   }

   @Override
   protected SoundEffect s() {
      return SoundEffects.kV;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.kZ;
   }

   @Nullable
   @Override
   protected SoundEffect fZ() {
      return SoundEffects.la;
   }

   @Override
   protected SoundEffect d(DamageSource var0) {
      return SoundEffects.lc;
   }

   @Override
   protected SoundEffect gr() {
      return SoundEffects.kW;
   }

   @Override
   public EnumInteractionResult b(EntityHuman var0, EnumHand var1) {
      boolean var2 = !this.y_() && this.gh() && var0.fz();
      if (!this.bM() && !var2) {
         ItemStack var3 = var0.b(var1);
         if (!var3.b()) {
            if (this.m(var3)) {
               return this.c(var0, var3);
            }

            if (!this.gh()) {
               this.gy();
               return EnumInteractionResult.a(this.H.B);
            }
         }

         return super.b(var0, var1);
      } else {
         return super.b(var0, var1);
      }
   }

   @Override
   public boolean a(EntityAnimal var0) {
      if (var0 == this) {
         return false;
      } else if (!(var0 instanceof EntityHorseDonkey) && !(var0 instanceof EntityHorse)) {
         return false;
      } else {
         return this.gA() && ((EntityHorseAbstract)var0).gA();
      }
   }

   @Nullable
   @Override
   public EntityAgeable a(WorldServer var0, EntityAgeable var1) {
      if (var1 instanceof EntityHorseDonkey) {
         EntityHorseMule var2 = EntityTypes.ap.a((World)var0);
         if (var2 != null) {
            this.a(var1, var2);
         }

         return var2;
      } else {
         EntityHorse var2 = (EntityHorse)var1;
         EntityHorse var3 = EntityTypes.Y.a((World)var0);
         if (var3 != null) {
            int var5 = this.af.a(9);
            HorseColor var4;
            if (var5 < 4) {
               var4 = this.r();
            } else if (var5 < 8) {
               var4 = var2.r();
            } else {
               var4 = SystemUtils.a(HorseColor.values(), this.af);
            }

            int var7 = this.af.a(5);
            HorseStyle var6;
            if (var7 < 2) {
               var6 = this.fS();
            } else if (var7 < 4) {
               var6 = var2.fS();
            } else {
               var6 = SystemUtils.a(HorseStyle.values(), this.af);
            }

            var3.a(var4, var6);
            this.a(var1, var3);
         }

         return var3;
      }
   }

   @Override
   public boolean gB() {
      return true;
   }

   @Override
   public boolean l(ItemStack var0) {
      return var0.c() instanceof ItemHorseArmor;
   }

   @Nullable
   @Override
   public GroupDataEntity a(WorldAccess var0, DifficultyDamageScaler var1, EnumMobSpawn var2, @Nullable GroupDataEntity var3, @Nullable NBTTagCompound var4) {
      RandomSource var5 = var0.r_();
      HorseColor var6;
      if (var3 instanceof EntityHorse.a) {
         var6 = ((EntityHorse.a)var3).a;
      } else {
         var6 = SystemUtils.a(HorseColor.values(), var5);
         var3 = new EntityHorse.a(var6);
      }

      this.a(var6, SystemUtils.a(HorseStyle.values(), var5));
      return super.a(var0, var1, var2, var3, var4);
   }

   public static class a extends EntityAgeable.a {
      public final HorseColor a;

      public a(HorseColor var0) {
         super(true);
         this.a = var0;
      }
   }
}
