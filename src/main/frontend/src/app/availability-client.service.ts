import { Injectable } from '@angular/core';
import {AvailableDay} from "src/app/available-day";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Property} from "src/app/property";


export abstract class AvailabilityClientService {
  //todo add error handling
  abstract get(property: Property, checkIn: string, checkOut: string): Observable<Array<AvailableDay>>
}

@Injectable({
  providedIn: 'root'
})
export class AvailabilityClientServiceImpl implements AvailabilityClientService {
  constructor(private httpClient: HttpClient) {
  }

  get(property: Property, checkIn: string, checkOut: string): Observable<Array<AvailableDay>> {
    return this.httpClient.get<Array<AvailableDay>>("/api/availability", {
      params: {
        property: property.name,
        start: checkIn,
        end: checkOut,
      }
    });
  }
}
