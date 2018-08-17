package com.jaiswalakshay.config;

public class TenantContext {

    private static final ThreadLocal<String> CONTEXT = new ThreadLocal<>();

    public static String getTenantId() {
        return CONTEXT.get();
    }

    public static void setTenantId(String tenantId) {
        CONTEXT.set(tenantId);
    }

    public static void clear() {
        CONTEXT.remove();
    }
}