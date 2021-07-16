import { enableProdMode } from '@angular/core';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { AppModule } from './app/app.module';
import { environment } from './environments/environment';
import {Settings} from "luxon";

if (environment.production) {
  enableProdMode();
}

// Luxon seems to use en-US as the default locale (check es6/luxon.js#systemLocale:1729)
Settings.defaultLocale = navigator.language

platformBrowserDynamic().bootstrapModule(AppModule)
  .catch(err => console.error(err));
