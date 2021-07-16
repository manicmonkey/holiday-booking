import {Component, Input, OnChanges, SimpleChanges} from '@angular/core';
import {Property} from "src/app/property";
import {layer, Map, proj, source, View} from "openlayers";
import OSM = source.OSM;
import Tile = layer.Tile;
import fromLonLat = proj.fromLonLat;

export interface LonLat {
  longitude: number,
  latitude: number
}

@Component({
  selector: 'app-property-map',
  templateUrl: './property-map.component.html'
})
export class PropertyMapComponent implements OnChanges {

  @Input()
  property?: Property;

  @Input()
  defaultZoomLevel: number = 6

  @Input()
  propertyFocusZoomLevel: number = 8

  @Input()
  defaultMapCenter: LonLat = {
    longitude: 0.1218,
    latitude: 52.2053
  }

  map?: Map;

  ngOnInit(): void {
    this.map = new Map({
      target: 'map',
      layers: [
        new Tile({
          source: new OSM()
        })
      ],
      view: new View({
        center: fromLonLat(this.property
          ? [this.property.location.longitude, this.property.location.latitude]
          : [this.defaultMapCenter.longitude, this.defaultMapCenter.latitude]),
        zoom: this.defaultZoomLevel
      })
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (!this.property || !this.map?.getView()) return;

    const view = this.map.getView();
    view.setCenter(fromLonLat([this.property.location.longitude, this.property.location.latitude]))
    view.setZoom(this.propertyFocusZoomLevel);
  }

}
