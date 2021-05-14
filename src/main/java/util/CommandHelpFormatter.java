package util;

import joptsimple.OptionDescriptor;

import java.util.HashSet;
import java.util.Map;

public class CommandHelpFormatter implements joptsimple.HelpFormatter {
    public String format(Map<String, ? extends OptionDescriptor> options) {
        StringBuilder buffer = new StringBuilder();
        for (OptionDescriptor each : new HashSet<>(options.values())) {
            buffer.append(lineFor(each));
        }
        return buffer.toString();
    }

    private String lineFor(OptionDescriptor descriptor) {
        if (descriptor.representsNonOptions()) {
            return descriptor.argumentDescription() + '(' + descriptor.argumentTypeIndicator() + "): "
                    + descriptor.description() + System.getProperty("line.separator");
        }

        String line = descriptor.options().toString() + ": description = " + descriptor.description() +
                ", required = " + descriptor.isRequired() +
                ", accepts arguments = " + descriptor.acceptsArguments() +
                ", requires argument = " + descriptor.requiresArgument() +
                ", argument description = " + descriptor.argumentDescription() +
                ", argument type indicator = " + descriptor.argumentTypeIndicator() +
                ", default values = " + descriptor.defaultValues() +
                System.getProperty("line.separator");
        return line;
    }
}
