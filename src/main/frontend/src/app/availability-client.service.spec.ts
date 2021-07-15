import {TestBed} from '@angular/core/testing';

import {AvailabilityClientServiceImpl} from './availability-client.service';
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";

describe('AvailabilityClientServiceImpl', () => {
  let service: AvailabilityClientServiceImpl;
  let httpMock: HttpTestingController;

  beforeEach(async () => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
      ],
    });
    await TestBed.compileComponents();

    service = TestBed.inject(AvailabilityClientServiceImpl);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  })

  it('should get availability from the backend', () => {
    //given
    const property = {name: 'test-property', address: 'test-address', location: { longitude: 0, latitude: 0}};
    const checkIn = '2021-08-01';
    const checkOut = '2021-08-02';
    const testResponse = Array({date: checkIn, rate: 'AUD 250.00'});

    //then
    service.get(property, checkIn, checkOut).subscribe((result) => {
      expect(result).toEqual(testResponse);
    });

    //then
    const req = (httpMock.match((req) => req.url == '/api/availability'))[0];
    req.flush(testResponse);

    expect(req.request.url).toBe('/api/availability');
    expect(req.request.method).toBe('GET');
    expect(req.request.params.get('property')).toBe(property.name);
    expect(req.request.params.get('start')).toBe(checkIn);
    expect(req.request.params.get('end')).toBe(checkOut);
  });});
