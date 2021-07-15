import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PropertyMapComponent } from './property-map.component';

describe('PropertyMapComponent', () => {
  let component: PropertyMapComponent;
  let fixture: ComponentFixture<PropertyMapComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PropertyMapComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PropertyMapComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  //todo implement real tests
  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
