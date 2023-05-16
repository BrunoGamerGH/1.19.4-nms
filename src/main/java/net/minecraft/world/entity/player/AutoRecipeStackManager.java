package net.minecraft.world.entity.player;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntAVLTreeSet;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import java.util.BitSet;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.IRecipe;
import net.minecraft.world.item.crafting.RecipeItemStack;

public class AutoRecipeStackManager {
   private static final int b = 0;
   public final Int2IntMap a = new Int2IntOpenHashMap();

   public void a(ItemStack var0) {
      if (!var0.i() && !var0.D() && !var0.z()) {
         this.b(var0);
      }
   }

   public void b(ItemStack var0) {
      this.a(var0, 64);
   }

   public void a(ItemStack var0, int var1) {
      if (!var0.b()) {
         int var2 = c(var0);
         int var3 = Math.min(var1, var0.K());
         this.b(var2, var3);
      }
   }

   public static int c(ItemStack var0) {
      return BuiltInRegistries.i.a(var0.c());
   }

   boolean b(int var0) {
      return this.a.get(var0) > 0;
   }

   int a(int var0, int var1) {
      int var2 = this.a.get(var0);
      if (var2 >= var1) {
         this.a.put(var0, var2 - var1);
         return var0;
      } else {
         return 0;
      }
   }

   void b(int var0, int var1) {
      this.a.put(var0, this.a.get(var0) + var1);
   }

   public boolean a(IRecipe<?> var0, @Nullable IntList var1) {
      return this.a(var0, var1, 1);
   }

   public boolean a(IRecipe<?> var0, @Nullable IntList var1, int var2) {
      return new AutoRecipeStackManager.a(var0).a(var2, var1);
   }

   public int b(IRecipe<?> var0, @Nullable IntList var1) {
      return this.a(var0, Integer.MAX_VALUE, var1);
   }

   public int a(IRecipe<?> var0, int var1, @Nullable IntList var2) {
      return new AutoRecipeStackManager.a(var0).b(var1, var2);
   }

   public static ItemStack a(int var0) {
      return var0 == 0 ? ItemStack.b : new ItemStack(Item.b(var0));
   }

   public void a() {
      this.a.clear();
   }

   class a {
      private final IRecipe<?> b;
      private final List<RecipeItemStack> c = Lists.newArrayList();
      private final int d;
      private final int[] e;
      private final int f;
      private final BitSet g;
      private final IntList h = new IntArrayList();

      public a(IRecipe var1) {
         this.b = var1;
         this.c.addAll(var1.a());
         this.c.removeIf(RecipeItemStack::d);
         this.d = this.c.size();
         this.e = this.a();
         this.f = this.e.length;
         this.g = new BitSet(this.d + this.f + this.d + this.d * this.f);

         for(int var2 = 0; var2 < this.c.size(); ++var2) {
            IntList var3 = this.c.get(var2).b();

            for(int var4 = 0; var4 < this.f; ++var4) {
               if (var3.contains(this.e[var4])) {
                  this.g.set(this.d(true, var4, var2));
               }
            }
         }
      }

      public boolean a(int var0, @Nullable IntList var1) {
         if (var0 <= 0) {
            return true;
         } else {
            int var2;
            for(var2 = 0; this.a(var0); ++var2) {
               AutoRecipeStackManager.this.a(this.e[this.h.getInt(0)], var0);
               int var3 = this.h.size() - 1;
               this.c(this.h.getInt(var3));

               for(int var4 = 0; var4 < var3; ++var4) {
                  this.c((var4 & 1) == 0, this.h.get(var4), this.h.get(var4 + 1));
               }

               this.h.clear();
               this.g.clear(0, this.d + this.f);
            }

            boolean var3 = var2 == this.d;
            boolean var4 = var3 && var1 != null;
            if (var4) {
               var1.clear();
            }

            this.g.clear(0, this.d + this.f + this.d);
            int var5 = 0;
            List<RecipeItemStack> var6 = this.b.a();

            for(int var7 = 0; var7 < var6.size(); ++var7) {
               if (var4 && var6.get(var7).d()) {
                  var1.add(0);
               } else {
                  for(int var8 = 0; var8 < this.f; ++var8) {
                     if (this.b(false, var5, var8)) {
                        this.c(true, var8, var5);
                        AutoRecipeStackManager.this.b(this.e[var8], var0);
                        if (var4) {
                           var1.add(this.e[var8]);
                        }
                     }
                  }

                  ++var5;
               }
            }

            return var3;
         }
      }

