package net.minecraft.gametest.framework;

import com.mojang.authlib.GameProfile;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.LongStream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.TagsBlock;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.ItemActionContext;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockButtonAbstract;
import net.minecraft.world.level.block.BlockLever;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EnumBlockMirror;
import net.minecraft.world.level.block.EnumBlockRotation;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityContainer;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.IBlockState;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.structure.StructureBoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructure;
import net.minecraft.world.level.pathfinder.PathEntity;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.Vec3D;

public class GameTestHarnessHelper {
   private final GameTestHarnessInfo a;
   private boolean b;

   public GameTestHarnessHelper(GameTestHarnessInfo var0) {
      this.a = var0;
   }

   public WorldServer a() {
      return this.a.g();
   }

   public IBlockData a(BlockPosition var0) {
      return this.a().a_(this.i(var0));
   }

   @Nullable
   public TileEntity b(BlockPosition var0) {
      return this.a().c_(this.i(var0));
   }

   public void b() {
      AxisAlignedBB var0 = this.j();
      List<Entity> var1 = this.a().a(Entity.class, var0.g(1.0), var0x -> !(var0x instanceof EntityHuman));
      var1.forEach(Entity::ah);
   }

   public EntityItem a(Item var0, float var1, float var2, float var3) {
      WorldServer var4 = this.a();
      Vec3D var5 = this.a(new Vec3D((double)var1, (double)var2, (double)var3));
      EntityItem var6 = new EntityItem(var4, var5.c, var5.d, var5.e, new ItemStack(var0, 1));
      var6.o(0.0, 0.0, 0.0);
      var4.b(var6);
      return var6;
   }

   public EntityItem a(Item var0, BlockPosition var1) {
      return this.a(var0, (float)var1.u(), (float)var1.v(), (float)var1.w());
   }

   public <E extends Entity> E a(EntityTypes<E> var0, BlockPosition var1) {
      return this.a(var0, Vec3D.c(var1));
   }

   public <E extends Entity> E a(EntityTypes<E> var0, Vec3D var1) {
      WorldServer var2 = this.a();
      E var3 = var0.a((World)var2);
      if (var3 == null) {
         throw new NullPointerException("Failed to create entity " + var0.r().g().a());
      } else {
         if (var3 instanceof EntityInsentient var4) {
            var4.fz();
         }

         Vec3D var4 = this.a(var1);
         var3.b(var4.c, var4.d, var4.e, var3.dw(), var3.dy());
         var2.b(var3);
         return var3;
      }
   }

   public <E extends Entity> E a(EntityTypes<E> var0, int var1, int var2, int var3) {
      return this.a(var0, new BlockPosition(var1, var2, var3));
   }

   public <E extends Entity> E a(EntityTypes<E> var0, float var1, float var2, float var3) {
      return this.a(var0, new Vec3D((double)var1, (double)var2, (double)var3));
   }

   public <E extends EntityInsentient> E b(EntityTypes<E> var0, BlockPosition var1) {
      E var2 = this.a(var0, var1);
      var2.fO();
      return var2;
   }

   public <E extends EntityInsentient> E b(EntityTypes<E> var0, int var1, int var2, int var3) {
      return this.b(var0, new BlockPosition(var1, var2, var3));
   }

   public <E extends EntityInsentient> E b(EntityTypes<E> var0, Vec3D var1) {
      E var2 = this.a(var0, var1);
      var2.fO();
      return var2;
   }

   public <E extends EntityInsentient> E b(EntityTypes<E> var0, float var1, float var2, float var3) {
      return this.b(var0, new Vec3D((double)var1, (double)var2, (double)var3));
   }

   public GameTestHarnessSequence a(EntityInsentient var0, BlockPosition var1, float var2) {
      return this.g().a(2, () -> {
         PathEntity var3x = var0.G().a(this.i(var1), 0);
         var0.G().a(var3x, (double)var2);
      });
   }

   public void a(int var0, int var1, int var2) {
      this.c(new BlockPosition(var0, var1, var2));
   }

   public void c(BlockPosition var0) {
      this.b(var0, var0x -> var0x.a(TagsBlock.e), () -> "Expected button");
      BlockPosition var1 = this.i(var0);
      IBlockData var2 = this.a().a_(var1);
      BlockButtonAbstract var3 = (BlockButtonAbstract)var2.b();
      var3.d(var2, this.a(), var1);
   }

