import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPlataforma } from 'app/shared/model/plataforma.model';

type EntityResponseType = HttpResponse<IPlataforma>;
type EntityArrayResponseType = HttpResponse<IPlataforma[]>;

@Injectable({ providedIn: 'root' })
export class PlataformaService {
  public resourceUrl = SERVER_API_URL + 'api/plataformas';

  constructor(protected http: HttpClient) {}

  create(plataforma: IPlataforma): Observable<EntityResponseType> {
    return this.http.post<IPlataforma>(this.resourceUrl, plataforma, { observe: 'response' });
  }

  update(plataforma: IPlataforma): Observable<EntityResponseType> {
    return this.http.put<IPlataforma>(this.resourceUrl, plataforma, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPlataforma>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPlataforma[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  findAll(): Observable<EntityArrayResponseType> {
    return this.http.get<IPlataforma[]>(`${this.resourceUrl}/all`, { observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
