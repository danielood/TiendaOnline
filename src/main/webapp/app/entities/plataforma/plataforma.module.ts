import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { TiendaOnlineSharedModule } from 'app/shared';
import {
  PlataformaComponent,
  PlataformaDetailComponent,
  PlataformaUpdateComponent,
  PlataformaDeletePopupComponent,
  PlataformaDeleteDialogComponent,
  plataformaRoute,
  plataformaPopupRoute
} from './';

const ENTITY_STATES = [...plataformaRoute, ...plataformaPopupRoute];

@NgModule({
  imports: [TiendaOnlineSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PlataformaComponent,
    PlataformaDetailComponent,
    PlataformaUpdateComponent,
    PlataformaDeleteDialogComponent,
    PlataformaDeletePopupComponent
  ],
  entryComponents: [PlataformaComponent, PlataformaUpdateComponent, PlataformaDeleteDialogComponent, PlataformaDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TiendaOnlinePlataformaModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