   public void d(BlockPosition var0) {
      this.a(var0, this.d());
   }

   public void a(BlockPosition var0, EntityHuman var1) {
      BlockPosition var2 = this.i(var0);
      this.a(var0, var1, new MovingObjectPositionBlock(Vec3D.b(var2), EnumDirection.c, var2, true));
   }

   public void a(BlockPosition var0, EntityHuman var1, MovingObjectPositionBlock var2) {
      BlockPosition var3 = this.i(var0);
      IBlockData var4 = this.a().a_(var3);
      EnumInteractionResult var5 = var4.a(this.a(), var1, EnumHand.a, var2);
      if (!var5.a()) {
         ItemActionContext var6 = new ItemActionContext(var1, EnumHand.a, var2);
         var1.b(EnumHand.a).a(var6);
      }
   }

   public void b(BlockPosition var0, EntityHuman var1, MovingObjectPositionBlock var2) {
      BlockPosition var3 = this.i(var0);
      IBlockData var4 = this.a().a_(var3);
      EnumInteractionResult var5 = var4.a(this.a(), var1, EnumHand.a, var2);
      if (!var5.a()) {
         ItemActionContext var6 = new ItemActionContext(var1, EnumHand.a, var2);
         ItemStack var7 = var1.b(EnumHand.a);
         if (var1.fe()) {
            var7.b(this.a(), var1, var1.fh());
         } else {
            var7.a(var6);
         }
      }
   }

   public EntityLiving a(EntityLiving var0) {
      var0.i(0);
      var0.c(0.25F);
      return var0;
   }

   public EntityHuman c() {
      return new EntityHuman(this.a(), BlockPosition.b, 0.0F, new GameProfile(UUID.randomUUID(), "test-mock-player")) {
         @Override
         public boolean F_() {
            return false;
         }

         @Override
         public boolean f() {
            return false;
         }
      };
   }

   public EntityHuman d() {
      return new EntityHuman(this.a(), BlockPosition.b, 0.0F, new GameProfile(UUID.randomUUID(), "test-mock-player")) {
         @Override
         public boolean F_() {
            return false;
         }

         @Override
         public boolean f() {
            return true;
         }

         @Override
         public boolean g() {
            return true;
         }
      };
   }

   public void b(int var0, int var1, int var2) {
      this.e(new BlockPosition(var0, var1, var2));
   }

   public void e(BlockPosition var0) {
      this.a(Blocks.dv, var0);
      BlockPosition var1 = this.i(var0);
      IBlockData var2 = this.a().a_(var1);
      BlockLever var3 = (BlockLever)var2.b();
      var3.d(var2, this.a(), var1);
   }

   public void a(BlockPosition var0, long var1) {
      this.a(var0, Blocks.gZ);
      this.b(var1, () -> this.a(var0, Blocks.a));
   }

   public void f(BlockPosition var0) {
      this.a().a(this.i(var0), false, null);
   }

   public void a(int var0, int var1, int var2, Block var3) {
      this.a(new BlockPosition(var0, var1, var2), var3);
   }

   public void a(int var0, int var1, int var2, IBlockData var3) {
      this.a(new BlockPosition(var0, var1, var2), var3);
   }

   public void a(BlockPosition var0, Block var1) {
      this.a(var0, var1.o());
   }

   public void a(BlockPosition var0, IBlockData var1) {
      this.a().a(this.i(var0), var1, 3);
   }

   public void e() {
      this.a(13000);
   }

   public void a(int var0) {
      this.a().b((long)var0);
   }

   public void a(Block var0, int var1, int var2, int var3) {
      this.a(var0, new BlockPosition(var1, var2, var3));
   }

   public void a(Block var0, BlockPosition var1) {
      IBlockData var2 = this.a(var1);
      this.a(var1, var2x -> var2.a(var0), "Expected " + var0.f().getString() + ", got " + var2.b().f().getString());
   }

   public void b(Block var0, int var1, int var2, int var3) {
      this.b(var0, new BlockPosition(var1, var2, var3));
   }

   public void b(Block var0, BlockPosition var1) {
      this.a(var1, var2x -> !this.a(var1).a(var0), "Did not expect " + var0.f().getString());
   }

   public void c(Block var0, int var1, int var2, int var3) {
      this.c(var0, new BlockPosition(var1, var2, var3));
   }

   public void c(Block var0, BlockPosition var1) {
      this.b(() -> this.a(var0, var1));
   }

