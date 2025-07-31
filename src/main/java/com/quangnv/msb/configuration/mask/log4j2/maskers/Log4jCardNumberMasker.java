package com.quangnv.msb.configuration.mask.log4j2.maskers;

import com.quangnv.msb.configuration.mask.masker.CardNumberMasker;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

@Plugin(name = "CardNumberMasker", category = Node.CATEGORY, elementType = Layout.ELEMENT_TYPE, printObject = true)
public class Log4jCardNumberMasker extends CardNumberMasker implements Log4jLogMasker {
    public Log4jCardNumberMasker(int startKeep, int endKeep, boolean luhnCheck) {
        this.startKeep = startKeep;
        this.endKeep = endKeep;
        this.luhnCheck = luhnCheck;
    }

    @PluginFactory
    public static Log4jCardNumberMasker createLayout() {
        return newBuilder()
                .build();
    }

    @PluginBuilderFactory
    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder implements org.apache.logging.log4j.core.util.Builder<Log4jCardNumberMasker> {
        @PluginBuilderAttribute
        private int startKeep = 6;

        @PluginBuilderAttribute
        private int endKeep = 4;

        @PluginBuilderAttribute
        private boolean luhnCheck = false;

        public Builder withStartKeep(Integer startKeep) {
            if (startKeep != null && startKeep > 0 && startKeep < 7) {
                this.startKeep = startKeep;
            }

            return this;
        }

        public Builder withEndKeep(Integer endKeep) {
            if (endKeep != null && endKeep > 0 && endKeep < 9) {
                this.endKeep = endKeep;
            }

            return this;
        }

        public Builder withLuhnCheck(Boolean luhnCheck) {
            if (luhnCheck != null) {
                this.luhnCheck = luhnCheck;
            }
            return this;
        }

        @Override
        public Log4jCardNumberMasker build() {
            return new Log4jCardNumberMasker(startKeep, endKeep, luhnCheck);
        }
    }
}
