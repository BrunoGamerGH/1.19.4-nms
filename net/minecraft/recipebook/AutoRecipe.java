package net.minecraft.recipebook;

import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.protocol.game.PacketPlayOutAutoRecipe;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.IInventory;
import net.minecraft.world.entity.player.AutoRecipeStackManager;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.world.inventory.ContainerRecipeBook;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.IRecipe;
import org.slf4j.Logger;

public class AutoRecipe<C extends IInventory> implements AutoRecipeAbstract<Integer> {
   private static final Logger d = LogUtils.getLogger();
   protected final AutoRecipeStackManager a = new AutoRecipeStackManager();
   protected PlayerInventory b;
   protected ContainerRecipeBook<C> c;

   public AutoRecipe(ContainerRecipeBook<C> var0) {
      this.c = var0;
   }

   public void a(EntityPlayer var0, @Nullable IRecipe<C> var1, boolean var2) {
      if (var1 != null && var0.E().b(var1)) {
         this.b = var0.fJ();
         if (this.b() || var0.f()) {
            this.a.a();
            var0.fJ().a(this.a);
            this.c.a(this.a);
            if (this.a.a(var1, null)) {
               this.a(var1, var2);
            } else {
               this.a();
               var0.b.a(new PacketPlayOutAutoRecipe(var0.bP.j, var1));
            }

            var0.fJ().e();
         }
      }
   }

   protected void a() {
      for(int var0 = 0; var0 < this.c.p(); ++var0) {
         if (this.c.e(var0)) {
            ItemStack var1 = this.c.b(var0).e().o();
            this.b.a(var1, false);
            this.c.b(var0).e(var1);
         }
      }

      this.c.l();
   }

   protected void a(IRecipe<C> var0, boolean var1) {
      boolean var2 = this.c.a(var0);
      int var3 = this.a.b(var0, null);
      if (var2) {
         for(int var4 = 0; var4 < this.c.o() * this.c.n() + 1; ++var4) {
            if (var4 != this.c.m()) {
               ItemStack var5 = this.c.b(var4).e();
               if (!var5.b() && Math.min(var3, var5.f()) < var5.K() + 1) {
                  return;
               }
            }
         }
      }

      int var4 = this.a(var1, var3, var2);
      IntList var5 = new IntArrayList();
      if (this.a.a(var0, var5, var4)) {
         int var6 = var4;
         IntListIterator var8 = var5.iterator();

         while(var8.hasNext()) {
            int var8x = var8.next();
            int var9 = AutoRecipeStackManager.a(var8x).f();
            if (var9 < var6) {
               var6 = var9;
            }
         }

         if (this.a.a(var0, var5, var6)) {
            this.a();
            this.a(this.c.n(), this.c.o(), this.c.m(), var0, var5.iterator(), var6);
         }
      }
   }

   @Override
   public void a(Iterator<Integer> var0, int var1, int var2, int var3, int var4) {
      Slot var5 = this.c.b(var1);
      ItemStack var6 = AutoRecipeStackManager.a(var0.next());
      if (!var6.b()) {
         for(int var7 = 0; var7 < var2; ++var7) {
            this.a(var5, var6);
         }
      }
   }

   protected int a(boolean var0, int var1, boolean var2) {
      int var3 = 1;
      if (var0) {
         var3 = var1;
      } else if (var2) {
         var3 = 64;

         for(int var4 = 0; var4 < this.c.n() * this.c.o() + 1; ++var4) {
            if (var4 != this.c.m()) {
               ItemStack var5 = this.c.b(var4).e();
               if (!var5.b() && var3 > var5.K()) {
                  var3 = var5.K();
               }
            }
         }

         if (var3 < 64) {
            ++var3;
         }
      }

      return var3;
   }

   protected void a(Slot var0, ItemStack var1) {
      int var2 = this.b.c(var1);
      if (var2 != -1) {
         ItemStack var3 = this.b.a(var2).o();
         if (!var3.b()) {
            if (var3.K() > 1) {
               this.b.a(var2, 1);
            } else {
               this.b.b(var2);
            }

            var3.f(1);
            if (var0.e().b()) {
               var0.e(var3);
            } else {
               var0.e().g(1);
            }
         }
      }
   }

   private boolean b() {
      List<ItemStack> var0 = Lists.newArrayList();
      int var1 = this.c();

      for(int var2 = 0; var2 < this.c.n() * this.c.o() + 1; ++var2) {
         if (var2 != this.c.m()) {
            ItemStack var3 = this.c.b(var2).e().o();
            if (!var3.b()) {
               int var4 = this.b.d(var3);
               if (var4 == -1 && var0.size() <= var1) {
                  for(ItemStack var6 : var0) {
                     if (var6.a(var3) && var6.K() != var6.f() && var6.K() + var3.K() <= var6.f()) {
                        var6.g(var3.K());
                        var3.f(0);
                        break;
                     }
                  }

                  if (!var3.b()) {
                     if (var0.size() >= var1) {
                        return false;
                     }

                     var0.add(var3);
                  }
               } else if (var4 == -1) {
                  return false;
               }
            }
         }
      }

      return true;
   }

   private int c() {
      int var0 = 0;

      for(ItemStack var2 : this.b.i) {
         if (var2.b()) {
            ++var0;
         }
      }

      return var0;
   }
}