   public void a(BlockPosition var0, Predicate<Block> var1, String var2) {
      this.a(var0, var1, () -> var2);
   }

   public void a(BlockPosition var0, Predicate<Block> var1, Supplier<String> var2) {
      this.b(var0, var1x -> var1.test(var1x.b()), var2);
   }

   public <T extends Comparable<T>> void a(BlockPosition var0, IBlockState<T> var1, T var2) {
      IBlockData var3 = this.a(var0);
      boolean var4 = var3.b(var1);
      if (!var4 || !var3.<T>c(var1).equals(var2)) {
         String var5 = var4 ? "was " + var3.c(var1) : "property " + var1.f() + " is missing";
         String var6 = String.format(Locale.ROOT, "Expected property %s to be %s, %s", var1.f(), var2, var5);
         throw new GameTestHarnessAssertionPosition(var6, this.i(var0), var0, this.a.p());
      }
   }

   public <T extends Comparable<T>> void a(BlockPosition var0, IBlockState<T> var1, Predicate<T> var2, String var3) {
      this.b(var0, var2x -> var2.test(var2x.c(var1)), () -> var3);
   }

   public void b(BlockPosition var0, Predicate<IBlockData> var1, Supplier<String> var2) {
      IBlockData var3 = this.a(var0);
      if (!var1.test(var3)) {
         throw new GameTestHarnessAssertionPosition(var2.get(), this.i(var0), var0, this.a.p());
      }
   }

   public void a(EntityTypes<?> var0) {
      List<? extends Entity> var1 = this.a().a(var0, this.j(), Entity::bq);
      if (var1.isEmpty()) {
         throw new GameTestHarnessAssertion("Expected " + var0.i() + " to exist");
      }
   }

   public void c(EntityTypes<?> var0, int var1, int var2, int var3) {
      this.c(var0, new BlockPosition(var1, var2, var3));
   }

   public void c(EntityTypes<?> var0, BlockPosition var1) {
      BlockPosition var2 = this.i(var1);
      List<? extends Entity> var3 = this.a().a(var0, new AxisAlignedBB(var2), Entity::bq);
      if (var3.isEmpty()) {
         throw new GameTestHarnessAssertionPosition("Expected " + var0.i(), var2, var1, this.a.p());
      }
   }

   public void a(EntityTypes<?> var0, Vec3D var1, Vec3D var2) {
      List<? extends Entity> var3 = this.a().a(var0, new AxisAlignedBB(var1, var2), Entity::bq);
      if (var3.isEmpty()) {
         throw new GameTestHarnessAssertionPosition("Expected " + var0.i() + " between ", BlockPosition.a(var1), BlockPosition.a(var2), this.a.p());
      }
   }

   public void a(EntityTypes<?> var0, BlockPosition var1, int var2, double var3) {
      BlockPosition var5 = this.i(var1);
      List<? extends Entity> var6 = this.b(var0, var1, var3);
      if (var6.size() != var2) {
         throw new GameTestHarnessAssertionPosition(
            "Expected " + var2 + " entities of type " + var0.i() + ", actual number of entities found=" + var6.size(), var5, var1, this.a.p()
         );
      }
   }

   public void a(EntityTypes<?> var0, BlockPosition var1, double var2) {
      List<? extends Entity> var4 = this.b(var0, var1, var2);
      if (var4.isEmpty()) {
         BlockPosition var5 = this.i(var1);
         throw new GameTestHarnessAssertionPosition("Expected " + var0.i(), var5, var1, this.a.p());
      }
   }

   public <T extends Entity> List<T> b(EntityTypes<T> var0, BlockPosition var1, double var2) {
      BlockPosition var4 = this.i(var1);
      return this.a().a(var0, new AxisAlignedBB(var4).g(var2), Entity::bq);
   }

   public void a(Entity var0, int var1, int var2, int var3) {
      this.a(var0, new BlockPosition(var1, var2, var3));
   }

   public void a(Entity var0, BlockPosition var1) {
      BlockPosition var2 = this.i(var1);
      List<? extends Entity> var3 = this.a().a(var0.ae(), new AxisAlignedBB(var2), Entity::bq);
      var3.stream()
         .filter(var1x -> var1x == var0)
         .findFirst()
         .orElseThrow(() -> new GameTestHarnessAssertionPosition("Expected " + var0.ae().i(), var2, var1, this.a.p()));
   }

