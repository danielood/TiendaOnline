import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { TiendaOnlineSharedModule } from 'app/shared';
import {
  ValoracionesComponent,
  ValoracionesDetailComponent,
  ValoracionesUpdateComponent,
  ValoracionesDeletePopupComponent,
  ValoracionesDeleteDialogComponent,
  valoracionesRoute,
  valoracionesPopupRoute
} from './';

const ENTITY_STATES = [...valoracionesRoute, ...valoracionesPopupRoute];

@NgModule({
  imports: [TiendaOnlineSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ValoracionesComponent,
    ValoracionesDetailComponent,
    ValoracionesUpdateComponent,
    ValoracionesDeleteDialogComponent,
    ValoracionesDeletePopupComponent
  ],
  entryComponents: [
    ValoracionesComponent,
    ValoracionesUpdateComponent,
    ValoracionesDeleteDialogComponent,
    ValoracionesDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TiendaOnlineValoracionesModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
