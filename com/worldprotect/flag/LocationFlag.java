/*     */ package com.worldprotect.flag;
/*     */ 
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ 
/*     */ public class LocationFlag
/*     */   extends Flag<LocationFlag.Location>
/*     */ {
/*     */   public static class Location
/*     */   {
/*     */     private final String world;
/*     */     private final double x;
/*     */     private final double y;
/*     */     private final double z;
/*     */     private final float yaw;
/*     */     private final float pitch;
/*     */     
/*     */     public Location(String world, double x, double y, double z, float yaw, float pitch) {
/*  21 */       this.world = world;
/*  22 */       this.x = x;
/*  23 */       this.y = y;
/*  24 */       this.z = z;
/*  25 */       this.yaw = yaw;
/*  26 */       this.pitch = pitch;
/*     */     }
/*     */     
/*     */     public Location(String world, double x, double y, double z) {
/*  30 */       this(world, x, y, z, 0.0F, 0.0F);
/*     */     }
/*     */     
/*     */     public Location(String world, BlockPos pos) {
/*  34 */       this(world, pos.m_123341_() + 0.5D, pos.m_123342_(), pos.m_123343_() + 0.5D, 0.0F, 0.0F);
/*     */     }
/*     */     
/*     */     public String getWorld() {
/*  38 */       return this.world;
/*     */     }
/*     */     
/*     */     public double getX() {
/*  42 */       return this.x;
/*     */     }
/*     */     
/*     */     public double getY() {
/*  46 */       return this.y;
/*     */     }
/*     */     
/*     */     public double getZ() {
/*  50 */       return this.z;
/*     */     }
/*     */     
/*     */     public float getYaw() {
/*  54 */       return this.yaw;
/*     */     }
/*     */     
/*     */     public float getPitch() {
/*  58 */       return this.pitch;
/*     */     }
/*     */     
/*     */     public BlockPos toBlockPos() {
/*  62 */       return new BlockPos((int)this.x, (int)this.y, (int)this.z);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  67 */       return String.format("%s: %.2f, %.2f, %.2f", new Object[] { this.world, Double.valueOf(this.x), Double.valueOf(this.y), Double.valueOf(this.z) });
/*     */     }
/*     */   }
/*     */   
/*     */   public LocationFlag(String name) {
/*  72 */     super(name);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Location getDefault() {
/*  78 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Location parseInput(String input) throws IllegalArgumentException {
/*  83 */     String[] parts = input.split(",");
/*  84 */     if (parts.length < 4) {
/*  85 */       throw new IllegalArgumentException("Location format: world,x,y,z[,yaw,pitch]");
/*     */     }
/*     */     try {
/*  88 */       String world = parts[0].trim();
/*  89 */       double x = Double.parseDouble(parts[1].trim());
/*  90 */       double y = Double.parseDouble(parts[2].trim());
/*  91 */       double z = Double.parseDouble(parts[3].trim());
/*  92 */       float yaw = (parts.length > 4) ? Float.parseFloat(parts[4].trim()) : 0.0F;
/*  93 */       float pitch = (parts.length > 5) ? Float.parseFloat(parts[5].trim()) : 0.0F;
/*  94 */       return new Location(world, x, y, z, yaw, pitch);
/*  95 */     } catch (NumberFormatException e) {
/*  96 */       throw new IllegalArgumentException("Invalid coordinates: " + input);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public JsonElement serialize(Location value) {
/* 102 */     JsonObject obj = new JsonObject();
/* 103 */     obj.addProperty("world", value.world);
/* 104 */     obj.addProperty("x", Double.valueOf(value.x));
/* 105 */     obj.addProperty("y", Double.valueOf(value.y));
/* 106 */     obj.addProperty("z", Double.valueOf(value.z));
/* 107 */     obj.addProperty("yaw", Float.valueOf(value.yaw));
/* 108 */     obj.addProperty("pitch", Float.valueOf(value.pitch));
/* 109 */     return (JsonElement)obj;
/*     */   }
/*     */ 
/*     */   
/*     */   public Location deserialize(JsonElement element) {
/* 114 */     JsonObject obj = element.getAsJsonObject();
/* 115 */     return new Location(obj
/* 116 */         .get("world").getAsString(), obj
/* 117 */         .get("x").getAsDouble(), obj
/* 118 */         .get("y").getAsDouble(), obj
/* 119 */         .get("z").getAsDouble(), 
/* 120 */         obj.has("yaw") ? obj.get("yaw").getAsFloat() : 0.0F, 
/* 121 */         obj.has("pitch") ? obj.get("pitch").getAsFloat() : 0.0F);
/*     */   }
/*     */ }


/* Location:              C:\Users\rockf\Desktop\server\SERVER1\mods\worldprotect-1.0.0 seguridad region.jar!\com\worldprotect\flag\LocationFlag.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */