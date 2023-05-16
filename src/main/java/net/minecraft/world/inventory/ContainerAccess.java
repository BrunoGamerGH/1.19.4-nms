package net.minecraft.world.inventory;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.World;
import org.bukkit.Location;

public interface ContainerAccess {
   ContainerAccess a = new ContainerAccess() {
      @Override
      public <T> Optional<T> a(BiFunction<World, BlockPosition, T> bifunction) {
         return Optional.empty();
      }
   };

   default World getWorld() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   default BlockPosition getPosition() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   default Location getLocation() {
      return new Location(this.getWorld().getWorld(), (double)this.getPosition().u(), (double)this.getPosition().v(), (double)this.getPosition().w());
   }

   static ContainerAccess a(final World world, final BlockPosition blockposition) {
      return new ContainerAccess() {
         @Override
         public World getWorld() {
            return world;
         }

         @Override
         public BlockPosition getPosition() {
            return blockposition;
         }

         @Override
         public <T> Optional<T> a(BiFunction<World, BlockPosition, T> bifunction) {
            return Optional.of(bifunction.apply(world, blockposition));
         }
      };
   }

   <T> Optional<T> a(BiFunction<World, BlockPosition, T> var1);

   default <T> T a(BiFunction<World, BlockPosition, T> bifunction, T t0) {
      return this.a(bifunction).orElse(t0);
   }

   default void a(BiConsumer<World, BlockPosition> biconsumer) {
      this.a((world, blockposition) -> {
         biconsumer.accept(world, blockposition);
         return Optional.empty();
      });
   }
}
