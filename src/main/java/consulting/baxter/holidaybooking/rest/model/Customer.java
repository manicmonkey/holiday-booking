package consulting.baxter.holidaybooking.rest.model;

import consulting.baxter.holidaybooking.data.model.CustomerEntity;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class Customer {
    String name;
    String email; //todo validation

    public static Customer from(CustomerEntity customerEntity) {
        return new Customer(customerEntity.getName(), customerEntity.getEmail());
    }
}
