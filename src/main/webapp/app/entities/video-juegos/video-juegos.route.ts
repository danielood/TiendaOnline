import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { VideoJuegos } from 'app/shared/model/video-juegos.model';
import { VideoJuegosService } from './video-juegos.service';
import { VideoJuegosComponent } from './video-juegos.component';
import { VideoJuegosDetailComponent } from './video-juegos-detail.component';
import { VideoJuegosUpdateComponent } from './video-juegos-update.component';
import { VideoJuegosDeletePopupComponent } from './video-juegos-delete-dialog.component';
import { IVideoJuegos } from 'app/shared/model/video-juegos.model';

@Injectable({ providedIn: 'root' })
export class VideoJuegosResolve implements Resolve<IVideoJuegos> {
  constructor(private service: VideoJuegosService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IVideoJuegos> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<VideoJuegos>) => response.ok),
        map((videoJuegos: HttpResponse<VideoJuegos>) => videoJuegos.body)
      );
    }
    return of(new VideoJuegos());
  }
}

export const videoJuegosRoute: Routes = [
  {
    path: '',
    component: VideoJuegosComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'tiendaOnlineApp.videoJuegos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: VideoJuegosDetailComponent,
    resolve: {
      videoJuegos: VideoJuegosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'tiendaOnlineApp.videoJuegos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: VideoJuegosUpdateComponent,
    resolve: {
      videoJuegos: VideoJuegosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'tiendaOnlineApp.videoJuegos.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: VideoJuegosUpdateComponent,
    resolve: {
      videoJuegos: VideoJuegosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'tiendaOnlineApp.videoJuegos.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const videoJuegosPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: VideoJuegosDeletePopupComponent,
    resolve: {
      videoJuegos: VideoJuegosResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'tiendaOnlineApp.videoJuegos.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
