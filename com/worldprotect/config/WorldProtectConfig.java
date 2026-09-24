/*     */ package com.worldprotect.config;
/*     */ 
/*     */ import java.util.function.Function;
/*     */ import net.minecraftforge.common.ForgeConfigSpec;
/*     */ import org.apache.commons.lang3.tuple.Pair;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldProtectConfig
/*     */ {
/*     */   public static final ForgeConfigSpec SERVER_SPEC;
/*     */   public static final ForgeConfigSpec COMMON_SPEC;
/*     */   public static ForgeConfigSpec.IntValue bypassOpLevel;
/*     */   public static ForgeConfigSpec.BooleanValue useWorldEditWand;
/*     */   public static ForgeConfigSpec.IntValue autoSaveInterval;
/*     */   public static ForgeConfigSpec.BooleanValue enableFireSpread;
/*     */   public static ForgeConfigSpec.BooleanValue enableLavaFire;
/*     */   public static ForgeConfigSpec.BooleanValue enableCreeperBlockDamage;
/*     */   public static ForgeConfigSpec.BooleanValue enableWitherBlockDamage;
/*     */   public static ForgeConfigSpec.BooleanValue enableEnderdragonBlockDamage;
/*     */   public static ForgeConfigSpec.BooleanValue enableGhastFireballBlockDamage;
/*     */   public static ForgeConfigSpec.BooleanValue enableEndermanGrief;
/*     */   public static ForgeConfigSpec.BooleanValue enableTntBlockDamage;
/*     */   public static ForgeConfigSpec.IntValue maxRegionCountPerPlayer;
/*     */   public static ForgeConfigSpec.IntValue maxRegionVolume;
/*     */   public static ForgeConfigSpec.IntValue claimPadding;
/*     */   
/*     */   static {
/*  30 */     Pair<ServerConfig, ForgeConfigSpec> serverPair = (new ForgeConfigSpec.Builder()).configure(ServerConfig::new);
/*  31 */     SERVER_SPEC = (ForgeConfigSpec)serverPair.getRight();
/*     */ 
/*     */     
/*  34 */     Pair<CommonConfig, ForgeConfigSpec> commonPair = (new ForgeConfigSpec.Builder()).configure(CommonConfig::new);
/*  35 */     COMMON_SPEC = (ForgeConfigSpec)commonPair.getRight();
/*     */   }
/*     */   
/*     */   public static class ServerConfig {
/*     */     public ServerConfig(ForgeConfigSpec.Builder builder) {
/*  40 */       builder.push("permissions");
/*     */ 
/*     */ 
/*     */       
/*  44 */       WorldProtectConfig.bypassOpLevel = builder.comment("Minimum operator level required to bypass protection (0-4, 0 = disabled)").defineInRange("bypassOpLevel", 2, 0, 4);
/*     */       
/*  46 */       builder.pop();
/*     */       
/*  48 */       builder.push("regions");
/*     */ 
/*     */ 
/*     */       
/*  52 */       WorldProtectConfig.maxRegionCountPerPlayer = builder.comment("Maximum number of regions a non-operator player can own (-1 for unlimited)").defineInRange("maxRegionCountPerPlayer", -1, -1, 2147483647);
/*     */ 
/*     */ 
/*     */       
/*  56 */       WorldProtectConfig.maxRegionVolume = builder.comment("Maximum volume (blocks) for a region (-1 for unlimited)").defineInRange("maxRegionVolume", -1, -1, 2147483647);
/*     */ 
/*     */ 
/*     */       
/*  60 */       WorldProtectConfig.claimPadding = builder.comment("Minimum distance between regions owned by different players").defineInRange("claimPadding", 0, 0, 100);
/*     */       
/*  62 */       builder.pop();
/*     */       
/*  64 */       builder.push("saving");
/*     */ 
/*     */ 
/*     */       
/*  68 */       WorldProtectConfig.autoSaveInterval = builder.comment("Auto-save interval in minutes (0 to disable)").defineInRange("autoSaveInterval", 5, 0, 60);
/*     */       
/*  70 */       builder.pop();
/*     */     }
/*     */   }
/*     */   
/*     */   public static class CommonConfig {
/*     */     public CommonConfig(ForgeConfigSpec.Builder builder) {
/*  76 */       builder.push("integration");
/*     */ 
/*     */ 
/*     */       
/*  80 */       WorldProtectConfig.useWorldEditWand = builder.comment("Use WorldEdit wand for selections when WorldEdit is installed").define("useWorldEditWand", true);
/*     */       
/*  82 */       builder.pop();
/*     */       
/*  84 */       builder.push("global_settings");
/*  85 */       builder.comment("Global settings that apply outside of regions");
/*     */ 
/*     */ 
/*     */       
/*  89 */       WorldProtectConfig.enableFireSpread = builder.comment("Enable fire spread globally").define("enableFireSpread", true);
/*     */ 
/*     */ 
/*     */       
/*  93 */       WorldProtectConfig.enableLavaFire = builder.comment("Enable lava causing fire globally").define("enableLavaFire", true);
/*     */ 
/*     */ 
/*     */       
/*  97 */       WorldProtectConfig.enableCreeperBlockDamage = builder.comment("Enable creeper explosion block damage globally").define("enableCreeperBlockDamage", true);
/*     */ 
/*     */ 
/*     */       
/* 101 */       WorldProtectConfig.enableWitherBlockDamage = builder.comment("Enable wither block damage globally").define("enableWitherBlockDamage", true);
/*     */ 
/*     */ 
/*     */       
/* 105 */       WorldProtectConfig.enableEnderdragonBlockDamage = builder.comment("Enable ender dragon block damage globally").define("enableEnderdragonBlockDamage", true);
/*     */ 
/*     */ 
/*     */       
/* 109 */       WorldProtectConfig.enableGhastFireballBlockDamage = builder.comment("Enable ghast fireball block damage globally").define("enableGhastFireballBlockDamage", true);
/*     */ 
/*     */ 
/*     */       
/* 113 */       WorldProtectConfig.enableEndermanGrief = builder.comment("Enable enderman griefing globally").define("enableEndermanGrief", true);
/*     */ 
/*     */ 
/*     */       
/* 117 */       WorldProtectConfig.enableTntBlockDamage = builder.comment("Enable TNT block damage globally").define("enableTntBlockDamage", true);
/*     */       
/* 119 */       builder.pop();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\rockf\Desktop\server\SERVER1\mods\worldprotect-1.0.0 seguridad region.jar!\com\worldprotect\config\WorldProtectConfig.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */