import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Direccion } from 'app/shared/model/direccion.model';
import { DireccionService } from './direccion.service';
import { IDireccion } from 'app/shared/model/direccion.model';

@Injectable({ providedIn: 'root' })
export class DireccionResolve implements Resolve<IDireccion> {
  constructor(private service: DireccionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDireccion> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Direccion>) => response.ok),
        map((direccion: HttpResponse<Direccion>) => direccion.body)
      );
    }
    return of(new Direccion());
  }
}
