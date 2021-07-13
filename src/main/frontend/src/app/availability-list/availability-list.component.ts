import {Component, ContentChild, Input, OnChanges, TemplateRef} from '@angular/core';
import {AvailabilityClientService} from "src/app/availability-client.service";
import {AvailableDay} from "src/app/available-day";
import {Property} from "src/app/property";

@Component({
  selector: 'availability-list',
  templateUrl: './availability-list.component.html'
})
export class AvailabilityListComponent implements OnChanges {

  constructor(private availabilityClient: AvailabilityClientService) {
  }

  @Input() property?: Property

  availability: Array<AvailableDay> = Array()

  @ContentChild(TemplateRef) template!: TemplateRef<Property>;

  ngOnChanges(): void {
    if (this.property)
      this.availabilityClient
        .get(this.property, '2021-08-01', '2021-08-11')
        .subscribe(availableDays => {
          this.availability = availableDays;
        });
  }
}
