import {Component} from '@angular/core';
import {HttpClient} from "@angular/common/http";

type Property = {
  name: string
}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  constructor(private httpClient: HttpClient) {
  }
  title = 'frontend';
  loadProperties() {
    this.httpClient
      .get<Property>("/api/properties")
      .subscribe(resp => {
        console.log("resp", resp)
      })
  }
}
