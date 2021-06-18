package consulting.baxter.holidaybooking.rest.model;

import consulting.baxter.holidaybooking.data.model.CustomerEntity;
import lombok.Value;

@Value
public class Customer {
    String name;
    String email; //todo validation

    public static Customer from(CustomerEntity customerEntity) {
        return new Customer(customerEntity.getName(), customerEntity.getEmail());
    }
}
