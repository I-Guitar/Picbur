package com.joe.picBed.core;

import com.hujinwen.utils.FileUtils;
import com.joe.picBed.conf.PicburConf;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by hu-jinwen on 2020/8/22
 */
public class PicburContext implements ApplicationContextAware {

    private static ApplicationContext springContext;

    private static PicburConf conf;

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

    public static PicburConf getConf() {
        if (conf == null) {
            conf = FileUtils.loadYamlFile(FileUtils.getResourceAsStream("config.yml"), PicburConf.class);
        }
        return conf;
    }


}