   public void a(Item var0, BlockPosition var1, double var2, int var4) {
      BlockPosition var5 = this.i(var1);
      List<EntityItem> var6 = this.a().a(EntityTypes.ad, new AxisAlignedBB(var5).g(var2), Entity::bq);
      int var7 = 0;

      for(Entity var9 : var6) {
         EntityItem var10 = (EntityItem)var9;
         if (var10.i().c().equals(var0)) {
            var7 += var10.i().K();
         }
      }

      if (var7 != var4) {
         throw new GameTestHarnessAssertionPosition(
            "Expected " + var4 + " " + var0.p().getString() + " items to exist (found " + var7 + ")", var5, var1, this.a.p()
         );
      }
   }

   public void a(Item var0, BlockPosition var1, double var2) {
      BlockPosition var4 = this.i(var1);

      for(Entity var7 : this.a().a(EntityTypes.ad, new AxisAlignedBB(var4).g(var2), Entity::bq)) {
         EntityItem var8 = (EntityItem)var7;
         if (var8.i().c().equals(var0)) {
            return;
         }
      }

      throw new GameTestHarnessAssertionPosition("Expected " + var0.p().getString() + " item", var4, var1, this.a.p());
   }

   public void b(Item var0, BlockPosition var1, double var2) {
      BlockPosition var4 = this.i(var1);

      for(Entity var7 : this.a().a(EntityTypes.ad, new AxisAlignedBB(var4).g(var2), Entity::bq)) {
         EntityItem var8 = (EntityItem)var7;
         if (var8.i().c().equals(var0)) {
            throw new GameTestHarnessAssertionPosition("Did not expect " + var0.p().getString() + " item", var4, var1, this.a.p());
         }
      }
   }

   public void b(EntityTypes<?> var0) {
      List<? extends Entity> var1 = this.a().a(var0, this.j(), Entity::bq);
      if (!var1.isEmpty()) {
         throw new GameTestHarnessAssertion("Did not expect " + var0.i() + " to exist");
      }
   }

   public void d(EntityTypes<?> var0, int var1, int var2, int var3) {
      this.d(var0, new BlockPosition(var1, var2, var3));
   }

   public void d(EntityTypes<?> var0, BlockPosition var1) {
      BlockPosition var2 = this.i(var1);
      List<? extends Entity> var3 = this.a().a(var0, new AxisAlignedBB(var2), Entity::bq);
      if (!var3.isEmpty()) {
         throw new GameTestHarnessAssertionPosition("Did not expect " + var0.i(), var2, var1, this.a.p());
      }
   }

   public void a(EntityTypes<?> var0, double var1, double var3, double var5) {
      Vec3D var7 = new Vec3D(var1, var3, var5);
      Vec3D var8 = this.a(var7);
      Predicate<? super Entity> var9 = var1x -> var1x.cD().a(var8, var8);
      List<? extends Entity> var10 = this.a().a(var0, this.j(), var9);
      if (var10.isEmpty()) {
         throw new GameTestHarnessAssertion("Expected " + var0.i() + " to touch " + var8 + " (relative " + var7 + ")");
      }
   }

   public void b(EntityTypes<?> var0, double var1, double var3, double var5) {
      Vec3D var7 = new Vec3D(var1, var3, var5);
      Vec3D var8 = this.a(var7);
      Predicate<? super Entity> var9 = var1x -> !var1x.cD().a(var8, var8);
      List<? extends Entity> var10 = this.a().a(var0, this.j(), var9);
      if (var10.isEmpty()) {
         throw new GameTestHarnessAssertion("Did not expect " + var0.i() + " to touch " + var8 + " (relative " + var7 + ")");
      }
   }

   public <E extends Entity, T> void a(BlockPosition var0, EntityTypes<E> var1, Function<? super E, T> var2, @Nullable T var3) {
      BlockPosition var4 = this.i(var0);
      List<E> var5 = this.a().a(var1, new AxisAlignedBB(var4), Entity::bq);
      if (var5.isEmpty()) {
         throw new GameTestHarnessAssertionPosition("Expected " + var1.i(), var4, var0, this.a.p());
      } else {
         for(E var7 : var5) {
            T var8 = var2.apply(var7);
            if (var8 == null) {
               if (var3 != null) {
                  throw new GameTestHarnessAssertion("Expected entity data to be: " + var3 + ", but was: " + var8);
               }
            } else if (!var8.equals(var3)) {
               throw new GameTestHarnessAssertion("Expected entity data to be: " + var3 + ", but was: " + var8);
            }
         }
      }
   }

