/*    */ package com.worldprotect.util;
/*    */ import com.worldprotect.WorldProtect;
/*    */ import com.worldprotect.integration.WorldEditIntegration;
/*    */ import net.minecraft.ChatFormatting;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.world.InteractionHand;
/*    */ import net.minecraft.world.InteractionResult;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.Items;
/*    */ import net.minecraftforge.event.entity.player.PlayerInteractEvent;
/*    */ import net.minecraftforge.eventbus.api.SubscribeEvent;
/*    */ import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
/*    */ 
/*    */ @EventBusSubscriber(modid = "worldprotect")
/*    */ public class WandHandler {
/*    */   private static final String WAND_NBT_TAG = "worldprotect_wand";
/*    */   
/*    */   @SubscribeEvent
/*    */   public static void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
/*    */     ServerPlayer player;
/* 24 */     if (event.getLevel().m_5776_())
/* 25 */       return;  Player player1 = event.getEntity(); if (player1 instanceof ServerPlayer) { player = (ServerPlayer)player1; } else { return; }
/* 26 */      if (event.getHand() != InteractionHand.MAIN_HAND)
/*    */       return; 
/* 28 */     ItemStack item = player.m_21205_();
/* 29 */     if (!isWand(item))
/*    */       return; 
/* 31 */     if (WorldProtect.getInstance().isWorldEditLoaded()) {
/*    */       return;
/*    */     }
/*    */     
/* 35 */     BlockPos pos = event.getPos();
/* 36 */     WorldEditIntegration integration = WorldProtect.getInstance().getWorldEditIntegration();
/* 37 */     if (integration == null) {
/* 38 */       integration = new WorldEditIntegration();
/*    */     }
/* 40 */     integration.setFallbackPos1((Player)player, pos);
/*    */     
/* 42 */     player.m_213846_((Component)Component.m_237113_("Position 1 set to (" + pos.m_123341_() + ", " + pos.m_123342_() + ", " + pos.m_123343_() + ")")
/* 43 */         .m_130940_(ChatFormatting.YELLOW));
/*    */     
/* 45 */     event.setCanceled(true);
/*    */   }
/*    */   @SubscribeEvent
/*    */   public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
/*    */     ServerPlayer player;
/* 50 */     if (event.getLevel().m_5776_())
/* 51 */       return;  Player player1 = event.getEntity(); if (player1 instanceof ServerPlayer) { player = (ServerPlayer)player1; } else { return; }
/* 52 */      if (event.getHand() != InteractionHand.MAIN_HAND)
/*    */       return; 
/* 54 */     ItemStack item = player.m_21205_();
/* 55 */     if (!isWand(item))
/*    */       return; 
/* 57 */     if (WorldProtect.getInstance().isWorldEditLoaded()) {
/*    */       return;
/*    */     }
/*    */     
/* 61 */     BlockPos pos = event.getPos();
/* 62 */     WorldEditIntegration integration = WorldProtect.getInstance().getWorldEditIntegration();
/* 63 */     if (integration == null) {
/* 64 */       integration = new WorldEditIntegration();
/*    */     }
/* 66 */     integration.setFallbackPos2((Player)player, pos);
/*    */     
/* 68 */     player.m_213846_((Component)Component.m_237113_("Position 2 set to (" + pos.m_123341_() + ", " + pos.m_123342_() + ", " + pos.m_123343_() + ")")
/* 69 */         .m_130940_(ChatFormatting.YELLOW));
/*    */     
/* 71 */     event.setCanceled(true);
/* 72 */     event.setCancellationResult(InteractionResult.SUCCESS);
/*    */   }
/*    */   
/*    */   public static boolean isWand(ItemStack item) {
/* 76 */     if (item.m_41619_()) return false;
/*    */     
/* 78 */     if (item.m_41720_() == Items.f_42423_) {
/* 79 */       return true;
/*    */     }
/*    */     
/* 82 */     if (item.m_41782_() && item.m_41783_().m_128471_("worldprotect_wand")) {
/* 83 */       return true;
/*    */     }
/*    */     
/* 86 */     return false;
/*    */   }
/*    */   
/*    */   public static ItemStack createWand() {
/* 90 */     ItemStack wand = new ItemStack((ItemLike)Items.f_42423_);
/* 91 */     wand.m_41714_((Component)Component.m_237113_("WorldProtect Wand").m_130940_(ChatFormatting.GOLD));
/* 92 */     wand.m_41784_().m_128379_("worldprotect_wand", true);
/* 93 */     return wand;
/*    */   }
/*    */ }


/* Location:              C:\Users\rockf\Desktop\server\SERVER1\mods\worldprotect-1.0.0 seguridad region.jar!\com\worldprotec\\util\WandHandler.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */