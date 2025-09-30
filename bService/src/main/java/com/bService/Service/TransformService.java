package com.bService.Service;

import org.springframework.stereotype.Service;

@Service
public class TransformService {
    public String toUpper(String text) {
        StringBuilder sb = new StringBuilder(text);
        for (int i = 0; i < sb.length(); i++) {
            sb.setCharAt(i, Character.toUpperCase(sb.charAt(i)));
        }
        return sb.toString();
    }
}
