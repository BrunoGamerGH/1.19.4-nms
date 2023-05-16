package net.minecraft.world.level.block.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.Holder;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.TagsItem;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ContainerUtil;
import net.minecraft.world.IInventory;
import net.minecraft.world.IWorldInventory;
import net.minecraft.world.entity.EntityExperienceOrb;
import net.minecraft.world.entity.player.AutoRecipeStackManager;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.inventory.AutoRecipeOutput;
import net.minecraft.world.inventory.IContainerProperties;
import net.minecraft.world.inventory.RecipeHolder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingManager;
import net.minecraft.world.item.crafting.IRecipe;
import net.minecraft.world.item.crafting.RecipeCooking;
import net.minecraft.world.item.crafting.Recipes;
import net.minecraft.world.level.IMaterial;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.BlockFurnace;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlock;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftMagicNumbers;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockExpEvent;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.FurnaceStartSmeltEvent;
import org.bukkit.inventory.CookingRecipe;

public abstract class TileEntityFurnace extends TileEntityContainer implements IWorldInventory, RecipeHolder, AutoRecipeOutput {
   protected static final int c = 0;
   protected static final int d = 1;
   protected static final int e = 2;
   public static final int f = 0;
   private static final int[] r = new int[1];
   private static final int[] s = new int[]{2, 1};
   private static final int[] t = new int[]{1};
   public static final int g = 1;
   public static final int h = 2;
   public static final int i = 3;
   public static final int j = 4;
   public static final int k = 200;
   public static final int l = 2;
   protected NonNullList<ItemStack> m;
   public int u;
   int v;
   public int w;
   public int x;
   protected final IContainerProperties n;
   public final Object2IntOpenHashMap<MinecraftKey> y;
   private final CraftingManager.a<IInventory, ? extends RecipeCooking> z;
   private int maxStack = 64;
   public List<HumanEntity> transaction = new ArrayList();

   protected TileEntityFurnace(
      TileEntityTypes<?> tileentitytypes, BlockPosition blockposition, IBlockData iblockdata, Recipes<? extends RecipeCooking> recipes
   ) {
      super(tileentitytypes, blockposition, iblockdata);
      this.m = NonNullList.a(3, ItemStack.b);
      this.n = new IContainerProperties() {
         @Override
         public int a(int i) {
            switch(i) {
               case 0:
                  return TileEntityFurnace.this.u;
               case 1:
                  return TileEntityFurnace.this.v;
               case 2:
                  return TileEntityFurnace.this.w;
               case 3:
                  return TileEntityFurnace.this.x;
               default:
                  return 0;
            }
         }

         @Override
         public void a(int i, int j) {
            switch(i) {
               case 0:
                  TileEntityFurnace.this.u = j;
                  break;
               case 1:
                  TileEntityFurnace.this.v = j;
                  break;
               case 2:
                  TileEntityFurnace.this.w = j;
                  break;
               case 3:
                  TileEntityFurnace.this.x = j;
            }
         }

         @Override
         public int a() {
            return 4;
         }
      };
      this.y = new Object2IntOpenHashMap();
      this.z = CraftingManager.b(recipes);
   }

