package net.minecraft.world.entity.npc;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.StructureTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectList;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.EnumColor;
import net.minecraft.world.item.IDyeable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemArmorColorable;
import net.minecraft.world.item.ItemDye;
import net.minecraft.world.item.ItemEnchantedBook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemSuspiciousStew;
import net.minecraft.world.item.ItemWorldMap;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewer;
import net.minecraft.world.item.alchemy.PotionRegistry;
import net.minecraft.world.item.alchemy.PotionUtil;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentManager;
import net.minecraft.world.item.enchantment.WeightedRandomEnchant;
import net.minecraft.world.item.trading.MerchantRecipe;
import net.minecraft.world.level.IMaterial;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.saveddata.maps.MapIcon;
import net.minecraft.world.level.saveddata.maps.WorldMap;

public class VillagerTrades {
   private static final int d = 12;
   private static final int e = 16;
   private static final int f = 3;
   private static final int g = 1;
   private static final int h = 2;
   private static final int i = 5;
   private static final int j = 10;
   private static final int k = 10;
   private static final int l = 20;
   private static final int m = 15;
   private static final int n = 30;
   private static final int o = 30;
   private static final float p = 0.05F;
   private static final float q = 0.2F;
   public static final Map<VillagerProfession, Int2ObjectMap<VillagerTrades.IMerchantRecipeOption[]>> a = SystemUtils.a(
      Maps.newHashMap(),
      var0 -> {
         var0.put(
            VillagerProfession.g,
            a(
               ImmutableMap.of(
                  1,
                  new VillagerTrades.IMerchantRecipeOption[]{
                     new VillagerTrades.b(Items.oE, 20, 16, 2),
                     new VillagerTrades.b(Items.ti, 26, 16, 2),
                     new VillagerTrades.b(Items.th, 22, 16, 2),
                     new VillagerTrades.b(Items.ul, 15, 16, 2),
                     new VillagerTrades.h(Items.oF, 1, 6, 16, 1)
                  },
                  2,
                  new VillagerTrades.IMerchantRecipeOption[]{
                     new VillagerTrades.b(Blocks.dU, 6, 12, 10), new VillagerTrades.h(Items.tv, 1, 4, 5), new VillagerTrades.h(Items.nB, 1, 4, 16, 5)
                  },
                  3,
                  new VillagerTrades.IMerchantRecipeOption[]{new VillagerTrades.h(Items.ra, 3, 18, 10), new VillagerTrades.b(Blocks.eZ, 4, 12, 20)},
                  4,
                  new VillagerTrades.IMerchantRecipeOption[]{
                     new VillagerTrades.h(Blocks.eg, 1, 1, 12, 15),
                     new VillagerTrades.i(MobEffects.p, 100, 15),
                     new VillagerTrades.i(MobEffects.h, 160, 15),
                     new VillagerTrades.i(MobEffects.r, 140, 15),
                     new VillagerTrades.i(MobEffects.o, 120, 15),
                     new VillagerTrades.i(MobEffects.s, 280, 15),
                     new VillagerTrades.i(MobEffects.w, 7, 15)
                  },
                  5,
                  new VillagerTrades.IMerchantRecipeOption[]{new VillagerTrades.h(Items.tm, 3, 3, 30), new VillagerTrades.h(Items.rA, 4, 3, 30)}
               )
            )
         );
         var0.put(
            VillagerProfession.h,
            a(
               ImmutableMap.of(
                  1,
                  new VillagerTrades.IMerchantRecipeOption[]{
                     new VillagerTrades.b(Items.oA, 20, 16, 2),
                     new VillagerTrades.b(Items.nE, 10, 16, 2),
                     new VillagerTrades.g(Items.qh, 6, Items.ql, 6, 16, 1),
                     new VillagerTrades.h(Items.pP, 3, 1, 16, 1)
                  },
                  2,
                  new VillagerTrades.IMerchantRecipeOption[]{
                     new VillagerTrades.b(Items.qh, 15, 16, 10),
                     new VillagerTrades.g(Items.qi, 6, Items.qm, 6, 16, 5),
                     new VillagerTrades.h(Items.vr, 2, 1, 5)
                  },
                  3,
                  new VillagerTrades.IMerchantRecipeOption[]{new VillagerTrades.b(Items.qi, 13, 16, 20), new VillagerTrades.e(Items.qd, 3, 3, 10, 0.2F)},
                  4,
                  new VillagerTrades.IMerchantRecipeOption[]{new VillagerTrades.b(Items.qj, 6, 12, 30)},
                  5,
                  new VillagerTrades.IMerchantRecipeOption[]{
                     new VillagerTrades.b(Items.qk, 4, 12, 30),
                     new VillagerTrades.c(
                        1,
                        12,
                        30,
                        ImmutableMap.builder()
                           .put(VillagerType.c, Items.ne)
                           .put(VillagerType.g, Items.ng)
                           .put(VillagerType.e, Items.ng)
                           .put(VillagerType.a, Items.nk)
                           .put(VillagerType.b, Items.nk)
                           .put(VillagerType.d, Items.nm)
                           .put(VillagerType.f, Items.nq)
                           .build()
                     )
                  }
               )
            )
         );
         var0.put(
            VillagerProfession.n,
            a(
               ImmutableMap.of(
                  1,
                  new VillagerTrades.IMerchantRecipeOption[]{
                     new VillagerTrades.b(Blocks.bz, 18, 16, 2),
                     new VillagerTrades.b(Blocks.bL, 18, 16, 2),
                     new VillagerTrades.b(Blocks.bO, 18, 16, 2),
                     new VillagerTrades.b(Blocks.bG, 18, 16, 2),
                     new VillagerTrades.h(Items.rc, 2, 1, 1)
                  },
                  2,
                  new VillagerTrades.IMerchantRecipeOption[]{
                     new VillagerTrades.b(Items.qq, 12, 16, 10),
                     new VillagerTrades.b(Items.qx, 12, 16, 10),
                     new VillagerTrades.b(Items.qF, 12, 16, 10),
                     new VillagerTrades.b(Items.qt, 12, 16, 10),
                     new VillagerTrades.b(Items.qv, 12, 16, 10),
                     new VillagerTrades.h(Blocks.bz, 1, 1, 16, 5),
                     new VillagerTrades.h(Blocks.bA, 1, 1, 16, 5),
                     new VillagerTrades.h(Blocks.bB, 1, 1, 16, 5),
                     new VillagerTrades.h(Blocks.bC, 1, 1, 16, 5),
                     new VillagerTrades.h(Blocks.bD, 1, 1, 16, 5),
                     new VillagerTrades.h(Blocks.bE, 1, 1, 16, 5),
                     new VillagerTrades.h(Blocks.bF, 1, 1, 16, 5),
                     new VillagerTrades.h(Blocks.bG, 1, 1, 16, 5),
                     new VillagerTrades.h(Blocks.bH, 1, 1, 16, 5),
                     new VillagerTrades.h(Blocks.bI, 1, 1, 16, 5),
                     new VillagerTrades.h(Blocks.bJ, 1, 1, 16, 5),
                     new VillagerTrades.h(Blocks.bK, 1, 1, 16, 5),
                     new VillagerTrades.h(Blocks.bL, 1, 1, 16, 5),
                     new VillagerTrades.h(Blocks.bM, 1, 1, 16, 5),
                     new VillagerTrades.h(Blocks.bN, 1, 1, 16, 5),
                     new VillagerTrades.h(Blocks.bO, 1, 1, 16, 5),
                     new VillagerTrades.h(Blocks.ij, 1, 4, 16, 5),
                     new VillagerTrades.h(Blocks.ik, 1, 4, 16, 5),
                     new VillagerTrades.h(Blocks.il, 1, 4, 16, 5),
                     new VillagerTrades.h(Blocks.im, 1, 4, 16, 5),
                     new VillagerTrades.h(Blocks.in, 1, 4, 16, 5),
                     new VillagerTrades.h(Blocks.io, 1, 4, 16, 5),
                     new VillagerTrades.h(Blocks.ip, 1, 4, 16, 5),
                     new VillagerTrades.h(Blocks.iq, 1, 4, 16, 5),
                     new VillagerTrades.h(Blocks.ir, 1, 4, 16, 5),
                     new VillagerTrades.h(Blocks.is, 1, 4, 16, 5),
                     new VillagerTrades.h(Blocks.it, 1, 4, 16, 5),
                     new VillagerTrades.h(Blocks.iu, 1, 4, 16, 5),
                     new VillagerTrades.h(Blocks.iv, 1, 4, 16, 5),
                     new VillagerTrades.h(Blocks.iw, 1, 4, 16, 5),
                     new VillagerTrades.h(Blocks.ix, 1, 4, 16, 5),
                     new VillagerTrades.h(Blocks.iy, 1, 4, 16, 5)
                  },
                  3,
                  new VillagerTrades.IMerchantRecipeOption[]{
                     new VillagerTrades.b(Items.qu, 12, 16, 20),
                     new VillagerTrades.b(Items.qy, 12, 16, 20),
                     new VillagerTrades.b(Items.qr, 12, 16, 20),
                     new VillagerTrades.b(Items.qE, 12, 16, 20),
                     new VillagerTrades.b(Items.qw, 12, 16, 20),
                     new VillagerTrades.h(Blocks.aY, 3, 1, 12, 10),
                     new VillagerTrades.h(Blocks.bc, 3, 1, 12, 10),
                     new VillagerTrades.h(Blocks.bm, 3, 1, 12, 10),
                     new VillagerTrades.h(Blocks.bn, 3, 1, 12, 10),
                     new VillagerTrades.h(Blocks.bj, 3, 1, 12, 10),
                     new VillagerTrades.h(Blocks.bk, 3, 1, 12, 10),
                     new VillagerTrades.h(Blocks.bh, 3, 1, 12, 10),
                     new VillagerTrades.h(Blocks.bf, 3, 1, 12, 10),
                     new VillagerTrades.h(Blocks.bl, 3, 1, 12, 10),
                     new VillagerTrades.h(Blocks.bb, 3, 1, 12, 10),
                     new VillagerTrades.h(Blocks.bg, 3, 1, 12, 10),
                     new VillagerTrades.h(Blocks.bd, 3, 1, 12, 10),
                     new VillagerTrades.h(Blocks.ba, 3, 1, 12, 10),
                     new VillagerTrades.h(Blocks.aZ, 3, 1, 12, 10),
                     new VillagerTrades.h(Blocks.be, 3, 1, 12, 10),
                     new VillagerTrades.h(Blocks.bi, 3, 1, 12, 10)
                  },
                  4,
                  new VillagerTrades.IMerchantRecipeOption[]{
                     new VillagerTrades.b(Items.qC, 12, 16, 30),
                     new VillagerTrades.b(Items.qA, 12, 16, 30),
                     new VillagerTrades.b(Items.qB, 12, 16, 30),
                     new VillagerTrades.b(Items.qD, 12, 16, 30),
                     new VillagerTrades.b(Items.qs, 12, 16, 30),
                     new VillagerTrades.b(Items.qz, 12, 16, 30),
                     new VillagerTrades.h(Items.tR, 3, 1, 12, 15),
                     new VillagerTrades.h(Items.uc, 3, 1, 12, 15),
                     new VillagerTrades.h(Items.tU, 3, 1, 12, 15),
                     new VillagerTrades.h(Items.uf, 3, 1, 12, 15),
                     new VillagerTrades.h(Items.tX, 3, 1, 12, 15),
                     new VillagerTrades.h(Items.ue, 3, 1, 12, 15),
                     new VillagerTrades.h(Items.tW, 3, 1, 12, 15),
                     new VillagerTrades.h(Items.tY, 3, 1, 12, 15),
                     new VillagerTrades.h(Items.ug, 3, 1, 12, 15),
                     new VillagerTrades.h(Items.ub, 3, 1, 12, 15),
                     new VillagerTrades.h(Items.tT, 3, 1, 12, 15),
                     new VillagerTrades.h(Items.ua, 3, 1, 12, 15),
                     new VillagerTrades.h(Items.ud, 3, 1, 12, 15),
                     new VillagerTrades.h(Items.tV, 3, 1, 12, 15),
                     new VillagerTrades.h(Items.tS, 3, 1, 12, 15),
                     new VillagerTrades.h(Items.tZ, 3, 1, 12, 15)
                  },
                  5,
                  new VillagerTrades.IMerchantRecipeOption[]{new VillagerTrades.h(Items.ph, 2, 3, 30)}
               )
            )
         );
         var0.put(
            VillagerProfession.i,
            a(
               ImmutableMap.of(
                  1,
                  new VillagerTrades.IMerchantRecipeOption[]{
                     new VillagerTrades.b(Items.ox, 32, 16, 2),
                     new VillagerTrades.h(Items.nD, 1, 16, 1),
                     new VillagerTrades.g(Blocks.L, 10, Items.pe, 10, 12, 1)
                  },
                  2,
                  new VillagerTrades.IMerchantRecipeOption[]{new VillagerTrades.b(Items.pe, 26, 12, 10), new VillagerTrades.h(Items.nC, 2, 1, 5)},
                  3,
                  new VillagerTrades.IMerchantRecipeOption[]{new VillagerTrades.b(Items.oA, 14, 16, 20), new VillagerTrades.h(Items.uT, 3, 1, 10)},
                  4,
                  new VillagerTrades.IMerchantRecipeOption[]{new VillagerTrades.b(Items.oB, 24, 16, 30), new VillagerTrades.e(Items.nC, 2, 3, 15)},
                  5,
                  new VillagerTrades.IMerchantRecipeOption[]{
                     new VillagerTrades.b(Items.lB, 8, 12, 30),
                     new VillagerTrades.e(Items.uT, 3, 3, 15),
                     new VillagerTrades.j(Items.nD, 5, Items.ur, 5, 2, 12, 30)
                  }
               )
            )
         );
         var0.put(
            VillagerProfession.k,
            a(
               ImmutableMap.builder()
                  .put(
                     1,
                     new VillagerTrades.IMerchantRecipeOption[]{
                        new VillagerTrades.b(Items.pW, 24, 16, 2), new VillagerTrades.d(1), new VillagerTrades.h(Blocks.ck, 9, 1, 12, 1)
                     }
                  )
                  .put(
                     2,
                     new VillagerTrades.IMerchantRecipeOption[]{
                        new VillagerTrades.b(Items.pX, 4, 12, 10), new VillagerTrades.d(5), new VillagerTrades.h(Items.vn, 1, 1, 5)
                     }
                  )
                  .put(
                     3,
                     new VillagerTrades.IMerchantRecipeOption[]{
                        new VillagerTrades.b(Items.qn, 5, 12, 20), new VillagerTrades.d(10), new VillagerTrades.h(Items.cj, 1, 4, 10)
                     }
                  )
                  .put(
                     4,
                     new VillagerTrades.IMerchantRecipeOption[]{
                        new VillagerTrades.b(Items.tc, 2, 12, 30),
                        new VillagerTrades.d(15),
                        new VillagerTrades.h(Items.qe, 5, 1, 15),
                        new VillagerTrades.h(Items.qa, 4, 1, 15)
                     }
                  )
                  .put(5, new VillagerTrades.IMerchantRecipeOption[]{new VillagerTrades.h(Items.tN, 20, 1, 30)})
                  .build()
            )
         );
         var0.put(
            VillagerProfession.e,
            a(
               ImmutableMap.of(
                  1,
                  new VillagerTrades.IMerchantRecipeOption[]{new VillagerTrades.b(Items.pW, 24, 16, 2), new VillagerTrades.h(Items.tl, 7, 1, 1)},
                  2,
                  new VillagerTrades.IMerchantRecipeOption[]{
                     new VillagerTrades.b(Items.fv, 11, 16, 10), new VillagerTrades.k(13, StructureTags.d, "filled_map.monument", MapIcon.Type.j, 12, 5)
                  },
                  3,
                  new VillagerTrades.IMerchantRecipeOption[]{
                     new VillagerTrades.b(Items.qa, 1, 12, 20), new VillagerTrades.k(14, StructureTags.c, "filled_map.mansion", MapIcon.Type.i, 12, 10)
                  },
                  4,
                  new VillagerTrades.IMerchantRecipeOption[]{
                     new VillagerTrades.h(Items.te, 7, 1, 15),
                     new VillagerTrades.h(Items.tR, 3, 1, 15),
                     new VillagerTrades.h(Items.uc, 3, 1, 15),
                     new VillagerTrades.h(Items.tU, 3, 1, 15),
                     new VillagerTrades.h(Items.uf, 3, 1, 15),
                     new VillagerTrades.h(Items.tX, 3, 1, 15),
                     new VillagerTrades.h(Items.ue, 3, 1, 15),
                     new VillagerTrades.h(Items.tW, 3, 1, 15),
                     new VillagerTrades.h(Items.tY, 3, 1, 15),
                     new VillagerTrades.h(Items.ug, 3, 1, 15),
                     new VillagerTrades.h(Items.ub, 3, 1, 15),
                     new VillagerTrades.h(Items.tT, 3, 1, 15),
                     new VillagerTrades.h(Items.ua, 3, 1, 15),
                     new VillagerTrades.h(Items.ud, 3, 1, 15),
                     new VillagerTrades.h(Items.tV, 3, 1, 15),
                     new VillagerTrades.h(Items.tS, 3, 1, 15),
                     new VillagerTrades.h(Items.tZ, 3, 1, 15)
                  },
                  5,
                  new VillagerTrades.IMerchantRecipeOption[]{new VillagerTrades.h(Items.va, 8, 1, 30)}
               )
            )
         );
         var0.put(
            VillagerProfession.f,
            a(
               ImmutableMap.of(
                  1,
                  new VillagerTrades.IMerchantRecipeOption[]{new VillagerTrades.b(Items.rl, 32, 16, 2), new VillagerTrades.h(Items.li, 1, 2, 1)},
                  2,
                  new VillagerTrades.IMerchantRecipeOption[]{new VillagerTrades.b(Items.nQ, 3, 12, 10), new VillagerTrades.h(Items.nI, 1, 1, 5)},
                  3,
                  new VillagerTrades.IMerchantRecipeOption[]{new VillagerTrades.b(Items.tF, 2, 12, 20), new VillagerTrades.h(Blocks.ec, 4, 1, 12, 10)},
                  4,
                  new VillagerTrades.IMerchantRecipeOption[]{
                     new VillagerTrades.b(Items.nz, 4, 12, 30), new VillagerTrades.b(Items.rs, 9, 12, 30), new VillagerTrades.h(Items.rm, 5, 1, 15)
                  },
                  5,
                  new VillagerTrades.IMerchantRecipeOption[]{new VillagerTrades.b(Items.rq, 22, 12, 30), new VillagerTrades.h(Items.ta, 3, 1, 30)}
               )
            )
         );
         var0.put(
            VillagerProfession.c,
            a(
               ImmutableMap.of(
                  1,
                  new VillagerTrades.IMerchantRecipeOption[]{
                     new VillagerTrades.b(Items.nE, 15, 16, 2),
                     new VillagerTrades.h(new ItemStack(Items.oQ), 7, 1, 12, 1, 0.2F),
                     new VillagerTrades.h(new ItemStack(Items.oR), 4, 1, 12, 1, 0.2F),
                     new VillagerTrades.h(new ItemStack(Items.oO), 5, 1, 12, 1, 0.2F),
                     new VillagerTrades.h(new ItemStack(Items.oP), 9, 1, 12, 1, 0.2F)
                  },
                  2,
                  new VillagerTrades.IMerchantRecipeOption[]{
                     new VillagerTrades.b(Items.nM, 4, 12, 10),
                     new VillagerTrades.h(new ItemStack(Items.vm), 36, 1, 12, 5, 0.2F),
                     new VillagerTrades.h(new ItemStack(Items.oN), 1, 1, 12, 5, 0.2F),
                     new VillagerTrades.h(new ItemStack(Items.oM), 3, 1, 12, 5, 0.2F)
                  },
                  3,
                  new VillagerTrades.IMerchantRecipeOption[]{
                     new VillagerTrades.b(Items.pI, 1, 12, 20),
                     new VillagerTrades.b(Items.nG, 1, 12, 20),
                     new VillagerTrades.h(new ItemStack(Items.oK), 1, 1, 12, 10, 0.2F),
                     new VillagerTrades.h(new ItemStack(Items.oL), 4, 1, 12, 10, 0.2F),
                     new VillagerTrades.h(new ItemStack(Items.ut), 5, 1, 12, 10, 0.2F)
                  },
                  4,
                  new VillagerTrades.IMerchantRecipeOption[]{new VillagerTrades.e(Items.oU, 14, 3, 15, 0.2F), new VillagerTrades.e(Items.oV, 8, 3, 15, 0.2F)},
                  5,
                  new VillagerTrades.IMerchantRecipeOption[]{new VillagerTrades.e(Items.oS, 8, 3, 30, 0.2F), new VillagerTrades.e(Items.oT, 16, 3, 30, 0.2F)}
               )
            )
         );
         var0.put(
            VillagerProfession.p,
            a(
               ImmutableMap.of(
                  1,
                  new VillagerTrades.IMerchantRecipeOption[]{
                     new VillagerTrades.b(Items.nE, 15, 16, 2),
                     new VillagerTrades.h(new ItemStack(Items.ol), 3, 1, 12, 1, 0.2F),
                     new VillagerTrades.e(Items.oi, 2, 3, 1)
                  },
                  2,
                  new VillagerTrades.IMerchantRecipeOption[]{
                     new VillagerTrades.b(Items.nM, 4, 12, 10), new VillagerTrades.h(new ItemStack(Items.vm), 36, 1, 12, 5, 0.2F)
                  },
                  3,
                  new VillagerTrades.IMerchantRecipeOption[]{new VillagerTrades.b(Items.pe, 24, 12, 20)},
                  4,
                  new VillagerTrades.IMerchantRecipeOption[]{new VillagerTrades.b(Items.nG, 1, 12, 30), new VillagerTrades.e(Items.oq, 12, 3, 15, 0.2F)},
                  5,
                  new VillagerTrades.IMerchantRecipeOption[]{new VillagerTrades.e(Items.on, 8, 3, 30, 0.2F)}
               )
            )
         );
         var0.put(
            VillagerProfession.o,
            a(
               ImmutableMap.of(
                  1,
                  new VillagerTrades.IMerchantRecipeOption[]{
                     new VillagerTrades.b(Items.nE, 15, 16, 2),
                     new VillagerTrades.h(new ItemStack(Items.ob), 1, 1, 12, 1, 0.2F),
                     new VillagerTrades.h(new ItemStack(Items.nZ), 1, 1, 12, 1, 0.2F),
                     new VillagerTrades.h(new ItemStack(Items.oa), 1, 1, 12, 1, 0.2F),
                     new VillagerTrades.h(new ItemStack(Items.oc), 1, 1, 12, 1, 0.2F)
                  },
                  2,
                  new VillagerTrades.IMerchantRecipeOption[]{
                     new VillagerTrades.b(Items.nM, 4, 12, 10), new VillagerTrades.h(new ItemStack(Items.vm), 36, 1, 12, 5, 0.2F)
                  },
                  3,
                  new VillagerTrades.IMerchantRecipeOption[]{
                     new VillagerTrades.b(Items.pe, 30, 12, 20),
                     new VillagerTrades.e(Items.ol, 1, 3, 10, 0.2F),
                     new VillagerTrades.e(Items.oj, 2, 3, 10, 0.2F),
                     new VillagerTrades.e(Items.ok, 3, 3, 10, 0.2F),
                     new VillagerTrades.h(new ItemStack(Items.or), 4, 1, 3, 10, 0.2F)
                  },
                  4,
                  new VillagerTrades.IMerchantRecipeOption[]{
                     new VillagerTrades.b(Items.nG, 1, 12, 30),
                     new VillagerTrades.e(Items.oq, 12, 3, 15, 0.2F),
                     new VillagerTrades.e(Items.oo, 5, 3, 15, 0.2F)
                  },
                  5,
                  new VillagerTrades.IMerchantRecipeOption[]{new VillagerTrades.e(Items.op, 13, 3, 30, 0.2F)}
               )
            )
         );
         var0.put(
            VillagerProfession.d,
            a(
               ImmutableMap.of(
                  1,
                  new VillagerTrades.IMerchantRecipeOption[]{
                     new VillagerTrades.b(Items.rj, 14, 16, 2),
                     new VillagerTrades.b(Items.pf, 7, 16, 2),
                     new VillagerTrades.b(Items.tC, 4, 16, 2),
                     new VillagerTrades.h(Items.tE, 1, 1, 1)
                  },
                  2,
                  new VillagerTrades.IMerchantRecipeOption[]{
                     new VillagerTrades.b(Items.nE, 15, 16, 2), new VillagerTrades.h(Items.pg, 1, 5, 16, 5), new VillagerTrades.h(Items.rk, 1, 8, 16, 5)
                  },
                  3,
                  new VillagerTrades.IMerchantRecipeOption[]{new VillagerTrades.b(Items.tP, 7, 16, 20), new VillagerTrades.b(Items.rh, 10, 16, 20)},
                  4,
                  new VillagerTrades.IMerchantRecipeOption[]{new VillagerTrades.b(Items.pV, 10, 12, 30)},
                  5,
                  new VillagerTrades.IMerchantRecipeOption[]{new VillagerTrades.b(Items.vp, 10, 12, 30)}
               )
            )
         );
         var0.put(
            VillagerProfession.j,
            a(
               ImmutableMap.of(
                  1,
                  new VillagerTrades.IMerchantRecipeOption[]{
                     new VillagerTrades.b(Items.pL, 6, 16, 2), new VillagerTrades.a(Items.oI, 3), new VillagerTrades.a(Items.oH, 7)
                  },
                  2,
                  new VillagerTrades.IMerchantRecipeOption[]{
                     new VillagerTrades.b(Items.pe, 26, 12, 10), new VillagerTrades.a(Items.oG, 5, 12, 5), new VillagerTrades.a(Items.oJ, 4, 12, 5)
                  },
                  3,
                  new VillagerTrades.IMerchantRecipeOption[]{new VillagerTrades.b(Items.tG, 9, 12, 20), new VillagerTrades.a(Items.oH, 7)},
                  4,
                  new VillagerTrades.IMerchantRecipeOption[]{new VillagerTrades.b(Items.nz, 4, 12, 30), new VillagerTrades.a(Items.tL, 6, 12, 15)},
                  5,
                  new VillagerTrades.IMerchantRecipeOption[]{
                     new VillagerTrades.h(new ItemStack(Items.mV), 6, 1, 12, 30, 0.2F), new VillagerTrades.a(Items.oG, 5, 12, 30)
                  }
               )
            )
         );
         var0.put(
            VillagerProfession.l,
            a(
               ImmutableMap.of(
                  1,
                  new VillagerTrades.IMerchantRecipeOption[]{new VillagerTrades.b(Items.pU, 10, 16, 2), new VillagerTrades.h(Items.pT, 1, 10, 16, 1)},
                  2,
                  new VillagerTrades.IMerchantRecipeOption[]{new VillagerTrades.b(Blocks.b, 20, 16, 10), new VillagerTrades.h(Blocks.eK, 1, 4, 16, 5)},
                  3,
                  new VillagerTrades.IMerchantRecipeOption[]{
                     new VillagerTrades.b(Blocks.c, 16, 16, 20),
                     new VillagerTrades.b(Blocks.g, 16, 16, 20),
                     new VillagerTrades.b(Blocks.e, 16, 16, 20),
                     new VillagerTrades.h(Blocks.ro, 1, 4, 16, 10),
                     new VillagerTrades.h(Blocks.h, 1, 4, 16, 10),
                     new VillagerTrades.h(Blocks.f, 1, 4, 16, 10),
                     new VillagerTrades.h(Blocks.d, 1, 4, 16, 10)
                  },
                  4,
                  new VillagerTrades.IMerchantRecipeOption[]{
                     new VillagerTrades.b(Items.nJ, 12, 12, 30),
                     new VillagerTrades.h(Blocks.hj, 1, 1, 12, 15),
                     new VillagerTrades.h(Blocks.hi, 1, 1, 12, 15),
                     new VillagerTrades.h(Blocks.ht, 1, 1, 12, 15),
                     new VillagerTrades.h(Blocks.hl, 1, 1, 12, 15),
                     new VillagerTrades.h(Blocks.hp, 1, 1, 12, 15),
                     new VillagerTrades.h(Blocks.hq, 1, 1, 12, 15),
                     new VillagerTrades.h(Blocks.hx, 1, 1, 12, 15),
                     new VillagerTrades.h(Blocks.hw, 1, 1, 12, 15),
                     new VillagerTrades.h(Blocks.ho, 1, 1, 12, 15),
                     new VillagerTrades.h(Blocks.hk, 1, 1, 12, 15),
                     new VillagerTrades.h(Blocks.hn, 1, 1, 12, 15),
                     new VillagerTrades.h(Blocks.hv, 1, 1, 12, 15),
                     new VillagerTrades.h(Blocks.hr, 1, 1, 12, 15),
                     new VillagerTrades.h(Blocks.hs, 1, 1, 12, 15),
                     new VillagerTrades.h(Blocks.hm, 1, 1, 12, 15),
                     new VillagerTrades.h(Blocks.hu, 1, 1, 12, 15),
                     new VillagerTrades.h(Blocks.le, 1, 1, 12, 15),
                     new VillagerTrades.h(Blocks.ld, 1, 1, 12, 15),
                     new VillagerTrades.h(Blocks.lo, 1, 1, 12, 15),
                     new VillagerTrades.h(Blocks.lg, 1, 1, 12, 15),
                     new VillagerTrades.h(Blocks.lk, 1, 1, 12, 15),
                     new VillagerTrades.h(Blocks.ll, 1, 1, 12, 15),
                     new VillagerTrades.h(Blocks.ls, 1, 1, 12, 15),
                     new VillagerTrades.h(Blocks.lr, 1, 1, 12, 15),
                     new VillagerTrades.h(Blocks.lj, 1, 1, 12, 15),
                     new VillagerTrades.h(Blocks.lf, 1, 1, 12, 15),
                     new VillagerTrades.h(Blocks.li, 1, 1, 12, 15),
                     new VillagerTrades.h(Blocks.lq, 1, 1, 12, 15),
                     new VillagerTrades.h(Blocks.lm, 1, 1, 12, 15),
                     new VillagerTrades.h(Blocks.ln, 1, 1, 12, 15),
                     new VillagerTrades.h(Blocks.lh, 1, 1, 12, 15),
                     new VillagerTrades.h(Blocks.lp, 1, 1, 12, 15)
                  },
                  5,
                  new VillagerTrades.IMerchantRecipeOption[]{new VillagerTrades.h(Blocks.he, 1, 1, 12, 30), new VillagerTrades.h(Blocks.hc, 1, 1, 12, 30)}
               )
            )
         );
      }
   );
   public static final Int2ObjectMap<VillagerTrades.IMerchantRecipeOption[]> b = a(
      ImmutableMap.of(
         1,
         new VillagerTrades.IMerchantRecipeOption[]{
            new VillagerTrades.h(Items.cw, 2, 1, 5, 1),
            new VillagerTrades.h(Items.pY, 4, 1, 5, 1),
            new VillagerTrades.h(Items.eW, 2, 1, 5, 1),
            new VillagerTrades.h(Items.uR, 5, 1, 5, 1),
            new VillagerTrades.h(Items.cr, 1, 1, 12, 1),
            new VillagerTrades.h(Items.dl, 1, 1, 8, 1),
            new VillagerTrades.h(Items.eM, 1, 1, 4, 1),
            new VillagerTrades.h(Items.dm, 3, 1, 12, 1),
            new VillagerTrades.h(Items.ey, 3, 1, 8, 1),
            new VillagerTrades.h(Items.cN, 1, 1, 12, 1),
            new VillagerTrades.h(Items.cO, 1, 1, 12, 1),
            new VillagerTrades.h(Items.cP, 1, 1, 8, 1),
            new VillagerTrades.h(Items.cQ, 1, 1, 12, 1),
            new VillagerTrades.h(Items.cR, 1, 1, 12, 1),
            new VillagerTrades.h(Items.cS, 1, 1, 12, 1),
            new VillagerTrades.h(Items.cT, 1, 1, 12, 1),
            new VillagerTrades.h(Items.cU, 1, 1, 12, 1),
            new VillagerTrades.h(Items.cV, 1, 1, 12, 1),
            new VillagerTrades.h(Items.cW, 1, 1, 12, 1),
            new VillagerTrades.h(Items.cX, 1, 1, 12, 1),
            new VillagerTrades.h(Items.cY, 1, 1, 7, 1),
            new VillagerTrades.h(Items.oD, 1, 1, 12, 1),
            new VillagerTrades.h(Items.um, 1, 1, 12, 1),
            new VillagerTrades.h(Items.rf, 1, 1, 12, 1),
            new VillagerTrades.h(Items.rg, 1, 1, 12, 1),
            new VillagerTrades.h(Items.N, 5, 1, 8, 1),
            new VillagerTrades.h(Items.L, 5, 1, 8, 1),
            new VillagerTrades.h(Items.P, 5, 1, 8, 1),
            new VillagerTrades.h(Items.M, 5, 1, 8, 1),
            new VillagerTrades.h(Items.J, 5, 1, 8, 1),
            new VillagerTrades.h(Items.K, 5, 1, 8, 1),
            new VillagerTrades.h(Items.Q, 5, 1, 8, 1),
            new VillagerTrades.h(Items.qE, 1, 3, 12, 1),
            new VillagerTrades.h(Items.qq, 1, 3, 12, 1),
            new VillagerTrades.h(Items.qB, 1, 3, 12, 1),
            new VillagerTrades.h(Items.qw, 1, 3, 12, 1),
            new VillagerTrades.h(Items.qF, 1, 3, 12, 1),
            new VillagerTrades.h(Items.qD, 1, 3, 12, 1),
            new VillagerTrades.h(Items.qy, 1, 3, 12, 1),
            new VillagerTrades.h(Items.qs, 1, 3, 12, 1),
            new VillagerTrades.h(Items.qu, 1, 3, 12, 1),
            new VillagerTrades.h(Items.qx, 1, 3, 12, 1),
            new VillagerTrades.h(Items.qA, 1, 3, 12, 1),
            new VillagerTrades.h(Items.qt, 1, 3, 12, 1),
            new VillagerTrades.h(Items.qv, 1, 3, 12, 1),
            new VillagerTrades.h(Items.qr, 1, 3, 12, 1),
            new VillagerTrades.h(Items.qC, 1, 3, 12, 1),
            new VillagerTrades.h(Items.qz, 1, 3, 12, 1),
            new VillagerTrades.h(Items.jY, 3, 1, 8, 1),
            new VillagerTrades.h(Items.jZ, 3, 1, 8, 1),
            new VillagerTrades.h(Items.ka, 3, 1, 8, 1),
            new VillagerTrades.h(Items.kb, 3, 1, 8, 1),
            new VillagerTrades.h(Items.jX, 3, 1, 8, 1),
            new VillagerTrades.h(Items.fx, 1, 1, 12, 1),
            new VillagerTrades.h(Items.dc, 1, 1, 12, 1),
            new VillagerTrades.h(Items.dd, 1, 1, 12, 1),
            new VillagerTrades.h(Items.fD, 1, 2, 5, 1),
            new VillagerTrades.h(Items.ds, 1, 2, 5, 1),
            new VillagerTrades.h(Items.S, 1, 8, 8, 1),
            new VillagerTrades.h(Items.U, 1, 4, 6, 1),
            new VillagerTrades.h(Items.wj, 1, 2, 5, 1),
            new VillagerTrades.h(Items.s, 1, 2, 5, 1),
            new VillagerTrades.h(Items.dp, 1, 2, 5, 1)
         },
         2,
         new VillagerTrades.IMerchantRecipeOption[]{
            new VillagerTrades.h(Items.pQ, 5, 1, 4, 1),
            new VillagerTrades.h(Items.pN, 5, 1, 4, 1),
            new VillagerTrades.h(Items.hx, 3, 1, 6, 1),
            new VillagerTrades.h(Items.kw, 6, 1, 6, 1),
            new VillagerTrades.h(Items.oC, 1, 1, 8, 1),
            new VillagerTrades.h(Items.r, 3, 3, 6, 1)
         }
      )
   );
   public static final Int2ObjectMap<VillagerTrades.IMerchantRecipeOption[]> c = a(
      ImmutableMap.of(1, new VillagerTrades.IMerchantRecipeOption[]{new VillagerTrades.h(Items.O, 5, 1, 8, 1)})
   );