      private int[] a() {
         IntCollection var0 = new IntAVLTreeSet();

         for(RecipeItemStack var2 : this.c) {
            var0.addAll(var2.b());
         }

         IntIterator var1 = var0.iterator();

         while(var1.hasNext()) {
            if (!AutoRecipeStackManager.this.b(var1.nextInt())) {
               var1.remove();
            }
         }

         return var0.toIntArray();
      }

      private boolean a(int var0) {
         int var1 = this.f;

         for(int var2 = 0; var2 < var1; ++var2) {
            if (AutoRecipeStackManager.this.a.get(this.e[var2]) >= var0) {
               this.a(false, var2);

               while(!this.h.isEmpty()) {
                  int var3 = this.h.size();
                  boolean var4 = (var3 & 1) == 1;
                  int var5 = this.h.getInt(var3 - 1);
                  if (!var4 && !this.b(var5)) {
                     break;
                  }

                  int var6 = var4 ? this.d : var1;
                  int var7 = 0;

                  while(true) {
                     if (var7 < var6) {
                        if (this.b(var4, var7) || !this.a(var4, var5, var7) || !this.b(var4, var5, var7)) {
                           ++var7;
                           continue;
                        }

                        this.a(var4, var7);
                     }

                     var7 = this.h.size();
                     if (var7 == var3) {
                        this.h.removeInt(var7 - 1);
                     }
                     break;
                  }
               }

               if (!this.h.isEmpty()) {
                  return true;
               }
            }
         }

         return false;
      }

      private boolean b(int var0) {
         return this.g.get(this.d(var0));
      }

      private void c(int var0) {
         this.g.set(this.d(var0));
      }

      private int d(int var0) {
         return this.d + this.f + var0;
      }

      private boolean a(boolean var0, int var1, int var2) {
         return this.g.get(this.d(var0, var1, var2));
      }

      private boolean b(boolean var0, int var1, int var2) {
         return var0 != this.g.get(1 + this.d(var0, var1, var2));
      }

      private void c(boolean var0, int var1, int var2) {
         this.g.flip(1 + this.d(var0, var1, var2));
      }

      private int d(boolean var0, int var1, int var2) {
         int var3 = var0 ? var1 * this.d + var2 : var2 * this.d + var1;
         return this.d + this.f + this.d + 2 * var3;
      }

      private void a(boolean var0, int var1) {
         this.g.set(this.c(var0, var1));
         this.h.add(var1);
      }

      private boolean b(boolean var0, int var1) {
         return this.g.get(this.c(var0, var1));
      }

      private int c(boolean var0, int var1) {
         return (var0 ? 0 : this.d) + var1;
      }

      public int b(int var0, @Nullable IntList var1) {
         int var2 = 0;
         int var3 = Math.min(var0, this.b()) + 1;

         while(true) {
            int var4 = (var2 + var3) / 2;
            if (this.a(var4, null)) {
               if (var3 - var2 <= 1) {
                  if (var4 > 0) {
                     this.a(var4, var1);
                  }

                  return var4;
               }

               var2 = var4;
            } else {
               var3 = var4;
            }
         }
      }

      private int b() {
         int var0 = Integer.MAX_VALUE;

         for(RecipeItemStack var2 : this.c) {
            int var3 = 0;

            int var5;
            for(IntListIterator var5x = var2.b().iterator(); var5x.hasNext(); var3 = Math.max(var3, AutoRecipeStackManager.this.a.get(var5))) {
               var5 = var5x.next();
            }

            if (var0 > 0) {
               var0 = Math.min(var0, var3);
            }
         }

         return var0;
      }
   }
}
