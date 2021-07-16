import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {HttpClientModule} from "@angular/common/http";

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {PropertyListComponent} from './property-list/property-list.component';
import {PropertiesClientService, PropertiesClientServiceImpl} from "src/app/properties-client.service";
import {AvailabilityListComponent} from './availability-list/availability-list.component';
import {AvailabilityClientService, AvailabilityClientServiceImpl} from "src/app/availability-client.service";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MAT_DATE_LOCALE, MatOptionModule} from "@angular/material/core";
import {MatSelectModule} from "@angular/material/select";
import {PropertyMapComponent} from './property-map/property-map.component';
import {MatLuxonDateModule} from "ngx-material-luxon";

@NgModule({
  declarations: [
    AppComponent,
    PropertyListComponent,
    AvailabilityListComponent,
    PropertyMapComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    BrowserAnimationsModule,
    MatFormFieldModule,
    MatDatepickerModule,
    ReactiveFormsModule,
    MatLuxonDateModule,
    MatSelectModule,
    MatOptionModule
  ],
  providers: [{
    provide: MAT_DATE_LOCALE,
    useValue: navigator.language
  }, {
    provide: PropertiesClientService,
    useClass: PropertiesClientServiceImpl
  }, {
    provide: AvailabilityClientService,
    useClass: AvailabilityClientServiceImpl
  }],
  bootstrap: [AppComponent]
})
export class AppModule {
}
