package consulting.baxter.holidaybooking.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.joda.money.Money;

import java.io.IOException;

public class MoneyDeserializer extends StdDeserializer<Money> {
    public MoneyDeserializer() {
        super(Money.class);
    }

    @Override
    public Money deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return Money.parse(jp.readValueAs(String.class));
    }
}