   private static Int2ObjectMap<VillagerTrades.IMerchantRecipeOption[]> a(ImmutableMap<Integer, VillagerTrades.IMerchantRecipeOption[]> var0) {
      return new Int2ObjectOpenHashMap(var0);
   }

   public interface IMerchantRecipeOption {
      @Nullable
      MerchantRecipe a(Entity var1, RandomSource var2);
   }

   static class a implements VillagerTrades.IMerchantRecipeOption {
      private final Item a;
      private final int b;
      private final int c;
      private final int d;

      public a(Item var0, int var1) {
         this(var0, var1, 12, 1);
      }

      public a(Item var0, int var1, int var2, int var3) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
         this.d = var3;
      }

      @Override
      public MerchantRecipe a(Entity var0, RandomSource var1) {
         ItemStack var2 = new ItemStack(Items.nH, this.b);
         ItemStack var3 = new ItemStack(this.a);
         if (this.a instanceof ItemArmorColorable) {
            List<ItemDye> var4 = Lists.newArrayList();
            var4.add(a(var1));
            if (var1.i() > 0.7F) {
               var4.add(a(var1));
            }

            if (var1.i() > 0.8F) {
               var4.add(a(var1));
            }

            var3 = IDyeable.a(var3, var4);
         }

         return new MerchantRecipe(var2, var3, this.c, this.d, 0.2F);
      }

