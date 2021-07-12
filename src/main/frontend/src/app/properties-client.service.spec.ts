import {TestBed} from '@angular/core/testing';

import {PropertiesClientServiceImpl} from './properties-client.service';
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";

describe('PropertiesClientServiceImpl', () => {
  let service: PropertiesClientServiceImpl;
  let httpMock: HttpTestingController;

  beforeEach(async () => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
      ],
    });
    await TestBed.compileComponents();

    service = TestBed.inject(PropertiesClientServiceImpl);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  })

  it('should get all properties from the backend', () => {
    const testData = Array({name: 'Wimpole Hall'});

    service.get().subscribe((result) => {
      expect(result).toEqual(testData);
    });

    const req = httpMock.expectOne("/api/properties");
    req.flush(testData);

    expect(req.request.method).toBe('GET');
  });
});
