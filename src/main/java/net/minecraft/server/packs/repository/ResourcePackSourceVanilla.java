package net.minecraft.server.packs.repository;

import java.nio.file.Path;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.packs.BuiltInMetadata;
import net.minecraft.server.packs.EnumResourcePackType;
import net.minecraft.server.packs.FeatureFlagsMetadataSection;
import net.minecraft.server.packs.IResourcePack;
import net.minecraft.server.packs.ResourcePackVanilla;
import net.minecraft.server.packs.VanillaPackResourcesBuilder;
import net.minecraft.server.packs.metadata.pack.ResourcePackInfo;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.storage.Convertable;
import net.minecraft.world.level.storage.SavedFile;

public class ResourcePackSourceVanilla extends BuiltInPackSource {
   private static final ResourcePackInfo b = new ResourcePackInfo(
      IChatBaseComponent.c("dataPack.vanilla.description"), SharedConstants.b().a(EnumResourcePackType.b)
   );
   private static final FeatureFlagsMetadataSection c = new FeatureFlagsMetadataSection(FeatureFlags.g);
   private static final BuiltInMetadata d = BuiltInMetadata.a(ResourcePackInfo.a, b, FeatureFlagsMetadataSection.a, c);
   private static final IChatBaseComponent e = IChatBaseComponent.c("dataPack.vanilla.name");
   private static final MinecraftKey f = new MinecraftKey("minecraft", "datapacks");

   public ResourcePackSourceVanilla() {
      super(EnumResourcePackType.b, b(), f);
   }

   private static ResourcePackVanilla b() {
      return new VanillaPackResourcesBuilder().a(d).a("minecraft").b().a().c();
   }

   @Override
   protected IChatBaseComponent a(String var0) {
      return IChatBaseComponent.b(var0);
   }

   @Nullable
   @Override
   protected ResourcePackLoader a(IResourcePack var0) {
      return ResourcePackLoader.a("vanilla", e, false, var1x -> var0, EnumResourcePackType.b, ResourcePackLoader.Position.b, PackSource.c);
   }

   @Nullable
   @Override
   protected ResourcePackLoader a(String var0, ResourcePackLoader.c var1, IChatBaseComponent var2) {
      return ResourcePackLoader.a(var0, var2, false, var1, EnumResourcePackType.b, ResourcePackLoader.Position.a, PackSource.d);
   }

   public static ResourcePackRepository a(Path var0) {
      return new ResourcePackRepository(new ResourcePackSourceVanilla(), new ResourcePackSourceFolder(var0, EnumResourcePackType.b, PackSource.e));
   }

   public static ResourcePackRepository a(Convertable.ConversionSession var0) {
      return a(var0.a(SavedFile.j));
   }
}
