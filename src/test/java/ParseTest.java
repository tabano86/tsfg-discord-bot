import joptsimple.OptionParser;
import joptsimple.OptionSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ParseTest {
    @Test
    public void test() {
        OptionParser parser = new OptionParser();
        parser.accepts("quote");
        parser.accepts("ticker").requiredIf("quote").withRequiredArg();
        OptionSet optionSet = parser.parse("-quote", "--ticker", "amd");

        Assertions.assertTrue(optionSet.has("quote"));
        Assertions.assertTrue(optionSet.hasArgument("ticker"));
        Assertions.assertTrue(optionSet.has("ticker"));
    }
}
