package util;

import joptsimple.OptionDescriptor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Map;

public class CommandHelpFormatter implements joptsimple.HelpFormatter {
    public String format(Map<String, ? extends OptionDescriptor> options) {
        StringBuilder buffer = new StringBuilder();
        for (OptionDescriptor each : new HashSet<>(options.values())) {
            String line = lineFor(each);
            if (StringUtils.isNotEmpty(line)) {
                buffer.append(lineFor(each));
            }
        }
        return buffer.toString();
    }

    private String lineFor(OptionDescriptor descriptor) {
        if (descriptor.representsNonOptions()) {
            return descriptor.argumentDescription() + '(' + descriptor.argumentTypeIndicator() + "): "
                    + descriptor.description() + System.getProperty("line.separator");
        }

        StringBuilder line = new StringBuilder();
        line.append("\n").append(descriptor.options().toString()).append(" - ").append(descriptor.description());
        line.append("\n\trequired: ").append(descriptor.isRequired());
        line.append("\n\taccepts arguments: ").append(descriptor.acceptsArguments());
        line.append("\n\trequires argument: ").append(descriptor.requiresArgument());

        if (StringUtils.isNotEmpty(descriptor.argumentDescription())) {
            line.append("\n\targument description: ").append(descriptor.argumentDescription());
        }
        if (CollectionUtils.isNotEmpty(descriptor.defaultValues())) {
            line.append("\n\tdefault values: ");
        }
        return line.toString();
    }
}
