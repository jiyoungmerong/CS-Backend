package com.project.ity.global.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.security.Principal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PrincipalUtil {
    public static String toUserId(Principal principal) {
        return principal.getName().split(":")[0];
    }

    public static String toName(Principal principal) {
        String[] splitPrincipal = principal.getName().split(":");
        if (splitPrincipal.length < 2) {
            return "anonymous";
        }
        return splitPrincipal[1];
    }

    public static String strToEmail(String principalStr) {
        return principalStr.split(":")[0];
    }
    public static String strToName(String principalStr) {
        String[] splitedPrincipal = principalStr.split(":");
        if (splitedPrincipal.length < 2) {
            return "anonymous";
        }
        return splitedPrincipal[1];
    }
}
