/*    */ package com.worldprotect.flag;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonPrimitive;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class DoubleFlag
/*    */   extends Flag<Double> {
/*    */   private final Double defaultValue;
/*    */   
/*    */   public DoubleFlag(String name) {
/* 12 */     super(name);
/* 13 */     this.defaultValue = null;
/*    */   }
/*    */   
/*    */   public DoubleFlag(String name, Double defaultValue) {
/* 17 */     super(name);
/* 18 */     this.defaultValue = defaultValue;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Double getDefault() {
/* 24 */     return this.defaultValue;
/*    */   }
/*    */ 
/*    */   
/*    */   public Double parseInput(String input) throws IllegalArgumentException {
/*    */     try {
/* 30 */       return Double.valueOf(Double.parseDouble(input));
/* 31 */     } catch (NumberFormatException e) {
/* 32 */       throw new IllegalArgumentException("Invalid number: " + input);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public JsonElement serialize(Double value) {
/* 38 */     return (JsonElement)new JsonPrimitive(value);
/*    */   }
/*    */ 
/*    */   
/*    */   public Double deserialize(JsonElement element) {
/* 43 */     return Double.valueOf(element.getAsDouble());
/*    */   }
/*    */ }


/* Location:              C:\Users\rockf\Desktop\server\SERVER1\mods\worldprotect-1.0.0 seguridad region.jar!\com\worldprotect\flag\DoubleFlag.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */