package net.minecraft.world.item;

import com.google.common.collect.Iterables;
import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.EnumChatFormat;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.Holder;
import net.minecraft.core.SectionPosition;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.context.ItemActionContext;
import net.minecraft.world.level.World;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.Chunk;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.MaterialMapColor;
import net.minecraft.world.level.saveddata.maps.WorldMap;
import org.bukkit.Bukkit;
import org.bukkit.event.server.MapInitializeEvent;

public class ItemWorldMap extends ItemWorldMapBase {
   public static final int a = 128;
   public static final int b = 128;
   private static final int e = -12173266;
   private static final String f = "map";
   public static final String c = "map_scale_direction";
   public static final String d = "map_to_lock";

   public ItemWorldMap(Item.Info item_info) {
      super(item_info);
   }

   public static ItemStack a(World world, int i, int j, byte b0, boolean flag, boolean flag1) {
      ItemStack itemstack = new ItemStack(Items.rb);
      a(itemstack, world, i, j, b0, flag, flag1, world.ab());
      return itemstack;
   }

   @Nullable
   public static WorldMap a(@Nullable Integer integer, World world) {
      return integer == null ? null : world.a(a(integer));
   }

   @Nullable
   public static WorldMap a(ItemStack itemstack, World world) {
      Integer integer = d(itemstack);
      return a(integer, world);
   }

   @Nullable
   public static Integer d(ItemStack itemstack) {
      NBTTagCompound nbttagcompound = itemstack.u();
      return nbttagcompound != null && nbttagcompound.b("map", 99) ? nbttagcompound.h("map") : -1;
   }

   public static int a(World world, int i, int j, int k, boolean flag, boolean flag1, ResourceKey<World> resourcekey) {
      WorldMap worldmap = WorldMap.a((double)i, (double)j, (byte)k, flag, flag1, resourcekey);
      int l = world.t();
      world.a(a(l), worldmap);
      MapInitializeEvent event = new MapInitializeEvent(worldmap.mapView);
      Bukkit.getServer().getPluginManager().callEvent(event);
      return l;
   }

   private static void a(ItemStack itemstack, int i) {
      itemstack.v().a("map", i);
   }

   private static void a(ItemStack itemstack, World world, int i, int j, int k, boolean flag, boolean flag1, ResourceKey<World> resourcekey) {
      int l = a(world, i, j, k, flag, flag1, resourcekey);
      a(itemstack, l);
   }

   public static String a(int i) {
      return "map_" + i;
   }

