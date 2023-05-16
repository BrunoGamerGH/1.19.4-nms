package net.minecraft.commands;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.IRegistry;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.flag.FeatureFlagSet;

public interface CommandBuildContext {
   <T> HolderLookup<T> a(ResourceKey<? extends IRegistry<T>> var1);

   static CommandBuildContext a(final HolderLookup.b var0, final FeatureFlagSet var1) {
      return new CommandBuildContext() {
         @Override
         public <T> HolderLookup<T> a(ResourceKey<? extends IRegistry<T>> var0x) {
            return var0.<T>b(var0).a(var1);
         }
      };
   }

   static CommandBuildContext.a a(final IRegistryCustom var0, final FeatureFlagSet var1) {
      return new CommandBuildContext.a() {
         CommandBuildContext.b c = CommandBuildContext.b.b;

         @Override
         public void a(CommandBuildContext.b var0x) {
            this.c = var0;
         }

         @Override
         public <T> HolderLookup<T> a(ResourceKey<? extends IRegistry<T>> var0x) {
            IRegistry<T> var1 = var0.d(var0);
            final HolderLookup.c<T> var2 = var1.p();
            final HolderLookup.c<T> var3 = var1.u();
            HolderLookup.c<T> var4 = new HolderLookup.c.a<T>() {
               @Override
               protected HolderLookup.c<T> a() {
                  return switch(c) {
                     case b -> var2;
                     case a -> var3;
                  };
               }
            };
            return var4.a(var1);
         }
      };
   }

   public interface a extends CommandBuildContext {
      void a(CommandBuildContext.b var1);
   }

   public static enum b {
      a,
      b;
   }
}