   public <E extends EntityLiving> void a(BlockPosition var0, EntityTypes<E> var1, Item var2) {
      BlockPosition var3 = this.i(var0);
      List<E> var4 = this.a().a(var1, new AxisAlignedBB(var3), Entity::bq);
      if (var4.isEmpty()) {
         throw new GameTestHarnessAssertionPosition("Expected entity of type: " + var1, var3, var0, this.h());
      } else {
         for(E var6 : var4) {
            if (var6.b(var2)) {
               return;
            }
         }

         throw new GameTestHarnessAssertionPosition("Entity should be holding: " + var2, var3, var0, this.h());
      }
   }

   public <E extends Entity & InventoryCarrier> void b(BlockPosition var0, EntityTypes<E> var1, Item var2) {
      BlockPosition var3 = this.i(var0);
      List<E> var4 = this.a().a(var1, new AxisAlignedBB(var3), var0x -> var0x.bq());
      if (var4.isEmpty()) {
         throw new GameTestHarnessAssertionPosition("Expected " + var1.i() + " to exist", var3, var0, this.h());
      } else {
         for(E var6 : var4) {
            if (var6.w().a_(var1x -> var1x.a(var2))) {
               return;
            }
         }

         throw new GameTestHarnessAssertionPosition("Entity inventory should contain: " + var2, var3, var0, this.h());
      }
   }

   public void g(BlockPosition var0) {
      BlockPosition var1 = this.i(var0);
      TileEntity var2 = this.a().c_(var1);
      if (var2 instanceof TileEntityContainer && !((TileEntityContainer)var2).aa_()) {
         throw new GameTestHarnessAssertion("Container should be empty");
      }
   }

   public void a(BlockPosition var0, Item var1) {
      BlockPosition var2 = this.i(var0);
      TileEntity var3 = this.a().c_(var2);
      if (!(var3 instanceof TileEntityContainer)) {
         throw new GameTestHarnessAssertion("Expected a container at " + var0 + ", found " + BuiltInRegistries.l.b(var3.u()));
      } else if (((TileEntityContainer)var3).a_(var1) != 1) {
         throw new GameTestHarnessAssertion("Container should contain: " + var1);
      }
   }

   public void a(StructureBoundingBox var0, BlockPosition var1) {
      BlockPosition.a(var0).forEach(var2x -> {
         BlockPosition var3 = var1.b(var2x.u() - var0.g(), var2x.v() - var0.h(), var2x.w() - var0.i());
         this.a(var2x, var3);
      });
   }

   public void a(BlockPosition var0, BlockPosition var1) {
      IBlockData var2 = this.a(var0);
      IBlockData var3 = this.a(var1);
      if (var2 != var3) {
         this.a("Incorrect state. Expected " + var3 + ", got " + var2, var0);
      }
   }

   public void a(long var0, BlockPosition var2, Item var3) {
      this.a(var0, () -> this.a(var2, var3));
   }

   public void a(long var0, BlockPosition var2) {
      this.a(var0, () -> this.g(var2));
   }

   public <E extends Entity, T> void b(BlockPosition var0, EntityTypes<E> var1, Function<E, T> var2, T var3) {
      this.b(() -> this.a(var0, var1, var2, var3));
   }

   public <E extends Entity> void a(E var0, Predicate<E> var1, String var2) {
      if (!var1.test(var0)) {
         throw new GameTestHarnessAssertion("Entity " + var0 + " failed " + var2 + " test");
      }
   }

   public <E extends Entity, T> void a(E var0, Function<E, T> var1, String var2, T var3) {
      T var4 = var1.apply(var0);
      if (!var4.equals(var3)) {
         throw new GameTestHarnessAssertion("Entity " + var0 + " value " + var2 + "=" + var4 + " is not equal to expected " + var3);
      }
   }

   public void e(EntityTypes<?> var0, int var1, int var2, int var3) {
      this.e(var0, new BlockPosition(var1, var2, var3));
   }

   public void e(EntityTypes<?> var0, BlockPosition var1) {
      this.b(() -> this.c(var0, var1));
   }

   public void f(EntityTypes<?> var0, int var1, int var2, int var3) {
      this.f(var0, new BlockPosition(var1, var2, var3));
   }

