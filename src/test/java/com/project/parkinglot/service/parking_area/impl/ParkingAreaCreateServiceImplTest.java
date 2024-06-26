package com.project.parkinglot.service.parking_area.impl;

import com.project.parkinglot.base.BaseServiceTest;
import com.project.parkinglot.builder.ParkingAreaCreateRequestBuilder;
import com.project.parkinglot.builder.PriceListBuilder;
import com.project.parkinglot.exception.parkingarea.ParkingAreaAlreadyExistException;
import com.project.parkinglot.model.ParkingArea;
import com.project.parkinglot.model.PriceList;
import com.project.parkinglot.model.dto.request.parking_area.ParkingAreaCreateRequest;
import com.project.parkinglot.model.dto.request.priceList.PriceListCreateRequest;
import com.project.parkinglot.model.entity.ParkingAreaEntity;
import com.project.parkinglot.model.mapper.parking_area.ParkingAreaCreateRequestToParkingAreaEntityMapper;
import com.project.parkinglot.model.mapper.parking_area.ParkingAreaEntityToParkingAreaMapper;
import com.project.parkinglot.repository.ParkingAreaRepository;
import com.project.parkinglot.service.priceList.PriceListCreateService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

class ParkingAreaCreateServiceImplTest extends BaseServiceTest {


    @InjectMocks
    private ParkingAreaCreateServiceImpl parkingAreaCreateService;

    @Mock
    private ParkingAreaRepository parkingAreaRepository;

    @Mock
    private PriceListCreateService priceListCreateService;

    private final ParkingAreaCreateRequestToParkingAreaEntityMapper parkingAreaCreateRequestToParkingAreaEntityMapper =
            ParkingAreaCreateRequestToParkingAreaEntityMapper.initialize();

    private final ParkingAreaEntityToParkingAreaMapper parkingAreaEntityToParkingAreaMapper =
            ParkingAreaEntityToParkingAreaMapper.initialize();

    @Test
    public void givenValidParkingAreaCreateRequest_whenCreateParkingArea_thenReturnParkingAreaDomainModel() {

        // Given
        final ParkingAreaCreateRequest mockValidParkingAreaCreateRequest = new ParkingAreaCreateRequestBuilder()
                .withValidFields()
                .build();

        final PriceListCreateRequest mockPriceListCreateRequest = mockValidParkingAreaCreateRequest.getPriceListRequest();
        final PriceList mockPriceList = new PriceListBuilder().withValidFields().build();

        final ParkingAreaEntity mockParkingAreaEntity = parkingAreaCreateRequestToParkingAreaEntityMapper
                .map(mockValidParkingAreaCreateRequest);

        final ParkingArea mockParkingAreaDomainModel = parkingAreaEntityToParkingAreaMapper
                .map(mockParkingAreaEntity);


        // When
        Mockito.when(parkingAreaRepository.existsParkingAreaEntitiesByNameAndLocation(
                Mockito.anyString(),
                Mockito.anyString())
        ).thenReturn(Boolean.FALSE);

        Mockito.when(parkingAreaRepository.save(Mockito.any(ParkingAreaEntity.class)))
                .thenReturn(mockParkingAreaEntity);

        Mockito.when(priceListCreateService.createPriceList(mockPriceListCreateRequest))
                .thenReturn(mockPriceList);


        // Then
        ParkingArea response = parkingAreaCreateService
                .createParkingArea(mockValidParkingAreaCreateRequest);

        Assertions.assertEquals(
                mockParkingAreaDomainModel.getName(),
                response.getName()
        );

        Assertions.assertEquals(
                mockValidParkingAreaCreateRequest.getLocation(),
                response.getLocation()
        );

        // Verify
        Mockito.verify(
                parkingAreaRepository,
                Mockito.times(1)
        ).save(Mockito.any(ParkingAreaEntity.class));

        Mockito.verify(
                parkingAreaRepository,
                Mockito.times(1)
        ).existsParkingAreaEntitiesByNameAndLocation(Mockito.anyString(), Mockito.anyString());

    }

    @Test
    public void givenNotValidParkingAreaCreateRequest_whenCreateParkingArea_thenThrowsException() {

        // Given
        final ParkingAreaCreateRequest mockNotValidParkingAreaCreateRequest = new ParkingAreaCreateRequestBuilder()
                .withValidFields()
                .build();

        // When
        Mockito.when(parkingAreaRepository.existsParkingAreaEntitiesByNameAndLocation(
                Mockito.anyString(),
                Mockito.anyString())
        ).thenReturn(Boolean.TRUE);

        Assertions.assertThrowsExactly(
                ParkingAreaAlreadyExistException.class,
                () -> parkingAreaCreateService.createParkingArea(mockNotValidParkingAreaCreateRequest)
        );

        // Verify
        Mockito.verify(
                parkingAreaRepository,
                Mockito.times(1)
        ).existsParkingAreaEntitiesByNameAndLocation(Mockito.anyString(), Mockito.anyString());

        Mockito.verify(
                parkingAreaRepository,
                Mockito.times(0)
        ).save(Mockito.any(ParkingAreaEntity.class));

    }

}
