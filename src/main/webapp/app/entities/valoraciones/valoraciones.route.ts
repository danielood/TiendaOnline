import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Valoraciones } from 'app/shared/model/valoraciones.model';
import { ValoracionesService } from './valoraciones.service';
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

export const valoracionesRoute: Routes = [];

export const valoracionesPopupRoute: Routes = [];
