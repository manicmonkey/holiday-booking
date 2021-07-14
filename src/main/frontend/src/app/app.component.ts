import {Component} from '@angular/core';
import {Property} from "src/app/property";
import {FormControl, FormGroup} from "@angular/forms";

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

  range = new FormGroup({
    start: new FormControl(),
    end: new FormControl()
  });
}
