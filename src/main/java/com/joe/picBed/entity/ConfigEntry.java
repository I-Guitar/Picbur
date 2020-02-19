package com.joe.picBed.entity;

import com.joe.picBed.utils.Tools;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_SINGLETON;

/**
 * Created by joe on 2020/2/19
 * <p>
 * 全局参数配置类
 * 该类为单例（默认）
 */
@Component
@Scope(SCOPE_SINGLETON)
public class ConfigEntry {

    private String endpoint;

    private String accessKeyId;

    private String secretAccessKey;

    private String user;

    private String password;

    @PostConstruct
    private void init() throws IOException {
        final String resourceConfPath = Tools.getResourcePath() + "/conf.properties";

        Properties confProp = null;

        if (new File(resourceConfPath).exists()) {
            confProp = Tools.readFileForProp(resourceConfPath);
        } else if (new File("conf.properties").exists()) {
            confProp = Tools.readFileForProp("conf.properties");
        }

        if (confProp == null) {
            throw new RuntimeException("Can not find conf.properties.");
        }

        endpoint = confProp.getProperty("endpoint");
        accessKeyId = confProp.getProperty("accessKeyId");
        secretAccessKey = confProp.getProperty("secretAccessKey");
        user = confProp.getProperty("user");
        password = confProp.getProperty("password");

        if (containsEmpty(endpoint, accessKeyId, secretAccessKey, user, password)) {
            throw new RuntimeException("All conf parameters must not be null!");
        }
    }

    private boolean containsEmpty(String... strings) {
        for (String str : strings) {
            if (StringUtils.isEmpty(str)) {
                return true;
            }
        }
        return false;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public String getSecretAccessKey() {
        return secretAccessKey;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
