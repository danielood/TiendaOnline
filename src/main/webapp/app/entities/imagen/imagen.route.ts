import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Imagen } from 'app/shared/model/imagen.model';
import { ImagenService } from './imagen.service';
import { ImagenComponent } from './imagen.component';
import { ImagenDetailComponent } from './imagen-detail.component';
import { ImagenUpdateComponent } from './imagen-update.component';
import { ImagenDeletePopupComponent } from './imagen-delete-dialog.component';
import { IImagen } from 'app/shared/model/imagen.model';

@Injectable({ providedIn: 'root' })
export class ImagenResolve implements Resolve<IImagen> {
  constructor(private service: ImagenService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IImagen> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Imagen>) => response.ok),
        map((imagen: HttpResponse<Imagen>) => imagen.body)
      );
    }
    return of(new Imagen());
  }
}

export const imagenRoute: Routes = [
  {
    path: '',
    component: ImagenComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'tiendaOnlineApp.imagen.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ImagenDetailComponent,
    resolve: {
      imagen: ImagenResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'tiendaOnlineApp.imagen.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ImagenUpdateComponent,
    resolve: {
      imagen: ImagenResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'tiendaOnlineApp.imagen.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ImagenUpdateComponent,
    resolve: {
      imagen: ImagenResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'tiendaOnlineApp.imagen.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const imagenPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ImagenDeletePopupComponent,
    resolve: {
      imagen: ImagenResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'tiendaOnlineApp.imagen.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