   public static Map<Item, Integer> f() {
      Map<Item, Integer> map = Maps.newLinkedHashMap();
      a(map, Items.pI, 20000);
      a(map, Blocks.iA, 16000);
      a(map, Items.rn, 2400);
      a(map, Items.nE, 1600);
      a(map, Items.nF, 1600);
      a(map, TagsItem.q, 300);
      a(map, TagsItem.B, 300);
      a(map, TagsItem.b, 300);
      a(map, Blocks.w, 300);
      a(map, TagsItem.h, 300);
      a(map, Blocks.hT, 300);
      a(map, TagsItem.i, 150);
      a(map, Blocks.jB, 150);
      a(map, TagsItem.m, 300);
      a(map, TagsItem.l, 300);
      a(map, TagsItem.j, 300);
      a(map, TagsItem.k, 300);
      a(map, Blocks.aX, 300);
      a(map, Blocks.ck, 300);
      a(map, Blocks.cl, 300);
      a(map, Blocks.nW, 300);
      a(map, Blocks.dS, 300);
      a(map, Blocks.cu, 300);
      a(map, Blocks.gU, 300);
      a(map, Blocks.cz, 300);
      a(map, Blocks.gY, 300);
      a(map, TagsItem.D, 300);
      a(map, Items.nC, 300);
      a(map, Items.qd, 300);
      a(map, Blocks.cN, 300);
      a(map, TagsItem.ao, 200);
      a(map, TagsItem.aD, 800);
      a(map, Items.nU, 200);
      a(map, Items.nT, 200);
      a(map, Items.nX, 200);
      a(map, Items.nW, 200);
      a(map, Items.nV, 200);
      a(map, TagsItem.g, 200);
      a(map, TagsItem.al, 1200);
      a(map, TagsItem.a, 100);
      a(map, TagsItem.d, 100);
      a(map, Items.ox, 100);
      a(map, TagsItem.o, 100);
      a(map, Items.oy, 100);
      a(map, TagsItem.f, 67);
      a(map, Blocks.mb, 4001);
      a(map, Items.uT, 300);
      a(map, Blocks.mV, 50);
      a(map, Blocks.bu, 100);
      a(map, Blocks.nO, 50);
      a(map, Blocks.nP, 300);
      a(map, Blocks.nQ, 300);
      a(map, Blocks.nT, 300);
      a(map, Blocks.nU, 300);
      a(map, Blocks.nX, 300);
      a(map, Blocks.oY, 300);
      a(map, Blocks.rs, 100);
      a(map, Blocks.rt, 100);
      a(map, Blocks.ab, 300);
      return map;
   }

   @Override
   public List<ItemStack> getContents() {
      return this.m;
   }

   @Override
   public void onOpen(CraftHumanEntity who) {
      this.transaction.add(who);
   }

   @Override
   public void onClose(CraftHumanEntity who) {
      this.transaction.remove(who);
   }

   @Override
   public List<HumanEntity> getViewers() {
      return this.transaction;
   }

   @Override
   public int ab_() {
      return this.maxStack;
   }

   @Override
   public void setMaxStackSize(int size) {
      this.maxStack = size;
   }

   public Object2IntOpenHashMap<MinecraftKey> getRecipesUsed() {
      return this.y;
   }

   private static boolean b(Item item) {
      return item.j().a(TagsItem.af);
   }

   private static void a(Map<Item, Integer> map, TagKey<Item> tagkey, int i) {
      for(Holder<Item> holder : BuiltInRegistries.i.c(tagkey)) {
         if (!b(holder.a())) {
            map.put(holder.a(), i);
         }
      }
   }

   private static void a(Map<Item, Integer> map, IMaterial imaterial, int i) {
      Item item = imaterial.k();
      if (b(item)) {
         if (SharedConstants.aO) {
            throw (IllegalStateException)SystemUtils.b(
               new IllegalStateException(
                  "A developer tried to explicitly make fire resistant item " + item.m(null).getString() + " a furnace fuel. That will not work!"
               )
            );
         }
      } else {
         map.put(item, i);
      }
   }

   private boolean i() {
      return this.u > 0;
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      this.m = NonNullList.a(this.b(), ItemStack.b);
      ContainerUtil.b(nbttagcompound, this.m);
      this.u = nbttagcompound.g("BurnTime");
      this.w = nbttagcompound.g("CookTime");
      this.x = nbttagcompound.g("CookTimeTotal");
      this.v = this.a(this.m.get(1));
      NBTTagCompound nbttagcompound1 = nbttagcompound.p("RecipesUsed");

      for(String s : nbttagcompound1.e()) {
         this.y.put(new MinecraftKey(s), nbttagcompound1.h(s));
      }
   }

