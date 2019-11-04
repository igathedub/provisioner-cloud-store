import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRetransmit } from 'app/shared/model/retransmit.model';

type EntityResponseType = HttpResponse<IRetransmit>;
type EntityArrayResponseType = HttpResponse<IRetransmit[]>;

@Injectable({ providedIn: 'root' })
export class RetransmitService {
  public resourceUrl = SERVER_API_URL + 'api/retransmits';

  constructor(protected http: HttpClient) {}

  create(retransmit: IRetransmit): Observable<EntityResponseType> {
    return this.http.post<IRetransmit>(this.resourceUrl, retransmit, { observe: 'response' });
  }

  update(retransmit: IRetransmit): Observable<EntityResponseType> {
    return this.http.put<IRetransmit>(this.resourceUrl, retransmit, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRetransmit>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRetransmit[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
