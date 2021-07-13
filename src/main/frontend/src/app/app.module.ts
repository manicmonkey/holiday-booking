import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {HttpClientModule} from "@angular/common/http";

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import { PropertyListComponent } from './property-list/property-list.component';
import {PropertiesClientService, PropertiesClientServiceImpl} from "src/app/properties-client.service";
import { AvailabilityListComponent } from './availability-list/availability-list.component';
import {AvailabilityClientService, AvailabilityClientServiceImpl} from "src/app/availability-client.service";

@NgModule({
  declarations: [
    AppComponent,
    PropertyListComponent,
    AvailabilityListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [{
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
