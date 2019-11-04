import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { INetKeyIndex } from 'app/shared/model/net-key-index.model';

type EntityResponseType = HttpResponse<INetKeyIndex>;
type EntityArrayResponseType = HttpResponse<INetKeyIndex[]>;

@Injectable({ providedIn: 'root' })
export class NetKeyIndexService {
  public resourceUrl = SERVER_API_URL + 'api/net-key-indices';

  constructor(protected http: HttpClient) {}

  create(netKeyIndex: INetKeyIndex): Observable<EntityResponseType> {
    return this.http.post<INetKeyIndex>(this.resourceUrl, netKeyIndex, { observe: 'response' });
  }

  update(netKeyIndex: INetKeyIndex): Observable<EntityResponseType> {
    return this.http.put<INetKeyIndex>(this.resourceUrl, netKeyIndex, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INetKeyIndex>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INetKeyIndex[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
