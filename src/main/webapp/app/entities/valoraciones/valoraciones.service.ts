import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IValoraciones } from 'app/shared/model/valoraciones.model';

type EntityResponseType = HttpResponse<IValoraciones>;
type EntityArrayResponseType = HttpResponse<IValoraciones[]>;

@Injectable({ providedIn: 'root' })
export class ValoracionesService {
  public resourceUrl = SERVER_API_URL + 'api/valoraciones';

  constructor(protected http: HttpClient) {}

  create(valoraciones: IValoraciones): Observable<EntityResponseType> {
    return this.http.post<IValoraciones>(this.resourceUrl, valoraciones, { observe: 'response' });
  }

  update(valoraciones: IValoraciones): Observable<EntityResponseType> {
    return this.http.put<IValoraciones>(this.resourceUrl, valoraciones, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IValoraciones>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IValoraciones[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
