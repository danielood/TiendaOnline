import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IJuegoTabla, IVideoJuegos } from 'app/shared/model/video-juegos.model';

type EntityResponseType = HttpResponse<IVideoJuegos>;
type EntityArrayResponseType = HttpResponse<IVideoJuegos[]>;
type EntityArrayResponseJuegoTabla = HttpResponse<IJuegoTabla[]>;

@Injectable({ providedIn: 'root' })
export class VideoJuegosService {
  public resourceUrl = SERVER_API_URL + 'api/video-juegos';

  constructor(protected http: HttpClient) {}

  create(videoJuegos: IVideoJuegos): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(videoJuegos);
    return this.http
      .post<IVideoJuegos>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(videoJuegos: IVideoJuegos): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(videoJuegos);
    return this.http
      .put<IVideoJuegos>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IVideoJuegos>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseJuegoTabla> {
    const options = createRequestOption(req);
    return this.http.get<IJuegoTabla[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(videoJuegos: IVideoJuegos): IVideoJuegos {
    const copy: IVideoJuegos = Object.assign({}, videoJuegos, {
      fechaLanzamiento:
        videoJuegos.fechaLanzamiento != null && videoJuegos.fechaLanzamiento.isValid()
          ? videoJuegos.fechaLanzamiento.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaLanzamiento = res.body.fechaLanzamiento != null ? moment(res.body.fechaLanzamiento) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((videoJuegos: IVideoJuegos) => {
        videoJuegos.fechaLanzamiento = videoJuegos.fechaLanzamiento != null ? moment(videoJuegos.fechaLanzamiento) : null;
      });
    }
    return res;
  }
}
