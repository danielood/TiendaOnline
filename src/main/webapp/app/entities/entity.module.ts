import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'producto',
        loadChildren: './producto/producto.module#TiendaOnlineProductoModule'
      },
      {
        path: 'cliente',
        loadChildren: './cliente/cliente.module#TiendaOnlineClienteModule'
      },
      {
        path: 'venta',
        loadChildren: './venta/venta.module#TiendaOnlineVentaModule'
      },
      {
        path: 'video-juegos',
        loadChildren: './video-juegos/video-juegos.module#TiendaOnlineVideoJuegosModule'
      },
      {
        path: 'compannia',
        loadChildren: './compannia/compannia.module#TiendaOnlineCompanniaModule'
      },
      {
        path: 'categoria',
        loadChildren: './categoria/categoria.module#TiendaOnlineCategoriaModule'
      },
      {
        path: 'plataforma',
        loadChildren: './plataforma/plataforma.module#TiendaOnlinePlataformaModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TiendaOnlineEntityModule {}
