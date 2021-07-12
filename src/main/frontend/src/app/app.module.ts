import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {HttpClientModule} from "@angular/common/http";

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import { PropertyListComponent } from './property-list/property-list.component';
import {PropertiesClientService, PropertiesClientServiceImpl} from "src/app/properties-client.service";

@NgModule({
  declarations: [
    AppComponent,
    PropertyListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [{
    provide: PropertiesClientService,
    useClass: PropertiesClientServiceImpl
  }],
  bootstrap: [AppComponent]
})
export class AppModule {
}
