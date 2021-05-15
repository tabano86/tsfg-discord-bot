package util;

import joptsimple.OptionDescriptor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class CommandHelpFormatter implements joptsimple.HelpFormatter {
    public String format(Map<String, ? extends OptionDescriptor> options) {
        StringBuilder buffer = new StringBuilder();
        for (OptionDescriptor each : new HashSet<>(options.values())) {
            String line = lineFor(each);
            if (StringUtils.isNotEmpty(line) && line.replaceAll("[\n\t\r]|\\(null\\):| ", "").length() > 0) {
                buffer.append(lineFor(each));
            }
        }
        return buffer.toString();
    }

    private String lineFor(OptionDescriptor descriptor) {
        if (StringUtils.isEmpty(descriptor.options().toString()) || descriptor.options().toString().equals("[help]")) {
            return null;
        }

        if (descriptor.representsNonOptions()) {
            return descriptor.argumentDescription() + '(' + descriptor.argumentTypeIndicator() + "): "
                    + descriptor.description() + System.getProperty("line.separator");
        }

        StringBuilder line = new StringBuilder();
        line.append("\n").append(descriptor.options().toString()).append(" - ").append(descriptor.description());
        line.append("\n\trequired: ").append(descriptor.isRequired() ? "Y" : "N");
        line.append("\n\taccepts arguments: ").append(descriptor.acceptsArguments() ? "Y" : "N");
        line.append("\n\trequires argument: ").append(descriptor.requiresArgument() ? "Y" : "N");

        if (StringUtils.isNotEmpty(descriptor.argumentDescription())) {
            line.append("\n\targument description: ").append(descriptor.argumentDescription());
        }
        if (CollectionUtils.isNotEmpty(descriptor.defaultValues())) {
            line.append("\n\tdefault values: ").append(Arrays.toString(new List[]{descriptor.defaultValues()}));
        }
        return line.toString();
    }
}
