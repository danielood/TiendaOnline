import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { TiendaOnlineSharedModule } from 'app/shared';
import {
  VideoJuegosComponent,
  VideoJuegosDetailComponent,
  VideoJuegosUpdateComponent,
  VideoJuegosDeletePopupComponent,
  VideoJuegosDeleteDialogComponent,
  videoJuegosRoute,
  videoJuegosPopupRoute
} from './';

const ENTITY_STATES = [...videoJuegosRoute, ...videoJuegosPopupRoute];

@NgModule({
  imports: [TiendaOnlineSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    VideoJuegosComponent,
    VideoJuegosDetailComponent,
    VideoJuegosUpdateComponent,
    VideoJuegosDeleteDialogComponent,
    VideoJuegosDeletePopupComponent
  ],
  entryComponents: [VideoJuegosComponent, VideoJuegosUpdateComponent, VideoJuegosDeleteDialogComponent, VideoJuegosDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TiendaOnlineVideoJuegosModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
