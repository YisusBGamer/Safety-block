/*     */ package com.worldprotect.config;
/*     */ 
/*     */ import net.minecraftforge.common.ForgeConfigSpec;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommonConfig
/*     */ {
/*     */   public CommonConfig(ForgeConfigSpec.Builder builder) {
/*  76 */     builder.push("integration");
/*     */ 
/*     */ 
/*     */     
/*  80 */     WorldProtectConfig.useWorldEditWand = builder.comment("Use WorldEdit wand for selections when WorldEdit is installed").define("useWorldEditWand", true);
/*     */     
/*  82 */     builder.pop();
/*     */     
/*  84 */     builder.push("global_settings");
/*  85 */     builder.comment("Global settings that apply outside of regions");
/*     */ 
/*     */ 
/*     */     
/*  89 */     WorldProtectConfig.enableFireSpread = builder.comment("Enable fire spread globally").define("enableFireSpread", true);
/*     */ 
/*     */ 
/*     */     
/*  93 */     WorldProtectConfig.enableLavaFire = builder.comment("Enable lava causing fire globally").define("enableLavaFire", true);
/*     */ 
/*     */ 
/*     */     
/*  97 */     WorldProtectConfig.enableCreeperBlockDamage = builder.comment("Enable creeper explosion block damage globally").define("enableCreeperBlockDamage", true);
/*     */ 
/*     */ 
/*     */     
/* 101 */     WorldProtectConfig.enableWitherBlockDamage = builder.comment("Enable wither block damage globally").define("enableWitherBlockDamage", true);
/*     */ 
/*     */ 
/*     */     
/* 105 */     WorldProtectConfig.enableEnderdragonBlockDamage = builder.comment("Enable ender dragon block damage globally").define("enableEnderdragonBlockDamage", true);
/*     */ 
/*     */ 
/*     */     
/* 109 */     WorldProtectConfig.enableGhastFireballBlockDamage = builder.comment("Enable ghast fireball block damage globally").define("enableGhastFireballBlockDamage", true);
/*     */ 
/*     */ 
/*     */     
/* 113 */     WorldProtectConfig.enableEndermanGrief = builder.comment("Enable enderman griefing globally").define("enableEndermanGrief", true);
/*     */ 
/*     */ 
/*     */     
/* 117 */     WorldProtectConfig.enableTntBlockDamage = builder.comment("Enable TNT block damage globally").define("enableTntBlockDamage", true);
/*     */     
/* 119 */     builder.pop();
/*     */   }
/*     */ }


/* Location:              C:\Users\rockf\Desktop\server\SERVER1\mods\worldprotect-1.0.0 seguridad region.jar!\com\worldprotect\config\WorldProtectConfig$CommonConfig.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */