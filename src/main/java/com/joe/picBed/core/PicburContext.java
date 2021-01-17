package com.joe.picBed.core;

import com.hujinwen.utils.FileUtils;
import com.joe.picBed.conf.PicburConf;
import com.joe.picBed.entity.exception.MissConfFileException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by hu-jinwen on 2020/8/22
 */
public class PicburContext implements ApplicationContextAware {

    /**
     * 默认配置文件名称
     */
    private static final String CONFIG_FILENAME = "config.yml";

    private static ApplicationContext springContext;

    private static PicburConf config;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        PicburContext.springContext = applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        return springContext.getBean(clazz);
    }

    public static Object getBean(String name) {
        return springContext.getBean(name);
    }

    public static PicburConf getConfig() {
        if (config == null) {
            loadConfig();
        }
        return config;
    }

    private static void loadConfig() {
        try {
            InputStream inputStream = new FileInputStream(CONFIG_FILENAME);
            config = FileUtils.loadYamlFile(inputStream, PicburConf.class);
        } catch (FileNotFoundException e) {
            throw new MissConfFileException("Missing configuration file named 'config.yml'. please check.", e);
        }
    }


}
