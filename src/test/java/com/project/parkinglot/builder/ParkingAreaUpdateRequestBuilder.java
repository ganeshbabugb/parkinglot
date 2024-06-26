package com.project.parkinglot.builder;

import com.github.javafaker.Faker;
import com.project.parkinglot.model.dto.request.parking_area.ParkingAreaUpdateRequest;

public class ParkingAreaUpdateRequestBuilder extends BaseBuilder<ParkingAreaUpdateRequest> {

    public ParkingAreaUpdateRequestBuilder() {
        super(ParkingAreaUpdateRequest.class);
    }

    public ParkingAreaUpdateRequestBuilder withValidFields() {
        final Faker faker = new Faker();
        return this
                .withCapacity(faker.number().numberBetween(1, 23));
    }

    public ParkingAreaUpdateRequestBuilder withCapacity(
            final Integer capacity
    ) {
        data.setCapacity(capacity);
        return this;
    }

    @Override
    public ParkingAreaUpdateRequest build() {
        return super.build();
    }

}
