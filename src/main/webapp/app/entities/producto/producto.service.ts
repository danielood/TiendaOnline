import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProducto, IProductoTabla } from 'app/shared/model/producto.model';

type EntityResponseType = HttpResponse<IProducto>;
type EntityArrayResponseType = HttpResponse<IProducto[]>;
type EntityArrayResponseTabla = HttpResponse<IProductoTabla[]>;

@Injectable({ providedIn: 'root' })
export class ProductoService {
  public resourceUrl = SERVER_API_URL + 'api/productos';

  constructor(protected http: HttpClient) {}

  create(producto: IProducto): Observable<EntityResponseType> {
    return this.http.post<IProducto>(this.resourceUrl, producto, { observe: 'response' });
  }

  update(producto: IProducto): Observable<EntityResponseType> {
    return this.http.put<IProducto>(this.resourceUrl, producto, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProducto>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseTabla> {
    const options = createRequestOption(req);
    return this.http.get<IProductoTabla[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  findAll(): Observable<EntityArrayResponseType> {
    return this.http.get<IProducto[]>(`${this.resourceUrl}/all`, { observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
