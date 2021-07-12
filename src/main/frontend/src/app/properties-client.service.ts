import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Property} from "src/app/property";
import {Observable} from "rxjs";

export abstract class PropertiesClientService {
  abstract get(): Observable<Array<Property>>
}

@Injectable({
  providedIn: 'root',
})
export class PropertiesClientServiceImpl implements PropertiesClientService {

  constructor(private httpClient: HttpClient) {
  }

  get(): Observable<Array<Property>> {
    return this.httpClient.get<Array<Property>>("/api/properties")
  }
}



