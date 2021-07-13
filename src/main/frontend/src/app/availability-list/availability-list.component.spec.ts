import {ComponentFixture, TestBed} from '@angular/core/testing';

import {AvailabilityListComponent} from './availability-list.component';
import {AvailabilityClientService} from "src/app/availability-client.service";
import {Property} from "src/app/property";
import {Observable, of} from "rxjs";
import {AvailableDay} from "src/app/available-day";

describe('AvailabilityListComponent', () => {
  let component: AvailabilityListComponent;
  let fixture: ComponentFixture<AvailabilityListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AvailabilityListComponent ],
      providers: [
        {
          provide: AvailabilityClientService,
          useClass: MockAvailabilityClientService
        }
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AvailabilityListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  //todo add real tests
  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

class MockAvailabilityClientService implements AvailabilityClientService {
  get(property: Property, checkIn: string, checkOut: string): Observable<Array<AvailableDay>> {
    return of(Array());
  }
}
