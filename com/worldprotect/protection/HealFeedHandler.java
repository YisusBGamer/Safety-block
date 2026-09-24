/*     */ package com.worldprotect.protection;
/*     */ import com.worldprotect.WorldProtect;
/*     */ import com.worldprotect.flag.Flag;
/*     */ import com.worldprotect.flag.Flags;
/*     */ import com.worldprotect.region.RegionManager;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraftforge.event.TickEvent;
/*     */ import net.minecraftforge.eventbus.api.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
/*     */ 
/*     */ @EventBusSubscriber(modid = "worldprotect")
/*     */ public class HealFeedHandler {
/*  18 */   private static final Map<UUID, Long> lastHeal = new HashMap<>();
/*  19 */   private static final Map<UUID, Long> lastFeed = new HashMap<>();
/*     */   @SubscribeEvent
/*     */   public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
/*     */     ServerPlayer player;
/*  23 */     if (event.phase != TickEvent.Phase.END)
/*  24 */       return;  Player player1 = event.player; if (player1 instanceof ServerPlayer) { player = (ServerPlayer)player1; } else { return; }
/*  25 */      if (player.m_9236_().m_5776_())
/*     */       return; 
/*  27 */     RegionManager manager = WorldProtect.getInstance().getRegionManager((Level)player.m_284548_());
/*  28 */     if (manager == null)
/*     */       return; 
/*  30 */     RegionManager.ApplicableRegionSet regions = manager.getApplicableRegionSet(player.m_20183_());
/*     */     
/*  32 */     handleHealing(player, regions);
/*  33 */     handleFeeding(player, regions);
/*     */   }
/*     */   
/*     */   private static void handleHealing(ServerPlayer player, RegionManager.ApplicableRegionSet regions) {
/*  37 */     Integer healAmount = (Integer)regions.queryValue((Player)player, (Flag)Flags.HEAL_AMOUNT);
/*  38 */     if (healAmount == null || healAmount.intValue() == 0)
/*     */       return; 
/*  40 */     Integer healDelay = (Integer)regions.queryValue((Player)player, (Flag)Flags.HEAL_DELAY);
/*  41 */     if (healDelay == null || healDelay.intValue() <= 0) healDelay = Integer.valueOf(1);
/*     */     
/*  43 */     long delayMillis = healDelay.intValue() * 1000L;
/*  44 */     long now = System.currentTimeMillis();
/*  45 */     UUID uuid = player.m_20148_();
/*     */     
/*  47 */     if (lastHeal.containsKey(uuid) && now - ((Long)lastHeal.get(uuid)).longValue() < delayMillis) {
/*     */       return;
/*     */     }
/*  50 */     lastHeal.put(uuid, Long.valueOf(now));
/*     */     
/*  52 */     Double minHealth = (Double)regions.queryValue((Player)player, (Flag)Flags.MIN_HEAL);
/*  53 */     Double maxHealth = (Double)regions.queryValue((Player)player, (Flag)Flags.MAX_HEAL);
/*     */     
/*  55 */     if (minHealth == null) minHealth = Double.valueOf(0.0D); 
/*  56 */     if (maxHealth == null) maxHealth = Double.valueOf(player.m_21233_());
/*     */     
/*  58 */     float currentHealth = player.m_21223_();
/*     */     
/*  60 */     if (healAmount.intValue() > 0) {
/*  61 */       if (currentHealth < maxHealth.doubleValue()) {
/*  62 */         float newHealth = Math.min(currentHealth + healAmount.intValue(), maxHealth.floatValue());
/*  63 */         player.m_21153_(newHealth);
/*     */       } 
/*  65 */     } else if (healAmount.intValue() < 0 && 
/*  66 */       currentHealth > minHealth.doubleValue()) {
/*  67 */       float newHealth = Math.max(currentHealth + healAmount.intValue(), minHealth.floatValue());
/*  68 */       player.m_21153_(newHealth);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void handleFeeding(ServerPlayer player, RegionManager.ApplicableRegionSet regions) {
/*  74 */     Integer feedAmount = (Integer)regions.queryValue((Player)player, (Flag)Flags.FEED_AMOUNT);
/*  75 */     if (feedAmount == null || feedAmount.intValue() == 0)
/*     */       return; 
/*  77 */     Integer feedDelay = (Integer)regions.queryValue((Player)player, (Flag)Flags.FEED_DELAY);
/*  78 */     if (feedDelay == null || feedDelay.intValue() <= 0) feedDelay = Integer.valueOf(1);
/*     */     
/*  80 */     long delayMillis = feedDelay.intValue() * 1000L;
/*  81 */     long now = System.currentTimeMillis();
/*  82 */     UUID uuid = player.m_20148_();
/*     */     
/*  84 */     if (lastFeed.containsKey(uuid) && now - ((Long)lastFeed.get(uuid)).longValue() < delayMillis) {
/*     */       return;
/*     */     }
/*  87 */     lastFeed.put(uuid, Long.valueOf(now));
/*     */     
/*  89 */     Integer minFood = (Integer)regions.queryValue((Player)player, (Flag)Flags.MIN_FOOD);
/*  90 */     Integer maxFood = (Integer)regions.queryValue((Player)player, (Flag)Flags.MAX_FOOD);
/*     */     
/*  92 */     if (minFood == null) minFood = Integer.valueOf(0); 
/*  93 */     if (maxFood == null) maxFood = Integer.valueOf(20);
/*     */     
/*  95 */     int currentFood = player.m_36324_().m_38702_();
/*     */     
/*  97 */     if (feedAmount.intValue() > 0) {
/*  98 */       if (currentFood < maxFood.intValue()) {
/*  99 */         int newFood = Math.min(currentFood + feedAmount.intValue(), maxFood.intValue());
/* 100 */         player.m_36324_().m_38705_(newFood);
/*     */       } 
/* 102 */     } else if (feedAmount.intValue() < 0 && 
/* 103 */       currentFood > minFood.intValue()) {
/* 104 */       int newFood = Math.max(currentFood + feedAmount.intValue(), minFood.intValue());
/* 105 */       player.m_36324_().m_38705_(newFood);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\rockf\Desktop\server\SERVER1\mods\worldprotect-1.0.0 seguridad region.jar!\com\worldprotect\protection\HealFeedHandler.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */