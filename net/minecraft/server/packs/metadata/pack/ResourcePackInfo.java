package net.minecraft.server.packs.metadata.pack;

import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.packs.metadata.MetadataSectionType;

public class ResourcePackInfo {
   public static final MetadataSectionType<ResourcePackInfo> a = new ResourcePackInfoDeserializer();
   private final IChatBaseComponent b;
   private final int c;

   public ResourcePackInfo(IChatBaseComponent var0, int var1) {
      this.b = var0;
      this.c = var1;
   }

   public IChatBaseComponent a() {
      return this.b;
   }

   public int b() {
      return this.c;
   }
}
