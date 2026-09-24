/*    */ package com.worldprotect.flag;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonPrimitive;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class StringFlag
/*    */   extends Flag<String> {
/*    */   private final String defaultValue;
/*    */   
/*    */   public StringFlag(String name) {
/* 12 */     super(name);
/* 13 */     this.defaultValue = null;
/*    */   }
/*    */   
/*    */   public StringFlag(String name, String defaultValue) {
/* 17 */     super(name);
/* 18 */     this.defaultValue = defaultValue;
/*    */   }
/*    */   
/*    */   public StringFlag(String name, String defaultValue, RegionGroup defaultGroup) {
/* 22 */     super(name, defaultGroup);
/* 23 */     this.defaultValue = defaultValue;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public String getDefault() {
/* 29 */     return this.defaultValue;
/*    */   }
/*    */ 
/*    */   
/*    */   public String parseInput(String input) {
/* 34 */     return input;
/*    */   }
/*    */ 
/*    */   
/*    */   public JsonElement serialize(String value) {
/* 39 */     return (JsonElement)new JsonPrimitive(value);
/*    */   }
/*    */ 
/*    */   
/*    */   public String deserialize(JsonElement element) {
/* 44 */     return element.getAsString();
/*    */   }
/*    */ }


/* Location:              C:\Users\rockf\Desktop\server\SERVER1\mods\worldprotect-1.0.0 seguridad region.jar!\com\worldprotect\flag\StringFlag.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */