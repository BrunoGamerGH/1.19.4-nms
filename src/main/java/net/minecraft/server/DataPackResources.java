package net.minecraft.server;

import com.mojang.logging.LogUtils;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandDispatcher;
import net.minecraft.core.Holder;
import net.minecraft.core.IRegistry;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.resources.IReloadListener;
import net.minecraft.server.packs.resources.IResourceManager;
import net.minecraft.server.packs.resources.Reloadable;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.TagRegistry;
import net.minecraft.util.Unit;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.crafting.CraftingManager;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.ItemModifierManager;
import net.minecraft.world.level.storage.loot.LootPredicateManager;
import net.minecraft.world.level.storage.loot.LootTableRegistry;
import org.slf4j.Logger;

public class DataPackResources {
   private static final Logger a = LogUtils.getLogger();
   private static final CompletableFuture<Unit> b = CompletableFuture.completedFuture(Unit.a);
   private final CommandBuildContext.a c;
   public CommandDispatcher d;
   private final CraftingManager e = new CraftingManager();
   private final TagRegistry f;
   private final LootPredicateManager g = new LootPredicateManager();
   private final LootTableRegistry h = new LootTableRegistry(this.g);
   private final ItemModifierManager i = new ItemModifierManager(this.g, this.h);
   private final AdvancementDataWorld j = new AdvancementDataWorld(this.g);
   private final CustomFunctionManager k;

   public DataPackResources(IRegistryCustom.Dimension var0, FeatureFlagSet var1, CommandDispatcher.ServerType var2, int var3) {
      this.f = new TagRegistry(var0);
      this.c = CommandBuildContext.a((IRegistryCustom)var0, var1);
      this.d = new CommandDispatcher(var2, this.c);
      this.c.a(CommandBuildContext.b.a);
      this.k = new CustomFunctionManager(var3, this.d.a());
   }

   public CustomFunctionManager a() {
      return this.k;
   }

   public LootPredicateManager b() {
      return this.g;
   }

   public LootTableRegistry c() {
      return this.h;
   }

   public ItemModifierManager d() {
      return this.i;
   }

   public CraftingManager e() {
      return this.e;
   }

   public CommandDispatcher f() {
      return this.d;
   }

   public AdvancementDataWorld g() {
      return this.j;
   }

   public List<IReloadListener> h() {
      return List.of(this.f, this.g, this.e, this.h, this.i, this.k, this.j);
   }

   public static CompletableFuture<DataPackResources> a(
      IResourceManager var0, IRegistryCustom.Dimension var1, FeatureFlagSet var2, CommandDispatcher.ServerType var3, int var4, Executor var5, Executor var6
   ) {
      DataPackResources var7 = new DataPackResources(var1, var2, var3, var4);
      return Reloadable.a(var0, var7.h(), var5, var6, b, a.isDebugEnabled())
         .a()
         .whenComplete((var1x, var2x) -> var7.c.a(CommandBuildContext.b.b))
         .thenApply(var1x -> var7);
   }

   public void a(IRegistryCustom var0) {
      this.f.a().forEach(var1x -> a(var0, var1x));
      Blocks.a();
   }

   private static <T> void a(IRegistryCustom var0, TagRegistry.a<T> var1) {
      ResourceKey<? extends IRegistry<T>> var2 = var1.a();
      Map<TagKey<T>, List<Holder<T>>> var3 = var1.b()
         .entrySet()
         .stream()
         .collect(Collectors.toUnmodifiableMap(var1x -> TagKey.a(var2, (MinecraftKey)var1x.getKey()), var0x -> List.copyOf((Collection)var0x.getValue())));
      var0.d(var2).a(var3);
   }
}
