package consulting.baxter.holidaybooking.rest.model;

import consulting.baxter.holidaybooking.data.model.PropertyEntity;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class Property {
    String name;

    public static Property from(PropertyEntity propertyEntity) {
        return new Property(propertyEntity.getName());
    }
    public PropertyEntity toNew() {
        return new PropertyEntity(name);
    }
}
