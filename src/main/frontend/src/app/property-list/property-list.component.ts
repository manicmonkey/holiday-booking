import { Component, OnInit } from '@angular/core';
import {PropertiesClientService} from "src/app/properties-client.service";
import {Property} from "src/app/property";

@Component({
  selector: 'app-property-list',
  templateUrl: './property-list.component.html',
  styleUrls: ['./property-list.component.css']
})
export class PropertyListComponent implements OnInit {

  constructor(private propertiesClient: PropertiesClientService) { }

  properties: Array<Property> = Array()

  ngOnInit(): void {
    this.propertiesClient
      .get()
      .subscribe(resp => {
        console.log("Got properties", resp)
        this.properties.push(...resp)
      })
  }

}
