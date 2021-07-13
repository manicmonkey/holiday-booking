import {Component, ContentChild, OnInit, TemplateRef} from '@angular/core';
import {PropertiesClientService} from "src/app/properties-client.service";
import {Property} from "src/app/property";

@Component({
  selector: 'property-list',
  templateUrl: './property-list.component.html'
})
export class PropertyListComponent implements OnInit {

  constructor(private propertiesClient: PropertiesClientService) {
  }

  properties: Array<Property> = Array()

  @ContentChild(TemplateRef) template!: TemplateRef<Property>;

  ngOnInit(): void {
    this.propertiesClient
      .get()
      .subscribe(resp => {
        console.log("Got properties", resp);
        this.properties.push(...resp);
      })
  }

}
