/*     */ package com.worldprotect.storage;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.worldprotect.WorldProtect;
/*     */ import com.worldprotect.flag.Flag;
/*     */ import com.worldprotect.flag.RegionGroup;
/*     */ import com.worldprotect.region.GlobalProtectedRegion;
/*     */ import com.worldprotect.region.ProtectedCuboidRegion;
/*     */ import com.worldprotect.region.ProtectedPolygonalRegion;
/*     */ import com.worldprotect.region.ProtectedRegion;
/*     */ import com.worldprotect.region.RegionManager;
/*     */ import java.io.Reader;
/*     */ import java.io.Writer;
/*     */ import java.nio.file.Path;
/*     */ import java.util.Map;
/*     */ import net.minecraft.core.BlockPos;
/*     */ 
/*     */ public class RegionStorage {
/*  20 */   private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
/*     */   private final Path dataFolder;
/*     */   
/*     */   public RegionStorage(MinecraftServer server) {
/*  24 */     this.dataFolder = server.m_129843_(LevelResource.f_78182_).resolve("worldprotect");
/*     */     try {
/*  26 */       Files.createDirectories(this.dataFolder, (FileAttribute<?>[])new FileAttribute[0]);
/*  27 */     } catch (IOException e) {
/*  28 */       WorldProtect.LOGGER.error("Failed to create data folder", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void saveRegions(RegionManager manager, String dimensionKey) {
/*  33 */     String sanitizedKey = sanitizeFileName(dimensionKey);
/*  34 */     Path file = this.dataFolder.resolve("regions_" + sanitizedKey + ".json");
/*     */     
/*  36 */     JsonObject root = new JsonObject();
/*  37 */     JsonObject regionsObj = new JsonObject();
/*     */     
/*  39 */     for (ProtectedRegion region : manager.getRegions()) {
/*  40 */       regionsObj.add(region.getId(), (JsonElement)serializeRegion(region));
/*     */     }
/*     */     
/*  43 */     root.add("regions", (JsonElement)regionsObj);
/*  44 */     root.addProperty("version", Integer.valueOf(1));
/*     */     
/*  46 */     try { Writer writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8, new java.nio.file.OpenOption[0]); 
/*  47 */       try { GSON.toJson((JsonElement)root, writer);
/*  48 */         manager.setDirty(false);
/*  49 */         WorldProtect.LOGGER.debug("Saved {} regions for {}", Integer.valueOf(manager.size()), dimensionKey);
/*  50 */         if (writer != null) writer.close();  } catch (Throwable throwable) { if (writer != null) try { writer.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException e)
/*  51 */     { WorldProtect.LOGGER.error("Failed to save regions for {}", dimensionKey, e); }
/*     */   
/*     */   }
/*     */   
/*     */   public void loadRegions(RegionManager manager, String dimensionKey) {
/*  56 */     String sanitizedKey = sanitizeFileName(dimensionKey);
/*  57 */     Path file = this.dataFolder.resolve("regions_" + sanitizedKey + ".json");
/*     */     
/*  59 */     if (!Files.exists(file, new java.nio.file.LinkOption[0])) {
/*  60 */       WorldProtect.LOGGER.debug("No regions file found for {}", dimensionKey);
/*     */       return;
/*     */     } 
/*     */     
/*  64 */     try { Reader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8); 
/*  65 */       try { JsonObject root = (JsonObject)GSON.fromJson(reader, JsonObject.class);
/*     */         
/*  67 */         if (root == null || !root.has("regions"))
/*     */         
/*     */         { 
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
/* 103 */           if (reader != null) reader.close();  return; }  JsonObject regionsObj = root.getAsJsonObject("regions"); Map<String, String> parentMap = new HashMap<>(); for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)regionsObj.entrySet()) { String id = entry.getKey(); JsonObject regionObj = ((JsonElement)entry.getValue()).getAsJsonObject(); ProtectedRegion region = deserializeRegion(id, regionObj); if (region != null) { manager.addRegion(region); if (regionObj.has("parent")) parentMap.put(id, regionObj.get("parent").getAsString());  }  }  for (Map.Entry<String, String> entry : parentMap.entrySet()) { ProtectedRegion child = manager.getRegion(entry.getKey()); ProtectedRegion parent = manager.getRegion(entry.getValue()); if (child != null && parent != null) try { child.setParent(parent); } catch (IllegalArgumentException e) { WorldProtect.LOGGER.warn("Could not set parent for region {}: {}", entry.getKey(), e.getMessage()); }   }  manager.setDirty(false); WorldProtect.LOGGER.info("Loaded {} regions for {}", Integer.valueOf(manager.size()), dimensionKey); if (reader != null) reader.close();  } catch (Throwable throwable) { if (reader != null) try { reader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException e)
/* 104 */     { WorldProtect.LOGGER.error("Failed to load regions for {}", dimensionKey, e); }
/*     */   
/*     */   }
/*     */   
/*     */   private JsonObject serializeRegion(ProtectedRegion region) {
/* 109 */     JsonObject obj = new JsonObject();
/*     */     
/* 111 */     obj.addProperty("type", region.getType().name());
/* 112 */     obj.addProperty("priority", Integer.valueOf(region.getPriority()));
/*     */     
/* 114 */     if (region.getParent() != null) {
/* 115 */       obj.addProperty("parent", region.getParent().getId());
/*     */     }
/*     */     
/* 118 */     if (region instanceof ProtectedCuboidRegion) { ProtectedCuboidRegion cuboid = (ProtectedCuboidRegion)region;
/* 119 */       BlockPos min = cuboid.getMinimumPoint();
/* 120 */       BlockPos max = cuboid.getMaximumPoint();
/*     */       
/* 122 */       JsonObject minObj = new JsonObject();
/* 123 */       minObj.addProperty("x", Integer.valueOf(min.m_123341_()));
/* 124 */       minObj.addProperty("y", Integer.valueOf(min.m_123342_()));
/* 125 */       minObj.addProperty("z", Integer.valueOf(min.m_123343_()));
/* 126 */       obj.add("min", (JsonElement)minObj);
/*     */       
/* 128 */       JsonObject maxObj = new JsonObject();
/* 129 */       maxObj.addProperty("x", Integer.valueOf(max.m_123341_()));
/* 130 */       maxObj.addProperty("y", Integer.valueOf(max.m_123342_()));
/* 131 */       maxObj.addProperty("z", Integer.valueOf(max.m_123343_()));
/* 132 */       obj.add("max", (JsonElement)maxObj); }
/*     */     
/* 134 */     else if (region instanceof ProtectedPolygonalRegion) { ProtectedPolygonalRegion poly = (ProtectedPolygonalRegion)region;
/* 135 */       JsonArray pointsArray = new JsonArray();
/* 136 */       for (BlockPos point : poly.getPoints()) {
/* 137 */         JsonObject pointObj = new JsonObject();
/* 138 */         pointObj.addProperty("x", Integer.valueOf(point.m_123341_()));
/* 139 */         pointObj.addProperty("z", Integer.valueOf(point.m_123343_()));
/* 140 */         pointsArray.add((JsonElement)pointObj);
/*     */       } 
/* 142 */       obj.add("points", (JsonElement)pointsArray);
/* 143 */       obj.addProperty("min-y", Integer.valueOf(poly.getMinY()));
/* 144 */       obj.addProperty("max-y", Integer.valueOf(poly.getMaxY())); }
/*     */ 
/*     */     
/* 147 */     JsonArray ownersArray = new JsonArray();
/* 148 */     for (UUID uuid : region.getOwners()) {
/* 149 */       ownersArray.add(uuid.toString());
/*     */     }
/* 151 */     obj.add("owners", (JsonElement)ownersArray);
/*     */     
/* 153 */     JsonArray membersArray = new JsonArray();
/* 154 */     for (UUID uuid : region.getMembers()) {
/* 155 */       membersArray.add(uuid.toString());
/*     */     }
/* 157 */     obj.add("members", (JsonElement)membersArray);
/*     */     
/* 159 */     JsonArray ownerGroupsArray = new JsonArray();
/* 160 */     for (String group : region.getOwnerGroups()) {
/* 161 */       ownerGroupsArray.add(group);
/*     */     }
/* 163 */     obj.add("owner-groups", (JsonElement)ownerGroupsArray);
/*     */     
/* 165 */     JsonArray memberGroupsArray = new JsonArray();
/* 166 */     for (String group : region.getMemberGroups()) {
/* 167 */       memberGroupsArray.add(group);
/*     */     }
/* 169 */     obj.add("member-groups", (JsonElement)memberGroupsArray);
/*     */     
/* 171 */     JsonObject flagsObj = new JsonObject();
/* 172 */     for (Map.Entry<Flag<?>, Object> entry : (Iterable<Map.Entry<Flag<?>, Object>>)region.getFlags().entrySet()) {
/* 173 */       Flag<?> flag = entry.getKey();
/*     */       
/* 175 */       Flag<Object> typedFlag = (Flag)flag;
/* 176 */       flagsObj.add(flag.getName(), typedFlag.serialize(entry.getValue()));
/*     */     } 
/* 178 */     obj.add("flags", (JsonElement)flagsObj);
/*     */     
/* 180 */     JsonObject flagGroupsObj = new JsonObject();
/* 181 */     for (Map.Entry<Flag<?>, RegionGroup> entry : (Iterable<Map.Entry<Flag<?>, RegionGroup>>)region.getFlagGroups().entrySet()) {
/* 182 */       flagGroupsObj.addProperty(((Flag)entry.getKey()).getName(), ((RegionGroup)entry.getValue()).getName());
/*     */     }
/* 184 */     obj.add("flag-groups", (JsonElement)flagGroupsObj);
/*     */     
/* 186 */     return obj; } private ProtectedRegion deserializeRegion(String id, JsonObject obj) { ProtectedCuboidRegion protectedCuboidRegion; ProtectedPolygonalRegion protectedPolygonalRegion; GlobalProtectedRegion globalProtectedRegion; JsonObject minObj; JsonArray pointsArray; JsonObject maxObj; List<BlockPos> points; BlockPos min;
/*     */     int minY;
/*     */     BlockPos max;
/*     */     int maxY;
/* 190 */     String typeStr = obj.get("type").getAsString();
/* 191 */     ProtectedRegion.RegionType type = ProtectedRegion.RegionType.valueOf(typeStr);
/*     */ 
/*     */ 
/*     */     
/* 195 */     switch (type) {
/*     */       case CUBOID:
/* 197 */         minObj = obj.getAsJsonObject("min");
/* 198 */         maxObj = obj.getAsJsonObject("max");
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 203 */         min = new BlockPos(minObj.get("x").getAsInt(), minObj.get("y").getAsInt(), minObj.get("z").getAsInt());
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 208 */         max = new BlockPos(maxObj.get("x").getAsInt(), maxObj.get("y").getAsInt(), maxObj.get("z").getAsInt());
/*     */ 
/*     */         
/* 211 */         protectedCuboidRegion = new ProtectedCuboidRegion(id, min, max);
/*     */         break;
/*     */       case POLYGON:
/* 214 */         pointsArray = obj.getAsJsonArray("points");
/* 215 */         points = new ArrayList<>();
/* 216 */         for (JsonElement elem : pointsArray) {
/* 217 */           JsonObject pointObj = elem.getAsJsonObject();
/* 218 */           points.add(new BlockPos(pointObj
/* 219 */                 .get("x").getAsInt(), 0, pointObj
/*     */                 
/* 221 */                 .get("z").getAsInt()));
/*     */         } 
/*     */         
/* 224 */         minY = obj.get("min-y").getAsInt();
/* 225 */         maxY = obj.get("max-y").getAsInt();
/* 226 */         protectedPolygonalRegion = new ProtectedPolygonalRegion(id, points, minY, maxY); break;
/*     */       case GLOBAL:
/* 228 */         globalProtectedRegion = new GlobalProtectedRegion(id); break;
/*     */       default:
/* 230 */         WorldProtect.LOGGER.warn("Unknown region type: {}", typeStr);
/* 231 */         return null;
/*     */     } 
/*     */ 
/*     */     
/* 235 */     globalProtectedRegion.setPriority(obj.get("priority").getAsInt());
/*     */     
/* 237 */     if (obj.has("owners")) {
/* 238 */       for (JsonElement elem : obj.getAsJsonArray("owners")) {
/*     */         try {
/* 240 */           globalProtectedRegion.addOwner(UUID.fromString(elem.getAsString()));
/* 241 */         } catch (IllegalArgumentException e) {
/* 242 */           WorldProtect.LOGGER.warn("Invalid owner UUID: {}", elem.getAsString());
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 247 */     if (obj.has("members")) {
/* 248 */       for (JsonElement elem : obj.getAsJsonArray("members")) {
/*     */         try {
/* 250 */           globalProtectedRegion.addMember(UUID.fromString(elem.getAsString()));
/* 251 */         } catch (IllegalArgumentException e) {
/* 252 */           WorldProtect.LOGGER.warn("Invalid member UUID: {}", elem.getAsString());
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 257 */     if (obj.has("owner-groups")) {
/* 258 */       for (JsonElement elem : obj.getAsJsonArray("owner-groups")) {
/* 259 */         globalProtectedRegion.addOwnerGroup(elem.getAsString());
/*     */       }
/*     */     }
/*     */     
/* 263 */     if (obj.has("member-groups")) {
/* 264 */       for (JsonElement elem : obj.getAsJsonArray("member-groups")) {
/* 265 */         globalProtectedRegion.addMemberGroup(elem.getAsString());
/*     */       }
/*     */     }
/*     */     
/* 269 */     if (obj.has("flags")) {
/* 270 */       JsonObject flagsObj = obj.getAsJsonObject("flags");
/* 271 */       for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)flagsObj.entrySet()) {
/* 272 */         Flag<?> flag = Flags.get(entry.getKey());
/* 273 */         if (flag != null) {
/*     */           
/*     */           try {
/* 276 */             Flag<Object> typedFlag = (Flag)flag;
/* 277 */             Object value = typedFlag.deserialize(entry.getValue());
/* 278 */             globalProtectedRegion.setFlag(typedFlag, value);
/* 279 */           } catch (Exception e) {
/* 280 */             WorldProtect.LOGGER.warn("Failed to deserialize flag {}: {}", entry.getKey(), e.getMessage());
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 286 */     if (obj.has("flag-groups")) {
/* 287 */       JsonObject flagGroupsObj = obj.getAsJsonObject("flag-groups");
/* 288 */       for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)flagGroupsObj.entrySet()) {
/* 289 */         Flag<?> flag = Flags.get(entry.getKey());
/* 290 */         if (flag != null) {
/* 291 */           RegionGroup group = RegionGroup.fromString(((JsonElement)entry.getValue()).getAsString());
/* 292 */           globalProtectedRegion.setFlagGroup(flag, group);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 297 */     return (ProtectedRegion)globalProtectedRegion; }
/*     */ 
/*     */   
/*     */   private String sanitizeFileName(String name) {
/* 301 */     return name.replaceAll("[^a-zA-Z0-9_-]", "_");
/*     */   }
/*     */ }


/* Location:              C:\Users\rockf\Desktop\server\SERVER1\mods\worldprotect-1.0.0 seguridad region.jar!\com\worldprotect\storage\RegionStorage.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */