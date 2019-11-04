import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IKeyIndex } from 'app/shared/model/key-index.model';

type EntityResponseType = HttpResponse<IKeyIndex>;
type EntityArrayResponseType = HttpResponse<IKeyIndex[]>;

@Injectable({ providedIn: 'root' })
export class KeyIndexService {
  public resourceUrl = SERVER_API_URL + 'api/key-indices';

  constructor(protected http: HttpClient) {}

  create(keyIndex: IKeyIndex): Observable<EntityResponseType> {
    return this.http.post<IKeyIndex>(this.resourceUrl, keyIndex, { observe: 'response' });
  }

  update(keyIndex: IKeyIndex): Observable<EntityResponseType> {
    return this.http.put<IKeyIndex>(this.resourceUrl, keyIndex, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IKeyIndex>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IKeyIndex[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
