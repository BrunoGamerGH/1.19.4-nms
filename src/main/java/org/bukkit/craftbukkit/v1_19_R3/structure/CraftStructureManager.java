package org.bukkit.craftbukkit.v1_19_R3.structure;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import net.minecraft.nbt.NBTCompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.apache.commons.lang3.Validate;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;
import org.bukkit.structure.Structure;
import org.bukkit.structure.StructureManager;

public class CraftStructureManager implements StructureManager {
   private final StructureTemplateManager structureManager;

   public CraftStructureManager(StructureTemplateManager structureManager) {
      this.structureManager = structureManager;
   }

   public Map<NamespacedKey, Structure> getStructures() {
      Map<NamespacedKey, Structure> cachedStructures = new HashMap();

      for(Entry<MinecraftKey, Optional<DefinedStructure>> entry : this.structureManager.f.entrySet()) {
         entry.getValue()
            .ifPresent(definedStructure -> cachedStructures.put(CraftNamespacedKey.fromMinecraft(entry.getKey()), new CraftStructure(definedStructure)));
      }

      return Collections.unmodifiableMap(cachedStructures);
   }

   public Structure getStructure(NamespacedKey structureKey) {
      Validate.notNull(structureKey, "structureKey cannot be null", new Object[0]);
      Optional<DefinedStructure> definedStructure = this.structureManager.f.get(CraftNamespacedKey.toMinecraft(structureKey));
      return definedStructure == null ? null : definedStructure.map(CraftStructure::new).orElse(null);
   }

   public Structure loadStructure(NamespacedKey structureKey, boolean register) {
      MinecraftKey minecraftKey = this.createAndValidateMinecraftStructureKey(structureKey);
      Optional<DefinedStructure> structure = this.structureManager.f.get(minecraftKey);
      structure = structure == null ? Optional.empty() : structure;
      structure = structure.isPresent() ? structure : this.structureManager.h(minecraftKey);
      structure = structure.isPresent() ? structure : this.structureManager.f(minecraftKey);
      if (register) {
         this.structureManager.f.put(minecraftKey, structure);
      }

      return structure.map(CraftStructure::new).orElse(null);
   }

   public Structure loadStructure(NamespacedKey structureKey) {
      return this.loadStructure(structureKey, true);
   }

   public void saveStructure(NamespacedKey structureKey) {
      MinecraftKey minecraftKey = this.createAndValidateMinecraftStructureKey(structureKey);
      this.structureManager.c(minecraftKey);
   }

   public void saveStructure(NamespacedKey structureKey, Structure structure) throws IOException {
      Validate.notNull(structure, "structure cannot be null", new Object[0]);
      File structureFile = this.getStructureFile(structureKey);
      Files.createDirectories(structureFile.toPath().getParent());
      this.saveStructure(structureFile, structure);
   }

   public Structure registerStructure(NamespacedKey structureKey, Structure structure) {
      Validate.notNull(structure, "structure cannot be null", new Object[0]);
      MinecraftKey minecraftKey = this.createAndValidateMinecraftStructureKey(structureKey);
      Optional<DefinedStructure> optionalDefinedStructure = Optional.of(((CraftStructure)structure).getHandle());
      Optional<DefinedStructure> previousStructure = this.structureManager.f.put(minecraftKey, optionalDefinedStructure);
      return previousStructure == null ? null : previousStructure.map(CraftStructure::new).orElse(null);
   }

   public Structure unregisterStructure(NamespacedKey structureKey) {
      MinecraftKey minecraftKey = this.createAndValidateMinecraftStructureKey(structureKey);
      Optional<DefinedStructure> previousStructure = this.structureManager.f.remove(minecraftKey);
      return previousStructure == null ? null : previousStructure.map(CraftStructure::new).orElse(null);
   }

   public void deleteStructure(NamespacedKey structureKey) throws IOException {
      this.deleteStructure(structureKey, true);
   }

   public void deleteStructure(NamespacedKey structureKey, boolean unregister) throws IOException {
      MinecraftKey key = CraftNamespacedKey.toMinecraft(structureKey);
      if (unregister) {
         this.structureManager.f.remove(key);
      }

      Path path = this.structureManager.a(key, ".nbt");
      Files.deleteIfExists(path);
   }

   public File getStructureFile(NamespacedKey structureKey) {
      MinecraftKey minecraftKey = this.createAndValidateMinecraftStructureKey(structureKey);
      return this.structureManager.a(minecraftKey, ".nbt").toFile();
   }

   public Structure loadStructure(File file) throws IOException {
      Validate.notNull(file, "file cannot be null", new Object[0]);
      FileInputStream fileinputstream = new FileInputStream(file);
      return this.loadStructure(fileinputstream);
   }

   public Structure loadStructure(InputStream inputStream) throws IOException {
      Validate.notNull(inputStream, "inputStream cannot be null", new Object[0]);
      return new CraftStructure(this.structureManager.a(inputStream));
   }

   public void saveStructure(File file, Structure structure) throws IOException {
      Validate.notNull(file, "file cannot be null", new Object[0]);
      Validate.notNull(structure, "structure cannot be null", new Object[0]);
      FileOutputStream fileoutputstream = new FileOutputStream(file);
      this.saveStructure(fileoutputstream, structure);
   }

   public void saveStructure(OutputStream outputStream, Structure structure) throws IOException {
      Validate.notNull(outputStream, "outputStream cannot be null", new Object[0]);
      Validate.notNull(structure, "structure cannot be null", new Object[0]);
      NBTTagCompound nbttagcompound = ((CraftStructure)structure).getHandle().a(new NBTTagCompound());
      NBTCompressedStreamTools.a(nbttagcompound, outputStream);
   }

   public Structure createStructure() {
      return new CraftStructure(new DefinedStructure());
   }

   private MinecraftKey createAndValidateMinecraftStructureKey(NamespacedKey structureKey) {
      Validate.notNull(structureKey, "structureKey cannot be null", new Object[0]);
      MinecraftKey minecraftkey = CraftNamespacedKey.toMinecraft(structureKey);
      if (minecraftkey.a().contains("//")) {
         throw new IllegalArgumentException("Resource key for Structures can not contain \"//\"");
      } else {
         return minecraftkey;
      }
   }

   public Structure copy(Structure structure) {
      return new CraftStructure(this.structureManager.a(((CraftStructure)structure).getHandle().a(new NBTTagCompound())));
   }
}
