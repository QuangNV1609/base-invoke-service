package com.quangnv.msb.configuration.mask.log4j2;

import com.quangnv.msb.configuration.mask.MaskingConverter;
import com.quangnv.msb.configuration.mask.log4j2.maskers.Log4jLogMasker;
import com.quangnv.msb.configuration.mask.masker.LogMasker;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.apache.logging.log4j.core.impl.MutableLogEvent;
import org.apache.logging.log4j.core.layout.AbstractStringLayout;

import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

@Plugin(name = "MaskingLayout", category = Node.CATEGORY, elementType = Layout.ELEMENT_TYPE, printObject = true)
public class Log4jMaskingLayout extends AbstractStringLayout {
    private final AbstractStringLayout layout;

    private final MaskingConverter maskingConverter;

    private final Character maskChar;

    protected Log4jMaskingLayout(AbstractStringLayout layout, List<? extends Log4jLogMasker> maskers, Character maskChar) {
        super(Charset.defaultCharset());
        this.layout = layout;
        this.maskChar = maskChar;

        this.maskingConverter = new MaskingConverter();
        if (maskers != null && !maskers.isEmpty()) {
            maskingConverter.setMaskers(maskers.stream().map(masker -> {
                LogMasker logMasker = masker.toLogMasker();
                logMasker.setMaskChar(this.maskChar);
                return logMasker;
            }).collect(Collectors.toList()));
        } else {
            maskingConverter.init(null);
        }
    }

    @PluginFactory
    public static Log4jMaskingLayout createLayout(AbstractStringLayout layout) {
        return newBuilder()
                .withLayout(layout)
                .build();

    }

    @PluginBuilderFactory
    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public String toSerializable(LogEvent event) {
        MutableLogEvent newEvent = new MutableLogEvent();
        newEvent.initFrom(event);
        StringBuilder masked = new StringBuilder(event.getMessage().getFormattedMessage());
        this.maskingConverter.mask(masked);

        newEvent.setMessage(new MutableLogEvent(masked, newEvent.getParameters()));
        return this.layout.toSerializable(newEvent);
    }

    public static class Builder implements org.apache.logging.log4j.core.util.Builder<Log4jMaskingLayout> {

        @PluginElement("AppenderRef")
        @Required(message = "No appender references provided to AsyncAppender")
        private AbstractStringLayout layout;

        @PluginElement("Maskers")
        private List<? extends Log4jLogMasker> maskers;

        @PluginBuilderAttribute
        private Character maskChar = '*';

        public Builder withLayout(AbstractStringLayout layout) {
            this.layout = layout;
            return this;
        }

        public Builder withMasker(List<Log4jLogMasker> maskers) {
            this.maskers = maskers;
            return this;
        }

        public Builder withMaskChar(Character maskChar) {
            if (maskChar != null) {
                this.maskChar = maskChar;
            }
            return this;
        }

        @Override
        public Log4jMaskingLayout build() {
            return new Log4jMaskingLayout(layout, maskers, maskChar);
        }
    }
}
