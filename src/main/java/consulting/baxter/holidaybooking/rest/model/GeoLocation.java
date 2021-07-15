package consulting.baxter.holidaybooking.rest.model;

import consulting.baxter.holidaybooking.data.model.EmbeddableGeoLocation;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Value
@Builder
@Jacksonized
public class GeoLocation {
    BigDecimal longitude;
    BigDecimal latitude;

    static GeoLocation from(EmbeddableGeoLocation location) {
        return new GeoLocation(location.getLongitude(), location.getLatitude());
    }

    EmbeddableGeoLocation toEmbeddable() {
        return new EmbeddableGeoLocation(longitude, latitude);
    }
}
