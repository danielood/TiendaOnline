import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Compannia } from 'app/shared/model/compannia.model';
import { CompanniaService } from './compannia.service';
import { CompanniaComponent } from './compannia.component';
import { CompanniaDetailComponent } from './compannia-detail.component';
import { CompanniaUpdateComponent } from './compannia-update.component';
import { CompanniaDeletePopupComponent } from './compannia-delete-dialog.component';
import { ICompannia } from 'app/shared/model/compannia.model';

@Injectable({ providedIn: 'root' })
export class CompanniaResolve implements Resolve<ICompannia> {
  constructor(private service: CompanniaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICompannia> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Compannia>) => response.ok),
        map((compannia: HttpResponse<Compannia>) => compannia.body)
      );
    }
    return of(new Compannia());
  }
}

export const companniaRoute: Routes = [
  {
    path: '',
    component: CompanniaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'tiendaOnlineApp.compannia.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CompanniaDetailComponent,
    resolve: {
      compannia: CompanniaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'tiendaOnlineApp.compannia.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CompanniaUpdateComponent,
    resolve: {
      compannia: CompanniaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'tiendaOnlineApp.compannia.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CompanniaUpdateComponent,
    resolve: {
      compannia: CompanniaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'tiendaOnlineApp.compannia.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const companniaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CompanniaDeletePopupComponent,
    resolve: {
      compannia: CompanniaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'tiendaOnlineApp.compannia.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
