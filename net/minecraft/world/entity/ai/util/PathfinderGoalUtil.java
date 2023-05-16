package net.minecraft.world.entity.ai.util;

import net.minecraft.core.BlockPosition;
import net.minecraft.tags.TagsFluid;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.ai.navigation.Navigation;
import net.minecraft.world.entity.ai.navigation.NavigationAbstract;
import net.minecraft.world.level.pathfinder.PathfinderNormal;

public class PathfinderGoalUtil {
   public static boolean a(EntityInsentient var0) {
      return var0.G() instanceof Navigation;
   }

   public static boolean a(EntityCreature var0, int var1) {
      return var0.fG() && var0.fD().a(var0.de(), (double)(var0.fE() + (float)var1) + 1.0);
   }

   public static boolean a(BlockPosition var0, EntityCreature var1) {
      return var0.v() < var1.H.v_() || var0.v() > var1.H.ai();
   }

   public static boolean a(boolean var0, EntityCreature var1, BlockPosition var2) {
      return var0 && !var1.a(var2);
   }

   public static boolean a(NavigationAbstract var0, BlockPosition var1) {
      return !var0.a(var1);
   }

   public static boolean a(EntityCreature var0, BlockPosition var1) {
      return var0.H.b_(var1).a(TagsFluid.a);
   }

   public static boolean b(EntityCreature var0, BlockPosition var1) {
      return var0.a(PathfinderNormal.a(var0.H, var1.j())) != 0.0F;
   }

   public static boolean c(EntityCreature var0, BlockPosition var1) {
      return var0.H.a_(var1).d().b();
   }
}
