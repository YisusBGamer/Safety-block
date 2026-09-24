/*     */ package com.worldprotect.flag;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ public final class Flags {
/*   7 */   private static final Map<String, Flag<?>> FLAGS = new HashMap<>();
/*     */ 
/*     */   
/*  10 */   public static final StateFlag PASSTHROUGH = register(new StateFlag("passthrough", false));
/*  11 */   public static final StateFlag BUILD = register(new StateFlag("build", true));
/*  12 */   public static final StateFlag BLOCK_BREAK = register(new StateFlag("block-break", true));
/*  13 */   public static final StateFlag BLOCK_PLACE = register(new StateFlag("block-place", true));
/*  14 */   public static final StateFlag USE = register(new StateFlag("use", true));
/*  15 */   public static final StateFlag INTERACT = register(new StateFlag("interact", true));
/*  16 */   public static final StateFlag CHEST_ACCESS = register(new StateFlag("chest-access", true));
/*     */ 
/*     */   
/*  19 */   public static final StateFlag PVP = register(new StateFlag("pvp", true));
/*  20 */   public static final StateFlag MOB_DAMAGE = register(new StateFlag("mob-damage", true));
/*  21 */   public static final StateFlag DAMAGE_ANIMALS = register(new StateFlag("damage-animals", true));
/*  22 */   public static final StateFlag FALL_DAMAGE = register(new StateFlag("fall-damage", true));
/*  23 */   public static final StateFlag FIRE_DAMAGE = register(new StateFlag("fire-damage", true));
/*  24 */   public static final StateFlag LAVA_DAMAGE = register(new StateFlag("lava-damage", true));
/*  25 */   public static final StateFlag LIGHTNING_DAMAGE = register(new StateFlag("lightning-damage", true));
/*  26 */   public static final StateFlag DROWNING_DAMAGE = register(new StateFlag("drowning-damage", true));
/*  27 */   public static final StateFlag SUFFOCATION_DAMAGE = register(new StateFlag("suffocation-damage", true));
/*  28 */   public static final StateFlag VOID_DAMAGE = register(new StateFlag("void-damage", true));
/*  29 */   public static final StateFlag INVINCIBILITY = register(new StateFlag("invincibility", false));
/*     */ 
/*     */   
/*  32 */   public static final StateFlag MOB_SPAWNING = register(new StateFlag("mob-spawning", true));
/*  33 */   public static final StateFlag CREEPER_EXPLOSION = register(new StateFlag("creeper-explosion", true));
/*  34 */   public static final StateFlag ENDERDRAGON_BLOCK_DAMAGE = register(new StateFlag("enderdragon-block-damage", true));
/*  35 */   public static final StateFlag GHAST_FIREBALL = register(new StateFlag("ghast-fireball", true));
/*  36 */   public static final StateFlag WITHER_DAMAGE = register(new StateFlag("wither-damage", true));
/*  37 */   public static final StateFlag ENDERMAN_GRIEF = register(new StateFlag("enderman-grief", true));
/*  38 */   public static final StateFlag SNOWMAN_TRAILS = register(new StateFlag("snowman-trails", true));
/*     */ 
/*     */   
/*  41 */   public static final StateFlag FIRE_SPREAD = register(new StateFlag("fire-spread", true));
/*  42 */   public static final StateFlag LAVA_FIRE = register(new StateFlag("lava-fire", true));
/*  43 */   public static final StateFlag LIGHTNING = register(new StateFlag("lightning", true));
/*  44 */   public static final StateFlag WATER_FLOW = register(new StateFlag("water-flow", true));
/*  45 */   public static final StateFlag LAVA_FLOW = register(new StateFlag("lava-flow", true));
/*  46 */   public static final StateFlag SNOW_FALL = register(new StateFlag("snow-fall", true));
/*  47 */   public static final StateFlag SNOW_MELT = register(new StateFlag("snow-melt", true));
/*  48 */   public static final StateFlag ICE_FORM = register(new StateFlag("ice-form", true));
/*  49 */   public static final StateFlag ICE_MELT = register(new StateFlag("ice-melt", true));
/*  50 */   public static final StateFlag FROSTED_ICE_FORM = register(new StateFlag("frosted-ice-form", true));
/*  51 */   public static final StateFlag FROSTED_ICE_MELT = register(new StateFlag("frosted-ice-melt", true));
/*  52 */   public static final StateFlag MUSHROOM_GROWTH = register(new StateFlag("mushroom-growth", true));
/*  53 */   public static final StateFlag LEAF_DECAY = register(new StateFlag("leaf-decay", true));
/*  54 */   public static final StateFlag GRASS_SPREAD = register(new StateFlag("grass-spread", true));
/*  55 */   public static final StateFlag MYCELIUM_SPREAD = register(new StateFlag("mycelium-spread", true));
/*  56 */   public static final StateFlag VINE_GROWTH = register(new StateFlag("vine-growth", true));
/*  57 */   public static final StateFlag CROP_GROWTH = register(new StateFlag("crop-growth", true));
/*  58 */   public static final StateFlag SOIL_DRY = register(new StateFlag("soil-dry", true));
/*  59 */   public static final StateFlag CORAL_FADE = register(new StateFlag("coral-fade", true));
/*     */ 
/*     */   
/*  62 */   public static final StateFlag TNT = register(new StateFlag("tnt", true));
/*  63 */   public static final StateFlag OTHER_EXPLOSION = register(new StateFlag("other-explosion", true));
/*     */ 
/*     */   
/*  66 */   public static final StateFlag ENTRY = register(new StateFlag("entry", true, RegionGroup.NON_MEMBERS));
/*  67 */   public static final StateFlag EXIT = register(new StateFlag("exit", true, RegionGroup.NON_MEMBERS));
/*  68 */   public static final StateFlag ENDERPEARL = register(new StateFlag("enderpearl", true));
/*  69 */   public static final StateFlag CHORUS_TELEPORT = register(new StateFlag("chorus-fruit-teleport", true));
/*     */ 
/*     */   
/*  72 */   public static final StateFlag RIDE = register(new StateFlag("ride", true));
/*  73 */   public static final StateFlag VEHICLE_PLACE = register(new StateFlag("vehicle-place", true));
/*  74 */   public static final StateFlag VEHICLE_DESTROY = register(new StateFlag("vehicle-destroy", true));
/*     */ 
/*     */   
/*  77 */   public static final StateFlag ITEM_PICKUP = register(new StateFlag("item-pickup", true));
/*  78 */   public static final StateFlag ITEM_DROP = register(new StateFlag("item-drop", true));
/*  79 */   public static final StateFlag EXP_DROPS = register(new StateFlag("exp-drops", true));
/*     */ 
/*     */   
/*  82 */   public static final IntegerFlag HEAL_AMOUNT = register(new IntegerFlag("heal-amount", Integer.valueOf(0)));
/*  83 */   public static final IntegerFlag HEAL_DELAY = register(new IntegerFlag("heal-delay", Integer.valueOf(1)));
/*  84 */   public static final DoubleFlag MIN_HEAL = register(new DoubleFlag("heal-min-health", Double.valueOf(0.0D)));
/*  85 */   public static final DoubleFlag MAX_HEAL = register(new DoubleFlag("heal-max-health", Double.valueOf(20.0D)));
/*  86 */   public static final IntegerFlag FEED_AMOUNT = register(new IntegerFlag("feed-amount", Integer.valueOf(0)));
/*  87 */   public static final IntegerFlag FEED_DELAY = register(new IntegerFlag("feed-delay", Integer.valueOf(1)));
/*  88 */   public static final IntegerFlag MIN_FOOD = register(new IntegerFlag("feed-min-hunger", Integer.valueOf(0)));
/*  89 */   public static final IntegerFlag MAX_FOOD = register(new IntegerFlag("feed-max-hunger", Integer.valueOf(20)));
/*     */ 
/*     */   
/*  92 */   public static final LocationFlag TELEPORT = register(new LocationFlag("teleport"));
/*  93 */   public static final LocationFlag SPAWN = register(new LocationFlag("spawn"));
/*     */ 
/*     */   
/*  96 */   public static final StringFlag GREETING = register(new StringFlag("greeting"));
/*  97 */   public static final StringFlag FAREWELL = register(new StringFlag("farewell"));
/*  98 */   public static final StringFlag GREETING_TITLE = register(new StringFlag("greeting-title"));
/*  99 */   public static final StringFlag FAREWELL_TITLE = register(new StringFlag("farewell-title"));
/* 100 */   public static final StringFlag DENY_MESSAGE = register(new StringFlag("deny-message"));
/*     */ 
/*     */   
/* 103 */   public static final StateFlag SEND_CHAT = register(new StateFlag("send-chat", true));
/* 104 */   public static final StateFlag RECEIVE_CHAT = register(new StateFlag("receive-chat", true));
/* 105 */   public static final StateFlag SLEEP = register(new StateFlag("sleep", true));
/* 106 */   public static final StateFlag RESPAWN_ANCHORS = register(new StateFlag("respawn-anchors", true));
/* 107 */   public static final StateFlag TRAMPLE_BLOCKS = register(new StateFlag("trample-blocks", true));
/* 108 */   public static final StateFlag PISTONS = register(new StateFlag("pistons", true));
/*     */ 
/*     */   
/* 111 */   public static final StringFlag GAME_MODE = register(new StringFlag("game-mode"));
/*     */ 
/*     */   
/* 114 */   public static final StringFlag WEATHER_LOCK = register(new StringFlag("weather-lock"));
/* 115 */   public static final StringFlag TIME_LOCK = register(new StringFlag("time-lock"));
/*     */ 
/*     */   
/* 118 */   public static final StringFlag DENY_SPAWN = register(new StringFlag("deny-spawn"));
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T extends Flag<?>> T register(T flag) {
/* 123 */     FLAGS.put(flag.getName().toLowerCase(), (Flag<?>)flag);
/* 124 */     return flag;
/*     */   }
/*     */   
/*     */   public static Flag<?> get(String name) {
/* 128 */     return FLAGS.get(name.toLowerCase());
/*     */   }
/*     */   
/*     */   public static Map<String, Flag<?>> getAll() {
/* 132 */     return new HashMap<>(FLAGS);
/*     */   }
/*     */   
/*     */   public static boolean exists(String name) {
/* 136 */     return FLAGS.containsKey(name.toLowerCase());
/*     */   }
/*     */ }


/* Location:              C:\Users\rockf\Desktop\server\SERVER1\mods\worldprotect-1.0.0 seguridad region.jar!\com\worldprotect\flag\Flags.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */