package net.minecraft.world.item;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.commands.arguments.blocks.ArgumentBlockPredicate;
import net.minecraft.core.IRegistry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.pattern.ShapeDetectorBlock;

public class AdventureModeCheck {
   private final String a;
   @Nullable
   private ShapeDetectorBlock b;
   private boolean c;
   private boolean d;

   public AdventureModeCheck(String var0) {
      this.a = var0;
   }

   private static boolean a(ShapeDetectorBlock var0, @Nullable ShapeDetectorBlock var1, boolean var2) {
      if (var1 == null || var0.a() != var1.a()) {
         return false;
      } else if (!var2) {
         return true;
      } else if (var0.b() == null && var1.b() == null) {
         return true;
      } else {
         return var0.b() != null && var1.b() != null ? Objects.equals(var0.b().n(), var1.b().n()) : false;
      }
   }

   public boolean a(ItemStack var0, IRegistry<Block> var1, ShapeDetectorBlock var2) {
      if (a(var2, this.b, this.d)) {
         return this.c;
      } else {
         this.b = var2;
         this.d = false;
         NBTTagCompound var3 = var0.u();
         if (var3 != null && var3.b(this.a, 9)) {
            NBTTagList var4 = var3.c(this.a, 8);

            for(int var5 = 0; var5 < var4.size(); ++var5) {
               String var6 = var4.j(var5);

               try {
                  ArgumentBlockPredicate.b var7 = ArgumentBlockPredicate.a(var1.p(), new StringReader(var6));
                  this.d |= var7.a();
                  if (var7.test(var2)) {
                     this.c = true;
                     return true;
                  }
               } catch (CommandSyntaxException var9) {
               }
            }
         }

         this.c = false;
         return false;
      }
   }
}
