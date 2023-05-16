package net.minecraft.world.level.block.entity;

import java.util.Arrays;
import java.util.Optional;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.game.PacketPlayOutTileEntityData;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.INamable;
import net.minecraft.world.level.block.BlockJigsaw;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.structure.pools.WorldGenFeatureDefinedStructureJigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.WorldGenFeatureDefinedStructurePoolTemplate;

public class TileEntityJigsaw extends TileEntity {
   public static final String a = "target";
   public static final String b = "pool";
   public static final String c = "joint";
   public static final String d = "name";
   public static final String e = "final_state";
   private MinecraftKey f = new MinecraftKey("empty");
   private MinecraftKey g = new MinecraftKey("empty");
   private ResourceKey<WorldGenFeatureDefinedStructurePoolTemplate> h = ResourceKey.a(Registries.aA, new MinecraftKey("empty"));
   private TileEntityJigsaw.JointType i = TileEntityJigsaw.JointType.a;
   private String j = "minecraft:air";

   public TileEntityJigsaw(BlockPosition var0, IBlockData var1) {
      super(TileEntityTypes.F, var0, var1);
   }

   public MinecraftKey c() {
      return this.f;
   }

   public MinecraftKey d() {
      return this.g;
   }

   public ResourceKey<WorldGenFeatureDefinedStructurePoolTemplate> f() {
      return this.h;
   }

   public String g() {
      return this.j;
   }

   public TileEntityJigsaw.JointType i() {
      return this.i;
   }

   public void a(MinecraftKey var0) {
      this.f = var0;
   }

   public void b(MinecraftKey var0) {
      this.g = var0;
   }

   public void a(ResourceKey<WorldGenFeatureDefinedStructurePoolTemplate> var0) {
      this.h = var0;
   }

   public void a(String var0) {
      this.j = var0;
   }

   public void a(TileEntityJigsaw.JointType var0) {
      this.i = var0;
   }

   @Override
   protected void b(NBTTagCompound var0) {
      super.b(var0);
      var0.a("name", this.f.toString());
      var0.a("target", this.g.toString());
      var0.a("pool", this.h.a().toString());
      var0.a("final_state", this.j);
      var0.a("joint", this.i.c());
   }

   @Override
   public void a(NBTTagCompound var0) {
      super.a(var0);
      this.f = new MinecraftKey(var0.l("name"));
      this.g = new MinecraftKey(var0.l("target"));
      this.h = ResourceKey.a(Registries.aA, new MinecraftKey(var0.l("pool")));
      this.j = var0.l("final_state");
      this.i = TileEntityJigsaw.JointType.a(var0.l("joint"))
         .orElseGet(() -> BlockJigsaw.h(this.q()).o().d() ? TileEntityJigsaw.JointType.b : TileEntityJigsaw.JointType.a);
   }

   public PacketPlayOutTileEntityData j() {
      return PacketPlayOutTileEntityData.a(this);
   }

   @Override
   public NBTTagCompound aq_() {
      return this.o();
   }

   public void a(WorldServer var0, int var1, boolean var2) {
      BlockPosition var3 = this.p().a(this.q().c(BlockJigsaw.a).a());
      IRegistry<WorldGenFeatureDefinedStructurePoolTemplate> var4 = var0.u_().d(Registries.aA);
      Holder<WorldGenFeatureDefinedStructurePoolTemplate> var5 = var4.f(this.h);
      WorldGenFeatureDefinedStructureJigsawPlacement.a(var0, var5, this.g, var1, var3, var2);
   }

   public static enum JointType implements INamable {
      a("rollable"),
      b("aligned");

      private final String c;

      private JointType(String var2) {
         this.c = var2;
      }

      @Override
      public String c() {
         return this.c;
      }

      public static Optional<TileEntityJigsaw.JointType> a(String var0) {
         return Arrays.stream(values()).filter(var1 -> var1.c().equals(var0)).findFirst();
      }

      public IChatBaseComponent a() {
         return IChatBaseComponent.c("jigsaw_block.joint." + this.c);
      }
   }
}
