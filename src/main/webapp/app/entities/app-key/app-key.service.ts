import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAppKey } from 'app/shared/model/app-key.model';

type EntityResponseType = HttpResponse<IAppKey>;
type EntityArrayResponseType = HttpResponse<IAppKey[]>;

@Injectable({ providedIn: 'root' })
export class AppKeyService {
  public resourceUrl = SERVER_API_URL + 'api/app-keys';

  constructor(protected http: HttpClient) {}

  create(appKey: IAppKey): Observable<EntityResponseType> {
    return this.http.post<IAppKey>(this.resourceUrl, appKey, { observe: 'response' });
  }

  update(appKey: IAppKey): Observable<EntityResponseType> {
    return this.http.put<IAppKey>(this.resourceUrl, appKey, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAppKey>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAppKey[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
