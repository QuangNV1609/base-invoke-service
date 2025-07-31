package com.quangnv.msb.configuration.mask.log4j2.maskers;

import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.Plugin;

import java.util.ArrayList;

@Plugin(name = "Maskers", category = Node.CATEGORY, elementType = Layout.ELEMENT_TYPE, printObject = true)
public class Log4jMaskerCollection<T extends Log4jLogMasker> extends ArrayList<T> {
}
