package net.minecraft.world.level.block;

import com.google.common.annotations.VisibleForTesting;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.block.state.IBlockData;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class MultifaceSpreader {
   public static final MultifaceSpreader.e[] a = new MultifaceSpreader.e[]{MultifaceSpreader.e.a, MultifaceSpreader.e.b, MultifaceSpreader.e.c};
   private final MultifaceSpreader.b b;

   public MultifaceSpreader(MultifaceBlock multifaceblock) {
      this(new MultifaceSpreader.a(multifaceblock));
   }

   public MultifaceSpreader(MultifaceSpreader.b multifacespreader_b) {
      this.b = multifacespreader_b;
   }

   public boolean a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
      return EnumDirection.a().anyMatch(enumdirection1 -> {
         MultifaceSpreader.b multifacespreader_b = this.b;
         return this.a(iblockdata, iblockaccess, blockposition, enumdirection, enumdirection1, multifacespreader_b::a).isPresent();
      });
   }

   public Optional<MultifaceSpreader.c> a(IBlockData iblockdata, GeneratorAccess generatoraccess, BlockPosition blockposition, RandomSource randomsource) {
      return EnumDirection.a(randomsource)
         .stream()
         .filter(enumdirection -> this.b.b(iblockdata, enumdirection))
         .map(enumdirection -> this.a(iblockdata, generatoraccess, blockposition, enumdirection, randomsource, false))
         .filter(Optional::isPresent)
         .findFirst()
         .orElse(Optional.empty());
   }

   public long a(IBlockData iblockdata, GeneratorAccess generatoraccess, BlockPosition blockposition, boolean flag) {
      return EnumDirection.a()
         .filter(enumdirection -> this.b.b(iblockdata, enumdirection))
         .map(enumdirection -> this.a(iblockdata, generatoraccess, blockposition, enumdirection, flag))
         .reduce(0L, Long::sum);
   }

   public Optional<MultifaceSpreader.c> a(
      IBlockData iblockdata,
      GeneratorAccess generatoraccess,
      BlockPosition blockposition,
      EnumDirection enumdirection,
      RandomSource randomsource,
      boolean flag
   ) {
      return EnumDirection.a(randomsource)
         .stream()
         .map(enumdirection1 -> this.a(iblockdata, generatoraccess, blockposition, enumdirection, enumdirection1, flag))
         .filter(Optional::isPresent)
         .findFirst()
         .orElse(Optional.empty());
   }

   private long a(IBlockData iblockdata, GeneratorAccess generatoraccess, BlockPosition blockposition, EnumDirection enumdirection, boolean flag) {
      return EnumDirection.a()
         .map(enumdirection1 -> this.a(iblockdata, generatoraccess, blockposition, enumdirection, enumdirection1, flag))
         .filter(Optional::isPresent)
         .count();
   }

   @VisibleForTesting
   public Optional<MultifaceSpreader.c> a(
      IBlockData iblockdata,
      GeneratorAccess generatoraccess,
      BlockPosition blockposition,
      EnumDirection enumdirection,
      EnumDirection enumdirection1,
      boolean flag
   ) {
      MultifaceSpreader.b multifacespreader_b = this.b;
      return this.a(iblockdata, generatoraccess, blockposition, enumdirection, enumdirection1, multifacespreader_b::a)
         .flatMap(multifacespreader_c -> this.a(generatoraccess, multifacespreader_c, flag));
   }

   public Optional<MultifaceSpreader.c> a(
      IBlockData iblockdata,
      IBlockAccess iblockaccess,
      BlockPosition blockposition,
      EnumDirection enumdirection,
      EnumDirection enumdirection1,
      MultifaceSpreader.d multifacespreader_d
   ) {
      if (enumdirection1.o() == enumdirection.o()) {
         return Optional.empty();
      } else if (this.b.a(iblockdata) || this.b.a(iblockdata, enumdirection) && !this.b.a(iblockdata, enumdirection1)) {
         for(MultifaceSpreader.e multifacespreader_e : this.b.a()) {
            MultifaceSpreader.c multifacespreader_c = multifacespreader_e.a(blockposition, enumdirection1, enumdirection);
            if (multifacespreader_d.test(iblockaccess, blockposition, multifacespreader_c)) {
               return Optional.of(multifacespreader_c);
            }
         }

         return Optional.empty();
      } else {
         return Optional.empty();
      }
   }

   public Optional<MultifaceSpreader.c> a(GeneratorAccess generatoraccess, MultifaceSpreader.c multifacespreader_c, boolean flag) {
      IBlockData iblockdata = generatoraccess.a_(multifacespreader_c.a());
      return this.b.a(generatoraccess, multifacespreader_c, iblockdata, flag) ? Optional.of(multifacespreader_c) : Optional.empty();
   }

   public static class a implements MultifaceSpreader.b {
      protected MultifaceBlock a;

      public a(MultifaceBlock multifaceblock) {
         this.a = multifaceblock;
      }

      @Nullable
      @Override
      public IBlockData a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
         return this.a.c(iblockdata, iblockaccess, blockposition, enumdirection);
      }

      protected boolean a(
         IBlockAccess iblockaccess, BlockPosition blockposition, BlockPosition blockposition1, EnumDirection enumdirection, IBlockData iblockdata
      ) {
         return iblockdata.h() || iblockdata.a(this.a) || iblockdata.a(Blocks.G) && iblockdata.r().b();
      }

      @Override
      public boolean a(IBlockAccess iblockaccess, BlockPosition blockposition, MultifaceSpreader.c multifacespreader_c) {
         IBlockData iblockdata = iblockaccess.a_(multifacespreader_c.a());
         return this.a(iblockaccess, blockposition, multifacespreader_c.a(), multifacespreader_c.b(), iblockdata)
            && this.a.a(iblockaccess, iblockdata, multifacespreader_c.a(), multifacespreader_c.b());
      }
   }

   public interface b {
      @Nullable
      IBlockData a(IBlockData var1, IBlockAccess var2, BlockPosition var3, EnumDirection var4);

      boolean a(IBlockAccess var1, BlockPosition var2, MultifaceSpreader.c var3);

      default MultifaceSpreader.e[] a() {
         return MultifaceSpreader.a;
      }

      default boolean a(IBlockData iblockdata, EnumDirection enumdirection) {
         return MultifaceBlock.a(iblockdata, enumdirection);
      }

      default boolean a(IBlockData iblockdata) {
         return false;
      }

      default boolean b(IBlockData iblockdata, EnumDirection enumdirection) {
         return this.a(iblockdata) || this.a(iblockdata, enumdirection);
      }

      default boolean a(GeneratorAccess generatoraccess, MultifaceSpreader.c multifacespreader_c, IBlockData iblockdata, boolean flag) {
         IBlockData iblockdata1 = this.a(iblockdata, generatoraccess, multifacespreader_c.a(), multifacespreader_c.b());
         if (iblockdata1 != null) {
            if (flag) {
               generatoraccess.A(multifacespreader_c.a()).e(multifacespreader_c.a());
            }

            return CraftEventFactory.handleBlockSpreadEvent(generatoraccess, multifacespreader_c.source(), multifacespreader_c.a(), iblockdata1, 2);
         } else {
            return false;
         }
      }
   }

   public static record c(BlockPosition pos, EnumDirection face, BlockPosition source) {
      private final BlockPosition a;
      private final EnumDirection b;

      public c(BlockPosition pos, EnumDirection face, BlockPosition source) {
         this.a = pos;
         this.b = face;
         this.source = source;
      }
   }

   @FunctionalInterface
   public interface d {
      boolean test(IBlockAccess var1, BlockPosition var2, MultifaceSpreader.c var3);
   }

   public static enum e {
      a {
         @Override
         public MultifaceSpreader.c a(BlockPosition blockposition, EnumDirection enumdirection, EnumDirection enumdirection1) {
            return new MultifaceSpreader.c(blockposition, enumdirection, blockposition);
         }
      },
      b {
         @Override
         public MultifaceSpreader.c a(BlockPosition blockposition, EnumDirection enumdirection, EnumDirection enumdirection1) {
            return new MultifaceSpreader.c(blockposition.a(enumdirection), enumdirection1, blockposition);
         }
      },
      c {
         @Override
         public MultifaceSpreader.c a(BlockPosition blockposition, EnumDirection enumdirection, EnumDirection enumdirection1) {
            return new MultifaceSpreader.c(blockposition.a(enumdirection).a(enumdirection1), enumdirection.g(), blockposition);
         }
      };

      public abstract MultifaceSpreader.c a(BlockPosition var1, EnumDirection var2, EnumDirection var3);
   }
}
