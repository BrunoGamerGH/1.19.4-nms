package net.minecraft.data.structures;

import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.GameProfileSerializer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.DataConverterRegistry;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructure;
import org.slf4j.Logger;

public class StructureUpdater implements SnbtToNbt.a {
   private static final Logger a = LogUtils.getLogger();

   @Override
   public NBTTagCompound apply(String var0, NBTTagCompound var1) {
      return var0.startsWith("data/minecraft/structures/") ? a(var0, var1) : var1;
   }

   public static NBTTagCompound a(String var0, NBTTagCompound var1) {
      DefinedStructure var2 = new DefinedStructure();
      int var3 = GameProfileSerializer.b(var1, 500);
      int var4 = 3318;
      if (var3 < 3318) {
         a.warn("SNBT Too old, do not forget to update: {} < {}: {}", new Object[]{var3, 3318, var0});
      }

      NBTTagCompound var5 = DataFixTypes.f.a(DataConverterRegistry.a(), var1, var3);
      var2.a(BuiltInRegistries.f.p(), var5);
      return var2.a(new NBTTagCompound());
   }
}
