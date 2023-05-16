package net.minecraft.world.item.trading;

import net.minecraft.nbt.GameProfileSerializer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftMerchantRecipe;

public class MerchantRecipe {
   public ItemStack a;
   public ItemStack b;
   public final ItemStack c;
   public int d;
   public int e;
   public boolean f = true;
   public int g;
   public int h;
   public float i;
   public int j = 1;
   private CraftMerchantRecipe bukkitHandle;

   public CraftMerchantRecipe asBukkit() {
      return this.bukkitHandle == null ? (this.bukkitHandle = new CraftMerchantRecipe(this)) : this.bukkitHandle;
   }

   public MerchantRecipe(
      ItemStack itemstack,
      ItemStack itemstack1,
      ItemStack itemstack2,
      int uses,
      int maxUses,
      int experience,
      float priceMultiplier,
      CraftMerchantRecipe bukkit
   ) {
      this(itemstack, itemstack1, itemstack2, uses, maxUses, experience, priceMultiplier, 0, bukkit);
   }

   public MerchantRecipe(
      ItemStack itemstack,
      ItemStack itemstack1,
      ItemStack itemstack2,
      int uses,
      int maxUses,
      int experience,
      float priceMultiplier,
      int demand,
      CraftMerchantRecipe bukkit
   ) {
      this(itemstack, itemstack1, itemstack2, uses, maxUses, experience, priceMultiplier, demand);
      this.bukkitHandle = bukkit;
   }

   public MerchantRecipe(NBTTagCompound nbttagcompound) {
      this.a = ItemStack.a(nbttagcompound.p("buy"));
      this.b = ItemStack.a(nbttagcompound.p("buyB"));
      this.c = ItemStack.a(nbttagcompound.p("sell"));
      this.d = nbttagcompound.h("uses");
      if (nbttagcompound.b("maxUses", 99)) {
         this.e = nbttagcompound.h("maxUses");
      } else {
         this.e = 4;
      }

      if (nbttagcompound.b("rewardExp", 1)) {
         this.f = nbttagcompound.q("rewardExp");
      }

      if (nbttagcompound.b("xp", 3)) {
         this.j = nbttagcompound.h("xp");
      }

      if (nbttagcompound.b("priceMultiplier", 5)) {
         this.i = nbttagcompound.j("priceMultiplier");
      }

      this.g = nbttagcompound.h("specialPrice");
      this.h = nbttagcompound.h("demand");
   }

   public MerchantRecipe(ItemStack itemstack, ItemStack itemstack1, int i, int j, float f) {
      this(itemstack, ItemStack.b, itemstack1, i, j, f);
   }

   public MerchantRecipe(ItemStack itemstack, ItemStack itemstack1, ItemStack itemstack2, int i, int j, float f) {
      this(itemstack, itemstack1, itemstack2, 0, i, j, f);
   }

   public MerchantRecipe(ItemStack itemstack, ItemStack itemstack1, ItemStack itemstack2, int i, int j, int k, float f) {
      this(itemstack, itemstack1, itemstack2, i, j, k, f, 0);
   }

   public MerchantRecipe(ItemStack itemstack, ItemStack itemstack1, ItemStack itemstack2, int i, int j, int k, float f, int l) {
      this.a = itemstack;
      this.b = itemstack1;
      this.c = itemstack2;
      this.d = i;
      this.e = j;
      this.j = k;
      this.i = f;
      this.h = l;
   }

   public ItemStack a() {
      return this.a;
   }

   public ItemStack b() {
      int i = this.a.K();
      if (i <= 0) {
         return ItemStack.b;
      } else {
         ItemStack itemstack = this.a.o();
         int j = Math.max(0, MathHelper.d((float)(i * this.h) * this.i));
         itemstack.f(MathHelper.a(i + j + this.g, 1, this.a.c().l()));
         return itemstack;
      }
   }

   public ItemStack c() {
      return this.b;
   }

   public ItemStack d() {
      return this.c;
   }

   public void e() {
      this.h = this.h + this.d - (this.e - this.d);
   }

   public ItemStack f() {
      return this.c.o();
   }

   public int g() {
      return this.d;
   }

   public void h() {
      this.d = 0;
   }

   public int i() {
      return this.e;
   }

   public void j() {
      ++this.d;
   }

   public int k() {
      return this.h;
   }

   public void a(int i) {
      this.g += i;
   }

   public void l() {
      this.g = 0;
   }

   public int m() {
      return this.g;
   }

   public void b(int i) {
      this.g = i;
   }

   public float n() {
      return this.i;
   }

   public int o() {
      return this.j;
   }

   public boolean p() {
      return this.d >= this.e;
   }

   public void q() {
      this.d = this.e;
   }

   public boolean r() {
      return this.d > 0;
   }

   public boolean s() {
      return this.f;
   }

   public NBTTagCompound t() {
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      nbttagcompound.a("buy", this.a.b(new NBTTagCompound()));
      nbttagcompound.a("sell", this.c.b(new NBTTagCompound()));
      nbttagcompound.a("buyB", this.b.b(new NBTTagCompound()));
      nbttagcompound.a("uses", this.d);
      nbttagcompound.a("maxUses", this.e);
      nbttagcompound.a("rewardExp", this.f);
      nbttagcompound.a("xp", this.j);
      nbttagcompound.a("priceMultiplier", this.i);
      nbttagcompound.a("specialPrice", this.g);
      nbttagcompound.a("demand", this.h);
      return nbttagcompound;
   }

   public boolean a(ItemStack itemstack, ItemStack itemstack1) {
      return this.c(itemstack, this.b()) && itemstack.K() >= this.b().K() && this.c(itemstack1, this.b) && itemstack1.K() >= this.b.K();
   }

   private boolean c(ItemStack itemstack, ItemStack itemstack1) {
      if (itemstack1.b() && itemstack.b()) {
         return true;
      } else {
         ItemStack itemstack2 = itemstack.o();
         if (itemstack2.c().o()) {
            itemstack2.b(itemstack2.j());
         }

         return ItemStack.c(itemstack2, itemstack1) && (!itemstack1.t() || itemstack2.t() && GameProfileSerializer.a(itemstack1.u(), itemstack2.u(), false));
      }
   }

   public boolean b(ItemStack itemstack, ItemStack itemstack1) {
      if (!this.a(itemstack, itemstack1)) {
         return false;
      } else {
         if (!this.b().b()) {
            itemstack.h(this.b().K());
         }

         if (!this.c().b()) {
            itemstack1.h(this.c().K());
         }

         return true;
      }
   }
}
