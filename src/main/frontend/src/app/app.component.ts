import {Component} from '@angular/core';
import {Property} from "src/app/property";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  constructor() {
  }
  title = 'Holiday Booking';
  selectedProperty?: Property;
}
