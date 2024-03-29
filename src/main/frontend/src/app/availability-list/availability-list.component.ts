import {Component, ContentChild, Input, OnChanges, TemplateRef} from '@angular/core';
import {AvailabilityClientService} from "src/app/availability-client.service";
import {AvailableDay} from "src/app/available-day";
import {Property} from "src/app/property";
import {DateTime} from "luxon";

@Component({
  selector: 'availability-list',
  templateUrl: './availability-list.component.html'
})
export class AvailabilityListComponent implements OnChanges {

  constructor(private availabilityClient: AvailabilityClientService) {
  }

  @Input() property?: Property
  @Input() checkIn?: DateTime
  @Input() checkOut?: DateTime

  availability: Array<AvailableDay> = Array()

  @ContentChild(TemplateRef) template!: TemplateRef<Property>;

  ngOnChanges(): void {
    if (this.property && this.checkIn && this.checkOut)
      this.availabilityClient
        .get(this.property, this.checkIn.toISODate(), this.checkOut.toISODate())
        .subscribe(availableDays => {
          this.availability = availableDays;
        });
  }
}
