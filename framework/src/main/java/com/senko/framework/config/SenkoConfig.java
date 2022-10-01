package com.senko.framework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 全局自定义配置
 *
 * @author senko
 * @date 2022/9/4 12:29
 */
@Component
@ConfigurationProperties(prefix = "senko")
public class SenkoConfig {

    /**
     * 启用Swagger
     */
    private boolean swaggerEnabled = false;

    /**
     * 项目版本
     */
    private String version;

    /**
     * 项目名
     */
    private String projectName = "";

    /**
     * 项目描述
     */
    private String projectDescription = "";

    /**
     * 密码失败重试次数限制
     */
    private Integer retryLimit = 5;

    /**
     * 密码失败重试时间限制 单位：秒
     */
    private Integer retryInterval = 300;

    private Author author = new Author();

    public Author getAuthor() {
        return author;
    }

    /**
     * 网站作者信息
     */
    public static class Author {

        /**
         * 作者名
         */
        private String name = "";

        /**
         * 联系地址
         */
        private String url = "";

        /**
         * 联系邮箱
         */
        private String email = "";

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    public Upload upload = new Upload();

    public Upload getUpload() {
        return upload;
    }

    public static class Upload {

        /**
         * 上传策略
         */
        private String strategy = "local";

        private Local local = new Local();

        public Local getLocal() {
            return local;
        }

        public static class Local {
            /**
             * 存储路径，文件的本地存储路径，以/结尾
             */
            private String path = "C:\\usr\\local\\upload\\";

            /**
             * 访问路径
             * 需使用Nginx反向代理以访问资源，默认83端口
             */
            private String url = "http://localhost:83/";

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public String getStrategy() {
            return strategy;
        }

        public void setStrategy(String strategy) {
            this.strategy = strategy;
        }

    }



    public Integer getRetryLimit() {
        return retryLimit;
    }

    public void setRetryLimit(Integer retryLimit) {
        this.retryLimit = retryLimit;
    }

    public Integer getRetryInterval() {
        return retryInterval;
    }

    public void setRetryInterval(Integer retryInterval) {
        this.retryInterval = retryInterval;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public boolean isSwaggerEnabled() {
        return swaggerEnabled;
    }

    public void setSwaggerEnabled(boolean swaggerEnabled) {
        this.swaggerEnabled = swaggerEnabled;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

}
