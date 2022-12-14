import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { TiendaOnlineSharedModule } from 'app/shared';
import {
  ProductoComponent,
  ProductoDetailComponent,
  ProductoDeletePopupComponent,
  ProductoDeleteDialogComponent,
  productoRoute,
  productoPopupRoute
} from './';
import { CrearEditarDialogComponent } from './crear-editar-dialog/crear-editar-dialog.component';
import { VistaProductoComponent } from './vista-producto/vista-producto.component';
const ENTITY_STATES = [...productoRoute, ...productoPopupRoute];

@NgModule({
  imports: [TiendaOnlineSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProductoComponent,
    ProductoDetailComponent,
    ProductoDeleteDialogComponent,
    ProductoDeletePopupComponent,
    CrearEditarDialogComponent,
    VistaProductoComponent
  ],
  entryComponents: [
    ProductoComponent,
    ProductoDeleteDialogComponent,
    ProductoDeletePopupComponent,
    CrearEditarDialogComponent,
    VistaProductoComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TiendaOnlineProductoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
