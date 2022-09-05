package com.senko.framework.config.security;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * 自定义 权限的反序列化器
 *
 * @author senko
 * @date 2022/9/5 17:55
 */
public class CustomAuthorityDeserializer extends JsonDeserializer {

    @Override
    public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        LinkedList<GrantedAuthority> result = new LinkedList<>();

        ObjectMapper mapper = (ObjectMapper) p.getCodec();
        // 获取jsonNode
        JsonNode jsonNode = mapper.readTree(p);
        Iterator<JsonNode> elements = jsonNode.elements();

        while (elements.hasNext()) {
            JsonNode curElement = elements.next();
            JsonNode authority = curElement.get("authority");
            result.add(new SimpleGrantedAuthority(authority.asText()));
        }
        return result;
    }

}
