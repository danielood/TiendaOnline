import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Valoraciones } from 'app/shared/model/valoraciones.model';
import { ValoracionesService } from './valoraciones.service';
import { ValoracionesComponent } from './valoraciones.component';
import { ValoracionesDetailComponent } from './valoraciones-detail.component';
import { ValoracionesUpdateComponent } from './valoraciones-update.component';
import { ValoracionesDeletePopupComponent } from './valoraciones-delete-dialog.component';
import { IValoraciones } from 'app/shared/model/valoraciones.model';

@Injectable({ providedIn: 'root' })
export class ValoracionesResolve implements Resolve<IValoraciones> {
  constructor(private service: ValoracionesService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IValoraciones> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Valoraciones>) => response.ok),
        map((valoraciones: HttpResponse<Valoraciones>) => valoraciones.body)
      );
    }
    return of(new Valoraciones());
  }
}

export const valoracionesRoute: Routes = [
  {
    path: '',
    component: ValoracionesComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'tiendaOnlineApp.valoraciones.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ValoracionesDetailComponent,
    resolve: {
      valoraciones: ValoracionesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'tiendaOnlineApp.valoraciones.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ValoracionesUpdateComponent,
    resolve: {
      valoraciones: ValoracionesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'tiendaOnlineApp.valoraciones.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ValoracionesUpdateComponent,
    resolve: {
      valoraciones: ValoracionesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'tiendaOnlineApp.valoraciones.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const valoracionesPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ValoracionesDeletePopupComponent,
    resolve: {
      valoraciones: ValoracionesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'tiendaOnlineApp.valoraciones.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
