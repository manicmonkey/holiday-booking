import {TestBed} from '@angular/core/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {AppComponent} from './app.component';
import {AvailabilityListComponent} from "src/app/availability-list/availability-list.component";
import {PropertyListComponent} from "src/app/property-list/property-list.component";
import {PropertiesClientService} from "src/app/properties-client.service";
import {Observable, of} from "rxjs";
import {Property} from "src/app/property";
import {AvailabilityClientService} from "src/app/availability-client.service";
import {AvailableDay} from "src/app/available-day";

describe('AppComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule
      ],
      declarations: [
        AppComponent,
        AvailabilityListComponent,
        PropertyListComponent
      ],
      providers: [
        {
          provide: PropertiesClientService,
          useClass: MockPropertiesClientService
        },
        {
          provide: AvailabilityClientService,
          useClass: MockAvailabilityClientService
        }
      ]
    }).compileComponents();
  });

  it(`should have the title model 'Holiday Booking'`, () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app.title).toEqual('Holiday Booking');
  });

  it('should render the title', () => {
    const fixture = TestBed.createComponent(AppComponent);
    fixture.detectChanges();
    const compiled = fixture.nativeElement;
    expect(compiled.querySelector('h1').textContent).toContain('Welcome to Holiday Booking');
  });
});

class MockPropertiesClientService implements PropertiesClientService {
  get(): Observable<Array<Property>> {
    return of(Array({name: 'test-property', address: 'test-address', location: { longitude: 0, latitude: 0}}));
  }
}

class MockAvailabilityClientService implements AvailabilityClientService {
  get(property: Property, checkIn: string, checkOut: string): Observable<Array<AvailableDay>> {
    return of(Array());
  }
}
