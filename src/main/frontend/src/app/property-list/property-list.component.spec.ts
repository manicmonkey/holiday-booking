import {ComponentFixture, TestBed} from '@angular/core/testing';

import {PropertyListComponent} from './property-list.component';
import {PropertiesClientService} from "src/app/properties-client.service";
import {Observable, of} from "rxjs";
import {Property} from "src/app/property";

const TestProperty = {name: 'test-property', address: 'test-address', location: { longitude: 0, latitude: 0}};

describe('PropertyListComponent', () => {
  let component: PropertyListComponent;
  let fixture: ComponentFixture<PropertyListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PropertyListComponent ],
      providers: [
        PropertyListComponent,
        {
          provide: PropertiesClientService,
          useClass: MockPropertiesClientService
        }
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PropertyListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should populate properties model', () => {
    expect(component.properties).toEqual(Array(TestProperty));
  });
});

class MockPropertiesClientService implements PropertiesClientService {
  get(): Observable<Array<Property>> {
    return of(Array(TestProperty));
  }
}
