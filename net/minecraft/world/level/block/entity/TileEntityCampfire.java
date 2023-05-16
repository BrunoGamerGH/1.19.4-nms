package net.minecraft.world.level.block.entity;

import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.Particles;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.protocol.game.PacketPlayOutTileEntityData;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Clearable;
import net.minecraft.world.ContainerUtil;
import net.minecraft.world.IInventory;
import net.minecraft.world.InventorySubcontainer;
import net.minecraft.world.InventoryUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingManager;
import net.minecraft.world.item.crafting.RecipeCampfire;
import net.minecraft.world.item.crafting.Recipes;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.BlockCampfire;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.gameevent.GameEvent;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlock;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.event.block.BlockCookEvent;
import org.bukkit.event.block.CampfireStartEvent;
import org.bukkit.inventory.CampfireRecipe;

public class TileEntityCampfire extends TileEntity implements Clearable {
   private static final int a = 2;
   private static final int b = 4;
   private final NonNullList<ItemStack> c = NonNullList.a(4, ItemStack.b);
   public final int[] d = new int[4];
   public final int[] e = new int[4];
   private final CraftingManager.a<IInventory, RecipeCampfire> f = CraftingManager.b(Recipes.e);

   public TileEntityCampfire(BlockPosition blockposition, IBlockData iblockdata) {
      super(TileEntityTypes.G, blockposition, iblockdata);
   }

   public static void a(World world, BlockPosition blockposition, IBlockData iblockdata, TileEntityCampfire tileentitycampfire) {
      boolean flag = false;

      for(int i = 0; i < tileentitycampfire.c.size(); ++i) {
         ItemStack itemstack = tileentitycampfire.c.get(i);
         if (!itemstack.b()) {
            flag = true;
            tileentitycampfire.d[i]++;
            if (tileentitycampfire.d[i] >= tileentitycampfire.e[i]) {
               InventorySubcontainer inventorysubcontainer = new InventorySubcontainer(itemstack);
               ItemStack itemstack1 = tileentitycampfire.f
                  .a(inventorysubcontainer, world)
                  .map(recipecampfire -> recipecampfire.a(inventorysubcontainer, world.u_()))
                  .orElse(itemstack);
               if (itemstack1.a(world.G())) {
                  CraftItemStack source = CraftItemStack.asCraftMirror(itemstack);
                  org.bukkit.inventory.ItemStack result = CraftItemStack.asBukkitCopy(itemstack1);
                  BlockCookEvent blockCookEvent = new BlockCookEvent(CraftBlock.at(world, blockposition), source, result);
                  world.getCraftServer().getPluginManager().callEvent(blockCookEvent);
                  if (blockCookEvent.isCancelled()) {
                     return;
                  }

                  result = blockCookEvent.getResult();
                  itemstack1 = CraftItemStack.asNMSCopy(result);
                  InventoryUtils.a(world, (double)blockposition.u(), (double)blockposition.v(), (double)blockposition.w(), itemstack1);
                  tileentitycampfire.c.set(i, ItemStack.b);
                  world.a(blockposition, iblockdata, iblockdata, 3);
                  world.a(GameEvent.c, blockposition, GameEvent.a.a(iblockdata));
               }
            }
         }
      }

      if (flag) {
         a(world, blockposition, iblockdata);
      }
   }

   public static void b(World world, BlockPosition blockposition, IBlockData iblockdata, TileEntityCampfire tileentitycampfire) {
      boolean flag = false;

      for(int i = 0; i < tileentitycampfire.c.size(); ++i) {
         if (tileentitycampfire.d[i] > 0) {
            flag = true;
            tileentitycampfire.d[i] = MathHelper.a(tileentitycampfire.d[i] - 2, 0, tileentitycampfire.e[i]);
         }
      }

      if (flag) {
         a(world, blockposition, iblockdata);
      }
   }

   public static void c(World world, BlockPosition blockposition, IBlockData iblockdata, TileEntityCampfire tileentitycampfire) {
      RandomSource randomsource = world.z;
      if (randomsource.i() < 0.11F) {
         for(int i = 0; i < randomsource.a(2) + 2; ++i) {
            BlockCampfire.a(world, blockposition, iblockdata.c(BlockCampfire.c), false);
         }
      }

      int i = iblockdata.c(BlockCampfire.e).e();

      for(int j = 0; j < tileentitycampfire.c.size(); ++j) {
         if (!tileentitycampfire.c.get(j).b() && randomsource.i() < 0.2F) {
            EnumDirection enumdirection = EnumDirection.b(Math.floorMod(j + i, 4));
            float f = 0.3125F;
            double d0 = (double)blockposition.u() + 0.5 - (double)((float)enumdirection.j() * 0.3125F) + (double)((float)enumdirection.h().j() * 0.3125F);
            double d1 = (double)blockposition.v() + 0.5;
            double d2 = (double)blockposition.w() + 0.5 - (double)((float)enumdirection.l() * 0.3125F) + (double)((float)enumdirection.h().l() * 0.3125F);

            for(int k = 0; k < 4; ++k) {
               world.a(Particles.ab, d0, d1, d2, 0.0, 5.0E-4, 0.0);
            }
         }
      }
   }

   public NonNullList<ItemStack> c() {
      return this.c;
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      this.c.clear();
      ContainerUtil.b(nbttagcompound, this.c);
      if (nbttagcompound.b("CookingTimes", 11)) {
         int[] aint = nbttagcompound.n("CookingTimes");
         System.arraycopy(aint, 0, this.d, 0, Math.min(this.e.length, aint.length));
      }

      if (nbttagcompound.b("CookingTotalTimes", 11)) {
         int[] aint = nbttagcompound.n("CookingTotalTimes");
         System.arraycopy(aint, 0, this.e, 0, Math.min(this.e.length, aint.length));
      }
   }

   @Override
   protected void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      ContainerUtil.a(nbttagcompound, this.c, true);
      nbttagcompound.a("CookingTimes", this.d);
      nbttagcompound.a("CookingTotalTimes", this.e);
   }

   public PacketPlayOutTileEntityData d() {
      return PacketPlayOutTileEntityData.a(this);
   }

   @Override
   public NBTTagCompound aq_() {
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      ContainerUtil.a(nbttagcompound, this.c, true);
      return nbttagcompound;
   }

   public Optional<RecipeCampfire> a(ItemStack itemstack) {
      return this.c.stream().noneMatch(ItemStack::b) ? Optional.empty() : this.f.a(new InventorySubcontainer(itemstack), this.o);
   }

   public boolean a(@Nullable Entity entity, ItemStack itemstack, int i) {
      for(int j = 0; j < this.c.size(); ++j) {
         ItemStack itemstack1 = this.c.get(j);
         if (itemstack1.b()) {
            CampfireStartEvent event = new CampfireStartEvent(
               CraftBlock.at(this.o, this.p), CraftItemStack.asCraftMirror(itemstack), (CampfireRecipe)this.a(itemstack).get().toBukkitRecipe()
            );
            this.o.getCraftServer().getPluginManager().callEvent(event);
            this.e[j] = event.getTotalCookTime();
            this.d[j] = 0;
            this.c.set(j, itemstack.a(1));
            this.o.a(GameEvent.c, this.p(), GameEvent.a.a(entity, this.q()));
            this.g();
            return true;
         }
      }

      return false;
   }

   private void g() {
      this.e();
      this.k().a(this.p(), this.q(), this.q(), 3);
   }

   @Override
   public void a() {
      this.c.clear();
   }

   public void f() {
      if (this.o != null) {
         this.g();
      }
   }
}
