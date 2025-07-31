package com.quangnv.msb.configuration.mask.log4j2.maskers;

import com.quangnv.msb.configuration.mask.masker.PasswordMasker;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

@Plugin(name = "PasswordMasker", category = Node.CATEGORY, elementType = Layout.ELEMENT_TYPE, printObject = true)
public class Log4jPasswordMasker extends PasswordMasker implements Log4jLogMasker {
    public Log4jPasswordMasker() {
        super();
    }

    public Log4jPasswordMasker(String[] keywords) {
        super(keywords);
    }

    @PluginFactory
    public static Log4jPasswordMasker createLayout() {
        return newBuilder()
                .build();
    }

    @PluginBuilderFactory
    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder implements org.apache.logging.log4j.core.util.Builder<Log4jPasswordMasker> {
        @PluginBuilderAttribute
        private String keywords = null;

        public Builder withKeywords(String keywords) {
            if (this.keywords != null) {
                this.keywords = keywords;
            }
            return this;
        }

        @Override
        public Log4jPasswordMasker build() {
            if (keywords != null) {
                String[] keywordsSplit = StringUtils.split(keywords, '|');
                return new Log4jPasswordMasker(keywordsSplit);
            }
            return new Log4jPasswordMasker();
        }
    }
}
