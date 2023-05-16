package net.minecraft.world.level.storage;

import com.mojang.serialization.Lifecycle;
import java.util.Locale;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.CrashReportSystemDetails;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.EnumGamemode;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.WorldDataConfiguration;
import net.minecraft.world.level.WorldSettings;
import net.minecraft.world.level.levelgen.WorldOptions;

public interface SaveData {
   int c = 19133;
   int d = 19132;

   WorldDataConfiguration F();

   void a(WorldDataConfiguration var1);

   boolean H();

   Set<String> I();

   void a(String var1, boolean var2);

   default void a(CrashReportSystemDetails var0) {
      var0.a("Known server brands", () -> String.join(", ", this.I()));
      var0.a("Level was modded", () -> Boolean.toString(this.H()));
      var0.a("Level storage version", () -> {
         int var0x = this.z();
         return String.format(Locale.ROOT, "0x%05X - %s", var0x, this.i(var0x));
      });
   }

   default String i(int var0) {
      switch(var0) {
         case 19132:
            return "McRegion";
         case 19133:
            return "Anvil";
         default:
            return "Unknown?";
      }
   }

   @Nullable
   NBTTagCompound G();

   void b(@Nullable NBTTagCompound var1);

   IWorldDataServer J();

   WorldSettings K();

   NBTTagCompound a(IRegistryCustom var1, @Nullable NBTTagCompound var2);

   boolean n();

   int z();

   String g();

   EnumGamemode m();

   void a(EnumGamemode var1);

   boolean o();

   EnumDifficulty s();

   void a(EnumDifficulty var1);

   boolean t();

   void d(boolean var1);

   GameRules q();

   @Nullable
   NBTTagCompound y();

   NBTTagCompound E();

   void a(NBTTagCompound var1);

   WorldOptions A();

   boolean B();

   boolean C();

   Lifecycle D();

   default FeatureFlagSet L() {
      return this.F().b();
   }
}
