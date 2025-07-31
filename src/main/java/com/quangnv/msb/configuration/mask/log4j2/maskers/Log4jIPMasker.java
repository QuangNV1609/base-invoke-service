package com.quangnv.msb.configuration.mask.log4j2.maskers;

import com.quangnv.msb.configuration.mask.masker.IPMasker;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

@Plugin(name = "IPMasker", category = Node.CATEGORY, elementType = Layout.ELEMENT_TYPE, printObject = true)
public class Log4jIPMasker extends IPMasker implements Log4jLogMasker {
    @PluginFactory
    public static Log4jIPMasker createLayout() {
        return newBuilder()
                .build();
    }

    @PluginBuilderFactory
    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder implements org.apache.logging.log4j.core.util.Builder<Log4jIPMasker> {
        @Override
        public Log4jIPMasker build() {
            return new Log4jIPMasker();
        }
    }
}
