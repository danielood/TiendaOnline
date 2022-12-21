import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';
import { TiendaOnlineSharedModule } from 'app/shared';
import { VentaComponent, VentaDetailComponent, VentaDeletePopupComponent, VentaDeleteDialogComponent, ventaRoute } from './';
import { CrearEditarDialogComponent } from './crear-editar-dialog/crear-editar-dialog.component';
import { VistaVentaComponent } from './vista-venta/vista-venta.component';
const ENTITY_STATES = [...ventaRoute];
@NgModule({
  imports: [TiendaOnlineSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    VentaComponent,
    VentaDetailComponent,
    VentaDeleteDialogComponent,
    VentaDeletePopupComponent,
    CrearEditarDialogComponent,
    VistaVentaComponent
  ],
  entryComponents: [VentaComponent, VentaDeleteDialogComponent, VentaDeletePopupComponent, CrearEditarDialogComponent, VistaVentaComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TiendaOnlineVentaModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
