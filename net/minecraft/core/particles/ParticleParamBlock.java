package net.minecraft.core.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import net.minecraft.commands.arguments.blocks.ArgumentBlock;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.IBlockData;

public class ParticleParamBlock implements ParticleParam {
   public static final ParticleParam.a<ParticleParamBlock> a = new ParticleParam.a<ParticleParamBlock>() {
      public ParticleParamBlock a(Particle<ParticleParamBlock> var0, StringReader var1) throws CommandSyntaxException {
         var1.expect(' ');
         return new ParticleParamBlock(var0, ArgumentBlock.a(BuiltInRegistries.f.p(), var1, false).a());
      }

      public ParticleParamBlock a(Particle<ParticleParamBlock> var0, PacketDataSerializer var1) {
         return new ParticleParamBlock(var0, var1.a(Block.o));
      }
   };
   private final Particle<ParticleParamBlock> b;
   private final IBlockData c;

   public static Codec<ParticleParamBlock> a(Particle<ParticleParamBlock> var0) {
      return IBlockData.b.xmap(var1 -> new ParticleParamBlock(var0, var1), var0x -> var0x.c);
   }

   public ParticleParamBlock(Particle<ParticleParamBlock> var0, IBlockData var1) {
      this.b = var0;
      this.c = var1;
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(Block.o, this.c);
   }

   @Override
   public String a() {
      return BuiltInRegistries.k.b(this.b()) + " " + ArgumentBlock.a(this.c);
   }

   @Override
   public Particle<ParticleParamBlock> b() {
      return this.b;
   }

   public IBlockData c() {
      return this.c;
   }
}
