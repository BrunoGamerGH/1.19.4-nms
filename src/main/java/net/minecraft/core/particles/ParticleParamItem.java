package net.minecraft.core.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import net.minecraft.commands.arguments.item.ArgumentParserItemStack;
import net.minecraft.commands.arguments.item.ArgumentPredicateItemStack;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.world.item.ItemStack;

public class ParticleParamItem implements ParticleParam {
   public static final ParticleParam.a<ParticleParamItem> a = new ParticleParam.a<ParticleParamItem>() {
      public ParticleParamItem a(Particle<ParticleParamItem> var0, StringReader var1) throws CommandSyntaxException {
         var1.expect(' ');
         ArgumentParserItemStack.a var2 = ArgumentParserItemStack.a(BuiltInRegistries.i.p(), var1);
         ItemStack var3 = new ArgumentPredicateItemStack(var2.a(), var2.b()).a(1, false);
         return new ParticleParamItem(var0, var3);
      }

      public ParticleParamItem a(Particle<ParticleParamItem> var0, PacketDataSerializer var1) {
         return new ParticleParamItem(var0, var1.r());
      }
   };
   private final Particle<ParticleParamItem> b;
   private final ItemStack c;

   public static Codec<ParticleParamItem> a(Particle<ParticleParamItem> var0) {
      return ItemStack.a.xmap(var1 -> new ParticleParamItem(var0, var1), var0x -> var0x.c);
   }

   public ParticleParamItem(Particle<ParticleParamItem> var0, ItemStack var1) {
      this.b = var0;
      this.c = var1;
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(this.c);
   }

   @Override
   public String a() {
      return BuiltInRegistries.k.b(this.b()) + " " + new ArgumentPredicateItemStack(this.c.d(), this.c.u()).b();
   }

   @Override
   public Particle<ParticleParamItem> b() {
      return this.b;
   }

   public ItemStack c() {
      return this.c;
   }
}
