package net.minecraft;

import java.util.Date;
import net.minecraft.server.packs.EnumResourcePackType;
import net.minecraft.world.level.storage.DataVersion;

public interface WorldVersion {
   DataVersion d();

   String b();

   String c();

   int e();

   int a(EnumResourcePackType var1);

   Date f();

   boolean g();
}
