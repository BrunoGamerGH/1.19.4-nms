package net.minecraft.world.entity.vehicle;

import javax.annotation.Nullable;
import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.core.IPosition;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.ContainerUtil;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.IInventory;
import net.minecraft.world.ITileInventory;
import net.minecraft.world.InventoryUtils;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.monster.piglin.PiglinAI;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.World;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameterSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameters;
import net.minecraft.world.phys.Vec3D;

public interface ContainerEntity extends IInventory, ITileInventory {
   Vec3D de();

   @Nullable
   MinecraftKey z();

   void a(@Nullable MinecraftKey var1);

   long A();

   void a(long var1);

   NonNullList<ItemStack> C();

   void D();

   World Y();

   boolean dB();

   @Override
   default boolean aa_() {
      return this.g();
   }

   default void c(NBTTagCompound var0) {
      if (this.z() != null) {
         var0.a("LootTable", this.z().toString());
         if (this.A() != 0L) {
            var0.a("LootTableSeed", this.A());
         }
      } else {
         ContainerUtil.a(var0, this.C());
      }
   }

   default void b_(NBTTagCompound var0) {
      this.D();
      if (var0.b("LootTable", 8)) {
         this.a(new MinecraftKey(var0.l("LootTable")));
         this.a(var0.i("LootTableSeed"));
      } else {
         ContainerUtil.b(var0, this.C());
      }
   }

   default void a(DamageSource var0, World var1, Entity var2) {
      if (var1.W().b(GameRules.h)) {
         InventoryUtils.a(var1, var2, this);
         if (!var1.B) {
            Entity var3 = var0.c();
            if (var3 != null && var3.ae() == EntityTypes.bt) {
               PiglinAI.a((EntityHuman)var3, true);
            }
         }
      }
   }

   default EnumInteractionResult c_(EntityHuman var0) {
      var0.a(this);
      return !var0.H.B ? EnumInteractionResult.b : EnumInteractionResult.a;
   }

   default void f(@Nullable EntityHuman var0) {
      MinecraftServer var1 = this.Y().n();
      if (this.z() != null && var1 != null) {
         LootTable var2 = var1.aH().a(this.z());
         if (var0 != null) {
            CriterionTriggers.N.a((EntityPlayer)var0, this.z());
         }

         this.a(null);
         LootTableInfo.Builder var3 = new LootTableInfo.Builder((WorldServer)this.Y()).a(LootContextParameters.f, this.de()).a(this.A());
         if (var0 != null) {
            var3.a(var0.gf()).a(LootContextParameters.a, var0);
         }

         var2.a(this, var3.a(LootContextParameterSets.b));
      }
   }

   default void f() {
      this.f(null);
      this.C().clear();
   }

   default boolean g() {
      for(ItemStack var1 : this.C()) {
         if (!var1.b()) {
            return false;
         }
      }

      return true;
   }

   default ItemStack e_(int var0) {
      this.f(null);
      ItemStack var1 = this.C().get(var0);
      if (var1.b()) {
         return ItemStack.b;
      } else {
         this.C().set(var0, ItemStack.b);
         return var1;
      }
   }

   default ItemStack f_(int var0) {
      this.f(null);
      return this.C().get(var0);
   }

   default ItemStack b(int var0, int var1) {
      this.f(null);
      return ContainerUtil.a(this.C(), var0, var1);
   }

   default void c(int var0, ItemStack var1) {
      this.f(null);
      this.C().set(var0, var1);
      if (!var1.b() && var1.K() > this.ab_()) {
         var1.f(this.ab_());
      }
   }

   default SlotAccess g_(final int var0) {
      return var0 >= 0 && var0 < this.b() ? new SlotAccess() {
         @Override
         public ItemStack a() {
            return ContainerEntity.this.f_(var0);
         }

         @Override
         public boolean a(ItemStack var0x) {
            ContainerEntity.this.c(var0, var0);
            return true;
         }
      } : SlotAccess.b;
   }

   default boolean g(EntityHuman var0) {
      return !this.dB() && this.de().a((IPosition)var0.de(), 8.0);
   }
}
