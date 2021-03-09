package com.chone.fightpet.common;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * Create 2021-03-02 16:56
 * ConfigUtils
 * @author chone
 */
@Slf4j
public class ConfigUtils {
    // 配置文件目录
    private final static String CONFIG_PATH = "C:\\app\\config.properties";
    private final File configFile;

    public ConfigUtils() {
        this(CONFIG_PATH);
    }

    public ConfigUtils(String configPath) {
        this.configFile = new File(configPath);
        create();
    }

    public void create() {
        try {
            if (!configFile.exists()) {
                File parentFile = configFile.getParentFile();
                if (!parentFile.exists()) {
                    if (parentFile.mkdirs()) {
                        log.info("The configuration directory created[{}]", parentFile.getPath());
                    }
                }
                if (configFile.createNewFile()) {
                    log.info("The configuration file created");
                }
            }
        } catch (IOException e) {
            log.warn("Create IOException");
        }
    }

    public Properties load() {
        Properties properties = new Properties();
        // 创建输入流
        FileInputStream fis_stm = null;
        // 缓存读
        BufferedReader buff_read = null;
        try {
            fis_stm = new FileInputStream(this.configFile);
            buff_read = new BufferedReader(new InputStreamReader(fis_stm, StandardCharsets.UTF_8));
            properties.load(buff_read);
            return properties;
        } catch (FileNotFoundException e) {
            log.warn("FileNotFoundException" + e);
        } catch (IOException e) {
            log.warn("IOException");
        } finally {
            if (buff_read != null) {
                try {
                    fis_stm.close();
                    buff_read.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public void store(Properties prop) {
        if (prop == null) {
            log.warn("The Properties Is NULL");
            return;
        }
        FileOutputStream fos_stm = null;
        OutputStreamWriter os_wrt = null;
        try {
            fos_stm = new FileOutputStream(this.configFile);
            os_wrt = new OutputStreamWriter(fos_stm, StandardCharsets.UTF_8);
            prop.store(os_wrt, "The New properties file");
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException:" + e);
        } catch (IOException e) {
            System.out.println("IOException");
        } finally {
            if (os_wrt != null) {
                try {
                    fos_stm.close();
                    os_wrt.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
