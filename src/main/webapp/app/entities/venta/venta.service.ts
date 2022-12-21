import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IVenta, IVentaTabla } from 'app/shared/model/venta.model';
import { IDireccion } from 'app/shared/model/direccion.model';

type EntityResponseType = HttpResponse<IVenta>;
type EntityArrayResponseType = HttpResponse<IVenta[]>;
type EntityArrayResponseType2 = HttpResponse<IDireccion[]>;
type EntityArrayResponseVentaTabla = HttpResponse<IVentaTabla[]>;

@Injectable({ providedIn: 'root' })
export class VentaService {
  public resourceUrl = SERVER_API_URL + 'api/ventas';
  public resourceUrl2 = SERVER_API_URL + 'api/direccions';

  constructor(protected http: HttpClient) {}

  create(venta: IVenta): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(venta);
    return this.http
      .post<IVenta>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(venta: IVenta): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(venta);
    return this.http
      .put<IVenta>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IVenta>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseVentaTabla> {
    const options = createRequestOption(req);
    return this.http
      .get<IVenta[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseVentaTabla) => this.convertDateArrayFromServer(res)));
  }

  findDireccionesByClientId(id: number): Observable<EntityArrayResponseType2> {
    return this.http
      .get<IDireccion[]>(`api/direccions/c/${id}`, { observe: 'response' })
      .pipe(map((res: EntityArrayResponseType2) => this.convertDateArrayFromServer(res)));
  }

  deleteDireccionById(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`api/direccions/${id}`, { observe: 'response' });
  }

  updateDireccionById(direccionDTO: IDireccion): Observable<HttpResponse<any>> {
    console.log(direccionDTO);
    return this.http.put<IDireccion>(this.resourceUrl2, direccionDTO, { observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(venta: IVenta): IVenta {
    const copy: IVenta = Object.assign({}, venta, {
      fechaVenta: venta.fechaVenta != null && venta.fechaVenta.isValid() ? venta.fechaVenta.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaVenta = res.body.fechaVenta != null ? moment(res.body.fechaVenta) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((venta: IVenta) => {
        venta.fechaVenta = venta.fechaVenta != null ? moment(venta.fechaVenta) : null;
      });
    }
    return res;
  }
}
