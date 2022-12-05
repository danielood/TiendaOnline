import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Plataforma } from 'app/shared/model/plataforma.model';
import { PlataformaService } from './plataforma.service';
import { PlataformaComponent } from './plataforma.component';
import { PlataformaDetailComponent } from './plataforma-detail.component';
import { PlataformaUpdateComponent } from './plataforma-update.component';
import { PlataformaDeletePopupComponent } from './plataforma-delete-dialog.component';
import { IPlataforma } from 'app/shared/model/plataforma.model';

@Injectable({ providedIn: 'root' })
export class PlataformaResolve implements Resolve<IPlataforma> {
  constructor(private service: PlataformaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPlataforma> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Plataforma>) => response.ok),
        map((plataforma: HttpResponse<Plataforma>) => plataforma.body)
      );
    }
    return of(new Plataforma());
  }
}

export const plataformaRoute: Routes = [
  {
    path: '',
    component: PlataformaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'tiendaOnlineApp.plataforma.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PlataformaDetailComponent,
    resolve: {
      plataforma: PlataformaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'tiendaOnlineApp.plataforma.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PlataformaUpdateComponent,
    resolve: {
      plataforma: PlataformaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'tiendaOnlineApp.plataforma.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PlataformaUpdateComponent,
    resolve: {
      plataforma: PlataformaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'tiendaOnlineApp.plataforma.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const plataformaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PlataformaDeletePopupComponent,
    resolve: {
      plataforma: PlataformaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'tiendaOnlineApp.plataforma.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
