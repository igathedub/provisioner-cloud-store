import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { INetKey } from 'app/shared/model/net-key.model';

type EntityResponseType = HttpResponse<INetKey>;
type EntityArrayResponseType = HttpResponse<INetKey[]>;

@Injectable({ providedIn: 'root' })
export class NetKeyService {
  public resourceUrl = SERVER_API_URL + 'api/net-keys';

  constructor(protected http: HttpClient) {}

  create(netKey: INetKey): Observable<EntityResponseType> {
    return this.http.post<INetKey>(this.resourceUrl, netKey, { observe: 'response' });
  }

  update(netKey: INetKey): Observable<EntityResponseType> {
    return this.http.put<INetKey>(this.resourceUrl, netKey, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INetKey>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INetKey[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
