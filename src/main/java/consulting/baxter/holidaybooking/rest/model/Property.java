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
    String address;
    GeoLocation location;

    public static Property from(PropertyEntity propertyEntity) {
        return new Property(propertyEntity.getName(), propertyEntity.getAddress(), GeoLocation.from(propertyEntity.getLocation()));
    }
    public PropertyEntity toNew() {
        return new PropertyEntity(name, address, location.toEmbeddable());
    }
}
