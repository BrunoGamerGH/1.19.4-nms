package net.minecraft.world.level.block.entity;

import javax.annotation.Nullable;
import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.ContainerUtil;
import net.minecraft.world.IInventory;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.world.inventory.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameterSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameters;
import net.minecraft.world.phys.Vec3D;

public abstract class TileEntityLootable extends TileEntityContainer {
   public static final String d = "LootTable";
   public static final String e = "LootTableSeed";
   @Nullable
   public MinecraftKey h;
   public long i;

   protected TileEntityLootable(TileEntityTypes<?> var0, BlockPosition var1, IBlockData var2) {
      super(var0, var1, var2);
   }

   public static void a(IBlockAccess var0, RandomSource var1, BlockPosition var2, MinecraftKey var3) {
      TileEntity var4 = var0.c_(var2);
      if (var4 instanceof TileEntityLootable) {
         ((TileEntityLootable)var4).a(var3, var1.g());
      }
   }

   protected boolean d(NBTTagCompound var0) {
      if (var0.b("LootTable", 8)) {
         this.h = new MinecraftKey(var0.l("LootTable"));
         this.i = var0.i("LootTableSeed");
         return true;
      } else {
         return false;
      }
   }

   protected boolean e(NBTTagCompound var0) {
      if (this.h == null) {
         return false;
      } else {
         var0.a("LootTable", this.h.toString());
         if (this.i != 0L) {
            var0.a("LootTableSeed", this.i);
         }

         return true;
      }
   }

   public void e(@Nullable EntityHuman var0) {
      if (this.h != null && this.o.n() != null) {
         LootTable var1 = this.o.n().aH().a(this.h);
         if (var0 instanceof EntityPlayer) {
            CriterionTriggers.N.a((EntityPlayer)var0, this.h);
         }

         this.h = null;
         LootTableInfo.Builder var2 = new LootTableInfo.Builder((WorldServer)this.o).a(LootContextParameters.f, Vec3D.b(this.p)).a(this.i);
         if (var0 != null) {
            var2.a(var0.gf()).a(LootContextParameters.a, var0);
         }

         var1.a(this, var2.a(LootContextParameterSets.b));
      }
   }

   public void a(MinecraftKey var0, long var1) {
      this.h = var0;
      this.i = var1;
   }

   @Override
   public boolean aa_() {
      this.e(null);
      return this.f().stream().allMatch(ItemStack::b);
   }

   @Override
   public ItemStack a(int var0) {
      this.e(null);
      return this.f().get(var0);
   }

   @Override
   public ItemStack a(int var0, int var1) {
      this.e(null);
      ItemStack var2 = ContainerUtil.a(this.f(), var0, var1);
      if (!var2.b()) {
         this.e();
      }

      return var2;
   }

   @Override
   public ItemStack b(int var0) {
      this.e(null);
      return ContainerUtil.a(this.f(), var0);
   }

   @Override
   public void a(int var0, ItemStack var1) {
      this.e(null);
      this.f().set(var0, var1);
      if (var1.K() > this.ab_()) {
         var1.f(this.ab_());
      }

      this.e();
   }

   @Override
   public boolean a(EntityHuman var0) {
      return IInventory.a(this, var0);
   }

   @Override
   public void a() {
      this.f().clear();
   }

   protected abstract NonNullList<ItemStack> f();

   protected abstract void a(NonNullList<ItemStack> var1);

   @Override
   public boolean d(EntityHuman var0) {
      return super.d(var0) && (this.h == null || !var0.F_());
   }

   @Nullable
   @Override
   public Container createMenu(int var0, PlayerInventory var1, EntityHuman var2) {
      if (this.d(var2)) {
         this.e(var1.m);
         return this.a(var0, var1);
      } else {
         return null;
      }
   }
}