      private static ItemDye a(RandomSource var0) {
         return ItemDye.a(EnumColor.a(var0.a(16)));
      }
   }

   static class b implements VillagerTrades.IMerchantRecipeOption {
      private final Item a;
      private final int b;
      private final int c;
      private final int d;
      private final float e;

      public b(IMaterial var0, int var1, int var2, int var3) {
         this.a = var0.k();
         this.b = var1;
         this.c = var2;
         this.d = var3;
         this.e = 0.05F;
      }

      @Override
      public MerchantRecipe a(Entity var0, RandomSource var1) {
         ItemStack var2 = new ItemStack(this.a, this.b);
         return new MerchantRecipe(var2, new ItemStack(Items.nH), this.c, this.d, this.e);
      }
   }

   static class c implements VillagerTrades.IMerchantRecipeOption {
      private final Map<VillagerType, Item> a;
      private final int b;
      private final int c;
      private final int d;

      public c(int var0, int var1, int var2, Map<VillagerType, Item> var3) {
         BuiltInRegistries.y.s().filter(var1x -> !var3.containsKey(var1x)).findAny().ifPresent(var0x -> {
            throw new IllegalStateException("Missing trade for villager type: " + BuiltInRegistries.y.b(var0x));
         });
         this.a = var3;
         this.b = var0;
         this.c = var1;
         this.d = var2;
      }

      @Nullable
      @Override
      public MerchantRecipe a(Entity var0, RandomSource var1) {
         if (var0 instanceof VillagerDataHolder) {
            ItemStack var2 = new ItemStack(this.a.get(((VillagerDataHolder)var0).gd().a()), this.b);
            return new MerchantRecipe(var2, new ItemStack(Items.nH), this.c, this.d, 0.05F);
         } else {
            return null;
         }
      }
   }

   static class d implements VillagerTrades.IMerchantRecipeOption {
      private final int a;

      public d(int var0) {
         this.a = var0;
      }

      @Override
      public MerchantRecipe a(Entity var0, RandomSource var1) {
         List<Enchantment> var2 = BuiltInRegistries.g.s().filter(Enchantment::h).collect(Collectors.toList());
         Enchantment var3 = var2.get(var1.a(var2.size()));
         int var4 = MathHelper.a(var1, var3.e(), var3.a());
         ItemStack var5 = ItemEnchantedBook.a(new WeightedRandomEnchant(var3, var4));
         int var6 = 2 + var1.a(5 + var4 * 10) + 3 * var4;
         if (var3.b()) {
            var6 *= 2;
         }

         if (var6 > 64) {
            var6 = 64;
         }

         return new MerchantRecipe(new ItemStack(Items.nH, var6), new ItemStack(Items.pX), var5, 12, this.a, 0.2F);
      }
   }

   static class e implements VillagerTrades.IMerchantRecipeOption {
      private final ItemStack a;
      private final int b;
      private final int c;
      private final int d;
      private final float e;

      public e(Item var0, int var1, int var2, int var3) {
         this(var0, var1, var2, var3, 0.05F);
      }

      public e(Item var0, int var1, int var2, int var3, float var4) {
         this.a = new ItemStack(var0);
         this.b = var1;
         this.c = var2;
         this.d = var3;
         this.e = var4;
      }

      @Override
      public MerchantRecipe a(Entity var0, RandomSource var1) {
         int var2 = 5 + var1.a(15);
         ItemStack var3 = EnchantmentManager.a(var1, new ItemStack(this.a.c()), var2, false);
         int var4 = Math.min(this.b + var2, 64);
         ItemStack var5 = new ItemStack(Items.nH, var4);
         return new MerchantRecipe(var5, var3, this.c, this.d, this.e);
      }
   }

   static class g implements VillagerTrades.IMerchantRecipeOption {
      private final ItemStack a;
      private final int b;
      private final int c;
      private final ItemStack d;
      private final int e;
      private final int f;
      private final int g;
      private final float h;

      public g(IMaterial var0, int var1, Item var2, int var3, int var4, int var5) {
         this(var0, var1, 1, var2, var3, var4, var5);
      }

      public g(IMaterial var0, int var1, int var2, Item var3, int var4, int var5, int var6) {
         this.a = new ItemStack(var0);
         this.b = var1;
         this.c = var2;
         this.d = new ItemStack(var3);
         this.e = var4;
         this.f = var5;
         this.g = var6;
         this.h = 0.05F;
      }

      @Nullable
      @Override
      public MerchantRecipe a(Entity var0, RandomSource var1) {
         return new MerchantRecipe(
            new ItemStack(Items.nH, this.c), new ItemStack(this.a.c(), this.b), new ItemStack(this.d.c(), this.e), this.f, this.g, this.h
         );
      }
   }

   static class h implements VillagerTrades.IMerchantRecipeOption {
      private final ItemStack a;
      private final int b;
      private final int c;
      private final int d;
      private final int e;
      private final float f;

      public h(Block var0, int var1, int var2, int var3, int var4) {
         this(new ItemStack(var0), var1, var2, var3, var4);
      }

      public h(Item var0, int var1, int var2, int var3) {
         this(new ItemStack(var0), var1, var2, 12, var3);
      }

      public h(Item var0, int var1, int var2, int var3, int var4) {
         this(new ItemStack(var0), var1, var2, var3, var4);
      }

      public h(ItemStack var0, int var1, int var2, int var3, int var4) {
         this(var0, var1, var2, var3, var4, 0.05F);
      }

      public h(ItemStack var0, int var1, int var2, int var3, int var4, float var5) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
         this.d = var3;
         this.e = var4;
         this.f = var5;
      }

      @Override
      public MerchantRecipe a(Entity var0, RandomSource var1) {
         return new MerchantRecipe(new ItemStack(Items.nH, this.b), new ItemStack(this.a.c(), this.c), this.d, this.e, this.f);
      }
   }

   static class i implements VillagerTrades.IMerchantRecipeOption {
      final MobEffectList a;
      final int b;
      final int c;
      private final float d;

      public i(MobEffectList var0, int var1, int var2) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
         this.d = 0.05F;
      }

      @Nullable
      @Override
      public MerchantRecipe a(Entity var0, RandomSource var1) {
         ItemStack var2 = new ItemStack(Items.uU, 1);
         ItemSuspiciousStew.a(var2, this.a, this.b);
         return new MerchantRecipe(new ItemStack(Items.nH, 1), var2, 12, this.c, this.d);
      }
   }

   static class j implements VillagerTrades.IMerchantRecipeOption {
      private final ItemStack a;
      private final int b;
      private final int c;
      private final int d;
      private final int e;
      private final Item f;
      private final int g;
      private final float h;

      public j(Item var0, int var1, Item var2, int var3, int var4, int var5, int var6) {
         this.a = new ItemStack(var2);
         this.c = var4;
         this.d = var5;
         this.e = var6;
         this.f = var0;
         this.g = var1;
         this.b = var3;
         this.h = 0.05F;
      }

      @Override
      public MerchantRecipe a(Entity var0, RandomSource var1) {
         ItemStack var2 = new ItemStack(Items.nH, this.c);
         List<PotionRegistry> var3 = BuiltInRegistries.j.s().filter(var0x -> !var0x.a().isEmpty() && PotionBrewer.a(var0x)).collect(Collectors.toList());
         PotionRegistry var4 = var3.get(var1.a(var3.size()));
         ItemStack var5 = PotionUtil.a(new ItemStack(this.a.c(), this.b), var4);
         return new MerchantRecipe(var2, new ItemStack(this.f, this.g), var5, this.d, this.e, this.h);
      }
   }

   static class k implements VillagerTrades.IMerchantRecipeOption {
      private final int a;
      private final TagKey<Structure> b;
      private final String c;
      private final MapIcon.Type d;
      private final int e;
      private final int f;

      public k(int var0, TagKey<Structure> var1, String var2, MapIcon.Type var3, int var4, int var5) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
         this.d = var3;
         this.e = var4;
         this.f = var5;
      }

      @Nullable
      @Override
      public MerchantRecipe a(Entity var0, RandomSource var1) {
         if (!(var0.H instanceof WorldServer)) {
            return null;
         } else {
            WorldServer var2 = (WorldServer)var0.H;
            BlockPosition var3 = var2.a(this.b, var0.dg(), 100, true);
            if (var3 != null) {
               ItemStack var4 = ItemWorldMap.a(var2, var3.u(), var3.w(), (byte)2, true, true);
               ItemWorldMap.a(var2, var4);
               WorldMap.a(var4, var3, "+", this.d);
               var4.a(IChatBaseComponent.c(this.c));
               return new MerchantRecipe(new ItemStack(Items.nH, this.a), new ItemStack(Items.qa), var4, this.e, this.f, 0.2F);
            } else {
               return null;
            }
         }
      }
   }
}
