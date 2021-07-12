import {ComponentFixture, TestBed} from '@angular/core/testing';

import {PropertyListComponent} from './property-list.component';
import {PropertiesClientService} from "src/app/properties-client.service";
import {Observable, of} from "rxjs";
import {Property} from "src/app/property";

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
    expect(component.properties).toEqual(Array({name: 'test-property'}));
  });
});

class MockPropertiesClientService implements PropertiesClientService {
  get(): Observable<Array<Property>> {
    return of(Array({name: 'test-property'}));
  }
}
