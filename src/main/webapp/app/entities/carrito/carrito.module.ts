import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { TiendaOnlineSharedModule } from 'app/shared';
import {
  CarritoComponent,
  CarritoDetailComponent,
  CarritoUpdateComponent,
  CarritoDeletePopupComponent,
  CarritoDeleteDialogComponent,
  carritoRoute,
  carritoPopupRoute
} from './';

const ENTITY_STATES = [...carritoRoute, ...carritoPopupRoute];

@NgModule({
  imports: [TiendaOnlineSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CarritoComponent,
    CarritoDetailComponent,
    CarritoUpdateComponent,
    CarritoDeleteDialogComponent,
    CarritoDeletePopupComponent
  ],
  entryComponents: [CarritoComponent, CarritoUpdateComponent, CarritoDeleteDialogComponent, CarritoDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TiendaOnlineCarritoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
