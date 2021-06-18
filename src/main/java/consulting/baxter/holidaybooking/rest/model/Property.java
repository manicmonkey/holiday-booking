package consulting.baxter.holidaybooking.rest.model;

import consulting.baxter.holidaybooking.data.model.PropertyEntity;
import lombok.Value;

@Value
public class Property {
    String name;

    public static Property from(PropertyEntity propertyEntity) {
        return new Property(propertyEntity.getName());
    }
    public PropertyEntity toNew() {
        return new PropertyEntity(name);
    }
}
