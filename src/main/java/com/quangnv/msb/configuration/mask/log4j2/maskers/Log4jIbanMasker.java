package com.quangnv.msb.configuration.mask.log4j2.maskers;

import com.quangnv.msb.configuration.mask.masker.IbanMasker;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

@Plugin(name = "IbanMasker", category = Node.CATEGORY, elementType = Layout.ELEMENT_TYPE, printObject = true)
public class Log4jIbanMasker extends IbanMasker implements Log4jLogMasker {
    public Log4jIbanMasker(String[] countryCodes) {
        if (countryCodes != null && countryCodes.length > 0) {
            this.populateCountriesToCheck(countryCodes);
        }
    }

    @PluginFactory
    public static Log4jIbanMasker createLayout() {
        return newBuilder()
                .build();
    }

    @PluginBuilderFactory
    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder implements org.apache.logging.log4j.core.util.Builder<Log4jIbanMasker> {

        @PluginBuilderAttribute
        private String countryCodes = null;

        public Builder withCountryCodes(String countryCodes) {
            if (countryCodes != null) {
                this.countryCodes = countryCodes;
            }

            return this;
        }

        @Override
        public Log4jIbanMasker build() {
            String[] countryCodesSplit = null;
            if (countryCodes != null) {
                countryCodesSplit = StringUtils.split(countryCodes, '|');
            }

            return new Log4jIbanMasker(countryCodesSplit);
        }
    }
}
