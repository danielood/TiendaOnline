import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICompannia } from 'app/shared/model/compannia.model';

type EntityResponseType = HttpResponse<ICompannia>;
type EntityArrayResponseType = HttpResponse<ICompannia[]>;

@Injectable({ providedIn: 'root' })
export class CompanniaService {
  public resourceUrl = SERVER_API_URL + 'api/compannias';

  constructor(protected http: HttpClient) {}

  create(compannia: ICompannia): Observable<EntityResponseType> {
    return this.http.post<ICompannia>(this.resourceUrl, compannia, { observe: 'response' });
  }

  update(compannia: ICompannia): Observable<EntityResponseType> {
    return this.http.put<ICompannia>(this.resourceUrl, compannia, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICompannia>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICompannia[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