   @Override
   protected void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      nbttagcompound.a("BurnTime", (short)this.u);
      nbttagcompound.a("CookTime", (short)this.w);
      nbttagcompound.a("CookTimeTotal", (short)this.x);
      ContainerUtil.a(nbttagcompound, this.m);
      NBTTagCompound nbttagcompound1 = new NBTTagCompound();
      this.y.forEach((minecraftkey, integer) -> nbttagcompound1.a(minecraftkey.toString(), integer));
      nbttagcompound.a("RecipesUsed", nbttagcompound1);
   }

   public static void a(World world, BlockPosition blockposition, IBlockData iblockdata, TileEntityFurnace tileentityfurnace) {
      boolean flag = tileentityfurnace.i();
      boolean flag1 = false;
      if (tileentityfurnace.i()) {
         --tileentityfurnace.u;
      }

      ItemStack itemstack = tileentityfurnace.m.get(1);
      boolean flag2 = !tileentityfurnace.m.get(0).b();
      boolean flag3 = !itemstack.b();
      if (tileentityfurnace.i() || flag3 && flag2) {
         IRecipe irecipe;
         if (flag2) {
            irecipe = tileentityfurnace.z.a(tileentityfurnace, world).orElse(null);
         } else {
            irecipe = null;
         }

         int i = tileentityfurnace.ab_();
         if (!tileentityfurnace.i() && a(world.u_(), irecipe, tileentityfurnace.m, i)) {
            CraftItemStack fuel = CraftItemStack.asCraftMirror(itemstack);
            FurnaceBurnEvent furnaceBurnEvent = new FurnaceBurnEvent(CraftBlock.at(world, blockposition), fuel, tileentityfurnace.a(itemstack));
            world.getCraftServer().getPluginManager().callEvent(furnaceBurnEvent);
            if (furnaceBurnEvent.isCancelled()) {
               return;
            }

            tileentityfurnace.u = furnaceBurnEvent.getBurnTime();
            tileentityfurnace.v = tileentityfurnace.u;
            if (tileentityfurnace.i() && furnaceBurnEvent.isBurning()) {
               flag1 = true;
               if (flag3) {
                  Item item = itemstack.c();
                  itemstack.h(1);
                  if (itemstack.b()) {
                     Item item1 = item.s();
                     tileentityfurnace.m.set(1, item1 == null ? ItemStack.b : new ItemStack(item1));
                  }
               }
            }
         }

         if (tileentityfurnace.i() && a(world.u_(), irecipe, tileentityfurnace.m, i)) {
            if (irecipe != null && tileentityfurnace.w == 0) {
               CraftItemStack source = CraftItemStack.asCraftMirror(tileentityfurnace.m.get(0));
               CookingRecipe<?> recipe = (CookingRecipe)irecipe.toBukkitRecipe();
               FurnaceStartSmeltEvent event = new FurnaceStartSmeltEvent(CraftBlock.at(world, blockposition), source, recipe);
               world.getCraftServer().getPluginManager().callEvent(event);
               tileentityfurnace.x = event.getTotalCookTime();
            }

            ++tileentityfurnace.w;
            if (tileentityfurnace.w == tileentityfurnace.x) {
               tileentityfurnace.w = 0;
               tileentityfurnace.x = a(world, tileentityfurnace);
               if (burn(tileentityfurnace.o, tileentityfurnace.p, world.u_(), irecipe, tileentityfurnace.m, i)) {
                  tileentityfurnace.a(irecipe);
               }

               flag1 = true;
            }
         } else {
            tileentityfurnace.w = 0;
         }
      } else if (!tileentityfurnace.i() && tileentityfurnace.w > 0) {
         tileentityfurnace.w = MathHelper.a(tileentityfurnace.w - 2, 0, tileentityfurnace.x);
      }

      if (flag != tileentityfurnace.i()) {
         flag1 = true;
         iblockdata = iblockdata.a(BlockFurnace.b, Boolean.valueOf(tileentityfurnace.i()));
         world.a(blockposition, iblockdata, 3);
      }

      if (flag1) {
         a(world, blockposition, iblockdata);
      }
   }

   private static boolean a(IRegistryCustom iregistrycustom, @Nullable IRecipe<?> irecipe, NonNullList<ItemStack> nonnulllist, int i) {
      if (!nonnulllist.get(0).b() && irecipe != null) {
         ItemStack itemstack = irecipe.a(iregistrycustom);
         if (itemstack.b()) {
            return false;
         } else {
            ItemStack itemstack1 = nonnulllist.get(2);
            return itemstack1.b()
               ? true
               : (!itemstack1.a(itemstack) ? false : (itemstack1.K() < i && itemstack1.K() < itemstack1.f() ? true : itemstack1.K() < itemstack.f()));
         }
      } else {
         return false;
      }
   }

   private static boolean burn(
      World world, BlockPosition blockposition, IRegistryCustom iregistrycustom, @Nullable IRecipe<?> irecipe, NonNullList<ItemStack> nonnulllist, int i
   ) {
      if (irecipe != null && a(iregistrycustom, irecipe, nonnulllist, i)) {
         ItemStack itemstack = nonnulllist.get(0);
         ItemStack itemstack1 = irecipe.a(iregistrycustom);
         ItemStack itemstack2 = nonnulllist.get(2);
         CraftItemStack source = CraftItemStack.asCraftMirror(itemstack);
         org.bukkit.inventory.ItemStack result = CraftItemStack.asBukkitCopy(itemstack1);
         FurnaceSmeltEvent furnaceSmeltEvent = new FurnaceSmeltEvent(CraftBlock.at(world, blockposition), source, result);
         world.getCraftServer().getPluginManager().callEvent(furnaceSmeltEvent);
         if (furnaceSmeltEvent.isCancelled()) {
            return false;
         } else {
            result = furnaceSmeltEvent.getResult();
            itemstack1 = CraftItemStack.asNMSCopy(result);
            if (!itemstack1.b()) {
               if (itemstack2.b()) {
                  nonnulllist.set(2, itemstack1.o());
               } else {
                  if (!CraftItemStack.asCraftMirror(itemstack2).isSimilar(result)) {
                     return false;
                  }

                  itemstack2.g(itemstack1.K());
               }
            }

            if (itemstack.a(Blocks.aO.k()) && !nonnulllist.get(1).b() && nonnulllist.get(1).a(Items.pG)) {
               nonnulllist.set(1, new ItemStack(Items.pH));
            }

            itemstack.h(1);
            return true;
         }
      } else {
         return false;
      }
   }

   protected int a(ItemStack itemstack) {
      if (itemstack.b()) {
         return 0;
      } else {
         Item item = itemstack.c();
         return f().getOrDefault(item, 0);
      }
   }

   private static int a(World world, TileEntityFurnace tileentityfurnace) {
      return world != null ? tileentityfurnace.z.a(tileentityfurnace, world).map(RecipeCooking::d).orElse(200) : 200;
   }

   public static boolean b(ItemStack itemstack) {
      return f().containsKey(itemstack.c());
   }

   @Override
   public int[] a(EnumDirection enumdirection) {
      return enumdirection == EnumDirection.a ? s : (enumdirection == EnumDirection.b ? r : t);
   }

   @Override
   public boolean a(int i, ItemStack itemstack, @Nullable EnumDirection enumdirection) {
      return this.b(i, itemstack);
   }

   @Override
   public boolean b(int i, ItemStack itemstack, EnumDirection enumdirection) {
      return enumdirection == EnumDirection.a && i == 1 ? itemstack.a(Items.pH) || itemstack.a(Items.pG) : true;
   }

   @Override
   public int b() {
      return this.m.size();
   }

   @Override
   public boolean aa_() {
      for(ItemStack itemstack : this.m) {
         if (!itemstack.b()) {
            return false;
         }
      }

      return true;
   }

   @Override
   public ItemStack a(int i) {
      return this.m.get(i);
   }

   @Override
   public ItemStack a(int i, int j) {
      return ContainerUtil.a(this.m, i, j);
   }

   @Override
   public ItemStack b(int i) {
      return ContainerUtil.a(this.m, i);
   }

   @Override
   public void a(int i, ItemStack itemstack) {
      ItemStack itemstack1 = this.m.get(i);
      boolean flag = !itemstack.b() && itemstack.a(itemstack1) && ItemStack.a(itemstack, itemstack1);
      this.m.set(i, itemstack);
      if (itemstack.K() > this.ab_()) {
         itemstack.f(this.ab_());
      }

      if (i == 0 && !flag) {
         this.x = a(this.o, this);
         this.w = 0;
         this.e();
      }
   }

   @Override
   public boolean a(EntityHuman entityhuman) {
      return IInventory.a(this, entityhuman);
   }

   @Override
   public boolean b(int i, ItemStack itemstack) {
      if (i == 2) {
         return false;
      } else if (i != 1) {
         return true;
      } else {
         ItemStack itemstack1 = this.m.get(1);
         return b(itemstack) || itemstack.a(Items.pG) && !itemstack1.a(Items.pG);
      }
   }

   @Override
   public void a() {
      this.m.clear();
   }

   @Override
   public void a(@Nullable IRecipe<?> irecipe) {
      if (irecipe != null) {
         MinecraftKey minecraftkey = irecipe.e();
         this.y.addTo(minecraftkey, 1);
      }
   }

   @Nullable
   @Override
   public IRecipe<?> d() {
      return null;
   }

   @Override
   public void b(EntityHuman entityhuman) {
   }

   public void awardUsedRecipesAndPopExperience(EntityPlayer entityplayer, ItemStack itemstack, int amount) {
      List<IRecipe<?>> list = this.getRecipesToAwardAndPopExperience(entityplayer.x(), entityplayer.de(), this.p, entityplayer, itemstack, amount);
      entityplayer.a(list);
      this.y.clear();
   }

   public List<IRecipe<?>> a(WorldServer worldserver, Vec3D vec3d) {
      return this.getRecipesToAwardAndPopExperience(worldserver, vec3d, this.p, null, null, 0);
   }

   public List<IRecipe<?>> getRecipesToAwardAndPopExperience(
      WorldServer worldserver, Vec3D vec3d, BlockPosition blockposition, EntityPlayer entityplayer, ItemStack itemstack, int amount
   ) {
      List<IRecipe<?>> list = Lists.newArrayList();
      ObjectIterator objectiterator = this.y.object2IntEntrySet().iterator();

      while(objectiterator.hasNext()) {
         Entry<MinecraftKey> entry = (Entry)objectiterator.next();
         worldserver.q().a((MinecraftKey)entry.getKey()).ifPresent(irecipe -> {
            list.add(irecipe);
            createExperience(worldserver, vec3d, entry.getIntValue(), ((RecipeCooking)irecipe).b(), blockposition, entityplayer, itemstack, amount);
         });
      }

      return list;
   }

   private static void createExperience(
      WorldServer worldserver, Vec3D vec3d, int i, float f, BlockPosition blockposition, EntityHuman entityhuman, ItemStack itemstack, int amount
   ) {
      int j = MathHelper.d((float)i * f);
      float f1 = MathHelper.h((float)i * f);
      if (f1 != 0.0F && Math.random() < (double)f1) {
         ++j;
      }

      BlockExpEvent event;
      if (amount != 0) {
         event = new FurnaceExtractEvent(
            (Player)entityhuman.getBukkitEntity(), CraftBlock.at(worldserver, blockposition), CraftMagicNumbers.getMaterial(itemstack.c()), amount, j
         );
      } else {
         event = new BlockExpEvent(CraftBlock.at(worldserver, blockposition), j);
      }

      worldserver.getCraftServer().getPluginManager().callEvent(event);
      j = event.getExpToDrop();
      EntityExperienceOrb.a(worldserver, vec3d, j);
   }

   @Override
   public void a(AutoRecipeStackManager autorecipestackmanager) {
      for(ItemStack itemstack : this.m) {
         autorecipestackmanager.b(itemstack);
      }
   }
}
