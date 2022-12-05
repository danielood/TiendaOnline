import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { TiendaOnlineSharedModule } from 'app/shared';
import {
  CompanniaComponent,
  CompanniaDetailComponent,
  CompanniaUpdateComponent,
  CompanniaDeletePopupComponent,
  CompanniaDeleteDialogComponent,
  companniaRoute,
  companniaPopupRoute
} from './';

const ENTITY_STATES = [...companniaRoute, ...companniaPopupRoute];

@NgModule({
  imports: [TiendaOnlineSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CompanniaComponent,
    CompanniaDetailComponent,
    CompanniaUpdateComponent,
    CompanniaDeleteDialogComponent,
    CompanniaDeletePopupComponent
  ],
  entryComponents: [CompanniaComponent, CompanniaUpdateComponent, CompanniaDeleteDialogComponent, CompanniaDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TiendaOnlineCompanniaModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
