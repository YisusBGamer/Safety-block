/*     */ package com.worldprotect;
/*     */ 
/*     */ import com.worldprotect.command.CommandRegistry;
/*     */ import com.worldprotect.config.WorldProtectConfig;
/*     */ import com.worldprotect.integration.WorldEditIntegration;
/*     */ import com.worldprotect.protection.ProtectionEventHandler;
/*     */ import com.worldprotect.region.RegionManager;
/*     */ import com.worldprotect.storage.RegionStorage;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.event.RegisterCommandsEvent;
/*     */ import net.minecraftforge.event.server.ServerStartedEvent;
/*     */ import net.minecraftforge.event.server.ServerStoppingEvent;
/*     */ import net.minecraftforge.eventbus.api.IEventBus;
/*     */ import net.minecraftforge.eventbus.api.SubscribeEvent;
/*     */ import net.minecraftforge.fml.ModList;
/*     */ import net.minecraftforge.fml.ModLoadingContext;
/*     */ import net.minecraftforge.fml.common.Mod;
/*     */ import net.minecraftforge.fml.config.IConfigSpec;
/*     */ import net.minecraftforge.fml.config.ModConfig;
/*     */ import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
/*     */ import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ @Mod("worldprotect")
/*     */ public class WorldProtect {
/*     */   public static final String MOD_ID = "worldprotect";
/*  33 */   public static final Logger LOGGER = LoggerFactory.getLogger("worldprotect");
/*     */   
/*     */   private static WorldProtect instance;
/*  36 */   private final Map<String, RegionManager> regionManagers = new HashMap<>();
/*     */   private RegionStorage regionStorage;
/*     */   private WorldEditIntegration worldEditIntegration;
/*     */   private MinecraftServer server;
/*     */   
/*     */   public WorldProtect() {
/*  42 */     instance = this;
/*     */     
/*  44 */     IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
/*  45 */     modEventBus.addListener(this::commonSetup);
/*     */     
/*  47 */     ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, (IConfigSpec)WorldProtectConfig.SERVER_SPEC);
/*  48 */     ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, (IConfigSpec)WorldProtectConfig.COMMON_SPEC);
/*     */     
/*  50 */     MinecraftForge.EVENT_BUS.register(this);
/*  51 */     MinecraftForge.EVENT_BUS.register(new ProtectionEventHandler());
/*     */     
/*  53 */     LOGGER.info("WorldProtect is loading...");
/*     */   }
/*     */   
/*     */   private void commonSetup(FMLCommonSetupEvent event) {
/*  57 */     LOGGER.info("WorldProtect common setup");
/*     */     
/*  59 */     if (ModList.get().isLoaded("worldedit")) {
/*  60 */       LOGGER.info("WorldEdit detected, enabling integration");
/*  61 */       this.worldEditIntegration = new WorldEditIntegration();
/*     */     } else {
/*  63 */       LOGGER.info("WorldEdit not found, selection features will use built-in wand");
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onServerStarted(ServerStartedEvent event) {
/*  69 */     this.server = event.getServer();
/*  70 */     this.regionStorage = new RegionStorage(this.server);
/*     */     
/*  72 */     for (ServerLevel level : this.server.m_129785_()) {
/*  73 */       String dimensionKey = level.m_46472_().m_135782_().toString();
/*  74 */       RegionManager manager = new RegionManager(dimensionKey);
/*  75 */       this.regionManagers.put(dimensionKey, manager);
/*  76 */       this.regionStorage.loadRegions(manager, dimensionKey);
/*  77 */       LOGGER.info("Loaded regions for dimension: {}", dimensionKey);
/*     */     } 
/*     */     
/*  80 */     LOGGER.info("WorldProtect is now active!");
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onServerStopping(ServerStoppingEvent event) {
/*  85 */     LOGGER.info("Saving all regions...");
/*  86 */     for (Map.Entry<String, RegionManager> entry : this.regionManagers.entrySet()) {
/*  87 */       this.regionStorage.saveRegions(entry.getValue(), entry.getKey());
/*     */     }
/*  89 */     LOGGER.info("All regions saved!");
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onRegisterCommands(RegisterCommandsEvent event) {
/*  94 */     CommandRegistry.registerCommands(event.getDispatcher());
/*  95 */     LOGGER.info("WorldProtect commands registered");
/*     */   }
/*     */   
/*     */   public static WorldProtect getInstance() {
/*  99 */     return instance;
/*     */   }
/*     */   
/*     */   public RegionManager getRegionManager(Level level) {
/* 103 */     String dimensionKey = level.m_46472_().m_135782_().toString();
/* 104 */     return this.regionManagers.get(dimensionKey);
/*     */   }
/*     */   
/*     */   public RegionManager getRegionManager(String dimensionKey) {
/* 108 */     return this.regionManagers.get(dimensionKey);
/*     */   }
/*     */   
/*     */   public Map<String, RegionManager> getAllRegionManagers() {
/* 112 */     return this.regionManagers;
/*     */   }
/*     */   
/*     */   public RegionStorage getRegionStorage() {
/* 116 */     return this.regionStorage;
/*     */   }
/*     */   
/*     */   public WorldEditIntegration getWorldEditIntegration() {
/* 120 */     return this.worldEditIntegration;
/*     */   }
/*     */   
/*     */   public boolean isWorldEditLoaded() {
/* 124 */     return (this.worldEditIntegration != null);
/*     */   }
/*     */   
/*     */   public MinecraftServer getServer() {
/* 128 */     return this.server;
/*     */   }
/*     */   
/*     */   public void saveAllRegions() {
/* 132 */     if (this.regionStorage != null)
/* 133 */       for (Map.Entry<String, RegionManager> entry : this.regionManagers.entrySet())
/* 134 */         this.regionStorage.saveRegions(entry.getValue(), entry.getKey());  
/*     */   }
/*     */ }


/* Location:              C:\Users\rockf\Desktop\server\SERVER1\mods\worldprotect-1.0.0 seguridad region.jar!\com\worldprotect\WorldProtect.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */