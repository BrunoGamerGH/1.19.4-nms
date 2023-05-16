package net.minecraft.server.packs.resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import javax.annotation.Nullable;
import net.minecraft.server.packs.IResourcePack;

public class IResource {
   private final IResourcePack a;
   private final IoSupplier<InputStream> b;
   private final IoSupplier<ResourceMetadata> c;
   @Nullable
   private ResourceMetadata d;

   public IResource(IResourcePack var0, IoSupplier<InputStream> var1, IoSupplier<ResourceMetadata> var2) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
   }

   public IResource(IResourcePack var0, IoSupplier<InputStream> var1) {
      this.a = var0;
      this.b = var1;
      this.c = ResourceMetadata.b;
      this.d = ResourceMetadata.a;
   }

   public IResourcePack a() {
      return this.a;
   }

   public String b() {
      return this.a.a();
   }

   public boolean c() {
      return this.a.b();
   }

   public InputStream d() throws IOException {
      return this.b.get();
   }

   public BufferedReader e() throws IOException {
      return new BufferedReader(new InputStreamReader(this.d(), StandardCharsets.UTF_8));
   }

   public ResourceMetadata f() throws IOException {
      if (this.d == null) {
         this.d = this.c.get();
      }

      return this.d;
   }
}
