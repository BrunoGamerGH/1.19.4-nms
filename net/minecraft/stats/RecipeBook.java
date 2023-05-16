package net.minecraft.stats;

import com.google.common.collect.Sets;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.inventory.ContainerRecipeBook;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.crafting.IRecipe;

public class RecipeBook {
   public final Set<MinecraftKey> a = Sets.newHashSet();
   protected final Set<MinecraftKey> b = Sets.newHashSet();
   private final RecipeBookSettings c = new RecipeBookSettings();

   public void a(RecipeBook var0) {
      this.a.clear();
      this.b.clear();
      this.c.a(var0.c);
      this.a.addAll(var0.a);
      this.b.addAll(var0.b);
   }

   public void a(IRecipe<?> var0) {
      if (!var0.ah_()) {
         this.a(var0.e());
      }
   }

   protected void a(MinecraftKey var0) {
      this.a.add(var0);
   }

   public boolean b(@Nullable IRecipe<?> var0) {
      return var0 == null ? false : this.a.contains(var0.e());
   }

   public boolean b(MinecraftKey var0) {
      return this.a.contains(var0);
   }

   public void c(IRecipe<?> var0) {
      this.c(var0.e());
   }

   protected void c(MinecraftKey var0) {
      this.a.remove(var0);
      this.b.remove(var0);
   }

   public boolean d(IRecipe<?> var0) {
      return this.b.contains(var0.e());
   }

   public void e(IRecipe<?> var0) {
      this.b.remove(var0.e());
   }

   public void f(IRecipe<?> var0) {
      this.d(var0.e());
   }

   protected void d(MinecraftKey var0) {
      this.b.add(var0);
   }

   public boolean a(RecipeBookType var0) {
      return this.c.a(var0);
   }

   public void a(RecipeBookType var0, boolean var1) {
      this.c.a(var0, var1);
   }

   public boolean a(ContainerRecipeBook<?> var0) {
      return this.b(var0.t());
   }

   public boolean b(RecipeBookType var0) {
      return this.c.b(var0);
   }

   public void b(RecipeBookType var0, boolean var1) {
      this.c.b(var0, var1);
   }

   public void a(RecipeBookSettings var0) {
      this.c.a(var0);
   }

   public RecipeBookSettings a() {
      return this.c.a();
   }

   public void a(RecipeBookType var0, boolean var1, boolean var2) {
      this.c.a(var0, var1);
      this.c.b(var0, var2);
   }
}