   public void f(EntityTypes<?> var0, BlockPosition var1) {
      this.b(() -> this.d(var0, var1));
   }

   public void f() {
      this.a.m();
   }

   private void i() {
      if (this.b) {
         throw new IllegalStateException("This test already has final clause");
      } else {
         this.b = true;
      }
   }

   public void a(Runnable var0) {
      this.i();
      this.a.q().a(0L, var0).a();
   }

   public void b(Runnable var0) {
      this.i();
      this.a.q().a(var0).a();
   }

   public void a(int var0, Runnable var1) {
      this.i();
      this.a.q().a((long)var0, var1).a();
   }

   public void a(long var0, Runnable var2) {
      this.a.a(var0, var2);
   }

   public void b(long var0, Runnable var2) {
      this.a(this.a.p() + var0, var2);
   }

   public void h(BlockPosition var0) {
      BlockPosition var1 = this.i(var0);
      WorldServer var2 = this.a();
      var2.a_(var1).b(var2, var1, var2.z);
   }

   public int a(HeightMap.Type var0, int var1, int var2) {
      BlockPosition var3 = this.i(new BlockPosition(var1, 0, var2));
      return this.j(this.a().a(var0, var3)).v();
   }

   public void a(String var0, BlockPosition var1) {
      throw new GameTestHarnessAssertionPosition(var0, this.i(var1), var1, this.h());
   }

   public void a(String var0, Entity var1) {
      throw new GameTestHarnessAssertionPosition(var0, var1.dg(), this.j(var1.dg()), this.h());
   }

   public void a(String var0) {
      throw new GameTestHarnessAssertion(var0);
   }

   public void c(Runnable var0) {
      this.a.q().a(var0).a(() -> new GameTestHarnessAssertion("Fail conditions met"));
   }

   public void d(Runnable var0) {
      LongStream.range(this.a.p(), (long)this.a.w()).forEach(var1x -> this.a.a(var1x, var0::run));
   }

   public GameTestHarnessSequence g() {
      return this.a.q();
   }

   public BlockPosition i(BlockPosition var0) {
      BlockPosition var1 = this.a.d();
      BlockPosition var2 = var1.a(var0);
      return DefinedStructure.a(var2, EnumBlockMirror.a, this.a.u(), var1);
   }

   public BlockPosition j(BlockPosition var0) {
      BlockPosition var1 = this.a.d();
      EnumBlockRotation var2 = this.a.u().a(EnumBlockRotation.c);
      BlockPosition var3 = DefinedStructure.a(var0, EnumBlockMirror.a, var2, var1);
      return var3.b(var1);
   }

   public Vec3D a(Vec3D var0) {
      Vec3D var1 = Vec3D.a(this.a.d());
      return DefinedStructure.a(var1.e(var0), EnumBlockMirror.a, this.a.u(), this.a.d());
   }

   public Vec3D b(Vec3D var0) {
      Vec3D var1 = Vec3D.a(this.a.d());
      return DefinedStructure.a(var0.d(var1), EnumBlockMirror.a, this.a.u(), this.a.d());
   }

   public void a(boolean var0, String var1) {
      if (!var0) {
         throw new GameTestHarnessAssertion(var1);
      }
   }

   public long h() {
      return this.a.p();
   }

   private AxisAlignedBB j() {
      return this.a.f();
   }

   private AxisAlignedBB k() {
      AxisAlignedBB var0 = this.a.f();
      return var0.a(BlockPosition.b.b(this.i(BlockPosition.b)));
   }

   public void a(Consumer<BlockPosition> var0) {
      AxisAlignedBB var1 = this.k();
      BlockPosition.MutableBlockPosition.a(var1.d(0.0, 1.0, 0.0)).forEach(var0);
   }

   public void e(Runnable var0) {
      LongStream.range(this.a.p(), (long)this.a.w()).forEach(var1x -> this.a.a(var1x, var0::run));
   }

   public void a(EntityHuman var0, ItemStack var1, BlockPosition var2, EnumDirection var3) {
      BlockPosition var4 = this.i(var2.a(var3));
      MovingObjectPositionBlock var5 = new MovingObjectPositionBlock(Vec3D.b(var4), var3, var4, false);
      ItemActionContext var6 = new ItemActionContext(var0, EnumHand.a, var5);
      var1.a(var6);
   }
}
