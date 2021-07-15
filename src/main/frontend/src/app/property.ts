export interface Location {
  longitude: number,
  latitude: number
}

export interface Property {
  name: string,
  address: string,
  location: Location
}
