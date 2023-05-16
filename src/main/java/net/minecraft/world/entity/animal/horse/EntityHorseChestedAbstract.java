package net.minecraft.world.entity.animal.horse;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.RandomSource;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Blocks;

public abstract class EntityHorseChestedAbstract extends EntityHorseAbstract {
   private static final DataWatcherObject<Boolean> bT = DataWatcher.a(EntityHorseChestedAbstract.class, DataWatcherRegistry.k);
   public static final int bS = 15;

   protected EntityHorseChestedAbstract(EntityTypes<? extends EntityHorseChestedAbstract> var0, World var1) {
      super(var0, var1);
      this.cr = false;
   }

   @Override
   protected void a(RandomSource var0) {
      this.a(GenericAttributes.a).a((double)a(var0::a));
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(bT, false);
   }

   public static AttributeProvider.Builder q() {
      return gs().a(GenericAttributes.d, 0.175F).a(GenericAttributes.m, 0.5);
   }

   public boolean r() {
      return this.am.a(bT);
   }

   public void w(boolean var0) {
      this.am.b(bT, var0);
   }

   @Override
   protected int U_() {
      return this.r() ? 17 : super.U_();
   }

   @Override
   public double bv() {
      return super.bv() - 0.25;
   }

   @Override
   protected void er() {
      super.er();
      if (this.r()) {
         if (!this.H.B) {
            this.a(Blocks.cu);
         }

         this.w(false);
      }
   }

   @Override
   public void b(NBTTagCompound var0) {
      super.b(var0);
      var0.a("ChestedHorse", this.r());
      if (this.r()) {
         NBTTagList var1 = new NBTTagList();

         for(int var2 = 2; var2 < this.cn.b(); ++var2) {
            ItemStack var3 = this.cn.a(var2);
            if (!var3.b()) {
               NBTTagCompound var4 = new NBTTagCompound();
               var4.a("Slot", (byte)var2);
               var3.b(var4);
               var1.add(var4);
            }
         }

         var0.a("Items", var1);
      }
   }

   @Override
   public void a(NBTTagCompound var0) {
      super.a(var0);
      this.w(var0.q("ChestedHorse"));
      this.go();
      if (this.r()) {
         NBTTagList var1 = var0.c("Items", 10);

         for(int var2 = 0; var2 < var1.size(); ++var2) {
            NBTTagCompound var3 = var1.a(var2);
            int var4 = var3.f("Slot") & 255;
            if (var4 >= 2 && var4 < this.cn.b()) {
               this.cn.a(var4, ItemStack.a(var3));
            }
         }
      }

      this.gp();
   }

   @Override
   public SlotAccess a_(int var0) {
      return var0 == 499 ? new SlotAccess() {
         @Override
         public ItemStack a() {
            return EntityHorseChestedAbstract.this.r() ? new ItemStack(Items.ep) : ItemStack.b;
         }

         @Override
         public boolean a(ItemStack var0) {
            if (var0.b()) {
               if (EntityHorseChestedAbstract.this.r()) {
                  EntityHorseChestedAbstract.this.w(false);
                  EntityHorseChestedAbstract.this.go();
               }

               return true;
            } else if (var0.a(Items.ep)) {
               if (!EntityHorseChestedAbstract.this.r()) {
                  EntityHorseChestedAbstract.this.w(true);
                  EntityHorseChestedAbstract.this.go();
               }

               return true;
            } else {
               return false;
            }
         }
      } : super.a_(var0);
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

            if (!this.r() && var3.a(Items.ep)) {
               this.d(var0, var3);
               return EnumInteractionResult.a(this.H.B);
            }
         }

         return super.b(var0, var1);
      } else {
         return super.b(var0, var1);
      }
   }

   private void d(EntityHuman var0, ItemStack var1) {
      this.w(true);
      this.fS();
      if (!var0.fK().d) {
         var1.h(1);
      }

      this.go();
   }

   @Override
   protected void fS() {
      this.a(SoundEffects.fX, 1.0F, (this.af.i() - this.af.i()) * 0.2F + 1.0F);
   }

   public int ga() {
      return 5;
   }
}
