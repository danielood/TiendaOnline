import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Venta } from 'app/shared/model/venta.model';
import { VentaService } from './venta.service';
import { VentaComponent } from './venta.component';
import { VentaDetailComponent } from './venta-detail.component';
import { VentaUpdateComponent } from './venta-update.component';
import { VentaDeletePopupComponent } from './venta-delete-dialog.component';
import { IVenta } from 'app/shared/model/venta.model';

@Injectable({ providedIn: 'root' })
export class VentaResolve implements Resolve<IVenta> {
  constructor(private service: VentaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IVenta> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Venta>) => response.ok),
        map((venta: HttpResponse<Venta>) => venta.body)
      );
    }
    return of(new Venta());
  }
}

export const ventaRoute: Routes = [
  {
    path: '',
    component: VentaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'tiendaOnlineApp.venta.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: VentaDetailComponent,
    resolve: {
      venta: VentaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'tiendaOnlineApp.venta.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: VentaUpdateComponent,
    resolve: {
      venta: VentaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'tiendaOnlineApp.venta.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: VentaUpdateComponent,
    resolve: {
      venta: VentaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'tiendaOnlineApp.venta.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const ventaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: VentaDeletePopupComponent,
    resolve: {
      venta: VentaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'tiendaOnlineApp.venta.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