   public void a(World world, Entity entity, WorldMap worldmap) {
      if (world.ab() == worldmap.e && entity instanceof EntityHuman) {
         int i = 1 << worldmap.f;
         int j = worldmap.c;
         int k = worldmap.d;
         int l = MathHelper.a(entity.dl() - (double)j) / i + 64;
         int i1 = MathHelper.a(entity.dr() - (double)k) / i + 64;
         int j1 = 128 / i;
         if (world.q_().h()) {
            j1 /= 2;
         }

         WorldMap.WorldMapHumanTracker worldmap_worldmaphumantracker = worldmap.a((EntityHuman)entity);
         ++worldmap_worldmaphumantracker.b;
         BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();
         BlockPosition.MutableBlockPosition blockposition_mutableblockposition1 = new BlockPosition.MutableBlockPosition();
         boolean flag = false;

         for(int k1 = l - j1 + 1; k1 < l + j1; ++k1) {
            if ((k1 & 15) == (worldmap_worldmaphumantracker.b & 15) || flag) {
               flag = false;
               double d0 = 0.0;

               for(int l1 = i1 - j1 - 1; l1 < i1 + j1; ++l1) {
                  if (k1 >= 0 && l1 >= -1 && k1 < 128 && l1 < 128) {
                     int i2 = MathHelper.h(k1 - l) + MathHelper.h(l1 - i1);
                     boolean flag1 = i2 > (j1 - 2) * (j1 - 2);
                     int j2 = (j / i + k1 - 64) * i;
                     int k2 = (k / i + l1 - 64) * i;
                     Multiset<MaterialMapColor> multiset = LinkedHashMultiset.create();
                     Chunk chunk = world.d(SectionPosition.a(j2), SectionPosition.a(k2));
                     if (!chunk.A()) {
                        int l2 = 0;
                        double d1 = 0.0;
                        if (world.q_().h()) {
                           int i3 = j2 + k2 * 231871;
                           i3 = i3 * i3 * 31287121 + i3 * 11;
                           if ((i3 >> 20 & 1) == 0) {
                              multiset.add(Blocks.j.o().d(world, BlockPosition.b), 10);
                           } else {
                              multiset.add(Blocks.b.o().d(world, BlockPosition.b), 100);
                           }

                           d1 = 100.0;
                        } else {
                           for(int i3 = 0; i3 < i; ++i3) {
                              for(int j3 = 0; j3 < i; ++j3) {
                                 blockposition_mutableblockposition.d(j2 + i3, 0, k2 + j3);
                                 int k3 = chunk.a(HeightMap.Type.b, blockposition_mutableblockposition.u(), blockposition_mutableblockposition.w()) + 1;
                                 IBlockData iblockdata;
                                 if (k3 <= world.v_() + 1) {
                                    iblockdata = Blocks.F.o();
                                 } else {
                                    do {
                                       blockposition_mutableblockposition.q(--k3);
                                       iblockdata = chunk.a_(blockposition_mutableblockposition);
                                    } while(iblockdata.d(world, blockposition_mutableblockposition) == MaterialMapColor.a && k3 > world.v_());

                                    if (k3 > world.v_() && !iblockdata.r().c()) {
                                       int l3 = k3 - 1;
                                       blockposition_mutableblockposition1.g(blockposition_mutableblockposition);

                                       IBlockData iblockdata1;
                                       do {
                                          blockposition_mutableblockposition1.q(l3--);
                                          iblockdata1 = chunk.a_(blockposition_mutableblockposition1);
                                          ++l2;
                                       } while(l3 > world.v_() && !iblockdata1.r().c());

                                       iblockdata = this.a(world, iblockdata, blockposition_mutableblockposition);
                                    }
                                 }

                                 worldmap.a(world, blockposition_mutableblockposition.u(), blockposition_mutableblockposition.w());
                                 d1 += (double)k3 / (double)(i * i);
                                 multiset.add(iblockdata.d(world, blockposition_mutableblockposition));
                              }
                           }
                        }

                        l2 /= i * i;
                        MaterialMapColor materialmapcolor = (MaterialMapColor)Iterables.getFirst(Multisets.copyHighestCountFirst(multiset), MaterialMapColor.a);
                        MaterialMapColor.a materialmapcolor_a;
                        if (materialmapcolor == MaterialMapColor.m) {
                           double d2 = (double)l2 * 0.1 + (double)(k1 + l1 & 1) * 0.2;
                           if (d2 < 0.5) {
                              materialmapcolor_a = MaterialMapColor.a.c;
                           } else if (d2 > 0.9) {
                              materialmapcolor_a = MaterialMapColor.a.a;
                           } else {
                              materialmapcolor_a = MaterialMapColor.a.b;
                           }
                        } else {
                           double d2 = (d1 - d0) * 4.0 / (double)(i + 4) + ((double)(k1 + l1 & 1) - 0.5) * 0.4;
                           if (d2 > 0.6) {
                              materialmapcolor_a = MaterialMapColor.a.c;
                           } else if (d2 < -0.6) {
                              materialmapcolor_a = MaterialMapColor.a.a;
                           } else {
                              materialmapcolor_a = MaterialMapColor.a.b;
                           }
                        }

                        d0 = d1;
                        if (l1 >= 0 && i2 < j1 * j1 && (!flag1 || (k1 + l1 & 1) != 0)) {
                           flag |= worldmap.a(k1, l1, materialmapcolor.b(materialmapcolor_a));
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private IBlockData a(World world, IBlockData iblockdata, BlockPosition blockposition) {
      Fluid fluid = iblockdata.r();
      return !fluid.c() && !iblockdata.d(world, blockposition, EnumDirection.b) ? fluid.g() : iblockdata;
   }

   private static boolean a(boolean[] aboolean, int i, int j) {
      return aboolean[j * 128 + i];
   }

   public static void a(WorldServer worldserver, ItemStack itemstack) {
      WorldMap worldmap = a(itemstack, worldserver);
      if (worldmap != null && worldserver.ab() == worldmap.e) {
         int i = 1 << worldmap.f;
         int j = worldmap.c;
         int k = worldmap.d;
         boolean[] aboolean = new boolean[16384];
         int l = j / i - 64;
         int i1 = k / i - 64;
         BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();

         for(int j1 = 0; j1 < 128; ++j1) {
            for(int k1 = 0; k1 < 128; ++k1) {
               Holder<BiomeBase> holder = worldserver.v(blockposition_mutableblockposition.d((l + k1) * i, 0, (i1 + j1) * i));
               aboolean[j1 * 128 + k1] = holder.a(BiomeTags.Z);
            }
         }

         for(int var15 = 1; var15 < 127; ++var15) {
            for(int k1 = 1; k1 < 127; ++k1) {
               int l1 = 0;

               for(int i2 = -1; i2 < 2; ++i2) {
                  for(int j2 = -1; j2 < 2; ++j2) {
                     if ((i2 != 0 || j2 != 0) && a(aboolean, var15 + i2, k1 + j2)) {
                        ++l1;
                     }
                  }
               }

               MaterialMapColor.a materialmapcolor_a = MaterialMapColor.a.d;
               MaterialMapColor materialmapcolor = MaterialMapColor.a;
               if (a(aboolean, var15, k1)) {
                  materialmapcolor = MaterialMapColor.p;
                  if (l1 > 7 && k1 % 2 == 0) {
                     switch((var15 + (int)(MathHelper.a((float)k1 + 0.0F) * 7.0F)) / 8 % 5) {
                        case 0:
                        case 4:
                           materialmapcolor_a = MaterialMapColor.a.a;
                           break;
                        case 1:
                        case 3:
                           materialmapcolor_a = MaterialMapColor.a.b;
                           break;
                        case 2:
                           materialmapcolor_a = MaterialMapColor.a.c;
                     }
                  } else if (l1 > 7) {
                     materialmapcolor = MaterialMapColor.a;
                  } else if (l1 > 5) {
                     materialmapcolor_a = MaterialMapColor.a.b;
                  } else if (l1 > 3) {
                     materialmapcolor_a = MaterialMapColor.a.a;
                  } else if (l1 > 1) {
                     materialmapcolor_a = MaterialMapColor.a.a;
                  }
               } else if (l1 > 0) {
                  materialmapcolor = MaterialMapColor.A;
                  if (l1 > 3) {
                     materialmapcolor_a = MaterialMapColor.a.b;
                  } else {
                     materialmapcolor_a = MaterialMapColor.a.d;
                  }
               }

               if (materialmapcolor != MaterialMapColor.a) {
                  worldmap.b(var15, k1, materialmapcolor.b(materialmapcolor_a));
               }
            }
         }
      }
   }

   @Override
   public void a(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
      if (!world.B) {
         WorldMap worldmap = a(itemstack, world);
         if (worldmap != null) {
            if (entity instanceof EntityHuman entityhuman) {
               worldmap.a(entityhuman, itemstack);
            }

            if (!worldmap.h && (flag || entity instanceof EntityHuman && ((EntityHuman)entity).eL() == itemstack)) {
               this.a(world, entity, worldmap);
            }
         }
      }
   }

   @Nullable
   @Override
   public Packet<?> a(ItemStack itemstack, World world, EntityHuman entityhuman) {
      Integer integer = d(itemstack);
      WorldMap worldmap = a(integer, world);
      return worldmap != null ? worldmap.a(integer, entityhuman) : null;
   }

   @Override
   public void b(ItemStack itemstack, World world, EntityHuman entityhuman) {
      NBTTagCompound nbttagcompound = itemstack.u();
      if (nbttagcompound != null && nbttagcompound.b("map_scale_direction", 99)) {
         a(itemstack, world, nbttagcompound.h("map_scale_direction"));
         nbttagcompound.r("map_scale_direction");
      } else if (nbttagcompound != null && nbttagcompound.b("map_to_lock", 1) && nbttagcompound.q("map_to_lock")) {
         a(world, itemstack);
         nbttagcompound.r("map_to_lock");
      }
   }

   private static void a(ItemStack itemstack, World world, int i) {
      WorldMap worldmap = a(itemstack, world);
      if (worldmap != null) {
         int j = world.t();
         world.a(a(j), worldmap.a(i));
         a(itemstack, j);
      }
   }

   public static void a(World world, ItemStack itemstack) {
      WorldMap worldmap = a(itemstack, world);
      if (worldmap != null) {
         int i = world.t();
         String s = a(i);
         WorldMap worldmap1 = worldmap.a();
         world.a(s, worldmap1);
         a(itemstack, i);
      }
   }

   @Override
   public void a(ItemStack itemstack, @Nullable World world, List<IChatBaseComponent> list, TooltipFlag tooltipflag) {
      Integer integer = d(itemstack);
      WorldMap worldmap = world == null ? null : a(integer, world);
      NBTTagCompound nbttagcompound = itemstack.u();
      boolean flag;
      byte b0;
      if (nbttagcompound != null) {
         flag = nbttagcompound.q("map_to_lock");
         b0 = nbttagcompound.f("map_scale_direction");
      } else {
         flag = false;
         b0 = 0;
      }

      if (worldmap != null && (worldmap.h || flag)) {
         list.add(IChatBaseComponent.a("filled_map.locked", integer).a(EnumChatFormat.h));
      }

      if (tooltipflag.a()) {
         if (worldmap != null) {
            if (!flag && b0 == 0) {
               list.add(IChatBaseComponent.a("filled_map.id", integer).a(EnumChatFormat.h));
            }

            int i = Math.min(worldmap.f + b0, 4);
            list.add(IChatBaseComponent.a("filled_map.scale", 1 << i).a(EnumChatFormat.h));
            list.add(IChatBaseComponent.a("filled_map.level", i, 4).a(EnumChatFormat.h));
         } else {
            list.add(IChatBaseComponent.c("filled_map.unknown").a(EnumChatFormat.h));
         }
      }
   }

   public static int k(ItemStack itemstack) {
      NBTTagCompound nbttagcompound = itemstack.b("display");
      if (nbttagcompound != null && nbttagcompound.b("MapColor", 99)) {
         int i = nbttagcompound.h("MapColor");
         return 0xFF000000 | i & 16777215;
      } else {
         return -12173266;
      }
   }

   @Override
   public EnumInteractionResult a(ItemActionContext itemactioncontext) {
      IBlockData iblockdata = itemactioncontext.q().a_(itemactioncontext.a());
      if (iblockdata.a(TagsBlock.F)) {
         if (!itemactioncontext.q().B) {
            WorldMap worldmap = a(itemactioncontext.n(), itemactioncontext.q());
            if (worldmap != null && !worldmap.a(itemactioncontext.q(), itemactioncontext.a())) {
               return EnumInteractionResult.e;
            }
         }

         return EnumInteractionResult.a(itemactioncontext.q().B);
      } else {
         return super.a(itemactioncontext);
      }
   }
}
