CREATE TABLE bookings (id BIGSERIAL PRIMARY KEY, start_date DATE NOT NULL, end_date DATE NOT NULL, customer_id BIGINT NOT NULL, property_id BIGINT NOT NULL,
CONSTRAINT fk_booking_customer FOREIGN KEY (customer_id) REFERENCES customers(id),
CONSTRAINT fk_booking_property FOREIGN KEY (property_id) REFERENCES properties(id))